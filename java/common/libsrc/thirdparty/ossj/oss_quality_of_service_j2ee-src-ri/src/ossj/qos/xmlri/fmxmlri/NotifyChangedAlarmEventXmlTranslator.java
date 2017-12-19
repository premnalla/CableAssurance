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
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.ManagedEntityValue;



import javax.oss.MultiValueList;

import javax.oss.fm.monitor.*;
import ossj.qos.fmri.*;

public class NotifyChangedAlarmEventXmlTranslator
{

    public NotifyChangedAlarmEventXmlTranslator()
    {

    }
/** 
 *  <NotifyChangedAlarmEvent>
 *  <event>
		<applicationDN>
		<eventTime>
        	<managedObjectClass></managedObjectClass>
        	<managedObjectInstance></managedObjectClass>
        	<notificationId><notificationId>
		<alarmType>	</alarmType>
		<alarmKey>	</alarmKey>
		<fm:basePerceivedAlarmSeverity></fm:basePerceivedAlarmSeverity>
		<fm:baseProbableAlarmCause></fm:baseProbableAlarmCause>
    </event>
    </NotifyChangedAlarmEvent>
 */
    public static String toXml(NotifyChangedAlarmEvent alarmEvent,String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (alarmEvent == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
    	    sb.append("<event>");
	    AlarmKey alarmKey = alarmEvent.getAlarmKey();
	    String alarmType = alarmEvent.getAlarmType();
	    String appDN = alarmEvent.getApplicationDN();
	    Date eventTime =  alarmEvent.getEventTime();
	    String strTime = eventTime.toString();
	    String objClass = alarmEvent.getManagedObjectClass();
	    String objInstance = alarmEvent.getManagedObjectInstance();
	    String notificationId =  alarmEvent.getNotificationId();
	    short percvdSeverity = alarmEvent.getPerceivedSeverity();
	    String strSeverity = String.valueOf(percvdSeverity);
	    short probableCause = alarmEvent.getProbableCause();
	    String strProbableCause = String.valueOf(probableCause);
	    sb.append("<applicationDN>"+appDN+"</applicationDN>");
	    sb.append("<eventTime>"+strTime+"</eventTime>");
	    sb.append("<managedObjectClass>"+objClass+"<managedObjectClass>");
	    sb.append("<managedObjectInstance>"+objInstance+"</managedObjectInstance>");
	    sb.append("<notificationId>"+notificationId+"</notificationId>");
	    sb.append("<alarmType>"+alarmType+"</alarmType>");
	    String alarmKeyXml =  AlarmKeyXmlTranslator.toXml(alarmKey,"alarmKey");
	    sb.append(alarmKeyXml);
	    sb.append("<fm:basePerceivedAlarmSeverity>"+strSeverity+"</fm:basePerceivedAlarmSeverity>");
	    sb.append("<fm:baseProbableAlarmCause>"+strProbableCause+"</fm:baseProbableAlarmCause>");
	    sb.append("</event>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();	
    }

    public static Object fromXml(org.w3c.dom.Element element, String type  )
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyChangedAlarmEvent.class.getName() )) 
	    {
                NotifyChangedAlarmEvent alarmEvent = new NotifyChangedAlarmEventImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alarmEvent);
                return alarmEvent;
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

    public static void fromXmlNoRoot(org.jdom.Element element, NotifyChangedAlarmEvent notifyAlarmEventaval)
    throws org.xml.sax.SAXException,ParseException
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
		}//end B
	    }//end C
	}//end A
    }

}//end class
