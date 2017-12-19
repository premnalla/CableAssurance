/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.File;
import ossj.qos.util.LogStream;

/**
 * Basic logger that logs to system.out (and optionally to a file).
 * @author Henrik Lindstrom, Ericsson
 */
public class BasicLog implements Logging {

   private SimpleDateFormat dateFormatter = null;

   /**
    * If log is to be written to a log file also when log is on.
    */
   private static boolean m_LogToFile = false;

   /**
    * Log file prefix.
    */
   public static final String LOG_FILE_PREFIX = "dbg";
   /**
    * Log file suffix.
    */
   public static final String LOG_FILE_SUFFIX = ".log";


    /**
     * Current log level.
     */
    //VP
	//private int currentLogLevel = LogLevels.LOG_ALL;
	private int currentLogLevel = 100;

    /**
     * Initialize a new basic log.
     */
    public BasicLog() {
        dateFormatter = new SimpleDateFormat("yyMMddhhmmss");
    }

    public void log(int logLevel, String message) {
        if ( currentLogLevel<=logLevel ) {
            StringBuffer buf = new StringBuffer();
            buf.append( dateFormatter.format( new Date() ) );
            buf.append('>');
            buf.append( message );
            System.out.println( buf.toString() );
        }
    }

    public void setLogLevel(int logLevel) {
        currentLogLevel = logLevel;
    }

    public int getLogLevel() {
        return currentLogLevel;
    }

   /**
    * @return true if Log is also written to a file
    * @roseuid 3B9604CB032C
    */
   public boolean isLogToFile()
   {
        return m_LogToFile;
   }

   /**
    * Activate logging to file.
    * @param       logToFile
    */
   public static void setLogToFile(boolean logToFile)
   {
        m_LogToFile = logToFile;
        if ( m_LogToFile == false && LogStream.isStarted() == true ) {
            LogStream.stop();
        } else if (  LogStream.isStarted() == false ) {
            startLogToFile();
        }
   }


   /**
    *  Start the logging to file.
    */
   private static void startLogToFile() {
     try {
        // create new log file
        File logFile = File.createTempFile( LOG_FILE_PREFIX, LOG_FILE_SUFFIX );
        LogStream.start( logFile );
     } catch ( IOException e ) {
        System.err.println("Log.setLogOn(), exception="+e);
        m_LogToFile = false;
     }
   }

}
