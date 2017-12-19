/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceAssociationKeyResult;
import javax.oss.cbe.resource.ResourceAssociationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceAssociationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceAssociationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceAssociationKeyResultIteratorImpl
extends ossj.common.cbe.AssociationKeyResultIteratorImpl
implements ResourceAssociationKeyResultIterator
{ 

    /**
     * Constructs a new ResourceAssociationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ResourceAssociationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceAssociationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceAssociationKeyResultIteratorImpl(ResourceAssociationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ResourceAssociationKeyResult[] getNextResourceAssociationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceAssociationKeyResult[]) getNext(howMany);
    }




}
