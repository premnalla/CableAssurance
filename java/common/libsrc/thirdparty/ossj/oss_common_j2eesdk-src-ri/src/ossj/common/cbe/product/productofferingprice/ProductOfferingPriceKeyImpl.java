/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productofferingprice;

import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingPriceKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements ProductOfferingPriceKey
{ 

    /**
     * Constructs a new ProductOfferingPriceKeyImpl with empty attributes.
     * 
     */

    public ProductOfferingPriceKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductOfferingPriceKey val = null;
            val = (ProductOfferingPriceKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductOfferingPriceKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductOfferingPriceKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductOfferingPriceKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductOfferingPriceKey)) {
            ProductOfferingPriceKey argVal = (ProductOfferingPriceKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
