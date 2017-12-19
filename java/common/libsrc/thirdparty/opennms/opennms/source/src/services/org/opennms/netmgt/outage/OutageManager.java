//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// 2003 Jan 31: Cleaned up some unused imports.
// 
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.                                                            
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//       
// For more information contact: 
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
// Tab Size = 8
//

package org.opennms.netmgt.outage;

import java.lang.reflect.UndeclaredThrowableException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.concurrent.RunnableConsumerThreadPool;
import org.opennms.core.fiber.PausableFiber;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.config.DbConnectionFactory;
import org.opennms.netmgt.config.OutageManagerConfig;
import org.opennms.netmgt.eventd.EventIpcManager;

/**
 * The OutageManager receives events selectively and maintains a historical
 * archive of each outage for all devices in the database
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="mailto:mike@opennms.org">Mike Davidson </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS.org </A>
 */
public final class OutageManager implements PausableFiber {
    /**
     * The log4j category used to log debug messsages and statements.
     */
    private static final String LOG4J_CATEGORY = "OpenNMS.Outage";

    /**
     * The singleton instance of this class
     */
    private static final OutageManager m_singleton = new OutageManager();
    
    

    /**
     * The number of threads that must be started.
     */
    private static final int NUM_THREADS = 2;

    /**
     * The service table map
     */
    private Map m_serviceTableMap;

    /**
     * The Network where nodes, interfaces and services live
     */
    private BasicNetwork m_network;

    /**
     * The events receiver
     */
    private OutageMgrEventProcessor m_eventReceiver;

    /**
     * The RunnableConsumerThreadPool that runs writers that update the database
     */
    private RunnableConsumerThreadPool m_writerPool;
    
    /**
     * The EventIpcManager to use for sending and receiving events
     */
    private EventIpcManager m_eventMgr;

    /**
     * Current status of this fiber
     */
    private int m_status;

    private OutageManagerConfig m_outageMgrConfig;
    
    private DbConnectionFactory m_dbConnFactory;

    /**
     * Build the servicename to serviceid map - this map is used so as to avoid
     * a database lookup for each incoming event
     */
    private void buildServiceTableMap(java.sql.Connection dbConn) throws SQLException {
        // map of service names to service identifer
        m_serviceTableMap = Collections.synchronizedMap(new HashMap());

        PreparedStatement stmt = dbConn.prepareStatement(OutageConstants.SQL_DB_SVC_TABLE_READ);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            long svcid = rset.getLong(1);
            String svcname = rset.getString(2);

            m_serviceTableMap.put(svcname, new Long(svcid));
        }

        rset.close();
        stmt.close();
    }

    /**
     * Close the currently open outages for services and interfaces that are
     * currently unmanaged
     */
    private void closeOutages(java.sql.Connection dbConn) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());

        if (log.isDebugEnabled())
            log.debug("OutageManager getting ready to close currently open outages for unmanaged services and interfaces ..");

        // get the time to which regained times are to be set
        Timestamp closeTime = new Timestamp((new java.util.Date()).getTime());

        // prepare statements
        PreparedStatement closeForServicesStmt = dbConn.prepareStatement(OutageConstants.DB_CLOSE_OUTAGES_FOR_UNMANAGED_SERVICES);
        PreparedStatement closeForInterfacesStmt = dbConn.prepareStatement(OutageConstants.DB_CLOSE_OUTAGES_FOR_UNMANAGED_INTERFACES);

        // set the time
        closeForServicesStmt.setTimestamp(1, closeTime);
        closeForInterfacesStmt.setTimestamp(1, closeTime);

        // close outages in ifservices
        int num = closeForServicesStmt.executeUpdate();

        if (log.isDebugEnabled())
            log.debug("OutageManager closed " + num + " outages for unmanaged services");

        // close outages in ipinterface
        num = closeForInterfacesStmt.executeUpdate();

        if (log.isDebugEnabled())
            log.debug("OutageManager closed " + num + " outages for unmanaged interfaces");

        // close all db resources
        closeForInterfacesStmt.close();
        closeForServicesStmt.close();

        if (log.isDebugEnabled())
            log.debug("OutageManager closed currently open outages for unmanaged services and interfaces");
    }

    /**
     * The constructor for the OutageManager
     * 
     */
    public OutageManager() {
        m_status = START_PENDING;
    }

    /**
     * Returns a name/id for this process
     */
    public String getName() {
        return "OpenNMS.OutageManager";
    }

    /**
     * Returns the current status
     */
    public int getStatus() {
        return m_status;
    }
    
    public EventIpcManager getEventMgr() {
        return m_eventMgr;
    }
    
    public void setEventMgr(EventIpcManager eventMgr) {
        m_eventMgr = eventMgr;
    }

    public void init() {
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        Category log = ThreadCategory.getInstance(getClass());
        
        if (m_eventMgr == null)
            throw new IllegalStateException("OutageManager.m_eventMgr is not set");
        
        if (m_outageMgrConfig == null)  
            throw new IllegalStateException("OutageManager.m_outageMgrConfig is not set");
        
        m_network = new BasicNetwork(getGetNextOutageID());

        // load the outage configuration and get the required attributes
        int numWriters = getNumWriters();

        //
        // Make sure we can connect to the database
        // - Close the open outages for unmanaged interfaces and services
        // - build a mapping of service name to service id from the service
        // table
        //
        java.sql.Connection conn = null;
        try {
            conn = getConnection();

            // close open outages for unmanaged entities
            closeOutages(conn);

            // build the service table map
            buildServiceTableMap(conn);
        } catch (SQLException sqlE) {
            log.fatal("Error closing outages for unmanaged services and interfaces or building servicename to serviceid mapping", sqlE);
            throw new UndeclaredThrowableException(sqlE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

        m_writerPool = new RunnableConsumerThreadPool("Outage Writer Pool", 0.6f, 1.0f, numWriters);

        if (log.isDebugEnabled())
            log.debug("Created writer pool");

        m_eventReceiver = new OutageMgrEventProcessor(this, m_writerPool.getRunQueue());
        if (log.isDebugEnabled())
            log.debug("Created event receiver");

        log.info("OutageManager ready to accept events");
    }

    Connection getConnection() throws SQLException {
        return m_dbConnFactory.getConnection();
    }
    
    public void setDbConnectionFactory(DbConnectionFactory dbConnFactory) {
        m_dbConnFactory = dbConnFactory;
    }

    public void setOutageMgrConfig(OutageManagerConfig config) {
        m_outageMgrConfig = config;
    }
    
    public OutageManagerConfig getOutageMgrConfig() {
        return m_outageMgrConfig;
    }

    /**
     * @param numWriters
     * @param log
     * @return
     */
    private int getNumWriters() {
        Category log = ThreadCategory.getInstance(getClass());

        int numWriters = 1;
        numWriters = m_outageMgrConfig.getWriters();
        return numWriters;
    }

    /**
     * Read the configuration xml, create and start all the subthreads.
     */
    public void start() {
        ThreadCategory.setPrefix(LOG4J_CATEGORY);

        Category log = ThreadCategory.getInstance(getClass());

        m_status = STARTING;

        //
        // Start all the threads
        //
        if (log.isDebugEnabled()) {
            log.debug("Starting writer pool");
        }

        m_writerPool.start();

        //
        // Subscribe to eventd
        //
        try {
            m_eventReceiver.start();
        } catch (Throwable t) {
            m_writerPool.stop();
            if (log.isDebugEnabled())
                log.debug("Writer pool shutdown");

            throw new UndeclaredThrowableException(t);
        }

        m_status = RUNNING;

        if (log.isDebugEnabled()) {
            log.debug("Outage Writer threads started");
        }

        log.info("OutageManager ready to accept events");
    }

    /**
     * Pauses all the threads
     */
    public void pause() {
        if (m_status != RUNNING)
            return;

        m_status = PAUSE_PENDING;

        Category log = ThreadCategory.getInstance(getClass());

        m_status = PAUSED;

        if (log.isDebugEnabled())
            log.debug("Finished pausing all threads");
    }

    /**
     * Resumes all the threads
     */
    public void resume() {
        if (m_status != PAUSED)
            return;

        m_status = RESUME_PENDING;

        Category log = ThreadCategory.getInstance(getClass());

        m_status = RUNNING;

        if (log.isDebugEnabled())
            log.debug("Finished resuming ");
    }

    /**
     * Stops all the threads
     */
    public void stop() {
        m_status = STOP_PENDING;

        Category log = ThreadCategory.getInstance(getClass());

        try {
            if (log.isDebugEnabled())
                log.debug("Beginning shutdown process");

            //
            // Close connection to the event subsystem and free associated
            // resources.
            //
            m_eventReceiver.close();

            if (log.isDebugEnabled())
                log.debug("sending shutdown to writers");

            m_writerPool.stop();

            if (log.isDebugEnabled())
                log.debug("Outage Writers shutdown");

            m_status = STOPPED;

            log.info("OutageManager shutdown complete");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Return the service id for the name passed
     * 
     * @param svcname
     *            the service name whose service id is required
     * 
     * @return the service id for the name passed, -1 if not found
     */
    public synchronized long getServiceID(String svcname) {
        Long val = (Long) m_serviceTableMap.get(svcname);
        if (val != null) {
            return val.longValue();
        } else {
            return -1;
        }
    }

    /**
     * Add the svcname/svcid mapping to the servicetable map
     */
    public synchronized void addServiceMapping(String svcname, long serviceid) {
        m_serviceTableMap.put(svcname, new Long(serviceid));
    }

    /**
     * Return a handle to the OutageManager
     */
    public static OutageManager getInstance() {
        return m_singleton;
    }

    public String getGetNextOutageID() {
        return m_outageMgrConfig.getGetNextOutageID();
    }
    
    public BasicNetwork getNetwork() {
        return m_network;
    }
}
