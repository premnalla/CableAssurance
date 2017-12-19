package ossj.ttri;

import ossj.fmri.AlarmValueImpl;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.oss.Event;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.trouble.*;
import java.util.Hashtable;

/**
 * TTEventPublisher Singleton Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

// KS 21-08-2003	commented out weblogic JNDI_FACTORY
// KS 21-08-2003	corrected typo in Sun ONE AS7 lookup


public class TTEventPublisher {

    //static public TTEventPublisher singleton = new TTEventPublisher();
    //Java singleton design pattern - private
    static private TTEventPublisher singleton = new TTEventPublisher();

    private TopicConnection connection;
    private TopicSession session;
    private Topic objectEventTopic;
    private TopicPublisher objectPublisher;
    private Topic xmlEventTopic;
    private TopicPublisher xmlPublisher;

    //EventPropertyDescriptor implementations which serve as factories for the TT Events.
    private TroubleTicketAttributeValueChangeEventPDImpl avcePD = null;
    private TroubleTicketCancellationEventPDImpl cnclPD = null;
    private TroubleTicketCloseOutEventPDImpl clsoPD = null;
    private TroubleTicketCreateEventPDImpl crePD = null;

    private TroubleTicketStatusChangeEventPDImpl cePD = null;
    //PG From descriptor or Resource Descriptor
    // KS 21-08-2003 public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public String TT_CONNECTION_FACTORY = null;
    public String TT_TOPIC = null;
    public String TT_XML_TOPIC = null;
    //public final static String separator = " || ";
    public final static String separator = " , ";

    public static final TTEventPublisher getInstance() {
        return singleton;
    }

    /** Creates new TTEventPublisher */
    protected TTEventPublisher() {

        Logger.log("TTEventPublisher ctor");

        Context ctx = null;
        //wipro
//        TT_CONNECTION_FACTORY = applicationDNCFG + "/Comp/TopicConnectionFactory";
//                   TT_TOPIC = applicationDNCFG + "/Comp/JVTEventTopic";
//                   TT_XML_TOPIC = applicationDNCFG + "/Comp/XVTEventTopic";
//
        TT_CONNECTION_FACTORY = TTHelper.projectProperties.getProperty("TT_TOPIC_CONNECTION_FACTORY");
        TT_TOPIC = TTHelper.projectProperties.getProperty("TT_JVT_EVENT_TOPIC");
        TT_XML_TOPIC = TTHelper.projectProperties.getProperty("TT_XVT_EVENT_TOPIC");
        //wipro
        try {
            ctx = new InitialContext();
// KS 21-08-2003 put into comment
//          String applicationDNCFG = (String) ctx.lookup( "/java:comp/env/applicationDN");
            //String applicationDNCFG = (String) ctx.lookup("java:comp/env/applicationDN");
            //TT_CONNECTION_FACTORY = applicationDNCFG + "/Comp/TopicConnectionFactory";
            //TT_TOPIC = applicationDNCFG + "/Comp/JVTEventTopic";
            //TT_XML_TOPIC = applicationDNCFG + "/Comp/XVTEventTopic";
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup(TT_CONNECTION_FACTORY);
            objectEventTopic = (Topic) ctx.lookup(TT_TOPIC);
            xmlEventTopic = (Topic) ctx.lookup(TT_XML_TOPIC);

            connection = factory.createTopicConnection();

            // TopicSession not transacted and messages are auto-acknowledged.
            Logger.log("TTEventPublisher:init: create Session ...");
            session = connection.createTopicSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Logger.log("TTEventPublisher:init: create publisher ...");
            objectPublisher = session.createPublisher(objectEventTopic);
            xmlPublisher = session.createPublisher(xmlEventTopic);
            connection.start(); // start delivery
        } catch (Exception x) {
            Logger.logException("unable to initialize TTEventPublisher", x);
            x.printStackTrace();
            Logger.log("Unable to initialize TTEventPublisher");
        } finally {
            try {
                if (ctx != null) ctx.close();
            } catch (Exception x) {
            }
        }

        //create and initialize the EventPropertyDescriptor impls.
        avcePD = TroubleTicketAttributeValueChangeEventPDImpl.getInstance();
        cnclPD = TroubleTicketCancellationEventPDImpl.getInstance();
        clsoPD = TroubleTicketCloseOutEventPDImpl.getInstance();
        crePD = TroubleTicketCreateEventPDImpl.getInstance();

        cePD = TroubleTicketStatusChangeEventPDImpl.getInstance();

        avcePD.setTopicSession(session);
        cnclPD.setTopicSession(session);
        clsoPD.setTopicSession(session);
        crePD.setTopicSession(session);
        cePD.setTopicSession(session);

    }


    //public void publish( TroubleTicketEvent event, Hashtable properties )
    public void publish(Event event, Hashtable properties) {

        ObjectMessage objMsg = null;
        TextMessage txtMsg = null;


        //---------------------------------------------------------------------
        // Send the object and XML text based messages.  The implementation of
        // the "make" methods for objects and text messages set the filterable
        // properties of the messages.
        //
        // The Hashtable contains name/value pairs, where the name corresponds
        // to the xxxx_PROP_NAME elements in the PropertyDescriptor interfaces
        // in the javax.oss.trouble package.  The value is one of the Integer,
        // Long, Boolean, etc. object types.  The ossj.ttri.utils class is
        // used to actually set the properties of the JMS message.
        //
        // For Text messages (i.e. XML messages) the same properties are used.
        //----------------------------------------------------------------------------
        if (event instanceof TroubleTicketAttributeValueChangeEvent) {
            objMsg = avcePD.makeTroubleTicketAttributeValueChangeEventObjectMessage
                    ((TroubleTicketAttributeValueChangeEvent) event, properties);
            txtMsg = avcePD.makeTroubleTicketAttributeValueChangeEventTextMessage
                    ((TroubleTicketAttributeValueChangeEvent) event, properties);
        } else if (event instanceof TroubleTicketCancellationEvent) {
            objMsg = cnclPD.makeTroubleTicketCancellationEventObjectMessage
                    ((TroubleTicketCancellationEvent) event, properties);
            txtMsg = cnclPD.makeTroubleTicketCancellationEventTextMessage
                    ((TroubleTicketCancellationEvent) event, properties);
        } else if (event instanceof TroubleTicketCloseOutEvent) {
            objMsg = clsoPD.makeTroubleTicketCloseOutEventObjectMessage
                    ((TroubleTicketCloseOutEvent) event, properties);
            txtMsg = clsoPD.makeTroubleTicketCloseOutEventTextMessage
                    ((TroubleTicketCloseOutEvent) event, properties);
        } else if (event instanceof TroubleTicketCreateEvent) {
            objMsg = crePD.makeTroubleTicketCreateEventObjectMessage
                    ((TroubleTicketCreateEvent) event, properties);
            txtMsg = crePD.makeTroubleTicketCreateEventTextMessage
                    ((TroubleTicketCreateEvent) event, properties);
        } else if (event instanceof TroubleTicketStatusChangeEvent) {
            objMsg = cePD.makeTroubleTicketStatusChangeEventObjectMessage
                    ((TroubleTicketStatusChangeEvent) event, properties);
            txtMsg = cePD.makeTroubleTicketStatusChangeEventTextMessage
                    ((TroubleTicketStatusChangeEvent) event, properties);
        } else {
            Logger.log("TTEventPublisher::publish: Unknown event type");
            return;
        }


        // publish an Object
        try {
            objectPublisher.publish(objMsg);
        } catch (Exception x) {
            Logger.logException("Exception caught in TTEventPublisher:publish", x);
            x.printStackTrace();
        }


        // publish an XML message
        try {
            xmlPublisher.publish(txtMsg);
        } catch (Exception x) {
            Logger.logException("Exception caught in TTEventPublisher:publish (XML) ", x);
            x.printStackTrace();
        }
        System.out.println("-----TTEventPublisher:publish end of call----");
    }


    //--------------------------------------------------------------------------
    // "Build and send" methods for events.  Expose strongly typed methods to
    // create/send events with their required attributes.  Use the PropertyDescriptor
    // implementations to create the events, and set the specific and base
    // event data.
    //
    // For now, set the TT app specific filterable properties on the create and
    // AVC events.  Q.  platform specific filterables?
    // the final set of filterable properties will be defined in the TT spec soon.
    //
    //--------------------------------------------------------------------------
    public void sendAVCEvent(TroubleTicketValue ttVal) {
        java.util.Hashtable props = makeProperties(ttVal);
        /*log("---IN sendAVCEvent----");
        String[] attrNames = ttVal.getPopulatedAttributeNames();
        log( "Number of Populated Attributes in Value being Sent" );
        log( new Integer(attrNames.length).toString());*/

        TroubleTicketAttributeValueChangeEvent evnt = (TroubleTicketAttributeValueChangeEvent) avcePD.makeEvent();

        evnt.setTroubleTicketValue(ttVal);
        setBaseEventData(evnt);
        publish(evnt, props);
    }

    public void sendCancellationEvent(TroubleTicketKey ttKey, String closeOutNarr) {
        java.util.Hashtable props = new java.util.Hashtable();

        TroubleTicketCancellationEvent evnt = (TroubleTicketCancellationEvent) cnclPD.makeEvent();
        evnt.setTroubleTicketKey(ttKey);
        evnt.setCloseOutNarr(closeOutNarr);
        setBaseEventData(evnt);
        publish(evnt, props);
    }

    public void sendCloseOutEvent(TroubleTicketValue ttVal) {
        java.util.Hashtable props = makeProperties(ttVal);

        TroubleTicketCloseOutEvent evnt = (TroubleTicketCloseOutEvent) clsoPD.makeEvent();
        evnt.setTroubleTicketValue(ttVal);
        setBaseEventData(evnt);
        publish(evnt, props);
    }

    public void sendCreateEvent(TroubleTicketValue ttVal) {
        java.util.Hashtable props = makeProperties(ttVal);
        TroubleTicketCreateEvent evnt = (TroubleTicketCreateEvent) crePD.makeEvent();
        evnt.setTroubleTicketValue(ttVal);
        setBaseEventData(evnt);
        publish(evnt, props);
    }

    public void sendStatusChangeEvent(TroubleTicketKey ttKey,
                                      int status,
                                      java.util.Date statusTime,
                                      int state) {

        java.util.Hashtable props = new java.util.Hashtable();

        TroubleTicketStatusChangeEvent evnt = (TroubleTicketStatusChangeEvent) cePD.makeEvent();
        evnt.setTroubleTicketKey(ttKey);
        evnt.setStatus(status);
        evnt.setStatusTime(statusTime);
        evnt.setState(state);
        setBaseEventData(evnt);
        publish(evnt, props);
    }


    public java.util.Hashtable makeProperties(TroubleTicketValue ttVal) {
        java.util.Hashtable props = new java.util.Hashtable();
        String parm = null;

        //---------------------
        // Originator
        //---------------------
        parm = ttVal.getOriginator();
        if (parm != null)
            props.put(TroubleTicketEventPropertyDescriptor.OSSJ_ORIGINATOR_PROP_NAME, parm);

        //---------------------
        // Alarm Id List
        //---------------------
        AlarmValueList relatedAlarms = ttVal.getRelatedAlarmList();
        if (relatedAlarms != null) {
            parm = new String();
            AlarmValue[] alarms = relatedAlarms.get();
            for (int x = 0; x < alarms.length; x++) {
                String alarmId = ((AlarmValueImpl) alarms[x]).getAlarmKey().getAlarmPrimaryKey();
                if (alarmId != null) {
                    if (x > 0)
                        parm = parm.concat(separator);
                    parm = parm.concat(alarmId);
                }
            }
        }
        if (parm != null)
            props.put(TroubleTicketEventPropertyDescriptor.OSSJ_RELATED_ALARMID_LIST_PROP_NAME, parm);

        //----------------------------
        // Suspect Object Id List
        //----------------------------
        SuspectObject[] suspectObjs = ttVal.getSuspectObjectList();
        if (suspectObjs != null) {
            parm = new String();
            for (int x = 0; x < suspectObjs.length; x++) {
                SuspectObject so = suspectObjs[x];
                if (so != null) {
                    String soId = so.getSuspectObjectId();
                    if (soId != null) {
                        if (x > 0)
                            parm = parm.concat(separator);
                        parm = parm.concat(soId);
                    }
                }
            }
        }
        if (parm != null)
            props.put(TroubleTicketEventPropertyDescriptor.OSSJ_SUSPECTOID_LIST_PROP_NAME, parm);


        //---------------------
        // System DN
        //---------------------
        parm = ttVal.getTroubleSystemDN();
        if (parm != null)
            props.put(TroubleTicketEventPropertyDescriptor.OSSJ_SYSTEMDN_PROP_NAME, parm);

        //---------------------
        // Customer ID
        //---------------------
        PersonReach cust = ttVal.getCustomer();
        if (cust != null) {
            //for now, assume we filter on customer number.
            parm = cust.getNumber();
            if (parm != null)
                props.put(TroubleTicketEventPropertyDescriptor.OSSJ_CUSTOMERID_PROP_NAME, parm);
        }

        //---------------------
        // Troubled Object Id
        //---------------------
        parm = ttVal.getTroubledObject();
        if (parm != null)
            props.put(TroubleTicketEventPropertyDescriptor.OSSJ_TROUBLEDOID_PROP_NAME, parm);

        return props;

    }


    public void setBaseEventData(Event evnt) {

        evnt.setEventTime(new java.util.Date());  //time of event origination (now)
        evnt.setApplicationDN(TTConfig.getInstance().applicationTypeDN);

    }


}
