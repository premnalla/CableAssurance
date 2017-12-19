package ossj.qos.fmri;

/**
 * AMEventPublisher
 * 
 * @author  Audrey Ward
 * @version 1.0
 * 
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */

import java.util.HashMap;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.oss.Event;
import javax.rmi.PortableRemoteObject;
import ossj.qos.util.Util;
import ossj.qos.util.Trace;


public class AMEventPublisher {

    private TopicConnection connection = null;
    private TopicSession session = null;
    private Topic objectEventTopic = null;
    private TopicPublisher objectPublisher = null;
    private Trace myLog = null;
    
    // default to false. 
    private boolean isLoggingEnabled = false;
    
    public AMEventPublisher() {
    }

    /**
     * Initializes the objects neccessary to send messages to a JMS Topic.
     *
     * @param ctx JNDI initial context
     * @param topicName name of topic
     * @param tfactory name of topic connection factory
     * @param logger logging mechanism 
     * @exception NamingException indicates a problem occurred with the JNDI context interface
     * @exception JMSException indicates an internal JMS initialization problem
     */
    //public void init(Context ctx, String topicName, String tfactory, Trace logger )
    public void init(Context ctx, String topicName, String tfactory)
    throws NamingException, JMSException
    {
        // AM_CONNECTION_FACTORY represents the topic connection factory associated
        // with AlarmMonitor topics.
        TopicConnectionFactory topicFactory = (TopicConnectionFactory)ctx.lookup( tfactory );

        // AM_TOPIC represents the topic associated with the AlarmMonitor notifications
        
        objectEventTopic = (Topic)ctx.lookup( topicName );
        
        connection = topicFactory.createTopicConnection();

        // The TopicSession is not transacted and the messages are auto-acknowledged.
        session = connection.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );

        // Publisher should to publish (send) object messages.
        objectPublisher = session.createPublisher( objectEventTopic );
        
      /*  myLog = logger; 
        if (myLog != null) {
            isLoggingEnabled = true;
        }
        */
        // Start message delivery
        connection.start();
        return;
    }

    /**
     * Publishes an event as a JMS message.
     *
     * @param event an event
     * @param properties a hashtable containing the message property values not found in the event.
     * @param msgBuilder a MessageBuilder is used to set the message's property values from data
     * contained in the event and the properties.
     */
    public void publish( Event event, HashMap properties, MessageBuilder msgBuilder)
    {
        
        ObjectMessage objMsg = null;

        objMsg = msgBuilder.makeObjectMessage(event, properties, session);
        if ( isLoggingEnabled ) {
           // myLog.log( "AMEventPublisher: publish() - obj msg = "  + Util.printObject( objMsg ) );
        }

        if ( objMsg != null )
        {
            // publish the event
            try
            {
                objectPublisher.publish( objMsg );
            }

            catch( Exception x )
            {
                if ( isLoggingEnabled ) {
                   // myLog.logException("AMEventPublisher:publish() ", x);
                }
            }
        }
        return;
    }

    /**
     * Closes the JMS resources.
     */
    public void close()
    {
        try
        {
            objectPublisher.close();
            session.close();
            connection.close();
        }
        catch ( JMSException e )
        {
            if ( isLoggingEnabled ) {
             //   myLog.logException( "AMEventPublisher: close() ", e );
            }
        }
        return;
    }
}
