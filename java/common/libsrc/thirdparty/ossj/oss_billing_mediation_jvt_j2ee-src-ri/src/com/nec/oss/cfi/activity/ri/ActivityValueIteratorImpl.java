
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */


import java.rmi.RemoteException;
import javax.ejb.RemoveException;


/**
 * Spec imports.
 */
import javax.oss.ManagedEntityValue;
import javax.oss.cfi.activity.ActivityValue;
import javax.oss.cfi.activity.ActivityValueIterator;

/**
 * Implements the ActivityValueIteratorImpl interface.
 * By itself, this interface
 * is not very useful, as each API, adapting this base interface, has
 * its own way of implementing the Iterator. For example, an IP Billing
 * API might implement this as a Stateful Session Bean, whereas QoS API
 * might implement this as a simple object array with index tracking.
 * 
 */

public class ActivityValueIteratorImpl
implements ActivityValueIterator
{

    /**
     * Currently, this always throws the remote exception.
     * Subclasses must override this to provide with implementation
     * appropriately. 
     * @return Array of the next set of ActivityValues
     * @param howMany Maximum number of ActivityValues to return
     * @exception java.rmi.RemoteException - thrown for all invocations
     */
    public ManagedEntityValue[] getNext(int howMany)
        throws RemoteException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityValueIteratorImpl baseclass");
    }
    
    /**
     * Currently, this always throws remote exception. Subclasses
     * must implement the clean up specific to their situation.
     * @exception java.rmi.RemoteException - thrown for all invocations
     */
    public void remove()
        throws RemoteException, RemoveException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityValueIteratorImpl baseclass");
        
    }

	/**
	 * Return an array of <CODE>ActivityValue</CODE> instances.
     * @return Array of the next set of ActivityValues
     * @param howMany Maximum number of ActivityValues to return
     * @exception java.rmi.RemoteException - thrown for all invocations
	 */
	public ActivityValue[] getNextActivities(int howMany)
		throws RemoteException,
            javax.oss.IllegalArgumentException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityValueIteratorImpl baseclass");
        
    }
}
