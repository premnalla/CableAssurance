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

public class GetVersion {
  //Hooman
  //String[] version = new String[10];
  String[] version = null;
  JVTAlarmMonitorSession fmRemote = null;
  ApplicationConnector ac = new ApplicationConnector();

  public GetVersion() {

  }

  public String getVersionResponse(org.jdom.Element elm){
  String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:getVersionResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd\"><fm:version> <co:item>";
  String result = null;
  String excption = null;
  String endResponse = "</co:item>  </fm:version> </fm:getVersionResponse>";
  String beginExpection = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fm:getVersionException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor XmlQosFmMonitorSchema.xsd> <co:remoteException>";
  String endException = "</co:remoteException> </fm:getVersionException>";
  String space = " ";
   try{

      fmRemote = ac.getFMRemote();
      version = fmRemote.getVersion();
      //VP
	  //System.out.println("Before Result");
      //Hooman
      //StringBuffer sb = new StringBuffer();
      result = xmlResponse + version[0]+endResponse;
      /*  Hooman
      for(int i = 0; i < version.length; i++){
        sb.append(version[i] + space);
      }
      result = xmlResponse + sb.toString().trim() +endResponse;
      //VP
	  //System.out.println("Hooman version.toString is " + version.toString());
      //VP
	  //System.out.println("Hooman version is " + version);
      //VP
	  //System.out.println("Hooman string buffer to string trimed is " + sb.toString().trim());
      //VP
	  //System.out.println("Hooman string buffer to string is " + sb.toString());
      //VP
	  //System.out.println("Response is " + result);
      */
      }catch(Exception re){
         excption = re.getMessage();
         result = beginExpection + excption + endException;
      }

   return result;
  }
}



























