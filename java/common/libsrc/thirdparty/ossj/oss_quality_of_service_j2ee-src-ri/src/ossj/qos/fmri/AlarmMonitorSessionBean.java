package ossj.qos.fmri;

import java.util.Properties;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJBException;

import java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.SimpleTimeZone;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import javax.ejb.SessionBean;
import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import javax.ejb.ObjectNotFoundException;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

import javax.oss.*;
import javax.oss.fm.monitor.*;
import javax.jms.*;
import ossj.qos.util.Util;
import ossj.qos.util.Trace;

//for sunmc adapter
import java.rmi.*;

/**
 * AlarmMonitorSessionBean
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * ¨ Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class AlarmMonitorSessionBean implements SessionBean, EnvironmentConstants {

    private SessionContext ctx = null;
    private InitialContext initCtx = null;

    private AMEventPublisher evPublisher = null;
    private DataSource dataSource = null;
    private Connection connection = null;
    private boolean isLoggingEnabled = true;
    private Trace myLog = null;

    private String[] compVersions = null;

    /**
     * Environment variable that determines if the application dn
     * is populated in the events and if the application context info is
     * populated in the alarm keys
     */
    private boolean includeApplicationInfo = true;

    private String applicationDN = null;

    private ApplicationContext applicationContext = null;

    //private SunMCAdapter sunmcAdapter = null;

    /**
     * AlarmMonitorSessionBean  - default constructor
     */
    public AlarmMonitorSessionBean() {
    }

    //-----------------------------------------------------------------------------
    //
    // Container callbacks/methods
    //
    //-----------------------------------------------------------------------------
    public void ejbCreate() {
	System.out.println("=========>ejbCreate() AlarmMonitorSessionBean");

        // creates the log
        myLog = Util.createLog("ossj.qos.fmri");

        // Removed by Stefan
        //Properties env = System.getProperties();

        try {
            // Changed by Stefan 
            //initCtx = new InitialContext( env );
            //to
            initCtx = new InitialContext( );
        }
        catch (NamingException nex) {
            if ( isLoggingEnabled ) {
                myLog.logException ("AlarmMonitorSessionBean:ejbCreate:  Naming exception caught while creating InitialContext", nex);
            }
        }

        Object result;
        // initialize debugLogEnabled from environment property
        // TURN_ON_DEBUG_LOGGING
        try {
            result = initCtx.lookup( TURN_ON_DEBUG_LOGGING );
            Boolean booleanValue = (Boolean) PortableRemoteObject.narrow(result,Boolean.class);
            isLoggingEnabled = booleanValue.booleanValue();
        } catch ( NamingException e ) {
            if ( isLoggingEnabled ) {
                myLog.log("AlarmMonitorSessionBean: ejbCreate(): unable to load " +
                TURN_ON_DEBUG_LOGGING + " property." );
            }
        }

        if ( isLoggingEnabled ) {
            myLog.log ("AlarmMonitorSessionBean:ejbCreate");
        }

        // initialize includeApplicationInfo from environment property
        // INCLUDE_APPLICATION_INFO_PROPERTY_NAME
        try {
            result = initCtx.lookup( INCLUDE_APPLICATION_INFO_PROPERTY_NAME );
            Boolean booleanValue = (Boolean) PortableRemoteObject.narrow(result,Boolean.class);
            includeApplicationInfo = booleanValue.booleanValue();
        } catch ( NamingException e ) {
            if ( isLoggingEnabled ) {
                myLog.log("AlarmMonitorSessionBean: ejbCreate(): unable to load " +
                INCLUDE_APPLICATION_INFO_PROPERTY_NAME + " property." );
            }
        }

        initializeApplicationContext();

        try {
			//VP
			myLog.log("Look up JDBC for Alarm: "+SessionResourceConstants.FMDB);
            result = initCtx.lookup( SessionResourceConstants.FMDB );

            dataSource = (javax.sql.DataSource) PortableRemoteObject.narrow(result,DataSource.class);
        }
        catch ( NamingException nex2 ) {
            if ( isLoggingEnabled ) {
                myLog.logException ("AlarmMonitorSessionBean:ejbCreate: Naming exception while acquiring db pool", nex2 );
            }
        }

        // create the event publisher...
        evPublisher = new AMEventPublisher();

        try {
            /*  Trace logger = null;
            if ( isLoggingEnabled ) {
            logger = myLog;
            } */
            evPublisher.init( initCtx, SessionResourceConstants.AM_TOPIC,
            SessionResourceConstants.AM_CONNECTION_FACTORY );
        }
        catch( Exception x ) {
            if ( isLoggingEnabled ) {
                myLog.logException("AlarmMonitorSessionBean:ejbCreate:  Unable to create Publisher.", x );
            }
            evPublisher = null;
        }

        compVersions = new String[1];
        compVersions[0] = SessionResourceConstants.CURRENT_VERSION;

//	initSunMCAdapter();

        if ( isLoggingEnabled ) {
            myLog.log( "AlarmMonitorSessionBean:ejbCreate" );
        }
	System.out.println("<=========ejbCreate() AlarmMonitorSessionBean");

        return;
    }

    public void setSessionContext(final javax.ejb.SessionContext ctx) throws javax.ejb.EJBException, java.rmi.RemoteException {
        this.ctx = ctx;
        return;
    }

    public void ejbRemove() throws javax.ejb.EJBException, java.rmi.RemoteException {

        if ( isLoggingEnabled ) {
            myLog.log( "AlarmMonitorSessionBean:ejbRemove" );
        }
        try {
            initCtx.close();
        }
    catch (NamingException nex) {}
        try {
            evPublisher.close();
        }
    catch( Exception e1 ) {}
        return;
    }

    public void ejbActivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
        if ( isLoggingEnabled ) {
            myLog.log("AlarmMonitorSessionBean:ejbActivate");
        }
        return;
    }

    public void ejbPassivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
        if ( isLoggingEnabled ) {
            myLog.log("AlarmMonitorSessionBean:ejbPassivate");
        }
        return;
    }

    /*public void initSunMCAdapter()
    {
	if( sunmcAdapter!= null) return;

	//create sunmc adapter
	if (System.getSecurityManager() == null) {
		System.setSecurityManager(new RMISecurityManager());
    	}
    	try {
      		sunmcAdapter = (SunMCAdapter) java.rmi.Naming.lookup("//localhost/SunMCAdapter");
		System.out.println("Connect to SunMC Adapter " + sunmcAdapter.getVersion()); 
    	}
    	catch (Exception e) {
		System.out.println("failed to init sunmc adapter");
		e.printStackTrace();
    	}
    }*/

    /**
     * Get the supported optional operations.
     *
     * <p>
     * Some of the operations on the alarm monitor bean are optional.
     * This operation gives information on which optional operations
     * are implemented and will not throw UnsupportedOperationException.
     *
     * <p><ul>
     * The following operation prototypes can be returned:
     * <li> BadAlarmReferenceValue[] unacknowledgeAlarms( String[], String ackUserId, String ackSystemId )
     * </ul>
     *
     * @return String[] List of operation names that will not throw
     * UnsupportedOperationException, if they are called.
     */
    public String[] getSupportedOptionalOperations() {
        return SessionResourceConstants.supportedOptionalOperations;
    }

    /**
     * Get the Managed Entity types supported by a JVT Session Bean
     *
     * @return String array which contains the fully qualified names of the leaf
     * node interfaces representing the supported managed entity types.
     * Note that it is not the implementation class name that is returned.
     */
    public String[] getManagedEntityTypes() {
        return SessionResourceConstants.managedEntityTypes;
    }

    /**
     * Get query types supported by the session component.
     *
     * All implementations must support at least query type
     * AlarmByFilterableAlarmAttrib.
     */
    public String[] getQueryTypes() {
        return SessionResourceConstants.queryTypes;
    }

    /**
     * Get the event type names supported by the session component.
     * <p>
     * All implementations must support at least event types
     * AlarmEventPropertyDesciptor.EVENT_TYPE_VALUE and
     * AlarmListRebuildEventPropertyDesciptor.EVENT_TYPE_VALUE.
     */
    public String[] getEventTypes() {
        return EventFactory.getEventTypes();
    }

    /**
     * Create a Query Value instance matching a Query type.
     * Valid input parameters are returned by getQueryTypes().
     * @exception javax.oss.IllegalArgumentException - Is raised when a bad argument is provided.
     */
    public javax.oss.QueryValue makeQueryValue(String type)
    throws javax.oss.IllegalArgumentException {
        QueryValue query = null;
        try{
        if ( type.equals( QueryByFilterableAttributesValue.QUERY_TYPE ) == false ) {
          throw new javax.oss.IllegalArgumentException( "Invalid query type entered: " + type  );
        }
        else {

            query = new QueryByFilterableAttributesValueImpl();

        }
        }catch(Exception e){
          System.out.println(e.getMessage());
        }

        return query;
    }

    /**
     * Get the Event Descriptor associated with an event type
     * Valid input parameters are returned by getEventTypes()
     * @exception javax.oss.UnsupportedOperationException - Is raised if this operation is not supported.
     */
    public javax.oss.EventPropertyDescriptor getEventDescriptor(String event_type)
    throws javax.oss.IllegalArgumentException {
        EventPropertyDescriptor evDescriptor = null;
        try {
            evDescriptor = EventFactory.getPropertyDescriptor( event_type );
        }
        catch ( java.lang.IllegalArgumentException iex ) {
            throw new javax.oss.IllegalArgumentException( "No property descriptor found for event type: " + event_type );
        }
        return evDescriptor;
    }

    /**
     * Create a Value Type object for a specific Managed Entity type.
     * Not to be confused with the creation of a Managed Entity.
     * The Session Bean is used as a factory for the creation of
     * Value types.
     *
     * @param valueType fully qualified name of the leaf managed entity value interface
     * @return Implementation class of a managed entity of a specific type
     * @exception javax.oss.IllegalArgumentException
     * @exception javax.ejb.CreateException
     */

    public ManagedEntityValue makeManagedEntityValue( String valueType)
    throws javax.oss.IllegalArgumentException {
        ManagedEntityValue entity = null;
        if ( valueType.equals( AlarmValue.VALUE_TYPE ) == false ) {
            throw new javax.oss.IllegalArgumentException("Invalid entity type: " + valueType );
        }
        else {
            entity = new AlarmValueImpl();
        }
        return entity;
    }

    /**
     * Query Multiple Managed Entities using a QueryValue
     *
     * @param query A QueryValue object representing the Query.
     * @param attrNames An array of strings representing the attribute names
     * @return ManagedEnityValueIterator used to extract the results of the Query.
     * @exception javax.oss.IllegalArgumentException
     */
    public ManagedEntityValueIterator
    queryManagedEntities( QueryValue query , String[] attrNames)
    throws javax.oss.IllegalArgumentException {

        if (isLoggingEnabled) {
            Object[][] parms = {  { "query[]", query },
                                  { "attributes", attrNames } };
            myLog.logMethodEntry( "queryManagedEntities", parms );
        }

        ManagedEntityValueIterator iter = null;

        // check for null arguments
        if ( query == null || attrNames == null ) {
            throw new javax.oss.IllegalArgumentException( "queryManagedEntities: Null argument entered" );
        }
        else {
            QueryValue[] queries = { query };
            iter = queryAlarmList( queries, attrNames );
        }

        return iter;
    }

    /**
     * Acknowledges one or more alarms in server's Alarm List.
     * <p>
     * The function registers the time of operation in ackTime
     * in Alarm in Alarm List.  It also registers ackUserId
     * in Alarm. It sets ackState to acknowledged as well.
     * <p>
     * The ackTime, ackUserId and ackState are
     * collectively called Acknowledgement Information.
     * <p>
     * As a result of this operation the server will emit
     * message about Acknowledgement Information.
     * <p>
     * Note that the time of acknowledgement is not parameter
     * in this function. The internal time of the server is
     * used when the function call is made instead.
     * <p>
     * If client needs to ack lots of alarms it is recommended
     * to call this method several times instead of passing
     * very long alarm reference list in one function call.
     *
     * <p>
     * If the ackSystemId is not supported, see {@link AlarmValue}, by the system then
     * the ackSystemId parameter is ignored.
     *
     * @see AlarmKeyResult
     * @param alarmReferenceList A array of alarm ids (AlarmKeys) to acknowledge
     * Alarms in Alarm List.
     * @param ackUserId A String representing the user id.
     * @param ackSystemId A String representing the system id of the user who
     * acknowledged the alarms, NULL denotes that the parameter is not used.
     * @return <code>AlarmKeyResult[]</code> - Bad alarm reference (AlarmKey)
     * list, if the list is empty, the call has totally succeeded.
     * It identifies Alarms that are not present in Alarm List
     * or that they are present, but Acknowledgement Information
     * has not changed, in contrast to clients request.  Element of
     * this list is a pair of Alarm Reference (AlarmKey) and reason.
     * @exception javax.oss.IllegalArgumentException Is raised when a
     * bad argument is provided
     */
    public AlarmKeyResult[] tryAcknowledgeAlarms(
    AlarmKey[] alarmReferenceList,
    String   ackUserId,
    String   ackSystemId )
    throws javax.oss.IllegalArgumentException {

        if (isLoggingEnabled) {
            Object[][] parms = {  { "alarmReferenceList", alarmReferenceList },
                                  { "ackUserId", ackUserId },
                                  { "ackSystemId", ackSystemId } };
            myLog.logMethodEntry( "tryAcknowledgeAlarms", parms );
        }
//VP: System.out.println("A");
        // check to make sure all arguments are present
        if ( alarmReferenceList == null || alarmReferenceList.length == 0 || ackUserId == null ) {
            throw new javax.oss.IllegalArgumentException( "Invalid argument value entered." );
        }
        else {
            for ( int i=0,len=alarmReferenceList.length; i<len; i++ ) {
                if ( alarmReferenceList[i].getAlarmPrimaryKey() == null ||
                alarmReferenceList[i].getAlarmPrimaryKey().trim().length() == 0 ) {
                    throw new javax.oss.IllegalArgumentException( "Missing primary key : for key [ " + i + " ]" );
                }
            }
        }
//VP: System.out.println("B");
        ArrayList resultList = new ArrayList();

        QuerySingleUpdateDBCommandImpl dbCmdQuery = null;
        SetDBCommandImpl dbCmdSet = null;


        // Configure a query database command
        dbCmdQuery = (QuerySingleUpdateDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.QUERY_SINGLE_UPDATE_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdQuery.setLogger(myLog);
        }

        // Configure the whereClause Template
        AlarmValue queryAlarmTemplate = (AlarmValue) new AlarmValueImpl();
        AlarmKey queryKey = queryAlarmTemplate.makeAlarmKey();
        queryAlarmTemplate.setAlarmKey( queryKey );
//VP: System.out.println("C");
        // set the query command attributes
        dbCmdQuery.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdQuery.setWhereClauseTemplate( queryAlarmTemplate );

        // Create the set database command
        dbCmdSet = (SetDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.SET_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdSet.setLogger(myLog);
        }

        // Create the update template for the db command and set the appropriate values
        AlarmValue setAlarmTemplate = new AlarmValueImpl();
        // set the ackTime for all the alarms to ack
        Date ackTime = new Date();
        setAlarmTemplate.setAckUserId( ackUserId );
        setAlarmTemplate.setAckSystemId( ackSystemId );
        setAlarmTemplate.setAckTime( ackTime );
        setAlarmTemplate.setAlarmAckState( AlarmAckState.ACKNOWLEDGED );
        // set with dummy value so the template so that UPDATE SQL will
        // contain an entry for notification id
        setAlarmTemplate.setNotificationId( "DUMMY" );
//VP: System.out.println("D");
        // build where clause template for the set database command
        AlarmValue setWhereTemplate = new AlarmValueImpl();
        AlarmKey setKey = setWhereTemplate.makeAlarmKey();
        setWhereTemplate.setAlarmKey( setKey );
        setWhereTemplate.setAlarmAckState( AlarmAckState.UNACKNOWLEDGED );

        // Configure the db set command with the templates and reuse
        dbCmdSet.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdSet.setUpdateTemplate( setAlarmTemplate );
        dbCmdSet.setWhereClauseTemplate( setWhereTemplate );

        ArrayList ackAlarmList = new ArrayList();

        try {
//VP: System.out.println("E");
            // set the connection and initialize the db commands
            dbCmdQuery.setConnection( getConnection() );
            dbCmdQuery.initializeCmd();
            dbCmdSet.setConnection( getConnection() );
            dbCmdSet.initializeCmd();
//VP: System.out.println("F");
            // Query and ack each alarm in the alarm reference list
            for ( int i=0, len=alarmReferenceList.length; i<len; i++ ) {
                // modify the alarm key (id) in the update template
                queryKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );

                // execute the query
                dbCmdQuery.executeCmd();
                ManagedEntityValue[] entities = (ManagedEntityValue[]) dbCmdQuery.getResult();

                if ( entities == null || entities.length == 0 ) {

                    // if alarm not found, create a alarm result and indicate no alarm found
                    AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                    false,
                    new ObjectNotFoundException ("alarm not found" ) );
                    resultList.add( result );
//VP: System.out.println("G");
                }
                else {

                    AlarmValue queriedAlarm = (AlarmValue) entities[0];
                    // check if already acked
                    if ( queriedAlarm.getAlarmAckState() == AlarmAckState.UNACKNOWLEDGED ) {
                        // get a notification id that will be used in the NotifyAckStateChangedEvent
                        String notId = genNotificationId();
                        setAlarmTemplate.setNotificationId( notId );
                        // set the alarm key (id) in the set where template
                        setKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );
                        // execute the set
                        dbCmdSet.executeCmd();
                        Integer setResult = (Integer)dbCmdSet.getResult();
                        // check if the set was successfull
                        if ( setResult.intValue() == 1 ) {
                            // if acked, populate the queried representation of the alarm with the
                            // updated ack attributes and place in a list that will be used to
                            // generate NotifyAckStateChangedEvents
                            populateWithNewAckNackAttributes( queriedAlarm, setAlarmTemplate );
                            ackAlarmList.add( queriedAlarm );
//VP: System.out.println("H");
                        } // end alarm successfully acked
                        else {
                            // already acked, create alarm result and indicate already acked
                            AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                            false,
                            new java.lang.IllegalArgumentException ("alarm already acked") );
                            resultList.add( result );
//VP: System.out.println("I");
                        } // end alarm already acked
                    } // end alarm unacknowledged
                    else {
                        // already acked, create alarm result and indicate already acked
                        AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                        false,
                        new java.lang.IllegalArgumentException("alarm already acked" ) );
                        resultList.add( result );
//VP: System.out.println("J");
                    } // end else alarm already acked
                } // end else alarm found
            } // end for querying and acking alarms in alarmReferenceList
            // delete all acked and cleared alarms
            deleteClearedAckedAlarms();
//VP: System.out.println("K");
        } // end of try statement
        catch ( SQLException sqe ) {
            throw new EJBException( sqe );
        }
        finally {
            // close the db commands to free resources
            dbCmdSet.closeCmd();
            dbCmdQuery.closeCmd();
            // close the connection.
            closeConnection();
//VP: System.out.println("L");
        }
        // generate events for all acked alarms
        generateAlarmEvents( NotifyAckStateChangedEventPropertyDescriptor.EVENT_TYPE_VALUE,
        (AlarmValue[]) ackAlarmList.toArray( new AlarmValue[0] ), ackTime );
//VP: System.out.println("M");
//VP: System.out.println("result list size: " + resultList.size());
//VP: System.out.println("result list is: " + resultList.toArray().toString());
        return (AlarmKeyResult[]) resultList.toArray( new AlarmKeyResult[0] );
    }

    /**
     * Removes acknowledgement information from one or
     * more alarms in server's Alarm List.
     * <p>
     * The function clears ackTime in Alarm in Alarm List.
     * It also clears ackUserId in Alarm.
     * It sets ackState to unacknowledged as well.
     * <p>
     * The ackTime, ackUserId and ackState are collectively
     * called Acknowledgement Information.
     * <p>
     * As a result of this operation the server will emit
     * message about Acknowledgement Information, where
     * ackState is set "unacknowledged".
     * <p>
     * If client needs to ack lots of alarms it is recommended
     * to call this method several times instead of passing
     * very long alarm reference list in one function call.
     *
     * <p>
     * If the ackSystemId is not supported, see {@link AlarmValue}, by the system then
     * the ackSystemId parameter is ignored.
     *
     * @see AlarmKeyResult
     * @param alarmReferenceList A array of alarm ids (AlarmKeys) to unacknowledge
     * Alarms in Alarm List.
     * @param ackUserId A String representing the user id.
     * @param ackSystemId A String representing the system id of the user who
     * acknowledged the alarms, NULL denotes that the parameter is not used.
     * @return <code>AlarmKeyResult[]</code> - Bad alarm reference (alarmID)
     * list, if the list is empty, the call has totally succeeded.
     * It identifies Alarms that are not present in Alarm List or
     * that they are present, but Acknowledgement Information has
     * not changed, in contrast to clients request.  Element of this
     * list is a pair of Alarm Reference (AlarmKey) and reason.
     * @exception javax.oss.UnsupportedOperationException Is raised
     * if this operation is not supported.
     * @exception javax.oss.IllegalArgumentException Is raised when a
     * bad argument is provided.
     */
    public AlarmKeyResult[] tryUnacknowledgeAlarms(
    AlarmKey[] alarmReferenceList,
    String   ackUserId,
    String   ackSystemId )
    throws javax.oss.IllegalArgumentException {

        if (isLoggingEnabled) {
            Object[][] parms = {  { "alarmReferenceList", alarmReferenceList },
                                  { "ackUserId", ackUserId },
                                  { "ackSystemId", ackSystemId } };
            myLog.logMethodEntry( "tryUnacknowledgeAlarms", parms );
        }

        // check to make sure all arguments are present
        if ( alarmReferenceList == null || alarmReferenceList.length == 0 || ackUserId == null ) {
            throw new javax.oss.IllegalArgumentException( "Invalid argument value entered." );
        }
        else {
            for ( int i=0,len=alarmReferenceList.length; i<len; i++ ) {
                if ( alarmReferenceList[i].getAlarmPrimaryKey() == null ||
                alarmReferenceList[i].getAlarmPrimaryKey().trim().length() == 0 ) {
                    throw new javax.oss.IllegalArgumentException( "Missing primary key : for key [ " + i + " ]" );
                }
            }
        }

        ArrayList resultList = new ArrayList();

        QuerySingleUpdateDBCommandImpl dbCmdQuery = null;
        SetDBCommandImpl dbCmdSet = null;

        // Configure a query database command
        dbCmdQuery = (QuerySingleUpdateDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.QUERY_SINGLE_UPDATE_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdQuery.setLogger(myLog);
        }

        // Configure the whereClause Template
        AlarmValue queryAlarmTemplate = (AlarmValue) new AlarmValueImpl();
        AlarmKey queryKey = queryAlarmTemplate.makeAlarmKey();
        queryAlarmTemplate.setAlarmKey( queryKey );

        // set the query command attributes
        dbCmdQuery.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdQuery.setWhereClauseTemplate( queryAlarmTemplate );

        // Create the set database command
        dbCmdSet = (SetDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.SET_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdSet.setLogger(myLog);
        }

        // set the nackTime that represents when the alarms were nacked
        // String nackTime = getCurrentUTCTimeString();
        Date nackTime = new Date();
        // Create the update template for the db command and set the appropriate values
        AlarmValue setAlarmTemplate = new AlarmValueImpl();
        setAlarmTemplate.setAckUserId( null );
        setAlarmTemplate.setAckSystemId( null );
        setAlarmTemplate.setAckTime( null );
        setAlarmTemplate.setAlarmAckState( AlarmAckState.UNACKNOWLEDGED );
        // set with dummy value so that UPDATE SQL will
        // contain an entry for notification id
        setAlarmTemplate.setNotificationId( "DUMMY" );

        // build where clause template for the set database command
        AlarmValue setWhereTemplate = new AlarmValueImpl();
        AlarmKey setKey = setWhereTemplate.makeAlarmKey();
        setWhereTemplate.setAlarmKey( setKey );

        // Configure the db set command with the templates and reuse
        dbCmdSet.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdSet.setUpdateTemplate( setAlarmTemplate );
        dbCmdSet.setWhereClauseTemplate( setWhereTemplate );

        ArrayList ackAlarmList = new ArrayList();

        try {
            // set the connection and initialize the db commands
            dbCmdQuery.setConnection( getConnection() );
            dbCmdQuery.initializeCmd();
            dbCmdSet.setConnection( getConnection() );
            dbCmdSet.initializeCmd();

            // Query and ack each alarm in the alarm reference list
            for ( int i=0, len=alarmReferenceList.length; i<len; i++ ) {
                // modify the alarm key (id) in the update template
                queryKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );

                // execute the query
                dbCmdQuery.executeCmd();

                ManagedEntityValue[] entities = (ManagedEntityValue[]) dbCmdQuery.getResult();

                if ( entities == null || entities.length == 0 ) {

                    // if alarm not found, create a alarm result and indicate no alarm found
                    AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                    false,
                    new ObjectNotFoundException("alarm not found") );
                    resultList.add( result );
                }
                else {

                    AlarmValue queriedAlarm = (AlarmValue) entities[0];
                    // check if already acked
                    String queriedAckSystemId = queriedAlarm.getAckSystemId();
                    if ( queriedAlarm.getAlarmAckState() == AlarmAckState.ACKNOWLEDGED ) {
                        // check if the supplied userid/systemid equals the stored acked userid/systemid
                        if ( queriedAlarm.getAckUserId().equals( ackUserId ) == false ||
                        Util.isEqual( queriedAckSystemId, ackSystemId ) == false ) {
                            // ackUserId or ackSystemId != queried alarm's ackUserId or ackSystemId
                            AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                            false,
                            new java.lang.IllegalArgumentException("Invalid userid/systemId entered. Alarm acked by userId: " +
                            queriedAlarm.getAckUserId() + " systemId: " + queriedAlarm.getAckSystemId() ) );
                            resultList.add( result );
                        }
                        else {
                            // get a notification id that will be used in the NotifyAckStateChangedEvent
                            String notId = genNotificationId();
                            setAlarmTemplate.setNotificationId( notId );
                            // set the alarm key (id) in the set where template
                            setKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );
                            // execute the set
                            dbCmdSet.executeCmd();
                            Integer setResult = (Integer)dbCmdSet.getResult();
                            // check if the set was successfull
                            if ( setResult.intValue() == 1 ) {
                                // if acked, populate the queried representation of the alarm with the
                                // updated ack attributes and place in a list that will be used to
                                // generate NotifyAckStateChangedEvents
                                populateWithNewAckNackAttributes( queriedAlarm, setAlarmTemplate );
                                ackAlarmList.add( queriedAlarm );
                            } // end alarm successfully nacked
                            else {
                                // this should never happen, since this method is in a transaction
                                AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                                false,
                                new SQLException("error performing db set operation") );
                                resultList.add( result );
                            } // end alarm already acked
                        } // end queried alarm ackUserId != ackUserId || queried alarm ackSystemId != ackSystemId
                    } // end alarm unacknowledged
                    else {
                        // already acked, create alarm result and indicate already acked
                        AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                        false,
                        new java.lang.IllegalArgumentException("alarm already in an unacked state") );
                        resultList.add( result );
                    } // end else alarm already acked
                } // end else alarm found
            } // end for querying and acking alarms in alarmReferenceList
        } // end of try statement
        catch ( SQLException sqe ) {
            throw new EJBException( sqe );
        }
        finally {
            // close the db commands to free resources
            dbCmdSet.closeCmd();
            dbCmdQuery.closeCmd();
            // close the connection.
            closeConnection();
        }
        // generate events for all unacked alarms
        generateAlarmEvents( NotifyAckStateChangedEventPropertyDescriptor.EVENT_TYPE_VALUE,
        (AlarmValue[]) ackAlarmList.toArray( new AlarmValue[0] ), nackTime );

        return (AlarmKeyResult[]) resultList.toArray( new AlarmKeyResult[0] );
    }

    /**
     * Returns alarm list. Number of retrieved alarms can be reduced
     * by defining filter templates.
     * <p>
     * All implementations must support at least the query
     * type defined in <code><code>QueryByFilterableAttributeValue</code></code>.
     *
     * <p>
     * If the attribute list is empty, all the attribute values in the
     * alarms are returned.
     *
     * @see AlarmValueIterator
     * @see QueryByFilterableAttributesValue
     * @param query A filter made up of an array of QueryValues each
     * containing a set of attribute values that are logically ORed together.
     * Empty list denotes that all alarms are selected.
     * @param attributes An array of the requested attributes.
     * @return <code>AlarmValueIterator</code> - iterator for retrieving returned alarms.
     * @exception javax.oss.IllegalArgumentException Is raised when
     * a bad argument is provided.
     */
    public AlarmValueIterator queryAlarmList(javax.oss.QueryValue[] query, String[] attributes)
    throws javax.oss.IllegalArgumentException {

        //System.out.println("wrx--in queryAlarmList of server");

        AlarmValueIterator alarmIter = null;

        if (isLoggingEnabled) {
            Object[][] parms = {  { "query[]", query },
                                  { "attributes", attributes } };
            myLog.logMethodEntry( "queryAlarmList", parms );
        }

        // check for null arguments
        if ( query == null || attributes == null ) {
            throw new javax.oss.IllegalArgumentException( "queryAlarmList: Null argument entered" );
        }

        ArrayList querylist = new ArrayList();

        for ( int i=0, len=query.length; i<len; i++ ) {
            if ( query[i] instanceof QueryByFilterableAttributesValue == false ) {
                throw new javax.oss.IllegalArgumentException( "queryAlarmList: Unsupported QueryValue type : " +
                query[i] );
            }
            else {
                QueryByFilterableAttributesValue fquery = (QueryByFilterableAttributesValue) query[i];
                if ( fquery.getPopulatedAttributeNames().length > 0 ) {
                    querylist.add( (WhereClause) fquery );
                }
            }
        }
        AlarmValue alarm = makeAlarmValue();

        // check for null attribute entries
        for ( int i=0, len=attributes.length; i<len; i++ ) {
            // Check for valid attribute entries ...
            if ( attributes[i] == null ) {
                throw new javax.oss.IllegalArgumentException( "queryAlarmList: Null attribute entered."  );
            }
        }

        QueryByQueryValueDBCommandImpl dbCmd = (QueryByQueryValueDBCommandImpl) DBCommandFactory.createOperation(
        DBCommandFactory.QUERY_BY_QUERYVALUE_DBCMD );
        if ( isLoggingEnabled) {
            dbCmd.setLogger(myLog);
        }

        dbCmd.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmd.setWhereClauses( (WhereClause[]) querylist.toArray( new WhereClause[0] ) );
        dbCmd.setEntityNames( attributes );

        try {
            dbCmd.setConnection( getConnection() );
            //System.out.println("wrx--before initializeCmd");
            dbCmd.initializeCmd();
            //System.out.println("wrx--after initializeCmd");
            dbCmd.executeCmd();
            //System.out.println("wrx--after executeCmd");

            // get the result
            System.out.println(dbCmd.getResult().getClass());
            ArrayList alarmlist = (ArrayList) dbCmd.getResult();

           //System.out.println("wrx--query alarmlist" + alarmlist);

            // if the includeApplicationInfo flag and the Key is one of the requested
            // attributes, fully populate the key with the application context and
            // the application domain.
            if ( includeApplicationInfo ) {
                for ( int i=0, len=alarmlist.size(); i<len; i++ ) {
                    AlarmValue alarm2 = (AlarmValue)alarmlist.get(i);
                    if ( ((AttributeAccess)alarm2).isPopulated( ManagedEntityValue.KEY ) ) {
                        // fully populate the key
                        alarm2.getManagedEntityKey().setApplicationContext( applicationContext );
                        alarm2.getManagedEntityKey().setApplicationDN( applicationDN );
                    }
                    else {
                        // no key attribute in the result set
                        break;
                    }
                }
            }
            alarmIter = new AlarmValueIteratorImpl( alarmlist, isLoggingEnabled);
        }
        catch  ( SQLException sqe ) {
            //sqe.printStackTrace();
            throw new EJBException( sqe );
        }
        finally {
            dbCmd.closeCmd();
            // close the connection.
            closeConnection();
        }
        return alarmIter;
    }

    /**
     * Returns amount of Alarms kept in server.
     * <p>
     * Possible usage is to find out the number of Alarms
     * in Alarm List before invoking GetAlarmList operation.
     * <p>
     * All implementations must support at least the query
     * type defined in <code>QueryByFilterableAttributeValue</code>.
     *
     * @see QueryByFilterableAttributesValue
     * @see AlarmCountsValue
     * @param query A filter made up of an array of QueryValues each
     * containing a set of attribute values that are logically ORed together.
     * Empty list denotes that all alarms are selected.
     * @return <code>AlarmCounts</code> - amount of Alarms in server's Alarm Information List.
     * The datastructure AlarmCounts contains own count for each alarm severity type.
     * @exception javax.oss.IllegalArgumentException Is raised when a bad argument is provided.
     * @exception javax.oss.UnsupportedOperationException Is raised if this
     * operation is not supported.
     */
    public AlarmCountsValue queryAlarmCounts(javax.oss.QueryValue[] query)
    throws javax.oss.IllegalArgumentException {

        if (isLoggingEnabled) {
            Object[][] parms = {  { "query[]", query } };
            myLog.logMethodEntry( "queryAlarmList", parms );
        }

        if ( query == null ) {
            throw new javax.oss.IllegalArgumentException( "queryAlarmCounts: Null QueryValue entered.");
        }

        AlarmCountsValue countValue = null;

        ArrayList querylist = new ArrayList();

        for ( int i=0, len=query.length; i<len; i++ ) {
            if ( query[i] instanceof QueryByFilterableAttributesValue == false ) {
                throw new javax.oss.IllegalArgumentException( "queryAlarmCounts: Unsupported QueryValue type ");
            }
            else {
                QueryByFilterableAttributesValue fquery = (QueryByFilterableAttributesValue) query[i];
                if ( fquery.getPopulatedAttributeNames().length > 0 ) {
                    querylist.add( (WhereClause) fquery );
                }
            }
        }

        QueryCountsDBCommandImpl dbCmd = (QueryCountsDBCommandImpl) DBCommandFactory.createOperation(
        DBCommandFactory.QUERY_ALARM_COUNTS_DBCMD );
        if ( isLoggingEnabled) {
            dbCmd.setLogger(myLog);
        }
        dbCmd.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmd.setWhereClauses( (WhereClause[]) querylist.toArray( new WhereClause[0] ) );

        try {
            dbCmd.setConnection( getConnection() );
            dbCmd.initializeCmd();
            dbCmd.executeCmd();
            countValue = (AlarmCountsValue)dbCmd.getResult();
        }
        catch  ( Exception ex ) {
            ex.printStackTrace();
            throw new EJBException(ex);
        }
        finally {
            dbCmd.closeCmd();
            // close the connection.
            closeConnection();
        }
        return countValue;
    }

    /**
     * Sets comment in one or more alarms in server's Alarm List.
     * <p>
     * As a result of this operation the server will emit
     * messages about commented alarms.
     * <p>
     * If client needs to comment lots of alarms it is recommended
     * to call this method several times instead of passing
     * very long alarm reference list in one function call.
     *
     * @see AlarmKeyResult
     * @return <code>AlarmKeyResult[]</code> - Bad alarm reference (AlarmKey)
     * list, if the list is empty, the call has totally succeeded.
     * It identifies Alarms that are not present in Alarm List
     * or that they are present, but Acknowledgement Information
     * has not changed, in contrast to clients request.  Element of
     * this list is a pair of Alarm Reference (AlarmKey) and reason.
     * @param alarmReferenceList A array of alarm ids (AlarmKey) to be unacknowledged.
     * @param commentUserId A String representing the user id.
     * @param commentText A String representing the comment.
     * @param commentSystemId A String representing the system id of the user who
     * generated the comment, NULL denotes that the parameter is not used.
     * @exception javax.oss.UnsupportedOperationException Is raised
     * if this operation is not supported.
     * @exception javax.oss.IllegalArgumentException Is raised when a
     * bad argument is provided.
     */
    public AlarmKeyResult[] tryCommentAlarms(
    AlarmKey[] alarmReferenceList,
    String   commentUserId,
    String   commentText,
    String   commentSystemId )
    throws javax.oss.IllegalArgumentException {

        if (isLoggingEnabled) {
            Object[][] parms = {  { "alarmReferenceList", alarmReferenceList },
                                  { "commentText", commentText },
                                  { "commentUserId", commentUserId },
                                  { "commentSystemId", commentSystemId } };
            myLog.logMethodEntry( "tryCommentAlarms", parms );
        }
        // check to make sure all arguments are present
        if ( alarmReferenceList == null || alarmReferenceList.length == 0 || commentUserId == null ||
        commentText == null ) {
            throw new javax.oss.IllegalArgumentException( "Invalid argument value entered." );
        }
        else {
            for ( int i=0,len=alarmReferenceList.length; i<len; i++ ) {
                if ( alarmReferenceList[i].getAlarmPrimaryKey() == null ||
                alarmReferenceList[i].getAlarmPrimaryKey().trim().length() == 0 ) {
                    throw new javax.oss.IllegalArgumentException( "Missing primary key : for key [ " + i + " ]" );
                }
            }
        }
        ArrayList resultList = new ArrayList();

        QuerySingleUpdateDBCommandImpl dbCmdQuery = null;
        SetDBCommandImpl dbCmdSet = null;

        // Configure a query database command
        dbCmdQuery = (QuerySingleUpdateDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.QUERY_SINGLE_UPDATE_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdQuery.setLogger(myLog);
        }
        // Configure the whereClause Template
        AlarmValue queryAlarmTemplate = (AlarmValue) new AlarmValueImpl();
        AlarmKey queryKey = queryAlarmTemplate.makeAlarmKey();
        queryAlarmTemplate.setAlarmKey( queryKey );

        // set the query command attributes
        dbCmdQuery.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdQuery.setWhereClauseTemplate( queryAlarmTemplate );

        // Create the set database command
        dbCmdSet = (SetDBCommandImpl) DBCommandFactory.createOperation( DBCommandFactory.SET_DBCMD );
        if ( isLoggingEnabled) {
            dbCmdSet.setLogger(myLog);
        }

        // Create the update template for the db command and set the appropriate values
        AlarmValue setAlarmTemplate = new AlarmValueImpl();
        // set the ackTime for all the alarms to ack
        Date commentTime = new Date();
        CommentValue comment = setAlarmTemplate.makeCommentValue();
        comment.setCommentUserId( commentUserId );
        comment.setCommentText( commentText );
        comment.setCommentTime( commentTime );
        if ( commentSystemId != null ) {
            comment.setCommentSystemId( commentSystemId );
        }

        // set with dummy value so the template so that UPDATE SQL will
        // contain an entry for notification id and comments
        setAlarmTemplate.setNotificationId( "DUMMY" );
        CommentValue[] dummyComments = { comment };
        setAlarmTemplate.setComments( dummyComments );

        // build where clause template for the set database command
        AlarmValue setWhereTemplate = new AlarmValueImpl();
        AlarmKey setKey = setWhereTemplate.makeAlarmKey();
        setWhereTemplate.setAlarmKey( setKey );

        // Configure the db set command with the templates and reuse
        dbCmdSet.setDBHelper( AlarmValue.VALUE_TYPE );
        dbCmdSet.setUpdateTemplate( setAlarmTemplate );
        dbCmdSet.setWhereClauseTemplate( setWhereTemplate );

        ArrayList commentAlarmList = new ArrayList();

        try {
            // set the connection and initialize the db commands
            dbCmdQuery.setConnection( getConnection() );
            dbCmdQuery.initializeCmd();
            dbCmdSet.setConnection( getConnection() );
            dbCmdSet.initializeCmd();

            // Query and add comment to the alarm in the alarm reference list
            for ( int i=0, len=alarmReferenceList.length; i<len; i++ ) {
                // modify the alarm key (id) in the update template
                queryKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );

                // execute the query
                dbCmdQuery.executeCmd();
                ManagedEntityValue[] entities = (ManagedEntityValue[]) dbCmdQuery.getResult();

                if ( entities == null || entities.length == 0 ) {
                    // if alarm not found, create a alarm result and indicate no alarm found
                    AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                    false,
                    new ObjectNotFoundException ("alarm not found" ) );
                    resultList.add( result );
                }
                else {

                    AlarmValue queriedAlarm = (AlarmValue) entities[0];
                    // add comment to queried alarm comments
                    CommentValue[] updatedComments = addComment( queriedAlarm.getComments(), comment );

                    // get a notification id that will be used in the NotifyAckStateChangedEvent
                    String notId = genNotificationId();
                    setAlarmTemplate.setNotificationId( notId );
                    setAlarmTemplate.setComments( updatedComments );
                    // set the alarm key (id) in the set where template
                    setKey.setAlarmPrimaryKey( alarmReferenceList[i].getAlarmPrimaryKey() );
                    // execute the set
                    dbCmdSet.executeCmd();
                    Integer setResult = (Integer)dbCmdSet.getResult();
                    // check if the set was successfull
                    if ( setResult.intValue() == 1 ) {
                        // if comment inserted, populate the queried representation of the alarm with the
                        // updated attributes and place in a list that will be used to
                        // generate NotifyAlarmCommentsEvents
                        queriedAlarm.setNotificationId( notId );
                        queriedAlarm.setComments( updatedComments );
                        commentAlarmList.add( queriedAlarm );
                    } // end alarm successfully acked
                    else {
                        // This should never happen
                        AlarmKeyResult result = new AlarmKeyResultImpl( alarmReferenceList[i],
                        false,
                        new java.lang.IllegalArgumentException ("Problem commenting alarm: " + alarmReferenceList[i] ) );
                        resultList.add( result );
                    } // end problem setting the comment
                } // end else alarm found
            } // end for querying and adding to alarms in alarmReferenceList
        } // end of try statement
        catch ( SQLException sqe ) {
            throw new EJBException( sqe );
        }
        finally {
            // close the db commands to free resources
            dbCmdSet.closeCmd();
            dbCmdQuery.closeCmd();
            // close the connection.
            closeConnection();
        }
        // generate events for all commented alarms
        generateAlarmEvents( NotifyAlarmCommentsEventPropertyDescriptor.EVENT_TYPE_VALUE,
        (AlarmValue[]) commentAlarmList.toArray( new AlarmValue[0] ), commentTime );

        return (AlarmKeyResult[]) resultList.toArray( new AlarmKeyResult[0] );
    }

    /**
     * Makes an alarm value.
     *
     * @return AlarmValue An alarm value.
     */
    public AlarmValue makeAlarmValue() {
        return new AlarmValueImpl();
    }

    /**
     * Retrieve information of the supported API versions.
     *
     * <p>
     * The operation shall indicate compatibility with the first release of OSS QoS API.
     *
     *@return String[]  Array of supported version strings.
     */

    public String[] getVersion() {
        return compVersions;
    }

    /**
     * Generates alarm events - NotifyAckStateChangedEvent and NotifyAlarmCommentsEvent.
     *
     * @param eventType A String indicating the event type to generate
     * @param alarms The list of alarms for which the events will be generated
     * @param time The time that the event occurred
     */
    private void generateAlarmEvents( String eventType, AlarmValue[] alarms, Date time ) {
        for ( int i=0, len=alarms.length; i<len; i++ ) {
            Event event = EventFactory.makeEvent( eventType, alarms[i] );
            // set the admin domain
            if ( includeApplicationInfo ) {
                // set the application domain name
                event.setApplicationDN( applicationDN );
            }
            event.setEventTime( time );
            if ( isLoggingEnabled ) {
                myLog.log("AlarmMonitorSessionBean: generating " + Util.printObject(event) );
            }
            MessageBuilder msgBuilder =
            (MessageBuilder) EventFactory.getPropertyDescriptor( eventType );
            evPublisher.publish( event, null, msgBuilder );
        }
        return;
    }

    private int deleteClearedAckedAlarms ()  {

        Integer deletedAlarms = new Integer(0);

        if ( isLoggingEnabled ) {
            myLog.log ("Deleting any cleared and acked alarms");
        }

        DeleteDBCommandImpl dbCmd = ( DeleteDBCommandImpl ) DBCommandFactory.createOperation( DBCommandFactory.DELETE_DBCMD );
        if ( isLoggingEnabled) {
            dbCmd.setLogger(myLog);
        }

        // prepare the where clause template
        AlarmValue alarm = (AlarmValue) new AlarmValueImpl();
        alarm.setPerceivedSeverity( PerceivedSeverity.CLEARED );
        alarm.setAlarmAckState( AlarmAckState.ACKNOWLEDGED );
        dbCmd.setWhereClauseTemplate( alarm );
        // load the connection
        dbCmd.setDBHelper( AlarmValue.VALUE_TYPE );
        try {
            dbCmd.setConnection( getConnection() );
            dbCmd.initializeCmd();
            dbCmd.executeCmd();
        }
        catch ( SQLException sqe ) {
            throw new EJBException( sqe );
        }
        finally {
            dbCmd.closeCmd();
        }

        deletedAlarms = (Integer) dbCmd.getResult();
        return deletedAlarms.intValue();
    }

    private void populateWithNewAckNackAttributes( AlarmValue alarm, AlarmValue template ) {

        alarm.setNotificationId( template.getNotificationId() );
        alarm.setAlarmAckState( template.getAlarmAckState() );
        alarm.setAckUserId( template.getAckUserId() );
        alarm.setAckSystemId( template.getAckSystemId() );
        alarm.setAckTime( template.getAckTime() );
        return;
    }

    // this will call the database for the notification id... This is a temporary solution.
    private String genNotificationId() {
        long timeMillis = java.lang.System.currentTimeMillis();
        return new String( "NotID: " + timeMillis );
    }

    private Connection getConnection() throws SQLException {
        if ( connection == null ) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    private void closeConnection() {
        try {
            if ( connection != null && connection.isClosed() == false ) {
                connection.close();
                connection = null;
            }
        }
        catch ( SQLException sqe ) {
            if ( isLoggingEnabled ) {
                myLog.logException ("AlarmMonitorSessionBean:closeConnection:  problem closing the db", sqe);
            }
        }
        return;
    }

    /**
     *  Adds a comment to the alarm's comment list. Only 4 comments are allowed. If
     *  a fifth one is added, the oldest one will be deleted from the list.
     *
     *  @param oldComments An array of comments - the comment list
     *  @param comment The comment to be added to the comment list
     *  @return CommentValue[] The comment list containing the new comment and
     *  not exceeding the max of 4 in the list.
     */
    private CommentValue[] addComment( CommentValue[] oldComments, CommentValue comment ) {

        // debug
        if (isLoggingEnabled) {
            Object[][] parms = {  { "oldComments", oldComments },
                                  { "comment", comment } };
            myLog.logMethodEntry( "addComment", parms );
        }

        ArrayList updatedComments = new ArrayList();
        CommentValue oldestComment = comment;
        for ( int i=0, len=oldComments.length; i<len; i++ ) {
            updatedComments.add( oldComments[i] );
            if ( oldComments[i].getCommentTime().before( oldestComment.getCommentTime() ) ) {
                oldestComment = oldComments[i];
            }
        }
        if ( updatedComments.size() > 3 ) {
            updatedComments.remove( oldestComment );
        }
        updatedComments.add( comment );
        return ( CommentValue[] ) updatedComments.toArray( new CommentValue[0] );
    }

    // Lookup the AlarmValueIteratorHome Bean
    /*private AlarmValueIteratorHome lookupAlarmValueIteratorHome()
    {
        AlarmValueIteratorHome alarmValueIterHome = null;
        try
        {
// Changed by Stefan
//            alarmValueIterHome = (AlarmValueIteratorHome)
//            initCtx.lookup( SessionResourceConstants.ALARM_ITERATOR_HOME );
// to
            alarmValueIterHome = (AlarmValueIteratorHome) PortableRemoteObject.narrow(
            initCtx.lookup( SessionResourceConstants.ALARM_ITERATOR_HOME ), 
                            AlarmValueIteratorHome.class);
        }
        catch (NamingException nex)
        {
            if ( isLoggingEnabled ) {
                myLog.logException ("AlarmMonitorSessionBean:lookupAlarmValueIteratorHome:  ",
                nex);
            }
        }
        return alarmValueIterHome;
    }*/

    /**
     * Initializes the applicationContext and applicationDN. The values are
     * fetched from the bean using JNDI (<code>env-entry</code>).
     * <pre>
     * applicationContextProviderURL
     * applicationContextInitialContextFactory
     * ...
     * applicationDistinguishedName
     * </pre>
     *
     */
    private void initializeApplicationContext() {

        // initialize ApplicationContext from EJB properties
        applicationContext = new ossj.qos.ApplicationContextImpl();

        try {

            // provider url
            Object ref = initCtx.lookup( APPLICATION_CONTEXT_PROPERTY_PROVIDER_URL );
            String url = (String)PortableRemoteObject.narrow(ref,String.class);
            applicationContext.setURL( url );

            // initial context factory class
            ref = initCtx.lookup( APPLICATION_CONTEXT_PROPERTY_INITIAL_CONTEXT_FACTORY );
            String initialContextFactory = (String) PortableRemoteObject.narrow(ref,String.class);
            applicationContext.setFactoryClass( initialContextFactory );

        } catch ( NamingException e ) {
            if ( isLoggingEnabled ) {
                myLog.logException("AlarmMonitorSessionBean: initializeApplicationContext()", e);
                myLog.log("Unable to initialize Application Context");
            }
        } catch ( ClassCastException e ) {
            myLog.logException("AlarmMonitorSessionBean: initializeApplicationContext()", e);
            myLog.log("Unable to initialize Application Context");
        }

        // initialize applicationDN from properties
        try {
            Object ref = initCtx.lookup( APPLICATION_DN_PROPERTY );
            applicationDN = (String)PortableRemoteObject.narrow(ref,String.class);
        } catch ( NamingException e ) {
            if ( isLoggingEnabled ) {
                myLog.logException("AlarmMonitorSessionBean: initializeApplicationContext() ", e);
                myLog.log("Unable to initialize Application Context");
            }
        }
        return;
    }

}
