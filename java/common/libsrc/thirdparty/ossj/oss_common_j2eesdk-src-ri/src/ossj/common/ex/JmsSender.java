/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import javax.jms.ObjectMessage;

import javax.oss.Event;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface JmsSender extends EJBObject {
	//VP: not necessary to get these method public....
    //    public void sendMessage(ObjectMessage message) throws RemoteException;
    //    public ObjectMessage prepareMessage(Event event, String eventInterface,Object anManagedEntity) throws RemoteException;
    public void publish(Event event, String eventInterface, Object anManagedEntity)
        throws RemoteException;

    // public void sendXmlReply(Destination destination, OssjXmlMessage xmlMessage) throws RemoteException, JMSException;
}
