package ossj.qos.ri.pm.measurement.eis;


import java.util.GregorianCalendar;
import java.io.*;


/**
 * This is a log class used by the EIS Simulator.
 * <p>
 * Examples of usage:
 * <p>
 *
 * <pre>
 * // Create a Log object with loglevel 3 (default) that will output
 * // log messages to stdout.
 * Log myLog = new Log()
 * // The first parameter is the loglevel. If the parameter is below or
 * // equal to the Log object loglevel the log message will be logged.
 * myLog.log( 2, "This string will be written to the log" );
 *
 * // Create a Log Object with loglevel 3
 * // that will send log messages to both file and stdout.
 * Log myLog = new Log( "c:/logfile.txt" );
 *
 * // Create a Log Object with loglevel 5
 * // that will send log messages to both file and stdout.
 * Log myLog = new Log( "c:/logfile.txt", 5 );
 * // The following log message will NOT be sent to file or stdout.
 * myLog.log( 3, "This message will not appear in the log since 3 < 5." );
 * </pre>
 * <p>
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */


public class Log {


  /** <code>logLevel</code> is used to filter out what should be sent to
   * the log and not. If the incoming loglevel is equal or less to the logLevel
   * of the object the log message will be sent to the log.
   */
  private static int logLevel = 3;

  /** <code>logFile</code> specifies the path and name of the log file.
   */
  private static String logFile = null;

  /** This constructor creates a <code>Log</code> object with default logLevel
   *  and all log messages will be sent to stdout.<p>
   *
   */
  public Log() {
  }

  /** This constructor creates a <code>Log</code> object with default logLevel.
   *  Log messages will be sent to stdout and stored in the specified
   *  log file.<p>
   * @param logFile   specifies the path and name of the log file
   */

  public Log( String logFile ) {
    this.logFile = logFile;
  }

  /** This constructor creates a <code>Log</code> object with specified logLevel.
   *  All log messages will be sent to stdout and stored in the specified
   *  log file.<p>
   * @param logFile   specifies the path and name of the log file
   * @param logLevel  log level for <code>Log</code> object. A low log level
   * will give a log of high detail.
   */

  public Log( String logFile, String logLevel ) {
    this.logFile = logFile;
    this.logLevel = Integer.parseInt( logLevel );
  }


  /** Always log a message independant of the current log level.<p>
   * @param logMessage  log message that should be sent to log
   */

  public void log(String logMessage) {
    log(logMessage, logLevel);
  }

  /** Log a message if the parameter <code>logLevel</code> is larger then or
   *  equal to the current log level.<p>
   *
   * @param logMessage  log message that should be sent to log
   * @param logLevel    log level of log message
   */

  public void log(String logMessage, int logLevel) {

    if (this.logLevel <= logLevel ) {
      GregorianCalendar currentTime = new GregorianCalendar();
      String logMsg = new String("[" + currentTime.getTime() + "] " + logMessage);

      System.out.println(logMsg);

      if ( logFile != null ) {
        try {
          FileWriter out = new FileWriter(logFile, true);

          out.write(logMsg);
          out.write(System.getProperty("line.separator"));
          out.close();
        }
        catch ( Exception e ) {
          System.out.println("Error writing to log " + logFile );
        }
      }
    }
  }
}
