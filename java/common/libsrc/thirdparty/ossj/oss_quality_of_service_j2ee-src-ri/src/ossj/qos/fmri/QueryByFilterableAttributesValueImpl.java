package ossj.qos.fmri;

import javax.oss.fm.monitor.PerceivedSeverity;
import javax.oss.fm.monitor.AlarmAckState;
import javax.oss.fm.monitor.QueryByFilterableAttributesValue;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmType;
import javax.oss.fm.monitor.ProbableCause;

import javax.oss.QueryValue;
import javax.oss.ManagedEntityValue;
import javax.oss.Serializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.lang.reflect.Field;

import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;

/**
 * QueryByFilterableAttributesImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class QueryByFilterableAttributesValueImpl extends QueryValueImpl implements QueryByFilterableAttributesValue,
WhereClause{

    // Filterable Attributes
    private String managedObjectInstance = null;
    private String managedObjectClass = null;
    private String alarmType = null;
    private short perceivedSeverity = 0;
    private short alarmAckState = 0;
    private Date timeConstraint = null;

    static private HashSet alarmTypeSet = null;
    static private HashSet perceivedSeveritySet = null;

     // Map containing a handler representing every attribute
    private static TreeMap attributeHandlerMap = null;

    // Manager that is responsible for the attribute descriptors
    private static AttributeManager attributeManager = null;

    /**
     * default constructor
     */
    public QueryByFilterableAttributesValueImpl() {
        super();
    }

    // static initializer for the HashSets used to validate Query Attributes
    static {

        // TreeMap for attribute handlers
        attributeHandlerMap = new TreeMap();
        // attribute handlers into the map..
        attributeHandlerMap.put( QueryByFilterableAttributesValue.ALARM_ACK_STATE, new AlarmAckStateAttr() );
        attributeHandlerMap.put( QueryByFilterableAttributesValue.ALARM_TYPE, new AlarmTypeAttr() );
        attributeHandlerMap.put( QueryByFilterableAttributesValue.MANAGED_OBJECT_CLASS, new ManagedObjectClassAttr() );
        attributeHandlerMap.put( QueryByFilterableAttributesValue.MANAGED_OBJECT_INSTANCE, new ManagedObjectInstanceAttr() );
        attributeHandlerMap.put( QueryByFilterableAttributesValue.PERCEIVED_SEVERITY, new PerceivedSeverityAttr() );
        attributeHandlerMap.put( QueryByFilterableAttributesValue.TIME_CONSTRAINT, new TimeConstraintAttr() );

        // TreeMap for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.ALARM_ACK_STATE, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.ALARM_TYPE, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.MANAGED_OBJECT_CLASS, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.MANAGED_OBJECT_INSTANCE, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.PERCEIVED_SEVERITY, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( QueryByFilterableAttributesValue.TIME_CONSTRAINT, new AttributeDescriptor(true,true,true));

        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( QueryValueImpl.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );

        // HashSet for support AlarmTypes
        alarmTypeSet = new HashSet();
        try {
            Class alarmTypeClass = Class.forName( "javax.oss.fm.monitor.AlarmType" );
            Field[] fields = alarmTypeClass.getFields();
            for ( int i=0, len=fields.length; i<len; i++ ) {
                alarmTypeSet.add( fields[i].get(null) );
            }
        }
        catch ( Exception exClass ) {}

        // HashSet for supported PerceivedSeverity values
        perceivedSeveritySet = new HashSet();
        try {
            Class perceivedSeverityClass = Class.forName( "javax.oss.fm.monitor.PerceivedSeverity" );
            Field[] fields = perceivedSeverityClass.getFields();
            for ( int i=0, len=fields.length; i<len; i++ ) {
                perceivedSeveritySet.add( fields[i].get(null) );
            }
        }
        catch ( Exception exClass ) {}
    }

   // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return QueryByFilterableAttributesValueImpl.attributeManager;
    }

    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) {
            handler = (AttrObjectHandler)QueryByFilterableAttributesValueImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes

    /**
     * Returns all the serializer types than can be created by this factory
     */
    /*
    public String[] getSupportedSerializerTypes() {
        return new String[0];
    }
     */
     // Took out the above and added this, 2002-03-14, Hooman Tahamtani
      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( FmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType The class name of the serializer interface that must
     *  be created. For example "XmlSerializer.getClass().getName()"
     *
     *
     * @return A serializer matching the serializer type
     * which can be used to serialize and deserialize the object which
     * inherits this interface.
     * @exception java.lang.IllegalArgumentException If an Illegal Argument is provided.
     * or if no Serializer can be created matching the provided
     * Serializer Type.
     */
     public Serializer makeSerializer( String serializerType )
     throws java.lang.IllegalArgumentException {
        Serializer serializer = null;
     /*
        Added below to comply with the new implementation.
        February 14, 2002.
        Hooman Tahamtani, Ericsson Microwave AB
        Gothenburg, Sweden
     */
     try{
        serializer = new FmXmlSerializerImpl(serializerType);
     }catch(java.lang.IllegalArgumentException e){
        throw new java.lang.IllegalArgumentException(e.getMessage());
     }
        return serializer;
    }


    public Object clone() {
        QueryByFilterableAttributesValueImpl obj = (QueryByFilterableAttributesValueImpl) super.clone();
        obj.timeConstraint = (Date) timeConstraint.clone();
        return obj;
    }

    /**
     * Returns managed object instance.
     *
     * @return string - managed object instance
     */
    public String getManagedObjectInstance() throws java.lang.IllegalStateException {
        if ( populatedAttributeNames.contains( MANAGED_OBJECT_INSTANCE ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            MANAGED_OBJECT_INSTANCE );
        }
        return managedObjectInstance;
    }

    /**
     * Sets the managed object instance.
     *
     * @param name - String that represents the distinguished name of the
     * managed object instance.
     */
    public void setManagedObjectInstance( String name )
    throws java.lang.IllegalArgumentException {
        if ( name == null ) {
            throw new IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            MANAGED_OBJECT_INSTANCE );
        }
        else {
            managedObjectInstance = name;
            populatedAttributeNames.add( MANAGED_OBJECT_INSTANCE );
        }
        return;
    }

    /**
     * Returns managed object class.
     *
     * @return string - managed object class
     */
    public String getManagedObjectClass() throws java.lang.IllegalStateException {
      /*  Hooman
        if ( populatedAttributeNames.contains( MANAGED_OBJECT_CLASS ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            MANAGED_OBJECT_CLASS );
        }
       */
        if ( populatedAttributeNames.contains( QueryByFilterableAttributesValue.MANAGED_OBJECT_CLASS ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            MANAGED_OBJECT_CLASS );
        }

        return managedObjectClass;
    }

    /**
     * Sets the managed object class.
     *
     * @param className - The name of the managed object class
     */
    public void setManagedObjectClass( String className ) throws java.lang.IllegalArgumentException {
        if ( className == null ) {
            throw new IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            MANAGED_OBJECT_CLASS );
        }
        else {
            managedObjectClass = className;
            populatedAttributeNames.add( MANAGED_OBJECT_CLASS );
        }
        return;
    }

    /**
     * Returns event type.
     *
     * @return string - event type
     * @see AlarmType
     */
    public String getAlarmType() throws java.lang.IllegalStateException {
        if ( populatedAttributeNames.contains( ALARM_TYPE ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            ALARM_TYPE );
        }
        return alarmType;
    }

    /**
     * Sets the event type.
     *
     * @param type - The event type
     * @see AlarmType
     */
    public void setAlarmType( String type ) throws java.lang.IllegalArgumentException {
        if ( type == null || alarmTypeSet.contains( type ) == false ) {
            throw new IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            ALARM_TYPE );
        }
        else {
            alarmType = type;
            populatedAttributeNames.add( ALARM_TYPE );
        }
        return;
    }

    /**
     * Returns perceived severity.
     *
     * @return short - perceived severity
     * @see PerceivedSeverity
     */
    public short getPerceivedSeverity() throws java.lang.IllegalStateException {
        if ( populatedAttributeNames.contains( PERCEIVED_SEVERITY ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            PERCEIVED_SEVERITY );
        }
        return perceivedSeverity;
    }

    /**
     * Sets the perceived severity.
     *
     * @param severity - sets the perceived severity
     * @see PerceivedSeverity
     */
    public void setPerceivedSeverity( short severity ) throws java.lang.IllegalArgumentException {
        Short objSeverity = new Short( severity );
        if ( perceivedSeveritySet.contains( objSeverity ) ) {
            perceivedSeverity = severity;
            populatedAttributeNames.add( PERCEIVED_SEVERITY );
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            PERCEIVED_SEVERITY );
        }
        return;
    }

    /**
     * Returns alarm ack state.
     *
     * @return short - alarm ack state
     * @see AlarmAckState
     */
    public short getAlarmAckState() throws java.lang.IllegalStateException {
        if ( populatedAttributeNames.contains( ALARM_ACK_STATE ) == false ) {
            throw new IllegalStateException(  LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            ALARM_ACK_STATE );
        }
        return alarmAckState;
    }

    /**
     * Sets the alarm's ack state.
     *
     * @param state - A short representing an alarm's ack state
     * @see AlarmAckState
     */
    public void setAlarmAckState(short ack_state) throws java.lang.IllegalArgumentException
    {
        if ( ack_state != AlarmAckState.UNACKNOWLEDGED &&
        ack_state != AlarmAckState.ACKNOWLEDGED ) {
            throw new IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            ALARM_ACK_STATE + ". Valid values are " +
            AlarmAckState.UNACKNOWLEDGED + " or " + AlarmAckState.ACKNOWLEDGED );
        }
        else {
            alarmAckState = ack_state;
            populatedAttributeNames.add( ALARM_ACK_STATE );
        }
        return;
    }

    /**
     * Returns time constraint.
     * <p>
     * The time constraint is in the following format: "yyyyMMddHHmmSS.tZ", which
     * is the GeneralizedTime authorized by ITU-T in Coordinated Universal Time
     * (UTC) format.
     * For example, to indicate 1:20 pm on May the 31st, 1999 for UTC, one
     * would write:
     * "19990531132000.0Z".
     *
     * @return <code>String</code> - Time limit, earlier events are filtered out
     */
    public Date getTimeConstraint() throws java.lang.IllegalStateException {
        if ( populatedAttributeNames.contains( TIME_CONSTRAINT ) == false ) {
            throw new IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            TIME_CONSTRAINT );
        }
        return timeConstraint;
    }

    /**
     * Sets time constraint.
     * <p>
     * The time constraint is in the following format: "yyyyMMddHHmmSS.tZ", which
     * is the GeneralizedTime authorized by ITU-T in Coordinated Universal Time
     * (UTC) format.
     * For example, to indicate 1:20 pm on May the 31st, 1999 for UTC, one
     * would write:
     * "19990531132000.0Z".
     *
     * @param time_limit Time, earlier events are filtered out
     */
    public void setTimeConstraint(Date time_limit) throws java.lang.IllegalArgumentException {
        if ( time_limit == null ) {
            throw new IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            TIME_CONSTRAINT );
        }
        else {
            timeConstraint = time_limit;
            populatedAttributeNames.add( TIME_CONSTRAINT );
        }
        return;
    }

    public String buildPrepStatementClause() {
        StringBuffer buffer = new StringBuffer(200);
        boolean started = false;

        if ( isPopulated( TIME_CONSTRAINT ) ) {
            started = true;
            buffer.append( "( "  + AlarmValueDBRep.ALARM_RAISED_TIME_DBN +
            " > ? OR " + AlarmValueDBRep.ALARM_CLEARED_TIME_DBN + " > ? OR " +
            AlarmValueDBRep.ACK_TIME_DBN + " > ? OR " + AlarmValueDBRep.ALARM_CHANGED_TIME_DBN +
            " > ? ) ");
        }
        if ( isPopulated( MANAGED_OBJECT_INSTANCE ) ) {
            if ( started == true ) {
                buffer.append( " AND " );
            }
            else {
                started = true;
            }
            buffer.append( AlarmValueDBRep.MANAGED_OBJECT_INSTANCE_DBN + " = ? " );
        }
        if ( isPopulated( MANAGED_OBJECT_CLASS ) ) {
            if ( started == true ) {
                buffer.append( " AND " );
            }
            else {
                started = true;
            }
            buffer.append( AlarmValueDBRep.MANAGED_OBJECT_CLASS_DBN + " = ? " );
        }
        if ( isPopulated( ALARM_TYPE ) ) {
            if ( started == true ) {
                buffer.append( " AND " );
            }
            else {
                started = true;
            }
            buffer.append( AlarmValueDBRep.ALARM_TYPE_DBN + " = ? " );
        }
        if ( isPopulated( PERCEIVED_SEVERITY ) ) {
            if ( started == true ) {
                buffer.append( " AND " );
            }
            else {
                started = true;
            }
            buffer.append( AlarmValueDBRep.PERCEIVED_SEVERITY_DBN + " = ? " );
        }
        if ( isPopulated( ALARM_ACK_STATE ) ) {
            if ( started == true ) {
                buffer.append( " AND " );
            }
            else {
                started = true;
            }
            buffer.append( AlarmValueDBRep.ALARM_ACK_STATE_DBN + " = ? " );
        }
        return buffer.toString();
    }

    public String[] getClauseMEVAttributeNames() {
        ArrayList names = new ArrayList(9);
        if ( isPopulated( TIME_CONSTRAINT ) ) {
            names.add(AlarmValue.ALARM_RAISED_TIME);
            names.add(AlarmValue.ALARM_CLEARED_TIME);
            names.add(AlarmValue.ACK_TIME);
            names.add(AlarmValue.ALARM_CHANGED_TIME);
        }
        if ( isPopulated( MANAGED_OBJECT_INSTANCE ) ) {
            names.add(AlarmValue.MANAGED_OBJECT_INSTANCE);
        }
        if ( isPopulated( MANAGED_OBJECT_CLASS ) ) {
            names.add(AlarmValue.MANAGED_OBJECT_CLASS);
        }
        if ( isPopulated( ALARM_TYPE ) ) {
            names.add(AlarmValue.ALARM_TYPE);
        }
        if ( isPopulated( PERCEIVED_SEVERITY ) ) {
            names.add(AlarmValue.PERCEIVED_SEVERITY);
        }
        if ( isPopulated( ALARM_ACK_STATE ) ) {
            names.add(AlarmValue.ALARM_ACK_STATE);
        }
        return (String[]) names.toArray( new String[0] );
    }

    public ManagedEntityValue buildManagedEntityTemplate() {
        AlarmValue alarm = new AlarmValueImpl();
        if ( isPopulated( MANAGED_OBJECT_INSTANCE ) ) {
            alarm.setManagedObjectInstance( managedObjectInstance );
        }
        if ( isPopulated( MANAGED_OBJECT_CLASS ) ) {
            alarm.setManagedObjectClass( managedObjectClass );
        }
        if ( isPopulated( ALARM_TYPE ) ) {
            alarm.setAlarmType( alarmType );
        }
        if ( isPopulated( PERCEIVED_SEVERITY ) ) {
            alarm.setPerceivedSeverity( perceivedSeverity );
        }
        if ( isPopulated( ALARM_ACK_STATE ) ) {
            alarm.setAlarmAckState( alarmAckState );
        }
        if ( isPopulated( TIME_CONSTRAINT ) ) {
            alarm.setAlarmRaisedTime( timeConstraint );
            alarm.setAlarmClearedTime( timeConstraint );
            alarm.setAckTime( timeConstraint );
            alarm.setAlarmChangedTime( timeConstraint );
        }
        return alarm;
    }

    private void resetManagedObjectClass() {
        managedObjectClass = null;
        return;
    }

    private void resetManagedObjectInstance() {
        managedObjectInstance = null;
        return;
    }

    private void resetAlarmType() {
        alarmType = null;
    }

    private void resetPerceivedSeverity() {
        perceivedSeverity = 0;
        return;
    }

    private void resetTimeConstraint() {
        timeConstraint = null;
        return;
    }

    private void resetAlarmAckState() {
        alarmAckState = 0;
        return;
    }

    // interface for dealing with generic access

    static class AlarmAckStateAttr implements AttrObjectHandler {
        public Object getObject( QueryValue query ) {
            return new Short ( ((QueryByFilterableAttributesValue)query).getAlarmAckState() );
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                Short val = (Short)obj;
                ((QueryByFilterableAttributesValue)query).setAlarmAckState(val.shortValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetAlarmAckState();
            return;
        }
    }

    static class AlarmTypeAttr implements AttrObjectHandler {
        public Object getObject( QueryValue query ) {
            return ((QueryByFilterableAttributesValue)query).getAlarmType();
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                ((QueryByFilterableAttributesValue)query).setAlarmType((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetAlarmType();
            return;
        }
    }

    static class ManagedObjectInstanceAttr implements AttrObjectHandler
    {
        public Object getObject( QueryValue query ) {
            return ((QueryByFilterableAttributesValue)query).getManagedObjectInstance();
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                ((QueryByFilterableAttributesValue)query).setManagedObjectInstance((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetManagedObjectInstance();
            return;
        }
    }

    static class ManagedObjectClassAttr implements AttrObjectHandler
    {
        public Object getObject( QueryValue query ) {
            return ((QueryByFilterableAttributesValue)query).getManagedObjectClass();
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                ((QueryByFilterableAttributesValue)query).setManagedObjectClass((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetManagedObjectClass();
            return;
        }
    }

    static class PerceivedSeverityAttr implements AttrObjectHandler
    {
        public Object getObject( QueryValue query ) {
            return new Short ( ((QueryByFilterableAttributesValueImpl)query).getPerceivedSeverity() );
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                Short val = (Short)obj;
                ((QueryByFilterableAttributesValueImpl)query).setPerceivedSeverity(val.shortValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetPerceivedSeverity();
            return;
        }
    }

    static class TimeConstraintAttr implements AttrObjectHandler
    {
        public Object getObject( QueryValue query ) {
            return ((QueryByFilterableAttributesValue)query).getTimeConstraint();
        }
        public void setObject( QueryValue query, Object obj )
        throws IllegalArgumentException {
            try {
                ((QueryByFilterableAttributesValue)query).setTimeConstraint((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( QueryValue query ) {
            ((QueryByFilterableAttributesValueImpl)query).resetTimeConstraint();
            return;
        }
    }

}