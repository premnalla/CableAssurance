/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceAssociationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceAssociationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceAssociationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceAssociationKeyImpl
extends ossj.common.cbe.AssociationKeyImpl
implements ResourceAssociationKey
{ 

    /**
     * Constructs a new ResourceAssociationKeyImpl with empty attributes.
     * 
     */

    public ResourceAssociationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceAssociationKey val = null;
            val = (ResourceAssociationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ResourceAssociationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceAssociationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceAssociationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceAssociationKey)) {
            ResourceAssociationKey argVal = (ResourceAssociationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
