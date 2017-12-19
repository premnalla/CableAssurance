package ossj.qos.fmri;

import javax.oss.fm.monitor.NotifyClearedAlarmEvent;
import javax.oss.fm.monitor.CorrelatedNotificationValue;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.AlarmEvent;
import javax.oss.fm.monitor.PerceivedSeverity;
import javax.oss.fm.monitor.NotifyClearedAlarmEventPropertyDescriptor;
import javax.oss.util.IRPEvent;

import java.util.TreeMap;

/**
 * NotifyClearedAlarmEvent 
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyClearedAlarmEventImpl extends BaseAlarmEvent implements NotifyClearedAlarmEvent{

    private CorrelatedNotificationValue[] corNotifications = null;
    
    // Map containing a handler representing every attribute
    private static TreeMap attributeHandlerMap = null;
    
    // Manager that is responsible for the attribute descriptors   
    private static AttributeManager attributeManager = null;

     /**
     * NotifyClearedAlarmEventImpl - default constructor.
     */
    public NotifyClearedAlarmEventImpl() {
        super();
    }
    
    /** 
     * NotifyClearedAlarmEvent - constructor 
     */
    public NotifyClearedAlarmEventImpl( AlarmValue alarm ) {
        super(alarm);
        if ( alarm.isPopulated( AlarmValue.ALARM_CLEARED_TIME ) ) {
            setEventTime( alarm.getAlarmClearedTime() );
        }
        if ( alarm.isPopulated( AlarmValue.CORRELATED_NOTIFICATIONS ) ) {
            setCorrelatedNotifications( alarm.getCorrelatedNotifications() );
        }
    }
    
    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS, new EventCorrelatedNotificationsAttr());
        
        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS, new AttributeDescriptor(true,true,true) );
        
        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseAlarmEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }
    
    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return NotifyClearedAlarmEventImpl.attributeManager;
    }
    
    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) { 
            handler = (AttrObjectHandler)NotifyClearedAlarmEventImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes


     /**
     * Returns the correlated notifications.
     * <p>
     * It identifies a set of notifications to which this notification is
     * considered to be correlated. 
     *
     * @return <code>CorrelatedNotificationValue[]</code> - list of correlated notifications.
     * @exception javax.oss.UnsupportedAttributeException Is thrown to report that
     * the attribute not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported, 
     * and the attribute has not been populated.
     * @see #setCorrelatedNotifications
     */
    public CorrelatedNotificationValue[] getCorrelatedNotifications()
        throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS );
        }  
        return corNotifications;
    }

    /**
     * Sets the correlated notifications.
     *
     * @param notifications Defines a set of notifications to which this notification
     * is correlated. For further information see 3G TS 32.111-2 [4].
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown to report that
     * the attribute not supported.
     * @see #getCorrelatedNotifications
     */
    public void setCorrelatedNotifications( CorrelatedNotificationValue[] notifications )
        throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( notifications == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS );
        }
        corNotifications = notifications;
        populatedAttributeNames.add( NotifyClearedAlarmEvent.CORRELATED_NOTIFICATIONS );
        return;
    }
    
    /**
     * Returns a CorrelatedNotificationValue.
     * @return <code>CorrelatedNotificationValue</code> - a correlated notification value.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.CorrelatedNotificationValue
     */
    public CorrelatedNotificationValue makeCorrelatedNotificationValue()
        throws javax.oss.UnsupportedAttributeException {
        return new CorrelatedNotificationValueImpl();
    }
    
    /**
     * Sets the perceived severity. Overloaded from BaseAlarmEvent to only 
     * allow a CLEARED severity.
     * 
     * @param perceived_severity Defines perceived severity, valid values
     * are defined in interface {@link PerceivedSeverity}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getPerceivedSeverity
     */
    public void setPerceivedSeverity(short perceived_severity) throws java.lang.IllegalArgumentException {
        if ( perceived_severity == PerceivedSeverity.CLEARED ) {
            super.setPerceivedSeverity( perceived_severity);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            AlarmEvent.PERCEIVED_SEVERITY  + ". Severity must equal CLEARED." );
        }
        return;
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        NotifyClearedAlarmEventImpl obj = (NotifyClearedAlarmEventImpl)super.clone();
        obj.corNotifications = (CorrelatedNotificationValue[]) corNotifications.clone();  
        return obj;
    }

    private void resetCorrelatedNotifications() {
        corNotifications = null;
        return;
    }
    
    // static inner classes that represent each attribute
    
    static class EventCorrelatedNotificationsAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyClearedAlarmEvent)event).getCorrelatedNotifications();
        }

        public void setObject( IRPEvent event, Object obj ) {
            try {
                ((NotifyClearedAlarmEvent)event).setCorrelatedNotifications((CorrelatedNotificationValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyClearedAlarmEventImpl)event).resetCorrelatedNotifications();
            return;
        }
    }    

}