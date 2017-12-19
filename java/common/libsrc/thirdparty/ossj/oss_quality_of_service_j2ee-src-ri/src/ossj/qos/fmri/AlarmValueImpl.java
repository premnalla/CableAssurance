package ossj.qos.fmri;

/**
 * AlarmValueImpl
 * Represents alarm attribute value implementation.
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */

import javax.oss.*;

import javax.oss.fm.monitor.AttributeValue;
import javax.oss.fm.monitor.AttributeValueChange;
import javax.oss.fm.monitor.CorrelatedNotificationValue;
import javax.oss.fm.monitor.CommentValue;
import javax.oss.fm.monitor.AlarmAckState;
import javax.oss.fm.monitor.AlarmType;
import javax.oss.fm.monitor.AlarmKey;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.ThresholdInfoType;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;

import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Array;

import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;
import ossj.qos.util.Util;

public class AlarmValueImpl implements javax.oss.fm.monitor.AlarmValue
{
    // Contains validation code
    private static HashSet alarmTypeSet = null;
    private static HashSet probableCauseSet = null;
    private static HashSet perceivedSeveritySet = null;

    // Tracks the populated attributes
    protected transient TreeSet populatedAttributeNames = null;

    // Tracks the populated attributes over the wire
    private BitSet populateAttributeVessel = null;

    // Map containing a handler representing every attribute
    protected static TreeMap attributeHandlerMap = null;

    // Manager that is responsible for the attribute descriptors
    protected static AttributeManager attributeManager = null;

    // The attribute values
    private Date ackTime = null;
    private Date alarmChangedTime = null;
    private Date alarmClearedTime = null;
    private Date alarmRaisedTime = null;
    private String ackUserId = null;
    private String ackSystemId = null;
    private String additionalText = null;
    private int ackState = AlarmAckState.UNACKNOWLEDGED;
    private AlarmKey alarmKey = null;
    private String alarmType = null;
    private AttributeValueChange[] attributeChanges = new AttributeValueChange[0];
    private Boolean backupStatus = null;
    private String backupObject = null;
    private CorrelatedNotificationValue[] correlatedNotifications = new CorrelatedNotificationValue[0];
    private CommentValue[] comments = new CommentValue[0];
    private String managedObjectClass = null;
    private String managedObjectInstance = null;
    private AttributeValue monitoredAttributes[] = new AttributeValue[0];
    private String notificationId = null;
    private short perceivedSeverity = 0;
    private short probableCause = 0;
    private String proposedRepairActions = null;
    private String specificProblem = null;
    private String systemDN = null;
    private ThresholdInfoType thresholdInfo = null;
    private String trendIndication = null;

    long lastUpdateVersion = 1;


    /**
     * AlarmValueImpl default constructor
     */
    public AlarmValueImpl()
    {
        // Container for determining if an attribute has been populated.
        populatedAttributeNames = new TreeSet();
    }

    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // HashMap for attribute handlers
        attributeHandlerMap = new TreeMap();
        // attribute handlers into the map..
        attributeHandlerMap.put(AlarmValue.ACK_TIME, new AckTimeAttr());
        attributeHandlerMap.put(AlarmValue.ALARM_CHANGED_TIME, new AlarmChangedTimeAttr());
        attributeHandlerMap.put(AlarmValue.ALARM_CLEARED_TIME, new AlarmClearedTimeAttr());
        attributeHandlerMap.put(AlarmValue.ALARM_RAISED_TIME, new AlarmRaisedTimeAttr());
        attributeHandlerMap.put(AlarmValue.ACK_USER_ID, new AckUserIdAttr());
        attributeHandlerMap.put(AlarmValue.ACK_SYSTEM_ID, new AckSystemIdAttr());
        attributeHandlerMap.put(AlarmValue.ADDITIONAL_TEXT, new AdditionalTextAttr());
        attributeHandlerMap.put(AlarmValue.ALARM_ACK_STATE, new AlarmAckStateAttr());
        attributeHandlerMap.put(ManagedEntityValue.KEY, new AlarmKeyAttr());
        attributeHandlerMap.put(AlarmValue.ALARM_TYPE, new AlarmTypeAttr());
        attributeHandlerMap.put(AlarmValue.ATTRIBUTE_CHANGES, new AttributeChangesAttr());
        attributeHandlerMap.put(AlarmValue.BACKED_UP_STATUS, new BackedUpStatusAttr());
        attributeHandlerMap.put(AlarmValue.BACK_UP_OBJECT, new BackedUpObjectAttr());
        attributeHandlerMap.put(AlarmValue.CORRELATED_NOTIFICATIONS, new CorrelatedNotificationsAttr());
        attributeHandlerMap.put(AlarmValue.COMMENTS, new CommentsAttr());
        attributeHandlerMap.put(AlarmValue.MANAGED_OBJECT_CLASS, new ManagedObjectClassAttr());
        attributeHandlerMap.put(AlarmValue.MANAGED_OBJECT_INSTANCE, new ManagedObjectInstanceAttr());
        attributeHandlerMap.put(AlarmValue.MONITORED_ATTRIBUTES, new MonitoredAttributesAttr());
        attributeHandlerMap.put(AlarmValue.NOTIFICATION_ID, new NotificationIdAttr());
        attributeHandlerMap.put(AlarmValue.PERCEIVED_SEVERITY, new PerceivedSeverityAttr());
        attributeHandlerMap.put(AlarmValue.PROBABLE_CAUSE, new ProbableCauseAttr());
        attributeHandlerMap.put(AlarmValue.PROPOSED_REPAIR_ACTIONS, new ProposedRepairActionsAttr());
        attributeHandlerMap.put(AlarmValue.SPECIFIC_PROBLEM, new SpecificProblemAttr());
        attributeHandlerMap.put(AlarmValue.SYSTEM_DN, new SystemDNAttr());
        attributeHandlerMap.put(AlarmValue.THRESHOLD_INFO, new ThresholdInfoAttr());
        attributeHandlerMap.put(AlarmValue.TREND_INDICATION, new TrendIndicationAttr());

        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( AlarmValue.ACK_TIME, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.ALARM_CHANGED_TIME, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.ALARM_CLEARED_TIME, new AttributeDescriptor());
         attributeDescriptorMap.put( AlarmValue.ALARM_RAISED_TIME, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.ACK_USER_ID, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.ACK_SYSTEM_ID, new AttributeDescriptor(true,true,true));
         attributeDescriptorMap.put( AlarmValue.ADDITIONAL_TEXT, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.ALARM_ACK_STATE, new AttributeDescriptor());
        attributeDescriptorMap.put( ManagedEntityValue.KEY, new AttributeDescriptor());
         attributeDescriptorMap.put( AlarmValue.ALARM_TYPE, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.ATTRIBUTE_CHANGES, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.BACKED_UP_STATUS, new AttributeDescriptor(true,true,true));
         attributeDescriptorMap.put( AlarmValue.BACK_UP_OBJECT, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.CORRELATED_NOTIFICATIONS, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.COMMENTS, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.MANAGED_OBJECT_CLASS, new AttributeDescriptor() );
        attributeDescriptorMap.put( AlarmValue.MANAGED_OBJECT_INSTANCE, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.MONITORED_ATTRIBUTES, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.NOTIFICATION_ID, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.PERCEIVED_SEVERITY, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.PROBABLE_CAUSE, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.PROPOSED_REPAIR_ACTIONS, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.SPECIFIC_PROBLEM, new AttributeDescriptor(false,true,false));
        attributeDescriptorMap.put( AlarmValue.SYSTEM_DN, new AttributeDescriptor());
        attributeDescriptorMap.put( AlarmValue.THRESHOLD_INFO, new AttributeDescriptor(true,true,true));
        attributeDescriptorMap.put( AlarmValue.TREND_INDICATION, new AttributeDescriptor(true,true,true));

        attributeManager = new AttributeManager();
        // No need to set the parent since this is the BASE CLASS.
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

      // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return AlarmValueImpl.attributeManager;
    }

    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) {
            handler = (AttrObjectHandler)AlarmValueImpl.attributeHandlerMap.get(handlerName);
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

    public boolean isAttributeSupported ( String name ) {
        boolean supported = false;
        if ( name != null ) {
            AttributeManager manager = getAttributeManager();
            AttributeDescriptor descriptor = manager.getAttributeDescriptor( name );
            if ( descriptor != null ) {
                supported = descriptor.isSupported();
            }
        }
        return supported;
    }

   // start of Attribute Access generic methods
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


  // end of Attribute Access generic methods

      /**
     * Last Update Version Number
     */
    public long getLastUpdateVersionNumber() {
        return lastUpdateVersion;
    }

    /**
     * Set the last update version number.
     *
     * This field should never be set by the application client. Mutator is
     * provided for Serialization and Deserialization purposes only.
     *
     * @param lastUpdateNumber
     */
    public void setLastUpdateVersionNumber( long lastUpdateNumber)
    throws java.lang.IllegalArgumentException {
        lastUpdateVersion = lastUpdateNumber;
    }

    /**
     * Gets the key for this object. The key is unique over all objects.
     * @return returns the key for this value object
     * @throws java.lang.IllegalArgumentException in case the key attribute is not populated
     * @see javax.oss.ManagedEntityKey
     */
    public ManagedEntityKey getManagedEntityKey()
    throws java.lang.IllegalStateException {
        return alarmKey;
    }

    public void setManagedEntityKey(ManagedEntityKey key)
    throws java.lang.IllegalArgumentException {
        alarmKey = (AlarmKey) key;
        return;
    }

    /**
     * Returns all the serializer types than can be created by this factory
     */
    /*
    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String( XmlSerializer.class.getName());
        return serializerTypes;
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
        if ( serializerType.equals( XmlSerializer.class.getName() ) ) {
            serializer = new AlarmKeyXmlSerializerImpl(AlarmKey.class.getName() );
        }
        else {
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");
        }
    */
     /*
        Commented out the above and added below to comply with the new implementation.
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
     * Manufacture a Key for this of managed entity.
     */
    public ManagedEntityKey makeManagedEntityKey() {
        return makeAlarmKey();
    }

    // start of the AlarmValue interface...

    /**
     * Returns the managed object class.
     * <p>
     * This parameter specifies the class of the Managed Object (MO) in which
     * the network event occurred.
     *
     * @return <code>String</code> - managed object class.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setManagedObjectClass
     */
    public String getManagedObjectClass()
    throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.MANAGED_OBJECT_CLASS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.MANAGED_OBJECT_CLASS);
        }
        return managedObjectClass;
    }

    /**
     * Sets the managed object class.
     *
     * @param moc Defines the managed object class.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getManagedObjectClass
     */
    public void setManagedObjectClass( String moc)
    throws java.lang.IllegalArgumentException {
        if ( moc == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.MANAGED_OBJECT_CLASS );
        }
        managedObjectClass = moc;
        populatedAttributeNames.add( AlarmValue.MANAGED_OBJECT_CLASS );
        return;
    }

    /**
     * Returns the managed object instance.
     * <p>
     * This parameter specifies the instance of the MO in
     * which the network event occurred.
     *
     * @return <code>String</code> - managed object instance.
     * @exception java.lang.java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setManagedObjectInstance
     */
    public String getManagedObjectInstance()
    throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.MANAGED_OBJECT_INSTANCE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.MANAGED_OBJECT_INSTANCE );
        }
        return managedObjectInstance;
    }

    /**
     * Sets the managed object instance.
     *
     * @param moi Defines managed object instance.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getManagedObjectInstance
     */
    public void setManagedObjectInstance(String instance)
    throws java.lang.IllegalArgumentException {
        if ( instance == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.MANAGED_OBJECT_INSTANCE );
        }
        managedObjectInstance = instance;
        populatedAttributeNames.add( AlarmValue.MANAGED_OBJECT_INSTANCE);
        return;
    }

    /**
     * Returns the system DN.
     * <p>
     * It carries the Distinguished Name (DN) of system that detects
     * the network event and generates the notification.
     *
     * @return <code>String</code> - system DN.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setSystemDN
     */
    public String getSystemDN() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.SYSTEM_DN ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.SYSTEM_DN );
        }
        return systemDN;
    }

    /**
     * Sets the system DN.
     *
     * @param system_dn Defines system DN (Distinguished Name).
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getSystemDN
     */
    public void setSystemDN( String system )
    throws java.lang.IllegalArgumentException {
        if ( system == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.SYSTEM_DN );
        }
        systemDN = system;
        populatedAttributeNames.add( AlarmValue.SYSTEM_DN);
        return;
    }

    /**
     * Returns the alarm type.
     * <p>
     * It identifies alarm type.
     *
     * @return <code>String</code> - alarm type.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAlarmType
     */
    public String getAlarmType() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ALARM_TYPE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ALARM_TYPE );
        }
        return alarmType;
    }

    /**
     * Sets the alarm type.
     *
     * @param alarm_type Defines alarm type, valid values are defined in
     *  interface {@link AlarmType}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmType
     */
    public void setAlarmType( String type )
    throws java.lang.IllegalArgumentException {
        if ( alarmTypeSet.contains( type ) ) {
            alarmType = type;
            populatedAttributeNames.add( AlarmValue.ALARM_TYPE);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG  +
            AlarmValue.ALARM_TYPE );
        }
        return;
    }

    /**
     * Returns the alarm key.
     * <p>
     * It identifies at most one Alarm information in the Alarm List.
     * Not to be confused with primary key because alarms are not
     * necessary implemented as entity beans. The AlarmKey is an OSS/J
     * conterpart to 3GPP AlarmID.
     *
     * @return <code>AlarmKey</code> - identifies alarm information in alarm list.
     * @exception java.lang.new java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see AlarmKey
     * @see #setAlarmKey
     */
    public AlarmKey getAlarmKey() throws java.lang.IllegalStateException {
        if ( isPopulated( ManagedEntityValue.KEY ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            ManagedEntityValue.KEY );
        }
        return alarmKey;
    }

    /**
     * Sets the alarm key.
     *
     * @param alarm_key Defines alarm key (OSS/J counterpart to 3GPP AlarmID).
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see AlarmKey
     * @see #getAlarmKey
     */
    public void setAlarmKey( AlarmKey alarm_key )
    throws java.lang.IllegalArgumentException {
        if ( alarm_key == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            ManagedEntityValue.KEY );
        }
        alarmKey = alarm_key;
        populatedAttributeNames.add( ManagedEntityValue.KEY );
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
     * of simple type such as integer or string.
     *
     * @return <code>short</code> - probable cause, valid values are defined in
     *  interface {@link ProbableCause}.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setProbableCause
     */
    public short getProbableCause() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.PROBABLE_CAUSE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.PROBABLE_CAUSE );
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
    public void setProbableCause( short cause )
    throws java.lang.IllegalArgumentException {
        Short objCause = new Short( cause );
        if ( probableCauseSet.contains( objCause ) ) {
            probableCause = cause;
            populatedAttributeNames.add( AlarmValue.PROBABLE_CAUSE );
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.PROBABLE_CAUSE );
        }
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
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setPerceivedSeverity
     */
    public short getPerceivedSeverity() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.PERCEIVED_SEVERITY ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.PERCEIVED_SEVERITY );
        }
        return perceivedSeverity;
    }

    /**
     * Sets the perceived severity.
     *
     * @param perceived_severity Defines perceived severity, valid values
     *  are defined in interface {@link PerceivedSeverity}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getPerceivedSeverity
     */
    public void setPerceivedSeverity( short severity )
    throws java.lang.IllegalArgumentException {
        Short objSeverity = new Short( severity );
        if ( perceivedSeveritySet.contains( objSeverity ) ) {
            perceivedSeverity = severity;
            populatedAttributeNames.add( AlarmValue.PERCEIVED_SEVERITY);
        }
        else {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.PROBABLE_CAUSE );
        }
        return;
    }

    /**
     * Returns the specific problem.
     * <p>
     * It provides further qualification on the alarm than probableCause.
     *
     *  ======= THIS ATTRIBUTE IS NOT SUPPORTED IN THIS IMPLEMENTATION
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @return <code>String</code> - specific problem, the content is vendor specific.
     * @see #setSpecificProblem
     */
    public String getSpecificProblem()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        throw new UnsupportedAttributeException( AlarmValue.SPECIFIC_PROBLEM, "Unsupported Attribute entered." );
        //if ( isPopulated( AlarmValue.SPECIFIC_PROBLEM ) == false ) {
        //    throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
        //    AlarmValue.SPECIFIC_PROBLEM );
        //}
        // return specificProblem;
    }

    /**
     * Sets the specific problem.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     *  ======= THIS ATTRIBUTE IS NOT SUPPORTED IN THIS IMPLEMENTATION
     *
     * @param specific_problem Defines specific problem, the content is
     *  vendor specific.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getSpecificProblem
     */
    public void setSpecificProblem( String problem )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        throw new UnsupportedAttributeException( AlarmValue.SPECIFIC_PROBLEM, "Unsupported Attribute entered." );
        //   specificProblem = problem;
        //   populatedAttributeNames.add( AlarmValue.SPECIFIC_PROBLEM);
        //   return;
    }

    /**
     * Returns the correlated notifications.
     * <p>
     * It identifies a set of notifications to which this notification is
     * considered to be correlated.
     *
     * @return <code>CorrelatedNotificationValue[]</code> - list of correlated notifications.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see CorrelatedNotificationValue
     * @see #setCorrelatedNotifications
     */
    public CorrelatedNotificationValue[] getCorrelatedNotifications()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.CORRELATED_NOTIFICATIONS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.CORRELATED_NOTIFICATIONS );
        }
        return correlatedNotifications;
    }

    /**
     * Sets the correlated notifications.
     *
     * <p>
     * If supported, the default value is an empty array.
     *
     * @param notifications Defines the correlated notifications.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getCorrelatedNotifications
     * @see CorrelatedNotificationValue
     */
    public void setCorrelatedNotifications( CorrelatedNotificationValue[] notifications )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( notifications == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.CORRELATED_NOTIFICATIONS );
        }
        correlatedNotifications = notifications;
        populatedAttributeNames.add( AlarmValue.CORRELATED_NOTIFICATIONS);
        return;
    }

    /**
     * Returns the backed up status.
     * <p>
     * It indicates if an object has a back up.
     *
     * @return <code>Boolean</code> - backed up status, valid values are defined in
     * {@link BackedUpStatusType}.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #setBackedUpStatus
     */
    public Boolean getBackedUpStatus()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.BACKED_UP_STATUS ) == false ) {
            throw new java.lang.IllegalStateException ( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            BACKED_UP_STATUS );
        }
        return backupStatus;
    }

    /**
     * Sets the backed up status.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param status Defines backed up status, valid values are defined in
     *  {@link BackedUpStatusType}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getBackedUpStatus
     */
    public void setBackedUpStatus(Boolean bkupStatus)
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        backupStatus = bkupStatus;
        populatedAttributeNames.add( AlarmValue.BACKED_UP_STATUS);
        return;
    }

    /**
     * Returns the back up object.
     * <p>
     * It carries the DN of the back up object. It shall be absent if
     * backUpStatus is absent or its value indicates false.
     *
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @return <code>String</code> - DN of the back up object.
     * @see #setBackUpObject
     */
    public String getBackUpObject()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.BACK_UP_OBJECT ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.BACK_UP_OBJECT );
        }
        return backupObject;
    }

    /**
     * Sets the back up object.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param back_up_object Defines the DN of the back up object.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getBackUpObject
     */
    public void setBackUpObject(String back_up_object )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        backupObject = back_up_object;
        populatedAttributeNames.add( AlarmValue.BACK_UP_OBJECT);
        return;
    }

    /**
     * Returns the trend indication.
     *
     * It indicates if some observed condition is getting better, worse,
     * or not changing. Predefined legal values are defined in interface
     * <code>javax.oss.fm.monitor.TrendIndication</code>.
     *
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @return <code>String</code> - trend indication, valid values are defined in
     *  interface {@link TrendIndicationType}.
     * @see #setTrendIndication
     */
    public String getTrendIndication() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.TREND_INDICATION ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.TREND_INDICATION );
        }
        return trendIndication;
    }

    /**
     * Sets the trend indication.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param trend_indication Defines the trend indication, valid values are
     *  defined in interface {@link TrendIndicationType}.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getTrendIndication
     */
    public void setTrendIndication( String trendId )
    throws java.lang.IllegalArgumentException {
        trendIndication = trendId;
        populatedAttributeNames.add( AlarmValue.TREND_INDICATION);
        return;
    }

    /**
     * Returns the threshold info.
     *
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @return <code>ThresholdInfoType</code> - threshold information, defined in
     *  interface {@link ThresholdInfoType}.
     * @see #setThresholdInfo
     */
    public ThresholdInfoType getThresholdInfo() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.THRESHOLD_INFO ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.THRESHOLD_INFO );
        }
        return thresholdInfo;
    }

    /**
     * Sets the threshold info.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param threshold_info Defines the threshold info, see the interface {@link ThresholdInfoType}.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getThresholdInfo
     */
    public void setThresholdInfo( ThresholdInfoType indication )
    throws java.lang.IllegalArgumentException {
        thresholdInfo = indication;
        populatedAttributeNames.add( AlarmValue.THRESHOLD_INFO);
        return;
    }

    /**
     * Returns the attribute changes.
     *
     * @return <code>AttributeValueChange</code> - the list of changed attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see AttributeValueChange
     * @see #setAttributeChanges
     */
    public AttributeValueChange[] getAttributeChanges()
    throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ATTRIBUTE_CHANGES ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ATTRIBUTE_CHANGES );
        }
        return attributeChanges;
    }

    /**
     * Sets the attribute changes.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param attribute_changes Defines changed attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see AttributeValueChange
     * @see #getAttributeChanges
     */
    public void setAttributeChanges( AttributeValueChange[] changes )
    throws java.lang.IllegalArgumentException {
        if ( changes == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.ATTRIBUTE_CHANGES );
        }
        attributeChanges = changes;
        populatedAttributeNames.add( AlarmValue.ATTRIBUTE_CHANGES);
        return;
    }

    /**
     * Returns the monitored attributes.
     *
     * @return <code>AttributeValue[]</code> - monitored attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see AttributeValue
     * @see #setMonitoredAttributes
     */
    public AttributeValue[] getMonitoredAttributes()
    throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.MONITORED_ATTRIBUTES ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.MONITORED_ATTRIBUTES );
        }
        return monitoredAttributes;
    }

    /**
     * Sets the monitored attributes.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param monitored_attributes Defines monitored attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see AttributeValue
     * @see #getMonitoredAttributes
     */
    public void setMonitoredAttributes( AttributeValue[] attributes )
    throws java.lang.IllegalArgumentException {
        if ( attributes == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.MONITORED_ATTRIBUTES );
        }
        monitoredAttributes = attributes;
        populatedAttributeNames.add( AlarmValue.MONITORED_ATTRIBUTES);
        return;
    }

    /**
     * Returns the notification id.
     * <p>
     * It identifies the notification that carries the alarm information.
     *
     * @return <code>String</code> - Identifies the notification that carries
     * the alarm information.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setNotificationId
     */
    public String getNotificationId() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.NOTIFICATION_ID ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.NOTIFICATION_ID );
        }
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param id A String identifying the notification that carries the alarm information.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getNotificationId
     */
    public void setNotificationId( String id )
    throws java.lang.IllegalArgumentException {
        if ( id == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.NOTIFICATION_ID );
        }
        notificationId = id;
        populatedAttributeNames.add( AlarmValue.NOTIFICATION_ID);
        return;
    }

    /**
     * Returns the proposed repair actions.
     *
     * @return <code>String</code> - proposed repair actions.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setProposedRepairActions
     */
    public String getProposedRepairActions() throws java.lang.IllegalStateException,
    javax.oss.UnsupportedAttributeException {
        if ( isPopulated(AlarmValue.PROPOSED_REPAIR_ACTIONS) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            AlarmValue.PROPOSED_REPAIR_ACTIONS);
        }
        return proposedRepairActions;
    }

    /**
     * Sets the proposed repair actions.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param proposed_repair_actions Defines proposed repair actions.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getProposedRepairActions
     */
    public void setProposedRepairActions( String repairs )
    throws java.lang.IllegalArgumentException {
        proposedRepairActions = repairs;
        populatedAttributeNames.add( AlarmValue.PROPOSED_REPAIR_ACTIONS);
        return;
    }

    /**
     * Returns the additional text.
     * <p>
     * It provides the identity of the NE (e.g. RNC, Node-B) from which
     * the alarm has been originated. It can contain further information
     * on the alarm.
     *
     * @return <code>String</code> - additional text.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAdditionalText
     */
    public String getAdditionalText() throws java.lang.IllegalStateException,
    javax.oss.UnsupportedAttributeException {
        if ( isPopulated(AlarmValue.ADDITIONAL_TEXT) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            AlarmValue.ADDITIONAL_TEXT);
        }
        return additionalText;
    }

    /**
     * Sets the additional text.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param additional_text Defines additional text.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAdditionalText
     */
    public void setAdditionalText( String text )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        additionalText = text;
        populatedAttributeNames.add( AlarmValue.ADDITIONAL_TEXT);
        return;
    }

    /**
     * Returns the ack user ID.
     * <p>
     * It identifies the last user who changed the Acknowledgement
     * state via the operation <code>tryAcknowledgeAlarms</code> or <code>
     * tryUnacknowledgeAlarms</code>.
     *
     * @return <code>String</code> - ack user ID.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAckUserId
     */
    public String getAckUserId() throws java.lang.IllegalStateException {
        if ( isPopulated(AlarmValue.ACK_USER_ID) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            AlarmValue.ACK_USER_ID);
        }
        return ackUserId;
    }

    /**
     * Returns the ack user ID.
     * <p>
     * It identifies the last user who changed the Acknowledgement
     * state via the operation <code>tryAcknowledgeAlarms</code> or <code>
     * tryUnacknowledgeAlarms</code>.
     *
     * @return <code>String</code> - ack user ID.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAckUserId
     */
    public void setAckUserId( String userId )
    throws java.lang.IllegalArgumentException {
        ackUserId = userId;
        populatedAttributeNames.add( AlarmValue.ACK_USER_ID);
        return;
    }

    /**
     * Returns the ack system ID.
     * <p>
     * It identifies the system of the last user who has changed the Acknowledgement
     * state via operation <code>tryAcknowledgeAlarms</code> or <code>
     * tryUnacknowledgeAlarms</code>.
     *
     * @return <code>String</code> - ack user ID.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAckSystemId
     */
    public String getAckSystemId()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.ACK_SYSTEM_ID ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ACK_SYSTEM_ID);
        }
        return ackSystemId;
    }

    /**
     * Sets the ack system ID.
     *
     * <p>
     * If supported, the default value is <code>null</code>.
     *
     * @param sid Defines the ack system ID.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAckSystemId
     */
    public void setAckSystemId( String systemId )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        ackSystemId = systemId;
        populatedAttributeNames.add( AlarmValue.ACK_SYSTEM_ID);
        return;
    }

    /**
     * Returns the ack time.
     * <p>
     * It identifies the time of the last operation <code>acknowledgeAlarms
     * </code> or <code>unacknowledgeAlarms</code>.
     *
     * @return <code>Date</code> - ack time.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAckTime
     */
    public Date getAckTime() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ACK_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ACK_TIME);
        }
        return ackTime;
    }

    /**
     * Sets the ack time.
     *
     * <p>
     * The default value is <code>null</code>.
     *
     * @param ack_time Defines the ack time.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAckTime
     */
    public void setAckTime( Date ack_time )
    throws java.lang.IllegalArgumentException {
        ackTime = ack_time;
        populatedAttributeNames.add( AlarmValue.ACK_TIME);
        return;
    }

    /**
     * Returns the alarm ack state.
     * <p>
     * It identifies the Acknowledgement State of the alarm. Its valid
     * values defined in interface <code>AlarmAckState</code>.
     *
     * @return <code>short</code> - alarm ack state, valid values are defines in
     *  interface {@link AlarmAckState}.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAlarmAckState
     */
    public int getAlarmAckState() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ALARM_ACK_STATE ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ALARM_ACK_STATE);
        }
        return ackState;
    }

    /**
     * Sets the alarm ack state.
     *
     * <p>
     * The default value is <code>AlarmAckState.UNACKNOWLEDGED</code>.
     *
     * @param ack_state Defines ack state, valid values are defines in
     *  interface {@link AlarmAckState}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmAckState
     */
    public void setAlarmAckState( int state )
    throws java.lang.IllegalArgumentException {
        if ( state != AlarmAckState.UNACKNOWLEDGED && state != AlarmAckState.ACKNOWLEDGED ) {
            throw new java.lang.IllegalArgumentException( LogMessages.INVALID_ATTRIBUTE_ENTERED_MSG
            + AlarmValue.ALARM_ACK_STATE );
        }
        ackState = state;
        populatedAttributeNames.add( AlarmValue.ALARM_ACK_STATE);
        return;
    }

    /**
     * Returns the alarm raised time.
     *
     * @return <code>java.util.Date</code> - alarm raised time.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAlarmRaisedTime
     */
    public Date getAlarmRaisedTime()
    throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ALARM_RAISED_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ALARM_RAISED_TIME);
        }
        return alarmRaisedTime;
    }

    /**
     * Sets the alarm raised time.
     *
     * @param alarm_raised_time Defines the alarm raised time.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmRaisedTime
     */
    public void setAlarmRaisedTime( Date alarm_raised_time )
    throws java.lang.IllegalArgumentException {
        if ( alarm_raised_time == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            AlarmValue.ALARM_RAISED_TIME );
        }
        alarmRaisedTime = alarm_raised_time;
        populatedAttributeNames.add( AlarmValue.ALARM_RAISED_TIME);
        return;
    }

    /**
     * Returns comment information.
     *
     * @return <code>Comment[]</code> - list of comments.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see CommentValue
     * @see #setComments
     */
    public CommentValue[] getComments()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.COMMENTS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.COMMENTS);
        }
        return comments;
    }

    /**
     * Sets the comment information.
     *
     * <p>
     * If supported, the default value is an empty array.
     *
     * @param comments Comment information.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see CommentValue
     * @see #getComments
     */
    public void setComments( CommentValue[] comments )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( comments == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG + AlarmValue.COMMENTS );
        }
        this.comments = comments;
        populatedAttributeNames.add( AlarmValue.COMMENTS);
        return;
    }

    /**
     * Returns the alarm cleared time.
     * <p>
     * It identifies the time when the perceived severity of the alarm
     * was changed as <code>PerceivedSeverity.CLEARED</code>. The <code>NULL</code>
     * value denotes that the alarm is not yet cleared.
     *
     * @return <code>java.util.Date</code> - alarm cleared time.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAlarmClearedTime
     */
    public Date getAlarmClearedTime() throws java.lang.IllegalStateException {
        if ( isPopulated( AlarmValue.ALARM_CLEARED_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ALARM_CLEARED_TIME);
        }
        return alarmClearedTime;
    }

    /**
     * Sets the alarm cleared time.
     *
     * <p>
     * The default value is <code>null</code>.
     *
     * @param alarm_cleared_time Defines the alarm cleared time.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmClearedTime
     */
    public void setAlarmClearedTime( Date alarm_cleared_time )
    throws java.lang.IllegalArgumentException  {
        alarmClearedTime = alarm_cleared_time;
        populatedAttributeNames.add( AlarmValue.ALARM_CLEARED_TIME);
        return;
    }

    /**
     * Returns the alarm changed time.
     * <p>
     * It identifies the time when alarm attribute values were changed.
     * The <code>null</code> value denotes that alarm attribute values
     * are not changed after the new alarm was added to alarm list.
     *
     * @return <code>java.util.Date</code> - alarm changed time.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown to report that
     * the attribute has not been populated (set).
     * @see #setAlarmChangedTime
     */
    public Date getAlarmChangedTime()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( AlarmValue.ALARM_CHANGED_TIME ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            AlarmValue.ALARM_CHANGED_TIME);
        }
        return alarmChangedTime;
    }

    /**
     * Sets the alarm changed time.
     *
     * <p>
     * The default value is <code>null</code>.
     *
     * @param alarm_changed_time Defines the alarm changed time.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAlarmChangedTime
     */
    public void setAlarmChangedTime( Date alarm_changed_time )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        alarmChangedTime = alarm_changed_time;
        populatedAttributeNames.add( AlarmValue.ALARM_CHANGED_TIME);
        return;
    }

    /**
     * Returns an AttributeValueChange.
     * @return <code>AttributeValueChange</code> - an attribute value change.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValueChange
     */
    public AttributeValueChange makeAttributeValueChange()
    throws javax.oss.UnsupportedAttributeException {
        return new AttributeValueChangeImpl();
    }

    /**
     * Returns an AttributeValue.
     * @return <code>AttributeValue</code> - an attribute value.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValue
     */
    public AttributeValue makeAttributeValue()
    throws javax.oss.UnsupportedAttributeException {
        return new AttributeValueImpl();
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
     * Returns a CommentValue.
     * @return <code>CommentValue</code> - a comment value.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.CommentValue
     */
    public CommentValue makeCommentValue()
    throws javax.oss.UnsupportedAttributeException {
        return new CommentValueImpl();
    }

    /**
     * Returns an ThresholdInfoType.
     * @return <code>ThresholdInfoType</code> - a threshold information.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValueChange
     */
    public javax.oss.fm.monitor.ThresholdInfoType makeThresholdInfoType()
    throws javax.oss.UnsupportedAttributeException {
        return new ThresholdInfoTypeImpl();
    }

    /**
     * Returns an AlarmKey.
     * @return <code>AlarmKey</code> - an alarm key.
     * @see AlarmKey
     */
    public AlarmKey makeAlarmKey() {
        return new AlarmKeyImpl();
    }

    /**
     * Deep copy of this AlarmValue
     *
     * @return deep copy of this AlarmValue
     */
    public Object clone() {
        AlarmValueImpl obj = null;
        try {
            obj = (AlarmValueImpl)super.clone();
            obj.populatedAttributeNames = (TreeSet)populatedAttributeNames.clone();
            obj.monitoredAttributes = (AttributeValue[])monitoredAttributes.clone();
            obj.attributeChanges = (AttributeValueChange[])attributeChanges.clone();
            obj.correlatedNotifications = (CorrelatedNotificationValue[])correlatedNotifications.clone();
            obj.alarmKey = (AlarmKey)alarmKey.clone();
            obj.comments = (CommentValue[])comments.clone();
            obj.ackTime = (Date)ackTime.clone();
            obj.alarmChangedTime = (Date)alarmChangedTime.clone();
            obj.alarmClearedTime = (Date)alarmClearedTime.clone();
            obj.alarmRaisedTime = (Date)alarmRaisedTime.clone();
            obj.thresholdInfo = (ThresholdInfoType)thresholdInfo.clone();
        }
        catch ( CloneNotSupportedException cex ) {
            //System.out.println( "Problem cloning AlarmValue." + cex.toString() );
        }
        return obj;
    }

    // end of AlarmValue interfaces...

    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in AlarmValueImpl instance are equal to the
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof AlarmValueImpl ) {
            AlarmValueImpl objAlarmVal = (AlarmValueImpl)anObject;
            isEqual = ( Util.isEqual( ackTime, objAlarmVal.ackTime ) &&
            Util.isEqual( alarmChangedTime, objAlarmVal.alarmChangedTime ) &&
            Util.isEqual( alarmClearedTime, objAlarmVal.alarmClearedTime ) &&
            Util.isEqual( alarmRaisedTime, objAlarmVal.alarmRaisedTime ) &&
            Util.isEqual( ackUserId, objAlarmVal.ackUserId ) &&
            Util.isEqual( ackSystemId, objAlarmVal.ackSystemId ) &&
            Util.isEqual( additionalText, objAlarmVal.additionalText ) &&
            ( ackState == objAlarmVal.ackState ) &&
            Util.isEqual( alarmKey, objAlarmVal.alarmKey ) &&
            Util.isEqual( alarmType, objAlarmVal.alarmType ) &&
            Util.isEqual( backupStatus, objAlarmVal.backupStatus ) &&
            Util.isEqual( backupObject, objAlarmVal.backupObject ) &&
            Util.isEqual( correlatedNotifications, objAlarmVal.correlatedNotifications ) &&
            Util.isEqual( comments, objAlarmVal.comments ) &&
            Util.isEqual( managedObjectClass, objAlarmVal.managedObjectClass ) &&
            Util.isEqual( managedObjectInstance, objAlarmVal.managedObjectInstance ) &&
            Util.isEqual( monitoredAttributes, objAlarmVal.monitoredAttributes ) &&
            Util.isEqual( notificationId, objAlarmVal.notificationId ) &&
            ( perceivedSeverity == objAlarmVal.perceivedSeverity ) &&
            ( probableCause == objAlarmVal.probableCause ) &&
            Util.isEqual( proposedRepairActions, objAlarmVal.proposedRepairActions ) &&
            //   SPECIFICPROBLEM is an Unsupported Attribute in this RI
            //    Util.isEqual( specificProblem, objAlarmVal.specificProblem ) &&
            Util.isEqual( systemDN, objAlarmVal.systemDN ) &&
            Util.isEqual( thresholdInfo, objAlarmVal.thresholdInfo ) &&
            Util.isEqual( trendIndication, objAlarmVal.trendIndication ) );
        }
        return isEqual;
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(500);
        buffer.append("AlarmValue >>>>>>>>begin>>>>>>>>>>> \n");
        String[] names = getPopulatedAttributeNames();
        for ( int i=0, len=names.length; i<len; i++ ) {
            AttrObjectHandler handler = getHandler( names[i] );
            String formattedName = new String ( names[i].toString() + " = " );
            buffer.append( Util.rightJustify(30, formattedName ) +
                           Util.printObject( handler.getObject(this) ) );
        }
        buffer.append("AlarmValue >>>>>>>>>end>>>>>>>>>>>> \n");
        return buffer.toString();
    }

    private void resetAckTime() {
        ackTime = null;
        return;
    }

    private void resetAlarmChangedTime() {
        alarmChangedTime = null;
        return;
    }

    private void resetAlarmClearedTime() {
        alarmClearedTime = null;
        return;
    }

    private void resetAlarmRaisedTime() {
        alarmRaisedTime = null;
        return;
    }

    private void resetAckUserId() {
        ackUserId = null;
        return;
    }

    private void resetAckSystemId() {
        ackSystemId = null;
        return;
    }

    private void resetAdditionalText() {
        additionalText = null;
        return;
    }

    private void resetAlarmAckState() {
        ackState = AlarmAckState.UNACKNOWLEDGED;
        return;
    }

    private void resetAlarmKey() {
        alarmKey = null;
        return;
    }

    private void resetAlarmType() {
        alarmType = null;
        return;
    }

    private void resetAttributeChanges() {
        attributeChanges = new AttributeValueChange[0];
        return;
    }

    private void resetBackupStatus() {
        backupStatus = null;
        return;
    }

    private void resetBackupObject() {
        backupObject = null;
        return;
    }

    private void resetCorrelatedNotifications() {
        correlatedNotifications = new CorrelatedNotificationValue[0];
        return;
    }

    private void resetComments() {
        comments = new CommentValue[0];
        return;
    }

    private void resetManagedObjectClass() {
        managedObjectClass = null;
        return;
    }

    private void resetManagedObjectInstance() {
        managedObjectInstance = null;
        return;
    }

    private void resetAttributeValue() {
        monitoredAttributes = new AttributeValue[0];
        return;
    }

    private void resetNotificationId() {
        notificationId = null;
        return;
    }

    private void resetPerceivedSeverity() {
        perceivedSeverity = 0;
        return;
    }

    private void resetProbableCause() {
        probableCause = 0;
        return;
    }

    private void resetProposedRepairActions() {
        proposedRepairActions = null;
        return;
    }

    private void resetSpecificProblem() {
        specificProblem = null;
        return;
    }

    private void resetSystemDN() {
        systemDN = null;
        return;
    }

    private void resetThresholdInfo() {
        thresholdInfo = null;
        return;
    }

    private void resetTrendIndication() {
        trendIndication = null;
        return;
    }


    // interface for dealing with generic access

    interface AttrObjectHandler {

        public Object getObject( AlarmValue alarm )
        throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException;

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException;

        public void clearObject( AlarmValue alarm );
    }

    // static inner classes that represent each attribute

    static class AdditionalTextAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAdditionalText();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAdditionalText( (String) obj );
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAdditionalText();
            return;
        }
    }

    static class AckTimeAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAckTime();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAckTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAckTime();
            return;
        }
    }

    static class AckUserIdAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAckUserId();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAckUserId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAckUserId();
            return;
        }
    }

    static class AckSystemIdAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAckSystemId();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAckSystemId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAckSystemId();
            return;
        }
    }

    static class AlarmAckStateAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return new Integer ( alarm.getAlarmAckState() );
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Integer val = (Integer)obj;
                alarm.setAlarmAckState(val.intValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmAckState();
            return;
        }
    }

    static class AlarmClearedTimeAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAlarmClearedTime();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAlarmClearedTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmClearedTime();
            return;
        }
    }

    static class AlarmChangedTimeAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAlarmChangedTime();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAlarmChangedTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmChangedTime();
            return;
        }
    }

    static class AlarmKeyAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAlarmKey();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAlarmKey((AlarmKey) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmKey();
            return;
        }
    }

    static class AlarmRaisedTimeAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAlarmRaisedTime();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAlarmRaisedTime((Date) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmRaisedTime();
            return;
        }
    }

    static class AlarmTypeAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAlarmType();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAlarmType((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAlarmType();
            return;
        }
    }

    static class AttributeChangesAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getAttributeChanges();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setAttributeChanges((AttributeValueChange[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAttributeChanges();
            return;
        }
    }

    static class BackedUpStatusAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getBackedUpStatus();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Boolean val = (Boolean)obj;
                alarm.setBackedUpStatus(val);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetBackupStatus();
            return;
        }
    }

    static class BackedUpObjectAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getBackUpObject();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setBackUpObject((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetBackupObject();
            return;
        }
    }

    static class CorrelatedNotificationsAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getCorrelatedNotifications();
        }

        public void setObject( AlarmValue alarm, Object obj ) {
            try {
                alarm.setCorrelatedNotifications((CorrelatedNotificationValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetCorrelatedNotifications();
            return;
        }
    }

    static class CommentsAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getComments();
        }

        public void setObject( AlarmValue alarm, Object obj ) {
            try {
                alarm.setComments((CommentValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetComments();
            return;
        }
    }

    static class ManagedObjectInstanceAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getManagedObjectInstance();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setManagedObjectInstance((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetManagedObjectInstance();
            return;
        }
    }

    static class ManagedObjectClassAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm )  {
            return alarm.getManagedObjectClass();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setManagedObjectClass((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetManagedObjectClass();
            return;
        }
    }

    static class MonitoredAttributesAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getMonitoredAttributes();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setMonitoredAttributes((AttributeValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetAttributeValue();
            return;
        }
    }

    static class NotificationIdAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getNotificationId();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setNotificationId((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetNotificationId();
            return;
        }
    }

    static class PerceivedSeverityAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return new Short ( alarm.getPerceivedSeverity() );
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Short val = (Short)obj;
                alarm.setPerceivedSeverity(val.shortValue());
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetPerceivedSeverity();
            return;
        }
    }

    static class ProbableCauseAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return new Short ( alarm.getProbableCause() );
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Short val = (Short)obj;
                alarm.setProbableCause( val.shortValue() );
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetProbableCause();
            return;
        }
    }

    static class ProposedRepairActionsAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getProposedRepairActions();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setProposedRepairActions((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetProposedRepairActions();
            return;
        }
    }

    static class SpecificProblemAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getSpecificProblem();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setSpecificProblem((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetSpecificProblem();
            return;
        }
    }

    static class SystemDNAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getSystemDN();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setSystemDN((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetSystemDN();
            return;
        }
    }

    static class ThresholdInfoAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getThresholdInfo();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setThresholdInfo((ThresholdInfoType) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetThresholdInfo();
            return;
        }
    }

    static class TrendIndicationAttr implements AttrObjectHandler
    {
        public Object getObject( AlarmValue alarm ) {
            return alarm.getTrendIndication();
        }

        public void setObject( AlarmValue alarm, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                alarm.setTrendIndication((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( AlarmValue alarm ) {
            ((AlarmValueImpl)alarm).resetTrendIndication();
            return;
        }
    }

}
