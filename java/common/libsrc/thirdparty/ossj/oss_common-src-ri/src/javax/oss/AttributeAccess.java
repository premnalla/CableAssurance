package javax.oss;

/**
 * Base interface from which all Value objects must
 * be derived.  Represents both an attribute dirty
 * marker and a generic attribute accessor.
 * Classes implementing a value interface provide several ways to access
 * the attributes:
 * <UL>
 * <LI>Attributes can be accessed through standard JavaBeans get/set (is/set)
 * methods.
 * <LI>Attributes can be accessed through the generic methods:
 * <UL>
 * <LI><CODE>public Object getAttributeValue(String attributeName)</CODE>
 * <LI><CODE>public void setAttributeValue(String attributeName, Object newValue)</CODE>
 * </UL>
 * </UL>
 * A client needs to know which attributes exist in order to provide
 * correct attributeNames. It can get this information with a call to
 * <CODE>public String[] getAttributeNames()</CODE>.
 * <P>An attribute may or may not be populated with a value.
 * An attribute is populated by calling <CODE>public void setAttributeValue(...)</CODE>.
 * A client can determine if an attribute is populated by calling
 * <CODE>public boolean isPopulated(String attributeName)</CODE>.
 *
 */
public interface AttributeAccess extends java.io.Serializable, Cloneable
{

    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * (if null array is provided or if one of the attributes is not recognized)
     * @exception java.lang.IllegalStateException
     * (if one of the attributes is not populated)
     * @exception javax.oss.UnsupportedAttributeException
     * (if one of the attributes is not supported)
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
    throws java.lang.IllegalArgumentException,
           java.lang.IllegalStateException,
           javax.oss.UnsupportedAttributeException;
    /**
     * Set multiple attribute values.
     *
     * @param attributeNamesAndValuePairs
     * @exception java.lang.IllegalArgumentException
     * (one of the attributes is not well formed or unrecognized)
     * @exception javax.oss.UnsupportedAttributeException
     * (one of the attributes is optional and is not supported)
     */

    public void setAttributeValues(java.util.Map attributeNamesAndValuePairs)
    throws java.lang.IllegalArgumentException,
           javax.oss.UnsupportedAttributeException;
    /**
     * Get all populated attribute values.
     * If no attributes are populated an empty Map is returned.
     *
     */


    public java.util.Map getAllPopulatedAttributes();


 /** Return the value of the specified attribute.
     * @param attributeName the attribute's name
     * @return the attribute's value. Primitive types are wrapped in their respective classes.
     * @exception java.lang.IllegalArgumentException
     * (the attribute name is null or is not recognized)
     * @exception java.lang.IllegalStateException
     * (the attribute is not populated)
     * @exception javax.oss.UnsupportedAttributeException
     * (the attribute is optional and not supported)
     *
     */
    public Object getAttributeValue(String attributeName)
    throws java.lang.IllegalArgumentException,
           java.lang.IllegalStateException,
           javax.oss.UnsupportedAttributeException;

    /** Assign a new value to an attribute. <p>
     * Even though some attributes
     * may be read-only in the server implementation, they can be set here nonetheless.
     * This is because value objects are also used as templates for a "query by template".
     * To see which attributes can be set in the server implementation, the client needs to call
     * <CODE>getSettableAttributeNames()</CODE>.
     * @param attributeName The attribute's name which shall be changed
     * @param value The attribute's new value. This can either be:
     * <UL>
     * <LI>An object of the type associated with <CODE>attributeName</CODE>
     * <LI>A wrapper object for a primitive type, e.g. an <CODE>Integer</CODE> wrapping an <CODE>int</CODE>
     * </UL>
     * @exception java.lang.IllegalArgumentException
     * (the attribute name is null or is not recognized, or the value is bad)
     * @exception javax.oss.UnsupportedAttributeException
     * (the attribute is optional and is not supported)
     */
    public void setAttributeValue(String attributeName, Object value)
    throws java.lang.IllegalArgumentException,
           javax.oss.UnsupportedAttributeException;


    /** Return all attribute names, which are available in this value object.
     * <p>
     * The returned names may be used as arguments to the generic methods <CODE>getAttributeValue(...)</CODE>
     * and <CODE>setAttributeValue(...)</CODE>.
     * <p>This method may be used by generic
     * clients to obtain information on the attributes. It does not say anything about
     * the state of an attribute, i.e. if it is populated or not.
     * @return an array containing all attribute names in no particular order.
     */
    public String[] getAttributeNames();


    /** Get the names of all populated attributes.
     * <p>Although an attribute is populated, it can be <CODE>null</CODE>!
     * @return names of all poplulated attributes.
     * When no attributes are populated an <B>empty array</B> is
     * returned.
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String[] getPopulatedAttributeNames();

    /** Check if a specific attribute is populated.
     * @param attributeName the name of the attribute which is to be checked for population.
     * @throws java.lang.IllegalArgumentException when there is no attribute with this name.
     * @return true, if this attribute is populated, false otherwise.
     * @see #isFullyPopulated()
     */
    public boolean isPopulated( String attributeName)
    throws java.lang.IllegalArgumentException;



    /** Return true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated, false otherwise.
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated();

    /**
     * Reset all the attributes to unpopulated.
     */
    public void unpopulateAllAttributes();

    /**
     * Mark a single attribute as unpopulated.
     * After this call getAttribute( String attributeName ) must
     * raise the IllegalStateException.
     *
     * @param attributeName name of the attribute to be unpopulated.
     * @throws java.lang.IllegalArgumentException if this is not a valid attribute name.
     * @see #unpopulateAllAttributes()
     */
    public void unpopulateAttribute( String attributeName )
    throws java.lang.IllegalArgumentException;


     /**
      * Provide run-time support for the discovery of optional attributes.
      *
      * @return the names of the optional attributes supported by the server.
      */
     public String[] getSupportedOptionalAttributeNames();
}

