
//package ossj.qos.pm.util;

package ossj.qos.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Captures standard output in a log file. It copies into the log file
 * characters printed to standard output and standard error. Log is started by
 * calling start() and stoped by calling stop().
 *
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Henrik Lindstrom
 * @version 0.2
 */


public class LogStream extends PrintStream
{
   private static boolean isStarted = false;
   private static OutputStream logfile;
   private static PrintStream oldStdout;
   private static PrintStream oldStderr;

   /**
    * Create a new LogStream.
    * @param ps the underlying PrintStream
    * @roseuid 3B96022002DA
    */
   public LogStream(PrintStream ps)
   {
        super( ps );
   }

   /**
    * @return true if LogStream is started (active)
    * @roseuid 3B9602200384
    */
   public static boolean isStarted()
   {
        return isStarted;
   }

   /**
    * Start copying stdout and stderr to the file f.
    * @param f file to write log to.@throws java.io.IOException
    * @roseuid 3B9602200316
    */
   public static void start(File f) throws IOException
   {
        if ( isStarted ) {
            return;
        }

        oldStdout = System.out;
        oldStderr = System.err;
        // create open/logfile
        logfile = new PrintStream(
            new BufferedOutputStream(
            new FileOutputStream( f ) ), true );

        // start redirect
        System.setOut( new LogStream( System.out ) );
        System.setErr( new LogStream( System.err ) );

        isStarted=true;
   }

   /**
    * Restore the original settings
    * @roseuid 3B9602200398
    */
   public static void stop()
   {
        if ( oldStdout != null ) {
            System.setOut( oldStdout );
        }
        if ( oldStderr != null ) {
            System.setErr( oldStderr );
        }
        if ( logfile != null ) {
            try {
                logfile.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        isStarted=false;
   }

   /**
    * override
    * @param b
    * @roseuid 3B96022003B6
    */
   public void write(int b)
   {
        try {
            logfile.write( b );
        } catch ( Exception e ) {
            e.printStackTrace();
            setError();
        }
        super.write( b );
   }

   /**
    * override
    * @param buf[]
    * @param off
    * @param len
    * @roseuid 3B96022003CB
    */
   public void write(byte buf[], int off, int len)
   {
        try {
            logfile.write( buf, off, len );
        } catch ( Exception e ) {
            e.printStackTrace();
            setError();
        }
        super.write( buf, off, len );
   }
}
