/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import java.net.InetAddress;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import javax.oss.ManagedEntityKey;


/**
 * *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
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

    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbRemove() throws EJBException {
        if (topicConnection != null) {
            try {
                topicConnection.close();
            } catch (JMSException jmse) {
            }

            topicConnection = null;
        }

        if (queueConnection != null) {
            try {
                queueConnection.close();
            } catch (JMSException jmse) {
            }

            queueConnection = null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbActivate() throws EJBException {
    }

    /**
     * DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbPassivate() throws EJBException {
        if (topicConnection != null) {
            try {
                topicConnection.close();
            } catch (JMSException jmse) {
            }

            topicConnection = null;
        }

        if (queueConnection != null) {
            try {
                queueConnection.close();
            } catch (JMSException jmse) {
            }

            queueConnection = null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     * @throws EJBException DOCUMENT ME!
     */
    public void ejbCreate() throws CreateException, EJBException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param sCtx DOCUMENT ME!
     *
     * @throws EJBException DOCUMENT ME!
     */
    public void setSessionContext(final SessionContext sCtx)
        throws EJBException {
        mySessionContext = sCtx;
    }

    private Context getNamingContext() {
        if (myNamingContext == null) {
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
            TopicConnectionFactory factory = (TopicConnectionFactory) lookup(
                    "java:comp/env/jms/TopicFactory");

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
                topicSession = getTopicConnection().createTopicSession(false,
                        javax.jms.Session.AUTO_ACKNOWLEDGE);
            } catch (javax.jms.JMSException je) {
            }
        }

        return topicSession;
    }

    private QueueConnection getQueueConnection() {
        if (queueConnection == null) {
            QueueConnectionFactory factory = (QueueConnectionFactory) lookup(
                    "java:comp/env/jms/QueueFactory");

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
                queueSession = getQueueConnection().createQueueSession(false,
                        javax.jms.Session.AUTO_ACKNOWLEDGE);
            } catch (javax.jms.JMSException je) {
            }
        }

        return queueSession;
    }

    private Topic getJvtEventTopic() {
        if (jvtEventTopic == null) {
            jvtEventTopic = (Topic) lookup("java:comp/env/jms/EventTopic");
        }

        return jvtEventTopic;
    }

    private TopicPublisher getJvtEventPublisher() {
        if (jvtEventPublisher == null) {
            try {
                System.out.println("****eventtopic****" + getJvtEventTopic());
                jvtEventPublisher = getTopicSession().createPublisher(getJvtEventTopic());
            } catch (javax.jms.JMSException je) {
            }
        }

        return jvtEventPublisher;
    }

    private ObjectMessage prepareMessage(javax.oss.Event event, String eventInterface,
        Object anEntity) {
        try {
            String clientId = null;
            ManagedEntityKey anManagedKey = null;

            ObjectMessage message = getTopicSession().createObjectMessage();
            message.setObject(event);

            message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                eventInterface);

            if (anEntity instanceof ManagedEntityEx1) {
                clientId = ((ManagedEntityEx1) anEntity).getEx1ApiClientId();
            } else {
                clientId = ((ManagedEntityEx2) anEntity).getEx2ApiClientId();
            }

            if (clientId == null) {
                clientId = "null";
            }

            message.setStringProperty("OSS_API_CLIENT_ID_PROP_NAME", clientId);

            if (anEntity instanceof ManagedEntityEx1) {
                anManagedKey = ((ManagedEntityEx1) anEntity).getManagedEntityKey();
            } else {
                anManagedKey = ((ManagedEntityEx2) anEntity).getManagedEntityKey();
            }

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

            message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                applicationDN);

            String type = anManagedKey.getType();

            if (type == null) {
                type = "null";
            }

            //message.setStringProperty(javax.oss.EventPropertyDescriptor.OSS_ORDER_TYPE_PROP_NAME, type);
            return message;
        } catch (javax.jms.JMSException je) {
        } catch (Exception re) {
        }

        return null;
    }

    private void sendMessage(ObjectMessage message) {
        try {
            if (message != null) {
                // Send JVT Event
                //System.out.println("**hi JEvent publisher is ***"+ getJvtEventPublisher());
                System.out.println("**hi message published is ***" + message);
                getJvtEventPublisher().publish(message);
            }
        } catch (javax.jms.JMSException je) {
            je.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param event DOCUMENT ME!
     * @param eventInterface DOCUMENT ME!
     * @param anEntity DOCUMENT ME!
     */
    public void publish(javax.oss.Event event, String eventInterface, Object anEntity) {
        sendMessage(prepareMessage(event, eventInterface, anEntity));
    }

    private String getApplicationDN() {
        if (myApplicationDN == null) {
            myApplicationDN = (String) lookup("java:comp/env/ApplicationDN");
        }

        return myApplicationDN;
    }

    private void setProps(TextMessage message, String type, String name, String id)
        throws JMSException {
        message.setStringProperty("OSSJ_MESSAGE_TYPE", type);
        message.setStringProperty("OSSJ_MESSAGE_NAME", name);
        message.setStringProperty("OSSJ_APPLICATION_TYPE", "ServiceActivation");
        message.setStringProperty("OSSJ_REPLY_SENDER_ID", getApplicationDN());
        message.setJMSCorrelationID(id);
    }

    /** Getter for property xvtEventTopic.
     * @return Value of property xvtEventTopic.
     */
    public Topic getXvtEventTopic() {
        if (xvtEventTopic == null) {
            xvtEventTopic = (Topic) lookup("java:comp/env/jms/XvtEventTopic");
        }

        return xvtEventTopic;
    }

    /** Getter for property xvtEventPublisher.
     * @return Value of property xvtEventPublisher.
     */
    public TopicPublisher getXvtEventPublisher() {
        if (jvtEventPublisher == null) {
            try {
                xvtEventPublisher = getTopicSession().createPublisher(getXvtEventTopic());
            } catch (javax.jms.JMSException je) {
                System.out.println(je.toString());
                je.printStackTrace();
            }
        }

        return xvtEventPublisher;
    }
}
