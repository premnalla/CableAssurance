/*
Copyright 2004-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.ex;

import java.util.logging.Logger;

import javax.jms.Destination;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.Message;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Interface definition JMSReplySender.java
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2
 * @since October 14, 2005, 5:41 PM
 */
public class JMSReplySender {
    private static final JMSReplySender singleton = new JMSReplySender();
    public String JMS_QUEUE_FACTORY = null;
    public String APPLICATIONDN = null;
    private QueueConnectionFactory queueConnectionFactory;
    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private TopicConnectionFactory topicConnectionFactory;
    private TopicConnection topicConnection;
    private TopicSession topicSession;

    /** Creates new JMSReplySender */
    protected JMSReplySender() {
        Context ctx = null;

        try {
            ctx = new InitialContext();
            APPLICATIONDN = (String) ctx.lookup("java:comp/env/applicationDN");
            JMS_QUEUE_FACTORY = APPLICATIONDN + "Comp/QueueConnectionFactory";
            queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(JMS_QUEUE_FACTORY);
            queueConnection = queueConnectionFactory.createQueueConnection();
            createSession();
            queueConnection.start();
        } catch (Exception x) {
            //Logger.log("unable to create JMSReplySender:" + x);
        } finally {
            try {
                if (ctx != null) {
                    ctx.close();
                }
            } catch (Exception x) {
            }
        }
    }
    
    public void createSession() throws javax.jms.JMSException{
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static JMSReplySender getInstance() {
        return singleton;
    }

    /**
     * DOCUMENT ME!
     *
     * @param destination DOCUMENT ME!
     * @param xmlMessage DOCUMENT ME!
     * @param messageTypeProp DOCUMENT ME!
     * @param messageNameProp DOCUMENT ME!
     * @param requestSenderIdProp DOCUMENT ME!
     *
     * @throws javax.jms.JMSException DOCUMENT ME!
     */
    public void sendXmlReply(Destination destination, String xmlMessage, String messageTypeProp,
        String messageNameProp, String requestSenderIdProp)
        throws javax.jms.JMSException {
        java.util.Hashtable properties = null;

        if (destination instanceof Queue) {
            QueueSender sender = queueSession.createSender((Queue) destination);
            TextMessage message = queueSession.createTextMessage();
            message.setText(xmlMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            setMessageProperties(message, properties);
            log("Send:\n"+xmlMessage);
            sender.send(message);
            sender.close();
        } else if (destination instanceof Topic) {
            //System.out.println("JMSReplySender Testpt 6");
            TopicPublisher publisher = topicSession.createPublisher((Topic) destination);
            TextMessage message = topicSession.createTextMessage();
            message.setText(xmlMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            setMessageProperties(message, properties);
            publisher.publish(message);
            publisher.close();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param destination DOCUMENT ME!
     * @param objMessage DOCUMENT ME!
     * @param messageTypeProp DOCUMENT ME!
     * @param messageNameProp DOCUMENT ME!
     * @param requestSenderIdProp DOCUMENT ME!
     *
     * @throws javax.jms.JMSException DOCUMENT ME!
     */
    public void sendObjectReply(Destination destination, java.io.Serializable objMessage,
        String messageTypeProp, String messageNameProp, String requestSenderIdProp)
        throws javax.jms.JMSException {
        java.util.Hashtable properties = null;

        if (destination instanceof Queue) {
            QueueSender sender = queueSession.createSender((Queue) destination);
            ObjectMessage message = queueSession.createObjectMessage();
            message.setObject(objMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            setMessageProperties(message, properties);
            sender.send(message);
            sender.close();
        } else if (destination instanceof Topic) {
            TopicPublisher publisher = topicSession.createPublisher((Topic) destination);
            ObjectMessage message = topicSession.createObjectMessage();
            message.setObject(objMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            setMessageProperties(message, properties);
            publisher.publish(message);
            publisher.close();
        }
    }

    //Adding the following properties
    //OSS_MESSAGE_TYPE { REQUEST, RESPONSE, EXCEPTION }
    //OSS_MESSAGE_NAME { <OP>REQUEST, <OP>RESPONSE, <OP>EXCEPTION
    //OSS_APPLICATION_TYPE {RDN of APPLICATION_TYPE NODE i.e "TroubleTicket"}
    //OSS_REQUEST_SENDER_ID { FREE FORMAT STRING OF SENDER ID }
    //OSS_REPLY_SENDER_ID { APPLICATION_DN }
    //OSS_REPLYTO_DESTINATION_TYPE { QUEUE, TOPIC only on incoming messages ? }
    public java.util.Hashtable makeProperties(String messageTypeProp, String messageNameProp,
        String requestSenderIdProp) {
        java.util.Hashtable props = new java.util.Hashtable();

        // OSS_MESSAGE_TYPE
        if (messageTypeProp != null) {
            props.put("OSS_MESSAGE_TYPE", messageTypeProp);
        }

        // OSS_MESSAGE_NAME
        if (messageNameProp != null) {
            props.put("OSS_MESSAGE_NAME", messageNameProp);
        }

        // OSS_APPLICATION_TYPE
        props.put("OSS_APPLICATION_TYPE", "Common");

        // OSS_REQUEST_SENDER_ID
        if (requestSenderIdProp != null) {
            props.put("OSS_REQUEST_SENDER_ID", requestSenderIdProp);
        }

        // OSS_REPLY_SENDER_ID
        props.put("OSS_REPLY_SENDER_ID", APPLICATIONDN);

        return props;
    }

    /**
     * DOCUMENT ME!
     *
     * @param msg DOCUMENT ME!
     * @param properties DOCUMENT ME!
     */
    public void setMessageProperties(Message msg, Hashtable properties) {
        Enumeration keys = properties.keys();
        String key = null;
        Object value = null;

        while (keys.hasMoreElements()) {
            Object okey = keys.nextElement();

            if (!(okey instanceof String)) {
                //log("utils.setMessageProperties - Invalid property name");
            } else {
                key = (String) okey;
            }

            value = properties.get(key);

            try {
                if (value instanceof Integer) {
                    msg.setIntProperty(key, ((Integer) value).intValue());
                } else if (value instanceof Byte) {
                    msg.setByteProperty(key, ((Byte) value).byteValue());
                } else if (value instanceof Short) {
                    msg.setShortProperty(key, ((Short) value).shortValue());
                } else if (value instanceof Boolean) {
                    msg.setBooleanProperty(key, ((Boolean) value).booleanValue());
                } else if (value instanceof Double) {
                    msg.setDoubleProperty(key, ((Double) value).doubleValue());
                } else if (value instanceof Long) {
                    msg.setLongProperty(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    msg.setFloatProperty(key, ((Float) value).floatValue());
                } else if (value instanceof String) {
                    msg.setStringProperty(key, (String) value);
                } else {
                    //log("utils::setMessageProperty: unknown value type, property ignored");
                }
            } catch (MessageNotWriteableException mex) {
                //log("utils::setMessageProperty: MessageNotWriteableException occured");
            } catch (JMSException jex) {
                //log("utils::setMessageProperty: JMSException occured");
            }
        }
    }
    private void log(String message){
    	System.out.println("jmsreplysende:"+message);
    }
}
