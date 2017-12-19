
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.type.ri;

/**
 * Standard imports
 */

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.type.*;
import javax.oss.cfi.activity.*;


/**
 * This interface is declared just to implement the iterator as a
 * true stateful session bean. The base interface, namely,
 * javax.oss.ManagedEntityValueIterator doesn't mandate this however.
 * Hence, this is an optional thing to do. If the stateful session bean
 * implementation for this iterator is not desired, there is no need
 * to use this interface anywhere.
 * <p>
 *  This interface is never directly exposed to the client
 * The client of the iterator, deals with
 * javax.oss.ipb.type.UsageRecValueIterator only.
 */
public interface UsageRecValueStatefulIterator
extends javax.ejb.EJBObject
{
    /**
     * Get the next set of UsageRecValue objects
     *
     * @param howMany Number of UsageRecValue objects requested
     * @return Array of UsageRecValue objects
     * @exception RemoteException is raised when there is a system error
     */
    UsageRecValue[] getNextUsageRecValues(int how_many)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;

    /**
     * Get the RecordDescriptor for this data set
     *
     * @return RecordDescriptor describing this set of data
     * @exception RemoteException is raised when there is a system error
     */
    RecordDescriptor getRecordDescriptor()
        throws java.rmi.RemoteException;

    /**
     * Get the next set of UsageRecValue objects
     *
     * @param howMany Number of UsageRecValue objects requested
     * @return Array of UsageRecValue objects
     * @exception RemoteException is raised when there is a system error
     */
    ReportRecord[] getNextReportRecords(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException;
}
