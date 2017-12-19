
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
import javax.oss.ManagedEntityKeyResult;
import javax.oss.cfi.activity.ActivityKeyResult;
import javax.oss.cfi.activity.ActivityKeyResultIterator;

/**
 * Implements the ActivityKeyResultIteratorImpl interface.
 * By itself, this interface
 * is not very useful, as each API, adapting this base interface, has
 * its own way of implementing the Iterator. For example, an IP Billing
 * API might implement this as a Stateful Session Bean, whereas QoS API
 * might implement this as a simple object array with index tracking.
 * 
 */

public class ActivityKeyResultIteratorImpl
implements ActivityKeyResultIterator
{

    /**
     * Currently, this always throws the remote exception.
     * Subclasses must override this to provide with implementation
     * appropriately. 
     * @param howMany number of items to return
     * @return Array of up to howMany ManagedEntityKeyResult objects
     * @exception RemoteException - Thrown always for this implementation
     */
    public ManagedEntityKeyResult[] getNext(int howMany)
        throws RemoteException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityKeyResultIteratorImpl baseclass");
    }
    
    /**
     * Currently, this always throws remote exception. Subclasses
     * must implement the clean up specific to their situation.
     * @exception RemoteException - Thrown always for this implementation
     */
    public void remove()
        throws RemoteException, RemoveException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityKeyResultIteratorImpl baseclass");
    }

	/**
	 * Return an array of <CODE>ActivityKeyResult</CODE> instances.
     * @param howMany number of items to return
     * @return Array of up to howMany ActivityyKeyResult objects
     * @exception RemoteException - Thrown always for this implementation
	 */
	public ActivityKeyResult[] getNextActivityKeyResults(int howMany)
		throws RemoteException,
            javax.oss.IllegalArgumentException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ActivityKeyResultIteratorImpl baseclass");
    }
}
