/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.ResourceInteractionRoleKeyResult;
import javax.oss.cbe.bi.ResourceInteractionRoleKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.ResourceInteractionRoleKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.ResourceInteractionRoleKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceInteractionRoleKeyResultIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultIteratorImpl
implements ResourceInteractionRoleKeyResultIterator
{ 

    /**
     * Constructs a new ResourceInteractionRoleKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ResourceInteractionRoleKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceInteractionRoleKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceInteractionRoleKeyResultIteratorImpl(ResourceInteractionRoleKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ResourceInteractionRoleKeyResult[] getNextResourceInteractionRoleKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceInteractionRoleKeyResult[]) getNext(howMany);
    }




}
