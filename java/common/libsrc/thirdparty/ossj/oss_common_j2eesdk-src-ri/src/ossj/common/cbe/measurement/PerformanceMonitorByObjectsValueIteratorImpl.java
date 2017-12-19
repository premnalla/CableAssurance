/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByObjectsValue;
import javax.oss.cbe.measurement.PerformanceMonitorByObjectsValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByObjectsValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByObjectsValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByObjectsValueIteratorImpl
extends ossj.common.cbe.measurement.PerformanceMonitorValueIteratorImpl
implements PerformanceMonitorByObjectsValueIterator
{ 

    /**
     * Constructs a new PerformanceMonitorByObjectsValueIteratorImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByObjectsValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PerformanceMonitorByObjectsValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PerformanceMonitorByObjectsValueIteratorImpl(PerformanceMonitorByObjectsValue[] values){
        super();
        super.addManagedEntityValues(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueIterator#getNext(int)
     */
    public PerformanceMonitorByObjectsValue[] getNextPerformanceMonitorByObjectss(int howMany)
    throws java.rmi.RemoteException {
        return (PerformanceMonitorByObjectsValue[]) getNext(howMany);
    }




}
