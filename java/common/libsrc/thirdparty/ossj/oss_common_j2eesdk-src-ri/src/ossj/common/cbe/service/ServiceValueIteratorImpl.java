/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceValue;
import javax.oss.cbe.service.ServiceValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ServiceValueIterator
{ 

    /**
     * Constructs a new ServiceValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceValueIteratorImpl(ServiceValue[] values){
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
    public ServiceValue[] getNextServices(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceValue[]) getNext(howMany);
    }




}
