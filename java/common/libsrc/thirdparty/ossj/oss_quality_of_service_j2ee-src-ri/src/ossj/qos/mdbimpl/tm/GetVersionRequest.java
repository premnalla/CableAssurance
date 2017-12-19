package ossj.qos.mdbimpl.tm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * Fixed this class, it was not implemented correctly, the response was wrong.
 * 2002-06-15, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
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




public class GetVersionRequest {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getVersionResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:version>";
String result = null;
String excption = null;
String endResponse = "</threshold:version> </threshold:getVersionResponse>";
String beginExpection = "<threshold:getVersionException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <co:remoteException>\n<co:message>";
String endException = "<co:message>\n</co:remoteException> </threshold:getVersionException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetVersionRequest() {
  }

  public String getResponse(org.jdom.Element elm){
    String xmlResponse = null;

    try{
      tmSession = ac.getTMRemote();
      if(elm.getName().equalsIgnoreCase("getVersionRequest")){
       StringBuffer sb = new StringBuffer();
       String beginItem = "<co:item>";
       String endItem = "</co:item>";
       String itemResult = null;

       String[] stArray = tmSession.getVersion();
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