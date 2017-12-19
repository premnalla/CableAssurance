/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionItemValue;
import javax.oss.cbe.bi.BusinessInteractionItemValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionItemValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionItemValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionItemValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements BusinessInteractionItemValueIterator
{ 

    /**
     * Constructs a new BusinessInteractionItemValueIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionItemValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionItemValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionItemValueIteratorImpl(BusinessInteractionItemValue[] values){
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
    public BusinessInteractionItemValue[] getNextBusinessInteractionItems(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionItemValue[]) getNext(howMany);
    }




}
