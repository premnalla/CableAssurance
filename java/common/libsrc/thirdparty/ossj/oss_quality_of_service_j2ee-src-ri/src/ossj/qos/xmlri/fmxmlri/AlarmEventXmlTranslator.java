package ossj.qos.xmlri.fmxmlri;



/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
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

public class AlarmEventXmlTranslator
{

    public AlarmEventXmlTranslator()
    {

    }

    /**
    <alarmEvent>
    <event>
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
    </event>
     */
    public static String toXml(AlarmEvent alarmEvent,String elementName )
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
	    sb.append("</" + elementName + ">");	
	}
    return sb.toString();
    }




    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException 
    {
        try 
	{
            if( type.equals(AlarmEvent.class.getName() )) 
	    {
                AlarmEvent alarmEvent = null; //TODO: find out what is the implementaion class
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);		
                fromXmlNoRoot(tempElement, alarmEvent);
                return alarmEvent;
            }

        }
        catch( org.xml.sax.SAXException ex ) {
            return new IllegalArgumentException( ex.getMessage() );
        }
        return null;


    }

    public static void fromXmlNoRoot(org.jdom.Element element,AlarmEvent aval)
    throws org.xml.sax.SAXException
    {
    /*
     NodeList nodeList1 = node.getChildNodes();
        Node node2;
        //VP: System.out.println("fromXmlNoRoot--> translated attributes:");
        //VP: System.out.println("fromXmlNoRoot--> length:" + nodeList1.getLength());
        for (int i=0; i<nodeList1.getLength(); i++)
        {
            node2 = nodeList1.item(i);
            if( node2.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName1 = node2.getNodeName();
            //VP: System.out.println( "Node Name = " + nodeName1 );
            if ( nodeName1.equals("fm:attributeName") )
            if (node2.hasChildNodes())
            aval.setAttributeName(node2.getFirstChild().getNodeValue());
            else if ( nodeName1.equals("fm:attributeType") )
            {
                if (node2.hasChildNodes())
                aval.setAttributeType(node2.getFirstChild().getNodeValue());
            }
            else if ( nodeName1.equals("fm:Value") )
            {
                if (node2.hasChildNodes())
                aval.setValue(node2.getFirstChild().getNodeValue());
            }


    }
*/

}

}

