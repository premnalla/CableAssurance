
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.cfi.activity.ri;

/**
 * Standard imports
 */

/**
 * OSS/J Interface imports.
 */
import javax.oss.cfi.activity.*;

/**
 * This interface is declared just to implement the iterator as a
 * true stateful session bean - this is an optional thing to do. 
 * If the stateful session bean
 * implementation for this iterator is not desired, there is no need
 * to use this interface anywhere.
 * <p>
 *  This interface is never directly exposed to the client
 * The client of the iterator, deals with
 * javax.oss.cfi.activity.ReportInfoIterator only.
 */
public interface ReportInfoStatefulIterator
extends javax.ejb.EJBObject
{

    /**
     * Get the next set of ReportInfo objects
     *
     * @param howMany Number of ReportInfo objects requested
     * @return Array of ReportInfo objects
     * @exception java.rmi.RemoteException is raised when there is a system error
     */
    ReportInfo[] getNextReportInfos(int howMany)
        throws java.rmi.RemoteException;

    /**
     * Get the next set of ReportInfo objects
     *
     * @param howMany Number of ReportInfo objects requested
     * @return Array of ReportInfo objects
     * @exception java.rmi.RemoteException is raised when there is a system error
     */
    ReportInfo[] getNext(int howMany)
        throws java.rmi.RemoteException;
}
