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

public class NotifyClearedAlarmEventXmlTranslator
{

    public NotifyClearedAlarmEventXmlTranslator()
    {

    }
/**
 * <NotifyClearedAlarmEvent>
 * <event>
	<AlarmEventType>
		<applicationDN>
		<eventTime>
        	<managedObjectClass></managedObjectClass>
        	<managedObjectInstance></managedObjectClass>
        	<notificationId><notificationId>
		<alarmType>	</alarmType>
		<alarmKey>	</alarmKey>
		<fm:basePerceivedAlarmSeverity></fm:basePerceivedAlarmSeverity>
		<fm:baseProbableAlarmCause></fm:baseProbableAlarmCause>
	</AlarmEventType>
	<correlatedNotifications>
		<item>
		    <notificationIds>
		        <item>2343242</item>
		    </notificationIds>
			<managedObjectInstance>gfdgfgd</managedObjectInstance>
		</item>
	</correlatedNotifications>     
</event>	
</NotifyClearedAlarmEvent>
 */
    public static String toXml(NotifyClearedAlarmEvent alarmEvent,String elementName )
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
	    sb.append("<AlarmEventType>");
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
	    sb.append("</AlarmEventType>");
	    sb.append("</event>");
    	    sb.append("<correlatedNotifications>");
	    CorrelatedNotificationValue[] corValues = alarmEvent.getCorrelatedNotifications();
	    StringBuffer corValuesXml = new StringBuffer();
	    for(int i=0;i<corValues.length;i++)
	    {
		corValuesXml.append(CorrelatedNotificationValueXmlTranslator.toXml(corValues[i],"Item"));
	    }
	    sb.append(corValuesXml.toString());
	    sb.append("</correlatedNotifications>");
	    sb.append("</" + elementName + ">");	
	}
    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(NotifyClearedAlarmEvent.class.getName() )) 
	    {
                NotifyClearedAlarmEvent alarmEvent = new NotifyClearedAlarmEventImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, alarmEvent);
                return alarmEvent;
             }
        }
        catch( org.xml.sax.SAXException ex ) 
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(Exception ex) 
	{
	    return null;
	}	
        return null;
    }

    public static void fromXmlNoRoot(org.jdom.Element element, NotifyClearedAlarmEvent notifyAlarmEventaval)
    throws org.xml.sax.SAXException,ParseException,JDOMException
    {
	DOMOutputter domOutputter = new DOMOutputter();
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
		List list2 = localElement.getChildren();
		Iterator it2 = list2.iterator();
		String elementName2 = null;
		String elementValue2 = null;
		org.jdom.Element localElement2 = null;
		while(it2.hasNext())//B
		{
		    localElement2 = (org.jdom.Element)it2.next();
		    elementName2 = localElement2.getName();
		    elementValue2 = localElement2.getTextTrim();
		    
		    if(elementName2.equalsIgnoreCase("AlarmEventType"))//If A2
		    {
			Iterator it3 = localElement2.getChildren().iterator();
		        String elementName3 = null;
		        String elementValue3 = null;
			org.jdom.Element localElement3 = null;
			while(it3.hasNext())// while A3
			{
			    localElement3 = (org.jdom.Element)it3.next();
			    elementName3 = localElement3.getName();
			    elementValue3 = localElement3.getTextTrim();
			    if(elementName3.equalsIgnoreCase("applicationDN"))
			    {
				    notifyAlarmEventaval.setApplicationDN(elementValue3);
			    }
			    else if(elementName3.equalsIgnoreCase("eventTime"))
			    {
				    DateFormat df = DateFormat.getInstance();
				    Date evenTime = df.parse(elementValue3);
				    notifyAlarmEventaval.setEventTime(evenTime);
			    }
			    else if(elementName3.equalsIgnoreCase("managedObjectClass"))
			    {
				    notifyAlarmEventaval.setManagedObjectClass(elementValue3);
			    }
			    else if(elementName3.equalsIgnoreCase("managedObjectInstance"))
			    {
				    notifyAlarmEventaval.setManagedObjectInstance(elementValue3);
			    }
			    else if(elementName3.equalsIgnoreCase("notificationId"))
			    {
				    notifyAlarmEventaval.setNotificationId(elementValue3);
			    }
			}//end while A3
		    }//end if A2
		    else if(elementName2.equalsIgnoreCase("correlatedNotifications"))
		    {
			Iterator it4= localElement2.getChildren().iterator();
			ArrayList ids = new ArrayList();
		        String elementName4 = null;
		        String elementValue4 = null;
			org.jdom.Element localElement4 = null;
			while(it4.hasNext())//while A4
			{
			    localElement4 = (org.jdom.Element)it4.next();
			    elementName4 = localElement4.getName();
			    elementValue4 = localElement4.getTextTrim();
			    String className = CorrelatedNotificationValue.class.getName();
			    CorrelatedNotificationValue corValue = null;
			    if(elementName4.equalsIgnoreCase("item"))
			    {
				org.w3c.dom.Element w3cElement = domOutputter.output(localElement4);
				corValue = (CorrelatedNotificationValue)CorrelatedNotificationValueXmlTranslator.fromXml(w3cElement,className);
				ids.add(corValue);
			    }
			}//end while A4
			CorrelatedNotificationValue[] corValues = (CorrelatedNotificationValue[])ids.toArray();
			notifyAlarmEventaval.setCorrelatedNotifications(corValues);
		    }
		}//end B
	    }//end C
	}//end A
    }

}//end class
