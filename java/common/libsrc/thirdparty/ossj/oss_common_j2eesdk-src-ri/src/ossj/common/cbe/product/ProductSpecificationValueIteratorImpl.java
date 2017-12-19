/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductSpecificationValue;
import javax.oss.cbe.product.ProductSpecificationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductSpecificationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductSpecificationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductSpecificationValueIteratorImpl
extends ossj.common.cbe.EntitySpecificationValueIteratorImpl
implements ProductSpecificationValueIterator
{ 

    /**
     * Constructs a new ProductSpecificationValueIteratorImpl with empty attributes.
     * 
     */

    public ProductSpecificationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductSpecificationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductSpecificationValueIteratorImpl(ProductSpecificationValue[] values){
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
    public ProductSpecificationValue[] getNextProductSpecifications(int howMany)
    throws java.rmi.RemoteException {
        return (ProductSpecificationValue[]) getNext(howMany);
    }




}
