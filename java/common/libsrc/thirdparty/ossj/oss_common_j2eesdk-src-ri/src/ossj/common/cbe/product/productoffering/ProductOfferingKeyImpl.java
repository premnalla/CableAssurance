/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductOfferingKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductOfferingKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductOfferingKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements ProductOfferingKey
{ 

    /**
     * Constructs a new ProductOfferingKeyImpl with empty attributes.
     * 
     */

    public ProductOfferingKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductOfferingKey val = null;
            val = (ProductOfferingKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductOfferingKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductOfferingKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductOfferingKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductOfferingKey)) {
            ProductOfferingKey argVal = (ProductOfferingKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
