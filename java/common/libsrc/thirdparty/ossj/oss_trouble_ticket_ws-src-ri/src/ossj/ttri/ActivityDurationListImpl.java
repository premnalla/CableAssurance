package ossj.ttri;

import javax.oss.MultiValueList;
import javax.oss.trouble.ActivityDuration;
import javax.oss.trouble.ActivityDurationList;

/**
 * ActivityDurationList Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class ActivityDurationListImpl implements ActivityDurationList {


    private ActivityDuration[] activityDurationsArray = null;
    private int modifier = MultiValueList.NONE;

    private static int MAX_ACTIVITIES = 1024;

    public Object clone() {

        ActivityDurationListImpl newVal = new ActivityDurationListImpl();

        ActivityDuration[] newactivityDurationsArray = null;
        if (activityDurationsArray != null) {
            newactivityDurationsArray = new ActivityDuration[activityDurationsArray.length];
            for (int i = 0; i < activityDurationsArray.length; i++) {
                newactivityDurationsArray[i] = (ActivityDuration) ((ActivityDuration) activityDurationsArray[i]).clone();
            }
        }
        newVal.set(newactivityDurationsArray);
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
        activityDurationsArray = null;
    }

    //adds the attrs to the end of the existing list
    public void add(ActivityDuration[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.ADD;
        activityDurationsArray = attributes;
    }

    //removes the attributes from the existing list
    //(uses equals method of attribute to test for equality)
    public void remove(ActivityDuration[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.REMOVE;
        activityDurationsArray = attributes;
    }

    //resets the whole existing array with the provided one.
    public void set(ActivityDuration[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.SET;
        activityDurationsArray = attributes;
    }


    //returns the array of values.
    //This is just an accessor for the MultiValue array attribute, it
    //does not do a "get" from the back end.  If none of the add, set,
    //or remove methods have been called then there is nothing to get,
    //so throw an IllegalStateException
    public ActivityDuration[] get()
            throws java.lang.IllegalStateException {
        if (modifier == MultiValueList.NONE) {
            throw new IllegalStateException
                    ("ActivityDurationListImpl.get: empty array (add, remove, set never called)");
        }

        return activityDurationsArray;
    }

    //The list is a factory for its elements
    public ActivityDuration[] make(int number)
            throws java.lang.IllegalArgumentException {
        if ((number < 0) || (number > MAX_ACTIVITIES)) {
            throw new java.lang.IllegalArgumentException
                    ("ActivityDurationListImpl: Invalid number of ActivityDurations: " + number);
        }

        ActivityDuration[] actDurationArray = new ActivityDurationImpl[number];
        for (int i = 0; i < number; i++) {
            actDurationArray[i] = new ActivityDurationImpl();
        }
        return actDurationArray;

    }


    private void validateAttributes(ActivityDuration[] attributes)
            throws IllegalArgumentException {
        if (attributes == null)
            throw new java.lang.IllegalArgumentException
                    ("ActivityDurationListImpl: Invalid ActivityDuration[] value - null");
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        if (activityDurationsArray == null)
            sb.append("ActivityDurationListImpl: null");
        else {
            for (int x = 0; x < activityDurationsArray.length; x++) {
                if (activityDurationsArray[x] != null)
                    sb.append(activityDurationsArray[x].toString());
            }
        }

        return sb.toString();

    }


    public boolean equals(Object arg) {
        if ((arg != null) && (arg instanceof ActivityDurationList)) {
            ActivityDurationList MVList = (ActivityDurationList) arg;

            //Transform both Arrays in Collections and check if they are equals
            //We use collections because we don't care about the order of the items

            ActivityDuration[] ArrayArg = MVList.get();

            return utils.testEqualityOfArrays(activityDurationsArray, ArrayArg);

        }
        return false;
    }


}

