/*
 * Copyright ¨ 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import java.util.Map;

import javax.oss.IllegalArgumentException;
import javax.oss.ManagedEntityKey;
import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.threshold.ThresholdMonitorState;
import javax.oss.pm.util.Schedule;

import ossj.qos.ManagedEntityValueImpl;
import ossj.qos.util.Log;
import ossj.qos.util.ScheduleImpl;

/**
 * Implements the ThresholdMonitorValue.
 * @author Henrik Lindstrom, Ericsson
 */

//public class ThresholdMonitorValueImpl extends ManagedEntityValueImpl implements ThresholdMonitorValue, java.io.Serializable
public class ThresholdMonitorValueImpl extends TmManagedEntityValueImpl implements ThresholdMonitorValue, java.io.Serializable
{

   /**
    * Constructs a new ThresholdMonitorValue.
    */
   public ThresholdMonitorValueImpl()
   {
        Log.write(this,Log.LOG_ALL,"ThresholdMonitorValueImpl()","called");

        // initialize all attribute names in map with null values (unpopulated)
        map.put( ThresholdMonitorValue.GRANULARITY, null );
        map.put( ThresholdMonitorValue.NAME, null );
        map.put( ThresholdMonitorValue.SCHEDULE, null );
        map.put( ThresholdMonitorValue.STATE, null ); // ThresholdMonitorValue.STATE is not a normal attribute!
        map.put("perfkey",null);
   }

	protected boolean isValidAttribute( String attrName, Object attrValue ) {
		boolean valid = super.isValidAttribute( attrName, attrValue ) ;
		if ( valid == false ) { // check also valid attribute for this class.
			if ( attrName != null && attrValue != null ) {
				if ( attrName.equals( ThresholdMonitorValue.GRANULARITY )
				  && attrValue instanceof Integer ) {
                    int granularity = ((Integer)attrValue).intValue();
                    valid = AttributeValidator.isValidGranularity( granularity );
				} else if ( attrName.equals( ThresholdMonitorValue.NAME )
				  && attrValue instanceof String ) {
					valid = true;
				} else if ( attrName.equals( ThresholdMonitorValue.SCHEDULE )
				  && attrValue instanceof Schedule ) {
                    // check schedule in more detail
                    Schedule schedule = (Schedule)attrValue;
                    valid = AttributeValidator.isValidSchedule( schedule );
				} else if ( attrName.equals( ThresholdMonitorValue.STATE )
				  && attrValue instanceof Integer ) {
                    // check for valid states
                    int state = ((Integer)attrValue).intValue();
                    valid = AttributeValidator.isValidState( state );
				} else if (attrName.equals("perfkey")) {
                                    valid = true;
                                }
			}
		} else if ( attrName.equals( ThresholdMonitorValue.KEY ) ) {
            // the generic test for KEY is not enough and has to be checked in detail
            if ( attrValue instanceof ThresholdMonitorKey ) {
                ThresholdMonitorKey key = (ThresholdMonitorKey)attrValue;
                valid = AttributeValidator.isValidThresholdMonitorKey( key );
            } else {
                valid=false; // not the expected type of key
            }
        }
		return valid;
	}

   public void setGranularityPeriod(int granularity) throws java.lang.IllegalArgumentException
   {
        this.setAttributeValue(ThresholdMonitorValue.GRANULARITY,new Integer( granularity ) );
   }


   public int getGranularityPeriod() throws java.lang.IllegalStateException
   {
        return ((Integer)this.getAttributeValue( ThresholdMonitorValue.GRANULARITY )).intValue();
   }

   public Schedule getSchedule() throws java.lang.IllegalStateException
   {
        return (Schedule)this.getAttributeValue(ThresholdMonitorValue.SCHEDULE);
   }

    public void setSchedule(Schedule schedule)
    {
        this.setAttributeValue(ThresholdMonitorValue.SCHEDULE,schedule);
    }

   public Schedule makeSchedule()
   {
        Log.write(this,Log.LOG_ALL,"makeSchedule()","called");
        return new ScheduleImpl();
   }

   public ThresholdMonitorKey makeThresholdMonitorKey() {
        Log.write(this,Log.LOG_ALL,"makeThresholdMonitorKey()","called");
        return new ThresholdMonitorKeyImpl();
   }

   public ThresholdMonitorKey getThresholdMonitorKey() throws java.lang.IllegalStateException
   {
        return (ThresholdMonitorKey)this.getAttributeValue( ThresholdMonitorValue.KEY );
   }

    public void setThresholdMonitorKey(ThresholdMonitorKey key)
                            throws java.lang.IllegalArgumentException   {
        this.setAttributeValue( ThresholdMonitorValue.KEY, key);
    }

   public void setName(String name)
   {
        this.setAttributeValue(ThresholdMonitorValue.NAME,name);
   }

   public String getName() throws java.lang.IllegalStateException
   {
        return (String)this.getAttributeValue( ThresholdMonitorValue.NAME );
   }

   public int getState() throws java.lang.IllegalStateException
   {
        return ((Integer)this.getAttributeValue( ThresholdMonitorValue.STATE ) ).intValue();
   }

   public void setState(int state)
   {
        this.setAttributeValue( ThresholdMonitorValue.STATE, new Integer( state) );
   }

    public String toString() {
        return "" + map.get( ThresholdMonitorValue.NAME );
    }
}
