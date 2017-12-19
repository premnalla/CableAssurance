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

/** This interface provides a general logging interface. This can be supported
 * by a native logging mechanism, like the one Weblogic Server offers, or a
 * generic logging to System.out
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface BeaTrace {
    
/** Setter method for property subSystem
 * @param subSystem new subSystem value
 */
    public void setSubSystem(String subSystem);
    
/** Logs a message. The output may vary from implementation to implementation
 * @param message Main message which will be logged. Will be accomplished by time of log entry and kind of subsystem
 */
    public void log(String message);
    
    public void logMethodEntry(String method, Object[][] parameter);
    
    public void logMethodExit(String method, Object[] returnParameter);
    
/** Logs an Exception.
 * @param message Message which explains why Exception is thrown
 * @param e exception which is about to be thrown
 */
    public void logException(String message, Exception e);

/** Logs an Exception.
 * @param message Message which explains why Exception is thrown
 * @param e exception which is about to be thrown
 */
    public void logException(Exception e);
    
    public void logThrownException(String message, Exception e);
}
