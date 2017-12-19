/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.SimpleProductOfferingValue;
import javax.oss.cbe.product.productoffering.SimpleProductOfferingValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.SimpleProductOfferingValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.SimpleProductOfferingValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class SimpleProductOfferingValueIteratorImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingValueIteratorImpl
implements SimpleProductOfferingValueIterator
{ 

    /**
     * Constructs a new SimpleProductOfferingValueIteratorImpl with empty attributes.
     * 
     */

    public SimpleProductOfferingValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new SimpleProductOfferingValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public SimpleProductOfferingValueIteratorImpl(SimpleProductOfferingValue[] values){
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
    public SimpleProductOfferingValue[] getNextSimpleProductOfferings(int howMany)
    throws java.rmi.RemoteException {
        return (SimpleProductOfferingValue[]) getNext(howMany);
    }




}
