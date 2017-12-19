/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResult;
import javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class SimpleProductOfferingKeyResultIteratorImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyResultIteratorImpl
implements SimpleProductOfferingKeyResultIterator
{ 

    /**
     * Constructs a new SimpleProductOfferingKeyResultIteratorImpl with empty attributes.
     * 
     */

    public SimpleProductOfferingKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new SimpleProductOfferingKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public SimpleProductOfferingKeyResultIteratorImpl(SimpleProductOfferingKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public SimpleProductOfferingKeyResult[] getNextSimpleProductOfferingKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (SimpleProductOfferingKeyResult[]) getNext(howMany);
    }




}
