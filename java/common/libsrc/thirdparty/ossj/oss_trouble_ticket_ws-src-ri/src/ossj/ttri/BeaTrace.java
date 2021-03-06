package ossj.ttri;

/**
 * BeaTrace Helper Class
 * This interface provides a general logging interface. This can be supported
 * by a native logging mechanism, like the one Weblogic Server offers, or a
 * generic logging to System.out
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public interface BeaTrace {

    /** Setter method for property subSystem
     * @param subSystem new subSystem value
     */
    public void setSubSystem(String subSystem);

    /** Logs a message. The output may vary from implementation to implementation
     * @param message Main message which will be logged. Will be accomplished by time of log entry and kind of subsystem
     */
    public void log(String message);

    public void logMethodEntry(String method, Object[][] parameter);

    public void logMethodExit(String method, Object[] returnParameter);

    /** Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(String message, Exception e);

    /** Logs an Exception.
     * @param e Exception
     * @param e exception which is about to be thrown
     */
    public void logException(Exception e);

    public void logThrownException(String message, Exception e);
}
