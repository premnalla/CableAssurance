package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import javax.oss.pm.measurement.*;
import java.text.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import ossj.qos.pm.measurement.PerformanceDataAvailableEventImpl;


 public class PerformanceDataAvailableEventXmlTranslator
 {

  public PerformanceDataAvailableEventXmlTranslator()
  {
  }


  /**
    <measurement:PerformanceDataAvailableEvent>
	<event>
		<applicationDN></applicationDN>
		<eventTime></eventTime>
		<managedObjectClass></managedObjectClass>
		<managedObjectInstance></managedObjectInstance>
		<notificationId></notificationId>
		<reportInformation>
			<url></url>
			<reportFormat>
				<owner></owner>
				<specification></specification>
				<technology></technology>
				<version></version>
				<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
			</reportFormat>
			<expirationDate></expirationDate>
		</reportInformation>
	</event>
    </measurement:PerformanceDataAvailableEvent>
   */

public static String toXml(PerformanceDataAvailableEvent performDataEvent, String elementName)
throws org.xml.sax.SAXException
    {

        StringBuffer sb = new StringBuffer();

        if (performDataEvent == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    sb.append("<" + elementName + ">");
	    sb.append("<event>");

	    String appDN = performDataEvent.getApplicationDN();
	    String eventTime = performDataEvent.getEventTime().toString();
	    String mngdObjClass = performDataEvent.getManagedObjectClass();
	    String mngdObjInstance = performDataEvent.getManagedObjectInstance();
	    String notifocationId = performDataEvent.getNotificationId();
	    ReportInfo reportInfo = performDataEvent.getReportInformation();
	    sb.append("<applicationDN>"+appDN +"</applicationDN>");
	    sb.append("<eventTime>"+eventTime+"</eventTime>");
	    sb.append("<managedObjectClass>"+mngdObjClass+"</managedObjectClass>");
	    sb.append("<managedObjectInstance>"+mngdObjInstance+"</managedObjectInstance>");
	    sb.append("<notificationId>"+notifocationId+"</notificationId>");
	    String reportInfoXml =  ReportInfoXmlTranslator.toXml(reportInfo,"reportInformation");
	    sb.append(reportInfoXml);
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
            if(type.equals(PerformanceDataAvailableEvent.class.getName() ))
	    {
                PerformanceDataAvailableEvent performData = new PerformanceDataAvailableEventImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, performData);
                return performData;
             }
	}
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
        catch(ParseException pex)
	{
            return null;
        }
        catch(JDOMException jdomeExc)
	{
            return null;
        }


    return null;
    }

    public static void fromXmlNoRoot( org.jdom.Element element,PerformanceDataAvailableEvent object)
    throws org.xml.sax.SAXException, ParseException,JDOMException
    {
	List list = element.getChildren();
	Iterator it = list.iterator();
	String elementName = null;
	String elementValue = null;
	org.jdom.Element localElement = null;
	while(it.hasNext())
	{
	    localElement = (org.jdom.Element)it.next();
	    elementName = localElement.getName();
	    elementValue = localElement.getTextTrim();
	    if(elementName.equalsIgnoreCase("event"))
	    {
		List listOfChildren = localElement.getChildren();
		Iterator it1 = listOfChildren.iterator();
		org.jdom.Element element1 = null;
		String name1 = null;
		String value1 = null;

		while(it1.hasNext())
		{
		    element1 = (org.jdom.Element)it1.next();
		    name1 = element1.getName();
		    value1 = element1.getTextTrim();
		    if(elementName.equalsIgnoreCase("applicationDN"))
		    {
			object.setApplicationDN(value1);
		    }
		    else if(elementName.equalsIgnoreCase("eventTime"))
		    {
			DateFormat df = DateFormat.getDateInstance();
			Date eventTime = df.parse(value1);
			object.setEventTime(eventTime);
		    }
		    else if(elementName.equalsIgnoreCase("managedObjectClass"))
		    {
			object.setManagedObjectClass(value1);
		    }
		    else if(elementName.equalsIgnoreCase("managedObjectInstance"))
		    {
			object.setManagedObjectInstance(value1);
		    }

		    else if(elementName.equalsIgnoreCase("notificationId"))
		    {
			object.setNotificationId(value1);
		    }
		    else if(elementName.equalsIgnoreCase("reportInformation"))
		    {
			DOMOutputter domOutputter = new DOMOutputter();
			org.w3c.dom.Element w3cElement = null;
			w3cElement = domOutputter.output(element1);
			String className = ReportInfo.class.getName();
			ReportInfo reportInfo = null;
			reportInfo = (ReportInfo)ReportInfoXmlTranslator.fromXml(w3cElement,className);
			object.setReportInformation(reportInfo);
		    }

		}
	    }
	}//while
    }

}