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


public class TryAcknowledgeAlarms {


String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <fm:tryAcknowledgeAlarmsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\">\n<fm:acknowledgeAlarms>\n<fm:Item>\n<co:success>false</co:success>\n<fm:alarmKey>";
String allSuccessBeginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <fm:tryAcknowledgeAlarmsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\">\n<fm:acknowledgeAlarms>\n<fm:Item>\n<co:success>true</co:success>\n<fm:alarmKey>\n<co:applicationContext>\n<co:factoryClass />\n<co:url />\n<co:systemProperties>\n<co:property>\n<co:name />\n<co:value />\n";
String result = null;
String excption = null;
String endResponse = "</fm:alarmKey></fm:Item></fm:acknowledgeAlarms> </fm:tryAcknowledgeAlarmsResponse>";
String allSuccessEndResponse = "</co:property>\n</co:systemProperties>\n</co:applicationContext>\n<co:applicationDN>\n</co:applicationDN>\n<co:type>\n</co:type>\n<fm:alarmPrimaryKey>\n</fm:alarmPrimaryKey>\n</fm:alarmKey>\n</fm:Item>\n</fm:acknowledgeAlarms>\n</fm:tryAcknowledgeAlarmsResponse>";
String beginExpection1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryAcknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:illegalArgumentException><co:message>";
String endException1 = "</co:message></fm:illegalArgumentException> </fm:tryAcknowledgeAlarmsException>";

String beginExpection2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryAcknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:unsupportedOperationException><co:message>";
String endException2 = "</co:message></fm:unsupportedOperationException> </fm:tryAcknowledgeAlarmsException>";

String beginExpection3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:tryAcknowledgeAlarmsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:remoteException><co:message>";
String endException3 = "</co:message></fm:remoteException> </fm:tryAcknowledgeAlarmsException>";


  public TryAcknowledgeAlarms() {
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
          // AlarmValue av = fmAlarmSession.makeAlarmValue();
          //VP
		  //System.out.println(elm.getName());
          if(elm.getName().equalsIgnoreCase("tryAcknowledgeAlarmsRequest")){
            if(elm.hasChildren()){
              List lstnew = elm.getChildren();
              Iterator itnew = lstnew.iterator();
              while(itnew.hasNext())
                  {
                org.jdom.Element tempElementnew = (org.jdom.Element)itnew.next();
                //VP
				//System.out.println("Element is: " + tempElementnew.getName());
                if(tempElementnew.getName().equalsIgnoreCase("ackUserId")){

                s1 = tempElementnew.getTextTrim();

                }else if(tempElementnew.getName().equalsIgnoreCase("ackSystemId")){

                s2 = tempElementnew.getTextTrim();

                }else if(tempElementnew.getName().equalsIgnoreCase("alarmReferenceList")){

                 List lstnew1 = tempElementnew.getChildren();
                 Iterator itnew1 = lstnew1.iterator();
                 while(itnew1.hasNext())
                {
                org.jdom.Element tempElementnew1 = (org.jdom.Element)itnew1.next();
                //VP
				//System.out.println(tempElementnew1.getName());
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

                    if(elementName.equalsIgnoreCase("applicationDN"))
                          {
                                alarmKey[0].setApplicationDN(elementValue);
                          }
                          else if(elementName.equalsIgnoreCase("type"))
                          {
                                alarmKey[0].setType(elementValue);
                          }
                          else if(elementName.equalsIgnoreCase("alarmPrimaryKey"))
                          {
                                alarmKey[0].setAlarmPrimaryKey(elementValue);
                          }
                        }
                        }
                        }//while ends

                }
          }
          }


           AlarmKeyResult[] alKeyResult = new AlarmKeyResult[1];
            alKeyResult =  fmAlarmSession.tryAcknowledgeAlarms(alarmKey,s1,s2);
            //VP
			//System.out.println("alKeyReslult, length ls : " + alKeyResult.length);

            if(alKeyResult != null){
              //VP
			  //System.out.println("alKeyReslult not null and is : " + alKeyResult.toString());
            }
            if(alKeyResult == null){
              //VP
			  //System.out.println("alKeyReslult is Null");
            }
            //VP
			//System.out.println("Got Alarm key results.");
            //System.out.println("Alarm Key is: " + alKeyResult[0].getAlarmKey().toString());
            //hoomn
            AlarmKey alKey = null;
            if(alKeyResult.length > 0)  // some alarms were not ackd.
              alKey = alKeyResult[0].getAlarmKey();  // if it is null all alrms were acked. (Hooman).
            //VP: if(alKey == null)
			//VP: System.out.println("Key result returned null, alKey is null");
            //VP: else
			//VP: System.out.println("alKey is not null and it is: " + alKey.toString());

           StringBuffer sb = new StringBuffer();
           String beginItem = "<Item>";
           String endItem = "</Item>";
           String itemResult = null;
           if(alKey != null) {
             FmXmlSerializerImpl mevXmlSerializer = new FmXmlSerializerImpl("any");

             xmlResponse = mevXmlSerializer.toXml(alKey, AlarmKey.class.getName());
             //VP
			 //System.out.println("Alarm Key not null, Got response from serializer....");
             result = beginResponse + xmlResponse + endResponse;
            }
            else {
              result = allSuccessBeginResponse + allSuccessEndResponse;
            }
            //VP
			//System.out.println("XML RESPONSE IS" + result);
    }
   }catch(javax.oss.IllegalArgumentException je){
     excption = je.getMessage();
     result = beginExpection1 + excption + endException1;
 }catch(RemoteException je){

     excption = je.getMessage();
     result = beginExpection3 + excption + endException3;
     //VP
	 //System.out.println("Remote Exception : ");
     je.printStackTrace();
 }catch(Exception je){
     excption = je.getMessage();
     //VP
	 //System.out.println(excption);
     result = beginExpection3 + excption + endException3;
     //VP
	 //System.out.println("Exception : ");
     je.printStackTrace();

 }

     return result;

   }

}
