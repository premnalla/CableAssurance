package javax.oss;


/**
 * The <CODE>ManagedEntityValue</CODE> interface is the base interface for all more
 * detailed interfaces which represent any kind of object that is to be managed.
 * <p>
 * Classes which implement <CODE>ManagedEntityValue</CODE> or a
 * sub interface of <CODE>ManagedEntityValue</CODE>.
 * are also called value classes.
 * <P>All classes implementing a value interface provide several ways to access
 * the attributes:
 * <UL>
 * <LI>Attributes can be accessed through standard JavaBeans get/set (is/set)
 * methods.
 * <LI>Attributes can be accessed through the generic methods defined
 *     in Attribute Access
 * <UL>
 * <LI><CODE>public Object getAttributeValue(String attributeName)</CODE>
 * <LI><CODE>public void setAttributeValue(String attributeName, Object newValue)</CODE>
 * </UL>
 *
 *
 */
public interface ManagedEntityValue extends javax.oss.AttributeAccess,
java.io.Serializable, java.lang.Cloneable, javax.oss.SerializerFactory
{

    /** This String defines the attribute name for the <CODE>ManagedEntityKey</CODE> attribute.
     *  It's value is "managedEntityKey".
     */
    public static final String KEY = "managedEntityKey";


    /**
     * Return a deep copy of this value.
     */
    public Object clone();

    /**
     * Get the version number of the last update.
     */
    public long getLastUpdateVersionNumber();


    /**
     * Set the last update version number.
     *
     * This field should never be set by the application client. Mutator is
     * provided for Serialization and Deserialization purposes only.
     *
     * @param lastUpdateNumber
     * @throws java.lang.IllegalArgumentException
     */
    public void setLastUpdateVersionNumber( long lastUpdateNumber)
    throws java.lang.IllegalArgumentException;


    /** Get the key for this object. The key is unique over all objects.
     * @return the key for this value object.
     * @throws java.lang.IllegalStateException in case the key attribute is not populated.
     * @see javax.oss.ManagedEntityKey
     */
    public ManagedEntityKey getManagedEntityKey()
    throws java.lang.IllegalStateException;


    /** Set a new key for this value object.
     * <p>
     * May be used when
     * there is a need to search for an specific object using this value as a
     * template.
     * @param key the new value for the key
     * @throws java.lang.IllegalArgumentException when the given key is
     * not of correct type. Typically, subinterfaces of ManagedEntityValue have
     * a corresponding subinterface of ManagedEntityKey. An implementing type of
     * this sub key class might be expected as a parameter.
     */

    public void setManagedEntityKey( ManagedEntityKey key )
    throws java.lang.IllegalArgumentException;

    /**
     * Manufacture a Key for this managed entity.
     * @return a newly created empty key of the appropriate type.
     */
    public ManagedEntityKey makeManagedEntityKey();

    /** Get names of all attributes which the server allows the client to set.
     * <p>
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     * @return the names of attributes that can be set by the client.
     */
    public String[] getSettableAttributeNames();



}


