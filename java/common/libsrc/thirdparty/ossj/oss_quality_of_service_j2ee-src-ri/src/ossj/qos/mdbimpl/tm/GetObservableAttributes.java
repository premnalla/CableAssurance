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
 * Fixed this class, the responses were not correct.
 * 2002-06-17, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden
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

import ossj.qos.pm.threshold.*;
import ossj.qos.util.*;
import ossj.qos.xmlri.tmxmlri.*;


public class GetObservableAttributes {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getObservableAttributesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:observableAttributes>";
String result = null;
String excption = null;
String endResponse = "</threshold:observableAttributes> </threshold:getObservableAttributesResponse>";
String beginExpection = "<threshold:getObservableAttributesException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:remoteException>\n<co:message>";
String endException = "</co:message>\n</threshold:remoteException> </threshold:getObservableAttributesException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetObservableAttributes() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String observableObjectClassName = null;

  try{
  tmSession = ac.getTMRemote();
  if(elm.getName().equalsIgnoreCase("getObservableAttributesRequest")){
    //this is a work around for elm.getChild, there seems to be a bug in Jdom (beta -8), the above
    //was always returning null;  Hooman Tahamtani, Ericsson AB, Gothenburg, Sweden.
    if(elm.hasChildren()){
        List list = elm.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext())
        {
          org.jdom.Element el = ((org.jdom.Element) it.next());
          if(el.getName().equalsIgnoreCase("observableObjectClassName")){
            observableObjectClassName = el.getText();
            observableObjectClassName = observableObjectClassName.trim();
          }
        }
    }

  }
   StringBuffer sb = new StringBuffer();
   String beginItem = "<measurement:Item>";
   String endItem = "</measurement:Item>";
   String itemResult = null;

  PerformanceAttributeDescriptor[] paDescriptor = tmSession.getObservableAttributes(observableObjectClassName);
  XmlSerializer mevXmlSerializer = (XmlSerializer)new TmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<paDescriptor.length; k++){
    xmlResponse = mevXmlSerializer.toXml(paDescriptor[k], PerformanceAttributeDescriptor.class.getName());
    itemResult = beginItem + xmlResponse + endItem;
    sb.append(itemResult);
      }

    result = beginResponse + sb.toString() + endResponse;
   }catch(Exception je){
    excption = je.getMessage();
    result = beginExpection + excption + endException;
   }

     return result;

   }



}