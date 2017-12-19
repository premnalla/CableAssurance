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


public class GetPerformanceMonitorByKey {


//Hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getPerformanceMonitorByKeyResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:pmValue>";
String result = null;
String excption = null;
String endResponse = "</measurement : pmValue> </measurement:getPerformanceMonitorByKeyResponse>";
String beginExpection = "<measurement:getPerformanceMonitorByKeyException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getPerformanceMonitorByKeyException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetPerformanceMonitorByKey() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   PerformanceMonitorKey pmKey = null;
   String howMany = null;
   String[] attributes = new String[25];
   DOMOutputter dout = new DOMOutputter();
  try{
   pmSession = ac.getPMRemote();
   if(elm.getName().equalsIgnoreCase("getPerformanceMonitorByKeyRequest")){
    java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext())
	{

          org.jdom.Element elm1 = (org.jdom.Element)it.next();
            if(elm1.getName().equalsIgnoreCase("howMany")){
               howMany= elm1.getTextTrim();
                          }
            if(elm1.getName().equalsIgnoreCase("pmKey")){
            org.w3c.dom.Element elmDom = dout.output(elm1);
              pmKey = (PerformanceMonitorKey)PerformanceMonitorKeyXmlTranslator.fromXml(elmDom,PerformanceMonitorKey.class.getName());
              }
            if(elm1.getName().equalsIgnoreCase("attributes")){
                java.util.List attr = elm1.getChildren();
              Iterator it2 = attr.iterator();
              for(int j=0; j<attr.size(); j++){
              while(it2.hasNext()){
                attributes[j] = ((org.jdom.Element)it.next()).getTextTrim();
               }
              }

              }
         }

   PerformanceMonitorValue pmValue = pmSession.getPerformanceMonitorByKey(pmKey,attributes);
    XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
   xmlResponse = mevXmlSerializer.toXml(pmValue, PerformanceMonitorValue.class.getName() );
   result = beginResponse + xmlResponse + endResponse;
    }
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;
   }
     return result;
   }


}