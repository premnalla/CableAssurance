/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.schedule;
import ossj.common.Utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.oss.cbe.schedule.WeeklySchedule;


/**
 * An implementation class for the <CODE>javax.oss.cbe.schedule.WeeklySchedule</CODE> interface.
 *
 * @see javax.oss.cbe.schedule.WeeklySchedule
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2
 * @since August 2005
 */
public class WeeklyScheduleImpl implements WeeklySchedule {
    private boolean[] isDayActive = new boolean[8];
    private TimeZone timeZone = null;

    /**
     * Creates a new WeeklyScheduleImpl object.
     */
    public WeeklyScheduleImpl() {
        // All days disabled by default.
        for (int i = 1; i < 8; i++) {
            isDayActive[i] = false;
        }
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

        if (timeZone != null) {
            currentDateAndTime = new GregorianCalendar(timeZone);
        } else {
            currentDateAndTime = new GregorianCalendar();
        }

        if (isDayActive[currentDateAndTime.get(Calendar.DAY_OF_WEEK)] == true) {
            dayActive = true;
        }

        return dayActive;
    }

    /**
     * Gets the time zone of the weekly schedule.
     *
     *@return TimeZone The time zone of the weekly schedule.
     */
    public java.util.TimeZone getTimeZone() {
        return (TimeZone) timeZone;
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
        timeZone = tz;
    }

    /**
     * Sets all days of the week to be active in the day of weekly schedule.
     */
    public void setAllDaysActive() {
        for (int i = 1; i < 8; i++) {
            isDayActive[i] = true;
        }
    }

    /**
     * Return true if the specified day is active.
     * @see java.util.Calendar
     * @param day is the integer representing the day of the week as specified in the java.util.Calendar
     */
    public boolean isActiveDay(int day){
    	return isDayActive[day];
    }

    /**
     * Return true if the specified day is active.
     * @see java.util.Calendar
     * @param day is the integer representing the day of the week as specified in the java.util.Calendar
     * @param active true to activate the given day
     */
    public void setActiveDay(int day, boolean active){
    	isDayActive[day]=active;
    }

    /**
     * Return the array of activity for the week.
     * @see java.util.Calendar
     * The index of the array corresponds to the day of the week as specified in the java.util.Calendar
     * @return the activity array
     */
    public boolean[] getActiveDays(){
    	return isDayActive;
    }

    /**
     * Sets the activity for the week.
     * @see java.util.Calendar
     * The index of the array corresponds to the day of the week as specified in the java.util.Calendar
     * @param activities the activity array
     */
    public void setActiveDays(boolean[] activities){
    	isDayActive = activities;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        WeeklyScheduleImpl clone = null;

        try {
            clone = (WeeklyScheduleImpl) super.clone();

            if (timeZone != null) {
                clone.setTimeZone(getTimeZone());
            }
		} catch( CloneNotSupportedException ex) {
            throw new RuntimeException("WeeklyScheduleImpl: Unable to clone this object: "
                    + ex.getMessage());
		}

        return clone;
    }

    /**
     * Checks whether two WeeklySchedule are equal.
     * The result is true if and only if the argument is not null
     * and is an WeeklySchedule object that has the arguments.
     *
     * @param value the Object to compare with this WeeklySchedule
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof WeeklySchedule)) {
            WeeklySchedule argVal = (WeeklySchedule) value;

            if (this.getActiveDays().length != argVal.getActiveDays().length) {
                return false;
            }
            for (int i = this.getActiveDays().length - 1; i > 0 ; i--){
            	if (this.isActiveDay(i) != argVal.isActiveDay(i)) return false;
            }
            	
            if (!Utils.compareAttributes(getTimeZone(), argVal.getTimeZone())) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
}
