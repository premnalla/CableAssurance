/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelObjectiveValue;
import javax.oss.cbe.service.ServiceLevelObjectiveValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelObjectiveValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelObjectiveValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelObjectiveValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements ServiceLevelObjectiveValueIterator
{ 

    /**
     * Constructs a new ServiceLevelObjectiveValueIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelObjectiveValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelObjectiveValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelObjectiveValueIteratorImpl(ServiceLevelObjectiveValue[] values){
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
    public ServiceLevelObjectiveValue[] getNextServiceLevelObjectives(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelObjectiveValue[]) getNext(howMany);
    }




}
