package com.nokia.oss.ossj.common.ri.ex;

import javax.ejb.*;
import java.rmi.*;
import javax.jms.*;

import javax.oss.*;
import com.nokia.oss.ossj.common.ri.*;

public interface JmsSender extends EJBObject {
    public void sendMessage(ObjectMessage message) throws RemoteException;
    public ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface,Object anManagedEntity) throws RemoteException;
    public void publish(javax.oss.Event event, String eventInterface, Object anManagedEntity) throws RemoteException;

   // public void sendXmlReply(Destination destination, OssjXmlMessage xmlMessage) throws RemoteException, JMSException;
}

