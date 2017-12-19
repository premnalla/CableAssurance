/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionRoleKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionRoleKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionRoleKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionRoleKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements BusinessInteractionRoleKey
{ 

    /**
     * Constructs a new BusinessInteractionRoleKeyImpl with empty attributes.
     * 
     */

    public BusinessInteractionRoleKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BusinessInteractionRoleKey val = null;
            val = (BusinessInteractionRoleKey)super.clone();

            return val;
    }

    /**
     * Checks whether two BusinessInteractionRoleKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an BusinessInteractionRoleKey object that has the arguments.
     * 
     * @param value the Object to compare with this BusinessInteractionRoleKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BusinessInteractionRoleKey)) {
            BusinessInteractionRoleKey argVal = (BusinessInteractionRoleKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
