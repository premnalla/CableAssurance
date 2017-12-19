/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */

package com.nokia.oss.ossj.common.ri;

/** This class prints out the log messages to System.out
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
*/
public class BeaTraceSimulator extends Object implements BeaTrace {

    private String subsystem;

    /** Creates new BeaTraceSimulator */
    public BeaTraceSimulator() {
        this.subsystem = "Unknown";
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
      System.out.println(sb.toString());
    }
    
/** Logs an Exception.
 * @param message Message which explains why Exception is thrown
 * @param e exception which is about to be thrown
 */
    public void logException(String message, Exception e) {
        log(message+"\n"+e.toString());
    }
    
/** Setter method for property subSystem
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
    }
    
    /** Logs an Exception.
     * @param message Message which explains why Exception is thrown
     * @param e exception which is about to be thrown
 */
    public void logException(Exception e) {
        log(e.toString());
    }
    
}
