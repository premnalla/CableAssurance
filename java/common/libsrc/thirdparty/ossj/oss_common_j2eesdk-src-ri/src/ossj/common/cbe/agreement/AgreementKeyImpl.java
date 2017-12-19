/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyImpl
implements AgreementKey
{ 

    /**
     * Constructs a new AgreementKeyImpl with empty attributes.
     * 
     */

    public AgreementKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AgreementKey val = null;
            val = (AgreementKey)super.clone();

            return val;
    }

    /**
     * Checks whether two AgreementKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an AgreementKey object that has the arguments.
     * 
     * @param value the Object to compare with this AgreementKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AgreementKey)) {
            AgreementKey argVal = (AgreementKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
