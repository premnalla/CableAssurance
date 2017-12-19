 
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;


/**
 * Standard imports
 */
import javax.ejb.EntityContext;
import java.util.Date;

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.MediationCapability;
import javax.oss.cfi.activity.ExecutionStatus;
import javax.oss.cfi.activity.ActivityControlParams;
import javax.oss.cfi.activity.ActivityExecParams;
import javax.oss.cfi.activity.ActivityReportParams;
import javax.oss.cfi.activity.SubscriptionParams;
import javax.oss.cfi.activity.Schedule;


/**
 * RI Adapter imports.
 */
import com.nec.oss.ipb.producer.ri.ProducerValueImpl;


/**
 * A representation of javax.oss.ipb.producer.ProducerValue object
 * in the backend as a ManagedEntity.
 * <p>
 * This implementation uses the containment managed persistence to store
 * the entity value and its attributes.
 *
 * @see com.nec.oss.ipb.producer.ri.ProducerEntity
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public abstract class ProducerEntityBean
implements javax.ejb.EntityBean
{
    /** --------------------------------------------------
     * Member fields.
     * ------------------------------------------------*/
    /**
     * The entity's context. This is required as a part of the
     * EJB implmentation
     */
    protected EntityContext ctx;

    /**
     * Abstract methods to define the data in methods.
     */

    /**
     * The Producer's well known name.
     */
    public abstract String getActivityName();
    public abstract void setActivityName(String producerName);

    public abstract String getActivityId();
    public abstract void setActivityId(String activityId);

    public abstract String getProducerType();
    public abstract void setProducerType(String producerType);

    
    
    public abstract MediationCapability[] getMediationCapabilityValues();
    
    public abstract void
    setMediationCapabilityValues(MediationCapability[] medCaps);
 
    public abstract int getControlState();
    public abstract void setControlState(int controlState);

    public abstract int getExecutionStatus();
    public abstract void setExecutionStatus(int executionStatus);

    public abstract ActivityControlParams getActivityControlParams();
    public abstract void setActivityControlParams(
                        ActivityControlParams activityControlParams);

    public abstract ActivityExecParams getActivityExecParams();
    public abstract void setActivityExecParams(
						ActivityExecParams activityExecParams);

    public abstract ActivityReportParams[] getActivityReportParams();
    public abstract void setActivityReportParams(
                        ActivityReportParams[] activityReportParams);

    public abstract SubscriptionParams getSubscriptionParams();
    public abstract void setSubscriptionParams(
						SubscriptionParams subscriptionParams);

    public abstract Schedule getSchedule();
    public abstract void setSchedule(Schedule schedule);

    /**
     * Getter and Setter methods for ProducerValue representation.
     */
    
    /**
     * Gets a ProducerValue object with only the specified attributes
     * populated.
     * @param requestedAttributes List of attributes to retrieve. If null
     * or empty, all attributes are retrieved.
     * @return ProducerValue containing the requested attributes.
     */
    public ProducerValue getAttributes(String[] requestedAttributes)
    {
        ProducerValueImpl producerValue = new ProducerValueImpl();

//        producerValue.setLastModifiedOnServer(getLastModified());
        producerValue.setLastModifiedOnServer(new Date());

        if ( (requestedAttributes == null) ||
             ( requestedAttributes.length==0) )
        {
            requestedAttributes = producerValue.getAttributeNames();
        }

        // first, set the key, so that the producerValueImpl
        // knows about its type. This is always set.
        /**
         * Build the producer's key value.
         */
        ProducerKey keyImpl = producerValue.makeProducerKey();
        
        ProducerPrimaryKey primKeyImpl = keyImpl.makeProducerPrimaryKey();
        
        primKeyImpl.setActivityId(getActivityId());
        primKeyImpl.setProducerType(getProducerType());

        keyImpl.setProducerPrimaryKey(primKeyImpl);

        producerValue.setProducerKey(keyImpl);
  
        for (int i = 0; i < requestedAttributes.length ; ++i )
        {
            try
            {
                if(requestedAttributes[i].equals(ProducerValue.ACTIVITY_NAME))
                {
                    if(getActivityName() != null)
                    {
                        producerValue.setActivityName(
                            getActivityName());
                    }
                }
                
                if(requestedAttributes[i].equals(
                    ProducerValue.MEDIATION_CAPABILITIES))
                {
                    // check if the attribute was set, before it is set.
                    if(getMediationCapabilityValues() != null)
                    {
                        producerValue.setMediationCapabilityValues(
                            getMediationCapabilityValues());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.CONTROL_STATE))
                {
					int tempVal = getControlState();
					{
                        producerValue.setControlState(getControlState());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.EXECUTION_STATUS))
                {
                    // check if the attribute was set, before it is provided
					int execStat = getExecutionStatus();
       				if( (execStat == 
							ExecutionStatus.ACTIVITY_EXECUTION_STATUS_FAILED) ||
          				(execStat == 
				   ExecutionStatus.ACTIVITY_EXECUTION_STATUS_PARTIALLY_FAILED) ||
            			(execStat == 
				   ExecutionStatus.ACTIVITY_EXECUTION_STATUS_NORMALLY_COMPLETED) )
					{
                        producerValue.setExecutionStatus(getExecutionStatus());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.SCHEDULE))
                {
                    // check if the attribute was set, before it is set.
                    if(getSchedule() != null)
                    {
                        producerValue.setSchedule(getSchedule());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.ACTIVITY_CONTROL_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(getActivityControlParams() != null)
                    {
                        producerValue.setActivityControlParams(
												getActivityControlParams());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.ACTIVITY_EXEC_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(getActivityExecParams() != null)
                    {
                        producerValue.setActivityExecParams(
												getActivityExecParams());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.ACTIVITY_REPORT_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(getActivityReportParams() != null)
                    {
                        producerValue.setActivityReportParams(
													getActivityReportParams());
                    }
                }

                if(requestedAttributes[i].equals(
                    ProducerValue.SUBSCRIPTION_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(getSubscriptionParams() != null)
                    {
                        producerValue.setSubscriptionParams(
													getSubscriptionParams());
                    }
                }
                
            }
            
            catch(java.lang.IllegalStateException e1)
            {
                // do nothing. the parameter value will not be filled in.
                e1.printStackTrace();
            }
            catch(java.lang.IllegalArgumentException e2)
            {
                // do nothing. the parameter value will not be filled in.
                e2.printStackTrace();
            }
            catch(Exception e3)
            {
                e3.printStackTrace();
                // Some other error. break out.
                break;
            
            }
        }
        
    
//        producerValue.cleanAttributes();
        return producerValue;
    }
    
    /**
     * Sets the specified attributes taken from ProducerValue object.
     * @param attributes List of attributes to set. If null
     * or empty, all attributes in the ProducerValue are set.
     * @param value ProducerValue containing the attribute values to set
     * @exception javax.ejb.EJBException Thrown if an error occurs setting
     * the values
     */
    public void setAttributes(
        String[] attributes,
        ProducerValue value)
		throws javax.ejb.EJBException
    {
		// Verify the input args
		if(attributes == null) 
		{
        	throw new javax.ejb.EJBException("Input attributes is null");
		}

		if(value == null) 
		{
        	throw new javax.ejb.EJBException("Input ProducerValue is null");
		}
	
		// For each name in the input list, step through and set the
		// appropriate arguments
		int numAttr=attributes.length;

		for(int attrIdx=0; attrIdx<numAttr; ++attrIdx)
		{
            try
            {
                if(attributes[attrIdx].equals(ProducerValue.ACTIVITY_NAME))
                {
                    if(value.getActivityName() != null)
                    {
                        setActivityName(value.getActivityName());
                    }
                }
                
                if(attributes[attrIdx].equals(
                    ProducerValue.MEDIATION_CAPABILITIES))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getMediationCapabilityValues() != null)
                    {
                        setMediationCapabilityValues(
									value.getMediationCapabilityValues());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.CONTROL_STATE))
                {
					int tempVal = getControlState();
					{
                        setControlState(value.getControlState());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.EXECUTION_STATUS))
                {
                    // check if the attribute was set, before it is provided
					int execStat = getExecutionStatus();
       				if( (execStat == 
							ExecutionStatus.ACTIVITY_EXECUTION_STATUS_FAILED) ||
          				(execStat == 
				   ExecutionStatus.ACTIVITY_EXECUTION_STATUS_PARTIALLY_FAILED) ||
            			(execStat == 
				   ExecutionStatus.ACTIVITY_EXECUTION_STATUS_NORMALLY_COMPLETED) )
					{
                        setExecutionStatus(value.getExecutionStatus());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.SCHEDULE))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getSchedule() != null)
                    {
                        setSchedule(value.getSchedule());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.ACTIVITY_CONTROL_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getActivityControlParams() != null)
                    {
                        setActivityControlParams(
										value.getActivityControlParams());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.ACTIVITY_EXEC_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getActivityExecParams() != null)
                    {
                        setActivityExecParams( 
											value.getActivityExecParams());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.ACTIVITY_REPORT_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getActivityReportParams() != null)
                    {
                        setActivityReportParams(
											value.getActivityReportParams());
                    }
                }

                if(attributes[attrIdx].equals(
                    ProducerValue.SUBSCRIPTION_PARAMS))
                {
                    // check if the attribute was set, before it is set.
                    if(value.getSubscriptionParams() != null)
                    {
                        setSubscriptionParams(
											value.getSubscriptionParams());
                    }
                }
			}
			catch (Exception ex)
			{
        		throw new javax.ejb.EJBException(
						"Error setting attribute:" + attributes[attrIdx]);
			}
		}
    }

    /**
     * Sets all the attributes taken from ProducerValue object.
     * @param value ProducerValue containing all the attribute values to set
     * @exception javax.ejb.EJBException Thrown if an error occurs setting
     * the values
     */
    public void setAttributes(ProducerValue value)
        throws javax.ejb.EJBException
    {
		// Get the list of attributes from the producer value that are
		// populated
        ProducerValueImpl producerValue = (ProducerValueImpl) value;
		String[] populatedAttr=producerValue.getPopulatedAttributeNames();

		setAttributes(populatedAttr, value);

        return;
    }
    
    /**
     * Only dirty attributes from ProducerValue are updated.
     * Returns attributes which really changed.
     * @param value ProducerValue containing the dirty attribute values to set
     * @exception javax.ejb.EJBException Thrown if an error occurs setting
     * the values
     */
    public String[] updateAttributes(ProducerValue value)
        throws javax.ejb.EJBException
    {
		// Get the list of attributes from the producer value that are dirty
        ProducerValueImpl producerValue = (ProducerValueImpl) value;
		String[] dirtyAttrs = producerValue.getDirtyAttributeNames();

		// Call the method to set the specified attribute names
		setAttributes(dirtyAttrs, value);

		return(dirtyAttrs);
    }
    
    /**
     * EJB Implementation methods. These are required.
     */

    /**
     * This is the initialization method that corresponds to
     * the create() method in the home interface.
     * When the client calls the home object's create()
     * method, the home object then calls this ejbCreate() method.
     * The bean's fields are initialized with the arguments
     * passed from the client, so that
     * the container can create the corresponding database entries
     * in the subclass after this method completes.
     *
     * @param producerKey Key value for the producer to create.
     * @param producerValue ProducerValue containing the attribute values to set
     * @exception javax.ejb.Create Thrown if an error occurs creating the producer
     */
    public ProducerPrimaryKeyImpl ejbCreate(
        ProducerKey producerKey,
        ProducerValue producerValue)
        throws javax.ejb.CreateException
    {
        ProducerPrimaryKey primaryKey = null;
        
        try
        {
            // Extract the priamry key from the producer key object.
            primaryKey = producerKey.getProducerPrimaryKey();
            
            setActivityId(primaryKey.getActivityId());

            setProducerType(primaryKey.getProducerType());

            if(producerValue.isPopulated(
                ProducerValue.ACTIVITY_NAME))
            {
                // Set the data.
                setActivityName(producerValue.getActivityName());
            }

			// Set capabilities
            // check if the attribute is first present.
            if(producerValue.isPopulated(
                ProducerValue.MEDIATION_CAPABILITIES))
            {
                setMediationCapabilityValues(
                    producerValue.getMediationCapabilityValues());
            }

			// Set controlState if it is populated.
            if(producerValue.isPopulated(
                ProducerValue.CONTROL_STATE))
            {
                setControlState(producerValue.getControlState());
            }

			// Set executionStatus, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.EXECUTION_STATUS))
            {
                setExecutionStatus(producerValue.getExecutionStatus());
            }

			// Set schedule, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.SCHEDULE))
            {
                setSchedule(producerValue.getSchedule());
            }

			// Set activityControlParams, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.ACTIVITY_CONTROL_PARAMS))
            {
                setActivityControlParams(
									producerValue.getActivityControlParams());
            }

			// Set activityExecParams, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.ACTIVITY_EXEC_PARAMS))
            {
                setActivityExecParams(producerValue.getActivityExecParams());
            }

			// Set activityReportParams, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.ACTIVITY_REPORT_PARAMS))
            {
                setActivityReportParams(
									producerValue.getActivityReportParams());
            }

			// Set subscriptionParams, if it is populated
            if(producerValue.isPopulated(
                ProducerValue.SUBSCRIPTION_PARAMS))
            {
                setSubscriptionParams(producerValue.getSubscriptionParams());
            }
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }


    /**
     * Default no-args constructor
     */
    public ProducerEntityBean()
    {
        // Do nothing for now
    }


    /**
     * Called by container. Implementation can require needed resources.
     * Does nothing for now.
     */
    public void ejbActivate()
    {
        // Do nothing for now.
    }

    /**
     * EJB Container calls this method right before it removes the entity bean
     * from the database. Corresponds to when client calls home.remove().
     * Does nothing for now.
     */
    public void ejbRemove()
    {
        // Do nothing for now.
    }

    /**
     * Called by Container. Releases held resources for passivation.
     * Does nothing for now.
     */
    public void ejbPassivate()
    {
        // Do nothing for now.
    }

    /**
     * Called from the Container. Updates the entity bean instance to reflect
     * the current value stored in the database.
     * Since this is a container-managed
     * persistence it can be left blank. The EJB container
     * will automatically load the data in the subclass.
     * Does nothing for now.
     */
    public void ejbLoad()
    {
        // Do nothing for now.
    }

    
    /**
     * Called from the container. Updates the database to
     * reflect the current values of this in-memory entity bean
     * instance representation. Since this
     * is a container-managed persistence it can be left
     * blank. The EJB container will automatically save
     * the data in the subclass.
     * Does nothing for now.
     */
    public void ejbStore()
    {
        // Do nothing for now.
    }

    /**
     * Called by container. Associates this bean instance
     * with a particular context. When it is done it is
     * possible to get the environment info from
     * context.
     *
     @param ctx The entity context
     *
     */
    public void setEntityContext( EntityContext ctx )
    {

        this.ctx = ctx;
    }

    /**
     * Called by container. Remove the association of the particular context
     * environment for this bean.
     */
    public void unsetEntityContext()
    {
        this.ctx = null;
    }

    /**
     * Called after ejbCreate(). Now it is possible for the bean
     * to retrieve its EJBObject from its context, and pass it
     * as a 'this' argument.
     * Does nothing for now.
     *
     */
    public void ejbPostCreate(
        ProducerKey producerKey,
        ProducerValue producerValue)
        throws javax.ejb.CreateException
    {
        // Do nothing for now.
        return;
    }
    
    /**
     * Checks all parameters from ProducerValue against the input 
     * This is a utility method used for all of the JVTProducerSession
     * template operations.
     * @param template Input ProducerValue to compare for a match
     * @return true if match, false if different
     *
     */
    public boolean checkAllAttributesForMatch(ProducerValue template)
    {
		boolean match=true;

        if((match) && (template.isPopulated(ProducerValue.ACTIVITY_NAME)))
        {
			if(!template.getActivityName().equals(getActivityName()))
			{
				match=false;
			}
        }

        if((match) && (template.isPopulated(ProducerValue.SCHEDULE)))
        {
			if(!template.getSchedule().equals(getSchedule()))
			{
				match=false;
			}
        }

        if((match) && 
				(template.isPopulated(ProducerValue.MEDIATION_CAPABILITIES)))
        {
			MediationCapability[] tempMedCap = 
								template.getMediationCapabilityValues();

		    match=checkMediationCapabilityForMatch(tempMedCap);

        }

        if((match) && (template.isPopulated(ProducerValue.CONTROL_STATE)))
        {
			if(template.getControlState() != getControlState())
			{
				match=false;
			}
        }

        if((match) && (template.isPopulated(ProducerValue.EXECUTION_STATUS)))
        {
			if(template.getExecutionStatus() != getExecutionStatus())
			{
				match=false;
			}
        }

        if((match) && 
			(template.isPopulated(ProducerValue.ACTIVITY_CONTROL_PARAMS)))
        {
			if(!template.getActivityControlParams().equals(
											getActivityControlParams()))
			{
				match=false;
			}
        }

        if((match) && 
				(template.isPopulated(ProducerValue.ACTIVITY_EXEC_PARAMS)))
        {
			if(!template.getActivityExecParams().equals(
											getActivityExecParams()))
			{
				match=false;
			}
        }

        if((match) && 
				(template.isPopulated(ProducerValue.ACTIVITY_REPORT_PARAMS)))
        {
			ActivityReportParams[] tempRptPar = 
									template.getActivityReportParams();
			ActivityReportParams[] localRptPar = getActivityReportParams();

			// If they are the same object, then they match
			if(tempRptPar == localRptPar)
			{
				match=true;
			}

			// If either one is null, they don't match (we just checked if
			// they were equal)
			else if((tempRptPar == null) || (localRptPar == null))
			{
				match=false;
			}

			else if(tempRptPar.length != localRptPar.length)
			{
				match=false;
			}

			else
			{
        		for(int firstIdx=0; firstIdx<tempRptPar.length; firstIdx++)
        		{
            		boolean found=false;
            		for(int secondIdx=0; secondIdx<localRptPar.length; 
						secondIdx++)
            		{
                		if(tempRptPar[firstIdx].equals(localRptPar[secondIdx]))
                		{
                    		found=true;
                    		break;
                		}
            		}
		
            		if(!found)
            		{
                		match=false;
            		}
        		}
        	}
        }

        if((match) && (template.isPopulated(ProducerValue.SUBSCRIPTION_PARAMS)))
        {
			if(!template.getSubscriptionParams().equals(
											getSubscriptionParams()))
			{
				match=false;
			}
        }

		return(match);
    }


    /**
     * Checks MediationCapability from ProducerValue against the input 
     * This is a utility method used for all of the JVTProducerSession
     * template operations.
     * @return true if match, false if different
     */

    public boolean checkMediationCapabilityForMatch(
                                        MediationCapability[] template)
    {
		boolean match=true;
		MediationCapability[] localMedCap = getMediationCapabilityValues();

		// If they are the same object, then they match
		if(template == localMedCap)
		{
			return true;
		}

		// If either one is null, they don't match (we just checked if
		// they were equal)
		if((template == null) || (localMedCap == null))
		{
			return false;
		}

		// Else if the lengths aren't the same, they don't match
		if(template.length != localMedCap.length)
		{
			return false;
		}

		// Else, lets check the contents
		else
		{
        	for(int firstIdx=0; firstIdx<template.length; firstIdx++)
        	{
           		boolean found=false;
           		for(int secondIdx=0; secondIdx<localMedCap.length; 
					secondIdx++)
           		{
               		if(template[firstIdx].equals(localMedCap[secondIdx]))
               		{
                   		found=true;
                   		break;
               		}
           		}
	
           		if(!found)
           		{
               		match=false;
           		}
       		}
		}
		return match;
    }
}
