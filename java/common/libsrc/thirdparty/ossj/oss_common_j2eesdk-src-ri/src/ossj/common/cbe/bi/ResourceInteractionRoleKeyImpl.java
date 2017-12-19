/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.ResourceInteractionRoleKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.ResourceInteractionRoleKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.ResourceInteractionRoleKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceInteractionRoleKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyImpl
implements ResourceInteractionRoleKey
{ 

    /**
     * Constructs a new ResourceInteractionRoleKeyImpl with empty attributes.
     * 
     */

    public ResourceInteractionRoleKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceInteractionRoleKey val = null;
            val = (ResourceInteractionRoleKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ResourceInteractionRoleKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceInteractionRoleKey object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceInteractionRoleKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceInteractionRoleKey)) {
            ResourceInteractionRoleKey argVal = (ResourceInteractionRoleKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
