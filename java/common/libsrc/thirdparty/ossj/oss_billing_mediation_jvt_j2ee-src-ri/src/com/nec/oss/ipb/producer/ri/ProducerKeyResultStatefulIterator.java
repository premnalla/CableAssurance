
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;

/**
 * Standard imports
 */
import java.rmi.*;
import javax.ejb.*;


/**
 * OSS/J Interface imports.
 */
import javax.oss.cfi.activity.*;
import javax.oss.ipb.producer.*;
import javax.oss.*;


/**
 * This interface is declared just to implement the iterator as a
 * true stateful session bean - this is an optional thing to do. 
 * If the stateful session bean
 * implementation for this iterator is not desired, there is no need
 * to use this interface anywhere.
 * <p>
 * This interface is never directly exposed to the client
 * The client of the iterator, deals with
 * javax.oss.ipb.producer.ProducerKeyResultIterator only.
 */
public interface ProducerKeyResultStatefulIterator
    extends javax.ejb.EJBObject
{
    /**
     * Get the next set of ProducerKeyResult objects
     *
     * @param howMany Number of ReportInfo objects requested
     * @return Array of ReportInfo objects
     * @exception javax.oss.IllegalArgumentException Thrown when the input
     * param is invalid
     * @exception RemoteException Thrown when there is a system error
     */
    ProducerKeyResult[] getNextProducerKeyResults(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;

    /**
     * Get the next set of ProducerKeyResult objects as ActivityKeyResults
     *
     * @param howMany Number of ReportInfo objects requested
     * @return Array of ReportInfo objects
     * @exception javax.oss.IllegalArgumentException Thrown when the input
     * param is invalid
     * @exception RemoteException Thrown when there is a system error
     */
    ActivityKeyResult[] getNextActivityKeyResults(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;

    /**
     * Get the next set of ProducerKeyResult objects as ManagedEntityKeyResults
     *
     * @param howMany Number of ReportInfo objects requested
     * @return Array of ReportInfo objects
     * @exception RemoteException Thrown when there is a system error
     */
    ManagedEntityKeyResult[] getNext(int howMany)
        throws java.rmi.RemoteException;
}
