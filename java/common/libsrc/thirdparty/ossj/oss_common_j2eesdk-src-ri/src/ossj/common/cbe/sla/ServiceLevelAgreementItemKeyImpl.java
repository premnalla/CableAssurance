/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementItemKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementItemKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementItemKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementItemKeyImpl
extends ossj.common.cbe.agreement.AgreementItemKeyImpl
implements ServiceLevelAgreementItemKey
{ 

    /**
     * Constructs a new ServiceLevelAgreementItemKeyImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementItemKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelAgreementItemKey val = null;
            val = (ServiceLevelAgreementItemKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceLevelAgreementItemKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelAgreementItemKey object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelAgreementItemKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelAgreementItemKey)) {
            ServiceLevelAgreementItemKey argVal = (ServiceLevelAgreementItemKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
