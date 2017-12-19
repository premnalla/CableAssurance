package ossj.qos.xmlri.tmxmlri;

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
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
//import ossj.qos.ri.pm.threshold.*;
import ossj.qos.pm.threshold.*;
import javax.oss.pm.threshold.*;
//import ossj.qos.ri.pm.measurement.*;
import javax.oss.pm.threshold.*;
import javax.oss.pm.util.*;
//import ossj.qos.xmlri.pmxmlri.*;


public class ThresholdMonitorValueXmlTranslator {

  public ThresholdMonitorValueXmlTranslator()
  {
  }

  /**
<ThresholdMonitorValue>
	<lastUpdateVersionNumber></lastUpdateVersionNumber>
	<granularityPeriod></granularityPeriod>
	<schedule></schedule>
	<thresholdMonitorKey></thresholdMonitorKey>
	<name></name>
	<state></state>
<ThresholdMonitorValue>
   */
  public static String toXml(ThresholdMonitorValue object, String elementName)
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
	    String versionNumber = String.valueOf(object.getLastUpdateVersionNumber());
	    String granPeriod = String.valueOf(object.getGranularityPeriod());
	    Schedule schedule = object.getSchedule();
	    ThresholdMonitorKey monitorKey = object.getThresholdMonitorKey();
	    String name = object.getName();
	    String state = String.valueOf(object.getState());
	    sb.append("<lastUpdateVersionNumber>"+versionNumber+"</lastUpdateVersionNumber>");
	    sb.append("<granularityPeriod>"+granPeriod+"</granularityPeriod>");
	    String scheduleXml = ScheduleXmlTranslator.toXml(schedule,"schedule");
	    sb.append(scheduleXml);
	    String monitorKeyXml = ThresholdMonitorKeyXmlTranslator.toXml(monitorKey,"thresholdMonitorKey");
	    sb.append(monitorKeyXml);
	    sb.append("<name>"+name+"</name>");
	    sb.append("<state>"+state+"</state>");
	    sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }


    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if( type.equals(ThresholdMonitorValue.class.getName() ))
	    {
                ThresholdMonitorValue monitorValue = new ThresholdMonitorValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, monitorValue);
                return monitorValue;
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

    public static void fromXmlNoRoot(org.jdom.Element element, ThresholdMonitorValue object)
    throws org.xml.sax.SAXException, ParseException, JDOMException
    {
	DOMOutputter domOutputter = new DOMOutputter();
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
	    if(elementName.equalsIgnoreCase("lastUpdateVersionNumber"))
	    {
		long verNumber = (new Long(elementValue)).longValue();
		object.setLastUpdateVersionNumber(verNumber);
	    }
	    else if(elementName.equalsIgnoreCase("granularityPeriod"))
	    {
		int granPeriod = (new Integer(elementValue)).intValue();
		object.setGranularityPeriod(granPeriod);
	    }
	    else if(elementName.equalsIgnoreCase("schedule"))
	    {
		org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		String className = Schedule.class.getName();
		Schedule schedule = null;
		schedule = (Schedule)ScheduleXmlTranslator.fromXml(w3cElement,className);
		object.setSchedule(schedule);
	    }
	    else if(elementName.equalsIgnoreCase("thresholdMonitorKey"))
	    {
		String className = ThresholdMonitorKey.class.getName();
		ThresholdMonitorKey monitorKey = null;
		org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		monitorKey = (ThresholdMonitorKey)ThresholdMonitorKeyXmlTranslator.fromXml(w3cElement,className);
		object.setThresholdMonitorKey(monitorKey);
	    }
	    else if(elementName.equalsIgnoreCase("name"))
	    {
		object.setName(elementValue);
	    }
	    else if(elementName.equalsIgnoreCase("state"))
	    {
		int state = Integer.parseInt(elementValue);
		object.setState(state);
	    }

	}
    }


}//end class
