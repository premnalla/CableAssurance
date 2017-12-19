package ossj.ttri;

import javax.oss.MultiValueList;
import javax.oss.trouble.Escalation;
import javax.oss.trouble.EscalationList;

/**
 * EscalationList Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class EscalationListImpl implements EscalationList {


    private Escalation[] escalationsArray = null;
    private int modifier = MultiValueList.NONE;

    private static int MAX_ESCALATIONS = 1024;

    public Object clone() {

        EscalationListImpl newVal = new EscalationListImpl();

        Escalation[] newArray = null;
        if (escalationsArray != null) {
            newArray = new Escalation[escalationsArray.length];
            for (int i = 0; i < escalationsArray.length; i++) {
                newArray[i] = (Escalation) ((Escalation) escalationsArray[i]).clone();
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
        escalationsArray = null;
    }

    //adds the attrs to the end of the existing list
    public void add(Escalation[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.ADD;
        escalationsArray = attributes;
    }

    //removes the attributes from the existing list
    //(uses equals method of attribute to test for equality)
    public void remove(Escalation[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.REMOVE;
        escalationsArray = attributes;
    }

    //resets the whole existing array with the provided one.
    public void set(Escalation[] attributes)
            throws java.lang.IllegalStateException,
            java.lang.IllegalArgumentException {
        validateAttributes(attributes);

        modifier = MultiValueList.SET;
        escalationsArray = attributes;
    }


    //returns the array of values.
    //This is just an accessor for the MultiValue array attribute, it
    //does not do a "get" from the back end.  If none of the add, set,
    //or remove methods have been called then there is nothing to get,
    //so throw an IllegalStateException
    public Escalation[] get()
            throws java.lang.IllegalStateException {
        if (modifier == MultiValueList.NONE) {
            throw new IllegalStateException
                    ("EscalationListImpl.get: empty array (add, remove, set never called)");
        }

        return escalationsArray;
    }

    //The list is a factory for its elements
    public Escalation[] make(int number)
            throws java.lang.IllegalArgumentException {
        if ((number < 0) || (number > MAX_ESCALATIONS)) {
            throw new java.lang.IllegalArgumentException
                    ("EscalationListImpl: Invalid number of escalations: " + number);
        }

        //the list must be populated with empty instances of EscalationImpl
        Escalation[] escArray = new EscalationImpl[number];
        for (int i = 0; i < number; i++) {
            escArray[i] = new EscalationImpl();
        }
        return escArray;
    }


    private void validateAttributes(Escalation[] attributes)
            throws IllegalArgumentException {
        if (attributes == null)
            throw new java.lang.IllegalArgumentException
                    ("EscalationListImpl: Invalid Escalation[] value - null");
    }


    public boolean equals(Object arg) {
        Logger.log("-----Testing equality of EscalationList-----");

        if ((arg != null) && (arg instanceof EscalationList)) {
            EscalationList escaListArg = (EscalationList) arg;

            //Transform both Arrays in Collections and check if they are equals
            //We use collections because we don't care about the order of the items

            Escalation[] escalationsArrayArg = escaListArg.get();
            Logger.log("-----About to Test Equality of Arrays-----");
            Logger.log("------escalationsArray: " + escalationsArray.length);
            Logger.log("-----escalationsArrayArg" + escalationsArrayArg.length);

            return utils.testEqualityOfArrays(escalationsArray, escalationsArrayArg);

        }
        return false;
    }


}

