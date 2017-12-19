package ossj.ttri;

import ossj.fmri.AlarmValueImpl;

import javax.oss.MultiValueList;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.trouble.AlarmValueList;

/**
 * AlarmValueList Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class AlarmValueListImpl implements AlarmValueList {


    private AlarmValue[] alarmValuesArray = null;
    private int modifier = MultiValueList.NONE;

    private static int MAX_ALARMS = 4096;

    public Object clone() {

        AlarmValueListImpl newVal = new AlarmValueListImpl();

        AlarmValue[] newalarmValuesArray = null;
        if (alarmValuesArray != null) {
            newalarmValuesArray = new AlarmValue[alarmValuesArray.length];
            for (int i = 0; i < alarmValuesArray.length; i++) {
                newalarmValuesArray[i] = (AlarmValue) ((AlarmValueImpl) alarmValuesArray[i]).clone();
            }
        }
        newVal.set(newalarmValuesArray);
        newVal.setModifier(getModifier());
        return newVal;
    }

    public void setModifier(int val) {
        modifier = val;
    }

    public int getModifier() {
        return modifier;
    }

    public void reset() {
        modifier = MultiValueList.NONE;
        alarmValuesArray = null;
    }

    //adds the attrs to the end of the existing list
    public void add(AlarmValue[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.ADD;
        alarmValuesArray = attributes;
    }

    //removes the attributes from the existing list
    //(uses equals method of attribute to test for equality)
    public void remove(AlarmValue[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.REMOVE;
        alarmValuesArray = attributes;
    }

    //resets the whole existing array with the provided one.
    public void set(AlarmValue[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.SET;
        alarmValuesArray = attributes;
    }


    //returns the array of values.
    //This is just an accessor for the MultiValue array attribute, it
    //does not do a "get" from the back end.  If none of the add, set,
    //or remove methods have been called then there is nothing to get,
    //so throw an IllegalStateException
    public AlarmValue[] get()
            throws java.lang.IllegalStateException {
        if (modifier == MultiValueList.NONE) {
            throw new IllegalStateException
                    ("AlarmValueListImpl.get: empty array (add, remove, set never called)");
        }

        return alarmValuesArray;
    }

    //The list is a factory for its elements
    public AlarmValue[] make(int number)
            throws java.lang.IllegalArgumentException {
        if ((number < 0) || (number > MAX_ALARMS)) {
            throw new java.lang.IllegalArgumentException
                    ("AlarmValueListImpl: Invalid number of AlarmValues: " + number);
        }

        AlarmValueImpl[] alarmArray = new AlarmValueImpl[number];
        for (int i = 0; i < number; i++) {
            alarmArray[i] = new AlarmValueImpl();
        }
        return alarmArray;
    }


    private void validateAttributes(AlarmValue[] attributes)
            throws IllegalArgumentException {
        if (attributes == null)
            throw new java.lang.IllegalArgumentException
                    ("AlarmValueListImpl: Invalid AlarmValue[] value - null");
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();

        if (alarmValuesArray == null)
            sb.append("AlarmValueListImpl: null");
        else {
            for (int x = 0; x < alarmValuesArray.length; x++) {
                if (alarmValuesArray[x] != null)
                    sb.append(alarmValuesArray[x].toString());
            }
        }
        return sb.toString();
    }


    public boolean equals(Object arg) {
        if ((arg != null) && (arg instanceof AlarmValueList)) {
            AlarmValueList MVList = (AlarmValueList) arg;

            //Transform both Arrays in Collections and check if they are equals
            //We use collections because we don't care about the order of the items

            AlarmValue[] ArrayArg = MVList.get();

            return utils.testEqualityOfArrays(alarmValuesArray, ArrayArg);

        }
        return false;
    }


}

