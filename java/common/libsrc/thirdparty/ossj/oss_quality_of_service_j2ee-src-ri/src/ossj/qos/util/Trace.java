package ossj.qos.util;

/** 
 * Trace
 *
 * This interface provides a general logging interface. This can be supported
 * by a native logging mechanism, like the one Weblogic Server offers, or a
 * generic logging to System.out. Class taken from Nokia's SA implementation.
 *
 * @author aebbert
 * @version 1.0
 */

public interface Trace {

    /**
     * Setter method for property subSystem
     * @param subSystem new subSystem value
     */
    public void setSubSystem(String subSystem);

    /** 
     * Logs a message. The output may vary from implementation to implementation
     * @param message Main message which will be logged. 
     * Will be accomplished by time of log entry and kind of subsystem
     */
    public void log(String message);

    public void logMethodEntry(String method, Object[][] parameter);

    public void logMethodExit(String method, Object[] returnParameter);


    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(String message, Exception e);

    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(Exception e);


    public void logThrownException(String message, Exception e);

}
