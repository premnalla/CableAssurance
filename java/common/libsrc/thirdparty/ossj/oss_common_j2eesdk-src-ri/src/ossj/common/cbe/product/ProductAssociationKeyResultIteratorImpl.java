/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductAssociationKeyResult;
import javax.oss.cbe.product.ProductAssociationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductAssociationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductAssociationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductAssociationKeyResultIteratorImpl
extends ossj.common.cbe.AssociationKeyResultIteratorImpl
implements ProductAssociationKeyResultIterator
{ 

    /**
     * Constructs a new ProductAssociationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ProductAssociationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductAssociationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductAssociationKeyResultIteratorImpl(ProductAssociationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ProductAssociationKeyResult[] getNextProductAssociationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ProductAssociationKeyResult[]) getNext(howMany);
    }




}
