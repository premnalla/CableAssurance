/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements AlarmKey
{ 

    /**
     * Constructs a new AlarmKeyImpl with empty attributes.
     * 
     */

    public AlarmKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AlarmKey val = null;
            val = (AlarmKey)super.clone();

            return val;
    }

    /**
     * Checks whether two AlarmKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an AlarmKey object that has the arguments.
     * 
     * @param value the Object to compare with this AlarmKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AlarmKey)) {
            AlarmKey argVal = (AlarmKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
