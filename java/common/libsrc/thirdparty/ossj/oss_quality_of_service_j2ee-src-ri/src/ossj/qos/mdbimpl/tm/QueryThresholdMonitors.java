package ossj.qos.mdbimpl.tm;

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
import javax.oss.pm.threshold.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;

import ossj.qos.pm.threshold.*;
import ossj.qos.util.*;
import ossj.qos.xmlri.tmxmlri.*;

//import ossj.qos.ri.pm.threshold.adapter.*;


public class QueryThresholdMonitors {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:queryThresholdMonitorsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:thresholdMonitorsByValuess>";
String result = null;
String excption = null;
String endResponse = "</threshold:thresholdMonitorsByValuess> </threshold:queryThresholdMonitorsResponse>";
String beginExpection = "<threshold:queryThresholdMonitorsException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </threshold:queryThresholdMonitorsException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public QueryThresholdMonitors() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   QueryByMonitorValue qval = null;
   String attrArray[] = new String[100];
   DOMOutputter dout = new DOMOutputter();
   int howMany;

  try{
       tmSession = ac.getTMRemote();
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
                   qval = (QueryByMonitorValue)QueryByMonitorValueXmlTranslator.fromXml(elmDom,QueryByMonitorValue.class.getName());
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


   ThresholdMonitorValueIterator tmValueIterator = tmSession.queryThresholdMonitors(qval,attrArray);
   ThresholdMonitorValue[] tmValueArray = tmValueIterator.getNextThresholdMonitors(100);
   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;


   XmlSerializer mevXmlSerializer = (XmlSerializer)new TmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<tmValueArray.length; k++){
   xmlResponse = mevXmlSerializer.toXml( tmValueArray[k] , ThresholdMonitorValue.class.getName() );
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