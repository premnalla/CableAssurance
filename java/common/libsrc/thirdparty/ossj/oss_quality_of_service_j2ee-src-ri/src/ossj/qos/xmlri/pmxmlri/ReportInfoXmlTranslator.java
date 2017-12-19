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
import java.net.*;
import javax.oss.pm.measurement.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import ossj.qos.pm.measurement.ReportInfoImpl;

public class ReportInfoXmlTranslator
{

  public ReportInfoXmlTranslator()
  {
  }

/**
<measurement:ReportInfo>
	<url></url>
	<reportFormat>
		<owner></owner>
		<specification></specification>
		<technology></technology>
		<version></version>
		<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
	</reportFormat>
	<expirationDate>dateTime</expirationDate>
</measurement:ReportInfo>
 */


 public static String toXml(ReportInfo reportInfo, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (reportInfo == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
	    String url = reportInfo.getURL().toString();
	    String expDate = reportInfo.getExpirationDate().getTime().toString();
	    ReportFormat reportFormat = reportInfo.getReportFormat();
	    String reportFormatXml = null;
	    reportFormatXml = ReportFormatXmlTranslator.toXml(reportFormat,"reportFormat");

	    //sb.append("<" + elementName + ">");
	    sb.append("<url>" + url + "</url>");
	    sb.append(reportFormatXml);
	    sb.append("<expirationDate>"+expDate+"</expirationDate>");
	    //sb.append("</" + elementName + ">");
	}
    return sb.toString();
    }

public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
        try
        {
            if( type.equals(ReportInfo.class.getName() ))
	    {
                ReportInfo reportInfo = new ReportInfoImpl();
                org.jdom.input.DOMBuilder builder= new org.jdom.input.DOMBuilder();
                org.jdom.Element jdomElement = builder.build(element);
                fromXmlNoRoot(jdomElement, reportInfo);
                return reportInfo;
             }

        }
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(JDOMException jdomEx)
	{
	    return null;
	}
	catch(MalformedURLException e)
	{
	    return null;
	}
	catch(Exception e)
	{
	    return null;
	}
        return null;
    }

    public static void fromXmlNoRoot( org.jdom.Element element,ReportInfo reportInfo)
    throws org.xml.sax.SAXException, ParseException ,MalformedURLException,JDOMException
    {
	  List list = element.getChildren();
	  Iterator it = list.iterator();
	  String elementName = null;
	  String elementValue = null;
	  org.jdom.Element localElement = null;
	  while(it.hasNext())
	  {
	        elementName = localElement.getName();
		elementValue = localElement.getTextTrim();
		if(elementName.equalsIgnoreCase("url"))
		{
		    URL url = new URL(elementValue);
		    reportInfo.setURL(url);
		}
		else if(elementName.equalsIgnoreCase("reportFormat"))
		{
		    String className = ReportFormatXmlTranslator.class.getName();
		    ReportFormat reportFormat = null;
		    DOMOutputter domOutputter = null;
		    org.w3c.dom.Element w3cElement = domOutputter.output(localElement);
		    reportFormat = (ReportFormat)ReportFormatXmlTranslator.fromXml(w3cElement,className);
		    reportInfo.setReportFormat(reportFormat);
		}
		else if(elementName.equalsIgnoreCase("expirationDate"))
		{
		    Calendar cal = Calendar.getInstance();
		    DateFormat df = DateFormat.getDateInstance();
		    Date date = df.parse(elementValue);
		    cal.setTime(date);
		    reportInfo.setExpirationDate(cal);
		}
	  }
    }
}//end class