package ossj.qos.fmri;

import javax.oss.util.IRPEvent;
import javax.oss.fm.monitor.AlarmEvent;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmKey;
import javax.oss.ManagedEntityValue;

import java.lang.reflect.Field;
import java.util.*;

/**
 * BaseAlarmEvent
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public abstract class BaseAlarmEvent extends BaseEvent implements AlarmEvent {

    private String alarmType = null;
    private AlarmKey alarmKey = null;
    private short perceivedSeverity = 0;
    private short probableCause = 0;

    private static HashSet alarmTypeSet = null;
    private static HashSet probableCauseSet = null;
    private static HashSet perceivedSeveritySet = null;
    
     // Map containing a handler representing every attribute
    protected static TreeMap attributeHandlerMap = null;
    
    // Manager that is responsible for the attribute descriptors   
    protected static AttributeManager attributeManager = null;

    /** 
     * Static Initializer for HashSets that used to validate 
     * ProbableCause, AlarmType, and PerceivedSeverity and generic handlers
     */
    static {
        
        // Static Initializer for attribute handlers dealing with generic access
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(AlarmEvent.ALARM_TYPE, new EventAlarmTypeAttr());
        attributeHandlerMap.put(AlarmEvent.KEY, new EventAlarmKeyAttr());
        attributeHandlerMap.put(AlarmEvent.PERCEIVED_SEVERITY, new EventPerceivedSeverityAttr());
        attributeHandlerMap.put(AlarmEvent.PROBABLE_CAUSE, new EventProbableCauseAttr());
        
        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( AlarmEvent.ALARM_TYPE, new AttributeDescriptor() );
        attributeDescriptorMap.put( AlarmEvent.KEY, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmEvent.PERCEIVED_SEVERITY, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmEvent.PROBABLE_CAUSE, new AttributeDescriptor());
        
        
        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
        
        
        // HashSet for supported AlarmTypes
        alarmTypeSet = new HashSet();
        try {
            Class alarmTypeClass = Class.forName( "javax.oss.fm.monitor.AlarmType" );
            Field[] fields = alarmTypeClass.getFields();
            for ( int i=0, len=fields.length; i<len; i++ ) {
                alarmTypeSet.add( fields[i].get(null) );
            }
        }
        catch ( Exception exClass ) {}

        // HashSet for supported ProbableCause values
        probableCauseSet = new HashSet();
        try {
            Class probableCauseClass = Class.forName( "javax.oss.fm.monitor.ProbableCause" );
            Field[] fields = probableCauseClass.getFields();
            for ( int i=0, len=fields.length; i<len; i++ ) {
                probableCauseSet.add( fields[i].get(null) );
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

    /**
     * BaseAlarmEvent default constructor.
     */
    public BaseAlarmEvent() {
        super();
    }
    
    /**
     * BaseAlarmEvent - constructor.
     */
    public BaseAlarmEvent(AlarmValue alarm) {
        super(alarm);
        if ( alarm.isPopulated( AlarmValue.ALARM_TYPE ) ) {
            setAlarmType( alarm.getAlarmType());
        }
        if ( alarm.isPopulated( AlarmValue.PERCEIVED_SEVERITY ) ) {
            setPerceivedSeverity( alarm.getPerceivedSeverity() );
        }
        if ( alarm.isPopulated( AlarmValue.PROBABLE_CAUSE ) ) {
            setProbableCause( alarm.getProbableCause() );
        }
        if ( alarm.isPopulated( ManagedEntityValue.KEY ) ) {
            setAlarmKey ( alarm.getAlarmKey() );
        }
    }
    
    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return BaseAlarmEvent.attributeManager;
    }
    
    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) { 
            handler = (AttrObjectHandler)BaseAlarmEvent.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    
    // End of Methods that MUST BE OVERIDDEN in the derived classes

    /**
     * Returns the alarm type.
     * <p>
     * It identifies alarm type.
     * 
     * @return <code>String</code> - alarm type.
     * @see #setAlarmType
     */
    public String getAlarmType() {
        if ( isPopulated( AlarmEvent.ALARM_TYPE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            AlarmEvent.ALARM_TYPE );
        }
        return alarmType;
    }

    /**
     * Sets the alarm type.
     * 
     * @param alarm_type Defines the alarm type.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmType
     */
    public void setAlarmType(String alarm_type) throws java.lang.IllegalArgumentException {
        if ( alarmTypeSet.contains( alarm_type ) ) {
            alarmType = alarm_type;
            populatedAttributeNames.add( AlarmEvent.ALARM_TYPE );
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            alarm_type );
        }
        return;
    }

   
    /**
     * Returns the alarm key.
     * <p>
     * It identifies at most one Alarm information in the Alarm List.
     * 
     * @return <code>AlarmKey</code> - identifies alarm information in the alarm list.
     * @see #setAlarmKey
     */
    public AlarmKey getAlarmKey() {
        if ( isPopulated( AlarmEvent.KEY ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            AlarmEvent.KEY );
        }
        return alarmKey;
    }

    /**
     * Sets the alarm id.
     * 
     * @param alarm_id Identifies the alarm information in the alarm list.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmKey
     */
    public void setAlarmKey(AlarmKey alarm_id) throws java.lang.IllegalArgumentException {
        if ( alarm_id == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmEvent.KEY );
        }
        alarmKey = alarm_id;
        populatedAttributeNames.add( AlarmEvent.KEY );
        return;
    }

     /**
     * Returns the perceived severity.
     * <p>
     * It indicates the relative level of urgency for operator attention.
     * valid values are defined in the interface <code>PerceivedSeverity</code>.
     * 
     * @return <code>short</code> - perceived severity, valid values are defined in
     *  interface {@link PerceivedSeverity}.
     * @see #setPerceivedSeverity
     */
    public short getPerceivedSeverity() {
        if ( isPopulated( AlarmEvent.PERCEIVED_SEVERITY ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            AlarmEvent.PERCEIVED_SEVERITY );
        }
        return perceivedSeverity;
    }

    /**
     * Sets the perceived severity.
     * 
     * @param perceived_severity Defines perceived severity, valid values
     * are defined in interface {@link PerceivedSeverity}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getPerceivedSeverity
     */
    public void setPerceivedSeverity(short perceived_severity) throws java.lang.IllegalArgumentException {
        Short objSeverity = new Short( perceived_severity );
        if ( perceivedSeveritySet.contains( objSeverity ) ) {
            perceivedSeverity = perceived_severity;
            populatedAttributeNames.add( AlarmEvent.PERCEIVED_SEVERITY );
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            AlarmEvent.PERCEIVED_SEVERITY );
        }
        return;
    }

    /**
     * Returns the probable cause.
     * <p>
     * It qualifies alarm and provides further information than eventType.
     * See <code>javax.oss.fm.methods.ProbableCause</code>. This list
     * is extensive.  It is recommended that the system should use the
     * list as is and not to extend it. It is noted that the system can
     * privately (outside the scope of this API) define values for
     * specificProblem that provides semantics not conveyed by ProbableCause.
     * A special ProbableCause value (–1) indicates that this alternative
     * is valid. This parameter-attribute value shall be single-value and
     * of simple type such as integer or string. See 3G TS 32.111-2 [4].
     * 
     * @return <code>short</code> - probable cause, valid values are defined in
     *  interface {@link ProbableCause}.
     * @see #setProbableCause
     */
    public short getProbableCause() {
        if ( isPopulated( AlarmEvent.PROBABLE_CAUSE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            AlarmEvent.PROBABLE_CAUSE );
        }
        return probableCause;
    }

    /**
     * Sets the probable cause.
     * 
     * @param probable_cause Defines probable cause, valid values are
     *  defined in interface {@link ProbableCause}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getProbableCause
     */
    public void setProbableCause(short probable_cause) throws java.lang.IllegalArgumentException {
        Short objCause = new Short( probable_cause );
        if ( probableCauseSet.contains( objCause ) ) {
            probableCause = probable_cause;
            populatedAttributeNames.add( AlarmEvent.PROBABLE_CAUSE );
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            AlarmEvent.PROBABLE_CAUSE );
        }
        return;
    }
    
     /**
     * Makes an empty instance of AlarmKey.
     *
     * @return <code>AlarmKey<code> - an AlarmKey that represents an empty alarm key.
     * @see javax.oss.fm.monitor.AlarmKey
     */
     public AlarmKey makeAlarmKey() {
        return new AlarmKeyImpl();
     }  

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        BaseAlarmEvent obj = (BaseAlarmEvent) super.clone(); 
        obj.alarmKey = (AlarmKey)alarmKey.clone();
        return obj;
    }
    
    private void resetAlarmKey() {
        alarmKey = null;
    }
    
    private void resetAlarmType() {
        alarmType = null;
    }
    
     private void resetPerceivedSeverity() {
        perceivedSeverity = 0;
        return;
    }

    private void resetProbableCause() {
        probableCause = 0;
        return;
    }
    
    
     // interface for dealing with generic access
    
    static class EventAlarmKeyAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((AlarmEvent)event).getAlarmKey();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((AlarmEvent) event).setAlarmKey((AlarmKey) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseAlarmEvent)event).resetAlarmKey();
            return;
        }
    }
    
    static class EventAlarmTypeAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((AlarmEvent)event).getAlarmType();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((AlarmEvent) event).setAlarmType((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseAlarmEvent)event).resetAlarmType();
            return;
        }
    }
    
    static class EventPerceivedSeverityAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return new Short ( ((AlarmEvent)event).getPerceivedSeverity() );
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Short val = (Short)obj;
                ((AlarmEvent) event).setPerceivedSeverity(val.shortValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseAlarmEvent)event).resetPerceivedSeverity();
            return;
        }
    }

    static class EventProbableCauseAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return new Short ( ((AlarmEvent)event).getProbableCause() );
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Short val = (Short)obj;
                ((AlarmEvent) event).setProbableCause( val.shortValue() );
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseAlarmEvent)event).resetProbableCause();
            return;
        }
    }

}
