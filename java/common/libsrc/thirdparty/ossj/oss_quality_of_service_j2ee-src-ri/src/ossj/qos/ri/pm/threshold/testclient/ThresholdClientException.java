/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.testclient;

/**
 * Threshold client exception.
 */
public class ThresholdClientException extends Exception {

    public ThresholdClientException() {
        super();
    }

    public ThresholdClientException( String msg) {
        super(msg);
    }
}