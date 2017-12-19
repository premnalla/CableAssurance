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

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;
import javax.naming.*;
import javax.jms.*;
import javax.oss.order.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderSchedulerStarter extends HttpServlet {

    private OrderScheduler myScheduler;
    
    public void init(ServletConfig config) throws ServletException {
        try {
            Context myNamingContext = (Context)new javax.naming.InitialContext().lookup("java:comp/env");

            myScheduler = new OrderScheduler(
                myNamingContext.getEnvironment(),
                "java:comp/env/jms/JvtEventTopic",
                "java:comp/env/jms/TopicFactory", 
                "java:comp/env/ejb/JVTActivationSession", 
                "java:comp/env/ejb/OrderProcessor");
            myScheduler.start();
            
        } catch (NamingException ne) {
            System.out.println(ne.toString());
        }
    }

    public void destroy() {
        try {
            System.out.println("Signalling Order Scheduler ("+myScheduler.getName()+") to shut down.");
            myScheduler.setThreadTermination(true);
            System.out.println("Order Scheduler ("+myScheduler.getName()+") will shut down soon.");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
