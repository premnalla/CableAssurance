/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productofferingprice;

import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResult;
import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingPriceKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements ProductOfferingPriceKeyResultIterator
{ 

    /**
     * Constructs a new ProductOfferingPriceKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ProductOfferingPriceKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductOfferingPriceKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductOfferingPriceKeyResultIteratorImpl(ProductOfferingPriceKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ProductOfferingPriceKeyResult[] getNextProductOfferingPriceKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ProductOfferingPriceKeyResult[]) getNext(howMany);
    }




}
