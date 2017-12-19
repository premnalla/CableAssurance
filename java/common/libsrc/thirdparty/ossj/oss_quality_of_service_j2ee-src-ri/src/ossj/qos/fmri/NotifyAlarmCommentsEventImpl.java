package ossj.qos.fmri;

import javax.oss.util.IRPEvent;
import javax.oss.fm.monitor.NotifyAlarmCommentsEvent;
import javax.oss.fm.monitor.CommentValue;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.NotifyAlarmCommentsEventPropertyDescriptor;

import java.util.TreeMap;

/**
 * NotifyAlarmCommentsEventImpl
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyAlarmCommentsEventImpl  extends BaseAlarmEvent implements NotifyAlarmCommentsEvent {

    private CommentValue[] comments;
    
    // Map containing a handler representing every attribute
    private static TreeMap attributeHandlerMap = null;
    
    // Manager that is responsible for the attribute descriptors   
    private static AttributeManager attributeManager = null;
    
     // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(NotifyAlarmCommentsEvent.COMMENTS, new EventCommentsAttr());
        
        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( NotifyAlarmCommentsEvent.COMMENTS, new AttributeDescriptor() );
        
        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseAlarmEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }
    
    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return NotifyAlarmCommentsEventImpl.attributeManager;
    }
    
    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) { 
            handler = (AttrObjectHandler)NotifyAlarmCommentsEventImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes


    /**
     * NotifyAlarmCommentsEventImpl - default constructor.
     */ 
    public NotifyAlarmCommentsEventImpl() {
        super();
    }
    
    /** 
     * NotifyAlarmCommentsEventImpl - constructor 
     */
    public NotifyAlarmCommentsEventImpl( AlarmValue alarm ) {
        super(alarm);
        if ( alarm.isPopulated(AlarmValue.COMMENTS) ) {
            setComments( alarm.getComments() );
        }
    }

    /**
     * Sets comment information.
     *
     * @param comment_value An array of CommentValues representing all the 
     * comments associated with the alarm.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see CommentValue
     * @see #getComment
     */
    public void setComments(CommentValue[] comment_values) throws java.lang.IllegalArgumentException {
        if ( comment_values == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyAlarmCommentsEvent.COMMENTS );
        }
        populatedAttributeNames.add( NotifyAlarmCommentsEvent.COMMENTS );
        comments = comment_values;
        return;
    }

    /**
     * Returns comment information.
     * 
     * @return <code>CommentValue[]</code> - the comment information.
     * @see CommentValue
     * @see #setComment
     */
    public CommentValue[] getComments() {
        if ( isPopulated( NotifyAlarmCommentsEvent.COMMENTS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  + 
            NotifyAlarmCommentsEvent.COMMENTS );
        }
        return comments;
    }
    
     /**
     * Returns a CommentValue.
     * @return <code>CommentValue</code> - a comment value.
     * @see javax.oss.fm.monitor.CommentValue
     */
    public javax.oss.fm.monitor.CommentValue makeCommentValue() {
        return new CommentValueImpl();
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        NotifyAlarmCommentsEventImpl obj = (NotifyAlarmCommentsEventImpl)super.clone();
        obj.comments = (CommentValue[])comments.clone();
        return obj;
    }
    
     private void resetComments() {
        comments = new CommentValue[0];
        return;
    }

    // static inner classes that represent each attribute
    
    static class EventCommentsAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyAlarmCommentsEvent)event).getComments();
        }
        
        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyAlarmCommentsEvent)event).setComments((CommentValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }
        
        public void clearObject( IRPEvent event ) {
            ((NotifyAlarmCommentsEventImpl)event).resetComments();
            return;
        }
    } 
}