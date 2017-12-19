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
import java.text.DateFormat;


import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.*;
import javax.oss.pm.util.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;


public class GetPerformanceReportInfo{

//hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getPerformanceReportInfoResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:performanceReportInfo>";
String result = null;
String excption = null;
String endResponse = "</measurement : performanceReportInfo> </measurement:getPerformanceReportInfoResponse>";
String beginExpection = "<measurement:getPerformanceReportInfoException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getPerformanceReportInfoException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();


 public void GetPerformanceReportInfo(){

 }
 public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String date = null;
   PerformanceMonitorKey pmKey = null;
   String howMany = null;
    DOMOutputter dout = new DOMOutputter();
  try{
    pmSession = ac.getPMRemote();

    if(elm.getName().equalsIgnoreCase("getPerformanceReportInfoRequest")){
       java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext())
	{
          org.jdom.Element elm1 = (org.jdom.Element)it.next();
          if(elm1.getName().equalsIgnoreCase("howMany")){
            howMany = elm.getChild("howMany").getTextTrim();
             }
          if(elm1.getName().equalsIgnoreCase("pmKey")){
            org.w3c.dom.Element elmDom = dout.output(elm1);
            pmKey = (PerformanceMonitorKey)PerformanceMonitorKeyXmlTranslator.fromXml(elmDom,PerformanceMonitorKey.class.getName());

             }
          if(elm1.getName().equalsIgnoreCase("date")){
           date = elm.getChild("date").getTextTrim();
             }
          }

  DateFormat df = DateFormat.getDateInstance();
    java.util.Calendar cal =  df.getCalendar();

  ReportInfoIterator rpInfoIterator = pmSession.getPerformanceReportInfo(pmKey, cal);
   ReportInfo[] reportInfo = rpInfoIterator.getNext(Integer.parseInt(howMany));

   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;

  XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<reportInfo.length; k++){
   xmlResponse = mevXmlSerializer.toXml( reportInfo[k] , ReportInfo.class.getName());
   itemResult = beginItem + xmlResponse + endItem;
   sb.append(itemResult);
   }
   result = beginResponse + sb.toString() + endResponse;
    }
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;

   }

     return result;

   }


}