package ossj.qos.mdbimpl.tm;

/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc., Ericsson AB.
 * @author Vijay Sharma
 * @author Hooman Tahamtani
 *
 * @version 1.0
 *
 * Fixed the onMessage(Message msg) method.  It was missing some calls.
 * 2002-06-15, Hooman Tahamtani, Ericsson AB., Gothenburg, Sweden.
 */
import javax.ejb.CreateException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;

import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.jdom.*;
import org.jdom.input.*;

import ossj.qos.mdbimpl.tm.*;
import javax.oss.*;

public class TmRequestResponseMDB implements MessageDrivenBean, MessageListener
{

 //VP: private static final boolean VERBOSE = true;
 private static final boolean VERBOSE = false;
  private MessageDrivenContext m_context;
  private static Context jndiContext = null;
  private static QueueConnectionFactory           queueConnectionFactory = null;
  private static QueueConnection                  queueConnection = null;
  private static QueueSession                     queueSession = null;
  private static Queue                            queue = null;
  private static QueueSender                      queueSender = null;
  private static TextMessage                      repMessage =null;
  private static TemporaryQueue                   tempQueue;
  ApplicationConnector ac = null;



  public TmRequestResponseMDB() {
  }

  private void log(String s) {
    if (VERBOSE) System.out.println(s);
  }

  /**
   * This method is required by the EJB Specification.
   *
   *
   */
  public void ejbActivate() {
    log("ejbActivate called");
  }

  /**
   * This method is required by the EJB Specification.
   *
   *
   */
  public void ejbRemove() {
    log("ejbRemove called");
  }

  /**
   * This method is required by the EJB Specification.
   *
   *
   */
  public void ejbPassivate() {
    log("ejbPassivate called");
  }

  /**
   * Sets the session context.
   *
   * @param ctx               MessageDrivenContext Context for session
   */
  public void setMessageDrivenContext(MessageDrivenContext ctx) {
    log("setMessageDrivenContext called");
    m_context = ctx;
  }

  /**
   * This method corresponds to the create method in the home interface
   *
   * @exception               javax.ejb.CreateException if there is
   *                          a communications or systems failure
   *
   */
  public void ejbCreate () throws CreateException {
    log("ejbCreate called");

  }

  /////
  // Implementation of MessageListener
  //

  /**
   *
   *
   */
  public void onMessage( Message msg ) {

    String response = null;
    ac = new ApplicationConnector();
    org.jdom.Element elm = null;
     try {

     TextMessage tm = (TextMessage) msg;
      String text = tm.getText();
      log("Message received ::" + text);
      Reader reader = new StringReader(text);



      org.apache.xerces.parsers.DOMParser parser = new org.apache.xerces.parsers.DOMParser();
      parser.setFeature("http://xml.org/sax/features/validation", true);
      log("Before Parsing");
      parser.setFeature("http://apache.org/xml/features/validation/schema", true);
      //parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation","XmlQosFmMonitorSchema.xsd");

      InputSource is = new InputSource(reader);
      log(is.toString());
      parser.parse(is);
      org.w3c.dom.Document docmnt = parser.getDocument();
      org.jdom.input.DOMBuilder builder = new org.jdom.input.DOMBuilder(true);
      org.jdom.Document doc = builder.build(docmnt);
     log("After Parsing");

     elm  = doc.getRootElement();
     if(elm.getName().equalsIgnoreCase("createThresholdMonitorByValueRequest")){
     CreateThresholdMonitorByValue obj = new CreateThresholdMonitorByValue();
        response = obj.getResponse(elm);
           log(response);

     } else if(elm.getName().equalsIgnoreCase("getObservableAttributesRequest")){
      GetObservableAttributes obj = new GetObservableAttributes();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getObservableObjectClassesRequest")){
      GetObservableObjectClasses obj = new GetObservableObjectClasses();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getObservableObjectsRequest")){
      GetObservableObjects obj = new GetObservableObjects();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getSupportedGranularitiesRequest")){
      GetSupportedGranularities obj = new GetSupportedGranularities();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getSupportedObservableObjectsRequest")){
      GetSupportedObservableObjects obj = new GetSupportedObservableObjects();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getThresholdMonitorByKeyRequest")){
      GetThresholdMonitorByKey obj = new GetThresholdMonitorByKey();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getThresholdMonitorsByKeysRequest")){
      GetThresholdMonitorsByKeys obj = new GetThresholdMonitorsByKeys();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("getVersionRequest")){
      GetVersionRequest obj = new GetVersionRequest();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("queryThresholdMonitorsRequest")){
      QueryThresholdMonitors obj = new QueryThresholdMonitors();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("removeThresholdMonitorByKeyRequest")){
      RemoveThresholdMonitorByKey obj = new RemoveThresholdMonitorByKey();
        response = obj.getResponse(elm);
           log(response);
     } else if(elm.getName().equalsIgnoreCase("resumeThresholdMonitorByKeyRequest")){
      ResumeThresholdMonitorByKey obj = new ResumeThresholdMonitorByKey();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("suspendThresholdMonitorByKeyRequest")){
      SuspendThresholdMonitorByKey obj = new SuspendThresholdMonitorByKey();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("tryCreateThresholdMonitorsByValuesRequest")){
      TryCreateThresholdMonitorsByValues obj = new TryCreateThresholdMonitorsByValues();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("tryRemoveThresholdMonitorsByKeysRequest")){
      TryRemoveThresholdMonitorsByKeys obj = new TryRemoveThresholdMonitorsByKeys();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("tryResumeThresholdMonitorsByKeysRequest")){
      TryResumeThresholdMonitorsByKeys obj = new TryResumeThresholdMonitorsByKeys();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("trySuspendThresholdMonitorsByKeysRequest")){
      TrySuspendThresholdMonitorsByKeys obj = new TrySuspendThresholdMonitorsByKeys();
        response = obj.getResponse(elm);
           log(response);
     }  else if(elm.getName().equalsIgnoreCase("GetSupportedOptionalOperationsRequest")){
      GetSupportedOptionalOperations obj = new GetSupportedOptionalOperations();
        response = obj.getResponse(elm);
           log(response);
     }
     //added 2002-06-17
     else if(elm.getName().equalsIgnoreCase("GetObservableObjectsRequest")){
      GetObservableObjects obj = new GetObservableObjects();
        response = obj.getResponse(elm);
           log(response);
     }
     //added 2002-06-18, Hooman
     else if(elm.getName().equalsIgnoreCase("getManagedEntityTypesRequest")){
        GetManagedEntityTypes obj = new GetManagedEntityTypes();
        response = obj.getResponse(elm);
        log(response);
     }

       log("Received XML Request : " + text);
       }catch(Exception e){
       response = e.getMessage();
     }
     try{
          log("Before Connection 1");
              jndiContext = ac.getInitialContext();
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup
                (ac.getJMSQueueConnectionFactory());
            //queue = (Queue) jndiContext.lookup("System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/TestQueue");

            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            tempQueue = (TemporaryQueue)msg.getJMSReplyTo();
            //queueSender = queueSession.createSender(queue);
            queueSender = queueSession.createSender(tempQueue);
            //QueueReceiver queueReceiver = queueSession.createReceiver(tempQueue);

            repMessage = queueSession.createTextMessage();

            repMessage.setText(response);
            //repMessage.setJMSReplyTo(tempQueue);

            log("Sending message: " +repMessage.getText());
            queueSender.send(repMessage);
            //QueueReceiver qreceiver = queueSession.createReceiver(tempQueue);
             queueConnection.start();
            //TextMessage m1  = (TextMessage)qreceiver.receive();
            //log(m1.getText());


        } catch(Exception ex) {
            log("Unable to send message to JMS Queue!!! Check your Queue Connections!!!" + ex.getMessage());
        }
    }

}
