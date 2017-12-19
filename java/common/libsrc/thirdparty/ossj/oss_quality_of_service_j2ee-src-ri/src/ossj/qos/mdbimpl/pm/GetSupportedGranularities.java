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
 * Fixed, re-wrote this class, it not implemented correctly.
 * 2002-06-24, Hooman Tahamtani, ERicsson AB., Gothenburg, Sweden.
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



public class GetSupportedGranularities {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getSupportedGranularitiesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:supportedGranularities>";
String result = null;
String excption = null;
String endResponse = "</measurement:supportedGranularities> </measurement:getSupportedGranularitiesResponse>";
String beginExpection = "<measurement:getSupportedGranularitiesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:remoteException>\n<co:message>";
String endException = "</co:message>\n</measurement:remoteException> </measurement:getSupportedGranularitiesException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetSupportedGranularities() {
  }

// Rr-wrote this method, 2002-06-24.
  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   DOMOutputter dout = new DOMOutputter();
   org.w3c.dom.Element elmDom = null;
   String objType = null;
   int[] inArray = null;


  try{
   pmSession = ac.getPMRemote();

  if(elm.getName().equalsIgnoreCase("getSupportedGranularitiesRequest")){
    if(elm.hasChildren()){
        List list = elm.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext())
        {
          org.jdom.Element el = ((org.jdom.Element) it.next());
          if(el.getName().equalsIgnoreCase("pmValue")){
            elmDom = dout.output(el);
          }
        }
    }
     objType = elmDom.getAttribute("type").trim();
     if(objType.equalsIgnoreCase("measurement:PerformanceMonitorByObjectsValue")){
        PerformanceMonitorByObjectsValue pmoValue = (PerformanceMonitorByObjectsValue)PerformanceMonitorByObjectsValueXmlTranslator.fromXml(elmDom,PerformanceMonitorByObjectsValue.class.getName());
        inArray = pmSession.getSupportedGranularities(pmoValue);
     }
     else if(objType.equalsIgnoreCase("measurement:PerformanceMonitorByClassesValue")){
        PerformanceMonitorByClassesValue pmcValue = (PerformanceMonitorByClassesValue)PerformanceMonitorByClassesValueXmlTranslator.fromXml(elmDom,PerformanceMonitorByClassesValue.class.getName());
        inArray = pmSession.getSupportedGranularities(pmcValue);
     }

     StringBuffer sb = new StringBuffer();
     String beginItem = "<co:item>";
     String endItem = "</co:item>";
     String itemResult = null;

    for(int k=0; k<inArray.length; k++){
       xmlResponse = new Integer(inArray[k]).toString();
       itemResult = beginItem + xmlResponse + endItem;
       sb.append(itemResult);

   }
    result = beginResponse + sb.toString() + endResponse;

  }

   }catch(Exception je){
     je.printStackTrace();
     excption = je.getMessage() + "\n\n" + je.toString();
     result = beginExpection + excption + endException;
   }

     return result;

   }


}