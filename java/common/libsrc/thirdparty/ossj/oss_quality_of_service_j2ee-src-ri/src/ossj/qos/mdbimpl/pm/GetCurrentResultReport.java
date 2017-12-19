package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.io.*;
import java.util.*;


import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;

public class GetCurrentResultReport {


//hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getCurrentResultReportResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:currentResultReport>";
String result = null;
String excption = null;
String endResponse = "</measurement : currentResultReport> </measurement:getCurrentResultReportResponse>";
String beginExpection = "<measurement:getCurrentResultReportException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getCurrentResultReportException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetCurrentResultReport() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   PerformanceMonitorKey pmKey = null;
   ReportFormat rf = null;
   DOMOutputter dout = new DOMOutputter();
  try{
    pmSession = ac.getPMRemote();
  // PerformanceMonitorValue pmValue = new PerformanceMonitorValueImpl();
   if(elm.getName().equalsIgnoreCase("measurement:getCurrentResultReportRequest")){
    java.util.List children = elm.getChildren();
    Iterator it = children.iterator();
    org.jdom.Element child = null;

    while(it.hasNext())
	{

          org.jdom.Element elm1 = (org.jdom.Element)it.next();
            if(elm1.getName().equalsIgnoreCase("pmKey")){
            org.w3c.dom.Element elmDom = dout.output(elm1);
              pmKey = (PerformanceMonitorKey)PerformanceMonitorKeyXmlTranslator.fromXml(elmDom, PerformanceMonitorKey.class.getName());
              }
            if(elm1.getName().equalsIgnoreCase("reportFormat")){
              org.w3c.dom.Element elmDom = dout.output(elm1);
              rf = (ReportFormat)ReportFormatXmlTranslator.fromXml(elmDom,ReportFormat.class.getName());
              }
         }

   CurrentResultReport crReport = pmSession.getCurrentResultReport(pmKey,rf);
  XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
    xmlResponse = mevXmlSerializer.toXml(crReport, CurrentResultReport.class.getName());
    result = beginResponse + xmlResponse + endResponse;

    }
   }catch(Exception je){
    excption = je.getMessage();
     result = beginExpection + excption + endException;
   }

     return result;

   }

}