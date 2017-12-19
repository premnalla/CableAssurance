package com.nec.oss.ipb.type.ri;


/**
 * Title:        JSR130 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports.
 */
import java.rmi.*;
import javax.ejb.*;


/**
 * Spec imports.
 */

import javax.oss.ipb.type.UsageRecValue;
import javax.oss.ipb.type.UsageRecValueIterator;
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.cfi.activity.ReportRecord;
import com.nec.oss.ipb.type.ri.UsageRecValueStatefulIterator;

/**
 * Implements the UsageRecValueIterator interface as a StatefulSessionBean.
 * Another API may want to implement this as a simple object array with
 * index tracking.
 */

public class UsageRecValueIteratorImpl
implements UsageRecValueIterator
{
	UsageRecValueStatefulIterator ssbIter;

    /**
     * Get the RecordDescriptor for this iterator
     * 
     * @return record descriptor
     * @exception RemoteException Thrown if a system error occurs getting the
     * RecordDescriptor
     */
    public RecordDescriptor getRecordDescriptor()
        throws RemoteException
    {
		return(ssbIter.getRecordDescriptor());
    }

   /**
     * Remove the StatefulSessionBean used by the iterator.
     * @exception RemoteException Is raised when it
     * encounters processing errors or system exception occurrs.
     * @exception RemoveException Is raised when it has a problem in removal.
     */
    public void remove()
        throws RemoteException,
            javax.ejb.RemoveException
    {
        try
        {
            ssbIter.remove();
        }
        catch(Exception ex)
        {
            System.out.println("Error removing UsageRecValueIterator:"+ex) ;
        }
    }

    /**
     * Get an array of <CODE>UsageRecValue</CODE> instances.
     * 
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return Array of <CODE>UsageRecValue</CODE> instances
     *
     * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     */
	public UsageRecValue[] getNextUsageRecValues(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException
	{
		return(ssbIter.getNextUsageRecValues(howMany));
	}

    /**
     * Return an array of <CODE>ReportRecord</CODE> instances 
     * 
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return Array of <CODE>ReportRecord</CODE> instances
     *
     * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     */
	public ReportRecord[] getNextReportRecords(int howMany)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException
	{
		return(ssbIter.getNextUsageRecValues(howMany));
	}

    /**
     * Set the StatefulSessionBean used by the iterator.
     *
     * @param beanIter StatefulSessionBean encapsulated by this object.
     */
    public void setIterBean(UsageRecValueStatefulIterator beanIter)
    {
        this.ssbIter=beanIter;
    }
}
