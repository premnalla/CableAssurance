package ossj.ttri;

import org.w3c.dom.*;
import ossj.fmri.AlarmValueImpl;

import javax.oss.ApplicationContext;
import javax.oss.MultiValueList;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.trouble.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

/**
 * Trouble Ticket XML Translator Helper Class
 *
 * Translates TroubleTicketValue object to and from Java/XML
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class XmlTranslator {

	public static final String NS_URI_XSI = "http://www.w3.org/2001/XMLSchema-instance";


    //Java singleton design pattern - private
    static private XmlTranslator singleton = new XmlTranslator();


    private static HashMap schemaToAttMap;
    private static HashMap monthHash;
    private static TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();


    //private HashMap desiredAttributes;   //leave in ttv for now
    //transient private TroubleTicketInterfaceReflector ttIntReflector;


    //protected ctor - singleton pattern
    protected XmlTranslator() {
        //ttIntReflector = new TroubleTicketInterfaceReflector();
        //desiredAttributes = null;
        //desiredAttributes = new HashMap();

        //----------------------------------------------------------
        // Load up the XML schema to Java attribute map
        //----------------------------------------------------------
        schemaToAttMap = new HashMap(50);   //initial capacity 50
        schemaToAttMap.put("tt:troubleTicketKey", TroubleTicketValue.TROUBLETICKETKEY);
        schemaToAttMap.put("tt:activityDurationList", TroubleTicketValue.ACTIVITYDURATIONLIST);
        schemaToAttMap.put("tt:relatedAlarmList", TroubleTicketValue.RELATEDALARMLIST);
        schemaToAttMap.put("tt:additionalTroubleInfoList", TroubleTicketValue.ADDITIONALTROUBLEINFOLIST);
        schemaToAttMap.put("tt:closeOutNarr", TroubleTicketValue.CLOSEOUTNARR);
        schemaToAttMap.put("tt:receivedTime", TroubleTicketValue.RECEIVEDTIME);
        schemaToAttMap.put("tt:relatedTroubleTicketKeyList", TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST);
        schemaToAttMap.put("tt:repairActivityList", TroubleTicketValue.REPAIRACTIVITYLIST);
        schemaToAttMap.put("tt:restoredTime", TroubleTicketValue.RESTOREDTIME);
        schemaToAttMap.put("tt:sPRoleAssignmentList", TroubleTicketValue.SPROLEASSIGNMENTLIST);
        schemaToAttMap.put("tt:troubleDescription", TroubleTicketValue.TROUBLEDESCRIPTION);
        schemaToAttMap.put("tt:clearancePerson", TroubleTicketValue.CLEARANCEPERSON);
        schemaToAttMap.put("tt:troubleFound", TroubleTicketValue.TROUBLEFOUND);
        schemaToAttMap.put("tt:troubleLocation", TroubleTicketValue.TROUBLELOCATION);
        schemaToAttMap.put("tt:troubleNumList", TroubleTicketValue.TROUBLENUMLIST);
        schemaToAttMap.put("tt:troubledObject", TroubleTicketValue.TROUBLEDOBJECT);
        schemaToAttMap.put("tt:troubleType", TroubleTicketValue.TROUBLETYPE);
        schemaToAttMap.put("tt:troubleState", TroubleTicketValue.TROUBLESTATE);
        schemaToAttMap.put("tt:troubleStatus", TroubleTicketValue.TROUBLESTATUS);
        schemaToAttMap.put("tt:troubleStatusTime", TroubleTicketValue.TROUBLESTATUSTIME);
        schemaToAttMap.put("tt:afterHoursRepairAuthority", TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY);
        schemaToAttMap.put("tt:authorizationList", TroubleTicketValue.AUTHORIZATIONLIST);
        schemaToAttMap.put("tt:cancelRequestedByCustomer", TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER);
        schemaToAttMap.put("tt:closeOutVerification", TroubleTicketValue.CLOSEOUTVERIFICATION);
        schemaToAttMap.put("tt:commitmentTime", TroubleTicketValue.COMMITMENTTIME);
        schemaToAttMap.put("tt:commitmentTimeRequested", TroubleTicketValue.COMMITMENTTIMEREQUESTED);
        schemaToAttMap.put("tt:customerRoleAssignmentList", TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST);
        schemaToAttMap.put("tt:customerTroubleNum", TroubleTicketValue.CUSTOMERTROUBLENUM);
        schemaToAttMap.put("tt:dialog", TroubleTicketValue.DIALOG);
        schemaToAttMap.put("tt:escalationList", TroubleTicketValue.ESCALATIONLIST);
        schemaToAttMap.put("tt:initiatingMode", TroubleTicketValue.INITIATINGMODE);
        schemaToAttMap.put("tt:lastUpdateTime", TroubleTicketValue.LASTUPDATETIME);
        schemaToAttMap.put("tt:maintServiceCharge", TroubleTicketValue.MAINTSERVICECHARGE);
        schemaToAttMap.put("tt:outageDuration", TroubleTicketValue.OUTAGEDURATION);
        schemaToAttMap.put("tt:perceivedTroubleSeverity", TroubleTicketValue.PERCEIVEDTROUBLESEVERITY);
        schemaToAttMap.put("tt:preferredPriority", TroubleTicketValue.PREFERREDPRIORITY);
        schemaToAttMap.put("tt:repeatReport", TroubleTicketValue.REPEATREPORT);
        schemaToAttMap.put("tt:suspectObjectList", TroubleTicketValue.SUSPECTOBJECTLIST);
        schemaToAttMap.put("tt:troubleDetectionTime", TroubleTicketValue.TROUBLEDETECTIONTIME);
        schemaToAttMap.put("tt:troubleLocationInfoList", TroubleTicketValue.TROUBLELOCATIONINFOLIST);
        schemaToAttMap.put("tt:troubledObjectAccessFromTime", TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME);
        schemaToAttMap.put("tt:troubledObjectAccessHoursList", TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST);
        schemaToAttMap.put("tt:troubledObjectAccessToTime", TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME);
        schemaToAttMap.put("tt:serviceUnavailableBeginTime", TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME);
        schemaToAttMap.put("tt:serviceUnavailableEndTime", TroubleTicketValue.ORIGINATOR);
        schemaToAttMap.put("tt:troubleSystemDN", TroubleTicketValue.TROUBLESYSTEMDN);
        schemaToAttMap.put("tt:customer", TroubleTicketValue.CUSTOMER);
        schemaToAttMap.put("tt:accountOwner", TroubleTicketValue.ACCOUNTOWNER);

        //----------------------------------------------------------
        // Load up the month name map
        //----------------------------------------------------------
        monthHash = new HashMap(12);

        monthHash.put("Jan", "01");
        monthHash.put("Feb", "02");
        monthHash.put("Mar", "03");
        monthHash.put("Apr", "04");
        monthHash.put("May", "05");
        monthHash.put("Jun", "06");
        monthHash.put("Jul", "07");
        monthHash.put("Aug", "08");
        monthHash.put("Sep", "09");
        monthHash.put("Oct", "10");
        monthHash.put("Nov", "11");
        monthHash.put("Dec", "12");


    }

    public static void log(String log) {
        Logger.log(log);

    }

//base types: pointer to base types - use a substitution group
    public static org.w3c.dom.Element getElement(Element doc, String tagName) {

        log("tag name:" + tagName);
        log("Element name: " + doc.getTagName());


        log("getElement 1");
        Node node = null;
        NodeList nodeList = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tagName);

        log("getElement 2");
        if (nodeList != null) {

            log("nodeList != null");
            log("nodeList.getLength()" + nodeList.getLength());
            node = nodeList.item(0);
        } else
            log("element is null");

        log("getElement 3");
        if (node == null) log("node is null");
        Element element = (Element) node;

        log("getElement 4");
        return element;

    }

    public static org.w3c.dom.Element getElement(Document doc, String tagName) {

        log("tag name:" + tagName);


        log("getElement 1");
        Node node = null;
        NodeList nodeList = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tagName);

        log("getElement 2");
        if (nodeList != null) {

            log("nodeList != null");
            log("nodeList.getLength()" + nodeList.getLength());
            node = nodeList.item(0);
        } else
            log("element is null");

        log("getElement 3");
        if (node == null) log("node is null");
        Element element = (Element) node;

        log("getElement 4");
        return element;

    }


    /**
     * Serialize this Java object as an XML document instance with an XML
     * doctype declaration
     *
     * @return String XML document instance
     */
    public static String toXml(TroubleTicketValue ttv,
                               HashMap desiredAttributes)
            throws org.xml.sax.SAXException {
        String xmlTTValNoRoot = XmlTranslator.toXmlNoRoot(ttv, desiredAttributes);

        // Now add the ttVal root element
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<tt:troubleTicketValue>\n");
        strBuffer.append(xmlTTValNoRoot);
        strBuffer.append("</tt:troubleTicketValue>\n");

        return (strBuffer.toString());
    }

    /**
     * Serialize this Java object as an XML document instance with an XML
     * doctype declaration.
     *
     * @return String XML document instance
     */

    public static TroubleTicketKey SerializeKey(Element doc, String tag) {
        TroubleTicketKey trKey = new TroubleTicketKeyImpl();
        TroubleTicketKey retKey = null;
        Serializer serializer = trKey.makeSerializer(XmlSerializer.class.getName());
        XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
        Element element = getElement(doc, tag);
        retKey = (TroubleTicketKey) ttXmlSerializer.fromXml(element);
        return retKey;
    }

    public static TroubleTicketValue SerializeValue(Element doc, String tag) {
        log("----Serialize Value from Element---");
        TroubleTicketValue trVal = new TroubleTicketValueImpl();
        TroubleTicketValue retVal = null;
        Serializer serializer = trVal.makeSerializer(XmlSerializer.class.getName());
        XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
        Element element = getElement(doc, tag);
        if (element != null) {
            log("----Element != NULL---");
            retVal = (TroubleTicketValue) ttXmlSerializer.fromXml(element);
            log("---Number of Pop Attributes---> " + retVal.getPopulatedAttributeNames().length);
        } else
            log("----Element is NULL---");
        return retVal;
    }

    public static TroubleTicketValue SerializeValue(Document doc, String tag) {
        log("----Serialize Value---");
        TroubleTicketValue trVal = new TroubleTicketValueImpl();
        TroubleTicketValue retVal = null;
        Serializer serializer = trVal.makeSerializer(XmlSerializer.class.getName());
        XmlSerializer ttXmlSerializer = (XmlSerializer) serializer;
        Element element = getElement(doc, tag);
        retVal = (TroubleTicketValue) ttXmlSerializer.fromXml(element);
        log("---Number of Pop Attributes---> " + retVal.getPopulatedAttributeNames().length);
        return retVal;
    }


    public static String toXml(TroubleTicketValue ttv,
                               String elementName)
            throws org.xml.sax.SAXException {
        return ((TroubleTicketValueImpl) ttv).toXml(elementName);
    }

    public static String toXml(TroubleTicketKey ttkey,
                               String elementName)
            throws org.xml.sax.SAXException {

        return ((TroubleTicketKeyImpl) ttkey).toXml(elementName);
    }

    public static String toXml(TroubleTicketCreateEvent event,
                               String elementName)
            throws org.xml.sax.SAXException {
        return troubleTicketCreateEventtoXml(event, elementName);
    }

    public static String toXml(TroubleTicketCloseOutEvent event,
                               String elementName)
            throws org.xml.sax.SAXException {
        return troubleTicketCloseOutEventtoXml(event, elementName);
    }

    public static String toXml(TroubleTicketCancellationEvent event,
                               String elementName)
            throws org.xml.sax.SAXException {
        return troubleTicketCancellationEventtoXml(event, elementName);
    }

    public static String toXml(TroubleTicketAttributeValueChangeEvent event,
                               String elementName)
            throws org.xml.sax.SAXException {
        return troubleTicketAttributeValueChangeEventtoXml(event, elementName);
    }

    public static String toXml(TroubleTicketStatusChangeEvent event,
                               String elementName)
            throws org.xml.sax.SAXException {
        return troubleTicketStatusChangeEventtoXml(event, elementName);
    }

    public static String toXml(QueryAllOpenTroubleTicketsValue query,
                               String elementName)
            throws org.xml.sax.SAXException {
        return ((QueryAllOpenTroubleTicketsImpl) query).toXml(elementName);
    }

    public static String toXml(QueryByEscalationValue query,
                               String elementName)
            throws org.xml.sax.SAXException {
        //return ((QueryByEscalationValueImpl) query).toXml(elementName) ;
        return null;
    }


    public static String toXmlNoRoot(TroubleTicketValue ttv,
                                     HashMap desiredAttributes)
            throws org.xml.sax.SAXException {
        Logger.log("XmlTranslator::toXmlNoRoot");

        // Create the transient interface reflector object
        TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();

        StringBuffer strBuffer = new StringBuffer();
        LastUpdateVersionNumber2Xml(strBuffer, ttv);
        // Translate the TT key and its elements to XML format.
        // No checking is needed for the code
        //Logger.log ("**********After TroubleTicketKey2Xml *******");
        TroubleTicketKey2Xml(strBuffer, ttv.getTroubleTicketKey());

        //Logger.log ("**********After TroubleTicketKey2Xml *******");

        //--------------------------------------------------------------------------
        // Translate the other attributes. Using the ttValue field
        // desiredAttributes, the map is queried and the desired attributes that
        // exist are converted to XML according to their schema rules and orders.
        //
        // NB.  Order is important here!  The order in the XML Schema must be adhered
        // to, otherwise validation will fail!  This is why we go thru in this specific
        // order rather than just looping thru the contents of the collection!
        //
        //--------------------------------------------------------------------------
        if (desiredAttributes != null) {

            Logger.log("**********desiredAttributes != null*******");

            Object[] objects = desiredAttributes.keySet().toArray();
            Logger.log("Desired Attribute Length=" + objects.length);
            for (int i = 0; i < objects.length; i++) {
                Logger.log("Desired Attribute =" + (String) objects[i]);
            }


            //if (desiredAttributes.containsKey("lastUpdateVersionNumber"))                           XmlTranslator.LastUpdateVersionNumber2Xml(strBuffer,ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.ACCOUNTOWNER)) XmlTranslator.AccountOwner2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.ACTIVITYDURATIONLIST)) XmlTranslator.ActivityDurationList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST)) XmlTranslator.AdditionalTroubleInfoList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) XmlTranslator.AfterHoursRepairAuthority2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.AUTHORIZATIONLIST)) XmlTranslator.AuthorizationList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) XmlTranslator.CancelRequestedByCustomer2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CLEARANCEPERSON)) XmlTranslator.ClearancePerson2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CLOSEOUTNARR)) XmlTranslator.CloseOutNarr2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CLOSEOUTVERIFICATION)) XmlTranslator.CloseOutVerification2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.COMMITMENTTIME)) XmlTranslator.CommitmentTime2Xml(strBuffer, ttv);

            if (desiredAttributes.containsKey(TroubleTicketValue.COMMITMENTTIMEREQUESTED)) XmlTranslator.CommitmentTimeRequested2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CUSTOMER)) XmlTranslator.Customer2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST)) XmlTranslator.CustomerRoleAssignmentList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.CUSTOMERTROUBLENUM)) XmlTranslator.CustomerTroubleNum2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.DIALOG)) XmlTranslator.Dialog2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.ESCALATIONLIST)) XmlTranslator.EscalationList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.INITIATINGMODE)) XmlTranslator.InitiatingMode2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.LASTUPDATETIME)) XmlTranslator.LastUpdateTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.MAINTSERVICECHARGE)) XmlTranslator.MaintServiceCharge2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.ORIGINATOR)) XmlTranslator.Originator2Xml(strBuffer, ttv);

            if (desiredAttributes.containsKey(TroubleTicketValue.OUTAGEDURATION)) XmlTranslator.OutageDuration2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.RECEIVEDTIME)) XmlTranslator.ReceivedTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.RELATEDALARMLIST)) XmlTranslator.RelatedAlarmList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST)) XmlTranslator.RelatedTroubleTicketKeyList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.REPAIRACTIVITYLIST)) XmlTranslator.RepairActivityList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.REPEATREPORT)) XmlTranslator.RepeatReport2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.RESTOREDTIME)) XmlTranslator.RestoredTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) XmlTranslator.ServiceUnavailableBeginTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) XmlTranslator.ServiceUnavailableEndTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.SPROLEASSIGNMENTLIST)) XmlTranslator.SPRoleAssignmentList2Xml(strBuffer, ttv);

            if (desiredAttributes.containsKey(TroubleTicketValue.SUSPECTOBJECTLIST)) XmlTranslator.SuspectObjectList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDESCRIPTION)) XmlTranslator.TroubleDescription2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEFOUND)) XmlTranslator.TroubleFound2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLELOCATION)) XmlTranslator.TroubleLocation2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLENUMLIST)) XmlTranslator.TroubleNumList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDOBJECT)) XmlTranslator.TroubledObject2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLETYPE)) XmlTranslator.TroubleType2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLESTATE)) XmlTranslator.TroubleState2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLESTATUS)) XmlTranslator.TroubleStatus2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLESTATUSTIME)) XmlTranslator.TroubleStatusTime2Xml(strBuffer, ttv);

            if (desiredAttributes.containsKey(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) XmlTranslator.PerceivedTroubleSeverity2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.PREFERREDPRIORITY)) XmlTranslator.PreferredPriority2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDETECTIONTIME)) XmlTranslator.TroubleDetectionTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) XmlTranslator.TroubleLocationInfoList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) XmlTranslator.TroubledObjectAccessFromTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) XmlTranslator.TroubledObjectAccessHoursList2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) XmlTranslator.TroubledObjectAccessToTime2Xml(strBuffer, ttv);
            if (desiredAttributes.containsKey(TroubleTicketValue.TROUBLESYSTEMDN)) XmlTranslator.TroubleSystemDN2Xml(strBuffer, ttv);


            //Logger.log ("**********desiredAttributes END *******");

        } else
            Logger.log("**********desiredAttributes == null*******");

//Logger.log ("XmlTranslator::toXmlNoRoot END....");
        return strBuffer.toString();

    }

    //TODO - XML translation code also appears in the TTKeyImpl
    public static void TroubleTicketKey2Xml(StringBuffer sb, TroubleTicketKey ttKey)
            throws org.xml.sax.SAXException {
        Logger.log("&&&&&XmlTranslator::TroubleTicketKey2Xml&&&&");
        if (ttKey == null)
        //provide nil XML encoding for the top level only
            sb.append("<tt:troubleTicketKey xsi:nil=\"true\"></tt:troubleTicketKey>");
        else
            sb.append(((TroubleTicketKeyImpl) ttKey).toXml());


    }

    public static TroubleTicketCancellationEvent troubleTicketCancellationEventFromXml(Element doc) {
        TroubleTicketCancellationEvent event = new TroubleTicketCancellationEventImpl();


        String closedOutnarr = null;

        Node node = null;
        NodeList nodeList = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "closeOutNarr");
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                log("No Status");
            } else {
                closedOutnarr = node.getFirstChild().getNodeValue();
            }

        }


        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKey");
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                log("No Trouble Ticket Key");
            }

        }


        TroubleTicketKey key = XmlTranslator.TroubleTicketKeyFromXml(node);

        //should check statusTime too...

        event.setTroubleTicketKey(key);
        event.setCloseOutNarr(closedOutnarr);

        return event;
    }

    public static TroubleTicketAttributeValueChangeEvent troubleTicketAttributeValueChangeEventFromXml(Element doc) {
        TroubleTicketAttributeValueChangeEvent event = new TroubleTicketAttributeValueChangeEventImpl();
        TroubleTicketValue ttValue = null;
        try {

            ttValue = SerializeValue(doc, "troubleTicketValue");

        } catch (Exception e) {
            log("troubleTicketCreateEventFromXml" + e.getMessage());
        }
        event.setTroubleTicketValue(ttValue);
        return event;
    }

    public static TroubleTicketCreateEvent troubleTicketCreateEventFromXml(Element doc) {
        TroubleTicketCreateEvent event = new TroubleTicketCreateEventImpl();
        TroubleTicketValue ttValue = null;
        try {
            ttValue = SerializeValue(doc, "troubleTicketValue");
        } catch (Exception e) {
            log("troubleTicketCreateEventFromXml" + e.getMessage());
        }
        event.setTroubleTicketValue(ttValue);
        return event;
    }

    public static TroubleTicketValue getTroubleTicketValue(Document doc) {
        TroubleTicketValue ttValue = null;
        try {

            ttValue = SerializeValue(doc, "troubleTicketValue");
        } catch (Exception ex) {
        }
        return ttValue;
    }

    public static TroubleTicketCloseOutEvent troubleTicketCloseOutEventFromXml(Element doc) {
        TroubleTicketCloseOutEvent event = new TroubleTicketCloseOutEventImpl();
        TroubleTicketValue ttValue = null;
        try {
            ttValue = SerializeValue(doc, "troubleTicketValue");
        } catch (Exception e) {
            log("troubleTicketCreateEventFromXml" + e.getMessage());
        }
        event.setTroubleTicketValue(ttValue);
        return event;
    }

    public static TroubleTicketStatusChangeEvent troubleTicketStatusChangeEventFromXml(Element doc) {
        TroubleTicketStatusChangeEvent event = new TroubleTicketStatusChangeEventImpl();


        int status = -1;
        int state = -1;

        Node node = null;
        NodeList nodeList = null;
        //Wipro
        /*
            first it searches for status tag, if it is not found then it will search for baseStatus tag (if any)
        */
         nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleStatus");
        if(nodeList==null){
            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "baseStatus");
        }
        //Wipro
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                log("No Status");
            }

        }


        status = XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class);
        //Wipro
         /*
            first it searches for troubleState tag, if it is not found then it will search for baseState tag (if any)
        */
        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket","troubleState");
        if(nodeList==null){
            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "baseState");
        }
        //Wipro
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                log("No State");
            }

        }


        state = XmlTranslator.EnumeratedTypeFromXml(node, TroubleState.class);


        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKey");
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                log("No Trouble Ticket Key");
            }

        }


        TroubleTicketKey key = XmlTranslator.TroubleTicketKeyFromXml(node);

        //should check statusTime too...

        event.setTroubleTicketKey(key);
        event.setStatus(status);
        java.util.Date date = new java.util.Date();
        event.setStatusTime(date);
        event.setState(state);

        return event;


    }


    public static void LastUpdateVersionNumber2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        long attr = ttv.getLastUpdateVersionNumber();
        sb.append("<co:lastUpdateVersionNumber>" + String.valueOf(attr) + "</co:lastUpdateVersionNumber>\n");

    }

    public static void AccountOwner2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        PersonReach attr = ttv.getAccountOwner();
        if (attr == null)
            sb.append("<tt:accountOwner xsi:nil=\"true\"></tt:accountOwner>\n");
        else {
            sb.append("<tt:accountOwner>\n");
            XmlTranslator.PersonReach2Xml(sb, attr);
            sb.append("</tt:accountOwner>\n");
        }
    }

    public static void ActivityDurationList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        ActivityDurationList attr = ttv.getActivityDurationList();
        if (attr == null) {
            sb.append("<tt:activityDurationList xsi:nil=\"true\"></tt:activityDurationList>\n");

            return;
        } else {
            ActivityDuration[] adArray = null;
            try {
                adArray = attr.get();
            } catch (java.lang.IllegalStateException ex) {
                //was never set, so set XML to null.

                sb.append("<tt:activityDurationList xsi:nil=\"true\"></tt:activityDurationList>\n");
                return;
            }
            sb.append("<tt:activityDurationList>\n");
            //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
            sb.append("<co:modifier>SET</co:modifier>\n");
            sb.append("<tt:activityDurations>\n");
            for (int x = 0; x < adArray.length; x++) {
                if (adArray[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");
                    XmlTranslator.ActivityDuration2Xml(sb, adArray[x]);
                    sb.append("</tt:item>\n");
                }
            }
            sb.append("</tt:activityDurations>\n");
            sb.append("</tt:activityDurationList>\n");
        }
    }

    public static void AdditionalTroubleInfoList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        String[] attr = ttv.getAdditionalTroubleInfoList();
        if (attr == null)
            sb.append("\n<tt:AdditionalTroubleInfoList xsi:nil=\"true\"></tt:AdditionalTroubleInfoList>\n");
        else {
            sb.append("<tt:additionalTroubleInfoList>\n");
            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null)
                    sb.append("<co:item xsi:nil=\"true\"></co:item>\n");
                else
                    sb.append("<co:item>" + attr[x] + "</co:item>\n");
            }
            sb.append("</tt:additionalTroubleInfoList>\n");
        }

    }

    public static void AfterHoursRepairAuthority2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        sb.append("<tt:afterHoursRepairAuthority>");
        XmlTranslator.Boolean2Xml(sb, ttv.getAfterHoursRepairAuthority());
        sb.append("</tt:afterHoursRepairAuthority>\n");
    }

    public static void AuthorizationList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        Authorization[] attr = ttv.getAuthorizationList();

        if (attr == null)
            sb.append("<tt:authorizationList xsi:nil=\"true\"></tt:authorizationList>\n");
        else {
            sb.append("<tt:authorizationList>\n");
            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");
                    XmlTranslator.Authorization2Xml(sb, attr[x]);
                    sb.append("</tt:item>\n");
                }
            }
            sb.append("</tt:authorizationList>\n");

        }
    }

    public static void CancelRequestedByCustomer2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        sb.append("<tt:cancelRequestedByCustomer>");
        XmlTranslator.Boolean2Xml(sb, ttv.getCancelRequestedByCustomer());
        sb.append("</tt:cancelRequestedByCustomer>\n");
    }

    public static void ClearancePerson2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        PersonReach attr = ttv.getClearancePerson();
        if (attr == null)
            sb.append("<tt:clearancePerson xsi:nil=\"true\"></tt:clearancePerson>\n");
        else {
            sb.append("<tt:clearancePerson>\n");
            XmlTranslator.PersonReach2Xml(sb, attr);
            sb.append("</tt:clearancePerson>\n");
        }
    }

    public static void CloseOutNarr2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        String attr = ttv.getCloseOutNarr();
        if (attr == null)
            sb.append("<tt:closeOutNarr xsi:nil=\"true\"></tt:closeOutNarr>\n");
        else
            sb.append("<tt:closeOutNarr>" + attr + "</tt:closeOutNarr>\n");
    }

    public static void CloseOutVerification2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        XmlTranslator.EnumeratedType2Xml(sb,
                                         CloseOutVerification.class,
                                         ttv.getCloseOutVerification());

    }


    public static void CommitmentTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        java.util.Date attr = ttv.getCommitmentTime();
        if (attr == null)
            sb.append("<tt:commitmentTime xsi:nil=\"true\"></tt:commitmentTime>\n");
        else {
            sb.append("<tt:commitmentTime>");
            log("commitmenttime2xml");
            XmlTranslator.Date2Xml(sb, attr);
            sb.append("</tt:commitmentTime>\n");
        }
    }

    public static void CommitmentTimeRequested2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getCommitmentTimeRequested();
        if (attr == null)
            sb.append("<tt:commitmentTimeRequested xsi:nil=\"true\"></tt:commitmentTimeRequested>/n");
        else {
            sb.append("<tt:commitmentTimeRequested>");
            XmlTranslator.Date2Xml(sb, attr);
            sb.append("</tt:commitmentTimeRequested>\n");
        }
    }

    public static void Customer2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        PersonReach attr = ttv.getCustomer();
        if (attr == null)
            sb.append("<tt:customer xsi:nil=\"true\"></tt:customer>\n");
        else {
            sb.append("<tt:customer>\n");
            XmlTranslator.PersonReach2Xml(sb, attr);
            sb.append("</tt:customer>\n");
        }
    }

    public static void CustomerRoleAssignmentList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        CustomerRoleAssignment[] attr = ttv.getCustomerRoleAssignmentList();

        if (attr == null)
            sb.append("<tt:customerRoleAssignmentList xsi:nil=\"true\"></tt:customerRoleAssignmentList>\n");
        else {
            sb.append("<tt:customerRoleAssignmentList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");

                    String role = attr[x].getRole();

                    if( role == null )
                        sb.append( "<tt:role xsi:nil=\"true\"></tt:role>\n");
                    else
                        sb.append( "<tt:role>" + role + "</tt:role>\n" );

                    PersonReach cp = attr[x].getContactPerson();
                    if (cp == null)
                        sb.append("<tt:contactPerson xsi:nil=\"true\"></tt:contactPerson>\n");
                    else {
                        sb.append("<tt:contactPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, attr[x].getContactPerson());
                        sb.append("</tt:contactPerson>\n");
                    }
                    sb.append("</tt:item>\n");
                }
            }

            sb.append("</tt:customerRoleAssignmentList>\n");
        }

    }

    public static void CustomerTroubleNum2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        String attr = ttv.getCustomerTroubleNum();
        if (attr == null)
            sb.append("<tt:CustomerTroubleNum xsi:nil=\"true\"></tt:CustomerTroubleNum>\n");
        else
            sb.append("<tt:customerTroubleNum>" + attr + "</tt:customerTroubleNum>\n");

    }

    public static void Dialog2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        String attr = ttv.getDialog();
        if (attr == null)
            sb.append("<tt:dialog xsi:nil=\"true\"></tt:dialog>\n");
        else
            sb.append("<tt:dialog>" + attr + "</tt:dialog>\n");

    }

    public static String escalationListToXMLItems(EscalationList list) {

        Escalation[] escArray = list.get();
        StringBuffer sb = new StringBuffer();

        sb.append("<co:modifier>SET</co:modifier>\n");
        sb.append("<tt:escalations>\n");

        for (int x = 0; x < escArray.length; x++) {
            if (escArray[x] == null) {
                sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
            } else {
                sb.append("<tt:item>\n");       //start array item

                PersonReach escaPerson = escArray[x].getEscalationPerson();
                if (escaPerson == null)
                    sb.append("<tt:escPerson xsi:nil=\"true\"></tt:escPerson>\n");
                else {
                    sb.append("<tt:escPerson>\n");
                    XmlTranslator.PersonReach2Xml(sb, escaPerson);
                    sb.append("</tt:escPerson>\n");
                }


                java.util.Date escaTime = escArray[x].getEscalationTime();
                if (escaTime == null)
                    sb.append("<tt:escTime xsi:nil=\"true\"></tt:escTime>\n");
                else {
                    sb.append("<tt:escTime>");
                    XmlTranslator.Date2Xml(sb, escaTime);
                    sb.append("</tt:escTime>\n");
                }

                XmlTranslator.EnumeratedType2Xml(sb, OrgLevel.class, escArray[x].getLevel());


                PersonReach reqPerson = escArray[x].getRequestPerson();
                if (reqPerson == null)
                    sb.append("<tt:requestPerson xsi:nil=\"true\"></tt:requestPerson>\n");
                else {
                    sb.append("<tt:requestPerson>\n");
                    XmlTranslator.PersonReach2Xml(sb, reqPerson);
                    sb.append("</tt:requestPerson>\n");
                }

                XmlTranslator.EnumeratedType2Xml(sb, RequestState.class, escArray[x].getRequestState());


                sb.append("</tt:item>\n");      //end array item
            }
        }
        sb.append("</tt:escalations>\n");

        return sb.toString();
    }


    public static void EscalationList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        EscalationList attr = ttv.getEscalationList();
        if (attr == null) {
            sb.append("<tt:escalationList xsi:nil=\"true\"></tt:escalationList>\n");
            return;
        } else {

            Escalation[] escArray = null;
            try {
                escArray = attr.get();
            } catch (java.lang.IllegalStateException ex) {
                //was never set, so set XML to null.
                sb.append("<tt:escalationList xsi:nil=\"true\"></tt:escalationList>\n");
                return;
            }

            sb.append("<tt:escalationList>\n");
            //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
            sb.append("<co:modifier>SET</co:modifier>\n");
            sb.append("<tt:escalations>\n");

            for (int x = 0; x < escArray.length; x++) {
                if (escArray[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");       //start array item

                    PersonReach escaPerson = escArray[x].getEscalationPerson();
                    if (escaPerson == null)
                        sb.append("<tt:escPerson xsi:nil=\"true\"></tt:escPerson>\n");
                    else {
                        sb.append("<tt:escPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, escaPerson);
                        sb.append("</tt:escPerson>\n");
                    }


                    java.util.Date escaTime = escArray[x].getEscalationTime();
                    if (escaTime == null)
                        sb.append("<tt:escTime xsi:nil=\"true\"></tt:escTime>\n");
                    else {
                        sb.append("<tt:escTime>");
                        XmlTranslator.Date2Xml(sb, escaTime);
                        sb.append("</tt:escTime>\n");
                    }

                    XmlTranslator.EnumeratedType2Xml(sb, OrgLevel.class, escArray[x].getLevel());


                    PersonReach reqPerson = escArray[x].getRequestPerson();
                    if (reqPerson == null)
                        sb.append("<tt:requestPerson xsi:nil=\"true\"></tt:requestPerson>\n");
                    else {
                        sb.append("<tt:requestPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, reqPerson);
                        sb.append("</tt:requestPerson>\n");
                    }

                    XmlTranslator.EnumeratedType2Xml(sb, RequestState.class, escArray[x].getRequestState());


                    sb.append("</tt:item>\n");      //end array item
                }
            }
            sb.append("</tt:escalations>\n");
            sb.append("</tt:escalationList>\n");
        }


    }

    public static void InitiatingMode2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        XmlTranslator.EnumeratedType2Xml(sb,
                                         InitiatingMode.class,
                                         ttv.getInitiatingMode());

    }

    public static void LastUpdateTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        java.util.Date attr = ttv.getLastUpdateTime();
        if (attr == null)
            sb.append("<tt:lastUpdateTime xsi:nil=\"true\"></tt:lastUpdateTime>\n");
        else {
            sb.append("<tt:lastUpdateTime>");
            XmlTranslator.Date2Xml(sb, ttv.getLastUpdateTime());
            sb.append("</tt:lastUpdateTime>\n");
        }
    }

    public static void MaintServiceCharge2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        sb.append("<tt:maintServiceCharge>");
        XmlTranslator.Boolean2Xml(sb, ttv.getMaintServiceCharge());
        sb.append("</tt:maintServiceCharge>\n");
    }

    public static void Originator2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        String attr = ttv.getOriginator();
        if (attr == null)
            sb.append("<tt:originator xsi:nil=\"true\"></tt:originator>\n");
        else
            sb.append("<tt:originator>" + attr + "</tt:originator>\n");

    }

    public static void OutageDuration2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        TimeLength attr = ttv.getOutageDuration();
        if (attr == null)
            sb.append("<tt:outageDuration xsi:nil=\"true\"></tt:outageDuration>\n");
        else {
            sb.append("<tt:outageDuration>\n");
            XmlTranslator.TimeLength2Xml(sb, ttv.getOutageDuration());
            sb.append("</tt:outageDuration>\n");
        }
    }

    public static void ReceivedTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        java.util.Date attr = ttv.getReceivedTime();
        if (attr == null)
            sb.append("<tt:receivedTime xsi:nil=\"true\"></tt:receivedTime>\n");
        else {
            sb.append("<tt:receivedTime>");
            XmlTranslator.Date2Xml(sb, ttv.getReceivedTime());
            sb.append("</tt:receivedTime>\n");
        }
    }


    public static void RelatedAlarmList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        AlarmValueList attr = ttv.getRelatedAlarmList();
        if (attr == null) {
            sb.append("<tt:relatedAlarmList xsi:nil=\"true\"></tt:relatedAlarmList>\n");
            return;
        } else {

            AlarmValue[] alarmArray = null;
            try {
                alarmArray = attr.get();
            } catch (java.lang.IllegalStateException ex) {
                sb.append("<tt:relatedAlarmList xsi:nil=\"true\"></tt:relatedAlarmList>\n");
                return;
            }

            sb.append("<tt:relatedAlarmList>\n");
            sb.append("<co:modifier>SET</co:modifier>\n");
            sb.append("<tt:relatedAlarms>\n");

            AlarmValue aval = new AlarmValueImpl();

            XmlSerializer xmls = (XmlSerializer) aval.makeSerializer(XmlSerializer.class.getName());

            for (int x = 0; x < alarmArray.length; x++) {
                sb.append(xmls.toXml(alarmArray[x], "fm:Item"));
            }
            sb.append("</tt:relatedAlarms>\n");
            sb.append("</tt:relatedAlarmList>\n");
        }


    }

    public static void RelatedTroubleTicketKeyList2Xml(StringBuffer sb, TroubleTicketValue ttv)
            throws org.xml.sax.SAXException {

        TroubleTicketKey[] attr = ttv.getRelatedTroubleTicketKeyList();


        if (attr == null) {
            Logger.log("&&&&&&&t RelatedTroubleTicketKeyListFromXml is NULL&&&&&&");
            sb.append("<tt:relatedTroubleTicketKeyList xsi:nil=\"true\"></tt:relatedTroubleTicketKeyList>\n");
            return;
        } else {
            sb.append("<tt:relatedTroubleTicketKeyList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");
                    //XmlTranslator.TroubleTicketKey2Xml(sb,attr[x]);

                    sb.append(((TroubleTicketKeyImpl) attr[x]).toXmlBase());
                    sb.append("</tt:item>\n");
                }
            }

            sb.append("</tt:relatedTroubleTicketKeyList>\n");
        }
    }

    public static void RepairActivityList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        RepairActivityList attr = ttv.getRepairActivityList();
        if (attr == null) {
            sb.append("<tt:repairActivityList xsi:nil=\"true\"></tt:repairActivityList>\n");
            return;
        } else {

            RepairActivity[] raArray = null;
            try {
                raArray = attr.get();
            } catch (java.lang.IllegalStateException ex) {
                //was never set, so set XML to null.
                sb.append("<tt:repairActivityList xsi:nil=\"true\"></tt:repairActivityList>\n");
                return;
            }


            sb.append("<tt:repairActivityList>\n");
            //PG BUG sb.append("<co:modifier>NONE</co:modifier>\n");
            sb.append("<co:modifier>SET</co:modifier>\n");
            sb.append("<tt:repairActivities>\n");
            for (int x = 0; x < raArray.length; x++) {
                if (raArray[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");       //array item

                    XmlTranslator.EnumeratedType2Xml(sb, ActivityCode.class, raArray[x].getActivityCode());

                    String actInfo = raArray[x].getActivityInfo();
                    if (actInfo == null)
                        sb.append("<tt:activityInfo xsi:nil=\"true\"></tt:activityInfo>\n");
                    else
                        sb.append("<tt:activityInfo>" + actInfo + "</tt:activityInfo>\n");

                    PersonReach actPers = raArray[x].getActivityPerson();
                    if (actPers == null)
                        sb.append("<tt:activityPerson xsi:nil=\"true\"></tt:activityPerson>\n");
                    else {
                        sb.append("<tt:activityPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, actPers);
                        sb.append("</tt:activityPerson>\n");
                    }
                    java.util.Date entryTm = raArray[x].getEntryTime();
                    if (entryTm == null)
                        sb.append("<tt:entryTime xsi:nil=\"true\"></tt:entryTime>\n");
                    else {
                        sb.append("<tt:entryTime>");
                        XmlTranslator.Date2Xml(sb, entryTm);
                        sb.append("</tt:entryTime>\n");
                    }
                    sb.append("</tt:item>\n");      //end array item
                }
            }
            sb.append("</tt:repairActivities>\n");
            sb.append("</tt:repairActivityList>\n");
        }

    }

    public static void RepeatReport2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        XmlTranslator.EnumeratedType2Xml(sb,
                                         RepeatReport.class,
                                         ttv.getRepeatReport());

    }

    public static void RestoredTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getRestoredTime();
        if (attr == null)
            sb.append("<tt:restoredTime xsi:nil=\"true\"></tt:restoredTime>\n");
        else {
            sb.append("<tt:restoredTime>");
            XmlTranslator.Date2Xml(sb, ttv.getRestoredTime());
            sb.append("</tt:restoredTime>\n");
        }
    }

    public static void ServiceUnavailableBeginTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getServiceUnavailableBeginTime();
        if (attr == null)
            sb.append("<tt:serviceUnavailableBeginTime xsi:nil=\"true\"></tt:serviceUnavailableBeginTime>\n");
        else {
            sb.append("<tt:serviceUnavailableBeginTime>");
            XmlTranslator.Date2Xml(sb, ttv.getServiceUnavailableBeginTime());
            sb.append("</tt:serviceUnavailableBeginTime>\n");
        }
    }

    public static void ServiceUnavailableEndTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        java.util.Date attr = ttv.getServiceUnavailableEndTime();
        if (attr == null)
            sb.append("<tt:serviceUnavailableEndTime xsi:nil=\"true\"></tt:serviceUnavailableEndTime>\n");
        else {
            sb.append("<tt:serviceUnavailableEndTime>");
            XmlTranslator.Date2Xml(sb, ttv.getServiceUnavailableEndTime());
            sb.append("</tt:serviceUnavailableEndTime>\n");
        }
    }

    public static void SPRoleAssignmentList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        SPRoleAssignment[] attr = ttv.getSPRoleAssignmentList();
        if (attr == null)
            sb.append("<tt:sPRoleAssignmentList xsi:nil=\"true\"></tt:sPRoleAssignmentList>\n");
        else {
            sb.append("<tt:sPRoleAssignmentList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");

                    String role = attr[x].getRole();
                    if (role == null)
                        sb.append("<tt:role xsi:nil=\"true\"></tt:role>\n");
                    else
                        sb.append("<tt:role>" + role + "</tt:role>\n");

                    PersonReach cp = attr[x].getContactPerson();
                    if (cp == null)
                        sb.append("<tt:contactPerson xsi:nil=\"true\"></tt:contactPerson>\n");
                    else {
                        sb.append("<tt:contactPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, cp);
                        sb.append("</tt:contactPerson>\n");
                    }

                    sb.append("</tt:item>\n");
                }
            }

            sb.append("</tt:sPRoleAssignmentList>\n");
        }

    }

    public static void SuspectObjectList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        SuspectObject[] attr = ttv.getSuspectObjectList();
        if (attr == null)
            sb.append("<tt:suspectObjectList xsi:nil=\"true\"></tt:suspectObjectList>\n");
        else {
            sb.append("<tt:suspectObjectList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");

                    String sot = attr[x].getSuspectObjectType();
                    if (sot == null)
                        sb.append("<tt:suspectObjectType xsi:nil=\"true\"></tt:suspectObjectType>\n");
                    else
                        sb.append("<tt:suspectObjectType>" + sot + "</tt:suspectObjectType>\n");

                    String sid = attr[x].getSuspectObjectId();
                    if (sid == null)
                        sb.append("<tt:suspectObjectId xsi:nil=\"true\"></tt:suspectObjectId>\n");
                    else
                        sb.append("<tt:suspectObjectId>" + sid + "</tt:suspectObjectId>\n");

                    //FailureProbability - int (primitive) no null equivalent
                    sb.append("<tt:failureProbability>");
                    sb.append(String.valueOf(attr[x].getFailureProbability()));
                    sb.append("</tt:failureProbability>\n");

                    sb.append("</tt:item>\n");
                }
            }

            sb.append("</tt:suspectObjectList>\n");
        }

    }

    public static void TroubleDescription2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        String attr = ttv.getTroubleDescription();
        if (attr == null)
            sb.append("<tt:troubleDescription xsi:nil=\"true\"></tt:troubleDescription>\n");
        else
            sb.append("<tt:troubleDescription>" + attr + "</tt:troubleDescription>\n");
    }

    public static void TroubleFound2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        XmlTranslator.EnumeratedType2Xml(sb,
                                         TroubleFound.class,
                                         ttv.getTroubleFound());

    }

    public static void TroubleLocation2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        String attr = ttv.getTroubleLocation();
        if (attr == null)
            sb.append("<tt:troubleLocation xsi:nil=\"true\"></tt:troubleLocation>\n");
        else
            sb.append("<tt:troubleLocation>" + attr + "</tt:troubleLocation>\n");
    }

    public static void TroubleNumList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        String[] attr = ttv.getTroubleNumList();
        if (attr == null)
            sb.append("<tt:troubleNumList xsi:nil=\"true\"></tt:troubleNumList>\n");
        else {
            sb.append("<tt:troubleNumList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null)
                    sb.append("<co:item xsi:nil=\"true\"></co:item>\n");
                else
                    sb.append("<co:item>" + attr[x] + "</co:item>\n");
            }

            sb.append("</tt:troubleNumList>\n");
        }
    }

    public static void TroubledObject2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        String attr = ttv.getTroubledObject();
        if (attr == null)
            sb.append("<tt:troubledObject xsi:nil=\"true\"></tt:troubledObject>\n");
        else
            sb.append("<tt:troubledObject>" + attr + "</tt:troubledObject>\n");
    }

    public static void TroubleType2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        XmlTranslator.EnumeratedType2Xml(sb,
                                         TroubleType.class,
                                         ttv.getTroubleType());

    }

    public static void TroubleState2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        XmlTranslator.EnumeratedType2Xml(sb,
                                         TroubleState.class,
                                         ttv.getTroubleState());

    }

    public static void TroubleStatus2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        //Wipro



        XmlTranslator.EnumeratedType2Xml(sb,
        TroubleStatus.class,
        ttv.getTroubleStatus());
     //Wipro

//	   int enum = ttv.getTroubleStatus();
//
//       if ( enum == -1 ) {      //if not set (unpopulated)
//           sb.append("<tt:troubleStatus xsi:nil=\"true\">" + "</tt:status>\n");
//       } else {
//           String enumStrName = XmlTranslator.EnumeratedType2Xml(TroubleStatus.class,
//                                         enum);
//           sb.append("<tt:status>" + enumStrName + "</tt:status>\n");
//	   }




    }

    public static void TroubleStatusTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getTroubleStatusTime();
        if (attr == null)
            sb.append("<tt:troubleStatusTime xsi:nil=\"true\"></tt:troubleStatusTime>\n");
        else {
            sb.append("<tt:troubleStatusTime>");
            XmlTranslator.Date2Xml(sb, attr);
            sb.append("</tt:troubleStatusTime>\n");
        }
    }

    public static void PerceivedTroubleSeverity2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        XmlTranslator.EnumeratedType2Xml(sb,
                                         PerceivedTroubleSeverity.class,
                                         ttv.getPerceivedTroubleSeverity());

    }

    public static void PreferredPriority2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        XmlTranslator.EnumeratedType2Xml(sb,
                                         PreferredPriority.class,
                                         ttv.getPreferredPriority());

    }

    public static void TroubleDetectionTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getTroubleDetectionTime();
        if (attr == null)
            sb.append("<tt:troubleDetectionTime xsi:nil=\"true\"></tt:troubleDetectionTime>\n");
        else {
            sb.append("<tt:troubleDetectionTime>");
            XmlTranslator.Date2Xml(sb, attr);
            sb.append("</tt:troubleDetectionTime>\n");
        }
    }

    public static void TroubleLocationInfoList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        TroubleLocationInfo[] attr = ttv.getTroubleLocationInfoList();
        if (attr == null)
            sb.append("<tt:troubleLocationInfoList xsi:nil=\"true\"></tt:troubleLocationInfoList>\n");
        else {
            sb.append("<tt:troubleLocationInfoList>\n");

            for (int x = 0; x < attr.length; x++) {

                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>\n");
                } else {
                    sb.append("<tt:item>\n");

                    AccessHours[] accHrs = attr[x].getAccessHoursList();
                    if (accHrs == null)
                        sb.append("<tt:accessHoursList xsi:nil=\"true\"></tt:accessHoursList>\n");
                    else {
                        sb.append("<tt:accessHoursList>\n");
                        for (int y = 0; y < accHrs.length; y++) {
                            sb.append("<tt:item>\n");
                            XmlTranslator.AccessHours2Xml(sb, accHrs[y]);
                            sb.append("</tt:item>\n");
                        }
                        sb.append("</tt:accessHoursList>\n");
                    }

                    PersonReach locPerson = attr[x].getLocationPerson();
                    if (locPerson == null)
                        sb.append("<tt:locationPerson xsi:nil=\"true\"></tt:locationPerson>\n");
                    else {
                        sb.append("<tt:locationPerson>\n");
                        XmlTranslator.PersonReach2Xml(sb, locPerson);
                        sb.append("</tt:locationPerson>\n");
                    }


                    String oid = attr[x].getObjectIdDN();
                    if (oid == null)
                        sb.append("<tt:objectIdDN xsi:nil=\"true\"></tt:objectIdDN>\n");
                    else
                        sb.append("<tt:objectIdDN>" + oid + "</tt:objectIdDN>\n");


                    Address addr = attr[x].getPremiseAddress();
					address2Xml(sb, "tt:premiseAddress", addr);

					String pn = attr[x].getPremiseName();
                    if (pn == null)
                        sb.append("<tt:premiseName xsi:nil=\"true\"></tt:premiseName>\n");
                    else
                        sb.append("<tt:premiseName>" + pn + "</tt:premiseName>\n");


                    sb.append("</tt:item>\n");  //end array item
                }
            }

            sb.append("</tt:troubleLocationInfoList>\n");
        }


    }


	public static void TroubledObjectAccessFromTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getTroubledObjectAccessFromTime();
        if (attr == null)
            sb.append("<tt:troubledObjectAccessFromTime xsi:nil=\"true\"></tt:troubledObjectAccessFromTime>\n");
        else {
            sb.append("<tt:troubledObjectAccessFromTime>");
            XmlTranslator.Date2Xml(sb, ttv.getTroubledObjectAccessFromTime());
            sb.append("</tt:troubledObjectAccessFromTime>\n");
        }
    }

    public static void TroubledObjectAccessHoursList2Xml(StringBuffer sb, TroubleTicketValue ttv) {

        AccessHours[] attr = ttv.getTroubledObjectAccessHoursList();
        if (attr == null)
            sb.append("<tt:troubledObjectAccessHoursList xsi:nil=\"true\"></tt:troubledObjectAccessHoursList>\n");
        else {
            sb.append("<tt:troubledObjectAccessHoursList>\n");

            for (int x = 0; x < attr.length; x++) {
                if (attr[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");
                    XmlTranslator.AccessHours2Xml(sb, attr[x]);
                    sb.append("</tt:item>\n");
                }
            }

            sb.append("</tt:troubledObjectAccessHoursList>\n");
        }
    }

    public static void TroubledObjectAccessToTime2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        java.util.Date attr = ttv.getTroubledObjectAccessToTime();
        if (attr == null)
            sb.append("<tt:troubledObjectAccessToTime xsi:nil=\"true\"></tt:troubledObjectAccessToTime>\n");
        else {
            sb.append("<tt:troubledObjectAccessToTime>");
            XmlTranslator.Date2Xml(sb, ttv.getTroubledObjectAccessToTime());
            sb.append("</tt:troubledObjectAccessToTime>\n");
        }
    }

    public static void TroubleSystemDN2Xml(StringBuffer sb, TroubleTicketValue ttv) {
        String attr = ttv.getTroubleSystemDN();
        if (attr == null)
            sb.append("<tt:troubleSystemDN xsi:nil=\"true\"></tt:troubleSystemDN>\n");
        else
            sb.append("<tt:troubleSystemDN>" + attr + "</tt:troubleSystemDN>\n");
    }



    //----------------------------------------------------------------------------
    //
    //                  "To XML" for sub-types
    //
    //----------------------------------------------------------------------------

    public static void AccessHours2Xml(StringBuffer sb, AccessHours ah) {

        if (ah != null) {
            XmlTranslator.EnumeratedType2Xml(sb, DayOfWeek.class, ah.getDayOfWeek());

            TimeInterval[] ti = ah.getTimeIntervalList();
            if (ti == null)
                sb.append("<tt:timeIntervalList xsi:nil=\"true\"></tt:timeIntervalList>\n");
            else {
                sb.append("<tt:timeIntervalList>\n");
                for (int x = 0; x < ti.length; x++) {
                    sb.append("<tt:item>\n");
                    XmlTranslator.TimeInterval2Xml(sb, ti[x]);
                    sb.append("</tt:item>\n");
                }
                sb.append("</tt:timeIntervalList>\n");
            }
        }

    }

    public static void ActivityDuration2Xml(StringBuffer sb, ActivityDuration ad) {
        if (ad != null) {
            XmlTranslator.EnumeratedType2Xml(sb,ActivityType.class,ad.getActivityType());

            sb.append("<tt:billable>");
            XmlTranslator.Boolean2Xml(sb, ad.getBillable());
            sb.append("</tt:billable>\n");


            TimeLength tl = ad.getDuration();
            if (tl == null)
                sb.append("<tt:duration xsi:nil=\"true\"></tt:duration>\n");
            else {
                sb.append("<tt:duration>\n");
                XmlTranslator.TimeLength2Xml(sb, tl);
                sb.append("</tt:duration>\n");
            }


        }
    }


    //-----------------------------------------------------------------------------
    // Maps the enumerated value (the int value) to its String equivalent using the
    // interface reflector
    //-----------------------------------------------------------------------------

    public static String EnumeratedType2Xml(Class interfaceClass, int enumInt) {
        String enumStrName = null;

        try {
            enumStrName = (String)
                    (ttIntReflector.getInterfaceFieldName(interfaceClass, enumInt));


        } catch (javax.oss.IllegalArgumentException iLLEx) {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            Logger.logException(iLLEx);
        } catch (java.lang.IllegalAccessException iLLAccEx) {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            Logger.logException(iLLAccEx);
        }
        return enumStrName;
    }

    public static void EnumeratedType2Xml(StringBuffer sb,
                                          Class interfaceClass,
                                          int enumInt) {

        String enumStrName = null;

        try {
            enumStrName = (String)
                    (ttIntReflector.getInterfaceFieldName(interfaceClass, enumInt));


        } catch (javax.oss.IllegalArgumentException iLLEx) {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            Logger.logException(iLLEx);
        } catch (java.lang.IllegalAccessException iLLAccEx) {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            Logger.logException(iLLAccEx);
        }

        //-----------------------------------------------------------------------------
        // The interface class name corresponds to the XML tag.
        // Class.getName() returns the fully qualified name, so strip out the leaf node.
        // -1 is the default for non-populated enum values.
        //-----------------------------------------------------------------------------
        String fqClassName = interfaceClass.getName();
        //Logger.log  ("EnumeratedType2Xml - interface class name: " + fqClassName);
        String classNameTxt = fqClassName.substring((fqClassName.lastIndexOf(".")) + 1);
        //lower case first character
        String lower = classNameTxt.substring(0, 1);
        //Logger.log ("****lower= " + lower );
        String restOf = classNameTxt.substring(1);
        //Logger.log ("****restOf= " + restOf );
        String className = lower.toLowerCase() + restOf;
        //Logger.log ("****className= " + className );



        if (enumInt == -1)      //if not set (unpopulated)
            sb.append("<tt:" + className + " xsi:nil=\"true\">" + "</tt:" + className + ">\n");
        else
            sb.append("<tt:" + className + ">" + enumStrName + "</tt:" + className + ">\n");


    }


    public static void Authorization2Xml(StringBuffer sb, Authorization auth) {

        if (auth != null) {
            XmlTranslator.EnumeratedType2Xml(sb,ActivityType.class,auth.getActivityType());

            sb.append("<tt:authPerson>\n");
            XmlTranslator.PersonReach2Xml(sb, auth.getAuthPerson());
            sb.append("</tt:authPerson>\n");

            sb.append("<tt:authTime>");
            XmlTranslator.Date2Xml(sb, auth.getAuthTime());
            sb.append("</tt:authTime>\n");


            XmlTranslator.EnumeratedType2Xml(sb, RequestState.class, auth.getRequestState());
        } else {


        }

    }


    public static void PersonReach2Xml(StringBuffer sb, PersonReach pr) {

        if (pr != null) {
            sb.append("<tt:email>");
            sb.append((String) (pr.getEmail()));
            sb.append("</tt:email>\n");

            sb.append("<tt:fax>");
            sb.append((String) (pr.getFax()));
            sb.append("</tt:fax>\n");

			XmlTranslator.address2Xml(sb, "tt:location", pr.getLocation());

            sb.append("<tt:name>");
            sb.append((String) (pr.getName()));
            sb.append("</tt:name>\n");

            sb.append("<tt:number>");
            sb.append((String) (pr.getNumber()));
            sb.append("</tt:number>\n");

            sb.append("<tt:organizationName>");
            sb.append((String) (pr.getOrganizationName()));
            sb.append("</tt:organizationName>\n");

            sb.append("<tt:phone>");
            sb.append((String) (pr.getPhone()));
            sb.append("</tt:phone>\n");

            sb.append("<tt:responsible>");
            sb.append((String) (pr.getResponsible()));
            sb.append("</tt:responsible>\n");

            sb.append("<tt:sMSAddress>");
            sb.append((String) (pr.getSMSAddress()));
            sb.append("</tt:sMSAddress>\n");
        }

    }

	public static void address2Xml(StringBuffer sb, String tag, Address addr) {
		if (addr == null)
			sb.append("<" + tag + " xsi:nil=\"true\" />\n");
		else {
			String xsiType = null;
			if ( addr instanceof NorthAmericaAddress ) {
				xsiType = "tt:NorthAmericaAddress";
			}

			beginTag(sb, tag, xsiType);
			addValueTag(sb, "tt:addressInfo", addr.getAddressInfo());

			if ( addr instanceof NorthAmericaAddress ) {
				NorthAmericaAddress naAddr = (NorthAmericaAddress) addr;

				addValueTag(sb, "tt:civicAddress", naAddr.getCivicAddress());
				addValueTag(sb, "tt:city", naAddr.getCity());
				addValueTag(sb, "tt:state", naAddr.getState());
				addValueTag(sb, "tt:zip", naAddr.getZip());
			}

			endTag(sb, tag);
		}
	}

	public static void beginTag(StringBuffer sb, String tag, String xsiType) {
		sb.append("<");
		sb.append(tag);
		if ( xsiType != null ) {
			sb.append(" xsi:type=\"");
			sb.append(xsiType);
			sb.append("\"");
		}
		sb.append(">\n");
	}


	public static void endTag(StringBuffer sb, String tag) {
		sb.append("</" + tag + ">\n");
	}

	public static void addValueTag(StringBuffer sb, String tagName, String value) {
		sb.append('<');
		sb.append(tagName);

		if ( value == null ) {
			sb.append(" xsi:nil=\"true\" />");
		}
		else {
			sb.append('>');
			sb.append(value);
			sb.append("</");
			sb.append(tagName);
			sb.append('>');
		}
	}


    public static void Boolean2Xml(StringBuffer sb, boolean bool) {
        //Since boolean is a primitive type, assume that there is no "null" value
        //for it - it's always either true or false.  May want to revisit this later.
        if (bool)
            sb.append("true");
        else
            sb.append("false");
    }

    public static void TimeLength2Xml(StringBuffer sb, TimeLength tl) {

        if (tl != null) {
            sb.append("<tt:years>" + tl.getYears() + "</tt:years>\n");
            sb.append("<tt:months>" + tl.getMonths() + "</tt:months>\n");
            sb.append("<tt:days>" + tl.getDays() + "</tt:days>\n");
            sb.append("<tt:hours>" + tl.getHours() + "</tt:hours>\n");
            sb.append("<tt:minutes>" + tl.getMinutes() + "</tt:minutes>\n");
            sb.append("<tt:seconds>" + tl.getSeconds() + "</tt:seconds>\n");
            sb.append("<tt:msecs>" + tl.getMsecs() + "</tt:msecs>\n");
        }
    }

    public static void Time2Xml(StringBuffer sb, javax.oss.trouble.Time tm) {
        if (tm != null) {
            sb.append("<tt:hours>" + tm.getHour() + "</tt:hours>\n");
            sb.append("<tt:minutes>" + tm.getMinute() + "</tt:minutes>\n");
            sb.append("<tt:seconds>" + tm.getSeconds() + "</tt:seconds>\n");
            sb.append("<tt:msecs>" + tm.getMilliSeconds() + "</tt:msecs>\n");
            sb.append("<tt:utcOffset>" + tm.getUtcOffset() + "</tt:utcOffset>\n");
        }
    }

    public static void TimeInterval2Xml(StringBuffer sb, TimeInterval ti) {

        if (ti != null) {
            sb.append("<tt:intervalBegin>\n");
            XmlTranslator.Time2Xml(sb, ti.getIntervalBegin());
            sb.append("</tt:intervalBegin>\n");

            sb.append("<tt:intervalEnd>\n");
            XmlTranslator.Time2Xml(sb, ti.getIntervalEnd());
            sb.append("</tt:intervalEnd>\n");
        }
    }


    //-----------------------------------------------------------------------------------
    // For now, use Date2Xml.  A future version may use a different idiom
    // since java.util.Date is being deprecated.
    //-----------------------------------------------------------------------------------
    public static void Date2Xml(StringBuffer sb, java.util.Date date) {

        if (date == null) {

            // wipro
            //VP
            sb.append("0000-00-00T00:00:00.000+00:00");
            // wipro

        } else {
            //----------------------------------------------------------------
            // Convert from java Date format into XML timeInstant format.
            // Example date format:    7 Mar 2001 10:10:10 GMT
            // Equivalent xml format:  2001-03-07T10:10:10.000Z
            //----------------------------------------------------------------

            // wipro
            //VP use HH in format instaed of hh
            //SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            // wipro


            String xmlStr = sdf.format(date);
			Calendar calendar = Calendar.getInstance();

            //Wipro
            calendar.setTime(date);
            //Wipro

			int offset = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000);
			String offsetHours = "" + Math.abs(offset / 60);
			if ( offsetHours.length() == 1 ) offsetHours = "0" + offsetHours;
			String offsetMinutes = "" + Math.abs(offset % 60);
			if ( offsetMinutes.length() == 1 ) offsetMinutes = "0" + offsetMinutes;
			xmlStr = xmlStr + (( offset > 0 ) ? '+' : '-') + offsetHours + ":" + offsetMinutes;

            Logger.log("Date2Xml date=" + date.toString() + " GMT=" + date.toGMTString() + " millisec=" + date.getTime() + " Formated=" + xmlStr);
            //System.out.println("The date in XML in date2Xml() is ***********"+ xmlStr);
            sb.append(xmlStr);
        }
    }


    //-----------------------------------------------------------------------------------
    //
    // Xml response encoding.  TroubleTicketKeyResults
    //
    //-----------------------------------------------------------------------------------
    public static String toXml(TroubleTicketKeyResult[] keyResults)
            throws org.xml.sax.SAXException {

        StringBuffer sb = new StringBuffer();

        if (keyResults == null)
            sb.append("<tt:results xsi:nil=\"true\"></tt:results>\n");
        else {
            sb.append("<tt:results>\n");

            for (int x = 0; x < keyResults.length; x++) {
                if (keyResults[x] == null) {
                    sb.append("<tt:item xsi:nil=\"true\"></tt:item>");
                } else {
                    sb.append("<tt:item>\n");


                    sb.append("<co:success>");
                    XmlTranslator.Boolean2Xml(sb, keyResults[x].isSuccess());
                    sb.append("</co:success>\n");
                    Exception exception = keyResults[x].getException();

                    if (exception != null)
                        sb.append("<tt:exception>\n" + exception.toString() + "</tt:exception>\n");
                    TroubleTicketKey key = keyResults[x].getTroubleTicketKey();
                    sb.append(((TroubleTicketKeyImpl) key).toXml("tt:troubleTicketKey"));
                    //TroubleTicketKey2Xml(sb,keyResults[x].getTroubleTicketKey());
                    sb.append("</tt:item>\n");
                }
            }
            sb.append("</tt:results>\n");

        }

        return sb.toString();

    }


    //****************************************************************************
    //
    //                            FROM XML
    //
    //****************************************************************************

    // - single TT value or template
    public static void fromXml(org.w3c.dom.Element doc, TroubleTicketValue ttv)
            throws org.xml.sax.SAXException {

        //Logger.log ("***************************************************************");
        Logger.log("*    XmlTranslator: fromXml (single TT value/template)        *");
        //Logger.log ("***************************************************************");

        Element docElement = ((Document) doc).getDocumentElement();
        String reqType = docElement.getTagName();
        Logger.log("Doc root element (request type): " + reqType);

        // Determine if we are getting a value or template based on request type
        NodeList nodeList = null;
        Node node = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketValue");
        node = nodeList.item(0);
        if (node == null) {

            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "template");
            node = nodeList.item(0);
        }

        if (node != null)
            fromXmlNoRoot(node, ttv);

    }

    public static void fromXmlTag(org.w3c.dom.Element doc, TroubleTicketValue ttv, String tag)
            throws org.xml.sax.SAXException {

        //Logger.log ("***************************************************************");
        Logger.log("*    XmlTranslator: fromXml (single TT value/template)        *");
        //Logger.log ("***************************************************************");

        Element docElement = ((Document) doc).getDocumentElement();
        String reqType = docElement.getTagName();
        Logger.log("Doc root element (request type): " + reqType);

        // Determine if we are getting a value or template based on request type
        NodeList nodeList = null;
        Node node = null;

        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tag);
        node = nodeList.item(0);


        if (node != null)
            fromXmlNoRoot(node, ttv);

    }


    // multiple TT values or templates
    public static TroubleTicketValue[] fromXml(org.w3c.dom.Element doc)
            throws org.xml.sax.SAXException {

        //Logger.log ("***************************************************************");
        Logger.log("*    XmlTranslator: fromXml (multiple TT values/templates)    *");
        //Logger.log ("***************************************************************");


        Element docElement = ((Document) doc).getDocumentElement();
        String reqType = docElement.getTagName();
        Logger.log("Doc root element is: " + reqType);

        // Determine the request type
        NodeList nodeList = null;

        if (reqType.equals("tt:tryCreateTroubleTicketsByValuesRequest") || //best effort
                reqType.equals("tt:trySetTroubleTicketsByValuesRequest") || //""
                reqType.equals("tt:createTroubleTicketsByValuesRequest") || //atomic
                reqType.equals("tt:setTroubleTicketsByValuesRequest"))            //""
        {
            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "TroubleTicketValueList");
        } else if (reqType.equals("tt:getTroubleTicketsByTemplatesRequest") ||
                reqType.equals("tt:trySetTroubleTicketsByValuesRequest") || //best effort
                reqType.equals("tt:tryCloseTroubleTicketsByTemplatesRequest") || //""
                reqType.equals("tt:tryCancelTroubleTicketsByTemplatesRequest") || //""
                reqType.equals("tt:tryEscalateTroubleTicketsByTemplatesRequest") || //""
                reqType.equals("tt:setTroubleTicketsByValuesRequest") || //atomic
                reqType.equals("tt:closeTroubleTicketsByTemplatesRequest") || //""
                reqType.equals("tt:cancelTroubleTicketsByTemplatesRequest") || //""
                reqType.equals("tt:escalateTroubleTicketsByTemplatesRequest"))        //""
        {
            nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "TroubleTicketTemplateList");
        }

        Node node = null;
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                Logger.log(nullNode);
                throw new org.xml.sax.SAXException(nullNode);
            }
        }

        return TTValuesFromXml(node);
    }

    public static void fromXmlNoRoot(TroubleTicketValue ttv, Node node)
            throws org.xml.sax.SAXException {
        fromXmlNoRoot(node, ttv);
    }

    public static void fromXmlNoRoot(TroubleTicketKey ttkey, Node node)
            throws org.xml.sax.SAXException {
        ttkey = TroubleTicketKeyFromXml(node);
    }

    public static void fromXmlNoRoot(TroubleTicketCreateEvent event, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(TroubleTicketCloseOutEvent event, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(TroubleTicketCancellationEvent event, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(TroubleTicketAttributeValueChangeEvent event, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(TroubleTicketStatusChangeEvent event, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(QueryAllOpenTroubleTicketsValue val, Node node)
            throws org.xml.sax.SAXException {

    }

    public static void fromXmlNoRoot(QueryByEscalationValue val, Node node)
            throws org.xml.sax.SAXException {

    }


    //public static void fromXmlNoRoot(NodeList nodeList,TroubleTicketValue ttv)
    public static void fromXmlNoRoot(Node node, TroubleTicketValue ttv)
            throws org.xml.sax.SAXException {

        // Get the node's children
        NodeList nodeList = node.getChildNodes();
        Node node2;
        Logger.log("fromXmlNoRoot--> translated attributes:");
        Logger.log("fromXmlNoRoot--> length:" + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            node2 = nodeList.item(i);

            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();


            if (nodeName.equals("troubleTicketKey"))
                TroubleTicketKeyFromXml(node2, ttv);
            else if (nodeName.equals("accountOwner"))
                AccountOwnerFromXml(node2, ttv);
            else if (nodeName.equals(":activityDurationList"))
                ActivityDurationListFromXml(node2, ttv);
            else if (nodeName.equals("additionalTroubleInfoList"))
                AdditionalTroubleInfoListFromXml(node2, ttv);
            else if (nodeName.equals("afterHoursRepairAuthority"))
                AfterHoursRepairAuthorityFromXml(node2, ttv);
            else if (nodeName.equals("authorizationList"))
                AuthorizationListFromXml(node2, ttv);
            else if (nodeName.equals("cancelRequestedByCustomer"))
                CancelRequestedByCustomerFromXml(node2, ttv);
            else if (nodeName.equals("clearancePerson"))
                ClearancePersonFromXml(node2, ttv);
            else if (nodeName.equals("closeOutNarr"))
                CloseOutNarrFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("baseCloseOutVerification")|| nodeName.equals("closeOutVerification"))
                CloseOutVerificationFromXml(node2, ttv);
            //Wipro

            else if (nodeName.equals("commitmentTime"))
                CommitmentTimeFromXml(node2, ttv);

            else if (nodeName.equals("commitmentTimeRequested"))
                CommitmentTimeRequestedFromXml(node2, ttv);
            else if (nodeName.equals("customer"))
                CustomerFromXml(node2, ttv);
            else if (nodeName.equals("customerRoleAssignmentList"))
                CustomerRoleAssignmentListFromXml(node2, ttv);
            else if (nodeName.equals("customerTroubleNum"))
                CustomerTroubleNumFromXml(node2, ttv);
            else if (nodeName.equals("dialog"))
                DialogFromXml(node2, ttv);
            else if (nodeName.equals("escalationList"))
                EscalationListFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("baseInitiatingMode")||  nodeName.equals("initiatingMode"))
                InitiatingModeFromXml(node2, ttv);
            //Wipro

            else if (nodeName.equals("lastUpdateTime"))
                LastUpdateTimeFromXml(node2, ttv);
            else if (nodeName.equals("maintServiceCharge"))
                MaintServiceChargeFromXml(node2, ttv);
            else if (nodeName.equals("originator"))
                OriginatorFromXml(node2, ttv);

            else if (nodeName.equals("outageDuration"))
                OutageDurationFromXml(node2, ttv);
            else if (nodeName.equals("receivedTime"))
                ReceivedTimeFromXml(node2, ttv);
            else if (nodeName.equals("relatedAlarmList"))
                RelatedAlarmListFromXml(node2, ttv);
            else if (nodeName.equals("relatedTroubleTicketKeyList"))
                RelatedTroubleTicketKeyListFromXml(node2, ttv);
            else if (nodeName.equals("repairActivityList"))
                RepairActivityListFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("baseRepeatReport")||nodeName.equals("repeatReport"))
                RepeatReportFromXml(node2, ttv);
            //Wipro

            else if (nodeName.equals("restoredTime"))
                RestoredTimeFromXml(node2, ttv);
            else if (nodeName.equals("serviceUnavailableBeginTime"))
                ServiceUnavailableBeginTimeFromXml(node2, ttv);
            else if (nodeName.equals("serviceUnavailableEndTime"))
                ServiceUnavailableEndTimeFromXml(node2, ttv);
            else if (nodeName.equals("sPRoleAssignmentList"))
                SPRoleAssignmentListFromXml(node2, ttv);

            else if (nodeName.equals("suspectObjectList"))
                SuspectObjectListFromXml(node2, ttv);
            else if (nodeName.equals("troubleDescription"))
                TroubleDescriptionFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("baseFoundType") || nodeName.equals("troubleFound"))
                TroubleFoundFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("troubleLocation"))
                TroubleLocationFromXml(node2, ttv);
            else if (nodeName.equals("troubleNumList"))
                TroubleNumListFromXml(node2, ttv);
            else if (nodeName.equals("troubledObject"))
                TroubledObjectFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("baseTroubleType") || nodeName.equals("troubleType"))
                TroubleTypeFromXml(node2, ttv);

            else if (nodeName.equals("baseState") || nodeName.equals("troubleState"))
                TroubleStateFromXml(node2, ttv);

            else if (nodeName.equals("baseStatus") || nodeName.equals("troubleStatus"))
                TroubleStatusFromXml(node2, ttv);
            //wipro
            else if (nodeName.equals("troubleStatusTime"))
                TroubleStatusTimeFromXml(node2, ttv);
            //Wipro
            else if (nodeName.equals("basePerceivedTroubleSeverity") || nodeName.equals("perceivedTroubleSeverity"))
                PerceivedTroubleSeverityFromXml(node2, ttv);

            else if (nodeName.equals("basePreferredPriority") || nodeName.equals("preferredPriority"))
                PreferredPriorityFromXml(node2, ttv);
            //Wipro

            else if (nodeName.equals("troubleDetectionTime"))
                TroubleDetectionTimeFromXml(node2, ttv);
            else if (nodeName.equals("troubleLocationInfoList"))
                TroubleLocationInfoListFromXml(node2, ttv);
            else if (nodeName.equals("troubledObjectAccessFromTime"))
                TroubledObjectAccessFromTimeFromXml(node2, ttv);
            else if (nodeName.equals("troubledObjectAccessHoursList"))
                TroubledObjectAccessHoursListFromXml(node2, ttv);
            else if (nodeName.equals("troubledObjectAccessToTime"))
                TroubledObjectAccessToTimeFromXml(node2, ttv);
            else if (nodeName.equals("troubleSystemDN"))
                TroubleSystemDNFromXml(node2, ttv);

            else if (nodeName.equals("#comment")) {
                continue;
            } else {
                //String unknownAttr = new String("XmlTranslator.fromXml(): Unknown Xml attribute: " + nodeName);
                //Logger.log (unknownAttr);
                //throw new org.xml.sax.SAXException(unknownAttr);
            }

        }
    }

    //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
    public static TroubleTicketKey[] TTKeysFromXmlItems(Node node)
            throws org.xml.sax.SAXException {

        //what is passed is  anode pointing at the "values"->"item"->"ttvalue"
        //the item is a container for a trouble ticket value

        java.util.Vector ttKeyVector = new java.util.Vector();

        NodeList items = node.getChildNodes();      //get the child items
        //Logger.log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);

        Node node2;
        TroubleTicketKey key = null;

        int len = items.getLength();

        for (int i = 0; i < len; i++) {
            node2 = items.item(i);      //tt:item
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;



            key = TroubleTicketKeyFromXml(node2);

            ttKeyVector.addElement(key);


        }

        //TroubleTicketValue[] ttValues = (TroubleTicketValue[])attVector.toArray(new TroubleTicketValue[0]);
        return (TroubleTicketKey[]) (ttKeyVector.toArray(new TroubleTicketKey[0]));

    }

    public static String getArrayofTroubleTicketValues(TroubleTicketValue[] ttVals, boolean useValues) {
        StringBuffer sb = new StringBuffer();
        if (useValues) sb.append("<tt:troubleTicketValues>");
        for (int i = 0; i < ttVals.length; i++) {
            sb.append("<tt:item>");
            sb.append(getXMLTroubleTicketValue(ttVals[i], true));
            sb.append("</tt:item>");
        }
        if (useValues) sb.append("</tt:troubleTicketValues>");
        return sb.toString();

    }

    public static String getXMLTroubleTicketValue(TroubleTicketValue ttValue, boolean noRoot) {

        String xmlValue = null;
        try {

            String[] attrNames = ttValue.getPopulatedAttributeNames();
            HashMap attMap = new HashMap();
            if (attrNames != null) {
                for (int i = 0; i < attrNames.length; i++) {
                    attMap.put(attrNames[i], null);
                }
            }

            if (noRoot) {
                xmlValue = XmlTranslator.toXmlNoRoot(ttValue, attMap);
            } else {
                xmlValue = XmlTranslator.toXml(ttValue, attMap);
            }
        } catch (Exception ex) {

        }


        return xmlValue;
    }


    public static TroubleTicketKey[] getTroubleTicketKeys(Document doc) {

        NodeList nodeList = null;
        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", "troubleTicketKeys");
        Node node = null;
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                Logger.log(nullNode);
                return null;

            }
        }
        //node with item and under item

        TroubleTicketKey[] keys = null;
        try {
            keys = XmlTranslator.TTKeysFromXmlItems(node);
        } catch (Exception e) {

        }
        return keys;
    }


    //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
    public static TroubleTicketValue[] TTValuesFromXmlItems(Node node)
            throws org.xml.sax.SAXException {

        //what is passed is  anode pointing at the "values"->"item"->"ttvalue"
        //the item is a container for a trouble ticket value

        java.util.Vector ttValVector = new java.util.Vector();

        NodeList items = node.getChildNodes();      //get the child items
        //Logger.log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);

        Node node2;
        TroubleTicketValue ttVal = null;

        int len = items.getLength();

        for (int i = 0; i < len; i++) {
            node2 = items.item(i);      //tt:item
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            Logger.log("XmlTranslator.TTValuesFromXml:  nodeName: " + nodeName);

            ttVal = new TroubleTicketValueImpl();
            fromXmlNoRoot(node2, ttVal);

            //System.out.println( "$$$$$TTValuesFromXMLItems ttVal.getTroubleTicketKey(): " + ttVal.getTroubleTicketKey());
            ttValVector.addElement(ttVal);


        }


        return (TroubleTicketValue[]) (ttValVector.toArray(new TroubleTicketValue[0]));

    }

    public static TroubleTicketValue[] getTroubleTicketValues(Document doc, String tag) {
        NodeList nodeList = null;
        nodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/TroubleTicket", tag);
        Node node = null;
        if (nodeList != null) {
            node = nodeList.item(0);
            if (node == null) {
                String nullNode = new String("null Node in XmlTranslator.fromXml (multiple values)");
                Logger.log(nullNode);
                return null;

            }
        }
        //node with item and under item

        TroubleTicketValue[] values = null;
        try {
            values = TTValuesFromXmlItems(node);
        } catch (Exception e) {

        }
        return values;
    }

    //handles XML TroubleTicketValueList (array of TroubleTicketValue or TroubleTicketTemplate)
    public static TroubleTicketValue[] TTValuesFromXml(Node node)
            throws org.xml.sax.SAXException {

        //------------------------------------------------------------------------------------
        // The passed Node is either a TroubleTicketValueList or TroubleTicketTemplateList.
        // Both are of type "tt:ArrayOfTroubleTicketValue".  Loop thru the array to translate
        // into the returned TroubleTicketValue[]
        //
        // Sample XML request structure:
        //
        //  TrySetTroubleTicketsByTemplatesRequest
        //      TroubleTicketTemplateList (search criteria - of type tt:ArrayOfTroubleTicketValue)
        //          Item
        //              TroubleTicketValue
        //          Item
        //              ...
        //      TroubleTicketValue  (values to be set)
        //      ReturnMode
        //
        //-----------------------------------------------------------------------------------

        java.util.Vector ttValVector = new java.util.Vector();

        NodeList items = node.getChildNodes();      //get the child items
        //Logger.log ("XmlTranslator.TTValuesFromXml:  NodeList: " + items);

        Node node2;
        TroubleTicketValue ttVal = null;

        int len = items.getLength();

        for (int i = 0; i < len; i++) {
            node2 = items.item(i);      //tt:item
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            // Iterate over the contents of each item
            NodeList nodeList2 = node2.getChildNodes();
            Node node3 = null;

            for (int y = 0; y < nodeList2.getLength(); y++) {
                node3 = nodeList2.item(y);
                if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                //WS String nodeName = node3.getNodeName();
                String nodeName = node3.getLocalName();
                //Logger.log ("XmlTranslator.TTValuesFromXml:  nodeName: " + nodeName);
                if (nodeName.equals("troubleTicketValue")
                        || nodeName.equals("template")) {
                    ttVal = new TroubleTicketValueImpl();
                    fromXmlNoRoot(node3, ttVal);
                    ttValVector.addElement(ttVal);
                }
            }

        }

        //TroubleTicketValue[] ttValues = (TroubleTicketValue[])attVector.toArray(new TroubleTicketValue[0]);
        return (TroubleTicketValue[]) (ttValVector.toArray(new TroubleTicketValue[0]));

    }


    /*--------------------------------------------------------------------------------
    *  JAXP XML parsing notes:
    *
    *    getChildNodes():
    *        will never be null - it either contains nodes or is an empty list
    *
    *    getFirstChild():
    *        will return null if there are no children. Refers to the 0th child,
    *        e.g. the first child is the contents of the node itself
    *
    *        Currently, if a node is nilled (e.g. <tt:sometag xsi:nil="true"></tt:sometag>)
    *        null is returned from the getFirstChild call.  The getNodeName call
    *        correctly returns the appropriate node name (e.g. for the above example,
    *        it returns "tt:sometag"
    *
    *    hasChildNodes():
    *        determines if there are child nodes
    *
    *-------------------------------------------------------------------------------*/

    public static void TroubleTicketKeyFromXml(Node node, TroubleTicketValue ttv) {

        //Logger.log ("**************************************");
        Logger.log("XmlTranslator:TroubleTicketKeyFromXml");
        //Logger.log ("**************************************");

        TroubleTicketKey ttKey = ttv.makeTroubleTicketKey();

        // Get all the elements contained by the key
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            /*  TODO - domain no longer used
            if ( nodeName.equals("tt:Domain") )
            {
                if (node2.hasChildNodes())  //verify that its not nilled
                    ttKey.setDomain( node2.getFirstChild().getNodeValue());
            }
            else
            */
            if (nodeName.equals("primaryKey")) {
                if (node2.hasChildNodes()) {
                    Logger.log("--->TRYING: " + node2.getFirstChild().getNodeValue());
                    ttKey.setPrimaryKey(node2.getFirstChild().getNodeValue());

                }
            }
            /* Update with the new entity key definition here
            else if ( nodeName.equals("tt:Type") )
            {
                if (node2.hasChildNodes())
                    ttKey.setType( node2.getFirstChild().getNodeValue());
            }
            else
            */
            if (nodeName.equals("#comment")) {
                continue;
            }
        }
        ttv.setTroubleTicketKey(ttKey);
        Logger.log("CREATED TTKEY FROM XML");
        //((TroubleTicketKeyImpl)ttKey).print(10);
    }
//VR: assuming there is no need for a fromXML method for the last update version number
/*  public static void LastUpdateVersionNumberFromXml(Node node,TroubleTicketValue ttv)
    {
        if (node.hasChildNodes())
            //aLong = new Long(node2.getFirstChild().getNodeValue());
            ttv.setLastVersionUpdateNumber(node2.getFirstChild().getNodeValue());
    }
    */

    public static void AccountOwnerFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setAccountOwner(XmlTranslator.PersonReachFromXml(node));
    }

    public static void ActivityDurationListFromXml(Node node, TroubleTicketValue ttv) {
        java.util.Vector adVec = new java.util.Vector();

        // Get all the immediate children of the curr Node.
        NodeList nodeList = node.getChildNodes();
        Node node2, node3;
        //PG Default is SET
        //was int modifier = MultiValueList.NONE;
        int modifier = MultiValueList.SET;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            if (nodeName.equals("modifier"))
                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
            else {


                NodeList nodeList2 = node2.getChildNodes();
                for (int y = 0; y < nodeList2.getLength(); y++) {
                    node3 = nodeList2.item(y);
                    if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                    adVec.addElement(XmlTranslator.ActivityDurationFromXml(node3));
                }
            }

        }


        //Use the toArray(new type[0) call - need to specify the type or will
        //get a runtime ClassCast exception
        ActivityDuration[] adArray = (ActivityDuration[]) adVec.toArray(new ActivityDuration[0]);


        ActivityDurationList adList = new ActivityDurationListImpl();

        //use the appropriate  method based on the modifier...

        if (modifier == MultiValueList.SET) {
            adList.set(adArray);
        } else if (modifier == MultiValueList.ADD) {
            adList.add(adArray);
        } else if (modifier == MultiValueList.REMOVE) {
            adList.remove(adArray);
        }


        ttv.setActivityDurationList(adList);

    }

    public static void AdditionalTroubleInfoListFromXml(Node node, TroubleTicketValue ttv) {

        java.util.Vector atiVec = new java.util.Vector();

        // Get all the immediate children of the curr Node.
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            if (node2.hasChildNodes())
                atiVec.addElement(node2.getFirstChild().getNodeValue());
        }

        String[] addTrInfoArray = (String[]) atiVec.toArray(new String[0]);
        ttv.setAdditionalTroubleInfoList(addTrInfoArray);
    }

    public static void AfterHoursRepairAuthorityFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setAfterHoursRepairAuthority(XmlTranslator.BooleanFromXml(node));
    }

    public static void AuthorizationListFromXml(Node node, TroubleTicketValue ttv) {
        java.util.Vector authVec = new java.util.Vector();

        // Get all the immediate children of the curr Node.
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            authVec.addElement(XmlTranslator.AuthorizationFromXml(node2));
        }

        Authorization[] authArray = (Authorization[]) authVec.toArray(new Authorization[0]);
        ttv.setAuthorizationList(authArray);
    }

    public static void CancelRequestedByCustomerFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setCancelRequestedByCustomer(XmlTranslator.BooleanFromXml(node));
    }

    public static void ClearancePersonFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setClearancePerson(XmlTranslator.PersonReachFromXml(node));
    }

    public static void CloseOutNarrFromXml(Node node, TroubleTicketValue ttv) {
        if (node.hasChildNodes())
            ttv.setCloseOutNarr((node.getFirstChild()).getNodeValue());
    }

    public static void CloseOutVerificationFromXml(Node node, TroubleTicketValue ttv) {
        //ttv.setCloseOutVerification(XmlTranslator.BooleanFromXml(node));
        ttv.setCloseOutVerification(XmlTranslator.EnumeratedTypeFromXml(node, CloseOutVerification.class));
    }

    public static void CommitmentTimeFromXml(Node node, TroubleTicketValue ttv) {
        log("commitmenttimefromxml");
        ttv.setCommitmentTime(XmlTranslator.DateFromXml(node));
    }

    public static void CommitmentTimeRequestedFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setCommitmentTimeRequested(XmlTranslator.DateFromXml(node));
    }

    public static void CustomerFromXml(Node node, TroubleTicketValue ttv) {
        //PersonReach
        ttv.setCustomer(XmlTranslator.PersonReachFromXml(node));
    }

    public static void CustomerRoleAssignmentListFromXml(Node node, TroubleTicketValue ttv) {

        // iterate over the array elements - of type "item"
        java.util.Vector craVec = new Vector();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        CustomerRoleAssignment custRA = null;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            // Iterate over the contents of each item
            NodeList nodeList2 = node2.getChildNodes();
            Node node3;

            //CustomerRoleAssignment custRA = ttv.makeCustomerRoleAssignment();
            custRA = new CustomerRoleAssignmentImpl();

            for (int y = 0; y < nodeList2.getLength(); y++) {
                node3 = nodeList2.item(y);
                if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                //WS String nodeName = node3.getNodeName();
                String nodeName = node3.getLocalName();
                //Wipro
                if (nodeName.equals("baseRole") || nodeName.equals("role"))
                //Wipro
                {
                    if (node3.hasChildNodes())
                        custRA.setRole(node3.getFirstChild().getNodeValue());
                } else if (nodeName.equals("contactPerson")) {
                    custRA.setContactPerson(XmlTranslator.PersonReachFromXml(node3));
                } else if (nodeName.equals("#comment")) {
                    continue;
                }

            }

            craVec.addElement(custRA);      //add to vector
        }

        CustomerRoleAssignment[] craArray = (CustomerRoleAssignment[]) craVec.toArray(new CustomerRoleAssignment[0]);
        ttv.setCustomerRoleAssignmentList(craArray);

    }


    public static void CustomerTroubleNumFromXml(Node node, TroubleTicketValue ttv) {
        //String
        if (node.hasChildNodes())
            ttv.setCustomerTroubleNum((node.getFirstChild()).getNodeValue());
    }

    public static void DialogFromXml(Node node, TroubleTicketValue ttv) {
        //String
        if (node.hasChildNodes())
            ttv.setDialog((node.getFirstChild()).getNodeValue());
    }


    public static void EscalationListFromXml(Node node, TroubleTicketValue ttv) {
        java.util.Vector escaVec = new Vector();

        // Get the immediate children of the escalation node
        NodeList nodeList = node.getChildNodes();
        Node node2;
        //PG Default is SET
        //was int modifier = MultiValueList.NONE;
        int modifier = MultiValueList.SET;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            if (nodeName.equals("modifier"))
                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
            else {
                // Get the immediate children (items) of the node "Escalations"
                NodeList nodeList2 = node2.getChildNodes();
                Node node3;
                for (int y = 0; y < nodeList2.getLength(); y++) {
                    node3 = nodeList2.item(y);
                    if (node3.getNodeType() != Node.ELEMENT_NODE) continue;

                    // Now iterate over the contents of each item node
                    NodeList nodeList3 = node3.getChildNodes();
                    Node node4;

                    Escalation esca = new EscalationImpl();

                    for (int z = 0; z < nodeList3.getLength(); z++) {
                        node4 = nodeList3.item(z);
                        if (node4.getNodeType() != Node.ELEMENT_NODE) continue;
                        //WS String nodeName2 = node4.getNodeName();
                        String nodeName2 = node4.getLocalName();

                        if (nodeName2.equals("requestState"))

                            esca.setRequestState(XmlTranslator.EnumeratedTypeFromXml(node4, RequestState.class));

                        else if (nodeName2.equals("escTime"))
                            esca.setEscalationTime(XmlTranslator.DateFromXml(node4));

                        else if (nodeName2.equals("requestPerson"))
                            esca.setRequestPerson(XmlTranslator.PersonReachFromXml(node4));

                        else if (nodeName2.equals("orgLevel"))

                            esca.setLevel(XmlTranslator.EnumeratedTypeFromXml(node4, OrgLevel.class));


                        else if (nodeName2.equals("escPerson"))
                            esca.setEscalationPerson(XmlTranslator.PersonReachFromXml(node4));

                        else if (nodeName2.equals("#comment"))
                            continue;
                    }

                    escaVec.addElement(esca);
                }
            }

        }

        Escalation[] escArray = (Escalation[]) escaVec.toArray(new Escalation[0]);

        EscalationList escList = new EscalationListImpl();

        //use the appropriate  method based on the modifier...

        if (modifier == MultiValueList.SET) {
            escList.set(escArray);
        } else if (modifier == MultiValueList.ADD) {
            escList.add(escArray);
        } else if (modifier == MultiValueList.REMOVE) {
            escList.remove(escArray);
        }

        ttv.setEscalationList(escList);
    }


    public static void InitiatingModeFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setInitiatingMode(XmlTranslator.EnumeratedTypeFromXml(node, InitiatingMode.class));
    }


    public static void LastUpdateTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setLastUpdateTime(XmlTranslator.DateFromXml(node));
    }

    public static void MaintServiceChargeFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setMaintServiceCharge(XmlTranslator.BooleanFromXml(node));
    }

    public static void OriginatorFromXml(Node node, TroubleTicketValue ttv) {
        if (node.hasChildNodes())
            ttv.setOriginator((node.getFirstChild()).getNodeValue());
    }


    public static void OutageDurationFromXml(Node node, TroubleTicketValue ttv) {
        ttv.setOutageDuration(XmlTranslator.TimeLengthFromXml(node));
    }


    public static void ReceivedTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setReceivedTime(XmlTranslator.DateFromXml(node));
    }


    public static void RelatedAlarmListFromXml(Node node, TroubleTicketValue ttv) {
        java.util.Vector alarmVec = new Vector();
        if (node.getChildNodes() == null) return;
        NodeList nodeList = node.getChildNodes();
        Node node2;
        int modifier = MultiValueList.SET;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            //WS String nodeName = node2.getNodeName();
            //WIPRO
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WIPRO
            String nodeName = node2.getLocalName();
            if (nodeName.equals("modifier"))
                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
            else {
                NodeList nodeList2 = node2.getChildNodes();
                Node node3;

                AlarmValue aval = new AlarmValueImpl();

                XmlSerializer xmls = (XmlSerializer) aval.makeSerializer(XmlSerializer.class.getName());

                for (int y = 0; y < nodeList2.getLength(); y++) {

                    node3 = nodeList2.item(y);
                    if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                    aval = (AlarmValue) xmls.fromXml((Element) node3);

                    alarmVec.addElement(aval);
                }
            }

        }

        AlarmValue[] avArray = (AlarmValue[]) alarmVec.toArray(new AlarmValue[0]);

        AlarmValueList avList = new AlarmValueListImpl();

        if (modifier == MultiValueList.SET) {
            avList.set(avArray);
        } else if (modifier == MultiValueList.ADD) {
            avList.add(avArray);
        } else if (modifier == MultiValueList.REMOVE) {
            avList.remove(avArray);
        }

        ttv.setRelatedAlarmList(avList);

    }

    public static void RelatedTroubleTicketKeyListFromXml(Node node, TroubleTicketValue ttv) {


        java.util.Vector rttVec = new Vector();

        // Get the immediate children
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            //NodeList nodeList2 = node2.getChildNodes();
            //Node node3;

            //for (int y=0; y<nodeList2.getLength(); y++)
            //{
            //  node3 = nodeList2.item(y);
            Logger.log("&&&&&&&t About to call TroubleTicketKeyFromXml RelatedTroubleTicketKeyListFromXml&&&&&&");
            rttVec.addElement(XmlTranslator.TroubleTicketKeyFromXml(node2));
            //}
        }

        TroubleTicketKey[] ttkArray = (TroubleTicketKey[]) rttVec.toArray(new TroubleTicketKey[0]);
        Logger.log("&&&&&&&t RelatedTroubleTicketKeyListFromXml&&&&&&" + ttkArray.length);
        if (ttkArray.length > 0) Logger.log("&&&&&&&tprimary key = " + ttkArray[0].getPrimaryKey());
        ttv.setRelatedTroubleTicketKeyList(ttkArray);

        //for (int x=0;x<ttkArray.length;x++)
        //  ((TroubleTicketKeyImpl)ttkArray[x]).print(10);
    }


    public static void RepairActivityListFromXml(Node node, TroubleTicketValue ttv) {
        Logger.log("RepairActivy Element DEBUG");

        java.util.Vector raVec = new Vector();

        // Get all the immediate children of the escalation node - of type "Item"
        NodeList nodeList = node.getChildNodes();
        Node node2;
        //PG Default is SET
        //was int modifier = MultiValueList.NONE;
        int modifier = MultiValueList.SET;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            if (nodeName.equals("modifier"))
                modifier = XmlTranslator.EnumeratedTypeFromXml(node2, MultiValueList.class);
            else {
                // Get the immediate children (items) of the node "repairActivities"
                NodeList nodeList2 = node2.getChildNodes();
                Node node3;
                for (int y = 0; y < nodeList2.getLength(); y++) {
                    node3 = nodeList2.item(y);
                    if (node3.getNodeType() != Node.ELEMENT_NODE) continue;


                    // Now iterate over the contents of each item node
                    NodeList nodeList3 = node3.getChildNodes();
                    Node node4;

                    RepairActivity ra = new RepairActivityImpl();

                    for (int z = 0; z < nodeList3.getLength(); z++) {
                        node4 = nodeList3.item(z);
                        if (node4.getNodeType() != Node.ELEMENT_NODE) continue;

                        //WS String nodeName2 = node4.getNodeName();
                        String nodeName2 = node4.getLocalName();

                        Logger.log("RepairActivy Element is" + nodeName2);

                        if (nodeName2.equals("entryTime")) {
                            ra.setEntryTime(XmlTranslator.DateFromXml(node4));
                        } else if (nodeName2.equals("activityInfo")) {
                            if (node4.hasChildNodes())
                                ra.setActivityInfo(node4.getFirstChild().getNodeValue());
                        } else if (nodeName2.equals("activityPerson")) {
                            ra.setActivityPerson(XmlTranslator.PersonReachFromXml(node4));
                        }
                        //Wipro
                          else if (nodeName2.equals("baseActivityCode") || nodeName2.equals("activityCode"))
                        //Wipro
                        {
                            ra.setActivityCode(XmlTranslator.EnumeratedTypeFromXml(node4, ActivityCode.class));
                        } else if (nodeName2.equals("#comment"))
                            continue;

                    }

                    raVec.addElement(ra);
                }
            }

        }

        RepairActivity[] raArray = (RepairActivity[]) raVec.toArray(new RepairActivity[0]);


        RepairActivityList raList = new RepairActivityListImpl();

        //use the appropriate  method based on the modifier...
        if (modifier == MultiValueList.SET) {
            raList.set(raArray);
        } else if (modifier == MultiValueList.ADD) {
            raList.add(raArray);
        } else if (modifier == MultiValueList.REMOVE) {
            raList.remove(raArray);
        } else
            raList.set(raArray);


        ttv.setRepairActivityList(raList);


    }


    public static void RepeatReportFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setRepeatReport(XmlTranslator.EnumeratedTypeFromXml(node, RepeatReport.class));
    }

    public static void RestoredTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setRestoredTime(XmlTranslator.DateFromXml(node));
    }

    public static void ServiceUnavailableBeginTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setServiceUnavailableBeginTime(XmlTranslator.DateFromXml(node));
    }

    public static void ServiceUnavailableEndTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setServiceUnavailableEndTime(XmlTranslator.DateFromXml(node));
    }

    public static void SPRoleAssignmentListFromXml(Node node, TroubleTicketValue ttv) {


        java.util.Vector spraVec = new Vector();

        // Get all the immediate children of the  node - of type "Item"
        NodeList nodeList = node.getChildNodes();
        Node node2;
        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            // Now iterate over the contents of each item node
            NodeList nodeList2 = node2.getChildNodes();
            Node node3;

            SPRoleAssignment spra = new SPRoleAssignmentImpl();

            for (int y = 0; y < nodeList2.getLength(); y++) {
                node3 = nodeList2.item(y);
                if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                //WS String nodeName = node3.getNodeName();
                String nodeName = node3.getLocalName();
                //wipro

                if (nodeName.equals("baseRole") || nodeName.equals("role"))
                //Wipro
                {
                    if (node3.hasChildNodes())
                        spra.setRole(node3.getFirstChild().getNodeValue());
                } else if (nodeName.equals("contactPerson")) {
                    spra.setContactPerson(XmlTranslator.PersonReachFromXml(node3));
                } else if (nodeName.equals("#comment"))
                    continue;
            }
            spraVec.addElement(spra);
        }

        SPRoleAssignment[] raArray = (SPRoleAssignment[]) spraVec.toArray(new SPRoleAssignment[0]);
        ttv.setSPRoleAssignmentList(raArray);

    }


    public static void SuspectObjectListFromXml(Node node, TroubleTicketValue ttv) {
        java.util.Vector solVec = new Vector();

        // Get all the immediate children of the  node - of type "Item"
        NodeList nodeList = node.getChildNodes();
        Node node2;
        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            // Now iterate over the contents of each item node
            NodeList nodeList2 = node2.getChildNodes();
            Node node3;

            SuspectObject so = new SuspectObjectImpl();

            for (int y = 0; y < nodeList2.getLength(); y++) {
                node3 = nodeList2.item(y);
                if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                //WS String nodeName = node3.getNodeName();
                String nodeName = node3.getLocalName();

                if (nodeName.equals("suspectObjectType")) {
                    if (node3.hasChildNodes())
                        so.setSuspectObjectType(node3.getFirstChild().getNodeValue());

                } else if (nodeName.equals("suspectObjectId")) {
                    if (node3.hasChildNodes()) {
                        so.setSuspectObjectId(node3.getFirstChild().getNodeValue());
                    }

                } else if (nodeName.equals("failureProbability")) {
                    Integer fp;
                    if (node3.hasChildNodes()) {
                        fp = new Integer(node3.getFirstChild().getNodeValue());
                        so.setFailureProbability(fp.intValue());
                    }
                } else if (nodeName.equals("#comment"))
                    continue;

            }
            solVec.addElement(so);
        }

        SuspectObject[] soArray = (SuspectObject[]) solVec.toArray(new SuspectObject[0]);
        ttv.setSuspectObjectList(soArray);

    }


    public static void TroubleDescriptionFromXml(Node node, TroubleTicketValue ttv) {
        if (node.hasChildNodes())
            ttv.setTroubleDescription(node.getFirstChild().getNodeValue());
    }


    public static void TroubleFoundFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setTroubleFound(XmlTranslator.EnumeratedTypeFromXml(node, TroubleFound.class));
    }


    public static void TroubleLocationFromXml(Node node, TroubleTicketValue ttv) {
        if (node.hasChildNodes())
            ttv.setTroubleLocation(node.getFirstChild().getNodeValue());
    }


    public static void TroubleNumListFromXml(Node node, TroubleTicketValue ttv) {

        java.util.Vector tnlVec = new java.util.Vector();

        // Get all the immediate children of the curr Node.
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            if (node2.hasChildNodes())
                tnlVec.addElement(node2.getFirstChild().getNodeValue());
        }

        String[] tnlArray = (String[]) tnlVec.toArray(new String[0]);
        ttv.setTroubleNumList(tnlArray);

    }

    public static void TroubledObjectFromXml(Node node, TroubleTicketValue ttv) {

        if (node.hasChildNodes())
            ttv.setTroubledObject(node.getFirstChild().getNodeValue());
    }

    public static void TroubleTypeFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setTroubleType(XmlTranslator.EnumeratedTypeFromXml(node, TroubleType.class));
    }


    public static void TroubleStateFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setTroubleState(XmlTranslator.EnumeratedTypeFromXml(node, TroubleState.class));
    }


    public static void TroubleStatusFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setTroubleStatus(XmlTranslator.EnumeratedTypeFromXml(node, TroubleStatus.class));
    }


    public static void TroubleStatusTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setTroubleStatusTime(XmlTranslator.DateFromXml(node));
    }


    public static void PerceivedTroubleSeverityFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setPerceivedTroubleSeverity(XmlTranslator.EnumeratedTypeFromXml(node, PerceivedTroubleSeverity.class));
    }


    public static void PreferredPriorityFromXml(Node node, TroubleTicketValue ttv) {
        //enum
        ttv.setPreferredPriority(XmlTranslator.EnumeratedTypeFromXml(node, PreferredPriority.class));
    }


    public static void TroubleDetectionTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setTroubleDetectionTime(XmlTranslator.DateFromXml(node));
    }


    public static void TroubleLocationInfoListFromXml(Node node, TroubleTicketValue ttv) {

        Logger.log("-----TroubleLocationInfoListFromXml----");

        java.util.Vector tliVec = new Vector();

        // Get all the immediate children of the escalation node - of type "Item"
        NodeList nodeList = node.getChildNodes();
        Node node2;
        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            //Logger.log ( "Node name= " + node2.getNodeName());
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

            // Now iterate over the contents of each item node
            NodeList nodeList2 = node2.getChildNodes();
            Node node3;

            TroubleLocationInfo tli = new TroubleLocationInfoImpl();

            for (int y = 0; y < nodeList.getLength(); y++) {
                node3 = nodeList2.item(y);
                //Logger.log ( "Node name= " + node3.getNodeName());
                if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                //WS String nodeName = node3.getNodeName();
                String nodeName = node3.getLocalName();
                //Logger.log ( "After Node name= " + node3.getNodeName());
                if (nodeName.equals("premiseAddress")) {
                    tli.setPremiseAddress(XmlTranslator.AddressFromXml(node3));
                } else if (nodeName.equals("premiseName")) {
                    if (node3.hasChildNodes())
                        tli.setPremiseName(node3.getFirstChild().getNodeValue());
                } else if (nodeName.equals("objectIdDN")) {
                    if (node3.hasChildNodes())
                        tli.setObjectIdDN(node3.getFirstChild().getNodeValue());
                } else if (nodeName.equals("locationPerson")) {
                    tli.setLocationPerson(XmlTranslator.PersonReachFromXml(node3));
                } else if (nodeName.equals("accessHoursList")) {

                    java.util.Vector acVec = new java.util.Vector();
                    NodeList accHrsNodeList = node3.getChildNodes();
                    Node node4;
                    for (int z = 0; z < accHrsNodeList.getLength(); z++) {
                        node4 = accHrsNodeList.item(z);
                        if (node4.getNodeType() != Node.ELEMENT_NODE) continue;
                        //String accHrChildNodeName = accHrsChildNode.getNodeName();
                        acVec.addElement(XmlTranslator.AccessHoursFromXml(node4));
                    }

                    tli.setAccessHoursList((AccessHours[]) acVec.toArray(new AccessHours[0]));
                } else if (nodeName.equals("#comment"))
                    continue;
            }
            tliVec.addElement(tli);

        }

        TroubleLocationInfo[] tliArray = (TroubleLocationInfo[]) tliVec.toArray(new TroubleLocationInfo[0]);
        ttv.setTroubleLocationInfoList(tliArray);

    }


    /* Keep this for example of old code
        // Since this element represents an array in the Trouble Ticket System,
        // it is expected to contain a number of elements inside this XML doc
        java.util.Vector trlocationInfoVec = new java.util.Vector();
        AccessHours[] accHrsArray = null;

        // As a first step, get all the immediate children whose tag is "Item"
        NodeList nodeList = ttChildNode.getChildNodes();
        Node node2;

        for( int q=0; q < nodeList.getLength(); q++ )
        {
            node2 = nodeList.item(q);

            // Create a local vector to look after the accessHours array
            java.util.Vector accessHrsVec = new java.util.Vector();

            String address = null;
            String locationPerson = null;
            AccessHours accHours = null;
            NodeList trInfoChildNodeList = node2.getChildNodes();
            Node trInfoChildNode;

            for (int t=0; t<trInfoChildNodeList.getLength(); t++)
            {
                trInfoChildNode = trInfoChildNodeList.item(t);
                String trInfoChildNodeName = trInfoChildNode.getNodeName();

                if ( trInfoChildNodeName.equals("tt:Address") )
                {
                    address = (trInfoChildNode.getFirstChild()).getNodeValue();
                }
                else if ( trInfoChildNodeName.equals("tt:LocationPerson") )
                {
                    locationPerson = (trInfoChildNode.getFirstChild()).getNodeValue();
                }
                else if ( trInfoChildNodeName.equals("tt:AccessHours") )
                {
                    // This is an array, i.e. there could be multiple accessHours
                    // elements in each troublelocationInfo element
                    java.util.Date startTime = null;
                    java.util.Date endTime = null;
                    NodeList accHrsChildNodeList = trInfoChildNode.getChildNodes();
                    Node accHrsChildNode;
                    AccessHours accHoursObj = null;
                    for (int ac=0; ac < accHrsChildNodeList.getLength(); ac++)
                    {
                        accHrsChildNode = accHrsChildNodeList.item(ac);
                        String accHrChildNodeName = accHrsChildNode.getNodeName();

                        String accHrChildNodeValue = (accHrsChildNode.getFirstChild()).getNodeValue();
                        //"2001-02-18T14:01:00Z"
                        StringTokenizer st = new StringTokenizer( accHrChildNodeValue, "T" );
                        String year;
                        String month;
                        String day;
                        String hour;
                        String min;
                        String sec;

                        String dateToken = st.nextToken();
                        String timeToken = st.nextToken();

                        StringTokenizer st2 = new StringTokenizer( dateToken, "-");
                        year = st2.nextToken();
                        month = st2.nextToken();
                        day = st2.nextToken();
                        //L

                        StringTokenizer st3 = new StringTokenizer( timeToken, ":");

                        hour = st3.nextToken();
                        min = st3.nextToken();
                        sec = (st3.nextToken()).substring(0,2);


                        //Logger.log ("year: " + year);
                        //Logger.log ("month: " + month);
                        //Logger.log ("day: " + day);
                        //Logger.log ("hour: " + hour);
                        //Logger.log ("min: " + min);
                        //Logger.log ("sec: " + sec);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Integer.parseInt(year),
                                Integer.parseInt(month ) - 1,
                                Integer.parseInt(day),
                                Integer.parseInt(hour),
                                Integer.parseInt(min),
                                Integer.parseInt(sec) );

                        if ( accHrChildNodeName.equals( "tt:StartTime") )
                        {
                            startTime = date = cal.getTime();
                        }
                        else
                        {
                            endTime = date = cal.getTime();
                        }
                        //public AccessHours              makeAccessHours()             { return new AccessHoursImpl(); }
                        //accHrsObj = new AccessHoursImpl ( startTime, endTime );
                        accHoursObj = ttv.makeAccessHours();
                        //MR1 accHoursObj.setStartTime(startTime);
                        //MR1 accHoursObj.setEndTime(endTime);
                    }

                    accessHrsVec.addElement( accHoursObj );
                }
                else if ( trInfoChildNodeName.equals("#comment" ) )
                {
                    continue;
                }
                accHrsArray = (AccessHours[])accessHrsVec.toArray( new AccessHours[0] );

            }   // for the Item children
            TroubleLocationInfo trlocationInfoObj = ttv.makeTroubleLocationInfo();
            //MR1 trlocationInfoObj.setPremiseAddress(address);
            //MR1 trlocationInfoObj.setLocationPerson(locationPerson);
            //MR1 trlocationInfoObj.setAccessHours(accHrsArray);
            //TroubleLocationInfo trlocationInfoObj = new TroubleLocationInfoImpl ( address, locationPerson, accHrsArray );
            trlocationInfoVec.addElement( trlocationInfoObj );

        }   // for loop for next item

        // convert the trlocationVec to array here
        TroubleLocationInfo[] trLocationInfoArray = (TroubleLocationInfo[])trlocationInfoVec.toArray(new TroubleLocationInfo[0]);
        // set the attr value
        ttv.setTroubleLocationInfoList( trLocationInfoArray );
        */


    public static void TroubledObjectAccessFromTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setTroubledObjectAccessFromTime(XmlTranslator.DateFromXml(node));
    }

    public static void TroubledObjectAccessHoursListFromXml(Node node, TroubleTicketValue ttv) {

        java.util.Vector toahVec = new java.util.Vector();
        NodeList nodeList = node.getChildNodes();
        for (int x = 0; x < nodeList.getLength(); x++) {
            node = nodeList.item(x);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            toahVec.addElement(XmlTranslator.AccessHoursFromXml(node));
        }

        ttv.setTroubledObjectAccessHoursList((AccessHours[]) toahVec.toArray(new AccessHours[0]));
    }

    public static void TroubledObjectAccessToTimeFromXml(Node node, TroubleTicketValue ttv) {
        //Date
        ttv.setTroubledObjectAccessToTime(XmlTranslator.DateFromXml(node));
    }


    public static void TroubleSystemDNFromXml(Node node, TroubleTicketValue ttv) {
        if (node.hasChildNodes())
            ttv.setTroubleSystemDN((node.getFirstChild()).getNodeValue());
    }


    //----------------------------------------------------------------------------
    //
    //                  "From XML" for sub-types
    //
    //----------------------------------------------------------------------------
    public static AccessHours AccessHoursFromXml(Node node) {

        AccessHours ah = new AccessHoursImpl();
        //go thru node list
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            if (nodeName.equals("dayOfWeek"))
                ah.setDayOfWeek(XmlTranslator.EnumeratedTypeFromXml(node2, DayOfWeek.class));

            else if (nodeName.equals("timeIntervalList")) {
                NodeList nodeList2 = node2.getChildNodes();
                java.util.Vector tiVect = new java.util.Vector();
                Node node22;
                for (int y = 0; y < nodeList2.getLength(); y++) {
                    node22 = nodeList2.item(y);
                    if (node22.getNodeType() != Node.ELEMENT_NODE) continue;
                    tiVect.addElement(XmlTranslator.TimeIntervalFromXml(node22));
                }

                ah.setTimeIntervalList((TimeInterval[]) tiVect.toArray(new TimeInterval[0]));
            }
        }

        return ah;

    }

    public static ActivityDuration ActivityDurationFromXml(Node node) {
        Logger.log("---ActivityDurationFromXml---");
        Logger.log("---Node Name--->" + node.getNodeName());
        ActivityDuration ad = new ActivityDurationImpl();

        //go thru node list
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS  String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            Logger.log("---Node Name--->" + nodeName);
            Logger.log("---Node Type--->" + node2.getNodeType());
            /*Logger.log ( "---Node Type--->" + Node.ELEMENT_NODE );
            Logger.log ( "---Node Type--->" + Node.ATTRIBUTE_NODE );
            Logger.log ( "---Node Type--->" + Node.TEXT_NODE );
            Logger.log ( "---Node Type--->" + Node.CDATA_SECTION_NODE );
            Logger.log ( "---Node Type--->" + Node.ENTITY_REFERENCE_NODE );
            Logger.log ( "---Node Type--->" + Node.ENTITY_NODE );
            Logger.log ( "---Node Type--->" + Node.PROCESSING_INSTRUCTION_NODE );
            Logger.log ( "---Node Type--->" + Node.COMMENT_NODE );
            Logger.log ( "---Node Type--->" + Node.DOCUMENT_NODE );
            Logger.log ( "---Node Type--->" + Node.DOCUMENT_TYPE_NODE );*/
            if (nodeName.equals("duration"))
                ad.setDuration(XmlTranslator.TimeLengthFromXml(node2));

            else if (nodeName.equals("billable"))
                ad.setBillable(XmlTranslator.BooleanFromXml(node2));
            //Wipro
              else if (nodeName.equals("baseActivityType") || nodeName.equals("activityType"))
            //Wipro
                ad.setActivityType(XmlTranslator.EnumeratedTypeFromXml(node2, ActivityType.class));

            else if (nodeName.equals("#comment"))
                continue;

        }

        return ad;

    }


    //-----------------------------------------------------------------------------
    // Maps the enumerated value (the string XML value) to its int equivalent
    // using the interface reflector
    //-----------------------------------------------------------------------------
    public static int EnumeratedTypeFromXml(Node node,
                                            Class interfaceClass) {

        Integer enumVal = null;

        //get string enum name from node, use reflector to transform to int
        try {
            String enumString;
            if (node.hasChildNodes()) {
                enumString = node.getFirstChild().getNodeValue();
                enumVal = (Integer) (ttIntReflector.getInterfaceFieldValue(interfaceClass, enumString));
            } else
                enumVal = new Integer(-1);      //-1 means uninitialized

        } catch (javax.oss.IllegalArgumentException iLLEx) {
            //throw new org.xml.sax.SAXException( iLLEx.getMessage() );
            Logger.logException(iLLEx);

        } catch (java.lang.IllegalAccessException iLLAccEx) {
            //throw new org.xml.sax.SAXException( iLLAccEx.getMessage() );
            Logger.logException(iLLAccEx);

        } catch (java.lang.NoSuchFieldException noSuchFldEx) {
            //throw new org.xml.sax.SAXException( noScuhFldEx.getMessage() );
            Logger.logException(noSuchFldEx);

        }

        return enumVal.intValue();

    }


    public static Authorization AuthorizationFromXml(Node node) {

        Authorization auth = new AuthorizationImpl();

        //go thru node list
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            if (nodeName.equals("requestState"))
                auth.setRequestState(XmlTranslator.EnumeratedTypeFromXml(node2, RequestState.class));
            //Wipro
                 else if (nodeName.equals("baseActivityType") || nodeName.equals("activityType"))
            //Wipro
                auth.setActivityType(XmlTranslator.EnumeratedTypeFromXml(node2, ActivityType.class));

            else if (nodeName.equals("authTime"))
                auth.setAuthTime(XmlTranslator.DateFromXml(node2));

            else if (nodeName.equals("authPerson"))
                auth.setAuthPerson(XmlTranslator.PersonReachFromXml(node2));

            else if (nodeName.equals("#comment"))
                continue;

        }

        return auth;


    }


    public static PersonReach PersonReachFromXml(Node node) {

        PersonReach pr = new PersonReachImpl();

        //go thru node list
        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            if (nodeName.equals("email")) {
                if (node2.hasChildNodes())
                    pr.setEmail(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("organizationName")) {
                if (node2.hasChildNodes())
                    pr.setOrganizationName(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("fax")) {
                if (node2.hasChildNodes())
                    pr.setFax(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("location")) {
                pr.setLocation(XmlTranslator.AddressFromXml(node2));
            } else if (nodeName.equals("name")) {
                if (node2.hasChildNodes())
                    pr.setName(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("number")) {
                if (node2.hasChildNodes())
                    pr.setNumber(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("phone")) {
                if (node2.hasChildNodes())
                    pr.setPhone(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("responsible")) {
                if (node2.hasChildNodes())
                    pr.setResponsible(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("sMSAddress")) {
                if (node2.hasChildNodes())
                    pr.setSMSAddress(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("#comment"))
                continue;

        }

        return pr;

    }

    public static Address AddressFromXml(Node node) {

		javax.oss.trouble.Address addr = null;
		String xsiType = getXsiTypeFrom(node);
        if ( "tt:NorthAmericaAddress".equals(xsiType) ) {
			addr = new NorthAmericaAddressImpl();
		} else {
			addr = new AddressImpl();
		}

        NodeList nodeList = node.getChildNodes();
        Node node2;

		for (int x = 0; x < nodeList.getLength(); x++) {
			node2 = nodeList.item(x);
			if (node2.getNodeType() != Node.ELEMENT_NODE) continue;

			String nodeName = node2.getLocalName();
			if (nodeName.equals("addressInfo")) {
				if (node2.hasChildNodes())
					addr.setAddressInfo(node2.getFirstChild().getNodeValue());
			} else if ( addr instanceof NorthAmericaAddress ) {
				NorthAmericaAddress naAddr = (NorthAmericaAddress) addr;

				if ( nodeName.equals("civicAddress"))
				{
					if (node2.hasChildNodes())
						naAddr.setCivicAddress(node2.getFirstChild().getNodeValue());
				}
				else if ( nodeName.equals("city"))
				{
					if (node2.hasChildNodes())
						naAddr.setCity(node2.getFirstChild().getNodeValue());
				}
				else if ( nodeName.equals("state"))
				{
					if (node2.hasChildNodes())
						naAddr.setState(node2.getFirstChild().getNodeValue());
				}
				else if ( nodeName.equals("zip"))
				{
					if (node2.hasChildNodes())
						naAddr.setZip(node2.getFirstChild().getNodeValue());
				}
			} else if (nodeName.equals("#comment"))
                continue;

        }

        return addr;

    }


	public static String getXsiTypeFrom(Node node) {
		if ( node.hasAttributes() ) {
			NamedNodeMap nodeMap = node.getAttributes();
			Attr attr = (Attr) nodeMap.getNamedItemNS(NS_URI_XSI, "type");
			return attr.getNodeValue();
		}

		return null;
	}

    public static boolean BooleanFromXml(Node node) {

        String val;
        if (node.hasChildNodes()) {
            val = node.getFirstChild().getNodeValue();
            if (val.equals("true"))
                return true;
        }
        return false;   //default is false

    }

    public static TimeLength TimeLengthFromXml(Node node) {
        Logger.log("+++++TimeLengthFromXml++++");
        TimeLength tl = new TimeLengthImpl();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        Integer anInt = null;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            Logger.log("node name: " + nodeName);

            if (nodeName.equals("years")) {
                if (node2.hasChildNodes()) {
                    Logger.log("tt:years: " + node2.getFirstChild().getNodeValue());
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setYears(anInt.shortValue());
                }
            } else if (nodeName.equals("months")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setMonths(anInt.shortValue());
                }
            } else if (nodeName.equals("days")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setDays(anInt.shortValue());
                }
            } else if (nodeName.equals("hours")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setHours(anInt.shortValue());
                }
            } else if (nodeName.equals("minutes")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setMinutes(anInt.shortValue());
                    ;
                }
            } else if (nodeName.equals("seconds")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setSeconds(anInt.shortValue());
                }
            } else if (nodeName.equals("msecs")) {
                if (node2.hasChildNodes()) {
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                    tl.setMsecs(anInt.shortValue());
                }
            } else if (nodeName.equals("#comment"))
                continue;

        }

        return tl;

    }

    public static javax.oss.trouble.Time TimeFromXml(Node node) {

        javax.oss.trouble.Time time = new TimeImpl();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        Integer anInt = null;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            if (nodeName.equals("hours")) {
                if (node2.hasChildNodes())
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                time.setHour(anInt.shortValue());
            } else if (nodeName.equals("minutes")) {
                if (node2.hasChildNodes())
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                time.setMinute(anInt.shortValue());
                ;
            } else if (nodeName.equals("seconds")) {
                if (node2.hasChildNodes())
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                time.setSeconds(anInt.shortValue());
            } else if (nodeName.equals("msecs")) {
                if (node2.hasChildNodes())
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                time.setMilliSeconds(anInt.shortValue());
            } else if (nodeName.equals("utcOffset")) {
                if (node2.hasChildNodes())
                    anInt = new Integer(node2.getFirstChild().getNodeValue());
                time.setUtcOffset(anInt.shortValue());
            } else if (nodeName.equals("#comment"))
                continue;

        }

        return time;

    }


    public static TimeInterval TimeIntervalFromXml(Node node) {

        TimeInterval ti = null;
        if (!(node.hasChildNodes()))      //is it nilled?
            return ti;


        ti = new TimeIntervalImpl();

        NodeList nodeList = node.getChildNodes();
        Node node2;


        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();

            if (nodeName.equals("intervalBegin"))
                ti.setIntervalBegin(XmlTranslator.TimeFromXml(node2));
            else if (nodeName.equals("intervalEnd"))
                ti.setIntervalEnd(XmlTranslator.TimeFromXml(node2));
            else if (nodeName.equals("#comment"))
                continue;
        }

        return ti;

    }


    //-----------------------------------------------------------------------------------
    // For now, use DateFromXml.  A future version may use a different idiom
    // since java.util.Date is being deprecated.
    //-----------------------------------------------------------------------------------
    public static java.util.Date DateFromXml(Node node) {
		SimpleDateFormat[] formats = { new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ"),
									   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"),
									   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S"),
									   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")};

        //1999-05-31T13:20:00-05:00.
        java.util.Date date = null;

        if (!(node.hasChildNodes()))      //is it nilled?
            return date;

        String childNodeValue = node.getFirstChild().getNodeValue();

		// The xml time zone format does not match the time zone format provided by the simple date
		// formatter. So we have to translate it to simple date formatter compatible code
		if ( childNodeValue.endsWith("Z") ) {
			// If the xml dateTime type may contain a 'Z' as time zone specifier
			// if present we will replace this with the equivalent time zone '+0000'
			childNodeValue = childNodeValue.substring(0, childNodeValue.length()-1) + "+0000";
		} else {
			// XML: +01:00
			// Java: +0100
			char c = childNodeValue.charAt(childNodeValue.length()-6);
			if ( c == '-' || c == '+') {
				int length = childNodeValue.length();
				childNodeValue = childNodeValue.substring(0, length-3) + childNodeValue.substring(length-2, length);
			}
		}

		for (int i = 0; i < formats.length; i++) {
			SimpleDateFormat format = formats[i];
			try {
				date = format.parse(childNodeValue);
				break;
			} catch (ParseException e) {
				// simply ignore and try next...
			}
		}

		if ( date == null ) throw new RuntimeException("Can't parse date '"+childNodeValue + "' source='" +
													   node.getFirstChild().getNodeValue() + "'");

        Logger.log("DateFromXml: GMT Date : " + date.toGMTString() + " date = " + date.toString() + " millisec = " + date.getTime());

        return date;
    }

    public static TroubleTicketKey TroubleTicketKeyFromXml(Node node) {

        //TroubleTicketKey ttKeyObj = new TroubleTicketKeyImpl();
        TroubleTicketKey ttKey = new TroubleTicketKeyImpl();

        NodeList nodeList = node.getChildNodes();
        Node node2;

        for (int x = 0; x < nodeList.getLength(); x++) {
            node2 = nodeList.item(x);
            if (node2.getNodeType() != Node.ELEMENT_NODE) continue;
            //WS String nodeName = node2.getNodeName();
            String nodeName = node2.getLocalName();
            Logger.log("&&&&&nodeName: &&&&&" + nodeName);

            if (nodeName.equals("applicationContext")) {

                // Get all the elements contained by the applicationContext
                NodeList nodeList2 = node2.getChildNodes();
                Node node3;

                ApplicationContext apc = ttKey.makeApplicationContext();
                for (int y = 0; y < nodeList2.getLength(); y++) {
                    node3 = nodeList2.item(y);
                    if (node3.getNodeType() != Node.ELEMENT_NODE) continue;
                    //WS String nodeName2 = node3.getNodeName();
                    String nodeName2 = node3.getLocalName();
                    Logger.log("nodeName: " + nodeName2);
                    if (nodeName2.equals("factoryClass")) {
                        apc.setFactoryClass(node3.getFirstChild().getNodeValue());
                    } else if (nodeName2.equals("url")) {

                        apc.setURL(node3.getFirstChild().getNodeValue());
                    }
                    /*else if ( nodeName2.equals("co:systemProperties") )
                       {
                        apc.setSystemProperties ();
                       }
                    */
                    else if (nodeName2.equals("#comment")) {
                        continue;
                    }
                }
                ttKey.setApplicationContext(apc);

            } else if (nodeName.equals("primaryKey")) {
                ttKey.setPrimaryKey(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("type")) {
                ttKey.setType(node2.getFirstChild().getNodeValue());
            }
            if (nodeName.equals("applicationDN")) {
                ttKey.setApplicationDN(node2.getFirstChild().getNodeValue());
            } else if (nodeName.equals("#comment")) {
                continue;
            }
        }


        return ttKey;
    }


    //--------------------------------------------------------------------
    //
    //                            UTILS
    //
    //--------------------------------------------------------------------

    public String toXml(String elementName) {
        return new String();
    }

    public String getXmlHeader() {
        return new String();
    }


    public static String toXml(Object object, String elementName)
            throws IllegalArgumentException {
        String doc = null;
        try {
            if (object instanceof TroubleTicketValue) {
                doc = toXml((TroubleTicketValue) object, elementName);
            } else if (object instanceof TroubleTicketKey) {
                Logger.log("---toXml( (TroubleTicketKey) object, elementName)---");
                doc = toXml((TroubleTicketKey) object, elementName);
            } else if (object instanceof TroubleTicketCreateEvent) {
                doc = toXml((TroubleTicketCreateEvent) object, elementName);
            } else if (object instanceof TroubleTicketCloseOutEvent) {
                doc = toXml((TroubleTicketCloseOutEvent) object, elementName);
            } else if (object instanceof TroubleTicketCancellationEvent) {
                doc = toXml((TroubleTicketCancellationEvent) object, elementName);
            } else if (object instanceof TroubleTicketAttributeValueChangeEvent) {
                doc = toXml((TroubleTicketAttributeValueChangeEvent) object, elementName);
            } else if (object instanceof TroubleTicketStatusChangeEvent) {
                doc = toXml((TroubleTicketStatusChangeEvent) object, elementName);
            } else if (object instanceof QueryAllOpenTroubleTicketsValue) {
                doc = toXml((QueryAllOpenTroubleTicketsValue) object, elementName);
            } else if (object instanceof QueryByEscalationValue) {
                doc = toXml((QueryByEscalationValue) object, elementName);
            }
            return doc;
        } catch (org.xml.sax.SAXException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    public static Object fromXml(Element element, String type)
            throws IllegalArgumentException {
        try {
            if (type.equals(TroubleTicketValue.class.getName())) {
                TroubleTicketValue ttVal = new TroubleTicketValueImpl();
                fromXmlNoRoot(ttVal, element);
                return ttVal;
            } else if (type.equals(TroubleTicketKey.class.getName())) {
                Logger.log("--fromXml on TroubleTicketKey---");
                //TroubleTicketKey ttKey = new TroubleTicketKeyImpl();
                //fromXmlNoRoot( ttKey , element );
                TroubleTicketKey ttKey = TroubleTicketKeyFromXml(element);
                return ttKey;
            } else if (type.equals(TroubleTicketCreateEvent.class.getName())) {
                //TroubleTicketCreateEvent  event = new TroubleTicketCreateEventImpl();
                //fromXmlNoRoot( event, element );
                TroubleTicketCreateEvent event = troubleTicketCreateEventFromXml(element);
                return event;
            } else if (type.equals(TroubleTicketCloseOutEvent.class.getName())) {
                //TroubleTicketCloseOutEvent event = new TroubleTicketCloseOutEventImpl();
                //fromXmlNoRoot( event, element );
                TroubleTicketCloseOutEvent event = troubleTicketCloseOutEventFromXml(element);
                return event;
            } else if (type.equals(TroubleTicketCancellationEvent.class.getName())) {
                //TroubleTicketCancellationEvent event = new TroubleTicketCancellationEventImpl();
                //fromXmlNoRoot( event, element );
                TroubleTicketCancellationEvent event = troubleTicketCancellationEventFromXml(element);
                return event;
            } else if (type.equals(TroubleTicketAttributeValueChangeEvent.class.getName())) {
                //TroubleTicketAttributeValueChangeEvent  event = new TroubleTicketAttributeValueChangeEventImpl();
                //fromXmlNoRoot(event, element );
                TroubleTicketAttributeValueChangeEvent event = troubleTicketAttributeValueChangeEventFromXml(element);
                return event;
            } else if (type.equals(TroubleTicketStatusChangeEvent.class.getName())) {
                //TroubleTicketStatusChangeEvent  event = new TroubleTicketStatusChangeEventImpl();
                //fromXmlNoRoot( event, element );
                TroubleTicketStatusChangeEvent event = troubleTicketStatusChangeEventFromXml(element);
                return event;
            } else if (type.equals(QueryAllOpenTroubleTicketsValue.class.getName())) {
                QueryAllOpenTroubleTicketsValue query = new QueryAllOpenTroubleTicketsImpl();
                fromXmlNoRoot(query, element);
                return query;
            }

        }
                /*-->PG TO DO<---  else if( type.equals(QueryByEscalationValue.class.getName() )){
                     QueryByEscalationValue query = new QueryByEscalationValueImpl();
                     fromXmlNoRoot( query, element );
                     return query;
                 }
                 */ catch (org.xml.sax.SAXException ex) {
            return new IllegalArgumentException(ex.getMessage());
        }
        return null;
    }

    public static String troubleTicketAttributeValueChangeEventtoXml(
            TroubleTicketAttributeValueChangeEvent event, String tag)
            throws org.xml.sax.SAXException {
        TroubleTicketValue ttValue = event.getTroubleTicketValue();
        String[] populatedAttr = ttValue.getPopulatedAttributeNames();
        Logger.log("TTCreateEventImpl:toXml - popAttrs: " + populatedAttr.length);
        ((TroubleTicketValueImpl) ttValue).setDesiredAttributes(populatedAttr, false);
        // As a first step convert to ttValue into an xml string format.
        String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();
        String eventType = TroubleTicketAttributeValueChangeEvent.class.getName();
        return addXmlRootElement(event, tag, xmlTTValue, eventType);
    }

    public static String troubleTicketCancellationEventtoXml(
            TroubleTicketCancellationEvent event, String tag)
            throws org.xml.sax.SAXException {
        StringBuffer strBuffer = new StringBuffer();
        //TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();

        TroubleTicketKey trKey = event.getTroubleTicketKey();
        String xmlKey = ((TroubleTicketKeyImpl) trKey).toXml();
        strBuffer.append(xmlKey);
        //---------------------------------

        strBuffer.append("<tt:closeOutNarr>");
        strBuffer.append(event.getCloseOutNarr());
        strBuffer.append("</tt:closeOutNarr>\n");

        String eventType = TroubleTicketCancellationEvent.class.getName();
        return addXmlRootElement(event, tag, strBuffer.toString(), eventType);
    }


    public static String troubleTicketCloseOutEventtoXml(
            TroubleTicketCloseOutEvent event, String tag)
            throws org.xml.sax.SAXException {
        TroubleTicketValue ttValue = event.getTroubleTicketValue();
        String[] populatedAttr = ((TroubleTicketValueImpl) ttValue).getPopulatedAttributeNames();
        ((TroubleTicketValueImpl) ttValue).setDesiredAttributes(populatedAttr, false);

        //translate the contained ttValue into an xml string format.
        String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();

        String eventType = TroubleTicketCloseOutEvent.class.getName();
        return addXmlRootElement(event, tag, xmlTTValue, eventType);
    }

    public static String troubleTicketCreateEventtoXml(
            TroubleTicketCreateEvent event, String tag)
            throws org.xml.sax.SAXException {
        TroubleTicketValue ttValue = event.getTroubleTicketValue();
        String[] populatedAttr = ((TroubleTicketValueImpl) ttValue).getPopulatedAttributeNames();
        Logger.log("TTCreateEventImpl:toXml - popAttrs: " + populatedAttr.length);
        ((TroubleTicketValueImpl) ttValue).setDesiredAttributes(populatedAttr, false);
        // As a first step convert to ttValue into an xml string format.
        String xmlTTValue = ((TroubleTicketValueImpl) ttValue).toXml();

        String eventType = TroubleTicketCreateEvent.class.getName();
        return addXmlRootElement(event, tag, xmlTTValue, eventType);
    }

    public static String troubleTicketStatusChangeEventtoXml(
            TroubleTicketStatusChangeEvent event, String tag)
            throws org.xml.sax.SAXException {
        StringBuffer strBuffer = new StringBuffer();
        TroubleTicketInterfaceReflector ttIntReflector = new TroubleTicketInterfaceReflector();

        TroubleTicketKey trKey = event.getTroubleTicketKey();
        String xmlKey = ((TroubleTicketKeyImpl) trKey).toXml();
        strBuffer.append(xmlKey);
        //---------------------------------
        String stateName = null;
        if (event.getState() != -1) {
            try {
                stateName = (String) (ttIntReflector.getInterfaceFieldName(TroubleState.class, new Integer(event.getState())));
            } catch (javax.oss.IllegalArgumentException iLLEx) {
                throw new org.xml.sax.SAXException(iLLEx.getMessage());
            } catch (java.lang.IllegalAccessException iLLAccEx) {
                throw new org.xml.sax.SAXException(iLLAccEx.getMessage());
            }
        }

        strBuffer.append("<tt:troubleState>");
        strBuffer.append( stateName );
        strBuffer.append("</tt:troubleState>\n");


        //----------------------------------

        String statusName = null;
        if (event.getStatus() != -1) {
            try {
                statusName = (String) (ttIntReflector.getInterfaceFieldName(TroubleStatus.class, new Integer(event.getStatus())));
            } catch (javax.oss.IllegalArgumentException iLLEx) {
                throw new org.xml.sax.SAXException(iLLEx.getMessage());
            } catch (java.lang.IllegalAccessException iLLAccEx) {
                throw new org.xml.sax.SAXException(iLLAccEx.getMessage());
            }
        }

        strBuffer.append("<tt:troubleStatus>");
        strBuffer.append( statusName );
        strBuffer.append("</tt:troubleStatus>\n");


        //----------------------------------

        java.util.Date statusDate = event.getStatusTime();
        strBuffer.append("<tt:statusTime>");
        if (event.getStatusTime() == null) {
            //strBuffer.append( (String)null );
            strBuffer.append("0000-00-00T00:00:00Z");

        } else {
            strBuffer.append(getXmlDate(event.getStatusTime()));
        }

        strBuffer.append("</tt:statusTime>\n");

        String eventType = TroubleTicketStatusChangeEvent.class.getName();
        return addXmlRootElement(event, tag, strBuffer.toString(), eventType);
    }


    public static String addXmlRootElement(javax.oss.Event event,
                                           String RootElementTag, String xmlEventCore, String eventType) {
        StringBuffer strBuffer = new StringBuffer();


        strBuffer.append("<" + RootElementTag + ">");

        strBuffer.append("<co:applicationDN>");
        strBuffer.append(event.getApplicationDN());
        strBuffer.append("</co:applicationDN>\n");

        strBuffer.append("<co:eventTime>");

        if (event.getEventTime() != null) {
            strBuffer.append(getXmlDate(event.getEventTime()));
        } else {
            strBuffer.append("0000-00-00T00:00:00Z");
        }
        strBuffer.append("</co:eventTime>\n");


        strBuffer.append(xmlEventCore);


        strBuffer.append("</" + RootElementTag + ">");

        return strBuffer.toString();
    }

    public static String getXmlDate(java.util.Date date) {
        //----------------------------------------------------------------
        // Convert from java Date format into XML timeInstant format.
        // Example date format:    7 Mar 2001 10:10:10 GMT
        // Equivalent xml format:  2001-03-07T10:10:10Z
        //----------------------------------------------------------------
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        String xmlStr = sdf.format(date);
        //Logger.log ("xmlStr: " + xmlStr );
        return xmlStr;
    }


}
