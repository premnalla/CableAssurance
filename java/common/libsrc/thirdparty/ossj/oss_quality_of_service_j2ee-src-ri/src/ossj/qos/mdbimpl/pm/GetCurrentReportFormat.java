package ossj.qos.mdbimpl.pm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.,
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
import javax.oss.pm.measurement.*;
import javax.oss.pm.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;



public class GetCurrentReportFormat {


      //Hooman
      String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:getCurrentReportFormatResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:currentReportFormat>";
      String result = null;
      String excption = null;
      String endResponse = "</measurement:currentReportFormat> </measurement:getCurrentReportFormatResponse>";
      String beginExpection = "<measurement:getCurrentReportFormatException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd> <co:remoteException>";
      String endException = "</co:remoteException> </measurement:getCurrentReportFormatException>";

      JVTPerformanceMonitorSession pmSession = null;
      ApplicationConnector ac = new ApplicationConnector();

  public GetCurrentReportFormat() {
  }

  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
//Hooman
//System.out.println("element is = " + elm.getName());
  try{
      pmSession = ac.getPMRemote();
//Hooman
    if(elm.getName().equalsIgnoreCase("getCurrentReportFormat") || elm.getName().equalsIgnoreCase("getCurrentReportFormatRequest")){
//System.out.println("element passed the check........");
         ReportFormat rf = pmSession.getCurrentReportFormat();
        XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");
          xmlResponse = mevXmlSerializer.toXml( rf, ReportFormat.class.getName());
           result = beginResponse + xmlResponse + endResponse;
//System.out.println("the result is ***************");
//System.out.println(result);
//System.out.println("end ***************");
    }

   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + endException;
   }

     return result;

   }


}
