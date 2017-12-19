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
package com.nokia.oss.ossj.sa.ri.xml;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.net.InetAddress;

import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.order.*;
import com.nokia.oss.ossj.sa.ri.service.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.ByteArrayOutputStream;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class JvtToXvtEventBridgeBean extends java.lang.Object implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {

    private MessageDrivenContext myContext;
    private Context myNamingContext;
    
    private BeaTrace myLog;
    private boolean isLoggingEnabled = false;

    // JMS SUPPORT
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private TopicPublisher jvtEventPublisher;

    /** Holds value of property xvtEventTopic. */
    private Topic xvtEventTopic;
    
    /** Holds value of property xvtEventPublisher. */
    private TopicPublisher xvtEventPublisher;
    
    /** Holds value of property xmlCodec. */
    private XmlCoDec xmlCodec;
    

    /** Creates new JvtToXvtEventBridge */
    public JvtToXvtEventBridgeBean() {
    }

    public void ejbCreate () throws CreateException {
		//VP
		try {
			myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
	//VP Close?
	try {
		getXvtEventPublisher().close();
		getTopicSession().close();
	} catch(javax.jms.JMSException jex){
		throw new javax.ejb.EJBException ("Fail to close topic session or publisher: "+jex.getMessage());
	}
	
    }
    
    public void onMessage(javax.jms.Message message) {
        if (isLoggingEnabled)
            myLog.logMethodEntry("onMessage", new Object[][] {
                {"message", message}
            });
        if (!(message instanceof ObjectMessage)) {
            if (isLoggingEnabled) {
                myLog.log("received message is no ObjectMessage!");
                myLog.logMethodExit("onMessage", null);
            }
            return;
        }
        // Send XML Event
        try {
            try {
                TextMessage xvtmessage = convertToXmlEventMessage((ObjectMessage)message);
                getXvtEventPublisher().publish( xvtmessage );

                // DEBUG: Send Exceptions as JMSMessage
            } catch (SAXException se) {
                if (isLoggingEnabled)
                    myLog.logException(se);
                getXvtEventPublisher().publish(getTopicSession().createTextMessage(se.toString()));
            } catch (java.io.IOException ioe) {
                if (isLoggingEnabled)
                    myLog.logException(ioe);
                getXvtEventPublisher().publish(getTopicSession().createTextMessage(ioe.toString()));
            }
        } catch (JMSException jmse) {
                if (isLoggingEnabled)
                    myLog.logException(jmse);
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("onMessage", null);
    }
    
    public void setMessageDrivenContext(javax.ejb.MessageDrivenContext messageDrivenContext) throws javax.ejb.EJBException {
        myContext = messageDrivenContext;
        //VP isLoggingEnabled = ((java.lang.Boolean)lookup("java:comp/env/loggingEnabled")).booleanValue();
        isLoggingEnabled = ((java.lang.Boolean)lookup("loggingEnabled")).booleanValue();
        createLog("OSS/J SA J2X Event");
    }

    private TextMessage convertToXmlEventMessage(ObjectMessage message) throws SAXException, java.io.IOException, JMSException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("convertToXmlEventMessage", new Object[][] {
                {"message", message}
            });
        TextMessage xvtEvent = getTopicSession().createTextMessage();
        String[] props = {
            javax.oss.order.OrderEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
            javax.oss.order.OrderEventPropertyDescriptor.OSS_API_CLIENT_ID_PROP_NAME,
            javax.oss.order.OrderEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
            javax.oss.order.OrderEventPropertyDescriptor.OSS_ORDER_TYPE_PROP_NAME
        };
        for (int i=0; i<props.length ; i++) {
            xvtEvent.setStringProperty(props[i], message.getStringProperty(props[i]));
        }
        
        javax.oss.Event event = (Event)message.getObject();
        String eventType = message.getStringProperty(javax.oss.order.OrderEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME);
        int lastDot = eventType.lastIndexOf('.');
        String aDocName =   eventType.substring(lastDot+1, lastDot+2).toLowerCase()+
                            eventType.substring(lastDot+2, eventType.length());
        
        Document aDoc = getXmlCodec().getXmlTemplate(aDocName);
        Element root = aDoc.getDocumentElement();
        
        try {
            getXmlCodec().encodeParameter(aDoc, root, Class.forName(eventType), event);
        } catch (ClassNotFoundException cnfe) {
                if (isLoggingEnabled)
                    myLog.logException(cnfe);
        }
        
        xvtEvent.setText(getXmlCodec().toString(aDoc));
        if (isLoggingEnabled)
            myLog.logMethodExit("convertToXmlEventMessage", new Object[] {"xvtEvent", xvtEvent});
        return xvtEvent;
    }
    
    /** Getter for property xvtEventTopic.
     * @return Value of property xvtEventTopic.
     */
    public Topic getXvtEventTopic() {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getXvtEventTopic", null);
        if (xvtEventTopic == null) {
            //VP xvtEventTopic = (Topic) lookup( "java:comp/env/jms/XvtEventTopic" );
            xvtEventTopic = (Topic) lookup( "jms/XvtEventTopic" );
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("getXvtEventTopic", new Object[] {"xvtEventTopic", xvtEventTopic});
        return xvtEventTopic;
    }
    
    /** Getter for property xvtEventPublisher.
     * @return Value of property xvtEventPublisher.
     */
    public TopicPublisher getXvtEventPublisher() {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getXvtEventPublisher", null);
        if (xvtEventPublisher == null) {
            try {
                xvtEventPublisher = getTopicSession().createPublisher( getXvtEventTopic() );
            } catch (javax.jms.JMSException je) {
                if (isLoggingEnabled)
                    myLog.logException(je);
            }
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("getXvtEventPublisher", new Object[] {"xvtEventPublisher", xvtEventPublisher});
        return xvtEventPublisher;
    }
    /** Getter for property xmlCodec.
     * @return Value of property xmlCodec.
     */
    public XmlCoDec getXmlCodec() {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getXmlCodec", null);
        if (xmlCodec == null) {
            try {
                //VP XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("java:comp/env/ejb/XmlCoDec");
                XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("ejb/XmlCoDec");
                xmlCodec = aCodecHome.create();
            } catch (CreateException ce) {
                if (isLoggingEnabled)
                    myLog.logException(ce);
            } catch (java.rmi.RemoteException re) {
                if (isLoggingEnabled)
                    myLog.logException(re);
            }
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("getXmlCodec", new Object[] {"xmlCodec", xmlCodec});
        return xmlCodec;
    }
    
    /** Setter for property xmlCodec.
     * @param xmlCodec New value of property xmlCodec.
     */
    public void setXmlCodec(XmlCoDec xmlCodec) {
        this.xmlCodec = xmlCodec;
    }
    
        private Context getNamingContext() {
        if (myNamingContext==null) {
            try {
                //VP myNamingContext = new InitialContext();
                myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
            } catch (javax.naming.NamingException ne) {
                if (isLoggingEnabled)
                    myLog.logException(ne);
            }
        }
        
        return myNamingContext;
    }
    
    private Object lookup(String name) {
        Object obj = null;
        try {
            obj = getNamingContext().lookup(name);
        } catch (javax.naming.NamingException ne) {
            if (isLoggingEnabled) 
                myLog.logException(ne);
        }
        
        return obj;
    }
    
    private TopicConnection getTopicConnection() {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getTopicConnection", null);
        if (topicConnection == null) {
            TopicConnectionFactory factory = (TopicConnectionFactory)
            //VP lookup( "java:comp/env/jms/TopicFactory" );
            lookup( "jms/TopicFactory" );
            try {
                topicConnection = factory.createTopicConnection();
            } catch (javax.jms.JMSException je) {
                if (isLoggingEnabled)
                    myLog.logException(je);
            }
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("getTopicConnection", new Object[] {"topicConnection", topicConnection});
        return topicConnection;
    }
    
    private TopicSession getTopicSession() {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getTopicSession", null);
        if (topicSession == null) {
            try {
                topicSession = getTopicConnection().createTopicSession( false,
                javax.jms.Session.AUTO_ACKNOWLEDGE );
            } catch (javax.jms.JMSException je) {
                if (isLoggingEnabled)
                    myLog.logException(je);
            }
        }
        if (isLoggingEnabled)
            myLog.logMethodExit("getTopicSession", new Object[] {"topicSession", topicSession});
        return topicSession;
    }
    
    private void createLog(String subSystem) {
        myLog = null;
        String loggingVersion = "";
        // First, try to use WLS 6.0 facility
        
        try {
            Class wls6Log = Class.forName("weblogic.logging.NonCatalogLogger");
            // if there is no exception thrown, this bean is running under WLS6, so that logging
            // facility can be used
            myLog = (BeaTrace)Class.forName("com.nokia.oss.ossj.common.ri.BeaTrace60").newInstance();
            loggingVersion = "WebLogic 6.0 logging classes";
        } catch (ClassNotFoundException cnfe) {
            //VP System.out.println(cnfe.toString());
        } catch (InstantiationException ie) {
            //VP System.out.println(ie.toString());
        } catch (IllegalAccessException iae) {
            //VP System.out.println(iae.toString());
        }
        if (myLog == null) {
            // if this is not WLS6.0, simulate logging
            myLog = new BeaTraceSimulator();
            loggingVersion = "Bea logging simulator";
        }
        myLog.setSubSystem(subSystem);
        if (isLoggingEnabled) myLog.log("Created new logger, using "+loggingVersion);
    }
    
   
}
