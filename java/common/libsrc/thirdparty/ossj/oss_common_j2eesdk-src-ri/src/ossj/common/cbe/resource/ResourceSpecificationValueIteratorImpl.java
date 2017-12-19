/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceSpecificationValue;
import javax.oss.cbe.resource.ResourceSpecificationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceSpecificationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceSpecificationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceSpecificationValueIteratorImpl
extends ossj.common.cbe.EntitySpecificationValueIteratorImpl
implements ResourceSpecificationValueIterator
{ 

    /**
     * Constructs a new ResourceSpecificationValueIteratorImpl with empty attributes.
     * 
     */

    public ResourceSpecificationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceSpecificationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceSpecificationValueIteratorImpl(ResourceSpecificationValue[] values){
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
    public ResourceSpecificationValue[] getNextResourceSpecifications(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceSpecificationValue[]) getNext(howMany);
    }




}
