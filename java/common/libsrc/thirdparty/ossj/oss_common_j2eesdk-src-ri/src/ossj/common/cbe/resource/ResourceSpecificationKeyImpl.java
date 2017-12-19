/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceSpecificationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceSpecificationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceSpecificationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceSpecificationKeyImpl
extends ossj.common.cbe.EntitySpecificationKeyImpl
implements ResourceSpecificationKey
{ 

    /**
     * Constructs a new ResourceSpecificationKeyImpl with empty attributes.
     * 
     */

    public ResourceSpecificationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceSpecificationKey val = null;
            val = (ResourceSpecificationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ResourceSpecificationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceSpecificationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceSpecificationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceSpecificationKey)) {
            ResourceSpecificationKey argVal = (ResourceSpecificationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
