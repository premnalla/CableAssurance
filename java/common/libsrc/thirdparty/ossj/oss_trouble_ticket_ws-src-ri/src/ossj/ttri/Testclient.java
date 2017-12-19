package ossj.ttri;


import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
//import org.w3c.dom.ra

import javax.oss.trouble.*;
import javax.oss.QueryValue;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.FinderException;
import javax.jms.*;
import javax.jms.QueueSession;
import javax.jms.QueueSender;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Vector;
import java.rmi.RemoteException;
import java.io.StringReader;


/**
 * Created by IntelliJ IDEA.
 * User: WIPROkumar
 * Date: Feb 1, 2005
 * Time: 12:08:38 PM
 * To change this template use Options | File Templates.
 */
public class Testclient implements MessageListener {
  String ttConnectionFactory = null; //"System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/TopicConnectionFactory";
       TopicSubscriber ttSubscriber=null;
         TopicConnection ttConnection=null;
    String homeName = null;
   String JNDI_FACT_CLASS_NAME=null;

    InitialContext namingContext = null;
    JVTTroubleTicketSessionHome	jvttroubleticketsessionhome =null;

     QueueSession theQueueSession=null;
     QueueSender theSender=null;
     Queue theReplyQueue=null;
     QueueReceiver theReceiver=null;

    String theQueueFactoryName="System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/QueueConnectionFactory";
    String theQueueName="System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/MessageQueue";


    /**
   * tt session.
   */
  public JVTTroubleTicketSession  ttSession = null;


    public Testclient () {

       homeName="System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/JVTHome";
        //"System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/JVTHome"
        //System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Res/XVTHome

       ttConnectionFactory="System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/WSTopicConnectionFactory";

        String ttJVTTopic = "System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/JVTEventTopic"; //"System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTEventTopic";
        String ttXVTTopic="System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI/Comp/XVTEventTopic";

         try
            {
              namingContext=getInitialContext();
             init(ttXVTTopic);

            ttSubscriber.close();
             ttConnection.close();
              init(ttXVTTopic);
               initRequest();
                      System.out.println("looking up .... ");
			Object	objref				= namingContext.lookup(homeName);
                jvttroubleticketsessionhome = (JVTTroubleTicketSessionHome)PortableRemoteObject.narrow(objref,JVTTroubleTicketSessionHome.class);

            }

            catch(ClassCastException castexception)
            {
                castexception.printStackTrace();
            }

            catch(NamingException namingexception1)
            {
                namingexception1.printStackTrace();
            }catch(Exception e)
            {
             e.printStackTrace();
            }
        /*
            try
            {
                System.out.println("Calling Create .........");
                ttSession = jvttroubleticketsessionhome.create();
            }
            catch(RemoteException remoteexception)
            {
                remoteexception.printStackTrace();
            }
            catch(CreateException createexception)
            {
                createexception.printStackTrace();
            }
           */


                   //ttSession.queryManagedEntities()    not implemented
                   // ttSession.queryTroubleTickets()

            try {

                  //  String [] str=ttSession.getQueryTypes();
                 //   for(int i=0; i< str.length; i++){
                //       System.out.println(" Querytypes ["+ i + "]"+ str[i]);
                //    }

                    String key=createTTByvalue();
                    System.out.println("The TT is created with Primary Key  "+ key);

//                    TroubleTicketValue ttValue=getTTByKey(key);
                    //System.out.println("The TT found : The Description is : "+ ttValue.getTroubleDescription());

/*

                    String key1=createTTByvalue();
                    System.out.println("The TT is created with Primary Key"+ key1);
                    String[] keys={key,key1};
*/

                   /* TroubleTicketValue[] ttValues=getTTByKeys(keys);

                    for(int i=0; i<ttValues.length; i++){
                        System.out.println("The TT[" + i + "] found : The Description is : "+ ttValue.getTroubleDescription());
                    }*/
                   String[] arrKeys={key, "37843784378"};

                   trySetTTByValuesXmlRequest(arrKeys);
                    execute(getTTByTemplatesXmlRequest());
                    String endOfReplyString="false";
                    while(endOfReplyString.equals("false")){
                        TextMessage message = (TextMessage) theReceiver.receive();
                        String xmlResponse= message.getText();
                        System.out.println("\n\nThe Response is \n\n");
                        System.out.println(xmlResponse);
                        Document doc=parseXmlString(xmlResponse);
                         NodeList endOfReplyNodeList = doc.getElementsByTagNameNS("http://java.sun.com/products/oss/xml/Common","endOfReply");
                        Node endOfReplyNode = endOfReplyNodeList.item(0);
                        if(endOfReplyNode==null)break;
                        endOfReplyString = (endOfReplyNode.getFirstChild()).getNodeValue();
                        System.out.println("endOfReplyString"+endOfReplyString);
                    }

                   // setTTByValue(key);

                   // closeTTByKey(key);
                    //cancelTTByKey(key);
                  //  escalateTTByKey(key1);
                    //queryTT();
                   // getTTByTemplates();

                   // getTTByTemplates();
//                   keys=tryCreateTTByValues();
//                   String[] arrKeys={keys[0],keys[1], "37843784378"};


                  // trySetTTByValues(keys);
                   //trySetTTByKeys(keys);
                    // trySetTTByTemplate();
                   //trySetTTByTemplates();
                   //tryCancelTTByKeys(arrKeys);
                    //tryCancelTTByTemplate();
                   // tryCancelTTByTemplates();
                   //tryCloseTTByKeys(arrKeys);
                     //tryCloseTTBytemplate();

                    // tryCloseTTBytemplates();
                       //tryEscalateTTByKeys(arrKeys);
                    //tryEscalateTTByTemplate();
                    //tryEscalateTTByTemplates();
            }catch (Exception e) {
                e.printStackTrace();
            }

    }
   private void initRequest(){
       QueueConnectionFactory theQueueConnFactory=null;
       QueueConnection   theQueueConnection=null;

       Queue theQueue=null;



        try {
      theQueueConnFactory = (QueueConnectionFactory)namingContext.lookup(theQueueFactoryName);
      theQueueConnection = theQueueConnFactory.createQueueConnection();
      theQueueSession = theQueueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      theQueue = (Queue)namingContext.lookup(theQueueName);
      theSender = theQueueSession.createSender(theQueue);
      theReplyQueue = theQueueSession.createTemporaryQueue();
      theReceiver = theQueueSession.createReceiver(theReplyQueue);
      theQueueConnection.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
   }
    public void execute (String request) {
    try {
      TextMessage theMessage = (TextMessage)theQueueSession.createTextMessage();
      String theRequest = request;
      theMessage.setText(theRequest);
      theMessage.setJMSReplyTo(theReplyQueue);
      theSender.send(theMessage);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
    private void init( String strTopic){
        TopicConnectionFactory ttTopicConnectionFactory=null;
        TopicSession ttsession=null;

        Topic ttTopic=null;

       try{
       ttTopicConnectionFactory = (TopicConnectionFactory) namingContext.lookup(ttConnectionFactory);
       ttConnection = ttTopicConnectionFactory.createTopicConnection();
             // ttConnection.setClientID("abc");
       ttsession = ttConnection.createTopicSession( true, Session.AUTO_ACKNOWLEDGE);
       ttTopic = (Topic) namingContext.lookup( strTopic );
       //ttSubscriber = ttsession.createSubscriber( ttTopic );

              ttSubscriber = ttsession.createDurableSubscriber( ttTopic,"id" );
       ttSubscriber.setMessageListener( this );
       ttConnection.start();
     } catch (Exception exp){
       exp.printStackTrace();
     }
    }

    public void onMessage(Message mesg){
       try{
           /* Object obj=((ObjectMessage)mesg).getObject();
            if(obj instanceof TroubleTicketCreateEvent ) {
                System.out.println("Create Event ");
                TroubleTicketCreateEvent objTTCreateEvent=(TroubleTicketCreateEventImpl)obj;
                TroubleTicketValue ttValue=objTTCreateEvent.getTroubleTicketValue();
                System.out.println("The TT Created at time " + objTTCreateEvent.getEventTime()
                        + " with key "+ ttValue.getTroubleTicketKey().getTroubleTicketPrimaryKey()
                        + "  and Description  "+ ttValue.getTroubleDescription());
            }
           else if(obj instanceof TroubleTicketStatusChangeEvent){
                System.out.println("TroubleTicketStatusChangeEvent ");
                 TroubleTicketStatusChangeEvent objTTStatusChangedEvent=(TroubleTicketStatusChangeEventImpl)obj;
                 System.out.println("The Status of TT with Key "+ objTTStatusChangedEvent.getTroubleTicketKey().getTroubleTicketPrimaryKey() + " is changed");
           }
           else if(obj instanceof TroubleTicketAttributeValueChangeEvent){
               System.out.println("TroubleTicketAttributeValueChangeEvent");
               TroubleTicketAttributeValueChangeEvent objTTAVCEvent=(TroubleTicketAttributeValueChangeEventImpl)obj;
               System.out.println("The Attribute Changed for the TT with PK " +objTTAVCEvent.getTroubleTicketValue().getTroubleTicketKey().getTroubleTicketPrimaryKey());
           }
           else if(obj instanceof TroubleTicketCancellationEvent){
               System.out.println("TroubleTicketCancellationEvent");
               TroubleTicketCancellationEvent objTTCEvent=(TroubleTicketCancellationEventImpl)obj;
               System.out.println("TT is cancelled with PK " +objTTCEvent.getTroubleTicketKey().getTroubleTicketPrimaryKey());
           }
            else if(obj instanceof TroubleTicketCloseOutEvent){
               System.out.println("TroubleTicketCloseOutEvent");
               TroubleTicketCloseOutEvent objTTCEvent=(TroubleTicketCloseOutEventImpl)obj;
               System.out.println("TT is closed with PK " +objTTCEvent.getTroubleTicketValue().getTroubleTicketKey().getTroubleTicketPrimaryKey());
           } */

            String xmlString = ((javax.jms.TextMessage)mesg).getText();
            System.out.println("The event is \n"+ xmlString);

       }catch(Exception e){
           e.printStackTrace();
       }


    }
    public InitialContext getInitialContext()
        throws NamingException
    {
        try
        {
            Properties properties = new Properties();
            JNDI_FACT_CLASS_NAME=System.getProperty("JNDI_FACT_CLASS_NAME");
            properties.put("java.naming.factory.initial", JNDI_FACT_CLASS_NAME);
            System.out.println(" "+ JNDI_FACT_CLASS_NAME);
            System.out.println(" "+ System.getProperty("JNDI_URL"));
            System.out.println(" STDOUT_ENABLED "+ System.getProperty("STDOUT_ENABLED"));
            properties.put("java.naming.provider.url", System.getProperty("JNDI_URL"));

            return new InitialContext(properties);
        }
        catch(NamingException namingexception)
        {
            namingexception.printStackTrace();
        }
        return null;
    }



   private String createTTByvalue(){
       try {
           TroubleTicketValue ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
           ttValue.setTroubleDescription("Test TT created By Testclient");
           //ttValue.setTroubleDescription("XYZ");
           ttValue.setDialog("MyDialog");
           ttValue.setTroubleState(TroubleState.OPENACTIVE);
           TroubleTicketKey ttKey=null;
           ttKey=ttSession.createTroubleTicketByValue(ttValue);
           return ttKey.getTroubleTicketPrimaryKey();
           //System.out.println("The TT is created with Primary Key"+ ttKey.getTroubleTicketPrimaryKey());


       }catch (javax.oss.IllegalArgumentException e) {
           e.printStackTrace();
       }catch (CreateException e) {
               e.printStackTrace();
       }catch (RemoteException e) {
               e.printStackTrace();
       }
       return null;

   }

   private TroubleTicketValue getTTByKey(String key){
       TroubleTicketKey ttKey= new TroubleTicketKeyImpl();
            ttKey.setTroubleTicketPrimaryKey(key);
       try{
            return ttSession.getTroubleTicketByKey(ttKey,null);
       }catch (javax.oss.IllegalArgumentException e) {
           e.printStackTrace();
       }catch (ObjectNotFoundException e) {
               e.printStackTrace();
       }catch (RemoteException e) {
               e.printStackTrace();
       }
       return null;
   }

   private TroubleTicketValue[] getTTByKeys(String[] keys){
           System.out.println("the keys length is "+ keys.length);
           TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
           System.out.println("The ttKeys length is "+ ttKeys.length);
            for(int i=0; i< keys.length; i++){
                System.out.println("The key[i] is " + keys[i]+ "   " + i);
                ttKeys[i]=new TroubleTicketKeyImpl();
                ttKeys[i].setTroubleTicketPrimaryKey(keys[i]);
            }
           try{
                return ttSession.getTroubleTicketsByKeys(ttKeys, null);
           }catch (javax.oss.IllegalArgumentException e) {
                e.printStackTrace();
           }catch (ObjectNotFoundException e) {
                e.printStackTrace();
           }catch (RemoteException e) {
                e.printStackTrace();
           }catch (FinderException e) {
                e.printStackTrace();
           }
           return null;
   }
   private void getTTByTemplate(){
       try{
           TroubleTicketValue ttTemplate=null;
           TroubleTicketValue ttValue=null;
           TroubleTicketValue[] ttValues=null;
           TroubleTicketValueIterator ttValueIterator=null;

           ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");


           TroubleTicketValue[] ttTemplates={ttTemplate};

           //ttTemplate.setTroubleState(3);
           //ttTemplate.setTroubleStatus(0);
          // ttTemplate.setTroubleDescription("String");
           ttTemplate.setTroubleDescription("String");


           ttValueIterator=ttSession.getTroubleTicketsByTemplates(ttTemplates, null);
           ttValues=ttValueIterator.getNextTroubleTickets(10);
           while(ttValues.length>0){
               for(int i=0; i<ttValues.length; i++){
                   ttValue=ttValues[i];
                   System.out.println("  "+ ttValue.getDialog());
                   System.out.println("  "+ ttValue.getCloseOutNarr());
                   System.out.println("  "+ ttValue.getTroubleDescription());
                   System.out.println("  "+ ttValue.getTroubleTicketKey().getTroubleTicketPrimaryKey());
               }
              System.out.println(" \n\n");
               ttValues=ttValueIterator.getNextTroubleTickets(10);
           }

           ttValueIterator.remove();
       }catch(Exception e){
           e.printStackTrace();
       }

   }
   private void getTTByTemplates(){
       try{
                  TroubleTicketValue ttTemplate1=null;
                  TroubleTicketValue ttTemplate2=null;

                  TroubleTicketValue [] ttValues=null;
                  TroubleTicketValue ttValue=null;
                  TroubleTicketValueIterator ttValueIterator=null;

                  ttTemplate1=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                  ttTemplate2=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");

                  TroubleTicketValue[] ttTemplates={ttTemplate1, ttTemplate2};

                 /* ttTemplate1.setTroubleType(4);
                  ttTemplate1.setTroubleStatus(24);

                  ttTemplate2.setTroubleState(3);
                  ttTemplate2.setTroubleStatus(0);*/

                ttTemplate1.setTroubleDescription("XML");
                ttTemplate2.setTroubleDescription("XMLAAA");

                  ttValueIterator=ttSession.getTroubleTicketsByTemplates(ttTemplates, null);
                  ttValues=ttValueIterator.getNextTroubleTickets(10);
                  while(ttValues.length>0){
                      for(int i=0; i<ttValues.length; i++){
                          ttValue=ttValues[i];
                          System.out.println("  "+ ttValue.getDialog());
                          System.out.println("  "+ ttValue.getCloseOutNarr());
                          System.out.println("  "+ ttValue.getTroubleDescription());
                          System.out.println("  "+ ttValue.getTroubleTicketKey().getTroubleTicketPrimaryKey());


                      }
                     System.out.println(" \n\n");
                      ttValues=ttValueIterator.getNextTroubleTickets(10);
                  }

                  ttValueIterator.remove();
              }catch(Exception e){
                  e.printStackTrace();
              }

   }
   private void setTTByValue(String key){
       try{
            TroubleTicketValue ttValue=null;
            TroubleTicketKey ttKey=null;
            //String[] attribs={TroubleTicketValue.TROUBLEDESCRIPTION};

            ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");

           ttKey=ttValue.makeTroubleTicketKey();
            ttKey.setTroubleTicketPrimaryKey(key);

            ttValue=ttSession.getTroubleTicketByKey(ttKey,null);
            //ttValue.setTroubleTicketKey(ttKey);

           //System.out.println(" "+ttValue.isPopulated(TroubleTicketValue.DIALOG));

            ttValue.setTroubleDescription("ABCXYZ");
           // ttValue.setTroubleStatus(TroubleStatus.CABLEFAILURE);   //It will send the TroubleTicketStatusChangeEvent
            //ttValue.setTroubleState(TroubleState.CLEARED);


            ttSession.setTroubleTicketByValue(ttValue, true);

       }catch(Exception e){
           e.printStackTrace();
       }

   }

   private void closeTTByKey(String key){
      try{
        TroubleTicketKey ttKey=new TroubleTicketKeyImpl();
        ttKey.setTroubleTicketPrimaryKey(key);
        ttSession.closeTroubleTicketByKey(ttKey, 1, "Closed By TestClient");
      }catch(Exception e){
          e.printStackTrace();
      }

   }
   private void escalateTTByKey(String key){
       try{

        TroubleTicketKey ttKey=new TroubleTicketKeyImpl();
        ttKey.setTroubleTicketPrimaryKey(key);
        Escalation esc=new EscalationImpl();
        esc.setLevel(1);
        esc.setRequestState(TroubleState.OPENACTIVE);

        PersonReach personReach=new PersonReachImpl();
        esc.setRequestPerson(personReach);

        esc.setEscalationPerson(personReach);
        esc.setEscalationTime(new java.util.Date());
        Escalation[] escl={esc};
        EscalationList escList=new EscalationListImpl();
        escList.set(escl);
        ttSession.escalateTroubleTicketByKey(ttKey, escList);

      }catch(Exception e){
          e.printStackTrace();
      }
   }
   private void cancelTTByKey(String key){
         try{
        TroubleTicketKey ttKey=new TroubleTicketKeyImpl();
        ttKey.setTroubleTicketPrimaryKey(key);
        ttSession.cancelTroubleTicketByKey(ttKey, 1, "Cancelled By TestClient");
      }catch(Exception e){
          e.printStackTrace();
      }
   }
   private void queryTT(){
       TroubleTicketValueIterator ttValueIterator=null;
       TroubleTicketValue[] ttValues= null;
       try{
           QueryValue qValue= new QueryAllOpenTroubleTicketsImpl();
           ttValueIterator=ttSession.queryTroubleTickets(qValue, null);
            ttValues = ttValueIterator.getNextTroubleTickets(10);
           while(ttValues.length>0){
               for(int i=0; i< ttValues.length; i++){
                   System.out.println(" The Opened TT Description is "+ ttValues[i].getTroubleDescription());
               }
               ttValues=ttValueIterator.getNextTroubleTickets(10);
           }
         }catch(Exception e){
           e.printStackTrace();
       }





   }
   private String[] tryCreateTTByValues(){
       Vector keyVector=new Vector();
        String[] keys=null;
        TroubleTicketKeyResult[] ttKeyResults=null;
        TroubleTicketValue[] ttValues= new TroubleTicketValue[2];
       try{
            ttValues[0]=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
            ttValues[1]=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
            ttValues[0].setTroubleDescription("TT # 1 created By Best Effort Method tryCreateTTByValues ");
            ttValues[1].setTroubleDescription("TT # 2 created By Best Effort Method tryCreateTTByValues ");
            ttKeyResults=ttSession.tryCreateTroubleTicketsByValues(ttValues);
            for(int i=0; i < ttKeyResults.length; i++){
                if(ttKeyResults[i].isSuccess()){
                    System.out.println("TT created by Best Effort Method  tryCreateTTByValues() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                    keyVector.add(ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                }
                else
                    System.out.println("TT creation failed by Best Effort Method  tryCreateTTByValues() The Exception is  "+ttKeyResults[i].getException());
            }
            keys=new String[keyVector.size()];
            keyVector.copyInto(keys);
       }catch(Exception e){
           e.printStackTrace();
       }
       return keys;
   }
   private void trySetTTByValues(String[] keys){
       for(int i=0; i< keys.length; i++)
            System.out.println(" key[i]  "+ keys[i]);
        try{
            TroubleTicketKeyResult[] ttKeyResults=null;
            TroubleTicketValue[] ttValues= new TroubleTicketValue[keys.length];
            TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
            TroubleTicketKey ttKey=new TroubleTicketKeyImpl();
            for(int i=0; i< keys.length; i++){
                ttKeys[i]=(TroubleTicketKey)ttKey.clone();
                ttKeys[i].setPrimaryKey(keys[i]);
                ttValues[i]=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttValues[i].setTroubleDescription("TT # " + (i+1) + "set By Best Effort Method trySetTTByValues()");
                ttValues[i].setTroubleTicketKey(ttKeys[i]);
            }
            ttKeyResults=ttSession.trySetTroubleTicketsByValues(ttValues, true);
            for(int i=0; i < ttKeyResults.length; i++){
                if(ttKeyResults[i].isSuccess())
                        System.out.println("TT set by Best Effort Method  trySetTTByValues() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                    else{
                        System.out.println("TT set failed by Best Effort Method  trySetTTByValues() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                        System.out.println("Exception is "+ttKeyResults[i].getException());
                    }
            }

       }catch(Exception e){
           e.printStackTrace();
       }
   }
   private void trySetTTByKeys(String[] keys){
           TroubleTicketKeyResult[] ttKeyResults=null;
           TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
            for(int i=0; i< keys.length; i++){
                ttKeys[i]=new TroubleTicketKeyImpl();
                ttKeys[i].setTroubleTicketPrimaryKey(keys[i]);
            }
           try{
                TroubleTicketValue ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttValue.setTroubleDescription("TT set By trySetTTByKeys() by TestClient");
                ttKeyResults= ttSession.trySetTroubleTicketsByKeys(ttKeys, ttValue);
                for(int i=0; i < ttKeyResults.length; i++){
                    if(ttKeyResults[i].isSuccess())
                        System.out.println("TT set by Best Effort Method  trySetTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                    else{
                        System.out.println("TT set failed by Best Effort Method  trySetTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                        System.out.println("Exception is "+ttKeyResults[i].getException());
                    }
                }
           }catch(Exception e){
               e.printStackTrace();
           }


   }
   private void trySetTTByTemplate(){
            TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            TroubleTicketKeyResult ttKeyResult=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");
                TroubleTicketValue ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttValue.setTroubleDescription("TT set By trySetTTByTemplate() by TestClient");
                ttKeyResultIterator= ttSession.trySetTroubleTicketsByTemplate(ttTemplate, ttValue, true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       ttKeyResult=ttKeyResults[i];
                       System.out.println(" trySetTTByTemplate() failed for the TT with PK  "+ ttValue.getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                  System.out.println(" \n\n");
                   ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               }
           }catch(Exception e){
               e.printStackTrace();
           }
   }
   private void trySetTTByTemplates(){
         TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");

                TroubleTicketValue ttTemplate1=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setDialog("MyDialog");

                TroubleTicketValue[] ttTemplates={ttTemplate, ttTemplate1};

                TroubleTicketValue ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttValue.setTroubleDescription("TT set By trySetTTByTemplates() by TestClient");
                ttValue.setDialog("ABC");
                ttKeyResultIterator= ttSession.trySetTroubleTicketsByTemplates(ttTemplates, ttValue, true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       System.out.println(" trySetTTByTemplates() failed for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                 System.out.println(" \n\n");
                 ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                }
             }catch(Exception e){
                   e.printStackTrace();
             }
   }
   private void tryEscalateTTByKeys(String[] keys){
      TroubleTicketKeyResult[] ttKeyResults=null;
      TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
       for(int i=0; i< keys.length; i++){
           ttKeys[i]=new TroubleTicketKeyImpl();
           ttKeys[i].setTroubleTicketPrimaryKey(keys[i]);
       }
      try{
           ttKeyResults= ttSession.tryEscalateTroubleTicketsByKeys(ttKeys, null);
           for(int i=0; i < ttKeyResults.length; i++){
               if(ttKeyResults[i].isSuccess())
                    System.out.println("TT Escalated By Best Effort Method  tryEscalateTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
               else {
                    System.out.println("TT Escalation failed By Best Effort Method  tryEscalateTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                    System.out.println("The Exception is "+ ttKeyResults[i].getException());
               }
            }
      }catch(Exception e){
          e.printStackTrace();
      }

   }

    /* Escalation esc=new EscalationImpl();
        esc.setLevel(1);
        esc.setRequestState(TroubleState.OPENACTIVE);

        PersonReach personReach=new PersonReachImpl();
        esc.setRequestPerson(personReach);

        esc.setEscalationPerson(personReach);
        esc.setEscalationTime(new java.util.Date());
        Escalation[] escl={esc};
        EscalationList escList=new EscalationListImpl();
        escList.set(escl);*/



   private void tryEscalateTTByTemplate(){
           TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");
                ttKeyResultIterator= ttSession.tryEscalateTroubleTicketsByTemplate(ttTemplate, null, false);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                   if(ttKeyResults[i].isSuccess())
                       System.out.println("TT Escalation tryEscalateTTByTemplate()for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   else
                       System.out.println(" TT Escalation Failed tryEscalateTTByTemplate() for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                  System.out.println(" \n\n");
                   ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               }
           }catch(Exception e){
               e.printStackTrace();
           }
   }
   private void tryEscalateTTByTemplates(){
             TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");

                TroubleTicketValue ttTemplate1=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setDialog("MyDialog");

                TroubleTicketValue[] ttTemplates={ttTemplate, ttTemplate1};

                ttKeyResultIterator= ttSession.tryEscalateTroubleTicketsByTemplates(ttTemplates, null, false);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       if(ttKeyResults[i].isSuccess())
                            System.out.println(" TT Escaletion tryEscalateTTByTemplates()  for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                       else{
                            System.out.println(" TT Escaletion tryEscalateTTByTemplates()  for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                            System.out.println("The Exception is "+ ttKeyResults[i]);

                       }
                   }
                 System.out.println(" \n\n");
                 ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                }
             }catch(Exception e){
                   e.printStackTrace();
            }
   }
   private void tryCloseTTByKeys(String[] keys){
       TroubleTicketKeyResult[] ttKeyResults=null;
         TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
          for(int i=0; i< keys.length; i++){
              ttKeys[i]=new TroubleTicketKeyImpl();
              ttKeys[i].setTroubleTicketPrimaryKey(keys[i]);
          }
         try{
              ttKeyResults= ttSession.tryCloseTroubleTicketsByKeys(ttKeys, 1,"Closed By tryCloseTTByKeys() by TestClient ");
              for(int i=0; i < ttKeyResults.length; i++){
                  if(ttKeyResults[i].isSuccess())
                    System.out.println("TT closed by Best Effort Method  trySetTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
               else{
                    System.out.println("TT closed failed by Best Effort Method  trySetTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                     System.out.println("The Exception is "+ ttKeyResults[i].getException());
                  }
              }
         }catch(Exception e){
             e.printStackTrace();
         }

   }
   private void tryCloseTTBytemplate(){
           TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");

                ttKeyResultIterator= ttSession.tryCloseTroubleTicketsByTemplate(ttTemplate, 1,"TT closed By tryCloseTTBytemplate By TestClient",true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       System.out.println(" tryCloseTTBytemplate() failed for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                  System.out.println(" \n\n");
                   ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               }
           }catch(Exception e){
               e.printStackTrace();
           }
   }
   private void tryCloseTTBytemplates(){
            TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");

                TroubleTicketValue ttTemplate1=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setDialog("ABC");

                TroubleTicketValue[] ttTemplates={ttTemplate,ttTemplate1};

                ttKeyResultIterator= ttSession.tryCloseTroubleTicketsByTemplates(ttTemplates, 1, "TT Closed By tryCloseTTBytemplates By TestClient",true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       System.out.println(" tryCloseTTBytemplates() failed for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                 System.out.println(" \n\n");
                 ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                }
             }catch(Exception e){
                   e.printStackTrace();
            }
   }
   private void tryCancelTTByKeys(String[] keys){
       TroubleTicketKeyResult[] ttKeyResults=null;
               TroubleTicketKey[] ttKeys=new TroubleTicketKeyImpl[keys.length];
                for(int i=0; i< keys.length; i++){
                    ttKeys[i]=new TroubleTicketKeyImpl();
                    ttKeys[i].setTroubleTicketPrimaryKey(keys[i]);
                }
               try{
                    ttKeyResults= ttSession.tryCancelTroubleTicketsByKeys(ttKeys, 1,"Closed By tryCloseTTByKeys() by TestClient ");
                    for(int i=0; i < ttKeyResults.length; i++){
                        if(ttKeyResults[i].isSuccess())
                            System.out.println("TT cancelled by Best Effort Method  tryCancelTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                        else{
                            System.out.println("TT cancellation failed by Best Effort Method  tryCancelTTByKeys() with Primary Key "+ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                            System.out.println("The Exception is "+ ttKeyResults[i].getException());
                        }
                    }
               }catch(Exception e){
                   e.printStackTrace();
               }

   }
   private void tryCancelTTByTemplate(){
         TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            TroubleTicketKeyResult ttKeyResult=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XML");
                TroubleTicketValue ttValue=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttValue.setTroubleDescription("TT closed By tryCancelTTByTemplate() by TestClient");
                ttKeyResultIterator= ttSession.tryCancelTroubleTicketsByTemplate(ttTemplate, 1,"TT cancelled By tryCancelTTByTemplate By TestClient",true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       ttKeyResult=ttKeyResults[i];
                       System.out.println(" tryCancelTTByTemplate() failed for the TT with PK  "+ ttValue.getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                  System.out.println("\n\n");
                   ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
               }
           }catch(Exception e){
               e.printStackTrace();
           }
   }
   private void tryCancelTTByTemplates(){
         TroubleTicketKeyResultIterator ttKeyResultIterator=null;
            TroubleTicketKeyResult[] ttKeyResults=null;
            try{
                TroubleTicketValue ttTemplate=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setTroubleDescription("XYZ");

                TroubleTicketValue ttTemplate1=ttSession.makeTroubleTicketValue("javax.oss.trouble.TroubleTicketValue");
                ttTemplate.setDialog("ABC");

                TroubleTicketValue[] ttTemplates={ttTemplate,ttTemplate1};
                ttKeyResultIterator= ttSession.tryCancelTroubleTicketsByTemplates(ttTemplates, 1, "TT Closed By tryCancelTTByTemplates By TestClient",true);
                ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                while(ttKeyResults.length>0){
                   for(int i=0; i<ttKeyResults.length; i++){
                       System.out.println(" tryCancelTTByTemplates() failed for the TT with PK  "+ ttKeyResults[i].getTroubleTicketKey().getTroubleTicketPrimaryKey());
                   }
                 System.out.println(" \n\n");
                 ttKeyResults=ttKeyResultIterator.getNextTroubleTicketKeyResults(10);
                }
             }catch(Exception e){
                   e.printStackTrace();
            }
   }

     private String getTTByKeyXmlRequest(String key){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:getTroubleTicketByKeyRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");

        xmlStr.append("\n<tt:troubleTicketKey>");
        xmlStr.append("\n\t<co:applicationContext>");
        xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
        xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
        xmlStr.append("\n\t\t<co:systemProperties/>");
        xmlStr.append("\n\t</co:applicationContext>");
        xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
        xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
        xmlStr.append("\n\t<tt:primaryKey>"+ key + "</tt:primaryKey>");
        xmlStr.append("\n</tt:troubleTicketKey>");
        xmlStr.append("\n</tt:getTroubleTicketByKeyRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
  }

   private String getTTByKeysXmlRequest(String[] keys){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:getTroubleTicketsByKeysRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("\n<tt:troubleTicketKeys>");
       for(int i=0; i<keys.length; i++){
            xmlStr.append("\n<tt:item>");
            xmlStr.append("\n<tt:troubleTicketKey>");
            xmlStr.append("\n\t<co:applicationContext>");
            xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
            xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
            xmlStr.append("\n\t\t<co:systemProperties/>");
            xmlStr.append("\n\t</co:applicationContext>");
            xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
            xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
            xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
            xmlStr.append("\n</tt:troubleTicketKey>");
            xmlStr.append("\n</tt:item>");
       }
        xmlStr.append("\n</tt:troubleTicketKeys>");
        xmlStr.append("\n</tt:getTroubleTicketsByKeysRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
   }

  private String getTTByTemplateXmlRequest(){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:getTroubleTicketsByTemplateRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
        xmlStr.append("<tt:template>");
        //xmlStr.append("<tt:troubleTicketValue>");
        xmlStr.append("<tt:troubleDescription>String</tt:troubleDescription>");
        //xmlStr.append("</tt:troubleTicketValue>");
        xmlStr.append("</tt:template>");
        xmlStr.append("</tt:getTroubleTicketsByTemplateRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
  }

    private String getTTByTemplatesXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:getTroubleTicketsByTemplatesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:templates>");
            xmlStr.append("<tt:item>");
            //xmlStr.append("<tt:troubleTicketValue>");
            xmlStr.append("<tt:troubleDescription>XML</tt:troubleDescription>");
            //xmlStr.append("</tt:troubleTicketValue>");
            xmlStr.append("</tt:item>");
            xmlStr.append("</tt:templates>");
            xmlStr.append("</tt:getTroubleTicketsByTemplatesRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }

      private String setTTByValueXmlRequest(String key){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:setTroubleTicketByValueRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");

        xmlStr.append("\n<tt:troubleTicketValue>");
        xmlStr.append("\n<tt:troubleTicketKey>");
        xmlStr.append("\n\t<co:applicationContext>");
        xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
        xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
        xmlStr.append("\n\t\t<co:systemProperties/>");
        xmlStr.append("\n\t</co:applicationContext>");
        xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
        xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
        xmlStr.append("\n\t<tt:primaryKey>"+ key + "</tt:primaryKey>");
        xmlStr.append("\n</tt:troubleTicketKey>");
        xmlStr.append("<tt:troubleDescription>set Value using XML</tt:troubleDescription>");
        xmlStr.append("\n</tt:troubleTicketValue>");
        xmlStr.append("<tt:resyncRequired>true</tt:resyncRequired>");
        xmlStr.append("\n</tt:setTroubleTicketByValueRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
  }

    private String trySetTTByKeysXmlRequest(String[] keys){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:trySetTroubleTicketsByKeysRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("\n<tt:troubleTicketKeys>");
        for(int i=0; i<keys.length; i++){
            xmlStr.append("\n<tt:item>");
            //xmlStr.append("\n<tt:troubleTicketKey>");
            xmlStr.append("\n\t<co:applicationContext>");
            xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
            xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
            xmlStr.append("\n\t\t<co:systemProperties/>");
            xmlStr.append("\n\t</co:applicationContext>");
            xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
            xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
            xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
            //xmlStr.append("\n</tt:troubleTicketKey>");
            xmlStr.append("\n</tt:item>");
        }
        xmlStr.append("\n</tt:troubleTicketKeys>");

        xmlStr.append("\n<tt:troubleTicketValue>");
        xmlStr.append("<tt:troubleDescription>try set Value using XML</tt:troubleDescription>");
       xmlStr.append("<tt:status>CABLEFAILURE</tt:status>");
        xmlStr.append("\n</tt:troubleTicketValue>");
        xmlStr.append("\n</tt:trySetTroubleTicketsByKeysRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
  }
     private String trySetTTByValuesXmlRequest(String[] keys){
       StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlStr.append("<tt:trySetTroubleTicketsByValuesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
        xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
        xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
        xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
        xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
        xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
        xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
        //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
        xmlStr.append("\n<tt:troubleTicketValues>");
         for(int i=0; i<keys.length; i++){
         xmlStr.append("\n<tt:item>");
            xmlStr.append("\n<tt:troubleTicketKey>");
            xmlStr.append("\n\t<co:applicationContext>");
            xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
            xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
            xmlStr.append("\n\t\t<co:systemProperties/>");
            xmlStr.append("\n\t</co:applicationContext>");
            xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
            xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
            xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
            xmlStr.append("\n</tt:troubleTicketKey>");
            xmlStr.append("\n<tt:dialog>TT set By trySetByValues()</tt:dialog> ");
            xmlStr.append("\n</tt:item>");
         }
        xmlStr.append("\n</tt:troubleTicketValues>");
        xmlStr.append("<tt:resyncRequired>true</tt:resyncRequired>");
         xmlStr.append("\n</tt:trySetTroubleTicketsByValuesRequest>");
        System.out.println("The request is \n\n"+ xmlStr.toString());
        return xmlStr.toString();
  }
    private String trySetTTByTemplateXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:trySetTroubleTicketsByTemplateRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:template>");
            xmlStr.append("<tt:troubleDescription>XML</tt:troubleDescription>");
            xmlStr.append("</tt:template>");
             xmlStr.append("<tt:troubleTicketValue>");
            xmlStr.append("<tt:dialog>");
            xmlStr.append("Dialog set By TT  XMLClient ");
            xmlStr.append("</tt:dialog>");
            xmlStr.append("</tt:troubleTicketValue>");
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:trySetTroubleTicketsByTemplateRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
    private String trySetTTByTemplatesXmlRequest(){
              StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:trySetTroubleTicketsByTemplatesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
               xmlStr.append("<tt:templates>");
               xmlStr.append("<tt:item>");
               xmlStr.append("<tt:troubleDescription>Zorro</tt:troubleDescription>");
               xmlStr.append("</tt:item>");
               xmlStr.append("</tt:templates>");
                xmlStr.append("<tt:troubleTicketValue>");
               xmlStr.append("<tt:dialog>");
               xmlStr.append("Dialog set By trySetTTByTemplatesXmlRequest By TT  XMLClient ");
               xmlStr.append("</tt:dialog>");
               xmlStr.append("</tt:troubleTicketValue>");
               xmlStr.append("<tt:failuresOnly>");
               xmlStr.append("false");
               xmlStr.append("</tt:failuresOnly>");
               xmlStr.append("</tt:trySetTroubleTicketsByTemplatesRequest>");
               System.out.println("The request is \n\n"+ xmlStr.toString());
               return xmlStr.toString();
         }
   private String createTTByValueXmlRequest()
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:createTroubleTicketByValueRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<tt:troubleTicketValue>");
                xmlStr.append("<tt:dialog>");
                xmlStr.append("Dialog set By TT createTTByValue() XMLClient ");
                xmlStr.append("</tt:dialog>");
                xmlStr.append("</tt:troubleTicketValue>");
                xmlStr.append("</tt:createTroubleTicketByValueRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
   private String queryTTXmlRequest()
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:queryTroubleTicketsRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<co:howMany>10</co:howMany>");
               xmlStr.append("<tt:query xsi:type=\"QueryAllOpenTroubleTicketsValue\"/>");
               xmlStr.append("<tt:attrNames>");
                xmlStr.append("<co:item>troubleLocation</co:item>");
                xmlStr.append("<co:item>troubleObject</co:item>");
                xmlStr.append("<co:item>troubleType</co:item>");
                xmlStr.append("<co:item>troubleState</co:item>");
                xmlStr.append("<co:item>troubleStatus</co:item>");
                xmlStr.append("</tt:attrNames>");
                xmlStr.append("</tt:queryTroubleTicketsRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
   private String tryCreateTTByValuesXmlRequest()
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:tryCreateTroubleTicketsByValuesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<tt:troubleTicketValues>");
               for(int i=0; i<2; i++){
                   xmlStr.append("<tt:item>");
                    xmlStr.append("<tt:troubleDescription>");
                    xmlStr.append("Description set By TT trycreateTTByValues() XMLClient ");
                    xmlStr.append("</tt:troubleDescription>");
                     xmlStr.append("</tt:item>");
               }
                xmlStr.append("</tt:troubleTicketValues>");
                xmlStr.append("</tt:tryCreateTroubleTicketsByValuesRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
   private String closeTTByKeyXmlRequest(String key)
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:closeTroubleTicketByKeyRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<tt:troubleTicketKey>");
                xmlStr.append("\n\t<co:applicationContext>");
                xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                xmlStr.append("\n\t\t<co:systemProperties/>");
                xmlStr.append("\n\t</co:applicationContext>");
                xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                xmlStr.append("\n\t<tt:primaryKey>"+ key + "</tt:primaryKey>");
                xmlStr.append("\n</tt:troubleTicketKey>");
                xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by closeTTByKey() by XML Client" + "</tt:closeOutNarr>");
                xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
                xmlStr.append("</tt:closeTroubleTicketByKeyRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
    private String tryCloseTTByKeysXmlRequest(String[] keys)
      {
             StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                  xmlStr.append("<tt:tryCloseTroubleTicketsByKeysRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
                  xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
                  xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
                  xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
                  xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
                  xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
                  xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
                  //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
                  xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
                  xmlStr.append("<tt:troubleTicketKeys>");
                   for(int i=0; i<keys.length; i++){
                       xmlStr.append("\n\n<tt:item>");
                      // xmlStr.append("<tt:troubleTicketKey>");
                       xmlStr.append("\n\t<co:applicationContext>");
                       xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                       xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                       xmlStr.append("\n\t\t<co:systemProperties/>");
                       xmlStr.append("\n\t</co:applicationContext>");
                       xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                       xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                       xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
                       xmlStr.append("</tt:item>");
                   }
                   xmlStr.append("\n</tt:troubleTicketKeys>");
                   xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
                   xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by closeTTByKey() by XML Client" + "</tt:closeOutNarr>");
                   xmlStr.append("</tt:tryCloseTroubleTicketsByKeysRequest>");
                   System.out.println("The Request is \n\n"+xmlStr.toString());
                   return xmlStr.toString();
      }

     private String tryCloseTTByTemplateXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryCloseTroubleTicketsByTemplateRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:template>");
            xmlStr.append("<tt:troubleDescription>XML</tt:troubleDescription>");
            xmlStr.append("</tt:template>");
            xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
            xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by closeTTBytemplate() by XML Client" + "</tt:closeOutNarr>");
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryCloseTroubleTicketsByTemplateRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
     private String tryCloseTTByTemplatesXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryCloseTroubleTicketsByTemplatesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:templates>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:troubleDescription>Zorro</tt:troubleDescription>");
            xmlStr.append("</tt:item>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:dialog>String</tt:dialog>");
            xmlStr.append("</tt:item>");
            xmlStr.append("</tt:templates>");
            xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
            xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by trycloseTTByTemplates() by XML Client" + "</tt:closeOutNarr>");
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryCloseTroubleTicketsByTemplatesRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
     private String cancelTTByKeyXmlRequest(String key)
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:cancelTroubleTicketByKeyRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<tt:troubleTicketKey>");
                xmlStr.append("\n\t<co:applicationContext>");
                xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                xmlStr.append("\n\t\t<co:systemProperties/>");
                xmlStr.append("\n\t</co:applicationContext>");
                xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                xmlStr.append("\n\t<tt:primaryKey>"+ key + "</tt:primaryKey>");
                xmlStr.append("\n</tt:troubleTicketKey>");
                xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by cancelTTByKey() by XML Client" + "</tt:closeOutNarr>");
                xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
                xmlStr.append("</tt:cancelTroubleTicketByKeyRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
     private String tryCancelTTByKeysXmlRequest(String[] keys)
      {
             StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                  xmlStr.append("<tt:tryCancelTroubleTicketsByKeysRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
                  xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
                  xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
                  xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
                  xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
                  xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
                  xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
                  //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
                  xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
                  xmlStr.append("<tt:troubleTicketKeys>");
                   for(int i=0; i<keys.length; i++){
                       xmlStr.append("\n\n<tt:item>");
                      // xmlStr.append("<tt:troubleTicketKey>");
                       xmlStr.append("\n\t<co:applicationContext>");
                       xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                       xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                       xmlStr.append("\n\t\t<co:systemProperties/>");
                       xmlStr.append("\n\t</co:applicationContext>");
                       xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                       xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                       xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
                       xmlStr.append("</tt:item>");
                   }
                   xmlStr.append("\n</tt:troubleTicketKeys>");
                   xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
                   xmlStr.append("\n\t<tt:closeOutNarr>"+ "Closed by tryCancelTTByKey() by XML Client" + "</tt:closeOutNarr>");
                   xmlStr.append("</tt:tryCancelTroubleTicketsByKeysRequest>");
                   System.out.println("The Request is \n\n"+xmlStr.toString());
                   return xmlStr.toString();
      }
    private String tryCancelTTByTemplateXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryCancelTroubleTicketsByTemplateRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>10</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:template>");
            xmlStr.append("<tt:troubleDescription>XML</tt:troubleDescription>");
            xmlStr.append("</tt:template>");
            xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
            xmlStr.append("\n\t<tt:closeOutNarr>"+ "cancelled by tryCancelTTByTemplate() by XML Client" + "</tt:closeOutNarr>");
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryCancelTroubleTicketsByTemplateRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
     private String tryCancelTTByTemplatesXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryCancelTroubleTicketsByTemplatesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>15</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:templates>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:troubleDescription>Zorro</tt:troubleDescription>");
            xmlStr.append("</tt:item>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:dialog>String</tt:dialog>");
            xmlStr.append("</tt:item>");
            xmlStr.append("</tt:templates>");
            xmlStr.append("\n\t<tt:status>"+ "CABLEFAILURE" + "</tt:status>");
            xmlStr.append("\n\t<tt:closeOutNarr>"+ "cancelled by tryCancelTTByTemplates() by XML Client" + "</tt:closeOutNarr>");
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryCancelTroubleTicketsByTemplatesRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
     private String escalateTTByKeyXmlRequest(String key)
   {
          StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
               xmlStr.append("<tt:escalateTroubleTicketByKeyRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
               xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
               xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
               xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
               xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
               xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
               xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
               //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
               xmlStr.append("<tt:troubleTicketKey>");
                xmlStr.append("\n\t<co:applicationContext>");
                xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                xmlStr.append("\n\t\t<co:systemProperties/>");
                xmlStr.append("\n\t</co:applicationContext>");
                xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                xmlStr.append("\n\t<tt:primaryKey>"+ key + "</tt:primaryKey>");
                xmlStr.append("\n</tt:troubleTicketKey>");

                xmlStr.append(buildEscalationList());
              // xmlStr.append("<tt:escalationList/>");
                xmlStr.append("</tt:escalateTroubleTicketByKeyRequest>");
                System.out.println("The Request is \n\n"+xmlStr.toString());
                return xmlStr.toString();
   }
    private String tryEscalateTTByKeysXmlRequest(String[] keys)
          {
                 StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
              xmlStr.append("<tt:tryEscalateTroubleTicketsByKeysRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
              xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
              xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
              xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
              xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
              xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
              xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
              //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
              xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
              xmlStr.append("<tt:troubleTicketKeys>");
               for(int i=0; i<keys.length; i++){
                   xmlStr.append("\n\n<tt:item>");
                  // xmlStr.append("<tt:troubleTicketKey>");
                   xmlStr.append("\n\t<co:applicationContext>");
                   xmlStr.append("\n\t\t<co:factoryClass>com.sun.appserv.naming.S1ASCtxFactory</co:factoryClass>");
                   xmlStr.append("\n\t\t<co:url>\"iiop://127.0.0.1:3700\"</co:url>");
                   xmlStr.append("\n\t\t<co:systemProperties/>");
                   xmlStr.append("\n\t</co:applicationContext>");
                   xmlStr.append("\n\t<co:applicationDN>System/RI/ApplicationType/TroubleTicket/Application/1-0;1-0-3;OSSJTTRI</co:applicationDN>");
                   xmlStr.append("\n\t<co:type>tt:TroubleTicketValue</co:type>");
                   xmlStr.append("\n\t<tt:primaryKey>"+ keys[i] + "</tt:primaryKey>");
                   xmlStr.append("</tt:item>");
               }
               xmlStr.append("\n</tt:troubleTicketKeys>");
               xmlStr.append(buildEscalationList());
               xmlStr.append("</tt:tryEscalateTroubleTicketsByKeysRequest>");
               System.out.println("The Request is \n\n"+xmlStr.toString());
               return xmlStr.toString();
          }

     private String tryEscalateTTByTemplateXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryEscalateTroubleTicketsByTemplateRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>15</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:template>");
            xmlStr.append("<tt:troubleDescription>XML</tt:troubleDescription>");
            xmlStr.append("</tt:template>");
            xmlStr.append(buildEscalationList());
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("false");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryEscalateTroubleTicketsByTemplateRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
       private String tryEscalateTTByTemplatesXmlRequest(){
           StringBuffer xmlStr=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<tt:tryEscalateTroubleTicketsByTemplatesRequest xmlns:xsd=\"http://www.w3.org/20001/XMLSchema\" ");
            xmlStr.append("xmlns:tt=\"http://java.sun.com/products/oss/xml/TroubleTicket\" ");
            xmlStr.append("xmlns:fm=\"http://java.sun.com/products/oss/xml/QualityOfService/FM/Monitor\" ");
            xmlStr.append("xmlns:measurement=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Measurement\" ");
            xmlStr.append("xmlns:threshold=\"http://java.sun.com/products/oss/xml/QualityOfService/PM/Threshold\" ");
            xmlStr.append("xmlns:co=\"http://java.sun.com/products/oss/xml/Common\" ");
            xmlStr.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchemainstance\" ");
            //xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("xsi:schemaLocation=\"http://java.sun.com/products/oss/xml/TroubleTicket/XmlTroubleTicketSchema.xsd\">");
            xmlStr.append("<co:IteratorRequest><co:howMany>15</co:howMany></co:IteratorRequest>");
            xmlStr.append("<tt:templates>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:troubleDescription>Zorro</tt:troubleDescription>");
            xmlStr.append("</tt:item>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:dialog>String</tt:dialog>");
            xmlStr.append("</tt:item>");
            xmlStr.append("</tt:templates>");
            xmlStr.append(buildEscalationList());
            xmlStr.append("<tt:failuresOnly>");
            xmlStr.append("true");
            xmlStr.append("</tt:failuresOnly>");
            xmlStr.append("</tt:tryEscalateTroubleTicketsByTemplatesRequest>");
            System.out.println("The request is \n\n"+ xmlStr.toString());
            return xmlStr.toString();
      }
    private String buildEscalationList(){
            StringBuffer xmlStr=new StringBuffer();
            xmlStr.append(" <tt:escalationList>");
            xmlStr.append("<co:modifier>REMOVE</co:modifier>");
            xmlStr.append("<tt:escalations>");
            xmlStr.append("<tt:item>");
            xmlStr.append("<tt:escPerson>");
            xmlStr.append("<tt:email>myEmail</tt:email>");
            xmlStr.append("<tt:fax>myFax</tt:fax>");
            xmlStr.append("<tt:location xsi:type=\"tt:NorthAmericaAddress\">");
            xmlStr.append("<tt:addressInfo>Info</tt:addressInfo>");
            xmlStr.append("<tt:civicAddress>myCivicAddress</tt:civicAddress>");
            xmlStr.append("<tt:city>mySity</tt:city>");
            xmlStr.append("<tt:state>myState</tt:state>");
            xmlStr.append("<tt:zip>myZip</tt:zip>");
            xmlStr.append("</tt:location>");
            xmlStr.append("<tt:name>myName</tt:name>");
            xmlStr.append("<tt:number>myNumber</tt:number>");
            xmlStr.append("<tt:organizationName>myOrganizationName</tt:organizationName>");
            xmlStr.append("<tt:phone>myPhone</tt:phone>");
            xmlStr.append("<tt:responsible>myResponsible</tt:responsible>");
            xmlStr.append("<tt:sMSAddress>mySMSAddress</tt:sMSAddress>");
            xmlStr.append("</tt:escPerson>");
            xmlStr.append("<tt:escTime>2001-09-11T09:30:47:345</tt:escTime>");
            xmlStr.append("<tt:orgLevel>NOESCALATION</tt:orgLevel>");
            xmlStr.append("<tt:requestPerson>");
            xmlStr.append("<tt:email>myEmail</tt:email>");
            xmlStr.append("<tt:fax>myFax</tt:fax>");
            xmlStr.append("<tt:location xsi:type=\"tt:NorthAmericaAddress\">");
            xmlStr.append("<tt:addressInfo>Info</tt:addressInfo>");
            xmlStr.append("<tt:civicAddress>myCivicAddress</tt:civicAddress>");
            xmlStr.append("<tt:city>mySity</tt:city>");
            xmlStr.append("<tt:state>myState</tt:state>");
            xmlStr.append("<tt:zip>myZip</tt:zip>");
            xmlStr.append("</tt:location>");
            xmlStr.append("<tt:name>myName</tt:name>");
            xmlStr.append("<tt:number>myNumber</tt:number>");
            xmlStr.append("<tt:organizationName>myOrganizationName</tt:organizationName>");
            xmlStr.append("<tt:phone>myPhone</tt:phone>");
            xmlStr.append("<tt:responsible>myResponsible</tt:responsible>");
            xmlStr.append("<tt:sMSAddress>mySMSAddress</tt:sMSAddress>");
            xmlStr.append("</tt:requestPerson>");
            xmlStr.append("<tt:requestState>DENIED</tt:requestState>");
            xmlStr.append("</tt:item>");
            xmlStr.append("</tt:escalations>");
            xmlStr.append("</tt:escalationList>");
            return xmlStr.toString();
    }
    public Document parseXmlString( String docTxt )
       throws org.xml.sax.SAXException,
              Exception,
              java.io.IOException

       {
           Document doc = null ;




        try {


       //Obtain an instance of DocumentBuilderFactory.
       DocumentBuilderFactory dbf =
       DocumentBuilderFactory.newInstance();
       //Specify a validating parser.
      dbf.setValidating(true); // Requires loading the DTD.
       dbf.setNamespaceAware( true );
       dbf.setCoalescing(false);
       dbf.setIgnoringElementContentWhitespace( true );
       dbf.setIgnoringComments( true );



       //Obtain an instance of a DocumentBuilder from the factory.
       DocumentBuilder db = dbf.newDocumentBuilder();
       //Parse the document.

       StringReader reader = new StringReader( docTxt );
       InputSource is = new InputSource( reader );
       doc = db.parse( is );
       }

       catch( javax.xml.parsers.ParserConfigurationException pe ) {
            pe.printStackTrace();
       }
       catch ( Exception ex ) {
            ex.printStackTrace();
       }
       return doc;
       }

   public static void main(String[] args){
       new Testclient();
       System.out.println("Exiting");
    }

}
