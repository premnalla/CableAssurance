/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri.util;

/**
 * Standard imports
 */

import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.io.*;
import java.util.*;
import javax.jms.*;

import javax.oss.EventPropertyDescriptor;

import com.nec.oss.cfi.activity.ri.*;
import com.nec.oss.ipb.event.ri.*;
import javax.oss.Event;
import javax.oss.cfi.activity.*;
import javax.oss.ipb.event.*;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.MediationCapability;

/**
 * XML Binding imports
 */
import com.nec.oss.ipb.producer.ri.xml.IPBRIConfiguration;
import com.ilog.ossj.xml.binding.MarshalException;
import com.ilog.ossj.xml.binding.XmlHandler;
import com.ilog.ossj.xml.binding.ClassUtils;
import com.ilog.ossj.xml.util.JDOMUtils;
import com.ilog.ossj.xml.util.Utilities;


/**
 * RI Adapter imports
 */
import com.nec.oss.ipb.producer.ri.xml.IPBRIConfiguration;
import com.nec.oss.ipb.producer.ri.IPBConfig;

/**
 * Helper class to publish the event results from the producer
 * session to the JMS topic. This is a Singleton class
 * 
 */

public class EventHelper
{
    private static EventHelper myInstance = null;
    private InitialContext ic = null;
    TopicConnectionFactory tConnFactory = null;

    TopicConnection tConn = null;
    TopicSession tSess = null;
    TopicPublisher jvtPublisher = null;
    TopicPublisher xvtPublisher = null;
    TextMessage txtMsg = null;
    ObjectMessage objMsg = null;
	String applicationDN;

	protected XmlHandler xmlHandler = null;

	// Property descriptors
	ActivityCreationEventPropertyDescriptorImpl createEvtPD=null;
	ActivityRemovalEventPropertyDescriptorImpl removalEvtPD=null;
	ActivityReportAvailableEventPropertyDescriptorImpl rptAvailEvtPD=null;
	ActivityReportDataEventPropertyDescriptorImpl rptDataEvtPD=null;
	ActivityResumeEventPropertyDescriptorImpl resumeEvtPD=null;
	ActivitySuspendEventPropertyDescriptorImpl suspendEvtPD=null;
	MediationCapabilityChangeEventPropertyDescriptorImpl medCapChgEvtPD=null;
	UsageDataAvailableEventPropertyDescriptorImpl usageDataAvailEvtPD=null;


    // Make this singleton instance.
    private EventHelper() 
	{
		applicationDN=IPBConfig.getInstance().getApplicationDN();
	}

    /**
     * Singleton instance accessor method
     * @return EventHelper instance
     */
    public static EventHelper getInstance()
    {
        if(myInstance == null)
        {
            myInstance = new EventHelper();

            // invoke init to set up the JMS stuff.
            myInstance.init();
        }

        return myInstance;
    }
    
    /**
     * Initialization method - creates the topic publishers for JVT and XVT
     */
    public void init()
    {
        try
        {
            ic = new InitialContext();

			// First, lookup the enviornment values for the 
			// parameters
			String jmsFactoryName =  (String)
            	ic.lookup( "java:comp/env/JMS_Factory_JDNI_Name");

            tConnFactory =
                (TopicConnectionFactory) ic.lookup(jmsFactoryName);

            tConn = tConnFactory.createTopicConnection();
            tSess = tConn.createTopicSession(
                false, Session.AUTO_ACKNOWLEDGE);

    		jvtPublisher = createTopicPublisher(
								"java:comp/env/JVT_Event_Topic_JNDI_Name");
    		xvtPublisher = createTopicPublisher(
								"java:comp/env/XVT_Event_Topic_JNDI_Name");

            txtMsg = tSess.createTextMessage();
            objMsg = tSess.createObjectMessage(null);
		}

        catch(javax.naming.NamingException ne)
        {
            ne.printStackTrace();
            return;
        }
        
        catch(javax.jms.JMSException je)
        {
            je.printStackTrace();
            return;
        }
        catch (Exception ex)
        {
			System.out.println("Error looking up stuff:"+ex);
			ex.printStackTrace();
        }

		// Create and initialize the EventPropertyDescriptors
		createEvtPD = new ActivityCreationEventPropertyDescriptorImpl();
		removalEvtPD  = new ActivityRemovalEventPropertyDescriptorImpl();
		rptAvailEvtPD = new ActivityReportAvailableEventPropertyDescriptorImpl();
		rptDataEvtPD = new ActivityReportDataEventPropertyDescriptorImpl();
		resumeEvtPD = new ActivityResumeEventPropertyDescriptorImpl();
		suspendEvtPD = new ActivitySuspendEventPropertyDescriptorImpl();
		medCapChgEvtPD = new MediationCapabilityChangeEventPropertyDescriptorImpl();
		usageDataAvailEvtPD = new UsageDataAvailableEventPropertyDescriptorImpl();
    }

    /**
     * Cleanup method - closes the topic publishers
     */
    public void cleanup()
    {
        if(jvtPublisher != null)
        {
            try
            {
                jvtPublisher.close();
            }
            catch(JMSException je)
            {
                je.printStackTrace();
            }
			jvtPublisher = null;
        }
        if(xvtPublisher != null)
        {
            try
            {
                xvtPublisher.close();
            }
            catch(JMSException je)
            {
                je.printStackTrace();
            }
			xvtPublisher = null;
        }
 
 
        if(tSess != null)
        {
            try
            {
                tSess.close();
            }
            catch(JMSException je)
            {
                je.printStackTrace();
            }
			tSess = null;
        }
        if(tConn != null)
        {
            try
            {
                tConn.close();
            }
            catch(JMSException je)
            {
                je.printStackTrace();
            }
			tConn=null;
        }
    }

    /**
     * Send the ActivityCreationEvent for a new Producer being created
     * @param prodVal Newly created Producer
     */
	public void sendCreationEvent(ProducerValue prodVal)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivityCreationEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivityCreationEvent evt = 
								(ActivityCreationEvent)createEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the ActivitySuspendEvent for a Producer being suspended
     * @param prodVal Producer which has been suspended
     */
	public void sendSuspendEvent(ProducerValue prodVal)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivitySuspendEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivitySuspendEvent evt = 
								(ActivitySuspendEvent)suspendEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the ActivityResumeEvent for a Producer being resumed
     * @param prodVal Producer which has been resumed
     */
	public void sendResumeEvent(ProducerValue prodVal)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivityResumeEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivityResumeEvent evt = (ActivityResumeEvent)resumeEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the ActivityRemovalEvent for a Producer being removed
     * @param prodVal Producer which has been removed
     */
	public void sendRemovalEvent(ProducerValue prodVal)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivityRemovalEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivityRemovalEvent evt = 
							(ActivityRemovalEvent)removalEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the MediationCapabilityChangeEvent for a Producer that has been
     * changed. Note that this method isn't fully implemented to have all of the
     * MediationCapability values for comparison
     * @param prodVal Producer which has been modified
     * @oldMedCaps Previous MediationCapability values
     */
	public void sendMediationCapabilityChangeEvent(ProducerValue prodVal,  
											MediationCapability[] oldMedCaps)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.ipb.producer.MediationCapabilityChangeEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		MediationCapabilityChangeEvent evt = 
					(MediationCapabilityChangeEvent)medCapChgEvtPD.makeEvent();
		evt.setProducerKey(prodVal.getProducerKey());
		//evt.setNewMediationCapability(prodVal.getMediationCapabilityValues());
		//evt.setOldMediationCapability(prodVal.getProducerKey());
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the ActivityReportAvailableEvent to indicate that a report is 
     * available.
     * @param prodVal Producer which has generated the report
     */
	public void sendReportAvailableEvent(ProducerValue prodVal)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivityReportAvailableEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivityReportAvailableEvent evt = 
					(ActivityReportAvailableEvent)rptAvailEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		publishMessage(evt, props);
	}

    /**
     * Send the ActivityReportDataEvent to indicate that a report is 
     * available.
     * @param prodVal Producer which has generated the data
     * @param reportData New report data
     * @param reportFormat Format of the new report data
     */
	public void sendReportDataEvent(ProducerValue prodVal, Object reportData,
										ReportFormat reportFormat )
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.cfi.activity.ActivityReportDataEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
		ActivityReportDataEvent evt = 
						(ActivityReportDataEvent)rptDataEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		evt.setReportData(reportData);
		evt.setReportFormat(reportFormat);
		publishMessage(evt, props);
	}

    /**
     * Send the UsageDataAvailableEvent to indicate that usage data is available
     * @param prodVal Producer which has generated the data
     * @param reportInfo Description of the report data
     * @param availableSince Time that the data has been available
     */
	public void sendUsageDataAvailableEvent(ProducerValue prodVal, 
          ReportInfo reportInfo, Calendar availableSince)
	{
        java.util.Hashtable props = new java.util.Hashtable();
		props.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
					"javax.oss.ipb.producer.UsageDataAvailableEvent");
		props.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                     applicationDN);
        props.put(ActivityEventPropertyDescriptor.ACTIVITY_NAME_PROP_NAME,
							prodVal.getActivityName());
        props.put(UsageDataAvailableEventPropertyDescriptor.AVAILABLE_SINCE_TIME_PROP_NAME,
							new java.util.Date());
		UsageDataAvailableEvent evt = 
				(UsageDataAvailableEvent)usageDataAvailEvtPD.makeEvent();
		evt.setActivityValue(prodVal);
		evt.setEventTime(new java.util.Date());
		evt.setApplicationDN(applicationDN);
		evt.setReportInformation(reportInfo);
		evt.setAvailableSince(availableSince);
		publishMessage(evt, props);
	}

    /**
     * Publish message to the JVT and XVT JMS event topics
     * @param event Formatted event to publish
     * @param properties Formatted event properties
     */
	public void publishMessage(Event event, Hashtable properties )
	{
		// If the TopicSession is null, initialization hasn't taken
		// place yet. Call init()
        if(tSess == null)
        {
            init();
        }

		// Try to send the message for the publishers that are valid
        try
        {
			// If the JVT Publisher has been successfully initialized,
			// publish the JVT event
			if(jvtPublisher != null)
			{
            	objMsg.setObject(event);
				setMessageProperties(objMsg, properties);
            	jvtPublisher.publish(objMsg);
			}

			// If the XVT Publisher has been successfully initialized,
			// publish the XVT event
			if(xvtPublisher != null)
			{
				xmlHandler = new XmlHandler(IPBRIConfiguration.getRegistry(),
                                 event.getClass());
				if(xmlHandler != null)
				{
					Class evtClass = ClassUtils.getSubInterface(
												javax.oss.Event.class, event);
					String elementName = Utilities.getShortClassName(evtClass);
					elementName = Utilities.lowerFirstLetter(elementName);
					org.jdom.Element eventElement = new org.jdom.Element(
                                    	elementName,
                                    	Utilities.getNamespace(evtClass));
					org.jdom.Element eventBodyElement =
											xmlHandler.marshal(event, "event");
					eventElement.addContent(eventBodyElement);	
					setAdditionalNamespaces(eventElement);
					String messageText = JDOMUtils.getXmlString(eventElement);
					txtMsg.setText(messageText);
					setMessageProperties(txtMsg, properties);
            		xvtPublisher.publish(txtMsg);
				}
				else
				{
					System.out.println("Error initializing XmlHandler");
				}
			}
        }
        catch(JMSException ex)
        {
            ex.printStackTrace();
        }
        catch(MarshalException ex)
        {
            ex.printStackTrace();
        }
	}


    /**
     * Set the message properties that are provided
     * @param msg Message which should contain the properties
     * @param properties Properties that should be set
     */
	public void setMessageProperties(Message msg, Hashtable properties)
	{
		Enumeration keys = properties.keys();
		String key = null;
		Object value = null;

		while (keys.hasMoreElements())
		{
			Object okey = keys.nextElement();
			if (! (okey instanceof String) )
			{
				System.out.println ("setMessageProperties - Invalid property name:"+okey);
			}
			else
			{
				key = (String)okey;
			}

			value = properties.get(key);

			try
			{
				if (value instanceof Integer)
				{
					msg.setIntProperty(key,((Integer)value).intValue());
				}
				else if (value instanceof Short)
				{
					msg.setShortProperty(key,((Short)value).shortValue());
				}
				else if (value instanceof Boolean)
				{
					msg.setBooleanProperty(key,((Boolean)value).booleanValue());
				}
				else if (value instanceof Double)
				{
					msg.setDoubleProperty(key,((Double)value).doubleValue());
				}
				else if (value instanceof Long)
				{
					msg.setLongProperty(key,((Long)value).longValue());
				}
				else if (value instanceof Float)
				{
					msg.setFloatProperty(key,((Float)value).floatValue());
				}
				else if (value instanceof String)
				{
					msg.setStringProperty(key,(String)value);
				}
				else
				{
					System.out.println("utils::setMessageProperty: unknown value type, property ignored. Type="+value.getClass());
				}
			}
			catch (MessageNotWriteableException mex)
			{
				System.out.println("utils::setMessageProperty: MessageNotWriteableException occured");
			}
			catch (JMSException jex)
			{
				System.out.println("utils::setMessageProperty: JMSException occurred: " + jex);
				jex.printStackTrace();
			}
		}
	}


    /**
     * Utility method to set XML Namespace parameters in the XML Event
     * @param rootElement XML Element which will have the XML namespace info
     */
	protected void setAdditionalNamespaces(org.jdom.Element rootElement)
	{
		rootElement.addNamespaceDeclaration(org.jdom.Namespace.getNamespace("http://www.w3.org/2001/XMLSchema"));
		rootElement.addNamespaceDeclaration(Utilities.getXsiNamespace());

		org.jdom.Namespace namespace = rootElement.getNamespace();
		org.jdom.Namespace[] registeredNamespaces = Utilities.getAllRegisteredNamespaces();
		for (int i=0; i<registeredNamespaces.length; i++)
		{
			if (!registeredNamespaces[i].equals(namespace))
			{
				rootElement.addNamespaceDeclaration(registeredNamespaces[i]);
			}
		}
	}


    /**
     * Utility method to create the JMS Topic Publisher
     * @param jndiEnvName Name of the env variable specifying the topic name
     * @return TopicPublisher for the specified JNDI name
     */
    protected TopicPublisher createTopicPublisher(String jndiEnvName)
	{
	    TopicPublisher eventTopicPublisher=null;

		try
		{
			String topicName = (String) ic.lookup(jndiEnvName);
    		Topic eventTopic = (Topic) ic.lookup(topicName);
            eventTopicPublisher = tSess.createPublisher(eventTopic);
		}
		catch (Exception ex)
		{
			// If this isn't defined, it will become obvious later
		}

		return(eventTopicPublisher);
	}
}
