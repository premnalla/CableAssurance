/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productofferingprice;

import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValue;
import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingPriceValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ProductOfferingPriceValueIterator
{ 

    /**
     * Constructs a new ProductOfferingPriceValueIteratorImpl with empty attributes.
     * 
     */

    public ProductOfferingPriceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductOfferingPriceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductOfferingPriceValueIteratorImpl(ProductOfferingPriceValue[] values){
        super();
        super.addManagedEntityValues(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueIterator#getNext(int)
     */
    public ProductOfferingPriceValue[] getNextProductOfferingPrices(int howMany)
    throws java.rmi.RemoteException {
        return (ProductOfferingPriceValue[]) getNext(howMany);
    }




}
