package ossj.qos.xmlri.pmxmlri;

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
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.Schedule;

import org.jdom.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.*;

public class PerformanceMonitorValueXmlTranslator
{

  public PerformanceMonitorValueXmlTranslator()
  {

  }

/**
<measurement:PerformanceMonitorValue>
	<lastUpdateVersionNumber>long Value</lastUpdateVersionNumber>
	<name></name>
	<granularityPeriod>int</granularityPeriod>
	<reportByFile>FILE_SINGLE</reportByFile>
	<reportByEvent>EVENT_SINGLE</reportByEvent>
	<reportPeriod>int</reportPeriod>
	<reportFormat>
		<owner></owner>
		<specification></specification>
		<technology></technology>
		<version></version>
		<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
	</reportFormat>
	<schedule>
		<startTime></startTime>
		<stopTime></stopTime>
		<weeklySchedule>
			<timeZone></timeZone>
			<monday>boolean</monday>
			<tuesday>boolean</tuesday>
			<wednesday>boolean</wednesday>
			<thursday>boolean</thursday>
			<friday>boolean</friday>
			<saturday>boolean</saturday>
			<sunday>boolean</sunday>
		</weeklySchedule>
		<dailySchedule>
			<startTimes>dateTime</startTimes>
			<stopTimes>dateTime</stopTimes>
		</dailySchedule>
	</schedule>
	<measurement:basePerformanceMonitorState>ACTIVE_ON_DUTY</measurement:basePerformanceMonitorState>
	<performanceMonitorKey>
		<applicationContext>
			<factoryClass></factoryClass>
			<url>anyURI</url>
			<systemProperties>
				<property>
					<name></name>
					<value></value>
				</property>
			</systemProperties>
		</applicationContext>
		<applicationDN></applicationDN>
		<type></type>
		<performanceMonitorPrimaryKey></performanceMonitorPrimaryKey>
	</performanceMonitorKey>
</measurement:PerformanceMonitorValue>
 */
public static String toXml(PerformanceMonitorValue performValue, String elementName)
throws org.xml.sax.SAXException
{
        StringBuffer sb = new StringBuffer();
	boolean flag = false;
        if (performValue == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    long version = performValue.getLastUpdateVersionNumber();
	    String lastVersion = String.valueOf(version);
	    String name = performValue.getName();
	    String granularityPeriod = String.valueOf(performValue.getGranularityPeriod());
	    String reportByFile = String.valueOf(performValue.getReportByFile());
	    String reportByEvent = String.valueOf(performValue.getReportByEvent());
	    String reportPeriod = String.valueOf(performValue.getReportPeriod());
	    ReportFormat reportFormat = performValue.getReportFormat();

	    String reportFormatXml = ReportFormatXmlTranslator.toXml(reportFormat,"reportFormat");

	    Schedule schedule = performValue.getSchedule();

	    String scheduleXml =  ScheduleXmlTranslator.toXml(schedule,"schedule");

	    PerformanceMonitorKey performKey = performValue.getPerformanceMonitorKey();
    	    String performKeyXml = PerformanceMonitorKeyXmlTranslator.toXml(performKey,"performanceMonitorKey");

	    sb.append("<lastUpdateVersionNumber>");
	    sb.append(lastVersion);
	    sb.append("</lastUpdateVersionNumber>");
	    sb.append("<name>");
	    sb.append(name);
   	    sb.append("</name>");
	    sb.append("<granularityPeriod>");
	    sb.append(granularityPeriod);
	    sb.append("</granularityPeriod>");
	    sb.append("<reportByFile>");
	    sb.append(reportByFile);
	    sb.append("</reportByFile>");
    	    sb.append("<reportByEvent>");
	    sb.append(reportByEvent);
	    sb.append("</reportByEvent>");
	    sb.append("<reportPeriod>");
	    sb.append(reportPeriod);
	    sb.append("</reportPeriod>");
	    sb.append(reportFormatXml);
	    sb.append(scheduleXml);
	    sb.append(performKeyXml);
	}
return sb.toString();
}


  public static Object fromXml( org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
	{
            if( type.equals(PerformanceMonitorByObjectsValue.class.getName() ))
	    {
                PerformanceMonitorValue performValue = new PerformanceMonitorByObjectsValueImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, performValue);
                return performValue;
             }
	}
	catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException pExc)
	{
	    return null;
	}
        return null;
    }


 public static void fromXmlNoRoot( org.jdom.Element element, PerformanceMonitorValue performValue)
    throws org.xml.sax.SAXException, ParseException
    {
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  String elementName = null;
	  String elementValue = null;
	  while(it.hasNext())
	  {
	      element = (org.jdom.Element)it.next();
	      elementName = element.getName();
	      elementValue = element.getTextTrim();
	  }
     }

}//end class