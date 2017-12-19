/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByObjectsKeyImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyImpl
implements PerformanceMonitorByObjectsKey
{ 

    /**
     * Constructs a new PerformanceMonitorByObjectsKeyImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByObjectsKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorByObjectsKey val = null;
            val = (PerformanceMonitorByObjectsKey)super.clone();

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorByObjectsKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorByObjectsKey object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorByObjectsKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorByObjectsKey)) {
            PerformanceMonitorByObjectsKey argVal = (PerformanceMonitorByObjectsKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
