/*
 * Copyright: Copyright ? 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.testclient;

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

import ossj.qos.util.Log;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.Cursor;

import org.w3c.dom.Document; // for XML report
import ossj.qos.util.Util;

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
public class ThresholdClient implements EnvironmentConstants {
    /**
     * Environment properties.
     */
    private static Properties envProperties = null;

    /**
     * Threshold session.
     */
    private JVTThresholdMonitorSession thresholdSession = null;

    /**
     * Threshold monitor key for created threshold.
     */
    private ThresholdMonitorKey thresholdMonitorKey=null;

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

    /**
     * The client UI.
     */
    private TMClientFrame tmFrame = null;

    /**
     * Combo box model for observable object classes.
     */
    private DefaultComboBoxModel observableObjectClassModel = null;

    /**
     * Combo box model for observable objects (for a class).
     */
    private DefaultComboBoxModel observableObjectModel = null;

    /**
     * Combo box model for observable objects (for a class).
     */
    private DefaultComboBoxModel granularityModel = null;

    /**
     * Combo box model for observable object attributes (for a observable object).
     */
    private DefaultComboBoxModel observableObjectAttributeModel = null;


    /**
     * Table model for measurement notifications.
     */
    private MeasurementTableModel measurementTableModel = null;

    /**
     * Table model for alarm notifications.
     */
    private AlarmTableModel alarmTableModel = null;

    /**
     * Create new client.
     */
    public ThresholdClient() {
    }

    /**
     * Starts the client. Makes a connection to the threshold monitor session EJB.
     * Then tries to create a threshold.
     *
     * @param args first argument should be the path and file name for
     * a property file with information about how to connect to app. server.
     * If not present, then default values will be used(WebLogic on localhost).
     */
    public static void main(String[] args) {
        //VP workaround 4766976
        {
            com.sun.enterprise.Switch  sw = com.sun.enterprise.Switch.getSwitch();
            sw.setContainerType(com.sun.enterprise.Switch.APPCLIENT_CONTAINER);
        }
        //end VP
        
        System.out.println("main() called");
        
        envProperties = new Properties( );

        if ( args.length==1 ) {
            System.out.println("getting main arguments");
            File propertyFile = new File( args[0] );
            if ( propertyFile.exists() ) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream( propertyFile );
                    BufferedInputStream bis = new BufferedInputStream( fis );
                    envProperties.load( bis );

                } catch( IOException e ) {
                    System.out.println("exception="+e);
					e.printStackTrace();
                } finally {
                    try {
                        if ( fis != null ) fis.close();
                    } catch ( IOException e ) {System.out.println("exception="+e);}
                }
            } else {
                System.out.println("Property file " + args[0] + " not found!");
                System.exit(1);
            }
        } else {
            System.out.println("No property file specified as argument!");
            System.out.println("main_class [propertyfile]");
            System.exit(1);
        }

		String level = envProperties.getProperty("LOG_LEVEL");
		Log.setLogOn(false);
		if (level != null){
		   try {
		   Log.setLogLevel(Integer.parseInt(level));
           Log.setLogOn(true);
		   System.out.println("Log Level set to: "+level);
		   } catch (Exception ex){
			 System.out.println("Unable to set Log Level: "+ex);
		   }
        } 
		Log.write("LOG_LEVEL = "+level);

        String homeName = envProperties.getProperty( THRESHOLD_MONITOR_HOME_JNDI_PROPERTY_NAME);
        if ( homeName == null ) {
            System.out.println("Property " + THRESHOLD_MONITOR_HOME_JNDI_PROPERTY_NAME
                + " not defined in property file!");
            System.exit(1);
        }

        // create threshold client

        ThresholdClient thresholdClient = new ThresholdClient();

        try {

            thresholdClient.initInitialContext(envProperties);

            // initialize PM event receive
            String pmConnectionFactory = null,
                   pmTopic = null;
            pmConnectionFactory = envProperties.getProperty(PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME);
            if ( pmConnectionFactory == null ) {
                System.out.println("Property " + PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME
                    + " not defined in property file!" );
                System.exit(1);
            }
            System.out.println(PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME + " |||| " + PERFORMANCE_MONITOR_JMS_TOPIC_PROPERTY_NAME);
            pmTopic = envProperties.getProperty(PERFORMANCE_MONITOR_JMS_TOPIC_PROPERTY_NAME);
            if ( pmTopic == null ) {
                System.out.println("Property " + PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME
                    + " not defined in property file!" );
                System.exit(1);
            }
            thresholdClient.initPMEventListener(pmConnectionFactory,pmTopic);

            // initialize AM event receive
            String amConnectionFactory = null,
                   amTopic = null;
            amConnectionFactory = envProperties.getProperty(ALARM_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME);
            if ( amConnectionFactory == null ) {
                System.out.println("Property " + ALARM_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME
                    + " not defined in property file!" );
                System.exit(1);
            }
            amTopic = envProperties.getProperty(ALARM_MONITOR_JMS_TOPIC_PROPERTY_NAME);
            if ( amTopic == null ) {
                System.out.println("Property " + ALARM_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME
                    + " not defined in property file!" );
                System.exit(1);
            }
            thresholdClient.initAMEventListener(amConnectionFactory,amTopic);

            thresholdClient.connectToThresholdSession(homeName);

            thresholdClient.initializeClient();
           // thresholdClient.testCreateThresholdMonitor();

        } catch ( ThresholdClientException e ) {
            System.out.println("exception="+e);
			e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Initialize the TM client, including creating initial listeners and models
     * for the user interface.
     * Last the user interface is displayed.
     */
    private void initializeClient() {
        System.out.println("initializeClient() called");

        // create user interface
        System.out.println("Create UI...");
        tmFrame = new TMClientFrame();
        tmFrame.setSize(800,600);

        // create model for observable classes
        try {
            ArrayList ooClasses = this.getObservableObjectClasses();
			System.out.println("Obtain the following Observable Object Classes:");
			Util.printObject(ooClasses.toArray());
            observableObjectClassModel = new DefaultComboBoxModel( ooClasses.toArray() );
        } catch ( ThresholdClientException e ) {
            System.out.println("initializeClient()" +"exception="+e);
			e.printStackTrace();
        }
        tmFrame.getObservableObjectClassesComboBox().setModel( observableObjectClassModel );

        // create model for observable object
        observableObjectModel = new DefaultComboBoxModel();
        tmFrame.getObservableObjectComboBox().setModel( observableObjectModel );

        // update model with selected observable object class
        String observableObjectClass = (String)tmFrame.getObservableObjectClassesComboBox().getSelectedItem();
        this.updateObservableObjectModel( observableObjectClass );

        // create model for attribute descriptors
        observableObjectAttributeModel = new DefaultComboBoxModel();
        tmFrame.getObservableObjectAttributeComboBox().setModel( observableObjectAttributeModel );

        // update model with selected observable object class
        this.updateObservableObjectAttributeModel( observableObjectClass );

        // create model for granularity
        granularityModel = new DefaultComboBoxModel();
        tmFrame.getGranularityComboBox().setModel( granularityModel );

        // update granularity model
        String observableObject = (String)tmFrame.getObservableObjectComboBox().getSelectedItem();
        PerformanceAttributeDescriptorListObject padListObject =
            (PerformanceAttributeDescriptorListObject)
                tmFrame.getObservableObjectAttributeComboBox().getSelectedItem();
        this.updateGranularityModel(observableObject,padListObject.pad);

        // add selection listener for observable object classes
        ItemListener oocComboBoxItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                String ooClass = (String)event.getItem();
                updateObservableObjectModel( ooClass );
                updateObservableObjectAttributeModel( ooClass );
            }
        };
        tmFrame.getObservableObjectClassesComboBox().addItemListener( oocComboBoxItemListener );

        // add selection listener for observable object
        ItemListener ooComboBoxItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                //updateObservableObjectAttributeModel( (String)event.getItem() );
                String observableObject = (String)tmFrame.getObservableObjectComboBox().getSelectedItem();
                PerformanceAttributeDescriptorListObject padListObject =
                    (PerformanceAttributeDescriptorListObject)
                        tmFrame.getObservableObjectAttributeComboBox().getSelectedItem();

                updateGranularityModel(observableObject,padListObject.pad);
            }
        };
        tmFrame.getObservableObjectComboBox().addItemListener( ooComboBoxItemListener );

        // add action listener for start button
        ActionListener createActionListener = new ActionListener() {
            public void actionPerformed( ActionEvent event) {
                try {
                    tmFrame.getStartButton().setEnabled(false);
                    tmFrame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR) );
                    createThresholdMonitor();
                    tmFrame.getStopButton().setEnabled(true);
                } catch ( ThresholdClientException e ) {
                    System.out.println("actionPerformed(),exception="+e);

                    JOptionPane.showMessageDialog(tmFrame,
                        e.getMessage(),
                        "Threshold Monitor Client",
                        JOptionPane.ERROR_MESSAGE );

                    tmFrame.getStartButton().setEnabled(true);
                } finally {
                    tmFrame.setCursor( Cursor.getDefaultCursor() );
                }
            }
        };
        tmFrame.getStartButton().addActionListener( createActionListener );

        // add action listener for stop button
        ActionListener removeActionListener = new ActionListener() {
            public void actionPerformed( ActionEvent event) {
                try {
                    tmFrame.getStopButton().setEnabled(false);
                    tmFrame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR) );
                    removeThresholdMonitor();
                    tmFrame.getStartButton().setEnabled(true);
                } catch ( ThresholdClientException e ) {
                    System.out.println("actionPerformed(),exception="+e);

                    JOptionPane.showMessageDialog(tmFrame,
                        e.getMessage(),
                        "Threshold Monitor Client",
                        JOptionPane.ERROR_MESSAGE );
                    tmFrame.getStopButton().setEnabled(true);
                } finally {
                    tmFrame.setCursor( Cursor.getDefaultCursor() );
                }
            }
        };
        tmFrame.getStopButton().addActionListener( removeActionListener );

        // register window closing listener
        tmFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.out.println("windowClosing(),called");
                // try remove currently running monitor if it exist
                if ( thresholdMonitorKey!=null ) {
                    try {
                        ThresholdClient.this.removeThresholdMonitor();
                    } catch( Exception e ) {
                        System.out.println("windowClosing(),exception="+e);
                    }
                }
            }
        } );

        // Make first object selected, generates an item state changed event
        // which fills in the observable object combo box and the observable
        // object attribute list.
        tmFrame.getObservableObjectClassesComboBox().setSelectedIndex(0);

        // create new performance monitor data event list table model
        measurementTableModel = new MeasurementTableModel();
        tmFrame.getMeasurementTable().setModel( measurementTableModel );

        // create new alarm list table model
        alarmTableModel = new AlarmTableModel();
        tmFrame.getAlarmTable().setModel( alarmTableModel );

        tmFrame.getStopButton().setEnabled(false);

        System.out.println("Display UI...");
        tmFrame.setVisible( true );
    }

    /**
     * Create a threshold monitor. Uses the information entered in the user
     * interface.
     */
    private void createThresholdMonitor() throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"createThresholdMonitor()","called" );

        String thresholdName = null;
        PerformanceAttributeDescriptor descriptor=null;
        int granularity = 0,
            direction = ThresholdDirection.RISING;
        Object valueObj = null,
               offsetObj = null;

        // get observable object
        observableObject = (String) tmFrame.getObservableObjectComboBox().getSelectedItem();

        // get performance attribute descriptor
        descriptor = ( (PerformanceAttributeDescriptorListObject)
            tmFrame.getObservableObjectAttributeComboBox().getSelectedItem() ).pad;

        // store attribute name for later use in onMessage() method.
        attribute = descriptor.getName();

        // get threshold monitor name
        thresholdName = tmFrame.getNameTextField().getText().trim();

        String value = null, offset=null;
        // get threshold value
        value = tmFrame.getThresholdValueTextField().getText().trim();

        // get threshold offset
        offset = tmFrame.getThresholdOffsetTextField().getText().trim();

        // get direction
        if ( tmFrame.getRisingRadioButton().isSelected() ) {
            direction = ThresholdDirection.RISING;
        } else {
            direction = ThresholdDirection.FALLING;
        }

        // get granularity
        granularity = ((Integer)tmFrame.getGranularityComboBox().getSelectedItem()).intValue();

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

	System.out.println(tmValue.getObservableObject());
            Log.write("Make threshold definition...");
            ThresholdDefinition td = tmValue.makeThresholdDefinition();

            Log.write("Set attribute descriptor...");
            td.setAttributeDescriptor( descriptor );

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

            ac.setPerceivedSeverity( PerceivedSeverity.MINOR );
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
            //System.out.println("wrx--before create threshold");
            System.out.println("tmValue" + tmValue.getGranularityPeriod());
            thresholdMonitorKey = thresholdSession.createThresholdMonitorByValue(tmValue);
            //System.out.println("wrx--after create threshold");
            Log.write("ok!");
        } catch (javax.oss.IllegalArgumentException e) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            System.out.println(e);
            throw new ThresholdClientException( e.getMessage() );
        } catch (javax.ejb.CreateException e) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            System.out.println("------wrx--exception---");
            System.out.println(e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"createThresholdMonitor()","exception="+e);
            System.out.println(e);
            throw new ThresholdClientException( e.getMessage() );
        } catch (Exception e) {
            
            System.out.println(e);
        }
    }

    /**
     * Remove previously created threshold monitor.
     */
    private void removeThresholdMonitor() throws ThresholdClientException {
        Log.write(this,Log.LOG_ALL,"removeThresholdMonitor()","called" );

        if ( thresholdMonitorKey != null ) {
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
     * Update the observable object model. Fills in the model with observable
     * objects for the specified observable object class.
     * @param observableObjectClass the class to update the model with
     */
    private void updateObservableObjectModel( String observableObjectClass ) {
        Log.write(this,Log.LOG_ALL,"updateObservableObjectModel()","called");
        try {
            ArrayList observableObject = this.getObservableObjects( observableObjectClass );
            // clear current observable object model
            observableObjectModel.removeAllElements();
            // add new observable object conent
            for (int i=0;i<observableObject.size();i++) {
                observableObjectModel.addElement( observableObject.get(i) );
            }
        } catch ( ThresholdClientException e ) {
            Log.write(this,Log.LOG_MAJOR,"updateObservableObjectModel()","exception="+e);
        }
    }

    /**
     * Update the observable object attribute model. Fills the model with the
     * attributes (PerformanceAttributeDescritors) for the specified observable
     * object class.
     * @param observableObjectClassName the object to update model with
     */
    private void updateObservableObjectAttributeModel( String observableObjectClassName ) {
        Log.write(this,Log.LOG_ALL,"updateObservableObjectAttributeModel()","called");
        try {
            ArrayList observableObjectAttribute = this.getObservableObjectAttributes( observableObjectClassName );
            // clear current observable object model
            observableObjectAttributeModel.removeAllElements();
            // add new observable object conent
            for (int i=0;i<observableObjectAttribute.size();i++) {
                observableObjectAttributeModel.addElement(
                    // create a new list object to use in the combo box model
                    new PerformanceAttributeDescriptorListObject(
                    (PerformanceAttributeDescriptor)observableObjectAttribute.get(i) ) );
            }
        } catch ( ThresholdClientException e ) {
            Log.write(this,Log.LOG_MAJOR,"updateObservableObjectAttributeModel()","exception="+e);
        }
    }

    /**
     * Update the granularity model for the selected observable object.
     */
    private void updateGranularityModel( String observableObject, PerformanceAttributeDescriptor pad ) {
        Log.write(this,Log.LOG_ALL,"updateGranularityModel()","observableObject="
            + observableObject + ", pad=" + getPerformanceAttributeDescriptorString( pad ) );

        try {
            // clear current granularity periods
            granularityModel.removeAllElements();
            ArrayList granularities = this.getSupportedGranularities(observableObject,pad);
            // add supported granularities
            for (int i=0;i<granularities.size();i++) {
                granularityModel.addElement( granularities.get( i ) );
            }
        } catch ( ThresholdClientException e ) {
            Log.write(this,"updateGranularityModel()","exception="+e);
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
    private ArrayList getObservableObjectAttributes( String observableObjectClassName )
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
            return ooAttributesArrayList;
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjectAttributes()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( javax.oss.IllegalArgumentException e ) {
            Log.write(this,Log.LOG_MAJOR,"getObservableObjectAttributes()","exception="+e);
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
            initialContext = new InitialContext(envProp);
            //initialContext = new InitialContext();
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
	System.out.println(filter);
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
	System.out.println("factoryName="+factoryName + "\ntopicName=" + topicName + "\nfilter = " + filter);  
        TopicConnectionFactory conFactory = null;
        try {
            Log.write("Get TopicConnectionFactory...");
            conFactory = (TopicConnectionFactory) initialContext.lookup(factoryName);

            Log.write("Create TopicConnection...");
            TopicConnection con = conFactory.createTopicConnection();
            Log.write("Create TopicSession...");
            TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE ); //
            Log.write("Get Topic...");
            Topic topic = (Topic) initialContext.lookup(topicName);

            // create a filter to only receive certain events
            Log.write("filter="+filter);

            Log.write("Create Subscriber...");
            TopicSubscriber subscriber = null;
            if ( filter == null ) {
                subscriber = session.createSubscriber(topic);
            } else {
                subscriber = session.createSubscriber(topic, filter, false);
            }

            subscriber.setMessageListener( listener );
            Log.write("Start TopicConnection...");
            con.start();

            Log.write("ok!");
        } catch ( JMSException e ) {
            e.printStackTrace();
            Log.write(this,"initEventListener()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( NamingException e ) {
            e.printStackTrace();
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

    /**
     * Simple test method for creating a threshold monitor.
     */
    private void testCreateThresholdMonitor() throws ThresholdClientException {
        Log.write(this,"testCreateThresholdMonitor()","called");

        // -- initial data, required!
        String observableObject =
            "G3SubNetwork=Sweden,G3ManagedElement=RNC-Gbg-1,Radio=R0010,Cell=C0010,CellMeasurement=CM090";
        String observableObjectClass = "CellMeasurement";
        String thresholdName = "myThreshold-" + System.currentTimeMillis();
        // --

        Log.write("Create threshold for " + observableObject );
        Log.write("Type of observable object:" + observableObjectClass );

        // performance attribute descriptor
        PerformanceAttributeDescriptor descriptor = null;

        ArrayList arrayList = this.getObservableObjectAttributes( observableObjectClass );
        descriptor = (PerformanceAttributeDescriptor)arrayList.get(0);
        Log.write("PerformanceAttributeDescriptor="+getPerformanceAttributeDescriptorString(descriptor) );

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
            // descriptors can be loaded by using getObservableAttributes()

            Log.write("Set attribute descriptor...");
            td.setAttributeDescriptor(descriptor);
            if ( descriptor.getType() == PerformanceAttributeDescriptor.INTEGER ) {
                Log.write("Set value...");
                td.setValue(new Integer(50));
                // Hysteresis
                Log.write("Set offset...");
                td.setOffset(new Integer(10));
            } else {
                Log.write(this,Log.LOG_ALL,"testCreateThresholdMonitor()","Not an INTEGER threshold!");
                throw new ThresholdClientException( "Not an INTEGER threshold!" );
            }
            Log.write("Set direction...");
            td.setDirection( ThresholdDirection.RISING );

            Log.write("Set threshold definition...");
            tmValue.setThresholdDefinition(td);
            // Sets the AlarmConfig attributes
            Log.write("Make alarm config...");
            AlarmConfig ac = tmValue.makeAlarmConfig();
            // The alarm config contains default values for all attributes
            // except PerceivedSeverity.
            Log.write("Set perceived severity...");

            ac.setPerceivedSeverity( PerceivedSeverity.MINOR );
            Log.write("Set alarm config...");
            tmValue.setAlarmConfig(ac);
            // Note that no granularity is set. Uses system default.

            // set schedule (run always)
            Log.write("Set schedule...");
            Schedule schedule = tmValue.makeSchedule();
            tmValue.setSchedule( schedule );

            // Give a name to the job.
            Log.write("Set name...");
            tmValue.setName( thresholdName );
            // Create the Threshold monitor job and save the key for further
            // reference.
            ThresholdMonitorKey key = null;
            Log.write("Create threshold monitor by value...");
            key = thresholdSession.createThresholdMonitorByValue(tmValue);
            Log.write("ok!");
        } catch (javax.oss.IllegalArgumentException e) {
            Log.write(this,Log.LOG_MAJOR,"testCreateThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch (javax.ejb.CreateException e) {
            Log.write(this,Log.LOG_MAJOR,"testCreateThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"testCreateThresholdMonitor()","exception="+e);
            throw new ThresholdClientException( e.getMessage() );
        }
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
            displayString = ThresholdClient.getPerformanceAttributeDescriptorString( pad );
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
System.out.println("The following is data from event : " + pmEvent.getAttributeValue( PerformanceDataEvent.EVENT_TIME )  + pmEvent.getAttributeValue( PerformanceDataEvent.MANAGED_OBJECT_INSTANCE) + "report content : " + measXMLReport);
                        // parse measurment report
                        Document doc = PerformanceMonitorReportHandler.getDocument( measXMLReport );
                        String measValue = PerformanceMonitorReportHandler.getMeasurementValue(
                            doc,observableObject,attribute,null);
                        if ( measValue != null ) {
                            rowData.add( measValue );
                            ThresholdClient.this.measurementTableModel.addRow( rowData );
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

                        ThresholdClient.this.alarmTableModel.addRow( rowData );

                    } else {
                        Log.write(this,Log.LOG_ALL,"onMessage()","not handled message: " + obj.getClass().getName());
                    }
                }
            } catch (Exception e) {
                Log.write(this,Log.LOG_MAJOR,"onMessage()","exception="+e);
            }
        }
    }//AlarmEventHandler class
}//ThresholdClient
