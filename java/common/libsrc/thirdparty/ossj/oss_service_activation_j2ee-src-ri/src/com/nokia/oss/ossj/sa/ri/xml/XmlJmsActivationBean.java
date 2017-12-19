/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.xml;

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.service.*;
import com.nokia.oss.ossj.sa.ri.order.*;

import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.rmi.*;
import java.text.*;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class XmlJmsActivationBean extends java.lang.Object implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {
    
    private static final Map methodParameters;
    
    public static final String CO_NS = "http://www.ossj.org/Common";
    public static final String SA_NS = "http://www.ossj.org/ServiceActivation";
    public static final String RI_NS = "http://www.ossj.org/RiServiceActivation";
    
    public static final String CO_NS_PREFIX = "co:";
    public static final String SA_NS_PREFIX = "sa:";
    public static final String RI_NS_PREFIX = "ri:";
    
    public static final Class[] CoDefinedArrays = {String[].class, ManagedEntityValue[].class};
    public static final Class[] SaDefinedArrays = {OrderValue[].class, ServiceValue[].class, OrderKey[].class, ServiceKeyResult[].class };
    
    
    /**
     * Defines the format string how a java.util.Date has to be represented in xml. As the time in java.util.Date 
     * is UTC per default, and xml time zone extension (-00:00 or +00:00) defines the difference to UT, it is set
     * to zero per default.
     */
    public static final SimpleDateFormat xmlDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:hh:ss:ssss'-00:00'");
    
    static {
        methodParameters = new TreeMap();
        methodParameters.put("getOrdersByKeysRequest", new Object[] {OrderKey[].class, String[].class});
        methodParameters.put("getOrdersByTemplatesRequest", new Object[] {OrderValue[].class, String[].class});
    }
    
    private MessageDrivenContext myContext;
    private DocumentBuilder myDocumentBuilder;
    private JVTActivationSession myJvtSession;
    private JmsSender myJmsSender;
    private Context myNamingContext;
    
    private BeaTrace myLog;
    private boolean isLoggingEnabled = false;
    
    /** Holds value of property xmlCodec. */
    private XmlCoDec xmlCodec;
    
    /** Creates new XmlJmsActivationBean */
    public XmlJmsActivationBean() {
    }
    
    public void ejbCreate () throws CreateException {
		//VP
		try {
			myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void onMessage(javax.jms.Message message) {
        if (isLoggingEnabled)
            myLog.logMethodEntry("onMessage", new Object[][] {
                {"message", message}
            });
            
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                try {
                    String text = tm.getText();
                    Document request = getDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes()));
                    OssjXmlMessage response = process(request);
                    sendResponse(message, response);
                } catch(RemoteException re) {
                    myLog.logException(re);
                } catch(JMSException ex) {
                    myLog.logException(ex);
                } catch(SAXException se) {
                    myLog.logException(se);
                } catch(IOException ioe) {
                    myLog.logException(ioe);
                } catch(Exception e) {
                    myLog.logException(e);
                }
            }
            
            if (isLoggingEnabled)
                myLog.logMethodExit("onMessage", null);
    }
    
    public void setMessageDrivenContext(javax.ejb.MessageDrivenContext messageDrivenContext) throws javax.ejb.EJBException {
        myContext = messageDrivenContext;
        //VP isLoggingEnabled = ((java.lang.Boolean)lookup("java:comp/env/loggingEnabled")).booleanValue();
        isLoggingEnabled = ((java.lang.Boolean)lookup("loggingEnabled")).booleanValue();
        createLog("OSS/J SA Xml/Jms");
    }
    
    private DocumentBuilder getDocumentBuilder() {
        if (myDocumentBuilder==null) {
            try {
                myDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException pce) {
                System.out.println(pce.toString());
            }
        }
        return myDocumentBuilder;
    }
    
    private Object lookup(String name) {
        try {
            if (myNamingContext==null) {
                //VP myNamingContext = new InitialContext();
                myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
            }
            return myNamingContext.lookup(name);
        } catch (NamingException ne) {
            myLog.logException(ne);
        }
        return null;
    }
    
    private JVTActivationSession getJVTSession() {
        if (myJvtSession==null) {
            try {
                //VP JVTActivationHome aSessionHome = (JVTActivationHome)lookup("java:comp/env/ejb/JVTActivationSession");
                JVTActivationHome aSessionHome = (JVTActivationHome)lookup("ejb/JVTActivationSession");
                myJvtSession = aSessionHome.create();
            } catch (CreateException ce) {
                myLog.logException(ce);
            } catch (RemoteException re) {
                myLog.logException(re);
            }
        }
        return myJvtSession;
    }
    
    private JmsSender getJmsSender() {
        if (myJmsSender == null) {
            try {
                //VP JmsSenderHome aHome = (JmsSenderHome)lookup("java:comp/env/ejb/JmsSender");
                JmsSenderHome aHome = (JmsSenderHome)lookup("ejb/JmsSender");
                myJmsSender = aHome.create();
            } catch (RemoteException re) {
                myLog.logException(re);
            } catch (CreateException ce) {
                myLog.logException(ce);
            }
        }
        return myJmsSender;
    }
    
    
    
    private OssjXmlMessage process(Document doc) throws SAXException, IOException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("process", new Object[][] {
                {"doc", doc}
            });
            
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            try {
                Method requestMethod = getMethod(rootName);
                OssjXmlMessage response = call(requestMethod, rootElement);
                
                if (isLoggingEnabled)
                    myLog.logMethodExit("process", new Object[] {"response", response});
                    return response;
            } catch (IllegalAccessException iae) {
                myLog.logException(iae);
            }
            
            OssjXmlMessage nullString = null;
            if (isLoggingEnabled)
                myLog.logMethodExit("process", new Object[] {"nullString", nullString});
                return nullString;
    }
    
    private Method getMethod(String rootName) {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getMethod", new Object[][] {
                {"rootName",rootName }
            });
            
            Method[] allMethods = JVTActivationSession.class.getMethods();
            Method requestMethod = null;
            String requestMethodName;
            int byPos = rootName.lastIndexOf("By");
            boolean uniqueMethod = (byPos==-1);
            if (uniqueMethod) {
                requestMethodName = rootName.substring(0, rootName.lastIndexOf("Request"));
            } else {
                requestMethodName = rootName.substring(0, byPos);
            }
            myLog.log("searching for "+(uniqueMethod?"unique":"overloaden")+" method "+requestMethodName+".");
            Object[] params = (Object[])methodParameters.get(rootName);
            for (int i=0 ; i<allMethods.length && requestMethod==null; i++) {
                if (allMethods[i].getName().equals(requestMethodName)) {
                    if (uniqueMethod) {
                        requestMethod = allMethods[i];
                        myLog.log("found method "+requestMethod.getName());
                    } else {
                        if (allMethods[i].getParameterTypes().equals(params)) {
                            requestMethod = allMethods[i];
                            myLog.log("found method "+requestMethod.getName());
                        }
                    }
                }
            }
            
            if (isLoggingEnabled)
                myLog.logMethodExit("getMethod", new Object[] {"requestMethod", requestMethod});
                return requestMethod;
    }
    
    private OssjXmlMessage call(Method request, Element rootElement) throws IllegalAccessException, SAXException, IOException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("call", new Object[][] {
                {"request",request },
                {"rootElement", rootElement}
            });
            
        Object returnParameter = null;

        try {
            Object[] parameters = decodeRequest(request, rootElement);
            myLog.log("invoking method on JVT Session: "+request.getName());
            returnParameter = request.invoke(getJVTSession(), parameters);
        } catch (InvocationTargetException ite) {
            Throwable aThrowable = ite.getTargetException();

            Class[] definedExceptions = request.getExceptionTypes();
            for (int i=0; i<definedExceptions.length && returnParameter == null; i++) {
                if (definedExceptions[i].equals(aThrowable.getClass())) {
                    // only use defined exceptions as return parameter, otherwise illegal xml documents may get produced
                    returnParameter = aThrowable;
                }
            }
            if (returnParameter == null) {
                returnParameter = new RemoteException("", aThrowable);
            }
        } catch (javax.oss.IllegalArgumentException iae) {
            returnParameter = iae;
        }

        OssjXmlMessage message = encodeResponse(rootElement, returnParameter);
        if (isLoggingEnabled)
            myLog.logMethodExit("call", new Object[] {"message", message});

            return message;
    }
    
    private Object[] decodeRequest(Method requestMethod, Element rootElement) throws SAXException, RemoteException, javax.oss.IllegalArgumentException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("decodeRequest", new Object[][] {
                {"requestMethod", requestMethod},
                {"rootElement", rootElement}
            });
            
        
        Class[] parameterTypes = requestMethod.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        
        String requestName = rootElement.getTagName();
        
        if (requestName.equals("getServiceTypesRequest")) {
            
            // no parameters to decode
            
        }
        
        else if (requestName.equals("getOrderTypesRequest")) {
            
            // no parameters to decode
            
        }
        
        else if (requestName.equals("makeServiceValueRequest")) {
            
            parameters[0] = getXmlCodec().decodeParameter(null, rootElement, "serviceType", parameterTypes[0]);
            
        }
        
        else if (requestName.equals("makeOrderValueRequest")) {

            parameters[0] = getXmlCodec().decodeParameter(null, rootElement, "orderType", parameterTypes[0]);
        
        }
        
        else if (requestName.equals("createOrderRequest")) {

            // type is determined by xsi:type attribute
            parameters[0] = getXmlCodec().decodeParameter(null, rootElement, "orderValue");
        
        }
        
        else if (requestName.equals("startOrderRequest")) {

            // type is determined by xsi:type attribute
            parameters[0] = getXmlCodec().decodeParameter(null, rootElement, "orderKey", parameterTypes[0]);
        
        }

        if (isLoggingEnabled)
            myLog.logMethodExit("decodeRequest", new Object[] {"parameters", parameters});
        return parameters;
        
    }
        
    private OssjXmlMessage encodeResponse(Element rootElement, Object returnParameter) throws SAXException, IOException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("encodeResponse", new Object[][] {
                {"rootElement", rootElement},
                {"returnParameter", returnParameter}
            });
            
            boolean isException = false;
            if (returnParameter instanceof Exception) {
                isException = true;
            }
            
            OssjXmlMessage responseMessage = new OssjXmlMessage();
            
            if (isException) {
                responseMessage.setType(OssjXmlMessage.EXCEPTION_TYPE);
            } else {
                responseMessage.setType(OssjXmlMessage.RESPONSE_TYPE);
            }
            
            String request = rootElement.getTagName();
            String templateName = getTemplateName(request, returnParameter instanceof Exception);
            // set name as message property later
            responseMessage.setName(templateName);
            Document template = getXmlCodec().getXmlTemplate(templateName);
            Element root = template.getDocumentElement();
            
            if (isException) {
                
                String tagName = returnParameter.getClass().getName();
                int lastDot = tagName.lastIndexOf(".");
                tagName = "co:"+tagName.substring(lastDot+1, tagName.length());
                
                getXmlCodec().encodeParameter(template, root, tagName, returnParameter);
                
            } else if (request.equals("createOrderRequest")) {
                
                getXmlCodec().encodeParameter(template, root, "orderKey", OrderKey.class, returnParameter);
                
            } else if (request.equals("getOrderTypesRequest")) {
                
                getXmlCodec().encodeParameter(template, root, "orderTypes", returnParameter);
                
            } else if (request.equals("getServiceTypesRequest")) {
                
                getXmlCodec().encodeParameter(template, root, "serviceTypes", returnParameter);
                
            } else if (request.equals("makeOrderValueRequest")) {

                getXmlCodec().encodeParameter(template, root, "orderValue", OrderValue.class, returnParameter);
                
            } else if (request.equals("makeServiceValueRequest")) {

                getXmlCodec().encodeParameter(template, root, "serviceValue", RiServiceValue.class, returnParameter);

            } else if (request.equals("startOrderRequest")) {
            }
            
            responseMessage.setMessage( getXmlCodec().toString(template) );
            if (isLoggingEnabled)
                myLog.logMethodExit("encodeResponse", new Object[] {"responseMessage", responseMessage});
                return responseMessage;
    }

    private void cleanElement(Element anElement) {
        NodeList list = anElement.getChildNodes();
        for (int i=list.getLength()-1; i>=0; i--) {
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                myLog.log("removing item "+list.item(i).getNodeName()+" from element");
                anElement.removeChild(list.item(i));
            }
        }
    }
    
    private Node getNode(Node parentNode, String name) {
        NodeList list = parentNode.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            myLog.log("found item "+list.item(i).getNodeName()+" to compare it to "+name);
            if (list.item(i).getLocalName().equals(name)) {
                return list.item(i);
            }
        }
        return null;
    }
    
    private void sendResponse(javax.jms.Message message, OssjXmlMessage response) throws RemoteException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("sendResponse", new Object[][] {
                {"message", message},
                {"response", response}
            });
            
            try {
                String id = message.getJMSCorrelationID();
                if (id==null ||id == "") {
                    id = message.getJMSMessageID();
                }
                
                response.setId(id);
                
                getJmsSender().sendXmlReply((new SaDestination(message.getJMSReplyTo())), response);
            } catch (JMSException jmse) {
                System.out.println(jmse.toString());
            }
            
            if (isLoggingEnabled)
                myLog.logMethodExit("sendResponse", null);
    }
    
    private void createLog(String subSystem) {
        myLog = null;
        String loggingVersion = "";
        // First, try to use WLS 6.0 facility
        
        try {
            Class wls6Log = Class.forName("weblogic.logging.NonCatalogLogger");
            // if there is no exception thrown, this bean is running under WLS6, so that logging
            // facility can be used
            myLog = (BeaTrace)Class.forName("com.nokia.oss.ossj.common.ri.BeaTrace60").newInstance();
            loggingVersion = "WebLogic 6.0 logging classes";
        } catch (ClassNotFoundException cnfe) {
            //VP System.out.println(cnfe.toString());
        } catch (InstantiationException ie) {
            //VP System.out.println(ie.toString());
        } catch (IllegalAccessException iae) {
            //VP System.out.println(iae.toString());
        }
        if (myLog == null) {
            // if this is not WLS6.0, simulate logging
            myLog = new BeaTraceSimulator();
            loggingVersion = "Bea logging simulator";
        }
        myLog.setSubSystem(subSystem);
        if (isLoggingEnabled) myLog.log("Created new logger, using "+loggingVersion);
    }
    
    private String getTemplateName(String name, boolean isException) {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getTemplateName", new Object[][] {
                {"name", name},
                {"isException", new Boolean(isException)}
            });
            
            int end = name.lastIndexOf("By");
            if (end==-1) {
                end = name.lastIndexOf("Request");
            }
            StringBuffer templateNameBuf = new StringBuffer(name.substring(0, end));
            templateNameBuf.append(isException?"Exception":"Response");
            String templateName = templateNameBuf.toString();
            
            if (isLoggingEnabled)
                myLog.logMethodExit("getTemplateName", new Object[] {"templateName", templateName});
                return templateName;
    }
    
    private Document getResponseTemplate(String templateName) throws SAXException, IOException {
        if (isLoggingEnabled)
            myLog.logMethodEntry("getResponseTemplate", new Object[][] {
                {"templateName", templateName}
            });
            
            Document response = getXmlCodec().getXmlTemplate(templateName);
            
            if (isLoggingEnabled)
                myLog.logMethodExit("getResponseTemplate", new Object[] {"response", response});
                return response;
    }
    
    
    private static final void main(String[] args) {
        InputStream docIS = XmlJmsActivationBean.class.getClassLoader().getResourceAsStream(args[0]);
        System.out.println(docIS);
        
    }
    
    /** Getter for property xmlCodec.
     * @return Value of property xmlCodec.
 */
    public XmlCoDec getXmlCodec() {
        if (xmlCodec == null) {
            try {
                //VP XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("java:comp/env/ejb/XmlCoDec");
                XmlCoDecHome aCodecHome = (XmlCoDecHome)lookup("ejb/XmlCoDec");
                xmlCodec = aCodecHome.create();
            } catch (CreateException ce) {
                myLog.logException(ce);
            } catch (java.rmi.RemoteException re) {
                myLog.logException(re);
            }
        }
        return xmlCodec;
    }
    
    /** Setter for property xmlCodec.
     * @param xmlCodec New value of property xmlCodec.
 */
    public void setXmlCodec(XmlCoDec xmlCodec) {
        this.xmlCodec = xmlCodec;
    }
    
}
