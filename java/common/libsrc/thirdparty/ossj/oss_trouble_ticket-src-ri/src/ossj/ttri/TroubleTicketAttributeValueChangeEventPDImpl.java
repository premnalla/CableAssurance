package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.oss.Event;
import javax.oss.EventPropertyDescriptor;
import javax.oss.trouble.TroubleTicketAttributeValueChangeEvent;
import javax.oss.trouble.TroubleTicketAttributeValueChangeEventPropertyDescriptor;

/**
 * TroubleTicketAttributeValueChangeEvent Property Descriptor
 * Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketAttributeValueChangeEventPDImpl
        extends EventPropertyDescriptorImpl
        implements TroubleTicketAttributeValueChangeEventPropertyDescriptor {


    private static TroubleTicketAttributeValueChangeEventPDImpl singleton = new TroubleTicketAttributeValueChangeEventPDImpl();

    static public final TroubleTicketAttributeValueChangeEventPDImpl getInstance() {
        return singleton;
    }

    public TroubleTicketAttributeValueChangeEvent makeTroubleTicketAttributeValueChangeEvent() {
        return new TroubleTicketAttributeValueChangeEventImpl();
    }

    public javax.jms.ObjectMessage
            makeTroubleTicketAttributeValueChangeEventObjectMessage(TroubleTicketAttributeValueChangeEvent event,
                                                                    java.util.Hashtable properties) {

        ObjectMessage objMsg = null;
        try {
            objMsg = topicSession.createObjectMessage();
            objMsg.setObject(event);
        } catch (JMSException jex) {
            Logger.logException("AttributeValueChangeEventPDImpl::makeTroubleTicketAttributeValueChangeEventObjectMessage - JMSException caught", jex);
        }

        //set the mandatory properties
        addMandatoryProperties(properties);

        //set the user defined properties
        utils.setMessageProperties(objMsg, properties);

        return objMsg;

    }

    public javax.jms.TextMessage
            makeTroubleTicketAttributeValueChangeEventTextMessage(TroubleTicketAttributeValueChangeEvent event,
                                                                  java.util.Hashtable properties) {

        //cast to TroubleTicketCreatEventImpl to pick up toXml()
        TroubleTicketAttributeValueChangeEventImpl ttEvent = (TroubleTicketAttributeValueChangeEventImpl) event;

        TextMessage txtMsg = null;
        try {
            txtMsg = topicSession.createTextMessage();
            txtMsg.setText(ttEvent.toXml());
        } catch (JMSException jex) {
            Logger.logException("AttributeValueChangeEventPDImpl::makeTroubleTicketAttributeValueChangeEventTextMessage - JMSException caught", jex);
        } catch (org.xml.sax.SAXException sex) {
            Logger.logException("AttributeValueChangeEventPDImpl::makeTroubleTicketAttributeValueChangeEventTextMessage - org.xml.sax.SAXException caught", sex);

        }

        //set the mandatory properties
        addXMLMandatoryProperties(properties);



        //set the user defined properties
        utils.setMessageProperties(txtMsg, properties);

        return txtMsg;

    }

    private void addMandatoryProperties(java.util.Hashtable properties) {
        //add the mandatory event type, application type, and version number properties

        //properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
        //               TroubleTicketAttributeValueChangeEventPropertyDescriptor.OSS_EVENT_TYPE_VALUE);
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "javax.oss.trouble.TroubleTicketAttributeValueChangeEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
        //properties.put(EventPropertyDescriptor.OSS_VERSION_NUMBER_PROP_NAME,
        //               TTConfig.getInstance().versionNumber);
    }
    //methods from base interface EventPropertyDescriptor (oss package)
    //for the "make" methods, delegate the call to the concrete
    //implementation above.

    private void addXMLMandatoryProperties(java.util.Hashtable properties) {
        properties.put(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                "tt:TroubleTicketAttributeValueChangeEvent");

        properties.put(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                TTConfig.getInstance().applicationTypeDN);
    }

    public javax.jms.ObjectMessage
            makeObjectMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketAttributeValueChangeEvent) {
            return makeTroubleTicketAttributeValueChangeEventObjectMessage
                    ((TroubleTicketAttributeValueChangeEvent) event, properties);
        }
        return null;
    }

    public javax.jms.TextMessage
            makeTextMessage(Event event, java.util.Hashtable properties) {

        if (event instanceof TroubleTicketAttributeValueChangeEvent) {
            return makeTroubleTicketAttributeValueChangeEventTextMessage
                    ((TroubleTicketAttributeValueChangeEvent) event, properties);
        }
        return null;

    }

    public Event makeEvent() {
        return new TroubleTicketAttributeValueChangeEventImpl();
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object deepCopy() {
        try {
            return this.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }


}







