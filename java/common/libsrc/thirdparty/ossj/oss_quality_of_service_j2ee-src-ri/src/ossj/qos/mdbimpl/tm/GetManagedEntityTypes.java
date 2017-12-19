package ossj.qos.mdbimpl.tm;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:  Ericsson AB., Gothenburg, Sweden
 * @author   Hooman Tahamtani
 * @version 1.0
 *
 * Implemented this class.  This was missing in Telegeas implementation.
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


public class GetManagedEntityTypes {
String beginResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <co:getManagedEntityTypesResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/Common XmlCommonSchema.xsd\"> <co:managedEntityTypes>";
String result = null;
String excption = null;
String endResponse = "</co:managedEntityTypes> </co:getManagedEntityTypesResponse>";
String beginExpection = "<co:getManagedEntityTypesException xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/Common XmlCommonSchema.xsd\"> <threshold:remoteException>\n<co:message>";
String endException = "</co:message>\n</threshold:remoteException> </co:getManagedEntityTypesException>";
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = new ApplicationConnector();
String response = null;
String[] METypes = null;

  public GetManagedEntityTypes() {
  }

  public String getResponse(org.jdom.Element elm){
    tmSession = ac.getTMRemote();
    String beginItem = "<co:item>";
    String endItem = "</co:item>";
    StringBuffer sb = new StringBuffer();

    if(elm.getName().equalsIgnoreCase("getManagedEntityTypesRequest")){
      try{
          METypes = tmSession.getManagedEntityTypes();
          for(int i = 0; i < METypes.length; i++){
            sb.append(beginItem + METypes[i] + endItem);
          }
          response = beginResponse + sb.toString() + endResponse;
      }
      catch(Exception e){
        response = beginExpection + e.getMessage() + endException;
      }
    }

    return response;
  }
}