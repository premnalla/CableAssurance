/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceAssociationValue;
import javax.oss.cbe.resource.ResourceAssociationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceAssociationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.resource.ResourceAssociationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceAssociationValueIteratorImpl
extends ossj.common.cbe.AssociationValueIteratorImpl
implements ResourceAssociationValueIterator
{ 

    /**
     * Constructs a new ResourceAssociationValueIteratorImpl with empty attributes.
     * 
     */

    public ResourceAssociationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ResourceAssociationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ResourceAssociationValueIteratorImpl(ResourceAssociationValue[] values){
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
    public ResourceAssociationValue[] getNextResourceAssociations(int howMany)
    throws java.rmi.RemoteException {
        return (ResourceAssociationValue[]) getNext(howMany);
    }




}
