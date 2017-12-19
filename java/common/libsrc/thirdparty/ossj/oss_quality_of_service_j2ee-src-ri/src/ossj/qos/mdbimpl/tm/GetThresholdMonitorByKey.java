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


public class GetThresholdMonitorByKey {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getThresholdMonitorByKeyResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:thresholdMonitorByKey>";
String result = null;
String excption = null;
String endResponse = "</threshold:thresholdMonitorByKey> </threshold:getThresholdMonitorByKeyResponse>";
String beginExpection = "<threshold:getThresholdMonitorByKeyException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </threshold:getThresholdMonitorByKeyException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetThresholdMonitorByKey() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   ThresholdMonitorKey tmKey = null;
   String howMany = null;
   String[] attributes = new String[25];
   DOMOutputter dout = new DOMOutputter();
  try{
     tmSession = ac.getTMRemote();
  if(elm.getName().equalsIgnoreCase("threshold:getThresholdMonitorByKeyRequest")){
    java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext())
	{

          org.jdom.Element elm1 = (org.jdom.Element)it.next();
            if(elm1.getName().equalsIgnoreCase("howMany")){
               howMany= elm1.getTextTrim();
                          }
            if(elm1.getName().equalsIgnoreCase("tmKey")){
            org.w3c.dom.Element elmDom = dout.output(elm1);
              tmKey = (ThresholdMonitorKey)ThresholdMonitorKeyXmlTranslator.fromXml(elmDom,ThresholdMonitorKey.class.getName());
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

  ThresholdMonitorValue tmValue1 = tmSession.getThresholdMonitorByKey(tmKey,attributes);
  XmlSerializer mevXmlSerializer = (XmlSerializer)new TmXmlSerializerImpl("javax.oss.Serializer");
  xmlResponse = mevXmlSerializer.toXml(tmValue1, ThresholdMonitorValue.class.getName() );
  result = beginResponse + xmlResponse + endResponse;
    }
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;
   }
     return result;

   }



}