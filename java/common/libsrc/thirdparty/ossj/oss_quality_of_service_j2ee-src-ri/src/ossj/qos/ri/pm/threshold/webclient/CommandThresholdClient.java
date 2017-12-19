/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
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
import javax.oss.pm.threshold.*;
import javax.oss.pm.util.*;

//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.Cursor;

import org.w3c.dom.Document; // for XML report

// A true client would not have access to this utility class in the
// threshold monitor reference implementation. But the class is generic and
// could have been in the client's package. Another client would have to
// implement the handling of the XML format itself.
import ossj.qos.pm.threshold.PerformanceMonitorReportHandler;

/**
 * Simple test client for thresholds. Handles and controls a user interface from
 * which it is possible to define and create a simple threshold.
 *
 * @see ossj.qos.ri.pm.threshold.adapter
 * @author Henrik Lindstrom, Ericsson
 */
public class CommandThresholdClient {
    String homeName = "System/Linkoping/ApplicationType/ThresholdMonitor/Application/1-0;1-0;JSR90RITM/Comp/JVTHome";
    String amConnectionFactory = "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/TopicConnectionFactory";
    String amTopic = "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTEventTopic";
    String pmConnectionFactory = "System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/TopicConnectionFactory";
    String pmTopic = "System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTEventTopic";

    /**
     * Environment properties.
     */
    private static Properties envProperties = null;

    /**
     * Threshold session.
     */
    private JVTThresholdMonitorSession thresholdSession = null;

    /**
     * Currently observed object.
     */
    private String observableObject = null;

    /**
     * Currently observed attribute (name).
     */
    private String attribute = null;

    /**
     * The initial context.
     */
    private InitialContext initialContext = null;

    public String ThresNames[] = new String[50];
    public int ThresNum = 0;

    public Vector  ThresAttriNames = new Vector();
    public Vector  ThresAttriValues = new Vector();

    public  Vector  alarmValue = new Vector();

    public Vector  allThresName = new Vector();
    
    public ArrayList webOOAttributes = new ArrayList();

    public static CommandThresholdClient tmClient = null;

    /**
     * Create new client.
     */
    public CommandThresholdClient(String fileName) {

        envProperties = new Properties( );

        File propertyFile = new File(fileName);
        if ( propertyFile.exists() ) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream( propertyFile );
                BufferedInputStream bis = new BufferedInputStream( fis );
                envProperties.load( bis );
            } catch( IOException e ) {
                Log.write("exception="+e);
            } finally {
                try {
                    if ( fis != null ) fis.close();
                } catch ( IOException e ) {Log.write("exception="+e);}
            }
        } else {
            System.exit(1);
        }

        try {
            System.out.println("before initInitialContext in tm client");
            initInitialContext(envProperties);
            
            System.out.println("after initInitialContext in tm client");

            //initPMEventListener(pmConnectionFactory,pmTopic);
            
            System.out.println("after initPMEvent  in tm client");
            //initAMEventListener(amConnectionFactory,amTopic);

            System.out.println("after initAMEvent  in tm client");
            connectToThresholdSession(homeName);
            
            System.out.println("after connect tm session   in tm client");

        } catch ( ThresholdClientException e ) {
                System.out.println(e);
                System.out.println("ThresholdClientException");
            System.exit(1);
        } catch ( Exception e ) {
                System.out.println(e);
                System.out.println("Exception");
            System.exit(1);
        }
    }

    public static CommandThresholdClient getInstance(String FileName) {
        if (tmClient == null) {
            tmClient = new CommandThresholdClient(FileName);
        }
        System.out.println("tmClient" +  tmClient);
        return tmClient;
    }

    /**
     * Initialize the TM client, including creating initial listeners and models
     * for the user interface.
     * Last the user interface is displayed.
     */
    private void initializeClient() throws ThresholdClientException, IOException, Exception {
        BufferedReader msgStream = new BufferedReader(
                  new InputStreamReader(System.in));

        String line = null;
        String line2 = null;

        System.out.println("");
        System.out.println("1) Create a Threshold");
        System.out.println("2) List All Monitor Thresolds");
        System.out.println("3) Display All Attributes of a Thresold");
        System.out.println("4) Get Currect Threshold Report Data ");
        System.out.println("5) Remove a Threshold");
        System.out.println("6) Exit ");
        System.out.print("Enter an option[1-6]: ");
        line = msgStream.readLine();
        int opt;
        try{
            opt = Integer.parseInt(line);
        }catch(Exception e ) {
            return;
        }

        switch(opt) {
        case 1 :
            int granularity = 900,
                direction = ThresholdDirection.RISING;
            String thresholdName = "test";
            String thresholdVal = "1200";
            String thresholdOffset = "0";
            String observableName = "python,Config-Reader4udt=1";
   
                 createThresholdMonitor(thresholdName,
                         observableName,
                         2,
                         thresholdVal,
                         thresholdOffset,
                         direction, granularity,
                         2);

            break;
        case 2 :
            listAllThreshold();
            break;
        case 3 :
            System.out.print("Enter Threshold job Name : ");
            line = msgStream.readLine();
            printThresholdValue(findThresholdJobByName(line));
            break;
        case 4 :
            break;
        case 5 :
            System.out.print("Enter Threshold job Name : ");
            line = msgStream.readLine();
            removeThresholdMonitor(line);
            break;
        case 6 :
            System.exit(0);
            break;
        default:
            System.out.println("wrong number");
            return ;
        }
    }

    /**
     * Create a threshold monitor. Uses the information entered in the user
     * interface.
     */
    public void createThresholdMonitor(String thresholdName, 
                                   String observalbleObjectParam,
                                   int prior,
                                   String value, String offset, 
                                   int direction, int granularity,
                                   int severity) 
                     throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"createThresholdMonitor()","called" );

        short perceivedSeverity;

        Object valueObj = null,
               offsetObj = null;

        if (severity == 5)
            perceivedSeverity = PerceivedSeverity.WARNING;
        else if (severity == 4)
            perceivedSeverity = PerceivedSeverity.MINOR;
        else if (severity == 3)
            perceivedSeverity = PerceivedSeverity.MAJOR; 
        else if (severity == 2)
            perceivedSeverity = PerceivedSeverity.INDETERMINATE;
        else if (severity == 1)
            perceivedSeverity = PerceivedSeverity.CRITICAL;
        else 
            perceivedSeverity = PerceivedSeverity.CLEARED;

              PerformanceAttributeDescriptor descriptor=null;
                 // get performance attribute descriptor
                 int stop = observalbleObjectParam.lastIndexOf('=');
                 int start = observalbleObjectParam.lastIndexOf(',');
                 String objectClassName = observalbleObjectParam.substring(
                     start+1, stop);
  
                 descriptor = (PerformanceAttributeDescriptor)
                     getObservableObjectAttributes(objectClassName).get(prior);

        // get observable object
        observableObject = observalbleObjectParam;

        // store attribute name for later use in onMessage() method.
        attribute = descriptor.getName();

        // parse value and offset
        if ( descriptor.getType()==PerformanceAttributeDescriptor.INTEGER ) {
            try {
                valueObj = Integer.valueOf( value );
            } catch ( NumberFormatException e ) {
                Log.write(this,Log.LOG_MINOR,"createThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( "Threshold value [" + value + "] is not valid!." );
            }

            if ( offset.length() != 0 ) {
                try {
                    offsetObj = Integer.valueOf( offset );
                } catch ( NumberFormatException e ) {
                    Log.write(this,Log.LOG_MINOR,"createThresholdMonitor()","exception="+e);
                    throw new ThresholdClientException( "Threshold offset [" + offset + "] is not valid!." );
                }
            }
        } else if ( descriptor.getType()==PerformanceAttributeDescriptor.REAL ) {
            try {
                valueObj = Double.valueOf( value );
            } catch ( NumberFormatException e ) {
                Log.write(this,Log.LOG_MINOR,"createThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( "Threshold value [" + value + "] is not valid!." );
            }

            if ( offset.length() != 0 ) {
                try {
                    offsetObj = Double.valueOf( offset );
                } catch ( NumberFormatException e ) {
                    Log.write(this,Log.LOG_MINOR,"createThresholdMonitor()","exception="+e);
                    throw new ThresholdClientException( "Threshold offset [" + offset + "] is not valid!." );
                }
            }
        } else {
            throw new ThresholdClientException( "Not supported attribute descriptor!" );
        }

        // create monitor
        try{
            // Get a SimpleThresholdMonitorValue object.
            Log.write("Make simple threshold monitor value...");
            SimpleThresholdMonitorValue tmValue = (SimpleThresholdMonitorValue)
                thresholdSession.makeThresholdMonitorValue( SimpleThresholdMonitorValue.VALUE_TYPE);

            // Populate the object to specify the parameters of the Threshold job.
            // Sets a threshold on the attribute of object
            // An alarm shall be sent when/if counter exceeds 100.
            Log.write("Set observable object...");
            tmValue.setObservableObject( observableObject );

            Log.write("Make threshold definition...");
            ThresholdDefinition td = tmValue.makeThresholdDefinition();

            Log.write("Set attribute descriptor...");
            td.setAttributeDescriptor(descriptor);

            td.setValue( valueObj );
            if ( offsetObj!=null ) {
                td.setOffset( offsetObj );
            }
            Log.write("Set direction...");
            td.setDirection( direction );

            Log.write("Set threshold definition...");
            tmValue.setThresholdDefinition(td);
            // Sets the AlarmConfig attributes

            Log.write("Make alarm config...");
            AlarmConfig ac = tmValue.makeAlarmConfig();
            // The alarm config contains default values for all attributes
            // except PerceivedSeverity.
            Log.write("Set perceived severity...");

            ac.setPerceivedSeverity(perceivedSeverity);
            Log.write("Set alarm config...");
            tmValue.setAlarmConfig(ac);
            // Note that no granularity is set. Uses system default.

            // set schedule (run always)
            Log.write("Set schedule...");
            Schedule schedule = tmValue.makeSchedule();
            tmValue.setSchedule( schedule );

            Log.write("Set granularity period...");
            tmValue.setGranularityPeriod( granularity );

            // Give a name to the job.
            Log.write("Set name...");
            tmValue.setName( thresholdName );
            // Create the Threshold monitor job and save the key for further
            // reference.
            Log.write("Create threshold monitor by value...");
            ThresholdMonitorKey thresholdMonitorKey = 
                thresholdSession.createThresholdMonitorByValue(tmValue);
            Log.write("ok!");
        } catch (javax.oss.IllegalArgumentException e) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch (javax.ejb.CreateException e) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Remove previously created threshold monitor.
     */
    public void removeThresholdMonitor(String thresholdName) 
            throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"removeThresholdMonitor()","called" );

        ThresholdMonitorValue thresholdVal = 
             findThresholdJobByName(thresholdName);

        ThresholdMonitorKey thresholdMonitorKey = 
             thresholdVal.getThresholdMonitorKey(); 

        if (thresholdMonitorKey != null ) {
            try {
                thresholdSession.removeThresholdMonitorByKey( thresholdMonitorKey );
                thresholdMonitorKey=null;
            } catch ( RemoteException e ) {
                Log.write(this,Log.LOG_MAJOR,"removeThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( e.getMessage() );
            } catch ( RemoveException e ) {
                Log.write(this,Log.LOG_MAJOR,"removeThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( e.getMessage() );
            } catch ( javax.oss.IllegalArgumentException e ) {
                Log.write(this,Log.LOG_MAJOR,"removeThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( e.getMessage() );
            } catch ( javax.ejb.ObjectNotFoundException e ) {
                Log.write(this,Log.LOG_MAJOR,"removeThresholdMonitor()","exception="+e);
                throw new ThresholdClientException( e.getMessage() );
            }
        } else {
            Log.write(this,Log.LOG_ALL,"removeThresholdMonitor()",
                "no threshold monitor key defined");
        }
    }

    /**
     * Get supported granularities. Uses a dummy 'SimpleThresholdMonitorValue'
     * to get the granularities. All, but zero, are returned.
     * @return array list with supported granularities
     * @exception if unable to get the granularities
     */
    private ArrayList getSupportedGranularities(String observableObject,
                                                PerformanceAttributeDescriptor pad )
                                                throws ThresholdClientException {
        Log.write(this,"getSupportedGranularities()","called");

        try {
            SimpleThresholdMonitorValue tmValue = (SimpleThresholdMonitorValue)thresholdSession.makeThresholdMonitorValue(
                SimpleThresholdMonitorValue.VALUE_TYPE );
            tmValue.setName( "dummy" );
            // schedule
            tmValue.setSchedule( tmValue.makeSchedule() );
            // alarmconfig
            AlarmConfig ac = tmValue.makeAlarmConfig();
            ac.setPerceivedSeverity( PerceivedSeverity.MINOR );
            tmValue.setAlarmConfig( ac );

            // observable object
            tmValue.setObservableObject( observableObject );

            // threshold definition (performance attribute descriptor)
            ThresholdDefinition td = tmValue.makeThresholdDefinition();
            td.setAttributeDescriptor( pad );
            td.setDirection( ThresholdDirection.RISING );
            if ( pad.getType()==PerformanceAttributeDescriptor.INTEGER ) {
                td.setValue( new Integer( 0 ) );
            } else if ( pad.getType()==PerformanceAttributeDescriptor.REAL ) {
                td.setValue( new Double( 0.0 ) );
            } else {
                throw new ThresholdClientException( "Not supported attribute descriptor!" );
            }

            tmValue.setThresholdDefinition( td );

            int[] suppGranularities = thresholdSession.getSupportedGranularities( tmValue );

            Arrays.sort( suppGranularities );
            ArrayList granularities = new ArrayList();
            int gran=0;
            for (int i=0;i<suppGranularities.length;i++) {
                gran=suppGranularities[i];
                if (gran>0) {
                    granularities.add( new Integer( gran ) );
                }
            }
            return granularities;
        } catch ( RemoteException e ) {
            Log.write(this,"getSupportedGranularities()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {
            Log.write(this,"getSupportedGranularities()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Get observable object attributes for an observable object class.
     * @param observableObjectClassName
     * @return array list of observable object attributes (PerformanceAttributeDescriptor)
     */
    public ArrayList getObservableObjectAttributes( String observableObjectClassName )
        throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"getObservableObjectAttributes()","ooClassName="+observableObjectClassName);
        ArrayList ooAttributesArrayList = new ArrayList();
        PerformanceAttributeDescriptor[] paDescriptors = null;
        PerformanceAttributeDescriptor descriptor = null;
        try {
            paDescriptors = thresholdSession.getObservableAttributes( observableObjectClassName );
            for (int i=0;i<paDescriptors.length;i++) {
                descriptor = paDescriptors[i];
                ooAttributesArrayList.add( descriptor );
            }
            webOOAttributes = ooAttributesArrayList;
            return ooAttributesArrayList;
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjectAttributes()","exception="+e);
            System.out.println(e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjectAttributes()","exception="+e);
            System.out.println(e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Get observable objects for observable object class. All are returned, i.e.
     * root is not specified.
     *
     * @return array list of observable objects
     */
    private ArrayList getObservableObjects( String observableObjectClass )
        throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"getObservableObjects()","ooc="+observableObjectClass);
        ArrayList observableArray = new ArrayList();

       ObservableObjectIterator ooIterator = null;
        try {
            // get iterator
            ooIterator = thresholdSession.getObservableObjects(observableObjectClass,""); // all are returned

            // Iterate through the observable objects.
            // (Each iteration returns a chunk of observable objects.)
            for (String observableObjects[] = ooIterator.getNext(100);
                    observableObjects.length != 0;
                    observableObjects = ooIterator.getNext(100)) {

                // Print the observable object names in this chunk
                for (int i=0;i<observableObjects.length;i++) {
                    observableArray.add( observableObjects[i] );
                }
            }

            return observableArray;
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjects()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjects()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } finally {
            // Remove the iterator when finished with it.
            if ( ooIterator != null ) {
                try {
                    ooIterator.remove();
                } catch ( RemoveException e ) {
                    Log.write(this,Log.LOG_MINOR,"getObservableObjects()","exception="+e);
                } catch ( RemoteException e ) {
                    Log.write(this,Log.LOG_MINOR,"getObservableObjects()","exception="+e);
                }
            }
        }
    }

    /**
     * List all supported granularities.
     * @retrun an array list of all observable object classes
     */
    private ArrayList getObservableObjectClasses() throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"listObservableObjectClasses()","called");
        ArrayList arrayList = new ArrayList();

        ObservableObjectClassIterator ooci =null;
        try {
            ooci = thresholdSession.getObservableObjectClasses();
            String[] observableObjectClasses = null;
            do {
                observableObjectClasses = ooci.getNext(10);
                if ( observableObjectClasses == null ) {
                    Log.write(this,Log.LOG_MAJOR,"listObservableObjectClasses()","observableObjectClasses is null!");
                    throw new ThresholdClientException( "observableObjectClasses is null!" );
                }
                for (int i=0;i<observableObjectClasses.length;i++) {
                    arrayList.add( observableObjectClasses[i] );
                }
            } while ( observableObjectClasses.length!=0 );
            return arrayList;
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_ALL,"listObservableObjectClasses()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } finally {
            if ( ooci != null ) {
               try {
                    ooci.remove();
                } catch ( RemoveException e ) {
                    Log.write(this,Log.LOG_MINOR,"listObservableObjectClasses()","exception="+e);
                } catch ( RemoteException e ) {
                    Log.write(this,Log.LOG_MINOR,"listObservableObjectClasses()","exception="+e);
                }
            }
        }
    }

    /**
     * List all event types in threshold monitor and the corresponding event
     * descriptor.
     */
    private void listEventTypes() throws ThresholdClientException {
        Log.write(this,"listEventTypes()","called");
        try {
            String[] eventTypes = thresholdSession.getEventTypes();
            EventPropertyDescriptor epd = null;
            for (int i=0;i<eventTypes.length;i++) {
                Log.write("eventType="+eventTypes[i]);
                epd = thresholdSession.getEventDescriptor(eventTypes[i]);
                Log.write("eventPropertyDescriptor="+epd);
            }
            Log.write("Ok, got all expected event types from FM!");
        } catch ( RemoteException e ) {
            Log.write(this,"listEventTypes()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {
            Log.write(this,"listEventTypes()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Connect to the threshold monitor session EJB.
     * @param homeName
     */
    private void connectToThresholdSession(String homeName )
        throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"connectToThresholdSession()","homeName="+homeName);

        try {

            Log.write("Look up the home interface...");
            // Look up the Home interface in JNDI.
            Object homeObject = initialContext.lookup(homeName);
            // Cast to the Home interface type and use the Home to
            Log.write("Cast to JVTThresholdMonitorHome interface...");
            JVTThresholdMonitorHome tmHome =
                (JVTThresholdMonitorHome) PortableRemoteObject.narrow(
                    homeObject, JVTThresholdMonitorHome.class);

            // create the session bean.
            Log.write("Create session...");
            thresholdSession = tmHome.create();

            Log.write("ok!");
        } catch ( NamingException e ) {
            Log.write(this,Log.LOG_ALL,"connectToThresholdSession()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch( RemoteException e ) {
            Log.write(this,Log.LOG_ALL,"connectToThresholdSession()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( CreateException e ) {
           Log.write(this,Log.LOG_ALL,"connectToThresholdSession()","exception="+e);
           throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Initialize initial context.
     * @param envProp envrionment properties needed to initialize
     */
    private void initInitialContext( Properties envProp ) throws ThresholdClientException {
        Log.write(this,"initInitialContext()","envProp="+envProp);
        // Get the InitialContext.
        try {
            Log.write("Get initial context...");
            //initialContext = new InitialContext(envProp);
            
            initialContext = new InitialContext();
            Log.write("ok!");
        } catch ( NamingException e ) {
            Log.write(this,"initInitialContext()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * Init PM event receiver.
     */
    private void initPMEventListener(String factoryName,String topicName ) throws ThresholdClientException {
        Log.write(this,"initPMEventListener()","");
        String filter = PerformanceDataEventDescriptor.OSS_EVENT_TYPE_PROP_NAME
            + " = '" + PerformanceDataEventDescriptor.OSS_EVENT_TYPE_VALUE + "'";
	// System.out.println(filter);
        initEventListener( factoryName, topicName, filter, new PerformanceDataEventHandler() );
    }

    /**
     * Init AM event receiver.
     */
    private void initAMEventListener(String factoryName,String topicName ) throws ThresholdClientException {
        Log.write(this,"initAMEventListener()","");
        String filter = null;
        initEventListener( factoryName, topicName, filter, new AlarmEventHandler() );
    }

    /**
     * Initialize event receiver.
     */
    private void initEventListener(String factoryName,String topicName,
        String filter, MessageListener listener ) throws ThresholdClientException {


        Log.write(this,"initEventListener()","factoryName="+factoryName
            +", topicName="+topicName+", filter="+filter +", listener="+listener);
	 System.out.println("factoryName="+factoryName + "topicName=" + topicName + "filter = " + filter);  
        TopicConnectionFactory conFactory = null;
        try {
            System.out.println("Get TopicConnectionFactory...");
            Context initialContext = new InitialContext();
            //System.out.println("wrx--before lookup factory");
            conFactory = (TopicConnectionFactory) initialContext.lookup(factoryName);
            //System.out.println("wrx--after lookup factory");
            System.out.println("Create TopicConnection...");
            TopicConnection con = conFactory.createTopicConnection();
            System.out.println("Create TopicSession...");
            TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE ); //
            System.out.println("Get Topic...");
            Topic topic = (Topic) initialContext.lookup(topicName);

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
            Log.write(this,"initEventListener()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( NamingException e ) {
            Log.write(this,"initEventListener()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
    }

    /**
     * @return String representation of PerformanceAttributeDescriptor
     */
    private static String getPerformanceAttributeDescriptorString( PerformanceAttributeDescriptor pad ) {
        if ( pad == null ) {
            return null;
        }
        int type = pad.getType();
        String typeString = "undef";
        switch ( type ) {
            case PerformanceAttributeDescriptor.INTEGER:
                typeString = "INTEGER";
                break;
            case PerformanceAttributeDescriptor.REAL:
                typeString = "REAL";
                break;
            case PerformanceAttributeDescriptor.STRING:
                typeString = "STRING";
                break;
            default:
        }
        String string = "[" + pad.getName() + ", type=" + typeString
            + ", collection method=" + pad.getCollectionMethod() + "]";
        return string;
    }

    public void printThresholdValue(ThresholdMonitorValue value) 
             throws Exception {

        ThresAttriNames.removeAllElements();
        ThresAttriValues.removeAllElements();

        System.out.println("============ " + value.getName() + "===========");
        System.out.println("Attribute \t\t Value");
        String[] attriNames = null;
        attriNames = value.getAttributeNames();

        for(int j=0; j<attriNames.length; j++) {
            if(attriNames[j].equals("perfkey")) {
                continue;
            }
            ThresAttriNames.add(attriNames[j]);
            ThresAttriValues.add(
                value.getAttributeValue(attriNames[j]).toString());

            System.out.print(attriNames[j] + "\t\t\t\t" );
            System.out.println(
                  value.getAttributeValue(attriNames[j]).toString());
        }

        System.out.println("============end of" + value.getName() + "===========");
    }

    public ThresholdMonitorValue findThresholdJobByName(String jobName) {
        ThresholdMonitorValueIterator threIterator = null;
        ThresholdMonitorValue[] rtnThresholdVal = null;

        try {
           // get the list of supported query types,
           String qTypes[] = thresholdSession.getQueryTypes();
 
           boolean supported = false;
           for (int i=0; i< qTypes.length; i++) {
               if (qTypes[i].compareTo(
                    QueryByMonitorValue.QUERY_TYPE) == 0) {
                    supported=true;
               }
           }

           if ( supported ) {
               // Get a query instance
               QueryByMonitorValue qpmValue = (QueryByMonitorValue)thresholdSession.makeQueryValue( QueryByMonitorValue.QUERY_TYPE );

               qpmValue.setName(jobName);
               // make the query and get all attributes
               threIterator = thresholdSession.queryThresholdMonitors(qpmValue, new String[]{});

               rtnThresholdVal = threIterator.getNextThresholdMonitors(1);
               if (rtnThresholdVal.length == 0) {
                   System.out.println("no monitor job is find");
                   return null;
               }
           } else {
                System.out.println("operation is not supported");
           }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return rtnThresholdVal[0];
    }

    public void listAllThreshold() {
        ThresholdMonitorValueIterator threIterator = null;

        allThresName.removeAllElements();

        try  {
           // get the list of supported query types,
           String qTypes[] = thresholdSession.getQueryTypes();
 
           boolean supported = false;
           for (int i=0; i< qTypes.length; i++) {
               if (qTypes[i].compareTo(
                    QueryByMonitorValue.QUERY_TYPE) == 0) {
                    supported=true;
               }
            }

            if ( supported ) {
                System.out.println("==========" +  "All Jobs" + "==========");
               // Get a query instance
               QueryByMonitorValue qpmValue = (QueryByMonitorValue)thresholdSession.makeQueryValue( QueryByMonitorValue.QUERY_TYPE );
               // make the query and get all attributes
               threIterator = thresholdSession.queryThresholdMonitors(qpmValue, new String[]{});

               for(ThresholdMonitorValue threVals[] =
                       threIterator.getNextThresholdMonitors(50);
                       threVals.length != 0; threVals = 
                       threIterator.getNextThresholdMonitors(50)) {
                   for (int i=0; i<threVals.length; i++) {
                       allThresName.add(threVals[i].getName());
                       System.out.println( String.valueOf(i)+  ")  " + threVals[i].getName());
                   }
                }
            } else {
                System.out.println("operation is not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } // Return the information. 
    }

    /**
     * Used in the combo box model to get a nice string representation.
     * Encapsulates the PerformanceAttributeDescriptor.
     */
    private class PerformanceAttributeDescriptorListObject {
        String displayString=null;
        PerformanceAttributeDescriptor pad=null;

        /**
         * Creates a new object.
         */
        PerformanceAttributeDescriptorListObject( PerformanceAttributeDescriptor pad ) {
            this.pad=pad;
            displayString = CommandThresholdClient.getPerformanceAttributeDescriptorString( pad );
        }

        public String toString() {
            return displayString;
        }
    }

    /**
     * Handle a performance data event.
     */
    private class PerformanceDataEventHandler implements MessageListener {
        /**
         * Handles an performance data event.
         */
        public void onMessage( Message msg ) {
            Log.write(this,Log.LOG_ALL,"onMessage()","msg="+msg);
            try
            {
                if (msg instanceof ObjectMessage) {
                    ObjectMessage omsg = (ObjectMessage) msg;
                    Object obj = omsg.getObject();
                    if ( obj instanceof PerformanceDataEvent ) {
                        Log.write( "PerformanceDataEvent" );

                        if ( observableObject==null || attribute==null ) {
                            // when not initialized, before threshold is started.
                            Log.write("observableObject/attribute not defined");
                            return;
                        }

                        PerformanceDataEvent pmEvent = (PerformanceDataEvent)obj;

                        String[] names = pmEvent.getPopulatedAttributeNames();
                        StringBuffer buf = new StringBuffer();
                        buf.append('[');
                        for (int i=0;i<names.length;i++) {
                            buf.append( names[i] );
                            if ( i!=names.length-1) {
                                buf.append(',');
                            }
                        }
                        buf.append(']');
                        Log.write("Populated attributes="+buf.toString());

                        ArrayList rowData = new ArrayList();
                        rowData.add( pmEvent.getAttributeValue( PerformanceDataEvent.EVENT_TIME ) );
                        rowData.add( pmEvent.getAttributeValue( PerformanceDataEvent.MANAGED_OBJECT_INSTANCE ) );

                        Object report = pmEvent.getAttributeValue( PerformanceDataEvent.REPORT );
                        String measXMLReport = null;
                        if ( report instanceof String ) {
                            measXMLReport = (String)report;
                        } else if ( report instanceof String[] ) { // fix for current delivered events from PM.
                            measXMLReport = ((String[])report)[0]; // first element is the report, second is a filename...
                        } else { // unknown format
                            Log.write(this,Log.LOG_MAJOR,"onMessage()","unknown report format!");
                            return;
                        }
                        // parse measurment report
                        Document doc = PerformanceMonitorReportHandler.getDocument( measXMLReport );
                        String measValue = PerformanceMonitorReportHandler.getMeasurementValue(
                            doc,observableObject,attribute,null);
                        if ( measValue != null ) {
                            rowData.add( measValue );
                            // CommandThresholdClient.this.measurementTableModel.addRow( rowData );
                        } else {
                            Log.write("Received report did not contain the observable object/attribute!");
                        }
                    } else {
                        Log.write(this,Log.LOG_ALL,"onMessage()","not handled message: " + obj.getClass().getName());
                    }
                }
            } catch (Exception e) {
                Log.write(this,Log.LOG_MAJOR,"onMessage()","exception="+e);
            }
        }
    }//PerformanceDataEventHandler class


    /**
     * Handle an alarm data event.
     */
    private class AlarmEventHandler implements MessageListener {
        /**
         * Handles an alarm event.
         */
        public void onMessage( Message msg ) {
            Log.write(this,Log.LOG_ALL,"onMessage()","msg="+msg);
            try
            {
                if (msg instanceof ObjectMessage) {
                    ObjectMessage omsg = (ObjectMessage) msg;
                    Object obj = omsg.getObject();
                    if ( obj instanceof AlarmEvent ) {
                        Log.write( "AlarmEvent" );
                        AlarmEvent alarmEvent = (AlarmEvent) obj;

                        ArrayList rowData = new ArrayList();

                        // severity
                        short severity = alarmEvent.getPerceivedSeverity();
                        switch ( severity ) {
                            case PerceivedSeverity.CLEARED:
                                rowData.add( "CLEARED" );
                                break;
                            case PerceivedSeverity.CRITICAL:
                                rowData.add( "CRITICAL" );
                                break;
                            case PerceivedSeverity.INDETERMINATE:
                                rowData.add( "INDETERMINATE" );
                                break;
                            case PerceivedSeverity.MAJOR:
                                rowData.add( "MAJOR" );
                                break;
                            case PerceivedSeverity.MINOR:
                                rowData.add( "MINOR" );
                                break;
                            case PerceivedSeverity.WARNING:
                                rowData.add( "WARNING" );
                                break;
                            default:
                                rowData.add( "unknown" );
                        }

                        // time
                        rowData.add( alarmEvent.getEventTime() );

                        // object
                        rowData.add( alarmEvent.getManagedObjectInstance() );

                        // alarm
                        rowData.add( alarmEvent.getAlarmType() );

                       //  CommandThresholdClient.this.alarmTableModel.addRow( rowData );

                    } else {
                        Log.write(this,Log.LOG_ALL,"onMessage()","not handled message: " + obj.getClass().getName());
                    }
                }
            } catch (Exception e) {
                Log.write(this,Log.LOG_MAJOR,"onMessage()","exception="+e);
            }
        }
    }//AlarmEventHandler class
}//CommandThresholdClient
