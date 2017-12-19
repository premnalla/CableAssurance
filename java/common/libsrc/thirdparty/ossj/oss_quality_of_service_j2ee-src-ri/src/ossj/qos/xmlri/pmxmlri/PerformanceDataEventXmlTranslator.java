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
import javax.oss.pm.measurement.*;
import ossj.qos.pm.measurement.*;
import java.text.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class PerformanceDataEventXmlTranslator 
{

  public PerformanceDataEventXmlTranslator() 
  {
  }
  
/**
<measurement:PerformanceDataEvent>
	<event>
		<performanceMonitorReport></performanceMonitorReport>
		<reportFormat>
			<owner></owner>
			<specification></specification>
			<technology></technology>
			<version></version>
			<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
		</reportFormat>
	</event>
</measurement:PerformanceDataEvent>

 */
 public static String toXml(PerformanceDataEvent performDataEvent, String elementName)
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
	    String performReport = (String)performDataEvent.getPerformanceMonitorReport();
	    ReportFormat reportFormat = performDataEvent.getReportFormat();
	    String reportFormatXml = ReportFormatXmlTranslator.toXml(reportFormat,"reportFormat");
	    sb.append("<performanceMonitorReport>"+performReport+"</performanceMonitorReport>");
	    sb.append(reportFormatXml);
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
            if( type.equals(PerformanceDataEvent.class.getName() )) 
	    {
                PerformanceDataEvent performData = new PerformanceDataEventImpl();
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
        catch(ParseException pEx) 
	{
	    return null;
	}
        catch(Exception ex) 
	{
	    return null;
	}
	
        return null;
    }  

    public static void fromXmlNoRoot( org.jdom.Element element, PerformanceDataEvent object)
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
	    if(elementName.equalsIgnoreCase("event"))
	    {
		String performReport =  localElement.getChild("performanceMonitorReport").getText();
		object.setPerformanceMonitorReport(performReport);
		org.jdom.Element reportChild = localElement.getChild("reportFormat");
		org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		String className = ReportFormat.class.getName();
		ReportFormat reportFormat = null;
		reportFormat  = (ReportFormat)ReportFormatXmlTranslator.fromXml(w3cElement,className);
		object.setReportFormat(reportFormat);		
	    }
	}
    }

  
}//end class