/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.ResourceInteractionRoleValue;
import javax.oss.cbe.bi.ResourceInteractionRoleValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.ResourceInteractionRoleValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.ResourceInteractionRoleValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceInteractionRoleValueIteratorImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueIteratorImpl
implements ResourceInteractionRoleValueIterator
{ 

    /**
     * Constructs a new ResourceInteractionRoleValueIteratorImpl with empty attributes.
     * 
     */

    public ResourceInteractionRoleValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceInteractionRoleValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceInteractionRoleValueIteratorImpl(ResourceInteractionRoleValue[] values){
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
    public ResourceInteractionRoleValue[] getNextResourceInteractionRoles(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceInteractionRoleValue[]) getNext(howMany);
    }




}
