/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.ex;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.UpdateProcedureResponse;
import javax.oss.UpdateProcedureValue;
import ossj.common.UpdateProcedureResponseImpl;


/**
 * XVTMessageDrivenBean Implementation Class
 *
 * XVTMessageDrivenBean is a message driven bean that will be used to
 * receive XML Message requests.  The XML instance and schema for each
 * type of Request/Response/Error Message is detailed in Common spec.
 * Each type of message has been modelled to mimic the methods from
 * the JVTSession interface. Therefore all functionality
 * that can be done directly through the JVTSession
 * interface, can also be done through messaging.
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2
 * @since October 13, 2005, 2:05 PM
 */
public class XVTMessageDrivenBean implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {
    private static final boolean VERBOSE = true;
    private String messageType = "RESPONSE";
    private String messageNameProp = null;
    private javax.ejb.MessageDrivenContext context;
    private javax.xml.parsers.DocumentBuilder docBuilder;
    private JMSReplySender replySender;
    private String xvtStringReply;
    private javax.oss.JVTSession jvtSession;
    
    /**
     * Creates new XmlMessageDrivenBean
     */
    public XVTMessageDrivenBean() {
    }
    
    //====================================================
    public void setMessageDrivenContext(final javax.ejb.MessageDrivenContext messageDrivenContext)
    throws javax.ejb.EJBException {
        context = messageDrivenContext;
    }
    
    //====================================================
    // mandatory EJB methods....
    //====================================================
    public void ejbCreate() throws javax.ejb.CreateException {
        log("XVTMessageDrivenBean:ejbCreate");
        
        //create the initial context for lookups
        InitialContext initCtx = null;
        
        try {
            initCtx = new InitialContext();
        } catch (NamingException nex) {
            log(
                    "XVTMessageDrivenBean:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
            throw new javax.ejb.CreateException();
        }
        
        // get the JVT Session home
        JVTActivationHome javtActivationHome = null;
        
        try {
            javtActivationHome = (JVTActivationHome) initCtx.lookup(
                    "java:comp/env/ejb/JVTActivationBean");
        } catch (NamingException nex2) {
            //log("XVTMessageDrivenBean:ejbCreate:  Naming exception caught on lookup of oss.XVTTroubleTicketSessionHome");
            nex2.printStackTrace();
        }
        
        // create the seession to obtain the application informations.
        try {
            this.jvtSession = javtActivationHome.create();
            replySender = JMSReplySender.getInstance();
            xvtStringReply = null;
        } catch (CreateException cex) {
            log("CreateException caught in XVTMessageDrivenBean: on create XVTSessionBean");
            cex.printStackTrace();
            throw cex;
        } catch (RemoteException rex) {
            log("RemoteException caught in XVTMessageDrivenBean: on create XVTSessionBean");
            rex.printStackTrace();
            throw new CreateException();
        }
        
        log("XVTMessageDrivenBean: created XVTSessionBean OK");
    }
    
    //====================================================
    public void ejbRemove() throws javax.ejb.EJBException {
        jvtSession = null;
        replySender = null;
    }
    
    //====================================================
    public void onMessage(final javax.jms.Message message) {
        javax.jms.Destination replyTo = null;
        String requestSenderIdProp = null; //OSS_REQUEST_SENDER_ID
        String doc = null;
        
        try {
            log("XVTMessageDrivenBean:onMessage - msg received!");
            
            // As a first step, check the type of the received message
            if ((message == null) || !(message instanceof javax.jms.TextMessage)) {
                return; //ignore the message
                
                // throw new javax.oss.OssIllegalArgumentException("XVTMessageDrivenBean: Illegal message type received - not a javax.jms.TextMessage ");
            }
            
            // Get the text message
            String xmlString = ((javax.jms.TextMessage) message).getText();
            log("getText: \n" + xmlString);
            
            //get the REQUEST_SENDER_ID property + DESTINATION_TYPE
            requestSenderIdProp = message.getStringProperty("REQUEST_SENDER_ID");
            
            // Get the replyTo destination
            replyTo = message.getJMSReplyTo();
            
            log("Destination name: " + replyTo.toString());
            
            // analyse the message and build the response (or the Exception)
            Pattern p = Pattern.compile("<?.*?>");
            Matcher m = p.matcher(xmlString);
            xmlString = m.replaceFirst("");
            xmlString = xmlString.substring(xmlString.indexOf("<"));

            doc = addXmlRootElement(parseXml(xmlString));
            
            if (doc != null) {
                replySender.sendXmlReply(replyTo, doc, messageType, messageNameProp,
                        requestSenderIdProp);
                log("XVT response sent OK");
            }
        } catch (javax.jms.IllegalStateException isex) {
            //may the session is closed so try to create a new instance
            try {
                replySender.createSession();
                replySender.sendXmlReply(replyTo, doc, messageType, messageNameProp,
                        requestSenderIdProp);
            } catch (Exception exa) {
                log("got 2nd exception " + isex.getMessage());
                isex.printStackTrace();
                
                // ok abandon
            }
        } catch (java.lang.Exception ex) {
            log("got exception " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    //====================================================
    // You might also consider using AS's log service
    private void log(String s) {
        if (VERBOSE) {
            System.out.println(s);
        }
    }
    
    private String parseXml(String inputXml) {
    	log("parse:\n"+inputXml);
        String response = null;
        
        
        
        // Check type of the object returned by
        // the call to XmlObject.Factory.parse() method.
        // If type of object returned is of
        // org.ossj.xml.common.v12.<waited common message>, then input xml
        // document carries invalid request.
            /* valid requests are :
            GetEventDescriptorRequestDocument
            GetEventTypesRequestDocument
            GetManagedEntityTypesRequestDocument
            GetNamedQueryTypesRequestDocument
            GetSupportedOptionalOperationsRequestDocument
            GetUpdateProcedureTypesRequestDocument
            QueryRequestDocument
            UpdateRequestDocument
             */
        messageType = "RESPONSE";
        
        if (inputXml.startsWith("<getEventTypesRequest")) {
            return replyToStringArrayRequest("getEventTypes");
        } else if (inputXml.startsWith("<getManagedEntityTypesRequest")){
            return replyToStringArrayRequest("getManagedEntityTypes");
        } else if (inputXml.startsWith("<getNamedQueryTypesRequest")) {
            return replyToStringArrayRequest("getNamedQueryTypes");
        } else if (inputXml.startsWith("<getSupportedOptionalOperationsRequest")) {
            return replyToStringArrayRequest("getSupportedOptionalOperations");
        } else if (inputXml.startsWith("<getUpdateProcedureTypesRequest")) {
            return replyToStringArrayRequest("getUpdateProcedureTypes");
        } else if (inputXml.startsWith("<queryRequest")) {
            messageNameProp = "query";
            
            String param = null;
            if (inputXml.indexOf("<namedQuery/>") >= 0
                    || inputXml.indexOf("<namedQuery>") < 0
                    || inputXml.indexOf("</namedQuery>") < 0) {
                response = "<queryException xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>Invalid or null NamedQueryValue attribute</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</queryException>";
                messageType = "EXCEPTION";
                return response;
            }
            
            param =  inputXml.substring(inputXml.indexOf("<namedQuery>") + 12,
                    inputXml.indexOf("</namedQuery>"));
            String[] qTypes = null;
            try {
                qTypes = jvtSession.getNamedQueryTypes();
            } catch(Exception ex){
                
            }
            if (param.length() == 0
                    || ! Arrays.asList(qTypes).contains(param)) {
                response = "<queryException xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>Invalid or null NamedQueryValue attribute</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</queryException>";
                messageType = "EXCEPTION";
                return response;
            }
            response = "<queryResponse  xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
            
            //TBD
            // get the attribute from the request
            // build the arg list, call the jvt method and format the response content
                /*
                NamedQueryValue nqValue = null;
                NamedQueryResponse nqResp = null;
                try {
                    nqResp = jvtSession.query(nqValue);
                } catch (javax.oss.OssIllegalArgumentException ex) {
                        response = "<"+request+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                        response += "<ossIllegalArgumentException>\n";
                        response += "<message>"+ex.getMessage()+"</message>\n";
                        response += "</ossIllegalArgumentException>\n";
                        response += "</"+request+"Exception>\n";
                    messageType = "EXCEPTION";
                    return response;
                }
                //extract nqResp fields....
                 */
            response += "<NamedQueryResponse>\n";
            // add here the fields
            response += "</NamedQueryResponse>\n";
            response += "</queryResponse>";
            return response;
            
            
            
        } else if (inputXml.startsWith("<updateRequest")) {
            messageNameProp = "update";
            
            String param = null;
            if (inputXml.indexOf("<updateValue/>") >= 0
                    || inputXml.indexOf("<updateValue>") < 0
                    || inputXml.indexOf("</updateValue>") < 0) {
                response = "<updateException xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>Invalid or null updateValue attribute</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</updateException>";
                messageType = "EXCEPTION";
                return response;
            }
            
            param =  inputXml.substring(inputXml.indexOf("<updateValue>") + 12,
                    inputXml.indexOf("</updateValue>"));
            String [] upTypes = null;
            try {
                upTypes = jvtSession.getUpdateProcedureTypes();
            } catch(Exception ex){
                
            }
            if (param.length() == 0
                    || ! Arrays.asList(upTypes).contains(param)) {
                response = "<updateException xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>Invalid or null updateValue attribute</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</updateException>";
                messageType = "EXCEPTION";
                return response;
            }
            response = "<updateResponse  xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
            
            //TBD
            // get the attribute from the request
            // build the arg list, call the jvt method and format the response content
            UpdateProcedureValue upValue = null;
            UpdateProcedureResponse upResp = null;
            
                /*
                try {
                    upResp = jvtSession.update(upValue);
                } catch (javax.oss.OssIllegalArgumentException ex) {
                        response = "<"+request+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                        response += "<ossIllegalArgumentException>\n";
                        response += "<message>"+ex.getMessage()+"</message>\n";
                        response += "</ossIllegalArgumentException>\n";
                        response += "</"+request+"Exception>\n";
                    messageType = "EXCEPTION";
                    return response;
                }
                //extract and get the fields
                 */
            upResp = new UpdateProcedureResponseImpl(UpdateProcedureResponse.COMPLETE);
            response += "<updateProcedureResponse>\n";
            response += "<status>"+upResp.getStatus()+"</status>\n";
            response += "<success>"+upResp.isSuccessful()+"</success>\n";
            response += "</updateProcedureResponse>\n";
            response += "</updateResponse>";
            return response;
            
        } else if (inputXml.startsWith("<getEventDescriptorRequest")) {
            messageNameProp = "getEventDescriptor";
            
            String param = null;
            if (inputXml.indexOf("<eventType/>") >= 0
                    || inputXml.indexOf("<eventType>") < 0
                    || inputXml.indexOf("</eventType>") < 0) {
                response = "<"+messageNameProp+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>Invalid or null eventType attribute</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</"+messageNameProp+"Exception>";
                messageType = "EXCEPTION";
                return response;
            }
            param =  inputXml.substring(inputXml.indexOf("<eventType>") + 11,
                    inputXml.indexOf("</eventType>"));
            
            log("get Event Type = " + param);
            
            javax.oss.EventPropertyDescriptor evtpd = null;
            
            try {
                evtpd = jvtSession.getEventDescriptor(param);
            } catch (javax.oss.OssIllegalArgumentException ex) {
                response = "<"+messageNameProp+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<ossIllegalArgumentException>\n";
                response += "<message>"+ex.getMessage()+"</message>\n";
                response += "</ossIllegalArgumentException>\n";
                response += "</"+messageNameProp+"Exception>";
                messageType = "EXCEPTION";
                return response;
            } catch (java.rmi.RemoteException rex) {
                response = "<"+messageNameProp+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
                response += "<remoteException>\n";
                response += "<message>"+rex.getMessage()+"</message>\n";
                response += "</remoteException>\n";
                response += "</"+messageNameProp+"Exception>";
                messageType = "EXCEPTION";
                return response;
            }
            
            log("Build the GetEventDescriptorResponse for event " + evtpd.getEventType());
            //log("Build the GetEventDescriptorResponse for pnames " + evtpd.getPropertyNames());
            //log("Build the GetEventDescriptorResponse for ptypes " + evtpd.getPropertyTypes());
            
            response = "<getEventDescriptorResponse  xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
            response += "<eventPropertyDescriptor>\n";
            response += "<eventType>"+evtpd.getEventType()+"</eventType>\n";
            response += "<propertyNames>\n";
            
            String[] p = evtpd.getPropertyNames();
            //log("get " + p.length + " properties");
            
            for (int i = p.length - 1; i >= 0; --i)
                response += "<item>"+p[i]+"</item>\n";
            response += "</propertyNames>\n";
            
            String[] t = evtpd.getPropertyTypes();
            response += "<propertyTypes>\n";
            
            for (int i = t.length - 1; i >= 0; --i)
                response += "<item>"+t[i]+"</item>\n";
            response += "</propertyTypes>\n";
            
            response += "</eventPropertyDescriptor>\n";
            response += "</getEventDescriptorResponse>";
            return response;
            
        } else {
            response = "</>";
            //invalid request for the common api test purpose
        }
        
        log("Finalized response = " + response.toString());
        
        return response;
    }
    
    private String replyToStringArrayRequest(String request) {
        
        String[] typeList = null;
        String reply = null;
        messageNameProp = request;
        
        try {
            typeList = (String[]) jvtSession.getClass().getDeclaredMethod(request, (Class[])null).invoke(jvtSession, (Object[])null);
        } catch (Exception ex) {
            
            reply = "<"+request+"Exception xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
            reply += "<remoteException>\n";
            reply += "<message>"+ex.getMessage()+"</message>\n";
            reply += "</remoteException>\n";
            reply += "</"+request+"Exception>";
            return reply;
        }
        reply = "<"+request+"Response xmlns=\"http://ossj.org/xml/Common/v1-2\">\n";
        reply += "<strings>\n";
        for (int i = typeList.length-1;i>=0;--i)
            reply += "<item>"+typeList[i]+"</item>\n";
        reply += "</strings>\n";
        reply += "</"+request+"Response>";
        log("Reply:\n"+reply);
        return reply;
        
    }
    private String addXmlRootElement(String xmlDoc) {
        if (xmlDoc != null) {
            return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + xmlDoc;
        } else {
            return null;
        }
    }

}
