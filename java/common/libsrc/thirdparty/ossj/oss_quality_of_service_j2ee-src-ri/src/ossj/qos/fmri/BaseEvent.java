package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmValue;
import javax.oss.util.IRPEvent;
import javax.oss.Serializer;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;
import ossj.qos.util.Util;

/**
 * BaseAlarmEventImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */

public abstract class BaseEvent implements IRPEvent {

    private String managedObjectInstance = null;
    private String managedObjectClass = null;
    private Date eventTime = null;
    private String notificationId = null;
    private String appDN = null;

    // Tracks the populated attributes
    protected transient TreeSet populatedAttributeNames = null;

    // Tracks the populated attributes over the wire
    private BitSet populateAttributeVessel = null;

    // Map containing a handler representing every attribute
    protected static TreeMap attributeHandlerMap = null;

    // Manager that is responsible for the attribute descriptors
    protected static AttributeManager attributeManager = null;

    /**
     * Default constructor.
     */
    public BaseEvent() {
        // Container for determining if an attribute has been populated.
        populatedAttributeNames = new TreeSet();
        // default event time setting
        setEventTime( new Date() );
    }

    public BaseEvent( AlarmValue alarm ) {
        this();
        if ( alarm.isPopulated( AlarmValue.MANAGED_OBJECT_INSTANCE ) ) {
            setManagedObjectInstance( alarm.getManagedObjectInstance() );
        }
        if ( alarm.isPopulated( AlarmValue.MANAGED_OBJECT_CLASS ) ) {
            setManagedObjectClass( alarm.getManagedObjectClass() );
        }
        if ( alarm.isPopulated( AlarmValue.NOTIFICATION_ID ) ) {
            setNotificationId( alarm.getNotificationId() );
        }
        // don't automatically set the application dn
    }

    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(IRPEvent.MANAGED_OBJECT_CLASS, new ManagedObjectClassAttr());
        attributeHandlerMap.put(IRPEvent.MANAGED_OBJECT_INSTANCE, new ManagedObjectInstanceAttr());
        attributeHandlerMap.put(IRPEvent.NOTIFICATION_ID, new NotificationIdAttr());
        attributeHandlerMap.put(IRPEvent.APPLICATION_DN, new AppDNAttr());
        attributeHandlerMap.put(IRPEvent.EVENT_TIME, new EventTimeAttr());

        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( IRPEvent.MANAGED_OBJECT_CLASS, new AttributeDescriptor() );
        attributeDescriptorMap.put(IRPEvent.MANAGED_OBJECT_INSTANCE, new AttributeDescriptor());
        attributeDescriptorMap.put(IRPEvent.NOTIFICATION_ID, new AttributeDescriptor());
        attributeDescriptorMap.put(IRPEvent.APPLICATION_DN, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put(IRPEvent.EVENT_TIME, new AttributeDescriptor());

        attributeManager = new AttributeManager();
        // No need to set the parent since this is the BASE CLASS.
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }

    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return BaseEvent.attributeManager;
    }

    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) {
            handler = (AttrObjectHandler)BaseEvent.attributeHandlerMap.get(handlerName);
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes

    // Methods that deal with the populated aspects

    private void writeObject( ObjectOutputStream out )
    throws IOException {
        String[] names = getSupportedAttributeNames();
        populateAttributeVessel = new BitSet( names.length );
        for ( int i=0, len=names.length; i<len; i++ ) {
            if ( populatedAttributeNames.contains(names[i]) ) {
                populateAttributeVessel.set(i);
            }
        }
        out.defaultWriteObject();
    }

    private void readObject( ObjectInputStream in )
    throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        populateAttributeSet();
    }

    private void populateAttributeSet() {
        String[] names = getSupportedAttributeNames();
        populatedAttributeNames = new TreeSet();
        if ( populateAttributeVessel != null ) {
            for ( int i=0, len=names.length; i<len; i++ ) {
                if ( populateAttributeVessel.get(i) ) {
                    populatedAttributeNames.add(names[i]);
                }
            }
        }
        return;
    }

    public void setFullyPopulated() {
        String[] names = getSupportedAttributeNames();
        for ( int i=0, len=names.length; i<len; i++ ) {
            populatedAttributeNames.add( names[i] );
        }
        return;
    }

    // End of Methods that deal with the populated aspects

    private AttributeDescriptor getAttributeDescriptor( String attributeName ) {
        AttributeManager manager = getAttributeManager();
        return manager.getAttributeDescriptor( attributeName );
    }

    public String[] getSupportedAttributeNames() {
        AttributeManager manager = getAttributeManager();
        return (String[]) manager.getSupportedAttributes().toArray( new String[0] );
    }

    // begin => Attribute Acces Methods

    /**
     * This method returns the value of the specified attribute.
     * @param attributeName the attribute's name
     * @return The attribute's value. Primitive types are wrapped in their respective classes.
     * @exception java.lang.IllegalArgumentException
     * (the attribute name is null or is not recognized)
     * @exception java.lang.IllegalStateException
     * (the attribute is not populated)
     * @exception java.lang.UnsupportedAttributeException
     * (the attribute is optional and not supported)
     *
     */
    public Object getAttributeValue(String attributeName)
    throws java.lang.IllegalArgumentException,
    java.lang.IllegalStateException,
    javax.oss.UnsupportedAttributeException {
        Object obj = null;
        AttrObjectHandler handler = getHandler(attributeName);
        //System.out.println( Util.printObject( attributeName ) );
        if ( handler != null ) {
            obj = handler.getObject(this);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName );
        }
        return obj;
    }

    /**
     * Assigns a new value to an attribute. <p>
     * Even though some attributes
     * may be readonly in the server implementation, they can be set here nontheless.
     * This is because value objects are also used as templates for a "query by template".
     * To see which attributes can be set in the server implementation, the client needs to call
     * <CODE>getSettableAttributeNames()</CODE>
     * @param attributeName The attribute's name which shall be changed
     * @param Value The attribute's new value. This can either be:
     * <UL>
     * <LI>An Object which can be casted to the real type of <CODE>attributesName</CODE>
     * <LI>A wrapper class for primitive types, i.e. <CODE>Integer</CODE> instead of <CODE>int</CODE>.
     * @exception java.lang.IllegalArgumentException
     * (the attribute name is null or is not recognized or the value is bad)
     * @exception java.lang.UnsupportedAttributeException
     * (the attribute is optional and is not supported)
     */
    public void setAttributeValue(String attributeName, Object Value)
    throws java.lang.IllegalArgumentException,
    javax.oss.UnsupportedAttributeException {

        AttrObjectHandler handler = getHandler(attributeName);
        if ( handler != null ) {
            handler.setObject(this, Value);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName );
        }
        return;
    }

    /**
     * Returns all attribute names, which are available in this value object.
     * <p>
     * Use
     * one of the returned names to access the generic methods <CODE>getAttributeValue(...)</CODE>
     * and <CODE>setAttributeValue(...)</CODE>.
     * <p>This method may be used by generic
     * clients to obtain information on the attributes. It does not say anything about
     * the state of an attribute, i.e. if it is populated or not.
     * @return the array contains all attribute names in no particular order.
     */
    public String [] getAttributeNames() {
        AttributeManager manager = getAttributeManager();
        return (String[]) manager.getAttributeNames().toArray( new String[0] );
    }

    public String [] getSettableAttributeNames() {
        AttributeManager manager = getAttributeManager();
        return (String[]) manager.getSettableAttributes().toArray( new String[0] );
    }

    /**
     * Gets all attribute names, which attribute values contain something
     * meaningful.
     * <p>Although an attribute is populated, it can be <CODE>null</CODE>!
     * @return all names of attributes, which contain some data.
     * When no attributes are populated an <B>empty array</B> is
     * returned.
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String [] getPopulatedAttributeNames() {
        return (String[]) populatedAttributeNames.toArray(new String [0] );
    }

    /**
     * Checks if a specific attribute is populated.
     * If the value object is fully
     * populated, i.e. <CODE>isFullyPopulated()</CODE> returns true,
     * this method returns true;
     * @param name the name of the attribute which is to be checked for population
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is no attribute with this name
     * @return true, if this value contains the attribute, false otherwise
     * @see #isFullyPopulated()
     */
    public boolean isPopulated( String attributeName )
    throws java.lang.IllegalArgumentException {
        boolean populated = false;
        AttributeDescriptor descriptor = getAttributeDescriptor( attributeName );
        if ( descriptor != null ) {
            populated = populatedAttributeNames.contains(attributeName);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName  );
        }
        return populated;
    }

    /**
     * Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        boolean fullyPopulated = false;
        //System.out.println(populatedAttributeNames.size() + " - " + getAttributeManager().getNumberOfSupportedAttributes() );
        if ( populatedAttributeNames.size() == getAttributeManager().getNumberOfSupportedAttributes() ) {
            fullyPopulated = true;
        }
        return fullyPopulated;
    }

    /*   public void setFullyPopulated() {
    populatedAttributeNames.addAll(settableAttributeNamesSet);
    return;
    } */

    /**
     * Unpopulate a Single Attribute.
     * After this call getAttribute( String attName ) must
     * raise the IllegalStateException
     *
     * @throws java.lang.IllegalArgumentException thrown, if this is not a valid attribute name
     *
     * @param attr_name The attribute which shall be unpopulated.
     * @exception java.lang.IllegalArgumentException
     * @see #unpopulateAllAttributes()
     */
    public void unpopulateAttribute( String attributeName )
    throws java.lang.IllegalArgumentException {
        AttributeDescriptor descriptor = getAttributeDescriptor( attributeName );
        if ( descriptor != null ) {
            if ( descriptor.isSettable() ) {
                AttrObjectHandler handler = getHandler(attributeName);
                handler.clearObject(this);
                populatedAttributeNames.remove(attributeName);
            }
            else {
                throw new java.lang.IllegalArgumentException( LogMessages.UNSETTABLE_ATTRIBUTE_MSG + attributeName );
            }
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName  );
        }
        return;
    }

    /**
     * Reset all the attributes to unpopulated.
     * After this call calling getAllAttributes() must
     * raise the IllegalStateException.
     */
    public void unpopulateAllAttributes() {
        // Clear out the attributes
        String[] attributeNames = getSupportedAttributeNames();
        for ( int i=0, len=attributeNames.length; i<len; i++ ) {
            AttrObjectHandler handler = getHandler( attributeNames[i] );
            handler.clearObject(this);
        }

        // Clear out mechanism tracking the populated fields */
        populatedAttributeNames.clear();
        return;
    }

    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * (if null array is provided or if one of the attribute is not recognized)
     * @exception java.lang.IllegalStateException
     * (if one of the attribute is not populated)
     * @exception java.lang.UnsupportedAttributeException
     * (if one of the attribute is not supported)
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
    throws java.lang.IllegalArgumentException,
    java.lang.IllegalStateException,
    javax.oss.UnsupportedAttributeException {

        HashMap map = new HashMap();

        if ( attributeNames == null || attributeNames.length == 0 ) {
            throw new java.lang.IllegalArgumentException( "Empty array of attribute names entered.");
        }
        else {
            for ( int i=0, len=attributeNames.length; i<len; i++ ) {
                map.put( attributeNames[i], getAttributeValue(attributeNames[i]) );
            }
        }
        return map;
    }

    /**
     * Setting multiple attribute values
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * (one of the attribute is not well formed or unrecognized)
     * @exception javax.oss.UnsupportedAttributeException
     * (one of the attribute is optional and is not supported)
     */

    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs)
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( attributeNamesAndValuePairs != null ) {
            Map beforeSetAttributes = null;
            // save the state of the value object
            try {
                beforeSetAttributes = getAllPopulatedAttributes();
            }
            catch ( java.lang.IllegalStateException ex1 ) {
                beforeSetAttributes = new HashMap();
            }
            // try and set the values ( atomic set operation )
            Iterator iter = attributeNamesAndValuePairs.keySet().iterator();
            try {
                while ( iter.hasNext() == true ) {
                    String name = (String)iter.next();
                    setAttributeValue( name, attributeNamesAndValuePairs.get( name ) );
                }
            }
            catch ( java.lang.IllegalArgumentException ex2 ) {
                // reset the state of the value object before the set and rethrow the
                // exception
                Iterator iter2 = beforeSetAttributes.keySet().iterator();
                while ( iter2.hasNext() == true ) {
                    String name = (String)iter2.next();
                    setAttributeValue( name, beforeSetAttributes.get( name ) );
                }
                throw ex2;
            }
            catch ( javax.oss.UnsupportedAttributeException ex3 ) {
                // reset the state of the value object before the set and rethrow the
                // exception
                Iterator iter2 = beforeSetAttributes.keySet().iterator();
                while ( iter2.hasNext() == true ) {
                    String name = (String)iter2.next();
                    setAttributeValue( name, beforeSetAttributes.get( name ) );
                }
                throw ex3;
            }
        }
        else {
            throw new java.lang.IllegalArgumentException( "Null Map of attributeName/value pairs entered");
        }
    }

    /**
     * Get all populated attribute values.
     */
    public java.util.Map getAllPopulatedAttributes() {
        String name[] = getPopulatedAttributeNames();
        HashMap map = new HashMap();
        for ( int i=0, len=name.length; i<len; i++ ) {
            map.put( name[i], getAttributeValue( name[i] ) );
        }
        return map;
    }

    /**
     * Provide run-time support for the discovery of optional attributes
     * support.
     *
     * @return The names of the optional attributes supported by an application.
     */
    public String[] getSupportedOptionalAttributeNames() {
        AttributeManager manager = getAttributeManager();
        return (String[]) manager.getSupportedOptionalAttributes().toArray( new String[0] );
    }
    // AttributeAccess methods => end

    /**
     * Gets the class name of the object instance where this event occurred.
     *
     * <p>
     * If the class name is not set then null is returned.
     *
     *@return String The class name object instance.
     *@see #setManagedObjectClass
     */
    public String getManagedObjectClass() throws java.lang.IllegalStateException {
        if ( isPopulated( IRPEvent.MANAGED_OBJECT_CLASS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            IRPEvent.MANAGED_OBJECT_CLASS );
        }
        return managedObjectClass;
    }

    /**
     * Sets the class name of the object instance that will issue this event.
     *
     *@param moc The class name of the object instance.
     *@see #getManagedObjectClass
     */
    public void setManagedObjectClass( String moc ) throws java.lang.IllegalArgumentException {
        if ( moc == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            IRPEvent.MANAGED_OBJECT_CLASS );
        }
        managedObjectClass = moc;
        populatedAttributeNames.add( IRPEvent.MANAGED_OBJECT_CLASS );
        return;
    }

    /**
     * Gets the distinguished name of the object instance where this event occurred.
     *
     * <p>
     * If the DN of the managed object is not set then null is returned.
     *
     *@return String The distinguished name object instance.
     *@see #setManagedObjectInstance
     */
    public String getManagedObjectInstance() throws java.lang.IllegalStateException {
        if ( isPopulated( IRPEvent.MANAGED_OBJECT_INSTANCE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            IRPEvent.MANAGED_OBJECT_INSTANCE );
        }
        return managedObjectInstance;
    }

    /**
     * Sets the distinguished name of the object instance that will issue this event.
     *
     *@param moi The distinguished name of the object instance.
     *@see #getManagedObjectInstance
     */
    public void setManagedObjectInstance( String moi )
    throws java.lang.IllegalArgumentException {
        if ( moi == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            IRPEvent.MANAGED_OBJECT_INSTANCE );
        }
        managedObjectInstance = moi;
        populatedAttributeNames.add( IRPEvent.MANAGED_OBJECT_INSTANCE);
        return;
    }

    /**
     * Returns the notification id.
     *
     * @return String The unique identifier across all notifications of a
     * particular managed entity.
     * @see #setNotificationId
     */
    public String getNotificationId() throws java.lang.IllegalStateException {
        if ( isPopulated( IRPEvent.NOTIFICATION_ID ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            IRPEvent.NOTIFICATION_ID );
        }
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param id A unique identifier across all notifications of a particular
     * managed entity.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getNotificationId
     */
    public void setNotificationId( String id )
    throws java.lang.IllegalArgumentException {
        if ( id == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            IRPEvent.NOTIFICATION_ID );
        }
        notificationId = id;
        populatedAttributeNames.add( IRPEvent.NOTIFICATION_ID);
        return;
    }

    /**
     * Returns the time when the event was published.
     *
     * @return Date The time when the event was published.
     */
    public java.util.Date getEventTime() throws java.lang.IllegalStateException {
        if ( isPopulated( IRPEvent.EVENT_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            IRPEvent.EVENT_TIME );
        }
        return eventTime;
    }

    /**
     * Sets the time when the event was published.
     *
     * @param time The time when the event was published.
     */
    public void setEventTime(java.util.Date time) throws java.lang.IllegalArgumentException {
        if ( time == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            IRPEvent.EVENT_TIME );
        }
        eventTime = time;
        populatedAttributeNames.add( IRPEvent.EVENT_TIME);
        return;
    }

    /**
     * The DN of the application sending the event.
     *
     * @return String The application's distinguished name
     */
    public String getApplicationDN() throws java.lang.IllegalStateException {
        if ( isPopulated( IRPEvent.APPLICATION_DN ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            IRPEvent.APPLICATION_DN );
        }
        return appDN;
    }

    /**
     * The DN of the application sending the Event.
     *
     * @param applicationDN The distinguished name of the application sending the
     * event.
     */
    public void setApplicationDN(String applicationDN) throws java.lang.IllegalArgumentException {
        if ( applicationDN == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            IRPEvent.APPLICATION_DN );
        }
        appDN = applicationDN;
        populatedAttributeNames.add( IRPEvent.APPLICATION_DN );
        return;
    }

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

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        BaseEvent obj = null;
        try {
            obj = (BaseEvent) super.clone();
            obj.populatedAttributeNames = (TreeSet)populatedAttributeNames.clone();
            obj.eventTime = (Date)eventTime.clone();
        }
        catch ( CloneNotSupportedException cex ) {
        }
        return obj;
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(500);
        String[] names = getPopulatedAttributeNames();
        for ( int i=0, len=names.length; i<len; i++ ) {
            AttrObjectHandler handler = getHandler( names[i] );
            String formattedName = new String ( names[i].toString() + " = " );
            buffer.append( Util.rightJustify(30, formattedName ) +
            Util.printObject( handler.getObject(this) ) );
        }
        return buffer.toString();
    }


    private void resetManagedObjectClass() {
        managedObjectClass = null;
        return;
    }

    private void resetManagedObjectInstance() {
        managedObjectInstance = null;
        return;
    }

    private void resetAppDN() {
        appDN = null;
        return;
    }

    private void resetNotificationId() {
        notificationId = null;
        return;
    }

    private void resetEventTime() {
        eventTime = null;
        return;
    }

    // interface for dealing with generic access

    interface AttrObjectHandler {

        public Object getObject( IRPEvent event )
        throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException;

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException;

        public void clearObject( IRPEvent event );
    }

    // static inner classes that represent each attribute

    static class ManagedObjectInstanceAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return event.getManagedObjectInstance();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                event.setManagedObjectInstance((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseEvent)event).resetManagedObjectInstance();
            return;
        }
    }

    static class ManagedObjectClassAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event )  {
            return event.getManagedObjectClass();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                event.setManagedObjectClass((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseEvent)event).resetManagedObjectClass();
            return;
        }
    }

    static class NotificationIdAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return event.getNotificationId();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                event.setNotificationId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseEvent)event).resetNotificationId();
            return;
        }
    }

    static class AppDNAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return event.getApplicationDN();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                event.setApplicationDN((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseEvent)event).resetAppDN();
            return;
        }
    }

    static class EventTimeAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return event.getEventTime();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                event.setEventTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((BaseEvent)event).resetEventTime();
            return;
        }
    }

}
