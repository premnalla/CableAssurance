/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductKeyResult;
import javax.oss.cbe.product.ProductKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements ProductKeyResultIterator
{ 

    /**
     * Constructs a new ProductKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ProductKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductKeyResultIteratorImpl(ProductKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ProductKeyResult[] getNextProductKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ProductKeyResult[]) getNext(howMany);
    }




}
