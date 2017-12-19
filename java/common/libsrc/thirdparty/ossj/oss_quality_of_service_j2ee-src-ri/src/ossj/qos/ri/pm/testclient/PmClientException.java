package ossj.qos.ri.pm.testclient;
/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */

/**
 * Threshold client exception.
 */
public class PmClientException extends Exception {

    public PmClientException() {
        super();
    }

    public PmClientException( String msg) {
        super(msg);
    }
}
