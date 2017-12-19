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
import javax.naming.*;
import javax.rmi.*;


import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;

import javax.oss.*;
import javax.oss.fm.monitor.*;

import ossj.qos.fmri.*;
import ossj.qos.xmlri.fmxmlri.*;

public class GetSupportedOptionalOperations {

  JVTAlarmMonitorSession fmRemote = null;
  ApplicationConnector ac = new ApplicationConnector();
 String[] supportedOptionalOperations = new String[15];

  public GetSupportedOptionalOperations() {
  }

  public String getResponse(org.jdom.Element elm){

  StringBuffer sb = new StringBuffer();
   String beginItem = "<co:item>";
   String endItem = "</co:item>";
  String itemResponse = null;


  //String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><co:getSupportedOptionalOperationsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"><co:optionalOperations>";
  String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><co:getSupportedOptionalOperationsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/Common XmlCommonSchema.xsd/\"><co:optionalOperations>";
  String result = null;
  String excption = null;
  String endResponse = "</co:optionalOperations> </co:getSupportedOptionalOperationsResponse>";
  String beginExpection = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><co:getSupportedOptionalOperationsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd> <co:remoteException>";
  String endException = "</co:remoteException> </co:getSupportedOptionalOperationsException>";
   try{

      fmRemote = ac.getFMRemote();
      supportedOptionalOperations = fmRemote.getSupportedOptionalOperations();
      //VP
	  //System.out.println("Before Result");

      for(int k=0; k<supportedOptionalOperations.length; k++){

       itemResponse = beginItem + supportedOptionalOperations[k] + endItem;
       sb.append(itemResponse);

      }
      result = xmlResponse + sb.toString() + endResponse;
      //VP
	  //System.out.println("Response is " + result);
      }catch(Exception re){
         excption = re.getMessage();
         result = beginExpection + excption + endException;
      }

   return result;
  }

}
