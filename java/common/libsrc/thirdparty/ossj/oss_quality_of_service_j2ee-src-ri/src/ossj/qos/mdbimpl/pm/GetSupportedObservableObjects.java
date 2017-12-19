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
 * Fixed this class, it was not correct.
 * 2002-06-11, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
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




public class GetSupportedObservableObjects {


//Fixed these, 2002-06-11
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getSupportedObservableObjectsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:supportedObservableObjects>";
String result = null;
String excption = null;
String endResponse = "</measurement:supportedObservableObjects> </measurement:getSupportedObservableObjectsResponse>";
String beginExpection = "<measurement:getSupportedObservableObjectsException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>";
String endException = "</co:remoteException> </measurement:getSupportedObservableObjectsException>";
JVTPerformanceMonitorSession pmSession = null;
ApplicationConnector ac = new ApplicationConnector();

  public GetSupportedObservableObjects() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   //Took this out, 2002-06-11
   //String[] dnList = new String[100];
   String[] dnList = null;
   java.util.List lst1 = null;

  try{
     pmSession = ac.getPMRemote();
    if(elm.getName().equalsIgnoreCase("getSupportedObservableObjectsRequest")){
      //Took this out, 2002-06-11
      //org.jdom.Element elm1 = elm.getChild("dnList");
      //Added this, 2002-06-11
      java.util.List lst = elm.getChildren();
      Iterator it = lst.iterator();
      while(it.hasNext()){
        org.jdom.Element dnlst = (org.jdom.Element) it.next();
        if(dnlst.getName().equalsIgnoreCase("dnList")){
          lst1 = dnlst.getChildren();
        }
      }

      //Took this out, 2002-06-11
      //java.util.List lst1 = elm1.getChildren();

      //Added this, 2002-06-11
      if(lst1 != null){
            Iterator it1 = lst1.iterator();
            dnList = new String[lst1.size()];
            for(int i=0;i<lst1.size();i++){
              dnList[i] = ((org.jdom.Element)it1.next()).getTextTrim();
            }

           StringBuffer sb = new StringBuffer();
           String beginItem = "<co:item>";
           String endItem = "</co:item>";
           String itemResult = null;


          String[] stArray = pmSession.getSupportedObservableObjects(dnList);
          for(int k=0; k<stArray.length; k++){
            xmlResponse = stArray[k];
            itemResult = beginItem + xmlResponse + endItem;
            sb.append(itemResult);
          }
          //Added this, 2002-06-11.  If the lenght is 0 this means that the
          //session did not find the dn, in supported objects and requested
          //object is not supported.
          if(stArray.length == 0)
            sb.append(beginItem + endItem);

          result = beginResponse + sb.toString() + endResponse;
          }
        }
        //Added this, 2002-06-11
        else{
          result = beginExpection + "Could not get children." + endException;
        }
     }catch(Exception je){
       excption = je.getMessage();
       result = beginExpection + excption + endException;
     }
     return result;
  }
}