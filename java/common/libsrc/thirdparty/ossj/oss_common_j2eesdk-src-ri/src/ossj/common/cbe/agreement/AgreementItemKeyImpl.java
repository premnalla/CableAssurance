/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementItemKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementItemKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementItemKeyImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyImpl
implements AgreementItemKey
{ 

    /**
     * Constructs a new AgreementItemKeyImpl with empty attributes.
     * 
     */

    public AgreementItemKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AgreementItemKey val = null;
            val = (AgreementItemKey)super.clone();

            return val;
    }

    /**
     * Checks whether two AgreementItemKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an AgreementItemKey object that has the arguments.
     * 
     * @param value the Object to compare with this AgreementItemKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AgreementItemKey)) {
            AgreementItemKey argVal = (AgreementItemKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
