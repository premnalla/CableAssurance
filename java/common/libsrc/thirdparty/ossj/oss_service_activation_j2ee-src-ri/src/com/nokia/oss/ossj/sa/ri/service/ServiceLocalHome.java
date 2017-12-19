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

import javax.oss.order.*;
import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.CMPManagedEntityKey;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface ServiceLocalHome extends EJBLocalHome, java.io.Serializable {
    public ServiceLocal create(RiServiceValueImpl template, String anOrderPrimaryKey) throws CreateException;
    public ServiceLocal findByPrimaryKey(CMPServiceDeltaKey serviceKey) throws FinderException;
    public Collection findByOrderKey(java.lang.String anOrderPrimaryKey) throws FinderException;
    public Collection findByServiceKey(java.lang.String aServicePrimaryKey) throws FinderException;
    public ServiceLocal findExistingService(java.lang.String aServicePrimaryKey) throws FinderException;
}
