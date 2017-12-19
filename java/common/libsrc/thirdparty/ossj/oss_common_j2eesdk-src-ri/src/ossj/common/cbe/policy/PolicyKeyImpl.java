/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.policy;

import javax.oss.cbe.policy.PolicyKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.policy.PolicyKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.policy.PolicyKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PolicyKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements PolicyKey
{ 

    /**
     * Constructs a new PolicyKeyImpl with empty attributes.
     * 
     */

    public PolicyKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PolicyKey val = null;
            val = (PolicyKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PolicyKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PolicyKey object that has the arguments.
     * 
     * @param value the Object to compare with this PolicyKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PolicyKey)) {
            PolicyKey argVal = (PolicyKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
