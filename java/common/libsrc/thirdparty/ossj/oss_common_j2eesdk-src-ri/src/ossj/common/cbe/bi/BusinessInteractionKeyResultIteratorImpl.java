/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionKeyResult;
import javax.oss.cbe.bi.BusinessInteractionKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements BusinessInteractionKeyResultIterator
{ 

    /**
     * Constructs a new BusinessInteractionKeyResultIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionKeyResultIteratorImpl(BusinessInteractionKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public BusinessInteractionKeyResult[] getNextBusinessInteractionKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionKeyResult[]) getNext(howMany);
    }




}
