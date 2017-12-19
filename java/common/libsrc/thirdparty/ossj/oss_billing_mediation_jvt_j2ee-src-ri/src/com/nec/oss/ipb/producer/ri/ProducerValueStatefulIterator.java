
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
 *  This interface is never directly exposed to the client
 * The client of the iterator, deals with
 * javax.oss.ipb.producer.ProducerValueIterator only.
 */
public interface ProducerValueStatefulIterator
extends javax.ejb.EJBObject
{
    /**
     * Get the next set of ProducerValue objects
     *
     * @param howMany Number of ProducerValue objects requested
     * @return Array of ProducerValue objects
     * @exception java.rmi.RemoteException is raised when there is a system error
     * @exception javax.oss.IllegalArgumentException Thrown when the input arg
     * is invalid
     */
    ProducerValue[] getNextProducers(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;

    /**
     * Get the next set of ProducerValue objects as ActivityValues
     *
     * @param howMany Number of ProducerValue objects requested
     * @return Array of ProducerValue objects
     * @exception java.rmi.RemoteException is raised when there is a system error
     * @exception javax.oss.IllegalArgumentException Thrown when the input arg
     * is invalid
     */
    ActivityValue[] getNextActivities(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;

    /**
     * Get the next set of ProducerValue objects as ManagedEntityValues
     *
     * @param howMany Number of ProducerValue objects requested
     * @return Array of ProducerValue objects
     * @exception RemoteException is raised when there is a system error
     */
    public ManagedEntityValue[] getNext(int howMany)
        throws RemoteException;
}
