/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductCatalogValue;
import javax.oss.cbe.product.productoffering.ProductCatalogValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductCatalogValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductCatalogValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductCatalogValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ProductCatalogValueIterator
{ 

    /**
     * Constructs a new ProductCatalogValueIteratorImpl with empty attributes.
     * 
     */

    public ProductCatalogValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductCatalogValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductCatalogValueIteratorImpl(ProductCatalogValue[] values){
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
    public ProductCatalogValue[] getNextProductCatalogs(int howMany)
    throws java.rmi.RemoteException {
        return (ProductCatalogValue[]) getNext(howMany);
    }




}
