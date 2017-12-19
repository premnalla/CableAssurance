/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceSpecificationKeyResult;
import javax.oss.cbe.service.ServiceSpecificationKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceSpecificationKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceSpecificationKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceSpecificationKeyResultIteratorImpl
extends ossj.common.cbe.EntitySpecificationKeyResultIteratorImpl
implements ServiceSpecificationKeyResultIterator
{ 

    /**
     * Constructs a new ServiceSpecificationKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceSpecificationKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceSpecificationKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceSpecificationKeyResultIteratorImpl(ServiceSpecificationKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceSpecificationKeyResult[] getNextServiceSpecificationKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceSpecificationKeyResult[]) getNext(howMany);
    }




}
