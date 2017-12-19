/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import javax.oss.MultiValueList;


/**
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface StringMultiValueList extends javax.oss.MultiValueList {
    /**
     * Set the modifer.
     * Valid values are
     * MultiValueList.NONE
     * MultiValueList.SET
     * MultiValueList.ADD
     * MultiValueList.REMOVE
     * else the modifier is set to MultiValueList.NONE
     *
     * @param val the modifer role
     */
    public void setModifier(int val);

    /**
     * adds the attrs to the existing list
     *
     * @param the array of String to add
     * @exception IllegalArgumentException in case the given attributes is null
     * @exception IllegalStateException
     */
    public void add(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException;

    /**
     * removes the attributes from the existing list
     * (uses equals method of attribute to test for equality)
     * The attribute not present in the system list are ignored.
     * @param the array of String to remove
     * @exception IllegalArgumentException in case the given attributes is null
     * @exception IllegalStateException
    */
    public void remove(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException;

    /**
     * resets the whole existing array with the provided one.
     * @param the new array of String to set
     * @exception IllegalArgumentException in case the given attributes is null
     * @exception IllegalStateException
    */
    public void set(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException;

    /**
     * returns the array of values.
     * This is just an accessor for the MultiValue array attribute, it
     * does not do a "get" from the back end.  If none of the add, set,
     * or remove methods have been called then there is nothing to get,
     * so throw an IllegalStateException.
     * @exception IllegalStateException the modifier is position to MultiValueList.NONE
    */
    public String[] get() throws java.lang.IllegalStateException;

    /**
     * The list is a factory for its elements
     * @exception IllegalArgumentException in case the given number is greate than 1024
    */
    public String[] make(int number) throws java.lang.IllegalArgumentException;

    /**
     * Checks whether two StringMultiValueList are equal.
     * The result is true if and only if the argument is not null
     * and is an StringMultiValueList object that has the arguments.
     *
     * @param value the Object to compare with this StringMultiValueList
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object arg);
}
