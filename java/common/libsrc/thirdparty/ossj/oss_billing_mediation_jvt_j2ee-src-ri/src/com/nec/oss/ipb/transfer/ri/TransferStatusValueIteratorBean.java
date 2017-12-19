/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.transfer.ri;

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
import javax.oss.ipb.transfer.TransferStatusValue;
import javax.oss.ipb.transfer.TransferStatusKey;
import javax.oss.ipb.transfer.TransferStatusPrimaryKey;


/**
 * RI imports
 */
import com.nec.oss.ipb.transfer.ri.TransferStatusValueImpl;
import com.nec.oss.ipb.transfer.ri.TransferStatusValueIteratorHome;

/**
 * RI util imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerEntity;
import com.nec.oss.ipb.producer.ri.ProducerEntityHome;

/**
 * A javax.oss.ipb.transfer.TransferStatusValueIterator implementation
 * using a Stateful Session Bean
 */
public class TransferStatusValueIteratorBean
implements SessionBean
{
    /**
     * Member fields. These should be serialiable.
     */
    SessionContext myContext = null;
    
    int currentPosition = 0;
    List transferStatusKeysList = null;

    String[] attributes = null;


    Iterator transferStatusKeysListIterator = null;
    ProducerEntityHome producerEntityHome = null;


    /**
     * getNext is called to retrieve the next available values.
     * This throws Remote exception, as the this method is not applicable
     * for the TransferStatusValue. It is not a ManagedEntityValue.
     * 
     * @param how_many Size of the result batches returned to the client.
     * @return an array of managed entity values with a size of at
     * most howMany. The
     * array is empty when no more results are available.
     * @exception java.rmi.RemoteException
     */
	public ManagedEntityValue[] getNext(int how_many) 
    {
/*

THIS METHOD NEEDS TO BE IMPLEMENTED IF THE TRANSFER STATUS IS SUPPORTED


        if( (transferStatusKeysList == null) ||
            (transferStatusKeysList.isEmpty()))
        {
            System.err.println("### The list is emtpy! Returning");
            
            return new TransferStatusValueImpl[0];
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
                throw new RemoteException(
                    "Could not locate the Producer Entity Home");
            }
        }

        System.err.println("Located producer entity home" + producerEntityHome);

        
        // Make sure that the iterator is valid. If re-activated,
        // we need to reiniitialize it. The iterator is not a serializable
        // object. Hence, we need to do this manually.
        if(currentPosition >= transferStatusKeysList.size())
        {
            // Nothing mroe to get. 
            return new TransferStatusValueImpl[0];
        }

        if(transferStatusKeysListIterator == null)
        {
            // Means, never initialized or just re-activated.
            // Needs to reinitialize the iterator.
            transferStatusKeysListIterator = transferStatusKeysList.iterator();

            // now advance the iterator to the correct position.
            for ( int i = 0; i < currentPosition; ++i )
            {
                transferStatusKeysListIterator.next();
            }
            
        }
        
        TransferStatusValue [] transferStatusValues = new TransferStatusValue[how_many];

        ProducerEntity producerEntity;
        TransferStatusKey transferStatusKey;

        System.err.println("Received request for " + how_many + " items");
        
        for ( int i = currentPosition;
              i < (currentPosition + how_many);
              ++i )
        {
                
            // Check if there are more items left in the iterator.
            if(!transferStatusKeysListIterator.hasNext())
            {
                System.err.println("We have no more items left to return");
                
                // none are left. All items were processed.

                // Create a smaller array to return.
                TransferStatusValue [] smallerTransferStatusValues
                    = new TransferStatusValue [i - currentPosition];

                System.arraycopy(transferStatusValues, currentPosition,
                                 smallerTransferStatusValues, 0,
                                 (i - currentPosition));
                currentPosition = i;
                
                return smallerTransferStatusValues;
            }

            transferStatusKey = (TransferStatusKey) transferStatusKeysListIterator.next();

            TransferStatusPrimaryKey transferStatusPK =
                transferStatusKey.getTransferStatusPrimaryKey();
            
            System.err.println("PrimaryKey of transferStatus is: " +
                               transferStatusPK);
                
            try
            {
                System.err.println("Invoking findByPrimaryKey with " +
                                   transferStatusPK);
                
                producerEntity = producerEntityHome.findByPrimaryKey
                    ((TransferStatusPrimaryKeyImpl)transferStatusproducerPK);

                transferStatusValues[i - currentPosition] =
                    producerEntity.getAttributes(attributes);
                    
            }
            catch (FinderException fe)
            {
                // Just log it.
                fe.printStackTrace();
                // Do not throw remoteexception, because we want to
                // do best effort semantics. Return as many values
                // as possible. 
            }
        }

        return transferStatusValues;
*/
            
		TransferStatusValue[] retVal= new TransferStatusValue[0];
        return retVal;
    }

	/**
	 * Return an array of <CODE>TransferStatusValue</CODE> instances.
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
	 * @return An array of <CODE>TransferStatusValue</CODE> instances.
	 *
	 * @exception java.rmi.RemoteException Is raised when
     * <CODE>Iterator</CODE> enounters
     * processing errors or system exception occurrs.
	 *
	 * @see javax.oss.ipb.transfer.TransferStatusValue
     *
	 */    
	public TransferStatusValue[] getNextTransferStatusValues(int how_many)
        throws javax.oss.IllegalArgumentException
    {
        if(how_many < 0)
        {
            throw new javax.oss.IllegalArgumentException(
                "how_many parameter value is invalid: " + how_many);
        }
        
        return (TransferStatusValue[]) getNext(how_many);
    }


    /**
     * Create the Stateful Session Bean to manage an array of TransferStatusValue
     * objects.
     * @param transferStatusKeys Array of TransferStatusKeys to manage
     * @param attributeNames Names of the TransferStatusValue objects
     *
     * @exception  CreateException Thown if the input array is invalid
     *
     */
    public void ejbCreate(
        TransferStatusKey [] transferStatusKeys,
        String[] attributeNames) throws CreateException
    {
        
        if(transferStatusKeys != null)
        {
            if(transferStatusKeys.length > 0)
            {
                transferStatusKeysList = new ArrayList();
                for(int xferIdx=0; xferIdx<transferStatusKeys.length; ++xferIdx)
                {
                    transferStatusKeysList.add(transferStatusKeys[xferIdx]);
                }

                transferStatusKeysListIterator = transferStatusKeysList.iterator();
                this.attributes = attributeNames;

            }
            System.err.println("Received " + transferStatusKeys.length + " items");
        }
        else
        {
            throw new CreateException (
                "Supplied invalid arguments to ejbCreate");
        }
    }

    /**
     * Free the TransferStatusValue objects managed by this iterator.
     */
    public void cleanup()
    {
        if(transferStatusKeysList != null)
        {
            transferStatusKeysList.clear();
            transferStatusKeysList = null;
        }
        
        transferStatusKeysListIterator = null;
        
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