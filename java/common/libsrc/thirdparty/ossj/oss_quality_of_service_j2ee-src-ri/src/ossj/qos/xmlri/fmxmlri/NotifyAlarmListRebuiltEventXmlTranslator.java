package ossj.qos.xmlri.fmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.util.*;
import java.text.*;
import java.lang.StringBuffer;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class NotifyAlarmListRebuiltEventXmlTranslator
{

    public NotifyAlarmListRebuiltEventXmlTranslator()
    {

    }

    /**
    <NotifyAlarmListRebuiltEvent>
    <event>
		<applicationDN>
		<eventTime>
        	<managedObjectClass></managedObjectClass>
        	<managedObjectInstance></managedObjectClass>
        	<notificationId></notificationId>
		<reason></reason>
    </event>
    </NotifyAlarmListRebuiltEvent>

     */
    public static String toXml(NotifyAlarmListRebuiltEvent object,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (object == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    sb.append("<event>");
	    
	    String appDN = object.getApplicationDN();
	    String eventTime = object.getEventTime().toString();
	    String objClass = object.getManagedObjectClass();
	    String objInstance = object.getManagedObjectInstance();
	    String notficationId = object.getNotificationId();
	    String reason = object.getReason();
	    
	    sb.append("<applicationDN>"+appDN+"</applicationDN>");
	    sb.append("<eventTime>"+eventTime+"</eventTime>");
	    sb.append("<managedObjectClass>"+objClass+"</managedObjectClass>");
	    sb.append("<managedObjectInstance>"+objInstance+"</managedObjectInstance>");
	    sb.append("<notificationId>"+notficationId+"</notificationId>");
	    sb.append("<reason>"+reason+"</reason>");
	    sb.append("</event>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }




    public static Object fromXml( Element element, String type)
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyAlarmListRebuiltEvent.class.getName())) {
                NotifyAlarmListRebuiltEvent alval = null;
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alval);
                return alval;
                }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(Exception ex) 
	{
            return null;
        }

        return null;


    }

    public static void fromXmlNoRoot(org.jdom.Element element, NotifyAlarmListRebuiltEvent notifyAlarmEventaval)
    throws org.xml.sax.SAXException, ParseException
    {

	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())//A
	{
	    localElement = (org.jdom.Element)it.next();
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("event"))//C
	    {
		List list2 = element.getChildren();
		Iterator it2 = list2.iterator();
		String elementName2 = null;
		String elementValue2 = null;
		org.jdom.Element localElement2 = null;
		while(it2.hasNext())//B
		{
		    localElement2 = (org.jdom.Element)it2.next();
		    elementName2 = localElement2.getName();
		    elementValue2 = localElement2.getTextTrim();
		    if(elementName2.equalsIgnoreCase("applicationDN"))
		    {
			    notifyAlarmEventaval.setApplicationDN(elementValue2);
		    }
		    else if(elementName2.equalsIgnoreCase("eventTime"))
		    {
			    DateFormat df = DateFormat.getInstance();
			    Date evenTime = df.parse(elementValue2);
			    notifyAlarmEventaval.setEventTime(evenTime);
		    }
		    else if(elementName2.equalsIgnoreCase("managedObjectClass"))
		    {
			    notifyAlarmEventaval.setManagedObjectClass(elementValue2);
		    }
		    else if(elementName2.equalsIgnoreCase("managedObjectInstance"))
		    {
			    notifyAlarmEventaval.setManagedObjectInstance(elementValue2);
		    }
		    else if(elementName2.equalsIgnoreCase("notificationId"))
		    {
			    notifyAlarmEventaval.setNotificationId(elementValue2);
		    }
		    else if(elementName2.equalsIgnoreCase("reason"))
		    {
			    notifyAlarmEventaval.setReason(elementValue2);
		    }
		}//end B
	    }//end C
	}//end A
    }

}//end class
