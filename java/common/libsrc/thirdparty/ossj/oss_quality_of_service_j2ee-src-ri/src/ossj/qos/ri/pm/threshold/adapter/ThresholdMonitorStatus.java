/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

/**
 * Different status for threshold monitors. Only the <code>THRESHOLD_CLEARED</code>
 * and the <code>THRESHOLD_CROSSED</code> constants are used as status in the
 * entity bean. The third constant <code>THRESHOLD_NOT_CROSSED_OR_CLEARED</code>
 * is only used when a threshold is evaluated and is neither cleared or crossed.
 *
 * @author Henrik Lindstrom, Ericsson
 */
public interface ThresholdMonitorStatus {

    /**
     * Indicates the status when a threshold is neither crossed or cleared.
     */
    public static final int THRESHOLD_NOT_CROSSED_OR_CLEARED = -1;

    /**
     * Indicates that a threshold monitor is cleared, i.e. its threshold value
     * has not been reached. The threshold also reach this status after a
     * crossed threshold is cleared again.
     */
    public static final int THRESHOLD_CLEARED = 1;

    /**
     * Indicates that a threshold monitor has crossed its threshold value.
     */
    public static final int THRESHOLD_CROSSED = 2;
}