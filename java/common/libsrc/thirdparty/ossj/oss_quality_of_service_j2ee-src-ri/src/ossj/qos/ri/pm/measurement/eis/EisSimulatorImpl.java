package ossj.qos.ri.pm.measurement.eis;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.Date;
import java.util.Enumeration;
import java.util.Calendar;

import javax.jms.*;
import javax.ejb.*;
import javax.naming.*;
import javax.oss.*;
import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityValueIterator;
import javax.oss.pm.util.*;
import javax.oss.pm.measurement.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.SAXParser;

import ossj.qos.util.GranualityFinder;
import ossj.qos.ApplicationContextImpl;
import ossj.qos.pm.measurement.PerformanceDataAvailableEventDescriptorImpl;
import ossj.qos.pm.measurement.PerformanceDataEventDescriptorImpl;
import ossj.qos.pm.measurement.QueryByDNValueImpl;
import ossj.qos.pm.measurement.QueryPerformanceMonitorValueImpl;
import ossj.qos.pm.measurement.ReportFormatImpl;
import ossj.qos.pm.measurement.ReportInfoImpl;
import ossj.qos.pm.measurement.ReportInfoIteratorImpl;
import ossj.qos.pm.measurement.PerformanceMonitorByObjectsValueImpl;
import ossj.qos.pm.measurement.PerformanceMonitorByClassesValueImpl;
import ossj.qos.pm.measurement.PerformanceMonitorValueImpl;
import ossj.qos.pm.measurement.PerformanceMonitorValueIteratorImpl;
import ossj.qos.pm.measurement.PerformanceMonitorKeyImpl;
import ossj.qos.pm.measurement.PerformanceAttributeDescriptorImpl;
import ossj.qos.pm.measurement.PerformanceMonitorKeyResultImpl;
import ossj.qos.pm.measurement.ReportDataImpl;
import ossj.qos.pm.measurement.CurrentResultReportImpl;

/**
 * The class <code>EisSimulatorImpl</code> implements the <code>EisSimulator</code>
 * interface.
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 1.0
 */


public class EisSimulatorImpl extends UnicastRemoteObject implements EisSimulator {


    // JMS stuff
    private TopicSession pubSession;
    private TopicPublisher publisher;
    private TopicConnection connection;

    /** References to jobs and their keys.
     */
    protected static Hashtable objIdentifier = new Hashtable();

    /** Observable objects.
     */
    private ObservableObject[] obsObjects;

    /** Observable object classes.
     */
    private String[] obsObjectClasses;

    /** Observable object classes.
     *  @todo Observable object classes should be removed from simulator to
     *  external configurable resources.
     */
    //private String subNetwork, managedElement, radio, sgsn,
    //  cell, sgsnMeasurement, cellMeasurement;

    /** One log for the whole simulator. Visible for all classes in the package.
     *  @todo This should be moved out from this class!!!!
     */
    public static Log trace = new Log();

    /** Hold the values from the property file that is given as a start parameter
     *  to the EisSimulator.
     */
    private static Properties properties = null;

    /** Keep the network model.
     */
    private NetworkModel networkModel = null;

    /** Holds the JNDI properties which have been read from the property file
     */
    private static Properties jndi_properties = null;

    /** Object for supervising expiration dates of existing file reports.
     */
    private ReportInformation reportInformation = null;
    

    /**
     * @param propertyFile    file from which the EIS simulator properties are taken
     * @exception java.rmi.RemoteException
     * @exception javax.naming.NamingException
     * @exception javax.jms.JMSException
     */


    public EisSimulatorImpl(String propertyFile) throws java.rmi.RemoteException,
    javax.naming.NamingException, javax.jms.JMSException {
        super ();
        // Read the propertyfile
        initProperties(propertyFile);
        // Start the log with configuration from the property file
        trace = new Log(properties.getProperty("LOG_FILE"), properties.getProperty("LOG_LEVEL"));

        // Load the network model from file
        networkModel = new NetworkModel( properties.getProperty("END_DATE") );

        // Load network model
        try {
            networkModel.loadModel( properties.getProperty("NETWORK_MODEL") );
        }
        catch (Exception e) {
            trace.log(e.toString(), 3);
            System.exit(1);
        }

        // Load data for network model
        try {
            networkModel.loadData( properties.getProperty("NETWORK_MODEL_DATA_DIR") );
        }
        catch (Exception e) {
            trace.log(e.toString(), 1);
            System.exit(1);
        }

        // Start Java Messaging
        initJms();
        //Initiate JNDI properties
        initJNDI();

        // Init the observable objects
        //createObservableObjects();

        //Start checking expiration dates
        reportInformation = new ReportInformation(properties.getProperty("PM_DATA_PATH"));
    }


    /** The <code>initProperties</code> method reads application properties from
     *  file.
     *
     * @param propertyFile   file from which the EIS simulator properties are taken
     *
     */

    private void initProperties(String propertyFile) {
        trace.log("->EisSimulatorImpl.initProperties(String propertyFile)", 1);
        try {
            File propertiesFile = new File(propertyFile);

            properties = new Properties();
            properties.load( new FileInputStream(propertiesFile) );
        }
        catch ( Exception e ) {
            trace.log("Error processing " + propertyFile );
			e.printStackTrace();
            System.exit(1);
        }
        trace.log("<-EisSimulatorImpl.initProperties(String propertyFile)", 1);
    }

    /** The <code>initJms</code> method reads application properties from
     *  file.
     *
     */
    private void initJms() {
        //System.out.println("begin to initJms");
        trace.log("->EisSimulatorImpl.initJms()", 1);
        trace.log("Initiate properties object from properties file to be able to connect to JNDI Context Factory", 2);
        /*
        Properties env = new Properties();

        env.put(Context.INITIAL_CONTEXT_FACTORY, properties.getProperty("JMS_INITIAL_CONTEXT_FACTORY"));
        env.put(Context.PROVIDER_URL, properties.getProperty("JMS_PROVIDER_URL"));
        env.put(Context.SECURITY_PRINCIPAL, properties.getProperty("JMS_SECURITY_PRINCIPAL"));
        env.put(Context.SECURITY_CREDENTIALS, properties.getProperty("JMS_SECURITY_CREDENTIALS"));
        
        */
        
        Context jndi = null ;

        try {

            //trace.log("Trying to get initial context factory: " + env.get(Context.INITIAL_CONTEXT_FACTORY), 2);
            trace.log("Trying to get initial context...");
            try {
                jndi = new InitialContext(properties);
            }
            catch (Exception e) {
                System.out.println("exception when inital context");
                System.out.println(e);
                trace.log("Was not able to resolve initial context factory. Will exit now!", 3);
                trace.log(e.toString(), 3);
                System.exit(1);
            }

            trace.log("Initial context factory resolved ");// + env.get(Context.INITIAL_CONTEXT_FACTORY), 1);
            trace.log("Will now try to lookup up the TopicConnectionFactory in JNDI and create a connection.", 1);
            try {

                //System.out.println("before factory");
                trace.log("Factory name="+properties.getProperty("JMS_CONNECTION_FACTORY"));
                TopicConnectionFactory tcf = (TopicConnectionFactory) jndi.lookup(properties.getProperty("JMS_CONNECTION_FACTORY"));
                //System.out.println("after factory");
                connection = tcf.createTopicConnection();
                //(properties.getProperty("JMS_SECURITY_USERNAME"),
                // properties.getProperty("JMS_SECURITY_PASSWORD"));
            }
            catch (Exception e) {
                System.out.println("exception when initial factory");
                System.out.println(e);
                trace.log("Did not succeed creating a connection to JMS. Will exit now!", 3);
                trace.log(e.toString(), 3);
                System.exit(1);
            }

            trace.log("Will try to create a JMS session", 1);
            try {
                pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            }
            catch (Exception e) {
                trace.log("Was not able to create a JMS session object. Will exit now!");
                trace.log(e.toString(), 3);
                System.exit(1);
            }

            trace.log("Will try to find the topic \"" + properties.getProperty("JMS_TOPIC") + "\"");
            try {
                Topic topic = (Topic) jndi.lookup(properties.getProperty("JMS_TOPIC"));

                publisher = pubSession.createPublisher(topic);
            }
            catch (Exception e) {
                System.out.println("three");
                System.out.println(e);
                trace.log("Had some problems resolving the topic \"" + properties.getProperty("JMS_TOPIC") + "\"");
                trace.log(e.toString(), 3);
                
            }
            // And start...
            connection.start();
        }
        catch (Exception e) {
            trace.log(e.toString(), 3);
            System.exit(1);
        }
        trace.log("<-EisSimulatorImpl.initJms()", 1);
        //System.out.println("after initJms");
    }

    /** The <code>initJNDI</code> method reads JNDI properties
     *   from the property file.
     */

    private void initJNDI() {
        trace.log("->EisSimulatorImpl.initJNDI()", 1);

        if (properties.getProperty("PM_PROPERTY_MODE").compareTo("true") == 0) {
            jndi_properties = new Properties();
            jndi_properties.put(Context.INITIAL_CONTEXT_FACTORY, properties.getProperty("PM_INITIAL_CONTEXT_FACTORY "));
            jndi_properties.put(Context.PROVIDER_URL, properties.getProperty("PM_PROVIDER_URL"));
            jndi_properties.put(Context.SECURITY_PRINCIPAL, properties.getProperty("PM_SECURITY_PRINCIPAL"));
            jndi_properties.put(Context.SECURITY_CREDENTIALS, properties.getProperty("PM_SECURITY_CREDENTIALS"));
        }

        trace.log("<-EisSimulatorImpl.initJNDI()", 1);
    }

    /** The <code>main</code> method. Takes one and only one parameter and that
     *  is the path and name of the property file.
     *  <p>
     *  @param args[0]   path and name of the property file
     */

    public static void main(String args[]) {
        //VP workaround 4766976
        {
            com.sun.enterprise.Switch  sw = com.sun.enterprise.Switch.getSwitch();
            sw.setContainerType(com.sun.enterprise.Switch.APPCLIENT_CONTAINER);
        }
        //end VP


        java.rmi.registry.Registry r = null;

        trace.log("->EisSimulatorImpl.main(String args[])", 1);

        if (args.length != 1) {
            System.out.println("Proper usage: java SimulatorImpl propertyfile");
            trace.log("Wrong number of arguments supplied when trying to start EIS Simlator!", 3);
            System.exit(1);
        }


        trace.log("Set security manager to RMISecurityManager()", 1);
        System.setSecurityManager(new RMISecurityManager());

        try {
            EisSimulator myEis = new EisSimulatorImpl(args[0]);

            trace.log("Will bind simulator to RMI registry now.", 1);
            try {
                java.rmi.Naming.rebind("//localhost/EisSimulator", myEis);
                trace.log("Simulator bound in RMI registry and ready!",2);

            } catch (RemoteException e) {

                r = java.rmi.registry.LocateRegistry.createRegistry(1099);
                try {
                    java.rmi.Naming.rebind("//localhost/EisSimulator", myEis);
                    trace.log("Simulator bound in RMI registry and ready!", 2);

                } catch (Exception x) {
                    trace.log("Was not able to register the EIS Simulator in the RMI Registry. Check if the RMI registry is started!", 3);
                    trace.log(e.toString());
                    System.exit(1);
                }

            } catch (Exception e) {
                trace.log("Was not able to register the EIS Simulator in the RMI Registry. Check if the RMI registry is started!", 3);
                trace.log(e.toString());
                System.exit(1);
            }
        }
        catch (Exception e) {
            trace.log(e.toString(), 3);
            System.exit(1);
        }
        trace.log("<-EisSimulatorImpl.main(String args[])", 1);
        System.out.println("register success");
    }

    /** @see EisSimulator#simulatorGetManagedEntityTypes()
     */
    public String[] simulatorGetManagedEntityTypes()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetManagedEntityTypes()", 1);
            String[] managedEntityTypes ={
                PerformanceMonitorByObjectsValue.VALUE_TYPE,
                PerformanceMonitorByClassesValue.VALUE_TYPE
            }
            ;

            trace.log("<-EisSimulatorImpl.simulatorGetManagedEntityTypes()", 1);
            return managedEntityTypes;
        }

    /** @see EisSimulator#simulatorGetVersion()
     */
    public String[] simulatorGetVersion()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetVersion()", 1);
            String version = "OSS Through Java Initiative, Quality of Service Reference Implementation, Version: " +
            JVTPerformanceMonitorSession.OSS_QOS_VERSION_R1;
            String[] supportedVersions ={
                version
            }
            ;

            trace.log("<-EisSimulatorImpl.simulatorGetVersion()", 1);
            return supportedVersions;
        }

    /** @see EisSimulator#simulatorGetSupportedOptionalOperations()
     */
    public String[] simulatorGetSupportedOptionalOperations()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetSupportedOptionalOperations()", 1);

            String[] supportedOperations =
            { new String(JVTPerformanceMonitorSessionOptionalOpt.GET_CURRENT_RESULT_REPORT),
              new String(JVTPerformanceMonitorSessionOptionalOpt.RESUME_PERFORMANCE_MONITOR_BY_KEY),
              new String(JVTPerformanceMonitorSessionOptionalOpt.SUSPEND_PERFORMANCE_MONITOR_BY_KEY),
              new String(JVTPerformanceMonitorSessionOptionalOpt.TRY_CREATE_PERFORMANCE_MONITORS_BY_VALUES),
              new String(JVTPerformanceMonitorSessionOptionalOpt.TRY_REMOVE_PERFORMANCE_MONITORS_BY_KEYS),
              new String(JVTPerformanceMonitorSessionOptionalOpt.TRY_RESUME_PERFORMANCE_MONITORS_BY_KEYS),
              new String(JVTPerformanceMonitorSessionOptionalOpt.TRY_SUSPEND_PERFORMANCE_MONITORS_BY_KEYS) };

            trace.log("<-EisSimulatorImpl.simulatorGetSupportedOptionalOperations()", 1);
            return supportedOperations;
        }

    /** @see EisSimulator#simulatorGetEventTypes()
     */
    public String[] simulatorGetEventTypes()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetEventTypes()", 1);
            String eventTypes[] = new String[2];

            eventTypes[0] = new String(PerformanceDataAvailableEventDescriptor.OSS_EVENT_TYPE_VALUE);
            eventTypes[1] = new String(PerformanceDataEventDescriptor.OSS_EVENT_TYPE_VALUE);
            trace.log("<-EisSimulatorImpl.simulatorGetEventTypes()", 1);
            return eventTypes;
        }

    /** @see EisSimulator#simulatorGetEventDescriptor( String eventType)
     */
    public EventPropertyDescriptor simulatorGetEventDescriptor( String eventType )
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetEventDescriptor( String eventType)", 1);
        EventPropertyDescriptor ed = null;

        if (eventType.compareTo(PerformanceDataAvailableEventDescriptor.OSS_EVENT_TYPE_VALUE) == 0)
            ed = new PerformanceDataAvailableEventDescriptorImpl();
        if (eventType.compareTo(PerformanceDataEventDescriptor.OSS_EVENT_TYPE_VALUE) == 0)
            ed = new PerformanceDataEventDescriptorImpl();
        if (ed == null)
            throw new javax.oss.IllegalArgumentException();
        trace.log("<-EisSimulatorImpl.simulatorGetEventDescriptor( String eventType)", 1);
        return ed;
    }

    /** @see EisSimulator#simulatorMakePerformanceMonitorValue( String value)
     */
    public PerformanceMonitorValue simulatorMakePerformanceMonitorValue( String value)
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorMakePerformanceMonitorValue( String value)", 1);
        PerformanceMonitorValue returnValue = null;
        boolean found = false;
        String[] pmTypes = simulatorGetManagedEntityTypes();

        for (int i = 0; i < pmTypes.length; i++) {
            if (pmTypes[i].compareTo(value) == 0)
                found = true;
        }
        if (found == false)
            throw new javax.oss.IllegalArgumentException();
        if (value.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) == 0)
            returnValue = new PerformanceMonitorByObjectsValueImpl();
        if (value.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) == 0)
            returnValue = new PerformanceMonitorByClassesValueImpl();
        trace.log("<-EisSimulatorImpl.simulatorMakePerformanceMonitorValue( String value)", 1);
        return returnValue;
    }

    /** @see EisSimulator#simulatorGetQueryTypes()
     */
    public String[] simulatorGetQueryTypes()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetQueryTypes()", 1);
            String queryTypes[] ={
                QueryPerformanceMonitorValue.QUERY_TYPE, QueryByDNValue.QUERY_TYPE
            };

            trace.log("<-EisSimulatorImpl.simulatorGetQueryTypes()", 1);
            return queryTypes;
        }

    /** @see EisSimulator#simulatorMakeQueryValue(String type)
     */
    public QueryValue simulatorMakeQueryValue(String type)
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorMakeQueryValue(String type)", 1);
        QueryValue returnValue = null;

        if (type.compareTo(QueryPerformanceMonitorValue.QUERY_TYPE) == 0)
            returnValue =  new QueryPerformanceMonitorValueImpl();
        if (type.compareTo(QueryByDNValue.QUERY_TYPE) == 0)
            returnValue = new QueryByDNValueImpl();
        if (returnValue == null)
            throw new javax.oss.IllegalArgumentException();
        trace.log("<-EisSimulatorImpl.simulatorMakeQueryValue(String type)", 1);
        return returnValue;
    }

    /** @see EisSimulator#simulatorGetReportFormats()
     */
    public ReportFormat[] simulatorGetReportFormats() throws java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetReportFormats()", 1);
        ReportFormat rf = new ReportFormatImpl();
        ReportFormat[] rfArray = {rf};

        trace.log("<-EisSimulatorImpl.simulatorGetReportFormats()", 1);
        return rfArray;
    }

    /** @see EisSimulator#simulatorGetCurrentReportFormat()
     */
    public ReportFormat simulatorGetCurrentReportFormat()
        throws java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetCurrentReportFormat()", 1);
        ReportFormat rf = new ReportFormatImpl();

        trace.log("<-EisSimulatorImpl.simulatorGetCurrentReportFormat()", 1);
        return rf;
    }

    /** @see EisSimulator#simulatorGetReportModes()
     */
    public int[] simulatorGetReportModes()
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetReportModes()", 1);
            int[] reportModes = { ReportMode.EVENT_SINGLE, ReportMode.FILE_SINGLE };

            trace.log("<-EisSimulatorImpl.simulatorGetReportModes()", 1);
            return reportModes;
        }

    /** @see EisSimulator#simulatorGetObservableObjectClasses()
     */
    public ObservableObjectClassIterator simulatorGetObservableObjectClasses()
        throws java.rmi.RemoteException {
        //System.out.println("---wrx: before in simulator,get class");
        trace.log("->EisSimulatorImpl.simulatorGetObservableObjectClasses()", 1);
        ObservableObjectClassIterator oocIterator = networkModel.getObservableObjectClasses();

        //System.out.println("---wrx:after in simulator,get class");
        trace.log("<-EisSimulatorImpl.simulatorGetObservableObjectClasses()", 1);
        //System.out.println("----wrx:return value" + oocIterator);
        return oocIterator;
    }

    /** @see EisSimulator#simulatorGetObservableObjects(String observableObjectClassName, String base )
     */
    public ObservableObjectIterator simulatorGetObservableObjects(String observableObjectClassName, String base )
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetObservableObjects(String observableObjectClassName, String base )", 1);
        ObservableObjectIterator ooIterator = networkModel.getObservableObjects(observableObjectClassName, base);

        trace.log("<-EisSimulatorImpl.simulatorGetObservableObjects(String observableObjectClassName, String base )", 1);
        return ooIterator;
    }

    /** @see EisSimulator#simulatorGetSupportedObservableObjects( String[] dnList )
     */
    public String[] simulatorGetSupportedObservableObjects( String[] dnList )
        throws java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetSupportedObservableObjects( String[] dnList )", 1);
            String[] supportedObservableObjects = networkModel.getSupportedObservableObjects(dnList);

            trace.log("<-EisSimulatorImpl.simulatorGetSupportedObservableObjects( String[] dnList )", 1);
            return supportedObservableObjects;
        }

    /** @see EisSimulator#simulatorGetObservableAttributes( String observableObjectClassName )
     *  @todo This method shoule be changed and not so "hard coded" in future releases.
     */
    public PerformanceAttributeDescriptor[] simulatorGetObservableAttributes( String observableObjectClassName )
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorGetObservableAttributes( String observableObjectClassName )", 1);
            PerformanceAttributeDescriptor[]  attrDescr = networkModel.getObservableAttributes(observableObjectClassName);

            trace.log("<-EisSimulatorImpl.simulatorGetObservableAttributes( String observableObjectClassName )", 1);
            return attrDescr;
        }

    /** @see EisSimulator#simulatorGetSupportedGranularities( PerformanceMonitorValue pmValue )
     *
     */
    public int[] simulatorGetSupportedGranularities( PerformanceMonitorValue pmValue )
        throws java.rmi.RemoteException, javax.oss.IllegalArgumentException {

            int[] supportedGranularities = null;

            trace.log("->simulatorGetSupportedGranularities( PerformanceMonitorValue pmValue )", 1);

            if (pmValue == null){
                throw new javax.oss.IllegalArgumentException();
            }

            String lastClassName = null;

            if (pmValue instanceof PerformanceMonitorByObjectsValue) {
                PerformanceMonitorByObjectsValue byObject = (PerformanceMonitorByObjectsValue) pmValue;
                String[] distinguishedNames = null;
                try {
                    distinguishedNames = byObject.getObservedObjects();
                } catch(java.lang.IllegalStateException ie) {
                    throw new javax.oss.IllegalArgumentException("Observed objects have not been set in input parameter" + ie);
                }

                try {
                    int start = distinguishedNames[0].lastIndexOf(',');
                    int stop = distinguishedNames[0].lastIndexOf('=');
                    lastClassName = distinguishedNames[0].substring(start+1, stop);
                }
                catch(Exception e){
                    throw new javax.oss.IllegalArgumentException("Wrong format for observed objects.");
                }
                supportedGranularities = networkModel.getSupportedGranularities(lastClassName);
                trace.log("<-simulatorGetSupportedGranularities( PerformanceMonitorValue pmValue )", 1);
            }
            else if (pmValue instanceof PerformanceMonitorByClassesValue) {
                PerformanceMonitorByClassesValue byClasses = (PerformanceMonitorByClassesValue) pmValue;
                String[] observedClasses = null;
                try {
                    observedClasses = byClasses.getObservedObjectClasses();
                } catch(java.lang.IllegalStateException ie) {
                    throw new javax.oss.IllegalArgumentException("Observed object classes have not been set in input parameter" + ie);
                }

                try {

                    GranualityFinder granulaityFinder = new GranualityFinder();
                    for (int i=0; i<observedClasses.length; i++) {
                        if (i == 0)
                            granulaityFinder.setFirstSet(networkModel.getSupportedGranularities(observedClasses[i]));
                        else
                            granulaityFinder.addToCompareSet(networkModel.getSupportedGranularities(observedClasses[i]));
                    }
                    supportedGranularities = granulaityFinder.getGranuality();
                    trace.log("<-simulatorGetSupportedGranularities( PerformanceMonitorValue pmValue )", 1);
                }
                catch(Exception e){
                    throw new javax.oss.IllegalArgumentException("Wrong format for observed object classes.");
                }
            }
            else
            throw new javax.oss.IllegalArgumentException("Not supported value type.");
            return supportedGranularities;
        }



    /** @see EisSimulator#simulatorGetPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)
     *
     */
    public ReportInfoIterator simulatorGetPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)
        throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)", 1);

		// FDP - add processing for null key
		Vector reportInfos = null;
		if (pmKey == null) {
		    reportInfos = reportInformation.getAllReportInfos();
		} else {
			if (pmKey.getPerformanceMonitorPrimaryKey() == null){
                trace.log("--EisSimulatorImpl.simulatorGetPerformanceReportInfo null primaryKey for key ["+pmKey+"]", 3);
                throw new javax.oss.IllegalArgumentException("null primary key");
            }
			reportInfos = reportInformation.getReportInfos(pmKey);
		}
        if (reportInfos == null){
            trace.log("--EisSimulatorImpl.simulatorGetPerformanceReportInfo reportInfos == null for key ["
                      +pmKey+"]", 3);
            throw new javax.ejb.ObjectNotFoundException();
        }
        
        trace.log("--EisSimulatorImpl.simulatorGetPerformanceReportInfo found "+reportInfos.size()+" report Infos", 1);
        
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd_hh-mm-ss");
            Date searchDate = date.getTime();
            String searchDateStr = sdf.format(searchDate);
            Enumeration enumReportInfos = reportInfos.elements();
            
            while (enumReportInfos.hasMoreElements()) {
                ReportInfo ri = (ReportInfo) enumReportInfos.nextElement();
                URL url = ri.getURL();
                String filepath = url.getFile();
                String filename = filepath.substring(filepath.lastIndexOf('/') + 1, filepath.lastIndexOf('.'));
                
                if (filename.compareTo(searchDateStr) < 0)
                    reportInfos.remove(ri);
            }
        }
        
        ReportInfoIterator infosIterator = null;
        ReportInfo[] reportInfosArray = null;
        
        reportInfosArray = new ReportInfoImpl[reportInfos.size()];
        reportInfosArray = (ReportInfo[]) reportInfos.toArray(reportInfosArray);
        infosIterator = new ReportInfoIteratorImpl(reportInfosArray);
        trace.log("<-EisSimulatorImpl.simulatorGetPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)", 1);
        return infosIterator;
    }

    /** @see EisSimulator#simulatorGetPerformanceMonitorByKey(PerformanceMonitorKey pmKey, String[] attributes)
     *
     */
    public PerformanceMonitorValue simulatorGetPerformanceMonitorByKey(PerformanceMonitorKey pmKey, String[] attributes)
        throws javax.ejb.ObjectNotFoundException, java.rmi.RemoteException, javax.oss.IllegalArgumentException {
        trace.log("->EisSimulatorImpl.simulatorGetPerformanceMonitor(PerformanceMonitorKey pmKey, String[] attributes)", 1);
        PerformanceMonitorValue measurementJob = null;
        PerformanceMonitorValue pmValue = null;
        PerformanceMonitorValue[] measurementJobs =  simulatorGetMeasurementJobsList();

        //boolean validAttribute = true;

        //Validating key
        if (pmKey == null)
            throw new javax.oss.IllegalArgumentException("pmKey should not be null");

        if (pmKey.getPerformanceMonitorPrimaryKey() == null)
            throw new javax.oss.IllegalArgumentException("primaryKey of pmKey should not be null");

        for (int i = 0; i < measurementJobs.length; i++) {
            if (pmKey.getPerformanceMonitorPrimaryKey().compareTo(measurementJobs[i].getPerformanceMonitorKey().getPerformanceMonitorPrimaryKey()) == 0)
                measurementJob = measurementJobs[i];
        }

        if (measurementJob == null)
            throw new javax.ejb.ObjectNotFoundException();


        //Validating attribute list
        if (attributes == null)
            throw new javax.oss.IllegalArgumentException();
        else {
            for (int i = 0; i < attributes.length; i++) {
                if (attributes[i] == null)
                    throw new javax.oss.IllegalArgumentException("attribute list contains null value");
            }
        }


        //Create value object to return
        if (measurementJob instanceof PerformanceMonitorByClassesValue)
            pmValue = new PerformanceMonitorByClassesValueImpl();

        if (measurementJob instanceof PerformanceMonitorByObjectsValue)
            pmValue = new PerformanceMonitorByObjectsValueImpl();


        //If attribute list is null, all attributes will be returned
        if (attributes.length == 0) {

            if (pmValue instanceof PerformanceMonitorByClassesValue) {

                String[] tempAttributes = {
                    PerformanceMonitorValue.GRANULARITY_PERIOD, PerformanceMonitorValue.NAME,
                    PerformanceMonitorValue.REPORT_BY_EVENT, PerformanceMonitorValue.REPORT_BY_FILE,
                    PerformanceMonitorValue.REPORT_FORMAT, PerformanceMonitorValue.REPORTING_PERIOD,
                    PerformanceMonitorValue.SCHEDULE, PerformanceMonitorValue.STATE,
                    ManagedEntityValue.KEY, PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES,
                    PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES, PerformanceMonitorByClassesValue.SCOPE };

                attributes = tempAttributes;
            }

            if (pmValue instanceof PerformanceMonitorByObjectsValue) {
                String[] tempAttributes = {
                    PerformanceMonitorValue.GRANULARITY_PERIOD, PerformanceMonitorValue.NAME,
                    PerformanceMonitorValue.REPORT_BY_EVENT, PerformanceMonitorValue.REPORT_BY_FILE,
                    PerformanceMonitorValue.REPORT_FORMAT, PerformanceMonitorValue.REPORTING_PERIOD,
                    PerformanceMonitorValue.SCHEDULE, PerformanceMonitorValue.STATE,
                    ManagedEntityValue.KEY, PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES,
                    PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS };

                attributes = tempAttributes;
            }

        }

        pmValue.setPerformanceMonitorKey(measurementJob.getPerformanceMonitorKey());

        for (int k = 0; k < attributes.length; k++) {
            // if(attributes[k] != null){
            if (attributes[k].compareTo(PerformanceMonitorValue.GRANULARITY_PERIOD) == 0)
                pmValue.setGranularityPeriod(measurementJob.getGranularityPeriod());
            else if (attributes[k].compareTo(PerformanceMonitorValue.NAME) == 0)
                pmValue.setName(measurementJob.getName());
            else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_BY_EVENT) == 0)
                pmValue.setReportByEvent(measurementJob.getReportByEvent());
            else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_BY_FILE) == 0)
                pmValue.setReportByFile(measurementJob.getReportByFile());
            else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_FORMAT) == 0)
                pmValue.setReportFormat(measurementJob.getReportFormat());
            else if (attributes[k].compareTo(PerformanceMonitorValue.REPORTING_PERIOD) == 0) {
                if (measurementJob.isPopulated(PerformanceMonitorValue.REPORTING_PERIOD) == true)
                    pmValue.setReportPeriod(measurementJob.getReportPeriod());
            }
            else if (attributes[k].compareTo(PerformanceMonitorValue.SCHEDULE) == 0)
                pmValue.setSchedule(measurementJob.getSchedule());
            else if (attributes[k].compareTo(PerformanceMonitorValue.STATE) == 0) {
                if (measurementJob.isPopulated(PerformanceMonitorValue.STATE) == true)
                    pmValue.setState(measurementJob.getState());
            }
            else if (attributes[k].compareTo(ManagedEntityValue.KEY) == 0)
                pmValue.setPerformanceMonitorKey(measurementJob.getPerformanceMonitorKey());
            else {
                if (pmValue instanceof PerformanceMonitorByClassesValue) {
                    PerformanceMonitorByClassesValue pmCValue = (PerformanceMonitorByClassesValue) pmValue;
                    PerformanceMonitorByClassesValue measurementJobsByClsValue = (PerformanceMonitorByClassesValue) measurementJob;

                    if (attributes[k].compareTo(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES) == 0)
                        pmCValue.setMeasurementAttributes(measurementJobsByClsValue.getMeasurementAttributes());
                    else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES) == 0)
                        pmCValue.setObservedObjectClasses(measurementJobsByClsValue.getObservedObjectClasses());
                    // Katarina
                    else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.SCOPE) == 0)
                        pmCValue.setScope(measurementJobsByClsValue.getScope());
                    else if (attributes[k].compareTo(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS) == 0)
                        throw new javax.oss.IllegalArgumentException();
                }

                if (pmValue instanceof PerformanceMonitorByObjectsValue) {
                    PerformanceMonitorByObjectsValue pmOValue = (PerformanceMonitorByObjectsValue) pmValue;
                    PerformanceMonitorByObjectsValue measurementJobsByObjValue = (PerformanceMonitorByObjectsValue) measurementJob;

                    if (attributes[k].compareTo(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES) == 0)
                        pmOValue.setMeasurementAttributes(measurementJobsByObjValue.getMeasurementAttributes());
                    else if (attributes[k].compareTo(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS) == 0)
                        pmOValue.setObservedObjects(measurementJobsByObjValue.getObservedObjects());
                    else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES) == 0)
                        throw new javax.oss.IllegalArgumentException();
                    // Katarina
                    else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.SCOPE) == 0)
                        throw new javax.oss.IllegalArgumentException();
                }
            }
        }
        trace.log("<-EisSimulatorImpl.simulatorGetPerformanceMonitor(PerformanceMonitorKey pmKey, String[] attributes)", 1);
        return pmValue;
    }

    /** @see EisSimulator#simulatorGetPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKey, String[] attributes)
     *
     */
    public PerformanceMonitorValueIterator simulatorGetPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKey, String[] attributes)
        throws javax.ejb.FinderException, java.rmi.RemoteException, javax.oss.IllegalArgumentException {
        trace.log("->EisSimulatorImpl.simulatorGetPerformanceMonitors(PerformanceMonitorKey[] pmKey, String[] attributes)", 1);
        PerformanceMonitorValueIterator pmvIterator = null;
        PerformanceMonitorValue pmValue = null;
        Vector performanceMonitorValue = new Vector();
        boolean allKeysChosen = false;
        boolean jobFound = false;
        boolean attributesSet = false;
        int nrOfJobs = 0;

        PerformanceMonitorValue[] measurementJobs =  simulatorGetMeasurementJobsList();

        if (pmKey == null)
            throw new javax.oss.IllegalArgumentException();

        if (pmKey.length == 0) {
            allKeysChosen = true;
        }
        else {
            nrOfJobs = pmKey.length;

            //Validating pmKeys
            for (int i = 0; i < pmKey.length; i++) {
                if (pmKey[i] == null)
                    throw new javax.oss.IllegalArgumentException("pmKey has null value");
            }
        }

        //Validating attribute list
        if (attributes == null)
            throw new javax.oss.IllegalArgumentException();
        else {
            for (int i = 0; i < attributes.length; i++) {
                if (attributes[i] == null)
                    throw new javax.oss.IllegalArgumentException("attribute list contains null value");
            }
        }

        if (attributes.length != 0)
            attributesSet = true;

        // Moved by Katarina
        /*  if (attributesSet == false) {
            String[] tempAttributes = {
            PerformanceMonitorValue.GRANULARITY_PERIOD, PerformanceMonitorValue.NAME,
            PerformanceMonitorValue.REPORT_BY_EVENT, PerformanceMonitorValue.REPORT_BY_FILE,
            PerformanceMonitorValue.REPORT_FORMAT, PerformanceMonitorValue.REPORTING_PERIOD,
            PerformanceMonitorValue.SCHEDULE, PerformanceMonitorValue.STATE,
            ManagedEntityValue.KEY, null, null, null }; // Katarina - null added

            attributes = tempAttributes;
            }*/

        for (int i = 0; i < nrOfJobs || nrOfJobs == 0; i++) {
            jobFound = false;
            for (int j = 0; j < measurementJobs.length; j++) {
                if (allKeysChosen == true || (pmKey[i].getPerformanceMonitorPrimaryKey().compareTo(measurementJobs[j].getPerformanceMonitorKey().getPerformanceMonitorPrimaryKey()) == 0)) {
                    jobFound = true;
                    if (measurementJobs[j] instanceof PerformanceMonitorByClassesValue)
                        pmValue = new PerformanceMonitorByClassesValueImpl();
                    if (measurementJobs[j] instanceof PerformanceMonitorByObjectsValue)
                        pmValue = new PerformanceMonitorByObjectsValueImpl();
                    pmValue.setPerformanceMonitorKey(measurementJobs[j].getPerformanceMonitorKey());

                    if (attributesSet == false) {
                        if (pmValue instanceof PerformanceMonitorByClassesValue) {
                            //Katarina
                            String[] tempAttributes = {
                                PerformanceMonitorValue.GRANULARITY_PERIOD, PerformanceMonitorValue.NAME,
                                PerformanceMonitorValue.REPORT_BY_EVENT, PerformanceMonitorValue.REPORT_BY_FILE,
                                PerformanceMonitorValue.REPORT_FORMAT, PerformanceMonitorValue.REPORTING_PERIOD,
                                PerformanceMonitorValue.SCHEDULE, PerformanceMonitorValue.STATE,
                                ManagedEntityValue.KEY, PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES,
                                PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES, PerformanceMonitorByClassesValue.SCOPE };

                            attributes = tempAttributes;
                        }
                        if (pmValue instanceof PerformanceMonitorByObjectsValue) {
                            String[] tempAttributes = {
                                PerformanceMonitorValue.GRANULARITY_PERIOD, PerformanceMonitorValue.NAME,
                                PerformanceMonitorValue.REPORT_BY_EVENT, PerformanceMonitorValue.REPORT_BY_FILE,
                                PerformanceMonitorValue.REPORT_FORMAT, PerformanceMonitorValue.REPORTING_PERIOD,
                                PerformanceMonitorValue.SCHEDULE, PerformanceMonitorValue.STATE,
                                ManagedEntityValue.KEY, PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES,
                                PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS };

                            attributes = tempAttributes;
                        }
                    }

                    for (int k = 0; k < attributes.length; k++) {
                        if (attributes[k].compareTo(PerformanceMonitorValue.GRANULARITY_PERIOD) == 0)
                            pmValue.setGranularityPeriod(measurementJobs[j].getGranularityPeriod());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.NAME) == 0)
                            pmValue.setName(measurementJobs[j].getName());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_BY_EVENT) == 0)
                            pmValue.setReportByEvent(measurementJobs[j].getReportByEvent());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_BY_FILE) ==  0)
                            pmValue.setReportByFile(measurementJobs[j].getReportByFile());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.REPORT_FORMAT) == 0)
                            pmValue.setReportFormat(measurementJobs[j].getReportFormat());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.REPORTING_PERIOD) == 0) {
                            if (measurementJobs[j].isPopulated(PerformanceMonitorValue.REPORTING_PERIOD) == true)
                                pmValue.setReportPeriod(measurementJobs[j].getReportPeriod());
                        }
                        else if (attributes[k].compareTo(PerformanceMonitorValue.SCHEDULE) == 0)
                            pmValue.setSchedule(measurementJobs[j].getSchedule());
                        else if (attributes[k].compareTo(PerformanceMonitorValue.STATE) == 0) {
                            if (measurementJobs[j].isPopulated(PerformanceMonitorValue.STATE) == true)
                                pmValue.setState(measurementJobs[j].getState());
                        }
                        else if (attributes[k].compareTo(ManagedEntityValue.KEY) == 0)
                            pmValue.setPerformanceMonitorKey(measurementJobs[j].getPerformanceMonitorKey());
                        else {
                            if (pmValue instanceof PerformanceMonitorByClassesValue) {
                                PerformanceMonitorByClassesValue pmCValue = (PerformanceMonitorByClassesValue) pmValue;
                                PerformanceMonitorByClassesValue measurementJobsByClsValue = (PerformanceMonitorByClassesValue) measurementJobs[j];

                                if (attributes[k].compareTo(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES) == 0)
                                    pmCValue.setMeasurementAttributes(measurementJobsByClsValue.getMeasurementAttributes());
                                else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES) == 0)
                                    pmCValue.setObservedObjectClasses(measurementJobsByClsValue.getObservedObjectClasses());
                                // Katarina
                                else if (attributes[k].compareTo(PerformanceMonitorByClassesValue.SCOPE) == 0)
                                    pmCValue.setScope(measurementJobsByClsValue.getScope());
                            }

                            if (pmValue instanceof PerformanceMonitorByObjectsValue) {
                                PerformanceMonitorByObjectsValue pmOValue = (PerformanceMonitorByObjectsValue) pmValue;
                                PerformanceMonitorByObjectsValue measurementJobsByObjValue = (PerformanceMonitorByObjectsValue) measurementJobs[j];

                                if (attributes[k].compareTo(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES) == 0)
                                    pmOValue.setMeasurementAttributes(measurementJobsByObjValue.getMeasurementAttributes());
                                else if (attributes[k].compareTo(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS) == 0)
                                    pmOValue.setObservedObjects(measurementJobsByObjValue.getObservedObjects());
                            }
                        }
                    }
                    performanceMonitorValue.add(pmValue);
                }// for3
                // According to NEC this is wrong performanceMonitorValue.add(pmValue);
            }

            if (allKeysChosen == true)
                nrOfJobs = -1;

            if (jobFound == false)
                throw new javax.ejb.FinderException("job could not be found");
        }

        PerformanceMonitorValue[] performanceMonitorValueArray = new PerformanceMonitorValueImpl[performanceMonitorValue.size()];

        performanceMonitorValueArray = (PerformanceMonitorValue[]) performanceMonitorValue.toArray(performanceMonitorValueArray);
        pmvIterator = new PerformanceMonitorValueIteratorImpl(performanceMonitorValueArray);

        trace.log("<-EisSimulatorImpl.simulatorGetPerformanceMonitors(PerformanceMonitorKey[] pmKey, String[] attributes)", 1);
        return pmvIterator;
    }

    /** @see EisSimulator#simulatorQueryPerformanceMonitors( QueryValue query , String[] attrNames)
     *
     */
    public PerformanceMonitorValueIterator simulatorQueryPerformanceMonitors( QueryValue query , String[] attrNames)
        throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorQueryPerformanceMonitors( QueryValue query , String[] attrNames)", 1);
        PerformanceMonitorValueIterator measurementJobsIterator = null;
        PerformanceMonitorValue[]  jobList = simulatorGetMeasurementJobsList();
        Vector measurementJobsVector = new Vector(0, 1);
        QueryPerformanceMonitorValue queryValue = null;
        String[] supportedQueryTypes = null;
        String interfaceName;
        boolean queryTypeSupported = false;

        //Validating querytype
        supportedQueryTypes = simulatorGetQueryTypes();
        if (query == null)
            query = this.simulatorMakeQueryValue(QueryPerformanceMonitorValue.QUERY_TYPE);

        interfaceName = query.getClass().getInterfaces()[0].getName();

        for (int i = 0; i < supportedQueryTypes.length; i++) {
            if (interfaceName.compareTo(supportedQueryTypes[i]) == 0)
                queryTypeSupported = true;
        }

        if (queryTypeSupported == false)
            throw new javax.oss.IllegalArgumentException();

        queryValue = (QueryPerformanceMonitorValue) query;

        //Validating attribute list
        if (attrNames == null)
            throw new javax.oss.IllegalArgumentException();
        else {
            for (int i = 0; i < attrNames.length; i++) {
                if (attrNames[i] == null)
                    throw new javax.oss.IllegalArgumentException();
            }
        }

        //Variables for deciding which attribute values shall be returned
        boolean granularityPeriod = false;
        boolean measurementName = false;
        boolean valueType = false;
        boolean state = false;
        boolean distinguishedNamesByClasses = false;
        boolean distinguishedNamesByObjects = false;
        boolean key = false;
        boolean reportByEvent = false;
        boolean reportByFile = false;
        boolean reportFormat = false;
        boolean reportingPeriod = false;
        boolean schedule = false;
        boolean measurementAttributesByClasses = false;
        boolean measurementAttributesByObjects = false;

        //Query parameters
        String queryValueType = null;
        String queryMeasurementName = null;
        int queryState = -1;
        int queryGranularityPeriod = -1;
        String[] queryDistinguishedNames = null;

        if (queryValue.isPopulated(QueryPerformanceMonitorValue.VALUE_TYPE) == true)
            queryValueType = queryValue.getValueType();
        if (queryValue.isPopulated(QueryPerformanceMonitorValue.NAME) == true)
            queryMeasurementName = queryValue.getName();
        if (queryValue.isPopulated(QueryPerformanceMonitorValue.STATE) == true)
            queryState = queryValue.getState();
        if (queryValue.isPopulated(QueryPerformanceMonitorValue.GRANULARITY_PERIOD) == true)
            queryGranularityPeriod = queryValue.getGranularityPeriod();
        if (queryValue instanceof QueryByDNValueImpl) {
            QueryByDNValue queryDNValue = (QueryByDNValue) queryValue;

            if (queryDNValue.isPopulated(QueryByDNValue.DISTINGUISHED_NAMES) == true)
                queryDistinguishedNames = queryDNValue.getDistinguishedNames();
        }

        if (attrNames.length == 0) {
            //If the attribute list is an empty list then all attribute values of the measurement jobs are set to true
            granularityPeriod = true;
            measurementName = true;
            valueType = true;
            state = true;
            distinguishedNamesByClasses = true;
            distinguishedNamesByObjects = true;
            key = true;
            reportByEvent = true;
            reportByFile = true;
            reportFormat = true;
            reportingPeriod = true;
            schedule = true;
            measurementAttributesByClasses = true;
            measurementAttributesByObjects = true;
        }
        //If the attribute list is not null then the chosen attribute values are set to true
        else
            for (int i = 0; i < attrNames.length; i++) {

                if (attrNames[i].compareTo(PerformanceMonitorValue.GRANULARITY_PERIOD) == 0) {
                    granularityPeriod = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.NAME) == 0) {
                    measurementName = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.STATE) == 0) {
                    state = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS) == 0) {
                    if (queryValue.isPopulated(QueryPerformanceMonitorValue.VALUE_TYPE) == false || queryValue.getValueType().compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) == 0) {
                        distinguishedNamesByObjects = true;
                    }
                }

                if (attrNames[i].compareTo(PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES) == 0) {
                    if (queryValue.isPopulated(QueryPerformanceMonitorValue.VALUE_TYPE) == false || queryValue.getValueType().compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) == 0) {
                        distinguishedNamesByClasses = true;
                    }
                }

                if (attrNames[i].compareTo(ManagedEntityValue.KEY) == 0) {
                    key = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.REPORT_BY_EVENT) == 0) {
                    reportByEvent = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.REPORT_BY_FILE) == 0) {
                    reportByFile = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.REPORT_FORMAT) == 0) {
                    reportFormat = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.REPORTING_PERIOD) == 0) {
                    reportingPeriod = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorValue.SCHEDULE) == 0) {
                    schedule = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES) == 0) {
                    measurementAttributesByClasses = true;
                }
                if (attrNames[i].compareTo(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES) == 0) {
                    measurementAttributesByObjects = true;
                }
            }

        PerformanceMonitorValue newObject = null;
        boolean queryMatch; //Decides if the current pmValue-object matches the query

        //Creates a vector with query results
        for (int i = 0; i < jobList.length; i++) {
            //Gets next object in the hashtable
            PerformanceMonitorValue pmValue = jobList[i];

            queryMatch = true;

            //Checks if the object in the hashtable matches the values that have
            // been set in the query object
            if (queryValue != null) {
                if (queryValueType != null) {
                    if (pmValue instanceof PerformanceMonitorByClassesValueImpl)
                        if (queryValueType.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) != 0)
                            queryMatch = false;
                    if (pmValue instanceof PerformanceMonitorByObjectsValueImpl)
                        if (queryValueType.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) != 0)
                            queryMatch = false;
                }
                //Hooman
                if (queryDistinguishedNames != null && pmValue != null && pmValue instanceof PerformanceMonitorByObjectsValue ) {
                    PerformanceMonitorByObjectsValue pmValueCast = (PerformanceMonitorByObjectsValue) pmValue;
                    String[] observedObjects = pmValueCast.getObservedObjects();

                    if (observedObjects != null) {
                        boolean dnMatch = false;

                        //VP fix to bugId 5057492
						//for (int j = 0 ; j < observedObjects.length; j++) {
                        //    if (observedObjects[j].startsWith(queryDistinguishedNames[j]) == true)
                        //        dnMatch = true;
                        //}
						for (int j = 0 ; j < observedObjects.length && !dnMatch; j++) {
							for (int k=0; k<queryDistinguishedNames.length && !dnMatch; k++) {
								if (observedObjects[j].startsWith(queryDistinguishedNames[k]))
									dnMatch = true;
							}
						}
						//end VP fix to bugId 5057492

                        if (dnMatch == false)
                            queryMatch = false;
                    }
                    else
                        queryMatch = false;
                }
                if (queryGranularityPeriod != -1)
                    if (queryGranularityPeriod != pmValue.getGranularityPeriod())
                        queryMatch = false;
                if (queryMeasurementName != null) {
                    int qmLastIndex = queryMeasurementName.length() - 1;

                    //Checks if a wildcard is included in the query's measurement name
                    if (queryMeasurementName.charAt(qmLastIndex) == '*') {
                        if (pmValue.getName().startsWith(queryMeasurementName.substring(0, qmLastIndex)) == false)
                            queryMatch = false;
                    }
                    else
                        if (queryMeasurementName.compareTo(pmValue.getName()) != 0)
                            queryMatch = false;
                }
                if (queryState != -1)
                    if (queryState != pmValue.getState())
                        queryMatch = false;
            }

            //If the query matched the object in the hashtable
            //the chosen attribute values will be copied to a new object
            if (queryMatch == true) {
                if (pmValue instanceof PerformanceMonitorByClassesValueImpl) {
                    PerformanceMonitorByClassesValue newObjByClassesValue = new PerformanceMonitorByClassesValueImpl();
                    PerformanceMonitorByClassesValue pmValueCast = (PerformanceMonitorByClassesValue) pmValue;

                    if (distinguishedNamesByClasses == true)
                        newObjByClassesValue.setObservedObjectClasses(pmValueCast.getObservedObjectClasses());

                    if (measurementAttributesByClasses == true)
                        newObjByClassesValue.setMeasurementAttributes(pmValueCast.getMeasurementAttributes());

                    newObject = newObjByClassesValue;
                }

                if (pmValue instanceof PerformanceMonitorByObjectsValueImpl) {
                    PerformanceMonitorByObjectsValue newObjByObjValue = new PerformanceMonitorByObjectsValueImpl();
                    PerformanceMonitorByObjectsValue pmValueCast = (PerformanceMonitorByObjectsValue) pmValue;

                    if (distinguishedNamesByObjects == true)
                        newObjByObjValue.setObservedObjects(pmValueCast.getObservedObjects());

                    if (measurementAttributesByObjects == true)
                        newObjByObjValue.setMeasurementAttributes(pmValueCast.getMeasurementAttributes());

                    newObject = newObjByObjValue;
                }

                if (granularityPeriod == true)
                    newObject.setGranularityPeriod(pmValue.getGranularityPeriod());
                if (measurementName == true)
                    newObject.setName(pmValue.getName());
                if (state == true)
                    newObject.setState(pmValue.getState());
                if (key == true)
                    newObject.setPerformanceMonitorKey(pmValue.getPerformanceMonitorKey());
                if (reportByEvent == true)
                    newObject.setReportByEvent(pmValue.getReportByEvent());
                if (reportByFile == true)
                    newObject.setReportByFile(pmValue.getReportByFile());
                if (reportFormat == true)
                    newObject.setReportFormat(pmValue.getReportFormat());
                if (reportingPeriod == true && pmValue.isPopulated(pmValue.REPORTING_PERIOD))
                    newObject.setReportPeriod(pmValue.getReportPeriod());
                if (schedule == true)
                    newObject.setSchedule(pmValue.getSchedule());

                //Add the new object to vector
                measurementJobsVector.add(newObject);
            }
        }

        //Create an array from the vector
        PerformanceMonitorValue[] measurementJobsArray = new PerformanceMonitorValueImpl[measurementJobsVector.size()];

        measurementJobsArray = (PerformanceMonitorValue[]) measurementJobsVector.toArray(measurementJobsArray);

        //Create an iterator for the array
        measurementJobsIterator = new PerformanceMonitorValueIteratorImpl(measurementJobsArray);

        trace.log("<-EisSimulatorImpl.simulatorQueryPerformanceMonitors( QueryValue query , String[] attrNames)", 1);
        return measurementJobsIterator;
    }

    /** @see EisSimulator#simulatorCreatePerformanceMonitorByValue(PerformanceMonitorValue pmValue)
     *
     */
    public PerformanceMonitorKey simulatorCreatePerformanceMonitorByValue(PerformanceMonitorValue pmValue)
        throws javax.ejb.CreateException, javax.ejb.DuplicateKeyException, javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorCreatePerformanceMonitor(PerformanceMonitorValue pmValue)", 1);
        boolean correctGranularity = false;
        boolean reportByEventSupported = false;
        boolean reportByFileSupported = false;
        int granularityPeriod;
        int reportPeriod;
        int[] supportedGranularities = null;
        int[] supportedReportModes = null;
        PerformanceMonitorKeyImpl pmKey = null;
        ReportFormat reportFormat = null;
        Schedule schedule = null;
        String measurementName = null;

        if (pmValue.isPopulated(PerformanceMonitorValue.NAME) == false)
            trace.log("Creating monitoring job: "+ PerformanceMonitorValue.NAME+" not populated");
        else
            trace.log("Creating monitoring job: " + pmValue.getName());
        //Validating granularity period
        try {
            supportedGranularities = simulatorGetSupportedGranularities(pmValue);
        }
        catch (javax.oss.IllegalArgumentException ie) {
            throw new javax.oss.IllegalArgumentException("Could not get supported granularities :: " + ie);
            //throw new javax.ejb.CreateException("Could not get supported granularities");
        }
        if (pmValue.isPopulated(pmValue.GRANULARITY_PERIOD) == false)
            throw new javax.oss.IllegalArgumentException("Granularity period has not been set");
        granularityPeriod = pmValue.getGranularityPeriod();
        for (int i = 0; i < supportedGranularities.length; i++) {
            if (granularityPeriod == supportedGranularities[i])
                correctGranularity = true;
        }
        if (correctGranularity == false){
            throw new javax.oss.IllegalArgumentException("Chosen granularity period is not supported");
        }
        //Validating key, should not have been set by client
        if (pmValue.isPopulated(pmValue.KEY) == true){
            throw new javax.oss.IllegalArgumentException("Key should not be populated");
        }
        //Validating report mode and sets a default value if not set
        supportedReportModes = simulatorGetReportModes();
        if (pmValue.isPopulated(pmValue.REPORT_BY_EVENT) == false)
            pmValue.setReportByEvent(ReportMode.NO_REPORT_MODE);
        if (pmValue.isPopulated(pmValue.REPORT_BY_FILE) == false) {
            pmValue.setReportByFile(ReportMode.NO_REPORT_MODE);
        }
        if (pmValue.getReportByEvent() == ReportMode.NO_REPORT_MODE)
            reportByEventSupported = true;
        if (pmValue.getReportByFile() == ReportMode.NO_REPORT_MODE) {
            reportByFileSupported = true;
        }
        for (int i = 0; i < supportedReportModes.length; i++) {
            if (pmValue.getReportByEvent() == supportedReportModes[i])
                reportByEventSupported = true;
            if (pmValue.getReportByFile() == supportedReportModes[i])
                reportByFileSupported = true;
        }
        if (reportByEventSupported == false){
            throw new javax.oss.IllegalArgumentException("The chosen value for Report By Event is not supported");
        }

        if (reportByFileSupported == false){
            throw new javax.oss.IllegalArgumentException("The chosen value for Report By File is not supported");
        }
        //Validating report format
        if (pmValue.isPopulated(pmValue.REPORT_FORMAT) == false)
            pmValue.setReportFormat(simulatorGetCurrentReportFormat());
        else {
			// Make sure MULTIPLE ReportMode is not set.  FDP Oct. 3, 2002
			if (pmValue.getReportByEvent() == ReportMode.EVENT_MULTIPLE ||
				pmValue.getReportByFile() == ReportMode.FILE_MULTIPLE) {
					throw new javax.oss.IllegalArgumentException("ReportFormat cannot be specified if ReportMode is *_MULTIPLE");
			}

            reportFormat = pmValue.getReportFormat();
            ReportFormat[] supportedReportFormats = simulatorGetReportFormats();
            boolean reportFormatFound = false;

            for (int i = 0; i < supportedReportFormats.length; i++) {
                if ((reportFormat.getOwner().compareTo(supportedReportFormats[i].getOwner()) == 0)
                    && (reportFormat.getSpecification().compareTo(supportedReportFormats[i].getSpecification()) == 0)
                    && (reportFormat.getTechnology().compareTo(supportedReportFormats[i].getTechnology()) == 0)
                    && (reportFormat.getType() == supportedReportFormats[i].getType())
                    && (reportFormat.getVersion().compareTo(supportedReportFormats[i].getVersion()) == 0))
                    reportFormatFound = true;
            }
            if (reportFormatFound == false){
                throw new javax.oss.IllegalArgumentException("Chosen report format not found");
            }
        }

        //Validating report period
        if (pmValue.getReportByFile() == ReportMode.FILE_SINGLE) {
            if (pmValue.isPopulated(pmValue.REPORTING_PERIOD) == false)
                pmValue.setReportPeriod(1);
        }
        //Validating schedule
        if (pmValue.isPopulated(pmValue.SCHEDULE) == false){
            throw new javax.oss.IllegalArgumentException("Schedule must be set");
        }

        schedule = pmValue.getSchedule();
        Calendar now = Calendar.getInstance();

        if (now.after(schedule.getStartTime()) == true){
            throw new javax.oss.IllegalArgumentException("Start time is not valid");
        }

        if (schedule.getStopTime() != null)
            if (schedule.getStopTime().before(schedule.getStartTime()) == true){
                throw new javax.oss.IllegalArgumentException("Stop time is before start time!");
            }
        if (schedule.getDailySchedule() != null) {
            Calendar[] startTimes = schedule.getDailySchedule().getStartTimes();
            Calendar[] stopTimes = schedule.getDailySchedule().getStopTimes();

            if (startTimes.length == 0 || stopTimes.length == 0){
                throw new javax.oss.IllegalArgumentException("Start- and stoptimes must be set for dailySchedule");
            }

            if (startTimes.length != stopTimes.length){
                throw new javax.oss.IllegalArgumentException("The number of starttimes must be equal to the number of stoptimes");
            }

            for (int i = 0; i < startTimes.length; i++) {

                if (startTimes[i].before(stopTimes[i]) == false){
                    throw new javax.oss.IllegalArgumentException("Every pair of start- and stoptime for dailySchedule must have a starttime which is before its stoptime");
                }

                if (i > 0)
                    if (startTimes[i].after(stopTimes[i - 1]) == false){
                        throw new javax.oss.IllegalArgumentException("Every new starttime must start after the last stoptime");
                    }
            }
        }

        if (pmValue instanceof PerformanceMonitorByObjectsValue) {
            PerformanceMonitorByObjectsValue pmOValue = (PerformanceMonitorByObjectsValue) pmValue;
            //Validating observable objects
            if (pmOValue.isPopulated(pmOValue.OBSERVED_OBJECTS) == false){
                throw new javax.oss.IllegalArgumentException("No observed objects have been set");
            }

            String[] distinguishedNames = pmOValue.getObservedObjects();
            int start = distinguishedNames[0].lastIndexOf(',');
            int stop = distinguishedNames[0].lastIndexOf('=');
            if (stop < 0 || stop < start){
                throw new javax.oss.IllegalArgumentException("Wrong format for observed objects");
            }

            String firstClassName = distinguishedNames[0].substring(start + 1, stop);
            for (int i = 0; i < distinguishedNames.length; i++) {
                start = distinguishedNames[i].lastIndexOf(',');
                stop = distinguishedNames[i].lastIndexOf('=');
                String currentClassName = distinguishedNames[i].substring(start + 1, stop);

                if (currentClassName == null || currentClassName.compareTo(firstClassName) != 0){
                    throw new javax.oss.IllegalArgumentException("Illegal values for observed objects");
                }
            }

            //Validating measurement attributes
            PerformanceAttributeDescriptor[] supportedAttributes = null;

            try {
                supportedAttributes = simulatorGetObservableAttributes(firstClassName);
            }
            catch (javax.oss.IllegalArgumentException ie) {
                throw new javax.ejb.CreateException("Could not get observable attributes");
            }
            PerformanceAttributeDescriptor[] attributes = null;
            PerformanceAttributeDescriptor[] empty = new PerformanceAttributeDescriptorImpl[0];

            if (pmOValue.isPopulated(pmOValue.MEASUREMENT_ATTRIBUTES) == true) {
                attributes = pmOValue.getMeasurementAttributes();
                for (int i = 0; i < attributes.length; i++) {
                    boolean found = false;

                    for (int j = 0; j < supportedAttributes.length; j++) {
                        if (attributes[i].getName().compareTo(supportedAttributes[j].getName()) == 0)
                            found = true;
                    }
                    if (found == false){
                        throw new javax.oss.IllegalArgumentException("Specified measurement attribute not found");
                    }
                }
            }
            else {
                pmOValue.setMeasurementAttributes(empty);
            }

            ((PerformanceMonitorByObjectsValue)pmValue).setMeasurementAttributes(supportedAttributes);
        }

        if (pmValue instanceof PerformanceMonitorByClassesValue) {
            PerformanceMonitorByClassesValue pmCValue = (PerformanceMonitorByClassesValue) pmValue;

            //Validating observable objects
            if (pmCValue.isPopulated(pmCValue.OBSERVABLE_OBJECT_CLASSES) == false){
                throw new javax.oss.IllegalArgumentException("No observable object classes have been set");
            }

            String[] objectClasses = pmCValue.getObservedObjectClasses();
            String firstClassName = objectClasses[0];
            for (int i = 0; i < objectClasses.length; i++) {
                String currentClassName = objectClasses[i];
                if (currentClassName == null || currentClassName.compareTo(firstClassName) != 0){
                    throw new javax.oss.IllegalArgumentException("Illegal values for observable object classes");
                }
            }


            //Validating measurement attributes
            PerformanceAttributeDescriptor[] supportedAttributes = null;

            try {
                supportedAttributes = simulatorGetObservableAttributes(firstClassName);
            }
            catch (javax.oss.IllegalArgumentException ie) {
                throw new javax.ejb.CreateException("Could not get observable attributes");
            }
            PerformanceAttributeDescriptor[] attributes = null;
            PerformanceAttributeDescriptor[] empty = new PerformanceAttributeDescriptorImpl[0];

            if (pmCValue.isPopulated(pmCValue.MEASUREMENT_ATTRIBUTES) == true) {
                attributes = pmCValue.getMeasurementAttributes();
                for (int i = 0; i < attributes.length; i++) {
                    boolean found = false;

                    for (int j = 0; j < supportedAttributes.length; j++) {
                        if (attributes[i].getName().compareTo(supportedAttributes[j].getName()) == 0)
                            found = true;
                    }
                    if (found == false){
                        throw new javax.oss.IllegalArgumentException("Specified measurement attribute not found");
                    }
                }
            }
            else {
                pmCValue.setMeasurementAttributes(empty);
            }

            String scope = null;
            Object obj = null;
            if (pmCValue.isPopulated(pmCValue.SCOPE) == true) {
                obj = (Object) pmCValue.getScope();        //just to be on the safe side
                if(obj != null && obj instanceof String){  //check to see if it is a string.
                    scope = (String) pmCValue.getScope();
                    if (scope.length() == 0 ){
                        throw new javax.oss.IllegalArgumentException("Scope can not be and empty string.");
                    }
                    ObservableObjectIterator ooi = null;
                    String[] values = null;
                    boolean done = true;
                    for(int i = 0; i < objectClasses.length; i++){
                        ooi = simulatorGetObservableObjects(objectClasses[i], scope);
                        values = ooi.getNext(100);
                        if(values == null || values.length == 0) // If you get anything from the
                            // first getNext() it means that scop
                            // is valid, no need to go through
                            // the rest of the values.
                            throw new javax.oss.IllegalArgumentException("Scope has an illegal value.");
                    }
                }
                else{
                    throw new javax.oss.IllegalArgumentException("Scope is either null or not an string value.");
                }
            }
            else {
                throw new javax.oss.IllegalArgumentException("Scope not populated");
            }
        }


        //Validating uniqueness of measurement name, setting default value if not set
        if (pmValue.isPopulated(pmValue.NAME) == false) {
            pmValue.setName("");
        }
        else {
            measurementName = pmValue.getName();
            PerformanceMonitorValue[] measurementJobs = simulatorGetMeasurementJobsList();
            for (int i = 0; i < measurementJobs.length; i++) {
                if (measurementName.compareTo(measurementJobs[i].getName()) == 0){
                    throw new javax.oss.IllegalArgumentException("Measurement name must be unique");
                }
            }
        }

        //Creates new job
        MonitorJob aMonitorJob = new MonitorJob(publisher, pubSession, pmValue, reportInformation,
                                                properties.getProperty("FTP_URL"),
                                                properties.getProperty("PM_DATA_PATH"),
                                                properties.getProperty("TIME_SPEEDUP_FACTOR"),
                                                networkModel);
        pmKey = new PerformanceMonitorKeyImpl();
        String key = (String) pmKey.makePrimaryKey();
        pmKey.setApplicationDN(properties.getProperty("APPLICATION_DN"));

        if (pmValue instanceof PerformanceMonitorByClassesValue) {
            pmKey.setType(PerformanceMonitorByClassesValue.VALUE_TYPE);
        } else{
            pmKey.setType(PerformanceMonitorByObjectsValue.VALUE_TYPE);
        }
        //Checks uniqueness of generated key
        PerformanceMonitorValue[] jobs = simulatorGetMeasurementJobsList();
        if (jobs != null) {
            for (int i = 0; i < jobs.length; i++) {
                if (key.compareTo(jobs[i].getPerformanceMonitorKey().getPerformanceMonitorPrimaryKey()) == 0){
                    throw new javax.ejb.DuplicateKeyException();
                }
            }
        }

        if (jndi_properties != null) {
            ApplicationContext appContext = new ApplicationContextImpl(jndi_properties);
            pmKey.setApplicationContext(appContext);
        }
        pmKey.setPerformanceMonitorPrimaryKey(key);
        pmValue.setPerformanceMonitorKey(pmKey);
        objIdentifier.put(key, aMonitorJob);

        // Start Monitor Job
        aMonitorJob.begin();

        trace.log("<-EisSimulatorImpl.simulatorCreatePerformanceMonitor(PerformanceMonitorValue pmValue)", 1);

        return pmKey;
    }

    /** @see EisSimulator#simulatorGetMeasurementJobsList()
     *
     */
    public PerformanceMonitorValue[] simulatorGetMeasurementJobsList() throws java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetMeasurementJobsList()", 1);
        Vector measurementJobsVector = new Vector(0, 1);
        Enumeration enum = objIdentifier.elements();

        while (enum.hasMoreElements()) {
            MonitorJob monitorJob = (MonitorJob) enum.nextElement();
            PerformanceMonitorValue pmValue = monitorJob.getPerformanceMonitorValue();
            PerformanceMonitorValue pmValueClone = null;

            if (pmValue instanceof  PerformanceMonitorByClassesValue) {
                PerformanceMonitorByClassesValue pmByClassesValue =  (PerformanceMonitorByClassesValue) pmValue;
                pmValueClone =  (PerformanceMonitorValue) pmByClassesValue.clone();
            }


            if (pmValue instanceof  PerformanceMonitorByObjectsValue) {
                PerformanceMonitorByObjectsValue pmByObjectsValue =  (PerformanceMonitorByObjectsValue) pmValue;

                pmValueClone =  (PerformanceMonitorValue) pmByObjectsValue.clone();
            }

            measurementJobsVector.add(pmValueClone);
        }
        PerformanceMonitorValue[] measurementJobsArray = new PerformanceMonitorValueImpl[measurementJobsVector.size()];

        measurementJobsArray = (PerformanceMonitorValue[]) measurementJobsVector.toArray(measurementJobsArray);
        trace.log("<-EisSimulatorImpl.simulatorGetMeasurementJobsList()", 1);
        return measurementJobsArray;
    }



    /** @see EisSimulator#simulatorTryCreatePerformanceMonitorsByValues(PerformanceMonitorValue[] pmValues)
     *
     */
    public PerformanceMonitorKeyResult[] simulatorTryCreatePerformanceMonitorsByValues(PerformanceMonitorValue[] pmValues)
        throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
               javax.ejb.DuplicateKeyException, java.rmi.RemoteException {
                   trace.log("->EisSimulatorImpl.simulatorTryCreatePerformanceMonitors(PerformanceMonitorValue[] pmValues)", 1);

                   if (pmValues == null)
                   throw new javax.oss.IllegalArgumentException("PeformanceMonitorValues are null");

                   PerformanceMonitorKeyResult[] pmKeyResult = new PerformanceMonitorKeyResult[pmValues.length];
                   PerformanceMonitorKey key;

                   for (int i=0; i<pmValues.length; i++ ) {
                       pmKeyResult[i] = new PerformanceMonitorKeyResultImpl();
                       key = null;
                       try {
                           key=this.simulatorCreatePerformanceMonitorByValue(pmValues[i]);
                           pmKeyResult[i].setSuccess(true);
                       }
                       catch (Exception e) {
                           pmKeyResult[i].setException(e);
                       }

                       pmKeyResult[i].setManagedEntityKey(key);

                   }

                   trace.log("<-EisSimulatorImpl.simulatorTryCreatePerformanceMonitors(PerformanceMonitorValue[] pmValues)", 1);

                   return pmKeyResult;
               }

    /** @see EisSimulator#simulatorRemovePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
     *
     */
    public void simulatorRemovePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
        throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, javax.ejb.RemoveException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorRemovePerformanceMonitor(PerformanceMonitorKey pmKey)", 1);

        if (pmKey == null)
            throw new javax.oss.IllegalArgumentException("PeformanceMonitorKey is null");

        String key = pmKey.getPerformanceMonitorPrimaryKey();
        MonitorJob mj = (MonitorJob) objIdentifier.get(key);

        if (mj == null)
            throw new javax.ejb.ObjectNotFoundException();

        mj.quit();
        try {
            objIdentifier.remove(key);
        }
        catch (Exception e) {
            throw new javax.ejb.RemoveException();
        }
        trace.log("<-EisSimulatorImpl.simulatorRemovePerformanceMonitor(PerformanceMonitorKey pmKey)", 1);
    }



    /** @see EisSimulator#simulatorTryRemovePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
     *
     */
    public PerformanceMonitorKeyResult[] simulatorTryRemovePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
        throws javax.oss.UnsupportedOperationException, javax.oss.IllegalArgumentException, java.rmi.RemoteException {
            trace.log("->EisSimulatorImpl.simulatorTryRemovePerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);

            if (pmKeys == null)
            throw new javax.oss.IllegalArgumentException("PerformanceMonitorKey[] is null");

            PerformanceMonitorKeyResult[] pmKeyResult = new PerformanceMonitorKeyResult[pmKeys.length];

            for (int i=0; i<pmKeys.length; i++ ) {
                pmKeyResult[i] = new PerformanceMonitorKeyResultImpl();

                try {

                    this.simulatorRemovePerformanceMonitorByKey(pmKeys[i]);
                    pmKeyResult[i].setSuccess(true);

                }
                catch (Exception e) {
                    pmKeyResult[i].setException(e);
                }

                pmKeyResult[i].setManagedEntityKey(pmKeys[i]);

            }

            trace.log("<-EisSimulatorImpl.simulatorTryRemovePerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);
            return pmKeyResult;
        }


    /** @see EisSimulator#simulatorSuspendPerformanceMonitorByKey(PerformanceMonitorKey pmKey)
     *
     */
    public void simulatorSuspendPerformanceMonitorByKey(PerformanceMonitorKey pmKey)
        throws javax.oss.UnsupportedOperationException, javax.ejb.ObjectNotFoundException,
               java.rmi.RemoteException, javax.oss.IllegalArgumentException {
        trace.log("->EisSimulatorImpl.simulatorSuspendPerformanceMonitor(PerformanceMonitorKey pmKey)", 1);

        if (pmKey == null)
            throw new javax.oss.IllegalArgumentException("Key has null value.");

        String key = pmKey.getPerformanceMonitorPrimaryKey();

        MonitorJob monitorJob = (MonitorJob) objIdentifier.get(key);

        if (monitorJob == null)
            throw new javax.ejb.ObjectNotFoundException("Could not found a monitor job with the supplied key.");
        monitorJob.suspend();

        trace.log("<-EisSimulatorImpl.simulatorSuspendPerformanceMonitor(PerformanceMonitorKey pmKey)", 1);
    }


    /** @see EisSimulator#simulatorTrySuspendPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
     *
     */
    public PerformanceMonitorKeyResult[] simulatorTrySuspendPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
        throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
               java.rmi.RemoteException {
                   trace.log("->EisSimulatorImpl.simulatorTrySuspendPerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);


                   if (pmKeys == null)
                   throw new javax.oss.IllegalArgumentException("PerformanceMonitorKey[] is null");

                   PerformanceMonitorKeyResult[] pmKeyResult = new PerformanceMonitorKeyResult[pmKeys.length];

                   for (int i=0; i<pmKeys.length; i++ ) {
                       pmKeyResult[i] = new PerformanceMonitorKeyResultImpl();

                       try {
                           this.simulatorSuspendPerformanceMonitorByKey(pmKeys[i]);
                           pmKeyResult[i].setSuccess(true);
                       }
                       catch (Exception e) {
                           pmKeyResult[i].setException(e);
                       }

                       pmKeyResult[i].setManagedEntityKey(pmKeys[i]);

                   }


                   trace.log("<-EisSimulatorImpl.simulatorTrySuspendPerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);
                   return pmKeyResult;
               }

    /** @see EisSimulator#simulatorResumePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
     *
     */

    public void simulatorResumePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
        throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
               javax.ejb.ObjectNotFoundException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorResumePerformanceMonitor(PerformanceMonitorKey pmKey)", 1);

        if (pmKey == null)
            throw new javax.oss.IllegalArgumentException("Key has null value.");

        String key = pmKey.getPerformanceMonitorPrimaryKey();

        MonitorJob monitorJob = (MonitorJob) objIdentifier.get(key);

        if (monitorJob == null)
            throw new javax.ejb.ObjectNotFoundException("Could not found a monitor job with the supplied key.");
        monitorJob.resume();

        trace.log("<-EisSimulatorImpl.simulatorResumePerformanceMonitor(PerformanceMonitorKey pmKey)", 1);
    }

    /** @see EisSimulator#simulatorTryResumePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
     *
     */
    public PerformanceMonitorKeyResult[] simulatorTryResumePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
        throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
               java.rmi.RemoteException {
                   trace.log("->EisSimulatorImpl.simulatorTryResumePerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);

                   if (pmKeys == null)
                   throw new javax.oss.IllegalArgumentException("PerformanceMonitorKey[] is null");

                   PerformanceMonitorKeyResult[] pmKeyResult = new PerformanceMonitorKeyResult[pmKeys.length];

                   for (int i=0; i<pmKeys.length; i++ ) {
                       pmKeyResult[i] = new PerformanceMonitorKeyResultImpl();

                       try {
                           this.simulatorResumePerformanceMonitorByKey(pmKeys[i]);
                           pmKeyResult[i].setSuccess(true);
                       }
                       catch (Exception e) {
                           pmKeyResult[i].setException(e);
                       }

                       pmKeyResult[i].setManagedEntityKey(pmKeys[i]);

                   }

                   trace.log("<-EisSimulatorImpl.simulatorTryResumePerformanceMonitors(PerformanceMonitorKey[] pmKeys)", 1);
                   return pmKeyResult;
               }

    /** @see EisSimulator#simulatorGetCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)
     *
     */
    public CurrentResultReport simulatorGetCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)
        throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.oss.IllegalStateException,
               javax.ejb.ObjectNotFoundException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorGetCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)", 1);

        if ( pmKey == null )
            throw new javax.oss.IllegalArgumentException("Key is null");

        String key = pmKey.getPerformanceMonitorPrimaryKey();

        MonitorJob monitorJob = (MonitorJob) objIdentifier.get(key);

        if (monitorJob == null)
            throw new javax.ejb.ObjectNotFoundException("Could not find monitor job");

        if ( monitorJob.getPerformanceMonitorValue().getState() == PerformanceMonitorState.SUSPENDED )
            throw new javax.oss.IllegalStateException("Monitor job is suspended");

        if ( format.getType() != ReportFormat.XML )
            throw new javax.oss.IllegalArgumentException("Reportformat not supported");

        // Get a snapshot from the monitorjob.
        String report = monitorJob.getCurrentResultReport();

        ReportData reportData = new ReportDataImpl(report, new ReportFormatImpl());
        ReportInfo reportInformation = new ReportInfoImpl();
        reportInformation.setExpirationDate(null);
        reportInformation.setReportFormat(new ReportFormatImpl());
        reportInformation.setURL(null);
        CurrentResultReport currentReport = new CurrentResultReportImpl(reportInformation, reportData);

        trace.log("<-EisSimulatorImpl.simulatorGetCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)", 1);
        return currentReport;
    }

    /** @see EisSimulator#simulatorMakeManagedEntityValue(String valueType)
     *
     */
    public ManagedEntityValue simulatorMakeManagedEntityValue(String valueType) throws javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorMakeManagedEntityValue(String valueType)", 1);
        trace.log("<-EisSimulatorImpl.simulatorMakeManagedEntityValue(String valueType)", 1);
        return simulatorMakePerformanceMonitorValue(valueType);

    }

    /** @see EisSimulator#simulatorQueryManagedEntities(QueryValue query, String[] attrNames)
     *
     */
    public ManagedEntityValueIterator simulatorQueryManagedEntities(QueryValue query, String[] attrNames) throws
    javax.oss.IllegalArgumentException, java.rmi.RemoteException {
        trace.log("->EisSimulatorImpl.simulatorQueryManagedEntities(QueryValue query, String[] attrNames)", 1);
        ManagedEntityValueIterator iterator = null;

        iterator = simulatorQueryPerformanceMonitors(query, attrNames);
        trace.log("<-EisSimulatorImpl.simulatorQueryManagedEntities(QueryValue query, String[] attrNames)", 1);
        return iterator;
    }
}
