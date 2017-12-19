package ossj.ttri;

import javax.oss.XmlSerializer;
import javax.oss.trouble.TroubleState;
import javax.oss.trouble.TroubleStatus;
import javax.oss.trouble.TroubleTicketKey;
import javax.oss.trouble.TroubleTicketStatusChangeEvent;
import java.text.SimpleDateFormat;

/**
 * TroubleTicketStatusChangeEvent Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketStatusChangeEventImpl
        extends TroubleTicketEvent
        implements TroubleTicketStatusChangeEvent,
        java.io.Serializable,
        Cloneable {


    private TroubleTicketKey Key;
    private int Status;
    private java.util.Date StatusTime;
    private int State;
    private static final String RootElementTag = "troubleTicketStatusChangeEvent";

    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(TroubleTicketStatusChangeEvent.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }


    public TroubleTicketStatusChangeEventImpl() {
        // setEventType("TroubleTicketCreationEvent");
        setEventType("TroubleTicketStatusChangeEvent");  //WIPRO
        setEventTime(new java.util.Date());
    }


    public TroubleTicketKey getTroubleTicketKey() {
        return Key;
    }

    public int getStatus() {
        return Status;
    }

    public java.util.Date getStatusTime() {
        return StatusTime;
    }

    public int getState() {
        return State;
    }

    public void setTroubleTicketKey(TroubleTicketKey key) {
        Key = key;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setStatusTime(java.util.Date date) {
        StatusTime = date;
    }

    public void setState(int state) {
        State = state;
    }


    //---------------------------------------------------------------
    // following XML methods from javax.oss.XMLSerializable interface
    //---------------------------------------------------------------
    public String toXml()
            throws org.xml.sax.SAXException {
        StringBuffer strBuffer = new StringBuffer();
        TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();

        TroubleTicketKey trKey = this.getTroubleTicketKey();
        String xmlKey = ((TroubleTicketKeyImpl) trKey).toXml();
        strBuffer.append(xmlKey);
        //---------------------------------
        String stateName = null;
        if (this.getState() != -1) {
            try {
                stateName = (String) (ttIntReflector.getInterfaceFieldName(TroubleState.class, new Integer(this.getState())));
            } catch (javax.oss.IllegalArgumentException iLLEx) {
                throw new org.xml.sax.SAXException(iLLEx.getMessage());
            } catch (java.lang.IllegalAccessException iLLAccEx) {
                throw new org.xml.sax.SAXException(iLLAccEx.getMessage());
            }
        }

        strBuffer.append("<tt:troubleState>");
        strBuffer.append(stateName);
        strBuffer.append("</tt:troubleState>\n");

        //----------------------------------

        String statusName = null;
        if (this.getStatus() != -1) {
            try {
                statusName = (String) (ttIntReflector.getInterfaceFieldName(TroubleStatus.class, new Integer(this.getStatus())));
            } catch (javax.oss.IllegalArgumentException iLLEx) {
                throw new org.xml.sax.SAXException(iLLEx.getMessage());
            } catch (java.lang.IllegalAccessException iLLAccEx) {
                throw new org.xml.sax.SAXException(iLLAccEx.getMessage());
            }
        }

        strBuffer.append("<tt:troubleStatus>");
        strBuffer.append(statusName);
        strBuffer.append("</tt:troubleStatus>\n");

        //----------------------------------

        java.util.Date statusDate = this.getStatusTime();
        strBuffer.append("<tt:statusTime>");
        if (this.getStatusTime() == null) {
            //strBuffer.append( (String)null );
            strBuffer.append("0000-00-00T00:00:00Z");

        } else {
            strBuffer.append(this.getXmlDate(this.getStatusTime()));
        }

        strBuffer.append("</tt:statusTime>\n");


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


    public void fromXml(org.w3c.dom.Element element)
            throws org.xml.sax.SAXException {

    }

    public String toXml(String elementName) {
        String retString = new String();

        return retString;
    }

    public String getXmlHeader() {
        String retString = new String();

        return retString;
    }

    public String getRootName() {
        String retString = new String();

        return retString;
    }

    public String getRootAttributes() {
        return null;
    }

    public boolean equals(Object arg) {

        if ((arg != null) && (arg instanceof TroubleTicketStatusChangeEvent)) {

            TroubleTicketStatusChangeEvent event = (TroubleTicketStatusChangeEvent) arg;

            if ((getTroubleTicketKey() == null) && (event.getTroubleTicketKey() != null)) return false;
            if ((getTroubleTicketKey() != null) && (event.getTroubleTicketKey() == null)) return false;
            if ((getTroubleTicketKey() != null) && (event.getTroubleTicketKey() != null)) {
                if (!(getTroubleTicketKey().equals(event.getTroubleTicketKey()))) return false;
            }


            if (getStatus() != event.getStatus()) return false;


            if (getState() != event.getState()) return false;


            if ((getStatusTime() == null) && (event.getStatusTime() != null)) return false;
            if ((getStatusTime() != null) && (event.getStatusTime() == null)) return false;
            if ((getStatusTime() != null) && (event.getStatusTime() == null)) {
                if (!(getStatusTime().toString().equals(event.getStatusTime().toString()))) return false;
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


