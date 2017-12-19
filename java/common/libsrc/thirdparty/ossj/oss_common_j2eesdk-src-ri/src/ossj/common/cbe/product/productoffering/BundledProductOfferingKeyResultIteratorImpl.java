/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResult;
import javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BundledProductOfferingKeyResultIteratorImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyResultIteratorImpl
implements BundledProductOfferingKeyResultIterator
{ 

    /**
     * Constructs a new BundledProductOfferingKeyResultIteratorImpl with empty attributes.
     * 
     */

    public BundledProductOfferingKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BundledProductOfferingKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BundledProductOfferingKeyResultIteratorImpl(BundledProductOfferingKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public BundledProductOfferingKeyResult[] getNextBundledProductOfferingKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (BundledProductOfferingKeyResult[]) getNext(howMany);
    }




}
