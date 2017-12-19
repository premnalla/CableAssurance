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
 * Fixed this class, it was not implemented correctly.
 * 2002-06-11, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden
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
import javax.oss.pm.util.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;


public class GetObservableObjects {

//Corrected these. 2002-06-11
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getObservableObjectsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\">";
String result = null;
String excption = null;
String endResponse = "</measurement:getObservableObjectsResponse>";
String beginExpection = "<measurement:getObservableObjectsException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getObservableObjectsException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetObservableObjects() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String s1 = null;
   String s2 = null;
   String howMany = null;

  try{
   pmSession = ac.getPMRemote();
  if(elm.getName().equalsIgnoreCase("getObservableObjectsRequest")){
       java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext()){
      org.jdom.Element elm1 = (org.jdom.Element)it.next();

  /*  These does not make sense to me, if you already have an element from the
   *  iterator why would you want to search for a child,
   *  i.e. howMany.getChild(howMany), etc., so I took it out.
   *  2002-06-11.

    if(elm1.getName().equalsIgnoreCase("howMany")){
      howMany = elm.getChild("howMany").getTextTrim();
       }
    if(elm1.getName().equalsIgnoreCase("observableObjectClassName")){
      s1 = elm.getChild("observableObjectClassName").getTextTrim();
       }
    if(elm1.getName().equalsIgnoreCase("base")){
      s2 = elm.getChild("base").getTextTrim();
       }
       }
  */

      if(elm1.getName().equalsIgnoreCase("howMany")){
        howMany = elm1.getTextTrim();
      }
      else if(elm1.getName().equalsIgnoreCase("observableObjectClassName")){
        s1 = elm1.getTextTrim();
      }
      else if(elm1.getName().equalsIgnoreCase("base")){
        s2 = elm1.getTextTrim();
      }

    }

   ObservableObjectIterator obObjectIterator = pmSession.getObservableObjects(s1,s2);
   String[] observableObjects = obObjectIterator.getNext(Integer.parseInt(howMany));

   StringBuffer sb = new StringBuffer();
   //Changed these, 2002-06-11.
   String beginItem = "<co:item>";
   String endItem = "</co:item>";
   String itemResult = null;
   //Added these, 2002-06-11
   String beginEOR = "<co:endOfReply>";
   String endEOR = "</co:endOfReply>";
   sb.append(beginEOR + "true" + endEOR + "<measurement:ooDNs>");


   XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");

   for(int k=0; k<observableObjects.length; k++){
     xmlResponse =  observableObjects[k] ;
     itemResult = beginItem + xmlResponse + endItem;
     sb.append(itemResult);
   }
   //Added these, 2002-06-11
   sb.append("</measurement:ooDNs>");
   result = beginResponse + "<co:sequence>" +  observableObjects.length + "</co:sequence>" + sb.toString() + endResponse;
    }
   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;

   }

     return result;

   }

}