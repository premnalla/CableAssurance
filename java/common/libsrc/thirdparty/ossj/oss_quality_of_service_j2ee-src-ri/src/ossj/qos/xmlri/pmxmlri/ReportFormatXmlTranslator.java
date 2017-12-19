package ossj.qos.xmlri.pmxmlri;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB, Gothenburg, Sweden
 * @author Vijay Sharma
 * @author Hooman Tahamtani, Ericsson AB.
 *
 * @version 1.0
 *
 * I fixed this class, the methods fromXml and toXml were incorect.  They were
 * returning worng format, and did not work.
 * Hooman Tahamtani, Ericsson AB, Gothenburg, Sweden
 * 2002-06-06
 */

import java.util.*;
import java.text.*;
import javax.oss.pm.measurement.*;
import org.jdom.*;
import org.jdom.input.*;
import ossj.qos.pm.measurement.ReportFormatImpl;


public class ReportFormatXmlTranslator
{

  public ReportFormatXmlTranslator()
  {
  }

    /*
		<reportFormat>
			<owner></owner>
			<specification></specification>
			<technology></technology>
			<version></version>
			<measurement:baseReportFormatTypes>ASCII</measurement:baseReportFormatTypes>
		</reportFormat>
    */

    public static String toXml(ReportFormat reportFormat, String elementName )
    throws org.xml.sax.SAXException
    {
        StringBuffer sb = new StringBuffer();
        if (reportFormat == null)
        {
            sb.append("<" + elementName + " xsi:nil=\"true\"></" + elementName + ">");
        }
        else
        {
            //Took this out, 2002-06-06
            //sb.append("<" + elementName + ">\n");
	    String owner = reportFormat.getOwner();
	    String specification = reportFormat.getSpecification();
	    String technology = reportFormat.getTechnology();
	    int type = reportFormat.getType();
            //Added below, type was not correct, 2002-06-06
            String stringType = null;
            switch(type){
              case (0) : stringType = "XML"; break;
              case (1) : stringType = "ASN1"; break;
              case (2) : stringType = "ASCII"; break;
              case (3) : stringType = "BINARY"; break;
            }
	    String version = reportFormat.getVersion();
	    //assuming all the values are not nullable
            //Fixed these, tags were not correct, 2002-04-06
	    sb.append("<measurement:owner>"+owner+"</measurement:owner>\n");
   	    sb.append("<measurement:specification>"+specification+"</measurement:specification>\n");
	    sb.append("<measurement:technology>"+technology+"</measurement:technology>\n");
	    sb.append("<measurement:version>"+version+"</measurement:version>\n");
            sb.append("<measurement:reportFormat>" + stringType);
            sb.append("</measurement:reportFormat>\n");
            //took this out, 2002-06-06
            //sb.append("</" + elementName + ">\n");
	}

    return sb.toString();
    }

    public static Object fromXml(org.w3c.dom.Element element, String type)
    throws IllegalArgumentException
    {
    try
    {
            /*  I fixed this, the ReportFromat class name should be ReportFormat.calss.getName()
             *  I don't know why this is looking for its own name, does not make any sense to me
             *  and does not work either.  Therefore I changed it.
             *
             * Hooman Tahamtani, Ericsson AB, 2002-06-06
             */
            //if( type.equals(ReportFormatXmlTranslator.class.getName() ))
            if( type.equalsIgnoreCase("ReportFormat") || type.equalsIgnoreCase("measurement:currentReportFormat"))
	    {
                ReportFormat reportFormat = new ReportFormatImpl();
		org.jdom.input.DOMBuilder builder= new DOMBuilder();
                org.jdom.Element tempElement = builder.build(element);
                fromXmlNoRoot(tempElement, reportFormat);
                if(reportFormat != null){
                  return reportFormat;
                }
            }

        }
        catch( org.xml.sax.SAXException ex )
	{
            return new IllegalArgumentException( ex.getMessage() );
        }
	catch(ParseException e)
	{
	    System.out.println("Parse exception, is: ");
            e.printStackTrace();
            return null;
	}
        return null;
    }

    public static void fromXmlNoRoot( org.jdom.Element element,ReportFormat reportFormat)
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
	     if(elementName.equalsIgnoreCase("owner"))
	     {
		reportFormat.setOwner(elementValue);
	     }
	     else if(elementName.equalsIgnoreCase("specification"))
	     {
		reportFormat.setSpecification(elementValue);
	     }
	     else if(elementName.equalsIgnoreCase("technology"))
	     {
		reportFormat.setTechnology(elementValue);
	     }
	     else if(elementName.equalsIgnoreCase("version"))
	     {
		reportFormat.setVersion(elementValue);
	     }
             //Fixed these, the tags were wrong, 2002-06-06
             else if(elementName.equalsIgnoreCase("measurement:baseReportFormatTypes"))
	     {
		int type = Integer.parseInt(elementValue);
		reportFormat.setType(type);
	     }
             else if (elementName.equalsIgnoreCase("measurement:ReportFormatTypes")){
             //Added below, type was not correct, 2002-06-06
             int type = 0;
             if(elementValue.equalsIgnoreCase("XML")){
               type = 0;
             }else if(elementValue.equalsIgnoreCase("ASN1")){
               type = 1;
             }else if(elementValue.equalsIgnoreCase("ASCII")){
               type = 2;
             }else if(elementValue.equalsIgnoreCase("BINARY")){
               type = 3;
             }
            reportFormat.setType(type);
            }
	  }
   }

}//end class