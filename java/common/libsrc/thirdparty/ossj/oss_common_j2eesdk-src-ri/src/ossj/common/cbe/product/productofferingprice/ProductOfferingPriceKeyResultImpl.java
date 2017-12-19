/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productofferingprice;

import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingPriceKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ProductOfferingPriceKeyResult
{ 

    /**
     * Constructs a new ProductOfferingPriceKeyResultImpl with empty attributes.
     * 
     */

    public ProductOfferingPriceKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductOfferingPriceKeyResultImpl's productOfferingPriceKey
     * 
     * @return the productOfferingPriceKey
     * 
    */

    public javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey getProductOfferingPriceKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey)getManagedEntityKey();
    }

}
