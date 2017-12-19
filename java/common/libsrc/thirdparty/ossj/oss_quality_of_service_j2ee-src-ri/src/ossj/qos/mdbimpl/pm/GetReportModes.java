package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * Fixed this class, the responses and the implementation was not correct.
 * 2002-06-06, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
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




public class GetReportModes {

//Fixed these, 2002-06-06
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getReportModesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:reportModes>";
String result = null;
String excption = null;
String endResponse = "</measurement:reportModes> </measurement:getReportModesResponse>";
String beginExpection = "<measurement:getReportModesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getReportModesException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetReportModes() {
  }

  public String getResponse(org.jdom.Element elm){
  String xmlResponse = null;
  try{
     pmSession = ac.getPMRemote();
     //Fixed this, 2002-06-06
     if(elm.getName().equalsIgnoreCase("getReportModesRequest")){
       int[] rpmodes = pmSession.getReportModes();

       StringBuffer sb = new StringBuffer();
       //Fixed these, 2002-06-06
       String beginItem = "<measurement:item>";
       String endItem = "</measurement:item>";
       String itemResult = null;

       //Added this, 2002-06-06
       for(int k=0; k<rpmodes.length; k++){
         switch(rpmodes[k]){
           case (0) : xmlResponse = "NO_REPORT_MODE"; break;
           case (1) : xmlResponse = "EVENT_SINGLE"; break;
           case (2) : xmlResponse = "EVENT_MULTIPLE"; break;
           case (3) : xmlResponse = "FILE_SINGLE"; break;
           case (4) : xmlResponse = "FILE_MULTIPLE"; break;
         }
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