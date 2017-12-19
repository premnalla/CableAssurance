/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByObjectsKeyResultImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyResultImpl
implements PerformanceMonitorByObjectsKeyResult
{ 

    /**
     * Constructs a new PerformanceMonitorByObjectsKeyResultImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByObjectsKeyResultImpl() {
        super();
    }




    /**
     * Returns this PerformanceMonitorByObjectsKeyResultImpl's performanceMonitorByObjectsKey
     * 
     * @return the performanceMonitorByObjectsKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey getPerformanceMonitorByObjectsKey() {
        // Use the based MEK method
        return (javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey)getManagedEntityKey();
    }

}
