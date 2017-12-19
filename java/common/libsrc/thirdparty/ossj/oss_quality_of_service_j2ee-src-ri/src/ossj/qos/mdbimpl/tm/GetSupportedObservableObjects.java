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

public class GetSupportedObservableObjects {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getSupportedObservableObjectsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:supportedObservableObjects>";
String result = null;
String excption = null;
String endResponse = "</threshold:supportedObservableObjects> </threshold:getSupportedObservableObjectsResponse>";
String beginExpection = "<threshold:getSupportedObservableObjectsException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:remoteException>\n<co:message>";
String endException = "</co:message>\n</threshold:remoteException> </threshold:getSupportedObservableObjectsException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();


  public GetSupportedObservableObjects() {
  }

   public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String[] dnList = new String[100];
   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;

  try{
      tmSession = ac.getTMRemote();

  if(elm.getName().equalsIgnoreCase("getSupportedObservableObjectsRequest")){
    org.jdom.Element elm1 = elm.getChild("dnList");
    java.util.List lst1 = elm1.getChildren();
    Iterator it1 = lst1.iterator();
    for(int i=0;i<lst1.size();i++){
           dnList[i] = ((org.jdom.Element)it1.next()).getTextTrim();
         }


  String[] stArray = tmSession.getSupportedObservableObjects(dnList);
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