package ossj.qos.fmri;

import javax.oss.fm.monitor.NotifyAckStateChangedEvent;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmAckState;
import javax.oss.fm.monitor.NotifyAckStateChangedEventPropertyDescriptor;
import javax.oss.util.IRPEvent;
import java.util.Date;
import java.util.TreeMap;

/**
 * NotifyAckStateChangedEventImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyAckStateChangedEventImpl extends BaseAlarmEvent implements NotifyAckStateChangedEvent{

    private String ackUserId = null;
    private String ackSystemId = null;
    private Date ackTime = null;
    private int ackState = 0;

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
        attributeHandlerMap.put(NotifyAckStateChangedEvent.ACK_USER_ID, new EventAckUserIdAttr());
        attributeHandlerMap.put(NotifyAckStateChangedEvent.ACK_TIME, new EventAckTimeAttr());
        attributeHandlerMap.put(NotifyAckStateChangedEvent.ALARM_ACK_STATE, new EventAlarmAckStateAttr());
        attributeHandlerMap.put(NotifyAckStateChangedEvent.ACK_SYSTEM_ID, new EventAckSystemIdAttr());

        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( NotifyAckStateChangedEvent.ACK_USER_ID, new AttributeDescriptor() );
        attributeDescriptorMap.put( NotifyAckStateChangedEvent.ACK_TIME, new AttributeDescriptor());
        attributeDescriptorMap.put( NotifyAckStateChangedEvent.ALARM_ACK_STATE, new AttributeDescriptor());
        attributeDescriptorMap.put( NotifyAckStateChangedEvent.ACK_SYSTEM_ID, new AttributeDescriptor(true,true,true));

        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseAlarmEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }

    /**
     * NotifyAckStateChangedEventImpl - default constructor.
     */
    public NotifyAckStateChangedEventImpl() {
        super();
    }

    /**
     * NotifyAckStateChangedEventImpl - constructor
     */
    public NotifyAckStateChangedEventImpl( AlarmValue alarm) {
        super(alarm);

        if ( alarm.isPopulated( AlarmValue.ACK_USER_ID ) ) {
           setAckUserId( alarm.getAckUserId() );
        }
        if ( alarm.isPopulated( AlarmValue.ACK_SYSTEM_ID ) ) {
            setAckSystemId( alarm.getAckSystemId() );
        }
        if ( alarm.isPopulated( AlarmValue.ACK_TIME ) ) {
            setAckTime( alarm.getAckTime() );
        }
        if ( alarm.isPopulated( AlarmValue.ALARM_ACK_STATE ) ) {
            setAlarmAckState( alarm.getAlarmAckState() );
        }
        if ( alarm.isPopulated( AlarmValue.ACK_TIME ) ) {
            if ( ackTime != null ) {
                setEventTime( (Date)ackTime.clone() );
            }
        }
    }

    // Methods that MUST BE OVERIDDEN in the derived classes that contain attributes
    protected AttributeManager getAttributeManager () {
        return NotifyAckStateChangedEventImpl.attributeManager;
    }

    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) { 
            handler = (AttrObjectHandler)NotifyAckStateChangedEventImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    
    // End of Methods that MUST BE OVERIDDEN in the derived classes that contain attributes

    /**
     * Sets the ack system ID.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param sid A String defines the ack system ID.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAckSystemId
     */
    public void setAckSystemId(String sid)
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        ackSystemId = sid;
        populatedAttributeNames.add( NotifyAckStateChangedEvent.ACK_SYSTEM_ID);
        return;
    }

    /**
     * Returns the ack user ID.
     * <p>
     * It identifies the system of the last user who has changed the Acknowledgement
     * state via operation <code>tryAcknowledgeAlarms</code> or <code>
     * tryUnacknowledgeAlarms</code>.
     *
     * @return <code>String</code> - ack user ID.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setAckSystemId
     */
    public String getAckSystemId()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyAckStateChangedEvent.ACK_SYSTEM_ID ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyAckStateChangedEvent.ACK_SYSTEM_ID);
        }
        return ackSystemId;
    }

    /**
     * Sets the alarm ack state.
     *
     * @param ack_state Defines ack state, valid values are defined in the
     *  interface {@link AlarmAckState}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmAckState
     */
    public void setAlarmAckState(int ack_state) throws java.lang.IllegalArgumentException {
        if ( ack_state != AlarmAckState.UNACKNOWLEDGED && ack_state != AlarmAckState.ACKNOWLEDGED ) {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG
            + NotifyAckStateChangedEvent.ALARM_ACK_STATE );
        }
        ackState = ack_state;
        populatedAttributeNames.add( NotifyAckStateChangedEvent.ALARM_ACK_STATE);
        return;
    }

    /**
     * Returns the alarm ack state.
     * <p>
     * It identifies the Acknowledgement State of the alarm. Its valid
     * values defined in interface <code>AlarmAckState</code>.
     *
     * @return <code>short</code> - alarm ack state, valid values are defined in
     *  the interface {@link AlarmAckState}.
     * @see #setAlarmAckState
     */
    public int getAlarmAckState() {
        if ( isPopulated( NotifyAckStateChangedEvent.ALARM_ACK_STATE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyAckStateChangedEvent.ALARM_ACK_STATE );
        }
        return ackState;
    }

    /**
     * Sets the ack time.
     *
     * @param ack_time Defines the ack time.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAckTime
     */
    public void setAckTime(Date ack_time) throws java.lang.IllegalArgumentException {
        ackTime = ack_time;
        populatedAttributeNames.add( NotifyAckStateChangedEvent.ACK_TIME );
        return;
    }

    /**
     * Returns the ack time.
     * <p>
     * It identifies the time of last operation <code>acknowledgeAlarms
     * </code> or <code>unacknowledgeAlarms</code>.
     *
     * @return <code>java.util.Date</code> - ack time.
     * @see #setAckTime
     */
    public Date getAckTime() {
        if ( isPopulated( NotifyAckStateChangedEvent.ACK_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyAckStateChangedEvent.ACK_TIME );
        }
        return ackTime;
    }

    /**
     * Sets the ack user ID.
     *
     * @param uid A String defines the ack user ID.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAckUserId
     */
    public void setAckUserId(String uid) throws java.lang.IllegalArgumentException {
        ackUserId = uid;
        populatedAttributeNames.add( NotifyAckStateChangedEvent.ACK_USER_ID);
        return;
    }

    /**
     * Returns the ack user ID.
     * <p>
     * It identifies the last user who has changeed the Acknowledgement
     * state via operation <code>tryAcknowledgeAlarms</code> or <code>
     * tryUnacknowledgeAlarms</code>.
     *
     * @return <code>String</code> - ack user ID.
     * @see #setAckUserId
     */
    public String getAckUserId() {
        if ( isPopulated(NotifyAckStateChangedEvent.ACK_USER_ID) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            NotifyAckStateChangedEvent.ACK_USER_ID);
        }
        return ackUserId;
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        NotifyAckStateChangedEventImpl obj = (NotifyAckStateChangedEventImpl)super.clone();
        obj.ackTime = (Date)ackTime.clone();
        return obj;
    }

    private void resetAckUserId() {
        ackUserId = null;
        return;
    }

    private void resetAckTime() {
        ackTime = null;
        return;
    }

    private void resetAlarmAckState() {
        ackState = AlarmAckState.UNACKNOWLEDGED;
        return;
    }

    private void resetAckSystemId() {
        ackSystemId = null;
        return;
    }

    // interface for dealing with generic access

    static class EventAckTimeAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyAckStateChangedEvent)event).getAckTime();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyAckStateChangedEvent)event).setAckTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyAckStateChangedEventImpl)event).resetAckTime();
            return;
        }
    }

    static class EventAckUserIdAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyAckStateChangedEvent)event).getAckUserId();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyAckStateChangedEvent)event).setAckUserId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyAckStateChangedEventImpl)event).resetAckUserId();
            return;
        }
    }

    static class EventAckSystemIdAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyAckStateChangedEvent)event).getAckSystemId();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyAckStateChangedEvent)event).setAckSystemId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyAckStateChangedEventImpl)event).resetAckSystemId();
            return;
        }
    }

    static class EventAlarmAckStateAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return new Integer ( ((NotifyAckStateChangedEvent)event).getAlarmAckState() );
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Integer val = (Integer)obj;
                ((NotifyAckStateChangedEvent)event).setAlarmAckState(val.intValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyAckStateChangedEventImpl)event).resetAlarmAckState();
            return;
        }
    }

}