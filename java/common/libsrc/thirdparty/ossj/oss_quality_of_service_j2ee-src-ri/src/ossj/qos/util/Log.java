//Source file: H:\\work\\qosapi\\threshold\\src\\com\\ericsson\\oss\\pm\\util\\Log.java

//package ossj.qos.pm.util;

package ossj.qos.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.IOException;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Log facility. Instead of using System.err or System.out this
 * class should be used by calling the appropiate write method.
 *
 * <p>Log can be turned on and off by calling method <code>setLogOn()</code>.
 *
 * <p>Log can also be redirected to a file by calling <code>setLogToFile()</code>.
 * The file is stored in current TEMP directory as defined by the system. The name of
 * the file is <code>LOG_FILE_PREFIX</code>+number+<code>LOG_FILE_SUFFIX</code>,
 * for example "log123.log".
 *
 * <p>It is possible to control the level of logging by setting a log level.
 * The defined levels are:<br>
 * <ul>
 * <li><code>LOG_MAJOR</code>
 * <li><code>LOG_MINOR</code> (includes <code>LOG_MAJOR</code> level)
 * <li><code>LOG_ALL</code> (includes <code>LOG_MAJOR</code> and <code>LOG_MINOR</code> levels)
 * </ul>
 * </code>
 * Calling write methods that do not have log level specified is the same as
 * using the <code>LOG_ALL</code> level.
 *
 * <p>Example:
 * <pre>
 *   void public aMethod( int value ) {
 *     Debug.write(this,DEBUG_MINOR,"aMethod()","called with value="+value);
 *     // do some stuff
 *   }
 * </pre>
 *
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Henrik Lindstrom
 * @version 0.2
 */


public class Log
{

   /**
    * Log level for ALL log events.
    */
   public static final int LOG_ALL = 3;

   /**
    * Current log level.
    */
   private static int m_LogLevel = LOG_ALL;

   /**
    * If log is active or not, i.e. calls to write methods actually
    * produce any output or not.
    */
   //VP: private static boolean m_LogOn = true;
   private static boolean m_LogOn = false;

   /**
    * If log is to be written to a log file also when log is on.
    */
   private static boolean m_LogToFile = false;

   /**
    * If time should be displayed together with log information.
    */
   private static boolean m_ShowTime = true;

   /**
    * null word for sender object that are null.
    */
   private static final String NULL = "null";

   /**
    * Separator text between class, method and log text.
    */
   private static final String SEPARATOR = ": ";

   /**
    * Log file prefix.
    */
   public static final String LOG_FILE_PREFIX = "dbg";
   /**
    * Log file suffix.
    */
   public static final String LOG_FILE_SUFFIX = ".log";

   /**
    * Log level for MAJOR log events.
    */
   public static final int LOG_MAJOR = 1;

   /**
    * Log level for MINOR log events.
    */
   public static final int LOG_MINOR = 2;

   /**
    * If true then only the last part of the class name is written in the log.
    */
   private static boolean m_UseShortClassName = true;

   /**
    * Returns the current log level.
    * @return log level
    * @roseuid 3B96074F03BF
    */
   public int getLogLevel()
   {
        return m_LogLevel;
   }

   /**
    * Returns the full classname for 'sender' in all cases accept when sender is an
    * instance of class String in which case the String text value is returned. If
    * sender is null then the string "null" is returned.
    * @param sender any class
    * @return name of class
    * @roseuid 3B96021F0225
    */
   private static String getSenderName(Object sender)
   {
      String senderName = null;
      if ( sender == null ) {
      	  senderName = NULL;
      } else if ( sender instanceof String ) {
          senderName = (String)sender;
      } else {
          senderName = sender.getClass().getName();
          if (  m_UseShortClassName == true ) {
            int lastIndex = senderName.lastIndexOf('.');
            if ( lastIndex != -1 && lastIndex < senderName.length() ) {
                senderName = senderName.substring( lastIndex+1 );
            }
          }
      }
      return senderName;
   }

   /**
    * Returns true if log is on.
    * @return boolean
    * @roseuid 3B96021F01D4
    */
   public static boolean isLogOn()
   {
        return m_LogOn;
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
    * @return true if time is displayed together with log information.
    * @roseuid 3B9607100350
    */
   public boolean isDisplayTime()
   {
        return m_ShowTime;
   }

   /**
    * The actual method which sends the log information to the correct
    * output device.
    * @param       totalLogText the text to write
    * @roseuid 3B96021F0301
    */
   private static void realWrite(String totalLogText)
   {
        /*
         * Make sure log is enabled
         */
        //if( m_LogOn == true ) {
            StringBuffer buf = new StringBuffer();
            // Check to see if timing is on
            if( m_ShowTime == true ) {
                buf.append( (new SimpleDateFormat()).format( new Date() ) );
            }
            // Print the actual log message
            buf.append( SEPARATOR );
            buf.append( totalLogText );

            System.out.println( buf ); // System.out might be redirected to file
        //}
   }

   /**
    * Set the Log to only write the short class name. This is everything after
    * the last '.' character. This makes the log more compact.
    * @param useShortClassName if true then a shorter class name is written by
    * the write methods.
    */
   public static void setUseShortClassName( boolean useShortClassName ) {
     m_UseShortClassName = useShortClassName;
   }

   /**
    * @param true if shorter class names are used
    */
   public static boolean isUseShortClassName() { return m_UseShortClassName; }

   /**
    * Set the lowest log level to display.
    * @param       newLevel
    * @roseuid 3B96021F02C6
    */
   public static void setLogLevel(int newLevel)
   {
		System.out.println("LOG: set level to:"+newLevel);
        m_LogLevel = newLevel;
   }

   /**
    * Set log mode.
    * @param logOn if true the log is activated otherwise inactivated.
    * @roseuid 3B96021F02B1
    */
   public static void setLogOn(boolean logOn)
   {
        m_LogOn = logOn;
        if ( logOn ) {
	        if ( m_LogToFile == true && LogStream.isStarted() == false ) {
                startLogToFile();
	        }
	      } else { // log is off
	        if ( m_LogToFile == true ) {
	            LogStream.stop(); // close log stream now
	        }
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
        Log.write("Log.setLogOn(), exception="+e);
		System.out.println("Unable to start Logging: "+e);
        m_LogToFile = false;
     }
   }
   /**
    * Activate logging to file.
    * @param       logToFile
    * @roseuid 3B96021F0316
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
    * Set if time information shall be displayed or not in log.
    * @param displayTime true if time shall be displayed
    * @roseuid 3B96021F02E4
    */
   public static void setDisplayTime(boolean displayTime)
   {
        m_ShowTime = displayTime;
   }

   /**
    * Write text to the log output device. It is adviced to use some of the
    * other more detailed log methods with class and method arguments.
    * Log is only written if log level is set to LOG_ALL.
    * @param       logText
    * @roseuid 3B96021F01E8
    */
   public static void write(String logText)
   {
        if( m_LogOn == true && m_LogLevel == LOG_ALL ) {
	        realWrite( logText );
	      }
   }

   /**
    * Write class name, method and text to the log output device.
    * Log is only written if log level is set to LOG_ALL.
    * @param       sender
    * @param       method
    * @param       logText
    * @roseuid 3B96021F0242
    */
   public static void write(Object sender, String method, String logText)
   {
        if( m_LogOn == true && m_LogLevel == LOG_ALL ) {
            realWrite( getSenderName( sender ) + SEPARATOR + method + SEPARATOR + logText );
        }
   }

   /**
    * Write class name, method and text to the log output device.
    * Depending on the logLevel the log information might be sent
    * to the output.
    * @param       sender
    * @param       logLevel
    * @param       method
    * @param       logText
    * @roseuid 3B96021F0289
    */
   public static void write(Object sender, int logLevel, String method, String logText)
   {
        if( m_LogOn == true && logLevel <= m_LogLevel ) {
	        realWrite( getSenderName( sender ) + SEPARATOR + method + SEPARATOR + logText );
	    }
   }
}
