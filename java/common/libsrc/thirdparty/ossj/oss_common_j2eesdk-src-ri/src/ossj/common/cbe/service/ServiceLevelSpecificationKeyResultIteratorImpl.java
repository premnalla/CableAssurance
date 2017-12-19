/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecificationKeyResult;
import javax.oss.cbe.service.ServiceLevelSpecificationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecificationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecificationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecificationKeyResultIteratorImpl
extends ossj.common.cbe.EntitySpecificationKeyResultIteratorImpl
implements ServiceLevelSpecificationKeyResultIterator
{ 

    /**
     * Constructs a new ServiceLevelSpecificationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecificationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelSpecificationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelSpecificationKeyResultIteratorImpl(ServiceLevelSpecificationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceLevelSpecificationKeyResult[] getNextServiceLevelSpecificationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelSpecificationKeyResult[]) getNext(howMany);
    }




}
