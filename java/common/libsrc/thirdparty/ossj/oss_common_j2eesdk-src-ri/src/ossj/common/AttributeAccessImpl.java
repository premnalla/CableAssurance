/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * This is an implementation of the AttributeAccess.
 *
 * This Implementation handle the dirty marker as noted in the AttributeAccess definition:
 *<BR>
 * The dirty marker of an attribute determine is the attribute is
 * clean or not. For Example, an attribute that has been written
 * by the application, but the change have not been replicated
 * into the persistent representation of this attribute. This
 * "dirty" information will be mainly used by the implementation/application.
 * <p>
 * For example:
 * <CODE>
 * ServiceValue myService = serviceSession.getServiceValue(myServiceKey);
 * // All Populated attributes of myService will not be "dirty".
 * myService.setMandatory(true);
 * // "mandatory" attribute is now tag "dirty"
 * serviceSession.setService(myService);
 * // When the client is calling the setService method,
 * // the session knows, that only the "mandatory" attribute has been changed,
 * // and nothing else.
 *</CODE> *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public abstract class AttributeAccessImpl extends java.lang.Object
        implements javax.oss.AttributeAccess {
    private BitSet dirtyAttributes;
    private BitSet populatedAttributes;
    private transient ArrayList enumerations;
    private boolean fullyPopulated;
    protected transient AttributeManager myAttributeManager;
    private transient Map getMethods;
    private transient Map setMethods;
    private transient Map attributeTypes;
    
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
    protected void configureAttributes(AttributeManager anAttributeManager) {
    }
    
    //VP change from private to public for the clone method ....
    public void initAttributeManager() {
        myAttributeManager = getAttributeManager();
        
        if (myAttributeManager == null) {
            myAttributeManager = makeAttributeManager();
            addAttributesTo(myAttributeManager);
            myAttributeManager.lock();
            configureAttributes(myAttributeManager);
        }
    }
    
    /**
     * DOCUMENT ME!
     */
    public void initBitSets() {
        int numberOfAttr = myAttributeManager.getAttributeCount();
        dirtyAttributes = new BitSet(numberOfAttr);
        populatedAttributes = new BitSet(numberOfAttr);
        fullyPopulated = false;
    }
    
    private boolean isSet(BitSet aBitSet, String attribute) {
        return aBitSet.get(myAttributeManager.getPosition(attribute));
    }
    
    private boolean isAllSet(BitSet aBitSet) {
        for (int i = 0; i < myAttributeManager.getAttributeCount(); i++) {
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
    
    /**
     * Sets the dirty flag for the given attribute name
     *
     * @param attribute name
     * @param status true means false means
     */
    public void setDirtyAttribute(String attribute, boolean status) {
        if (status) {
            if (!isSet(dirtyAttributes, attribute)) {
                set(dirtyAttributes, attribute);
                
                if (!(fullyPopulated || isSet(populatedAttributes, attribute))) {
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
    
    /**
     * return true if the given attribute is dirty
     *
     * @param attribute name of the attribute
     *
     * @return true if the attribute is noted dirty
     */
    public boolean isDirty(String attribute) {
        return isSet(dirtyAttributes, attribute);
    }
    
    /**
     * returns the list fo dirty attribute names
     *
     * @return the array of dirty attribute names
     */
    public String[] getDirtyAttributeNames() {
        Iterator attributeIterator = myAttributeManager.getAttributeIterator();
        List dirtyList = new ArrayList();
        String attribute;
        int bit = 0;
        
        while (attributeIterator.hasNext()) {
            attribute = (String) attributeIterator.next();
            
            if (dirtyAttributes.get(bit)) {
                dirtyList.add(attribute);
            }
            
            bit++;
        }
        
        return (String[]) dirtyList.toArray(new String[0]);
    }
    
    /**
     * Resets the dirty attribute list
     */
    public void cleanAttributes() {
        dirtyAttributes.and(myAttributeManager.EMPTY_BITSET);
    }
    
    /*
    ============================================================================
    ENUMERATIONS ===============================================================
    ============================================================================
     */
    
    /**
     * Add a enumeration type to the list.
     * If the attribute Name is already in the list the attribute type will be
     * replaced by the given one.
     *
     * @param attName the attribute Name
     * @param attType the enumeration type
     */
    protected void addEnumeration(String attName, String attType) {
        if (attName != null && attType != null){
            if (enumerations == null){
                enumerations = new ArrayList();
            }
            enumerations.add(attName);
            String[] list = new String[1];
            list[0] = attType;
            addAttributeTypes(attName,list);
        }
    }
    
    /**
     * Remove a attribute name from the enumartion list.
     * if the name do not exists nothing is done.
     *
     * @param attName the attribute Name
     */
    protected void removeEnumeration(String attName){
        if (enumerations != null && attName != null){
            enumerations.remove(attName);
        }
    }
    /**
     * Returns true if the given attribute is in a format of an enumeration. If
     * true is returned, then a call to attributeTypeFor() method will return
     * the type of the class that contains the possible value definitions rather
     * than the proper type of the handle value.
     * <p>
     * For exemple:
     * <PRE> if
     * (troubleTicketValue.isEnumerationBased("troubleTicketState")){
     * System.out.println("tt state type is
     * "+troubeTicketValue.attributeTypeFor("troubleTicketState")); // displays
     * javax.oss.cbe.trouble.TroubleTicketState instead of java.lang.String
     * </PRE>
     * <p>
     *
     * @param attributeName
     *            the name of the attribute to check
     * @return true if the attribute is an enumeration type, false otherwise.
     * @exception java.lang.IllegalArgumentException
     *                the attribute name is null or is not recognized, or the
     *                value is bad
     */
    
    public boolean isEnumerationBased(String attributeName)
    throws java.lang.IllegalArgumentException{
        if (!isValidAttributeName(attributeName)) {
            throw new java.lang.IllegalArgumentException("Attribute \"" + attributeName
                    + "\" is not a valid attribute name.");
        }
        if (enumerations != null && enumerations.contains(attributeName)){
            return true;
        } else {
            return false;
        }
    }
    
    /*
    ============================================================================
    META DATA ==================================================================
    ============================================================================
     */
    /**
     * Add a types supported by this attribute.
     * If the attribute Name is already in the list, the attribute types will be
     * added to the existing list.
     *
     * @param attName the attribute Name
     * @param the list of types
     */
    protected void addAttributeTypes(String attName, String[] attTypes){
        if (attName != null && attTypes != null){
            if (attributeTypes == null ) {
                attributeTypes = new HashMap();
            }
            
            if (attributeTypes.containsKey(attName)){                
                ArrayList types = new ArrayList((Arrays.asList((String[])attributeTypes.get(attName))));
                for (int i = attTypes.length-1;i>=0;i--){
                    if (!types.contains(attTypes[i])){
                        types.add(attTypes[i]);
                    }
                }
                attributeTypes.put(attName,types.toArray(new String[0]));
            } else {
                attributeTypes.put(attName, attTypes);
            }
        }
    }
    /**
     * Remove a attribute name from the attribute types list.
     * if the name do not exists nothing is done.
     *
     * @param attName the attribute Name
     */
    protected void removeAttributeTypes(String attName){
        if (attributeTypes != null && attName != null){
            attributeTypes.remove(attName);
        }
    }
    
    /**
     * Returns the array of supported types of the given attribute name, or null if the attribute
     * name is unknown. If the attribute is an enumeration based attribute like:
     * <UL>
     * <LI> *State
     * <LI> *Status
     * <LI> etc
     * </UL>
     * this method shall return the type of the definition instead of int or
     * String.
     *
     * If (isEnumerationBased) return String[0] = enumeration type
     * <BR>If (attribute name registered using addAttributeTypes()) return the String[] of recorded attribute types
     * <BR>else return String[1] = ((Method)getMethods.get(attributeName)).getReturnType().getName()
     * <p>
     *
     * @param attributeName
     *            name of the attribute
     * @return the array of supported types of the given attribute name
     * @exception java.lang.IllegalArgumentException
     *                (the attribute name is null or is not recognized, or the
     *                value is bad)
     */
    public String[] attributeTypeFor(String attributeName)
    throws java.lang.IllegalArgumentException{
        if (!isValidAttributeName(attributeName)) {
            throw new java.lang.IllegalArgumentException("Attribute \"" + attributeName
                    + "\" is not a valid attribute name.");
        }
        
        if (attributeTypes != null && attributeTypes.containsKey(attributeName)){
            return (String[])attributeTypes.get(attributeName);
        } else {
            String [] list = new String[1];
            try {
                list[0] = ((Method)getMethods.get(attributeName)).getReturnType().getName();
            } catch (GenericSignatureFormatError gsfe){
                throw new java.lang.IllegalArgumentException("Access to Attribute \"" + attributeName
                        + "\" generates a GenericSignatureFormatError: "+gsfe.getMessage());
                
            } catch (TypeNotPresentException tnpe){
                throw new java.lang.IllegalArgumentException("Access to Attribute \"" + attributeName
                        + "\" generates a TypeNotPresentException: "+tnpe.getMessage());
                
            } catch(MalformedParameterizedTypeException mpte){
                throw new java.lang.IllegalArgumentException("Access to Attribute \"" + attributeName
                        + "\" generates a MalformedParameterizedTypeException: "+mpte.getMessage());
            }
            return list;
        }
    }
    
    /**
     * Generic factory method for supported types.
     * <p>
     * This method try to first find a factory method:
     * <BR> if the typeName ends with "Impl", the Impl is cut)
     * <BR> make<typeName.substring(typeName.lastIndexOf(".")+1)>()
     * <BR> or
     * <BR> make<typeName.substring(typeName.lastIndexOf(".")+1)>(typeName)
     * <BR>
     * if it fails the
     * <BR> Class.forName(typeName).newInstance();
     * <P>
     *
     * @param typeName the type of the object to be created
     * @return the new created instance of the given type or null
     * @exception java.lang.IllegalArgumentException
     *                (the attribute name is not recognized, or the
     *                value is bad)
     */
    public Object make(String typeName) throws java.lang.IllegalArgumentException{
        
        Object retVal = null;
        Class cls = null;
        
        if (typeName == null){
            throw new java.lang.IllegalArgumentException("provided null typeName is not supported.");
        }
        if (typeName.startsWith("[L")){
            throw new java.lang.IllegalArgumentException("Array type \"" + typeName
                    + "\" are not supported.");
        }
        
        String type = typeName.substring(typeName.lastIndexOf(".")+1).replaceFirst("Impl$","");
        
        try {
            // try first the call to makeXxxx()
            retVal = this.getClass().getMethod("make"+type,(Class[])null).invoke(this,(Object[])null);
        } catch (Exception ex){
            // try then the call to makeXxxx(String)
            try{
                
                retVal = this.getClass().getMethod("make"+type,String.class).invoke(this,typeName);
            } catch (Exception newEx){
                retVal = null;
            }
        }
        
        if (retVal == null) {
            // try a forName
            try {
                retVal = Class.forName(typeName).newInstance();
            } catch (Exception ex){
                throw new java.lang.IllegalArgumentException("Array type \"" + typeName
                        + "\" are not supported. forName throws "+ex.toString());
            }
        }
        return retVal;
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
    public String[] getPopulatedAttributeNames() {
        Iterator attributeIterator = myAttributeManager.getAttributeIterator();
        List populatedList = new ArrayList();
        String attribute;
        int bit = 0;
        
        while (attributeIterator.hasNext()) {
            attribute = (String) attributeIterator.next();
            
            if (fullyPopulated || populatedAttributes.get(bit)) {
                populatedList.add(attribute);
            }
            
            bit++;
        }
        
        return (String[]) populatedList.toArray(new String[0]);
    }
    
    protected boolean isPopulatedNoCheck(String attribute) {
        return (fullyPopulated || isSet(populatedAttributes, attribute));
    }
    
    /**
     * Checks if a specific attribute is populated.
     * If the value object is fully
     * populated, i.e. <CODE>isFullyPopulated()</CODE> returns true,
     * this method returns true;
     * @param name the name of the attribute which is to be checked for population
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is null
     * @throws javax.oss.javax.oss.UnsupportedAttributeException if optional attribute is not supported
     * @return true, if this attribute contains some data, false otherwise
     * @see #isFullyPopulated()
     */
    public boolean isPopulated(String attribute) {
        if ((attribute == null) || attribute.equals("")) {
            throw new java.lang.IllegalArgumentException(
                    "Null or empty String are invalid attribute names.");
        }
        
        //VP TODO add the test for unsupported attribute
        if (!isValidAttributeName(attribute)) {
            throw new java.lang.IllegalArgumentException("Invalid attribute name \"" + attribute
                    + "\".");
        }
        
        return isPopulatedNoCheck(attribute);
    }
    
    protected void populateAttribute(String attributeName) {
        set(populatedAttributes, attributeName);
        
        if (isAllSet(populatedAttributes)) {
            setFullyPopulated();
        }
    }
    
    /**
     * Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        return fullyPopulated;
    }
    
    /**
     * Set Populated flag for all attributes...
     * @see #isFullyPopulated
     */
    public void setFullyPopulated() {
        populatedAttributes = null;
        fullyPopulated = true;
    }
    
    /**
     * Reset all the attributes to unpopulated.
     * After this call calling getAllAttributes() must
     * raise the IllegalStateException.
     */
    public void unpopulateAllAttributes() {
        // set all attributes to null
        String[] populatedAttributeNames = getPopulatedAttributeNames();
        
        for (int i = 0; i < populatedAttributeNames.length; i++) {
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
    
    /**
     * checks if an attribute is populated, throws an IllegalArgumentException otherwise.
     * used in all get-methods. No check for valid attribute name is performed.
     */
    public void checkAttribute(String attribute) {
        if (!isPopulatedNoCheck(attribute)) {
            throw new java.lang.IllegalStateException("Attribute \"" + attribute
                    + "\" is not populated");
        }
    }
    
    /**
     * Unpopulate a Single Attribute.
     * After this call getAttribute( String attName ) must
     * raise the IllegalStateException
     *
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is null
     * @throws javax.oss.javax.oss.UnsupportedAttributeException if optional attribute is not supported
     *
     * @param attr_name The attribute which shall be unpopulated.
     * @see #unpopulateAllAttributes()
     */
    public void unpopulateAttribute(String attribute) throws java.lang.IllegalArgumentException {
        if ((attribute == null) || attribute.equals("")) {
            throw new java.lang.IllegalArgumentException(
                    "Null or empty String are invalid attribute names.");
        }
        
        //VP TODO add the test for unsupported attribute
        if (!isValidAttributeName(attribute)) {
            throw new java.lang.IllegalArgumentException("Invalid attribute name \"" + attribute
                    + "\".");
        }
        
        try {
            setAttributeValue(attribute, null);
        } catch (java.lang.IllegalArgumentException iae) {
            /* this happens
             * - if null is assigned to a primitive (like priority in OrderValue)
             * - if null is assigned to an attribute which does not accept null
             */
        }
        
        if (fullyPopulated) {
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
    /**
     * Returns true if the attribute name is valid for this component
     */
    public boolean isValidAttributeName(String attribute) {
        if ((attribute != null) && !attribute.equals("")) {
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
        
        for (int i = 0; i < populatedAttributes.length; i++) {
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
    public java.lang.Object getAttributeValue(java.lang.String attributeName)
    throws java.lang.IllegalArgumentException {
        if (!isValidAttributeName(attributeName)) {
            throw new java.lang.IllegalArgumentException("Attribute \"" + attributeName
                    + "\" is not an attribute.");
        }
        
        // throws IllegalStateException if not populated
        checkAttribute(attributeName);
        
        if (getMethods == null) {
            // after deserialization
            getMethods = new HashMap();
        }
        
        Method getMethod = (Method) getMethods.get(attributeName);
        
        // if no getMethod was determined yet, look it up and put it in map
        if (getMethod == null) {
            try {
                PropertyDescriptor[] props = getBeanInfo().getPropertyDescriptors();
                
                for (int i = 0; i < props.length; i++) {
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
            return getMethod.invoke(this, (Object[])null);
        } catch (java.lang.IllegalAccessException iae) {
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable t = ite.getTargetException();
            
            if (t instanceof java.lang.IllegalArgumentException) {
                throw (java.lang.IllegalArgumentException) t;
            }
        }
        
        throw new java.lang.RuntimeException("Error invoking " + getMethod.toString());
    }
    
    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
    throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (attributeNames == null) {
            throw new java.lang.IllegalArgumentException("attributeNames is null");
        }
        
        Map attributeMap = new HashMap();
        
        for (int i = 0; i < attributeNames.length; i++) {
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
    public void setAttributeValue(java.lang.String attributeName, java.lang.Object newValue)
    throws java.lang.IllegalArgumentException {
        if (!isValidAttributeName(attributeName)) {
            throw new java.lang.IllegalArgumentException("Attribute \"" + attributeName
                    + "\" is not an attribute.");
        }
        
        if (setMethods == null) {
            //after deserialization
            setMethods = new HashMap();
        }
        
        Method setMethod = (Method) setMethods.get(attributeName);
        
        if (setMethod == null) {
            try {
                PropertyDescriptor[] props = getBeanInfo().getPropertyDescriptors();
                
                for (int i = 0; i < props.length; i++) {
                    // is equalsIgnoreCase less efficiant than equals(Introspector.decapitalize())
                    if (props[i].getName().equals(Introspector.decapitalize(attributeName))) {
                        setMethod = props[i].getWriteMethod();
                        setMethods.put(attributeName, setMethod);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        
        try {
            if (setMethod.getParameterTypes()[0].isPrimitive() && (newValue == null)) {
                throw new java.lang.IllegalArgumentException(
                        "Cannot assign null to an attribute which expects a primitive type");
            }
            
            setMethod.invoke(this, new Object[] { newValue });
            
            return;
        } catch (java.lang.IllegalAccessException iae) {
            System.out.println(iae.toString());
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable t = ite.getTargetException();
            
            if (t instanceof java.lang.IllegalArgumentException) {
                throw (java.lang.IllegalArgumentException) t;
            } else if (t instanceof java.lang.IllegalStateException) {
                throw (java.lang.IllegalStateException) t;
            } else {
                throw new java.lang.RuntimeException("Exception while setting value \""
                        + ((newValue == null) ? "null" : newValue.toString()) + "\" for attribute "
                        + attributeName + ":\n" + ite.toString() + "-------\n" + t.toString());
            }
        }
        
        throw new java.lang.RuntimeException("Error invoking " + setMethod.toString());
    }
    
    /**
     * Setting multiple attribute values
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs)
    throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        Iterator it = attributeNamesAndValuePairs.entrySet().iterator();
        Map.Entry anEntry;
        
        while (it.hasNext()) {
            anEntry = (Map.Entry) it.next();
            setAttributeValue((String) anEntry.getKey(), anEntry.getValue());
        }
    }
    
    protected BeanInfo getBeanInfo() throws IntrospectionException {
        // Introspector caches bean info!
        return Introspector.getBeanInfo(this.getClass(),
                ManagedEntityValueImpl.class.getSuperclass());
        
        //return null;
    }
    
    private void readObject(java.io.ObjectInputStream in)
    throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initAttributeManager();
    }
    
    /**
     * Returns a deep copy of this value
     *
     * Subclasses of ManagedEntity should call this method first, which calls Object.clone() itself to create
     * a shallow copy first, and then hande all properties which have to be deeply cloned.
     * @return deep copy of this Event
     */
    public Object clone() {
        // first call clone in Object
        try {
            AttributeAccessImpl myClone = (AttributeAccessImpl) super.clone();
            myClone.initAttributeManager();
            myClone.initBitSets();
            
            String[] dirty = getDirtyAttributeNames();
            String[] pops = getPopulatedAttributeNames();
            for (int i=pops.length-1; i>=0; i--) {
                myClone.populateAttribute(pops[i]);
            }
            for (int i=dirty.length-1; i>=0; i--) {
                myClone.setDirtyAttribute(dirty[i]);
            }
            String name = null;
            if (enumerations != null){
//                for (Iterator e = enumerations.iterator(); e.hasNext();){
//                    name = (String)e.next();
//                    myClone.addEnumeration(name,(String)((String[])attributeTypes.get(name))[0]);
//                }
                // dont' use Iterator because it cause java.util.ConcurrentModificationException
                String[] eList = (String[]) enumerations.toArray(new String[0]);
                
                for (int i = eList.length -1 ; i >= 0 ; i--) {
                    myClone.addEnumeration(eList[i],(String)((String[])attributeTypes.get(eList[i]))[0]);
                }
                
            }
            if (attributeTypes != null){
                for (Iterator e = attributeTypes.keySet().iterator(); e.hasNext();){
                    name = (String)e.next();
                    myClone.addAttributeTypes(name,(String[])attributeTypes.get(name));
                }
            }
            
            return myClone;
        } catch (java.lang.CloneNotSupportedException cnse) {
            throw new java.lang.RuntimeException("AttributeAccessImpl: Error invoking clone() CloneNotSupportedException, " +cnse.getMessage());
            //return null;
        }
    }
}
