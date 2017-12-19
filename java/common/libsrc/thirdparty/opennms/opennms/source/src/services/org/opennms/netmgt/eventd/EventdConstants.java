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
// Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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

package org.opennms.netmgt.eventd;

/**
 * This class is a repository for constant, static information concerning
 * Eventd.
 * 
 * @author <A HREF="mailto:weave@oculan.com">Sowmya Nataraj </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS.org </A>
 */
public final class EventdConstants {
    /**
     * The SQL statement necessary to read service id and service name into map.
     */
    public final static String SQL_DB_SVC_TABLE_READ = "SELECT serviceID, serviceName FROM service";

    /**
     * The SQL insertion string used by eventd to store the event information
     * into the database.
     */
    public final static String SQL_DB_INS_EVENT = "INSERT into events (eventID, eventUei, nodeID, eventTime, eventHost, ipAddr, eventDpName, eventSnmpHost, serviceID, eventSnmp, eventParms, eventCreateTime, eventDescr, eventLoggroup, eventLogmsg, eventLog, eventDisplay, eventSeverity, eventPathOutage, eventCorrelation, eventSuppressedCount, eventOperInstruct, eventAutoAction, eventOperAction, eventOperActionMenuText, eventNotification, eventTticket, eventTticketState, eventForward, eventMouseOverText, eventAckUser, eventAckTime, eventSource) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The SQL string used by eventd to update number of duplicate events in
     * case of duplicate event suppression.
     */
    public final static String SQL_DB_UPDATE_EVENT_COUNT = "UPDATE events set eventSuppressedCount=? where (eventID=?)";

    /**
     * The SQL statement necessary to convert the service name into a service id
     * using the distributed poller database.
     */
    public final static String SQL_DB_SVCNAME_TO_SVCID = "SELECT serviceID FROM service WHERE serviceName = ?";

    /**
     * The SQL statement necessary to convert the event host into a hostname
     * using the 'ipinterface' table.
     */
    public final static String SQL_DB_HOSTIP_TO_HOSTNAME = "SELECT ipHostname FROM ipinterface WHERE ipAddr = ?";

}
