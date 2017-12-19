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
import javax.oss.*;


public class CurrentResultReportXmlTranslator {

  public CurrentResultReportXmlTranslator() {
  }
  /*
  <complexType name="CurrentResultReport">
		<sequence>
			<element name="reportData" type="measurement:ReportData" nillable="true"/>
			<element name="reportInformation" type="measurement:ReportInfo"
						nillable="true"/>
		</sequence>
	</complexType>
  */

  public static String toXml(CurrentResultReport currentResultReport, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");

        if (currentResultReport == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            //sb.append("<measurement:CurrentResultReport>\n");
            sb.append("<measurement:reportData>\n");
            ReportData reportData = currentResultReport.getReportData();
	    String s1 = ReportDataXmlTranslator.toXml(reportData, ReportData.class.getName());
	    sb.append(s1);
            sb.append("</measurement:reportData>\n");
            sb.append("<measurement:reportInformation>\n");
            ReportInfo rInfo = currentResultReport.getReportInformation();
            String s2 = ReportInfoXmlTranslator.toXml(rInfo, ReportInfo.class.getName());
            sb.append(s2);
            sb.append("</measurement:reportInformation>\n");
	}
    return sb.toString();
    }

 public static Object fromXml(org.w3c.dom.Element element, String type)
    throws javax.oss.IllegalArgumentException
    {
        try
        {
            if( type.equals(CurrentResultReport.class.getName() ))
	    {
		CurrentResultReport currentResultReport = new CurrentResultReportImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, currentResultReport);
                return currentResultReport;
            }
        }
	catch( org.xml.sax.SAXException ex )
	{
            return new javax.oss.IllegalArgumentException( ex.getMessage() );
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



    public static void fromXmlNoRoot(org.jdom.Element element,CurrentResultReport currentResultReport)
    throws org.xml.sax.SAXException, ParseException,JDOMException
    {
	  // Impl not required
    }//end method




}