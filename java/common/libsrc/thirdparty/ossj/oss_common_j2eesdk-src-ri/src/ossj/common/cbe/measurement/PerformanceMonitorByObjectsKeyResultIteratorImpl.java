/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResult;
import javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByObjectsKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByObjectsKeyResultIteratorImpl
extends ossj.common.cbe.measurement.PerformanceMonitorKeyResultIteratorImpl
implements PerformanceMonitorByObjectsKeyResultIterator
{ 

    /**
     * Constructs a new PerformanceMonitorByObjectsKeyResultIteratorImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByObjectsKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PerformanceMonitorByObjectsKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PerformanceMonitorByObjectsKeyResultIteratorImpl(PerformanceMonitorByObjectsKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public PerformanceMonitorByObjectsKeyResult[] getNextPerformanceMonitorByObjectsKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (PerformanceMonitorByObjectsKeyResult[]) getNext(howMany);
    }




}
