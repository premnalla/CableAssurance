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
package com.nokia.oss.ossj.sa.ri.order;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface QueryOrdersByDateInterval extends javax.oss.QueryValue {
    
    public static final String FROM_REQUESTED_COMPLETION_DATE = "fromRequestedCompletionDate";

    public java.util.Date getFromRequestedCompletionDate();
    public void setFromRequestedCompletionDate(java.util.Date newFromDate);

    public static final String TO_REQUESTED_COMPLETION_DATE = "toRequestedCompletionDate";
    
    public java.util.Date getToRequestedCompletionDate();
    public void setToRequestedCompletionDate(java.util.Date newToDate);
    
}

