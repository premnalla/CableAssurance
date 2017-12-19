/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorKeyResult;
import javax.oss.cbe.measurement.PerformanceMonitorKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements PerformanceMonitorKeyResultIterator
{ 

    /**
     * Constructs a new PerformanceMonitorKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PerformanceMonitorKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PerformanceMonitorKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PerformanceMonitorKeyResultIteratorImpl(PerformanceMonitorKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PerformanceMonitorKeyResult[] getNextPerformanceMonitorKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PerformanceMonitorKeyResult[]) getNext(howMany);
    }




}
