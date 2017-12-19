/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmSpecificInfo;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmSpecificInfo</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmSpecificInfo
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmSpecificInfoImpl
implements AlarmSpecificInfo
{ 

    /**
     * Constructs a new AlarmSpecificInfoImpl with empty attributes.
     * 
     */

    public AlarmSpecificInfoImpl() {
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AlarmSpecificInfo val = null;
        try { 
            val = (AlarmSpecificInfo)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("AlarmSpecificInfoImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two AlarmSpecificInfo are equal.
     * The result is true if and only if the argument is not null 
     * and is an AlarmSpecificInfo object that has the arguments.
     * 
     * @param value the Object to compare with this AlarmSpecificInfo
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AlarmSpecificInfo)) {
            AlarmSpecificInfo argVal = (AlarmSpecificInfo) value;

            return true;
        } else {
            return super.equals(value);
        }
    }

}
