package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.trouble.TroubleTicketCancellationEvent;
import javax.oss.trouble.TroubleTicketCancellationEventPropertyDescriptor;

/**
 * TroubleTicketCancellationEvent Property Descriptor
 * Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketCancellationEventPDImpl
        extends EventPropertyDescriptorImpl
        implements TroubleTicketCancellationEventPropertyDescriptor {


    private static TroubleTicketCancellationEventPDImpl singleton = new TroubleTicketCancellationEventPDImpl();

    static public final TroubleTicketCancellationEventPDImpl getInstance() {
        return singleton;
    }


    public TroubleTicketCancellationEvent makeTroubleTicketCancellationEvent() {
        return new TroubleTicketCancellationEventImpl();
    }

    public javax.jms.ObjectMessage
            makeTroubleTicketCancellationEventObjectMessage(TroubleTicketCancellationEvent event,
                                                            java.util.Hashtable properties) {

        ObjectMessage objMsg = null;
        try {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);
        } catch (JMSException jex) {
            Logger.logException("CancellationEventPDImpl::makeTroubleTicketCancellationEventObjectMessage - JMSException caught", jex);
        }

        //set the mandatory properties
        addMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(objMsg, properties);

        return objMsg;

    }

    public javax.jms.TextMessage
            makeTroubleTicketCancellationEventTextMessage(TroubleTicketCancellationEvent event,
                                                          java.util.Hashtable properties) {

        //cast to TroubleTicketCreatEventImpl to pick up toXml()
        TroubleTicketCancellationEventImpl ttEvent = (TroubleTicketCancellationEventImpl) event;

        TextMessage txtMsg = null;
        try {
            txtMsg = topicSession.createTextMessage();
            txtMsg.setText(ttEvent.toXml());
        } catch (JMSException jex) {
            Logger.logException("CancellationEventPDImpl::makeTroubleTicketCancellationEventTextMessage - JMSException caught", jex);
        } catch (org.xml.sax.SAXException sex) {
            Logger.logException("CancellationEventPDImpl::makeTroubleTicketCancellationEventTextMessage - org.xml.sax.SAXException caught", sex);

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
                "javax.oss.trouble.TroubleTicketCancellationEvent");
        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
        //properties.put(EventPropertyDescriptor.OSS_VERSION_NUMBER_PROP_NAME,
        //               TTConfig.getInstance().versionNumber);
    }

    private void addXMLMandatoryProperties(java.util.Hashtable properties) {
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "tt:TroubleTicketCancellationEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
    }



    //methods from base interface EventPropertyDescriptor (oss package)
    //for the "make" methods, delegate the call to the concrete
    //implementation above.

    public javax.jms.ObjectMessage
            makeObjectMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCancellationEvent) {
            return makeTroubleTicketCancellationEventObjectMessage
                    ((TroubleTicketCancellationEvent) event, properties);
        }
        return null;
    }

    public javax.jms.TextMessage
            makeTextMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketCancellationEvent) {
            return makeTroubleTicketCancellationEventTextMessage
                    ((TroubleTicketCancellationEvent) event, properties);
        }
        return null;

    }

    public Event makeEvent() {
        return new TroubleTicketCancellationEventImpl();
    }


}
