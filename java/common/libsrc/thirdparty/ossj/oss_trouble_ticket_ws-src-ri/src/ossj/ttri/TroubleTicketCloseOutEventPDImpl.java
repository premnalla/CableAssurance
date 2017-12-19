package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.trouble.TroubleTicketCloseOutEvent;
import javax.oss.trouble.TroubleTicketCloseOutEventPropertyDescriptor;

/**
 * TroubleTicketCloseOutEvent Property Descriptor Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketCloseOutEventPDImpl
        extends EventPropertyDescriptorImpl
        implements TroubleTicketCloseOutEventPropertyDescriptor {

    private static TroubleTicketCloseOutEventPDImpl singleton = new TroubleTicketCloseOutEventPDImpl();

    static public final TroubleTicketCloseOutEventPDImpl getInstance() {
        return singleton;
    }

    public TroubleTicketCloseOutEvent makeTroubleTicketCloseOutEvent() {
        return new TroubleTicketCloseOutEventImpl();
    }

    public javax.jms.ObjectMessage
            makeTroubleTicketCloseOutEventObjectMessage(TroubleTicketCloseOutEvent event,
                                                        java.util.Hashtable properties) {

        ObjectMessage objMsg = null;
        try {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);
        } catch (JMSException jex) {
            Logger.logException("CloseOutEventPDImpl::makeTroubleTicketCloseOutEventObjectMessage - JMSException caught", jex);
        }

        //set the mandatory properties
        addMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(objMsg, properties);
        return objMsg;

    }

    public javax.jms.TextMessage
            makeTroubleTicketCloseOutEventTextMessage(TroubleTicketCloseOutEvent event,
                                                      java.util.Hashtable properties) {

        //cast to TroubleTicketCloseOutEventImpl to pick up toXml()
        TroubleTicketCloseOutEventImpl ttEvent = (TroubleTicketCloseOutEventImpl) event;

        TextMessage txtMsg = null;
        try {
            txtMsg = topicSession.createTextMessage();
            String xmlStr = ttEvent.toXml();
            txtMsg.setText(xmlStr);
        } catch (org.xml.sax.SAXException saxex) {
            Logger.logException("CloseOutEventPDImpl::makeTroubleTicketCloseOutEventTextMessage - org.xml.sax.SAXException caught", saxex);

        } catch (JMSException jex) {
            Logger.logException("CloseOutEventPDImpl::makeTroubleTicketCloseOutEventTextMessage - JMSException caught", jex);
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
                "javax.oss.trouble.TroubleTicketCloseOutEvent");
        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
        //properties.put(EventPropertyDescriptor.OSS_VERSION_NUMBER_PROP_NAME,
        //               TTConfig.getInstance().versionNumber);
    }

    private void addXMLMandatoryProperties(java.util.Hashtable properties) {
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "tt:TroubleTicketCloseOutEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
    }

    //methods from base interface EventPropertyDescriptor (oss package)
    //for the "make" methods, delegate the call to the concrete
    //implementation above.

    public javax.jms.ObjectMessage
            makeObjectMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCloseOutEvent) {
            return makeTroubleTicketCloseOutEventObjectMessage
                    ((TroubleTicketCloseOutEvent) event, properties);
        }
        return null;
    }

    public javax.jms.TextMessage
            makeTextMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCloseOutEvent) {
            return makeTroubleTicketCloseOutEventTextMessage
                    ((TroubleTicketCloseOutEvent) event, properties);
        }
        return null;

    }

    public Event makeEvent() {
        return new TroubleTicketCloseOutEventImpl();
    }


}
