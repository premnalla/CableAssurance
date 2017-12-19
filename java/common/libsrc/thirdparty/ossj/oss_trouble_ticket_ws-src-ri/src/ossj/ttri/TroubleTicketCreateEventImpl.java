package ossj.ttri;

import javax.oss.XmlSerializer;
import javax.oss.trouble.TroubleTicketCreateEvent;
import javax.oss.trouble.TroubleTicketValue;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * TroubleTicketCreateEvent Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class TroubleTicketCreateEventImpl extends TroubleTicketEvent
        implements TroubleTicketCreateEvent,
        Serializable {
    private TroubleTicketValue ttValue;

    private static final String RootElementTag = "troubleTicketCreateEvent";


    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(TroubleTicketCreateEvent.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }

    public TroubleTicketCreateEventImpl() {
        //setEventType("TroubleTicketCreationEvent");
        setEventType("TroubleTicketCreateEvent");  //WIPRO
        setEventTime(new java.util.Date());
    }

    public void setTroubleTicketValue(TroubleTicketValue ttVal) {
        ttValue = ttVal;
    }

    public TroubleTicketValue getTroubleTicketValue() {
        return ttValue;
    }

    //---------------------------------------------------------------
    // following XML methods from javax.oss.XMLSerializable interface
    //---------------------------------------------------------------
    public String toXml()
            throws org.xml.sax.SAXException {
        String[] populatedAttr = ((TroubleTicketValueImpl) ttValue).getPopulatedAttributeNames();
        Logger.log("TTCreateEventImpl:toXml - popAttrs: " + populatedAttr.length);
        ((TroubleTicketValueImpl) ttValue).setDesiredAttributes(populatedAttr, false);
        // As a first step convert to ttValue into an xml string format.
        String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();

        return addXmlRootElement(RootElementTag, xmlTTValue);
    }

    private String addXmlRootElement(String RootElementTag, String xmlTTValue) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<?xml version=\"1.0\"?>" + "\n");
        strBuffer.append("<tt:" + RootElementTag + "\n");
        strBuffer.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\"\n" +
                "xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"\n" +
                "xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"\n" +
                "xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"\n" +
                "xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"\n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                // "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket\">\n\n" );
                "xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">\n\n");
        strBuffer.append("<tt:event>\n");
        strBuffer.append("<co:applicationDN>");
        strBuffer.append(getApplicationDN());
        strBuffer.append("</co:applicationDN>\n");

        strBuffer.append("<co:eventTime>");

        if (getEventTime() != null) {
            strBuffer.append(getXmlDate(getEventTime()));
        } else {
            strBuffer.append("0000-00-00T00:00:00Z");
        }
        strBuffer.append("</co:eventTime>\n");


        strBuffer.append(xmlTTValue);
        strBuffer.append("</tt:event>\n");
        strBuffer.append("</tt:" + RootElementTag + ">");

        return strBuffer.toString();
    }

    //===============================================
    private String getXmlDate(java.util.Date date) {
        // Need to convert the from the Date format into the xml timeInstant
        // format.
        // Example date format:	   7 Mar 2001 10:10:10 GMT
        // Equivalent xml format:  2001-03-07T10:10:10Z

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        String xmlStr = sdf.format(date);
        return xmlStr;
    }


    public void fromXml(org.w3c.dom.Element element)
            throws org.xml.sax.SAXException {
        //to do
    }

    /**
     public String toXml()
     {
     String retString = new String();
     //to do
     return retString;
     }
     **/
    public String toXml(String elementName)
            throws org.xml.sax.SAXException {
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("\n<" + elementName + ">\n");


        strBuffer.append("<tt:eventType>");
        strBuffer.append(getEventType());
        strBuffer.append("</tt:eventType>\n");

        strBuffer.append("<tt:eventTime>");
        //strBuffer.append( getXmlDate(getEventTime()) );
        if (getEventTime() != null) {
            strBuffer.append(getXmlDate(getEventTime()));
        } else {
            strBuffer.append("0000-00-00T00:00:00Z");
        }
        strBuffer.append("</tt:eventTime>\n");


        strBuffer.append("<tt:applicationDN>");
        strBuffer.append(getApplicationDN());
        strBuffer.append("</tt:applicationDN>\n");


        String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();
        strBuffer.append(xmlTTValue);
        strBuffer.append("</" + elementName + ">\n");


        return strBuffer.toString();

    }

    public String getXmlHeader() {
        String retString = new String();
        //to do
        return retString;
    }

    public String getRootName() {
        String retString = new String();
        //to do
        return retString;
    }

    public String getRootAttributes() {
        return null;
    }

    public boolean equals(Object arg) {

        if ((arg != null) && (arg instanceof TroubleTicketCreateEvent)) {

            TroubleTicketCreateEvent event = (TroubleTicketCreateEvent) arg;

            if ((getTroubleTicketValue() == null) && (event.getTroubleTicketValue() != null)) return false;
            if ((getTroubleTicketValue() != null) && (event.getTroubleTicketValue() == null)) return false;
            if ((getTroubleTicketValue() != null) && (event.getTroubleTicketValue() != null)) {
                if (!(getTroubleTicketValue().equals(event.getTroubleTicketValue()))) return false;
            }

            if ((getApplicationDN() == null) && (event.getApplicationDN() != null)) return false;
            if ((getApplicationDN() != null) && (event.getApplicationDN() == null)) return false;
            if ((getApplicationDN() != null) && (event.getApplicationDN() != null)) {
                if (!(getApplicationDN().equals(event.getApplicationDN()))) return false;
            }

            if ((getEventTime() == null) && (event.getEventTime() != null)) return false;
            if ((getEventTime() != null) && (event.getEventTime() == null)) return false;
            if ((getEventTime() != null) && (event.getEventTime() == null)) {
                if (!(getEventTime().toString().equals(event.getEventTime().toString()))) return false;
            }

            return true;
        } else
            return false;
    }

}

