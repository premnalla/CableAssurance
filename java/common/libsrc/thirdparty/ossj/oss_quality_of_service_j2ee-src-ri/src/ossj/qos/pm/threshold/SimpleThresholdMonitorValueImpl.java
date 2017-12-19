/*
 * Copyright ¨ 2001 Ericsson RadiogetKeyArray.
 */
package ossj.qos.pm.threshold;

import javax.oss.IllegalArgumentException;
import javax.oss.pm.threshold.*;
import javax.oss.pm.measurement.*;
import javax.oss.fm.monitor.*;

//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;
/**
 * Implements the SimpleThresholdMonitorValue.
 * @author Henrik Lindstrom, Ericsson
 */
public class SimpleThresholdMonitorValueImpl extends ThresholdMonitorValueImpl implements SimpleThresholdMonitorValue
{
    /**
     * Constructs the SimpleThresholdMonitorValue. A new object is created and all
     * attributes initialized to null (unpopulated).
     */
    public SimpleThresholdMonitorValueImpl()
    {
        Log.write(this,Log.LOG_ALL,"SimpleThresholdMonitorValueImpl()","called");

        // populate all attributes
        map.put( SimpleThresholdMonitorValue.OBSERVABLE_OBJECT,null );
        map.put( SimpleThresholdMonitorValue.DEFINITION,null );
        map.put( SimpleThresholdMonitorValue.ALARM_CONFIG,null );
        // SimpleThresholdMonitorValue.VALUE_TYPE is not an attribute
    }

	protected boolean isValidAttribute( String attrName, Object attrValue ) {
		boolean valid = super.isValidAttribute( attrName, attrValue ) ;
		if ( valid == false ) { // check also valid attribute for this class.
			if ( attrName != null && attrValue != null ) {
				if ( attrName.equals( SimpleThresholdMonitorValue.ALARM_CONFIG )
				  && attrValue instanceof AlarmConfig ) {
                    AlarmConfig alarmConfig = (AlarmConfig)attrValue;
                    valid = AttributeValidator.isValidAlarmConfig( alarmConfig );
				} else if ( attrName.equals( SimpleThresholdMonitorValue.DEFINITION )
				  && attrValue instanceof ThresholdDefinition ) {
                    // check definition
                    ThresholdDefinition thresholdDefinition = (ThresholdDefinition)attrValue;
                    valid = AttributeValidator.isValidThresholdDefinition( thresholdDefinition );
				} else if ( attrName.equals( SimpleThresholdMonitorValue.OBSERVABLE_OBJECT )
				  && attrValue instanceof String ) {
					valid = true;
				}
			}
		}
		return valid;
	}

   public void setObservableObject(String observableObject)
   {
        this.setAttributeValue( SimpleThresholdMonitorValue.OBSERVABLE_OBJECT, observableObject );
   }

   public void setThresholdDefinition(ThresholdDefinition threshDefinition)
   {
        this.setAttributeValue( SimpleThresholdMonitorValue.DEFINITION, threshDefinition );
   }

   public ThresholdDefinition getThresholdDefinition()
   {
       return  (ThresholdDefinition)this.getAttributeValue(SimpleThresholdMonitorValue.DEFINITION);
   }

   public ThresholdDefinition makeThresholdDefinition()
   {
        return new ThresholdDefinitionImpl();
   }

   public void setAlarmConfig(AlarmConfig alarmConfig)
   {
    this.setAttributeValue(SimpleThresholdMonitorValue.ALARM_CONFIG, alarmConfig);
   }

   public AlarmConfig getAlarmConfig()
   {
        return (AlarmConfig)this.getAttributeValue(SimpleThresholdMonitorValue.ALARM_CONFIG );
   }

   public AlarmConfig makeAlarmConfig()
   {
        return new AlarmConfigImpl();
   }

   public String getObservableObject()
   {
        return (String)this.getAttributeValue( SimpleThresholdMonitorValue.OBSERVABLE_OBJECT );
   }

}

