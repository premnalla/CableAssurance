package com.nec.oss.ipb.transfer.ri;

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
import javax.oss.ManagedEntityValue;
import javax.oss.ipb.transfer.TransferStatusValue;
import javax.oss.ipb.transfer.TransferStatusValueIterator;
import com.nec.oss.ipb.transfer.ri.TransferStatusValueStatefulIterator;

/**
 * Implements the TransferStatusIterator interface as a StatefulSessionBean.
 * Another API may want to implement this as a simple object array with 
 * index tracking.
 * 
 */

public class TransferStatusValueIteratorImpl
implements TransferStatusValueIterator
{
	TransferStatusValueStatefulIterator ssbIter;

    /**
     * Return an array of <CODE>ManagedEntityValue</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return An array of <CODE>ManagedEntityValue</CODE> instances.
     *
     * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     */
    public ManagedEntityValue[] getNext(int howMany)
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
            System.out.println("Error removing TransferStatusValueIterator:"+ex);
        }
    }

    /**
     * Return an array of <CODE>TransferStatusValue</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
     *
     * @return An array of <CODE>TransferStatusValue</CODE> instances.
     *
     * @exception RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
     * @exception javax.oss.IllegalArgumentException Thrown when the input
     * argument is invalid.
     */
	public TransferStatusValue[] getNextTransferStatusValues(int howMany)
		throws RemoteException,
            javax.oss.IllegalArgumentException
    {
		return(ssbIter.getNextTransferStatusValues(howMany));
    }

    /**
     * Set the StatefulSessionBean used by the iterator.
     *
     * @param beanIter StatefulSessionBean encapsulated by this object.
     */
    public void setIterBean(TransferStatusValueStatefulIterator beanIter)
    {
        this.ssbIter=beanIter;
    }

}
