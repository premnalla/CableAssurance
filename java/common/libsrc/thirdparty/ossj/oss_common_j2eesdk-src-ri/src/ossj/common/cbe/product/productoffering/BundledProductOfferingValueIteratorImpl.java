/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.BundledProductOfferingValue;
import javax.oss.cbe.product.productoffering.BundledProductOfferingValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.BundledProductOfferingValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.BundledProductOfferingValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BundledProductOfferingValueIteratorImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingValueIteratorImpl
implements BundledProductOfferingValueIterator
{ 

    /**
     * Constructs a new BundledProductOfferingValueIteratorImpl with empty attributes.
     * 
     */

    public BundledProductOfferingValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BundledProductOfferingValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BundledProductOfferingValueIteratorImpl(BundledProductOfferingValue[] values){
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
    public BundledProductOfferingValue[] getNextBundledProductOfferings(int howMany)
    throws java.rmi.RemoteException {
        return (BundledProductOfferingValue[]) getNext(howMany);
    }




}
