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

import javax.ejb.*;
import java.rmi.*;
import javax.naming.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class ServiceActivatorBean implements javax.ejb.SessionBean {

	//VP 
	private Context context;
    private SessionContext mySessionContext;
    
    public void setSessionContext(final SessionContext sCtx) throws EJBException {
        mySessionContext = sCtx;
    }
    
    public void ejbCreate() throws javax.ejb.EJBException, CreateException {
		//VP
		try {
			context = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
    }
 
    // BUSINESS METHODS

    public void createService(RiServiceValue aService) throws  ServiceException {
        System.out.println("create Service: "+aService);
    }
    
    public void modifyService(RiServiceValue aService) throws  ServiceException {
        System.out.println("modify Service: "+aService);
    }
    
    public void cancelService(RiServiceValue aService) throws  ServiceException {
        System.out.println("remove Service: "+aService);
    }
    
}
