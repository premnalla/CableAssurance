
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.cfi.activity.ri;

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
import javax.oss.cfi.activity.ReportInfo;

/**
 * RI imports
 */
import com.nec.oss.cfi.activity.ri.ReportInfoIteratorHome;
import com.nec.oss.cfi.activity.ri.ReportInfoImpl;

/**
 * A javax.oss.ipb.type.ReportInfoIterator implementation using a Stateful
 * Session Bean
 */
public class ReportInfoIteratorBean
implements SessionBean
{
    
    /**
     * Member fields. These should be serialiable.
     */
    SessionContext myContext = null;
    
    int currentPosition = 0;
    List reportInfoValuesList = null;


    Iterator reportInfoValuesListIterator = null;

    /**
     * getNext is called to retrieve the next available values.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entity values with a size of at
     * most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException
     */
	public ReportInfo[] getNext(int howMany)
    {
        if( (reportInfoValuesList == null) ||
            (reportInfoValuesList.isEmpty()))
        {
            //System.err.println("### The list is empty! Returning");
            
            return new ReportInfoImpl[0];
        }

        /**
         *  Make sure that the iterator is valid. If re-activated,
         * we need to reiniitialize it. The iterator is not a serializable
         * object. Hence, we need to do this manually.
         */
        if(currentPosition >= reportInfoValuesList.size())
        {
            // Nothing more to get. 
            return new ReportInfoImpl[0];
        }

        if(reportInfoValuesListIterator == null)
        {
            // Means, never initialized or just re-activated.
            // Needs to reinitialize the iterator.
            reportInfoValuesListIterator = reportInfoValuesList.iterator();

            // now advance the iterator to the correct position.
            for ( int i = 0; i < currentPosition; ++i )
            {
                reportInfoValuesListIterator.next();
            }
        }
        
        ReportInfo [] prodKeyRes = new ReportInfo[howMany];

        //System.err.println("Received request for " + howMany + " items");
        
        for ( int i = currentPosition;
              i < (currentPosition + howMany);
              ++i )
        {
            // Check if there are more items left in the iterator.
            if(!reportInfoValuesListIterator.hasNext())
            {
                //System.err.println("We have no more items left to return");
                
                // none are left. All items were processed.

                // Create a smaller array to return.
                ReportInfo [] smallerReportInfos
                    = new ReportInfo [i - currentPosition];

                System.arraycopy(prodKeyRes, currentPosition,
                                 smallerReportInfos, 0,
                                 (i - currentPosition));
                currentPosition = i;
                
                return smallerReportInfos;
            }

            prodKeyRes[i - currentPosition] = 
				(ReportInfo)reportInfoValuesListIterator.next();
        }

        return prodKeyRes;
            
    }

	/**
	 * Return an array of <CODE>ReportInfo</CODE> instances.
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
	 * @return An array of <CODE>ReportInfo</CODE> instances.
	 *
	 * @exception java.rmi.RemoteException Is raised when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 * @see javax.oss.cfi.activity.ReportInfo
     *
	 */    
	public ReportInfo[] getNextReportInfos(int howMany)
    {
        if(howMany < 0)
        {
            throw new javax.ejb.EJBException(
                "howMany parameter value is invalid: " + howMany);
        }
        
        return (ReportInfo[]) getNext(howMany);
    }

	/**
	 * Create the Stateful Session Bean to manage an array of ReportInfo
     * values.
     * @param reportInfoValues Array of ReportInfo to manage
     *
     * @exception  CreateException Thown if the input array is invalid
     *
	 */    
    public void ejbCreate(
        ReportInfo [] reportInfoValues) throws CreateException
    {
        if(reportInfoValues != null)
        {
            if(reportInfoValues.length > 0)
            {
                reportInfoValuesList = new ArrayList();
                for(int infoIdx=0; infoIdx<reportInfoValues.length; ++infoIdx)
                {
                    reportInfoValuesList.add(reportInfoValues[infoIdx]);

                }

                reportInfoValuesListIterator = 
										reportInfoValuesList.iterator();
            }
        }
        else
        {
            throw new CreateException (
                "Supplied invalid reportInfoValues argument to ejbCreate");
        }
    }

    /**
     * Free the ReportInfo objects managed by this iterator.
     */
    public void cleanup()
    {
        if(reportInfoValuesList != null)
        {
            reportInfoValuesList.clear();
            reportInfoValuesList = null;
        }
        
        reportInfoValuesListIterator=null;
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
    public void setSessionContext(SessionContext newContext)
    {
        myContext = newContext;
    }
}