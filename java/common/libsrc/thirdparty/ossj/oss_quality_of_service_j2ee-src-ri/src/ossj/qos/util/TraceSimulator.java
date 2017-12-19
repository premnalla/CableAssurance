package ossj.qos.util;

/** 
 * TraceSimulator.java
 * Class taken from Nokia SA Implementation
 * This class prints out the log messages to System.out
 *
 * @author aebbert from Nokia
 * @version 1.0
 */
public class TraceSimulator extends Object implements Trace {

    private String subsystem;

    /** 
     * Creates new TraceSimulator 
     */
    public TraceSimulator() {
        this.subsystem = "Unknown";
    }

    /** 
     * Logs a message. The output is:
     * <CODE>
     * <[DATE]><[subsystem]><[message]>
     * </CODE>
     * @param message Main message which will be logged. Will be accomplished by time of log entry and kind of subsystem
     */
    public void log(String message) {
        /*StringBuffer sb = new StringBuffer(128);
        sb.append("<");
        java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(), sb, new java.text.FieldPosition(java.text.DateFormat.YEAR_FIELD));
        sb.append("> <Debug> <");
        sb.append(subsystem);
        sb.append("> <");
        sb.append(message);
        sb.append(">");*/
        System.out.println(message);
    }

    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(String message, Exception e) {
        log(message+"\n"+e.toString());
    }

    /**
     * Setter method for property subSystem
     * @param subSystem new subSystem value
     */
    public void setSubSystem(String subSystem) {
        this.subsystem = subSystem;
    }

    public void logMethodEntry(String method,Object[][] parameter) {
    }

    public void logMethodExit(String method,Object[] returnParameter) {
    }

    public void logThrownException(String message,Exception e) {
       logException(message,e);
    }

    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(Exception e) {
        log(e.toString());
    }

}
