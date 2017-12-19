

package com.nokia.oss.ossj.common.ri;

import java.io.*;
import java.util.*;
import java.beans.*;
import java.lang.reflect.*;
import javax.oss.*;

import com.sun.oss.common.ri.XmlSerializerImpl ;

/**
 *
 * @author BanuPrasad Dhanakoti Nokia Networks
 * @version 1.1
 * January 2002
 */
public abstract class AttributeAccessImpl extends java.lang.Object implements javax.oss.AttributeAccess {
    
    private BitSet dirtyAttributes;
    private BitSet populatedAttributes;
    private boolean fullyPopulated;
    
    protected transient AttributeManager myAttributeManager;
    
    private transient Map getMethods;
    private transient Map setMethods;
    
    /** Creates new AttributeAccessImpl */
    public AttributeAccessImpl() {
        initAttributeManager();
        initBitSets();
    }
    
    /**
     * a subclass should implement this method so that it returns a reference to the class specific static
     * attribute manager object
     */
    protected abstract AttributeManager getAttributeManager();
    
    /**
     * a subclass should create a new class specific attribute manager, which has to be returned by getAttributeManager()
     * in future calls
     */
    protected abstract AttributeManager makeAttributeManager();
    
    /**
     * in this method, subclasses have to 
     * <UL>
     * <LI> call super.addAddtributesTo(attributeManager) 
     * <LI> call attributeManager.addAttributes( ... ); with whatever attributes they want to add
     * </UL>
     */
    protected void addAttributesTo(AttributeManager anAttributeManager) {
    }
    
    /**
     * in this method, subclasses can the type of the attributes, the added to the attribute manager in addAttributesTo(am).
     * they can define them either as settable by calling attributeManager.setSettableAttributes( ... ) or 
     * optional attributes can be defined supported by calling attributeManager.setSupportedOptionalAttributes( ... )
     */
    protected void configureAttributes(AttributeManager anAttributeManager){
    }
    
    private void initAttributeManager()
    {
        myAttributeManager = getAttributeManager();
        
        if (myAttributeManager == null) {
            myAttributeManager = makeAttributeManager();
            addAttributesTo(myAttributeManager);
            myAttributeManager.lock();
            configureAttributes(myAttributeManager);
        }
        
    }

    public void initBitSets(){
        int numberOfAttr = myAttributeManager.getAttributeCount();
        dirtyAttributes = new BitSet(numberOfAttr);
        populatedAttributes = new BitSet(numberOfAttr);
        fullyPopulated = false;
    }
    
    private boolean isSet(BitSet aBitSet, String attribute) {
        return aBitSet.get(myAttributeManager.getPosition(attribute));
    }
    
    private boolean isAllSet(BitSet aBitSet) {
        for (int i=0 ; i<myAttributeManager.getAttributeCount() ; i++) {
            if (!aBitSet.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    private void set(BitSet aBitSet, String attribute) {
        aBitSet.set(myAttributeManager.getPosition(attribute));
    }
    
    private void clear(BitSet aBitSet, String attribute) {
        aBitSet.clear(myAttributeManager.getPosition(attribute));
    }
    
            /*
        ============================================================================
        DIRTY ATTRIBUTE ============================================================
        ============================================================================
             */
    
    public void setDirtyAttribute(String attribute) {
        this.setDirtyAttribute(attribute, true);
    }
    
    public void setDirtyAttribute(String attribute, boolean status) {
        if (status) {
            if ( !isSet(dirtyAttributes, attribute)) {
                set(dirtyAttributes, attribute);
                if ( !(fullyPopulated || isSet(populatedAttributes, attribute)) ){
                    populateAttribute(attribute);
                }
            }
        } else {
            if (!isFullyPopulated()) {
                // since this method will (up to now) only be called with status=true
                // this is not a problem. It would be, because fullyPopulated would
                // be set to false and the vector has to be initialized somehow
                clear(dirtyAttributes, attribute);
            }
        }
    }
    
    public boolean isDirty(String attribute) {
        return isSet(dirtyAttributes, attribute);
    }
    
    public String[] getDirtyAttributeNames() {
        Iterator attributeIterator = myAttributeManager.getAttributeIterator();
        List dirtyList = new ArrayList();
        String attribute;
        int bit=0;
        while (attributeIterator.hasNext()) {
            attribute = (String)attributeIterator.next();
            if (dirtyAttributes.get(bit)) {
                dirtyList.add(attribute);
            }
            bit++;
        }
        return (String[])dirtyList.toArray(new String[0]);
    }
    
    public void cleanAttributes() {
        dirtyAttributes.and(myAttributeManager.EMPTY_BITSET);
    }
    
        /*
        ============================================================================
        POPULATED ATTRIBUTES =======================================================
        ============================================================================
         */
    
    /** Gets all attribute names, which attribute values contain something
     * meaningful.
     * <p>Although an attribute is populated, it can be <CODE>null</CODE>!
     * @return all names of attributes, which contain some data.
     * When no attributes are populated an <B>empty array</B> is
     * returned.
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String[] getPopulatedAttributeNames(){
        Iterator attributeIterator = myAttributeManager.getAttributeIterator();
        List populatedList = new ArrayList();
        String attribute;
        int bit=0;
        while (attributeIterator.hasNext()) {
            attribute = (String)attributeIterator.next();
            if (fullyPopulated || populatedAttributes.get(bit)) {
                populatedList.add(attribute);
            }
            bit++;
        }
        return (String[])populatedList.toArray(new String[0]);
    }
    
    protected boolean isPopulatedNoCheck(String attribute) {
        return (fullyPopulated || isSet(populatedAttributes, attribute) );
    }
    
    /** Checks if a specific attribute is populated.
     * If the value object is fully
     * populated, i.e. <CODE>isFullyPopulated()</CODE> returns true,
     * this method returns true;
     * @param name the name of the attribute which is to be checked for population
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is no attribute with this name
     * @return true, if this attribute contains some data, false otherwise
     * @see #isFullyPopulated()
     */
    public boolean isPopulated(String attribute) {
        if (!isValidAttributeName(attribute)) {
            throw new java.lang.IllegalArgumentException("Invalid attribute name \""+attribute+"\".");
        }
        return isPopulatedNoCheck(attribute);
    }
    
    protected void populateAttribute(String attributeName) {
        set(populatedAttributes, attributeName);
        if (isAllSet(populatedAttributes)) {
            setFullyPopulated();
        }
    }
    
    /** Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        return fullyPopulated;
    }
    
    public void setFullyPopulated() {
        populatedAttributes = null;
        fullyPopulated = true;
    }

    /**
     * Reset all the attributes to unpopulated.
     * After this call calling getAllAttributes() must
     * raise the IllegalStateException.
     */
    public void unpopulateAllAttributes(){
        
        // set all attributes to null
        String[] populatedAttributeNames = getPopulatedAttributeNames();
        for (int i=0 ; i<populatedAttributeNames.length ; i++) {
            try {
                setAttributeValue(populatedAttributeNames[i], null);
            } catch (java.lang.IllegalArgumentException iae) {
            /* this happens
             * - if null is assigned to a primitive (like priority in OrderValue)
             * - if null is assigned to an attribute which does not accept null
             */
            }
        }
        
        dirtyAttributes.and(myAttributeManager.EMPTY_BITSET);
        if (fullyPopulated) {
            fullyPopulated = false;
            populatedAttributes = new BitSet(myAttributeManager.getAttributeCount());
        } else {
            populatedAttributes.and(myAttributeManager.EMPTY_BITSET);
        }
    }
    
    /*
     * checks if an attribute is populated, throws an IllegalArgumentException otherwise.
     * used in all get-methods. No check for valid attribute name is performed.
     */
    public void checkAttribute(String attribute) {
        if (!isPopulatedNoCheck(attribute)) {
            throw new java.lang.IllegalStateException("Attribute \""+attribute+"\" is not populated");
        }
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
    public void unpopulateAttribute( String attribute )
    throws java.lang.IllegalArgumentException
    {
        if (!isValidAttributeName(attribute)) 
        {
            throw new java.lang.IllegalArgumentException("\""+attribute+"\" is not an attribute");
        }
        try 
        {
            setAttributeValue(attribute, null);
        } 
        catch (java.lang.IllegalArgumentException iae) 
        {
        /* this happens
         * - if null is assigned to a primitive (like priority in OrderValue)
         * - if null is assigned to an attribute which does not accept null
         */
        }
        if (fullyPopulated)
        {
            populatedAttributes = new BitSet(myAttributeManager.getAttributeCount());
            populatedAttributes.or(myAttributeManager.SET_BITSET);
            fullyPopulated = false;
        }
        
        clear(dirtyAttributes, attribute);
        clear(populatedAttributes, attribute);
    }
    
        /*
        ============================================================================
        GETTABLE / SETTABLE ATTRIBUTES =============================================
        ============================================================================
         */
    public boolean isValidAttributeName(String attribute) {
        if (attribute!=null && !attribute.equals("")) {
            Iterator it = myAttributeManager.getAttributeIterator();
            while (it.hasNext()) {
                if (attribute.equals(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get all populated attribute values.
     *
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAllPopulatedAttributes() throws java.lang.IllegalStateException {
        String[] populatedAttributes = getPopulatedAttributeNames();
        Map attributeMap = new HashMap();
        for (int i=0 ; i<populatedAttributes.length ; i++) {
            attributeMap.put(populatedAttributes[i], getAttributeValue(populatedAttributes[i]));
        }
        return attributeMap;
    }
    
    /** Returns all attribute names, which are available in this value object.
     * <p>
     * Use
     * one of the returned names to access the generic methods <CODE>getAttributeValue(...)</CODE>
     * and <CODE>setAttributeValue(...)</CODE>.
     * <p>This method may be used by generic
     * clients to obtain information on the attributes. It does not say anything about
     * the state of an attribute, i.e. if it is populated or not.
     * @return the array contains all attribute names in no particular order.
     */
    public String[] getAttributeNames() {
        return myAttributeManager.getAttributeNames();
    }
    
     /**
      * Provide run-time support for the discovery of optional attributes
      * support.
      * 
      * @return The names of the optional attributes supported by an application.
      */
     public String[] getSupportedOptionalAttributeNames() {
         return myAttributeManager.getSupportedOptionalAttributeNames();
     }

     /** This method returns the value of the specified attribute.
     * @param attributeName the attribute's name
     * @return The attribute's value. Primitive types are wrapped in their respective classes.
     * @throws java.lang.IllegalArgumentException An <CODE>IllegalArgumentException</CODE> is thrown, when
     * <UL>
     * <LI>there is no attribute with this name
     * <LI>the attribute is not populated
     * </UL>
     */
    public java.lang.Object getAttributeValue(java.lang.String attributeName) throws java.lang.IllegalArgumentException {
        if (!isValidAttributeName(attributeName)) {
            throw new java.lang.IllegalArgumentException("Attribute \""+attributeName+"\" is not an attribute.");
        }
        // throws IllegalStateException if not populated
        checkAttribute(attributeName);
        if (getMethods==null) {
            // after deserialization
            getMethods = new HashMap();
        }
        Method getMethod = (Method)getMethods.get(attributeName);
        // if no getMethod was determined yet, look it up and put it in map
        if (getMethod == null) {
            try {
                PropertyDescriptor[] props = getBeanInfo().getPropertyDescriptors();
                for (int i=0 ; i<props.length ; i++) {
                    // is equalsIgnoreCase less efficiant than equals(Introspector.decapitalize())
                    if (props[i].getName().equals(Introspector.decapitalize(attributeName))) {
                        getMethod = props[i].getReadMethod();
                        getMethods.put(attributeName, getMethod);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        try {
            return getMethod.invoke(this, null);
        } catch (java.lang.IllegalAccessException iae) {
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable t = ite.getTargetException();
            if (t instanceof java.lang.IllegalArgumentException) {
                throw (java.lang.IllegalArgumentException)t;
            }
        }
        throw new java.lang.RuntimeException("Error invoking "+getMethod.toString());
    }
    
    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAttributeValues(String[] attributeNames) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (attributeNames == null) {
            throw new java.lang.IllegalArgumentException("attributeNames is null");
        }
        Map attributeMap = new HashMap();
        for (int i=0 ; i<attributeNames.length ; i++) {
            attributeMap.put(attributeNames[i], getAttributeValue(attributeNames[i]));
        }
        return attributeMap;
    }
    
    /** Assings a new value to an attribute. <p>
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
     * In any other case an exception is thrown.
     * </UL>
     * @throws java.lang.IllegalArgumentException This Exception is thrown, when either
     * <UL>
     * <LI>There is no attribute with this name
     * <LI>The value is out-of-range.
     * </UL>
     */
    public void setAttributeValue(java.lang.String attributeName,java.lang.Object newValue) throws java.lang.IllegalArgumentException 
    {
        if (!isValidAttributeName(attributeName)) 
        {
            throw new java.lang.IllegalArgumentException("Attribute \""+attributeName+"\" is not an attribute.");
        }
        if (setMethods == null) 
        {
            //after deserialization
            setMethods = new HashMap();
        }
        Method setMethod = (Method)setMethods.get(attributeName);
        if (setMethod == null) 
        {
            try {
                PropertyDescriptor[] props = getBeanInfo().getPropertyDescriptors();
                for (int i=0 ; i<props.length ; i++) {
                    // is equalsIgnoreCase less efficiant than equals(Introspector.decapitalize())
                    if (props[i].getName().equals(Introspector.decapitalize(attributeName))) 
                    {
                        setMethod = props[i].getWriteMethod();
                        setMethods.put(attributeName, setMethod);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        try 
        {
            if (setMethod.getParameterTypes()[0].isPrimitive() && newValue == null) 
            {
                throw new java.lang.IllegalArgumentException("Cannot assign null to an attribute which expects a primitive type");
            }
            setMethod.invoke(this, new Object[] {newValue});
            return;
        } 
        catch (java.lang.IllegalAccessException iae) 
        {
            System.out.println(iae.toString());
        }
        catch (java.lang.reflect.InvocationTargetException ite) 
        {
            Throwable t = ite.getTargetException();
            if (t instanceof java.lang.IllegalArgumentException) 
            {
                throw (java.lang.IllegalArgumentException)t;
            } else 
            {
                throw new java.lang.RuntimeException("Exception while setting value \""+(newValue==null?"null":newValue.toString())+"\" for attribute "+attributeName+":\n"+ite.toString()+"-------\n"+t.toString());
            }
        }
        throw new java.lang.RuntimeException("Error invoking "+setMethod.toString());
    }
    
    /**
     * Setting multiple attribute values
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        Iterator it = attributeNamesAndValuePairs.entrySet().iterator();
        Map.Entry anEntry;
        while (it.hasNext()) {
            anEntry = (Map.Entry)it.next();
            setAttributeValue((String)anEntry.getKey(), anEntry.getValue());
        }
    }
    
    
    protected BeanInfo getBeanInfo() throws IntrospectionException 
    {
        // Introspector caches bean info!
        return Introspector.getBeanInfo(this.getClass(), ManagedEntityValueImpl.class.getSuperclass());
       //return null;
    }
    
    private void readObject(java.io.ObjectInputStream in)
    throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initAttributeManager();
    }
    
    
    //does not really belong here, but then, each query value does not have to define it's own empty methods
    
    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType The class name of the serializer interface that must
     * be created. For example <CODE>XmlSerializer.getClass().getName()</CODE>
     * @return A serializer matching the serializer type .
     * @exception java.lang.IllegalArgumentException If an Illegal Argument is provided or if no Serializer can be created matching
     * the provided Serializer Type.
     */
    public Serializer makeSerializer(String serializerType) throws java.lang.IllegalArgumentException {
            if( serializerType.equals(XmlSerializer.class.getName()))
        return new XmlSerializerImpl(this.getClass().getName() );
        else throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");
    }
    
    /**
     * Returns all the serializer types than can be created by this factory.
     * This may return an empty array, in case no serializer is
     * implemented.
     *
     * @return Array of supported serializer types.
     */
    public String[] getSupportedSerializerTypes() {
           String[] serializerTypes = new String[1];
        serializerTypes[0] = new String( XmlSerializer.class.getName());
        return serializerTypes;
}
    
}
