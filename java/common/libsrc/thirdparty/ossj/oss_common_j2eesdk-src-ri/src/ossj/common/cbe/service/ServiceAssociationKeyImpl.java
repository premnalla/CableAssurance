/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceAssociationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceAssociationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceAssociationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceAssociationKeyImpl
extends ossj.common.cbe.AssociationKeyImpl
implements ServiceAssociationKey
{ 

    /**
     * Constructs a new ServiceAssociationKeyImpl with empty attributes.
     * 
     */

    public ServiceAssociationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceAssociationKey val = null;
            val = (ServiceAssociationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceAssociationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceAssociationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceAssociationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceAssociationKey)) {
            ServiceAssociationKey argVal = (ServiceAssociationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
