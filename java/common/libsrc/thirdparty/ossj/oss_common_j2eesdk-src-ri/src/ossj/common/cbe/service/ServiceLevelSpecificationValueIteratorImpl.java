/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecificationValue;
import javax.oss.cbe.service.ServiceLevelSpecificationValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecificationValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecificationValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecificationValueIteratorImpl
extends ossj.common.cbe.EntitySpecificationValueIteratorImpl
implements ServiceLevelSpecificationValueIterator
{ 

    /**
     * Constructs a new ServiceLevelSpecificationValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecificationValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelSpecificationValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelSpecificationValueIteratorImpl(ServiceLevelSpecificationValue[] values){
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
    public ServiceLevelSpecificationValue[] getNextServiceLevelSpecifications(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelSpecificationValue[]) getNext(howMany);
    }




}
