/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.ApplicationContext;
import ossj.qos.ManagedEntityKeyImpl;
import ossj.qos.util.Log;

import java.io.Serializable; // just added again in implements list to avoid J2EE 'verifier' tool faults.

/**
 * Implements the ThresholdMonitorKey.
 * @author Henrik Lindstrom, Ericsson
 */
public class ThresholdMonitorKeyImpl extends ManagedEntityKeyImpl implements ThresholdMonitorKey
{
   /**
    * Construct a ThresholdMonitorKey.
    */
   public ThresholdMonitorKeyImpl()
   {
        Log.write(this,Log.LOG_ALL,"ThresholdMonitorKeyImpl","created");
   }

   public String getThresholdMonitorPrimaryKey()
   {
        Log.write(this,Log.LOG_ALL,"getThresholdMonitorPrimaryKey()","called");
        Object key = this.getPrimaryKey();
        if ( key != null ) {
            return key.toString(); // String representation of key
        } else {
            return null; // key is null
        }
   }

   public void setThresholdMonitorPrimaryKey(String key)
   {
        Log.write(this,Log.LOG_ALL,"getThresholdMonitorPrimaryKey()","called");
        this.setPrimaryKey(key);
   }

	/**
	 * The primary key for ThresholdMonitorKey is returned.
	 * @return string representation of ThresholdMonitorKey
	 */
	public String toString() {
		return getThresholdMonitorPrimaryKey();
	}
}
