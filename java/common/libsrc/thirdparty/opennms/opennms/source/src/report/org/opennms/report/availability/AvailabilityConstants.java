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
// Tab Stop = 8
//

package org.opennms.report.availability;

/**
 * This class is a repository for constant, static information concerning the
 * Availability Reporter Module.
 * 
 * @author <A HREF="mailto:jacinta@oculan.com">Jacinta Remedios </A>
 * @author <A HREF="http://www.oculan.com">Oculan </A>
 * 
 */
public class AvailabilityConstants {
    /**
     * The sql statement that is used to get node information for an IP address.
     */
    public final static String DB_GET_INFO_FOR_IP = "SELECT  node.nodeid, node.nodelabel FROM " + "node, ipInterface WHERE ((ipInterface.ipaddr = ?) AND " + "(ipInterface.nodeid = node.nodeid) AND (node.nodeType = 'A') AND (ipinterface.ismanaged = 'M') )";

    /**
     * The sql statement that is used to get services information for a
     * nodeid/IP address.
     */
    public final static String DB_GET_SVC_ENTRIES = "SELECT ifServices.serviceid, service.servicename FROM ifServices, ipInterface, node, " + "service WHERE ((ifServices.nodeid = ? ) AND (ifServices.ipaddr = ?) AND ipinterface.ipaddr = ? AND ipinterface.isManaged ='M' AND " + "(ifServices.serviceid = service.serviceid) AND (ifservices.status = 'A')) AND node.nodeid = ? AND node.nodetype = 'A'";

    /**
     * The sql statement for getting outage entries for a nodeid/ip/serviceid
     */
    public final static String DB_GET_OUTAGE_ENTRIES = "SELECT ifLostService, ifRegainedService from outages " + "where  (outages.nodeid = ?) AND (outages.ipaddr = ?) AND (outages.serviceid = ?)"; // and
                                                                                                                                                                                                        // ((ifRegainedService
                                                                                                                                                                                                        // IS
                                                                                                                                                                                                        // null)
                                                                                                                                                                                                        // OR
                                                                                                                                                                                                        // (ifRegainedService
                                                                                                                                                                                                        // IS
                                                                                                                                                                                                        // NOT
                                                                                                                                                                                                        // NULL
                                                                                                                                                                                                        // AND
                                                                                                                                                                                                        // ifRegainedService
                                                                                                                                                                                                        // > ?)
                                                                                                                                                                                                        // )";

    /**
     * The list of Availability Report Constants that are needed to display
     * appropriate messages on the report.
     */
    public final static String LAST_30_DAYS_DAILY_LABEL = "The last 30 Days Daily Availability";

    public final static String LAST_30_DAYS_DAILY_DESCR = "Daily average of svcs and dvcs monitored and their availability divided by total mins for 30days";

    public final static String LAST_30_DAYS_TOTAL_LABEL = "The last 30 Days Total Availability";

    public final static String LAST_30_DAYS_TOTAL_DESCR = "Average of svcs monitored and availability of svcs divided by total svc minutes of the last 30 days";

    public final static String LAST_30_DAYS_SVC_AVAIL_LABEL = "The last 30 days Daily Service Availability";

    public final static String LAST_30_DAYS_SVC_AVAIL_DESCR = "The last 30 days Daily Service Availability is the daily average of services";

    public final static String LAST_MONTH_SVC_AVAIL_LABEL = "The last Months Daily Service Availability";

    public final static String LAST_MONTH_SVC_AVAIL_DESCR = "The last Months Daily Service Availability is the daily average of services and devices";

    public final static String LAST_MTD_DAILY_LABEL = "Month To Date Daily Availability";

    public final static String LAST_MTD_DAILY_DESCR = "Daily Average of svc monitored and availability of svcs div by total svc minutes of month frm 1st till date";

    public final static String LAST_MTD_TOTAL_LABEL = "Month To Date Total Availability";

    public final static String LAST_MTD_TOTAL_DESCR = "Average of svc monitored and availability of svcs dividedby total svc minutes of month frm 1st till date";

    public final static String LAST_MONTH_DAILY_LABEL = "The last Months Daily Availability";

    public final static String LAST_MONTH_DAILY_DESCR = "Daily Average of svcs monitored and availability of svcs divided by the total svc minutes (last month)";

    public final static String LAST_MONTH_TOTAL_LABEL = "The last Months Total Availability";

    public final static String LAST_MONTH_TOTAL_DESCR = "Average of svcs monitored and availability of svcs divided by the total svc minutes of the month";

    public final static String NOFFENDERS_LABEL = "Last Months Top Offenders";

    public final static String NOFFENDERS_DESCR = "This is the list of the worst available devices in the category for the last month";

    public final static String TOP20_SVC_OUTAGES_LABEL = "Last Month Top Service Outages for";

    public final static String TOP20_SVC_OUTAGES_DESCR = "Last Month Top Service Outages for";

    public final static String NMONTH_TOTAL_LABEL = "The last 12 Months Availability";

    public final static String NMONTH_TOTAL_DESCR = "The last 12 Months Availability";

}
