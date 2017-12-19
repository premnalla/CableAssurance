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

import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import javax.oss.*;
import javax.oss.fm.monitor.*;
import ossj.qos.xmlri.fmxmlri.*;

import ossj.qos.fmri.*;

public class TryUnacknowledgeAlarms {


String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <fm:tryUnacknowledgeAlarmsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\">\n<fm:unacknowledgeAlarms>\n<fm:Item>\n<co:success>true</co:success>\n<fm:alarmKey>";
String result = null;
String excption = null;
String endResponse = "</fm:alarmKey></fm:Item></fm:unacknowledgeAlarms> </fm:tryUnacknowledgeAlarmsResponse>";
String beginExpection1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryUnacknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:illegalArgumentException><co:message>";
String endException1 = "</co:message></fm:illegalArgumentException> </fm:tryUnacknowledgeAlarmsException>";

String beginExpection2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryUnacknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:unsupportedOperationException><co:message>";
String endException2 = "</co:message></fm:unsupportedOperationException> </fm:tryUnacknowledgeAlarmsException>";

String beginExpection3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryUnacknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:remoteException><co:message>";
String endException3 = "</co:message></fm:remoteException> </fm:tryUnacknowledgeAlarmsException>";



  public TryUnacknowledgeAlarms() {
  }

  public String getResponse(org.jdom.Element elm){
    String xmlResponse = null;
           AlarmKey[] alarmKey = new  AlarmKey[1];
           String s1 = null;
            String s2 = null;

          try{
           ApplicationConnector ac = new ApplicationConnector();
           JVTAlarmMonitorSession fmAlarmSession = ac.getFMRemote();
           alarmKey[0] = new AlarmKeyImpl();
            //VP: System.out.println("REACHED HERE 1");
          // AlarmValue av = fmAlarmSession.makeAlarmValue();
          //VP: System.out.println(elm.getName());
          if(elm.getName().equalsIgnoreCase("tryUnacknowledgeAlarmsRequest")){
            if(elm.hasChildren()){
              List lstnew = elm.getChildren();
              Iterator itnew = lstnew.iterator();
              while(itnew.hasNext())
                  {
                org.jdom.Element tempElementnew = (org.jdom.Element)itnew.next();
                if(tempElementnew.getName().equalsIgnoreCase("ackUserId")){

                s1 = tempElementnew.getTextTrim();

                }else if(tempElementnew.getName().equalsIgnoreCase("ackSystemId")){

                s2 = tempElementnew.getTextTrim();

                }else if(tempElementnew.getName().equalsIgnoreCase("alarmReferenceList")){
                if(tempElementnew.hasChildren()){
                 List lstnew1 = tempElementnew.getChildren();
                 Iterator itnew1 = lstnew1.iterator();
                 while(itnew1.hasNext())
                {
                org.jdom.Element tempElementnew1 = (org.jdom.Element)itnew1.next();
                //VP: System.out.println(tempElementnew1.getName());
                if(tempElementnew1.getName().equalsIgnoreCase("Item")){
                org.jdom.Element elmquery = tempElementnew1;
                java.util.List children = elmquery.getChildren();
                Iterator it = children.iterator();
                org.jdom.Element child = null;

                while(it.hasNext())
                    {
                    org.jdom.Element tempElement = (org.jdom.Element)it.next();
                          String elementName = tempElement.getName();
                          String elementValue = tempElement.getTextTrim();
                    //VP: System.out.println(elementName);
                    if(elementName.equalsIgnoreCase("applicationDN"))
                          {
                              if(elementValue!=null)
                                alarmKey[0].setApplicationDN(elementValue);
                          }
                          else if(elementName.equalsIgnoreCase("type"))
                          {
                              if(elementValue!=null)
                                alarmKey[0].setType(elementValue);
                          }
                          else if(elementName.equalsIgnoreCase("alarmPrimaryKey"))
                          {
                              if(elementValue!=null)
                                alarmKey[0].setAlarmPrimaryKey(elementValue);
                                 //VP: System.out.println("REACHED HERE 2");
                          }
                        }
                        }
                        }
                        }//while ends

                }
          }
          }


           AlarmKeyResult[] alKeyResult = new AlarmKeyResult[1];

            alKeyResult =  fmAlarmSession.tryUnacknowledgeAlarms(alarmKey,s1,s2);

            //AlarmKey alKey = alKeyResult[0].getAlarmKey();

           StringBuffer sb = new StringBuffer();
           String beginItem = "<co:applicationContext><co:factoryClass></co:factoryClass><co:url></co:url><co:systemProperties><co:property><co:name></co:name><co:value></co:value></co:property></co:systemProperties></co:applicationContext><co:applicationDN></co:applicationDN><co:type></co:type><fm:alarmPrimaryKey></fm:alarmPrimaryKey>";
           //String endItem = "</Item>";
           String itemResult = null;

           //FmXmlSerializerImpl mevXmlSerializer = new FmXmlSerializerImpl("any");

           //xmlResponse = mevXmlSerializer.toXml(alKey, AlarmKey.class.getName());

            result = beginResponse + beginItem + endResponse;
            //VP: System.out.println("XML RESPONSE IS" + result);
    }
   }catch(javax.oss.IllegalArgumentException je){
     excption = je.getMessage();
     result = beginExpection1 + excption + endException1;
 }catch(RemoteException je){

     excption = je.getMessage();
     result = beginExpection3 + excption + endException3;
 } catch(javax.oss.UnsupportedOperationException je){

     excption = je.getMessage();
     //VP: System.out.println(excption);
     result = beginExpection3 + excption + endException3;
 }catch(Exception je){

     excption = je.getMessage();
     //VP: System.out.println(excption);
     result = beginExpection3 + excption + endException3;
 }

     return result;

   }

}