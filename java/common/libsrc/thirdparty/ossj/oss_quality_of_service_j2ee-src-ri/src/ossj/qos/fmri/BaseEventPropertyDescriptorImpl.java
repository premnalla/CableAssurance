package ossj.qos.fmri;

import javax.oss.EventPropertyDescriptor;
import javax.oss.Event;
import javax.oss.util.IRPEventPropertyDescriptor;
import javax.oss.util.IRPEvent;
import javax.jms.TopicSession;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.ArrayList;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.jms.JMSException;
import ossj.qos.util.Util;

/**
 * BaseEventPropertyDescriptorImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public abstract class BaseEventPropertyDescriptorImpl implements IRPEventPropertyDescriptor, MessageBuilder {
    
    private ArrayList      propertyNames = null;
    private ArrayList      propertyTypes = null;

    /** Creates new EventPropertyDescriptorImpl */
    public BaseEventPropertyDescriptorImpl() {
        
        propertyNames = new ArrayList();
        propertyNames.add(OSS_EVENT_TYPE_PROP_NAME);
        propertyNames.add(OSS_APPLICATION_DN_PROP_NAME);
        propertyNames.add(OSS_MANAGED_ENTITY_TYPE_PROP_NAME);
        propertyNames.add(OSS_MANAGED_ENTITY_PK_PROP_NAME);
        propertyNames.add(OSS_EVENT_TIME_PROP_NAME);

        propertyTypes = new ArrayList();
        propertyTypes.add(OSS_EVENT_TYPE_PROP_TYPE);
        propertyTypes.add(OSS_APPLICATION_DN_PROP_TYPE);
        propertyTypes.add(OSS_MANAGED_ENTITY_TYPE_PROP_TYPE);
        propertyTypes.add(OSS_MANAGED_ENTITY_PK_PROP_TYPE);
        propertyTypes.add(OSS_EVENT_TIME_PROP_TYPE);
    }
    
    protected void addPropertyName ( String name ) {
        propertyNames.add( name );
        return;
    }
    
    protected void addPropertyType ( String type ) {
        propertyTypes.add( type );
        return;
    }

    public String[] getPropertyNames() {
        return (String[]) propertyNames.toArray( new String[0] );
    }

    public String[] getPropertyTypes() {
        return (String[]) propertyTypes.toArray( new String[0] );
    }

    protected void setDefaultMessageProperties( ObjectMessage msg, IRPEvent event )
    throws javax.jms.JMSException {
 
        if ( event.isPopulated( IRPEvent.APPLICATION_DN ) ) {
            msg.setStringProperty( OSS_APPLICATION_DN_PROP_NAME, event.getApplicationDN() );
        }
        msg.setStringProperty( OSS_MANAGED_ENTITY_PK_PROP_NAME, event.getManagedObjectInstance() );
        msg.setStringProperty( OSS_MANAGED_ENTITY_TYPE_PROP_NAME, event.getManagedObjectClass() );
        msg.setStringProperty( OSS_EVENT_TIME_PROP_NAME, Util.convertUTCTimeString( event.getEventTime() ) );
        
        return;
    }

    protected void updateMessageProperties( ObjectMessage msg, HashMap properties )
    throws javax.jms.JMSException {
 
        Object obj = properties.remove( OSS_EVENT_TYPE_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( OSS_EVENT_TYPE_PROP_NAME, (String) obj );
        }

        obj = properties.remove( OSS_APPLICATION_DN_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( OSS_APPLICATION_DN_PROP_NAME, (String) obj );
        }

        obj = properties.remove( OSS_MANAGED_ENTITY_PK_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( OSS_MANAGED_ENTITY_PK_PROP_NAME, (String) obj );
        }

        obj = properties.remove( OSS_MANAGED_ENTITY_TYPE_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( OSS_MANAGED_ENTITY_TYPE_PROP_NAME, (String) obj );
        }
        
        obj = properties.remove( OSS_EVENT_TIME_PROP_NAME );
        if ( obj != null ) {
            msg.setStringProperty( OSS_EVENT_TIME_PROP_NAME, (String) obj );
        }
        return;
    }
    
    public ObjectMessage makeObjectMessage( Event event,
    java.util.HashMap properties, Session session ) throws IllegalArgumentException {
        
        TopicSession topicSession = (TopicSession)session;
        ObjectMessage objMsg = null;
        try
        {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);

            if ( properties == null ) {
                properties = new HashMap();
            }
            
            setDefaultMessageProperties( objMsg, (IRPEvent)event );
            if ( properties.isEmpty() == false ) {
                updateMessageProperties( objMsg, properties );
            }
        }
        catch (JMSException jex)
        {
            //System.out.println("EventPropertyDescriptorImpl::makeObjectMessage - JMSException caught");
            //System.out.println( jex.toString() );
        }
        return objMsg;
    }
    
    public TextMessage makeTextMessage(Event event, HashMap properties, Session session) {
        // not supported in this implementation.
        return null;
    }
    
}
