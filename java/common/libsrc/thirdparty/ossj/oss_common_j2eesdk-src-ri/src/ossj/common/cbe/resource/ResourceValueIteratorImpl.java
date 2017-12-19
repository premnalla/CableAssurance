/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceValue;
import javax.oss.cbe.resource.ResourceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ResourceValueIterator
{ 

    /**
     * Constructs a new ResourceValueIteratorImpl with empty attributes.
     * 
     */

    public ResourceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceValueIteratorImpl(ResourceValue[] values){
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
    public ResourceValue[] getNextResources(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceValue[]) getNext(howMany);
    }




}
