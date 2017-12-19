/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.schedule;

import java.util.Calendar;
import javax.oss.cbe.schedule.DailySchedule;
import javax.oss.cbe.schedule.WeeklySchedule;
import javax.oss.cbe.schedule.Schedule;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.schedule.Schedule</CODE> interface.  
 * 
 * @see javax.oss.cbe.schedule.Schedule
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ScheduleImpl
implements Schedule
{ 

    /**
     * Constructs a new ScheduleImpl with empty attributes.
     * 
     */

    public ScheduleImpl() {
    }

    public String[] attributeTypeForWeeklySchedule() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.schedule.WeeklySchedule";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.schedule.WeeklySchedule makeWeeklySchedule(String type){
        if(type.equals("javax.oss.cbe.schedule.WeeklySchedule") || type.equals("ossj.common.cbe.schedule.WeeklyScheduleImpl")) {
            return new WeeklyScheduleImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForDailySchedule() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.schedule.DailySchedule";
        return list;
    }

    public javax.oss.cbe.schedule.DailySchedule makeDailySchedule(String type){
        if(type.equals("javax.oss.cbe.schedule.DailySchedule") || type.equals("ossj.common.cbe.schedule.DailyScheduleImpl")) {
            return new DailyScheduleImpl();
        } else {
            return null;
        }
    }

    private javax.oss.cbe.schedule.DailySchedule _scheduleimpl_dailySchedule = null;
    private java.util.Calendar _scheduleimpl_startTime = null;
    private java.util.Calendar _scheduleimpl_stopTime = null;
    private javax.oss.cbe.schedule.WeeklySchedule _scheduleimpl_weeklySchedule = null;



    /**
     * Returns this ScheduleImpl's active
     * 
     * @return the active
     * 
    */

    public boolean isActive() {
        boolean active = false;
        Calendar now = Calendar.getInstance();
        if ((((_scheduleimpl_startTime == null) || now.after(_scheduleimpl_startTime))
            && ((_scheduleimpl_stopTime == null) || now.before(_scheduleimpl_stopTime)))) {
            active = true;
        }
        return active;
    }

    /**
     * Changes the dailySchedule to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new dailySchedule for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDailySchedule(javax.oss.cbe.schedule.DailySchedule value)
    throws java.lang.IllegalArgumentException    {
        _scheduleimpl_dailySchedule = value;
    }


    /**
     * Returns this ScheduleImpl's dailySchedule
     * 
     * @return the dailySchedule
     * 
    */

    public javax.oss.cbe.schedule.DailySchedule getDailySchedule() {
        return _scheduleimpl_dailySchedule;
    }

    /**
     * Changes the startTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new startTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStartTime(java.util.Calendar value)
    throws java.lang.IllegalArgumentException    {
        _scheduleimpl_startTime = value;
    }


    /**
     * Returns this ScheduleImpl's startTime
     * 
     * @return the startTime
     * 
    */

    public java.util.Calendar getStartTime() {
        return _scheduleimpl_startTime;
    }

    /**
     * Changes the stopTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new stopTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStopTime(java.util.Calendar value)
    throws java.lang.IllegalArgumentException    {
        _scheduleimpl_stopTime = value;
    }


    /**
     * Returns this ScheduleImpl's stopTime
     * 
     * @return the stopTime
     * 
    */

    public java.util.Calendar getStopTime() {
        return _scheduleimpl_stopTime;
    }

    /**
     * Changes the weeklySchedule to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new weeklySchedule for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setWeeklySchedule(javax.oss.cbe.schedule.WeeklySchedule value)
    throws java.lang.IllegalArgumentException    {
        _scheduleimpl_weeklySchedule = value;
    }


    /**
     * Returns this ScheduleImpl's weeklySchedule
     * 
     * @return the weeklySchedule
     * 
    */

    public javax.oss.cbe.schedule.WeeklySchedule getWeeklySchedule() {
        return _scheduleimpl_weeklySchedule;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        Schedule val = null;
        try { 
            val = (Schedule)super.clone();

            if( this.getDailySchedule()!=null) 
                val.setDailySchedule((javax.oss.cbe.schedule.DailySchedule)((javax.oss.cbe.schedule.DailySchedule) this.getDailySchedule()).clone());
            else
                val.setDailySchedule( null );

            if( this.getStartTime()!=null) 
                val.setStartTime((java.util.Calendar)((java.util.Calendar) this.getStartTime()).clone());
            else
                val.setStartTime( null );

            if( this.getStopTime()!=null) 
                val.setStopTime((java.util.Calendar)((java.util.Calendar) this.getStopTime()).clone());
            else
                val.setStopTime( null );

            if( this.getWeeklySchedule()!=null) 
                val.setWeeklySchedule((javax.oss.cbe.schedule.WeeklySchedule)((javax.oss.cbe.schedule.WeeklySchedule) this.getWeeklySchedule()).clone());
            else
                val.setWeeklySchedule( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ScheduleImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two Schedule are equal.
     * The result is true if and only if the argument is not null 
     * and is an Schedule object that has the arguments.
     * 
     * @param value the Object to compare with this Schedule
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Schedule)) {
            Schedule argVal = (Schedule) value;
            if( this.isActive() != argVal.isActive()) { return false; } 
            if( !Utils.compareAttributes( getDailySchedule(), argVal.getDailySchedule())) { return false; } 
            if( !Utils.compareAttributes( getStartTime(), argVal.getStartTime())) { return false; } 
            if( !Utils.compareAttributes( getStopTime(), argVal.getStopTime())) { return false; } 
            if( !Utils.compareAttributes( getWeeklySchedule(), argVal.getWeeklySchedule())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
