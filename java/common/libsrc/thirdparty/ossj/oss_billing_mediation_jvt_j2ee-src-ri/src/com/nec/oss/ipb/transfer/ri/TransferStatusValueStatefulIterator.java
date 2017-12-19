
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.transfer.ri;

/**
 * Standard imports
 */

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.transfer.*;
import javax.oss.ManagedEntityValue;


/**
 * This interface is declared just to implement the iterator as a
 * true stateful session bean - this is an optional thing to do.
 * If the stateful session bean
 * implementation for this iterator is not desired, there is no need
 * to use this interface anywhere.
 * <p>
 *  This interface is never directly exposed to the client
 * The client of the iterator, deals with
 * javax.oss.ipb.transfer.TransferStatusValueIterator only.
 */
public interface TransferStatusValueStatefulIterator
extends javax.ejb.EJBObject
{
    /**
     * Get the next set of TransferStatusValue objects
     *
     * @param howMany Number of TransferStatusValue objects requested
     * @exception RemoteException Thrown when there is a system error
     * @exception javax.oss.IllegalArgumentException Thrown when the input
	 * argument is invalid.
     */
     TransferStatusValue[] getNextTransferStatusValues(int howMany)
         throws java.rmi.RemoteException, javax.oss.IllegalArgumentException;
 
    /**
     * Get the next set of TransferStatusValue objects
     *
     * @param howMany Number of TransferStatusValue objects requested
     * @exception RemoteException Thrown when there is a system error
     */
     ManagedEntityValue[] getNext(int howMany)
         throws java.rmi.RemoteException;
}
