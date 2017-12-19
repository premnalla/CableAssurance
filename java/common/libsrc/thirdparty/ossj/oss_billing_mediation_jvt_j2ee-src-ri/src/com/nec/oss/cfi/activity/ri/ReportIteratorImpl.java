
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
import javax.oss.cfi.activity.ReportRecord;
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.cfi.activity.ReportIterator;

/**
 * Implements the ReportIterator interface. By itself, this interface
 * is not very useful, as each API, adapting this base interface, has
 * its own way of implementing the Iterator. For example, an IP Billing
 * API might implement this as a Stateful Session Bean, whereas QoS API
 * might implement this as a simple object array with index tracking.
 * 
 */

public class ReportIteratorImpl
implements javax.oss.cfi.activity.ReportIterator
{

    /**
     * Get the RecordDescriptor providing descriptive information on the
     * data managed by this iterator.
     *
     * @return RecordDescriptor for this iterator
     *
     * @exception RemoteException Thrown when there is an error getting the
     * record descriptor - always thrown by this implementation
     */
    public RecordDescriptor getRecordDescriptor()
        throws RemoteException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ReportIteratorImpl baseclass");
    }
    
    
    /**
     * Get the next set of ReportRecords
     *
     * @param howMany Number of records requested
     *
     * @exception RemoteException Thrown when there is an error getting the
     * record descriptor - always thrown by this implementation
     * @exception javax.oss.IllegalArgumentException Thrown when the input
     * argument is invalid
     */
    public ReportRecord[] getNextReportRecords(int howMany)
        throws RemoteException,
            javax.oss.IllegalArgumentException
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ReportIteratorImpl baseclass");
    }
    
    
    /**
     * Free the resources used by this iterator
     *
     * @param howMany Number of records requested
     *
     * @exception RemoteException Thrown when there is a system error performing
     * the removal - always thrown by this implementation
     * @exception javax.ejb.RemoveException Thrown when there is a processing
     * error freeing the resources
     */
    public void remove()
        throws RemoteException, javax.ejb.RemoveException
            
    {
        throw new RemoteException("Sorry! Not implemented in " +
                                  "the ReportIteratorImpl baseclass");
    }
}
