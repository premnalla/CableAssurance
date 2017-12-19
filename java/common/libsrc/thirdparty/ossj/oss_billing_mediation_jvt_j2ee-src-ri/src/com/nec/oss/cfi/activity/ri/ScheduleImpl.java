
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
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Spec imports.
 */
import javax.oss.cfi.activity.DailyScheduleInfo;
import javax.oss.cfi.activity.WeeklyScheduleInfo;

/**
 * Implementation for javax.oss.cfi.activity.Schedule interface.
 * The schedule is active as soon as the start time - if supplied - is
 * reached. The schedule remains active until the stop time - if supplied - is
 * reached. If no stop time is specified the schedule will active indefinitely.
 * The time frame defined by the schedule may contain one or more intervals.
 * These intervals may repeat on a daily and/or weekly basis and specify the
 * time periods during which the schedule is active. A daily schedule includes
 * a start time and end time, which lie between 00.00 and 24.00 hours. If
 * daily schedule is omitted, the schedule will run continuously through the
 * day. If weekly schedule is omitted the schedule will run all days of the
 * week.  Alternatively the weekly schedule will indicate which days of the
 * week the schedule will be active on.
 *
 */
public class ScheduleImpl
implements javax.oss.cfi.activity.Schedule
{

    /**
     * Members.
     */
    private TimeZone timeZone = null;

    WeeklyScheduleInfo weeklyScheduleInfo = null;
    DailyScheduleInfo dailyScheduleInfo = null;
    Calendar startTime = null;
    Calendar stopTime = null;
    
    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        /**
         * Weekly schedule.
         */
        ScheduleImpl clone = null;

        try
        {
            clone = (ScheduleImpl) super.clone();

            if(timeZone != null)
            {
                clone.timeZone= (TimeZone) timeZone.clone();
            }
            if(weeklyScheduleInfo != null)
            {
                clone.weeklyScheduleInfo = (WeeklyScheduleInfo)
                    weeklyScheduleInfo.clone();
            }
            if(dailyScheduleInfo != null)
            {
                clone.dailyScheduleInfo = (DailyScheduleInfo) dailyScheduleInfo.clone();
            }
            if(startTime != null)
            {
                clone.startTime = (Calendar) startTime.clone();
            }
            if(stopTime != null)
            {
                clone.stopTime = (Calendar) stopTime.clone();
            }
            
        }

        catch ( CloneNotSupportedException e )
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
        }

        return clone;
    }

    /**
     * Based on current time of day, should this schedule have been
     * active. Note that this does not reflect the actual Job's
     * active status.
     * <p>
     * Checks if the schedule is active or not.
     * <p>
     * TODO: Need to bring in daily and weekly schedule attribute based
     * calculations to determine whether the schedule is active or not.
     *
     * <p>
     * If the current date and time is within the defined schedule this method will return
     * true. If the current date and time is outside the schedule false will be returned.
     *
     *@return boolean Returns true if the schedule is active, else false.
     */
    public boolean isActive()
    {
        boolean active = false;
        
        Calendar now;

        if(timeZone == null)
        {
            now = Calendar.getInstance();
        }
        else
        {
            now = Calendar.getInstance(timeZone);
        }
        

        if ( ((startTime == null || now.after(startTime)) &&
              (stopTime == null || now.before(stopTime))) )
        {
            active = true;
        }

        return active;
    }
    
    /**
     * Gets the time zone of the  schedule.
     *
     *@return TimeZone The time zone of the  schedule.
     * @exception java.lang.IllegalStateException Thrown if the time zone has
     * not been set.
     */
    public TimeZone getTimeZone()
        throws java.lang.IllegalStateException
    {
        if(timeZone == null)
        {
            throw new java.lang.IllegalStateException(
                "TimeZone attribute was not set");
        }
        
        return (TimeZone) timeZone.clone();
    }
    
    /**
     * Sets the time zone for the schedule.
     *
     * <p>
     * If the time zone is not set the local time zone will be used.
     *
     *@param timeZone Time zone of the schedule.
     * @exception java.lang.IllegalArgumentException Thrown if the input time
     * zone is not valid
     */
    public void setTimeZone(TimeZone timeZone)
        throws java.lang.IllegalArgumentException
    {
        if(timeZone == null)
        {
            throw new java.lang.IllegalArgumentException(
                "timeZone parameter value is not valid");
        }

        this.timeZone = timeZone;
    }
    

    /**
     * The begin time, after reaching which the scheduled Job
     * could be started. Before this time, the Job will never run.
     * If this is not set (null), then the Job is immeidately once it
     * is activated., based on whatever schedule it actually is.
     * i.e daily, weekly.
     * @return start time for this schedule
     * @exception java.lang.IllegalStateException Thrown if the start time has
     * not been set.
     */
    public Calendar getStartTime()
        throws java.lang.IllegalStateException
    {
        if(startTime == null)
        {
            throw new java.lang.IllegalStateException(
                "StartTime attribute was not set");
        }
        
        return (Calendar) startTime.clone();
    }
    
    /**
     * Sets the start time of the schedule.
     *
     * <p>
     * The start time specifies when the schedule shall start to be active.
     * If no start time is provided, the schedule is active immediately.
     *
     * <p>
     * If start time, by default, is set to null, and thus is not allowed
     * to be set to this value explicitly.
     *
     *@param startTime Start time of the schedule.
     * @exception java.lang.IllegalArgumentException Thrown if the input start
     * time is not valid
     */
    public void setStartTime(Calendar startTime)
        throws java.lang.IllegalArgumentException
    {
        if(startTime == null)
        {
            throw new java.lang.IllegalArgumentException(
                "startTime parameter value is not valid");
        }

        this.startTime = (Calendar) startTime.clone();
    }
    

    /**
     * The end time, after reaching which the scheduled Job will
     * no longer be re-started.
     * If this is not set (null), then the Job is run forever according
     * to whatever schedule it actually is.
     *
     * @return stop time for this schedule
     * @exception java.lang.IllegalStateException Thrown if the stop time has
     * not been set.
     */
    public Calendar getStopTime()
        throws java.lang.IllegalStateException
    {
        if(stopTime == null)
        {
            throw new java.lang.IllegalStateException(
                "StopTime attribute was not set");
        }
        
        return (Calendar) stopTime.clone();
    }
    
    /**
     * Sets the stop time of the schedule.
     *
     * <p>
     * The stop time specifies when the schedule shall stop. The schedule will remain active
     * until the stop time - if set - is reached. If no stop time is specified the schedule
     * will be active indefinitely.
     * <p>
     * If stop time, by default, is set to null, and thus is not allowed
     * to be set to this value explicitly.
     *
     * @param stopTime Stop time of the measurement job.
     * @exception java.lang.IllegalArgumentException Thrown if the input stop
     * time is not valid
     */
    public void setStopTime(Calendar stopTime)
        throws java.lang.IllegalArgumentException
    {
        if(stopTime == null)
        {
            throw new java.lang.IllegalArgumentException(
                "stopTime parameter value is not valid");
        }

        this.stopTime = (Calendar) stopTime.clone();
    }
    

    /**
     * Returns whether this is a daily schedule.
     * @return - true - If and only if DailyScheduleInfo is set to non-null,
     * among DailyScheduleInfo and WeeklyScheduleInfo attributes.
     * Otherwise false is returned.
     * @return Flag indicating if this is a daily schedule
     */
    public boolean isADailySchedule()
    {
        if(dailyScheduleInfo != null)
        {
            return true;
        }
        return false;
    }
    
    
    /**
     * Creates a new instance of the DailyScheduleInfo interface.
     *
     *@return DailyScheduleInfo The created object. The object is empty.
     */
    public DailyScheduleInfo makeDailyScheduleInfo()
    {
        return new DailyScheduleInfoImpl();
    }
    
    
    /**
     * Gets the daily schedule of the schedule.
     *
     *@return DailyScheduleInfo The daily schedule of the schedule
     * @exception java.lang.IllegalStateException Thrown if the daily schedule
     * information is not available
     *
     */
    public DailyScheduleInfo getDailyScheduleInfo()
        throws java.lang.IllegalStateException
    {
        if(dailyScheduleInfo != null)
        {
            return (DailyScheduleInfo) dailyScheduleInfo.clone();
        }

        throw new java.lang.IllegalStateException(
            "DailyScheduleInfo attribute value was not set");
        
    }
    

    /**
     * Sets the daily schedule of the schedule.
     *
     * <p>
     * The daily schedule specifies the time frames during the day that the schedule will
     * be active. If daily schedule is omitted, the schedule will run continuously through
     * the day.
     *
     * <p>
     * If daily schedule is null the daily schedule will be cleared.
     *
     * @param dailySchedule The daily schedule of the schedule.
     * @exception java.lang.IllegalArgumentException Thrown if the input daily
     * schedule information is not valid
     *
     */
    public void setDailyScheduleInfo(
        DailyScheduleInfo dailyScheduleInfo)
        throws java.lang.IllegalArgumentException
    {
        if(dailyScheduleInfo == null)
		{
        	this.dailyScheduleInfo = null;
        }
		else
		{
        	this.dailyScheduleInfo = (DailyScheduleInfo) dailyScheduleInfo.clone();
        }
    }

    /**
     * Returns whether this is a weekly schedule.
     * @return - true - If and only if WeeklyScheduleInfo is set to non-null,
     * among DailyScheduleInfo and WeeklyScheduleInfo attributes.
     * Otherwise false is returned.
     */
    public boolean isAWeeklySchedule()
    {
        if(weeklyScheduleInfo != null)
        {
            return true;
        }
        return false;
    }
    
    
    /**
     * Creates a new instance of the WeeklyScheduleInfo interface.
     *
     *@return WeeklyScheduleInfo The created object. The object is empty.
     */
    public WeeklyScheduleInfo makeWeeklyScheduleInfo()
    {
        return new WeeklyScheduleInfoImpl();
    }
    
    
    /**
     * Gets the weekly schedule of the schedule.
     *
     * @return WeeklyScheduleInfo The weekly schedule of the schedule
     * @exception java.lang.IllegalStateException Thrown if the weekly schedule
     * information is not available
     *
     */
    public WeeklyScheduleInfo getWeeklyScheduleInfo()
        throws java.lang.IllegalStateException
    {
        if(weeklyScheduleInfo != null)
        {
            return (WeeklyScheduleInfo) weeklyScheduleInfo.clone();
        }

        throw new java.lang.IllegalStateException(
            "WeeklyScheduleInfo attribute value was not set");
    }
    

    /**
     * Sets the weekly schedule of the schedule.
     *
     * <p>
     * The weekly schedule specifies which day of the week the schedule will be active.
     * If weekly schedule is omitted the schedule will run all days of the week.
     *
     * <p>
     * If weekly schedule is null the weekly schedule will be cleared.
     *
     *@param weekScheduleInfo The weekly schedule of the schedule.
     * @exception java.lang.IllegalArgumentException Thrown if the input weekly
     * schedule information is not valid
     */
    public void setWeeklyScheduleInfo(
        WeeklyScheduleInfo weeklyScheduleInfo)
        throws java.lang.IllegalArgumentException
    {
        if(weeklyScheduleInfo == null)
        {
        	this.weeklyScheduleInfo = null;
		}
		else
		{
        	this.weeklyScheduleInfo = (WeeklyScheduleInfo)
            	weeklyScheduleInfo.clone();
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

        if(!(other instanceof ScheduleImpl))
        {
            return false;
        }

        ScheduleImpl localOther = (ScheduleImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			if (timeZone != null)
			{
				if  (!(timeZone.equals(localOther.timeZone)))
				{
					return false;
				}

			}
			else if(timeZone != localOther.timeZone)
			{
				return false;
			}

			if(weeklyScheduleInfo != null)
			{
				if  (!(weeklyScheduleInfo.equals(localOther.weeklyScheduleInfo)))
				{
					return false;
				}
			}
			else if(weeklyScheduleInfo != localOther.weeklyScheduleInfo)
			{
				return false;
			}
                         
			if (dailyScheduleInfo != null)
			{
				if  (!(dailyScheduleInfo.equals(
									localOther.dailyScheduleInfo)))
				{
					return false;
				}

			}
			else if(dailyScheduleInfo != localOther.dailyScheduleInfo)
			{
				return false;
			}

			if (startTime != null)
			{
				if  (!(startTime.equals(localOther.startTime)))
				{
					return false;
				}
			}
			else if(startTime != localOther.startTime)
			{
				return false;
			}

			if (stopTime != null)
			{
				if  (!(stopTime.equals(localOther.stopTime)))
				{
					return false;
				}
			}
			else if(stopTime != localOther.stopTime)
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
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

        if(timeZone != null)
        {
            hashCode = timeZone.hashCode();
        }

        if(weeklyScheduleInfo != null)
        {
            hashCode ^= weeklyScheduleInfo.hashCode();
        }

        if(dailyScheduleInfo != null)
        {
            hashCode ^= dailyScheduleInfo.hashCode();
        }

        if(startTime != null)
        {
            hashCode ^= startTime.hashCode();
        }

        if(stopTime != null)
        {
            hashCode ^= stopTime.hashCode();
        }

		return hashCode;
	}

    /**
     * Provides a string representations of the Schedule
     *
     *@return String representing the Schedule
     */
    public String toString()
    {
		
		StringBuffer sb = new StringBuffer(150);

		sb.append("timeZone: " + timeZone + " | weeklyScheduleInfo: " + 
				  weeklyScheduleInfo + "| dailyScheduleInfo: " + 
				  dailyScheduleInfo + "| startTime: " + startTime + 
				  "| stopTime: " + stopTime);
        return sb.toString();
	}
}
