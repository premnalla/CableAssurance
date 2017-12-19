package ossj.qos.fmri;

/**
 * BackEndEventProducer
 * 
 * @author  Audrey Ward
 * @version 1.0
 *
 * ¨ Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.naming.*;
import javax.jms.*;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.oss.fm.monitor.*;
import javax.oss.*;
import ossj.qos.util.Util;
import ossj.qos.util.Trace;

public class BackEndEventProducer
{
    private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    private static final String MOI_BASE = "IRPNetwork=Example/Subnet=TN2/BSC=BSC15/Cell=0015/CellMeasurement=";
    private static final String JVT_HOME = "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTHome";

    private InitialContext namingContext = null;

    public  boolean done = false;

    private AMEventPublisher eventPublisher = null;

    private AlarmValue alarmBase = null;
    
    private Trace myLog = null;

    public BackEndEventProducer() {
    }

    //------------------------------------------------------------------------
    // init -
    //
    // get the intial context and create the Alarm Monitor Session Bean
    //------------------------------------------------------------------------
    public void init(String url) {
        
        myLog = Util.createLog( "ossj.qos.fmri");

        try {
            namingContext = getInitialContext(url);
        }
        catch (NamingException nex) {
            myLog.logException("BackEndEventProducer: init() ", nex );
            System.exit(1);
        }

        try {
            eventPublisher = new AMEventPublisher();
            eventPublisher.init( namingContext, SessionResourceConstants.BEAM_TOPIC, 
                                 "jms/sampleTCF");
            initializeAlarmBase();
        }
        catch( Exception x ) {
            myLog.logException(" BackEndEventProducer: init() - creating eventPublisher ", x );
            eventPublisher = null;
        }
        
         try {
            JVTAlarmMonitorHome amSessionHome = (JVTAlarmMonitorHome)javax.rmi.PortableRemoteObject.narrow(
            namingContext.lookup( JVT_HOME ),
                                  JVTAlarmMonitorHome.class);
            JVTAlarmMonitorSession amSession = amSessionHome.create();
            alarmBase = amSession.makeAlarmValue();
            initializeAlarmBase();
            
        }
        catch (NamingException nex2)
        {
            myLog.logException("BackEndEventProducer: init() - lookup for JVTAlarmMonitorHome ", nex2 );
            System.exit(1);
        }
        catch (RemoteException rex)
        {
            myLog.logException("BackEndEventProducer: init() - creating JVTAlarmMonitorSession ", rex );
            System.exit(2);
        }
        catch (CreateException cex)
        {
            myLog.logException("BackEndEventProducer: init() -  creating of JVTAlarmMonitorSession ", cex );
            System.exit(3);
        }
        myLog.log("BackEndEventProducer: init() -  initialization complete.");
        
        return;
    }

    //------------------------------------------------------------------------
    // cleanup - free resources
    //------------------------------------------------------------------------
    public void cleanup() {
        eventPublisher.close();

        myLog.log("BackEndEventProducer: cleanup() -  complete");
        return;
    }

    //------------------------------------------------------------------------
    //
    //  main
    //
    //------------------------------------------------------------------------
    public static void main(String[] args)
        throws Exception {
        
        //VP workaround 4766976
        {
            com.sun.enterprise.Switch  sw = com.sun.enterprise.Switch.getSwitch();
            sw.setContainerType(com.sun.enterprise.Switch.APPCLIENT_CONTAINER);
        }
       
        //end VP

        String url       = "iiop://localhost:3700";

        System.out.println(" ");
        System.out.println("**************************************************************");
        System.out.println("*               BackEnd Event Producer                       *");
        System.out.println("**************************************************************");
        System.out.println(" ");
        System.out.println("--------------------------------------------------------------");
        System.out.println(" This simple simulator provides a mechanism to add, modify,   ");
        System.out.println(" and clear alarms in an alarm list.");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        if (args.length == 0) {
            System.out.println("BackEndEventProducer: using default WLS server (" + url + ")");
        }
        else
        if (args.length == 1) {
            url = args[0];
            System.out.println("BackEventProducer: using WLS server at: " + url);
        }
        else
        if (args.length > 1) {
            System.out.println("Usage: java javax.ossj.qos.fmri.BackEndEventProducer [WebLogicURL]");
            return;
        }
        
        BackEndEventProducer eventProducer = new BackEndEventProducer();

        eventProducer.init(url);
        String descrip = null;

        while (!eventProducer.done) {
            descrip = getOption();
            descrip = descrip.trim();

            if (descrip.equals("single")) {
                eventProducer.generateSingleEvent();
            }
            else if (descrip.equals("rebuilt")) {
                eventProducer.generateRebuiltEvent();
            }
            else if (descrip.equals("quit")) {
                eventProducer.done = true;
                System.out.println("quit entered - terminating.");
            }
        }
        eventProducer.cleanup();

        return;
    }

    //-----------------------------------------------------------------------
    // prompt user for an option
    //------------------------------------------------------------------------
    public static String getOption()
    throws IOException {
        BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in));
        String line = null;

        do {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("**************************************************************");
            System.out.println("*               BackEnd Event Producer                       *");
            System.out.println("**************************************************************");
            System.out.println(" ");
            System.out.println("--------------------------------------------------------------");
            System.out.println("Enter one of the following options: ");
            System.out.println(" ");
            System.out.println("  \"single\"         - add/modify a single alarm");
            System.out.println("  \"rebuilt\"        - trigger an NotifyAlarmListRebuiltEvent");
            System.out.println("  \"quit\"           - stop the BackEnd Event Producer");
            System.out.println("---------------------------------------------------------------");
            System.out.print("Enter an option: ");

            line = msgStream.readLine();
        }
        while ( (line == null) &&
        (line.trim().length() == 0) );

        return line;
    }

    private void generateSingleEvent() {

        String queryArgs = null;
        StringTokenizer tok = null;

        try {

            do {
                queryArgs = getSingleArgs();
                tok = new StringTokenizer( queryArgs, ",");
            } while( tok.countTokens() < 2 );

            String moiSuffix = tok.nextToken().trim();
            String severityString = tok.nextToken().trim();

            short severity = validateSeverity( severityString );
           
            alarmBase.setManagedObjectInstance( MOI_BASE + moiSuffix);
            alarmBase.setPerceivedSeverity( severity );
        
            // Generate a simulated MOI unique key for the new alarm. 
            long timeMillis = java.lang.System.currentTimeMillis();
            Long longTimeMillis = new Long( timeMillis );
            String timeKey = new String( longTimeMillis.toString() );
            AlarmKey key = alarmBase.makeAlarmKey();
            key.setAlarmPrimaryKey(timeKey);
            alarmBase.setAlarmKey( key );
            
            // Generate a simulated MOI notification id
            timeMillis = java.lang.System.currentTimeMillis();
            longTimeMillis = new Long( timeMillis );
            alarmBase.setNotificationId( longTimeMillis.toString() );
            
            if ( severity == PerceivedSeverity.CLEARED ) {
                alarmBase.setAlarmClearedTime( new Date() );
                publishAlarmEvent( NotifyClearedAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE, alarmBase );
                System.out.println( "Published a NotifyClearedAlarmEvent: " );
            }
            else {
                alarmBase.setAlarmRaisedTime( new Date() );
                publishAlarmEvent( NotifyNewAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE, alarmBase );
                System.out.println( "Published a NotifyNewAlarmEvent: " );
            }
            System.out.println( "       MOI => " + alarmBase.getManagedObjectInstance() );
            System.out.println( "  Severity => " + alarmBase.getPerceivedSeverity() );
        }
        catch ( Exception e ) {
            myLog.logException( "BackEndEventPublisher: Problem publishing event. ", e );
        }
        return;
    }

    private void publishAlarmEvent( String eventType, AlarmValue alarm ) throws JMSException {

        // make the NotifyNewAlarmEvent or NotifyClearedAlarmEvent
        Event event =  EventFactory.makeEvent( eventType, alarm );
        
        // Publish the event
        HashMap evProperties = new HashMap();
        EventPropertyDescriptor epd = EventFactory.getPropertyDescriptor(  eventType );
        eventPublisher.publish( event, evProperties, (MessageBuilder)epd);
        return;
    }
    
    private void generateRebuiltEvent() {

        // Generate key to trigger rebuilt event by the agent using a NotifyNewAlarmEvent. 
        AlarmKey key = alarmBase.makeAlarmKey();
        key.setAlarmPrimaryKey("rebuild");
        alarmBase.setAlarmKey( key );
        alarmBase.setAlarmRaisedTime( new Date() );
        alarmBase.setManagedObjectInstance("Trigger for NotifyAlarmListRebuiltEvent");
        alarmBase.setPerceivedSeverity( PerceivedSeverity.WARNING );
        // Generate a simulated MOI notification id
        long timeMillis = java.lang.System.currentTimeMillis();
        Long longTimeMillis = new Long( timeMillis );
        alarmBase.setNotificationId( longTimeMillis.toString() );

        try {
            publishAlarmEvent( NotifyNewAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE, alarmBase );
            myLog.log( "Trigger the generation of a NotifyAlarmListRebuiltEvent.");
        }
        catch ( Exception e ) {
            myLog.logException( "BackEndEventProducer: generateRebuiltEvent ", e );
            e.printStackTrace();
        }
        return;
    }
    
    private short validateSeverity( String severityString ) 
    throws java.lang.IllegalArgumentException {
        short severity = 1;
        try { 
            severity = Short.parseShort(severityString);
            if ( severity > 6 || severity < 0 ) {
                throw new java.lang.IllegalArgumentException( "Severity must be a value from 1-6" );
            }
        }
        catch ( NumberFormatException e ) {
            throw new java.lang.IllegalArgumentException( "Severity must be a value from 1-6" );
        }
        return severity;
    }
    
    public static String getSingleArgs()
    throws IOException {
        
        BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in) );
        String line = null;

        do
        {
            System.out.println(" ");
            System.out.println("**************************************************************");
            System.out.println("*               BackEnd Event Producer                       *");
            System.out.println("**************************************************************");
            System.out.println(" ");
            System.out.println("-------------------------------------------------------------- ");
            System.out.println("Enter a numerical MOI suffix that will be concatenated to the ");
            System.out.println("to a MOI DSN base and a severity. ");
            System.out.println(" ");
            System.out.println("          Delimit the arguments with a comma ");
            System.out.println("  ");
            System.out.println("   Valid severity values are : Indeterminant = 1, ");
            System.out.println("                                    Critical = 2, ");
            System.out.println("                                       Major = 3, ");
            System.out.println("                                       Minor = 4, ");
            System.out.println("                                     Warning = 5, ");
            System.out.println("                                     Cleared = 6  ");
            System.out.println("   Example User Input: 0010, 2");
            System.out.println(" ");
            System.out.println("   The complete MOI DSN using the above example user data is: ");
            System.out.println("   IRPNetwork=Example/Subnet=TN2/BSC=BSC15/Cell=0015/CellMeasurement=0010 ");
            System.out.println("  ");
            System.out.println("-------------------------------------------------------------- ");
            System.out.print(" Enter MOI suffix and severity: ");

            line = msgStream.readLine();
        }
        while ( (line == null) ||
        (line.trim().length() == 0) );

        return line;
    }
    
    private void initializeAlarmBase() {
        alarmBase = new AlarmValueImpl();
        
        alarmBase.setManagedObjectClass("CellMeasurement" );
        alarmBase.setSystemDN("System Domain");
        alarmBase.setAlarmType( AlarmType.QUALITY_OF_SERVICE_ALARM );
        alarmBase.setProbableCause( ProbableCause.CALL_SETUP_FAILURE );
        // SPECIFICPROBLEM not supported in this version of the QOSFMRI
        //alarmBase.setSpecificProblem("0234:0346:0023");
        alarmBase.setAdditionalText("Houston we have a problem");

        // build attribute change values..
        int numChAttrs = 3;
        ArrayList attrChanValList = new ArrayList(numChAttrs);
        for ( int i = 0; i < numChAttrs; i++ ) {
            AttributeValueChange attrChanVal = alarmBase.makeAttributeValueChange();
            attrChanVal.setAttributeName("Name:" + i);
            attrChanVal.setAttributeType("Integer");
            attrChanVal.setOldValue( new Integer( i + 2 ) );
            attrChanVal.setNewValue( new Integer( i + 3 ) );
            attrChanValList.add(attrChanVal);
        }
        // add to the alarm...
        AttributeValueChange[] attrChanValArray = (AttributeValueChange[])
        attrChanValList.toArray(new AttributeValueChange[0]);
        alarmBase.setAttributeChanges(attrChanValArray);

        // build monitored values..
        int numAttrs = 3;
        ArrayList attrValList = new ArrayList(numAttrs);
        for ( int i = 0; i < numAttrs; i++ ) {
            AttributeValue attrVal = alarmBase.makeAttributeValue();
            attrVal.setAttributeName("MonitoredName:" + i);
            attrVal.setAttributeType("Integer");
            attrVal.setValue( new Integer( i + 5 ) );
            attrValList.add(attrVal);
        }
        // add to the alarm...
        AttributeValue[] attrValArray = (AttributeValue[])
        attrValList.toArray(new AttributeValue[0]);
        alarmBase.setMonitoredAttributes(attrValArray);

        return;
    }

    private InitialContext getInitialContext(String url)
    throws NamingException 
    {
        InitialContext ic = null;
        
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
            env.put(Context.PROVIDER_URL, url);
            env.put("weblogic.jndi.createIntermediateContexts", "true");
            //ic = new InitialContext(env);
            ic = new InitialContext();
        }
        catch (NamingException ne) {
            myLog.logException("BackEndEventProducer: getInitialContext() ", ne );
            myLog.log("Please make sure that the server is running.");
            throw ne;
        }
        return ic;
    }
}



