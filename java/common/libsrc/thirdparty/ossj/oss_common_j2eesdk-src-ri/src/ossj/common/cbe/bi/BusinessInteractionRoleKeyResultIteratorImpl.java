/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionRoleKeyResult;
import javax.oss.cbe.bi.BusinessInteractionRoleKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionRoleKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionRoleKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionRoleKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements BusinessInteractionRoleKeyResultIterator
{ 

    /**
     * Constructs a new BusinessInteractionRoleKeyResultIteratorImpl with empty attributes.
     * 
     */

    public BusinessInteractionRoleKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new BusinessInteractionRoleKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public BusinessInteractionRoleKeyResultIteratorImpl(BusinessInteractionRoleKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public BusinessInteractionRoleKeyResult[] getNextBusinessInteractionRoleKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (BusinessInteractionRoleKeyResult[]) getNext(howMany);
    }




}
