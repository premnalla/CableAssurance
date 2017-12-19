/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.*;
//import ossj.qos.pm.util.Log;
import ossj.qos.util.Log;


/**
 * Implements the TriggerOnAnyThresholdMonitorValue
 * interface.
 * @author Henrik Lindstrom, Ericsson
 */
public class TriggerOnAnyThresholdMonitorValueImpl extends ThresholdMonitorValueImpl implements TriggerOnAnyThresholdMonitorValue  {

    public TriggerOnAnyThresholdMonitorValueImpl() {
       Log.write(this,Log.LOG_ALL,"TriggerOnAnyThresholdMonitorValueImpl()","called");

        // populate all attributes
        map.put( TriggerOnAnyThresholdMonitorValue.ALARM_CONFIG,null );
        map.put( TriggerOnAnyThresholdMonitorValue.DEFINITIONS,null );
        map.put( TriggerOnAnyThresholdMonitorValue.OBSERVABLE_OBJECTS,null );
    }

    public void setObservableObjects(String[] observableobjects)  throws java.lang.IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setObservableObjects()","called");
        setAttributeValue( TriggerOnAnyThresholdMonitorValue.OBSERVABLE_OBJECTS, observableobjects );
    }

    public String[] getObservableObjects() throws java.lang.IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getObservableObjects()","called");
        return (String[]) getAttributeValue( OBSERVABLE_OBJECTS );
    }

    public void setThresholdDefinitions(ThresholdDefinition[] threshDefinitions) throws java.lang.IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setThresholdDefinitions()","called");
        setAttributeValue( DEFINITIONS, threshDefinitions );
    }

    public ThresholdDefinition[] getThresholdDefinitions() throws java.lang.IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getThresholdDefinitions()","called");
        return (ThresholdDefinition[]) getAttributeValue( DEFINITIONS );
    }

    public ThresholdDefinition makeThresholdDefinition() {
        Log.write(this,Log.LOG_ALL,"makeThresholdDefinition()","called");
        return new ThresholdDefinitionImpl();
    }

    public void setAlarmConfig(AlarmConfig alarmConfig) throws java.lang.IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setAlarmConfig()","called");
        setAttributeValue( ALARM_CONFIG, alarmConfig );
    }

    public AlarmConfig getAlarmConfig() throws java.lang.IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getAlarmConfig()","called");
        return (AlarmConfig) getAttributeValue( ALARM_CONFIG );
    }

    public AlarmConfig makeAlarmConfig() {
        Log.write(this,Log.LOG_ALL,"makeAlarmConfig()","called");
        return new AlarmConfigImpl();
    }

    /**
     * Lets the super method(s) validate the common attributes, then the specific
     * attributes for this class is checked.
     */
    protected boolean isValidAttribute( String attrName, Object attrValue ) {
        boolean valid = super.isValidAttribute( attrName, attrValue ) ;

        if ( valid == false ) { // check also valid attribute for this class.
            if ( attrName != null && attrValue != null ) {
                if ( attrName.equals( ALARM_CONFIG ) && attrValue instanceof AlarmConfig ) {
                    AlarmConfig alarmConfig = (AlarmConfig)attrValue;
                    valid = AttributeValidator.isValidAlarmConfig( alarmConfig );
                } else if ( attrName.equals( DEFINITIONS ) && attrValue instanceof ThresholdDefinition[] ) {
                    // check definitions
                    ThresholdDefinition[] definitions = (ThresholdDefinition[])attrValue;
                    ThresholdDefinition td = null;
                    for (int i=0;i<definitions.length;i++) {
                        td = definitions[i];
                        valid = AttributeValidator.isValidThresholdDefinition( td );
                        if ( valid == false ) {
                            break;
                        }
                    }
                } else if ( attrName.equals( OBSERVABLE_OBJECTS ) && attrValue instanceof String[] ) {
                    // observable objects for null
                    String[] observObjects = (String[])attrValue;
                    for (int i=0;i<observObjects.length;i++) {
                        if ( observObjects[i] == null ) {
                            return false; // not specified!
                        }
                    }
                    valid = true;
                }
            }
        }
        return valid;
    }
}