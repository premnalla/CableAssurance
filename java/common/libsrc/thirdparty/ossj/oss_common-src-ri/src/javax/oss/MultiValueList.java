package javax.oss;


/**
 * Base interface for all MultiValueList attributes.
 * <p>
 * By default no operations on the ManagedEntityValue object are defined for
 * the addition or removal of individual values to multi-valued attributes.
 * If  this functionality is required then the multi-valued attribute should
 * be modeled as a MultiValueList object.
 * <p>
 * Some value objects may require the addition or removal of individual values
 * to multi-valued attributes. For example a Trouble Ticket may contains a large
 * set of  Alarms. In oder to minimize the amount of data required to add or remove
 * a single attribute value from a multi-valued attribute associated with a Managed
 * Entity it is recommended to use the following MultiValuedList template:
 * <pre>
 *
 * public interface &lt;AttributeType&gt;List extends MultiValueList
 * {
 *        public void  add(AttributeType[] attributes)
 *        throws java.lang.IllegalStateException,
 *        java.lang.IllegalArgumentException;
 *
 *        public void remove(AttributeType[] attributes)
 *        throws java.lang.IllegalStateException,
 *        java.lang.IllegalArgumentException;
 *
 *        public void set(AttributeType[] attributes)
 *        throws java.lang.IllegalStateException,
 *        java.lang.IllegalArgumentException;

 *        public &lt;AttributeType&gt;[] get()
 *        throws java.lang.IllegalStateException;
 *        //return an exception if modifier is NONE
 *
 *        public &lt;AttributeType&gt;[] make(int number )
 *        throws java.lang.IllegalArgumentException;
 *       //The list is a factory for its elements
 *
 * }
 *
 * </pre>
 *
 * The MultiValueList object itself supports the addition, removal and replacement
 * of individual values.
 * <p>
 * The list can have at most one operation (add, remove,replace) pending.
 * <p>
 * The <CODE>reset()</CODE> operation undo any <CODE>add()</CODE>, <CODE>remove()</CODE>
 * or <CODE>set()</CODE> that was done previously and set the modifier value to NONE.
 * <p>
 * The semantics of the operations are as follows:
 * <UL>
 * <LI><CODE>add()</CODE> is used to append values at the end of an existing attribute array.
 * <LI><CODE>remove()</CODE> is used to remove values from an existing list.  I.e., each element of
 *   the existing list matching (where <CODE>AttributeType.equals(AttributeType))</CODE> an
 *   element of the provided list is removed.
 * <LI><CODE>set()</CODE> is used to replace all the elements of the existing attribute array with
 *   the provided elements.  I.e., replace all attributes with the provided ones.
 * </UL>
 * A MultiValuedList which contains no elements must be explicitly set with an
 * empty array, and calling <CODE>get()</CODE> must return an empty array.
 * <p>
 * Using null in the <CODE>set()</CODE> operation is prohibited and the IllegalArgumentException should be
 * raised.
 *
 *
 */
public interface MultiValueList extends java.io.Serializable, java.lang.Cloneable
{

    /**
     * Returns a deep copy of this object.
     */
    public Object clone();

      /**
     * No modifer is attached to this object.
     */
  public static final int NONE = -1 ;

    /**
     * A value of SET indicates that this object will be used to replace all the elements
     * of the back-end existing attribute array with the provided elements.  I.e., replace all
     * attributes with the provided ones.
     */
    public static final int SET = 0;

    /**
     * A value of ADD indicates that this object is used to append values at the end of
     * an existing attribute array.
     */
    public static final int ADD = 1;

    /**
     * A value of REMOVE indicates that this object is used to remove values from an
     * existing list.  I.e., each element of the existing list matching an
     * element of the provided list is removed from the back-end.
     */
    public static final int REMOVE = 2;

    /**
     * Get the value of the current modifier.
     */
    int getModifier();

    /**
     * Reset the whole object.  I.e., set the modifer to NONE and
     * empty the contained array.
     */
    void reset();

}
