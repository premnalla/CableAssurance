/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmValue;
import javax.oss.cbe.alarm.AlarmValueIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmValueIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmValueIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmValueIteratorImpl
extends ossj.common.cbe.EntityValueIteratorImpl
implements AlarmValueIterator
{ 

    /**
     * Constructs a new AlarmValueIteratorImpl with empty attributes.
     * 
     */

    public AlarmValueIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AlarmValueIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AlarmValueIteratorImpl(AlarmValue[] values){
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
    public AlarmValue[] getNextAlarms(int howMany)
    throws java.rmi.RemoteException {
        return (AlarmValue[]) getNext(howMany);
    }




}
