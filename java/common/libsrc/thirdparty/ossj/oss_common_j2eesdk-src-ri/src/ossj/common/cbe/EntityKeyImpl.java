/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntityKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntityKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntityKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntityKeyImpl
extends ossj.common.cbe.CBEManagedEntityKeyImpl
implements EntityKey
{ 

    /**
     * Constructs a new EntityKeyImpl with empty attributes.
     * 
     */

    public EntityKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        EntityKey val = null;
            val = (EntityKey)super.clone();

            return val;
    }

    /**
     * Checks whether two EntityKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an EntityKey object that has the arguments.
     * 
     * @param value the Object to compare with this EntityKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof EntityKey)) {
            EntityKey argVal = (EntityKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
