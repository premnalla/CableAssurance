//package ossj.qos.pm.util;


package ossj.qos.util;


import java.util.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class WeeklyScheduleImpl implements javax.oss.pm.util.WeeklySchedule {


  private boolean[] isDayActive = new boolean[8];
  private TimeZone timeZone = null;


  public WeeklyScheduleImpl() {
    // All days disabled by default.
    for (int i = 1; i < 8; i++)
      isDayActive[i] = false;
  }

  public Object clone() {

    WeeklyScheduleImpl clone = null;

    try {
      clone = (WeeklyScheduleImpl) super.clone();
      if(timeZone != null)
        clone.timeZone = (TimeZone) timeZone.clone();
    }
    catch(CloneNotSupportedException e) {
       Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
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
  public boolean isActive() {
    boolean dayActive = false;
    GregorianCalendar currentDateAndTime;

    if (timeZone != null)
      currentDateAndTime = new GregorianCalendar(timeZone);
    else
      currentDateAndTime = new GregorianCalendar();

    if (isDayActive[currentDateAndTime.get(Calendar.DAY_OF_WEEK)] == true)
      dayActive = true;

    return dayActive;
  }

  /**
   * Gets the time zone of the weekly schedule.
   *
   *@return TimeZone The time zone of the weekly schedule.
   */
  public java.util.TimeZone getTimeZone() {
    return (TimeZone) timeZone.clone();
  }

  /**
   * Sets the time zone for the weekly schedule.
   *
   * <p>
   * If the time zone is not set the local time zone will be used.
   *
   *@param tz Time zone of the weekly schedule.
   */
  public void setTimeZone(java.util.TimeZone tz) {
    timeZone = (TimeZone) tz.clone();
  }

  /**
   * Sets all days of the week to be active in the day of weekly schedule.
   */
  public void setAllDaysActive() {
    for (int i = 1; i < 8; i++)
      isDayActive[i] = true;
  }

  /**
   * Indicate if the day of week schedule is active on Monday.
   *
   *@return boolean Returns true if the day of week schedule is active on Monday, else it returns false.
   */
  public boolean isMondayActive() {
    return isDayActive[Calendar.MONDAY];
  }

  /**
   * Sets if Monday shall be active in the day of weekly schedule.
   *
   *@param mondayActive If true, Monday will be active in the day of weekly schedule. If false, Monday will not be active.
   */
  public void setMondayActive( boolean mondayActive ) {
    isDayActive[Calendar.MONDAY] = mondayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Tuesday.
   *
   *@return boolean Returns true if the day of week schedule is active on Tuesday, else it returns false.
   */
  public boolean isTuesdayActive() {
    return isDayActive[Calendar.TUESDAY];
  }

  /**
   * Sets if Tuesday shall be active in the day of weekly schedule.
   *
   *@param tuesdayActive If true, Tuesday will be active in the day of weekly schedule. If false, Tuesday will not be active.
   */
  public void setTuesdayActive( boolean tuesdayActive ) {
    isDayActive[Calendar.TUESDAY] = tuesdayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Wednesday.
   *
   *@return boolean Returns true if the day of week schedule is active on Wednesday, else it returns false.
   */
  public boolean isWednesdayActive() {
    return isDayActive[Calendar.WEDNESDAY];
  }

  /**
   * Sets if Wednesday shall be active in the day of weekly schedule.
   *
   *@param wednesdayActive If true, Wednesday will be active in the day of weekly schedule. If false, Wednesday will not be active.
   */
  public void setWednesdayActive( boolean wednesdayActive ) {
    isDayActive[Calendar.WEDNESDAY] = wednesdayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Thursday.
   *
   *@return boolean Returns true if the day of week schedule is active on Thursday, else it returns false.
   */
  public boolean isThursdayActive() {
    return isDayActive[Calendar.THURSDAY];
  }

  /**
   * Sets if Thursday shall be active in the day of weekly schedule.
   *
   *@param thursdayActive If true, Thursday will be active in the day of weekly schedule. If false, Thursday will not be active.
   */
  public void setThursdayActive( boolean thursdayActive ) {
    isDayActive[Calendar.THURSDAY] = thursdayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Friday.
   *
   *@return boolean Returns true if the day of week schedule is active on Friday, else it returns false.
   */
  public boolean isFridayActive() {
    return isDayActive[Calendar.FRIDAY];
  }

  /**
   * Sets if Friday shall be active in the day of weekly schedule.
   *
   *@param fridayActive If true, Friday will be active in the day of weekly schedule. If false, Friday will not be active.
   */
  public void setFridayActive( boolean fridayActive ) {
    isDayActive[Calendar.FRIDAY] = fridayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Saturday.
   *
   *@return boolean Returns true if the day of week schedule is active on Saturday, else it returns false.
   */
  public boolean isSaturdayActive() {
    return isDayActive[Calendar.SATURDAY];
  }

  /**
   * Sets if Saturday shall be active in the day of weekly schedule.
   *
   *@param saturdayActive If true, Saturday will be active in the day of weekly schedule. If false, Saturday will not be active.
   */
  public void setSaturdayActive( boolean saturdayActive ) {
    isDayActive[Calendar.SATURDAY] = saturdayActive;
  }

  /**
   * Indicate if the day of week schedule is active on Sunday.
   *
   *@return boolean Returns true if the day of week schedule is active on Sunday, else it returns false.
   */
  public boolean isSundayActive() {
    return isDayActive[Calendar.SUNDAY];
  }

  /**
   * Sets if Sunday shall be active in the day of weekly schedule.
   *
   *@param sundayActive If true, Sunday will be active in the day of weekly schedule. If false, Sunday will not be active.
   */
  public void setSundayActive( boolean sundayActive ) {
    isDayActive[Calendar.SUNDAY] = sundayActive;
  }

}
