package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @Hooman Tahamtani, Ericsson AB
 * @version 1.0
 *
 * Hooman Tahamtani, Ericsson AB, Gothenburg Sweden 2002-05-30
 * Fixed the bug in the message format, it was sending the wrong format.
 *
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


public class GetVersion {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getVersionResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:versions>";
String result = null;
String excption = null;
String endResponse = "</measurement:versions> </measurement:getVersionResponse>";
String beginExpection = "<measurement:getVersionException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getVersionException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetVersion() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;

  try{
     pmSession = ac.getPMRemote();
     if(elm.getName().equalsIgnoreCase("getVersionRequest")){
         StringBuffer sb = new StringBuffer();
         String beginItem = "<co:item>";
         String endItem = "</co:item>";
         String itemResult = null;


         String[] stArray = pmSession.getVersion();
          for(int k=0; k<stArray.length; k++){
            xmlResponse = stArray[k];
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