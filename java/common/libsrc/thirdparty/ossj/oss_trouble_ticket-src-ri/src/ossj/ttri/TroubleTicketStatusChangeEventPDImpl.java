package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.trouble.TroubleTicketStatusChangeEvent;
import javax.oss.trouble.TroubleTicketStatusChangeEventPropertyDescriptor;

/**
 * TroubleTicketStatusChangeEvent Property Descriptor Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketStatusChangeEventPDImpl
        extends EventPropertyDescriptorImpl
        implements TroubleTicketStatusChangeEventPropertyDescriptor {

    private static TroubleTicketStatusChangeEventPDImpl singleton = new TroubleTicketStatusChangeEventPDImpl();

    static public final TroubleTicketStatusChangeEventPDImpl getInstance() {
        return singleton;
    }

    public TroubleTicketStatusChangeEvent makeTroubleTicketStatusChangeEvent() {
        return new TroubleTicketStatusChangeEventImpl();
    }

    public javax.jms.ObjectMessage
            makeTroubleTicketStatusChangeEventObjectMessage(TroubleTicketStatusChangeEvent event,
                                                            java.util.Hashtable properties) {

        ObjectMessage objMsg = null;
        try {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);
        } catch (JMSException jex) {
            Logger.logException("StatusChangeEventPDImpl::makeTroubleTicketStatusChangeEventObjectMessage - JMSException caught", jex);
        }

        //set the mandatory properties
        addMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(objMsg, properties);

        return objMsg;

    }

    public javax.jms.TextMessage
            makeTroubleTicketStatusChangeEventTextMessage(TroubleTicketStatusChangeEvent event,
                                                          java.util.Hashtable properties) {

        //cast to TroubleTicketCreatEventImpl to pick up toXml()
        TroubleTicketStatusChangeEventImpl ttEvent = (TroubleTicketStatusChangeEventImpl) event;

        TextMessage txtMsg = null;
        try {
            txtMsg = topicSession.createTextMessage();
            txtMsg.setText(ttEvent.toXml());
        } catch (JMSException jex) {
            Logger.logException("StatusChangeEventPDImpl::makeTroubleTicketStatusChangeEventTextMessage - JMSException caught", jex);
        } catch (org.xml.sax.SAXException sex) {
            Logger.logException("StatusChangeEventPDImpl::makeTroubleTicketStatusChangeEventTextMessage - org.xml.sax.SAXException caught", sex);

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
                "javax.oss.trouble.TroubleTicketStatusChangeEvent");
        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
        //properties.put(EventPropertyDescriptor.OSSJ_VERSION_NUMBER_PROP_NAME,
        //               TTConfig.getInstance().versionNumber);
    }

    private void addXMLMandatoryProperties(java.util.Hashtable properties) {
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "tt:TroubleTicketStatusChangeEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
    }

    //methods from base interface EventPropertyDescriptor (oss package)
    //for the "make" methods, delegate the call to the concrete
    //implementation above.

    public javax.jms.ObjectMessage
            makeObjectMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketStatusChangeEvent) {
            return makeTroubleTicketStatusChangeEventObjectMessage
                    ((TroubleTicketStatusChangeEvent) event, properties);
        }
        return null;
    }

    public javax.jms.TextMessage
            makeTextMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketStatusChangeEvent) {
            return makeTroubleTicketStatusChangeEventTextMessage
                    ((TroubleTicketStatusChangeEvent) event, properties);
        }
        return null;

    }

    public Event makeEvent() {
        return new TroubleTicketStatusChangeEventImpl();
    }


}
