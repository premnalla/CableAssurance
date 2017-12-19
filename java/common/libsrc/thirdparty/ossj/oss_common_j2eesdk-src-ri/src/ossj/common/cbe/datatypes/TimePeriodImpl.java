/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.datatypes;

import java.util.Date;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.datatypes.TimePeriod</CODE> interface.  
 * 
 * @see javax.oss.cbe.datatypes.TimePeriod
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TimePeriodImpl
implements TimePeriod
{ 

    /**
     * Constructs a new TimePeriodImpl with empty attributes.
     * 
     */

    public TimePeriodImpl() {
    }

    private java.util.Date _timeperiodimpl_endDateTime = null;
    private java.util.Date _timeperiodimpl_startDateTime = null;


    /**
     * Changes the endDateTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new endDateTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setEndDateTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        _timeperiodimpl_endDateTime = value;
    }


    /**
     * Returns this TimePeriodImpl's endDateTime
     * 
     * @return the endDateTime
     * 
    */

    public java.util.Date getEndDateTime() {
        return _timeperiodimpl_endDateTime;
    }

    /**
     * Changes the startDateTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new startDateTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStartDateTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        _timeperiodimpl_startDateTime = value;
    }


    /**
     * Returns this TimePeriodImpl's startDateTime
     * 
     * @return the startDateTime
     * 
    */

    public java.util.Date getStartDateTime() {
        return _timeperiodimpl_startDateTime;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TimePeriod val = null;
        try { 
            val = (TimePeriod)super.clone();

            if( this.getEndDateTime()!=null) 
                val.setEndDateTime((java.util.Date)((java.util.Date) this.getEndDateTime()).clone());
            else
                val.setEndDateTime( null );

            if( this.getStartDateTime()!=null) 
                val.setStartDateTime((java.util.Date)((java.util.Date) this.getStartDateTime()).clone());
            else
                val.setStartDateTime( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("TimePeriodImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two TimePeriod are equal.
     * The result is true if and only if the argument is not null 
     * and is an TimePeriod object that has the arguments.
     * 
     * @param value the Object to compare with this TimePeriod
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TimePeriod)) {
            TimePeriod argVal = (TimePeriod) value;
            if( !Utils.compareAttributes( getEndDateTime(), argVal.getEndDateTime())) { return false; } 
            if( !Utils.compareAttributes( getStartDateTime(), argVal.getStartDateTime())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }
    /**
     * true if current Date within the TimePeriod
     * (implementation may add time margins on both sides)
     * @return boolean
     */
    public boolean isWithin(java.util.Date currentDate){
        return (currentDate.compareTo(_timeperiodimpl_startDateTime) >=0 && currentDate.compareTo(_timeperiodimpl_endDateTime)<=0);
    }

}
