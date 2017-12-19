package ossj.qos.fmri;

import ossj.qos.util.Trace;

/**
 * BeaTrace60.java
 * 
 * This class uses Weblogic's NonCatalogLogger class to print debug output.
 * Note that debug output is only visible in standard out, if you turn it
 * on in Weblogic Server's admin console. This class was taken from Nokia's
 * SA implemenation.
 *
 * @author aebbert
 * @version 1.0
 */
public class BeaTrace60 extends Object implements Trace {

    /** Creates new BeaTrace60 */
    public BeaTrace60() {
    }

    /**
     * Setter method for property subSystem
     * @param subSystem new subSystem value
     */
    public void setSubSystem(String subSystem) {
    }

    /**
     * Logs a message with Weblogic Server's NonCatalogLogger to debug output.
     * @param message Main message which will be logged. Will be accomplished by time of log entry and kind of subsystem
     */
    public void log(String message) {
        System.out.println(message);
    }

    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(String message, Exception e) {
        System.out.println(" (E) caughtException: "+ "message,:" +  e);
    }

    /** 
     * Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
     */
    public void logException(Exception e) {
        System.out.println(" (E) caughtException" + e);
    }

    public void logThrownException(String message, Exception e) {
        System.out.println(" EXC new Exception: "+  e);
    }

    public void logMethodEntry(String method, Object[][] parameter) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" IN  ");
        buffer.append(method);
        buffer.append(" (");
        int i=0;
        if (parameter!=null && parameter.length>0) {
            while (true) {

                // append class type first
                Class parameterClass = null;
                if (parameter[i][1] == null) {
                    buffer.append("unknown ");
                } else {
                    parameterClass = parameter[i][1].getClass();
                    buffer.append(parameterClass.getName());
                    buffer.append(" ");
                }

                // append attribute name
                buffer.append((String)parameter[i][0]);
                buffer.append(" ");

                // append attribute value
                buffer.append("[");
                if (parameter[i][1] == null) {
                    buffer.append("null");
                } else {
                    if (parameterClass.isArray()) {
                        buffer.append(ArrayWriter.toString((Object[])(parameter[i][1])));
                    } else {
                        buffer.append(parameter[i][1].toString());
                    }
                }
                buffer.append("]");

                i++;
                if (i==parameter.length) break;

                buffer.append(", ");
            }
        }
        buffer.append(")");
        log(buffer.toString());
    }

    public void logMethodExit(String method,Object[] returnParameter) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" OUT ");
        buffer.append(method);
        buffer.append(" (");
        if (returnParameter!=null && returnParameter[1]!=null) {
            // append class type first
            Class parameterClass = returnParameter[1].getClass();
            buffer.append(parameterClass.getName());
            buffer.append(" ");

            // append attribute name
            buffer.append((String)returnParameter[0]);
            buffer.append(" ");

            // append attribute value
            buffer.append("[");
            if (parameterClass.isArray()) {
                buffer.append(ArrayWriter.toString((Object[])(returnParameter[1])));
            } else {
                buffer.append(returnParameter[1].toString());
            }
            buffer.append("]");
        } else {
            buffer.append(" void ");
        }
        buffer.append(")");
        log(buffer.toString());
    }

}
