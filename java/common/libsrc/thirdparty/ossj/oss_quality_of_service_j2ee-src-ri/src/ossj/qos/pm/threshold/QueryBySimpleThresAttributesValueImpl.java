/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.*;
import javax.oss.IllegalArgumentException;
import javax.oss.pm.measurement.*;
import javax.oss.fm.monitor.*;
//import ossj.qos.pm.util.Log; // logging
import ossj.qos.*;
import ossj.qos.util.*;
import ossj.qos.pm.threshold.*;
import ossj.qos.pm.threshold.QueryByMonitorValueImpl;

/**
 * Implements the QueryBySimpleThresAttributesValue.
 * @author Henrik Lindstrom, Ericsson
 */
public class QueryBySimpleThresAttributesValueImpl extends QueryByMonitorValueImpl implements QueryBySimpleThresAttributesValue
{
    /**
     * Constructs the QueryBySimpleThresAttributesValue. A new object is created and all
     * attributes initialized to null (unpopulated).
     */
   public QueryBySimpleThresAttributesValueImpl()
   {
        Log.write(this,Log.LOG_ALL,"QueryBySimpleThresAttributesValueImpl()","called");

        map.put( QueryBySimpleThresAttributesValue.ALARM_CONFIG, null);
        map.put( QueryBySimpleThresAttributesValue.OBSERVABLE_OBJECTS, null);
        map.put( QueryBySimpleThresAttributesValue.DEFINITIONS, null);

        // QueryBySimpleThresAttributesValue.QUERY_TYPE is not an attribute and
        // therefor not added to the map.
   }

	protected boolean isValidAttribute( String attrName, Object attrValue ) {
		boolean valid = super.isValidAttribute( attrName, attrValue ) ;
		if ( valid == false ) { // check also valid attribute for this class.
			if ( attrName != null && attrValue != null ) {
				if ( attrName.equals( QueryBySimpleThresAttributesValue.ALARM_CONFIG )
				  && attrValue instanceof AlarmConfig ) {
                    AlarmConfig alarmConfig = (AlarmConfig)attrValue;
                    valid = AttributeValidator.isValidAlarmConfig( alarmConfig );
				} else if ( attrName.equals( QueryBySimpleThresAttributesValue.DEFINITIONS ) ) {
                    if ( attrValue instanceof ThresholdDefinition[] ) {
                         ThresholdDefinition[] definitions = (ThresholdDefinition[])attrValue;
                         ThresholdDefinition thresholdDefinition = null;
                         for (int i=0;i<definitions.length;i++) {
                            valid = AttributeValidator.isValidThresholdDefinition( definitions[i] );
                            if ( valid==false ) {
                                Log.write("An illegal definition was found among the definitions!");
                                break;
                            }
                         }
                     } else {
                        Log.write("Not an array of definitions! " + attrValue);
                    }
				} else if ( attrName.equals( QueryBySimpleThresAttributesValue.OBSERVABLE_OBJECTS )
				    && attrValue instanceof String[] ) {
                    String[] observObjs = (String[])attrValue;
                    for (int i=0;i<observObjs.length;i++) {
                        if ( observObjs[i]==null ) {
                            return false;
                        }
                    }
    				valid = true;
				}
			}
		}/* else if ( attrName.equals( QueryByMonitorValue.VALUE_TYPE ) // extra check, the QueryBySimple... only supports one value type
          && attrValue instanceof String ) { // is this really needed?
            String valueType = (String)attrValue;
            if ( valueType.equals(SimpleThresholdMonitorValue.VALUE_TYPE) ){
                valid = true;
            } else {
                valid = false;
            }
        }*/
                return valid;
	}

   public AlarmConfig getAlarmConfig()
   {
        Log.write(this,Log.LOG_ALL,"getAlarmConfig()","called");
        return (AlarmConfig)this.getAttributeValue( QueryBySimpleThresAttributesValue.ALARM_CONFIG );
   }

   public String[] getObservableObjects()
   {
        Log.write(this,Log.LOG_ALL,"getObservableObjects()","called");
        return (String[])this.getAttributeValue( QueryBySimpleThresAttributesValue.OBSERVABLE_OBJECTS );
   }

   public ThresholdDefinition[] getThresholdDefinitions()
   {
        Log.write(this,Log.LOG_ALL,"getThresholdDefinitions()","called");
        return (ThresholdDefinition[]) this.getAttributeValue( QueryBySimpleThresAttributesValue.DEFINITIONS );
   }

   public void setAlarmConfig(AlarmConfig alarmConfig)
   {
        Log.write(this,Log.LOG_ALL,"setAlarmConfig()","alarmConfig="+alarmConfig);
        this.setAttributeValue(QueryBySimpleThresAttributesValue.ALARM_CONFIG,alarmConfig);
   }

   public void setObservableObjects(String[] observableobjects)
   {
        Log.write(this,Log.LOG_ALL,"setObservableObjects()","observableobjects="+observableobjects);
        this.setAttributeValue(QueryBySimpleThresAttributesValue.OBSERVABLE_OBJECTS,observableobjects);
   }

   public void setThresholdDefinitions(ThresholdDefinition[] threshDefinitions)
   {
        Log.write(this,Log.LOG_ALL,"setThresholdDefinitions()","threshDefinitions="+threshDefinitions);
        this.setAttributeValue(QueryBySimpleThresAttributesValue.DEFINITIONS,threshDefinitions);
   }

   public ThresholdDefinition makeThresholdDefinition()
   {
        Log.write(this,Log.LOG_ALL,"makeThresholdDefinition()","called");
        return new ThresholdDefinitionImpl();
   }

   public AlarmConfig makeAlarmConfig()
   {
        Log.write(this,Log.LOG_ALL,"makeAlarmConfig()","called");
        return new AlarmConfigImpl();
   }
}
