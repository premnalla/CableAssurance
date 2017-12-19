
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
import javax.oss.ManagedEntityValue;
import javax.oss.cfi.activity.ActivityValue;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;


/**
 * RI imports
 */
import com.nec.oss.ipb.producer.ri.ProducerValueImpl;
import com.nec.oss.ipb.producer.ri.ProducerValueIteratorHome;

/**
 * RI util imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerEntity;
import com.nec.oss.ipb.producer.ri.ProducerEntityHome;

/**
 * A javax.oss.ipb.producer.ProducerValueIterator implementation.
 * using a Stateful Session Bean
 */
public class ProducerValueIteratorBean
implements SessionBean
{
    
    /**
     * Member fields. These should be serialiable.
     */
    SessionContext myContext = null;
    
    int currentPosition = 0;
    List producerKeysList = null;

    String[] attributes = null;


    Iterator producerKeysListIterator = null;
    ProducerEntityHome producerEntityHome = null;
  
       
    /**
     * getNext is called to retrieve the next available values.
     * This throws Remote exception, as the this method is not applicable
     * for the ProducerValue. It is not a ManagedEntityValue.
     * 
     * @param howMany Size of the result batches returned to the client.
     * @return an array of managed entity values with a size of at
     * most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException Thrown if a system error occurs
     */
	public ManagedEntityValue[] getNext(int howMany) 
    {
        if( (producerKeysList == null) ||
            (producerKeysList.isEmpty()))
        {
            return new ProducerValueImpl[0];
        }

        if(producerEntityHome == null)
        {
            try
            {
                Context initContext = new InitialContext();

                producerEntityHome = (ProducerEntityHome)
                    PortableRemoteObject.narrow(

                        initContext.lookup(
                            "java:comp/env/ejb/ProducerEntityBean"),
                        ProducerEntityHome.class);
            }
            catch (NamingException ne)
            {
                throw new javax.ejb.EJBException(
                    "Could not locate the Producer Entity Home");
            }
        }

        //System.err.println("Located producer entity home" + producerEntityHome);

        /**
         *  Make sure that the iterator is valid. If re-activated,
         * we need to reiniitialize it. The iterator is not a serializable
         * object. Hence, we need to do this manually.
         */
        if(currentPosition >= producerKeysList.size())
        {
            // Nothing more to get. 
            return new ProducerValueImpl[0];
        }

        if(producerKeysListIterator == null)
        {
            // Means, never initialized or just re-activated.
            // Needs to reinitialize the iterator.
            producerKeysListIterator = producerKeysList.iterator();

            // now advance the iterator to the correct position.
            for ( int i = 0; i < currentPosition; ++i )
            {
                producerKeysListIterator.next();
            }
            
        }
        
        ProducerValue [] producerValues = new ProducerValue[howMany];

        ProducerEntity producerEntity;
        ProducerKey producerKey;

        //System.err.println("Received request for " + howMany + " items");
        
        for ( int i = currentPosition;
              i < (currentPosition + howMany);
              ++i )
        {
                
            // Check if there are more items left in the iterator.
            if(!producerKeysListIterator.hasNext())
            {
                //System.err.println("We have no more items left to return");
                
                // none are left. All items were processed.

                // Create a smaller array to return.
                ProducerValue [] smallerProducerValues
                    = new ProducerValue [i - currentPosition];

                System.arraycopy(producerValues, currentPosition,
                                 smallerProducerValues, 0,
                                 (i - currentPosition));
                currentPosition = i;
                
                return smallerProducerValues;
            }

            producerKey = (ProducerKey) producerKeysListIterator.next();

            ProducerPrimaryKey producerPK =
                producerKey.getProducerPrimaryKey();
            
            //System.err.println("PrimaryKey of producer is: " + producerPK);
                
            try
            {
                //System.err.println("Invoking findByPrimaryKey with " + producerPK);
                
                producerEntity = producerEntityHome.findByPrimaryKey
                    ((ProducerPrimaryKeyImpl)producerPK);

                producerValues[i - currentPosition] =
                    producerEntity.getAttributes(attributes);
                    
                producerValues[i - currentPosition].setProducerKey(producerKey);
            }
            catch (FinderException fe)
            {
                // Just log it.
                fe.printStackTrace();
                // Do not throw remoteexception, because we want to
                // do best effort semantics. Return as many values
                // as possible.
            }
            catch(RemoteException ree)
            {
                throw new javax.ejb.EJBException(
                    "Error finding the producer"+ree);
            }
        }

        return producerValues;
    }

	/**
	 * Return an array of <CODE>ProducerValue</CODE> instances.
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
	 * @return An array of <CODE>ProducerValue</CODE> instances.
	 *
	 * @exception  RemoteException Thrown when
     * <CODE>Iterator</CODE> enounters
	 * @exception  javax.oss.IllegalArgumentException Thrown when
     * the input argument is invalid.
	 *
	 * @see javax.oss.ipb.producer.ProducerValue
     *
	 */
	public ProducerValue[] getNextProducers(int howMany)
        throws javax.oss.IllegalArgumentException
    {
        if(howMany < 0)
        {
            throw new javax.oss.IllegalArgumentException(
                "howMany parameter value is invalid: " + howMany);
        }
        
        return (ProducerValue[]) getNext(howMany);
    }

	/**
	 * Return the next set of ProducerValues as ActivityValues.
	 * @param howMany Specifies maximum number of items to be returned.
     * The implementation can decide to return less.
	 *
	 * @return An array of <CODE>ProducerValue</CODE> instances as ActivityValues.
	 *
	 * @exception  RemoteException Thrown when
     * <CODE>Iterator</CODE> enounters
	 * @exception  javax.oss.IllegalArgumentException Thrown when
     * the input argument is invalid.
	 *
	 * @see javax.oss.ipb.producer.ProducerValue
     *
	 */
    public ActivityValue[] getNextActivities(int howMany)
        throws javax.oss.IllegalArgumentException
    {
        return getNextProducers(howMany);
    }
    

    /**
     * Create the Stateful Session Bean to manage an array of ProducerValues
     * @param producerKeys Array of ProducerKeys for the ProducerValues
     *
     * @exception  CreateException Thown if the input array is invalid
     *
     */
    public void ejbCreate(
        ProducerKey [] producerKeys,
        String[] attributeNames) throws CreateException
    {
        if(producerKeys != null)
        {
            if(producerKeys.length > 0)
            {
				producerKeysList = new ArrayList();
                for(int prodIdx=0; prodIdx<producerKeys.length; ++prodIdx)
                {
                    producerKeysList.add(producerKeys[prodIdx]);
                }

                producerKeysListIterator = producerKeysList.iterator();
                this.attributes = attributeNames;

            }
        }
        else
        {
            throw new CreateException (
                "Supplied invalid arguments to ejbCreate");
        }
    }

    /**
     * Free the ProducerKey list managed by this iterator.
     */
    public void cleanup()
    {
        if(producerKeysList != null)
        {
            producerKeysList.clear();
            producerKeysList = null;
        }
        
        producerKeysListIterator = null;
        
        attributes = null;
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