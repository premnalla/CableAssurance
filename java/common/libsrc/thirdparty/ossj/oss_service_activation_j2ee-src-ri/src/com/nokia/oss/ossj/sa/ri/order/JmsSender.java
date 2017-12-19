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

import javax.ejb.*;
import java.rmi.*;
import javax.jms.*;

import javax.oss.order.*;
import com.nokia.oss.ossj.sa.ri.order.*;
import com.nokia.oss.ossj.sa.ri.xml.*;
//VP
import com.nokia.oss.ossj.sa.ri.SaDestination;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface JmsSender extends EJBObject {
    public void sendMessage(ObjectMessage message) throws RemoteException;

//AE    public ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface, OrderLocal anOrder) throws RemoteException;
//AE    public void publish(javax.oss.Event event, String eventInterface, OrderLocal anOrder) throws RemoteException;

    public ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface, String clientID, OrderKey key) throws RemoteException;
    public void publish(javax.oss.Event event, String eventInterface, String clientID, OrderKey key) throws RemoteException;

    //VP public void sendXmlReply(Destination destination, OssjXmlMessage xmlMessage) throws RemoteException, JMSException;
    public void sendXmlReply(SaDestination destination, OssjXmlMessage xmlMessage) throws RemoteException, JMSException;
}
