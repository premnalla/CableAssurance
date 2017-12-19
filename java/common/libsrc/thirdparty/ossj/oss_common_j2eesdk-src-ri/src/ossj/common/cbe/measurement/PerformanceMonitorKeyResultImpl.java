/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements PerformanceMonitorKeyResult
{ 

    /**
     * Constructs a new PerformanceMonitorKeyResultImpl with empty attributes.
     * 
     */

    public PerformanceMonitorKeyResultImpl() {
        super();
    }




    /**
     * Returns this PerformanceMonitorKeyResultImpl's performanceMonitorKey
     * 
     * @return the performanceMonitorKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorKey getPerformanceMonitorKey() {
        // Use the based MEK method
        return (javax.oss.cbe.measurement.PerformanceMonitorKey)getManagedEntityKey();
    }

}
