
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
 * WeeklyScheduleInfo interface which specifies
 * weekly time frames during
 * the schedule will be active.
 */

public class WeeklyScheduleInfoImpl
implements javax.oss.cfi.activity.WeeklyScheduleInfo
{
    private boolean[] isDayActive = new boolean[Calendar.SATURDAY + 1];
    
    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */

    public Object clone()
    {
        WeeklyScheduleInfoImpl clone = null;

        try
        {
            clone = (WeeklyScheduleInfoImpl) super.clone();
            clone.isDayActive = (boolean []) isDayActive.clone();
            
        }
        catch(CloneNotSupportedException e)
        {
            return null;
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }

        return clone;
    }
    
    /**
     * Checks if the schedule is active or not.
     *
     * <p>
     * If the current day of week is within the defined schedule this method will return
     * true. If the current day of week is outside the schedule false will be returned.
     *
     *@return boolean Returns true if the schedule is active, else false.
     */
    public boolean isActive(TimeZone timeZone)
    {
        boolean dayActive = false;
        Calendar currentDateAndTime;

        if (timeZone != null)
        {
            currentDateAndTime = Calendar.getInstance(timeZone);
        }
    
        else
        {
            currentDateAndTime = Calendar.getInstance();
        }
    

        if (isDayActive[currentDateAndTime.get(Calendar.DAY_OF_WEEK)] == true)
        {
            dayActive = true;
        }
    

        return dayActive;
    }

    /**
     * Sets all days of the week to be active in the day of weekly schedule.
     */
    public void setAllDaysActive()
    {
        for (int i = 1; i < 8; i++)
            isDayActive[i] = true;
    }

    /**
     * Indicate if the day of week schedule is active on Monday.
     *
     *@return boolean Returns true if the day of week schedule is active on Monday, else it returns false.
     */
    public boolean isMondayActive()
    {
        return isDayActive[Calendar.MONDAY];
    }

    /**
     * Sets if Monday shall be active in the day of weekly schedule.
     *
     *@param mondayActive If true, Monday will be active in the day of weekly schedule. If false, Monday will not be active.
     */
    public void setMondayActive( boolean mondayActive )
    {
        isDayActive[Calendar.MONDAY] = mondayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Tuesday.
     *
     *@return boolean Returns true if the day of week schedule is active on Tuesday, else it returns false.
     */
    public boolean isTuesdayActive()
    {
        return isDayActive[Calendar.TUESDAY];
    }

    /**
     * Sets if Tuesday shall be active in the day of weekly schedule.
     *
     *@param tuesdayActive If true, Tuesday will be active in the day of weekly schedule. If false, Tuesday will not be active.
     */
    public void setTuesdayActive( boolean tuesdayActive )
    {
        isDayActive[Calendar.TUESDAY] = tuesdayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Wednesday.
     *
     *@return boolean Returns true if the day of week schedule is active on Wednesday, else it returns false.
     */
    public boolean isWednesdayActive()
    {
        return isDayActive[Calendar.WEDNESDAY];
    }

    /**
     * Sets if Wednesday shall be active in the day of weekly schedule.
     *
     *@param wednesdayActive If true, Wednesday will be active in the day of weekly schedule. If false, Wednesday will not be active.
     */
    public void setWednesdayActive( boolean wednesdayActive )
    {
        isDayActive[Calendar.WEDNESDAY] = wednesdayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Thursday.
     *
     *@return boolean Returns true if the day of week schedule is active on Thursday, else it returns false.
     */
    public boolean isThursdayActive()
    {
        return isDayActive[Calendar.THURSDAY];
    }

    /**
     * Sets if Thursday shall be active in the day of weekly schedule.
     *
     *@param thursdayActive If true, Thursday will be active in the day of weekly schedule. If false, Thursday will not be active.
     */
    public void setThursdayActive( boolean thursdayActive )
    {
        isDayActive[Calendar.THURSDAY] = thursdayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Friday.
     *
     *@return boolean Returns true if the day of week schedule is active on Friday, else it returns false.
     */
    public boolean isFridayActive()
    {
        return isDayActive[Calendar.FRIDAY];
    }

    /**
     * Sets if Friday shall be active in the day of weekly schedule.
     *
     *@param fridayActive If true, Friday will be active in the day of weekly schedule. If false, Friday will not be active.
     */
    public void setFridayActive( boolean fridayActive )
    {
        isDayActive[Calendar.FRIDAY] = fridayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Saturday.
     *
     *@return boolean Returns true if the day of week schedule is active on Saturday, else it returns false.
     */
    public boolean isSaturdayActive()
    {
        return isDayActive[Calendar.SATURDAY];
    }

    /**
     * Sets if Saturday shall be active in the day of weekly schedule.
     *
     *@param saturdayActive If true, Saturday will be active in the day of weekly schedule. If false, Saturday will not be active.
     */
    public void setSaturdayActive( boolean saturdayActive )
    {
        isDayActive[Calendar.SATURDAY] = saturdayActive;
    }

    /**
     * Indicate if the day of week schedule is active on Sunday.
     *
     *@return boolean Returns true if the day of week schedule is active on Sunday, else it returns false.
     */
    public boolean isSundayActive()
    {
        return isDayActive[Calendar.SUNDAY];
    }

    /**
     * Sets if Sunday shall be active in the day of weekly schedule.
     *
     *@param sundayActive If true, Sunday will be active in the day of weekly schedule. If false, Sunday will not be active.
     */
    public void setSundayActive( boolean sundayActive )
    {
        isDayActive[Calendar.SUNDAY] = sundayActive;
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

        if(!(other instanceof WeeklyScheduleInfoImpl))
        {
            return false;
        }

        WeeklyScheduleInfoImpl localOther = (WeeklyScheduleInfoImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			
			if (isDayActive != null)
			{
				if  (isDayActive.length != localOther.isDayActive.length)
				{
					return false;
				}
				else
				{
					int tpLen=isDayActive.length;
					for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
					{
						if (isDayActive[tpIdx] != 
								localOther.isDayActive[tpIdx])
						{
							return false;
						}
					}
				}
			}
			else if(isDayActive != localOther.isDayActive)
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

        if(isDayActive != null)
        {
			int tpLen=isDayActive.length;
			for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
			{
				if(isDayActive[tpIdx])
				{
					hashCode ^= 1;
				}
				else
				{
					hashCode ^= 0;
				}
			}
        }
		return hashCode;
	}
}
