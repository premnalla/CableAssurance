/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceSpecificationValue;
import javax.oss.cbe.service.ServiceSpecificationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceSpecificationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceSpecificationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceSpecificationValueIteratorImpl
extends ossj.common.cbe.EntitySpecificationValueIteratorImpl
implements ServiceSpecificationValueIterator
{ 

    /**
     * Constructs a new ServiceSpecificationValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceSpecificationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceSpecificationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceSpecificationValueIteratorImpl(ServiceSpecificationValue[] values){
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
    public ServiceSpecificationValue[] getNextServiceSpecifications(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceSpecificationValue[]) getNext(howMany);
    }




}
