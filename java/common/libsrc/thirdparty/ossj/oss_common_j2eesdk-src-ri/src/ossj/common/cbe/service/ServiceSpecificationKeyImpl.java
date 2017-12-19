/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceSpecificationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceSpecificationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceSpecificationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceSpecificationKeyImpl
extends ossj.common.cbe.EntitySpecificationKeyImpl
implements ServiceSpecificationKey
{ 

    /**
     * Constructs a new ServiceSpecificationKeyImpl with empty attributes.
     * 
     */

    public ServiceSpecificationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceSpecificationKey val = null;
            val = (ServiceSpecificationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceSpecificationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceSpecificationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceSpecificationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceSpecificationKey)) {
            ServiceSpecificationKey argVal = (ServiceSpecificationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
