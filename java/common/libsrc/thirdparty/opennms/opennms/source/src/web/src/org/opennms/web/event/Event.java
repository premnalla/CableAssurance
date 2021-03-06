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

package org.opennms.web.event;

import java.util.Date;

/**
 * A JavaBean implementation to hold information about a network event as
 * defined by OpenNMS.
 * 
 * @author <A HREF="mailto:larry@opennms.org">Lawrence Karnowski </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class Event extends Object {
    public static final int INDETERMINATE_SEVERITY = 1;

    public static final int CLEARED_SEVERITY = 2;

    public static final int NORMAL_SEVERITY = 3;

    public static final int WARNING_SEVERITY = 4;

    public static final int MINOR_SEVERITY = 5;

    public static final int MAJOR_SEVERITY = 6;

    public static final int CRITICAL_SEVERITY = 7;

    /** Unique identifier for the event, cannot be null */
    protected int id;

    /** Universal Event Identifer (UEI) for this event, cannot be null */
    protected String uei;

    /**
     * Contains the eid, eidtext (optionally), specific, and generic identifier
     * for the SNMP Trap. This maps directly to the &lt;snmp&gt;element in the
     * Event Data Stream DTD.
     */
    protected String snmp;

    /**
     * The &lt;time&gt; element from the Event Data Stream DTD, which is the
     * time the event was received by the source process. Cannot be null.
     */
    protected Date time;

    /** The &lt;host&gt; element from the Event Data Stream DTD */
    protected String host;

    /** The &lt;snmphost&gt; element from the Event Data Stream DTD */
    protected String snmphost;

    /** The dpName of the Dist Poller which received the event, cannot be null. */
    protected String dpName;

    /** The &lt;parms&gt; element from the Event Data Stream DTD */
    protected String parms;

    /** Unique integer identifier for node, can be null */
    protected Integer nodeID;

    /** Unique integer identifier of service/poller package, can be null */
    protected Integer serviceID;

    /** Human-readable name of the service */
    protected String serviceName;

    /** IP Address of node's interface */
    protected String ipAddr;

    /** Creation time of event in database, cannot be null */
    protected Date createTime;

    /** Free-form textual description of the event */
    protected String description;

    /**
     * Formatted display text to control how the event will appear in the
     * browser. This field may contain variables that are populated by field
     * values of the message.
     */
    protected String logMessage;

    /**
     * Logical group with which to associate event. This field provides a means
     * of logically grouping related events.
     */
    protected String logGroup;

    /**
     * Severity the of event.
     * 
     * <pre>
     * 
     *   1  = Indeterminate
     *   2 = Cleared (unimplemented at this time)
     *   3 = Warning
     *   4 = Minor
     *   5 = Major
     *   6 = Critical
     *  
     * </pre>
     */
    protected int severity;

    /** Operator instruction for event. */
    protected String operatorInstruction;

    /**
     * Automated Action for event. Should consist of fully-qualfied pathname to
     * executable command, with possible variables used to reference
     * event-specific data
     */
    protected String autoAction;

    /**
     * Operator Action for event. Should consist of fully-qualfied pathname to
     * executable command, with possible variables used to reference
     * event-specific data
     */
    protected String operatorAction;

    /**
     * Text of the eventOperAction. Menu text displayed to Operator, which if
     * selected, will invoke action described in eventOperAction.
     */
    protected String operatorActionMenuText;

    /**
     * Notification string. Should consist of a fully-qualfied pathname to an
     * executable which invokes the notification software, and will likely
     * contain event-specific variables
     */
    protected String notification;

    /**
     * Trouble ticket integration string. Should consist of fully-qualfied
     * pathname to executable command, with possible variables used to reference
     * event-specific data
     */
    protected String troubleTicket;

    /**
     * State of the trouble ticket. Trouble ticket on/off boolean 1=on, 0=off.
     * Can be null.
     */
    protected Integer troubleTicketState;

    /**
     * The forwarding information. Contains a list of triplets:
     * <code>Destination,State,Mechanism;Destination,State,Mechanism;</code>
     * which reflect the following:
     * <ul>
     * <li>State is a boolean flag as to whether the entry is active or not.
     * 1=on, 0=off.
     * <li>Destination is hostname or IP of system to forward the event to
     * <li>Method is the means by which it will be forwarded. A keyword, e.g.,
     * SNMP
     * </ul>
     */
    protected String forward;

    /**
     * Mouse over text. Text to be displayed on MouseOver event, if the event is
     * displayed in the browser and the operator needs additional info.
     */
    protected String mouseOverText;

    /** The name of the user who acknowledged this event. */
    protected String acknowledgeUser;

    /** The time this event was acknowledged. */
    protected Date acknowledgeTime;

    /** The human-readable name of the node of this event. Can be null. */
    protected String nodeLabel;

    /**
     * Empty constructor to create an empty <code>Event</code> instance. All
     * fields will hold the default values.
     */
    public Event() {
    }

    /**
     * Create an event that represents a real network event with only the
     * required parameters.
     */
    public Event(int id, String uei, Date time, String dpName, Date createTime, int severity) {
        if (uei == null || time == null || dpName == null || createTime == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        this.id = id;
        this.uei = uei;
        this.time = time;
        this.dpName = dpName;
        this.createTime = createTime;
        this.severity = severity;
    }

    /**
     * Create an event that represents a real network event with all the
     * parameters.
     */
    public Event(int id, String uei, Date time, String dpName, Date createTime, int severity, String snmp, String host, String snmphost, String parms, Integer nodeID, Integer serviceID, String ipAddr, String description, String logMessage, String logGroup, String operatorInstruction, String autoAction, String operatorAction, String operatorActionMenuText, String notification, String troubleTicket, Integer troubleTicketState, String forward, String mouseOverText, String acknowledgeUser, Date acknowledgeTime) {
        this(id, uei, time, dpName, createTime, severity, snmp, host, snmphost, parms, nodeID, serviceID, ipAddr, description, logMessage, logGroup, operatorInstruction, autoAction, operatorAction, operatorActionMenuText, notification, troubleTicket, troubleTicketState, forward, mouseOverText, acknowledgeUser, acknowledgeTime, null, null);
    }

    /**
     * Create an event that represents a real network event with all the
     * parameters.
     */
    public Event(int id, String uei, Date time, String dpName, Date createTime, int severity, String snmp, String host, String snmphost, String parms, Integer nodeID, Integer serviceID, String ipAddr, String description, String logMessage, String logGroup, String operatorInstruction, String autoAction, String operatorAction, String operatorActionMenuText, String notification, String troubleTicket, Integer troubleTicketState, String forward, String mouseOverText, String acknowledgeUser, Date acknowledgeTime, String nodeLabel, String serviceName) {

        if (uei == null || time == null || dpName == null || createTime == null) {
            throw new IllegalArgumentException("Cannot take null values for the following parameters: uei, time, dpName, createTime.");
        }

        // required fields
        this.id = id;
        this.uei = uei;
        this.time = time;
        this.dpName = dpName;
        this.createTime = createTime;
        this.severity = severity;

        // optional fields
        this.snmp = snmp;
        this.host = host;
        this.snmphost = snmphost;
        this.parms = parms;
        this.nodeID = nodeID;
        this.serviceID = serviceID;
        this.ipAddr = ipAddr;
        this.description = description;
        this.logMessage = logMessage;
        this.logGroup = logGroup;
        this.operatorInstruction = operatorInstruction;
        this.autoAction = autoAction;
        this.operatorAction = operatorAction;
        this.operatorActionMenuText = operatorActionMenuText;
        this.notification = notification;
        this.troubleTicket = troubleTicket;
        this.troubleTicketState = troubleTicketState;
        this.forward = forward;
        this.mouseOverText = mouseOverText;
        this.acknowledgeUser = acknowledgeUser;
        this.acknowledgeTime = acknowledgeTime;
        this.nodeLabel = nodeLabel;
        this.serviceName = serviceName;
    }

    public int getId() {
        return (this.id);
    }

    public String getUei() {
        return (this.uei);
    }

    public Date getTime() {
        return (time);
    }

    public String getDpName() {
        return (this.dpName);
    }

    public Date getCreateTime() {
        return (this.createTime);
    }

    public int getSeverity() {
        return (this.severity);
    }

    public String getSnmp() {
        return (this.snmp);
    }

    public String getHost() {
        return (this.host);
    }

    public String getSnmpHost() {
        return (this.snmphost);
    }

    public String getParms() {
        return (this.parms);
    }

    public int getNodeId() {
        return (this.nodeID.intValue());
    }

    public String getNodeLabel() {
        return (this.nodeLabel);
    }

    public int getServiceId() {
        return (this.serviceID.intValue());
    }

    public String getServiceName() {
        return (this.serviceName);
    }

    public String getIpAddress() {
        return (this.ipAddr);
    }

    public String getDescription() {
        return (this.description);
    }

    public String getLogMessage() {
        return (this.logMessage);
    }

    public String getLogGroup() {
        return (this.logGroup);
    }

    public String getOperatorInstruction() {
        return (this.operatorInstruction);
    }

    public String getAutoAction() {
        return (this.autoAction);
    }

    public String getOperatorAction() {
        return (this.operatorAction);
    }

    public String getOperatorActionMenuText() {
        return (this.operatorActionMenuText);
    }

    public String getNotification() {
        return (this.notification);
    }

    public String getTroubleTicket() {
        return (this.troubleTicket);
    }

    public Integer getTroubleTicketState() {
        return (this.troubleTicketState);
    }

    public String getForward() {
        return (this.forward);
    }

    public String getMouseOverText() {
        return (this.mouseOverText);
    }

    public String getAcknowledgeUser() {
        return (this.acknowledgeUser);
    }

    public Date getAcknowledgeTime() {
        return (this.acknowledgeTime);
    }

    public boolean isAcknowledged() {
        return (this.acknowledgeUser != null);
    }

}
