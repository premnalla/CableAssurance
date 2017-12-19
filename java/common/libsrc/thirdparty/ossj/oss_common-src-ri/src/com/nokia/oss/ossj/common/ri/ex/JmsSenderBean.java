package com.nokia.oss.ossj.common.ri.ex;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.ejb.FinderException;

import javax.naming.NamingException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.net.InetAddress;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.directory.DirContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.directory.SearchControls;

import javax.oss.*;


import com.nokia.oss.ossj.common.ri.*;


import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.ByteArrayOutputStream;

/**
 * *
 * @author BanuPrasad
 * @version 
 * @stereotype SessionBean
 */
public class JmsSenderBean extends java.lang.Object implements SessionBean {
    private SessionContext mySessionContext;
    private Context myNamingContext;
    
    // JMS SUPPORT
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private Topic jvtEventTopic;
    private TopicPublisher jvtEventPublisher;
    private String myApplicationDN;
    
    /** Holds value of property xvtEventTopic. */
    private Topic xvtEventTopic;
    
    /** Holds value of property xvtEventPublisher. */
    private TopicPublisher xvtEventPublisher;
    
    
    
    public void ejbRemove() throws EJBException {
        if (topicConnection!=null) {
            try {
                topicConnection.close();
            } catch (JMSException jmse) {
            }
            topicConnection=null;
        }
        if (queueConnection!=null) {
            try {
                queueConnection.close();
            } catch (JMSException jmse) {
            }
            queueConnection=null;
        }
    }
    
    public void ejbActivate() throws EJBException {
    }
    
    public void ejbPassivate() throws EJBException {
        if (topicConnection!=null) {
            try {
                topicConnection.close();
            } catch (JMSException jmse) {
            }
            topicConnection=null;
        }
        if (queueConnection!=null) {
            try {
                queueConnection.close();
            } catch (JMSException jmse) {
            }
            queueConnection=null;
        }
    }
    
    public void ejbCreate() throws CreateException, EJBException {
    }
    
    public void setSessionContext(final SessionContext sCtx) throws EJBException {
        mySessionContext = sCtx;
    }
    
    private Context getNamingContext() {
        if (myNamingContext==null) {
            try {
                myNamingContext = new InitialContext();
            } catch (javax.naming.NamingException ne) {
            }
        }
        
        return myNamingContext;
    }
    
    private Object lookup(String name) {
        Object obj = null;
        try {
            obj = getNamingContext().lookup(name);
        } catch (javax.naming.NamingException ne) {
        }
        
        return obj;
    }
    
    private TopicConnection getTopicConnection() {
        if (topicConnection == null) {
            TopicConnectionFactory factory = (TopicConnectionFactory)
            lookup( "java:comp/env/jms/TopicFactory" );
            try {
                topicConnection = factory.createTopicConnection();
            } catch (javax.jms.JMSException je) {
            }
        }
        return topicConnection;
    }
    
    private TopicSession getTopicSession() {
        if (topicSession == null) {
            try {
                topicSession = getTopicConnection().createTopicSession( false,
                javax.jms.Session.AUTO_ACKNOWLEDGE );
            } catch (javax.jms.JMSException je) {
            }
        }
        return topicSession;
    }
    
    private QueueConnection getQueueConnection() {
        if (queueConnection == null) {
            QueueConnectionFactory factory = (QueueConnectionFactory)
            lookup( "java:comp/env/jms/QueueFactory" );
            try {
                queueConnection = factory.createQueueConnection();
            } catch (javax.jms.JMSException je) {
            }
        }
        return queueConnection;
    }
    
    private QueueSession getQueueSession() {
        if (queueSession == null) {
            try {
                queueSession = getQueueConnection().createQueueSession( false,
                javax.jms.Session.AUTO_ACKNOWLEDGE );
            } catch (javax.jms.JMSException je) {
            }
        }
        return queueSession;
    }
    
    private Topic getJvtEventTopic() {
        if (jvtEventTopic == null) {
            jvtEventTopic = (Topic) lookup( "java:comp/env/jms/EventTopic" );
        }
        return jvtEventTopic;
    }
    
    private TopicPublisher getJvtEventPublisher() {

        if (jvtEventPublisher == null) {
            try {
                System.out.println("****eventtopic****" + getJvtEventTopic());
                jvtEventPublisher = getTopicSession().createPublisher( getJvtEventTopic() );
            } catch (javax.jms.JMSException je) {
            }
        }
        return jvtEventPublisher;
    }
    
    public ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface, Object anEntity) throws RemoteException{
        try {
           
            String clientId = null;
            ManagedEntityKey anManagedKey = null;
              
            ObjectMessage message = getTopicSession().createObjectMessage();
            message.setObject( event );
            
            message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME, eventInterface);
            if (  anEntity instanceof ManagedEntityEx1)
             clientId = ((ManagedEntityEx1)anEntity).getex1ApiClientId();
             else 
              clientId = ((ManagedEntityEx2)anEntity).getex2ApiClientId();
              
            if (clientId == null) {
                clientId = "null";
            }
            message.setStringProperty("OSS_API_CLIENT_ID_PROP_NAME", clientId);
            if (  anEntity instanceof ManagedEntityEx1)
            anManagedKey = ((ManagedEntityEx1)anEntity).getManagedEntityKey();
           else 
           anManagedKey = ((ManagedEntityEx2)anEntity).getManagedEntityKey();
            Object primaryKeyObj = anManagedKey.getPrimaryKey().toString();
            String primaryKey;
            if (primaryKeyObj == null) {
                primaryKey = "null";
            } else {
                primaryKey = primaryKeyObj.toString();
            }
            //message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.ORDER_PRIMARY_KEY, primaryKey);
            
            String applicationDN = anManagedKey.getApplicationDN();
            if (applicationDN == null) {
                applicationDN = "null";
            }
            message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME, applicationDN);
            
            String type = anManagedKey.getType();
            if (type == null) {
                type = "null";
            }
            //message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_ORDER_TYPE_PROP_NAME, type);
            
            return message;
        } catch( javax.jms.JMSException je ) {
        } catch (Exception re) {
        }
        return null;
    }
    
    public void sendMessage(ObjectMessage message)throws RemoteException {
        try {
            if (message!=null) {
                
                
                // Send JVT Event
                //System.out.println("**hi JEvent publisher is ***"+ getJvtEventPublisher());
                System.out.println("**hi message published is ***"+ message);
                getJvtEventPublisher().publish( message );
                
            }
        } catch( javax.jms.JMSException je ) {
            je.printStackTrace();
        }
    }
    
    public void publish(javax.oss.Event event, String eventInterface, Object anEntity) throws RemoteException{
        sendMessage(prepareMessage(event, eventInterface, anEntity));
    }
    
    private String getApplicationDN() {
        if (myApplicationDN == null) {
            myApplicationDN = (String)lookup("java:comp/env/ApplicationDN");
        }
        return myApplicationDN;
    }
    
    
    private void setProps(TextMessage message, String type, String name, String id) throws JMSException {
        message.setStringProperty("OSSJ_MESSAGE_TYPE", type);
        message.setStringProperty("OSSJ_MESSAGE_NAME", name);
        message.setStringProperty("OSSJ_APPLICATION_TYPE", "ServiceActivation");
        message.setStringProperty("OSSJ_REPLY_SENDER_ID",  getApplicationDN());
        message.setJMSCorrelationID(id);
    }
    
    
    
    /** Getter for property xvtEventTopic.
     * @return Value of property xvtEventTopic.
     */
    public Topic getXvtEventTopic() {
        if (xvtEventTopic == null) {
            xvtEventTopic = (Topic) lookup( "java:comp/env/jms/XvtEventTopic" );
        }
        return xvtEventTopic;
    }
    
    /** Getter for property xvtEventPublisher.
     * @return Value of property xvtEventPublisher.
     */
    public TopicPublisher getXvtEventPublisher() {
        if (jvtEventPublisher == null) {
            try {
                xvtEventPublisher = getTopicSession().createPublisher( getXvtEventTopic() );
            } catch (javax.jms.JMSException je) {
                System.out.println(je.toString());
                je.printStackTrace();
            }
        }
        return xvtEventPublisher;
    }
    
    
    
}
