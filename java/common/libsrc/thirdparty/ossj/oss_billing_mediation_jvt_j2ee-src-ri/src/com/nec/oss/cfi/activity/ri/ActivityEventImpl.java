
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports
 */
import javax.oss.cfi.activity.ActivityValue;


/**
 * Base Interface implementation for the Activity events.
 * Raised by an OSS/J application, when individual Activities are
 * created or removed or when there is a change an activity's
 * Control Status.
 * <p>
 * The Factory method for making empty instances of ActivityValue are
 * not specified in this interface. It is expected that each API using
 * this interface, will have its own sub interface of ActivityValue as
 * well as a sub interface of ActivityEvent, so that the appropriate
 * makexxxValue(String typename) will be provided by that API.
 *
 *
 */
public class ActivityEventImpl
extends com.nokia.oss.ossj.common.ri.BaseEvent
implements javax.oss.cfi.activity.ActivityEvent
{
 	/**
	 * ActivityValue associated with this event
	 */
    private ActivityValue activityValue;
    
 	/**
	 * Get the value of the <CODE>Activity</CODE>.
     * 
     * @return The value object of the <CODE>Activity</CODE>.
     *
     * @throws java.lang.IllegalStateException If the
     * <CODE>activityValue</CODE> is not set for some reason.
	 */
    public ActivityValue getActivityValue()
        throws java.lang.IllegalStateException
    {
        if(activityValue == null)
        {
            throw new java.lang.IllegalStateException(
                "ActivityValue is not yet set");
        }
        
        return activityValue;
    }
    

 	/**
	 * Set the value of the <CODE>Activity</CODE>.
     * 
     * @param activityValue The value object of the <CODE>Activity</CODE>.
     *
     * @throws java.lang.IllegalArgumentException If the
     * <CODE>activityValue</CODE> is not a valid value.
	 */
    public void setActivityValue(ActivityValue activityValue)
        throws java.lang.IllegalArgumentException
    {
        if(activityValue == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityValue parameter is not valid");
            
        }

        this.activityValue = activityValue;
    }

    /**
     * Constructor - Creates new ActivityEvent empty instance.
     */
    public ActivityEventImpl()
    {
        super("");
    }

    /**
     * Deep copy of this object.
     *
     * @return deep copy of this object.
     */
    public Object clone()
    {
        Object myClone = super.clone();

        ActivityEventImpl realClone = (ActivityEventImpl) myClone;

        if(activityValue != null)
        {
            realClone.activityValue = (ActivityValue) activityValue.clone();
        }

        return myClone;
    }
    
	/**
	 * Deep equality checking of the instance.
	 * 
	 * @return flag indicating value equality
	 */
	public boolean equals(Object other)
	{
		if(other == this)
		{
			return true;
		}

		if(!(other instanceof ActivityEventImpl))
		{
			return false;
		}

		ActivityEventImpl localOther = 
						(ActivityEventImpl) other;

		if(localOther.hashCode() == hashCode())
		{

			if (activityValue != null)
			{
				if  (!(activityValue.equals(localOther.activityValue)))
				{
					return false;
				}
			}
			else if(activityValue != localOther.activityValue)
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

    /**
     * It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     * * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode = -1;

        if(activityValue != null)
        {
            hashCode = activityValue.hashCode();
        }

        return hashCode;
    }
}
