package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
 * @version 1.0
 *
 * Fixed this class, it did not work properly. 2002-06-12.  HT
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
import javax.oss.pm.util.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;


public class GetReportFormats {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getReportFormatsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\">\n <measurement:reportFormats>\n";
String result = null;
String excption = null;
String endResponse = "</measurement:reportFormats>\n</measurement:getReportFormatsResponse>";
String beginExpection = "<measurement:getReportFormatsException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getReportFormatsException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetReportFormats() {
  }

  public String getResponse(org.jdom.Element elm){
    String xmlResponse = null;
    try{
       pmSession = ac.getPMRemote();
       if(elm.getName().equalsIgnoreCase("getReportFormatsRequest")){
         ReportFormat[] rpFormat = pmSession.getReportFormats();
         StringBuffer sb = new StringBuffer();
         String beginItem = "<measurement:item>\n";
         String endItem = "</measurement:item>\n";
         String itemResult = null;
         XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
         for(int k=0; k<rpFormat.length; k++){
           xmlResponse = mevXmlSerializer.toXml( rpFormat[k] , ReportFormat.class.getName());
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