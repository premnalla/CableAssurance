/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionItemKeyResult;
import javax.oss.cbe.bi.BusinessInteractionItemKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionItemKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionItemKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionItemKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements BusinessInteractionItemKeyResultIterator
{ 

    /**
     * Constructs a new BusinessInteractionItemKeyResultIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionItemKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionItemKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionItemKeyResultIteratorImpl(BusinessInteractionItemKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public BusinessInteractionItemKeyResult[] getNextBusinessInteractionItemKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionItemKeyResult[]) getNext(howMany);
    }




}
