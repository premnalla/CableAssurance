//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2005 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// 2003 Nov 11: Merged changes from Rackspace project
// 2003 Jan 31: Cleaned up some unused imports.
// 2004 Sep 08: Completely reorganize to clean up the delete code.
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

package org.opennms.netmgt.capsd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Category;
import org.opennms.core.queue.FifoQueue;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.CapsdConfigFactory;
import org.opennms.netmgt.config.DatabaseConnectionFactory;
import org.opennms.netmgt.config.OpennmsServerConfigFactory;
import org.opennms.netmgt.eventd.EventIpcManagerFactory;
import org.opennms.netmgt.eventd.EventListener;
import org.opennms.netmgt.utils.XmlrpcUtil;
import org.opennms.netmgt.xml.event.Event;

/**
 * @author <a href="mailto:matt@opennms.org">Matt Brozowski </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
final class BroadcastEventProcessor implements EventListener {

    /**
     * SQL statement used to add an interface/server mapping into the database;
     */
    private static String SQL_ADD_INTERFACE_TO_SERVER = "INSERT INTO serverMap VALUES (?, ?)";

    /**
     * SQL statement used to add an interface/service mapping into the database.
     */
    private static String SQL_ADD_SERVICE_TO_MAPPING = "INSERT INTO serviceMap VALUES (?, ?)";

    /**
     * SQL statement used to count all the interface on a node
     */
    private static String SQL_COUNT_INTERFACES_ON_NODE = "SELECT count(ipaddr) FROM ipinterface WHERE nodeid = (SELECT nodeid FROM node WHERE nodelabel = ?) ";

    /**
     * SQL statement used to delete all the ipinterfaces with a specified
     * nodeid.
     */
    private static String SQL_DELETE_ALL_INTERFACES_ON_NODE = "DELETE FROM ipinterface WHERE nodeid = ?";

    /**
     * SQL statement used to delete all services mapping to a specified
     * interface from the database.
     */
    private static String SQL_DELETE_ALL_SERVICES_INTERFACE_MAPPING = "DELETE FROM serviceMap WHERE ipaddr = ?";

    /**
     * SQL statement used to delete all assets from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_ASSETS_ON_NODE = "DELETE FROM assets WHERE nodeid = ?";

    /**
     * SQL statement used to delete all events associated with a specified
     * interface from the database.
     */
    private static String SQL_DELETE_EVENTS_ON_INTERFACE = "DELETE FROM events " + "WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to delete all events from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_EVENTS_ON_NODE = "DELETE FROM events WHERE nodeid = ?";

    /**
     * SQL statement used to delete all ifservices from the database with a
     * specified interface.
     */
    private static String SQL_DELETE_IFSERVICES_ON_INTERFACE = "DELETE FROM ifservices WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to delete all ifservices from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_IFSERVICES_ON_NODE = "DELETE FROM ifservices WHERE nodeid = ?";

    /**
     * SQL statement used to delete an ipinterfac with a specified nodeid and
     * ipaddress.
     */
    private static String SQL_DELETE_INTERFACE = "DELETE FROM ipinterface WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to delete an interface/server mapping from the
     * database.
     */
    private static String SQL_DELETE_INTERFACE_ON_SERVER = "DELETE FROM serverMap WHERE ipaddr = ? AND servername = ?";

    /**
     * SQL statement used to delete a node from the database with a specified
     * nodelabel.
     */
    private static String SQL_DELETE_NODEID = "DELETE FROM node WHERE nodeid = ?";

    /**
     * SQL statement used to delete all notifications associated with a
     * specified interface from the database.
     */
    private static String SQL_DELETE_NOTIFICATIONS_ON_INTERFACE = "DELETE FROM notifications " + "WHERE nodeid = ? AND interfaceid = ?";

    /**
     * SQL statement used to delete all notifications from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_NOTIFICATIONS_ON_NODE = "DELETE FROM notifications WHERE nodeid = ?";

    /**
     * SQL statement used to delete all notifications associated with a
     * specified interface from the database.
     */
    private static String SQL_DELETE_OUTAGES_ON_INTERFACE = "DELETE FROM outages " + "WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to delete all outages from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_OUTAGES_ON_NODE = "DELETE FROM outages WHERE nodeid = ?";

    /**
     * SQL statement used to delete an interface/service mapping from the
     * database.
     */
    private static String SQL_DELETE_SERVICE_INTERFACE_MAPPING = "DELETE FROM serviceMap WHERE ipaddr = ? AND servicemapname = ?";

    /**
     * SQL statement used to delete the snmpinterface entry associated with a
     * specified interface from the database.
     */
    private static String SQL_DELETE_SNMPINTERFACE_ON_INTERFACE = "DELETE FROM snmpinterface " + "WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to delete all snmpinterface from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_SNMPINTERFACE_ON_NODE = "DELETE FROM snmpinterface WHERE nodeid = ?";

    /**
     * SQL statement used to delete all usersnotified info associated with a
     * specified interface from the database.
     */
    private static String SQL_DELETE_USERSNOTIFIED_ON_INTERFACE = "DELETE FROM usersnotified WHERE notifyid IN " + "(SELECT notifid FROM notifications " + " WHERE nodeid = ? AND interfaceid = ?)";

    /**
     * SQL statement used to delete all usersNotified from the database with a
     * specified nodeid.
     */
    private static String SQL_DELETE_USERSNOTIFIED_ON_NODE = "DELETE FROM usersNotified WHERE notifyID IN ( SELECT notifyID from notifications WHERE nodeid = ?)";

    /**
     * SQL statement used to count all the interface on a node
     */
    private static String SQL_FIND_SERVICES_ON_NODE = "SELECT ifservices.ipaddr, service.servicename FROM ifservices, service WHERE nodeID = ?";

    /**
     * SQL statement used to verify if an ipinterface with the specified ip
     * address exists in the database and retrieve the nodeid if exists.
     */
    private static String SQL_QUERY_IPADDRESS_EXIST = "SELECT nodeid FROM ipinterface WHERE ipaddr = ? AND isManaged !='D'";

    /**
     * SQL statement used to query the 'node' and 'ipinterface' tables to verify
     * if a specified ipaddr and node label have already exist in the database.
     */
    private static String SQL_QUERY_IPINTERFACE_EXIST = "SELECT nodelabel, ipaddr FROM node, ipinterface WHERE node.nodeid = ipinterface.nodeid AND node.nodelabel = ? AND ipinterface.ipaddr = ? AND isManaged !='D' AND nodeType !='D'";

    /**
     * SQL statement used to query if a node with the specified nodelabel exist
     * in the database, and the nodeid from the database if exists.
     */
    private static String SQL_QUERY_NODE_EXIST = "SELECT nodeid, dpname FROM node WHERE nodelabel = ? AND nodeType !='D'";

    /**
     * SQL statement used to query if an interface is the snmp primary interface
     * of a node.
     */
    private static String SQL_QUERY_PRIMARY_INTERFACE = "SELECT isSnmpPrimary FROM ipinterface " + "WHERE nodeid = ? AND ipaddr = ?";

    /**
     * SQL statement used to verify if an ifservice with the specified ip
     * address and service name exists in the database.
     */
    private static String SQL_QUERY_SERVICE_EXIST = "SELECT nodeid FROM ifservices, service WHERE ifservices.serviceid = service.serviceid AND ipaddr = ? AND servicename = ? AND status !='D'";

    /**
     * SQL statement used to query if an interface/service mapping already
     * exists in the database.
     */
    private static String SQL_QUERY_SERVICE_MAPPING_EXIST = "SELECT * FROM serviceMap WHERE ipaddr = ? AND servicemapname = ?";

    /**
     * SQL query to retrieve nodeid of a particulary interface address
     */
    private static String SQL_RETRIEVE_NODEID = "select nodeid from ipinterface where ipaddr=? and isManaged!='D'";

    /**
     * SQL statement used to retrieve the serviced id from the database with a
     * specified service name.
     */
    private static String SQL_RETRIEVE_SERVICE_ID = "SELECT serviceid FROM service WHERE servicename = ?";

    /**
     * Determines if deletePropagation is enabled in the Outage Manager.
     * 
     * @return true if deletePropagation is enable, false otherwise
     */
    public static boolean isPropagationEnabled() {
        return CapsdConfigFactory.getInstance().getDeletePropagationEnabled();
    }

    /**
     * FIXME: finish the doc here
     * 
     * @return Returns the xmlrpc.
     */
    public static boolean isXmlRpcEnabled() {
        return CapsdConfigFactory.getInstance().getXmlrpc().equals("true");
    }

    /**
     * local openNMS server name
     */
    private String m_localServer = null;

    /**
     * Set of event ueis that we should notify when we receive and when a
     * success or failure occurs.
     */
    private Set m_notifySet = new HashSet();

    /**
     * The Capsd rescan scheduler
     */
    private Scheduler m_scheduler;

    /**
     * The location where suspectInterface events are enqueued for processing.
     */
    private FifoQueue m_suspectQ;

    /**
     * Constructor
     * 
     * @param suspectQ
     *            The queue where new SuspectEventProcessor objects are enqueued
     *            for running..
     * @param scheduler
     *            Rescan scheduler.
     */
    BroadcastEventProcessor(FifoQueue suspectQ, Scheduler scheduler) {
        // Suspect queue
        //
        m_suspectQ = suspectQ;

        // Scheduler
        //
        m_scheduler = scheduler;

        // the local servername
        m_localServer = OpennmsServerConfigFactory.getInstance().getServerName();

        // Subscribe to eventd
        //
        createMessageSelectorAndSubscribe();
    }

    /**
     * Unsubscribe from eventd
     */
    public void close() {
        EventIpcManagerFactory.getInstance().getManager().removeEventListener(this);
    }

    /**
     * Counts the number of interfaces on the node other than a given interface
     * 
     * @param dbConn
     *            the database connection
     * @param nodeid
     *            the node to check interfaces for
     * @param ipAddr
     *            the interface not to include in the count
     * @return the numer of interfaces other than the given one
     * @throws SQLException
     *             if an error occurs talking to the database
     */
    private int countOtherInterfacesOnNode(Connection dbConn, long nodeId, String ipAddr) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());

        final String DB_COUNT_OTHER_INTERFACES_ON_NODE = "SELECT count(*) FROM ipinterface WHERE nodeID=? and ipAddr != ? and isManaged != 'D'";

        PreparedStatement stmt = dbConn.prepareStatement(DB_COUNT_OTHER_INTERFACES_ON_NODE);
        stmt.setLong(1, nodeId);
        stmt.setString(2, ipAddr);
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }

        if (log.isDebugEnabled())
            log.debug("countServicesForInterface: count services for interface " + nodeId + "/" + ipAddr + ": found " + count);

        stmt.close();

        return count;
    }

    /**
     * Counts the number of other non deleted services associated with the
     * interface defined by nodeid/ipAddr
     * 
     * @param dbConn
     *            the database connection
     * @param nodeId
     *            the node to chck
     * @param ipAddr
     *            the interface to check
     * @param service
     *            the name of the service not to include
     * @return the number of non deleted services, other than serviceId
     */
    private int countOtherServicesOnInterface(Connection dbConn, long nodeId, String ipAddr, String service) throws SQLException {

        Category log = ThreadCategory.getInstance(getClass());

        final String DB_COUNT_OTHER_SERVICES_ON_IFACE = "SELECT count(*) FROM ifservices, service " + "WHERE ifservices.serviceId = service.serviceId AND ifservices.status != 'D' " + "AND ifservices.nodeID=? AND ifservices.ipAddr=? AND service.servicename != ?";

        PreparedStatement stmt = dbConn.prepareStatement(DB_COUNT_OTHER_SERVICES_ON_IFACE);
        stmt.setLong(1, nodeId);
        stmt.setString(2, ipAddr);
        stmt.setString(3, service);
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }

        if (log.isDebugEnabled())
            log.debug("countServicesForInterface: count services for interface " + nodeId + "/" + ipAddr + ": found " + count);

        stmt.close();

        return count;
    }

    /**
     * Counts the number of non deleted services on a node on interfaces other
     * than a given interface
     * 
     * @param dbConn
     *            the database connection
     * @param nodeId
     *            the nodeid to check
     * @param ipAddr
     *            the address of the interface not to include
     * @return the number of non deleted services on other interfaces
     */
    private int countServicesOnOtherInterfaces(Connection dbConn, long nodeId, String ipAddr) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());

        final String DB_COUNT_SERVICES_ON_OTHER_INTERFACES = "SELECT count(*) FROM ifservices WHERE nodeID=? and ipAddr != ? and status != 'D'";

        PreparedStatement stmt = dbConn.prepareStatement(DB_COUNT_SERVICES_ON_OTHER_INTERFACES);
        stmt.setLong(1, nodeId);
        stmt.setString(2, ipAddr);
        ResultSet rs = stmt.executeQuery();

        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }

        if (log.isDebugEnabled())
            log.debug("countServicesOnOtherInterfaces: count services for node " + nodeId + ": found " + count);

        stmt.close();

        return count;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipaddr
     * @param txNo
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List createInterfaceOnNode(Connection dbConn, String nodeLabel, String ipaddr, long txNo) throws SQLException, FailedOperationException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Category log = ThreadCategory.getInstance(getClass());
            // There is no ipinterface associated with the specified nodeLabel
            // exist
            // in the database. Verify if a node with the nodeLabel already
            // exist in
            // the database. If not, create a node with the nodeLabel and add it
            // to the
            // database, and also add the ipaddress associated with this node to
            // the
            // database. If the node with the nodeLabel exists in the node
            // table, just
            // add the ip address to the database.
            stmt = dbConn.prepareStatement(SQL_QUERY_NODE_EXIST);
            stmt.setString(1, nodeLabel);

            rs = stmt.executeQuery();
            List eventsToSend = new LinkedList();
            while (rs.next()) {

                if (log.isDebugEnabled())
                    log.debug("addInterfaceHandler:  add interface: " + ipaddr + " to the database.");

                // Node already exists. Add the ipaddess to the ipinterface
                // table
                InetAddress ifaddr = InetAddress.getByName(ipaddr);
                int nodeId = rs.getInt(1);
                String dpName = rs.getString(2);

                DbIpInterfaceEntry ipInterface = DbIpInterfaceEntry.create(nodeId, ifaddr);
                ipInterface.setHostname(ifaddr.getHostName());
                ipInterface.setManagedState(DbIpInterfaceEntry.STATE_MANAGED);
                ipInterface.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);
                ipInterface.store(dbConn);

                // create a nodeEntry
                DbNodeEntry nodeEntry = DbNodeEntry.get(nodeId, dpName);
                Event newEvent = EventUtils.createNodeGainedInterfaceEvent(nodeEntry, ifaddr, txNo);
                eventsToSend.add(newEvent);

            }
            return eventsToSend;
        } catch (UnknownHostException e) {
            throw new FailedOperationException("unable to resolve host " + ipaddr + ": " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Create message selector to set to the subscription
     */
    private void createMessageSelectorAndSubscribe() {
        // Create the selector for the ueis this service is interested in
        //
        List ueiList = new ArrayList();

        // newSuspectInterface
        ueiList.add(EventConstants.NEW_SUSPECT_INTERFACE_EVENT_UEI);

        // forceRescan
        ueiList.add(EventConstants.FORCE_RESCAN_EVENT_UEI);

        // addNode
        ueiList.add(EventConstants.ADD_NODE_EVENT_UEI);
        m_notifySet.add(EventConstants.ADD_NODE_EVENT_UEI);

        // deleteNode
        ueiList.add(EventConstants.DELETE_NODE_EVENT_UEI);
        m_notifySet.add(EventConstants.DELETE_NODE_EVENT_UEI);

        // addInterface
        ueiList.add(EventConstants.ADD_INTERFACE_EVENT_UEI);
        m_notifySet.add(EventConstants.ADD_INTERFACE_EVENT_UEI);

        // deleteInterface
        ueiList.add(EventConstants.DELETE_INTERFACE_EVENT_UEI);
        m_notifySet.add(EventConstants.DELETE_INTERFACE_EVENT_UEI);

        // deleteService
        ueiList.add(EventConstants.DELETE_SERVICE_EVENT_UEI);

        // changeService
        ueiList.add(EventConstants.CHANGE_SERVICE_EVENT_UEI);
        m_notifySet.add(EventConstants.CHANGE_SERVICE_EVENT_UEI);

        // updateServer
        ueiList.add(EventConstants.UPDATE_SERVER_EVENT_UEI);
        m_notifySet.add(EventConstants.UPDATE_SERVER_EVENT_UEI);

        // updateService
        ueiList.add(EventConstants.UPDATE_SERVICE_EVENT_UEI);
        m_notifySet.add(EventConstants.UPDATE_SERVICE_EVENT_UEI);

        // nodeAdded
        ueiList.add(EventConstants.NODE_ADDED_EVENT_UEI);

        // nodeDeleted
        ueiList.add(EventConstants.NODE_DELETED_EVENT_UEI);

        // duplicateNodeDeleted
        ueiList.add(EventConstants.DUP_NODE_DELETED_EVENT_UEI);

        EventIpcManagerFactory.init();
        EventIpcManagerFactory.getInstance().getManager().addEventListener(this, ueiList);
    }

    /**
     * This method add a node with the specified node label to the database. If
     * also adds in interface with the given ipaddress to the node, if the
     * ipaddr is not null
     * 
     * @param conn
     *            The JDBC Database connection.
     * @param nodeLabel
     *            the node label to identify the node to create.
     * @param ipaddr
     *            the ipaddress to be added into the ipinterface table.
     * @param txNo
     *            the transaction no.
     * 
     * @throws SQLException
     *             if a database error occurs
     * @throws FailedOperationException
     *             if the ipaddr is not resolvable
     */
    private List createNodeWithInterface(Connection conn, String nodeLabel, String ipaddr, long txNo) throws SQLException, FailedOperationException {
        Category log = ThreadCategory.getInstance(getClass());

        if (nodeLabel == null)
            return Collections.EMPTY_LIST;

        if (log.isDebugEnabled())
            log.debug("addNode:  Add a node " + nodeLabel + " to the database");

        List eventsToSend = new LinkedList();
        DbNodeEntry node = DbNodeEntry.create();
        Date now = new Date();
        node.setCreationTime(now);
        node.setNodeType(DbNodeEntry.NODE_TYPE_ACTIVE);
        node.setLabel(nodeLabel);
        node.setLabelSource(DbNodeEntry.LABEL_SOURCE_USER);
        node.store(conn);

        Event newEvent = EventUtils.createNodeAddedEvent(node, txNo);
        eventsToSend.add(newEvent);

        if (ipaddr != null)
            try {
                if (log.isDebugEnabled())
                    log.debug("addNode:  Add an IP Address " + ipaddr + " to the database");

                // add the ipaddess to the database
                InetAddress ifaddress = InetAddress.getByName(ipaddr);
                DbIpInterfaceEntry ipInterface = DbIpInterfaceEntry.create(node.getNodeId(), ifaddress);
                ipInterface.setHostname(ifaddress.getHostName());
                ipInterface.setManagedState(DbIpInterfaceEntry.STATE_MANAGED);
                ipInterface.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);
                ipInterface.store(conn);

                Event gainIfEvent = EventUtils.createNodeGainedInterfaceEvent(node, ifaddress, txNo);
                eventsToSend.add(gainIfEvent);
            } catch (UnknownHostException e) {
                throw new FailedOperationException("unable to resolve host " + ipaddr + ": " + e.getMessage(), e);
            }
        return eventsToSend;
    }

    /**
     * FIXME: finish the docs here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipaddr
     * @param txNo
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doAddInterface(Connection dbConn, String nodeLabel, String ipaddr, long txNo) throws SQLException, FailedOperationException {
        List eventsToSend;
        if (interfaceExists(dbConn, nodeLabel, ipaddr)) {
            Category log = ThreadCategory.getInstance(getClass());
            if (log.isDebugEnabled()) {
                log.debug("addInterfaceHandler: node " + nodeLabel + " with IPAddress " + ipaddr + " already exist in the database.");
            }
            eventsToSend = Collections.EMPTY_LIST;
        }

        else if (nodeExists(dbConn, nodeLabel)) {
            eventsToSend = createInterfaceOnNode(dbConn, nodeLabel, ipaddr, txNo);
        } else {
            // The node does not exist in the database, add the node and
            // the ipinterface into the database.
            eventsToSend = createNodeWithInterface(dbConn, nodeLabel, ipaddr, txNo);
        }
        return eventsToSend;
    }

    /**
     * Perform the buld of the work for processing an addNode event
     * 
     * @param dbConn
     *            the database connection
     * @param nodeLabel
     *            the label for the node to add
     * @param ipaddr
     *            an interface on the node (may be null if no interface is
     *            supplied)
     * @param txNo
     *            a transaction number to associate with the modification
     * @return a list of events that need to be sent in response to these
     *         changes
     * @throws SQLException
     *             if a database error occurrs
     * @throws FailedOperationException
     *             if other errors occur
     */
    private List doAddNode(Connection dbConn, String nodeLabel, String ipaddr, long txNo) throws SQLException, FailedOperationException {
        List eventsToSend;
        if (!nodeExists(dbConn, nodeLabel)) {
            // the node does not exist in the database. Add the node with the
            // specified
            // node label and add the ipaddress to the database.
            eventsToSend = createNodeWithInterface(dbConn, nodeLabel, ipaddr, txNo);
        } else {
            eventsToSend = Collections.EMPTY_LIST;
            Category log = ThreadCategory.getInstance(getClass());
            if (log.isDebugEnabled()) {
                log.debug("doAddNode: node " + nodeLabel + " with IPAddress " + ipaddr + " already exist in the database.");
            }
        }
        return eventsToSend;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param ipaddr
     * @param serviceName
     * @param action
     * @param txNo
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doAddServiceMapping(Connection dbConn, String ipaddr, String serviceName, long txNo) throws SQLException, FailedOperationException {
        PreparedStatement stmt = null;
        Category log = ThreadCategory.getInstance(getClass());
        stmt = dbConn.prepareStatement(SQL_ADD_SERVICE_TO_MAPPING);

        stmt.setString(1, ipaddr);
        stmt.setString(2, serviceName);
        stmt.executeUpdate();
        stmt.close();

        if (log.isDebugEnabled()) {
            log.debug("updateServiceHandler: add service " + serviceName + " to interface: " + ipaddr);
        }

        return doChangeService(dbConn, ipaddr, serviceName, "ADD", txNo);
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param sourceUei
     * @param ipaddr
     * @param serviceName
     * @param serviceId
     * @param txNo
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doAddServiceToInterface(Connection dbConn, String ipaddr, String serviceName, int serviceId, long txNo) throws SQLException, FailedOperationException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Category log = ThreadCategory.getInstance(getClass());
            stmt = dbConn.prepareStatement(SQL_QUERY_IPADDRESS_EXIST);

            stmt.setString(1, ipaddr);
            rs = stmt.executeQuery();

            List eventsToSend = new LinkedList();
            while (rs.next()) {
                if (log.isDebugEnabled()) {
                    log.debug("changeServiceHandler: add service " + serviceName + " to interface: " + ipaddr);
                }

                int nodeId = rs.getInt(1);
                // insert service
                DbIfServiceEntry service = DbIfServiceEntry.create(nodeId, InetAddress.getByName(ipaddr), serviceId);
                service.setSource(DbIfServiceEntry.SOURCE_PLUGIN);
                service.setStatus(DbIfServiceEntry.STATUS_ACTIVE);
                service.setNotify(DbIfServiceEntry.NOTIFY_ON);
                service.store(dbConn);

                // Create a nodeGainedService event to eventd.
                DbNodeEntry nodeEntry = DbNodeEntry.get(nodeId);
                Event newEvent = EventUtils.createNodeGainedServiceEvent(nodeEntry, InetAddress.getByName(ipaddr), serviceName, txNo);
                eventsToSend.add(newEvent);
            }
            return eventsToSend;
        } catch (UnknownHostException e) {
            throw new FailedOperationException("Unable to resolve host: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException ex) {
            }
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param sourceUei
     * @param ipaddr
     * @param serviceName
     * @param action
     * @param txNo
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doChangeService(Connection dbConn, String ipaddr, String serviceName, String action, long txNo) throws SQLException, FailedOperationException {
        List eventsToSend = null;
        int serviceId = verifyServiceExists(dbConn, serviceName);

        if (action.equalsIgnoreCase("DELETE")) {
            eventsToSend = new LinkedList();
            // find the node Id associated with the serviceName and interface
            int[] nodeIds = findNodeIdForServiceAndInterface(dbConn, ipaddr, serviceName);
            for (int i = 0; i < nodeIds.length; i++) {
                int nodeId = nodeIds[i];
                // delete the service from the database
                eventsToSend.addAll(doDeleteService(dbConn, "OpenNMS.Capsd", nodeId, ipaddr, serviceName, txNo));
            }
        } else if (action.equalsIgnoreCase("ADD")) {
            eventsToSend = doAddServiceToInterface(dbConn, ipaddr, serviceName, serviceId, txNo);
        } else {
            eventsToSend = Collections.EMPTY_LIST;
        }
        return eventsToSend;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipaddr
     * @param hostName
     * @param txNo
     * @return
     * @throws SQLException
     */
    private List doCreateInterfaceMappings(Connection dbConn, String nodeLabel, String ipaddr, String hostName, long txNo) throws SQLException {
        PreparedStatement stmt = null;
        try {
            Category log = ThreadCategory.getInstance(getClass());
            stmt = dbConn.prepareStatement(SQL_ADD_INTERFACE_TO_SERVER);

            stmt.setString(1, ipaddr);
            stmt.setString(2, hostName);
            stmt.executeUpdate();

            if (log.isDebugEnabled()) {
                log.debug("updateServerHandler: added interface " + ipaddr + " into NMS server: " + hostName);
            }

            // Create a addInterface event and process it.
            // FIXME: do I need to make a direct call here?
            Event newEvent = EventUtils.createAddInterfaceEvent("OpenNMS.Capsd", nodeLabel, ipaddr, hostName, txNo);
            return Collections.singletonList(newEvent);
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }

    }

    /**
     * Mark as deleted the specified interface and its associated services, if
     * delete propagation is enable and the interface is the only one on the
     * node, delete the node as well.
     * 
     * @param dbConn
     *            the database connection
     * @param source
     *            the source for any events that must be sent
     * @param nodeid
     *            the id of the node the interface resides on
     * @param ipAddr
     *            the ip address of the interface to be deleted
     * @param txNo
     *            a transaction number to associate with the deletion
     * @return a list of events that need to be sent w.r.t. this deletion
     * @throws SQLException
     *             if any database errors occur
     */
    private List doDeleteInterface(Connection dbConn, String source, long nodeid, String ipAddr, long txNo) throws SQLException {
        List eventsToSend = new LinkedList();

        // if this is the last interface for the node then delete the node
        // instead
        if (isPropagationEnabled() && countOtherInterfacesOnNode(dbConn, nodeid, ipAddr) == 0) {
            // there are no other ifs for this node so delete the node
            doDeleteNode(dbConn, source, nodeid, txNo);
        } else {
            eventsToSend.addAll(markAllServicesForInterfaceDeleted(dbConn, source, nodeid, ipAddr, txNo));
            eventsToSend.addAll(markInterfaceDeleted(dbConn, source, nodeid, ipAddr, txNo));
        }
        return eventsToSend;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipaddr
     * @param hostName
     * @param txNo
     * @param log
     * @return
     * @throws SQLException
     */
    private List doDeleteInterfaceMappings(Connection dbConn, String nodeLabel, String ipaddr, String hostName, long txNo) throws SQLException {
        PreparedStatement stmt = null;
        try {
            List eventsToSend = new LinkedList();

            Category log = ThreadCategory.getInstance(getClass());

            // Delete all services on the specified interface in
            // interface/service
            // mapping
            //
            if (log.isDebugEnabled()) {
                log.debug("updateServer: delete all services on the interface: " + ipaddr + " in the interface/service mapping.");
            }
            stmt = dbConn.prepareStatement(SQL_DELETE_ALL_SERVICES_INTERFACE_MAPPING);
            stmt.setString(1, ipaddr);
            stmt.executeUpdate();

            // Delete the interface on interface/server mapping
            if (log.isDebugEnabled()) {
                log.debug("updateServer: delete interface: " + ipaddr + " on NMS server: " + hostName);
            }
            stmt = dbConn.prepareStatement(SQL_DELETE_INTERFACE_ON_SERVER);
            stmt.setString(1, ipaddr);
            stmt.setString(2, hostName);
            stmt.executeUpdate();

            // Now mark the interface as deleted (and its services as well)
            long[] nodeIds = findNodeIdsForInterfaceAndLabel(dbConn, nodeLabel, ipaddr);
            for (int i = 0; i < nodeIds.length; i++) {
                long nodeId = nodeIds[i];
                eventsToSend.addAll(doDeleteInterface(dbConn, "OpenNMS.Capsd", nodeId, ipaddr, txNo));
            }
            return eventsToSend;
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }
    }

    /**
     * Mark as deleted the specified node, its associated interfaces and
     * services.
     * 
     * @param dbConn
     *            the connection to the database
     * @param source
     *            the source for any events to send
     * @param nodeid
     *            the nodeid to be deleted
     * @param txNo
     *            a transaction id to associate with this deletion
     * 
     * @return the list of events that need to be sent in response to the node
     *         being deleted
     * @throws SQLException
     *             if any exception occurs communicating with the database
     */
    private List doDeleteNode(Connection dbConn, String source, long nodeid, long txNo) throws SQLException {
        List eventsToSend = new LinkedList();
        eventsToSend.addAll(markInterfacesAndServicesDeleted(dbConn, source, nodeid, txNo));
        eventsToSend.addAll(markNodeDeleted(dbConn, source, nodeid, txNo));
        return eventsToSend;
    }

    /**
     * Mark as deleted the specified service, if this is the only service on an
     * interface or node and deletePropagation is enabled, the interface or node
     * is marked as deleted as well.
     * 
     * @param dbConn
     *            the connection to the database
     * @param source
     *            the source for any events to send
     * @param nodeid
     *            the nodeid that the service resides on
     * @param ipAddr
     *            the interface that the service resides on
     * @param service
     *            the name of the service
     * @param txNo
     *            a transaction id to associate with this deletion
     * 
     * @return the list of events that need to be sent in response to the
     *         service being deleted
     * @throws SQLException
     *             if any exception occurs communicating with the database
     */
    private List doDeleteService(Connection dbConn, String source, long nodeid, String ipAddr, String service, long txNo) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());
        List eventsToSend = new LinkedList();

        if (isPropagationEnabled()) {
            // if this is the last service for the interface or the last service
            // for the node then send delete events for the interface or node
            // instead
            int otherSvcsOnIfCnt = countOtherServicesOnInterface(dbConn, nodeid, ipAddr, service);
            if (otherSvcsOnIfCnt == 0 && countServicesOnOtherInterfaces(dbConn, nodeid, ipAddr) == 0) {
                // no services on this interface or any other interface on this
                // node so delete
                // node
                log.debug("Propagating service delete to node " + nodeid);
                eventsToSend.addAll(doDeleteNode(dbConn, source, nodeid, txNo));
            } else if (otherSvcsOnIfCnt == 0) {
                // no services on this interface so delete interface
                log.debug("Propagting service delete to interface " + nodeid + "/" + ipAddr);
                eventsToSend.addAll(doDeleteInterface(dbConn, source, nodeid, ipAddr, txNo));
            } else {
                log.debug("No need to Propagate service delete " + nodeid + "/" + ipAddr + "/" + service);
                // otherwise just mark the service as deleted and send a
                // serviceDeleted event
                eventsToSend.addAll(markServiceDeleted(dbConn, source, nodeid, ipAddr, service, txNo));
            }
        } else {
            log.debug("Propagation disabled:  deleting only service " + nodeid + "/" + ipAddr + "/" + service);
            // otherwise just mark the service as deleted and send a
            // serviceDeleted event
            eventsToSend.addAll(markServiceDeleted(dbConn, source, nodeid, ipAddr, service, txNo));
        }
        return eventsToSend;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param ipaddr
     * @param serviceName
     * @param action
     * @param txNo
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doDeleteServiceMapping(Connection dbConn, String ipaddr, String serviceName, long txNo) throws SQLException, FailedOperationException {
        PreparedStatement stmt = null;
        try {
            Category log = ThreadCategory.getInstance(getClass());
            if (log.isDebugEnabled()) {
                log.debug("handleUpdateService: delete service: " + serviceName + " on IPAddress: " + ipaddr);
            }
            stmt = dbConn.prepareStatement(SQL_DELETE_SERVICE_INTERFACE_MAPPING);

            stmt.setString(1, ipaddr);
            stmt.setString(2, serviceName);

            stmt.executeUpdate();

            return doChangeService(dbConn, ipaddr, serviceName, "DELETE", txNo);
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipaddr
     * @param action
     * @param hostName
     * @param txNo
     * @throws SQLException
     */
    private List doUpdateServer(Connection dbConn, String nodeLabel, String ipaddr, String action, String hostName, long txNo) throws SQLException {

        Category log = ThreadCategory.getInstance(getClass());

        boolean exists = existsInServerMap(dbConn, hostName, ipaddr);

        if (exists && "DELETE".equalsIgnoreCase(action)) {
            return doDeleteInterfaceMappings(dbConn, nodeLabel, ipaddr, hostName, txNo);
        } else if (!exists && "ADD".equalsIgnoreCase(action)) {
            return doCreateInterfaceMappings(dbConn, nodeLabel, ipaddr, hostName, txNo);
        } else {
            log.error("updateServerHandler: could not process interface: " + ipaddr + " on NMS server: " + hostName);
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param ipaddr
     * @param serviceName
     * @param action
     * @param txNo
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private List doUpdateService(Connection dbConn, String ipaddr, String serviceName, String action, long txNo) throws SQLException, FailedOperationException {
        List eventsToSend;
        verifyServiceExists(dbConn, serviceName);

        boolean mapExists = serviceMappingExists(dbConn, ipaddr, serviceName);

        if (mapExists && "DELETE".equalsIgnoreCase(action)) {
            // the mapping exists and should be deleted
            eventsToSend = doDeleteServiceMapping(dbConn, ipaddr, serviceName, txNo);
        } else if (!mapExists && "ADD".equalsIgnoreCase(action)) {
            // we need to add the mapping, it doesn't exist
            eventsToSend = doAddServiceMapping(dbConn, ipaddr, serviceName, txNo);
        } else {
            eventsToSend = Collections.EMPTY_LIST;
        }
        return eventsToSend;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param hostName
     * @param ipaddr
     * @return
     * @throws SQLException
     */
    private boolean existsInServerMap(Connection dbConn, String hostName, String ipaddr) throws SQLException {
        PreparedStatement stmt = null;
        try {
            /**
             * SQL statement used to query if an interface/server mapping
             * already exists in the database.
             */
            final String SQL_QUERY_INTERFACE_ON_SERVER = "SELECT count(*)  FROM serverMap WHERE ipaddr = ? AND servername = ?";

            // Verify if the interface already exists on the NMS server
            stmt = dbConn.prepareStatement(SQL_QUERY_INTERFACE_ON_SERVER);

            stmt.setString(1, ipaddr);
            stmt.setString(2, hostName);

            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param ipaddr
     * @param serviceName
     * @return
     * @throws SQLException
     */
    private int[] findNodeIdForServiceAndInterface(Connection dbConn, String ipaddr, String serviceName) throws SQLException {
        int[] nodeIds;
        Category log = ThreadCategory.getInstance(getClass());
        PreparedStatement stmt = null;
        // Verify if the specified service already exist.
        stmt = dbConn.prepareStatement(SQL_QUERY_SERVICE_EXIST);

        stmt.setString(1, ipaddr);
        stmt.setString(2, serviceName);

        ResultSet rs = stmt.executeQuery();
        List nodeIdList = new LinkedList();
        while (rs.next()) {
            if (log.isDebugEnabled()) {
                log.debug("changeService: service " + serviceName + " on IPAddress " + ipaddr + " already exists in the database.");
            }
            int nodeId = rs.getInt(1);
            nodeIdList.add(new Integer(nodeId));
        }
        rs.close();
        stmt.close();
        nodeIds = new int[nodeIdList.size()];
        int i = 0;
        for (Iterator it = nodeIdList.iterator(); it.hasNext(); i++) {
            Integer n = (Integer) it.next();
            nodeIds[i] = n.intValue();
        }
        return nodeIds;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param nodeLabel
     * @param ipAddr
     * @return
     * @throws SQLException
     */
    private long[] findNodeIdsForInterfaceAndLabel(Connection dbConn, String nodeLabel, String ipAddr) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("SELECT node.nodeid FROM node, ipinterface WHERE node.nodeid = ipinterface.nodeid AND node.nodelabel = ? AND ipinterface.ipaddr = ? AND isManaged !='D' AND nodeType !='D'");
            stmt.setString(1, nodeLabel);
            stmt.setString(2, ipAddr);

            ResultSet rs = stmt.executeQuery();
            List nodeIdList = new LinkedList();
            while (rs.next()) {
                nodeIdList.add(new Long(rs.getLong(1)));
            }

            long[] nodeIds = new long[nodeIdList.size()];
            int i = 0;
            for (Iterator it = nodeIdList.iterator(); it.hasNext(); i++) {
                Long nodeId = (Long) it.next();
                nodeIds[i] = nodeId.longValue();
            }
            return nodeIds;
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }
    }

    /**
     * Get the local server name
     */
    public String getLocalServer() {
        return m_localServer;
    }

    /**
     * Return an id for this event listener
     */
    public String getName() {
        return "Capsd:BroadcastEventProcessor";
    }

    /**
     * Process the event, add the specified interface into database. If the
     * associated node does not exist in the database yet, add a node into the
     * database.
     * 
     * @param event
     *            The event to process.
     * @throws InsufficientInformationException
     *             if the event is missing essential information
     * @throws FailedOperationException
     *             if the operation fails (because of database error for
     *             example)
     */
    private void handleAddInterface(Event event) throws InsufficientInformationException, FailedOperationException {
        Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkInterface(event);
        EventUtils.requireParm(event, EventConstants.PARM_NODE_LABEL);
        if (isXmlRpcEnabled())
            EventUtils.requireParm(event, EventConstants.PARM_TRANSACTION_NO);
        if (log.isDebugEnabled())
            log.debug("addInterfaceHandler:  processing addInterface event for " + event.getInterface());

        String nodeLabel = EventUtils.getParm(event, EventConstants.PARM_NODE_LABEL);
        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);

        // First make sure the specified node label and ipaddress do not exist
        // in the database
        // before trying to add them in.
        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            eventsToSend = doAddInterface(dbConn, nodeLabel, event.getInterface(), txNo);
        } catch (SQLException sqlE) {
            log.error("addInterfaceHandler: SQLException during add node and ipaddress to the database.", sqlE);
            throw new FailedOperationException("Database error: " + sqlE.getMessage(), sqlE);
        } finally {
            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), event.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }
    }

    /**
     * Process an addNode event.
     * 
     * @param event
     *            The event to process.
     * @throws InsufficientInformationException
     *             if the event is missing information
     * @throws FailedOperationException
     *             if an error occurs during processing
     */
    private void handleAddNode(Event event) throws InsufficientInformationException, FailedOperationException {
        // Category log = ThreadCategory.getInstance(getClass());

        EventUtils.requireParm(event, EventConstants.PARM_NODE_LABEL);
        if (isXmlRpcEnabled()) {
            EventUtils.requireParm(event, EventConstants.PARM_TRANSACTION_NO);
        }

        String ipaddr = event.getInterface();
        String sourceUei = event.getUei();
        String nodeLabel = EventUtils.getParm(event, EventConstants.PARM_NODE_LABEL);
        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);
        {

            Category log = ThreadCategory.getInstance(getClass());

            if (log.isDebugEnabled())
                log.debug("addNodeHandler:  processing addNode event for " + ipaddr);
        }
        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            eventsToSend = doAddNode(dbConn, nodeLabel, ipaddr, txNo);
        } catch (SQLException sqlE) {
            Category log = ThreadCategory.getInstance(getClass());
            log.error("addNodeHandler: SQLException during add node and ipaddress to tables", sqlE);
            throw new FailedOperationException("database error: " + sqlE.getMessage(), sqlE);
        } finally {
            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), event.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }

    }

    /**
     * Process the event, add or remove a specified service from an interface.
     * An 'action' parameter wraped in the event will tell which action to take
     * to the service.
     * 
     * @param event
     *            The event to process.
     * @throws FailedOperationException
     *             FIXME: finish the doc here
     */
    private void handleChangeService(Event event) throws InsufficientInformationException, FailedOperationException {
        Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkInterface(event);
        EventUtils.checkService(event);
        EventUtils.requireParm(event, EventConstants.PARM_ACTION);
        if (isXmlRpcEnabled()) {
            EventUtils.requireParm(event, EventConstants.PARM_TRANSACTION_NO);
        }

        String action = EventUtils.getParm(event, EventConstants.PARM_ACTION);
        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);

        if (log.isDebugEnabled())
            log.debug("changeServiceHandler:  processing changeService event on: " + event.getInterface());

        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            eventsToSend = doChangeService(dbConn, event.getInterface(), event.getService(), action, txNo);
        } catch (SQLException sqlE) {
            log.error("SQLException during changeService on database.", sqlE);
            throw new FailedOperationException("exeption processing changeService: " + sqlE.getMessage(), sqlE);
        } finally {
            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), event.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }

    }

    /**
     * Handle a deleteInterface Event. Here we process the event by marking all
     * the appropriate data rows as deleted.
     * 
     * FIXME: finish the doc here
     * 
     * @param e
     *            The event indicating what interface to delete
     * @throws InsufficientInformationException
     *             if the required information is not part of the event
     */
    private void handleDeleteInterface(Event e) throws InsufficientInformationException, FailedOperationException {
        // validate event
        EventUtils.checkEventId(e);
        EventUtils.checkInterface(e);
        EventUtils.checkNodeId(e);
        if (isXmlRpcEnabled())
            EventUtils.requireParm(e, EventConstants.PARM_TRANSACTION_NO);

        Category log = ThreadCategory.getInstance(getClass());

        // log the event
        if (log.isDebugEnabled())
            log.debug("handleDeleteInterface: Event\n" + "uei\t\t" + e.getUei() + "\neventid\t\t" + e.getDbid() + "\nnodeId\t\t" + e.getNodeid() + "\nipaddr\t\t" + e.getInterface() + "\neventtime\t" + (e.getTime() != null ? e.getTime() : "<null>"));

        long txNo = EventUtils.getLongParm(e, EventConstants.PARM_TRANSACTION_NO, -1L);

        // update the database
        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            String source = (e.getSource() == null ? "OpenNMS.Capsd" : e.getSource());

            eventsToSend = doDeleteInterface(dbConn, source, e.getNodeid(), e.getInterface(), txNo);
        } catch (SQLException ex) {
            log.error("handleDeleteService:  Database error deleting service " + e.getService() + " on ipAddr " + e.getInterface() + " for node " + e.getNodeid(), ex);
            throw new FailedOperationException("database error: " + ex.getMessage(), ex);
        } finally {
            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), e.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }
    }

    /**
     * Handle a deleteNode Event. Here we process the event by marking all the
     * appropriate data rows as deleted.
     * 
     * FIXME: finish the doc here
     * 
     * @param e
     *            The event indicating what node to delete
     * @throws InsufficientInformationException
     *             if the required information is not part of the event
     */
    private void handleDeleteNode(Event e) throws InsufficientInformationException, FailedOperationException {
        // validate event
        EventUtils.checkEventId(e);
        EventUtils.checkNodeId(e);
        if (isXmlRpcEnabled())
            EventUtils.requireParm(e, EventConstants.PARM_TRANSACTION_NO);

        Category log = ThreadCategory.getInstance(getClass());

        // log the event
        long nodeid = e.getNodeid();
        if (log.isDebugEnabled())
            log.debug("handleDeleteNode: Event\n" + "uei\t\t" + e.getUei() + "\neventid\t\t" + e.getDbid() + "\nnodeId\t\t" + nodeid + "\neventtime\t" + (e.getTime() != null ? e.getTime() : "<null>"));

        long txNo = EventUtils.getLongParm(e, EventConstants.PARM_TRANSACTION_NO, -1L);

        // update the database
        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            String source = (e.getSource() == null ? "OpenNMS.Capsd" : e.getSource());

            eventsToSend = doDeleteNode(dbConn, source, nodeid, txNo);
        } catch (SQLException ex) {
            log.error("handleDeleteService:  Database error deleting service " + e.getService() + " on ipAddr " + e.getInterface() + " for node " + nodeid, ex);
            throw new FailedOperationException("database error: " + ex.getMessage(), ex);

        } finally {

            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), e.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }
    }

    /**
     * Handle a deleteService Event. Here we process the event by marking all
     * the appropriate data rows as deleted.
     * 
     * FIXME: finish the doc here
     * 
     * @param e
     *            The event indicating what service to delete
     * @throws InsufficientInformationException
     *             if the required information is not part of the event
     */
    private void handleDeleteService(Event e) throws InsufficientInformationException, FailedOperationException {

        // validate event
        EventUtils.checkEventId(e);
        EventUtils.checkNodeId(e);
        EventUtils.checkInterface(e);
        EventUtils.checkService(e);

        Category log = ThreadCategory.getInstance(getClass());

        // log the event
        if (log.isDebugEnabled())
            log.debug("handleDeleteService: Event\nuei\t\t" + e.getUei() + "\neventid\t\t" + e.getDbid() + "\nnodeid\t\t" + e.getNodeid() + "\nipaddr\t\t" + e.getInterface() + "\nservice\t\t" + e.getService() + "\neventtime\t" + (e.getTime() != null ? e.getTime() : "<null>"));

        long txNo = EventUtils.getLongParm(e, EventConstants.PARM_TRANSACTION_NO, -1L);

        // update the database
        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);
            String source = (e.getSource() == null ? "OpenNMS.Capsd" : e.getSource());
            eventsToSend = doDeleteService(dbConn, source, e.getNodeid(), e.getInterface(), e.getService(), txNo);
        } catch (SQLException ex) {
            log.error("handleDeleteService:  Database error deleting service " + e.getService() + " on ipAddr " + e.getInterface() + " for node " + e.getNodeid(), ex);
            throw new FailedOperationException("database error: " + ex.getMessage(), ex);
        } finally {

            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), e.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleDupNodeDeleted(Event event) throws InsufficientInformationException {
        Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkNodeId(event);

        // Remove the deleted node from the scheduler
        m_scheduler.unscheduleNode((int) event.getNodeid());
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleForceRescan(Event event) throws InsufficientInformationException {
        Category log = ThreadCategory.getInstance(getClass());
        // If the event has a node identifier use it otherwise
        // will need to use the interface to lookup the node id
        // from the database
        int nodeid = -1;

        if (event.hasNodeid())
            nodeid = (int) event.getNodeid();
        else {
            // Extract interface from the event and use it to
            // lookup the node identifier associated with the
            // interface from the database.
            //

            // ensure the ipaddr is set
            EventUtils.checkInterface(event);

            // Get database connection and retrieve nodeid
            Connection dbc = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                dbc = DatabaseConnectionFactory.getInstance().getConnection();

                // Retrieve node id
                stmt = dbc.prepareStatement(SQL_RETRIEVE_NODEID);
                stmt.setString(1, event.getInterface());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    nodeid = rs.getInt(1);
                }
            } catch (SQLException sqlE) {
                log.error("onMessage: Database error during nodeid retrieval for interface " + event.getInterface(), sqlE);
            } finally {
                // Close the prepared statement
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlE) {
                        // Ignore
                    }
                }

                // Close the connection
                if (dbc != null) {
                    try {
                        dbc.close();
                    } catch (SQLException sqlE) {
                        // Ignore
                    }
                }
            }

        }

        if (nodeid == -1) {
            log.error("onMessage: Nodeid retrieval for interface " + event.getInterface() + " failed.  Unable to perform rescan.");
        } else {
            // Rescan the node.
            m_scheduler.forceRescan(nodeid);
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleNewSuspect(Event event) throws InsufficientInformationException {
        Category log = ThreadCategory.getInstance(getClass());

        // ensure the event has an interface
        EventUtils.checkInterface(event);
        // new poll event
        try {
            if (log.isDebugEnabled()) {
                log.debug("onMessage: Adding interface to suspectInterface Q: " + event.getInterface());
            }
            m_suspectQ.add(new SuspectEventProcessor(event.getInterface()));
        } catch (Exception ex) {
            log.error("onMessage: Failed to add interface to suspect queue", ex);
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleNodeAdded(Event event) throws InsufficientInformationException {
        Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkNodeId(event);

        // Schedule the new node.
        try {
            m_scheduler.scheduleNode((int) event.getNodeid());
        } catch (SQLException sqlE) {
            log.error("onMessage: SQL exception while attempting to schedule node " + event.getNodeid(), sqlE);
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleNodeDeleted(Event event) throws InsufficientInformationException {
        Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkNodeId(event);

        // Remove the deleted node from the scheduler
        m_scheduler.unscheduleNode((int) event.getNodeid());
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void handleUpdateServer(Event event) throws InsufficientInformationException, FailedOperationException {
        Category log = ThreadCategory.getInstance(getClass());

        // If there is no interface or NMS server found then it cannot be
        // processed
        EventUtils.checkInterface(event);
        EventUtils.checkHost(event);
        EventUtils.requireParm(event, EventConstants.PARM_ACTION);
        EventUtils.requireParm(event, EventConstants.PARM_NODE_LABEL);
        if (isXmlRpcEnabled()) {
            EventUtils.requireParm(event, EventConstants.PARM_TRANSACTION_NO);
        }

        String action = EventUtils.getParm(event, EventConstants.PARM_ACTION);
        String nodeLabel = EventUtils.getParm(event, EventConstants.PARM_NODE_LABEL);
        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);

        if (log.isDebugEnabled())
            log.debug("updateServerHandler:  processing updateServer event for: " + event.getInterface() + " on OpenNMS server: " + getLocalServer());

        Connection dbConn = null;
        List eventsToSend = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            eventsToSend = doUpdateServer(dbConn, nodeLabel, event.getInterface(), action, getLocalServer(), txNo);

        } catch (SQLException sqlE) {
            log.error("SQLException during updateServer on database.", sqlE);
            throw new FailedOperationException("SQLException during updateServer on database.", sqlE);
        } finally {
            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), event.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }

    }

    /**
     * Process the event, add or remove a specified interface/service pair into
     * the database. this event will cause an changeService event with the
     * specified action. An 'action' parameter wraped in the event will tell
     * which action to take to the service on the specified interface. The
     * ipaddress of the interface, the service name must be included in the
     * event.
     * 
     * @param event
     *            The event to process.
     * @throws InsufficientInformationException
     *             if there is missing information in the event
     * @throws FailedOperationException
     *             if the operation fails for some reason
     */
    private void handleUpdateService(Event event) throws InsufficientInformationException, FailedOperationException {
        // Category log = ThreadCategory.getInstance(getClass());

        EventUtils.checkInterface(event);
        EventUtils.checkService(event);
        EventUtils.requireParm(event, EventConstants.PARM_ACTION);
        if (isXmlRpcEnabled()) {
            EventUtils.requireParm(event, EventConstants.PARM_TRANSACTION_NO);
        }

        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);
        String action = EventUtils.getParm(event, EventConstants.PARM_ACTION);

        Category log = ThreadCategory.getInstance(getClass());
        if (log.isDebugEnabled())
            log.debug("handleUpdateService:  processing updateService event for : " + event.getService() + " on : " + event.getInterface());

        List eventsToSend = null;
        Connection dbConn = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            dbConn.setAutoCommit(false);

            eventsToSend = doUpdateService(dbConn, event.getInterface(), event.getService(), action, txNo);

        } catch (SQLException sqlE) {
            log.error("SQLException during handleUpdateService on database.", sqlE);
            throw new FailedOperationException(sqlE.getMessage());
        } finally {

            if (dbConn != null)
                try {
                    if (eventsToSend != null) {
                        dbConn.commit();
                        for (Iterator it = eventsToSend.iterator(); it.hasNext();) {
                            EventUtils.sendEvent((Event) it.next(), event.getUei(), txNo, isXmlRpcEnabled());
                        }
                    } else {
                        dbConn.rollback();
                    }
                } catch (SQLException ex) {
                } finally {
                    if (dbConn != null)
                        try {
                            dbConn.close();
                        } catch (SQLException ex) {
                        }
                }
        }

    }

    /**
     * Returns true if and only an interface with the given ipaddr on a node
     * with the give label exists
     * 
     * @param dbConn
     *            a database connection
     * @param nodeLabel
     *            the label of the node the interface must reside on
     * @param ipaddr
     *            the ip address the interface should have
     * @return true iff the interface exists
     * @throws SQLException
     *             if a database error occurs
     */
    private boolean interfaceExists(Connection dbConn, String nodeLabel, String ipaddr) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbConn.prepareStatement(SQL_QUERY_IPINTERFACE_EXIST);

            stmt.setString(1, nodeLabel);
            stmt.setString(2, ipaddr);

            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }

    }

    /**
     * Mark all the services associated with a given interface as deleted and
     * create service deleted events for each one that gets deleted
     * 
     * @param dbConn
     *            the database connection
     * @param nodeId
     *            the node that interface resides on
     * @param ipAddr
     *            the ipAddress of the interface
     * @param txNo
     *            a transaction number that can be associated with this deletion
     * @return a List of serviceDeleted events, one for each service marked
     * @throws SQLException
     *             if a database error occurs
     */
    private List markAllServicesForInterfaceDeleted(Connection dbConn, String source, long nodeId, String ipAddr, long txNo) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            List eventsToSend = new LinkedList();

            final String DB_FIND_SERVICES_FOR_INTERFACE = "SELECT DISTINCT service.serviceName FROM ifservices as ifservices, service as service WHERE ifservices.nodeID = ? and ifservices.ipAddr = ? and ifservices.status != 'D' and ifservices.serviceID = service.serviceID";
            stmt = dbConn.prepareStatement(DB_FIND_SERVICES_FOR_INTERFACE);
            stmt.setLong(1, nodeId);
            stmt.setString(2, ipAddr);
            rs = stmt.executeQuery();

            Set services = new HashSet();
            while (rs.next()) {
                String serviceName = rs.getString(1);
                log.debug("found service " + serviceName + " for ipAddr " + ipAddr + " node " + nodeId);
                services.add(serviceName);
            }

            rs.close();
            rs = null;
            stmt.close();
            stmt = null;

            final String DB_MARK_SERVICES_FOR_INTERFACE = "UPDATE ifservices SET status = 'D' where ifservices.nodeID = ? and ifservices.ipAddr = ?";
            stmt = dbConn.prepareStatement(DB_MARK_SERVICES_FOR_INTERFACE);
            stmt.setLong(1, nodeId);
            stmt.setString(2, ipAddr);

            int count = stmt.executeUpdate();

            for (Iterator it = services.iterator(); it.hasNext();) {
                String serviceName = (String) it.next();
                log.debug("creating event for service " + serviceName + " for ipAddr " + ipAddr + " node " + nodeId);
                eventsToSend.add(EventUtils.createServiceDeletedEvent(source, nodeId, ipAddr, serviceName, txNo));
            }

            if (log.isDebugEnabled())
                log.debug("markServicesDeleted: marked service deleted: " + nodeId + "/" + ipAddr);

            return eventsToSend;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }

        }
    }

    /**
     * Mark the given interface deleted
     * 
     * @param dbConn
     *            the database connection
     * @param source
     *            the source for any events set
     * @param nodeId
     *            the id the interface resides on
     * @param ipAddr
     *            the ipAddress of the interface
     * @param txNo
     *            a transaction no to associate with this deletion
     * @return a List containing an interfaceDeleted event for the interface if
     *         it was actually marked
     * @throws SQLException
     *             if a database error occurs
     */
    private List markInterfaceDeleted(Connection dbConn, String source, long nodeId, String ipAddr, long txNo) throws SQLException {
        final String DB_FIND_INTERFACE = "UPDATE ipinterface SET isManaged = 'D' WHERE nodeid = ? and ipAddr = ? and isManaged != 'D'";
        Category log = ThreadCategory.getInstance(getClass());
        PreparedStatement stmt = null;
        try {
            List eventsToSend = new LinkedList();

            stmt = dbConn.prepareStatement(DB_FIND_INTERFACE);
            stmt.setLong(1, nodeId);
            stmt.setString(2, ipAddr);
            int count = stmt.executeUpdate();
            stmt.close();

            if (log.isDebugEnabled())
                log.debug("markServicesDeleted: marked service deleted: " + nodeId + "/" + ipAddr);

            if (count > 0)
                return Collections.singletonList(EventUtils.createInterfaceDeletedEvent(source, nodeId, ipAddr, txNo));
            else
                return Collections.EMPTY_LIST;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Marks all the interfaces and services for a given node deleted and
     * constructs events for each. The order of events is significant
     * representing the hierarchy, service events preceed the event for the
     * interface the service is on
     * 
     * @param dbConn
     *            the database connection
     * @param source
     *            the source for use in the constructed events
     * @param nodeId
     *            the node whose interfaces and services are to be deleted
     * @param txNo
     *            a transaction number to associate with this deletion
     * @return a List of events indicating which nodes and services have been
     *         deleted
     * 
     * @throws SQLException
     */
    private List markInterfacesAndServicesDeleted(Connection dbConn, String source, long nodeId, long txNo) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());
        List eventsToSend = new LinkedList();

        final String DB_FIND_IFS_FOR_NODE = "SELECT ipinterface.ipaddr FROM ipinterface WHERE ipinterface.nodeid = ? and ipinterface.ismanaged != 'D'";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbConn.prepareStatement(DB_FIND_IFS_FOR_NODE);
            stmt.setLong(1, nodeId);
            rs = stmt.executeQuery();

            Set ipAddrs = new HashSet();
            while (rs.next()) {
                String ipAddr = rs.getString(1);
                log.debug("found interface " + ipAddr + " for node " + nodeId);
                ipAddrs.add(ipAddr);
            }

            for (Iterator it = ipAddrs.iterator(); it.hasNext();) {
                String ipAddr = (String) it.next();
                log.debug("deleting interface " + ipAddr + " for node " + nodeId);
                eventsToSend.addAll(markAllServicesForInterfaceDeleted(dbConn, source, nodeId, ipAddr, txNo));
                eventsToSend.addAll(markInterfaceDeleted(dbConn, source, nodeId, ipAddr, txNo));
            }

            return eventsToSend;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Marks a node deleted and creates an event for it if necessary.
     * 
     * @param dbConn
     *            the database connection
     * @param source
     *            the source to use for constructed events
     * @param nodeId
     *            the node to delete
     * @param txNo
     *            a transaction number to associate with this deletion
     * @return a List containing the node deleted event if necessary
     * @throws SQLException
     *             if a database error occurs
     */
    private List markNodeDeleted(Connection dbConn, String source, long nodeId, long txNo) throws SQLException {
        final String DB_FIND_INTERFACE = "UPDATE node SET nodeType = 'D' WHERE nodeid = ? and nodeType != 'D'";
        Category log = ThreadCategory.getInstance(getClass());
        PreparedStatement stmt = null;
        List eventsToSend = new LinkedList();

        stmt = dbConn.prepareStatement(DB_FIND_INTERFACE);
        stmt.setLong(1, nodeId);
        int count = stmt.executeUpdate();
        stmt.close();

        if (log.isDebugEnabled())
            log.debug("markServicesDeleted: marked service deleted: " + nodeId);

        if (count > 0)
            return Collections.singletonList(EventUtils.createNodeDeletedEvent(source, nodeId, txNo));
        else
            return Collections.EMPTY_LIST;
    }

    /**
     * Marks the service deleted in the database and returns a serviceDeleted
     * event for the service, if and only if the service existed
     * 
     * @param dbConn
     *            the database connection
     * @param source
     *            the source for any events sent
     * @param nodeId
     *            the node the service resides on
     * @param ipAddr
     *            the interface the service resides on
     * @param service
     *            the name of the service
     * @param txNo
     *            a transaction number to associate with this deletion
     * @return a List containing a service deleted event.
     * @throws SQLException
     *             if an error occurs communicating with the database
     */
    private List markServiceDeleted(Connection dbConn, String source, long nodeId, String ipAddr, String service, long txNo) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());
        PreparedStatement stmt = null;

        final String DB_MARK_SERVICE_DELETED = "UPDATE ifservices SET status='D' " + "WHERE ifservices.serviceID = service.serviceID " + "AND ifservices.nodeID=? AND ifservices.ipAddr=? AND service.serviceName=?";

        stmt = dbConn.prepareStatement(DB_MARK_SERVICE_DELETED);
        stmt.setLong(1, nodeId);
        stmt.setString(2, ipAddr);
        stmt.setString(3, service);
        int count = stmt.executeUpdate();

        if (log.isDebugEnabled())
            log.debug("markServiceDeleted: marked service deleted: " + nodeId + "/" + ipAddr + "/" + service);

        stmt.close();

        if (count > 0)
            return Collections.singletonList(EventUtils.createServiceDeletedEvent(source, nodeId, ipAddr, service, txNo));
        else
            return Collections.EMPTY_LIST;
    }

    /**
     * Returns true if and only a node with the give label exists
     * 
     * @param dbConn
     *            a database connection
     * @param nodeLabel
     *            the label to check
     * @return true iff the node exists
     * @throws SQLException
     *             if a database error occurs
     */
    private boolean nodeExists(Connection dbConn, String nodeLabel) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbConn.prepareStatement(SQL_QUERY_NODE_EXIST);

            stmt.setString(1, nodeLabel);

            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }

    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     * @param msg
     * @param ex
     */
    private void notifyEventError(Event event, String msg, Exception ex) {
        if (!isXmlRpcEnabled())
            return;

        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);
        if ((txNo != -1) && m_notifySet.contains(event.getUei())) {
            int status = EventConstants.XMLRPC_NOTIFY_FAILURE;
            XmlrpcUtil.createAndSendXmlrpcNotificationEvent(txNo, event.getUei(), msg + ex.getMessage(), status, "OpenNMS.Capsd");
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void notifyEventReceived(Event event) {
        if (!isXmlRpcEnabled())
            return;

        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);

        if ((txNo != -1) && m_notifySet.contains(event.getUei())) {
            StringBuffer message = new StringBuffer("Received event: ");
            message.append(event.getUei());
            message.append(" : ");
            message.append(event);
            int status = EventConstants.XMLRPC_NOTIFY_RECEIVED;
            XmlrpcUtil.createAndSendXmlrpcNotificationEvent(txNo, event.getUei(), message.toString(), status, "OpenNMS.Capsd");
        }
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param event
     */
    private void notifyEventSuccess(Event event) {
        if (!isXmlRpcEnabled())
            return;

        long txNo = EventUtils.getLongParm(event, EventConstants.PARM_TRANSACTION_NO, -1L);

        if ((txNo != -1) && m_notifySet.contains(event.getUei())) {
            StringBuffer message = new StringBuffer("Completed processing event: ");
            message.append(event.getUei());
            message.append(" : ");
            message.append(event);
            int status = EventConstants.XMLRPC_NOTIFY_SUCCESS;
            XmlrpcUtil.createAndSendXmlrpcNotificationEvent(txNo, event.getUei(), message.toString(), status, "OpenNMS.Capsd");
        }
    }

    /**
     * This method is invoked by the EventIpcManager when a new event is
     * available for processing. Currently only text based messages are
     * processed by this callback. Each message is examined for its Universal
     * Event Identifier and the appropriate action is taking based on each UEI.
     * 
     * @param event
     *            The event.
     * 
     */
    public void onEvent(Event event) {
        Category log = ThreadCategory.getInstance(getClass());

        try {

            String eventUei = event.getUei();
            if (eventUei == null) {
                return;
            }

            if (log.isDebugEnabled()) {
                log.debug("Received event: " + eventUei);
            }

            notifyEventReceived(event);

            if (eventUei.equals(EventConstants.NEW_SUSPECT_INTERFACE_EVENT_UEI)) {
                handleNewSuspect(event);
            } else if (eventUei.equals(EventConstants.FORCE_RESCAN_EVENT_UEI)) {
                handleForceRescan(event);
            } else if (event.getUei().equals(EventConstants.UPDATE_SERVER_EVENT_UEI)) {
                handleUpdateServer(event);
            } else if (event.getUei().equals(EventConstants.UPDATE_SERVICE_EVENT_UEI)) {
                handleUpdateService(event);
            } else if (event.getUei().equals(EventConstants.ADD_NODE_EVENT_UEI)) {
                handleAddNode(event);
            } else if (event.getUei().equals(EventConstants.DELETE_NODE_EVENT_UEI)) {
                handleDeleteNode(event);
            } else if (event.getUei().equals(EventConstants.ADD_INTERFACE_EVENT_UEI)) {
                handleAddInterface(event);
            } else if (event.getUei().equals(EventConstants.DELETE_INTERFACE_EVENT_UEI)) {
                handleDeleteInterface(event);
            } else if (event.getUei().equals(EventConstants.DELETE_SERVICE_EVENT_UEI)) {
                handleDeleteService(event);
            } else if (event.getUei().equals(EventConstants.CHANGE_SERVICE_EVENT_UEI)) {
                handleChangeService(event);
            } else if (eventUei.equals(EventConstants.NODE_ADDED_EVENT_UEI)) {
                handleNodeAdded(event);
            } else if (eventUei.equals(EventConstants.NODE_DELETED_EVENT_UEI)) {
                handleNodeDeleted(event);
            } else if (eventUei.equals(EventConstants.DUP_NODE_DELETED_EVENT_UEI)) {
                handleDupNodeDeleted(event);
            }
            notifyEventSuccess(event);
        } catch (InsufficientInformationException ex) {
            log.info("BroadcastEventProcessor: insufficient information in event, discarding it: " + ex.getMessage());
            notifyEventError(event, "Invalid parameters: ", ex);
        } catch (FailedOperationException ex) {
            log.error("BroadcastEventProcessor: operation failed for event: " + event.getUei() + ", exception: " + ex.getMessage());
            notifyEventError(event, "processing failed: ", ex);
        }
    } // end onEvent()

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param ipaddr
     * @param serviceName
     * @return
     * @throws SQLException
     */
    private boolean serviceMappingExists(Connection dbConn, String ipaddr, String serviceName) throws SQLException {
        boolean mapExists;
        PreparedStatement stmt = null;
        // Verify if the specified service already exists on the
        // interface/service
        // mapping.
        stmt = dbConn.prepareStatement(SQL_QUERY_SERVICE_MAPPING_EXIST);

        stmt.setString(1, ipaddr);
        stmt.setString(2, serviceName);

        ResultSet rs = stmt.executeQuery();
        mapExists = rs.next();
        stmt.close();
        return mapExists;
    }

    /**
     * FIXME: finish the doc here
     * 
     * @param dbConn
     * @param serviceName
     * @return
     * @throws SQLException
     * @throws FailedOperationException
     */
    private int verifyServiceExists(Connection dbConn, String serviceName) throws SQLException, FailedOperationException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Category log = ThreadCategory.getInstance(getClass());

            // Retrieve the serviceId
            stmt = dbConn.prepareStatement(SQL_RETRIEVE_SERVICE_ID);

            stmt.setString(1, serviceName);

            rs = stmt.executeQuery();
            int serviceId = -1;
            while (rs.next()) {
                if (log.isDebugEnabled())
                    log.debug("verifyServiceExists: retrieve serviceid for service " + serviceName);
                serviceId = rs.getInt(1);
            }

            if (serviceId < 0) {
                if (log.isDebugEnabled())
                    log.debug("verifyServiceExists: the specified service: " + serviceName + " does not exist in the database.");
                throw new FailedOperationException("Invalid service: " + serviceName);
            }

            return serviceId;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {

            }
        }
    }

} // end class

