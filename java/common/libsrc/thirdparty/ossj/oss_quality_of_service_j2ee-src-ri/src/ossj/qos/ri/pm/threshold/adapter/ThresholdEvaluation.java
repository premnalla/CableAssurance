/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.oss.pm.threshold.*;
//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;

/**
 * Evaluates if a threshold is crossed or cleared. Has an overloaded method
 * <code>evaluateThreshold()</code> for evaluating thresholds of INTEGER and
 * REAL type.
 *
 * @author Henrik Lindstrom, Ericsson
 */
class ThresholdEvaluation {

    /**
     * Evaluate threshold based on an integer. Returns THRESHOLD_CROSSED if the
     * threshold was crossed, THRESHOLD_CLEARED if it was cleared or
     * THRESHOLD_NOT_CROSSED_OR_CLEARED otherwise.
     *
     * @param performanceValue received performance data value
     * @param thresholdValue value for threshold crossing
     * @param thresholdOffset offset for threshold clearing
     * @param direction the direction of the threshold
     * @param previousStatus if threshold has been crossed before
     * @return if threshold was crossed, cleared or not affected
     * @exception ThresholdMonitorException is thrown if the threshold can not
     * be evaluated.
     */
    static int evaluateThreshold( int performanceValue,
                                  int thresholdValue,
                                  int thresholdOffset,
                                  int direction,
                                  int previousStatus ) throws ThresholdMonitorException {
        // If threshold crossing direction is FALLING.
        if ( direction == ThresholdDirection.FALLING ) {
            Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","FALLING");
            // If performance value is under the threshold (and has not been before).
            if ( performanceValue <= thresholdValue
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CROSSED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","THRESHOLD_CROSSED");
                return ThresholdMonitorStatus.THRESHOLD_CROSSED;
            }
            // If performance value is over the threshold incl offset (but has crossed it before).
            else if ( performanceValue > (thresholdValue+thresholdOffset)
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CLEARED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","THRESHOLD_CLEARED");
                return ThresholdMonitorStatus.THRESHOLD_CLEARED;

            } else {
                if ( previousStatus == ThresholdMonitorStatus.THRESHOLD_CROSSED ) {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(int)()","Threshold has already been crossed.");
                } else {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(int)()","Threshold is not violated.");
                }
                return ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED;
            }
        }
        // Threshold crossing direction is RISING.
        else if ( direction == ThresholdDirection.RISING ) {
            Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","RISING");
            // If performance value is over the threshold (and has not been before).
            if ( performanceValue >= thresholdValue
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CROSSED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","THRESHOLD_CROSSED");
                return ThresholdMonitorStatus.THRESHOLD_CROSSED;
            }
            // If performance value is under the threshold incl offset (but has crossed it before).
            else if ( performanceValue < (thresholdValue+thresholdOffset)
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CLEARED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(int)()","THRESHOLD_CLEARED");
                return ThresholdMonitorStatus.THRESHOLD_CLEARED;

            } else {
                if ( previousStatus == ThresholdMonitorStatus.THRESHOLD_CROSSED ) {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(int)()","Threshold has already been crossed.");
                } else {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(int)()","Threshold is not violated.");
                }
                return ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED;
            }
        } else {
            Log.write(ThresholdEvaluation.class,Log.LOG_MAJOR,"evaluateThreshold(int)()",MessageConstants.ERR_NOT_SUPPORTED_DIRECTION);
            throw new ThresholdMonitorException( MessageConstants.ERR_NOT_SUPPORTED_DIRECTION );
        }
    }

    /**
     * Evaluate threshold based on a double. Returns THRESHOLD_CROSSED if the
     * threshold was crossed, THRESHOLD_CLEARED if it was cleared or
     * THRESHOLD_NOT_CROSSED_OR_CLEARED otherwise.
     *
     * @param performanceValue received performance data value
     * @param thresholdValue value for threshold crossing
     * @param thresholdOffset offset for threshold clearing
     * @param direction the direction of the threshold
     * @param previousStatus if threshold has been crossed before
     * @return if threshold was crossed, cleared or not affected
     * @exception ThresholdMonitorException is thrown if the threshold can not
     * be evaluated.
     */
    static int evaluateThreshold( double performanceValue,
                                  double thresholdValue,
                                  double thresholdOffset,
                                  double direction,
                                  double previousStatus ) throws ThresholdMonitorException {
        // If threshold crossing direction is FALLING.
        if ( direction == ThresholdDirection.FALLING ) {
            Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","FALLING");
            // If performance value is under the threshold (and has not been before).
            if ( performanceValue <= thresholdValue
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CROSSED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","THRESHOLD_CROSSED");
                return ThresholdMonitorStatus.THRESHOLD_CROSSED;
            }
            // If performance value is over the threshold incl offset (but has crossed it before).
            else if ( performanceValue > (thresholdValue+thresholdOffset)
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CLEARED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","THRESHOLD_CLEARED");
                return ThresholdMonitorStatus.THRESHOLD_CLEARED;

            } else {
                if ( previousStatus == ThresholdMonitorStatus.THRESHOLD_CROSSED ) {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(double)()","Threshold has already been crossed.");
                } else {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(double)()","Threshold is not violated.");
                }
                return ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED;
            }
        }
        // Threshold crossing direction is RISING.
        else if ( direction == ThresholdDirection.RISING ) {
            Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","RISING");
            // If performance value is over the threshold (and has not been before).
            if ( performanceValue >= thresholdValue
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CROSSED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","THRESHOLD_CROSSED");
                return ThresholdMonitorStatus.THRESHOLD_CROSSED;
            }
            // If performance value is under the threshold incl offset (but has crossed it before).
            else if ( performanceValue < (thresholdValue+thresholdOffset)
              && previousStatus != ThresholdMonitorStatus.THRESHOLD_CLEARED ) {

                Log.write(ThresholdEvaluation.class,Log.LOG_ALL,"evaluateThreshold(double)()","THRESHOLD_CLEARED");
                return ThresholdMonitorStatus.THRESHOLD_CLEARED;

            } else {
                if ( previousStatus == ThresholdMonitorStatus.THRESHOLD_CROSSED ) {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(double)()","Threshold has already been crossed.");
                } else {
                    Log.write(ThresholdEvaluation.class,Log.LOG_ALL,
                        "evaluateThreshold(double)()","Threshold is not violated.");
                }
                return ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED;
            }
        } else {
            Log.write(ThresholdEvaluation.class,Log.LOG_MAJOR,"evaluateThreshold(double)()",MessageConstants.ERR_NOT_SUPPORTED_DIRECTION);
            throw new ThresholdMonitorException( MessageConstants.ERR_NOT_SUPPORTED_DIRECTION );
        }
    }
}
