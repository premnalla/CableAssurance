package ossj.ttri;

import javax.oss.MultiValueList;
import javax.oss.trouble.RepairActivity;
import javax.oss.trouble.RepairActivityList;

/**
 * RepairActivityList Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class RepairActivityListImpl implements RepairActivityList {


    private RepairActivity[] repairActivityArray = null;
    private int modifier = MultiValueList.NONE;

    private static int MAX_REPAIRS = 1024;

    public Object clone() {

        RepairActivityListImpl newVal = new RepairActivityListImpl();

        RepairActivity[] newArray = null;
        if (repairActivityArray != null) {
            newArray = new RepairActivity[repairActivityArray.length];
            for (int i = 0; i < repairActivityArray.length; i++) {
                newArray[i] = (RepairActivity) ((RepairActivity) repairActivityArray[i]).clone();
            }
        }
        newVal.set(newArray);
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
        repairActivityArray = null;
    }

    //adds the attrs to the end of the existing list
    public void add(RepairActivity[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.ADD;
        repairActivityArray = attributes;
    }

    //removes the attributes from the existing list
    //(uses equals method of attribute to test for equality)
    public void remove(RepairActivity[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.REMOVE;
        repairActivityArray = attributes;
    }

    //resets the whole existing array with the provided one.
    public void set(RepairActivity[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.SET;
        repairActivityArray = attributes;
    }


    //returns the array of values.
    //This is just an accessor for the MultiValue array attribute, it
    //does not do a "get" from the back end.  If none of the add, set,
    //or remove methods have been called then there is nothing to get,
    //so throw an IllegalStateException
    public RepairActivity[] get()
            throws java.lang.IllegalStateException {
        if (modifier == MultiValueList.NONE) {
            throw new IllegalStateException
                    ("RepairActivityListImpl.get: empty array (add, remove, set never called)");
        }

        return repairActivityArray;
    }

    //The list is a factory for its elements
    public RepairActivity[] make(int number)
            throws java.lang.IllegalArgumentException {
        if ((number < 0) || (number > MAX_REPAIRS)) {
            throw new java.lang.IllegalArgumentException
                    ("RepairActivityListImpl: Invalid number of escalations: " + number);
        }

        RepairActivityImpl[] repairArray = new RepairActivityImpl[number];
        for (int i = 0; i < number; i++) {
            repairArray[i] = new RepairActivityImpl();
        }
        return repairArray;
    }


    private void validateAttributes(RepairActivity[] attributes)
            throws IllegalArgumentException {
        if (attributes == null)
            throw new java.lang.IllegalArgumentException
                    ("RepairActivityListImpl: Invalid RepairActivity[] value - null");
    }


    public void print() {
        if (repairActivityArray == null)
            Logger.log("RepairActivityListImpl: null");
        else {
            for (int x = 0; x < repairActivityArray.length; x++) {
                if (repairActivityArray[x] != null)
                    ((RepairActivityImpl) repairActivityArray[x]).print(20);
            }
        }

    }

    public boolean equals(Object arg) {
        if ((arg != null) && (arg instanceof RepairActivityList)) {
            RepairActivityList MVList = (RepairActivityList) arg;

            //Transform both Arrays in Collections and check if they are equals
            //We use collections because we don't care about the order of the items

            RepairActivity[] ArrayArg = MVList.get();

            return utils.testEqualityOfArrays(repairActivityArray, ArrayArg);

        }
        return false;
    }


}


