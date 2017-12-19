
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;

/**
 * Standard imports
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.naming.*;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

/**
 * OSS/J Interface imports.
 */
import javax.oss.ManagedEntityKeyResult;
import javax.oss.cfi.activity.ActivityKeyResult;
import javax.oss.ipb.producer.ProducerKeyResult;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;


/**
 * RI imports
 */
import com.nec.oss.ipb.producer.ri.ProducerKeyResultImpl;
import com.nec.oss.ipb.producer.ri.ProducerKeyResultIteratorHome;

/**
 * RI util imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerEntity;
import com.nec.oss.ipb.producer.ri.ProducerEntityHome;

/**
 * A javax.oss.ipb.producer.ProducerKeyResultIterator implementation
 * using a Stateful Session Bean.
 */
public class ProducerKeyResultIteratorBean
implements SessionBean
{
    /**
     * Member fields. These should be serialiable.
     */
    SessionContext myContext = null;
    
    int currentPosition = 0;
    List producerKeyResultsList = null;


    Iterator producerKeyResultsListIterator = null;

    /**
     * getNext is called to retrieve the next available values.
     * This throws Remote exception, as the this method is not applicable
     * for the ProducerKeyResult. It is not a ManagedEntityValue.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entity values with a size of at
     * most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException Thrown if a system error occurs.
     */
	public ManagedEntityKeyResult[] getNext(int howMany) 
    {
        if( (producerKeyResultsList == null) ||
            (producerKeyResultsList.isEmpty()))
        {
            //System.err.println("### The list is empty! Returning");
            
            return new ProducerKeyResultImpl[0];
        }

        /**
         *  Make sure that the iterator is valid. If re-activated,
         * we need to reiniitialize it. The iterator is not a serializable
         * object. Hence, we need to do this manually.
         */
        if(currentPosition >= producerKeyResultsList.size())
        {
            // Nothing more to get. 
            return new ProducerKeyResultImpl[0];
        }

        if(producerKeyResultsListIterator == null)
        {
            // Means, never initialized or just re-activated.
            // Needs to reinitialize the iterator.
            producerKeyResultsListIterator = producerKeyResultsList.iterator();

            // now advance the iterator to the correct position.
            for ( int i = 0; i < currentPosition; ++i )
            {
                producerKeyResultsListIterator.next();
            }
        }
        
        ProducerKeyResult [] prodKeyRes = new ProducerKeyResult[howMany];

        //System.err.println("Received request for " + howMany + " items");
        
        for ( int i = currentPosition;
              i < (currentPosition + howMany);
              ++i )
        {
            // Check if there are more items left in the iterator.
            if(!producerKeyResultsListIterator.hasNext())
            {
                //System.err.println("We have no more items left to return");
                
                // none are left. All items were processed.

                // Create a smaller array to return.
                ProducerKeyResult [] smallerProducerKeyResults
                    = new ProducerKeyResult [i - currentPosition];

                System.arraycopy(prodKeyRes, currentPosition,
                                 smallerProducerKeyResults, 0,
                                 (i - currentPosition));
                currentPosition = i;
                
                return smallerProducerKeyResults;
            }

            prodKeyRes[i - currentPosition] = 
				(ProducerKeyResult)producerKeyResultsListIterator.next();
        }

        return prodKeyRes;
            
    }

	/**
	 * Return an array of <CODE>ProducerKeyResult</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * The implementation is free to return less or equal number of
     * the requested items.
     * <p>
     * If zero is specified as the value of <CODE>howMany</CODE>,
     * an empty list is returned.
     * <p>
     * If the iteration is empty or has reached the end, an empty
     * array (valid array object, with zero length) is returned.
     *
     * <p>
     * If the <CODE>howMany</CODE> argument value is greater than the
     * number of items retrieved in the result, it is upto the
     * implementation to decide how many items should be returned
     * in one or more operations.
     *
     *<p>
     * If the <CODE>howMany</CODE> argument plus the cursor of the
     * <CODE>Iterator</CODE>
     * is greater than the number of items being held by the <CODE>Iterator</CODE>,
     * it is upto the implementation to decide either to return all
     * or to return a certain number of items from the present position
     * of the cursor to the end.
	 * 
	 * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ProducerKeyResult</CODE> instances.
	 *
	 *@exception java.rmi.RemoteException Is raised when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 * @see javax.oss.ipb.producer.ProducerKeyResult
     *
	 */    
	public ProducerKeyResult[] getNextProducerKeyResults(int howMany)
        throws javax.oss.IllegalArgumentException
    {
        if(howMany < 0)
        {
            throw new javax.oss.IllegalArgumentException(
                "howMany parameter value is invalid: " + howMany);
        }
        
        return (ProducerKeyResult[]) getNext(howMany);
    }

	/**
	 * Return an array of <CODE>ActivityKeyResult</CODE> instances.
     * The maximum number of the elements in the returned array
     * is capped to the client specified <CODE>howMany</CODE> parameter value.
     * The implementation is free to return less or equal number of
     * the requested items.
	 * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ProducerKeyResult</CODE> instances.
	 *
	 * @exception java.rmi.RemoteException Is raised when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 */    
    public ActivityKeyResult[] getNextActivityKeyResults(int howMany)
        throws javax.oss.IllegalArgumentException
    {
        return getNextProducerKeyResults(howMany);
    }
    

    /**
     * Create the Stateful Session Bean to manage an array of ProducerKeyResult
     * values.
     * @param reportInfoValues Array of ProducerKeyResult to manage
     *
     * @exception  CreateException Thown if the input array is invalid
     *
     */
    public void ejbCreate(
        ProducerKeyResult [] producerKeyResults) throws CreateException
    {
        if(producerKeyResults != null)
        {
            if(producerKeyResults.length > 0)
            {
                producerKeyResultsList = new ArrayList();
                for(int prodIdx=0; prodIdx<producerKeyResults.length; ++prodIdx)
                {
                    producerKeyResultsList.add(producerKeyResults[prodIdx]);

                }

                producerKeyResultsListIterator = 
										producerKeyResultsList.iterator();
            }
        }
        else
        {
            throw new CreateException (
                "Supplied invalid arguments to ejbCreate");
        }
    }

    /**
     * Free the ProducerKeyResult objects managed by this iterator.
     */
    public void cleanup()
    {
        if(producerKeyResultsList != null)
        {
            producerKeyResultsList.clear();
            producerKeyResultsList = null;
        }
        
        producerKeyResultsListIterator = null;
    }
    
    /**
     * Activiation of the Stateful Session Bean. Performs no processing.
     */
    public void ejbActivate()
    {
        // Do nothing for now.
    }

    /**
     * Passivating of the Stateful Session Bean. Performs no processing.
     */
    public void ejbPassivate()
    {
        // Do nothing for now.
    }

    /**
     * Removal of the Stateful Session Bean. Calls {@link #cleanup()}
     */
    public void ejbRemove()
    {
		cleanup();
    }

    /**
     * Set the value of the SessionContext
     * @param newContext new SessionContext valud
     */
    public void setSessionContext(
        SessionContext newContext)
    {
        myContext = newContext;
    }
}