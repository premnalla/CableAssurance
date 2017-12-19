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



public class TryCreatePerformanceMonitorsByValues {

//hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:tryCreatePerformanceMonitorsByValuesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:createPerformanceMonitorsByValues>";
String result = null;
String excption = null;
String endResponse = "</measurement : createPerformanceMonitorsByValues> </measurement:tryCreatePerformanceMonitorsByValuesResponse>";
String beginExpection = "<measurement:tryCreatePerformanceMonitorsByValuesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:tryCreatePerformanceMonitorsByValuesException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public TryCreatePerformanceMonitorsByValues() {
  }

   public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   PerformanceMonitorValue[] pmValueArray = null;
   DOMOutputter dout = new DOMOutputter();
  try{
   pmSession = ac.getPMRemote();
   if(elm.getName().equalsIgnoreCase("tryCreatePerformanceMonitorsByValuesRequest")){
    org.jdom.Element elm1 = elm.getChild("pmValues");
    java.util.List lst1 = elm1.getChildren();
    Iterator it = lst1.iterator();
    for(int i=0;i<lst1.size();i++){
    while(it.hasNext())
	{

          org.jdom.Element elm2 = (org.jdom.Element)it.next();
          org.w3c.dom.Element elmDom = dout.output(elm2);
          pmValueArray[i] = (PerformanceMonitorValue)PerformanceMonitorValueXmlTranslator.fromXml(elmDom, PerformanceMonitorValue.class.getName());

            }
          }

   PerformanceMonitorKeyResult[] pmKeyResultArray = pmSession.tryCreatePerformanceMonitorsByValues(pmValueArray);

   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;

  XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<pmKeyResultArray.length; k++){
   xmlResponse = mevXmlSerializer.toXml( pmKeyResultArray[k] , PerformanceMonitorKeyResult.class.getName() );
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