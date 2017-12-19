/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByClassesValue;
import javax.oss.cbe.measurement.PerformanceMonitorByClassesValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByClassesValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByClassesValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByClassesValueIteratorImpl
extends ossj.common.cbe.measurement.PerformanceMonitorValueIteratorImpl
implements PerformanceMonitorByClassesValueIterator
{ 

    /**
     * Constructs a new PerformanceMonitorByClassesValueIteratorImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByClassesValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new PerformanceMonitorByClassesValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public PerformanceMonitorByClassesValueIteratorImpl(PerformanceMonitorByClassesValue[] values){
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
    public PerformanceMonitorByClassesValue[] getNextPerformanceMonitorByClassess(int howMany)
    throws java.rmi.RemoteException {
        return (PerformanceMonitorByClassesValue[]) getNext(howMany);
    }




}
