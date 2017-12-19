package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 * @version 1.0
 *
 * Fixed this class also, this was not working, the response was not correct.
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
//import ossj.qos.ri.measurement.adapter.*;



public class GetObservableAttributes {


//Fixed these, the tags were wrong.
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getObservableAttributesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:observableAttributes>";
String result = null;
String excption = null;
String endResponse = "</measurement:observableAttributes> </measurement:getObservableAttributesResponse>";
String beginExpection = "<measurement:getObservableAttributesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:remoteException>\n<co:message>";
String endException = "</co:message>\n</measurement:remoteException> </measurement:getObservableAttributesException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();


  public GetObservableAttributes() {
  }

public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String observableObjectClassName = null;
   StringBuffer sb = new StringBuffer();

  try{
   pmSession = ac.getPMRemote();
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

  PerformanceAttributeDescriptor[] paDescriptor = pmSession.getObservableAttributes(observableObjectClassName);
  XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
   for(int k=0; k<paDescriptor.length; k++){
    xmlResponse = mevXmlSerializer.toXml(paDescriptor[k],PerformanceAttributeDescriptor.class.getName() );
    sb.append("<measurement:Item>" + xmlResponse + "</measurement:Item>");
      }

   result = beginResponse + sb.toString() + endResponse;
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;
   }

     return result;

   }


}