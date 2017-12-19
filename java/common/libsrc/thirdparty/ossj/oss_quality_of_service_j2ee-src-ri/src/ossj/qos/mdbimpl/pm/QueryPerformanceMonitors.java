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

public class QueryPerformanceMonitors {

//hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:queryPerformanceMonitorsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:performanceMonitors>";
String result = null;
String excption = null;
String endResponse = "</measurement : performanceMonitors> </measurement:queryPerformanceMonitorsResponse>";
String beginExpection = "<measurement:queryPerformanceMonitorsException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:queryPerformanceMonitorsException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public QueryPerformanceMonitors() {
  }

   public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   QueryPerformanceMonitorValue qval = null;
   String attrArray[] = new String[100];
   DOMOutputter dout = new DOMOutputter();
   int howMany;

  try{
   pmSession = ac.getPMRemote();
   if(elm.getName().equalsIgnoreCase("queryPerformanceMonitorsRequest")){
    java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext())
	{

          org.jdom.Element elm1 = (org.jdom.Element)it.next();
            if(elm1.getName().equalsIgnoreCase("howMany")){
              howMany = Integer.parseInt(elm1.getTextTrim());
              }
            if(elm1.getName().equalsIgnoreCase("query")){


                  org.w3c.dom.Element elmDom = dout.output(elm1);
                   qval = (QueryPerformanceMonitorValue)QueryPerformanceMonitorValueXmlTranslator.fromXml(elmDom,QueryPerformanceMonitorValue.class.getName());


              }
            if(elm1.getName().equalsIgnoreCase("attrNames")){
              java.util.List attr = elm1.getChildren();
              Iterator it2 = attr.iterator();
              for(int j=0; j<attr.size(); j++){
              while(it2.hasNext()){
                attrArray[j] = ((org.jdom.Element)it.next()).getTextTrim();
              }
              }
              }
         }


   PerformanceMonitorValueIterator pmValueIterator = pmSession.queryPerformanceMonitors(qval,attrArray);
   PerformanceMonitorValue[] pmValueArray = pmValueIterator.getNextPerformanceMonitors(100);

   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;


  XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<pmValueArray.length; k++){
   xmlResponse = mevXmlSerializer.toXml( pmValueArray[k] , PerformanceMonitorValue.class.getName() );
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