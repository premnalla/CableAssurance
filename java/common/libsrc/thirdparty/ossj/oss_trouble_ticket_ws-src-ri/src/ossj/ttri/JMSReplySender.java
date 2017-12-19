package ossj.ttri;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * JMS Reply Sender Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class JMSReplySender {
    private static final JMSReplySender singleton = new JMSReplySender();
    public  String JMS_QUEUE_FACTORY = null;
    public  String APPLICATIONDN = null;

    public static JMSReplySender getInstance() {
        return singleton;
    }

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
            //Wipro
//            JMS_QUEUE_FACTORY = APPLICATIONDN + "/Comp/QueueConnectionFactory";
            APPLICATIONDN = (String) ctx.lookup("java:comp/env/applicationDN");
            JMS_QUEUE_FACTORY = TTHelper.projectProperties.getProperty("TT_QUEUE_CONNECTION_FACTORY");
            queueConnectionFactory = (QueueConnectionFactory)
                    //PG should work too ctx.lookup("javax/comp/env/ejb/QueueConnectionFactory");
                    ctx.lookup(JMS_QUEUE_FACTORY);
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            queueConnection.start();

        } catch (Exception x) {
            Logger.log("unable to create JMSReplySender:" + x);
        } finally {
            try {
                if (ctx != null) ctx.close();
            } catch (Exception x) {
            }
        }
    }


    public void sendXmlReply(Destination destination, String xmlMessage,
                             String messageTypeProp, String messageNameProp, String requestSenderIdProp)
            throws javax.jms.JMSException {
        java.util.Hashtable properties = null;
        if (destination instanceof Queue) {
            QueueSender sender = queueSession.createSender((Queue) destination);
            TextMessage message = queueSession.createTextMessage();
            message.setText(xmlMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            utils.setMessageProperties(message, properties);
            sender.send(message);
            sender.close();
        } else if (destination instanceof Topic) {
            //System.out.println("JMSReplySender Testpt 6");
            TopicPublisher publisher = topicSession.createPublisher((Topic) destination);
            TextMessage message = topicSession.createTextMessage();
            message.setText(xmlMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            utils.setMessageProperties(message, properties);
            publisher.publish(message);
            publisher.close();

        }
    }

    public void sendObjectReply(Destination destination, java.io.Serializable objMessage,
                                String messageTypeProp, String messageNameProp, String requestSenderIdProp)
            throws javax.jms.JMSException {
        java.util.Hashtable properties = null;
        if (destination instanceof Queue) {

            QueueSender sender = queueSession.createSender((Queue) destination);
            ObjectMessage message = queueSession.createObjectMessage();
            message.setObject(objMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            utils.setMessageProperties(message, properties);
            sender.send(message);
            sender.close();
        } else if (destination instanceof Topic) {

            TopicPublisher publisher = topicSession.createPublisher((Topic) destination);
            ObjectMessage message = topicSession.createObjectMessage();
            message.setObject(objMessage);
            properties = makeProperties(messageTypeProp, messageNameProp, requestSenderIdProp);
            utils.setMessageProperties(message, properties);
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
    public java.util.Hashtable makeProperties(String messageTypeProp,
                                              String messageNameProp, String requestSenderIdProp) {
        java.util.Hashtable props = new java.util.Hashtable();

        //---------------------
        // OSS_MESSAGE_TYPE
        //---------------------

        if (messageTypeProp != null)
            props.put("OSS_MESSAGE_TYPE", messageTypeProp);


        //---------------------
        // OSS_MESSAGE_NAME
        //---------------------

        if (messageNameProp != null)
            props.put("OSS_MESSAGE_NAME", messageNameProp);

        //---------------------
        // OSS_APPLICATION_TYPE
        //---------------------


        props.put("OSS_APPLICATION_TYPE", "TroubleTicket");


        //---------------------
        // OSS_REQUEST_SENDER_ID
        //---------------------

        if (requestSenderIdProp != null)
            props.put("OSS_REQUEST_SENDER_ID", requestSenderIdProp);


        //---------------------
        // OSS_REPLY_SENDER_ID
        //---------------------


        props.put("OSS_REPLY_SENDER_ID", APPLICATIONDN);

        return props;

    }
}
