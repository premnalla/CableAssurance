/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.util;

public interface Logging extends LogLevels {
    /**
     * Logs the specified message. Message is logged if the log level is
     * set to a number higher or equal to 'logLevel'.
     */
    public void log( int logLevel, String message );

    /**
     * Set current log level.
     * @param logLevel
     * @see ossj.qos.pm.util.LogLevels
     */
    public void setLogLevel( int logLevel );

    /**
     * Get current log level.
     * @see ossj.qos.pm.util.LogLevels
     */
    public int getLogLevel();
}