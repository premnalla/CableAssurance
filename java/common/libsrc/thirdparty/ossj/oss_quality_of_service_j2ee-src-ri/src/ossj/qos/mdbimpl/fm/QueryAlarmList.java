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

public class QueryAlarmList {


String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <fm:queryAlarmListResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\">";
String result = null;
String excption = null;
String endResponse = "</fm:queryAlarmListResponse>";

String beginExpection1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmListException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:illegalArgumentException><co:message>";
String endException1 = "</co:message></fm:illegalArgumentException> </fm:queryAlarmListException>";

String beginExpection2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmListException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:unsupportedOperationException><co:message>";
String endException2 = "</co:message></fm:unsupportedOperationException> </fm:queryAlarmListException>";

String beginExpection3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:queryAlarmListException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"     xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"   xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"> <fm:remoteException><co:message>";
String endException3 = "</co:message></fm:remoteException> </fm:queryAlarmListException>";



  public QueryAlarmList() {
  }
/*

<fm:queryAlarmListResponse>
	<co:sequence>int</co:sequence>
	<co:endOfReply>boolean</co:endOfReply>
	<fm:alarm>
		<fm:Item>
			//<co:lastUpdateVersionNumber></co:lastUpdateVersionNumber>

		</fm:Item>
	</fm:alarm>
</fm:queryAlarmListResponse>

*/



  public String getResponse(org.jdom.Element elm){
   String xmlResponse = null;
   /*QueryByFilterableAttributesValue[] qval = null;
   String attrArray[] = new String[100];
   DOMOutputter dout = new DOMOutputter();
   int howMany;*/
  try{
   ApplicationConnector ac = new ApplicationConnector();
   JVTAlarmMonitorSession fmAlarmSession = ac.getFMRemote();

   QueryValue[] qval1 = new QueryValue[0];
   String[] s = new String[0];
   AlarmValueIterator alvalitr =  fmAlarmSession.queryAlarmList(qval1, s);

   AlarmValue[] alval = alvalitr.getNextAlarmValues(100);

   FmXmlSerializerImpl serImpl = new FmXmlSerializerImpl("javax.oss.XmlSerializer");



   int n = alval.length;
   String Item = "";
   for(int i=0; i<n; i++){
   String temp = serImpl.toXml(alval[i],AlarmValue.class.getName());
   Item = Item + "<fm:Item>"+ temp +"</fm:Item>";

   }

  String temp = null;
    if(n!=0){
    temp = "<co:sequence>"+ n +"</co:sequence><co:endOfReply>true</co:endOfReply><fm:alarm>"+ Item + "</fm:alarm>";
    }else{
    temp = "<co:sequence>"+ n +"</co:sequence><co:endOfReply>true</co:endOfReply><fm:alarm><fm:Item></fm:Item></fm:alarm>";
    }
   result = beginResponse + temp + endResponse;

   //AlarmValue av = fmAlarmSession.makeAlarmValue();

 /* if(elm.getName().equalsIgnoreCase("fm:queryAlarmListRequest")){
    java.util.List children = elm.getChildren();
    Iterator it = children.iterator();

    while(it.hasNext())
	{

          org.jdom.Element elmqueryAlarmListRequest = (org.jdom.Element)it.next();
            if(elmqueryAlarmListRequest.getName().equalsIgnoreCase("howMany")){
              howMany = Integer.parseInt(elmqueryAlarmListRequest.getTextTrim());
              }
            if(elmqueryAlarmListRequest.getName().equalsIgnoreCase("query")){
              java.util.List childrenQuery = elmqueryAlarmListRequest.getChildren();
              Iterator it1 = childrenQuery.iterator();
             for(int i=0;i<childrenQuery.size();i++){
                while(it1.hasNext()){
                   org.jdom.Element elmQueryValue = (org.jdom.Element)it1.next();
                   //QueryByFilterableAttributesValue valObject = new QueryByFilterableAttributesValueImpl();

                  org.w3c.dom.Element elmDom = dout.output(elmQueryValue);
                   qval[i] = (QueryByFilterableAttributesValue)QueryByFilterableAttributesValueXmlTranslator.fromXml(elmDom,"QueryByFilterableAttributesValue");

                   }
                }
              }
            if(elmqueryAlarmListRequest.getName().equalsIgnoreCase("attributes")){
              java.util.List attr = elmqueryAlarmListRequest.getChildren();
              Iterator it2 = attr.iterator();
              for(int j=0; j<attr.size(); j++){
              while(it2.hasNext()){
                attrArray[j] = ((org.jdom.Element)it.next()).getTextTrim();
              }
              }
              }
         }

   AlarmValueIterator alValItr =  fmAlarmSession.queryAlarmList(qval,attrArray);
   AlarmValue alarmVal[] = alValItr.getNextAlarmValues(100);
   StringBuffer sb = new StringBuffer();
   String beginItem = "<Item>";
   String endItem = "</Item>";
   String itemResult = null;
   Serializer serializer = av.makeSerializer( AlarmValue.class.getName());
   XmlSerializer mevXmlSerializer = (XmlSerializer) serializer;
   mevXmlSerializer.setEncodingStyle( XmlSerializerEncodingStyles.OSS_XML_ENCODING_STYLE);
   for(int k=0; k<alarmVal.length; k++){
   xmlResponse = mevXmlSerializer.toXml( alarmVal[k] , "fm:AlarmValue" );
   itemResult = beginItem + xmlResponse + endItem;
   sb.append(itemResult);
      }

     result = beginResponse + sb.toString() + endResponse;
    }

*/
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