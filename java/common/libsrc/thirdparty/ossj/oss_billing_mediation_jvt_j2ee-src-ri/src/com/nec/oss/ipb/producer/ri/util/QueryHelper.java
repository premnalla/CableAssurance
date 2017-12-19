
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri.util;

/**
 * Standard imports
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;


import java.rmi.*;

import javax.ejb.*;

import javax.naming.*;

import javax.rmi.PortableRemoteObject;

/**
 * OSS/J Interface imports.
 */
import javax.oss.QueryValue;
import javax.oss.ipb.query.QueryProducersByMediationCapability;
import javax.oss.ipb.producer.ProducerValueIterator;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.ProducerPrimaryKey;
import javax.oss.ipb.producer.MediationCapability;

/**
 * RI imports
 */
import com.nec.oss.ipb.producer.ri.ProducerValueImpl;
import com.nokia.oss.ossj.common.ri.ApplicationContextImpl;
import com.nec.oss.ipb.producer.ri.IPBConfig;


/**
 * RI Adapter imports
 */
import com.nec.oss.ipb.producer.ri.ProducerEntityHome;
import com.nec.oss.ipb.producer.ri.ProducerEntity;
import com.nec.oss.ipb.producer.ri.ProducerValueIteratorHome;
import com.nec.oss.ipb.producer.ri.ProducerValueIteratorImpl;
import com.nec.oss.ipb.producer.ri.ProducerValueStatefulIterator;
import com.nec.oss.ipb.producer.ri.ProducerValueIteratorBean;


/**
 * Utility class to handle the query results from the producer
 * session named queries.
 * 
 */

public class QueryHelper
{

    /**
     * Reference to the ProducerEntityHome
     */
    protected ProducerEntityHome producerEntityHome = null;

    /**
     * Reference to the ProducerValueIteratorHome
     */
    protected ProducerValueIteratorHome producerValueIteratorHome = null;

    /**
     * Constructor - does nothing
     */
    public QueryHelper()
    {
    }
    
    /**
     * Set the ProducerEntityHome reference
     * @param home ProducerEntityHome reference
     */
    public void setProducerEntityHome(
        ProducerEntityHome home)
    {
        producerEntityHome = home;
    }

    /**
     * Set the ProducerValueIteratorHome reference
     * @param home ProducerValueIteratorHome reference
     */
    public void setProducerValueIteratorHome(
        ProducerValueIteratorHome home)
    {
        producerValueIteratorHome = home;
    }
    
    /**
     * Perform a QueryProducersByMediationCapability
     * @param queryValue QueryProducersByMediationCapability instance
     * @param attributeNames Attributes to retrieve. If null or empty, retrieve
     * all attributes
     * @return ProducerValueIterator managing the ProducerValues that match
     * the query
     * @exception javax.oss.IllegalArgumentException Thrown if the input args
     * are invalid
     * @exception RemoteException Thrown if a system error occurs
     * @exception NamingException Thrown if a naming error occurs
     * @exception FinderException Thrown if a finder error occurs
     * @exception CreateException Thrown if a result creation error occurs
     */
    public ProducerValueIterator processProducerQueryResults(
        QueryValue queryValue,
        String[] attributeNames)
        throws javax.oss.IllegalArgumentException,
            RemoteException, NamingException,
            FinderException, CreateException
    {
        boolean returnAllProducers = false;
        Collection prodList = null;
		boolean mustMatchAll=false;
		MediationCapability[] inputMedCaps=null;
        
		// Verify the query provided
        if( (queryValue != null) &&
				(!(queryValue instanceof
                   QueryProducersByMediationCapability)))
        {
            throw new javax.oss.IllegalArgumentException(
                "Invalid query type is specified");
        }

        if(producerEntityHome == null)
        {
            throw new RemoteException(
                "ProducerEntityHome is not located");
        }
        
        if(producerValueIteratorHome == null)
        {
            throw new RemoteException(
                "ProducerEntityHome is not located");
        }
        
		// Get all the Producers
		prodList = producerEntityHome.findAllProducers();

        if ((prodList == null) ||
            (prodList.isEmpty()))
        {
            // We didn't get any exception. But, we got zero length
            // results. So, just return an empty iterator.
        	ProducerValueStatefulIterator prodValBeanIter =
				producerValueIteratorHome.create( new ProducerKey[0], 
													new String [0]);
			ProducerValueIteratorImpl prodValIter = 
										new ProducerValueIteratorImpl();
			prodValIter.setIterBean(prodValBeanIter);

        	return (prodValIter);
        }

		// If there was a query specified, then get the information
		// from the query
        if( queryValue != null )
        {
			// If there are no MediationCapabilities specified, then assume
			// that all producers are to be returned
			QueryProducersByMediationCapability medCapQuery = 
						(QueryProducersByMediationCapability) queryValue;

			// Try to get the MediationCapabilities, if they are set
			try
			{
			
				inputMedCaps= medCapQuery.getMediationCapabilityValues();
			}

			// The MediationCapabilities aren't set, so we will assume that
			// they should be null
			catch (java.lang.IllegalStateException ex)
			{
				inputMedCaps=null;
			}

			// If there are no MediationCapabilities specified we have to
			// return all
       		if((inputMedCaps == null) || (inputMedCaps.length <= 0))
       		{
           		returnAllProducers = true;
       		}

        	// Else, check and see if we are to match any or all of the 
			// MediationCapabilities
			else if(medCapQuery.getJoinOperand() == 
						QueryProducersByMediationCapability.JOIN_OPERAND_AND)
			{
				mustMatchAll=true;
			}
        }

        // Else, we are just going to return all producers
        else
        {
            returnAllProducers = true;
        }


		// If this isn't supposed to return all of the producers, then 
		// each producer must be checked for a match
        ProducerKey[] producerKeys = null;
		if(!returnAllProducers)
		{
			ArrayList aList = new ArrayList();
        	Iterator prodIter = prodList.iterator();
			MediationCapability[] querMedCaps=null;
        	while(prodIter.hasNext())
        	{
				try
				{
            		ProducerEntity prodEnt =((ProducerEntity)
								PortableRemoteObject.narrow(prodIter.next(), 
								ProducerEntity.class));

					ProducerValue prodValue =
                                    prodEnt.getAttributes(attributeNames);

					querMedCaps=prodValue.getMediationCapabilityValues();

					// Loop through the lists, determining how the match fits
					int numQuer=querMedCaps.length;
					int numInput=inputMedCaps.length;

					boolean match=false;
					for(int iIdx=0; iIdx < numInput; ++iIdx)
					{
						match=false;
						for(int qIdx=0; qIdx < numQuer; ++qIdx)
						{
							// If the queried MediationCapability matches,
							// see if we are supposed to continue
							if(inputMedCaps[iIdx].equals(querMedCaps[qIdx]))
							{
								match=true;
								break;
							}

							// Else, if they didn't match, see if we were
							// supposed to match all or not. If we had to
							// match all, then we are done here.
							else
							{
							}
						}

						// If we were supposed to match all and we didn't find
						// this one, break
						if((!match) && (mustMatchAll))
						{
							break;
						}

						// Else, if we were only supposed to match the 
						// first and we found a match, we can break
						else if ((match) && (!mustMatchAll))
						{
							break;
						}
					}

					// Now, if we got here with a match == true, then
					// include this producer.
					if(match)
					{
						ProducerKey tmpPKey = 
							IPBConfig.getInstance().refreshProducerKey(
													prodValue.getProducerKey());
						
						aList.add(tmpPKey);
					}
				}
				catch (Exception ex)
				{
					throw new RemoteException("Error on getting producers:" + ex);
				}
			}

			// Now, convert the list into an array
			producerKeys = 
				(ProducerKey[]) aList.toArray(new ProducerKey[aList.size()]);
		}

		// Else, just return all Producers
		else
		{
        	// Convert the collection into an array and pass it to the
        	// ProducerValueIterator to work on.
        	producerKeys = new ProducerKey[prodList.size()];

        	Iterator prodIter = prodList.iterator();
        	while(prodIter.hasNext())
        	for(int index=0; prodIter.hasNext(); index++)
        	{
            	ProducerEntity pEnt =    ((ProducerEntity)
                 	PortableRemoteObject.narrow(
                     	prodIter.next(),
                     	ProducerEntity.class));
            
            	ProducerKey pKey = new ProducerValueImpl().makeProducerKey();
            	ProducerPrimaryKey primKey = pKey.makeProducerPrimaryKey();
            	primKey.setActivityId(pEnt.getActivityId());
            	primKey.setProducerType(pEnt.getProducerType());
            	pKey.setProducerPrimaryKey(primKey);

				pKey=IPBConfig.getInstance().refreshProducerKey(pKey);

            	producerKeys[index] = pKey;
        	}
       	}

        ProducerValueStatefulIterator prodValBeanIter =
				producerValueIteratorHome.create(producerKeys, attributeNames);
		 
		ProducerValueIteratorImpl prodValIter = new ProducerValueIteratorImpl();
		prodValIter.setIterBean(prodValBeanIter);

        return (prodValIter);
    }
}

