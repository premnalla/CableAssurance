/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionItemKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionItemKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionItemKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionItemKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements BusinessInteractionItemKey
{ 

    /**
     * Constructs a new BusinessInteractionItemKeyImpl with empty attributes.
     * 
     */

    public BusinessInteractionItemKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BusinessInteractionItemKey val = null;
            val = (BusinessInteractionItemKey)super.clone();

            return val;
    }

    /**
     * Checks whether two BusinessInteractionItemKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an BusinessInteractionItemKey object that has the arguments.
     * 
     * @param value the Object to compare with this BusinessInteractionItemKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BusinessInteractionItemKey)) {
            BusinessInteractionItemKey argVal = (BusinessInteractionItemKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
