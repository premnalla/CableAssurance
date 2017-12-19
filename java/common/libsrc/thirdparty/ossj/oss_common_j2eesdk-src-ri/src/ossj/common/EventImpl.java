/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common;


import java.util.Date;
import javax.oss.Event;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.Event</CODE> interface.
 *
 * @see javax.oss.AttributeAccess
 *
 * @see javax.oss.Event
 *
 * @see javax.oss.Event
 *
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2
 * @since September 2005
 */


public class EventImpl
        extends ossj.common.AttributeAccessImpl
        implements Event {
    
    /**
     * Constructs a new EventImpl with empty attributes.
     *
     */
    
    public EventImpl() {
        super();
        initAttributeTypes();
        setApplicationDN("");
        setEventTime(new Date(System.currentTimeMillis()));
    }
    
    /**
     * Constructs a new EventImpl with the given Application DN.
     * @param applicationDN, the string representing the application DN
     *
     */
    
    public EventImpl(String applicationDN){
        super();
        initAttributeTypes();
        setApplicationDN(applicationDN);
        setEventTime(new java.util.Date(System.currentTimeMillis()));
    }
    
    
    private static final String[] attributeNames = {
        Event.APPLICATION_DN,
        Event.EVENT_TIME,
        Event.MANAGED_OBJECT_CLASS,
        Event.MANAGED_OBJECT_INSTANCE
    };
    
    private static final String[] settableAttributeNames = {
        Event.APPLICATION_DN,
        Event.EVENT_TIME,
        Event.MANAGED_OBJECT_CLASS,
        Event.MANAGED_OBJECT_INSTANCE
    };
    
    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (EventImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }
    
    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (EventImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(EventImpl.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }
    
    /**
     * Holds the Attribute manager that manage the optional fields
     */
    private static AttributeManager attributemanager = null;
    
    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }
    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }
    
    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("managedObjectClass", list);
        list[0] = "java.util.Date";
        addAttributeTypes("eventTime", list);
        list[0] = "java.lang.String";
        addAttributeTypes("managedObjectInstance", list);
        list[0] = "java.lang.String";
        addAttributeTypes("applicationDN", list);
    }
    
    private java.lang.String _eventimpl_applicationDN = null;
    private java.util.Date _eventimpl_eventTime = null;
    private java.lang.String _eventimpl_managedObjectClass = null;
    private java.lang.String _eventimpl_managedObjectInstance = null;
    
    
    /**
     * The DN of the Application sending the Event.
     * The format of this DN is: <p>
     *
     * <i>deployment dependent top context</i>
     * /System=<i>vendor</i>-<i>id</i>
     * /ApplicationType=<i>api-name</i>
     * <p>
     * under which the components the building block
     * sending the event are deployed.
     * @param value Destinguished Name of the Application Type
     * @throws IllegalArgumentException if format is not correct
     */
    
    public void setApplicationDN(java.lang.String value)
    throws java.lang.IllegalArgumentException	{
        
        setDirtyAttribute(Event.APPLICATION_DN);
        _eventimpl_applicationDN = value;
    }
    
    
    /**
     * The DN of the Application sending the Event.
     * The format of this DN is: <p>
     *
     * <i>deployment dependent top context</i>
     * /System=<i>vendor</i>-<i>id</i>
     * /ApplicationType=<i>api-name</i>
     * <p>
     * under which the components the building block
     * sending the event are deployed.
     *
     * @return a string representation of the ApplicationDN
     */
    
    public java.lang.String getApplicationDN() {
        checkAttribute(Event.APPLICATION_DN);
        return _eventimpl_applicationDN;
    }
    
    /**
     * Changes the eventTime to be equal to the given argument.
     *
     * @param value the new eventTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad or a null argument was provided to the method.
     */
    
    public void setEventTime(java.util.Date value)
    throws java.lang.IllegalArgumentException	{
        
        setDirtyAttribute(Event.EVENT_TIME);
        _eventimpl_eventTime = value;
    }
    
    
    /**
     * Returns this EventImpl's eventTime
     *
     * @return the eventTime
     *
     */
    
    public java.util.Date getEventTime() {
        checkAttribute(Event.EVENT_TIME);
        return _eventimpl_eventTime;
    }
    
    /**
     * Changes the managedObjectClass to be equal to the given argument.
     *
     * @param value the new managedObjectClass for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad or a null argument was provided to the method.
     */
    
    public void setManagedObjectClass(java.lang.String value)
    throws java.lang.IllegalArgumentException	{
        
        setDirtyAttribute(Event.MANAGED_OBJECT_CLASS);
        _eventimpl_managedObjectClass = value;
    }
    
    
    /**
     * Returns this EventImpl's managedObjectClass
     *
     * @return the managedObjectClass
     *
     */
    
    public java.lang.String getManagedObjectClass()
    throws java.lang.IllegalStateException {
        checkAttribute(Event.MANAGED_OBJECT_CLASS);
        return _eventimpl_managedObjectClass;
    }
    
    /**
     * Changes the managedObjectInstance to be equal to the given argument.
     *
     * @param value the new managedObjectInstance for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad or a null argument was provided to the method.
     */
    
    public void setManagedObjectInstance(java.lang.String value)
    throws java.lang.IllegalArgumentException	{
        
        setDirtyAttribute(Event.MANAGED_OBJECT_INSTANCE);
        _eventimpl_managedObjectInstance = value;
    }
    
    
    /**
     * Returns this EventImpl's managedObjectInstance
     *
     * @return the managedObjectInstance
     *
     */
    
    public java.lang.String getManagedObjectInstance()
    throws java.lang.IllegalStateException {
        checkAttribute(Event.MANAGED_OBJECT_INSTANCE);
        return _eventimpl_managedObjectInstance;
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        Event val = null;
        val = (Event)super.clone();
        
        if( isPopulated(Event.APPLICATION_DN)) {
            if( this.getApplicationDN()!=null)
                val.setApplicationDN(((java.lang.String) new String( this.getApplicationDN())));
            else
                val.setApplicationDN( null );
        }
        
        if( isPopulated(Event.EVENT_TIME)) {
            if( this.getEventTime()!=null)
                val.setEventTime((java.util.Date)((java.util.Date) this.getEventTime()).clone());
            else
                val.setEventTime( null );
        }
        
        if( isPopulated(Event.MANAGED_OBJECT_CLASS)) {
            if( this.getManagedObjectClass()!=null)
                val.setManagedObjectClass(((java.lang.String) new String( this.getManagedObjectClass())));
            else
                val.setManagedObjectClass( null );
        }
        
        if( isPopulated(Event.MANAGED_OBJECT_INSTANCE)) {
            if( this.getManagedObjectInstance()!=null)
                val.setManagedObjectInstance(((java.lang.String) new String( this.getManagedObjectInstance())));
            else
                val.setManagedObjectInstance( null );
        }
        
        return val;
    }
    
    /**
     * Checks whether two Event are equal.
     * The result is true if and only if the argument is not null
     * and is an Event object that has the arguments.
     *
     * @param value the Object to compare with this Event
     * @return if the objects are equal; false otherwise.
     */
    
    public boolean equals(Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Event)) {
            Event argVal = (Event) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }
    
}
