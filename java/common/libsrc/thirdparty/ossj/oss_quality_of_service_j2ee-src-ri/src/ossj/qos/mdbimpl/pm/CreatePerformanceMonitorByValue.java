package ossj.qos.mdbimpl.pm;

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
import javax.oss.pm.measurement.*;
import javax.oss.pm.*;
import ossj.qos.xmlri.pmxmlri.*;

import ossj.qos.*;
import ossj.qos.pm.measurement.*;
import ossj.qos.util.*;
//import ossj.qos.ri.measurement.adapter.*;

public class CreatePerformanceMonitorByValue {

      //hooman
      String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <measurement:createPerformanceMonitorByValueResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:performanceMonitorByValue>";
      String result = null;
      String excption = null;
      String endResponse = "</measurement : performanceMonitorByValue> </measurement:createPerformanceMonitorByValueResponse>";
      String beginExpection = "<measurement:createPerformanceMonitorByValueException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <measurement:remoteException>\n<co:message>";
      String endException = "<co:message>\n</measurement:remoteException> </measurement:createPerformanceMonitorByValueException>";
      JVTPerformanceMonitorSession pmSession = null;
      ApplicationConnector ac = new ApplicationConnector();

  public CreatePerformanceMonitorByValue() {
  }
/*
<?xml version="1.0" encoding="UTF-8"?>
<measurement:createPerformanceMonitorByValueRequest xmlns="http://www.w3.org/2001/XMLSchema"
    xmlns:measurement="http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement"
    xmlns:threshold="http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold"
    xmlns:fm="http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:co="http://java.sun.com/products/oss/xml/Common"
    xmlns:irp="http://java.sun.com/products/oss/xml/Common/Util"
    xmlns:pmUtil="http://java.sun.com/products/oss/xml/QualityOfService/PM/Util"
    xsi:schemaLocation="http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd">
<measurement:pmValue>
# replace
</measurement:pmValue>
</measurement:createPerformanceMonitorByValueRequest>

*/
  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   DOMOutputter dout = new DOMOutputter();

  try{
   pmSession = ac.getPMRemote();
   PerformanceMonitorValue pmValue = new PerformanceMonitorValueImpl();
  if(elm.getName().equalsIgnoreCase("createPerformanceMonitorByValueRequest")){
       org.jdom.Element element1 = elm.getChild("pmValue");

       org.w3c.dom.Element elmDom = dout.output(element1);
       pmValue = (PerformanceMonitorValue)PerformanceMonitorValueXmlTranslator.fromXml(elmDom, PerformanceMonitorValue.class.getName());

         }

  PerformanceMonitorKey pmKey = pmSession.createPerformanceMonitorByValue(pmValue);

   XmlSerializer mevXmlSerializer = new PmXmlSerializerImpl("javax.oss.Serializer");

    xmlResponse = mevXmlSerializer.toXml( pmKey, PerformanceMonitorKey.class.getName());
    result = beginResponse + xmlResponse + endResponse;

   }catch(Exception je){
     excption = je.getMessage();
     result = beginExpection + excption + "\n***********\n" + je.toString() + endException;
   }

     return result;

   }


}