package ossj.ttri;

import javax.oss.XmlSerializer;
import javax.oss.trouble.TroubleTicketCancellationEvent;
import javax.oss.trouble.TroubleTicketKey;
import java.text.SimpleDateFormat;

/**
 * TroubleTicketCancellationEvent Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketCancellationEventImpl
        extends TroubleTicketEvent
        implements TroubleTicketCancellationEvent,
        java.io.Serializable {
    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(TroubleTicketCancellationEvent.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }

    // public String[] getSupportedOptionalAttributeNames() { return null; }
    private TroubleTicketKey Key;
    private String CloseOutNarr;
    private static final String RootElementTag = "troubleTicketCancellationEvent";

    public TroubleTicketKey getTroubleTicketKey() {
        return Key;
    }

    public String getCloseOutNarr() {
        return CloseOutNarr;
    }

    public void setTroubleTicketKey(TroubleTicketKey key) {
        Key = key;
    }

    public void setCloseOutNarr(String narr) {
        CloseOutNarr = narr;
    }

    //methods from XMLSerializable
    public void fromXml(org.w3c.dom.Element element)
            throws org.xml.sax.SAXException {

    }

    //---------------------------------------------------------------
    // following XML methods from javax.oss.XMLSerializable interface
    //---------------------------------------------------------------
    public String toXml()
            throws org.xml.sax.SAXException {
        StringBuffer strBuffer = new StringBuffer();
        //TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();

        TroubleTicketKey trKey = this.getTroubleTicketKey();
        String xmlKey = ((TroubleTicketKeyImpl) trKey).toXml();
        strBuffer.append(xmlKey);
        //---------------------------------

        strBuffer.append("<tt:closeOutNarr>");
        strBuffer.append(this.getCloseOutNarr());
        strBuffer.append("</tt:closeOutNarr>\n");


        return addXmlRootElement(RootElementTag, strBuffer.toString());
    }

    private String addXmlRootElement(String RootElementTag, String xmlEventCore) {
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
                //"xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket\">\n\n" );
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


        strBuffer.append(xmlEventCore);
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

        TroubleTicketKey trKey = this.getTroubleTicketKey();
        String xmlKey = ((TroubleTicketKeyImpl) trKey).toXml();
        strBuffer.append(xmlKey);
        //---------------------------------

        strBuffer.append("<tt:closeOutNarr>");
        strBuffer.append(this.getCloseOutNarr());
        strBuffer.append("</tt:closeOutNarr>\n");

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

        if ((arg != null) && (arg instanceof TroubleTicketCancellationEvent)) {

            TroubleTicketCancellationEvent event = (TroubleTicketCancellationEvent) arg;

            if ((getTroubleTicketKey() == null) && (event.getTroubleTicketKey() != null)) return false;
            if ((getTroubleTicketKey() != null) && (event.getTroubleTicketKey() == null)) return false;
            if ((getTroubleTicketKey() != null) && (event.getTroubleTicketKey() != null)) {
                if (!(getTroubleTicketKey().equals(event.getTroubleTicketKey()))) return false;
            }
            if ((getCloseOutNarr() == null) && (event.getCloseOutNarr() != null)) return false;
            if ((getCloseOutNarr() != null) && (event.getCloseOutNarr() == null)) return false;
            if ((getCloseOutNarr() != null) && (event.getCloseOutNarr() != null)) {
                if (!(getCloseOutNarr().equals(event.getCloseOutNarr()))) return false;
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










