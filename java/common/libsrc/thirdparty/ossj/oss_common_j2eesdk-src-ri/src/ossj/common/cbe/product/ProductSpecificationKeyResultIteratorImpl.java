/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductSpecificationKeyResult;
import javax.oss.cbe.product.ProductSpecificationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductSpecificationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductSpecificationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductSpecificationKeyResultIteratorImpl
extends ossj.common.cbe.EntitySpecificationKeyResultIteratorImpl
implements ProductSpecificationKeyResultIterator
{ 

    /**
     * Constructs a new ProductSpecificationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ProductSpecificationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductSpecificationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductSpecificationKeyResultIteratorImpl(ProductSpecificationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ProductSpecificationKeyResult[] getNextProductSpecificationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ProductSpecificationKeyResult[]) getNext(howMany);
    }




}
