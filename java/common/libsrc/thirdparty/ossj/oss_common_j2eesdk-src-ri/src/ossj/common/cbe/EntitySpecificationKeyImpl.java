/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntitySpecificationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.EntitySpecificationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntitySpecificationKeyImpl
extends ossj.common.cbe.CBEManagedEntityKeyImpl
implements EntitySpecificationKey
{ 

    /**
     * Constructs a new EntitySpecificationKeyImpl with empty attributes.
     * 
     */

    public EntitySpecificationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        EntitySpecificationKey val = null;
            val = (EntitySpecificationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two EntitySpecificationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an EntitySpecificationKey object that has the arguments.
     * 
     * @param value the Object to compare with this EntitySpecificationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof EntitySpecificationKey)) {
            EntitySpecificationKey argVal = (EntitySpecificationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
