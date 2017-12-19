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
// 2003 Nov 11: Merged changes from Rackspace project
// 2003 Nov 10: Removed event cache calls - too many issues - set outage writer threads to 1
// 2003 Jan 31: Cleaned up some unused imports. 
// 2003 Jan 08: Changed SQL "= null" to "is null" to work with Postgres 7.2
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.opennms.netmgt.xml.event.Parms;
import org.opennms.netmgt.xml.event.Value;

/**
 * When a 'nodeLostService' is received, it is made sure that there is no 'open'
 * outage record in the 'outages' table for this nodeid/ipaddr/serviceid - i.e
 * that there is not already a record for this n/i/s where the 'lostService'
 * time is known and the 'regainedService' time is NULL - if there is, the
 * current 'lostService' event is ignored else a new outage is created.
 * 
 * The 'interfaceDown' is similar to the 'nodeLostService' except that it acts
 * relevant to a nodeid/ipaddr combination and a 'nodeDown' acts on a nodeid.
 * 
 * When a 'nodeRegainedService' is received and there is an 'open' outage for
 * the nodeid/ipaddr/serviceid, the outage is cleared. If not, the event is
 * placed in the event cache in case a race condition has occurred that puts the
 * "up" event in before the "down" event. (currently inactive).
 * 
 * The 'interfaceUp' is similar to the 'nodeRegainedService' except that it acts
 * relevant to a nodeid/ipaddr combination and a 'nodeUp' acts on a nodeid.
 * 
 * When a 'deleteService' is received, the appropriate entry is marked for
 * deletion is the 'ifservices' table - if this entry is the only entry for a
 * node/ip combination, the corresponding entry in the 'ipinterface' table is
 * marked for deletion and this is then cascaded to the node table All deletions
 * are followed by an appropriate event(serviceDeleted or interfaceDeleted or..)
 * being generated and sent to eventd.
 * 
 * When an 'interfaceReparented' event is received, 'outages' table entries
 * associated with the old nodeid/interface pairing are changed so that those
 * outage entries will be associated with the new nodeid/interface pairing.
 * 
 * The nodeLostService, interfaceDown, nodeDown, nodeUp, interfaceUp,
 * nodeRegainedService, deleteService events update the svcLostEventID and the
 * svcRegainedEventID fields as approppriate. The interfaceReparented event has
 * no impact on these eventid reference fields.
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="mailto:mike@opennms.org">Mike Davidson </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS.org </A>
 */
public final class OutageWriter implements Runnable {
    private static final String SNMP_SVC = "SNMP";

    private static final String SNMPV2_SVC = "SNMPv2";

    /**
     * The event from which data is to be read.
     */
    private Event m_event;

    // Control whether or not an event is generated following
    // database modifications to notify other OpenNMS processes
    private boolean m_generateNodeDeletedEvent;

    private OutageManager m_outageMgr;

    private BasicNetwork m_network;


    /**
     * <P>
     * This method is used to convert the service name into a service id. It
     * first looks up the information from a service map in OutagesManager and
     * if no match is found, by performing a lookup in the database. If the
     * conversion is successful then the corresponding integer identifier will
     * be returned to the caller.
     * </P>
     * 
     * @param name
     *            The name of the service
     * 
     * @return The integer identifier for the service name.
     * 
     * @throws java.sql.SQLException
     *             if there is an error accessing the stored data, the SQL text
     *             is malformed, or the result cannot be obtained.
     * 
     * @see org.opennms.netmgt.outage.OutageConstants#DB_GET_SVC_ID
     *      DB_GET_SVC_ID
     */
    private long getServiceID(String name) throws SQLException {
        //
        // Check the name to make sure that it is not null
        //
        if (name == null)
            throw new NullPointerException("The service name was null");

        // ask OutageManager
        //
        long id = m_outageMgr.getServiceID(name);
        if (id != -1)
            return id;

        //
        // talk to the database and get the identifer
        //
        Connection dbConn = null;
        try {
            dbConn = getConnection();

            // SQL statement to get service id for a servicename from the
            // service table
            PreparedStatement serviceStmt = dbConn.prepareStatement(OutageConstants.DB_GET_SVC_ID);

            serviceStmt.setString(1, name);
            ResultSet rset = serviceStmt.executeQuery();
            if (rset.next()) {
                id = rset.getLong(1);
            }

            // close result set
            rset.close();

            // close statement
            if (serviceStmt != null)
                serviceStmt.close();
        } finally {
            try {
                if (dbConn != null)
                    dbConn.close();
            } catch (SQLException e) {
                ThreadCategory.getInstance(getClass()).warn("Exception closing JDBC connection", e);
            }
        }

        // Record the new find
        //
        if (id != -1)
            m_outageMgr.addServiceMapping(name, id);

        //
        // return the id to the caller
        //
        return id;
    }

    /**
     * Handles node lost service events. Record the 'nodeLostService' event in
     * the outages table - create a new outage entry if the service is not
     * already down.
     */
    private void handleNodeLostService(long eventID, String eventTime, BasicService svc) {

        Category log = ThreadCategory.getInstance(OutageWriter.class);
       if (eventID == -1 || !svc.isValid()) {
            log.warn(EventConstants.NODE_LOST_SERVICE_EVENT_UEI + " ignored - info incomplete - eventid/nodeid/ip/svc: " + eventID + "/" + svc);
            return;
        }

        // check that there is no 'open' entry already
        Connection dbConn = null;

        try {
            dbConn = getConnection();
            if (!svc.openOutage(dbConn, eventID, eventTime)) {
                log.warn("\'" + EventConstants.NODE_LOST_SERVICE_EVENT_UEI + "\' for " + svc + " ignored - table already  has an open record ");
            }
            
            // commit work
            DbUtil.commit(dbConn, "nodeLostService : " + svc + " recorded in DB", "outage could not be created for nodeid/ipAddr/service: " + svc);

        } catch (SQLException sqle) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'nodeLostService\'", sqle);
        } finally {
            DbUtil.close(dbConn);
        }

    }

    /**
     * Handles interface down events. Record the 'interfaceDown' event in the
     * outages table - create a new outage entry for each active service of the
     * nodeid/ip if service not already down.
     */
    private void handleInterfaceDown(long eventID, String eventTime, BasicInterface iface) {
        if (eventID == -1 || !iface.isValid()) {
            ThreadCategory.getInstance(getClass()).warn(EventConstants.INTERFACE_DOWN_EVENT_UEI + " ignored - info incomplete - eventid/nodeid/ip: " + eventID + "/" + iface);
            return;
        }

        Connection dbConn = null;
        try {
            dbConn = getConnection();

            iface.openOutages(dbConn, eventID, eventTime);

            // commit work
            DbUtil.commit(dbConn, "Outage recorded for all active services for " + iface, "interfaceDown could not be recorded  for nodeid/ipAddr: " + iface);

        } catch (SQLException sqle) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'interfaceDown\'", sqle);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    /**
     * Handles node down events. Record the 'nodeDown' event in the outages
     * table - create a new outage entry for each active service of the nodeid
     * if service is not already down.
     */
    private void handleNodeDown(long eventID, String eventTime, BasicNode node) {
        if (eventID == -1 || !node.isValid()) {
            ThreadCategory.getInstance(getClass()).warn(EventConstants.NODE_DOWN_EVENT_UEI + " ignored - info incomplete - eventid/nodeid: " + eventID + "/" + node);
            return;
        }

        Connection dbConn = null;
        try {
            dbConn = getConnection();

            node.openOutages(dbConn, eventID, eventTime);

            // commit work
            DbUtil.commit(dbConn, "Outage recorded for all active services for " + node, "nodeDown could not be recorded  for nodeId: " + node);

        } catch (SQLException sqle) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'nodeDown\'", sqle);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    /**
     * Handle node up events. Record the 'nodeUp' event in the outages table -
     * close all open outage entries for the nodeid in the outages table.
     */
    private void handleNodeUp(long eventID, String eventTime, BasicNode node) {
        Category log = ThreadCategory.getInstance(OutageWriter.class);

        if (eventID == -1 || !node.isValid()) {
            log.warn(EventConstants.NODE_UP_EVENT_UEI + " ignored - info incomplete - eventid/nodeid: " + eventID + "/" + node);
            return;
        }

        Connection dbConn = null;
        try {
            dbConn = getConnection();

            int count = 0;

            if (node.openOutageExists(dbConn)) {
                count = node.closeOutages(dbConn, eventID, eventTime);
            } else {
                // Outage table does not have an open record.
                log.warn("\'" + EventConstants.NODE_UP_EVENT_UEI + "\' for " + node + " no open record.");
            }

            // commit work
            DbUtil.commit(dbConn, "nodeUp closed " + count + " outages for nodeid " + node + " in DB", "nodeUp could not be recorded  for nodeId: " + node);

        } catch (SQLException se) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'nodeRegainedService\'", se);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    /**
     * Handles interface up events. Record the 'interfaceUp' event in the
     * outages table - close all open outage entries for the nodeid/ip in the
     * outages table.
     */
    private void handleInterfaceUp(long eventID, String eventTime, BasicInterface iface) {
        Category log = ThreadCategory.getInstance(OutageWriter.class);

        if (eventID == -1 || !iface.isValid()) {
            log.warn(EventConstants.INTERFACE_UP_EVENT_UEI + " ignored - info incomplete - eventid/nodeid/ipAddr: " + eventID + "/" + iface);
            return;
        }

        Connection dbConn = null;
        try {
            dbConn = getConnection();

            if (iface.openOutageExists(dbConn)) {
                int count = iface.closeOutages(dbConn, eventID, eventTime);

                // commit work
                DbUtil.commit(dbConn, "handleInterfaceUp: interfaceUp closed " + count + " outages for nodeid/ip " + iface + " in DB", "interfaceUp could not be recorded for nodeId/ipaddr: " + iface);
            } else {
                log.warn("\'" + EventConstants.INTERFACE_UP_EVENT_UEI + "\' for " + iface + " ignored.");
            }
        } catch (SQLException se) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'interfaceUp\'", se);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    /**
     * Hanlde node regained service events. Record the 'nodeRegainedService'
     * event in the outages table - close the outage entry in the table if the
     * service is currently down.
     * @param svc TODO
     */
    private void handleNodeRegainedService(long eventID, String eventTime, BasicService svc) {
        Category log = ThreadCategory.getInstance(OutageWriter.class);

        if (eventID == -1 || !svc.isValid()) {
            log.warn(EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI + " ignored - info incomplete - eventid/nodeid/ip/svc: " + eventID + "/" + svc);
            return;
        }

        Connection dbConn = null;
        try {
            dbConn = getConnection();

            if (svc.openOutageExists(dbConn)) {
                svc.closeOutage(dbConn, eventID, eventTime);

                // commit work
                DbUtil.commit(dbConn, "nodeRegainedService: closed outage for nodeid/ip/service " + svc + " in DB", "nodeRegainedService could not be recorded  for nodeId/ipAddr/service: " + svc);
            } else {
                // Outage table does not have an open record.
                log.warn("\'" + EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI + "\' for " + svc + " does not have open record.");
            }
        } catch (SQLException se) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'nodeRegainedService\'", se);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    /**
     * <p>
     * Record the 'interfaceReparented' event in the outages table.
     * Change'outages' table entries associated with the old nodeid/interface
     * pairing so that those outage entries will be associated with the new
     * nodeid/interface pairing.
     * </p>
     * 
     * <p>
     * <strong>Note: </strong>This event has no impact on the event id reference
     * fields
     * </p>
     */
    private void handleInterfaceReparented(String ipAddr, Parms eventParms) {
        Category log = ThreadCategory.getInstance(OutageWriter.class);

        if (log.isDebugEnabled())
            log.debug("interfaceReparented event received...");

        if (ipAddr == null || eventParms == null) {
            log.warn(EventConstants.INTERFACE_REPARENTED_EVENT_UEI + " ignored - info incomplete - ip/parms: " + ipAddr + "/" + eventParms);
            return;
        }

        long oldNodeId = -1;
        long newNodeId = -1;

        String parmName = null;
        Value parmValue = null;
        String parmContent = null;

        Enumeration parmEnum = eventParms.enumerateParm();
        while (parmEnum.hasMoreElements()) {
            Parm parm = (Parm) parmEnum.nextElement();
            parmName = parm.getParmName();
            parmValue = parm.getValue();
            if (parmValue == null)
                continue;
            else
                parmContent = parmValue.getContent();

            // old nodeid
            if (parmName.equals(EventConstants.PARM_OLD_NODEID)) {
                try {
                    oldNodeId = Integer.valueOf(parmContent).intValue();
                } catch (NumberFormatException nfe) {
                    log.warn("Parameter " + EventConstants.PARM_OLD_NODEID + " cannot be non-numeric");
                    oldNodeId = -1;
                }

            }

            // new nodeid
            else if (parmName.equals(EventConstants.PARM_NEW_NODEID)) {
                try {
                    newNodeId = Integer.valueOf(parmContent).intValue();
                } catch (NumberFormatException nfe) {
                    log.warn("Parameter " + EventConstants.PARM_NEW_NODEID + " cannot be non-numeric");
                    newNodeId = -1;
                }
            }
        }

        if (newNodeId == -1 || oldNodeId == -1) {
            log.warn("Unable to process 'interfaceReparented' event, invalid event parm.");
            return;
        }

        BasicInterface iface = getInterface(oldNodeId, ipAddr);
        Connection dbConn = null;
        try {
            dbConn = getConnection();

            // Set the database commit mode
            dbConn.setAutoCommit(false);

            // Issue SQL update to change the 'outages' table entries
            // associated with the old nodeid/interface pairing
            // so that those outage entries will be associated with
            // the new nodeid/interface pairing.

            // Prepare SQL statement used to reparent outage table entries -
            // used when a 'interfaceReparented' event is received
            PreparedStatement reparentOutagesStmt = dbConn.prepareStatement(OutageConstants.DB_REPARENT_OUTAGES);
            reparentOutagesStmt.setLong(1, newNodeId);
            reparentOutagesStmt.setLong(2, iface.getNodeId());
            reparentOutagesStmt.setString(3, iface.getIpAddr());
            int count = reparentOutagesStmt.executeUpdate();

            // commit work
            String s = "Reparented " + count + " outages - ip: " + iface.getIpAddr() + " reparented from " + iface.getNodeId() + " to " + newNodeId;
            String f = "reparent outages failed for newNodeId/ipAddr: " + newNodeId + "/" + iface.getIpAddr();
            DbUtil.commit(dbConn, s, f);

            // close statement
            reparentOutagesStmt.close();

        } catch (SQLException se) {
            DbUtil.rollback(dbConn, "SQL exception while handling \'interfaceReparented\'", se);
        } finally {
            DbUtil.close(dbConn);
        }
    }

    private Connection getConnection() throws SQLException {
        return m_outageMgr.getConnection();
    }
    
    private BasicNetwork getNetwork() {
        return m_outageMgr.getNetwork();
    }

    /**
     * This method creates an event for the passed parameters.
     * 
     * @param uei
     *            Event to generate and send
     * @param eventDate
     *            Time to be set for the event
     * @param nodeID
     *            Node identifier associated with this event
     * @param ipAddr
     *            Interface address associated with this event
     * @param serviceName
     *            Service name associated with this event
     */
    private Event createEvent(String uei, java.util.Date eventDate, long nodeID, String ipAddr, String serviceName) {
        // build event to send
        Event newEvent = new Event();

        newEvent.setUei(uei);
        newEvent.setSource("OutageManager");

        // Convert integer nodeID to String
        newEvent.setNodeid(nodeID);

        if (ipAddr != null)
            newEvent.setInterface(ipAddr);

        if (serviceName != null)
            newEvent.setService(serviceName);

        newEvent.setTime(EventConstants.formatToString(eventDate));

        return newEvent;
    }

    /**
     * Process an event. Read the event UEI, nodeid, interface and service -
     * depending on the UEI, read event parms, if necessary, and process as
     * appropriate.
     */
    private void processEvent() {
        Category log = ThreadCategory.getInstance(OutageWriter.class);

        if (m_event == null) {
            if (log.isDebugEnabled())
                log.debug("Event is null, nothing to process");
            return;
        }

        if (log.isDebugEnabled())
            log.debug("About to process event: " + m_event.getUei());

        //
        // Check to make sure the event has a uei
        //
        String uei = m_event.getUei();
        if (uei == null) {
            // should only get registered events
            if (log.isDebugEnabled())
                log.debug("Event received with null UEI, ignoring event");
            return;
        }

        // get eventid
        long eventID = -1;
        if (m_event.hasDbid())
            eventID = m_event.getDbid();

        // convert the node id
        long nodeID = -1;
        if (m_event.hasNodeid())
            nodeID = m_event.getNodeid();

        String ipAddr = m_event.getInterface();
        String service = m_event.getService();
        String eventTime = m_event.getTime();

        if (log.isDebugEnabled())
            log.debug("processEvent: Event\nuei\t\t" + uei + "\neventid\t\t" + eventID + "\nnodeid\t\t" + nodeID + "\nipaddr\t\t" + ipAddr + "\nservice\t\t" + service + "\neventtime\t" + (eventTime != null ? eventTime : "<null>"));

        // get service id for the service name
        long serviceID = -1;
        if (service != null) {
            try {
                serviceID = getServiceID(service);
            } catch (SQLException sqlE) {
                log.warn("Error converting service name \"" + service + "\" to an integer identifier, storing -1", sqlE);
            }
        }

        //
        // Check for any of the following UEIs:
        //
        // nodeLostService
        // interfaceDown
        // nodeDown
        // nodeUp
        // interfaceUp
        // nodeRegainedService
        // deleteService
        // interfaceReparented
        //
        if (uei.equals(EventConstants.NODE_LOST_SERVICE_EVENT_UEI)) {
            handleNodeLostService(eventID, eventTime, getService(nodeID, ipAddr, serviceID));
        } else if (uei.equals(EventConstants.INTERFACE_DOWN_EVENT_UEI)) {
            handleInterfaceDown(eventID, eventTime, getInterface(nodeID, ipAddr));
        } else if (uei.equals(EventConstants.NODE_DOWN_EVENT_UEI)) {
            handleNodeDown(eventID, eventTime, getNode(nodeID));
        } else if (uei.equals(EventConstants.NODE_UP_EVENT_UEI)) {
            handleNodeUp(eventID, eventTime, getNode(nodeID));
        } else if (uei.equals(EventConstants.INTERFACE_UP_EVENT_UEI)) {
            handleInterfaceUp(eventID, eventTime, getInterface(nodeID, ipAddr));
        } else if (uei.equals(EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI)) {
            handleNodeRegainedService(eventID, eventTime, getService(nodeID, ipAddr, serviceID));
        } else if (uei.equals(EventConstants.INTERFACE_REPARENTED_EVENT_UEI)) {
            handleInterfaceReparented(ipAddr, m_event.getParms());
        }
    }

    private BasicNode getNode(long nodeID) {
        return getNetwork().getNode(nodeID);
    }

    private BasicInterface getInterface(long nodeID, String ipAddr) {
        return getNetwork().getInterface(nodeID, ipAddr);
    }

    private BasicService getService(long nodeID, String ipAddr, long serviceID) {
        return getNetwork().getService(nodeID, ipAddr, serviceID);
    }
    
    private BasicService getService(BasicInterface iface, long serviceID) {
        return getNetwork().getService(iface, serviceID);
    }

    /**
     * The constructor.
     * @param mgr
     * 
     * @param event
     *            the event for this outage writer.
     */
    public OutageWriter(OutageManager mgr, Event event) {
        m_outageMgr = mgr;
        m_network = mgr.getNetwork();
        m_event = event;
    }

    /**
     * Process the event depending on the UEI.
     */
    public void run() {
        try {
            processEvent();
        } catch (Throwable t) {
            Category log = ThreadCategory.getInstance(OutageWriter.class);
            log.warn("Unexpected exception processing event", t);
        }
    }
}
