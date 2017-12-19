package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.trouble.TroubleTicketCreateEvent;
import javax.oss.trouble.TroubleTicketCreateEventPropertyDescriptor;

/**
 * TroubleTicketCreateEvent Property Descriptor Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketCreateEventPDImpl
        extends EventPropertyDescriptorImpl
        implements TroubleTicketCreateEventPropertyDescriptor {

    private static TroubleTicketCreateEventPDImpl singleton = new TroubleTicketCreateEventPDImpl();

    static public final TroubleTicketCreateEventPDImpl getInstance() {
        return singleton;
    }

    public TroubleTicketCreateEvent makeTroubleTicketCreateEvent() {
        return new TroubleTicketCreateEventImpl();
    }

    public javax.jms.ObjectMessage
            makeTroubleTicketCreateEventObjectMessage(TroubleTicketCreateEvent event,
                                                      java.util.Hashtable properties) {

        ObjectMessage objMsg = null;
        try {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);
        } catch (JMSException jex) {
            jex.printStackTrace();
            Logger.logException("CreateEventPDImpl::makeTroubleTicketCreateEventObjectMessage - JMSException caught", jex);
        }

        //set the mandatory properties
        addMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(objMsg, properties);

        return objMsg;

    }

    public javax.jms.TextMessage
            makeTroubleTicketCreateEventTextMessage(TroubleTicketCreateEvent event,
                                                    java.util.Hashtable properties) {

        //cast to TroubleTicketCreatEventImpl to pick up toXml()
        TroubleTicketCreateEventImpl ttEvent = (TroubleTicketCreateEventImpl) event;

        TextMessage txtMsg = null;
        try {
            txtMsg = topicSession.createTextMessage();
            txtMsg.setText(ttEvent.toXml());
        } catch (JMSException jex) {
            Logger.logException("CreateEventPDImpl::makeTroubleTicketCreateEventTextMessage - JMSException caught", jex);
        } catch (org.xml.sax.SAXException sex) {
            Logger.logException("CreateEventPDImpl::makeTroubleTicketCreateEventTextMessage - org.xml.sax.SAXException caught", sex);

        }

        //set the mandatory properties
        addXMLMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(txtMsg, properties);

        return txtMsg;

    }

    private void addMandatoryProperties(java.util.Hashtable properties) {
        //add the mandatory event type, application type, and version number properties
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                ("javax.oss.trouble.TroubleTicketCreateEvent"));
        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);

    }

    private void addXMLMandatoryProperties(java.util.Hashtable properties) {
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "tt:TroubleTicketCreateEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
    }

    //methods from base interface EventPropertyDescriptor (oss package)
    //for the "make" methods, delegate the call to the concrete
    //implementation above.

    public javax.jms.ObjectMessage
            makeObjectMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCreateEvent) {
            return makeTroubleTicketCreateEventObjectMessage
                    ((TroubleTicketCreateEvent) event, properties);
        }
        return null;
    }

    public javax.jms.TextMessage
            makeTextMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCreateEvent) {
            return makeTroubleTicketCreateEventTextMessage
                    ((TroubleTicketCreateEvent) event, properties);
        }
        return null;

    }

    public Event makeEvent() {
        return new TroubleTicketCreateEventImpl();
    }


}
