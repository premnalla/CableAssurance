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

public class GetSupportedGranularities {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getSupportedGranularitiesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:supportedGranularities>";
String result = null;
String excption = null;
String endResponse = "</threshold:supportedGranularities> </threshold:getSupportedGranularitiesResponse>";
String beginExpection = "<threshold:getSupportedGranularitiesException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd> <co:remoteException>";
String endException = "</co:remoteException> </threshold:getSupportedGranularitiesException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

 public GetSupportedGranularities() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   ThresholdMonitorValue tmValue1 = null;
   DOMOutputter dout = new DOMOutputter();
  try{
    tmSession = ac.getTMRemote();

  if(elm.getName().equalsIgnoreCase("getSupportedGranularitiesRequest")){
    org.jdom.Element elm1 = elm.getChild("tmValue");
     org.w3c.dom.Element elmDom = dout.output(elm1);
     tmValue1 = (ThresholdMonitorValue)ThresholdMonitorValueXmlTranslator.fromXml(elmDom,ThresholdMonitorValue.class.getName());

     StringBuffer sb = new StringBuffer();
     String beginItem = "<Item>";
     String endItem = "</Item>";
     String itemResult = null;


  int[] inArray = tmSession.getSupportedGranularities(tmValue1);

   for(int k=0; k<inArray.length; k++){

   xmlResponse = new Integer(inArray[k]).toString();
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