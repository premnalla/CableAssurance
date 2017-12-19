package ossj.qos.ri.fm.testclient;

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

//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;

import org.w3c.dom.Document; // for XML report

/**
 * Simple test client for pms. Handles and controls a user interface from
 * which it is possible to define and create a simple pm.
 *
 * @see ossj.qos.ri.pm.pm.adapter
 * @author Henrik Lindstrom, Ericsson
 */
public class FmClient {
    /**
     * Environment properties.
     */
    private Properties envProperties = null;

    /**
     * Pm session.
     */
    public JVTAlarmMonitorSession amSession = null;

    private InitialContext initialContext = null;

	private HashMap allAlarms;

//	public  FmClient fmClient = null;
	public static FmClient fmClient = null;

    /**
     * Create new client.
     */
    public FmClient() {
    }

    public static FmClient getInstance(String filename ) {
//	FmClient fmClient = null;
	try{
        if(fmClient == null) {
            fmClient = new FmClient(filename);
       }

	}
	catch(java.rmi.RemoteException e){
                   System.out.println("FmClient.getInstance() exception="+e);
	}
        return fmClient;
    }

    public FmClient(String filename) throws RemoteException {
        envProperties = new Properties( );

            File propertyFile = new File( filename );
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
                System.out.println("Properties file not exist");
            }

        String homeName = envProperties.getProperty("ALARM_MONITOR_HOME_JNDI_NAME");
	String amConnectionFactory = envProperties.getProperty("ALARM_MONITOR_JMS_CONNECTION_FACTORY");
        String amTopic = envProperties.getProperty("ALARM_MONITOR_JMS_TOPIC");

	//System.out.println(homeName);
	//System.out.println(amConnectionFactory);
	//System.out.println(amTopic);

        try {
            initInitialContext(envProperties);

            // initialize PM event receive
            if ( amConnectionFactory == null ) {
            }

            if ( amTopic == null ) {
                System.out.println("failed to initaliaze amTopoc");
            }

            initAMEventListener(amConnectionFactory,amTopic);

            connectToAmSession(homeName);

        } catch ( FmClientException e ) {
		System.out.println("FmClientException" + e );
        } catch ( Exception e ) {
		System.out.println("Exception" + e );
        }

    }

    public static void main(String[] args)
        throws RemoteException {
        
        //VP workaround 4766976
        {
            com.sun.enterprise.Switch sw = com.sun.enterprise.Switch.getSwitch();
            sw.setContainerType(com.sun.enterprise.Switch.APPCLIENT_CONTAINER);
        }
        //end VP

        // create pm client
	try{
        //FmClient fmClient = new FmClient(args[0]);
        fmClient = new FmClient(args[0]);

		for(;;){
			fmClient.run();
		}
        } catch ( IOException e ) {
		System.out.println("IOException" + e );
            System.exit(1);
        } catch ( FmClientException e ) {
		System.out.println("FmClientException" + e );
            System.exit(1);
        } catch ( Exception e ) {
		System.out.println("Exception" + e );
            System.exit(1);
        }

    }

	public void run ()
    		throws FmClientException, IOException, Exception {
        	BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in));
        	String line = null;
        	String line2 = null;

		System.out.println("");
		System.out.println("");
		System.out.println("1) Query All Alarms Count ");
		System.out.println("2) List All Alarms ");
		/*System.out.println("3) Acknowledge All Alarms ");
		System.out.println("4) Unacknowledge All Alarms ");
		System.out.println("5) Comment All Alarms ");*/
		System.out.println("3) Exit ");
		System.out.print("Enter an option[1-3]: ");

		line = msgStream.readLine();
		int opt;
		try{
			opt = Integer.parseInt(line);
		}catch(Exception e ) {
			return;
		}
		switch(opt)  {
		case 1 :
			QueryAlarmsCount();
			break;
		case 2 :
			QueryAllAlarms();
			break;
                case 3 :
                        System.exit(0);
                        break;

		/*case 3 :
			AcknowledgeAllAlarms();
			break;
		case 4 :
			UnacknowledgeAllAlarms();
			break;
		case 5 :
			System.out.print("Enter Comment Text : ");
                        line = msgStream.readLine();
			CommentAllAlarms(line);
			break;
		case 0 :
			System.exit(0);
			break;*/
		default:
			System.out.println("wrong number");
			return ;	
		}
	}	

    /*
     * Connect to the pm monitor session EJB.
     * @param homeName
     */
    private void connectToAmSession(String homeName )
        throws FmClientException {
        try {
            // Look up the Home interface in JNDI.            
	    //System.out.println("home name = " + homeName);
            JVTAlarmMonitorHome tmHome =
                (JVTAlarmMonitorHome) javax.rmi.PortableRemoteObject.narrow(
                    initialContext.lookup(homeName), JVTAlarmMonitorHome.class);

            // create the session bean.
	    //System.out.println("home string = " + tmHome.toString());
            amSession = tmHome.create();
	//if(amSession == null)
    //        System.out.println("null This is after session bean have been created");
	//else 
    //        System.out.println("no null This is after session bean have been created");
    //        System.out.println("This is after session bean have been created");
        } catch ( NamingException e ) {
            System.out.println("connectToAmSession() exception="+e);
            e.printStackTrace();
            throw new FmClientException( e.getMessage() );
        } catch( RemoteException e ) {
            System.out.println("connectToFmSession() exception="+e);
            e.printStackTrace();
            throw new FmClientException( e.getMessage() );
        } catch ( CreateException e ) {
           System.out.println("connectToFmSession() exception="+e);
            e.printStackTrace();
           throw new FmClientException( e.getMessage() );
        } catch ( Exception e ) {
           System.out.println("connectToFmSession() exception="+e);
            e.printStackTrace();
           throw new FmClientException( e.getMessage() );
        }
   }

    /**
     * Initialize initial context.
     * @param envProp envrionment properties needed to initialize
     */
    private void initInitialContext( Properties envProp ) 
        throws FmClientException {
        try {
            //initialContext = new InitialContext(envProp);
              initialContext = new InitialContext();
        } catch ( NamingException e ) {
            throw new FmClientException( e.getMessage() );
        }
    }

    /**
     * Init PM event receiver.
     */
    private void initAMEventListener(String factoryName,String topicName )
        throws FmClientException {
        String filter = null;
        initEventListener( factoryName, topicName, filter, new AlarmEventHandler() );
    }

    private void initEventListener(String factoryName,String topicName,
        String filter, MessageListener listener ) throws FmClientException
 {
	//System.out.println( " into init event listener ");

        TopicConnectionFactory conFactory = null;
        try {
            System.out.println("Get TopicConnectionFactory...");
            conFactory = (TopicConnectionFactory) initialContext.lookup(factoryName);

            System.out.println("Create TopicConnection...");
            TopicConnection con = conFactory.createTopicConnection();
            System.out.println("Create TopicSession...");
            TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE ); //
            System.out.println("Get Topic...");
            Topic topic = (Topic) initialContext.lookup(topicName);

            // create a filter to only receive certain events
            System.out.println("filter="+filter);

            System.out.println("Create Subscriber...");
            TopicSubscriber subscriber = null;
            if ( filter == null ) {
                subscriber = session.createSubscriber(topic);
            } else {
                subscriber = session.createSubscriber(topic, filter, false);
            }

            subscriber.setMessageListener( listener );
            System.out.println("Start TopicConnection...");
            con.start();

            System.out.println("ok!");
        } catch ( JMSException e ) {
            System.out.println("initEventListener()  " + "exception="+e);
            throw new FmClientException( e.getMessage() );
        } catch ( NamingException e ) {
            System.out.println("initEventListener()  " + "exception="+e);
            throw new FmClientException ( e.getMessage() );
        }
    }

	public AlarmCountsValue QueryAlarmsCount() 
		throws RemoteException, javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException
	{
           //System.out.println("======>0.QueryAlarmsCount()");
	   AlarmCountsValue acv = null ; 

           String qTypes[] = amSession.getQueryTypes();

           //System.out.println("======>1.QueryAlarmsCount()");
           boolean supported = false; 
           for (int i=0; i< qTypes.length; i++) { 
               if (qTypes[i].compareTo( 
                    QueryByFilterableAttributesValue.QUERY_TYPE) == 0) { 
                    supported=true; 
                        System.out.println("the operation QUERY_TYPE is supported");
               } 
            } 

           if ( supported ) { 
                System.out.println("============ " +  "All Alarms Count"
                        + "===========");
               // Get a query instance 
               //QueryByFilterableAttributesValue queryValue = ( QueryByFilterableAttributesValue) amSession.makeQueryValue( QueryByFilterableAttributesValue.QUERY_TYPE ); 
		acv = amSession.queryAlarmCounts( new QueryValue[]{} ) ; 
		System.out.println( "Cleared Count : " 
			+ (new Integer( acv.getClearedCount() )).toString());
		System.out.println( "Critical Count : " 
			+ (new Integer( acv.getCriticalCount() )).toString());
		System.out.println( "Major Count : " 
			+ (new Integer( acv.getMajorCount() )).toString());
		System.out.println( "Minor Count : " 
			+ (new Integer( acv.getMinorCount() )).toString());
		System.out.println( "Warning Count : " 
			+ (new Integer( acv.getWarningCount() )).toString());

                System.out.println("============END " + "===========");
		}
	    return acv;
	}

	public AlarmValue[]  QueryAllAlarms()
		throws RemoteException, javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException
	{
		AlarmValue [] alarmlist = null;
		ArrayList  avlist = new ArrayList();
		AlarmValueIterator avIterator = null;
/*
               QueryByFilterableAttributesValue queryValue = ( QueryByFilterableAttributesValue) amSession.makeQueryValue( QueryByFilterableAttributesValue.QUERY_TYPE ); 
		QueryValue[] querylist = new QueryValue[1];
		querylist[0] = queryValue;
*/		
		String[] attrs = {
			AlarmValue.ADDITIONAL_TEXT,
			AlarmValue.ALARM_RAISED_TIME,
			AlarmValue.MANAGED_OBJECT_CLASS,
			AlarmValue.MANAGED_OBJECT_INSTANCE ,
			AlarmValue.PERCEIVED_SEVERITY 
			};

        //System.out.println("sherry--QueryAllAlarms: ");
	try{

		avIterator =(AlarmValueIterator)  amSession.queryAlarmList( new QueryValue[]{} , new String[] {});	

                //System.out.println("sherry--queryAlarmList function result: " + avIterator);
	        for (AlarmValue amVals[] =
                      avIterator.getNextAlarmValues(50);
                      amVals.length != 0; amVals =
                      avIterator.getNextAlarmValues(50)) {
                   for (int i=0; i < amVals.length; i++) {
			printAlarmValue( amVals[i] );
			avlist.add(amVals[i]);
                   }
               }
		alarmlist  = (AlarmValue [] ) avlist.toArray(new AlarmValue[0]);
	}
	catch(Exception e){
		e.printStackTrace();
	}

		return alarmlist;
	}

	private void printAlarmValue( AlarmValue alarmValue )
	{
		System.out.println( alarmValue.toString() );
	}
	
	private void 	AcknowledgeAllAlarms()
		throws RemoteException, javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException
	{
		ArrayList alarmList = new ArrayList();

		AlarmValueIterator avIterator = amSession.queryAlarmList( new QueryValue[]{} , new String[] {});	
	        for (AlarmValue amVals[] =
                      avIterator.getNextAlarmValues(50);
                      amVals.length != 0; amVals =
                      avIterator.getNextAlarmValues(50)) {
                   for (int i=0; i < amVals.length; i++) {
			alarmList.add( amVals[i].getAlarmKey());
                   }
               }

		String ackUserId = "testuser" ;
		String ackSystemId = "System-pine" ;
		AlarmKeyResult[] alarmKeyResult = null; 
		AlarmKey [] alarmArray = new  AlarmKey [alarmList.size()];
		for( int j =0 ; j<alarmList.size() ; j++)
			alarmArray[j] = (AlarmKey) alarmList.get(j);
		alarmKeyResult = 
			amSession.tryAcknowledgeAlarms( alarmArray , ackUserId, ackSystemId); 
		if ( alarmKeyResult.length == 0 ) {
			System.out.println("All alarms have been Acknowledged successfully");
		}else {
			System.out.println("Not All alarms have been Acknowledged successfully ,total failed number = " + (new Integer(alarmKeyResult.length)).toString() );	
			for( int i=0 ; i< alarmKeyResult.length ; i++ ){
				System.out.println( (alarmKeyResult[i].getAlarmKey()).toString());
			}
		}
	}

	private void 	UnacknowledgeAllAlarms()
		throws RemoteException, javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException
	{
		ArrayList alarmList = new ArrayList();

		AlarmValueIterator avIterator = amSession.queryAlarmList( new QueryValue[]{} , new String[] {});	
	        for (AlarmValue amVals[] =
                      avIterator.getNextAlarmValues(50);
                      amVals.length != 0; amVals =
                      avIterator.getNextAlarmValues(50)) {
                   for (int i=0; i < amVals.length; i++) {
			alarmList.add( amVals[i].getAlarmKey());
                   }
               }

		String ackUserId = "testuser" ;
		String ackSystemId = "System-test" ;
		AlarmKeyResult[] alarmKeyResult = null; 
		AlarmKey [] alarmArray = new  AlarmKey [alarmList.size()];
		for( int j =0 ; j<alarmList.size() ; j++)
			alarmArray[j] = (AlarmKey) alarmList.get(j);
		alarmKeyResult = 
			amSession.tryUnacknowledgeAlarms( alarmArray , ackUserId, ackSystemId); 
		if ( alarmKeyResult.length == 0 ) {
			System.out.println("All alarms have been unAcknowledged successfully");
		}else {
			System.out.println("Not All alarms have been unAcknowledged successfully , total failed number = " + (new Integer(alarmKeyResult.length)).toString() );	
			for( int i=0 ; i< alarmKeyResult.length ; i++ ){
				System.out.println( (alarmKeyResult[i].getAlarmKey()).toString());
			}
		}
	}


	private void 	CommentAllAlarms( String comment)
		throws RemoteException, javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException
	{
		ArrayList alarmList = new ArrayList();

		AlarmValueIterator avIterator = amSession.queryAlarmList( new QueryValue[]{} , new String[] {});	
	        for (AlarmValue amVals[] =
                      avIterator.getNextAlarmValues(50);
                      amVals.length != 0; amVals =
                      avIterator.getNextAlarmValues(50)) {
                   for (int i=0; i < amVals.length; i++) {
			alarmList.add( amVals[i].getAlarmKey());
                   }
               }

		System.out.println("query all alarm end");
		String ackUserId = "testuser" ;
		String ackSystemId = "System-test" ;
		AlarmKeyResult[] alarmKeyResult = null; 
		AlarmKey [] alarmArray = new  AlarmKey [alarmList.size()];
		for( int j =0 ; j<alarmList.size() ; j++)
			alarmArray[j] = (AlarmKey) alarmList.get(j);
		alarmKeyResult = 
			amSession.tryCommentAlarms( alarmArray , 
			ackUserId, comment, ackSystemId); 
		System.out.println("ack all alarm end");
		if ( alarmKeyResult.length == 0 ) {
			System.out.println("All alarms have been Commented successfully");
		}else {
			System.out.println("Not All alarms have been Commented successfully , total failed number = " + (new Integer(alarmKeyResult.length)).toString() );	
			for( int i=0 ; i< alarmKeyResult.length ; i++ ){
				System.out.println( (alarmKeyResult[i].getAlarmKey()).toString());
			}
		}
	}

}//FmClient



