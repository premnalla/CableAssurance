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
// 2005 Mar 25: Fixed bug 1178 regarding designation of secondary SNMP
//              interfaces, as well as a few other minor bugs discovered
//              in testing the bug fix.
// 2004 Dec 27: Updated code to determine primary SNMP interface to select
//              an interface from collectd-configuration.xml first, and if
//              none found, then from all interfaces on the node. In either
//              case, a loopback interface is preferred if available.
// 2004 Apr 01: Fixed case where sysObjectId is null for suspect device
// 2004 Feb 12: Rebuild collectd package agaist IP List map when determining primary
//              interface.
// 2003 Nov 11: Merged changes from Rackspace project
// 2003 Sep 09: Modifications to allow OpenNMS to handle duplicate IP addresses.
// 2003 Mar 18: Fixed null pointer exceptions from some poorly written SNMP agents.
// 2003 Jan 31: Cleaned up some unused imports.
// 2002 Oct 03: Added the ability to discover non 127.*.*.* loopback interfaces
//		and to use that for the primary SNMP interface, if possible.
// 2002 Aug 01: Modified the code to label nodes based first on DNS, then SMB,
//		SNMP and finally IP Address. If available, the SNMP primary
//		interface will be used.
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.ConfigFileConstants;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.capsd.snmp.IfTable;
import org.opennms.netmgt.capsd.snmp.IfTableEntry;
import org.opennms.netmgt.capsd.snmp.IpAddrTable;
import org.opennms.netmgt.capsd.snmp.SystemGroup;
import org.opennms.netmgt.config.CapsdConfigFactory;
import org.opennms.netmgt.config.CollectdConfigFactory;
import org.opennms.netmgt.config.DatabaseConnectionFactory;
import org.opennms.netmgt.config.PollerConfigFactory;
import org.opennms.netmgt.eventd.EventIpcManagerFactory;
import org.opennms.netmgt.utils.IPSorter;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.opennms.netmgt.xml.event.Parms;
import org.opennms.netmgt.xml.event.Value;
import org.opennms.protocols.snmp.SnmpInt32;
import org.opennms.protocols.snmp.SnmpObjectId;
import org.opennms.protocols.snmp.SnmpOctetString;
import org.opennms.protocols.snmp.SnmpUInt32;

/**
 * This class is designed to scan/capability check a suspect interface, update
 * the database based on the information collected from the device, and generate
 * events necessary to notify the other OpenNMS services. The constructor takes
 * a string which is the IP address of the interface to be scanned.
 * 
 * @author <a href="mailto:jamesz@opennms.com">James Zuo </a>
 * @author <a href="mailto:mike@opennms.org">Mike Davidson </a>
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 */
final class SuspectEventProcessor implements Runnable {
    /**
     * SQL statement to retrieve the node identifier for a given IP address
     */
    private static String SQL_RETRIEVE_INTERFACE_NODEID_PREFIX = "SELECT nodeId FROM ipinterface WHERE ";

    /**
     * SQL statement to retrieve the ipaddresses for a given node ID
     */
    private final static String SQL_RETRIEVE_IPINTERFACES_ON_NODEID = "SELECT ipaddr FROM ipinterface WHERE nodeid = ? and ismanaged != 'D'";

    private final static String SELECT_METHOD_MIN = "min";

    private final static String SELECT_METHOD_MAX = "max";

    /**
     * IP address of new suspect interface
     */
    String m_suspectIf;

    /**
     * Constructor.
     * 
     * @param ifAddress
     *            Suspect interface address.
     */
    SuspectEventProcessor(String ifAddress) {
        // Check the arguments
        //
        if (ifAddress == null)
            throw new IllegalArgumentException("The interface address cannot be null");

        m_suspectIf = ifAddress;
    }

    /**
     * This method is responsible for determining if a node already exists in
     * the database for the current interface. If the IfCollector object
     * contains a valid SNMP collection, an attempt will be made to look up in
     * the database each interface contained in the SNMP collection's ifTable.
     * If an interface is found to already exist in the database a DbNodeEntry
     * object will be created from it and returned. If the IfCollector object
     * does not contain a valid SNMP collection or if none of the interfaces
     * exist in the database null is returned.
     * 
     * @param dbc
     *            Connection to the database.
     * @param collector
     *            Interface collector object
     * 
     * @return dbNodeEntry Returns null if a node does not already exist in the
     *         database, otherwise returns the DbNodeEntry object for the node
     *         under which the current interface/IP address should be added.
     * 
     * @throws SQLException
     *             Thrown if an error occurs retrieving the parent nodeid from
     *             the database.
     */
    private DbNodeEntry getExistingNodeEntry(java.sql.Connection dbc, IfCollector collector) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());

        if (log.isDebugEnabled())
            log.debug("getExistingNodeEntry: checking for current target: " + collector.getTarget());

        // Do we have any additional interface information collected via SNMP?
        // If not simply return, there is nothing to check
        if (!collector.hasSnmpCollection() || collector.getSnmpCollector().failed())
            return null;

        // Next verify that ifTable and ipAddrTable entries were collected
        IfSnmpCollector snmpc = collector.getSnmpCollector();
        IfTable ifTable = null;
        IpAddrTable ipAddrTable = null;

        if (snmpc.hasIfTable())
            ifTable = snmpc.getIfTable();

        if (snmpc.hasIpAddrTable())
            ipAddrTable = snmpc.getIpAddrTable();

        if (ifTable == null || ipAddrTable == null)
            return null;

        // SQL statement prefix
        StringBuffer sqlBuffer = new StringBuffer(SQL_RETRIEVE_INTERFACE_NODEID_PREFIX);
        boolean firstAddress = true;

        // Loop through the interface table entries and see if any already exist
        // in the database.
        Iterator iter = ifTable.getEntries().iterator();
        List ipaddrsOfNewNode = new ArrayList();
        List ipaddrsOfOldNode = new ArrayList();

        while (iter.hasNext()) {
            IfTableEntry ifEntry = (IfTableEntry) iter.next();

            if (ifEntry.containsKey("ifIndex") != true) {
                log.debug("getExistingNodeEntry:  Breaking from loop");
                break;
            }

            //
            // Get ifIndex
            //
            int ifIndex = -1;
            SnmpInt32 snmpIfIndex = (SnmpInt32) ifEntry.get(IfTableEntry.IF_INDEX);
            if (snmpIfIndex != null)
                ifIndex = snmpIfIndex.getValue();

            //
            // Get ALL IP Addresses for this ifIndex
            //
            List ipAddrs = IpAddrTable.getIpAddresses(ipAddrTable.getEntries(), ifIndex);
            if (log.isDebugEnabled())
                log.debug("getExistingNodeEntry: number of interfaces retrieved for ifIndex " + ifIndex + " is: " + ipAddrs.size());
            // 
            // Get ifType for this interface
            //
            int ifType = -1;
            SnmpInt32 snmpIfType = (SnmpInt32) ifEntry.get(IfTableEntry.IF_TYPE);
            if (snmpIfType != null)
                ifType = snmpIfType.getValue();

            // Iterate over IP address list and add each to the sql buffer
            //
            Iterator aiter = ipAddrs.iterator();
            while (aiter.hasNext()) {
                InetAddress ipAddress = (InetAddress) aiter.next();

                // 
                // Skip interface if no IP address or if IP address is "0.0.0.0"
                // or if this interface is of type loopback
                if (ipAddress == null || ipAddress.getHostAddress().equals("0.0.0.0") || ipAddress.getHostAddress().startsWith("127."))
                    continue;

                if (firstAddress) {
                    sqlBuffer.append("ipaddr='").append(ipAddress.getHostAddress()).append("'");
                    firstAddress = false;
                } else
                    sqlBuffer.append(" OR ipaddr='").append(ipAddress.getHostAddress()).append("'");

                ipaddrsOfNewNode.add(ipAddress.getHostAddress());
            }
        } // end while

        // Make sure we added at least one address to the SQL query
        //
        if (firstAddress)
            return null;

        // Prepare the db statement in advance
        //
        if (log.isDebugEnabled())
            log.debug("getExistingNodeEntry: issuing SQL command: " + sqlBuffer.toString());

        PreparedStatement stmt = dbc.prepareStatement(sqlBuffer.toString());

        // Do any of the IP addrs already exist in the database under another
        // node?
        //
        int nodeID = -1;
        try {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nodeID = rs.getInt(1);
                if (log.isDebugEnabled())
                    log.debug("getExistingNodeEntry: target " + collector.getTarget().getHostAddress() + nodeID);
                rs = null;
            }
        } catch (SQLException sqlE) {
            throw sqlE;
        } finally {
            try {
                stmt.close(); // automatically closes the result set as well
            } catch (Exception e) {
                // Ignore
            }
        }

        if (nodeID == -1)
            return null;

        try {
            stmt = dbc.prepareStatement(SQL_RETRIEVE_IPINTERFACES_ON_NODEID);
            stmt.setInt(1, nodeID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String ipaddr = rs.getString(1);
                if (!ipaddr.equals("0.0.0.0"))
                    ipaddrsOfOldNode.add(ipaddr);
            }
        } catch (SQLException sqlE) {
            throw sqlE;
        } finally {
            try {
                stmt.close(); // automatically closes the result set as well
            } catch (Exception e) {
                // Ignore
            }
        }

        if (ipaddrsOfNewNode.containsAll(ipaddrsOfOldNode)) {
            if (log.isDebugEnabled())
                log.debug("getExistingNodeEntry: found one of the addrs under existing node: " + nodeID);
            return DbNodeEntry.get(nodeID);
        } else {
            String dupIpaddr = getDuplicateIpaddress(ipaddrsOfOldNode, ipaddrsOfNewNode);
            createAndSendDuplicateIpaddressEvent(nodeID, dupIpaddr);
            return null;
        }
    }

    /**
     * This method is used to verify if there is a same ipaddress existing in
     * two sets of ipaddresses, and return the first ipaddress that is the same
     * in both sets as a string.
     * 
     * @param ipListA
     *            a collection of ip addresses.
     * @param ipListB
     *            a collection of ip addresses.
     * 
     * @return the first ipaddress exists in both ipaddress lists.
     * 
     */
    private String getDuplicateIpaddress(List ipListA, List ipListB) {
        Category log = ThreadCategory.getInstance(getClass());
        if (ipListA == null || ipListB == null)
            return null;

        String ipaddr = null;
        Iterator iter = ipListA.iterator();
        while (iter.hasNext()) {
            ipaddr = (String) iter.next();
            if (ipListB.contains(ipaddr)) {
                if (log.isDebugEnabled())
                    log.debug("getDuplicateIpaddress: get duplicate ip address: " + ipaddr);
                break;
            } else
                ipaddr = null;
        }
        return ipaddr;
    }

    /**
     * This method is responsble for inserting a new node into the node table.
     * 
     * @param dbc
     *            Database connection.
     * @param ifaddr
     *            Suspect interface
     * @param collector
     *            Interface collector containing SMB and SNMP info collected
     *            from the remote device.
     * 
     * @return DbNodeEntry object associated with the newly inserted node table
     *         entry.
     * 
     * @throws SQLException
     *             if an error occurs inserting the new node.
     */
    private DbNodeEntry createNode(Connection dbc, InetAddress ifaddr, IfCollector collector) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());

        // Determine primary interface for the node. Primary interface
        // is needed for determining the node label.
        //
        InetAddress primaryIf = determinePrimaryInterface(collector);

        // Get Snmp and Smb collector objects
        //
        IfSnmpCollector snmpc = collector.getSnmpCollector();
        IfSmbCollector smbc = collector.getSmbCollector();

        // First create a node entry for the new interface
        //
        DbNodeEntry entryNode = DbNodeEntry.create();

        // fill in the node information
        //
        Date now = new Date();
        entryNode.setCreationTime(now);
        entryNode.setLastPoll(now);
        entryNode.setNodeType(DbNodeEntry.NODE_TYPE_ACTIVE);
        entryNode.setLabel(primaryIf.getHostName());
        if (entryNode.getLabel().equals(primaryIf.getHostAddress()))
            entryNode.setLabelSource(DbNodeEntry.LABEL_SOURCE_ADDRESS);
        else
            entryNode.setLabelSource(DbNodeEntry.LABEL_SOURCE_HOSTNAME);

        if (snmpc != null) {
            if (snmpc.hasSystemGroup()) {
                SystemGroup sysgrp = snmpc.getSystemGroup();

                // sysObjectId
                SnmpObjectId sysObjectId = (SnmpObjectId) sysgrp.get(SystemGroup.SYS_OBJECTID);
                if (sysObjectId != null)
                    entryNode.setSystemOID(sysObjectId.toString());
                else
                    log.warn("SuspectEventProcessor: " + ifaddr.getHostAddress() + " has NO sysObjectId!!!!");

                // sysName
                String str = SystemGroup.getPrintableString((SnmpOctetString) sysgrp.get(SystemGroup.SYS_NAME));
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: " + ifaddr.getHostAddress() + " has sysName: " + str);

                if (str != null && str.length() > 0) {
                    entryNode.setSystemName(str);

                    // Hostname takes precedence over sysName so only replace
                    // label if
                    // hostname was not available.
                    if (entryNode.getLabelSource() == DbNodeEntry.LABEL_SOURCE_ADDRESS) {
                        entryNode.setLabel(str);
                        entryNode.setLabelSource(DbNodeEntry.LABEL_SOURCE_SYSNAME);
                    }
                }

                // sysDescription
                str = SystemGroup.getPrintableString((SnmpOctetString) sysgrp.get(SystemGroup.SYS_DESCR));
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: " + ifaddr.getHostAddress() + " has sysDescription: " + str);
                if (str != null && str.length() > 0)
                    entryNode.setSystemDescription(str);

                // sysLocation
                str = SystemGroup.getPrintableString((SnmpOctetString) sysgrp.get(SystemGroup.SYS_LOCATION));
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: " + ifaddr.getHostAddress() + " has sysLocation: " + str);
                if (str != null && str.length() > 0)
                    entryNode.setSystemLocation(str);

                // sysContact
                str = SystemGroup.getPrintableString((SnmpOctetString) sysgrp.get(SystemGroup.SYS_CONTACT));
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: " + ifaddr.getHostAddress() + " has sysContact: " + str);
                if (str != null && str.length() > 0)
                    entryNode.setSystemContact(str);
            }
        }

        // check for SMB information
        //
        if (smbc != null) {
            // Netbios Name and Domain
            // Note: only override if the label source is not HOSTNAME
            if (smbc.getNbtName() != null && entryNode.getLabelSource() != DbNodeEntry.LABEL_SOURCE_HOSTNAME) {
                entryNode.setLabel(smbc.getNbtName());
                entryNode.setLabelSource(DbNodeEntry.LABEL_SOURCE_NETBIOS);
                entryNode.setNetBIOSName(entryNode.getLabel());
                if (smbc.getDomainName() != null) {
                    entryNode.setDomainName(smbc.getDomainName());
                }
            }

            // Operating System
            if (smbc.getOS() != null) {
                entryNode.setOS(smbc.getOS());
            }
        }

        entryNode.store(dbc);
        return entryNode;
    }

    /**
     * This method is responsble for inserting new entries into the ipInterface
     * table for each interface found to be associated with the suspect
     * interface during the capabilities scan.
     * 
     * @param dbc
     *            Database connection.
     * @param node
     *            DbNodeEntry object representing the suspect interface's parent
     *            node table entry
     * @param useExistingNode
     *            False if a new node was created for the suspect interface.
     *            True if an existing node entry was found under which the the
     *            suspect interface is to be added.
     * @param ifaddr
     *            Suspect interface
     * @param collector
     *            Interface collector containing SMB and SNMP info collected
     *            from the remote device.
     * 
     * @throws SQLException
     *             if an error occurs adding interfaces to the ipInterface
     *             table.
     */
    private void addInterfaces(Connection dbc, DbNodeEntry node, boolean useExistingNode, InetAddress ifaddr, IfCollector collector) throws SQLException {
        
        Category log = ThreadCategory.getInstance(getClass());

        CapsdConfigFactory cFactory = CapsdConfigFactory.getInstance();
        PollerConfigFactory pollerCfgFactory = PollerConfigFactory.getInstance();

        Date now = new Date();

        int nodeId = node.getNodeId();

        DbIpInterfaceEntry ipIfEntry = DbIpInterfaceEntry.create(nodeId, ifaddr);
        ipIfEntry.setLastPoll(now);
        ipIfEntry.setHostname(ifaddr.getHostName());

        // NOTE: (reference internal bug# 201)
        // If the ip is 'managed', it might still be 'not polled' based
        // on the poller configuration
        //
        // The package filter evaluation requires that the ip be in the
        // database - at this point the ip is NOT in db, so insert as active
        // and update afterward
        //
        // Try to avoid re-evaluating the ip against filters for
        // each service, try to get the first package here and use
        // that for service evaluation
        //

        boolean addrUnmanaged = cFactory.isAddressUnmanaged(ifaddr);
        if (addrUnmanaged) {
            log.debug("addInterfaces: " + ifaddr + " is unmanaged");
            ipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_UNMANAGED);
        } else {
            log.debug("addInterfaces: " + ifaddr + " is managed");
            ipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_MANAGED);
        }

        ipIfEntry.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);

        ipIfEntry.store(dbc);

        // now update if necessary
        org.opennms.netmgt.config.poller.Package ipPkg = null;
        if (!addrUnmanaged) {
            // The newly discoveried IP addr is not in the Package IPList
            // Mapping yet, so rebuild the list.
            //
            PollerConfigFactory.getInstance().rebuildPackageIpListMap();

            boolean ipToBePolled = false;
            ipPkg = pollerCfgFactory.getFirstPackageMatch(ifaddr.getHostAddress());
            if (ipPkg != null)
                ipToBePolled = true;

            log.debug("addInterfaces: " + ifaddr + " is to be polled = " + ipToBePolled);

            if (!ipToBePolled) {
                // update ismanaged to 'N' in ipinterface
                ipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_NOT_POLLED);
                ipIfEntry.store(dbc);
            }
        }

        int ifIndex = -1;
        IfSnmpCollector snmpc = null;
        if (collector.hasSnmpCollection()) {
            snmpc = collector.getSnmpCollector();
            // Just set primary state to not eligible for now. The primary SNMP
            // interface won't be selected until after all interfaces have
            // been inserted into the database. This is because the interface
            // must already be in the database for filter rule evaluation to
            // succeed.

            ipIfEntry.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);
            if (snmpc.hasIpAddrTable() && (ifIndex = snmpc.getIfIndex(ifaddr)) != -1) {
                if (snmpc.hasIfTable()) {
                    int status = snmpc.getAdminStatus(ifIndex);
                    if (status != -1)
                        ipIfEntry.setStatus(status);
                }
            } else {
                // Address does not have a valid ifIndex associated with it
                // Assume there is no ipAddrTable and set ifIndex equal to
                // CapsdConfigFactory.LAME_SNMP_HOST_IFINDEX
                ifIndex = CapsdConfigFactory.LAME_SNMP_HOST_IFINDEX;
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: no valid ifIndex for " + ifaddr + " Assume this is a lame SNMP host");
            }
            if (ifIndex != -1)
                if (log.isDebugEnabled())
                    log.debug("SuspectEventProcessor: setting ifindex for " + ifaddr + " to " + ifIndex);
                ipIfEntry.setIfIndex(ifIndex);
            ipIfEntry.store(dbc);
        }

        // Add supported protocols
        addSupportedProtocols(node, ifaddr, collector.getSupportedProtocols(), addrUnmanaged, ifIndex, ipPkg);

        // If the useExistingNode flag is true, then we're done. The
        // interface is most likely an alias and the subinterfaces
        // collected via SNMP should already be in the database.
        //
        if (useExistingNode == true)
            return;

        if (snmpc != null) {

            boolean forceSnmpInterfaceEntry = true;

            // Made it this far...lets add the sub interfaces
            // 
            if (snmpc.hasIpAddrTable()) {
                Map extraTargets = collector.getAdditionalTargets();
                Iterator iter = extraTargets.keySet().iterator();
                while (iter.hasNext()) {
                    InetAddress xifaddr = (InetAddress) iter.next();

                    if (log.isDebugEnabled())
                        log.debug("addInterfaces: adding interface " + xifaddr.getHostAddress());

                    DbIpInterfaceEntry xipIfEntry = DbIpInterfaceEntry.create(nodeId, xifaddr);
                    xipIfEntry.setLastPoll(now);
                    xipIfEntry.setHostname(xifaddr.getHostName());

                    // NOTE: (reference internal bug# 201)
                    // If the ip is 'managed', it might still be 'not polled' based
                    // on the poller configuration
                    //
                    // The package filter evaluation requires that the ip be in the
                    // database - at this point the ip is NOT in db, so insert as
                    // active
                    // and update afterward
                    //
                    // Try to avoid re-evaluating the ip against filters for
                    // each service, try to get the first package here and use
                    // that for service evaluation
                    //
                    boolean xaddrUnmanaged = cFactory.isAddressUnmanaged(xifaddr);
                    if (xaddrUnmanaged)
                        xipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_UNMANAGED);
                    else
                        xipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_MANAGED);
                    // Just set primary state to not eligible for now. The
                    // primary SNMP interface won't be selected until after
                    // all interfaces have been inserted into the database.
                    // This is because the interface must already be in
                    // the database for filter rule evaluation to succeed.

                    xipIfEntry.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);
                    int xifIndex = -1;
                    if ((xifIndex = snmpc.getIfIndex(xifaddr)) != -1) {
                        xipIfEntry.setIfIndex(xifIndex);
                        int status = snmpc.getAdminStatus(xifIndex);
                        if (status != -1)
                            xipIfEntry.setStatus(status);

                        if (!supportsSnmp((List) extraTargets.get(xifaddr))) {
                            log.debug("addInterfaces: Interface doesn't support SNMP. " + xifaddr.getHostAddress() + " set to not eligible");
                        }
                    } else {
                        // No ifIndex found so set primary state to NOT_ELIGIBLE
                        log.debug("addInterfaces: No ifIndex found. " + xifaddr.getHostAddress() + " set to not eligible");
                    }

                    xipIfEntry.store(dbc);

                    // now update if necessary
                    org.opennms.netmgt.config.poller.Package xipPkg = null;
                    if (!xaddrUnmanaged) {
                        // The newly discoveried IP addr is not in the Package
                        // IPList
                        // Mapping yet, so rebuild the list.
                        //
                        PollerConfigFactory.getInstance().rebuildPackageIpListMap();

                        boolean xipToBePolled = false;
                        xipPkg = pollerCfgFactory.getFirstPackageMatch(xifaddr.getHostAddress());
                        if (xipPkg != null)
                            xipToBePolled = true;

                        if (!xipToBePolled) {
                            // update ismanaged to 'N' in ipinterface
                            xipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_NOT_POLLED);
                            xipIfEntry.store(dbc);
                        }
                    }

                    // add the supported protocols
                    addSupportedProtocols(node, xifaddr, (List) extraTargets.get(xifaddr), xaddrUnmanaged, xifIndex, xipPkg);
                } // end while()
                // Now add any non-IP interfaces
                //
                if (collector.hasNonIpInterfaces()) {
                    iter = ((List) collector.getNonIpInterfaces()).iterator();
                    while (iter.hasNext()) {
                        SnmpInt32 ifindex = (SnmpInt32) iter.next();

                        DbIpInterfaceEntry xipIfEntry = null;
                        try {
                            xipIfEntry = DbIpInterfaceEntry.create(nodeId, InetAddress.getByName("0.0.0.0"));
                        } catch (UnknownHostException e) {
                            continue;
                        }
                        xipIfEntry.setLastPoll(now);
                        xipIfEntry.setManagedState(DbIpInterfaceEntry.STATE_UNMANAGED);

                        xipIfEntry.setIfIndex(ifindex.getValue());
                        
                        int status = snmpc.getAdminStatus(ifindex.getValue());
                        if (status != -1)
                            xipIfEntry.setStatus(status);

                        xipIfEntry.setPrimaryState(DbIpInterfaceEntry.SNMP_NOT_ELIGIBLE);

                        xipIfEntry.store(dbc);
                    }
                }
            }

            // last thing to do is add all the snmp interface information
            //
            if (snmpc.hasIfTable()) {
                IfTable ift = snmpc.getIfTable();
                Iterator ifiter = ift.getEntries().iterator();
                while (ifiter.hasNext()) {
                    IfTableEntry ifte = (IfTableEntry) ifiter.next();

                    // index
                    //
                    int xifIndex = -1;
                    SnmpInt32 sint = (SnmpInt32) ifte.get(IfTableEntry.IF_INDEX);
                    if (sint != null)
                        xifIndex = sint.getValue();
                    else
                        continue;

                    // address
                    //
                    // WARNING: IfSnmpCollector.getIfAddressAndMask() ONLY returns
                    // the FIRST IP address and mask for a given interface as
                    // specified
                    // in the ipAddrTable.
                    //
                    InetAddress[] aaddrs = null;
                    if (snmpc.hasIpAddrTable())
                        aaddrs = snmpc.getIfAddressAndMask(sint.getValue());
                    if (aaddrs == null) {
                        // Must be non-IP interface, set ifAddress to '0.0.0.0' and
                        // Mask to null
                        aaddrs = new InetAddress[2];
                        try {
                            aaddrs[0] = InetAddress.getByName("0.0.0.0");
                        } catch (UnknownHostException e) {
                            continue;
                        }
                        aaddrs[1] = null;
                    }

                    // Retrieve ifType so we can check for loopback
                    //
                    sint = (SnmpInt32) ifte.get(IfTableEntry.IF_TYPE);
                    int ifType = sint.getValue();

                    // Skip loopback interfaces
                    //
                    if (aaddrs[0].getHostAddress().startsWith("127."))
                        continue;

                    DbSnmpInterfaceEntry snmpEntry = DbSnmpInterfaceEntry.create(nodeId, xifIndex);

                    // IP address
                    snmpEntry.setIfAddress(aaddrs[0]);
                    if(aaddrs[0].equals(ifaddr))
                        forceSnmpInterfaceEntry = false;

                    // netmask
                    if (aaddrs[1] != null)
                        snmpEntry.setNetmask(aaddrs[1]);

                    // description
                    String str = SystemGroup.getPrintableString((SnmpOctetString) ifte.get(IfTableEntry.IF_DESCR));
                    if (log.isDebugEnabled())
                        log.debug("SuspectEventProcessor: " + aaddrs[0].getHostAddress() + " has ifDescription: " + str);
                    if (str != null && str.length() > 0)
                        snmpEntry.setDescription(str);

                    // physical address
                    StringBuffer sbuf = new StringBuffer();
                    SnmpOctetString ostr = (SnmpOctetString) ifte.get(IfTableEntry.IF_PHYS_ADDR);
                    if (ostr != null) {
                        
                        byte[] bytes = ostr.getString();
                        for (int i = 0; i < bytes.length; i++) {
                            sbuf.append(Integer.toHexString(((int) bytes[i] >> 4) & 0xf));
                            sbuf.append(Integer.toHexString((int) bytes[i] & 0xf));
                        }
                        
                        String physAddr = sbuf.toString().trim();
                        
                        if (log.isDebugEnabled())
                            log.debug("SuspectEventProcessor: " + aaddrs[0].getHostAddress() + " has physical address: -" + physAddr + "-");
                        
                        if (physAddr.length() == 12) {
                            snmpEntry.setPhysicalAddress(physAddr);
                        }
                    }

                    // type
                    snmpEntry.setType(ifType);

                    // speed
                    SnmpUInt32 uint = (SnmpUInt32) ifte.get(IfTableEntry.IF_SPEED);
                    if (uint == null) {
                        snmpEntry.setSpeed(0);
                    } else {
                        snmpEntry.setSpeed((int) uint.getValue());
                    }

                    // admin status
                    sint = (SnmpInt32) ifte.get(IfTableEntry.IF_ADMIN_STATUS);
                    if (sint == null) {
                        snmpEntry.setAdminStatus(0);
                    } else {
                        snmpEntry.setAdminStatus(sint.getValue());
                    }

                    // oper status
                    sint = (SnmpInt32) ifte.get(IfTableEntry.IF_OPER_STATUS);
                    if (sint == null) {
                        snmpEntry.setOperationalStatus(0);
                    } else {
                        snmpEntry.setOperationalStatus(sint.getValue());
                    }

                    // name (from interface extensions table)
                    SnmpOctetString snmpIfName = snmpc.getIfName(xifIndex);
                    if (snmpIfName != null) {
                        String ifName = SystemGroup.getPrintableString(snmpIfName);
                        if (ifName != null && ifName.length() > 0)
                            snmpEntry.setName(ifName);
                    }

                    // alias (from interface extensions table)
                    SnmpOctetString snmpIfAlias = snmpc.getIfAlias(xifIndex);
                    if (snmpIfAlias != null) {
                        String ifAlias = SystemGroup.getPrintableString(snmpIfAlias);
                        if (ifAlias != null && ifAlias.length() > 0)
                            snmpEntry.setAlias(ifAlias);
                    }

                    snmpEntry.store(dbc);
                }
            }
            if(ifIndex == CapsdConfigFactory.LAME_SNMP_HOST_IFINDEX || forceSnmpInterfaceEntry) {
                DbSnmpInterfaceEntry snmpEntry = DbSnmpInterfaceEntry.create(nodeId, ifIndex);
                // IP address
                snmpEntry.setIfAddress(ifaddr);
                snmpEntry.store(dbc);
            }
        }
    }

    /**
     * Responsible for iterating inserting an entry into the ifServices table
     * for each protocol supported by the interface.
     * 
     * @param node
     *            Node entry
     * @param ifaddr
     *            Interface address
     * @param protocols
     *            List of supported protocols
     * @param addrUnmanaged
     *            Boolean flag indicating if interface is managed or unmanaged
     *            according to the Capsd configuration.
     * @param ifIndex
     *            Interface index or -1 if index is not known
     * @param ipPkg
     *            Poller package to which the interface belongs
     * 
     * @throws SQLException
     *             if an error occurs adding interfaces to the ipInterface
     *             table.
     */
    private void addSupportedProtocols(DbNodeEntry node, InetAddress ifaddr, List protocols, boolean addrUnmanaged, int ifIndex, org.opennms.netmgt.config.poller.Package ipPkg) throws SQLException {
        Category log = ThreadCategory.getInstance(getClass());
        if(ifaddr.getHostAddress().equals("0.0.0.0")) {
            log.debug("addSupportedProtocols: node " + node.getNodeId() + ": Cant add ip services for non-ip interface. Just return.");
            return;
        }

        // add the supported protocols
        //
        // NOTE!!!!!: (reference internal bug# 201)
        // If the ip is 'managed', the service can still be 'not polled'
        // based on the poller configuration - at this point the ip is already
        // in the database, so package filter evaluation should go through OK
        //
        Iterator iproto = protocols.iterator();
        while (iproto.hasNext()) {
            IfCollector.SupportedProtocol p = (IfCollector.SupportedProtocol) iproto.next();
            Number sid = (Number) CapsdConfigFactory.getInstance().getServiceIdentifier(p.getProtocolName());

            DbIfServiceEntry ifSvcEntry = DbIfServiceEntry.create(node.getNodeId(), ifaddr, sid.intValue());

            // now fill in the entry
            //
            if (addrUnmanaged)
                ifSvcEntry.setStatus(DbIfServiceEntry.STATUS_UNMANAGED);
            else {
                boolean svcToBePolled = false;
                if (ipPkg != null) {
                    svcToBePolled = PollerConfigFactory.getInstance().isPolled(p.getProtocolName(), ipPkg);
                    if (!svcToBePolled)
                        svcToBePolled = PollerConfigFactory.getInstance().isPolled(ifaddr.getHostAddress(), p.getProtocolName());
                }

                if (svcToBePolled)
                    ifSvcEntry.setStatus(DbIfServiceEntry.STATUS_ACTIVE);
                else
                    ifSvcEntry.setStatus(DbIfServiceEntry.STATUS_NOT_POLLED);
            }

            // Set qualifier if available. Currently the qualifier field
            // is used to store the port at which the protocol was found.
            //
            if (p.getQualifiers() != null && p.getQualifiers().get("port") != null) {
                try {
                    Integer port = (Integer) p.getQualifiers().get("port");
                    ifSvcEntry.setQualifier(port.toString());
                } catch (ClassCastException ccE) {
                    // Do nothing
                }
            }

            ifSvcEntry.setSource(DbIfServiceEntry.SOURCE_PLUGIN);
            ifSvcEntry.setNotify(DbIfServiceEntry.NOTIFY_ON);
            if (ifIndex != -1)
                ifSvcEntry.setIfIndex(ifIndex);
            ifSvcEntry.store();
        }
    }

    /**
     * Utility method which checks the provided list of supported protocols to
     * determine if the SNMP service is present.
     * 
     * @param supportedProtocols
     *            List of supported protocol objects.
     * 
     * @return TRUE if service "SNMP" or "SNMPv1" or "SNMPv2" is present in the list, FALSE otherwise
     */
    static boolean supportsSnmp(List supportedProtocols) {
        Iterator iter = supportedProtocols.iterator();
        while (iter.hasNext()) {
            IfCollector.SupportedProtocol p = (IfCollector.SupportedProtocol) iter.next();
            if (p.getProtocolName().equals("SNMP"))
                return true;
            else if (p.getProtocolName().equals("SNMPv1"))
                return true;
            else if (p.getProtocolName().equals("SNMPv2"))
                return true;
        }
        return false;
    }

    /**
     * Utility method which determines if the passed IfSnmpCollector object
     * contains an ifIndex value for the passed IP address.
     * 
     * @param ipaddr
     *            IP address
     * @param snmpc
     *            SNMP collection
     * 
     * @return TRUE if an ifIndex value was found in the SNMP collection for the
     *         provided IP address, FALSE otherwise.
     */
    static boolean hasIfIndex(InetAddress ipaddr, IfSnmpCollector snmpc) {
        int ifIndex = -1;
        if (snmpc.hasIpAddrTable())
            ifIndex = snmpc.getIfIndex(ipaddr);

        Category log = ThreadCategory.getInstance(Capsd.class);
        if (log.isDebugEnabled())
            log.debug("hasIfIndex: ipAddress: " + ipaddr.getHostAddress() + " has ifIndex: " + ifIndex);
        if (ifIndex == -1)
            return false;
        else
            return true;
    }

    /**
     * Utility method which determines returns the ifType for the passed IP
     * address.
     * 
     * @param ipaddr
     *            IP address
     * @param snmpc
     *            SNMP collection
     * 
     * @return TRUE if an ifIndex value was found in the SNMP collection for the
     *         provided IP address, FALSE otherwise.
     */
    static int getIfType(InetAddress ipaddr, IfSnmpCollector snmpc) {
        int ifIndex = snmpc.getIfIndex(ipaddr);
        int ifType = snmpc.getIfType(ifIndex);

        Category log = ThreadCategory.getInstance(Capsd.class);
        if (log.isDebugEnabled())
            log.debug("getIfType: ipAddress: " + ipaddr.getHostAddress() + " has ifIndex: " + ifIndex + " and ifType: " + ifType);
        return ifType;
    }

    /**
     * Utility method which compares two InetAddress objects based on the
     * provided method (MIN/MAX) and returns the InetAddress which is to be
     * considered the primary interface.
     * 
     * NOTE: In order for an interface to be considered primary it must be
     * managed. This method will return null if the 'oldPrimary' address is null
     * and the 'currentIf' address is unmanaged.
     * 
     * @param currentIf
     *            Interface with which to compare the 'oldPrimary' address.
     * @param oldPrimary
     *            Primary interface to be compared against the 'currentIf'
     *            address.
     * @param method
     *            Comparison method to be used (either "min" or "max")
     * 
     * @return InetAddress object of the primary interface based on the provided
     *         method or null if neither address is eligible to be primary.
     */
    static InetAddress compareAndSelectPrimary(InetAddress currentIf, InetAddress oldPrimary, String method) {
        InetAddress newPrimary = null;
        if (oldPrimary == null) {
            if (!CapsdConfigFactory.getInstance().isAddressUnmanaged(currentIf))
                return currentIf;
            else
                return oldPrimary;
        }

        long current = IPSorter.convertToLong(currentIf.getAddress());
        long primary = IPSorter.convertToLong(oldPrimary.getAddress());

        if (method.equals(SELECT_METHOD_MIN)) {
            // Smallest address wins
            if (current < primary) {
                // Replace the primary interface with the current
                // interface only if the current interface is managed!
                if (!CapsdConfigFactory.getInstance().isAddressUnmanaged(currentIf))
                    newPrimary = currentIf;
            }
        } else {
            // Largest address wins
            if (current > primary) {
                // Replace the primary interface with the current
                // interface only if the current interface is managed!
                if (!CapsdConfigFactory.getInstance().isAddressUnmanaged(currentIf))
                    newPrimary = currentIf;
            }
        }

        if (newPrimary != null)
            return newPrimary;
        else
            return oldPrimary;
    }

    /**
     * Builds a list of InetAddress objects representing each of the interfaces
     * from the IfCollector object which support SNMP and have a valid ifIndex
     * and is a loopback interface.
     * 
     * This is in order to allow a non-127.*.*.* loopback address to be chosen
     * as the primary SNMP interface.
     * 
     * @param collector
     *            IfCollector object containing SNMP and SMB info.
     * 
     * @return List of InetAddress objects.
     */
    private static List buildLBSnmpAddressList(IfCollector collector) {
        Category log = ThreadCategory.getInstance(SuspectEventProcessor.class);

        List addresses = new ArrayList();

        // Verify that SNMP info is available
        if (collector.getSnmpCollector() == null) {
            if (log.isDebugEnabled())
                log.debug("buildLBSnmpAddressList: no SNMP info for " + collector.getTarget());
            return addresses;
        }

        // Verify that both the ifTable and ipAddrTable were
        // successfully collected.
        IfSnmpCollector snmpc = collector.getSnmpCollector();
        if (!snmpc.hasIfTable() || !snmpc.hasIpAddrTable()) {
            log.info("buildLBSnmpAddressList: missing SNMP info for " + collector.getTarget());
            return addresses;
        }

        // To be eligible to be the primary SNMP interface for a node:
        //
        // 1. The interface must support SNMP
        // 2. The interface must have a valid ifIndex
        //

        // Add eligible target.
        //
        InetAddress ipAddr = collector.getTarget();
        if (supportsSnmp(collector.getSupportedProtocols()) && hasIfIndex(ipAddr, snmpc) && getIfType(ipAddr, snmpc) == 24) {
            if (log.isDebugEnabled())
                log.debug("buildLBSnmpAddressList: adding target interface " + ipAddr.getHostAddress() + " temporarily marked as primary!");
            addresses.add(ipAddr);
        }

        // Add eligible subtargets.
        //
        if (collector.hasAdditionalTargets()) {
            Map extraTargets = collector.getAdditionalTargets();
            Iterator iter = extraTargets.keySet().iterator();
            while (iter.hasNext()) {
                InetAddress currIf = (InetAddress) iter.next();

                // Test current subtarget.
                // 
                if (supportsSnmp((List) extraTargets.get(currIf)) && getIfType(currIf, snmpc) == 24) {
                    if (log.isDebugEnabled())
                        log.debug("buildLBSnmpAddressList: adding subtarget interface " + currIf.getHostAddress() + " temporarily marked as primary!");
                    addresses.add(currIf);
                }
            } // end while()
        } // end if()

        return addresses;
    }

    /**
     * Builds a list of InetAddress objects representing each of the interfaces
     * from the IfCollector object which support SNMP and have a valid ifIndex.
     * 
     * @param collector
     *            IfCollector object containing SNMP and SMB info.
     * 
     * @return List of InetAddress objects.
     */
    private static List buildSnmpAddressList(IfCollector collector) {
        Category log = ThreadCategory.getInstance(SuspectEventProcessor.class);

        List addresses = new ArrayList();

        // Verify that SNMP info is available
        if (collector.getSnmpCollector() == null) {
            if (log.isDebugEnabled())
                log.debug("buildSnmpAddressList: no SNMP info for " + collector.getTarget());
            return addresses;
        }

        // Verify that both the ifTable and ipAddrTable were
        // successfully collected.
        IfSnmpCollector snmpc = collector.getSnmpCollector();
        if (!snmpc.hasIfTable() || !snmpc.hasIpAddrTable()) {
            log.info("buildSnmpAddressList: missing SNMP info for " + collector.getTarget());
            return addresses;
        }

        // To be eligible to be the primary SNMP interface for a node:
        //
        // 1. The interface must support SNMP
        // 2. The interface must have a valid ifIndex
        //

        // Add eligible target.
        //
        InetAddress ipAddr = collector.getTarget();
        if (supportsSnmp(collector.getSupportedProtocols()) && hasIfIndex(ipAddr, snmpc)) {
            if (log.isDebugEnabled())
                log.debug("buildSnmpAddressList: adding target interface " + ipAddr.getHostAddress() + " temporarily marked as primary!");
            addresses.add(ipAddr);
        }

        // Add eligible subtargets.
        //
        if (collector.hasAdditionalTargets()) {
            Map extraTargets = collector.getAdditionalTargets();
            Iterator iter = extraTargets.keySet().iterator();
            while (iter.hasNext()) {
                InetAddress currIf = (InetAddress) iter.next();

                // Test current subtarget.
                // 
                if (supportsSnmp((List) extraTargets.get(currIf)) && hasIfIndex(currIf, snmpc)) {
                    if (log.isDebugEnabled())
                        log.debug("buildSnmpAddressList: adding subtarget interface " + currIf.getHostAddress() + " temporarily marked as primary!");
                    addresses.add(currIf);
                }
            } // end while()
        } // end if()

        return addresses;
    }

    /**
     * This method is responsbile for determining the node's primary IP
     * interface from among all the node's IP interfaces.
     * 
     * @param collector
     *            IfCollector object containing SNMP and SMB info.
     * 
     * @return InetAddress object of the primary SNMP interface or null if none
     *         of the node's interfaces are eligible.
     */
    private InetAddress determinePrimaryInterface(IfCollector collector) {
        Category log = ThreadCategory.getInstance(getClass());

        InetAddress primaryIf = null;

        // For now hard-coding primary interface address selection method to MIN
        String method = SELECT_METHOD_MIN;

        // Initially set the target interface as primary
        primaryIf = collector.getTarget();

        // Next the subtargets will be tested. If is managed and
        // has a smaller numeric IP address then it will in turn be
        // set as the primary interface.
        if (collector.hasAdditionalTargets()) {
            Map extraTargets = collector.getAdditionalTargets();
            Iterator iter = extraTargets.keySet().iterator();
            while (iter.hasNext()) {
                InetAddress currIf = (InetAddress) iter.next();
                primaryIf = compareAndSelectPrimary(currIf, primaryIf, method);
            } // end while()
        } // end if (Collector.hasAdditionalTargets())

        if (log.isDebugEnabled())
            if (primaryIf != null)
                log.debug("determinePrimaryInterface: selected primary interface: " + primaryIf.getHostAddress());
            else
                log.debug("determinePrimaryInterface: no primary interface found");
        return primaryIf;
    }

    /**
     * This is where all the work of the class is done.
     */
    public void run() {
        Category log = ThreadCategory.getInstance(getClass());

        CapsdConfigFactory cFactory = CapsdConfigFactory.getInstance();

        // Convert interface InetAddress object
        //
        InetAddress ifaddr = null;
        try {
            ifaddr = InetAddress.getByName(m_suspectIf);
        } catch (UnknownHostException e) {
            log.warn("SuspectEventProcessor: Failed to convert interface address " + m_suspectIf + " to InetAddress", e);
            return;
        }

        // collect the information
        //
        if (log.isDebugEnabled())
            log.debug("SuspectEventProcessor: running collection for " + ifaddr.getHostAddress());

        IfCollector collector = new IfCollector(ifaddr, true);
        collector.run();

        // Track changes to primary SNMP interface
        InetAddress oldSnmpPrimaryIf = null;
        InetAddress newSnmpPrimaryIf = null;

        // Update the database
        //
        boolean updateCompleted = false;
        boolean useExistingNode = false;
        DbNodeEntry entryNode = null;
        try {
            // Synchronize on the Capsd sync lock so we can check if
            // the interface is already in the database and perform
            // the necessary inserts in one atomic operation
            //	
            // The RescanProcessor class is also synchronizing on this
            // lock prior to performing database inserts or updates.
            Connection dbc = null;
            synchronized (Capsd.getDbSyncLock()) {
                // Get database connection
                //
                try {
                    dbc = DatabaseConnectionFactory.getInstance().getConnection();

                    // Only add the node/interface to the database if
                    // it isn't already in the database
                    if (!cFactory.isInterfaceInDB(dbc, ifaddr)) {
                        // Using the interface collector object determine
                        // if this interface belongs under a node already
                        // in the database.
                        //
                        entryNode = getExistingNodeEntry(dbc, collector);

                        if (entryNode == null) {
                            // Create a node entry for the new interface
                            //
                            entryNode = createNode(dbc, ifaddr, collector);
                        } else {
                            // Will use existing node entry
                            //
                            useExistingNode = true;
                        }

                        // Get old primary SNMP interface(s) (if one or more exists)
                        //
                        List oldPriIfs = getPrimarySnmpInterfaceFromDb(dbc, entryNode);

                        // Add interfaces
                        //
                        addInterfaces(dbc, entryNode, useExistingNode, ifaddr, collector);

                        // Now that all interfaces have been added to the
                        // database we can update the 'primarySnmpInterface'
                        // field of the ipInterface table. Necessary because
                        // the IP address must already be in the database
                        // to evaluate against a filter rule.
                        //
                        // Determine primary SNMP interface from the lists of possible addresses
                        // in this order: loopback interfaces in collectd-configuration.xml,
                        // other interfaces in collectd-configuration.xml, loopback interfaces,
                        // other interfaces
                        //
                        boolean strict = true;
                        CollectdConfigFactory.getInstance().rebuildPackageIpListMap();
                        List lbAddressList = buildLBSnmpAddressList(collector);
                        List addressList = buildSnmpAddressList(collector);
                        // first set the value of issnmpprimary for secondaries
                        Iterator iter = addressList.iterator();
                        while(iter.hasNext()) {
                            InetAddress addr = (InetAddress) iter.next();
                            if (CollectdConfigFactory.getInstance().lookupInterfaceServicePair(addr.getHostAddress(), "SNMP") || CollectdConfigFactory.getInstance().lookupInterfaceServicePair(addr.getHostAddress(), "SNMPv1") || CollectdConfigFactory.getInstance().lookupInterfaceServicePair(addr.getHostAddress(), "SNMPv2")) {
                                PreparedStatement stmt = dbc.prepareStatement("UPDATE ipInterface SET isSnmpPrimary='S' WHERE nodeId=? AND ipAddr=? AND isManaged!='D'");
                                stmt.setInt(1, entryNode.getNodeId());
                                stmt.setString(2, addr.getHostAddress());

                                // Execute statement
                                try {
                                    stmt.executeUpdate();
                                    log.debug("updated " + addr.getHostAddress() + " to secondary.");
                                } catch (SQLException sqlE) {
                                    throw sqlE;
                                } finally {
                                    try {
                                        stmt.close();
                                    } catch (Exception e) {
                                        // Ignore
                                    }
                                }
                            }
                        }
                        String psiType = null;
                        if (lbAddressList != null) {
                            newSnmpPrimaryIf = CollectdConfigFactory.getInstance().determinePrimarySnmpInterface(lbAddressList, strict);
                            psiType = ConfigFileConstants.getFileName(ConfigFileConstants.COLLECTD_CONFIG_FILE_NAME) + " loopback addresses";
                        }
                        if(newSnmpPrimaryIf == null) {
                            newSnmpPrimaryIf = CollectdConfigFactory.getInstance().determinePrimarySnmpInterface(addressList, strict);
                            psiType = ConfigFileConstants.getFileName(ConfigFileConstants.COLLECTD_CONFIG_FILE_NAME) + " addresses";
                        }
                        strict = false;
                        if((newSnmpPrimaryIf == null) && (lbAddressList != null)){
                            newSnmpPrimaryIf = CollectdConfigFactory.getInstance().determinePrimarySnmpInterface(lbAddressList, strict);
                            psiType = "DB loopback addresses";
                        }
                        if(newSnmpPrimaryIf == null) {
                            newSnmpPrimaryIf = CollectdConfigFactory.getInstance().determinePrimarySnmpInterface(addressList, strict);
                            psiType = "DB addresses";
                        }
                        if (collector.hasSnmpCollection() &&newSnmpPrimaryIf == null) {
                            newSnmpPrimaryIf = ifaddr;
                            psiType = "New suspect ip address";
                        }

                        if (log.isDebugEnabled()) {
                            if(newSnmpPrimaryIf == null) {
                                log.debug("No primary SNMP interface found");
                            } else {
                                log.debug("primary SNMP interface is: " + newSnmpPrimaryIf + ", selected from " + psiType);
                            }
                        }  
                        // iterate over list of old primaries. There should only be
                        // one or none, but in case there are more, this will clear
                        // out the extras.
                        Iterator opiter = oldPriIfs.iterator();
                        if (opiter.hasNext()) {
                            while (opiter.hasNext()) {
                                setPrimarySnmpInterface(dbc, entryNode, newSnmpPrimaryIf, (InetAddress) opiter.next());
                            } 
                        } else {
                            setPrimarySnmpInterface(dbc, entryNode, newSnmpPrimaryIf, null);
                        }
                        // Update
                        updateCompleted = true;
                    }
                } finally {
                    if (dbc != null) {
                        try {
                            dbc.close();
                        } catch (SQLException e) {
                            if (log.isInfoEnabled())
                                log.info("run: an sql exception occured closing the database connection", e);
                        }
                    }
                    dbc = null;
                }
            }

        } // end try
        catch (Throwable t) {
            log.error("Error writing records", t);
        }

        // Send events
        //
        if (updateCompleted) {
            if (!useExistingNode)
                createAndSendNodeAddedEvent(entryNode);

            sendInterfaceEvents(entryNode, useExistingNode, ifaddr, collector);

            if (useExistingNode) {
                generateSnmpDataCollectionEvents(entryNode, oldSnmpPrimaryIf, newSnmpPrimaryIf);
            }

        }

        if (log.isDebugEnabled())
            log.debug("SuspectEventProcessor for " + m_suspectIf + " completed.");
    } // end run

    /**
     * Returns a list of InetAddress object(s) of the primary SNMP interface(s)
     * (if one or more exists).
     * 
     * @param dbc
     *            Database connection.
     * @param node
     *            DbNodeEntry object representing the interface's parent node
     *            table entry
     * 
     * @throws SQLException
     *             if an error occurs updating the ipInterface table
     * @return List of Old SNMP primary interface addresses (usually just one).
     */
    static List getPrimarySnmpInterfaceFromDb(Connection dbc, DbNodeEntry node) throws SQLException {
        Category log = ThreadCategory.getInstance(SuspectEventProcessor.class);

        List priSnmpAddrs = new ArrayList();

        log.debug("getPrimarySnmpInterfaceFromDb: retrieving primary snmp interface(s) from DB for node " + node.getNodeId());
        InetAddress oldPrimarySnmpIf = null;

        // Prepare SQL statement
        PreparedStatement stmt = dbc.prepareStatement("SELECT ipAddr FROM ipInterface WHERE nodeId=? AND isSnmpPrimary='P' AND isManaged!='D'");
        stmt.setInt(1, node.getNodeId());

        // Execute statement
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
            while (rs.next()) {
                String oldPrimaryAddr = rs.getString(1);
                log.debug("getPrimarySnmpInterfaceFromDb: String oldPrimaryAddr = " + oldPrimaryAddr);
                if (oldPrimaryAddr != null) {
                    try {
                        oldPrimarySnmpIf = InetAddress.getByName(oldPrimaryAddr);
                        log.debug("getPrimarySnmpInterfaceFromDb: old primary Snmp interface is " + oldPrimarySnmpIf.getHostAddress());
                    } catch (UnknownHostException e) {
                        log.warn("Failed converting IP address " + oldPrimaryAddr);
                    }
                    priSnmpAddrs.add(oldPrimarySnmpIf);
                }
            }
        } catch (SQLException sqlE) {
            log.warn("getPrimarySnmpInterfaceFromDb: Exception: " + sqlE);
            throw sqlE;
        } finally {
            try {
                stmt.close(); // automatically closes the result set as well
            } catch (Exception e) {
                // Ignore
            }
        }

        return priSnmpAddrs;
    }

    /**
     * Responsible for setting the value of the 'isSnmpPrimary' field of the
     * ipInterface table to 'P' (Primary) for the primary SNMP interface
     * address.
     * 
     * @param dbc
     *            Database connection.
     * @param node
     *            DbNodeEntry object representing the suspect interface's parent
     *            node table entry
     * @param newPrimarySnmpIf
     *            New primary SNMP interface.
     * @param oldPrimarySnmpIf
     *            Old primary SNMP interface.
     * 
     * @throws SQLException
     *             if an error occurs updating the ipInterface table
     * 
     */
    static void setPrimarySnmpInterface(Connection dbc, DbNodeEntry node, InetAddress newPrimarySnmpIf, InetAddress oldPrimarySnmpIf) throws SQLException {
        Category log = ThreadCategory.getInstance(SuspectEventProcessor.class);

        if (newPrimarySnmpIf == null) {
            if (log.isDebugEnabled())
                log.debug("setPrimarySnmpInterface: newSnmpPrimary is null, nothing to set, returning.");
            return;
        } else {
            if (log.isDebugEnabled())
                log.debug("setPrimarySnmpInterface: newSnmpPrimary = " + newPrimarySnmpIf);
        }

        // Verify that old and new primary interfaces are different
        //
        if (oldPrimarySnmpIf != null && oldPrimarySnmpIf.equals(newPrimarySnmpIf)) {
            // Old and new primary interfaces are the same
            if (log.isDebugEnabled())
                log.debug("setPrimarySnmpInterface: Old and new primary interfaces are the same");
            return;
        }


        // Set primary SNMP interface 'isSnmpPrimary' field to 'P' for primary
        //
        if (newPrimarySnmpIf != null) {
            if (log.isDebugEnabled())
                log.debug("setPrimarySnmpInterface:  Updating primary SNMP interface " + newPrimarySnmpIf.getHostAddress());

            // Update the appropriate entry in the 'ipInterface' table
            //

            // Prepare SQL statement
            PreparedStatement stmt = dbc.prepareStatement("UPDATE ipInterface SET isSnmpPrimary='P' WHERE nodeId=? AND ipaddr=? AND isManaged!='D'");
            stmt.setInt(1, node.getNodeId());
            stmt.setString(2, newPrimarySnmpIf.getHostAddress());

            // Execute statement
            try {
                stmt.executeUpdate();
                if (log.isDebugEnabled())
                    log.debug("setPrimarySnmpInterface: completed update of new primary interface to PRIMARY.");
            } catch (SQLException sqlE) {
                throw sqlE;
            } finally {
                try {
                    stmt.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    /**
     * Determines if any SNMP data collection related events need to be
     * generated based upon the results of the current rescan. If necessary will
     * generate one of the following events: 'reinitializePrimarySnmpInterface'
     * 'primarySnmpInterfaceChanged'
     * 
     * @param nodeEntry
     *            DbNodeEntry object of the node being rescanned.
     * @param oldPrimary
     *            Old primary SNMP interface
     * @param newPrimary
     *            New primary SNMP interface
     */
    private void generateSnmpDataCollectionEvents(DbNodeEntry nodeEntry, InetAddress oldPrimary, InetAddress newPrimary) {
        Category log = ThreadCategory.getInstance(getClass());

        // Sanity check -- should not happen
        if (oldPrimary == null && newPrimary == null) {
            log.warn("generateSnmpDataCollectionEvents: both old and new primary SNMP interface vars are null!");
        }

        // Sanity check -- should not happen
        else if (oldPrimary != null && newPrimary == null) {
            log.warn("generateSnmpDataCollectionEvents: old primary (" + oldPrimary.getHostAddress() + ") is not null but new primary is null!");
        }

        // Just added the primary SNMP interface to the node, the
        // nodeGainedService
        // event already generated is sufficient to start SNMP data
        // collection...no
        // additional events are required.
        else if (oldPrimary == null && newPrimary != null) {
            if (log.isDebugEnabled())
                log.debug("generateSnmpDataCollectionEvents: identified " + newPrimary.getHostAddress() + " as the primary SNMP interface for node " + nodeEntry.getNodeId());
        }

        // A PrimarySnmpInterfaceChanged event is generated if the scan
        // found a different primary SNMP interface than what is stored
        // in the database.
        //
        else if (!oldPrimary.equals(newPrimary)) {
            if (log.isDebugEnabled()) {
                log.debug("generateSnmpDataCollectionEvents: primary SNMP interface has changed.  Was: " + oldPrimary.getHostAddress() + " Is: " + newPrimary.getHostAddress());
            }

            createAndSendPrimarySnmpInterfaceChangedEvent(nodeEntry.getNodeId(), newPrimary, oldPrimary);
        }

        // The primary SNMP interface did not change but the Capsd scan just
        // added
        // an interface to the node so we need to update the interface
        // map which is maintained in memory for the purpose of doing
        // SNMP data collection. Therefore we generate a
        // reinitializePrimarySnmpInterface event so that this map
        // can be refreshed based on the most up to date information
        // in the database.
        else {
            if (log.isDebugEnabled())
                log.debug("generateSnmpDataCollectionEvents: Generating reinitializeSnmpInterface event for interface " + newPrimary.getHostAddress());
            createAndSendReinitializePrimarySnmpInterfaceEvent(nodeEntry.getNodeId(), newPrimary);
        }
    }

    /**
     * This method is responsible for generating a primarySnmpInterfaceChanged
     * event and sending it to eventd..
     * 
     * @param nodeId
     *            Nodeid of node being rescanned.
     * @param newPrimaryIf
     *            new primary SNMP interface address
     * @param oldPrimaryIf
     *            old primary SNMP interface address
     */
    private void createAndSendPrimarySnmpInterfaceChangedEvent(int nodeId, InetAddress newPrimaryIf, InetAddress oldPrimaryIf) {
        Category log = ThreadCategory.getInstance(getClass());

        String oldPrimaryAddr = null;
        if (oldPrimaryIf != null)
            oldPrimaryAddr = oldPrimaryIf.getHostAddress();

        String newPrimaryAddr = null;
        if (newPrimaryIf != null)
            newPrimaryAddr = newPrimaryIf.getHostAddress();

        if (log.isDebugEnabled())
            log.debug("createAndSendPrimarySnmpInterfaceChangedEvent: nodeId: " + nodeId + " oldPrimarySnmpIf: '" + oldPrimaryAddr + "' newPrimarySnmpIf: '" + newPrimaryAddr + "'");

        Event newEvent = new Event();

        newEvent.setUei(EventConstants.PRIMARY_SNMP_INTERFACE_CHANGED_EVENT_UEI);

        newEvent.setSource("OpenNMS.Capsd");

        newEvent.setNodeid(nodeId);

        newEvent.setInterface(newPrimaryAddr);

        newEvent.setHost(Capsd.getLocalHostAddress());

        newEvent.setService("SNMP");

        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Add appropriate parms
        Parms eventParms = new Parms();
        Parm eventParm = null;
        Value parmValue = null;

        if (oldPrimaryAddr != null) {
            // Add old node label
            eventParm = new Parm();
            eventParm.setParmName(EventConstants.PARM_OLD_PRIMARY_SNMP_ADDRESS);
            parmValue = new Value();
            parmValue.setContent(oldPrimaryAddr);
            eventParm.setValue(parmValue);
            eventParms.addParm(eventParm);
        }

        if (newPrimaryAddr != null) {
            // Add new node label
            eventParm = new Parm();
            eventParm.setParmName(EventConstants.PARM_NEW_PRIMARY_SNMP_ADDRESS);
            parmValue = new Value();
            parmValue.setContent(newPrimaryAddr);
            eventParm.setValue(parmValue);
            eventParms.addParm(eventParm);
        }

        // Add Parms to the event
        newEvent.setParms(eventParms);

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendPrimarySnmpInterfaceChangedEvent: successfully sent primarySnmpInterfaceChanged event for nodeID: " + nodeId);
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }
    }

    /**
     * This method is responsible for generating a
     * reinitializePrimarySnmpInterface event and sending it to eventd.
     * 
     * @param nodeId
     *            Nodeid of node being rescanned.
     * @param primarySnmpIf
     *            Primary SNMP interface address.
     */
    private void createAndSendReinitializePrimarySnmpInterfaceEvent(int nodeId, InetAddress primarySnmpIf) {
        Category log = ThreadCategory.getInstance(getClass());

        if (log.isDebugEnabled())
            log.debug("reinitializePrimarySnmpInterface: nodeId: " + nodeId + " interface: " + primarySnmpIf.getHostAddress());

        Event newEvent = new Event();

        newEvent.setUei(EventConstants.REINITIALIZE_PRIMARY_SNMP_INTERFACE_EVENT_UEI);

        newEvent.setSource("OpenNMS.Capsd");

        newEvent.setNodeid(nodeId);

        newEvent.setHost(Capsd.getLocalHostAddress());

        newEvent.setInterface(primarySnmpIf.getHostAddress());

        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendReinitializePrimarySnmpInterfaceEvent: successfully sent reinitializePrimarySnmpInterface event for nodeID: " + nodeId);
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }
    }

    /**
     * This method is responsible for creating all the necessary interface-level
     * events for the node and sending them to Eventd.
     * 
     * @param node
     *            DbNodeEntry object for the parent node.
     * @param useExistingNode
     *            TRUE if existing node was used, FALSE if new node was created.
     * @param ifaddr
     *            Target interface address
     * @param collector
     *            Interface collector containing SNMP and SMB info.
     */
    private void sendInterfaceEvents(DbNodeEntry node, boolean useExistingNode, InetAddress ifaddr, IfCollector collector) {
        Category log = ThreadCategory.getInstance(getClass());

        // Go ahead and send events for the target interface
        //

        // nodeGainedInterface
        //
        if (log.isDebugEnabled())
            log.debug("sendInterfaceEvents: sending node gained interface event for " + ifaddr.getHostAddress());

        createAndSendNodeGainedInterfaceEvent(node.getNodeId(), ifaddr);

        // nodeGainedService
        //
        log.debug("sendInterfaceEvents: processing supported services for " + ifaddr.getHostAddress());
        Iterator iproto = collector.getSupportedProtocols().iterator();
        while (iproto.hasNext()) {
            IfCollector.SupportedProtocol p = (IfCollector.SupportedProtocol) iproto.next();
            if (log.isDebugEnabled())
                log.debug("sendInterfaceEvents: sending event for service: " + p.getProtocolName());
            createAndSendNodeGainedServiceEvent(node, ifaddr, p.getProtocolName(), null);
        }

        // If the useExistingNode flag is set to TRUE we're done, none of the
        // sub-targets should have been added.
        //
        if (useExistingNode)
            return;

        // If SNMP info available send events for sub-targets
        //
        if (collector.hasSnmpCollection() && !collector.getSnmpCollector().failed()) {
            Map extraTargets = collector.getAdditionalTargets();
            Iterator iter = extraTargets.keySet().iterator();
            while (iter.hasNext()) {
                InetAddress xifaddr = (InetAddress) iter.next();

                // nodeGainedInterface
                //
                createAndSendNodeGainedInterfaceEvent(node.getNodeId(), xifaddr);

                // nodeGainedService
                //
                List supportedProtocols = (List) extraTargets.get(xifaddr);
                log.debug("interface " + xifaddr + " supports " + supportedProtocols.size() + " protocols.");
                if (supportedProtocols != null) {
                    iproto = supportedProtocols.iterator();
                    while (iproto.hasNext()) {
                        IfCollector.SupportedProtocol p = (IfCollector.SupportedProtocol) iproto.next();
                        createAndSendNodeGainedServiceEvent(node, xifaddr, p.getProtocolName(), null);
                    }
                }
            }
        }
    }

    /**
     * This method is responsible for creating and sending a 'nodeAdded' event
     * to Eventd
     * 
     * @param nodeEntry
     *            DbNodeEntry object for the newly created node.
     */
    private void createAndSendNodeAddedEvent(DbNodeEntry nodeEntry) {
        Category log = ThreadCategory.getInstance(getClass());

        // create the event to be sent
        Event newEvent = new Event();

        newEvent.setUei(EventConstants.NODE_ADDED_EVENT_UEI);

        newEvent.setSource("OpenNMS.Capsd");

        newEvent.setNodeid(nodeEntry.getNodeId());

        newEvent.setHost(Capsd.getLocalHostAddress());

        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Add appropriate parms
        Parms eventParms = new Parms();
        Parm eventParm = null;
        Value parmValue = null;

        // Add node label
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_NODE_LABEL);
        parmValue = new Value();
        parmValue.setContent(nodeEntry.getLabel());
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add node label source
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_NODE_LABEL_SOURCE);
        parmValue = new Value();
        char labelSource[] = new char[] { nodeEntry.getLabelSource() };
        parmValue.setContent(new String(labelSource));
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add discovery method
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_METHOD);
        parmValue = new Value();
        parmValue.setContent("icmp");
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add Parms to the event
        newEvent.setParms(eventParms);

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendNodeAddedEvent: successfully sent NodeAdded event for nodeID: " + nodeEntry.getNodeId());
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }

    }

    /**
     * This method is responsible for creating and sending a
     * 'duplicateIPAddress' event to Eventd
     * 
     * @param nodeId
     *            Interface's parent node identifier.
     * @param ipAddr
     *            Interface's IP address
     */
    private void createAndSendDuplicateIpaddressEvent(int nodeId, String ipAddr) {
        Category log = ThreadCategory.getInstance(getClass());

        // create the event to be sent
        Event newEvent = new Event();

        newEvent.setUei(EventConstants.DUPLICATE_IPINTERFACE_EVENT_UEI);
        newEvent.setSource("OpenNMS.Capsd");
        newEvent.setNodeid(nodeId);
        newEvent.setHost(Capsd.getLocalHostAddress());
        newEvent.setInterface(ipAddr);
        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Add appropriate parms
        Parms eventParms = new Parms();
        Parm eventParm = null;
        Value parmValue = null;

        // Add IP host name
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_IP_HOSTNAME);
        parmValue = new Value();
        String hostName = null;
        try {
            hostName = InetAddress.getByName(ipAddr).getHostName();
        } catch (UnknownHostException ue) {
            hostName = "";
        }
        parmValue.setContent(hostName);
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add discovery method
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_METHOD);
        parmValue = new Value();
        parmValue.setContent("icmp");
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add Parms to the event
        newEvent.setParms(eventParms);

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendDuplicateIpaddressEvent: successfully sent duplicateIPAddress event for interface: " + ipAddr);
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }
    }

    /**
     * This method is responsible for creating and sending a
     * 'nodeGainedInterface' event to Eventd
     * 
     * @param nodeId
     *            Interface's parent node identifier.
     * @param ipAddr
     *            Interface's IP address
     */
    private void createAndSendNodeGainedInterfaceEvent(int nodeId, InetAddress ipAddr) {
        Category log = ThreadCategory.getInstance(getClass());

        // create the event to be sent
        Event newEvent = new Event();

        newEvent.setUei(EventConstants.NODE_GAINED_INTERFACE_EVENT_UEI);

        newEvent.setSource("OpenNMS.Capsd");

        newEvent.setNodeid(nodeId);

        newEvent.setHost(Capsd.getLocalHostAddress());

        newEvent.setInterface(ipAddr.getHostAddress());

        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Add appropriate parms
        Parms eventParms = new Parms();
        Parm eventParm = null;
        Value parmValue = null;

        // Add IP host name
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_IP_HOSTNAME);
        parmValue = new Value();
        parmValue.setContent(ipAddr.getHostName());
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add discovery method
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_METHOD);
        parmValue = new Value();
        parmValue.setContent("icmp");
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add Parms to the event
        newEvent.setParms(eventParms);

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendNodeGainedInterfaceEvent: successfully sent NodeGainedInterface event for interface: " + ipAddr.getHostAddress());
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }
    }

    /**
     * This method is responsible for creating and sending a 'nodeGainedService'
     * event to Eventd
     * 
     * @param nodeEntry
     *            Interface's parent node identifier.
     * @param ipAddr
     *            Interface's IP address
     * @param svcName
     *            Service name
     * @param qualifier
     *            Service qualifier (typically the port on which the service was
     *            found)
     */
    private void createAndSendNodeGainedServiceEvent(DbNodeEntry nodeEntry, InetAddress ipAddr, String svcName, String qualifier) {
        Category log = ThreadCategory.getInstance(getClass());

        // create the event to be sent
        Event newEvent = new Event();

        newEvent.setUei(EventConstants.NODE_GAINED_SERVICE_EVENT_UEI);

        newEvent.setSource("OpenNMS.Capsd");

        newEvent.setNodeid(nodeEntry.getNodeId());

        newEvent.setHost(Capsd.getLocalHostAddress());

        newEvent.setInterface(ipAddr.getHostAddress());

        newEvent.setService(svcName);

        newEvent.setTime(EventConstants.formatToString(new java.util.Date()));

        // Add appropriate parms
        Parms eventParms = new Parms();
        Parm eventParm = null;
        Value parmValue = null;

        // Add IP host name
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_IP_HOSTNAME);
        parmValue = new Value();
        parmValue.setContent(ipAddr.getHostName());
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add node label
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_NODE_LABEL);
        parmValue = new Value();
        parmValue.setContent(nodeEntry.getLabel());
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add node label source
        eventParm = new Parm();
        eventParm.setParmName(EventConstants.PARM_NODE_LABEL_SOURCE);
        parmValue = new Value();
        char labelSource[] = new char[] { nodeEntry.getLabelSource() };
        parmValue.setContent(new String(labelSource));
        eventParm.setValue(parmValue);
        eventParms.addParm(eventParm);

        // Add qualifier (if available)
        if (qualifier != null && qualifier.length() > 0) {
            eventParm = new Parm();
            eventParm.setParmName(EventConstants.PARM_QUALIFIER);
            parmValue = new Value();
            parmValue.setContent(qualifier);
            eventParm.setValue(parmValue);
            eventParms.addParm(eventParm);
        }

        // Add sysName (if available)
        if (nodeEntry.getSystemName() != null) {
            eventParm = new Parm();
            eventParm.setParmName(EventConstants.PARM_NODE_SYSNAME);
            parmValue = new Value();
            parmValue.setContent(nodeEntry.getSystemName());
            eventParm.setValue(parmValue);
            eventParms.addParm(eventParm);
        }

        // Add sysDescr (if available)
        if (nodeEntry.getSystemDescription() != null) {
            eventParm = new Parm();
            eventParm.setParmName(EventConstants.PARM_NODE_SYSDESCRIPTION);
            parmValue = new Value();
            parmValue.setContent(nodeEntry.getSystemDescription());
            eventParm.setValue(parmValue);
            eventParms.addParm(eventParm);
        }

        // Add Parms to the event
        newEvent.setParms(eventParms);

        // Send event to Eventd
        try {
            EventIpcManagerFactory.getInstance().getManager().sendNow(newEvent);

            if (log.isDebugEnabled())
                log.debug("createAndSendNodeGainedServiceEvent: successfully sent NodeGainedService event for interface: " + ipAddr.getHostAddress() + " and service: " + svcName);
        } catch (Throwable t) {
            log.warn("run: unexpected throwable exception caught during send to middleware", t);
        }
    }

} // end class
