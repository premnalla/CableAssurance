/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.schedule;

import ossj.common.Utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.oss.cbe.schedule.DailySchedule;


/**
 * An implementation class for the <CODE>javax.oss.cbe.schedule.DailySchedule</CODE> interface.
 *
 * @see javax.oss.cbe.schedule.DailySchedule
 *
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2
 * @since September 2005
 */
public class DailyScheduleImpl implements DailySchedule {
    private java.util.Calendar[] _dailyscheduleimpl_startTimes = new GregorianCalendar[0];
    private java.util.Calendar[] _dailyscheduleimpl_stopTimes = new GregorianCalendar[0];

    /**
     * Constructs a new DailyScheduleImpl with empty attributes.
     *
     */
    public DailyScheduleImpl() {
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
    *@return boolean Returns true if the schedule is active, else false.     *
     *
    */
    public boolean isActive() {
        /* Get the clients timezone */
        if ((_dailyscheduleimpl_startTimes.length == 0) || (_dailyscheduleimpl_stopTimes.length == 0)) {
            return false;
        }

        TimeZone timezone = _dailyscheduleimpl_startTimes[0].getTimeZone();
        Calendar nowClient = Calendar.getInstance(timezone);

        int timeNowClient = (nowClient.get(Calendar.HOUR_OF_DAY) * 10000)
            + (nowClient.get(Calendar.MINUTE) * 100) + nowClient.get(Calendar.SECOND);

        boolean active = false;

        for (int i = 0; (i < _dailyscheduleimpl_startTimes.length) && !active; i++) {
            int startTime = (_dailyscheduleimpl_startTimes[i].get(Calendar.HOUR_OF_DAY) * 10000)
                + (_dailyscheduleimpl_startTimes[i].get(Calendar.MINUTE) * 100)
                + _dailyscheduleimpl_startTimes[i].get(Calendar.SECOND);

            if (timeNowClient >= startTime) {
                int stopTime = (_dailyscheduleimpl_stopTimes[i].get(Calendar.HOUR_OF_DAY) * 10000)
                    + (_dailyscheduleimpl_stopTimes[i].get(Calendar.MINUTE) * 100)
                    + _dailyscheduleimpl_stopTimes[i].get(Calendar.SECOND);

                if (timeNowClient < stopTime) {
                    active = true;
                }
            }
        }

        return active;
    }

    /**
     * Changes the startTimes to be equal to the given argument.
     *
     * This method do not allow a null argument.
     *
     * @param value the new startTimes for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad or a null argument was provided to the method.
    */
    public void setStartTimes(java.util.Calendar[] value) {
        if (value == null) {
            throw new java.lang.IllegalArgumentException();
        }

        _dailyscheduleimpl_startTimes = value;
    }

    /**
     * Returns this DailyScheduleImpl's startTimes
     *
     * @return the startTimes
     *
    */
    public java.util.Calendar[] getStartTimes() {
        return _dailyscheduleimpl_startTimes;
    }

    /**
     * Changes the stopTimes to be equal to the given argument.
     *
     * This method do not allow a null argument.
     *
     * @param value the new stopTimes for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad or a null argument was provided to the method.
    */
    public void setStopTimes(java.util.Calendar[] value) {
        if (value == null) {
            throw new java.lang.IllegalArgumentException();
        }

        _dailyscheduleimpl_stopTimes = value;
    }

    /**
     * Returns this DailyScheduleImpl's stopTimes
     *
     * @return the stopTimes
     *
    */
    public java.util.Calendar[] getStopTimes() {
        return _dailyscheduleimpl_stopTimes;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        try {
            DailySchedule val = (DailySchedule) super.clone();

            val.setStartTimes((java.util.Calendar[]) ((java.util.Calendar[]) this.getStartTimes())
                .clone());

            val.setStopTimes((java.util.Calendar[]) ((java.util.Calendar[]) this.getStopTimes())
                .clone());

            return val;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("DailyScheduleImpl: Unable to clone this object: "
                + ex.getMessage());
        }
    }

    /**
     * Checks whether two DailySchedule are equal.
     * The result is true if and only if the argument is not null
     * and is an DailySchedule object that has the arguments.
     *
     * @param value the Object to compare with this DailySchedule
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof DailySchedule)) {
            DailySchedule argVal = (DailySchedule) value;

            // if( this.isActive() != argVal.isActive()) { return false; } 
            if (!Utils.compareAttributes(getStartTimes(), argVal.getStartTimes())) {
                return false;
            }

            if (!Utils.compareAttributes(getStopTimes(), argVal.getStopTimes())) {
                return false;
            }

            return true;
        } else {
            return super.equals(value);
        }
    }
}
