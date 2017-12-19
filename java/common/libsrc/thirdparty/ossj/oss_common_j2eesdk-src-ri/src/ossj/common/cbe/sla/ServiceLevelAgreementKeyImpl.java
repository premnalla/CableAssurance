/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementKeyImpl
extends ossj.common.cbe.agreement.AgreementKeyImpl
implements ServiceLevelAgreementKey
{ 

    /**
     * Constructs a new ServiceLevelAgreementKeyImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelAgreementKey val = null;
            val = (ServiceLevelAgreementKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceLevelAgreementKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelAgreementKey object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelAgreementKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelAgreementKey)) {
            ServiceLevelAgreementKey argVal = (ServiceLevelAgreementKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
