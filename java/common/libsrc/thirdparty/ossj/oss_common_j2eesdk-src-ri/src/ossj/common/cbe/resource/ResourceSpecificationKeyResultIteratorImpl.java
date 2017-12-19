/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceSpecificationKeyResult;
import javax.oss.cbe.resource.ResourceSpecificationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceSpecificationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceSpecificationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceSpecificationKeyResultIteratorImpl
extends ossj.common.cbe.EntitySpecificationKeyResultIteratorImpl
implements ResourceSpecificationKeyResultIterator
{ 

    /**
     * Constructs a new ResourceSpecificationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ResourceSpecificationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceSpecificationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceSpecificationKeyResultIteratorImpl(ResourceSpecificationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ResourceSpecificationKeyResult[] getNextResourceSpecificationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceSpecificationKeyResult[]) getNext(howMany);
    }




}
