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

import ossj.qos.pm.threshold.*;
//import ossj.qos.pm.util.*;
import ossj.qos.util.*;
import ossj.qos.xmlri.tmxmlri.*;

//import ossj.qos.ri.pm.threshold.adapter.*;

public class CreateThresholdMonitorByValue {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:createThresholdMonitorByValueResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:thresholdMonitorByValue>";
String result = null;
String excption = null;
String endResponse = "</threshold:thresholdMonitorByValue> </threshold:createThresholdMonitorByValueResponse>";
String beginExpection = "<threshold:createThresholdMonitorByValueException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </threshold:createThresholdMonitorByValueException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public CreateThresholdMonitorByValue(){
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   ThresholdMonitorValue tmValue = null;
   DOMOutputter dout = new DOMOutputter();
  try{
   tmSession = ac.getTMRemote();

  if(elm.getName().equalsIgnoreCase("createPerformanceMonitorByValueRequest")){
       org.jdom.Element element1 = elm.getChild("value");

       org.w3c.dom.Element elmDom = dout.output(element1);
       tmValue = (ThresholdMonitorValue)ThresholdMonitorValueXmlTranslator.fromXml(elmDom,ThresholdMonitorValue.class.getName());

         }

        ThresholdMonitorKey tmKey = tmSession.createThresholdMonitorByValue(tmValue);

       XmlSerializer mevXmlSerializer = (XmlSerializer)new TmXmlSerializerImpl("javax.oss.Serializer");

        xmlResponse = mevXmlSerializer.toXml(tmKey, ThresholdMonitorKey.class.getName());
        result = beginResponse + xmlResponse + endResponse;
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;
   }
     return result;

   }


}