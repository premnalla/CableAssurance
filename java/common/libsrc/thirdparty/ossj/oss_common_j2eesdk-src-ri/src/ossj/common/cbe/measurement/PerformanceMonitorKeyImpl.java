/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements PerformanceMonitorKey
{ 

    /**
     * Constructs a new PerformanceMonitorKeyImpl with empty attributes.
     * 
     */

    public PerformanceMonitorKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorKey val = null;
            val = (PerformanceMonitorKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorKey object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorKey)) {
            PerformanceMonitorKey argVal = (PerformanceMonitorKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
