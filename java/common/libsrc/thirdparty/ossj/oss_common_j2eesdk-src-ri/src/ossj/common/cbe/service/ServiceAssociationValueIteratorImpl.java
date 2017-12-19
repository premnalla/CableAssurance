/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceAssociationValue;
import javax.oss.cbe.service.ServiceAssociationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceAssociationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceAssociationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceAssociationValueIteratorImpl
extends ossj.common.cbe.AssociationValueIteratorImpl
implements ServiceAssociationValueIterator
{ 

    /**
     * Constructs a new ServiceAssociationValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceAssociationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceAssociationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceAssociationValueIteratorImpl(ServiceAssociationValue[] values){
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
    public ServiceAssociationValue[] getNextServiceAssociations(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceAssociationValue[]) getNext(howMany);
    }




}
