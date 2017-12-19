/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductOfferingKeyResult;
import javax.oss.cbe.product.productoffering.ProductOfferingKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductOfferingKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductOfferingKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements ProductOfferingKeyResultIterator
{ 

    /**
     * Constructs a new ProductOfferingKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ProductOfferingKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductOfferingKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductOfferingKeyResultIteratorImpl(ProductOfferingKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ProductOfferingKeyResult[] getNextProductOfferingKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ProductOfferingKeyResult[]) getNext(howMany);
    }




}
