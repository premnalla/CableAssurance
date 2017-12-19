package ossj.qos.ri.pm.testclient;
/*
 * Copyright: Copyright ? 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */

// imports
import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.oss.*;
import javax.oss.fm.monitor.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.*;
import javax.oss.pm.util.*;

/**
 * Simple test client for pms. Handles and controls a user interface from
 * which it is possible to define and create a simple pm.
 *
 * @see ossj.qos.ri.pm.pm.adapter
 * @author Henrik Lindstrom, Ericsson
 */
public class PmClient {
    
    String pmConnectionFactory = null; //"System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/TopicConnectionFactory";
    String pmTopic = null; //"System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTEventTopic";
    String homeName = null;
    boolean eventListenerOn = false;
    TopicConnection con = null;
    /**
     * Environment properties.
     */
    public static Properties envProperties = null;

    /**
     * Pm session.
     */
    public JVTPerformanceMonitorSession pmSession = null;

    /**
     * Pm monitor key for created pm.
     */
    public PerformanceMonitorKey pmMonitorKey = null;

    public PerformanceMonitorValue pmMonitorVal = null;

    /**
     * Currently observed object.
     */
    public String observableObject = null;

    /**
     * Currently observed attribute (name).
     */
    public String attribute = null;

    /**
     * The initial context.
     */
    public InitialContext initialContext = null;

	public ArrayList observableClasses;
	public HashMap observableAttributes;
	public HashMap observableObjects;

    public ArrayList webObject;

    public Vector  allJobName = new Vector();

    public String currentData;
     
    public String realtimeData;
    public boolean ifNewData = false;

    public static PmClient pmClient = null;

    /**
     * Create new client.
     */
    public PmClient() {
    }

    public PmClient(String FileName) {
 
        envProperties = new Properties( );

        File propertyFile = new File(FileName);
        if ( propertyFile.exists() ) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream( propertyFile );
                BufferedInputStream bis = new BufferedInputStream( fis );
                envProperties.load( bis );
            } catch( IOException e ) {
                System.out.println("exception="+e);
            } finally {
                try {
                    if ( fis != null ) fis.close();
                } catch ( IOException e ) {
                    System.out.println("exception="+e);
                }
            }
        } else {
            System.out.println("file not exist");
            System.exit(1);
        }
        homeName = envProperties.getProperty("PERFORMANCE_MONITOR_HOME_JNDI_NAME");
        //"System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTHome";

        pmConnectionFactory = envProperties.getProperty("PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY");
        pmTopic = envProperties.getProperty("PERFORMANCE_MONITOR_JMS_TOPIC");
        
        System.out.println("homeName="+homeName);
        System.out.println("pmConnectionFactory="+pmConnectionFactory);
        System.out.println("pmTopic="+pmTopic);
        // initialize PM event receive
        if ( pmConnectionFactory == null || pmTopic == null) {
            System.out.println(" PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY or PERFORMANCE_MONITOR_JMS_TOPIC not specified in property file...");   
            System.exit(1);
        }
        
        try {

            initInitialContext(envProperties);
            
            //initPMEventListener(pmConnectionFactory,pmTopic);   // added by Bruce wu

            connectToPmSession(homeName);
        } catch ( PmClientException e ) {
            System.out.println("PmClientException");
            System.exit(1);
        } catch ( Exception e ) {
            System.out.println("Exception");
            System.exit(1);
        }
    }

    public static PmClient getInstance(String FileName) {
        if(pmClient == null) {
            pmClient = new PmClient(FileName);
        }

        return pmClient;
    }

    /**
     * Starts the client. Makes a connection to the pm monitor session EJB.
     * Then tries to create a pm.
     *
     * @param args first argument should be the path and file name for
     * a property file with information about how to connect to app. server.
     * If not present, then default values will be used(WebLogic on localhost).
     */
    public static void main(String[] args)
        throws RemoteException {
        
        //VP workaround 4766976
        {
            com.sun.enterprise.Switch  sw = com.sun.enterprise.Switch.getSwitch();
            sw.setContainerType(com.sun.enterprise.Switch.APPCLIENT_CONTAINER);
        }
        //end VP
        envProperties = new Properties( );

        if ( args.length==1 ) {
            File propertyFile = new File( args[0] );
            if (! propertyFile.exists() ) {
                System.out.println("file ["+args[0]+" not exist");
                System.exit(1);
            }
        } else {
            System.out.println("Property file missing...");
            System.exit(1);
        }


        // create pm client
        PmClient pmClient = new PmClient(args[0]);
        

        try {
            //pmClient.initInitialContext(envProperties);

            //pmClient.initPMEventListener(pmConnectionFactory,pmTopic);

            //pmClient.connectToPmSession(pmClient.homeName);

            for(;;){
                pmClient.run();
            }
        } catch ( IOException e ) {
            System.out.println("IOException");
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        } catch ( PmClientException e ) {
            System.out.println("PmClientException");
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        } catch ( Exception e ) {
            System.out.println("Exception");
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }

    }

	public void run ()
        throws PmClientException, IOException, Exception {
        BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in));
        String line = null;
        String line2 = null;

		System.out.println("");
		System.out.println("");
		/*System.out.println("1) List All Monitor jobs");
          System.out.println("2) Display All Attributes of a Monitor job");
          System.out.println("3) Get Current Report Data ");
          System.out.println("4) Create a Monitor job  by Object");
          System.out.println("5) Create a Monitor job  by Class");
          System.out.println("5) Display All observable Classes , Object and Attributes ");
          System.out.println("6) Exit ");*/
		System.out.println("1) Display All observable Classes , Object and Attributes ");
		System.out.println("2) Create a Monitor job  by Object");
		System.out.println("3) List All Monitor jobs");
		System.out.println("4) Get Current Report Data");
		System.out.println("5) Display All Attributes of a Monitor job ");
		if (eventListenerOn)
            System.out.println("6) Stop Event listener ");
        else 
            System.out.println("6) Start Event listener ");
            
		System.out.println("7) Exit ");
		System.out.print("Enter an option[1-7]: ");

		line = msgStream.readLine();
		int opt;
		try{
			opt = Integer.parseInt(line);
		}catch(Exception e ) {
			return;
		}
		switch(opt)  {
		case 3  :
			listAllPMJob();
			break;
		case 5  :
			System.out.print("Enter Monitor jobs Name : ");
			line = msgStream.readLine();
			printMonitorJobValue(findMonitorJobByName(line));
			break;
		case 4 :
			System.out.print("Enter Monitor jobs Name : ");
			line = msgStream.readLine();
            getReportData(line);
			break;
		case 2 :
			System.out.print("Enter Monitor jobs Name : ");
			line = msgStream.readLine();
			System.out.print("Enter Monitor jobs Object Name : ");
			line2 = msgStream.readLine();
            createPmMonitorJobByObject(line, line2);
			break;
            /*case 5 :
              System.out.print("Enter Monitor jobs Name : ");
              line = msgStream.readLine();
              System.out.print("Enter Monitor jobs Class Name : ");
              line2 = msgStream.readLine();
              createPmMonitorJobByClass(line, line2);
              break;*/
		case 1   :
			discoverObservableObjects();
			break;
        case 6:
            
            try {
                if (!eventListenerOn) {
                    if (con == null) initPMEventListener(pmConnectionFactory,pmTopic);
                    startEvent();
                    eventListenerOn = true;
                } else {
                    stopEvent();
                    eventListenerOn = false;
                }
            } catch ( Exception e ) {
                System.out.println("Exception");
                e.printStackTrace();
                System.exit(1);
            }
            break;
		case 7  :
			System.exit(0);
			break;
		default:
			System.out.println("wrong number");
			return ;	
		}
	}	

    /*
     * Connect to the pm monitor session EJB.
     * @param homeName
     */
    private void connectToPmSession(String homeName )
        throws PmClientException {
        try {
            // Look up the Home interface in JNDI.
            Object homeObject = initialContext.lookup(homeName);
            JVTPerformanceMonitorHome tmHome =
                (JVTPerformanceMonitorHome) PortableRemoteObject.narrow(
                                                                        homeObject, JVTPerformanceMonitorHome.class);

            // create the session bean.
            //System.out.println(tmHome.toString());
            pmSession = tmHome.create();
            System.out.println("This is after session bean have been created");
        } catch ( NamingException e ) {
            System.out.println("connectToPmSession() exception="+e);
            throw new PmClientException( e.getMessage() );
        } catch( RemoteException e ) {
            System.out.println("connectToPmSession() exception="+e);
            throw new PmClientException( e.getMessage() );
        } catch ( CreateException e ) {
            System.out.println("connectToPmSession() exception="+e);
            throw new PmClientException( e.getMessage() );
        }
    }

    /**
     * Initialize initial context.
     * @param envProp envrionment properties needed to initialize
     */
    private void initInitialContext( Properties envProp ) 
        throws PmClientException {
        try {
            initialContext = new InitialContext();
        } catch ( NamingException e ) {
            throw new PmClientException( e.getMessage() );
        }
    }

    /**
     * Init PM event receiver.
     */
    private void initPMEventListener(String factoryName,String topicName )
        throws PmClientException {
        String filter = PerformanceDataEventDescriptor.OSS_EVENT_TYPE_PROP_NAME + " = '" + PerformanceDataEventDescriptor.OSS_EVENT_TYPE_VALUE + "'" ;
        //System.out.println("filter ===>");
        //System.out.println(filter);
        initEventListener( factoryName, topicName, filter, new PerformanceDataEventHandler() );
    }

    private void initEventListener(String factoryName,String topicName,
                                   String filter, MessageListener listener ) throws PmClientException
    {
        //System.out.println( "------ into init event listener ------ ");

        TopicConnectionFactory conFactory = null;
        try {
            System.out.println("Get TopicConnectionFactory...");
            conFactory = (TopicConnectionFactory) initialContext.lookup(factoryName);

            System.out.println("Create TopicConnection...");
            con = conFactory.createTopicConnection();
            System.out.println("Create TopicSession...");
            TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE ); //
            System.out.println("Get Topic...");
            Topic topic = (Topic) initialContext.lookup(topicName);

            // create a filter to only receive certain events
            //System.out.println("filter="+filter);

            System.out.println("Create Subscriber...");
            TopicSubscriber subscriber = null;
            if ( filter == null ) {
                subscriber = session.createSubscriber(topic);
            } else {
                subscriber = session.createSubscriber(topic, filter, false);
            }

            subscriber.setMessageListener( listener );
            //System.out.println("Start TopicConnection...");
            //con.start();

            //System.out.println("ok!");
        } catch ( JMSException e ) {
            System.out.println("initEventListener()  " + "exception="+e);
            throw new PmClientException( e.getMessage() );
        } catch ( NamingException e ) {
            System.out.println("initEventListener()  " + "exception="+e);
            throw new PmClientException ( e.getMessage() );
        }
    }
    
    private void stopEvent() throws javax.jms.JMSException {
        System.out.println("Stop TopicConnection...");
        if (con != null) con.stop();
    }
    private void startEvent() throws javax.jms.JMSException {
        System.out.println("Start TopicConnection...");
        if (con!=null) con.start();
    }
    
    public void getReportData( String monitorJobName ) {
        System.out.println("tHIS IS in getReportData()");
        try {
            PerformanceMonitorValue pm = findMonitorJobByName( monitorJobName ) ;
            if( pm !=null ){
                System.out.println(" monitor job name " + pm.getName());
                getCurrentData(pmSession, pm.getPerformanceMonitorKey());

            }
        } catch (Exception e) {
            System.out.println("There is some exception generated");
        }
    }

    public PerformanceMonitorKey createPmMonitorJobByObject(String jobname, String objname) {
        try { 
            // Get a PerformanceMonitorByObjectsValue object. 
            // All implementations must support PerformanceMonitorByObjectsValue 
            PerformanceMonitorByObjectsValue pmByObjectsValue = 
                (PerformanceMonitorByObjectsValue) 
                pmSession.makePerformanceMonitorValue( 
                                                      PerformanceMonitorByObjectsValue.VALUE_TYPE); 
                   
            // Populate the object to specify the parameters of the PM job. 
            // generate a report every 4 granularity periods (1 hour) 
            // pmByObjectsValue.setReportPeriod(4); 
            // do not send full reports via JMS (default) 
            //pmByObjectsValue.setReportByEvent(ReportMode.NO_REPORT_MODE); 
            pmByObjectsValue.setReportByEvent(ReportMode.EVENT_SINGLE ); 
            // generate a file specifically for this job 
            pmByObjectsValue.setReportByFile(ReportMode.FILE_SINGLE); 
            pmByObjectsValue.setName( jobname ); 
            // 15 minute granularity period, could also use 
            // getSupportedGranularities() to get the supported GP.  
            //        pmByObjectsValue.setGranularityPeriod(15*60); 
            pmByObjectsValue.setGranularityPeriod(15*4);
            // Assume we want the report for a 3G network and we want it in XML. 
            // Find the appropriate ReportFormat. 
            ReportFormat formatXML3G = null; 
            ReportFormat[] formats = pmSession.getReportFormats(); 
            for (int i = 0; i < formats.length; ++i) { 
                if (formats[i].getTechnology().equals("3G") && 
                    formats[i].getType()== ReportFormat.XML) { 
                    formatXML3G=formats[i]; break; 
                } 
            } 
        
            if (formatXML3G== null) { 
                System.err.println("XML format for 3G network not supported"); 
                return null; 
            } 

            // use XML format for 3G 
            pmByObjectsValue.setReportFormat(formatXML3G); 
            // Specify the objects to be monitored. 
            // Assume that this object is supported. 
            // Could be checked with getSupportedObservableObjects() 
            pmByObjectsValue.setObservedObjects( new String[] {objname}); 
            // Monitor all measurement attributes on the obervable object, 
            // i.e. no need to set the attribute list. 

            // ccao add in here
            int stop = objname.lastIndexOf('=');
            int start = objname.lastIndexOf(',');
            String objectClassName = objname.substring(start+1, stop);
            // ccao add in here

            pmByObjectsValue.setMeasurementAttributes(
                                                      pmSession.getObservableAttributes(objectClassName));

            // Create a schedule that will run the job Monday, Wednesday, and 
            // Friday. 
            // On these days it will be active during the two intervals 
            // 08:00 to 17:00 and 21:30 to 24:00. 
            // The job will be start immediately, and will stop after a month. 
            Schedule schedule=pmByObjectsValue.makeSchedule(); 

            /* (run always)
               // Set the stop time to one month from now. 
               Calendar stopTime=new GregorianCalendar(); 
               stopTime.add(Calendar.MONTH, 1); 
               schedule.setStopTime(stopTime); 
               // Set the days that the schedule will be active. 
               WeeklySchedule dayOfWeekSchedule=schedule.makeWeeklySchedule(); 
               dayOfWeekSchedule.setMondayActive(true); 

               dayOfWeekSchedule.setWednesdayActive(true); 
               dayOfWeekSchedule.setFridayActive(true); 
               schedule.setWeeklySchedule(dayOfWeekSchedule); 
               // Set the hours that the schedule will be active. 
               DailySchedule hourSchedule=schedule.makeDailySchedule(); 
               Calendar start1=new GregorianCalendar(); 
               start1.set(Calendar.HOUR_OF_DAY, 8); 
               start1.set(Calendar.MINUTE, 0); 
               start1.set(Calendar.SECOND, 0); 
               Calendar start2=new GregorianCalendar(); 
               start2.set(Calendar.HOUR_OF_DAY, 21); 
               start2.set(Calendar.MINUTE, 30); 
               start2.set(Calendar.SECOND, 0); 
               hourSchedule.setStartTimes(new Calendar[] {start1, start2}); 
               Calendar stop1=new GregorianCalendar(); 
               stop1.set(Calendar.HOUR_OF_DAY, 17); 
               stop1.set(Calendar.MINUTE, 0); 
               stop1.set(Calendar.SECOND, 0); 
               Calendar stop2=new GregorianCalendar(); 
               stop2.set(Calendar.HOUR_OF_DAY, 24); 
               stop2.set(Calendar.MINUTE, 0); 
               stop2.set(Calendar.SECOND, 0); 
               hourSchedule.setStopTimes(new Calendar[] {stop1, stop2}); 
               schedule.setDailySchedule(hourSchedule); 
            */
            // Finally, set the schedule. 
            pmByObjectsValue.setSchedule(schedule); 
            // Create the PM job. 
            PerformanceMonitorKey key = 
                pmSession.createPerformanceMonitorByValue(pmByObjectsValue); 

            if (key!=null){
                System.out.println("Job is created successfully"); 
                System.out.println("Primary key is " + 
                                   key.getPerformanceMonitorPrimaryKey());
            }
            else
                System.out.println("Primary key is null");
            return key; 
        } catch (Exception e) {
            
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Simple test method for creating a threshold monitor.
     */
    private PerformanceMonitorKey createPmMonitorJobByClass(String jobname, String classname) 
        throws PmClientException {
        try { 
            PerformanceMonitorByClassesValue pmByClassesValue = 
                (PerformanceMonitorByClassesValue) 
                pmSession.makePerformanceMonitorValue( 
                                                      PerformanceMonitorByClassesValue.VALUE_TYPE); 
                   
            pmByClassesValue.setReportByEvent(ReportMode.EVENT_SINGLE ); 
            pmByClassesValue.setReportByFile(ReportMode.FILE_SINGLE); 
            pmByClassesValue.setName( jobname ); 
            // 15 minute granularity period, could also use 
            // getSupportedGranularities() to get the supported GP.  
            pmByClassesValue.setGranularityPeriod(15*60); 
            // Assume we want the report for a 3G network and we want it in XML. 
            // Find the appropriate ReportFormat. 
            ReportFormat formatXML3G = null; 
            ReportFormat[] formats = pmSession.getReportFormats(); 
            for (int i = 0; i < formats.length; ++i) { 
                if (formats[i].getTechnology().equals("3G") && 
                    formats[i].getType()== ReportFormat.XML) { 
                    formatXML3G=formats[i]; break; 
                } 
            } 
        
            if (formatXML3G== null) { 
                System.err.println("XML format for 3G network not supported"); 
                return null; 
            } 

            // use XML format for 3G 
            pmByClassesValue.setReportFormat(formatXML3G); 
            pmByClassesValue.setObservedObjectClasses( new String[] {classname}); 
            Schedule schedule=pmByClassesValue.makeSchedule(); 

            /* (run always)
               // Set the stop time to one month from now. 
               Calendar stopTime=new GregorianCalendar(); 
               stopTime.add(Calendar.MONTH, 1); 
               schedule.setStopTime(stopTime); 
               // Set the days that the schedule will be active. 
               WeeklySchedule dayOfWeekSchedule=schedule.makeWeeklySchedule(); 
               dayOfWeekSchedule.setMondayActive(true); 

               dayOfWeekSchedule.setWednesdayActive(true); 
               dayOfWeekSchedule.setFridayActive(true); 
               schedule.setWeeklySchedule(dayOfWeekSchedule); 
               // Set the hours that the schedule will be active. 
               DailySchedule hourSchedule=schedule.makeDailySchedule(); 
               Calendar start1=new GregorianCalendar(); 
               start1.set(Calendar.HOUR_OF_DAY, 8); 
               start1.set(Calendar.MINUTE, 0); 
               start1.set(Calendar.SECOND, 0); 
               Calendar start2=new GregorianCalendar(); 
               start2.set(Calendar.HOUR_OF_DAY, 21); 
               start2.set(Calendar.MINUTE, 30); 
               start2.set(Calendar.SECOND, 0); 
               hourSchedule.setStartTimes(new Calendar[] {start1, start2}); 
               Calendar stop1=new GregorianCalendar(); 
               stop1.set(Calendar.HOUR_OF_DAY, 17); 
               stop1.set(Calendar.MINUTE, 0); 
               stop1.set(Calendar.SECOND, 0); 
               Calendar stop2=new GregorianCalendar(); 
               stop2.set(Calendar.HOUR_OF_DAY, 24); 
               stop2.set(Calendar.MINUTE, 0); 
               stop2.set(Calendar.SECOND, 0); 
               hourSchedule.setStopTimes(new Calendar[] {stop1, stop2}); 
               schedule.setDailySchedule(hourSchedule); 
            */
            // Finally, set the schedule. 
            pmByClassesValue.setSchedule(schedule); 
            // Create the PM job. 
            PerformanceMonitorKey key = 
                pmSession.createPerformanceMonitorByValue(pmByClassesValue); 

            if (key!=null){
                System.out.println("Job is created successfully"); 
                System.out.println("Primary key is " + 
                                   key.getPerformanceMonitorPrimaryKey());
            }
            else
                System.out.println("Primary key is null");
            return key; 
        } catch (Exception e) { 
            e.printStackTrace(); return null; 
        }
    }

    public void getCurrentData(JVTPerformanceMonitorSession pmSession, 
                               PerformanceMonitorKey pmKey) { 
        try { 
            // holds the report 
            CurrentResultReport xmlReport = null; 
            // Make sure that the implementation supports 
            //GET_CURRENT_RESULT_REPORT. 

            String [] supportedMethods = 
                pmSession.getSupportedOptionalOperations(); 
            boolean supported = false; 
            for (int i=0; !supported && i < supportedMethods.length; i++) { 
                System.out.println(supportedMethods[i]); 
                if (supportedMethods[i].compareTo( 
                                                  JVTPerformanceMonitorSessionOptionalOpt.GET_CURRENT_RESULT_REPORT)==0) 
                    supported=true; 
            } 
           
            if (supported) { 
                xmlReport = pmSession.getCurrentResultReport(
                                                             pmKey, pmSession.getCurrentReportFormat()); 
                if (xmlReport.isDataType() == true ) { 
                    ReportData rd= xmlReport.getReportData(); 
                    ReportFormat rf=rd.getReportFormat(); 
                    if ((rf.getType() == ReportFormat.XML )  
                        || (rf.getType() == ReportFormat.ASCII) ) { 
                        String data=(String)rd.getPerformanceMonitorReport(); 
                        currentData = data;
                        // Handle the data. 
                        System.out.println("To get data by primary key :" );
                        System.out.println( data);
                   		System.out.println("--------------------------------------"); 
                    } else
                        System.out.println("data format is not XML or ASCII");
                } else 
                    System.out.println("data format is not data format"); 
            }else 
                System.out.println("operation is not supported"); 
		
        } catch (javax.oss.IllegalStateException ie) { 
            System.out.println("The job was suspended.urrent report cannot be produced."); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 

    public void listAllPMJob() {
        allJobName.removeAllElements();
        PerformanceMonitorValueIterator pmvIterator = null;
        try {
            // get the list of supported query types,
            String qTypes[] = pmSession.getQueryTypes();

            // Check that the implementation support the query type
            // "QueryPerformancemonitoValue"
           
            // To be complient, the implementation must at least return two 
            // elements: "QueryPerformanceMonitorValue" and "QueryByDNValue". 
            boolean supported = false; 
            for (int i=0; i< qTypes.length; i++) { 
                if (qTypes[i].compareTo( 
                                        QueryPerformanceMonitorValue.QUERY_TYPE) == 0) { 
                    supported=true; 
                    //System.out.println("the operation QUERY_TYPE is supported");
                } 
            } 
            
            if ( supported ) { 
                System.out.println("============ " +  "All Jobs"
                                   + "===========");
                // Get a query instance 
                QueryPerformanceMonitorValue qpmValue = (QueryPerformanceMonitorValue) pmSession.makeQueryValue( QueryPerformanceMonitorValue.QUERY_TYPE ); 
                // make the query and get all attributes 
                pmvIterator = pmSession.queryPerformanceMonitors(qpmValue, new String[]{}); 
                int count = 0;
                for (PerformanceMonitorValue pmVals[] = 
                         pmvIterator.getNextPerformanceMonitors(50);
                     pmVals.length != 0; pmVals = 
                         pmvIterator.getNextPerformanceMonitors(50)) {
                    for (int i=0; i < pmVals.length; i++) {
                        allJobName.add(pmVals[i].getName());
                        System.out.println( (count+i) +  ")  name=["+pmVals[i].getName()+"]\n      key=[" + pmVals[i].getPerformanceMonitorKey().getPrimaryKey()+"]");
                    }
                    count+=50;
                }
                System.out.println("============END " +  "All Jobs"
                                   + "===========");
            } 
            else{
                System.out.println("operation is not supported");
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        } // Return the information. return pmvIterator; 
    } 

	public void printMonitorJobValue(PerformanceMonitorValue value)
		throws Exception {
        System.out.println("============ " + value.getName() 
                           + "===========");
        System.out.println("Attribute \t\t\t\t Value"); 
        String[] attriNames = null;
        attriNames = value.getAttributeNames();
        for(int j=0; j<attriNames.length; j++) {
            System.out.print(attriNames[j] + "\t\t\t\t" );
            System.out.println(value.getAttributeValue(attriNames[j]).toString());
		}
        System.out.println("============end of  " + value.getName() 
                           + "===========");
	}

    public PerformanceMonitorValue findMonitorJobByName( String jobname ) {
        PerformanceMonitorValueIterator pmvIterator = null;
        PerformanceMonitorValue[] pmvs = null;
        try {
            // get the list of supported query types,
            String qTypes[] = pmSession.getQueryTypes();

            // Check that the implementation support the query type
            // "QueryPerformancemonitoValue"
           
            // To be complient, the implementation must at least return two 
            // elements: "QueryPerformanceMonitorValue" and "QueryByDNValue". 
            boolean supported = false; 
            for (int i=0; i< qTypes.length; i++) { 
                if (qTypes[i].compareTo( 
                                        QueryPerformanceMonitorValue.QUERY_TYPE) == 0) { 
                    supported=true; 
                } 
            } 
            
            if ( supported ) { 
                // Get a query instance 
                QueryPerformanceMonitorValue qpmValue = (QueryPerformanceMonitorValue) pmSession.makeQueryValue( QueryPerformanceMonitorValue.QUERY_TYPE ); 
                // Set the query criteria. 
                // Select all measurement jobs that starts with "EXAMPLE_" 
                qpmValue.setName( jobname ); 
                // make the query and get all attributes 
                pmvIterator = pmSession.queryPerformanceMonitors(qpmValue, new String[]{}); 
                pmvs = pmvIterator.getNextPerformanceMonitors(1);
                if(pmvs.length == 0 ){
                    System.out.println("no monitor job is find");
                    return null;
                }
            } 
            else{
                System.out.println("operation is not supported");
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        } // Return the information. return pmvIterator; 
        return pmvs[0];
    } 

	public void discoverObservableObjects() 
		throws RemoteException {
		observableClasses = new ArrayList();
		observableAttributes = new HashMap();
		observableObjects = new HashMap();

        webObject = new ArrayList();

		System.out.println("get all observable classes ");
		ObservableObjectClassIterator ci = pmSession.getObservableObjectClasses();
		for(String[] cnext = ci.getNext(100);
            cnext.length != 0;
            cnext = ci.getNext(100) ) {
            for (int i=0;i<cnext.length;i++) {
				System.out.println("===========ClassName :" + cnext[i]
                                   +" ===============");
				observableClasses.add(cnext[i]);
				ArrayList al = discoverAttributes(cnext[i]);
				observableAttributes.put(cnext[i], al );
				ArrayList ol = discoverObjects(cnext[i]);
				observableObjects.put(cnext[i], ol );
				System.out.println("===========ClassName :" + cnext[i]
                                   +" END ===============");
            }
		}
	}//end of discoverObservableObjects()

	public ArrayList discoverAttributes( String cname) 
		throws RemoteException {
        System.out.println("---------------Observable Attributes-------------" );
        System.out.println("Attribute\t\t\t\t Type" );
		ArrayList out = new ArrayList();
		try{
            PerformanceAttributeDescriptor[] pa=pmSession.getObservableAttributes( cname );
			for(int i=0; i< pa.length ; i++ ){
				out.add(pa[i]);
				System.out.println( pa[i].getName() + "\t\t\t\t" 
                                    + (new Integer(pa[i].getType())).toString() );
			}
		}
		catch( javax.oss.IllegalArgumentException ie ){
			System.out.println("IllegalArgumentException in discoverAttributes" );
		}
		return out;
	}

	public ArrayList discoverObjects( String cname) 
		throws RemoteException {
        System.out.println("---------------Observable Objects-------------" );
		ArrayList out = new ArrayList();

		try{
 			ObservableObjectIterator obji = pmSession.getObservableObjects(cname, "");
			for( String[] onext= obji.getNext(100);
                 onext.length!=0;
                 onext = obji.getNext(100)){ 
				for(int i=0; i< onext.length ; i++ ){
					out.add(onext[i]);
                    webObject.add(onext[i]);
					System.out.println( onext[i] );
				}
			}
		}
		catch( javax.oss.IllegalArgumentException ie ){
			System.out.println("IllegalArgumentException in discoverAttributes" );
		}
		return out;
	}

	public void removeSingle(String jobName) 
        throws PmClientException {

	    try {

	        PerformanceMonitorValue pmValue = findMonitorJobByName(jobName);                pmSession.removePerformanceMonitorByKey(
                                                                                                                                    pmValue.getPerformanceMonitorKey());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PmClientException(e.getMessage());
        }
    }

    public String getObjectOfJob(String jobName) {
        String allObject = "";
        String observedObjects [] = ((PerformanceMonitorByObjectsValue)findMonitorJobByName(jobName)).getObservedObjects();
        for(int i = 0; i < observedObjects.length; i++) { 
            allObject = allObject.concat(observedObjects[i]);
        }
        return allObject;    
    }


    public ArrayList getObservableObjectAttributes( String observableObjectClassName )
        throws PmClientException {
        
        System.out.println( "-----> into getObservableObjectAttributes ");
        ArrayList ooAttributesArrayList = new ArrayList();
        PerformanceAttributeDescriptor[] paDescriptors = null;
        PerformanceAttributeDescriptor descriptor = null;
        try {
            paDescriptors = pmSession.getObservableAttributes( observableObjectClassName );
            for (int i=0;i<paDescriptors.length;i++) {
                descriptor = paDescriptors[i];
                System.out.println( descriptor.toString());           
                ooAttributesArrayList.add( descriptor );
            }
            return ooAttributesArrayList;
        } catch ( RemoteException e ) {

            throw new PmClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {

            throw new PmClientException( e.getMessage() );
        }
    }



}//PmClient



