package ossj.ttri;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.util.Properties;

/**
 * BeaTraceSimulator Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

// KS 22-08-2003	modified constructor
// KS 22-08-2003	commented out logfile stuff in constructor and method log()

/** This class prints out the log messages to System.out
 *
 */
public class BeaTraceSimulator extends Object implements BeaTrace {

    private String subsystem;
// KS 22-08-2003 switched to TRUE

    private FileOutputStream out; // declare a file output object
    private PrintStream p; // declare a print stream object
    private String fName = null;
    private String isStdoutEnabled = null;

    /* Creates new BeaTraceSimulator */
    public BeaTraceSimulator() {
        this.subsystem = "Unknown";


        try {
            fName = TTHelper.projectProperties.getProperty("LOG_FILE", "ttlog.log");

            isStdoutEnabled =  TTHelper.projectProperties.getProperty("STDOUT_ENABLED", "false");
            //  System.out.println("LOG_FILE value is "+ fName );
            if (fName.trim().length() != 0 && fName != null) {

                out = new FileOutputStream(fName, false);
                p = new PrintStream(new BufferedOutputStream(out), true);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("IOException: " + ex.getMessage());
        }


        /* */
    }

    /** Logs a message. The output is:
     * <CODE>
     * <[DATE]><[subsystem]><[message]>
     * </CODE>
     * @param message Main message which will be logged. Will be accomplished by time of log entry and kind of subsystem
     */
    public void log(String message) {
        StringBuffer sb = new StringBuffer(128);
        sb.append("<");
        java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(), sb, new java.text.FieldPosition(java.text.DateFormat.YEAR_FIELD));
        sb.append("> <Debug> <");
        sb.append(subsystem);
        sb.append("> <");
        sb.append(message);
        sb.append(">");

        if (isStdoutEnabled.equalsIgnoreCase("true"))
            System.out.println(sb.toString());
        if (fName.trim().length() != 0 && fName != null)
            p.println(sb.toString());

    }


    /** Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(String message, Exception e) {
        log(message + "\n" + e.toString());
    }

    /** Setter method for property subSystem
     * @param subSystem new subSystem value
     */
    public void setSubSystem(String subSystem) {
        this.subsystem = subSystem;
    }

    public void logMethodEntry(String method, Object[][] parameter) {
    }

    public void logMethodExit(String method, Object[] returnParameter) {
    }

    public void logThrownException(String message, Exception e) {
    }

    /** Logs an Exception.
     * @param e Exception
     * @param e exception which is about to be thrown
     */
    public void logException(Exception e) {
        log(e.toString());
    }

    public void finalize() {
        try {
            out.close();
            p.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

}
