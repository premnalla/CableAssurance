/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmKeyResult;
import javax.oss.cbe.alarm.AlarmKeyResultIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmKeyResultIterator</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AlarmKeyResultIterator
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmKeyResultIteratorImpl
extends ossj.common.cbe.EntityKeyResultIteratorImpl
implements AlarmKeyResultIterator
{ 

    /**
     * Constructs a new AlarmKeyResultIteratorImpl with empty attributes.
     * 
     */

    public AlarmKeyResultIteratorImpl() {
        super();
    }

    /**
     * Constructs a new AlarmKeyResultIteratorImpl and add the given values.
     * @param values, the array of values
     *
     */
    public AlarmKeyResultIteratorImpl(AlarmKeyResult[] values){
        super(values);
    }

    /** 
     * Returns the next specified number of elements in the iteration. 
     * @param howMany number of element to return
     * @return the array of howMany elements
     * @throws java.rmi.RemoteException
     * @see javax.oss.ManagedEntityValueKeyResultIterator#getNext(int)
     */
    public AlarmKeyResult[] getNextAlarmKeyResults(int howMany)
    throws java.rmi.RemoteException {
        return (AlarmKeyResult[]) getNext(howMany);
    }




}
