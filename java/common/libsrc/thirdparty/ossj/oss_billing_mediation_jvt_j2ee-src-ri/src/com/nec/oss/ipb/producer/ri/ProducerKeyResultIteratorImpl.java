
package com.nec.oss.ipb.producer.ri;


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
import javax.oss.ipb.producer.ProducerKeyResultIterator;
import javax.oss.ipb.producer.ProducerKeyResult;
import javax.oss.cfi.activity.*;
import javax.oss.*;


/**
 * RI imports.
 */
import com.nec.oss.cfi.activity.ri.ActivityKeyResultIteratorImpl;

/**
 * Implementation for
 * javax.oss.ipb.producer.ProducerKeyResultIterator interface as a 
 * StatefulSessionBean. Another API may want to implement this as a simple
 * object array with index tracking.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */
public class ProducerKeyResultIteratorImpl
extends ActivityKeyResultIteratorImpl
implements ProducerKeyResultIterator
{
	ProducerKeyResultStatefulIterator ssbIter;

	/**
	 * Return an array of <CODE>ProducerKeyResult</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>size</CODE> parameter value.
     * The implementation is free to return less or equal number of
     * the requested items.
     * <p>
     * If zero is specified as the value of <CODE>size</CODE>,
     * an empty list is returned.
     * <p>
     * If the iteration is empty or has reached the end, an empty
     * array (valid array object, with zero length) is returned.
     *
     * <p>
     * If the <CODE>size</CODE> argument value is greater than the
     * number of items retrieved in the result, it is upto the
     * implementation to decide how many items should be returned
     * in one or more operations.
     *
     *<p>
     * If the <CODE>size</CODE> argument plus the cursor of the <CODE>Iterator</CODE>
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * it is upto the implementation to decide either to return all
     * or to return a certain number of items from the present position
     * of the cursor to the end.
	 * 
	 * @param size Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ProducerKeyResult</CODE> instances.
	 *
	 * @exception javax.oss.IllegalArgumentException Thrown if the input arg
     * is not valid
     * encounters processing errors or system exception occurrs.
	 * @exception java.rmi.RemoteException Thrown when <CODE>Iterator</CODE> 
     * encounters processing errors or system exception occurrs.
	 *
	 * @see javax.oss.ipb.producer.ProducerKeyResult
	 */
	public ProducerKeyResult[] getNextProducerKeyResults(
        int size) 
		throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException
    {
		return(ssbIter.getNextProducerKeyResults(size));
    }

	/**
	 * Return an array of <CODE>ManagedEntityKeyResult</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>size</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
	 * @param size Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ManagedEntityKeyResult</CODE> instances.
	 *
	 * @exception javax.oss.IllegalArgumentException Thrown if the input arg
     * is not valid
	 * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
	 */
    public ManagedEntityKeyResult[] getNext(int size)
        throws RemoteException
    {
        return(ssbIter.getNext(size));
    }

	/**
	 * Return an array of <CODE>ActivityKeyResult</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>size</CODE> parameter value.
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
	 * @param size Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ActivityKeyResult</CODE> instances.
	 *
	 * @exception javax.oss.IllegalArgumentException Thrown if the input arg
     * is not valid
	 * @exception java.rmi.RemoteException Is raised when <CODE>Iterator</CODE>
     * encounters processing errors or system exception occurrs.
	 */
    public ActivityKeyResult[] getNextActivityKeyResults(int size)
        throws java.rmi.RemoteException,
        javax.oss.IllegalArgumentException
    {
        return(ssbIter.getNextActivityKeyResults(size));
    }

	/**
	 * Remove the StatefulSessionBean used by the iterator.
	 */
    public void remove()
    {
        try
        {
            ssbIter.remove();
        }
        catch(Exception ex)
        {
            System.out.println("Error removing ProducerKeyResultStatefulIterator:"+ex);
        }
	}

	/**
	 * Set the StatefulSessionBean used by the iterator.
	 * 
	 * @param beanIter StatefulSessionBean encapsulated by this object.
	 */
    public void setIterBean(ProducerKeyResultStatefulIterator beanIter)
    {
        this.ssbIter=beanIter;
    }
}
