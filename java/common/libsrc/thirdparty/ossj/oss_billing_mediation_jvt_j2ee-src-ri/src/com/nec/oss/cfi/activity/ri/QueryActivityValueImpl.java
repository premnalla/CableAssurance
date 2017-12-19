
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports
 */

/**
 * Spec imports 
 */
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import javax.oss.cfi.activity.QueryActivityValue;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.AttributeAccessImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

/**
 * This is an implementation of
 * {@link javax.oss.cfi.activity.QueryActivityValue} interface.
 *
 */

public class QueryActivityValueImpl
extends AttributeAccessImpl
implements javax.oss.cfi.activity.QueryActivityValue
{
    private static AttributeManager queryAttributeManager;
    private static String[] noSupportedSerializerTypes = new String[0];

    private static final String[] attributeNames =
    {
        ACTIVITY_NAME,
        GRANULARITY_PERIOD,
        CONTROL_STATE,
        EXECUTION_STATUS,
        ACTIVITY_TYPE,
        ONE_SHOT_CAPABILITY,
        SCHEDULE_CATEGORY
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames =
    {
        ACTIVITY_NAME,
        GRANULARITY_PERIOD,
        CONTROL_STATE,
        EXECUTION_STATUS,
        ACTIVITY_TYPE,
        ONE_SHOT_CAPABILITY,
        SCHEDULE_CATEGORY
    };

    /**
     * Member data.
     */

    /**
     * String name of this activity.
     */
    private String activityName;

    /**
     * Granularity period of this activity.
     */
    private int granularityPeriod;
    
    /**
     * Control State of this activity
     */
    private int controlState;
    
    /**
     * Execution Status of this activity.
     */
    private int executionStatus;

    /**
     * Class of activity value type. A Java type name of the desired
     * activity class.
     */
    private String activityType;

    /**
     * Attribute to specify the desired oneShorCapapbility.
     * 
     */
    private boolean oneShotCapability;

    /**
     * Attribute to specify the desired scheduleCategory.
     */
    private int scheduleCategory;

    /**
     * Operations
     */

    /**
     * Configuration of AttributeManager and AttributeAccess
     */
    
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.attributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager()
    {
        return queryAttributeManager;
    }

    protected AttributeManager makeAttributeManager()
    {
        queryAttributeManager = new AttributeManager();
        return queryAttributeManager;
    }

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        QueryActivityValueImpl clone = null;

        try
        {
            clone = (QueryActivityValueImpl) super.clone();

        }
        
        catch ( CloneNotSupportedException e )
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
        }

        return clone;
    }

    /**
     * Compare two instances of this class.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     *
     */
    public boolean equals(
        Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof QueryActivityValueImpl))
        {
            return false;
        }

        QueryActivityValueImpl realOther =
            (QueryActivityValueImpl) other;

        if(realOther.hashCode() == hashCode())
        {
            return (
                ( (activityName == null) ?
                  (realOther.activityName == null) :
                  (activityName.equals(realOther.activityName))) &&

                ( (activityType == null) ?
                  (realOther.activityType == null) :
                  (activityType.equals(realOther.activityType))) &&

                (controlState == realOther.controlState) &&
                (executionStatus == realOther.executionStatus) &&
                (granularityPeriod == realOther.granularityPeriod) &&
                (oneShotCapability == realOther.oneShotCapability) &&
                (scheduleCategory == realOther.scheduleCategory)
                       );
        }

        return false;
    }

    /**
     * It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     * @return hashcode for this object
     */
    public int hashCode()
    {
        int hashCode = -1;

        if(activityName != null)
        {
            hashCode = activityName.hashCode();
        }

        if(activityType != null)
        {
            hashCode ^= activityType.hashCode();
        }
        
        return hashCode;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance.
     */
    public String toString()
    {
        return "activityName: " + activityName
            + ", " + "activityType: " + activityType
            + ", " + "granularityPeriod: " + granularityPeriod
            + ", " + "controlState: " + controlState
            + ", " + "executionStatus: " + executionStatus
            + ", " + "oneShotCapability: " + oneShotCapability
            + ", " + "scheduleCategory: " + scheduleCategory
            ;
    }

    /**
     * Returns an empty String Array. Always.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     * @return Empty array for all invocations
     */
    public String[] getSupportedSerializerTypes()
        throws java.lang.IllegalStateException
    {
        return noSupportedSerializerTypes;
    }
    
    /**
     * Always throws IllegalArgumentException.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     * @return New instance of Serializer
     * @exception java.lang.IllegalArgumentException Thrown every time
     */
    public Serializer makeSerializer(
        String serializerType )
        throws java.lang.IllegalArgumentException
    {
        throw new java.lang.IllegalArgumentException(
            "makeSerializer() is not supported in the " +
            RecordDescriptorImpl.class.getName() +
            "Class.");
    }

    /**
     * Get the Activity name for this query.
     *
     * @return activity name for this query
     * @exception IllegalStateException thrown if the activity name is not set
     */
    public String getActivityName()
        throws IllegalStateException
    {
        checkAttribute(ACTIVITY_NAME);
        return activityName;
    }
    
    /**
     * Set the Activity name for this query.
     *
     * @param activityName activity name for this query
     * @exception IllegalArgumentException thrown if the input activity name is
     * not valid
     */
    public void setActivityName(
        String activityName)
        throws IllegalArgumentException
    {
        if (activityName == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityName parameter value is invalid");
        }

        setDirtyAttribute(ACTIVITY_NAME);
        this.activityName = activityName;
    }
    
    
    /**
     * Get the granularity period for this query.
     *
     * @return granularity period for this query
     * @exception IllegalStateException thrown if the granularity period is not
     * set
     */
    public int getGranularityPeriod()
        throws IllegalStateException
    {
        checkAttribute(GRANULARITY_PERIOD);
        return granularityPeriod;
    }
    
    /**
     * Set the granularity period for this query.
     *
     * @param granularityPeriod granularity period for this query
     * @exception IllegalArgumentException thrown if the input granularity
     * period not valid
     */
    public void setGranularityPeriod(
        int granularityPeriod)
        throws IllegalArgumentException
    {
        setDirtyAttribute(GRANULARITY_PERIOD);
        this.granularityPeriod = granularityPeriod;
    }
    
    
    /**
     * Get the control state for this query.
     *
     * @return control state for this query
     * @exception IllegalStateException thrown if the control state is not
     * set
     */
    public int getControlState()
        throws IllegalStateException
    {
        checkAttribute(CONTROL_STATE);
        return controlState;
    }
    
    /**
     * Set the control state for this query.
     *
     * @param controlState control state for this query
     * @exception IllegalArgumentException thrown if the input control state is
     * not valid
     */
    public void setControlState(
        int controlState)
        throws IllegalArgumentException
    {
        setDirtyAttribute(CONTROL_STATE);
        this.controlState = controlState;
    }
    

    /**
     * Get the execution status for this query.
     *
     * @return execution status for this query
     * @exception IllegalStateException thrown if the execution status is not
     * set
     */
    public int getExecutionStatus()
        throws IllegalStateException
    {
        checkAttribute(EXECUTION_STATUS);
        return executionStatus;
    }
    
    /**
     * Set the execution status for this query.
     *
     * @param executionStatus execution status for this query
     * @exception IllegalArgumentException thrown if the input execution status
     * is not valid
     */
    public void setExecutionStatus(
        int executionStatus)
        throws IllegalArgumentException
    {
        setDirtyAttribute(EXECUTION_STATUS);
        this.executionStatus = executionStatus;
    }
    
    
    /**
     * Get the activity type for this query.
     *
     * @return activity type for this query
     * @exception IllegalStateException thrown if the activity type is not
     * set
     */
    public String getActivityType()
        throws IllegalStateException
    {
        checkAttribute(ACTIVITY_TYPE);
        return activityType;
    }
    
    /**
     * Set the activity type for this query.
     *
     * @param valueType activity type for this query
     * @exception IllegalArgumentException thrown if the input activity type
     * is not valid
     */
    public void setActivityType(
        String activityType)
        throws IllegalArgumentException
    {
        if (activityType == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityType parameter value is invalid");
        }

        setDirtyAttribute(ACTIVITY_TYPE);
        this.activityType = activityType;
    }

    /**
     * Get the one-shot flag value for this query.
     *
     * @return one-shot flag value for this query
     * @exception IllegalStateException thrown if the one-shot flag value is not
     * set
     */
    public boolean getOneShotCapability()
        throws IllegalStateException
    {
        checkAttribute(ONE_SHOT_CAPABILITY);
        return oneShotCapability;
    }
    

    /**
     * Set the one-shot flag value for this query.
     *
     * @param oneShotCapability one-shot flag value for this query
     * @exception IllegalArgumentException thrown if the input one-shot flag
     * value is not valid
     */
    public void setOneShotCapability(
        boolean oneShotCapability)
    {
        setDirtyAttribute(ONE_SHOT_CAPABILITY);
        this.oneShotCapability = oneShotCapability;
    }
    
    
    /**
     * Get the scheduleCategory query attribute. This is a
     * daily or weekly schedule category.
     *
     * @return schedule category for this query
     * @exception IllegalStateException thrown if the schedule category is not
     * set
     */
    public int getScheduleCategory()
        throws java.lang.IllegalStateException
    {
        checkAttribute(SCHEDULE_CATEGORY);
        return scheduleCategory;
    }
    

    /**
     * Set the scheduleCategory query attribute.
     *
     * @param scheduleCategory one-shot flag value for this query
     * @exception IllegalArgumentException thrown if the input scheduleCategory
     * value is not valid
     */
    public void setScheduleCategory(
        int scheduleCategory)
        throws java.lang.IllegalArgumentException
    {
        if( ( scheduleCategory != SCHEDULE_CATEGORY_ANY) &&
            ( scheduleCategory != SCHEDULE_CATEGORY_DAILY) &&
            ( scheduleCategory != SCHEDULE_CATEGORY_WEEKLY) )
        {
            throw new java.lang.IllegalArgumentException(
                "ScheduleCategory parameter value is invalid");
        }
        
        setDirtyAttribute(SCHEDULE_CATEGORY);
        this.scheduleCategory = scheduleCategory;
    }
}
