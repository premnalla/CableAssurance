package ossj.ttri;

import javax.oss.ManagedEntityValue;
import java.util.Vector;

/**
 * AttributeAccess Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public abstract class AttributeAccessImpl implements ManagedEntityValue,
        java.io.Serializable,
        Cloneable {


    //data
    private boolean fullyPopulated = false;
    protected Vector populatedAttributeNames;

    //ctor
    public AttributeAccessImpl() {
        populatedAttributeNames = new Vector();
    }


    //-------------------------------------------------------------------------
    // Methods from ManagedEntityValue i/f
    //-------------------------------------------------------------------------

    /**
     * Deep copy of this value
     *
     * @return deep copy of this Event
     */
    public Object clone()
            //throws CloneNotSupportedException
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }




    //Return the list of the attribute names for the attributes
    //included in this Value object (modified attributes)

    /** Gets all attribute names, whose attribute values contain something
     * meaningful.
     * <p>Although an attribute is populated, it can be <CODE>null</CODE>!
     * @return all names of attributes, which contain some data.
     * When no attributes are populated an <B>empty array</B> is
     * returned.
     * It is required to return a subset of the array returned
     * by <CODE>getAllAttributeNames()</CODE>.
     */
    public String[] getPopulatedAttributeNames() {

        int sz = populatedAttributeNames.size();
        String[] retArray = new String[sz];

        for (int x = 0; x < sz; x++) {
            retArray[x] = (String) populatedAttributeNames.elementAt(x);
        }
        return retArray;
    }

    /** Checks if a specific attribute is populated.
     * If the value object is fully populated, i.e.
     * <CODE>isFullyPopulated()</CODE> returns true, this method returns true;
     * @param name the name of the attribute which is to be checked for population
     * @throws java.lang.IllegalArgumentException this exception is thrown, when there is no attribute with this name
     * @return true, if this attribue contains some data, false otherwise
     * @see #isFullyPopulated()
     */
    public boolean isPopulated(String name)
            throws java.lang.IllegalArgumentException {
        //VP
        if (name == null)
            throw new java.lang.IllegalArgumentException("null is not a valid attribute name.");
        return populatedAttributeNames.contains(name);
    }

    /** Returns true, if all attributes in this value object are populated.
     *
     * @return true, if all attributes are populated
     * @see #isPopulated(String attribute)
     */
    public boolean isFullyPopulated() {
        return fullyPopulated;
    }

    /** Defines this value object as fully populated.
     */
    public void setFullyPopulated() {
        fullyPopulated = true;
    }

    /** Reset all the attributes to unpopulated.
     *
     */
    public void unpopulateAllAttributes() {
        populatedAttributeNames.removeAllElements();
        fullyPopulated = false;
    }

    /** Unpopulate a Single Attribute.
     * @see #unpopulateAllAttributes()
     * @param attr_name The attribute which shall be unpopulated.
     * @throws java.lang.IllegalArgumentException thrown, if this is not a valid attribute name
     */
    public void unpopulateAttribute(String attr_name)
            throws java.lang.IllegalArgumentException {
        //VP
        if (attr_name == null)
            throw new java.lang.IllegalArgumentException("null is not a valid attribute name.");
        populatedAttributeNames.remove(attr_name);
        fullyPopulated = false;
    }


    //Reset all the attributes to null and remove all the attributes
    //from the populated attribute names. Everything is null but
    //nothing is meant to suggest that a value is indeed a real null.
    //The attribute values are just not populated.
    public void reset() {

        populatedAttributeNames.removeAllElements();
    }


    public void setAttributePopulated(String attrName) {
        if (!populatedAttributeNames.contains(attrName))
            populatedAttributeNames.add(attrName);
    }

}
