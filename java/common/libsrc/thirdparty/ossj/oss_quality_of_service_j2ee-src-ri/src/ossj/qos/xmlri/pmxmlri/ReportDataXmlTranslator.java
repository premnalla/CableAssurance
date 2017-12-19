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
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import ossj.qos.pm.measurement.*;

public class ReportDataXmlTranslator {

  public ReportDataXmlTranslator()
  {
  }

 /**
    <measurement:ReportData>
	<performanceMonitorReport></performanceMonitorReport>
	<reportFormat>
		<owner></owner>
		<specification></specification>
		<technology></technology>
		<version></version>
		<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
	</reportFormat>
    </measurement:ReportData>
  */
 public static String toXml(ReportData reportData, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (reportData == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            //sb.append("<measurement:ReportData>\n");
	    String performMonReport = reportData.getPerformanceMonitorReport().toString();
	    sb.append(performMonReport);
	    String className = ReportFormatXmlTranslator.class.getName();
	    String reportFormatXml = ReportFormatXmlTranslator.toXml(reportData.getReportFormat(),className);
	    sb.append(reportFormatXml);
            //sb.append("</measurement:ReportData>\n");
	}
    return sb.toString();
    }

 public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if( type.equals(ReportDataXmlTranslator.class.getName() ))
	    {

		ReportData reportData =  new ReportDataImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, reportData);
                return reportData;
            }
        }
	catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException pe)
	{
	    return null;
	}
	catch(JDOMException jdomException)
	{
	    return null;
	}


    return null;
    }



    public static void fromXmlNoRoot(org.jdom.Element element,ReportData reportData)
    throws org.xml.sax.SAXException, ParseException,JDOMException
    {
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  String elementName = null;
	  String elementValue = null;
	  org.jdom.Element tempElement = null;
	  while(it.hasNext())
	  {
	      tempElement = (org.jdom.Element)it.next();
	      elementName = tempElement.getName();
	      elementValue = tempElement.getTextTrim();

	      if(elementName.equalsIgnoreCase("performanceMonitorReport"))
	      {

	      }
	      else if(elementName.equalsIgnoreCase("reportFormat"))
	      {
		    DOMOutputter domOutputter = new DOMOutputter();
		    org.w3c.dom.Element w3cElement = domOutputter.output(element);
		    String className = ReportFormatXmlTranslator.class.getName();
		    ReportFormat reportFormat = null;
		    reportFormat = (ReportFormat)ReportFormatXmlTranslator.fromXml(w3cElement,className);
		    ReportFormat rf = reportData.getReportFormat();
		    rf.setOwner(reportFormat.getOwner());
		    rf.setSpecification(reportFormat.getSpecification());
		    rf.setTechnology(reportFormat.getTechnology());
		    rf.setType(reportFormat.getType());
		    rf.setVersion(reportFormat.getVersion());
	      }
	  }//end while
    }//end method


}//end class

