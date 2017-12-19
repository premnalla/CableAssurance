package ossj.ttri;

import javax.oss.QueryValue;

/**
 * QueryValue Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class QueryValueImpl implements QueryValue,
        java.io.Serializable {
    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        return (javax.oss.Serializer) null;
    }

    public String[] getSupportedSerializerTypes() {
        return null;
    }

    public String[] getSupportedOptionalAttributeNames() {
        return null;
    }


    String QueryName;

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone()
            //throws CloneNotSupportedException
    {
        return null;
        //super.clone();
    }

    public void reset() {
    }

    public void setQueryName(String queryName) {
        QueryName = queryName;
    }

    public String getQueryName() {
        return QueryName;
    }



    //-------------------------------------------------------------------------
    //
    // Methods from AttributeAccess - not implemented yet.
    //
    //-------------------------------------------------------------------------

    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
        return new java.util.Hashtable();
    }

    /**
     * Setting multiple attribute values
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
    }

    /**
     * Get all populated attribute values.
     *
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAllPopulatedAttributes()
            throws java.lang.IllegalStateException {
        return new java.util.Hashtable();
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
    public Object getAttributeValue(String attributeName)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
        return new Object();
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
    public void setAttributeValue(String attributeName, Object Value)
            throws java.lang.IllegalArgumentException {
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
        return new String[1];
    }

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
        return new String[1];
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
    public boolean isPopulated(String name)
            throws java.lang.IllegalArgumentException {
        return false;
    }

    /** Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        return false;
    }


    /**
     * Reset all the attributes to unpopulated.
     * After this call calling getAllAttributes() must
     * raise the IllegalStateException.
     */
    public void unpopulateAllAttributes() {
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
    public void unpopulateAttribute(String attr_name)
            throws java.lang.IllegalArgumentException {
    }

}
