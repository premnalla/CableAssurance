/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.util.Arrays;
import java.util.Collection;
import javax.oss.MultiValueList;


/**
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class StringMultiValueListImpl extends Object implements StringMultiValueList {
    private static int _MAX = 1024;
    private String[] _listArray = null;
    private int _modifier = MultiValueList.NONE;

    /**
     * Creates a new StringMultiValueListImpl object.
     * default modifer = MultiValueList.NONE, and the String[] = null
     */
    public StringMultiValueListImpl() {
    }

    /**
     * Creates a new MultiValueListImpl object.
     *
     * @param initialList DOCUMENT ME!
     * @param modifier DOCUMENT ME!
     */
    public StringMultiValueListImpl(String[] initialList, int modifier) {
        _modifier = modifier;
        _listArray = initialList;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        return new StringMultiValueListImpl(this.get(), this.getModifier());
    }

    /**
     * Set the modifer.
     * Valid values are
     * MultiValueList.NONE
     * MultiValueList.SET
     * MultiValueList.ADD
     * MultiValueList.REMOVE
     * else the modifier is set to MultiValueList.NONE
     *
     *
     * @param val the modifer
     */
    public void setModifier(int val) {
        if ((val == MultiValueList.NONE) || (val == MultiValueList.SET)
                || (val == MultiValueList.ADD) || (val == MultiValueList.REMOVE)) {
            _modifier = val;
        } else {
            _modifier = MultiValueList.NONE;
        }
    }

    /**
     * returns the modifier
     *
     * @return DOCUMENT ME!
     */
    public int getModifier() {
        return _modifier;
    }

    /**
     * Set the Modifer to NONE and the list to null
     */
    public void reset() {
        _modifier = MultiValueList.NONE;
        _listArray = null;
    }

    /**
     * adds the attrs to the existing list
     */
    public void add(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        _modifier = MultiValueList.ADD;

        _listArray = attributes;
    }

    /**
     * removes the attributes from the existing list
     *(uses equals method of attribute to test for equality)
    */
    public void remove(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        _modifier = MultiValueList.REMOVE;
        _listArray = attributes;
    }

    /**
     * resets the whole existing array with the provided one.
    */
    public void set(String[] attributes)
        throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        _modifier = MultiValueList.SET;
        _listArray = attributes;
    }

    /**
     * returns the array of values.
     * This is just an accessor for the MultiValue array attribute, it
     * does not do a "get" from the back end.  If none of the add, set,
     * or remove methods have been called then there is nothing to get,
     * so throw an IllegalStateException
    */
    public String[] get() throws java.lang.IllegalStateException {
        if (_modifier == MultiValueList.NONE) {
            throw new IllegalStateException(
                "MultiValueListImpl.get: empty array (add, remove, set never called)");
        }

        return _listArray;
    }

    /**
     * The list is a factory for its elements
    */
    public String[] make(int number) throws java.lang.IllegalArgumentException {
        if ((number < 0) || (number > _MAX)) {
            throw new java.lang.IllegalArgumentException(
                "MultiValueListImpl: Invalid number of strings max = " + _MAX + " : " + number);
        }

        return new String[number];
    }

    private void validateAttributes(String[] attributes)
        throws IllegalArgumentException {
        if (attributes == null) {
            throw new java.lang.IllegalArgumentException(
                "MultiValueListImpl: Invalid String[] value - null");
        }
    }

    /**
     * Checks whether two StringMultiValueList are equal.
     * The result is true if and only if the argument is not null
     * and is an StringMultiValueList object that has the arguments.
     *
     * @param value the Object to compare with this StringMultiValueList
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object arg) {
        if (this == arg) {
            return true;
        }

        if ((arg == null) || !(arg instanceof StringMultiValueList)) {
            return false;
        }

        StringMultiValueList listArg = (StringMultiValueList) arg;

        //Transform both Arrays in Collections and check if they are equals
        //We use collections because we don't care about the order of the items
        String[] arrayArg = listArg.get();

        if (arrayArg.length != _listArray.length) {
            return false;
        }

        Collection col1 = Arrays.asList(arrayArg);
        Collection col2 = Arrays.asList(_listArray);

        if (col1.containsAll(col2)) {
            return true;
        } else {
            return false;
        }
    }
}
