/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByClassesKeyResultImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyResultImpl
implements PerformanceMonitorByClassesKeyResult
{ 

    /**
     * Constructs a new PerformanceMonitorByClassesKeyResultImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByClassesKeyResultImpl() {
        super();
    }




    /**
     * Returns this PerformanceMonitorByClassesKeyResultImpl's performanceMonitorByClassesKey
     * 
     * @return the performanceMonitorByClassesKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorByClassesKey getPerformanceMonitorByClassesKey() {
        // Use the based MEK method
        return (javax.oss.cbe.measurement.PerformanceMonitorByClassesKey)getManagedEntityKey();
    }

}
