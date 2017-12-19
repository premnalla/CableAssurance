package ossj.qos.fmri;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJBException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Date;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.oss.fm.monitor.AlarmValue;
import javax.oss.ManagedEntityValue;
import javax.oss.fm.monitor.AttributeValueChange;
import javax.oss.fm.monitor.AttributeValue;
import javax.oss.fm.monitor.ThresholdInfoType;
import javax.oss.fm.monitor.CorrelatedNotificationValue;
import javax.oss.fm.monitor.CommentValue;
import javax.oss.fm.monitor.AlarmKey;

/**
 * AlarmValueDBRep
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * � Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class AlarmValueDBRep implements ManagedEntityValueDBRep {

    // Map containing a handler representing every attribute
    private static HashMap attributeDBHandlerMap = null;

    private static String TableName = "ALARMVALUE";

    /**
     * Constants representing the database fields associated with a ManagedEntityType.
     */
    public static final String ACK_TIME_DBN = "AckTime";
    public static final String ACK_USER_ID_DBN = "AckUserId";
    public static final String ACK_SYSTEM_ID_DBN = "AckSystemId";
    public static final String ADDITIONAL_TEXT_DBN = "AdditionalText";
    public static final String ALARM_ACK_STATE_DBN = "AlarmAckState";
    public static final String ALARM_CHANGED_TIME_DBN = "AlarmChangedTime";
    public static final String ALARM_CLEARED_TIME_DBN = "AlarmClearedTime";
    public static final String ALARM_KEY_DBN = "AlarmKey";
    public static final String ALARM_RAISED_TIME_DBN = "AlarmRaisedTime";
    public static final String ALARM_TYPE_DBN = "AlarmType";
    public static final String ATTRIBUTE_CHANGES_DBN = "AttributeChanges";
    public static final String BACKED_UP_STATUS_DBN = "BackedUpStatus";
    public static final String BACK_UP_OBJECT_DBN = "BackUpObject";
    public static final String CORRELATED_NOTIFICATIONS_DBN = "CorrelatedNotifications";
    public static final String COMMENTS_DBN = "Comments";
    public static final String MANAGED_OBJECT_CLASS_DBN = "ManagedObjectClass";
    public static final String MANAGED_OBJECT_INSTANCE_DBN = "ManagedObjectInstance";
    public static final String MONITORED_ATTRIBUTES_DBN = "MonitoredAttributes";
    public static final String NOTIFICATION_ID_DBN = "NotificationId";
    public static final String PERCEIVED_SEVERITY_DBN = "PerceivedSeverity";
    public static final String PROBABLE_CAUSE_DBN = "ProbableCause";
    public static final String PROPOSED_REPAIR_ACTIONS_DBN = "ProposedRepairActions";
    
    public static final String SPECIFIC_PROBLEM_DBN = "SpecificProblem";
    public static final String SYSTEM_DN_DBN = "SystemDN";
    public static final String THRESHOLD_INFO_DBN = "ThresholdInfo";
    public static final String TREND_INDICATION_DBN = "TrendIndication";
    
    // db representation for the BACKED_UP_STATUS attribute
    private static int BACKED_UP_STATUS_TRUE = 0;
    private static int BACKED_UP_STATUS_FALSE = 1;
    private static int BACKED_UP_STATUS_NOTUSED = 2;

    // Static Initializer for attribute db handlers dealing with generic access.
    static
    {
        // HashMap for attribute handlers
        attributeDBHandlerMap = new HashMap();
        // attribute handlers into the map..
        attributeDBHandlerMap.put(AlarmValue.ACK_TIME, new AckTimeDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ALARM_CHANGED_TIME, new AlarmChangedTimeDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ALARM_CLEARED_TIME, new AlarmClearedTimeDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ALARM_RAISED_TIME, new AlarmRaisedTimeDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ACK_USER_ID, new AckUserIdDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ACK_SYSTEM_ID, new AckSystemIdDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ADDITIONAL_TEXT, new AdditionalTextDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ALARM_ACK_STATE, new AlarmAckStateDBAttr());
        attributeDBHandlerMap.put(ManagedEntityValue.KEY, new AlarmKeyDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ALARM_TYPE, new AlarmTypeDBAttr());
        attributeDBHandlerMap.put(AlarmValue.ATTRIBUTE_CHANGES, new AttributeChangesDBAttr());
        attributeDBHandlerMap.put(AlarmValue.BACKED_UP_STATUS, new BackedUpStatusDBAttr());
        attributeDBHandlerMap.put(AlarmValue.BACK_UP_OBJECT, new BackedUpObjectDBAttr());
        attributeDBHandlerMap.put(AlarmValue.CORRELATED_NOTIFICATIONS, new CorrelatedNotificationsDBAttr());
        attributeDBHandlerMap.put(AlarmValue.COMMENTS, new CommentsDBAttr());
        attributeDBHandlerMap.put(AlarmValue.MANAGED_OBJECT_CLASS, new ManagedObjectClassDBAttr());
        attributeDBHandlerMap.put(AlarmValue.MANAGED_OBJECT_INSTANCE, new ManagedObjectInstanceDBAttr());
        attributeDBHandlerMap.put(AlarmValue.MONITORED_ATTRIBUTES, new MonitoredAttributesDBAttr());
        attributeDBHandlerMap.put(AlarmValue.NOTIFICATION_ID, new NotificationIdDBAttr());
        attributeDBHandlerMap.put(AlarmValue.PERCEIVED_SEVERITY, new PerceivedSeverityDBAttr());
        attributeDBHandlerMap.put(AlarmValue.PROBABLE_CAUSE, new ProbableCauseDBAttr());
        attributeDBHandlerMap.put(AlarmValue.PROPOSED_REPAIR_ACTIONS, new ProposedRepairActionsDBAttr());
        attributeDBHandlerMap.put(AlarmValue.SPECIFIC_PROBLEM, new SpecificProblemDBAttr());
        attributeDBHandlerMap.put(AlarmValue.SYSTEM_DN, new SystemDNDBAttr());
        attributeDBHandlerMap.put(AlarmValue.THRESHOLD_INFO, new ThresholdInfoDBAttr());
        attributeDBHandlerMap.put(AlarmValue.TREND_INDICATION, new TrendIndicationDBAttr());
    }


    /** Creates new AlarmValueDBRep */
    public AlarmValueDBRep() {
    }

    /**
     * Sets the db representation of a managed entity value in a prepared statement.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index position of the prepared statement variable
     * @param ps A PreparedStatement that will contain the db representation of the
     * populated attributes in the managed entity.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityInPrepStatement( ManagedEntityValue entity, int pos, PreparedStatement ps )
    throws SQLException
    {
        AlarmValue alarm = (AlarmValue) entity;
        String[] popNames =  alarm.getPopulatedAttributeNames();
        for ( int i=0; i< popNames.length; i++) {
            AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get(popNames[i]);
            if ( handler != null ) {
                handler.setObjInDBStatement( ps,pos,alarm );
                pos++;
                //System.out.println("Writing out" + popNames[i]);
            }
            else {
                throw new SQLException();
            }
        }
        //System.out.println(" Writing template: " + alarm.toString() );
        return pos;
    }

    /**
     * Sets the db representation of a managed entity value attribute in a prepared statement.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index the vacant position in the prepared statement
     * @param ps A PreparedStatement that will contain the db representation of the attribute object.
     * @param attributeName The name of the attribute in the managedEntity to populate in the
     * prepared statement.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityAttributeInPrepStatement( ManagedEntityValue entity, int pos, PreparedStatement ps,
    String attributeName )
    throws SQLException
    {
        AlarmValue alarm = (AlarmValue) entity;
        if ( alarm.isPopulated( attributeName ) == false ) {
            throw new SQLException ( "Attribute Value is not populated." );
        }
        else {
            AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get( attributeName );
            if ( handler != null ) {
                handler.setObjInDBStatement( ps,pos,alarm );
                pos++;
            }
            else {
                throw new SQLException("Attribute name is invalid.");
            }
        }
        return pos;
    }

    /**
     * Sets the db representation of populated managed entity value attributes
     * in a prepared statement in a specified order.
     *
     * @param entity The ManagedEntityValue that contains the attribute's value.
     * @param pos The index the vacant position in the prepared statement
     * @param ps A PreparedStatement that will contain the db representation of the attribute object.
     * @param attributeName The name of the attribute in the managedEntity to populate in the
     * prepared statement.
     * @returns Int The position of the next vacant position in the prepared statement.
     * @exception SQLException thrown if there is a problem setting the db
     * representation of the attribute.
     */
    public int setManagedEntityInPrepStatementByOrder( ManagedEntityValue entity, int pos, PreparedStatement ps,
    String[] order )
    throws SQLException
    {
        AlarmValue alarm = (AlarmValue) entity;
        for ( int i = 0, len=order.length; i<len; i++ ) {
            if ( alarm.isPopulated( order[i] ) == false ) {
                throw new SQLException ( "Attribute Value is not populated" );
            }
            else {
                AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get( order[i] );
                //System.out.println( order[i] );

                if ( handler != null ) {
                    // System.out.println("Writing to the ps statement" + pos );
                    handler.setObjInDBStatement( ps,pos,alarm );
                    pos++;
                }
                else {
                    throw new SQLException("Unable to populate prepared statement. Attribute not found.");
                }
            }
        }
        return pos;
    }

    public int setObjectInPrepStatement ( String name, int pos, PreparedStatement ps, Object attrValue )
    throws SQLException {
        AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get( name );
        if ( handler != null ) {
            //System.out.println("Writing to the ps statement" + pos );
            handler.setObjInDBStatement( ps, pos, attrValue );
            pos++;
        }
        else {
            throw new SQLException("Unable to populate prepared statement. Attribute not found.");
        }
        return pos;
    }

    /**
     * Sets a ManagedEntity value from the DB representation
     * in a ResultSet.
     *
     * @param rs A ResultSet.
     * @param names An array containing the attribute names to populate.
     * @param entity The ManagedEntity to populate.
     * @exception SQLException thrown if there is a problem setting the managed entity
     * attributes from their db representation.
     */
    public ManagedEntityValue setManagedEntityAttrsFromDBRep( ResultSet rs, String[] names )
    throws SQLException
    {
        AlarmValue alarm = new AlarmValueImpl();

        // if no attribute names, then get all.
        if ( names == null || names.length == 0 ) {
            // Get all supported attribute names.
            names = alarm.getSettableAttributeNames();
        }

        for ( int i=0, len=names.length; i<len; i++) {
            AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get(names[i]);
            if ( handler != null ) {
                alarm = handler.getObjFromDBResult( rs, alarm );
                //System.out.println("Reading in" + names[i]);
            }
            // ignore invalid names 
            /*else {
                
               // throw new SQLException( "Invalid entity attribute name specified: " + names[i] );
            } */
        }
        return alarm;
    }

    /**
     * Sets all the attributes of a ManagedEntity value from the DB representation
     * in a ResultSet.
     *
     * @param rs A ResultSet.
     * @param entity The ManagedEntity to populate.
     * @exception SQLException thrown if there is a problem setting the managed entity
     * attributes from its db representation.
     */
    public ManagedEntityValue setManagedEntityFromDBRep( ResultSet rs )
    throws SQLException
    {
        return setManagedEntityAttrsFromDBRep( rs, null );
    }

    /**
     * Returns the database name of the ManagedEntity value attribute.
     *
     * @param name The name of the ManagedEntity value attribute.
     * @return String A string that represents the database name of the attribute
     * @exception IllegalArgumentException thrown if the name doesn't exist.
     */
    public String getDBAttributeName ( String name )
    throws IllegalArgumentException {

        String dbName = null;
        AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get( name );
        if ( handler != null ) {
            dbName = handler.getDBAttributeName();
        }
        else {
            throw new IllegalArgumentException( "Invalid attribute name entered: " + name );
        }
        return dbName;
    }

    /**
     * Returns the database names for the populated ManagedEntity value attributes.
     *
     * @param entity A ManagedEntity.
     * @return String[] An array of strings that represents the database names of the
     * populated attributes.
     * @exception IllegalArgumentException thrown if a db name doesn't exist.
     */
    public String[] getDBAttributeNames ( ManagedEntityValue entity )
    throws IllegalArgumentException {

        String[] popNames = entity.getPopulatedAttributeNames();
        String names[] = new String[popNames.length];

        for ( int i=0, len=popNames.length; i<len; i++ ) {
            AttrObjectDBHandler handler = (AttrObjectDBHandler)attributeDBHandlerMap.get( popNames[i] );
            if ( handler != null ) {
                names[i] = handler.getDBAttributeName();
            }
            else {
                throw new IllegalArgumentException( "Invalid attribute name entered: " + popNames[i]  );
            }
        }
        return names;
    }

    public String getTableName() {
        return TableName;
    }

    // interface for dealing with generic db access specific to the AlarmValue entity.

    interface AttrObjectDBHandler {

        /**
         * Populates the attribute value from a db ResultSet.
         *
         * @param rs A ResultSet that contains a db representation of the attribute object.
         * @param alarm The AlarmValue that contains the attribute value to be populated.
         * @exception SQLException thrown if there is a problem setting a value attribute
         * from its db representation.
         */
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException;

        /**
         * Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param alarm The AlarmValue that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement ( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException;

        /**
         * Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement ( PreparedStatement ps, int pos, Object object )
        throws SQLException;

        public String getDBAttributeName();

    }

    // static inner classes that represent each attribute

    static class AdditionalTextDBAttr implements AttrObjectDBHandler
    {
        /**
         * Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param alarm The AlarmValue that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getAdditionalText() );
            return;
        }

        /** Populates the attribute value from a db ResultSet.
         *
         * @param rs A ResultSet that contains a db representation of the attribute object.
         * @param alarm The AlarmValue that contains the attribute value to be populated.
         * @exception SQLException thrown if there is a problem setting a value attribute
         * from its db representation.
         */
        public AlarmValue getObjFromDBResult ( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setAdditionalText( rs.getString(ADDITIONAL_TEXT_DBN) );
            return alarm;
        }

        public String getDBAttributeName() {
            return ADDITIONAL_TEXT_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement(PreparedStatement ps, int pos, Object object)
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : AdditionalText" );
            }
            return;
        }
    }

    static class AckTimeDBAttr implements AttrObjectDBHandler
    {
        /**
         * Populates the attribute value from a db ResultSet.
         *
         * @param rs A ResultSet that contains a db representation of the attribute object.
         * @param alarm The AlarmValue that contains the attribute value to be populated.
         * @exception SQLException thrown if there is a problem setting a value attribute
         * from its db representation.
         */
        public AlarmValue getObjFromDBResult(ResultSet rs, AlarmValue alarm)
        throws SQLException {
            Date date = null;
            long time = rs.getLong(ACK_TIME_DBN);
            if ( time != 0 ) {
                date = new Date(time);
            }
            alarm.setAckTime( date );
            return alarm;
        }

        /**
         * Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param alarm The AlarmValue that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            long time = 0L;
            if ( alarm.getAckTime() != null ) {
                time = alarm.getAckTime().getTime();
            }
            ps.setLong( pos, time );
            return;
        }

        public String getDBAttributeName() {
            return ACK_TIME_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            try {
                long time = 0L;
                if ( object != null ) {
                    time = ((Date)object).getTime();
                }
                else {
                    ps.setLong( pos, time );
                }
            }
            catch ( Exception e ) {
                throw new SQLException( "Error loading prepared statement for attribute : AckTime" );
            }
        }
    }

    static class AckUserIdDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setAckUserId( rs.getString( ACK_USER_ID_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getAckUserId() );
            return;
        }

        public String getDBAttributeName() {
            return ACK_USER_ID_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement(PreparedStatement ps, int pos, Object object)
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : AckUserId" );
            }
            return;
        }
    }

    static class AlarmAckStateDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setAlarmAckState( rs.getInt( ALARM_ACK_STATE_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setInt( pos, alarm.getAlarmAckState() );
            return;
        }

        public String getDBAttributeName() {
            return ALARM_ACK_STATE_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof Integer == true ) {
                ps.setInt( pos, ((Integer)object).intValue() );
            }
            else {
                throw new SQLException ( "Error loading prepared statement for attribute : AlarmAckState");
            }
            return;
        }
    }

    static class AckSystemIdDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setAckSystemId( rs.getString( ACK_SYSTEM_ID_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getAckSystemId() );
            return;
        }

        public String getDBAttributeName() {
            return ACK_SYSTEM_ID_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement(PreparedStatement ps,int pos,Object object) throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : AckSystemId" );
            }
            return;
        }
    }

    static class NotificationIdDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setNotificationId( rs.getString( NOTIFICATION_ID_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getNotificationId() );
            return;
        }

        public String getDBAttributeName() {
            return NOTIFICATION_ID_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object)
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : NotificationId" );
            }
            return;
        }
    }

    static class AlarmClearedTimeDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            Date date = null;
            long time = rs.getLong( ALARM_CLEARED_TIME_DBN );
            if ( time != 0 ) {
                date = new Date(time);
            }
            alarm.setAlarmClearedTime( date );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            long time = 0L;
            if ( alarm.getAlarmClearedTime() != null ) {
                time = alarm.getAlarmClearedTime().getTime();
            }
            ps.setLong( pos, time );
            return;
        }

        public String getDBAttributeName() {
            return ALARM_CLEARED_TIME_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object)
        throws SQLException {
            try {
                long time = 0L;
                if ( object != null ) {
                    time = ((Date)object).getTime();
                }
                else {
                    ps.setLong( pos, time );
                }
            }
            catch ( Exception e ) {
                throw new SQLException( "Error loading prepared statement for attribute : AlarmClearedTime" );
            }
            return;
        }
    }

    static class AlarmChangedTimeDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            Date date = null;
            long time = rs.getLong(ALARM_CHANGED_TIME_DBN);
            if ( time != 0 ) {
                date = new Date(time);
            }
            alarm.setAlarmChangedTime( date );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            long time = 0L;
            if ( alarm.getAlarmChangedTime() != null ) {
                time = alarm.getAlarmChangedTime().getTime();
            }
            ps.setLong( pos, time );
            return;
        }

        public String getDBAttributeName() {
            return ALARM_CHANGED_TIME_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            try {
                long time = 0L;
                if ( object != null ) {
                    time = ((Date)object).getTime();
                }
                ps.setLong( pos, time );
            }
            catch ( Exception e ) {
                throw new SQLException( "Error loading prepared statement for attribute : AlarmClearedTime" );
            }
            return;
        }
    }

    static class AlarmKeyDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            AlarmKey key = alarm.makeAlarmKey();
            key.setAlarmPrimaryKey( rs.getString( ALARM_KEY_DBN ) );
            alarm.setAlarmKey(key);
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            //System.out.println( "Writing out alarm key: " + alarm.getAlarmKey().toString() );
            ps.setString( pos, (alarm.getAlarmKey().getAlarmPrimaryKey() ));
            return;
        }

        public String getDBAttributeName() {
            return ALARM_KEY_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object ) throws SQLException {
            if ( object instanceof AlarmKey == true ) {
                ps.setString( pos, ((AlarmKey)object).getAlarmPrimaryKey() );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : AlarmKey" );
            }
            return;
        }
    }

    static class AlarmRaisedTimeDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            Date date = null;
            long time = rs.getLong(ALARM_RAISED_TIME_DBN);
            if ( time != 0 ) {
                date = new Date(time);
            }
            alarm.setAlarmRaisedTime( date );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            long time = 0L;
            if ( alarm.getAlarmRaisedTime() != null ) {
                time = alarm.getAlarmRaisedTime().getTime();
            }
            ps.setLong( pos, time );
            return;
        }

        public String getDBAttributeName() {
            return ALARM_RAISED_TIME_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            try {
                long time = 0L;
                if ( object != null ) {
                    time = ((Date)object).getTime();
                }
                else {
                    ps.setLong( pos, time );
                }
            }
            catch ( Exception e ) {
                throw new SQLException( "Error loading prepared statement for attribute : AlarmRaisedTime" );
            }
            return;
        }
    }

    static class AlarmTypeDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setAlarmType( rs.getString( ALARM_TYPE_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getAlarmType() );
            return;
        }

        public String getDBAttributeName() {
            return ALARM_TYPE_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String)object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : AlarmType" );
            }
            return;
        }
    }

    static class AttributeChangesDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            //VP AttributeValueChange[] attributeChanges = (AttributeValueChange[]) rs.getObject( ATTRIBUTE_CHANGES_DBN );
			//VP the blob shall be read first
			AttributeValueChange[] attributeChanges = null;
			try{
			ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(ATTRIBUTE_CHANGES_DBN).getBinaryStream());
			attributeChanges = (AttributeValueChange[]) ois1.readObject();
			} catch (Exception ioex){
				throw new SQLException(ioex.getClass().getName()+" when get Blob..."+ioex.getMessage());
			}
			// end VP

            if ( attributeChanges == null ) {
                attributeChanges = new AttributeValueChange[0];
            }
            alarm.setAttributeChanges( attributeChanges );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            AttributeValueChange[] attributeChanges = alarm.getAttributeChanges();
            if ( attributeChanges != null && attributeChanges.length == 0 ) {
                attributeChanges = null;
            }
        //VP    ps.setObject( pos, attributeChanges );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(attributeChanges);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP

            return;
        }

        public String getDBAttributeName() {
            return ATTRIBUTE_CHANGES_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            try {
                AttributeValueChange[] attributeChanges = (AttributeValueChange[])object;
                if ( attributeChanges != null && attributeChanges.length == 0 ) {
                    attributeChanges = null;
                }
                //VP ps.setObject( pos, attributeChanges );
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(attributeChanges);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
        //    }
        //    catch ( ClassCastException ex )  {
        //        throw new SQLException( "Error loading prepared statement for attribute : AttributeChanges" );
        //    }
		//end VP
            return;
        }
    }

    static class BackedUpStatusDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            Boolean bolStatus = null;
            int status = rs.getInt( BACKED_UP_STATUS_DBN );
            if ( status == BACKED_UP_STATUS_FALSE ) {
                bolStatus = new Boolean( false );
            }
            else if ( status == BACKED_UP_STATUS_TRUE ) {
                bolStatus = new Boolean( true );
            }
            alarm.setBackedUpStatus( bolStatus );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            Boolean bolStatus = alarm.getBackedUpStatus();
            int status = BACKED_UP_STATUS_NOTUSED;
            if ( bolStatus != null ) {
                status =  bolStatus.booleanValue() ? BACKED_UP_STATUS_TRUE : 
                          BACKED_UP_STATUS_FALSE; 
            }
            ps.setInt( pos, status );
            return;
        }

        public String getDBAttributeName() {
            return BACKED_UP_STATUS_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof Boolean == true ) {
                ps.setBoolean( pos, ((Boolean)object).booleanValue() );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : BackedUpStatus" );
            }
            return;
        }
    }

    static class BackedUpObjectDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setBackUpObject( rs.getString( BACK_UP_OBJECT_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getBackUpObject() );
            return;
        }

        public String getDBAttributeName() {
            return BACK_UP_OBJECT_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : BackedUpObject" );
            }
            return;
        }
    }

    static class CorrelatedNotificationsDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException{
            //VP CorrelatedNotificationValue[] corNotifications = ( CorrelatedNotificationValue[] )rs.getObject(CORRELATED_NOTIFICATIONS_DBN );
			//VP the blob shall be read first
			CorrelatedNotificationValue[] corNotifications = null;
			try{
			ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(CORRELATED_NOTIFICATIONS_DBN).getBinaryStream());
			corNotifications = ( CorrelatedNotificationValue[] )ois1.readObject();
			} catch (Exception ioex){
				throw new SQLException(ioex.getClass().getName()+" when get Blob..."+ioex.getMessage());
			}
			//end VP
            if ( corNotifications == null ) {
                corNotifications = new CorrelatedNotificationValue[0];
            }
            alarm.setCorrelatedNotifications( corNotifications );
         
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            CorrelatedNotificationValue[] corNotifications = alarm.getCorrelatedNotifications(); 
            if ( corNotifications != null && corNotifications.length == 0 ) {
                corNotifications = null;
            }
            //VP ps.setObject( pos, corNotifications );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(corNotifications);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP
            
            return;
        }

        public String getDBAttributeName() {
            return CORRELATED_NOTIFICATIONS_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object)
        throws SQLException {
            try {
                CorrelatedNotificationValue[] corNotifications = (CorrelatedNotificationValue[]) object;
                if ( corNotifications != null && corNotifications.length == 0  ) {
                    corNotifications = null;
                }
                //VP ps.setObject( pos, corNotifications );
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(corNotifications);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
        //    }
        //    catch ( ClassCastException ex ) {
        //        throw new SQLException( "Error loading prepared statement for attribute : CorrelatedNotifications" );
        //    }
		//end VP
            return;
        }
    }

    static class CommentsDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException{
            //VP CommentValue[] comments = ( CommentValue[] ) rs.getObject(COMMENTS_DBN);
			//VP the blob shall be read first
			CommentValue[] comments = null;
			try{
			ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(COMMENTS_DBN).getBinaryStream());
			comments = ( CommentValue[] ) ois1.readObject();
			} catch (Exception ioex){
				throw new SQLException(ioex.getClass().getName()+" when get Blob..."+ioex.getMessage());
			}
			//end VP
            if ( comments == null ) {
                comments = new CommentValue[0];
            }
            alarm.setComments( comments ); 
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            CommentValue[] comments = alarm.getComments();
            if ( comments != null && comments.length == 0 ) {
                comments = null;
            }
            //VP ps.setObject( pos, alarm.getComments() );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(alarm.getComments());
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP
            return;
        }

        public String getDBAttributeName() {
            return COMMENTS_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            try {
                CommentValue[] comments = (CommentValue[]) object;
                if ( comments != null && comments.length == 0 ) {
                    comments = null;
                }
                //VP ps.setObject( pos, comments );
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(comments);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
        //    }
        //    catch ( ClassCastException ex ) {
        //        throw new SQLException( "Error loading prepared statement for attribute : Comments" );
        //    }
		//end VP
            return;
        }
    }

    static class ManagedObjectInstanceDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setManagedObjectInstance( rs.getString( MANAGED_OBJECT_INSTANCE_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getManagedObjectInstance() );
            return;
        }

        public String getDBAttributeName() {
            return MANAGED_OBJECT_INSTANCE_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : ManagedObjectInstance" );
            }
            return;
        }
    }

    static class ManagedObjectClassDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setManagedObjectClass( rs.getString( MANAGED_OBJECT_CLASS_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getManagedObjectClass() );
            return;
        }

        public String getDBAttributeName() {
            return MANAGED_OBJECT_CLASS_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : ManagedObjectClass" );
            }
            return;
        }
    }

    static class MonitoredAttributesDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException{
            //VP AttributeValue[] attributeValues = ( AttributeValue[] ) rs.getObject( MONITORED_ATTRIBUTES_DBN );
			//VP the blob shall be read first
			AttributeValue[] attributeValues = null;
			try{
			ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(MONITORED_ATTRIBUTES_DBN).getBinaryStream());
			attributeValues = ( AttributeValue[] ) ois1.readObject();
			} catch (Exception ioex){
				throw new SQLException(ioex.getClass().getName()+" when get Blob..."+ioex.getMessage());
			}
			//end VP
            if ( attributeValues == null ) {
                attributeValues = new AttributeValue[0];
            }
            alarm.setMonitoredAttributes( attributeValues );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            AttributeValue[] attributeValues = alarm.getMonitoredAttributes();
            if ( attributeValues != null && attributeValues.length == 0 ) {
                attributeValues = null;
            }
            //VP ps.setObject( pos, attributeValues );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(attributeValues);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP
            return;
        }

        public String getDBAttributeName() {
            return MONITORED_ATTRIBUTES_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object)
        throws SQLException {
            try {
                AttributeValue[] attributeValues = (AttributeValue[])object;
                if ( attributeValues != null && attributeValues.length == 0 ) {
                    attributeValues = null;
                }
                //VP ps.setObject( pos, attributeValues );
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(attributeValues);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
        //    }
        //    catch ( ClassCastException ex ) {
        //        throw new SQLException( "Error loading prepared statement for attribute : MonitoredValues" );
        //    }
		//end VP
            return;
        }
    }

    static class PerceivedSeverityDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setPerceivedSeverity( (short) rs.getInt( PERCEIVED_SEVERITY_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setInt( pos, (int)alarm.getPerceivedSeverity() );
            return;
        }

        public String getDBAttributeName() {
            return PERCEIVED_SEVERITY_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof Short == true ) {
                ps.setInt( pos, (int)((Short)object).shortValue() );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : PerceivedSeverity" );
            }
            return;
        }
    }

    static class ProbableCauseDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setProbableCause( (short) rs.getInt( PROBABLE_CAUSE_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setInt( pos, (int)alarm.getProbableCause() );
            return;
        }

        public String getDBAttributeName() {
            return PROBABLE_CAUSE_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof Short == true ) {
                ps.setInt( pos, (int)((Short)object).shortValue() );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : ProbableCause" );
            }
            return;
        }
    }

    static class ProposedRepairActionsDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setProposedRepairActions( rs.getString( PROPOSED_REPAIR_ACTIONS_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getProposedRepairActions() );
            return;
        }

        public String getDBAttributeName() {
            return PROPOSED_REPAIR_ACTIONS_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : ProposedRepairActions" );
            }
            return;
        }
    }

    static class SpecificProblemDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            String spString = rs.getString( SPECIFIC_PROBLEM_DBN );
            if ( spString.equals( "NOT_PROVIDED" ) ) {
                 alarm.setSpecificProblem( null );
            }
            else {
                alarm.setSpecificProblem( spString );
            }
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            String spString = alarm.getSpecificProblem();
            if ( spString == null) {
                spString = "NOT_PROVIDED";
            }
            ps.setString( pos, spString );
            return;
        }

        public String getDBAttributeName() {
            return SPECIFIC_PROBLEM_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            String spString = null;
            if ( object == null ) {
                spString = "NOT_PROVIDED";
            }
            else {
                if ( object instanceof String == true ) {
                    spString = (String) object;
                }
                else {
                    throw new SQLException( "Error loading prepared statement for attribute : SpecificProblem" );
                }
            }
            ps.setString( pos, spString );
            return;
        }
    }

    static class SystemDNDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setSystemDN( rs.getString( SYSTEM_DN_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getSystemDN() );
            return;
        }

        public String getDBAttributeName() {
            return SYSTEM_DN_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : SystemDN" );
            }
            return;
        }
    }

    static class ThresholdInfoDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException{
            //VP alarm.setThresholdInfo( (ThresholdInfoType) rs.getObject( THRESHOLD_INFO_DBN ) );
			//VP the blob shall be read first
			try{
			ObjectInputStream ois1 = new ObjectInputStream(rs.getBlob(THRESHOLD_INFO_DBN).getBinaryStream());
			alarm.setThresholdInfo( (ThresholdInfoType) ois1.readObject() );
			} catch (Exception ioex){
				throw new SQLException(ioex.getClass().getName()+" when get Blob..."+ioex.getMessage());
			}
			//end VP
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            //VP ps.setObject( pos, alarm.getThresholdInfo() );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(alarm.getThresholdInfo());
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
		//end VP
            return;
        }

        public String getDBAttributeName() {
            return THRESHOLD_INFO_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            //VP if ( object instanceof String == true ) {
            //VP    ps.setString( pos, (String) object );
		try {
			ByteArrayOutputStream   ba2         = new ByteArrayOutputStream();
			ObjectOutputStream      bos2        = new ObjectOutputStream(ba2 );
			bos2.writeObject(object);
			bos2.flush();
			byte                    abyte2[]    = ba2.toByteArray();
			ByteArrayInputStream    bi2         = new ByteArrayInputStream(abyte2 );
			ps.setBinaryStream(pos, bi2, abyte2.length );
		} catch (Exception ex){
				throw new SQLException (ex.getClass().getName()+" when writting blob...");
		}
            //}
            //else {
            //    throw new SQLException( "Error loading prepared statement for attribute : ThresholdInfo" );
            //}
		//end VP
            return;
        }
    }

    static class TrendIndicationDBAttr implements AttrObjectDBHandler
    {
        public AlarmValue getObjFromDBResult( ResultSet rs, AlarmValue alarm )
        throws SQLException {
            alarm.setTrendIndication( rs.getString( TREND_INDICATION_DBN ) );
            return alarm;
        }

        public void setObjInDBStatement( PreparedStatement ps, int pos, AlarmValue alarm )
        throws SQLException {
            ps.setString( pos, alarm.getTrendIndication() );
            return;
        }

        public String getDBAttributeName() {
            return TREND_INDICATION_DBN;
        }

        /** Sets the db representation of the attribute in a PreparedStatement.
         *
         * @param ps A PreparedStatement that will contain the db representation of the attribute object.
         * @param pos The attribute value's position in the prepared statement.
         * @param object The Object that contains the attribute's value.
         * @exception SQLException thrown if there is a problem setting the db
         * representation of the attribute.
         */
        public void setObjInDBStatement( PreparedStatement ps, int pos, Object object )
        throws SQLException {
            if ( object instanceof String == true ) {
                ps.setString( pos, (String) object );
            }
            else {
                throw new SQLException( "Error loading prepared statement for attribute : TrendIndication" );
            }
            return;
        }
    }
}

