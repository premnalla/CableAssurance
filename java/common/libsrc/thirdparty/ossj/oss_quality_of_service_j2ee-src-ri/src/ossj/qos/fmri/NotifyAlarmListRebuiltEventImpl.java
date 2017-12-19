package ossj.qos.fmri;

import javax.oss.fm.monitor.NotifyAlarmListRebuiltEvent;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.util.IRPEvent;
import javax.oss.fm.monitor.NotifyAlarmListRebuiltEventPropertyDescriptor;
import java.util.TreeMap;


/**
 * NotifyAlarmListRebuiltEventImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class NotifyAlarmListRebuiltEventImpl extends BaseEvent implements NotifyAlarmListRebuiltEvent {

    private String reason = null;
    
    // Map containing a handler representing every attribute
    private static TreeMap attributeHandlerMap = null;
    
    // Manager that is responsible for the attribute descriptors   
    private static AttributeManager attributeManager = null;

    /**
     * Default constructor.
     */
    public NotifyAlarmListRebuiltEventImpl() {
        super();
    }
    
    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(NotifyAlarmListRebuiltEvent.REASON, new ReasonAttr());
        
        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( NotifyAlarmListRebuiltEvent.REASON, new AttributeDescriptor() );
        
        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }
    
    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return NotifyAlarmListRebuiltEventImpl.attributeManager;
    }
    
    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) { 
            handler = (AttrObjectHandler)NotifyAlarmListRebuiltEventImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes

    /**
     * Sets reason for alarm list rebuild.
     *
     * @param rebuild_reason  A <code>String</code> representing the Alarm List rebuilt reason.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getReason
     */
    public void setReason(String rebuild_reason) throws java.lang.IllegalArgumentException {
        if ( rebuild_reason == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyAlarmListRebuiltEvent.REASON );
        }
        populatedAttributeNames.add( NotifyAlarmListRebuiltEvent.REASON );
        reason = rebuild_reason;
        return;
    }

    /**
     * Returns reason for alarm list rebuild.
     *
     * @return <code>String</code> - Alarm List rebuilt reason. One valid reason is "indeterminate".
     * @see #setReason
     */
    public String getReason() {
        if ( isPopulated( NotifyAlarmListRebuiltEvent.REASON ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            NotifyAlarmListRebuiltEvent.REASON );
        }
        return reason;
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        NotifyAlarmListRebuiltEventImpl obj = (NotifyAlarmListRebuiltEventImpl)super.clone();
        return obj;
    }

    private void resetReason() {
        reason = null;
        return;
    }

    // static inner classes that represent each attribute
    
    static class ReasonAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyAlarmListRebuiltEvent)event).getReason();
        }
        
        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyAlarmListRebuiltEvent)event).setReason((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        
        public void clearObject( IRPEvent event ) {
            ((NotifyAlarmListRebuiltEventImpl)event).resetReason();
            return;
        }
    }
}