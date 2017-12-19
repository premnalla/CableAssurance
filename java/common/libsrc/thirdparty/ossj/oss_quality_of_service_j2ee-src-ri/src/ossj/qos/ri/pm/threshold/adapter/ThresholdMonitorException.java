/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

/**
 * Threshold Monitor Exception. Used internally in the threshold monitor
 * implementation.
 */
public class ThresholdMonitorException extends Exception {
    public ThresholdMonitorException() {}
    public ThresholdMonitorException( String message ) {
        super( message );
    }
}