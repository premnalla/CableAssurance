/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecificationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecificationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecificationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecificationKeyImpl
extends ossj.common.cbe.EntitySpecificationKeyImpl
implements ServiceLevelSpecificationKey
{ 

    /**
     * Constructs a new ServiceLevelSpecificationKeyImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecificationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelSpecificationKey val = null;
            val = (ServiceLevelSpecificationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceLevelSpecificationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelSpecificationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelSpecificationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelSpecificationKey)) {
            ServiceLevelSpecificationKey argVal = (ServiceLevelSpecificationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
