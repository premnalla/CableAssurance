//package ossj.qos.pm.util;

package ossj.qos.util;


import javax.oss.pm.util.*;

import java.util.Calendar;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class ScheduleImpl implements javax.oss.pm.util.Schedule {

  WeeklySchedule weeklySchedule = null;
  DailySchedule dailySchedule = null;
  Calendar startTime = null;
  Calendar stopTime = null;

  public ScheduleImpl() {
  }

  public Object clone() {
    ScheduleImpl clone = null;
    try {
      clone = (ScheduleImpl) super.clone();
      if(weeklySchedule != null)
        clone.weeklySchedule = (WeeklyScheduleImpl) weeklySchedule.clone();
      if(dailySchedule != null)
        clone.dailySchedule = (DailyScheduleImpl) dailySchedule.clone();
      if(startTime != null)
        clone.startTime = (Calendar) startTime.clone();
      if(stopTime != null)
        clone.stopTime = (Calendar) stopTime.clone();
    }
    catch ( CloneNotSupportedException e ) {
      Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
    }
    return clone;
  }

  /**
   * Creates a new instance of the WeeklySchedule interface.
   *
   *@return WeeklySchedule The created object. The object is empty.
   */
  public WeeklySchedule makeWeeklySchedule() {
    return new WeeklyScheduleImpl();
  }

  /**
   * Creates a new instance of the DailySchedule interface.
   *
   *@return DailySchedule The created object. The object is empty.
   */
  public DailySchedule makeDailySchedule() {
    return new DailyScheduleImpl();
  }

  /**
   * Checks if the schedule is active or not.
   *
   * <p>
   * If the current date and time is within the defined schedule this method will return
   * true. If the current date and time is outside the schedule false will be returned.
   *
   *@return boolean Returns true if the schedule is active, else false.
   */
  public boolean isActive() {
    boolean active = false;

    Calendar now = Calendar.getInstance();

    if (((startTime == null || now.after(startTime)) && (stopTime == null || now.before(stopTime))))
      active = true;
    return active;
  }

  /**
   * Returns the start time.
   *
   *@return Date Start time of the schedule or null if the start time is not set.
   */
  public java.util.Calendar getStartTime() {
    return startTime;
  }

  /**
   * Sets the start time of the schedule.
   *
   * <p>
   * The start time specifies when the schedule shall start to be active.
   * If no start time is provided, the schedule is active immediately.
   *
   * <p>
   * If start time is null the start time will be cleared.
   *
   *@param startTime Start time of the schedule.
   */
  public void setStartTime( java.util.Calendar start) {
    startTime = start;
  }

  /**
   * Returns the stop time of the schedule.
   *
   *@return Date Stop time of the schedule or null if the stop time is not set.
   */
  public java.util.Calendar getStopTime() {
    return stopTime;
  }

  /**
   * Sets the stop time of the schedule.
   *
   * <p>
   * The stop time specifies when the schedule shall stop. The schedule will remain active
   * until the stop time - if set - is reached. If no stop time is specified the schedule
   * will be active indefinitely.
   *
   * <p>
   * If stop time is null the stop time will be cleared.
   *
   *@param stopTime Stop time of the measurement job.
   */
  public void setStopTime( java.util.Calendar stop ) {
    stopTime = stop;
  }

  /**
   * Gets the weekly schedule of the schedule.
   *
   *@return WeeklySchedule The weekly schedule of the schedule or null if weekly schedule is not set.
   */
  public WeeklySchedule getWeeklySchedule() {
    return weeklySchedule;
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
   *@param weekSchedule The weekly schedule of the schedule.
   */
  public void setWeeklySchedule( WeeklySchedule week ) {
    weeklySchedule = week;
  }

  /**
   * Gets the daily schedule of the schedule.
   *
   *@return DailySchedule The daily schedule of the schedule or null if daily schedule is not set.
   */
  public DailySchedule getDailySchedule() {
    return dailySchedule;
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
   *@param dailySchedule The daily schedule of the schedule.
   */
  public void setDailySchedule( DailySchedule daily ) {
    dailySchedule = daily;
  }

}
