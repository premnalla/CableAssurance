
package com.nec.oss.cfi.activity.ri;

/**
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
import javax.oss.cfi.activity.ReportInfo;
import javax.oss.cfi.activity.ReportInfoIterator;

/**
 * Implements the ReportInfoIterator interface as a StatefulSessionBean.
 * Another API may want to implement this as a simple object array with 
 * index tracking.
 * 
 */

public class ReportInfoIteratorImpl
implements javax.oss.cfi.activity.ReportInfoIterator
{

	ReportInfoStatefulIterator ssbIter;

    /**
     * Return an array of <CODE>ReportInfo</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</COD
E>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return An array of <CODE>ReportInfo</CODE> instances.
     *
     * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     */
    public ReportInfo[] getNext(int howMany)
        throws RemoteException
    {
		return(ssbIter.getNext(howMany));
    }
    
    /**
     * Remove the StatefulSessionBean used by the iterator.
     * @exception RemoteException Is raised when it
     * encounters processing errors or system exception occurrs.
     * @exception RemoveException Is raised when it has a problem in removal.
     */
    public void remove()
        throws RemoteException, RemoveException
    {
        try
        {
            ssbIter.remove();
        }
        catch(Exception ex)
        {
            System.out.println("Error removing ReportInfoStatefulIterator:"+ex);
        }
    }


    /**
     * Return an array of <CODE>ReportInfo</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</COD
E>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return An array of <CODE>ReportInfo</CODE> instances.
     *
     * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     */
    public ReportInfo[] getNextReportInfos(int howMany)
        throws RemoteException
    {
		return(ssbIter.getNextReportInfos(howMany));
    }

    /**
     * Set the StatefulSessionBean used by the iterator.
     *
     * @param beanIter StatefulSessionBean encapsulated by this object.
     */
    public void setIterBean(ReportInfoStatefulIterator beanIter)
    {
        this.ssbIter=beanIter;
    }
}
