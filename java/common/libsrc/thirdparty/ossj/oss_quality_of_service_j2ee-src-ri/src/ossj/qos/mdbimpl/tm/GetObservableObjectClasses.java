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
 * Fixed this class, the response was wrong.
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


public class GetObservableObjectClasses {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <threshold:getObservableObjectClassesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> ";
String result = null;
String excption = null;
String endResponse = "</threshold:getObservableObjectClassesResponse>";
String beginExpection = "<threshold:getObservableObjectClassesException xmlns=\"http://www.w3.org/2001/XMLSchema\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold XmlQosPmThresholdSchema.xsd\"> <threshold:remoteException>\n<co:message>";
String endException = "</co:message>\n</threshold:remoteException> </threshold:getObservableObjectClassesException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetObservableObjectClasses() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   String howMany = null;

  try{
    tmSession = ac.getTMRemote();

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
            }
          }
        }
    }

     ObservableObjectClassIterator obClassIterator = tmSession.getObservableObjectClasses();
     String[] observableObjectClasses = obClassIterator.getNext(Integer.parseInt(howMany));

     StringBuffer sb = new StringBuffer();
     String beginItem = "<co:item>";
     String endItem = "</co:item>";
     String itemResult = null;
     //The tags below were missing, so I added them. 2002-06-15
     String beginSeq = "<co:sequence>";
     String endSeq = "</co:sequence>";
     String beginEOR = "<co:endOfReply>";
     String endEOR = "</co:endOfReply>";
     String measClassesBegin = "<threshold:classes>";
     String measClassesEnd = "\n</threshold:classes>";

     //Start building the response. 2002-06-15
     String seq = beginSeq + observableObjectClasses.length + endSeq + beginEOR + "true" + endEOR + measClassesBegin;
     sb.append(seq);

     for(int k=0; k<observableObjectClasses.length; k++){
        xmlResponse = observableObjectClasses[k];
        itemResult = beginItem + xmlResponse + endItem;
        sb.append(itemResult);
     }
      sb.append(measClassesEnd);
      result = beginResponse + sb.toString() + endResponse;
   } catch(Exception je){
      excption = je.getMessage();
      result = beginExpection + excption + endException;
   }
   return result;
   }

}