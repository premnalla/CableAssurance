/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelObjectiveKeyResult;
import javax.oss.cbe.service.ServiceLevelObjectiveKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelObjectiveKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.ServiceLevelObjectiveKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelObjectiveKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements ServiceLevelObjectiveKeyResultIterator
{ 

    /**
     * Constructs a new ServiceLevelObjectiveKeyResultIteratorImpl with empty attributes.
     * 
     */

    public ServiceLevelObjectiveKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new ServiceLevelObjectiveKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public ServiceLevelObjectiveKeyResultIteratorImpl(ServiceLevelObjectiveKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public ServiceLevelObjectiveKeyResult[] getNextServiceLevelObjectiveKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (ServiceLevelObjectiveKeyResult[]) getNext(howMany);
    }




}
