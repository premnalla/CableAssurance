package ossj.qos.mdbimpl.tm;
/**
 * Title:   OSS/J XML RI Ericsson
 * Description:
 * Copyright: Copyright (c) 2002
 * Company: Ericsson AB, Gothenburg, Sweden
 * @author  Hooman Tahamtani
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
import javax.oss.pm.threshold.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;

import ossj.qos.pm.threshold.*;
import ossj.qos.util.*;
import ossj.qos.xmlri.tmxmlri.*;


public class GetSupportedOptionalOperations {

StringBuffer sb = null;
JVTThresholdMonitorSession tmSession = null;
ApplicationConnector ac = null;

String beginItem = null;
String endItem = null;
String[] supportedOptionalOperations = null;
String reply = null;
String item = null;

String responseBegin = null;
String responseEnd = null;

  public GetSupportedOptionalOperations() {
    responseBegin = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <co:getSupportedOptionalOperationsResponse xmlns=\"http://www.w3.org/2001/XMLSchema\"     xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"    xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"    xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"    xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"    xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"    xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/Common XmlCommonSchema.xsd/\"> <co:optionalOperations>";
    responseEnd = "</co:optionalOperations> </co:getSupportedOptionalOperationsResponse>";
    sb = new StringBuffer();
    ac = new ApplicationConnector();
    beginItem = "<co:item>";
    endItem = "</co:item>";
  }

  public String getResponse(org.jdom.Element elm){
    try{
      tmSession = ac.getTMRemote();
      supportedOptionalOperations = tmSession.getSupportedOptionalOperations();
      for(int i = 0; i < supportedOptionalOperations.length; i++){
        item = beginItem + supportedOptionalOperations[i] + endItem;
        sb.append(item);
      }
      reply = responseBegin + sb.toString() + responseEnd;
    }
    catch (Exception e) {
      reply = doException(e.getMessage());
    }
    if(reply == null)
      reply = doException("Could not get the Supported optional operations.");
    return reply;
  }

  private String doException(String message){
    String exceptionReply = null;
    String beginException = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><co:getSupportedOptionalOperationsException xmlns=\"http://www.w3.org/2001/XMLSchema\"  xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:co=\"http://java.sun.com/products/oss/xml/Common\"  xmlns:irp=\"http://java.sun.com/products/oss/xml/Common/Util\"  xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\"  xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\"  xmlns:pmUtil=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Util\"  xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement XmlQosPmMeasurementSchema.xsd\"> <co:remoteException>\n<co:message>";
    String endException = "</co:message>\n</co:remoteException> </co:getSupportedOptionalOperationsException>";
    exceptionReply = beginException + message + endException;
    return exceptionReply;

  }
}
