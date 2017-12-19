/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.ThresholdMonitorValueIterator;
import java.rmi.RemoteException;
import javax.ejb.RemoveException;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.ManagedEntityValue;

import ossj.qos.ManagedEntityValueIteratorImpl;
//import ossj.qos.pm.util.ObjectIterator;
//import ossj.qos.pm.util.Log;

import ossj.qos.util.ObjectIterator;
import ossj.qos.util.Log;


/**
 * Implements the ThresholdMonitorValueIterator.
 * @author Henrik Lindstrom, Ericsson
 */
public class ThresholdMonitorValueIteratorImpl extends ManagedEntityValueIteratorImpl implements ThresholdMonitorValueIterator
{
    /**
     * Constructs a new ThresholdMonitorValueIterator.
     */
    public ThresholdMonitorValueIteratorImpl( Object[] objects )
    {
        super( objects );
    }

   public ThresholdMonitorValue[] getNextThresholdMonitors(int how_many) throws RemoteException
   {
        Log.write(this,Log.LOG_ALL,"getNextThresholdMonitors()","how_many="+how_many);
        Object[] nextValues = getNextValue(how_many);
        ThresholdMonitorValue[] values = null;
        if(nextValues != null) {
          values = new  ThresholdMonitorValue[nextValues.length];
          System.arraycopy(nextValues, 0, values, 0, nextValues.length);
        }
        return values;
   }
}