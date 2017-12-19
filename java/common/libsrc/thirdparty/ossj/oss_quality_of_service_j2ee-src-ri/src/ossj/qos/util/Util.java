/*
 * Copyright © 2003 Sun Microsystems, Inc.
 * All rights reserved. Use is subject to license terms.
 */

package ossj.qos.util;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.Date;
import java.util.Collection;
import java.util.Arrays;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Utility class with some utility methods. (says it all doesn't it... :-)
 *
 * @author Henrik Lindström
 * @version 0.2
 */
public final class Util {

    /**
     * Random generator. Needed for generating unique primary keys.
     */
    private static Random rndGenerator = null;
    /**
     * Cached value for local InetAddress in HEX.
     */
    private static String inetHex = null;

    /**
     *  Try to clone any object if possible. If it is not possible the original
     *  object is returned.
     *  @param obj object to clone
     *  @return cloned object or obj if not possible
     */
    static public Object clone( Object obj ) throws CloneNotSupportedException {
        Object clone = null;
        if ( obj != null ) {
		  if (obj.getClass().isArray()){
			clone((Object[])obj);
          } else {
            if ( obj instanceof Cloneable ) {
                try {
                    Method method = obj.getClass().getMethod("clone",null);
                    clone = method.invoke(obj,null);
                }
                catch ( Exception e ) {
                    System.out.print( "Problem cloning " + obj.getClass().getName() );
					if (obj.getClass().isArray())  {
					System.out.println ("  number of Element:" + ((Object[])obj).length); 
					System.out.println (printObject((Object[])obj));
					}
					e.printStackTrace();
                }
            }
            else if (obj instanceof String){
			   clone = new String((String)obj);
			} else {
                throw new CloneNotSupportedException("for class: "+obj.getClass());
            }
	      }
        }
        return clone;
    }

	static public Object clone(Object[] obj)throws CloneNotSupportedException {
	   Object[] array = new Object[obj.length];
	   for (int i = obj.length -1 ;i>=0;i--)
		  array[i]=clone(obj[i]);
	  return array;
    }

    static public String printObject( Object[] value )  {
        StringBuffer buffer = new StringBuffer();
        if ( value != null && value.length > 0 ) {
            buffer.append( "<< " + value.getClass().getName() + " []  >> \n" );
            //Object arrayObj = value[0];
            //buffer.append( "<< " + arrayObj.getClass().getName() + " []  >> \n" );
            for ( int i=0; i<value.length; i++ ) {
                String index = new String ( " [ " + i + " ] = " );
                Object obj = value[i];
                buffer.append( Util.rightJustify( 30, index) );
                if ( obj != null ) {
                    buffer.append( printObject(obj) );
                }
                else {
                    buffer.append( "null \n");
                }
            }
        }
        else {
            buffer.append("null \n");
        }
        return buffer.toString();
    }
 
    static public String printObject( Object value )  {
        StringBuffer buffer = new StringBuffer();
        if ( value != null ) {
            if ( value instanceof String || value instanceof Integer || value instanceof Short ||
            value instanceof Long || value instanceof Boolean || value instanceof Character ||
            value instanceof Float || value instanceof Double ) {
                buffer.append( value.toString() + "\n" );
            }
            else if ( value instanceof Date ) {
                buffer.append( Util.convertUTCTimeString( (Date) value ) + "\n" );
            }
            else if ( value.getClass().isArray() ) {
                buffer.append( Util.printObject( (Object[]) value ) );
            }
            else {
                buffer.append( "<<" + value.getClass().getName() + ">> \n" );
                buffer.append( value.toString() );
            }
        }
        else {
            buffer.append("null \n");
        }
        return buffer.toString();
    }
 

    /**
     * Creates a GUID for an object. GUID stands for Globally Unique IDentifier
     * a.k.a. UUIDs (Universally Unique IDentifier). The GUID (128-bits) is
     * guaranteed to be unique. Implementation follows the EJB primary key
     * design pattern, see The Server Side, design patterns.
     * <p>
     * The GUID is built up like this:<br>
     * <pre>
     *  System.currentTimeMillis()  System.identityHashCode(object)
     *  |                           |
     *  XXXXXXXX      XXXXXXXX      XXXXXXXXX      XXXXXXXX
     *                |                            |
     *                IP-address                   Random number
     * </pre>
     * <p>
     * Note, the first call to this method might be very slow (seconds) because
     * the SecureRandom class is initialized.
     * <p>
     * For details see:<br>
     * <a href="http://www.theserverside.com">http://www.theserverside.com</a><br>
     * <a href="http://casl.csa.iisc.ernet.in/Standards/internet-drafts/draft-leach-uuids-guids-01.txt">http://casl.csa.iisc.ernet.in/Standards/internet-drafts/draft-leach-uuids-guids-01.txt</a>
     */
    public static synchronized String makeGUID(Object object)
    {
       Log.write("Util",Log.LOG_ALL,"makeGUID()","called");

        // if necessary initialize random generator
        if ( rndGenerator == null ) {
            // long start = System.currentTimeMillis();
            rndGenerator = new Random();
            // Log.write("Util",Log.LOG_ALL,"makeGUID()","SecureRandom() call took "
            //    + (System.currentTimeMillis()-startTime) );

            // Initialize inet address (cache information for subsequent calls)
            try {
                InetAddress inet = InetAddress.getLocalHost();
                byte[] bytes = inet.getAddress();
                StringBuffer hex = new StringBuffer();
                String s = null;
                for (int i=0;i<bytes.length;i++) {
                    s = Integer.toHexString( bytes[i]&0xFF ); // 0xFF is because InetAddress.getAddress() returns the bytes in network byte order
                    if ( s.length() == 1 ) { // add leading zero (0)
                        hex.append( '0' );
                    }
                    hex.append( s );
                }
                inetHex = hex.toString();
            } catch ( UnknownHostException e ) {
                Log.write("Util",Log.LOG_MAJOR,"makeGUID()","exception="+e);
            }
            Log.write("ip="+inetHex);
        } // if seeder null

        // build up the primary key... (32 characters)
        StringBuffer pkBuf = new StringBuffer( "000000000000000000000000000000000" );

        // time (low part of the long value)
        String time = Integer.toHexString((int) System.currentTimeMillis() & 0xFFFFFFFF);
        pkBuf.insert(0+8-time.length(),time);

        //ip (pre-defined 8 digits)
        pkBuf.insert(8,inetHex);

        // hashcode
        String hash = Integer.toHexString( System.identityHashCode( object ) );
        pkBuf.insert(16+8-hash.length(), hash );

        // random
        String rnd = Integer.toHexString( rndGenerator.nextInt() );
        pkBuf.insert(24+8-rnd.length(),rnd);

        pkBuf.setLength(32); // might have overflow of zeros

        Log.write("GUID="+pkBuf.toString());
        return pkBuf.toString();
    }

    static public boolean isEqual ( Object objectA, Object objectB ) {

        return ( ( objectA != null && objectB != null && objectA.getClass().isArray() &&
        objectB.getClass().isArray() ) ? Util.compareArrays( (Object[]) objectA, (Object[]) objectB ) :
        Util.isObjectEqual( objectA, objectB ) );
    }

    static public boolean isObjectEqual ( Object objectA, Object objectB ) {
        return (objectA == null ? objectB == null : objectA.equals( objectB ) );
    }

    public static boolean compareArrays( Object[] attributeValue1, Object[] attributeValue2 ) {
        boolean isEqual = true;
        if( attributeValue1.length != attributeValue2.length ) {
            isEqual = false;
        }
        else {
            Collection col1 = Arrays.asList(attributeValue1);
            Collection col2 = Arrays.asList(attributeValue2);
 
            if( col1.containsAll(col2) == false ) {
                isEqual = false;
            }
        }
        return isEqual;
    }

    public static String rightJustify( int width, String field ) {
        StringBuffer outString = new StringBuffer();
        if ( field != null ) {
            if ( field.length() < width ) {
                char[] blank = new char [ width - field.length() ];
                Arrays.fill( blank, ' ' );
                outString.append( blank );
            }
            outString.append( field );
        }
        return outString.toString();
    }

 
    static public String convertUTCTimeString( Date date ) {

        String timeString = null;

        if ( date != null ) {
            Calendar calendar = Calendar.getInstance();
            SimpleTimeZone utc = new SimpleTimeZone(0, "UTC");

            calendar.setTimeZone(utc);
            calendar.setTime( date );
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.setCalendar(calendar);
            formatter.applyPattern("yyyyMMddHHmmss.S'Z'");
            timeString = formatter.format( calendar.getTime() );
        }
        return timeString;
    }

    static public Trace createLog(String subSystem) {

        Trace myLog = null;

        myLog = new TraceSimulator();
        myLog.setSubSystem(subSystem);

        return myLog;
    }

}
