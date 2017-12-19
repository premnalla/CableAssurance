/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceAssociationKeyResult;
import javax.oss.cbe.service.ServiceAssociationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceAssociationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceAssociationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceAssociationKeyResultIteratorImpl
extends ossj.common.cbe.AssociationKeyResultIteratorImpl
implements ServiceAssociationKeyResultIterator
{ 

    /**
     * Constructs a new ServiceAssociationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceAssociationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceAssociationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceAssociationKeyResultIteratorImpl(ServiceAssociationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceAssociationKeyResult[] getNextServiceAssociationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceAssociationKeyResult[]) getNext(howMany);
    }




}
