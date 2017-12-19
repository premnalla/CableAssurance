
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 1.1
 */

/**
 * Spec imports.
 */
import javax.oss.cfi.activity.ActivityControlParams;


/**
 * This is the base interface for representing the controlling aspects of
 * a Activity that utilizes a schedule.
 * <ul> The following parameters constitute a ActivityControlParams definitions:
 * <LI> OneShot Capability - Tells whether this Activity can be fired independently. This is a useful concept, if the a pre-created Activity defintion can also be used ad-hoc querying.
 * <li> overlapAllowed - Tells whether a new run can be started, if the previous run is not yet finished.
 * <li> failureTolerance - Tell how to proceed if there are intermediate failures. Works also with Best Effort Semantics.
 * </ul>
 *
 */

public class ActivityControlParamsImpl
implements ActivityControlParams
{
    /**
     * Indicates whether oneShot capability is supported or not.
     * Defaults to true, meaning it is supported.
     */
    private boolean oneShotSupported = true;

    /**
     * Indicates if overlap is allowed for multiple runs.
     * Defaults to false, meaning it is not allowed.
     */
    private boolean overlapAllowed;

    /**
     * Count of how many failures to tolerate before suspending the activity.
     * Defaults to zero, indicating the first failure causes it to stop.
     */
    private int failureToleranceLimit =
    ActivityControlParams.FAILURE_TOLERANCE_UNLIMITED;

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */

    public Object clone()
    {
        Object clone = null;
        try
        {
            clone = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
            
        }

        return clone;
    }
    
    
    /**
     * Indicates whether this schedule can be used for one shot firing.
     * For certain Activities to run, there may be some pre-conditions
     * that need to be satisfied.
     *
     * @return flag indicating if one-shot is supported
     */
    public boolean supportsOneShot()
    {
        return oneShotSupported;
        
    }
    
    /**
     * Set whether this schedule can be used for one shot firing.
     * For certain Activities to run, there may be some pre-conditions
     * that need to be satisfied.
     *
     * @param  oneShotFlag Flag indicating if one-shot is supported
     */
    public void setSupportsOneShot(
        boolean oneShotFlag)
    {
        this.oneShotSupported = oneShotFlag;
    }
    

    /**
     * If the successive runs of a Activity, defined according to its schedule
     * cause overlap at runtime, this attribute defines the expected behavior.
     *
     * A value of 'false' indicates that if the current run is no
     * yet finished before it is the time for next run, the next run must
     * be skipped until the current run is completed.
     *
     * A value of 'true' indicates that it is okay to fire the next
     * run, regardless of the current run's completion.
     *
     * * @return Flag indicating if overlap is allowed
     */
    public boolean isOverlapAllowed()
    {
        return overlapAllowed;
    }
    
    /**
     * Set the value indicating if overlap is allowed
     *
     * @param flag Flag indicating if overlap is allowed
     */
    public void setOverlapAllowed(
        boolean flag)
    {
        this.overlapAllowed = flag;
    }
    

    /**
     * This attribute defines the behavior of further runs of a
     * Activity upon encoutering failures.
     *
     * <UL> Upon encounter each failure, the behavior will be as follows:
     * <LI> failureToleranceLimit < 0 - Continue with successive runs. Default.
     * <LI> failureToleranceLimit = 0 - Zero tolerance. Stop after first failure.
     * <LI> failureToleranceLimit = n (n > 0) - Stop after encountering (n+1) failures.
     * </UL>
     *  @return Value of the failure tolerance limit
     */
    public int getFailureToleranceLimit()
    {
        return failureToleranceLimit;
    }
    
    /**
     * Set the value indicating if overlap is allowed
     *
     * @param failureTolerance Value of the failure tolerance limit
     */

    public void setFailureToleranceLimit(
        int failureTolerance)
    {
        if(failureToleranceLimit < FAILURE_TOLERANCE_UNLIMITED)
        {
            /**
             * Always set it to FAILURE_TOLERANCE_UNLIMITED value.
             */
            this.failureToleranceLimit = FAILURE_TOLERANCE_UNLIMITED;
        }
        else
        {
            this.failureToleranceLimit = failureTolerance;
        }
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

		if(!(other instanceof ActivityControlParamsImpl))
		{
			return false;
		}

		ActivityControlParamsImpl other1 = 
						(ActivityControlParamsImpl) other;

		if (( oneShotSupported == other1.oneShotSupported ) &&
			( overlapAllowed == other1.overlapAllowed ) &&
			( failureToleranceLimit == other1.failureToleranceLimit ) )
		{
 			return true;
		}
		else
		{
			return false;
		}
	}

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
		int hashCode=-1;

		return hashCode;
	}
}
