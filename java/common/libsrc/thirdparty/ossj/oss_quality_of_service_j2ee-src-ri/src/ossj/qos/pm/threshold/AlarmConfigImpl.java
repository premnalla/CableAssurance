/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.AlarmConfig;
import ossj.qos.util.Log;

import javax.oss.fm.monitor.ProbableCause;

/**
 * Implements the AlarmConfig.
 * @author Henrik Lindstrom, Ericsson
 */
public class AlarmConfigImpl implements AlarmConfig {

    /**
     * The alarm type for this Alarm Config. Default is QUALITY_OF_SERVICE_ALARM.
     */
    private String alarmType;

    /**
     * Perceived level of urgency associated with the alarm. Mandatory.
     */
    private short perceivedSeverity;
    private boolean perceivedSeveritySet = false;

    /**
     * The probable cause associated with the alarm. More detailed then event type.
     * Optional. Default is ProbableCause.UNAVAILABLE.
     */
    private short probableCause;

    /**
     * The specific problem associated with the alarm. Optional. Default is "".
     */
    private String specificProblem;

    /**
     * Construct the AlarmConfig and initalizes member variables. Alarm type is
     * set to <code>DEFAULT_ALARM_TYPE</code>. Probable cause is set to
     * <code>ProbableCause.UNAVAILABLE</code>. Specific problem is set to empty
     * String (<code>""</code>). Perceived Serverity is set to 0.
     *
     */
    public AlarmConfigImpl() {
        Log.write(this,Log.LOG_ALL,"AlarmConfigImpl","called");
        alarmType = DEFAULT_ALARM_TYPE;
        probableCause = ProbableCause.UNAVAILABLE; // default
        perceivedSeverity = 0;
        specificProblem = ""; // default
    }

    public Object clone() {
         Log.write(this,Log.LOG_ALL,"clone()","called");
         Object clone = null;
         try {
            clone = super.clone();
         } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
         }
         return clone;
    }

    public String getAlarmType() {
        Log.write(this,Log.LOG_ALL,"getAlarmType()","alarmType="+alarmType);
        return alarmType;
    }

    public short getPerceivedSeverity() {
        Log.write(this,Log.LOG_ALL,"getPerceivedSeverity()","perceivedSeverity="+perceivedSeverity);
        if ( perceivedSeveritySet==false ) {
            throw new IllegalStateException( "PercievedSeverity not defined!" );
        }
        return perceivedSeverity;
    }

    public short getProbableCause() {
      Log.write(this,Log.LOG_ALL,"getProbableCause()","probableCause="+probableCause);
      return probableCause;
    }

    public String getSpecificProblem() {
        Log.write(this,Log.LOG_ALL,"getSpecificProblem()","specificProblem="+specificProblem);
        return specificProblem;
    }

    public void setAlarmType(String eventType) {
      Log.write(this,Log.LOG_ALL,"setAlarmType()","eventType="+eventType);
      alarmType = eventType;
    }

    public void setPerceivedSeverity(short severity) {
        Log.write(this,Log.LOG_ALL,"setPerceivedSeverity()","severity="+severity);
        if ( AttributeValidator.isValidAlarmConfigPerceivedSeverity( severity )==false ) {
            throw new IllegalArgumentException( "The AlarmConfig PerceivedSeverity is not valid." );
        }
        perceivedSeverity = severity;
        perceivedSeveritySet = true;
    }

    public void setProbableCause(short cause) {
        Log.write(this,Log.LOG_ALL,"setProbableCause()","cause="+cause);

        if ( AttributeValidator.isValidAlarmConfigProbableCause( cause )==false ) {
            throw new IllegalArgumentException( "The AlarmConfig ProbableCause is not valid." );
        }
        probableCause = cause;
    }

    public void setSpecificProblem(String problem) {
        Log.write(this,Log.LOG_ALL,"setSpecificProblem()","problem="+problem);
        if ( AttributeValidator.isValidAlarmConfigSpecificProblem( problem )==false ) {
            throw new IllegalArgumentException( "The specific problem is not valid.");
        }
        specificProblem = problem;
    }

    public String toString() {
        return "AlarmConfig=" + alarmType + ",problem=" + specificProblem
            + ",severity=" + perceivedSeverity + ",cause=" + probableCause;
    }

    // compare for equal all member variables
    public boolean equals( Object o ) {
        if ( o!=null && o instanceof AlarmConfigImpl ) {
            AlarmConfigImpl obj = (AlarmConfigImpl)o;
            // test if all attributes are equal
            if ( obj.perceivedSeverity == this.perceivedSeverity
              && obj.probableCause == this.probableCause ) {
                if ( (obj.alarmType==null && this.alarmType==null)
                  || (obj.alarmType!=null && obj.alarmType.equals( this.alarmType ) ) ) {
                    if ( (obj.specificProblem==null && this.specificProblem==null)
                      || (obj.specificProblem!=null && obj.specificProblem.equals(this.specificProblem)) ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
