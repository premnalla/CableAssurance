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
import javax.oss.order.*;
import javax.oss.service.*;

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.order.*;
import com.nokia.oss.ossj.sa.ri.service.*;
import com.nokia.oss.ossj.sa.ri.xml.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.ByteArrayOutputStream;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
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
    
    /** Holds value of property xmlCodec. */
    private XmlCoDec xmlCodec;
    
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
		//VP
		try {
			 myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
    }
    
    public void setSessionContext(final SessionContext sCtx) throws EJBException {
        mySessionContext = sCtx;
    }
    
    private Context getNamingContext() {
        if (myNamingContext==null) {
            try {
                //VP myNamingContext = new InitialContext();
                myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
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
            //VP lookup( "java:comp/env/jms/TopicFactory" );
            lookup( "jms/TopicFactory" );
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
            //VP lookup( "java:comp/env/jms/QueueFactory" );
            lookup( "jms/QueueFactory" );
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
            //VP jvtEventTopic = (Topic) lookup( "java:comp/env/jms/JvtEventTopic" );
            jvtEventTopic = (Topic) lookup( "jms/JvtEventTopic" );
        }
        return jvtEventTopic;
    }
    
    private TopicPublisher getJvtEventPublisher() {
        if (jvtEventPublisher == null) {
            try {
                jvtEventPublisher = getTopicSession().createPublisher( getJvtEventTopic() );
            } catch (javax.jms.JMSException je) {
            }
        }
        return jvtEventPublisher;
    }
    
    public ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface, String clientID, OrderKey key) {
        try {
            ObjectMessage message = getTopicSession().createObjectMessage();
            message.setObject( event );
            
            message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME, eventInterface);
            
            if (clientID == null) {
                clientID = "null";
            }
            message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.OSS_API_CLIENT_ID_PROP_NAME, clientID);
            
            OrderKey anOrderKey = key;
            Object primaryKeyObj = anOrderKey.getPrimaryKey().toString();
            String primaryKey;
            if (primaryKeyObj == null) {
                primaryKey = "null";
            } else {
                primaryKey = primaryKeyObj.toString();
            }
            //message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.ORDER_PRIMARY_KEY, primaryKey);
            
            String applicationDN = anOrderKey.getApplicationDN();
            if (applicationDN == null) {
                applicationDN = "null";
            }
            message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME, applicationDN);
            
            String type = anOrderKey.getType();
            if (type == null) {
                type = "null";
            }
            message.setStringProperty(javax.oss.order.OrderEventPropertyDescriptor.OSS_ORDER_TYPE_PROP_NAME, type);
            
            return message;
        } catch( javax.jms.JMSException je ) {
        }
        return null;
    }
    
    public void sendMessage(ObjectMessage message) {
        try {
            if (message!=null) {
                
                
                // Send JVT Event
                getJvtEventPublisher().publish( message );
                
            }
        } catch( javax.jms.JMSException je ) {
        }
    }
    
    public void publish(javax.oss.Event event, String eventInterface, String clientID, OrderKey key) {
        sendMessage(prepareMessage(event, eventInterface, clientID, key));
    }
    
    private String getApplicationDN() {
        if (myApplicationDN == null) {
            //VP myApplicationDN = (String)lookup("java:comp/env/ApplicationDN");
            myApplicationDN = (String)lookup("ApplicationDN");
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
    
    //VP public void sendXmlReply(Destination destination, OssjXmlMessage xmlMessage) throws JMSException {
    public void sendXmlReply(SaDestination saDestination, OssjXmlMessage xmlMessage) throws JMSException {

	 Destination destination = saDestination.getDestination();

        if (destination instanceof Queue) {
            QueueSender sender = getQueueSession().createSender( (Queue)destination );
            TextMessage message = getQueueSession().createTextMessage();
            message.setText( xmlMessage.getMessage() );
            setProps(message, xmlMessage.getType(), xmlMessage.getName(), xmlMessage.getId());
            sender.send( message );
        } else if (destination instanceof Topic) {
            TopicPublisher publisher = getTopicSession().createPublisher( (Topic)destination );
            TextMessage message = getTopicSession().createTextMessage();
            message.setText( xmlMessage.getMessage() );
            setProps(message, xmlMessage.getType(), xmlMessage.getName(), xmlMessage.getId());
            publisher.publish( message );
        }
    }
    
    /** Getter for property xvtEventTopic.
     * @return Value of property xvtEventTopic.
     */
    public Topic getXvtEventTopic() {
        if (xvtEventTopic == null) {
            //VP xvtEventTopic = (Topic) lookup( "java:comp/env/jms/XvtEventTopic" );
            xvtEventTopic = (Topic) lookup( "jms/XvtEventTopic" );
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
    
    /** Getter for property xmlCodec.
     * @return Value of property xmlCodec.
     */
    public XmlCoDec getXmlCodec() {
        if (xmlCodec == null) {
            try {
                //VP XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("java:comp/env/ejb/XmlCoDec");
                XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("ejb/XmlCoDec");
                xmlCodec = aCodecHome.create();
            } catch (CreateException ce) {
                System.out.println(ce.toString());
            } catch (java.rmi.RemoteException re) {
                System.out.println(re.toString());
            }
        }
        return xmlCodec;
    }
    
    /** Setter for property xmlCodec.
     * @param xmlCodec New value of property xmlCodec.
     */
    public void setXmlCodec(XmlCoDec xmlCodec) {
        this.xmlCodec = xmlCodec;
    }
    
}
