/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByClassesKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByClassesKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByClassesKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByClassesKeyImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyImpl
implements PerformanceMonitorByClassesKey
{ 

    /**
     * Constructs a new PerformanceMonitorByClassesKeyImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByClassesKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorByClassesKey val = null;
            val = (PerformanceMonitorByClassesKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorByClassesKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorByClassesKey object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorByClassesKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorByClassesKey)) {
            PerformanceMonitorByClassesKey argVal = (PerformanceMonitorByClassesKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
