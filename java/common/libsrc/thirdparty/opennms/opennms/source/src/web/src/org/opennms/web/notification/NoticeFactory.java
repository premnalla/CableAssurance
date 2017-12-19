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

package org.opennms.web.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import org.opennms.core.resource.Vault;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.web.element.NetworkElementFactory;

/**
 * Encapsulates all querying functionality for notices
 * 
 * @author <A HREF="mailto:larry@opennms.org">Lawrence Karnowski </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class NoticeFactory extends Object {

    /** Convenience class to determine sort style of a query. */
    public static class SortStyle extends Object {
        /* CORBA-style enumeration */
        public static final int _USER = 1;

        public static final int _RESPONDER = 2;

        public static final int _PAGETIME = 3;

        public static final int _RESPONDTIME = 4;

        public static final int _NODE = 5;

        public static final int _INTERFACE = 6;

        public static final int _SERVICE = 7;

        public static final int _ID = 8;

        public static final SortStyle USER = new SortStyle("USER", _USER);

        public static final SortStyle RESPONDER = new SortStyle("RESPONDER", _RESPONDER);

        public static final SortStyle PAGETIME = new SortStyle("PAGETIME", _PAGETIME);

        public static final SortStyle RESPONDTIME = new SortStyle("RESPONDTIME", _RESPONDTIME);

        public static final SortStyle NODE = new SortStyle("NODE", _NODE);

        public static final SortStyle INTERFACE = new SortStyle("INTERFACE", _INTERFACE);

        public static final SortStyle SERVICE = new SortStyle("SERVICE", _SERVICE);

        public static final SortStyle ID = new SortStyle("ID", _ID);

        public static final int _REVERSE_USER = 101;

        public static final int _REVERSE_RESPONDER = 102;

        public static final int _REVERSE_PAGETIME = 103;

        public static final int _REVERSE_RESPONDTIME = 104;

        public static final int _REVERSE_NODE = 105;

        public static final int _REVERSE_INTERFACE = 106;

        public static final int _REVERSE_SERVICE = 107;

        public static final int _REVERSE_ID = 108;

        public static final SortStyle REVERSE_USER = new SortStyle("REVERSE_USER", _REVERSE_USER);

        public static final SortStyle REVERSE_RESPONDER = new SortStyle("REVERSE_RESPONDER", _REVERSE_RESPONDER);

        public static final SortStyle REVERSE_PAGETIME = new SortStyle("REVERSE_PAGETIME", _REVERSE_PAGETIME);

        public static final SortStyle REVERSE_RESPONDTIME = new SortStyle("REVERSE_RESPONDTIME", _REVERSE_RESPONDTIME);

        public static final SortStyle REVERSE_NODE = new SortStyle("REVERSE_NODE", _REVERSE_NODE);

        public static final SortStyle REVERSE_INTERFACE = new SortStyle("REVERSE_INTERFACE", _REVERSE_INTERFACE);

        public static final SortStyle REVERSE_SERVICE = new SortStyle("REVERSE_SERVICE", _REVERSE_SERVICE);

        public static final SortStyle REVERSE_ID = new SortStyle("REVERSE_ID", _REVERSE_ID);

        protected String name;

        protected int id;

        private SortStyle(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String toString() {
            return ("Notice.SortStyle." + this.name);
        }

        public String getName() {
            return (this.name);
        }

        public int getId() {
            return (this.id);
        }
    }

    /**
     * Convenience class to determine what sort of notices to include in a
     * query.
     */
    public static class AcknowledgeType extends Object {
        /* CORBA-style enumeration */
        public static final int _ACKNOWLEDGED = 1;

        public static final int _UNACKNOWLEDGED = 2;

        public static final int _BOTH = 3;

        public static final AcknowledgeType ACKNOWLEDGED = new AcknowledgeType("ACKNOWLEDGED", _ACKNOWLEDGED);

        public static final AcknowledgeType UNACKNOWLEDGED = new AcknowledgeType("UNACKNOWLEDGED", _UNACKNOWLEDGED);

        public static final AcknowledgeType BOTH = new AcknowledgeType("BOTH", _BOTH);

        protected String name;

        protected int id;

        private AcknowledgeType(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String toString() {
            return ("Notice.AcknowledgeType." + this.name);
        }

        public String getName() {
            return (this.name);
        }

        public int getId() {
            return (this.id);
        }
    }

    /**
     * Convenience class to determine what sort of notices to include in a
     * query.
     */
    public interface Filter {
        public String getSql();

        public String getDescription();

        public String getTextDescription();
    }

    /** Encapsulates all user filtering functionality. */
    public static class UserFilter extends Object implements Filter {
        public static final String TYPE = "user";

        protected String user;

        public UserFilter(String user) {
            this.user = user;
        }

        public String getSql() {
            return (" notifications.notifyid in (SELECT DISTINCT usersnotified.notifyid FROM usersnotified WHERE usersnotified.userid='" + this.user + "')");
        }

        public String getDescription() {
            return (TYPE + "=" + this.user);
        }

        public String getTextDescription() {
            return this.getDescription();
        }

        public String toString() {
            return ("<NoticeFactory.UserFilter: " + this.getDescription() + ">");
        }

        public String getUser() {
            return (this.user);
        }

        public boolean equals(Object obj) {
            return (this.toString().equals(obj.toString()));
        }
    }

    /** Encapsulates all responder filtering functionality. */
    public static class ResponderFilter extends Object implements Filter {
        public static final String TYPE = "responder";

        protected String responder;

        public ResponderFilter(String responder) {
            this.responder = responder;
        }

        public String getSql() {
            return (" ANSWEREDBY='" + this.responder + "'");
        }

        public String getDescription() {
            return (TYPE + "=" + this.responder);
        }

        public String getTextDescription() {
            return this.getDescription();
        }

        public String toString() {
            return ("<NoticeFactory.ResponderFilter: " + this.getDescription() + ">");
        }

        public String getResponder() {
            return (this.responder);
        }

        public boolean equals(Object obj) {
            return (this.toString().equals(obj.toString()));
        }
    }

    /** Encapsulates all node filtering functionality. */
    public static class NodeFilter extends Object implements Filter {
        public static final String TYPE = "node";

        protected int nodeId;

        public NodeFilter(int nodeId) {
            this.nodeId = nodeId;
        }

        public String getSql() {
            return (" NODEID=" + this.nodeId);
        }

        public String getDescription() {
            return (TYPE + "=" + this.nodeId);
        }

        public String getTextDescription() {
            String nodeName = Integer.toString(this.nodeId);
            try {
                nodeName = NetworkElementFactory.getNodeLabel(this.nodeId);
            } catch (SQLException e) {
            }

            return (TYPE + "=" + nodeName);
        }

        public String toString() {
            return ("<NoticeFactory.NodeFilter: " + this.getDescription() + ">");
        }

        public int getNodeId() {
            return (this.nodeId);
        }

        public boolean equals(Object obj) {
            return (this.toString().equals(obj.toString()));
        }
    }

    /** Encapsulates all interface filtering functionality. */
    public static class InterfaceFilter extends Object implements Filter {
        public static final String TYPE = "interface";

        protected String ipAddress;

        public InterfaceFilter(String ipAddress) {
            if (ipAddress == null) {
                throw new IllegalArgumentException("Cannot take null parameters.");
            }

            this.ipAddress = ipAddress;
        }

        public String getSql() {
            return (" INTERFACEID='" + this.ipAddress + "'");
        }

        public String getDescription() {
            return (TYPE + "=" + this.ipAddress);
        }

        public String getTextDescription() {
            return this.getDescription();
        }

        public String toString() {
            return ("<NoticeFactory.InterfaceFilter: " + this.getDescription() + ">");
        }

        public String getIpAddress() {
            return (this.ipAddress);
        }

        public boolean equals(Object obj) {
            return (this.toString().equals(obj.toString()));
        }
    }

    /** Encapsulates all service filtering functionality. */
    public static class ServiceFilter extends Object implements Filter {
        public static final String TYPE = "service";

        protected int serviceId;

        public ServiceFilter(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getSql() {
            return (" SERVICEID=" + this.serviceId);
        }

        public String getDescription() {
            return (TYPE + "=" + this.serviceId);
        }

        public String getTextDescription() {
            String serviceName = Integer.toString(this.serviceId);
            try {
                serviceName = NetworkElementFactory.getServiceNameFromId(this.serviceId);
            } catch (SQLException e) {
            }

            return (TYPE + "=" + serviceName);
        }

        public String toString() {
            return ("<NoticeFactory.ServiceFilter: " + this.getDescription() + ">");
        }

        public int getServiceId() {
            return (this.serviceId);
        }

        public boolean equals(Object obj) {
            return (this.toString().equals(obj.toString()));
        }
    }

    /** Private constructor so this class cannot be instantiated. */
    private NoticeFactory() {
    }

    /**
     * Count the number of notices for a given acknowledgement type.
     */
    public static int getNoticeCount(AcknowledgeType ackType, Filter[] filters) throws SQLException {
        if (ackType == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        int noticeCount = 0;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT COUNT(*) AS NOTICECOUNT FROM NOTIFICATIONS WHERE");
            select.append(getAcknowledgeTypeClause(ackType));

            for (int i = 0; i < filters.length; i++) {
                select.append(" AND");
                select.append(filters[i].getSql());
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select.toString());

            if (rs.next()) {
                noticeCount = rs.getInt("NOTICECOUNT");
            }

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return noticeCount;
    }

    /** Return a specific notice. */
    public static Notification getNotice(int noticeId) throws SQLException {
        Notification notice = null;
        Connection conn = Vault.getDbConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM NOTIFICATION WHERE NOTIFYID=?");
            stmt.setInt(1, noticeId);
            ResultSet rs = stmt.executeQuery();

            Notification[] notices = rs2Notices(rs);

            // what do I do if this actually returns more than one service?
            if (notices.length > 0) {
                notice = notices[0];
            }

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notice;
    }

    /**
     * This method determines the log status of an event associated with a
     * notification
     * 
     * @param eventId
     *            the unique id of the event from the notice
     * @return true if the event is display, false if log only
     */
    public static boolean canDisplayEvent(int eventId) {
        boolean display = false;

        Connection connection = null;
        try {
            connection = Vault.getDbConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT eventDisplay FROM events WHERE eventid=?");

            statement.setInt(1, eventId);

            ResultSet results = statement.executeQuery();

            results.next();
            String status = results.getString(1);

            if (status.equals("Y")) {
                display = true;
            }

            statement.close();
            results.close();
        } catch (SQLException e) {
            ThreadCategory.getInstance(NoticeFactory.class.getName()).error("Error getting event display status: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    Vault.releaseDbConnection(connection);
                } catch (SQLException e) {
                }
            }
        }

        return display;
    }

    /** Return all unacknowledged notices sorted by id. */
    public static Notification[] getNotices() throws SQLException {
        return (NoticeFactory.getNotices(SortStyle.ID, AcknowledgeType.UNACKNOWLEDGED));
    }

    /** Return all unacknowledged or acknowledged notices sorted by id. */
    public static Notification[] getNotices(AcknowledgeType ackType) throws SQLException {
        return (NoticeFactory.getNotices(SortStyle.ID, ackType));
    }

    /** Return all unacknowledged notices sorted by the given sort style. */
    public static Notification[] getNotices(SortStyle sortStyle) throws SQLException {
        return (NoticeFactory.getNotices(sortStyle, AcknowledgeType.UNACKNOWLEDGED));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by the
     * given sort style.
     * 
     * @deprecated Replaced by
     *             {@link " #getNotices(SortStyle,AcknowledgeType) getNotices( SortStyle, AcknowledgeType )"}
     */
    public static Notification[] getNotices(SortStyle sortStyle, boolean includeAcknowledged) throws SQLException {
        AcknowledgeType ackType = (includeAcknowledged) ? AcknowledgeType.BOTH : AcknowledgeType.UNACKNOWLEDGED;
        return (NoticeFactory.getNotices(sortStyle, ackType));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by the
     * given sort style.
     */
    public static Notification[] getNotices(SortStyle sortStyle, AcknowledgeType ackType) throws SQLException {
        return (NoticeFactory.getNotices(sortStyle, ackType, new Filter[0]));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by the
     * given sort style.
     */
    public static Notification[] getNotices(SortStyle sortStyle, AcknowledgeType ackType, Filter[] filters) throws SQLException {
        return (NoticeFactory.getNotices(sortStyle, ackType, filters, -1, -1));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by the
     * given sort style.
     * 
     * <p>
     * <strong>Note: </strong> This limit/offset code is <em>Postgres 
     * specific!</em>
     * Per <a href="mailto:shaneo@opennms.org">Shane </a>, this is okay for now
     * until we can come up with an Oracle alternative too.
     * </p>
     * 
     * @param limit
     *            if -1 or zero, no limit or offset is used
     * @param offset
     *            if -1, no limit or offset if used
     */
    public static Notification[] getNotices(SortStyle sortStyle, AcknowledgeType ackType, Filter[] filters, int limit, int offset) throws SQLException {
        if (sortStyle == null || ackType == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        boolean useLimits = false;
        if (limit > 0 && offset > -1) {
            useLimits = true;
        }

        Notification[] notices = null;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT * FROM NOTIFICATIONS WHERE");
            select.append(getAcknowledgeTypeClause(ackType));

            for (int i = 0; i < filters.length; i++) {
                select.append(" AND");
                select.append(filters[i].getSql());
            }

            select.append(getOrderByClause(sortStyle));

            if (useLimits) {
                select.append(" LIMIT ");
                select.append(limit);
                select.append(" OFFSET ");
                select.append(offset);
            }
            System.out.println(select.toString());

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select.toString());

            notices = rs2Notices(rs);

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notices;
    }

    /** Return all unacknowledged notices sorted by time for the given node. */
    public static Notification[] getNoticesForNode(int nodeId) throws SQLException {
        return (getNoticesForNode(nodeId, SortStyle.ID, AcknowledgeType.UNACKNOWLEDGED));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by id
     * for the given node.
     * 
     * @deprecated Replaced by
     *             {@link " #getNoticesForNode(int,SortStyle,AcknowledgeType) getNoticesForNode( int, SortStyle, AcknowledgeType )"}
     */
    public static Notification[] getNoticesForNode(int nodeId, boolean includeAcknowledged) throws SQLException {
        AcknowledgeType ackType = (includeAcknowledged) ? AcknowledgeType.BOTH : AcknowledgeType.UNACKNOWLEDGED;
        return (getNoticesForNode(nodeId, SortStyle.ID, ackType));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by
     * given sort style for the given node.
     */
    public static Notification[] getNoticesForNode(int nodeId, SortStyle sortStyle, AcknowledgeType ackType) throws SQLException {
        if (sortStyle == null || ackType == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        Filter[] filters = new Filter[] { new NodeFilter(nodeId) };
        return (NoticeFactory.getNotices(sortStyle, ackType, filters));
    }

    /** Return all unacknowledged notices for the given interface. */
    public static Notification[] getNoticesForInterface(int nodeId, String ipAddress) throws SQLException {
        return (getNoticesForInterface(nodeId, ipAddress, false));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by id
     * for the given interface.
     */
    public static Notification[] getNoticesForInterface(int nodeId, String ipAddress, boolean includeAcknowledged) throws SQLException {
        if (ipAddress == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        Notification[] notices = null;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT * FROM NOTIFICATIONS WHERE NODEID=? AND INTERFACEID=?");

            if (!includeAcknowledged) {
                select.append(" AND RESPONDTIME IS NULL");
            }

            select.append(" ORDER BY NOTIFYID DESC");

            PreparedStatement stmt = conn.prepareStatement(select.toString());
            stmt.setInt(1, nodeId);
            stmt.setString(2, ipAddress);
            ResultSet rs = stmt.executeQuery();

            notices = rs2Notices(rs);

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notices;
    }

    /**
     * Return all unacknowledged notices sorted by time for that have the given
     * IP address, regardless of what node they belong to.
     */
    public static Notification[] getNoticesForInterface(String ipAddress) throws SQLException {
        return (getNoticesForInterface(ipAddress, false));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by id
     * that have the given IP address, regardless of what node they belong to.
     */
    public static Notification[] getNoticesForInterface(String ipAddress, boolean includeAcknowledged) throws SQLException {
        if (ipAddress == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        Notification[] notices = null;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT * FROM NOTIFICATIONS WHERE INTERFACEID=?");

            if (!includeAcknowledged) {
                select.append(" AND RESPONDTIME IS NULL");
            }

            select.append(" ORDER BY NOTIFYID DESC");

            PreparedStatement stmt = conn.prepareStatement(select.toString());
            stmt.setString(1, ipAddress);
            ResultSet rs = stmt.executeQuery();

            notices = rs2Notices(rs);

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notices;
    }

    /** Return all unacknowledged notices sorted by time for the given service. */
    public static Notification[] getNoticesForService(int nodeId, String ipAddress, int serviceId) throws SQLException {
        return (getNoticesForService(nodeId, ipAddress, serviceId, false));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by
     * time for the given service.
     */
    public static Notification[] getNoticesForService(int nodeId, String ipAddress, int serviceId, boolean includeAcknowledged) throws SQLException {
        if (ipAddress == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        Notification[] notices = null;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT * FROM NOTIFICATIONS WHERE NODEID=? AND INTERFACEID=? AND SERVICEID=?");

            if (!includeAcknowledged) {
                select.append(" AND RESPONDTIME IS NULL");
            }

            select.append(" ORDER BY NOTIFYID DESC");

            PreparedStatement stmt = conn.prepareStatement(select.toString());
            stmt.setInt(1, nodeId);
            stmt.setString(2, ipAddress);
            stmt.setInt(3, serviceId);
            ResultSet rs = stmt.executeQuery();

            notices = rs2Notices(rs);

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notices;
    }

    /**
     * Return all unacknowledged notices sorted by time for the given service
     * type, regardless of what node or interface they belong to.
     */
    public static Notification[] getNoticesForService(int serviceId) throws SQLException {
        return (getNoticesForService(serviceId, false));
    }

    /**
     * Return all notices (optionally only unacknowledged notices) sorted by id
     * for the given service type, regardless of what node or interface they
     * belong to.
     */
    public static Notification[] getNoticesForService(int serviceId, boolean includeAcknowledged) throws SQLException {
        Notification[] notices = null;
        Connection conn = Vault.getDbConnection();

        try {
            StringBuffer select = new StringBuffer("SELECT * FROM NOTIFICATION WHERE SERVICEID=?");

            if (!includeAcknowledged) {
                select.append(" AND RESPONDTIME IS NULL");
            }

            select.append(" ORDER BY NOTIFIYID DESC");

            PreparedStatement stmt = conn.prepareStatement(select.toString());
            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            notices = rs2Notices(rs);

            rs.close();
            stmt.close();
        } finally {
            Vault.releaseDbConnection(conn);
        }

        return notices;
    }

    /**
     * Acknowledge a list of notices with the given username
     */
    public static void acknowledge(Notification[] notices, String user) throws SQLException {
        acknowledge(notices, user, new Date());
    }

    /**
     * Acknowledge a list of notices with the given username and the given time.
     */
    public static void acknowledge(Notification[] notices, String user, Date time) throws SQLException {
        if (notices == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        int[] ids = new int[notices.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = notices[i].getId();
        }

        acknowledge(ids, user, time);
    }

    /**
     * Acknowledge a list of notices with the given username and the current
     * time.
     */
    public static void acknowledge(int[] noticeIds, String user) throws SQLException {
        acknowledge(noticeIds, user, new Date());
    }

    /**
     * Acknowledge a list of notices with the given username and the given time.
     */
    public static void acknowledge(int[] noticeIds, String user, Date time) throws SQLException {
        if (noticeIds == null || user == null || time == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        if (noticeIds.length > 0) {
            StringBuffer update = new StringBuffer("UPDATE NOTIFICATIONS SET RESPONDTIME=?, ANSWEREDBY=?");
            update.append(" WHERE NOTIFYID IN (");
            update.append(noticeIds[0]);

            for (int i = 1; i < noticeIds.length; i++) {
                update.append(",");
                update.append(noticeIds[i]);
            }

            update.append(")");
            update.append(" AND RESPONDTIME IS NULL");

            Connection conn = Vault.getDbConnection();

            try {
                PreparedStatement stmt = conn.prepareStatement(update.toString());
                stmt.setTimestamp(1, new Timestamp(time.getTime()));
                stmt.setString(2, user);

                stmt.executeUpdate();
                stmt.close();
            } finally {
                Vault.releaseDbConnection(conn);
            }
        }
    }

    /**
     * Convenience method for translating a <code>java.sql.ResultSet</code>
     * containing notice information into an array of <code>Notification</code>
     * objects.
     */
    protected static Notification[] rs2Notices(ResultSet rs) throws SQLException {
        Notification[] notices = null;
        Vector vector = new Vector();

        while (rs.next()) {
            Notification notice = new Notification();

            Object element = new Integer(rs.getInt("notifyid"));
            notice.m_notifyID = ((Integer) element).intValue();

            element = rs.getTimestamp("pagetime");
            notice.m_timeSent = ((Timestamp) element).getTime();

            element = rs.getTimestamp("respondtime");
            if (element != null) {
                notice.m_timeReply = ((Timestamp) element).getTime();
            }

            element = rs.getString("textmsg");
            notice.m_txtMsg = (String) element;

            element = rs.getString("numericmsg");
            notice.m_numMsg = (String) element;

            element = rs.getString("answeredby");
            notice.m_responder = (String) element;

            element = new Integer(rs.getInt("nodeid"));
            notice.m_nodeID = ((Integer) element).intValue();

            element = rs.getString("interfaceid");
            notice.m_interfaceID = (String) element;

            element = new Integer(rs.getInt("eventid"));
            notice.m_eventId = ((Integer) element).intValue();

            element = new Integer(rs.getInt("serviceid"));
            if (element != null) {
                notice.m_serviceId = ((Integer) element).intValue();
                element = NetworkElementFactory.getServiceNameFromId(notice.m_serviceId);
                notice.m_serviceName = (String) element;
            }

            vector.addElement(notice);
        }

        notices = new Notification[vector.size()];

        for (int i = 0; i < notices.length; i++) {
            notices[i] = (Notification) vector.elementAt(i);
        }

        return notices;
    }

    /**
     * Convenience method for getting the SQL <em>ORDER BY</em> clause related
     * to a given sort style.
     */
    protected static String getOrderByClause(SortStyle sortStyle) {
        if (sortStyle == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        String clause = null;

        switch (sortStyle.getId()) {
        case SortStyle._USER:
            clause = " ORDER BY USERID DESC";
            break;

        case SortStyle._REVERSE_USER:
            clause = " ORDER BY USERID ASC";
            break;

        case SortStyle._RESPONDER:
            clause = " ORDER BY ANSWEREDBY DESC";
            break;

        case SortStyle._REVERSE_RESPONDER:
            clause = " ORDER BY ANSWEREDBY ASC";
            break;

        case SortStyle._PAGETIME:
            clause = " ORDER BY PAGETIME DESC";
            break;

        case SortStyle._REVERSE_PAGETIME:
            clause = " ORDER BY PAGETIME ASC";
            break;

        case SortStyle._RESPONDTIME:
            clause = " ORDER BY RESPONDTIME DESC";
            break;

        case SortStyle._REVERSE_RESPONDTIME:
            clause = " ORDER BY RESPONDTIME ASC";
            break;

        case SortStyle._NODE:
            clause = " ORDER BY NODEID ASC";
            break;

        case SortStyle._REVERSE_NODE:
            clause = " ORDER BY NODEID DESC";
            break;

        case SortStyle._INTERFACE:
            clause = " ORDER BY IPADDR ASC";
            break;

        case SortStyle._REVERSE_INTERFACE:
            clause = " ORDER BY IPADDR DESC";
            break;

        case SortStyle._SERVICE:
            clause = " ORDER BY SERVICEID ASC";
            break;

        case SortStyle._REVERSE_SERVICE:
            clause = " ORDER BY SERVICEID DESC";
            break;

        case SortStyle._ID:
            clause = " ORDER BY NOTIFYID DESC";
            break;

        case SortStyle._REVERSE_ID:
            clause = " ORDER BY NOTIFYID ASC";
            break;

        default:
            throw new IllegalArgumentException("Unknown NoticeFactory.SortStyle: " + sortStyle.getName());
        }

        return clause;
    }

    /**
     * Convenience method for getting the SQL <em>ORDER BY</em> clause related
     * to a given sort style.
     * 
     * @param ackType
     *            the acknowledge type to map to a clause
     */
    protected static String getAcknowledgeTypeClause(AcknowledgeType ackType) {
        if (ackType == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        String clause = null;

        switch (ackType.getId()) {
        case AcknowledgeType._ACKNOWLEDGED:
            clause = " RESPONDTIME IS NOT NULL";
            break;

        case AcknowledgeType._UNACKNOWLEDGED:
            clause = " RESPONDTIME IS NULL";
            break;

        case AcknowledgeType._BOTH:
            clause = "";
            break;

        default:
            throw new IllegalArgumentException("Unknown NoticeFactory.AcknowledgeType: " + ackType.getName());
        }

        return clause;
    }
}
