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


public class SuspendThresholdMonitorByKey {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:suspendThresholdMonitorByKeyResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\">";
String result = null;
String excption = null;
String endResponse = "</threshold:suspendThresholdMonitorByKeyResponse>";
String beginExpection = "<threshold:suspendThresholdMonitorByKeyException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </threshold:suspendThresholdMonitorByKeyException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public SuspendThresholdMonitorByKey() {
  }

   public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   ThresholdMonitorKey tmKey = null;
   DOMOutputter dout = new DOMOutputter();

  try{
   tmSession = ac.getTMRemote();

  if(elm.getName().equalsIgnoreCase("suspendThresholdMonitorByKeyRequest")){
    org.w3c.dom.Element elmDom = dout.output(elm.getChild("tmKey"));
    tmKey = (ThresholdMonitorKey)ThresholdMonitorKeyXmlTranslator.fromXml(elmDom,ThresholdMonitorKey.class.getName());

     }

      tmSession.suspendThresholdMonitorByKey(tmKey);
      result = beginResponse + endResponse;


   }catch(Exception je){
    excption = je.getMessage();
    result = beginExpection + excption + endException;
   }

     return result;

   }

}