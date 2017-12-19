package ossj.qos.mdbimpl.fm;

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
import java.rmi.RemoteException;
import java.text.ParseException;

import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;

import javax.oss.*;
import javax.oss.fm.monitor.*;
import ossj.qos.xmlri.fmxmlri.*;

import ossj.qos.fmri.*;
public class QueryAlarmCounts {

String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <fm:queryAlarmCountsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"><fm:alarmCounts>";
String result = null;
String excption = null;
String endResponse = "</fm:alarmCounts> </fm:queryAlarmCountsResponse>";

String beginExpection1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmCountsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:illegalArgumentException><co:message>";
String endException1 = "</co:message></fm:illegalArgumentException> </fm:queryAlarmCountsException>";

String beginExpection2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmCountsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:unsupportedOperationException><co:message>";
String endException2 = "</co:message></fm:unsupportedOperationException> </fm:queryAlarmCountsException>";

String beginExpection3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmCountsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:remoteException><co:message>";
String endException3 = "</co:message></fm:remoteException> </fm:queryAlarmCountsException>";


public QueryAlarmCounts() {
  }
public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;

   ApplicationConnector ac = new ApplicationConnector();
   JVTAlarmMonitorSession fmAlarmSession = ac.getFMRemote();



    try{
    QueryValue[] qval1 = new QueryValue[0];

   AlarmCountsValue alcntsval =  fmAlarmSession.queryAlarmCounts(qval1);

   FmXmlSerializerImpl serImpl = new FmXmlSerializerImpl("javax.oss.XmlSerializer");

   String temp = serImpl.toXml(alcntsval,AlarmCountsValue.class.getName());

   result = beginResponse + temp + endResponse;

 }catch(javax.oss.IllegalArgumentException je){

      excption = je.getMessage();
     result = beginExpection1 + excption + endException1;

 }catch(RemoteException je){

     excption = je.getMessage();
     result = beginExpection3 + excption + endException3;

 }catch(Exception je){

     excption = je.getMessage();
     result = beginExpection3 + excption + endException3;

 }

   return result;

}
}