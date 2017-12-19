package ossj.qos.fmri;

import javax.oss.QueryValue;
import javax.oss.AttributeAccess;

import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import ossj.qos.util.Util;

/**
 * QueryValueImpl
 *
 * @author  Audrey Ward
 * @version 1.0 
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public abstract class QueryValueImpl implements QueryValue, AttributeAccess {

    // Tracks the populated attributes
    protected transient TreeSet populatedAttributeNames = null;

    // Tracks the populated attributes over the wire
    private BitSet populateAttributeVessel = null;

    // Map containing a handler representing every attribute
    protected static TreeMap attributeHandlerMap = null;

    // Manager that is responsible for the attribute descriptors
    protected static AttributeManager attributeManager = null;

    /**
     * QueryValueImpl - default constructor
     */
    public QueryValueImpl()  {
        // Container for determining if an attribute has been populated.
        populatedAttributeNames = new TreeSet();
    }

    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeManager = new AttributeManager();

        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        // No need to set the parent since this is the BASE CLASS.
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }

    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return QueryValueImpl.attributeManager;
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

    private String[] getSupportedAttributeNames() {
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
        if ( handler != null ) {
            obj = handler.getObject(this);
            //System.out.println( "getting ; " + Util.printObject( attributeName )  + " = "  + Util.printObject( obj ) );
        }
        else {
            //System.out.println( "getting : " + LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName );
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
            //System.out.println( "setting ; " + Util.printObject( attributeName )  + " = " + Util.printObject( Value ) );
        }
        else {
            //System.out.println( "setting : " + LogMessages.ATTRIBUTE_NOT_FOUND_MSG + attributeName );
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
            //System.out.println( "isPopulated " + attributeName + " : " + populated );
        }
        else {
            //System.out.println( "isPopulated: IllegalArgumentException - Attribute Not Found - " + attributeName );
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
                //System.out.println( "unpopulateAttribute: " + attributeName );
                //System.out.println( "remaining populatedNames are : " + Util.printObject( populatedAttributeNames ) );
            }
            else {
                //System.out.println( "unpopulateAttribute: IllegalArgumentException - UNSETTABLE - " + attributeName );
                throw new java.lang.IllegalArgumentException( LogMessages.UNSETTABLE_ATTRIBUTE_MSG + attributeName );
            }
        }
        else {
            //System.out.println( "unpopulateAttribute: IllegalArgumentException - Not Found - " + attributeName );
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
        //System.out.println( "unpopulateAllAttributes called");
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

        //System.out.println( "getAttributesValues - attribute names = " + Util.printObject( attributeNames ) );

        HashMap map = new HashMap();

        if ( attributeNames == null || attributeNames.length == 0 ) {
            //System.out.println( "getAttributeValues: threw IllegalArgumentException for nulls" );
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

        //System.out.println( "setAttributesValues - attribute names = " + Util.printObject( attributeNamesAndValuePairs ) );
        if ( attributeNamesAndValuePairs != null ) {
            Map beforeSetAttributes = null;
            // save the state of the value object
            try {
                beforeSetAttributes = getAllPopulatedAttributes();
            }
            catch ( IllegalStateException ex1 ) {
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
            catch ( IllegalArgumentException ex2 ) {
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
            //System.out.println( "setAttributesValues: IllegalArgumentException - null map entered" );
            throw new IllegalArgumentException( "Null Map of attributeName/value pairs entered");
        }
        return;
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

    // interface for dealing with generic access

    interface AttrObjectHandler {

        public Object getObject( QueryValue query )
        throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException;

        public void setObject( QueryValue query, Object obj )
        throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException;

        public void clearObject( QueryValue query );
    }

    // static inner classes that represent each attribute

    /**
     * Deep copy of this Query
     *
     * @return deep copy of this Query
     */
    public Object clone() {
        QueryValueImpl obj = null;
        try {
            obj = (QueryValueImpl) super.clone();
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

}
