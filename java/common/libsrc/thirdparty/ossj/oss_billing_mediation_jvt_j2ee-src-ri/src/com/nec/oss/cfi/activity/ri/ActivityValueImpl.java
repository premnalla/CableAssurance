
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

/**
 * Spec. imports.
 */

import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityKey;

import javax.oss.cfi.activity.ActivityKey;
import javax.oss.cfi.activity.ActivityPrimaryKey;
import javax.oss.cfi.activity.ActivityValue;

import javax.oss.cfi.activity.ControlState;
import javax.oss.cfi.activity.ExecutionStatus;
import javax.oss.cfi.activity.ActivityControlParams;
import javax.oss.cfi.activity.ActivityExecParams;
import javax.oss.cfi.activity.ActivityReportParams;
import javax.oss.cfi.activity.SubscriptionParams;
import javax.oss.cfi.activity.Schedule;


/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityValueImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

/**
 * Implementaion of ActivityValue interface.
 * This is the base value object representation for all Activities in
 * OSS/J activity package.
 */
public class ActivityValueImpl
extends ManagedEntityValueImpl
implements ActivityValue
{

    /**
     * AttributeManager for this value object
     */
    protected static AttributeManager activityAttributeManager;

    /**
     * String array which contains all attribute names
     */
    private static final String [] activityAttributeNames =
    {
        ACTIVITY_NAME,
        CONTROL_STATE,
        EXECUTION_STATUS,
        SCHEDULE,
        ACTIVITY_CONTROL_PARAMS,
        ACTIVITY_EXEC_PARAMS,
        ACTIVITY_REPORT_PARAMS,
        SUBSCRIPTION_PARAMS
    };
    

    /**
     * String array which contains names of all settable attributes.
     */
    private static final String [] activitySettableAttributeNames =
    {
        ACTIVITY_NAME,
        SCHEDULE,
        ACTIVITY_CONTROL_PARAMS,
        ACTIVITY_EXEC_PARAMS,
        ACTIVITY_REPORT_PARAMS,
        SUBSCRIPTION_PARAMS
    };
    
    /**
     * ManagedEntityKey
     */
    protected ActivityKey activityKey;

    /**
     * Name for this ActivityValue
     */
    protected String activityName;

    /**
     * Current control state for this ActivityValue
     */
    protected int controlState;

    /**
     * Current execution status for this ActivityValue
     */
    protected int executionStatus;

    /**
     * Control parameters for this ActivityValue
     */
    protected ActivityControlParams activityControlParams;

    /**
     * Execution parameters for this ActivityValue
     */
    protected ActivityExecParams activityExecParams;

    /**
     * Set of report parameters for this ActivityValue
     */
    protected ActivityReportParams[] activityReportParams;

    /**
     * Subscription parameters for this ActivityValue
     */
    protected SubscriptionParams subscriptionParams;

    /**
     * Schedule for this ActivityValue
     */
    protected Schedule schedule;


    /**
     * Default Constructor - sets the default control state and
     * execution status 
     */
    public ActivityValueImpl()
    {
        setControlState(ControlState.ACTIVITY_CONTROL_STATUS_SUSPENDED);
        setExecutionStatus(ExecutionStatus.ACTIVITY_EXECUTION_STATUS_CLEARED);
    }

    /**
     * Add attributes to the AttributeManager
     * @param anAttributeManager AttributeManager to setup
     */
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.activityAttributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    /**
     * Configuration of attributes for AttributeManager
     * @param anAttributeManager AttributeManager to setup
     */
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.activitySettableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }

    /**
     * Get the AttributeManager for this ActivityValue
     * @return AttributeManager for this ActivityValue
     */
    protected AttributeManager getAttributeManager()
    {
        return activityAttributeManager;
    }

    /**
     * Make a new instance of AttributeManager
     * @return new instance of AttributeManager 
     */
    protected AttributeManager makeAttributeManager()
    {
        activityAttributeManager = new AttributeManager();
        return activityAttributeManager;
    }

    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
     */
    public void setManagedEntityKeyDummy(
        javax.oss.ManagedEntityKey managedEntityKeyDummy)
    {
        if (managedEntityKeyDummy instanceof ActivityKey)
        {
            super.setManagedEntityKeyDummy(managedEntityKeyDummy);
        }
    }
    
    public ManagedEntityKey getManagedEntityKey()
        throws java.lang.IllegalStateException
    {
    	checkAttribute(KEY);
        return activityKey;
    }
         
    public void setManagedEntityKey(
        ManagedEntityKey managedEntityKey) 
        throws java.lang.IllegalArgumentException
    {
    	if (managedEntityKey instanceof ActivityKey)
        {
        	setDirtyAttribute(KEY);
        	this.activityKey = (ActivityKey) managedEntityKey;
        } 
        else
        {
            throw new java.lang.IllegalArgumentException(
                "Not the correct type of key");
        }
        
    }

	/**
	 * Factory method for making an instance of
     * <CODE>ActivityKey</CODE>, an attribute of
     * this instance.
	 * 
	 * @return An new unfilled instance of <CODE>ActivityKey</CODE>.
	 *
	 * @see javax.oss.cfi.activity.ActivityKey
	 */
    public ManagedEntityKey makeManagedEntityKey()
    {
        if ( getManagedEntityKeyDummy() != null )
        {
            return super.makeManagedEntityKey() ;
        }
        
        return  new ActivityKeyImpl() ;
    }

	/**
	 * Factory method for making an instance of
     * <CODE>ActivityKey</CODE>, an attribute of
     * this instance.
	 * 
	 * @return An new unfilled instance of <CODE>ActivityKey</CODE>.
	 *
	 * @see javax.oss.cfi.activity.ActivityKey
	 */
	public ActivityKey makeActivityKey()
    {
        return (ActivityKey) makeManagedEntityKey();
    }
    

	/**
	 * Get the <CODE>Activity</CODE> entity's key attribute value.
	 * 
	 * @return The key value instance of the <CODE>Activity</CODE> Entity's key.
	 *
	 * @exception java.lang.IllegalStateException If the <CODE>ActivityKey</CODE>
     * attribute value is not yet filled in.
	 *
	 * @see javax.oss.cfi.activity.ActivityKey
	 */
	public ActivityKey getActivityKey()
        throws java.lang.IllegalStateException
    {
        return (ActivityKey) getManagedEntityKey();
    }
    
    

	/**
	 * Set the <CODE>Activity</CODE> entity's key attribute value.
	 * 
	 * @param key The key attribute value of the <CODE>Activity</CODE> Entity.
	 *
	 * @exception java.lang.IllegalArgumentException If
     * the <CODE>ActivityKey</CODE> attribute value provided as the argument to
     * this method is not well formed.
	 *
	 * @see javax.oss.cfi.activity.ActivityKey
	 */
	public void setActivityKey(ActivityKey key) 
		throws java.lang.IllegalArgumentException
    {
        setManagedEntityKey(key);
    }

    /**
     * Get the user-friendly name associated with the Activity.
     * This is mandatory.The value cannot be empty string, because this
     * attribute value is used as the JMS property selector
     * by the client to
     * receive the ActivityCreationEvent destined for it,
     * in the System's common JVTEventTopic topic.
     * <p>
     * It is the client's responsibility to make sure that this
     * activityName be unique across all of the client of the particular
     * API, at the creation of the ActivityValue.
     *
     * @return  Name for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the excecution
     * status is not set
     *
     * @see javax.oss.cfi.activity.ActivityCreationEvent
     * @see javax.oss.cfi.activity.ActivityCreationEventPropertyDescriptor
     */
    public String getActivityName()
        throws java.lang.IllegalStateException
    {
        checkAttribute(ACTIVITY_NAME);
        return activityName;
    }
    
    /**
     * Set the user-friendly name associated with the Activity.
     * This is mandatory.The value cannot be empty string, because this
     * attribute value is used as the JMS property selector
     * by the client to
     * receive the ActivityCreationEvent destined for it,
     * in the System's common JVTEventTopic topic.
     * <p>
     * It is the client's responsibility to make sure that this
     * activityName be unique across all of the client of the particular
     * API, at the creation of the ActivityValue.
     * @param  activityName Name for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the name
     * is not valid
     *
     * @see javax.oss.cfi.activity.ActivityCreationEvent
     * @see javax.oss.cfi.activity.ActivityCreationEventPropertyDescriptor
     */
    public void setActivityName(String activityName)
        throws java.lang.IllegalArgumentException
    {
        if( (activityName == null) ||
            (activityName.length() == 0) )
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityName parameter value is invalid");
        }
        setDirtyAttribute(ACTIVITY_NAME);
        this.activityName = activityName;
    }
    

    /**
     * Get the state of the Activity control. This indicates merely the
     * activation status of the Activity, not the execution status.
     *
     * <UL> Valid values are:
     * 
     * <LI> ACTIVITY_CONTROL_STATUS_SUSPENDED - The Activity has been suspended.  This is the initial/default state for all newly created Activities.
     * <LI> ACTIVITY_CONTROL_STATUS_ACTIVATED_OFF_DUTY - The Activity has been activated. This does not indicate
     * an actual execution status of the Activity. It merely states that this
     * Activity was scheduled to run and it is currently idling.
     * <LI> ACTIVITY_CONTROL_STATUS_ACTIVATED_ON_DUTY - The Activity has been activated and is currently running.
     * </UL>
     *
     * @return  Control state for this ActivityValue
     * @see javax.oss.cfi.activity.ControlState
     */
    public int getControlState()
    {
        checkAttribute(CONTROL_STATE);
        return controlState;
    }
    
    
    /**
     * Set the state of the Activity control. See {@link #getControlState()}.
     * @param controlState control state for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the control
     * state is not valid
     */
    public void setControlState(
        int controlState)
        throws java.lang.IllegalArgumentException
    {
        if( (controlState != ControlState.ACTIVITY_CONTROL_STATUS_SUSPENDED ) &&
            (controlState != ControlState.ACTIVITY_CONTROL_STATUS_ACTIVATED_OFF_DUTY ) &&
            (controlState != ControlState.ACTIVITY_CONTROL_STATUS_ACTIVATED_ON_DUTY ) )
        {
            throw new java.lang.IllegalArgumentException(
                "ControlState parameter value is not valid");
        }
        
        setDirtyAttribute(CONTROL_STATE);
        this.controlState = controlState;
    }
    

    /**
     * Get the status of the most recent execution of the Activity.
     *
     * <UL> Valid values are:
     * 
     * <LI> EXECUTION_STATUS_FAILED - The most recent execution
     * resulted in failure.
     * <LI> EXECUTION_STATUS_PARTIALLY_FAILED - The most recent execution
     * has succeeded, but there were one or more failures in the past.
     * <LI> EXECUTION_STATUS_NORMALLY_COMPLETED - The most recent execution
     * was successful, and there were no failures encountered by this Activity.
     * </UL>
     * @return  Execution status for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the execution
     * status is not set
     *
     * @see javax.oss.cfi.activity.ExecutionStatus
     */
    public int getExecutionStatus()
        throws java.lang.IllegalStateException
    {
        checkAttribute(EXECUTION_STATUS);
        return executionStatus;
    }
    

    /**
     * Set the status of the most recent execution of the Activity.
     *
     * @param executionStatus execution status for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the excecution
     * status is not valid
     */
    public void setExecutionStatus(int executionStatus)
        throws java.lang.IllegalArgumentException
    {
        if( (executionStatus != ExecutionStatus.ACTIVITY_EXECUTION_STATUS_FAILED) &&
            (executionStatus != ExecutionStatus.ACTIVITY_EXECUTION_STATUS_CLEARED) &&
            (executionStatus != ExecutionStatus.ACTIVITY_EXECUTION_STATUS_PARTIALLY_FAILED) &&
            (executionStatus != ExecutionStatus.ACTIVITY_EXECUTION_STATUS_NORMALLY_COMPLETED) )
        {
            throw new java.lang.IllegalArgumentException(
                "ExecutionStatus parameter value is invalid");
        }
        
        setDirtyAttribute(EXECUTION_STATUS);
        this.executionStatus = executionStatus;
    }
    
    

    /**
     * Make a Schedule definition for use by this Activity.
     * @param scheduleType - pass the string name of the actual schedule type
     * that is required. For example, javax.oss.cfi.activity.DailySchedule.
     *
     * @return An empty instance of Schedule.
     */
    public Schedule makeSchedule(String scheduleType)
        throws java.lang.IllegalArgumentException
    {
        
        ClassLoader cl = getClass().getClassLoader();
        Class scheduleClass = null;
        try
        {
            scheduleClass = cl.loadClass(scheduleType);

            return (Schedule) (scheduleClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown Schedule Type" + e);
        }
    }
    
    
    /**
     * Get the Schedule definition of this Activity.
     * @return  Schedule for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the Schedule
     * is not set
     */
    public Schedule getSchedule()
        throws java.lang.IllegalStateException
    {
        checkAttribute(SCHEDULE);
        return (Schedule) schedule.clone();
    }


    /**
     * Set the Schedule definition assoicated with this Activity.
     * @param schedule Schedule for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the Schedule
     * is not valid
     */
    public void setSchedule(Schedule schedule)
        throws java.lang.IllegalArgumentException
    {
        if(schedule == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Schedule parameter is invalid");
        }

        setDirtyAttribute(SCHEDULE);
        this.schedule = (Schedule) schedule.clone();
    }
    

    /**
     * Make an empty ActivityControlParams instance.
     * @param ActivityControlParamsType - pass the string name of the
     * actual ActivityControlParams type
     * that is required. For example, javax.oss.cfi.activity.ActivityControlParams.
     *
     * @return An empty instance of ActivityControlParams.
     */
    public ActivityControlParams makeActivityControlParams(
        String activityControlParamsType)
        throws java.lang.IllegalArgumentException
    {
        
        ClassLoader cl = getClass().getClassLoader();
        try
        {
            Class activityControlParamsClass =
                cl.loadClass(activityControlParamsType);
            return (ActivityControlParams)
                (activityControlParamsClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown ActivityControlParams Type" + e);
        }
    }
    
    
    /**
     * Get the control parameters of this Activity.
     */
    public ActivityControlParams getActivityControlParams()
        throws java.lang.IllegalStateException
    {
        checkAttribute(ACTIVITY_CONTROL_PARAMS);
        return (ActivityControlParams) activityControlParams.clone();
    }

    /**
     * Set the Control Parameters associated with this Activity.
     * <p>
     * If an implementation does not support handling of specified
     * activity Control
     * parameters, it must throw IllegalArgumentException at the time
     * of creation of Activity Entity.
     * <p>
     * Note that in an implementation
     * a subset of control parameters may make sense, but this may
     * not necessarily true for just any permutation of control parameters.
     * In general, the implementation must check for such a partial support
     * at the entity creation time and must throw IllegalArgumentException
     * whereever and whenever appropriate.
     * @param params ActivityControlParams for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the Activity
     * control parameters are not valid
     */
    public void setActivityControlParams(
        ActivityControlParams params)
        throws java.lang.IllegalArgumentException
    {
        if (params == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityControlParams parameter value is invalid");
        }

        setDirtyAttribute(ACTIVITY_CONTROL_PARAMS);
        this.activityControlParams = (ActivityControlParams) params.clone();
        
    }

    /**
     * Make an empty ActivityExecParams instance.
     * @param ActivityExecParamsType - pass the string name of the
     * actual ActivityExecParams type
     * that is required. For example, javax.oss.cfi.activity.ActivityExecParams.
     *
     * @return An empty instance of ActivityExecParams.
     */
    public ActivityExecParams makeActivityExecParams(
        String activityExecParamsType)
    {
        
        ClassLoader cl = getClass().getClassLoader();
        try
        {
            Class activityExecParamsClass = cl.loadClass(activityExecParamsType);
            return (ActivityExecParams) (activityExecParamsClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown ActivityExecParams Type" + e);
        }
    }
    
    

    /**
     * Get the Activity Execution Parameters, including the
     * granularityPeriod.
     * @return  Activity Execution Parameters for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the Activity
     * Execution params are not set
     */
    public ActivityExecParams getActivityExecParams()
        throws java.lang.IllegalStateException
    {
        checkAttribute(ACTIVITY_EXEC_PARAMS);
        return (ActivityExecParams) activityExecParams.clone();
    }
    

    /**
     * Set the Activity Execution Parameters, including the
     * granularityPeriod.
     * <p>
     * If an implementation does not support handling of activity execution
     * parameters, it must throw IllegalArgumentException at the time
     * of creation of Activity Entity.
     * <p>
     * Note that in an implementation
     * a subset of execution parameters may make sense, but this may
     * not necessarily true for just any permutation of execution parameters.
     * In general, the implementation must check for such a partial support
     * and throw IllegalArgumentException whereever and whenever appropriate.
     * @param params ActivityExecParams for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the Activity
     * execution parameters are not valid
     */
    public void setActivityExecParams(
        ActivityExecParams params)
        throws java.lang.IllegalArgumentException
    {
        if (params == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityExecParams parameter value is invalid");
        }

        setDirtyAttribute(ACTIVITY_EXEC_PARAMS);
        this.activityExecParams = (ActivityExecParams) params.clone();
    }
    
    /**
     * Make an empty ActivityReportParams instance.
     * @param ActivityReportParamsType - pass the string name of the
     * actual ActivityReportParams type
     * that is required. For example, javax.oss.cfi.activity.ActivityReportParams.
     *
     * @return An empty instance of ActivityReportParams.
     */
    public ActivityReportParams makeActivityReportParams(
        String activityReportParamsType)
    {
        ClassLoader cl = getClass().getClassLoader();
        try
        {
            Class activityReportParamsClass =
                cl.loadClass(activityReportParamsType);
            return (ActivityReportParams)
                (activityReportParamsClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown ActivityReportParams Type" + e);
        }
        
    }
    

    /**
     * Get the reporting parameters for this activity.
     * An array of reporting parameters is returned. It is possible
     * that one class of activity may be able to support different
     * reporting schemes simultaneously. Ultimately, the JVT Session impl. is
     * responsible for validating these values, before creating this entity.
     * @return Array of ActivityReportParams for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the Activity
     * report params are not set
     */
    public ActivityReportParams[] getActivityReportParams()
        throws java.lang.IllegalStateException
    {
        checkAttribute(ACTIVITY_REPORT_PARAMS);

        ActivityReportParams[] reportParams =
            (ActivityReportParams[])activityReportParams.clone();

        for (int i = 0; i < activityReportParams.length; ++i)
        {
            reportParams[i] =
                (ActivityReportParams) activityReportParams[i].clone();
        }

        return reportParams;
    }
    

    /**
     * Set the Activity Reporting Parameters, for this activity.
     * <p>
     * If an implementation does not support handling of specified
     * activity Reporting
     * parameters, it must throw IllegalArgumentException at the time
     * of creation of Activity Entity.
     * <p>
     * Note that in an implementation
     * a subset of reporting parameters may make sense, but this may
     * not necessarily true for just any permutation of reporting parameters.
     * In general, the implementation must check for such a partial support
     * at the entity creation time and must throw IllegalArgumentException
     * whereever and whenever appropriate.
     * @param params Array of ActivityReportParams for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the Activity
     * report parameters are not valid
     */
    public void setActivityReportParams(
        ActivityReportParams[] params)
        throws java.lang.IllegalArgumentException
    {
        if (params == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityReportParams parameter value is invalid");
        }

        this.activityReportParams = (ActivityReportParams []) params.clone();

        for(int i = 0; i < params.length; ++i)
        {
            this.activityReportParams[i] =
                (ActivityReportParams) params[i].clone();
            
        }
        
        setDirtyAttribute(ACTIVITY_REPORT_PARAMS);
    }
    
    /**
     * Make an empty SubscriptionParams instance.
     * @param subscriptionParamsType - pass the string name of the
     * actual SubscriptionParams type
     * that is required. For example, javax.oss.cfi.activity.SubscriptionParams.
     *
     * @return An empty instance of SubscriptionParams.
     */
    public SubscriptionParams makeSubscriptionParams(
        String subscriptionParamsType)
        throws java.lang.IllegalArgumentException
    {
        
        ClassLoader cl = getClass().getClassLoader();
        try
        {
            Class subscriptionParamsClass = cl.loadClass(subscriptionParamsType);
            return (SubscriptionParams) (subscriptionParamsClass.newInstance());
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown SubscriptionParams Type" + e);
        }
    }
    

    /**
     * Get the optional subscription information.
     * @see #setSubscriptionParams
     * @return SubscriptionParams for this ActivityValue
     * @exception java.lang.IllegalStateException Thrown if the Subscription
     * Params are not set
     */
    public SubscriptionParams getSubscriptionParams()
        throws java.lang.IllegalStateException
    {
        checkAttribute(SUBSCRIPTION_PARAMS);
        return (SubscriptionParams) subscriptionParams.clone();
    }

    /**
     * Set the optional subscription information. Not all activities
     * are of nature that require a subscription specification,
     * such as house-keeping
     * type activities. Hence, this field is optional.
     * <p>
     * If an implementation does not support handling of specified
     * activity subscription
     * parameters, it must throw IllegalArgumentException at the time
     * of creation of Activity Entity.
     * <p>
     * Note that in an implementation
     * a subset of subscription parameters may make sense, but this may
     * not necessarily true for just any permutation of subscription parameters.
     * In general, the implementation must check for such a partial support
     * at the entity creation time and must throw IllegalArgumentException
     * whereever and whenever appropriate.
     * @param subscription SubscriptionParams for this ActivityValue
     * @exception java.lang.IllegalArgumentException Thrown if the Subscription
     * Params are not valid
     */
    public void setSubscriptionParams(
        SubscriptionParams subscription)
        throws java.lang.IllegalArgumentException
    {
        if (subscription == null)
        {
            throw new java.lang.IllegalArgumentException(
                "SubscriptionParams parameter value is invalid");
        }

        setDirtyAttribute(SUBSCRIPTION_PARAMS);
        this.subscriptionParams = (SubscriptionParams) subscription.clone();
    }

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

		if(activityControlParams != null)
		{		
        	hashCode = activityControlParams.hashCode();
		}

        if(activityReportParams != null)
		{
           int tpLen=activityReportParams.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(activityReportParams[tpIdx] != null)
                {
                        hashCode ^= activityReportParams[tpIdx].hashCode();
                }
            }
        }

		if(activityExecParams != null)
		{		
        	hashCode ^= activityExecParams.hashCode();
		}

		if(subscriptionParams != null)
		{		
        	hashCode ^= subscriptionParams.hashCode();
		}

		if(schedule != null)
		{		
        	hashCode ^= schedule.hashCode();
		}


		if(activityKey != null)
		{		
        	hashCode = activityKey.hashCode();
		}

		return hashCode;
    }

    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {

        ActivityValueImpl clone = null;
            clone = (ActivityValueImpl) super.clone();

			if(activityKey != null)
			{
				clone.activityKey = (ActivityKey) activityKey.clone();
			}

			if(activityControlParams != null)
			{
				clone.activityControlParams = 
						(ActivityControlParams) activityControlParams.clone();
			}

			if(activityExecParams != null)
			{
				clone.activityExecParams= 
						(ActivityExecParams) activityExecParams.clone();
			}

			if(activityReportParams != null)
			{
				clone.activityReportParams = 
						(ActivityReportParams[]) activityReportParams.clone();

				for(int i = 0; i < activityReportParams.length; ++i)
				{
					clone.activityReportParams[i] = (ActivityReportParams)
						activityReportParams[i].clone();
				} 
			}

			if(subscriptionParams != null)
			{
				clone.subscriptionParams = 
						(SubscriptionParams) subscriptionParams.clone();
			}

			if(schedule != null)
			{
				clone.schedule = 
						(Schedule) schedule.clone();
			}

        return clone;
    }

    /*
     * Deep equality checking of the instance.
     *
     * @return Flag indicating if the objects were equal
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof ActivityValueImpl))
        {
            return false;
        }

        ActivityValueImpl localOther =
                            (ActivityValueImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if ((controlState != localOther.controlState)||
				(executionStatus != localOther.executionStatus))
			{
				return false;
			}

            if(activityKey != null)
            {      
                if(!activityKey.equals(localOther.activityKey))
                {
                    return false;
                }
            }
            else if (activityKey != localOther.activityKey)
            {
                return false;
            }

            if(activityName != null)
            {      
                if(!activityName.equals(localOther.activityName))
                {
                    return false;
                }
            }
            else if (activityName != localOther.activityName)
            {
                return false;
            }

            if ((activityReportParams != null) && 
							(localOther.activityReportParams != null))
            {
                if  (activityReportParams.length != localOther.activityReportParams.length)
                {
                    return false;
                }
                else
                {
                    int tpLen=activityReportParams.length;
                    for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
                    {
                        if (!activityReportParams[tpIdx].equals(localOther.activityReportParams[tpIdx]))
                        {
                            return false;
                        }
                    }
                }
            }
            else if(activityReportParams != localOther.activityReportParams)
            {
                return false;
            }


			if(activityControlParams != null)
			{		
				if(!activityControlParams.equals(localOther.activityControlParams))
				{
					return false;
				}
			}
			else if (activityControlParams != localOther.activityControlParams)
			{
				return false;
			}

			if(activityExecParams != null)
			{		
				if(!activityExecParams.equals(localOther.activityExecParams))
				{
					return false;
				}
			}
			else if (activityExecParams != localOther.activityExecParams)
			{
				return false;
			}

			if(subscriptionParams != null)
			{		
				if(!subscriptionParams.equals(localOther.subscriptionParams))
				{
					return false;
				}
			}
			else if (subscriptionParams != localOther.subscriptionParams)
			{
				return false;
			}

			if(schedule != null)
			{		
				if(!schedule.equals(localOther.schedule))
				{
					return false;
				}
			}
			else if (schedule != localOther.schedule)
			{
				return false;
			}
		}
		else
		{
			return false;
		}

		return true;
    }
}
