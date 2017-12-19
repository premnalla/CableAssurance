package ossj.ttri;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  ger Class
 *
 * Singleton Logger class which could be used both by the TCK client and the TTRI.
 * The Logger is an application of the Bridge design pattern  (see "Design Patterns" , GoF).
 * The Logger decouples its clients from the implementation of the logging functionality.
 * Depending on whether the TCK client or the TTRI is using the Logger
 * the WLS 6.x logging or the logging simulator is used, i.e. the implementation of the
 * Logger varies.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class Logger {

    private static BeaTrace myLog = null;

    private static String isLoggingEnabled = "true";

    static {

        isLoggingEnabled = TTHelper.projectProperties.getProperty("LOGGING_ENABLED", "true");
        // Simulate logging until migration to Java logging complete
        if (myLog == null) {
            // Simulate logging
            myLog = new BeaTraceSimulator();
        }
        myLog.setSubSystem("OSS/J TT");
        //VP System.out.println("Created new logger, using "+loggingVersion);
        //log("Created new logger, using " + loggingVersion);

    }

    protected Logger() {

    }


    public static void setSubSystem(String subSystem) {
        myLog.setSubSystem(subSystem);
    }

    public static void log(String message) {
        //System.out.println("Logger.log() isLoggingEnabled "+ isLoggingEnabled);
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.log(message);
    }

    public static void logException(String message, Exception e) {
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.logException(message, e);
    }

    public static void logException(Exception e) {
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.logException(e);
    }

    public static void logThrownException(String message, Exception e) {
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.logThrownException(message, e);
    }

    public static void logMethodEntry(String method, Object[][] parameter) {
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.logMethodEntry(method, parameter);
    }

    public static void logMethodExit(String method, Object[] returnParameter) {
        if (isLoggingEnabled.equalsIgnoreCase("true")) myLog.logMethodExit(method, returnParameter);
    }

}
