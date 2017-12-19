//package ossj.qos.pm.util;

package ossj.qos.util;

import javax.oss.pm.util.DailySchedule;
import java.util.*;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class DailyScheduleImpl implements DailySchedule {

  Calendar[] startTimes = new Calendar[0];
  Calendar[] stopTimes = new Calendar[0];

  public DailyScheduleImpl() {
  }

  public Object clone() {
    DailyScheduleImpl clone = null;

    try {
      clone = (DailyScheduleImpl) super.clone();
      if(startTimes != null) {
        clone.startTimes = (Calendar[]) startTimes.clone();
        for(int i=0; i<startTimes.length; i++)
          clone.startTimes[i] = (Calendar) startTimes[i].clone();
      }
       if(stopTimes != null) {
        clone.stopTimes = (Calendar[]) stopTimes.clone();
        for(int i=0; i<stopTimes.length; i++)
          clone.stopTimes[i] = (Calendar) stopTimes[i].clone();
      }
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
  public boolean isActive() {

    /* Get the clients timezone */
    TimeZone timezone = startTimes[0].getTimeZone();
    Calendar nowClient = Calendar.getInstance(timezone);

    int timeNowClient = nowClient.get(Calendar.HOUR_OF_DAY) * 10000 +
        nowClient.get(Calendar.MINUTE) * 100 +
        nowClient.get(Calendar.SECOND);


    boolean found = false;

    for (int i = 0; i < startTimes.length && !found; i++) {
      int startTime = startTimes[i].get(Calendar.HOUR_OF_DAY) * 10000 +
        startTimes[i].get(Calendar.MINUTE) * 100 +
        startTimes[i].get(Calendar.SECOND);

      if (timeNowClient >= startTime) {
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
   * Returns the start times when the daily schedule shall be active.
   *
   *@return java.util.Calendar[] Array of start times the daily schedule shall be active.
   */
  public java.util.Calendar[] getStartTimes() {
    return startTimes;
  }

  /**
   * Sets the start times when the daily schedule shall be active.
   *
   *@param startTimes Array of start times which defines when the daily schedule shall be active.
   */
  public void setStartTimes(java.util.Calendar[] start) {
    startTimes = start;
  }

  /**
   * Returns the stop times when the daily schedule shall not be active.
   *
   *@return java.util.Calendar[] Array of stop times the daily schedule shall not be active.
   */
  public java.util.Calendar[] getStopTimes() {
    return stopTimes;
  }

  /**
   * Sets the stop times when the daily schedule shall not be active.
   *
   *@param stopTimes Array of stop times which defines when the daily schedule shall not be active.
   */
  public void setStopTimes(java.util.Calendar[] stop) {
    stopTimes = stop;
  }

}
