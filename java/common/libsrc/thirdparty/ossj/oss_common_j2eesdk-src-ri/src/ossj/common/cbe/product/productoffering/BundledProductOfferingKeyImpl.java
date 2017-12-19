/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.BundledProductOfferingKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.BundledProductOfferingKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.BundledProductOfferingKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BundledProductOfferingKeyImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyImpl
implements BundledProductOfferingKey
{ 

    /**
     * Constructs a new BundledProductOfferingKeyImpl with empty attributes.
     * 
     */

    public BundledProductOfferingKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BundledProductOfferingKey val = null;
            val = (BundledProductOfferingKey)super.clone();

            return val;
    }

    /**
     * Checks whether two BundledProductOfferingKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an BundledProductOfferingKey object that has the arguments.
     * 
     * @param value the Object to compare with this BundledProductOfferingKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BundledProductOfferingKey)) {
            BundledProductOfferingKey argVal = (BundledProductOfferingKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
