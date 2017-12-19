/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.type.ri;

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
import javax.oss.cfi.activity.ReportRecord;
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.ipb.type.UsageRecValue;


/**
 * RI imports
 */
import com.nec.oss.ipb.type.ri.UsageRecValueIteratorHome;

/**
 * A javax.oss.ipb.type.UsageRecValueIterator implementation.
 * as a Stateful Session Bean
 */
public class UsageRecValueIteratorBean
implements SessionBean
{
    
    /**
     * Member fields. These should be serialiable.
     */
    SessionContext myContext = null;
    
    int currentPosition = 0;
    List usageRecValuesList = null;
    RecordDescriptor recordDescriptor = null;


    Iterator usageRecValuesListIterator = null;

    /**
     * getNext is called to retrieve the next available values.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entity values with a size of at
     * most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException Thrown when a system error occurs
     */
	public ReportRecord[] getNext(int howMany) 
		throws RemoteException
    {
        if( (usageRecValuesList == null) ||
            (usageRecValuesList.isEmpty()))
        {
            //System.err.println("### The list is empty! Returning");
            
            return new UsageRecValueImpl[0];
        }

        /**
         *  Make sure that the iterator is valid. If re-activated,
         * we need to reiniitialize it. The iterator is not a serializable
         * object. Hence, we need to do this manually.
         */
        if(currentPosition >= usageRecValuesList.size())
        {
            // Nothing more to get. 
            return new UsageRecValueImpl[0];
        }

        if(usageRecValuesListIterator == null)
        {
            // Means, never initialized or just re-activated.
            // Needs to reinitialize the iterator.
            usageRecValuesListIterator = usageRecValuesList.iterator();

            // now advance the iterator to the correct position.
            for ( int i = 0; i < currentPosition; ++i )
            {
                usageRecValuesListIterator.next();
            }
        }
        
        UsageRecValue [] prodKeyRes = new UsageRecValue[howMany];

        //System.err.println("Received request for " + howMany + " items");
        
        for ( int i = currentPosition;
              i < (currentPosition + howMany);
              ++i )
        {
            // Check if there are more items left in the iterator.
            if(!usageRecValuesListIterator.hasNext())
            {
                //System.err.println("We have no more items left to return");
                
                // none are left. All items were processed.

                // Create a smaller array to return.
                UsageRecValue [] smallerUsageRecValues
                    = new UsageRecValue [i - currentPosition];

                System.arraycopy(prodKeyRes, currentPosition,
                                 smallerUsageRecValues, 0,
                                 (i - currentPosition));
                currentPosition = i;
                
                return smallerUsageRecValues;
            }

            prodKeyRes[i - currentPosition] = 
				(UsageRecValue)usageRecValuesListIterator.next();
        }

        return prodKeyRes;
            
    }

	/**
	 * Return an array of <CODE>UsageRecValue</CODE> instances.
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
	 * @return An array of <CODE>UsageRecValue</CODE> instances.
	 *
	 * @exception  RemoteException Thrown when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 * @exception  javax.oss.IllegalArgumentException Thrown when
     * then input argument is invalid.
	 * @see javax.oss.ipb.type.UsageRecValue
     *
	 */
	public UsageRecValue[] getNextUsageRecValues(int howMany)
        throws javax.oss.IllegalArgumentException
    {
        if(howMany < 0)
        {
            throw new javax.oss.IllegalArgumentException(
                "howMany parameter value is invalid: " + howMany);
        }
        try
        {
        return (UsageRecValue[]) getNext(howMany);
        }catch(RemoteException ree)
        {
        throw new javax.ejb.EJBException(ree);
        }
    }


	/**
	 * Get the next set of UsageRecValues as ReportRecords.
	 * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>UsageRecValue</CODE> instances.
	 *
	 * @exception  RemoteException Thrown when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 * @exception  javax.oss.IllegalArgumentException Thrown when
     * then input argument is invalid.
	 * @see javax.oss.ipb.type.UsageRecValue
     *
	 */
    public ReportRecord[] getNextReportRecords(int howMany)
           throws javax.oss.IllegalArgumentException
    {
        return getNextUsageRecValues(howMany);
    }
    

    /**
     * Create the Stateful Session Bean to manage an array of UsageRecValue
     * objects.
     * @param recordDesc RecordDescriptor providing details on the data
     * @param usageRecValues Array of UsageRecValues to manage
     *
     * @exception  CreateException Thown if the input array is invalid
     *
     */
    public void ejbCreate(
        RecordDescriptor recordDesc,
        UsageRecValue [] usageRecValues) throws CreateException
    {
        //System.err.println("Enter ejbCrate of UsageRecValueIteatorBean");
        
        if(usageRecValues != null)
        {
            if(usageRecValues.length > 0)
            {
                usageRecValuesList = new ArrayList();
                for(int usageRecIdx=0; usageRecIdx<usageRecValues.length; 
						++usageRecIdx)
                {
                    usageRecValuesList.add(usageRecValues[usageRecIdx]);
                }

                usageRecValuesListIterator = 
										usageRecValuesList.iterator();

            }
            //System.err.println("Received " + usageRecValues.length + " items");
        }
        else
        {
            throw new CreateException (
                "Supplied invalid usageRecValues argument to ejbCreate");
        }


		if( recordDesc != null)
		{
			recordDescriptor = recordDesc;
		}
		else
		{
            throw new CreateException (
                "Supplied invalid recordDescriptor argument to ejbCreate");
		}
	
        //System.err.println("Exit ejbCrate of UsageRecValueIteatorBean");
        
    }

    /**
     * Free the UsageRecValue objects managed by this iterator.
     */
    public void cleanup()
    {
        if(usageRecValuesList != null)
        {
            usageRecValuesList.clear();
            usageRecValuesList = null;
        }
        
        usageRecValuesListIterator=null;
    }
    

    /**
     * Get the RecordDescriptor managed by this iterator.
     * @return RecordDescriptor for this iterator
     */
    public RecordDescriptor getRecordDescriptor()
    {
		return(recordDescriptor);
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