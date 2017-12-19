/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductOfferingValue;
import javax.oss.cbe.product.productoffering.ProductOfferingValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductOfferingValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductOfferingValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ProductOfferingValueIterator
{ 

    /**
     * Constructs a new ProductOfferingValueIteratorImpl with empty attributes.
     * 
     */

    public ProductOfferingValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductOfferingValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductOfferingValueIteratorImpl(ProductOfferingValue[] values){
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
    public ProductOfferingValue[] getNextProductOfferings(int howMany)
    throws java.rmi.RemoteException {
        return (ProductOfferingValue[]) getNext(howMany);
    }




}
