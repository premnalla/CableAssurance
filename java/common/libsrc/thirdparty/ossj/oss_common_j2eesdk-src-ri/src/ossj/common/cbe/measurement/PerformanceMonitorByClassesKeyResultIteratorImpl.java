/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResult;
import javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByClassesKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByClassesKeyResultIteratorImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyResultIteratorImpl
implements PerformanceMonitorByClassesKeyResultIterator
{ 

    /**
     * Constructs a new PerformanceMonitorByClassesKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByClassesKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PerformanceMonitorByClassesKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PerformanceMonitorByClassesKeyResultIteratorImpl(PerformanceMonitorByClassesKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PerformanceMonitorByClassesKeyResult[] getNextPerformanceMonitorByClassesKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PerformanceMonitorByClassesKeyResult[]) getNext(howMany);
    }




}
