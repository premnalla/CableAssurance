/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecParam;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecParam</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecParam
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecParamImpl
implements ServiceLevelSpecParam
{ 

    /**
     * Constructs a new ServiceLevelSpecParamImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecParamImpl() {
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelSpecParam val = null;
        try { 
            val = (ServiceLevelSpecParam)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ServiceLevelSpecParamImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two ServiceLevelSpecParam are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelSpecParam object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelSpecParam
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelSpecParam)) {
            ServiceLevelSpecParam argVal = (ServiceLevelSpecParam) value;

            return true;
        } else {
            return super.equals(value);
        }
    }

}
