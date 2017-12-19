/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.fm.monitor.PerceivedSeverity;
import javax.oss.fm.monitor.ProbableCause;
import javax.oss.fm.monitor.AlarmType;
import javax.oss.pm.measurement.PerformanceAttributeDescriptor;
import javax.oss.pm.threshold.*;
import javax.oss.pm.util.Schedule;
import java.util.Calendar;
import ossj.qos.util.Log;

import java.lang.reflect.Field;

/**
 * Validates threshold attributes. Contains static methods for evaluating if a
 * an attribute, for instance AlarmConfig, is valid. The validation is simple in
 * that way that it does not interact with the system (Threshold Monitor), i.e.
 * further validation might be required by the monitor itself before creation of
 * a threshold.
 *
 * @author Henrik Lindstrom, Ericsson
 */
public class AttributeValidator {
    /**
     * Validate AlarmConfig.
     * @param alarmConfig
     */
    public static boolean isValidAlarmConfig( AlarmConfig alarmConfig ) {
        if ( alarmConfig==null ) {
            return false;
        }
        short severity;
        try {
            severity = alarmConfig.getPerceivedSeverity();
        } catch ( IllegalStateException e ) {
            Log.write("AlarmConfig:perceivedSeverity is not defined");
            return false;
        }
        if ( isValidAlarmConfigPerceivedSeverity( severity )==false ) {
            return false;
        }

        short cause = alarmConfig.getProbableCause();
        if ( isValidAlarmConfigProbableCause( cause )==false ) {
            return false;
        }

        String alarmType = alarmConfig.getAlarmType();
        if ( isValidAlarmType( alarmType )==false ) {
            return false;
        }

        String specificProblem = alarmConfig.getSpecificProblem();
        if ( isValidAlarmConfigSpecificProblem( specificProblem )== false ) {
            return false;
        }

        return true; // all test passed
    }

    /**
     * Validate the perceived severity.
     * @return true if valid
     */
    public static boolean isValidAlarmConfigPerceivedSeverity( short severity ) {
        if ( severity == PerceivedSeverity.CLEARED
          || severity == PerceivedSeverity.CRITICAL
          || severity != PerceivedSeverity.INDETERMINATE
          || severity != PerceivedSeverity.MAJOR
          || severity != PerceivedSeverity.MINOR
          || severity != PerceivedSeverity.WARNING ) {
            Log.write("AlarmConfig:perceivedSeverity is valid.");
            return true;
        }
        Log.write( "AlarmConfig:perceivedSeverity is not valid.");
        return false;
    }

    /**
     * Validate probable cause. The probable cause is valid if it is defined as
     * a constant in the ProbableCause interface. The method uses Java Reflection
     * API to lookup all valid constants in the ProbableCause interface.
     *
     * @param cause the probable cause to validate
     * @return true if valid
     * @see javax.oss.fm.monitor.ProbableCause
     */
    public static boolean isValidAlarmConfigProbableCause( short cause ) {
        try {
            // There are so many constants that it is easier to validate using
            // reflection.
            Class cls = Class.forName( "javax.oss.fm.monitor.ProbableCause" );
            Field[] causes = cls.getFields();
            Short sObj = new Short((short)0); // just an object for the getShort(), the value is not important
            for (int i=0;i<causes.length;i++) {
                //Log.write("testing with " + causes[i] );
                if ( cause == causes[i].getShort( sObj ) ) {
                    Log.write("AttributeValidator::isValidAlarmConfigProbableCause(), probable cause is valid.");
                    return true;
                }
            }
            Log.write("AttributeValidator::isValidAlarmConfigProbableCause(), probable cause is not valid.");
        } catch ( ClassNotFoundException e ) {
            Log.write("AttributeValidator",Log.LOG_MAJOR,"isValidAlarmConfigProbableCause()","exception="+e);
            throw new RuntimeException( e.getMessage() );
        } catch ( IllegalAccessException e ) {
            Log.write("AttributeValidator",Log.LOG_MAJOR,"isValidAlarmConfigProbableCause()","exception="+e);
            throw new RuntimeException( e.getMessage() );
        }
        return false;
    }

    /**
     * Validate specific problem.
     * @return true if valid (not null)
     */
    public static boolean isValidAlarmConfigSpecificProblem( String specificProblem ) {
        if ( specificProblem==null ) {
            return false;
        }
        return true;
    }

    /**
     * Validate alarm type. The alarm type is valid if it is defined as
     * a constant in the AlarmType interface. The method uses Java Reflection
     * API to lookup all valid constants in the AlarmType interface.
     *
     * @param alarmType the alarm type to validate
     * @return true if valid
     * @see javax.oss.fm.monitor.AlarmType
     */
    public static boolean isValidAlarmType( String alarmType ) {
        if ( alarmType==null ) {
            Log.write("AttributeValidator::isValidAlarmType(), alarm type is not valid.");
            return false;
        }
        try {
            // There are so many constants that it is easier to validate using
            // reflection.
            Class cls = Class.forName( "javax.oss.fm.monitor.AlarmType" );
            Field[] types = cls.getFields();
            String sObj = new String(); // just an object for the getShort(), the value is not important
            for (int i=0;i<types.length;i++) {
                //Log.write("testing with " + types[i] );
                if ( alarmType.equals( types[i].get( sObj ) ) ) {
                    Log.write("AttributeValidator::isValidAlarmType(), alarm type is valid.");
                    return true;
                }
            }
            Log.write("AttributeValidator::isValidAlarmType(), alarm type is not valid.");
        } catch ( ClassNotFoundException e ) {
            Log.write("AttributeValidator",Log.LOG_MAJOR,"isValidAlarmType()","exception="+e);
            throw new RuntimeException( e.getMessage() );
        } catch ( IllegalAccessException e ) {
            Log.write("AttributeValidator",Log.LOG_MAJOR,"isValidAlarmType()","exception="+e);
            throw new RuntimeException( e.getMessage() );
        }
        return false;
    }

    /**
     * Validate Threshold Definition.
     * @param thresholdDefinition
     * @return true if valid
     */
    public static boolean isValidThresholdDefinition( ThresholdDefinition thresholdDefinition ) {
        if ( thresholdDefinition==null)  {
            Log.write("Threshold definition is null!");
            return false;
        }

        Log.write("ThresholdDefinition="+thresholdDefinition);

        try {
            // check performance attribute descriptor
            Log.write("Check performance attribute descriptor...");
            PerformanceAttributeDescriptor descriptor = thresholdDefinition.getAttributeDescriptor();
            if ( descriptor == null ) {
                Log.write("PerformanceAttributeDescriptor is null");
                return false;
            }

            // check collection method
            Log.write("Check collection method in descriptor...");
            String collectionMethod = descriptor.getCollectionMethod();
            if ( PerformanceAttributeDescriptor.CUMULATIVE_COUNTER.equals( collectionMethod )
              || PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION.equals( collectionMethod )
              || PerformanceAttributeDescriptor.GAUGE.equals( collectionMethod )
              || PerformanceAttributeDescriptor.STATUS_INSPECTION.equals( collectionMethod ) ) {
            } else {
                Log.write("Collection method is not valid!");
                return false;
            }

            // check name
            Log.write("Check name of descriptor...");
            String descriptorName = descriptor.getName();
            if ( descriptorName == null ) {
                Log.write("Descriptor name is null!");
                return false;
            }

            // check type
            Log.write("Check type of descriptor...");
            int type = descriptor.getType();
            if ( type != PerformanceAttributeDescriptor.INTEGER
              && type != PerformanceAttributeDescriptor.REAL ) {
               //  Note, PerformanceAttributeDescriptor.STRING is not valid for threshold monitor
               Log.write("Not valid type, " + type );
               return false;
            }

            Log.write("Check direction...");
            int direction = thresholdDefinition.getDirection();
            if ( direction != ThresholdDirection.FALLING
              && direction != ThresholdDirection.RISING ) {
                Log.write("Not valid direction, "+ direction);
                return false;
            }

            // check value
            Log.write("Check value...");
            Object value = thresholdDefinition.getValue();
            if ( value == null ) {
                Log.write("Value is null!");
                return false;
            }
            // check that value match the type
            if ( (value instanceof Integer && type != PerformanceAttributeDescriptor.INTEGER)
              || (value instanceof Double && type != PerformanceAttributeDescriptor.REAL )
              || (value instanceof String && type != PerformanceAttributeDescriptor.STRING ) )
            {
                Log.write("Missmatch between type and value!");
                return false;
            }

            // check offset, null is allowed
            Log.write("Check offset...");
            try {
                Object offset = thresholdDefinition.getOffset();
                if ( offset != null ) {
                    if ( offset.getClass().isInstance( value ) == false ) {
                        Log.write("Missmatch between type and offset!");
                        return false;
                    }
                }
            } catch ( IllegalStateException e ) {
                Log.write("IllegalStateException");
                // ok, the offset is allowed to be null
            }
            // everything was valid, ok!
            return true;
        } catch ( IllegalStateException e ) {
            // something was wrong when accessing the variables -> not valid
            Log.write("AttributeValidator.isValidThresholdDefinition(): " + e );
            return false;
        }
    }

    /**
     * Validate schedule.
     * @param schedule
     * @return true if valid
     */
    public static boolean isValidSchedule( Schedule schedule ) {

        if ( schedule==null )
            return false;

        Calendar startTime = schedule.getStartTime(),
                 stopTime = schedule.getStopTime();

        if ( startTime != null && stopTime != null ) {
                if ( stopTime.before( startTime ) )
                    return false;
        }

        return true;
    }

    /**
     * Validate state.
     * @param state
     * @return true if valid
     */
    public static boolean isValidState( int state ) {
        if ( state == ThresholdMonitorState.ACTIVE_OFF_DUTY
          || state == ThresholdMonitorState.ACTIVE_ON_DUTY
          || state == ThresholdMonitorState.SUSPENDED ) {
            return true;
        }
        return false;
    }

    /**
     * Validate ThresholdMonitorKey.
     * @param key
     * @return true if valid
     */
    public static boolean isValidThresholdMonitorKey( ThresholdMonitorKey key ) {
        if ( key==null || key.getThresholdMonitorPrimaryKey()==null ) {
            return false;
        }
        return true;
    }

    /**
     * Validate granularity.
     * @param granularity
     * @return true if valid
     */
    public static boolean isValidGranularity( int granularity ) {
        if ( granularity>=0 ) {
            return true;
        }
        return false;
    }
}