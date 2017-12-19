package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani, Ericsson AB.
 * @version 1.0
 *
 * Fixed up this class, it was not correct.  2002-06-11, Hooman Tahamtani.
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


public class GetObservableObjectClasses {

//The tags below were wrong, so I fixed them.  Hooman
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getObservableObjectClassesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\">\n";
String result = null;
String excption = null;
String endResponse = "</measurement:getObservableObjectClassesResponse>";
String beginExpection = "<measurement:getObservableObjectClassesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:remoteException>\n<co:message>";
String endException = "</co:message>\n</measurement:remoteException> </measurement:getObservableObjectClassesException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetObservableObjectClasses() {
  }


  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String howMany = null;

  try{
   pmSession = ac.getPMRemote();
  if(elm.getName().equalsIgnoreCase("getObservableObjectClassesRequest")){
    //howMany = elm.getChild("co:howMany").getTextTrim();

    //this is a work around for elm.getChild, there seems to be a bug in Jdom (beta -8), the above
    //was always returning null;  Hooman Tahamtani, Ericsson AB, Gothenburg, Sweden.
    if(elm.hasChildren()){
        List list = elm.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext())
        {
          org.jdom.Element el = ((org.jdom.Element) it.next());
          if(el.getName().equalsIgnoreCase("howMany")){
            howMany = el.getTextTrim();
            //howMany = howMany.trim();
          }
        }
    }
  }
  ObservableObjectClassIterator obClassIterator = pmSession.getObservableObjectClasses();
    String[] observableObjectClasses = obClassIterator.getNext(Integer.parseInt(howMany));

   StringBuffer sb = new StringBuffer();
   String beginItem = "<co:item>";
   String endItem = "</co:item>";
   String itemResult = null;
   //The tags below were missing, so I added them.
   String beginSeq = "<co:sequence>";
   String endSeq = "</co:sequence>";
   String beginEOR = "<co:endOfReply>";
   String endEOR = "</co:endOfReply>";
   String measClassesBegin = "<measurement:classes>";
   String measClassesEnd = "\n</measurement:classes>";

   //Start building the response.
   String seq = beginSeq + observableObjectClasses.length + endSeq + beginEOR + "true" + endEOR + measClassesBegin;
   sb.append(seq);

   //I don't know why telegea has this here, the mevXmlSerializer is not used, so I took it out.
   //Hooman Tahamtani, Ericsson AB, Gothenburg, Sweden.

   //XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");

   for(int k=0; k<observableObjectClasses.length; k++){
   xmlResponse =  observableObjectClasses[k] ;
   itemResult = beginItem + xmlResponse + endItem;
   sb.append(itemResult);
   }
   sb.append(measClassesEnd);
   result = beginResponse + sb.toString() + endResponse;


   }catch(Exception je){
       excption = je.getMessage();
     result = beginExpection + excption + endException;
    je.printStackTrace();

   }

     return result;

   }
}