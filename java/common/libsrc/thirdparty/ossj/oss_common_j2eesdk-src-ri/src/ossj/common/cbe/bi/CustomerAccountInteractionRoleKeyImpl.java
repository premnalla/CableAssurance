/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.CustomerAccountInteractionRoleKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.CustomerAccountInteractionRoleKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.CustomerAccountInteractionRoleKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CustomerAccountInteractionRoleKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyImpl
implements CustomerAccountInteractionRoleKey
{ 

    /**
     * Constructs a new CustomerAccountInteractionRoleKeyImpl with empty attributes.
     * 
     */

    public CustomerAccountInteractionRoleKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        CustomerAccountInteractionRoleKey val = null;
            val = (CustomerAccountInteractionRoleKey)super.clone();

            return val;
    }

    /**
     * Checks whether two CustomerAccountInteractionRoleKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an CustomerAccountInteractionRoleKey object that has the arguments.
     * 
     * @param value the Object to compare with this CustomerAccountInteractionRoleKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof CustomerAccountInteractionRoleKey)) {
            CustomerAccountInteractionRoleKey argVal = (CustomerAccountInteractionRoleKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
