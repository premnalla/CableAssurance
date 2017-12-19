
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports.
 */
import javax.oss.cfi.activity.ActivityExecParams;


/**
 * This class implements the ActivityExecParams,
 * which specifies how often or how many times
 * the specified Activity will be run
 * <p>
 * This implementation allows one to specify granularityPeriod for
 * activity execution (which indicates the repetition rate of runs).
 * This implementation allows optional specification of
 * maximum runs and an optional delay interval between the two runs.
 * This provides for a Activities whose duration of completion of each run is not
 * necessarily consistently the same.
 * This class also allows defining of a schedule that runs 'n' times
 * without any delay between the runs.
 *
 */
public class ActivityExecParamsImpl
implements ActivityExecParams
{
    /**
     * Indicates how many times the job must be run.
     * Defaults to {@link ActivityExecParams#MAX_ITERATIONS_NOT_SET} value.
     * A negative value indicates that it is not set.
     */
    private int maxIterations =
    ActivityExecParams.MAX_ITERATIONS_NOT_SET;

    /**
     * Indicates rate at which the activity is run, in units of seconds.
     * Defaults to {@link ActivityExecParams#GRANULARITY_PERIOD_NOT_SET} value.
     */
    private long granularityPeriod =
    ActivityExecParams.GRANULARITY_PERIOD_NOT_SET;

    /**
     * Indicates delay interval which indicates the delay between
     * each run. Defaults to
     * {@link ActivityExecParams#DELAY_INTERVAL_NOT_SET} value.
     */
    private long delayInterval =
    ActivityExecParams.DELAY_INTERVAL_NOT_SET;


    /**
     * OPERATIONS
     */

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
     * Maximum Iterations. The valid value can range from -1 (means not used)
     * thru zero (means unlimited) to any positive number.
     * <p>
     * This attribute might be used in conjuction with
     * <CODE>delayInterval</CODE> attribute defined in this interface.
     * This attribute and
     * <CODE>granularityPeriod</CODE> are mutually exclusive, meaning,
     * both are not valid simulataneously for the same ActivityValue instance.
     * <p>
     * A value of zero means the max iterations are unlimited.
     * 
     * <UL>
     * <LI> maxIterations=0 , Schedule's stopTime=null: Run the Activity forever.
     * <LI> maxIterations=n (n>0), Schedule' sstopTime=null: Run the Activity exactly 'n' times.
     * <LI> maxIterations=0, Schedule's stopTime=not-null: Run the Activity according to the (daily, weekly) schedule until stop time is reached.
     * <LI> maxIterations=n (n>0), Schedule's stopTime=not-null: Run the Activity, at the most 'n' times, OR until stop time is reached.
     * </UL>
     * @return maximum iterations value
     */
    public int getMaxIterations()
    {
        return maxIterations;
    }
    
    /**
     * Set the value of maxIterations attribute. See #getMaxIteraions
     */
    public void setMaxIterations(int maxIterations)
    {
        if(maxIterations < ActivityExecParams.MAX_ITERATIONS_NOT_SET)
        {
            this.maxIterations = ActivityExecParams.MAX_ITERATIONS_NOT_SET;
            
        }
        else
        {
            this.maxIterations = maxIterations;
        }

    }
    
    

    /**
     * This attribute represents the run rate of a Activity,
     * <UL>
     * <LI> For collection type
     * of jobs, this attribute's value indicates how often the collection job
     * is initiated.
     * <LI> For Monitoring type of jobs, this attribute value indicates
     * at what rate the monitoring activity is carried out.
     * <LI> For  Reporting type of jobs, this attribute value indicates
     * how often a report (and possibly a notification) is generated
     * for gathered data.
     *
     * Valid range is
     * between 0 to any positive number. This attribute and the
     * <CODE>delayInterval</CODE> attribute specified in this interface
     * are mutually exclusive. They are not set to valid values at the
     * same time for a given job.
     * <p>
     * <UL>
     * <LI> A value of zero or unset state,
     * indicates that this attribute is not being used.
     * This is applicable to Activities for which granularityPeriod attribute doesn't
     * not apply.
     *
     * <LI> A value n, where n > 0, indicates that the job is run every 'n'
     * seconds, synchronized from the beginning of the hour.
     *
     * </UL>
     * @return granularity period value
     */
    public long getGranularityPeriod()
    {
        return granularityPeriod;
    }
    
    /**
     * Set the granularityPeriod of the Activity.
     * @param granularityPeriod granularity period value
     * @see #getGranularityPeriod
     */
    public void setGranularityPeriod(long granularityPeriod)
    {
        if(granularityPeriod < ActivityExecParams.GRANULARITY_PERIOD_NOT_SET)
        {
            this.granularityPeriod =
                ActivityExecParams.GRANULARITY_PERIOD_NOT_SET;
            
        }
        else
        {
            this.granularityPeriod = granularityPeriod;
        }

    }
    
    
    
    
    /**
     * This attribute represents the delay between the end of one run,
     * to the beginning of next run, in units of seconds.
     * This attribute might be used in conjuction with
     * <CODE>maxIterations</CODE> attribute defined in this interface.
     * This attribute and
     * <CODE>granularityPeriod</CODE> are mutually exclusive, meaning,
     * both are not valid simulataneously for the same ActivityValue instance.
     * <p>
     * <UL>
     *
     * <LI> A value < zero indicates this value is not being used.
     * This is the default. This value is used in situations, where the
     * job once activated, never terminates by itself, unless it is
     * stopTime is reached, when it is suspended automatically by the
     * Activity.
     *
     * <LI> A value of zero indicates that there is no delay.
     * Each Activity is run immediately after another one.
     *
     * <LI> A value > 0 indicates that there should be that many seconds delay.
     * For the case of multiple runs, with no upper end on maxIterations,
     * this setting turns into a scanning rate.
     *
     * </UL>
     * @return delay iterval value
     * 
     */
    public long getDelayInterval()
    {
        return delayInterval;
    }
    
    /**
     * Set the delayInterval attribute value.
     * @param delayInterval delay interval value
     * @see #getDelayInterval
     */
    public void setDelayInterval(long delayInterval)
    {
        if(delayInterval < ActivityExecParams.DELAY_INTERVAL_NOT_SET)
        {
            this.delayInterval = ActivityExecParams.DELAY_INTERVAL_NOT_SET;
        }
        else
        {
            this.delayInterval = delayInterval;
        }

	}

    /**
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

        if(!(other instanceof ActivityExecParamsImpl))
        {
            return false;
        }

        ActivityExecParamsImpl localOther = (ActivityExecParamsImpl) other;

		if ((maxIterations == localOther.maxIterations) &&
			(granularityPeriod == localOther.granularityPeriod) &&
			(delayInterval == localOther.delayInterval))
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
