/*
 * Copyright: Copyright ¨ 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.ejb.*;
import javax.jms.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import javax.naming.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.oss.util.*;
import javax.oss.UnsupportedAttributeException;
import javax.oss.pm.measurement.*;
import javax.oss.pm.threshold.*;
import javax.oss.fm.monitor.*;
import javax.oss.pm.util.Schedule;

// XML
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;

//import ossj.qos.pm.util.Log;
import ossj.qos.util.Log;
//import ossj.qos.pm.util.Util;
import ossj.qos.util.Util;
import ossj.qos.pm.threshold.PerformanceMonitorReportHandler;

/**
 * The message-driven bean listens for performance monitor events, evaluates them
 * with the threshold monitor entity beans and sends threshold monitor alarms
 * if a threshold is violated (crossed or cleared).
 *
 * <p>The received performance monitor events are evaluated against the
 * <code>ThresholdMonitorEntityBean</code>. The evaluation is performed by using
 * static methods in the <code>ThresholdEvaluation</code> class. If a threshold
 * is crossed (or cleared) an alarm is sent to the alarm monitor.
 *
 * <p>The message-driven bean is also responsible for removing expired threshold
 * monitors from the entity bean. When a message is received the bean first
 * retrieves all the thresholds currently available and removes all expired
 * thresholds.
 * Note, that this approach can be quite time consuming, especially since the
 * check is performed every time. A real implementation should solve this in
 * another way. At least to have some smarter (faster) finder method in the
 * entity bean to get only expired thresholds that should be removed.
 *
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorBean
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorEntityBean
 * @author Henrik Lindstrom, Ericsson
 */
public class ThresholdMonitorMessageBean implements MessageDrivenBean,
    MessageListener, EnvironmentConstants, MessageConstants {

    /**
     * The bean context.
     */
	private MessageDrivenContext ctx = null;

    /**
     * Local interface for threshold monitor.
     */
    private ThresholdMonitorEntityLocalHome entityHome = null;

    /**
     * Interface for the alarm monitor (fault monitor) session.
     */
    private JVTAlarmMonitorSession alarmSession = null;

    /**
     * Interface to the alarm TopicPublisher for threshold alarm events.
     */
    private TopicPublisher alarmTopicPublisher = null;

    /**
     * Interface to the alarm topic session.
     */
    private TopicSession alarmTopicSession = null;

    /**
     * Initialize connection to entity bean and performance monitor events.
     */
    public void ejbCreate() {
        Log.write(this,Log.LOG_ALL,"ejbCreate()","called");
        System.out.println("***************************debug1 ejbCreate*************");
        // set up connection to local threshold monitor entity bean...
        Log.write( "get initial context" );
        Context initCtx = null;
        try {
            initCtx = new InitialContext();
        } catch ( NamingException e ) {
            Log.write(this,Log.LOG_MAJOR,"ejbCreate()",
                "could not get initial context, exception="+e);
            throw new RuntimeException( e.toString() );
        }

        Log.write( "lookup " + THRESHOLD_MONITOR_ENTITY_LOCAL_HOME + "...");
        Object result = null;
        try {
            result = initCtx.lookup( THRESHOLD_MONITOR_ENTITY_LOCAL_HOME );
        } catch ( NamingException e ) {
            Log.write(this,Log.LOG_MAJOR,"ejbCreate()","could not lookup "
                +THRESHOLD_MONITOR_ENTITY_LOCAL_HOME+", exception="+e);
            throw new RuntimeException( e.toString() );
        }
        entityHome = (ThresholdMonitorEntityLocalHome) PortableRemoteObject.narrow(
            result, ThresholdMonitorEntityLocalHome.class);
        Log.write( "got local entity home!" );

        // initialize alarm session
        Log.write( "lookup " + ALARM_MONITOR_HOME + "..." );
        try {
            result = initCtx.lookup( ALARM_MONITOR_HOME );
        } catch ( NamingException e ) {
            Log.write(this,Log.LOG_MAJOR,"ejbCreate()","could not lookup "
                +ALARM_MONITOR_HOME+", exception="+e);
            throw new RuntimeException( e.toString() );
        }
        JVTAlarmMonitorHome alarmHome = (JVTAlarmMonitorHome)
            PortableRemoteObject.narrow( result, JVTAlarmMonitorHome.class);
        Log.write("Create alarm monitor session...");
        try {
            alarmSession = alarmHome.create();
        } catch ( RemoteException e ) {
            Log.write(this,Log.LOG_MAJOR,"ejbCreate()","exception="+e);
            Log.write( ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR );
            throw new RuntimeException( ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR
                + " [" + e.getMessage() + "]" );
        } catch ( CreateException e ) {
            Log.write(this,Log.LOG_MAJOR,"ejbCreate()","exception="+e);
            Log.write( ERR_ALARM_MONITOR_SESSION_CREATE_FAILED );
            throw new RuntimeException( ERR_ALARM_MONITOR_SESSION_CREATE_FAILED
                + " [" + e.getMessage() + "]" );
        }
        System.out.println("***************************debug1 call initTopicPublisher*************");
        // initialize topic publisher
        initTopicPublisher();

    }

    /**
     * Remove connection to local entity bean and performance monitor event
     * listener.
     */
    public void ejbRemove() {
        Log.write(this,Log.LOG_ALL,"ejbRemove()","called");

        // free resources
        entityHome = null;
        alarmSession = null;
        alarmTopicPublisher = null;
        alarmTopicSession = null;
    }

    /**
     * Initialize the context.
     * @param ctx the bean context
     */
    public void setMessageDrivenContext( MessageDrivenContext ctx ) {
        Log.write(this,Log.LOG_ALL,"setMessageDrivenContext()","called");
        this.ctx = ctx;
    }

    /**
     * Evaluate a message. If the message contains a PerformanceDataEvent it is
     * evaluated against the (entity) thresholds. If one or more matching thresholds
     * are found the threshold monitor values are evaluated against the
     * performance data event to see if there is any threshold violation.
     * @param msg the message
     */
    public void onMessage( Message msg ) {

       //System.out.println("wrx--in threshold message bean");  
        Log.write(this,Log.LOG_ALL,"onMessage()","msg="+msg);

        if ( msg instanceof ObjectMessage ) {
            ObjectMessage objMsg = (ObjectMessage) msg;

            try {
                Object obj = objMsg.getObject();
                if ( obj instanceof PerformanceDataEvent ) {
                    PerformanceDataEvent pde = (PerformanceDataEvent) obj;

                    Date eventTime = pde.getEventTime();
                    Log.write(this,Log.LOG_ALL,"onMessage()","eventTime="+eventTime);

                    // 1. Check if schedule is "expired", then the threshold(s)
                    // should be automatically deleted.
                    removeExpiredThresholds( eventTime );

                    // 2. find threshold monitor entities with performance monitor key

                    String performanceMonitorPrimaryKey = pde.getManagedObjectInstance();
                    // Search database for entity beans with performance monitor key.
                    // Usually the case should be thar there is one performance
                    // monitor per threshold defined. However there could be more
                    // then one, in theory at least.
                    Log.write("Find thresholds with performance monitor primary key...");
                    Collection thresholds = null;
                    try {
                        thresholds = entityHome.findThresholdsWithPerformanceMonitorPrimaryKey(
                            performanceMonitorPrimaryKey );
                    } catch ( FinderException e ) {
                        Log.write(this,"onMessage()","exception="+e);
                        Log.write("No threshold using the performance monitor was found.");
                        return; // finished
                    }

                    // 3. validate thresholds returned against the performance data event
                    Log.write("Validate thresholds against performance data...");
                    Iterator iter = thresholds.iterator();
                    ThresholdMonitorEntityLocal thresholdEntity = null;
                    while ( iter.hasNext() ) {
                        try {
                            thresholdEntity = (ThresholdMonitorEntityLocal)iter.next();

                            // check that threshold monitor is not suspended
                            int state = thresholdEntity.getThresholdMonitorValue().getState();
                            if ( state != ThresholdMonitorState.SUSPENDED ) {
                                // 4. validate against threshold (including sending any alarm)
                                evaluateThreshold( thresholdEntity, pde );
                            }
                        } catch ( ThresholdMonitorException e ) {
                            Log.write(this,Log.LOG_MINOR,"onMessage()","exception="+e);
                            Log.write("Could not evaluate threshold with key="
                                +thresholdEntity.getPerformanceMonitorPrimaryKey());
                        }
                    }
                    Log.write("Validation done.");
                }
            } catch ( JMSException e ) {
                Log.write(this,Log.LOG_MAJOR,"onMessage()","exception="+e);
            } catch ( Exception e ) {
                Log.write(this,Log.LOG_MAJOR,"onMessage()","exception="+e);
                Log.write("THIS WAS AN UNEXPECTED EXCEPTION!");
            }
        } else {
            Log.write(this,"onMessage()","not supported message");
        }
    }

    /**
     * Remove all thresholds that have expired. All thresholds having a expire
     * date earlier then the <code>endDate</code> parameter will be removed. All
     * thresholds with an end date later or equal to the <code>endDate</code>
     * are not removed.
     *
     * <p>If a threshold found could not be removed it is simply ignored.
     *
     * <p>Note, that the associated performance monitor should be automatically
     * removed by the performance monitor EJB when it expires. Since the
     * performance monitor should have the same stop time. Therefor it is not
     * removed by this method.
     *
     * @param endDate the earliest end date for the thresholds
     */
    private void removeExpiredThresholds( Date endDate ) {
        Log.write(this,Log.LOG_ALL,"removeExpiredThresholds()","endDate="+endDate);
        long startTime = System.currentTimeMillis();
        try {
            Collection thresholdKeys = null;
            try {
                thresholdKeys = entityHome.findAllThresholds();
            } catch ( FinderException e ) {
                Log.write(this,Log.LOG_MINOR,"removeExpiredThresholds()","exception="+e);
                Log.write(ERR_THRESHOLD_MONITOR_NOT_FOUND);
                return;
            }
            Iterator thresholdKeysIterator = thresholdKeys.iterator();
            ThresholdMonitorEntityLocal aThreshold = null;
            //ThresholdMonitorKey thresholdMonitorKey = null;
            //String thresholdPrimaryKey = null;
            ThresholdMonitorValue thresholdValue = null;
            Schedule thresholdSchedule = null;
            Calendar stopTimeCalendar = null;
            Date stopTime = null;
            Object obj = null;
            while ( thresholdKeysIterator.hasNext() ) {
                try {
                    aThreshold = (ThresholdMonitorEntityLocal) thresholdKeysIterator.next();
                    Log.write(this,"removeExpiredThresholds()","obj="+obj);
                    thresholdValue = aThreshold.getThresholdMonitorValue();
                    thresholdSchedule = thresholdValue.getSchedule();
                    stopTimeCalendar = thresholdSchedule.getStopTime();
                    // null stop time means the monitor should run "forever"
                    if ( stopTimeCalendar != null ) {
                        stopTime = stopTimeCalendar.getTime();
                        if ( stopTime.before( endDate ) ) { // if expired
                            Log.write(this,Log.LOG_ALL,"removeExpiredThresholds()",
                                "removing [" + aThreshold.getPerformanceMonitorPrimaryKey() + "] with expire date="
                              + stopTime );

                            aThreshold.remove(); // remove threshold in database
                        }
                    }
                } catch ( RemoveException e ) {
                    Log.write(this,Log.LOG_MINOR,"removeExpiredThresholds()","exception="+e);
                    Log.write( ERR_COULD_NOT_REMOVE_THRESHOLD_MONITOR );
                }
            }
        } finally {
            Log.write(this,Log.LOG_ALL,"removeExpiredThresholds()","took "
                + (System.currentTimeMillis()-startTime) + " ms" );
        }
    }

    /**
     * Validates a performance data event against the threshold to see if there
     * is any violation.
     *
     * @param entity the thershold to validate
     * @param pde the performance data event
     * @exception ThresholdMonitorException if something goes wrong during
     * evaluation
     */
    private void evaluateThreshold( ThresholdMonitorEntityLocal entity,
                                    PerformanceDataEvent pde )
                                    throws ThresholdMonitorException {
        Log.write(this,Log.LOG_ALL,"evaluateThreshold()","pde="+pde);

        Log.write("event time=" + pde.getEventTime() );
        Log.write("value="+ pde.getPerformanceMonitorReport() );

        ReportFormat reportFormat = pde.getReportFormat();
        if ( reportFormat.getType() == ReportFormat.XML ) {

            // This need to be updated if the report format returns something
            // else then just a simple String with the measurement value.
            Log.write("Get performance monitor report...");
            String measurementValueReport = null, measurementValue = null;

						Object objReport = pde.getPerformanceMonitorReport();
						if ( objReport instanceof String[] ) {
						    measurementValueReport = ((String[])objReport)[0];
						} else if ( objReport instanceof String ) {
                measurementValueReport = (String)objReport;
            } else {
            	  throw new ThresholdMonitorException( "Unknown report format!" );
            }

            Document reportDoc = null;
            try {
                reportDoc = PerformanceMonitorReportHandler.getDocument( measurementValueReport );

            } catch ( ParserConfigurationException e ) {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()","exception="+e);
                Log.write("Can not handle the report!");
                throw new ThresholdMonitorException( e.getMessage() );
            }  catch ( SAXException e ) {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()","exception="+e);
                Log.write("Can not handle the report!");
                throw new ThresholdMonitorException( e.getMessage() );
            }  catch ( IOException e ) {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()","exception="+e);
                Log.write("Can not handle the report!");
                throw new ThresholdMonitorException( e.getMessage() );
            }

            // Get related ThresholdMonitorValue.
            Log.write("Get ThresholdMonitorValue...");
            SimpleThresholdMonitorValue tmValue =
                (SimpleThresholdMonitorValue)entity.getThresholdMonitorValue();
            Log.write("Get threshold definition...");
            ThresholdDefinition thresholdDefinition = tmValue.getThresholdDefinition();
            Log.write("Get performance attribute descriptor...");
            PerformanceAttributeDescriptor pad = thresholdDefinition.getAttributeDescriptor();

            // find the right measurement value in the report
            String observableObject = tmValue.getObservableObject();
            String measurementAttributeName = pad.getName();

            //
            // find the (right) measurement in the report and then validate
            try {
                measurementValue =
                    PerformanceMonitorReportHandler.getMeasurementValue(
                        reportDoc, observableObject, measurementAttributeName, null );
            } catch ( Exception e ) {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()","exception="+e);
            }

            Log.write("returned measurementValue=" + measurementValue );

            // The type (Integer, Real, String) is checked against the threshold
            // definition, not the performance monitor. But they should be of the
            // same type!
            int type = pad.getType(); // type of value, i.e. Integer, Real (double), String

            Object thresholdValueObj = thresholdDefinition.getValue(),
                   thresholdOffsetObj = thresholdDefinition.getOffset();
            if ( thresholdValueObj == null ) {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()",ERR_THRESHOLD_MONITOR_VALUE_NULL);
                // Can not validate the threshold because the value is null!
                // This should not happen unless the value was not correctly
                // evaluated when the threshold monitor was created.
                return; // skip validating threshold!
            }

            // Evaluate performance value based on the type
            int resultStatus = ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED;

            Log.write("Evaluate...");
            // Evaluation of INTEGER performance values.
            if ( type == PerformanceAttributeDescriptor.INTEGER ) {
                Log.write(this,Log.LOG_ALL,"evaluateThreshold()","INTEGER");
                int performanceValue = Integer.parseInt( measurementValue ),
                    thresholdValue = ((Integer)thresholdValueObj).intValue(),
                    thresholdOffset = 0,
                    direction = thresholdDefinition.getDirection(),
                    previousStatus = entity.getThresholdMonitorStatus();
                if ( thresholdOffsetObj != null ) { // if offset is specified
                    thresholdOffset = ((Integer)thresholdOffsetObj).intValue();
                }

                resultStatus = ThresholdEvaluation.evaluateThreshold(
                                                            performanceValue,
                                                            thresholdValue,
                                                            thresholdOffset,
                                                            direction,
                                                            previousStatus);
            }
            // Evaluation of REAL performance values.
            else if ( type == PerformanceAttributeDescriptor.REAL ) {
                Log.write(this,Log.LOG_ALL,"evaluateThreshold()","REAL");
                double performanceValue = Double.parseDouble( measurementValue ),
                       thresholdValue = ((Double)thresholdDefinition.getValue()).doubleValue(),
                       thresholdOffset = 0.0d;
                int direction = thresholdDefinition.getDirection(),
                    previousStatus = entity.getThresholdMonitorStatus();
                if ( thresholdOffsetObj != null ) { // if offset is specified
                    thresholdOffset = ((Double)thresholdOffsetObj).doubleValue();
                }

                resultStatus = ThresholdEvaluation.evaluateThreshold(
                                                            performanceValue,
                                                            thresholdValue,
                                                            thresholdOffset,
                                                            direction,
                                                            previousStatus);
            } else {
                Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()",
                    ERR_NOT_SUPPORTED_VALUE_FORMAT + " [" + type + "]" );
                throw new ThresholdMonitorException( ERR_NOT_SUPPORTED_VALUE_FORMAT );
            }

            // check if an event should be sent or not (if anything else then THRESHOLD_NOT_CROSSED_OR_CLEARED)
            if ( resultStatus!=ThresholdMonitorStatus.THRESHOLD_NOT_CROSSED_OR_CLEARED ) {
                try {

                    //System.out.println("wrx-in thre mesage,send event");
                    sendEvent(entity,pde,measurementValue, resultStatus );
                    // update status for entity bean in database
                    entity.setThresholdMonitorStatus( resultStatus );
                } catch ( ThresholdMonitorException e ) {
                    Log.write(this,Log.LOG_MINOR,"evaluateThreshold","exception="+e);
                    e.fillInStackTrace();
                    throw e;
                }
            }
        } else {
            Log.write(this,Log.LOG_MAJOR,"evaluateThreshold()",ERR_NOT_SUPPORTED_REPORT_FORMAT);
            throw new ThresholdMonitorException( ERR_NOT_SUPPORTED_REPORT_FORMAT );
        }
    }

    /**
     * Generate and send an alarm event. Depending on the threshold monitor
     * status, crossed or cleared, either a new alarm or a cleared alarm is sent.
     *
     * @param entity the threshold that has caused the event (alarm)
     * @param pde the received performance data event
     * @param observedValue the received value
     * @param thresholdMonitorStatus indicates if a new or cleared alarm should be sent
     * @exception ThresholdMonitorException if unable to send an event
     */
    private void sendEvent( ThresholdMonitorEntityLocal entity,
                            PerformanceDataEvent pde,
                            Object observedValue,
                            int thresholdMonitorStatus ) throws ThresholdMonitorException {
        Log.write(this,Log.LOG_ALL,"sendEvent(entity,pde,observedValue,thresholdMonitorStatus)","called");

        AlarmEvent alarmEvent = null;
        // populate the event
        SimpleThresholdMonitorValue tmValue =
            (SimpleThresholdMonitorValue)entity.getThresholdMonitorValue();
        AlarmConfig alarmConfig = tmValue.getAlarmConfig();

        AlarmEventPropertyDescriptor alarmEventPropertyDescriptor = null;
        //
        // Create a new event for threshold crossed or a cleared event for
        // threshold cleared.
        //
        if ( thresholdMonitorStatus == ThresholdMonitorStatus.THRESHOLD_CROSSED ) {

            // construct the event
            NotifyNewAlarmEvent newAlarmEvent = null;
            NotifyNewAlarmEventPropertyDescriptor epd = null;
            try {
                epd = (NotifyNewAlarmEventPropertyDescriptor) alarmSession.getEventDescriptor(
                    NotifyNewAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE );
            } catch ( javax.oss.IllegalArgumentException e ) {
                Log.write(this,Log.LOG_MINOR,"sendEvent(entity,pde,observedValue,thresholdMonitorStatus)","exception="+e);
                Log.write(ERR_COULD_NOT_CREATE_EVENT);
                throw new ThresholdMonitorException( ERR_COULD_NOT_CREATE_EVENT );
            } catch ( RemoteException e ) {
                Log.write(this,Log.LOG_MINOR,"sendEvent(entity,pde,observedValue,thresholdMonitorStatus)","exception="+e);
                Log.write( ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR );
                throw new ThresholdMonitorException( ERR_COULD_NOT_CREATE_EVENT );
            }
            newAlarmEvent = epd.makeNotifyNewAlarmEvent();

            try {
                String specificProblem = alarmConfig.getSpecificProblem();
                if ( specificProblem!=null ) {
                    newAlarmEvent.setSpecificProblem( specificProblem );
                }
            } catch ( UnsupportedAttributeException e ) {
                // Log problem but try send event anyway. The attribute might not
                // be supported by the Alarm Monitor.
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "Unsupported attribute="+e.getAttributeName() );
            }

            // create the threshold info type object
            Log.write(this,Log.LOG_ALL,
                "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                "Make ThresholdInfoType...");
            try {
                ThresholdInfoType thresholdInfoType = newAlarmEvent.makeThresholdInfoType();
                thresholdInfoType.setArmTime( pde.getEventTime() ); // how to handle

                thresholdInfoType.setObservedObject( tmValue.getObservableObject() );
                thresholdInfoType.setObservedValue( observedValue );
                Log.write(this,Log.LOG_ALL,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "Set threshold definition...");
                thresholdInfoType.setThresholdDefinition( tmValue.getThresholdDefinition() );
                Log.write("Set ThresholdInfoType...");
                newAlarmEvent.setThresholdInfo( thresholdInfoType );
            } catch ( UnsupportedAttributeException e ) {
                // Log problem but try send event anyway.
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "Unsupported attribute="+e.getAttributeName() );
            }

            /*
             * what about these attributes, should they be initialized?
             *
            alarmEvent.setAdditionalText(); //?
            alarmEvent.setBackedUpStatus(); //?
            alarmEvent.setBackUpObject(); // ?
            alarmEvent.setCorrelatedNotifications(); // ?
            alarmEvent.setProposedRepairActions(); // ?
            alarmEvent.setTrendIndication(); // ?
            */
            Log.write(this,Log.LOG_ALL,
                "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                "Set perceived severity...");
            newAlarmEvent.setPerceivedSeverity( alarmConfig.getPerceivedSeverity() );

            // Set attribute value(s). Isn't this the same information as
            // previous set above in the threshold info type?
            Log.write(this,Log.LOG_ALL,
                "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                "Set AttributeValue...");
            try {
                AttributeValue attribValue = newAlarmEvent.makeAttributeValue();
                PerformanceAttributeDescriptor pad = tmValue.getThresholdDefinition().getAttributeDescriptor();
                attribValue.setAttributeName( pad.getName() ); // string
                attribValue.setAttributeType( observedValue.getClass().getName() ); // string
                attribValue.setValue( observedValue ); // object
                newAlarmEvent.setMonitoredAttributes( new AttributeValue[]{attribValue} );
            } catch ( UnsupportedAttributeException e ) {
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "Unsupported attribute="+e.getAttributeName() );
            }

            // store (cast) in "global" alarmEvent variable
            alarmEvent = newAlarmEvent;
            alarmEventPropertyDescriptor = epd;

            // finally set and store for later use the alarm key
            String alarmEventGUID = Util.makeGUID(alarmEvent);
            Log.write( "alarmEventGUID="+alarmEventGUID);
            AlarmKey alarmKey = alarmEvent.makeAlarmKey();
            alarmKey.setAlarmPrimaryKey( alarmEventGUID );
            alarmEvent.setAlarmKey( alarmKey );
            entity.setThresholdAlarmPrimaryKey( alarmEventGUID );

        } else if ( thresholdMonitorStatus == ThresholdMonitorStatus.THRESHOLD_CLEARED ) {
            NotifyClearedAlarmEvent clearedAlarmEvent = null;
            NotifyClearedAlarmEventPropertyDescriptor epd = null;

            try {
                epd = (NotifyClearedAlarmEventPropertyDescriptor) alarmSession.getEventDescriptor(
                    NotifyClearedAlarmEventPropertyDescriptor.EVENT_TYPE_VALUE );
            } catch ( javax.oss.IllegalArgumentException e ) {
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write(ERR_COULD_NOT_CREATE_EVENT);
                throw new ThresholdMonitorException( ERR_COULD_NOT_CREATE_EVENT );
            } catch ( RemoteException e ) {
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write( ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR );
                throw new ThresholdMonitorException( ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR );
            }

            clearedAlarmEvent = epd.makeNotifyClearedAlarmEvent();

            // Set correlated notification id.
            try {
                CorrelatedNotificationValue correlatedNotificationValue =
                    clearedAlarmEvent.makeCorrelatedNotificationValue();
                correlatedNotificationValue.setManagedObjectInstance(
                     pde.getManagedObjectInstance() );
                // set notification id
                long notificationId = entity.getThresholdAlarmNotificationId();
                String correlatedNotificationId = Long.toString( notificationId );
                correlatedNotificationValue.setNotificationIds( new String[] {correlatedNotificationId} );

                clearedAlarmEvent.setCorrelatedNotifications(
                    new CorrelatedNotificationValue[]{correlatedNotificationValue} );
            } catch ( UnsupportedAttributeException e ) {
                // Alarm Monitor might not support the attribute.
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "exception="+e);
                Log.write(this,Log.LOG_MINOR,
                    "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                    "Unsupported attribute="+e.getAttributeName() );
            }

            // store in "global" alarmEvent variable
            alarmEvent = clearedAlarmEvent;
            alarmEventPropertyDescriptor = epd;

            Log.write(this,Log.LOG_ALL,
                "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
                "Set perceived severity (CLEARED)...");
            clearedAlarmEvent.setPerceivedSeverity( PerceivedSeverity.CLEARED );

            // update associated alarm key.
            // Note, this seems a little strange, should really the alarmKey be the same
            // as for previously sent alarm?
            AlarmKey alarmKey = alarmEvent.makeAlarmKey();
            alarmKey.setAlarmPrimaryKey( entity.getThresholdAlarmPrimaryKey() ); // same key again, is that really supposed to be like that?
            alarmEvent.setAlarmKey( alarmKey );
        }

        //
        // Continue and fill in the common information for alarm events.
        //

        // javax.oss.fm.monitor.AlarmEvent specific methods
        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set alarm type...");
        alarmEvent.setAlarmType( alarmConfig.getAlarmType() );

        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set probable cause...");
        alarmEvent.setProbableCause( alarmConfig.getProbableCause() );

        // javax.oss.util.IRPEvent specific methods
        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set managed object class...");
        alarmEvent.setManagedObjectClass( pde.getManagedObjectClass() );

        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set managed object instance...");

        //
        // This should be changed to the ThresholdMonitor instead of the
        // PerformanceMonitor!
        //
        alarmEvent.setManagedObjectInstance( pde.getManagedObjectInstance() );

        long currentTime = System.currentTimeMillis();
        String time = Long.toString( currentTime );
        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set notification id...");
        entity.setThresholdAlarmNotificationId( currentTime );
        alarmEvent.setNotificationId( time );

        // javax.oss.Event specific methods
        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "Set event time...");
        alarmEvent.setEventTime( pde.getEventTime() ); // using the performance event time, correct?

        // include application DN if that is configured.
        ThresholdMonitorKey key = tmValue.getThresholdMonitorKey();
        String appDN = key.getApplicationDN();
        if ( appDN != null ) {
            alarmEvent.setApplicationDN( appDN );
        }

        // send the event
        Log.write(this,Log.LOG_ALL,
            "sendEvent(entity,pde,observedValue,thresholdMonitorStatus)",
            "call sendEvent()");
        sendEvent( alarmEvent, alarmEventPropertyDescriptor );
    }

    /**
     * Send an event to publisher (Alarm Monitor). The event object is put into a
     * <code>javax.jms.ObjectMessage</code> and the filterable properties are added to the
     * message.
     *
     * @param alarmEvent the alarm event to send
     * @param descriptor the descriptor for the event
     * @see javax.oss.fm.monitor.AlarmEventPropertyDescriptor
     * @see javax.oss.util.IRPEventPropertyDescriptor
     */
    private void sendEvent( AlarmEvent alarmEvent, AlarmEventPropertyDescriptor descriptor ) {
        Log.write(this,Log.LOG_ALL,"sendEvent(alarmEvent,descriptor)","called");
        ObjectMessage objMsg = null;

        try {
            // Create object message, passing the event as value.
            // It will be serialized in the object message.
            Log.write("Create object message...");
            objMsg = alarmTopicSession.createObjectMessage( alarmEvent );

            // Set filterable properties.

            // AlarmEventPropertyDescriptor:
            //  ALARM_TYPE is set to the same value event.getAlarmType() returns
            Log.write("Set " + AlarmEventPropertyDescriptor.ALARM_TYPE_PROP_NAME + "...");
            objMsg.setStringProperty( AlarmEventPropertyDescriptor.ALARM_TYPE_PROP_NAME,
                alarmEvent.getAlarmType() );

            //  PERCEIVED_SEVERITY is set to the same value event.getPerceivedSeverity() returns
            if ( alarmEvent.isPopulated( AlarmEvent.PERCEIVED_SEVERITY ) ) {
                Log.write("Set " + AlarmEventPropertyDescriptor.PERCEIVED_SEVERITY_PROP_NAME + "...");
                objMsg.setIntProperty(AlarmEventPropertyDescriptor.PERCEIVED_SEVERITY_PROP_NAME,
                    alarmEvent.getPerceivedSeverity() );
            }

            //  PROBABLE_CAUSE is set to the same value event.getProbableCause() returns
            if ( alarmEvent.isPopulated( AlarmEvent.PROBABLE_CAUSE ) ) {
                Log.write("Set " + AlarmEventPropertyDescriptor.PROBABLE_CAUSE_PROP_NAME + "...");
                objMsg.setIntProperty( AlarmEventPropertyDescriptor.PROBABLE_CAUSE_PROP_NAME,
                    alarmEvent.getProbableCause() );
            }

            // IRPEvent properties:
            //  OSS_EVENT_TYPE is set to the same value eventDescriptor.getEventType() returns
            Log.write("Set " + AlarmEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME + "...");
            objMsg.setStringProperty( AlarmEventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
                descriptor.getEventType() );

            //  OSS_APPLICATION_TYPE_DN is set to the same value event.getApplicationTypeDN() returns
            if ( alarmEvent.isPopulated( AlarmEvent.APPLICATION_DN ) ) {
                Log.write("Set " + AlarmEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME + "...");
                objMsg.setStringProperty( AlarmEventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
                    alarmEvent.getApplicationDN() );
            }

            //  OSS_EVENT_TIME is set to the same value, in string format, event.getEventTime() returns

            // Convert event time into a string in the format 'yyyyMMddHHmmss.SZ'
            // Could be more efficient if the formatter was initialized and then
            // reused instead of creating a new every time.
            Log.write("Convert event time...");
            Date eventTime = alarmEvent.getEventTime();
            Calendar calendar = Calendar.getInstance();
            SimpleTimeZone UTCTimeZone = new SimpleTimeZone(0, "UTC");
            calendar.setTimeZone( UTCTimeZone );
            calendar.setTime( eventTime );
            SimpleDateFormat eventTimeFormater = new SimpleDateFormat();
            eventTimeFormater.setCalendar(calendar);
            eventTimeFormater.applyPattern("yyyyMMddHHmmss.S'Z'");
            String eventTimeString = eventTimeFormater.format( calendar.getTime() );
            // set event time
            Log.write("Set " + AlarmEventPropertyDescriptor.OSS_EVENT_TIME_PROP_NAME + "...");
            objMsg.setStringProperty( AlarmEventPropertyDescriptor.OSS_EVENT_TIME_PROP_NAME,
                eventTimeString );

            //  OSS_MANAGED_ENTITY_TYPE_PROP_NAME is set to the same value event.getManagedObjectClass() returns
            Log.write("Set " + AlarmEventPropertyDescriptor.OSS_MANAGED_ENTITY_TYPE_PROP_NAME + "...");
            objMsg.setStringProperty( AlarmEventPropertyDescriptor.OSS_MANAGED_ENTITY_TYPE_PROP_NAME,
                alarmEvent.getManagedObjectClass() );

            //  OSS_MANAGED_ENTITY_PK_PROP_NAME is set to the same value event.getManagedObjectInstance() returns
            Log.write("Set " + AlarmEventPropertyDescriptor.OSS_MANAGED_ENTITY_PK_PROP_NAME + "...");
            objMsg.setStringProperty( AlarmEventPropertyDescriptor.OSS_MANAGED_ENTITY_PK_PROP_NAME,
                alarmEvent.getManagedObjectInstance() );

            // publish the event
            Log.write(this,Log.LOG_ALL,"sendEvent(alarmEvent,descriptor)","publishing alarm event...");
            alarmTopicPublisher.publish( objMsg );
            Log.write(this,Log.LOG_ALL,"sendEvent(alarmEvent,descriptor)","alarm event published!");
        } catch ( JMSException e ) {
            Log.write(this,Log.LOG_MAJOR,"sendEvent(alarmEvent,descriptor)","exception="+e);
        }
    }


    /**
     * Initialize a topic publisher for threshold alarm events.
     */
    private void initTopicPublisher() {
        Log.write(this,Log.LOG_ALL,"initTopicPublisher()","called");
        try {
            Context ctx = new InitialContext();
            // get topic connection factory

            System.out.println("***************************debug1 initTopicPublisher*************");
	    System.out.println("debug1:get topic connection factory: " + ALARM_MONITOR_TOPIC_CONNECTION_FACTORY );

            Log.write("get topic connection factory: " + ALARM_MONITOR_TOPIC_CONNECTION_FACTORY );
            TopicConnectionFactory topicConnectionFactory = 
				(TopicConnectionFactory) ctx.lookup(ALARM_MONITOR_TOPIC_CONNECTION_FACTORY);

	    System.out.println("debug1:topicConnectionFactory: " + topicConnectionFactory);
	    System.out.println("debug1:get topic: " + ALARM_MONITOR_EVENT_MESSAGE_TOPIC );

            // get topic
            Log.write("get topic: " + ALARM_MONITOR_EVENT_MESSAGE_TOPIC );
            Topic objectEventTopic = (Topic)ctx.lookup( ALARM_MONITOR_EVENT_MESSAGE_TOPIC );

	    System.out.println(" objectEventTopic: " +  objectEventTopic);
	    System.out.println("debug1:get topic connection: " );

            // get topic connection
            Log.write("get topic connection..." );
            TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();

	    System.out.println(" topicConnection: " +  topicConnection);
            // get session
            Log.write("get topic session...");
            alarmTopicSession = topicConnection.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
	    System.out.println(" alarmTopicSession: " +  alarmTopicSession);
            // get publisher
            Log.write("get publisher...");
            alarmTopicPublisher = alarmTopicSession.createPublisher( objectEventTopic );
	    System.out.println(" alarmTopicPublisher: " +  alarmTopicPublisher);
            Log.write("ok!");
        } catch ( NamingException e ) {
            Log.write(this,Log.LOG_MAJOR,"initTopicPublisher()","exception="+e);
            throw new RuntimeException( e.toString() );
        } catch ( JMSException e ) {
            Log.write(this,Log.LOG_MAJOR,"initTopicPublisher()","exception="+e);
            throw new RuntimeException( e.toString() );
        }
    }
}

