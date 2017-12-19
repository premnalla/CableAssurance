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

package com.nokia.oss.ossj.sa.ri.service;

import javax.oss.service.ServiceValue;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface RiServiceValue extends ServiceValue {

    public static final String SERVICE_ACTIVATOR_HOME_JNDI_NAME = "serviceActivatorHomeJndiName";
    public void setServiceActivatorHomeJndiName(String  newValue);
    public String getServiceActivatorHomeJndiName();
    
}
