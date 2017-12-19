/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductAssociationValue;
import javax.oss.cbe.product.ProductAssociationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductAssociationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductAssociationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductAssociationValueIteratorImpl
extends ossj.common.cbe.AssociationValueIteratorImpl
implements ProductAssociationValueIterator
{ 

    /**
     * Constructs a new ProductAssociationValueIteratorImpl with empty attributes.
     * 
     */

    public ProductAssociationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ProductAssociationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ProductAssociationValueIteratorImpl(ProductAssociationValue[] values){
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
    public ProductAssociationValue[] getNextProductAssociations(int howMany)
    throws java.rmi.RemoteException {
        return (ProductAssociationValue[]) getNext(howMany);
    }




}
