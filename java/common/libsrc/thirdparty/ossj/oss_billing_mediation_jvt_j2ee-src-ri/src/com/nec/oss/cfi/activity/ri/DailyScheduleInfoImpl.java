
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
 *  This class implements the
 * DailyScheduleInfo interface which specifies the time periods
 * during the day, when the
 * schedule is active.
 */
public class DailyScheduleInfoImpl
implements javax.oss.cfi.activity.DailyScheduleInfo
{
    private Calendar[] startTimes = new Calendar[0];
    private Calendar[] stopTimes = new Calendar[0];
    
    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        DailyScheduleInfoImpl clone = null;

        try
        {
            clone = (DailyScheduleInfoImpl) super.clone();
            if(startTimes != null)
            {
                clone.startTimes = (Calendar[]) startTimes.clone();
                for(int i=0; i<startTimes.length; i++)
                {
                    clone.startTimes[i] = (Calendar) startTimes[i].clone();
                }
            
            }
        
            if(stopTimes != null)
            {
                clone.stopTimes = (Calendar[]) stopTimes.clone();
                for(int i=0; i<stopTimes.length; i++)
                {
                    clone.stopTimes[i] = (Calendar) stopTimes[i].clone();
                }
            }

        }
        catch(CloneNotSupportedException e)
        {
            clone = null;
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }

        return clone;
    }
    

    /**
     * Returns the start times when the daily schedule shall be active.
     *
     *@return Array of start times the daily schedule shall be active.
     */
    public Calendar[] getStartTimes()
    {
        Calendar [] newST = (Calendar []) startTimes.clone();

        for (int i = 0; i < startTimes.length; ++i)
        {
            newST[i] = (Calendar) startTimes[i].clone();
        }

        return newST;
    }
    
    /**
     * Sets the start times when the daily schedule shall be active.
     *
     *@param startTimes Array of start times which defines when the daily schedule shall be active.
     * @exception java.lang.IllegalArgumentException Thrown if the input array
     * is null, empty or invalid
     */
    public void setStartTimes(Calendar [] startTimes)
        throws java.lang.IllegalArgumentException
    {
        if(startTimes == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid startTimes parameter");
            
        }

        for (int i = 0; i < startTimes.length; i++)
        {
            if(startTimes[i] == null)
            {
                throw new java.lang.IllegalArgumentException(
                    "Invalid element in startTimes parameter");
            }
            
        }
        
        this.startTimes = (Calendar [] ) startTimes.clone();

        for (int i = 0; i < startTimes.length; ++i)
        {
            this.startTimes[i] = (Calendar) startTimes[i].clone();
        }
    }
    

    /**
     * Returns the stop times when the daily schedule shall not be active.
     *
     *@return Array of stop times the daily schedule shall not be active.
     */
    public Calendar[] getStopTimes()
    {
        Calendar [] newST = (Calendar []) stopTimes.clone();

        for (int i = 0; i < stopTimes.length; ++i)
        {
            newST[i] = (Calendar) stopTimes[i].clone();
        }
        return newST;
    }
    
    /**
     * Sets the stop times when the daily schedule shall not be active.
     *
     *@param stopTimes Array of stop times which defines when the daily schedule shall not be active.
     * @exception java.lang.IllegalArgumentException Thrown if the input array
     * is null, empty or invalid
     */
    public void setStopTimes(Calendar [] stopTimes)
        throws java.lang.IllegalArgumentException
    {
        if(stopTimes == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid stopTimes parameter");
        }

        for (int i = 0; i < stopTimes.length; i++)
        {
            if(stopTimes[i] == null)
            {
                throw new java.lang.IllegalArgumentException(
                    "Invalid element in stopTimes parameter");
            }
        }

        this.stopTimes = (Calendar [] ) stopTimes.clone();

        for (int i = 0; i < stopTimes.length; ++i)
        {
            this.stopTimes[i] = (Calendar) stopTimes[i].clone();
        }
    }

    /**
     * Checks if the schedule is active or not.
     *
     * <p>
     * If the current time is within the defined schedule this method will return
     * true. If the current time is outside the schedule false will be returned.
     *
     * <p><ul>
     * The check will use the following fields in the Calendar:
     * <li>Calendar.HOUR_OF_DAY,
     * <li>Calendar.MINUTE,
     * <li>Calendar.SECOND
     * </ul>
     * and the time zone. Other fields will be ignored.
     *
     *@return boolean Returns true if the schedule is active, else false.
     */
    public boolean isActive()
    {
        if( (startTimes == null) ||
            (startTimes.length == 0))
        {
            /**
             * No valid startTimes. Return false.
             */
            return false;
        }
            
        /* Get the clients timezone */
        TimeZone timezone = startTimes[0].getTimeZone();
        Calendar nowClient = Calendar.getInstance(timezone);
        
        int timeNowClient = nowClient.get(Calendar.HOUR_OF_DAY) * 10000 +
            nowClient.get(Calendar.MINUTE) * 100 +
            nowClient.get(Calendar.SECOND);
        
        boolean found = false;
        
        for (int i = 0; i < startTimes.length && !found; i++)
        {
            int startTime = startTimes[i].get(Calendar.HOUR_OF_DAY) * 10000 +
                startTimes[i].get(Calendar.MINUTE) * 100 +
                startTimes[i].get(Calendar.SECOND);
            
            if (timeNowClient >= startTime)
            {
                if( (stopTimes == null) ||
                    (startTimes.length == 0))
                {
                    /**
                     * No stop time has been set. 
                     */
                    return true;
                }

                int stopTime = stopTimes[i].get(Calendar.HOUR_OF_DAY) * 10000 +
                    stopTimes[i].get(Calendar.MINUTE) * 100 +
                    stopTimes[i].get(Calendar.SECOND);
                
                if (timeNowClient < stopTime)
                    found = true;
            }
        }
        
        return found;
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

        if(!(other instanceof DailyScheduleInfoImpl))
        {
            return false;
        }

        DailyScheduleInfoImpl localOther = (DailyScheduleInfoImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			
			if (startTimes != null)
			{
				if  (startTimes.length != 
									localOther.startTimes.length)
				{
					return false;
				}
				else
				{
					int tpLen=startTimes.length;
					for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
					{
						if (!(startTimes[tpIdx].equals(
								localOther.startTimes[tpIdx])))
						{
							return false;
						}
					}
				}
			}
			else if(startTimes != localOther.startTimes)
			{
				return false;
			}


			if (stopTimes != null)
			{
				if  (stopTimes.length != 
									localOther.stopTimes.length)
				{
					return false;
				}

				else
				{
					int tpLen=stopTimes.length;
					for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
					{
						if (!(stopTimes[tpIdx].equals(
								localOther.stopTimes[tpIdx])))
						{
							return false;
						}
					}
				}
			}
			else if(stopTimes != localOther.stopTimes)
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
		boolean hashCodeFlag = false;

        if(startTimes != null)
        {
			int tpLen=startTimes.length;
			for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
			{
				if(startTimes[tpIdx] != null)
				{
					if(hashCodeFlag)
					{
						hashCode ^= startTimes[tpIdx].hashCode();
					}
					else
					{
						hashCode = startTimes[tpIdx].hashCode();
						hashCodeFlag = true;
					}
				}
			}
        }

        if(stopTimes != null)
        {
			int tpLen=stopTimes.length;
			for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
			{
				if(stopTimes[tpIdx] != null)
				{
					if(hashCodeFlag)
					{
						hashCode ^= stopTimes[tpIdx].hashCode();
					}
					else
					{
						hashCode = stopTimes[tpIdx].hashCode();
						hashCodeFlag = true;
					}
				}
			}
        }

		return hashCode;
	}
}
