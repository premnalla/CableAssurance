package ossj.qos.ri.pm.measurement.eis;


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.jms.*;
import ossj.qos.pm.measurement.*;
import javax.oss.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;
import javax.oss.util.IRPEventPropertyDescriptor;
import java.text.DateFormat;
import ossj.qos.util.ScaledTimeGMT; // The ScaledTimeGMT class is provided by NEC


/**
 * A <code>MonitorJob</code> object is created each time
 * a new Performance Monitor is created.
 *
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */

public class MonitorJob implements Runnable {

  /** A thread.
   */
  private Thread job = new Thread(this);

  /** JMS Session
   */
  private TopicSession topicSession;

  /** JMS Topic
   */
  private TopicPublisher topicPublisher;

  /**
   */
  private PerformanceMonitorValue pmValue;

  /** Where to put the performance data.
   */
  private String ftpURL;

  /** Where to find the data files that are the source
   *  creating the performance data files.
   */
  private String pmDataPath;

  /** How many times faster time should proceed.
   */
  private int timeSpeedupFactor = 1;

  /** An end date used by the NEC class.
   */
  private GregorianCalendar endDate;

  /**
   */
  private ReportInformation reportInformation;

  /** A network model.
   */
  private NetworkModel networkModel;

  /**
   *
   */
  PerformanceMonitorByObjectsValue byObject=null;

  /**
   *
   */
  long reportGenerateTime;



  /**
   * @param topicPublisher
   * @param topicSession
   * @param pmValue
   * @param reportInformation
   * @param ftpURL
   * @param pmDataPath
   * @param timeSpeedupFactor
   * @param networkModel
   *
   */

  public MonitorJob( TopicPublisher topicPublisher, TopicSession topicSession, PerformanceMonitorValue pmValue, ReportInformation reportInformation,
    String ftpURL, String pmDataPath, String timeSpeedupFactor, NetworkModel networkModel) {
    EisSimulatorImpl.trace.log("->MonitorJob.MonitorJob( TopicPublisher topicPublisher, TopicSession topicSession, PerformanceMonitorValue pmValue, ReportInformation reportInformation," +
      " String ftpURL, String pmDataPath, String timeSpeedupFactor, NetworkModel networkModel)", 1);

    // Init.
    this.topicSession = topicSession;
    this.topicPublisher = topicPublisher;
    this.pmValue = pmValue;
    
    // VP see: NOTE xxx
    if ( pmValue instanceof PerformanceMonitorByObjectsValue )
        byObject = (PerformanceMonitorByObjectsValue) pmValue;
    
    this.ftpURL = ftpURL;
    this.pmDataPath = pmDataPath;
    this.reportInformation = reportInformation;
    this.networkModel = networkModel;
    this.timeSpeedupFactor = Integer.parseInt(timeSpeedupFactor);

    EisSimulatorImpl.trace.log("<-MonitorJob.MonitorJob( TopicPublisher topicPublisher, TopicSession topicSession, PerformanceMonitorValue pmValue, ReportInformation ="+reportInformation+"," +
      " String ftpURL, String pmDataPath, String timeSpeedupFactor, NetworkModel networkModel)", 1);
  }

  public void run() {

    EisSimulatorImpl.trace.log("->MonitorJob.run()", 1);

    // Init
    int measurementCount = 0;
    boolean finished = false;
    Schedule schedule = pmValue.getSchedule();
    WeeklySchedule weeklySchedule = schedule.getWeeklySchedule();
    DailySchedule dailySchedule = schedule.getDailySchedule();
    reportGenerateTime = 0;

    pmValue.setState(PerformanceMonitorState.ACTIVE_OFF_DUTY);

    //Register key with the object that tracks generated reports for each job
    reportInformation.initializeKey((PerformanceMonitorKey) pmValue.getPerformanceMonitorKey());

    // Loop that is finished when the job terminates.
    while ( !Thread.interrupted() && !finished ) {

     //networkModel.loadData("/wrx/CNP/OSSJ/QoSAPI/RI/qospmri/network_model/data/demo/");
      schedule = pmValue.getSchedule();
      
      EisSimulatorImpl.trace.log("-- MonitorJob:run: schedule = "+schedule);
      
      weeklySchedule = schedule.getWeeklySchedule();
      dailySchedule = schedule.getDailySchedule();

      if ( (schedule.isActive()) && (weeklySchedule == null || weeklySchedule.isActive())
           && (dailySchedule == null || dailySchedule.isActive()) ) {
          EisSimulatorImpl.trace.log("-- MonitorJob:run: schedule active");

        if ( reportGenerateTime == 0 )
          reportGenerateTime = System.currentTimeMillis();

        measurementCount++;


        if ( pmValue.getState() != PerformanceMonitorState.SUSPENDED ) {
            
            EisSimulatorImpl.trace.log("-- MonitorJob:run: not suspended..");
            
            pmValue.setState(PerformanceMonitorState.ACTIVE_ON_DUTY);

          //-------------------------------------------
          // Generate the report and publish report!
          // ------------------------------------------

          if ( pmValue.getGranularityPeriod() != 0 ) {

            // Init with dummy values.
              String[] report = new String[] {"", "error.xml"};
              
              
              
              // Go and get a performance report!
              // VP NOTE xxx issue here:
              // the byObject is set only if the performance is a byObject one.
              // byObject.xxx throws a null pointer exception
              // change that witha test if (byObject != null) ... else ???
              
            try {
              report = networkModel.getReport( byObject.getObservedObjects(), byObject.getMeasurementAttributes(),
                                               pmValue.getGranularityPeriod(), new Date(reportGenerateTime) );
            }
            catch (Exception e) {
                EisSimulatorImpl.trace.log("-- run: networkModel.getReport failure, Exception:");
                EisSimulatorImpl.trace.log(e.toString(), 3);
            }

            EisSimulatorImpl.trace.log("Generating report for measurement: " + pmValue.getName());
            EisSimulatorImpl.trace.log("With key: " + pmValue.getPerformanceMonitorKey());

            if ( pmValue.getReportByEvent() == ReportMode.EVENT_SINGLE )
              generateReportToNotification(report[0]);

            int reportPeriod = -1;
            if (pmValue.isPopulated(PerformanceMonitorValue.REPORTING_PERIOD) == true) {
              reportPeriod = pmValue.getReportPeriod();
            }

            if ( (reportPeriod == measurementCount) && (pmValue.getReportByFile() == ReportMode.FILE_SINGLE) ) {
              generateReportToFile(report[0], report[1]);
              measurementCount = 0;
            }
          }
          // End of publishing
        }

        // --------------------------------
        // Check schedule
        // --------------------------------

        if ( schedule.isActive() ) {

          if (pmValue.getGranularityPeriod() == 0) {
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
          } else {
            long timeUntilNextReport;

            do {
              reportGenerateTime = reportGenerateTime + (pmValue.getGranularityPeriod() * 1000) / timeSpeedupFactor;
              timeUntilNextReport = reportGenerateTime - System.currentTimeMillis();
            }
            while ( timeUntilNextReport < 0 );

            try {
              Thread.sleep(timeUntilNextReport);
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
                break;
            }
          }
          
        } else {
            pmValue.setState(PerformanceMonitorState.ACTIVE_OFF_DUTY);
            finished = true;
            EisSimulatorImpl.objIdentifier.remove(pmValue.getPerformanceMonitorKey());
        }
        
      }
      // -------------------------------
      // The monitor job is not active!
      // -------------------------------
      else {
          EisSimulatorImpl.trace.log("-- MonitorJob:run: job is not active!");
          pmValue.setState(PerformanceMonitorState.ACTIVE_OFF_DUTY);
          Calendar now = Calendar.getInstance();
          
          if (schedule.getStopTime() != null && schedule.getStopTime().before(now) == true) {
              finished = true;
              EisSimulatorImpl.objIdentifier.remove(pmValue.getPerformanceMonitorKey());
          }
          else {
              try {
                  Thread.sleep(500);
              }
              catch (InterruptedException e) {
                  e.printStackTrace();
                  break;
              }
          }
      }
    } // end of while
    EisSimulatorImpl.trace.log("<-MonitorJob.run()", 1);
  }
    
    

  private boolean isJobOnDuty() {

    Schedule schedule = pmValue.getSchedule();
    WeeklySchedule weeklySchedule = schedule.getWeeklySchedule();
    DailySchedule dailySchedule = schedule.getDailySchedule();

    schedule = pmValue.getSchedule();
    weeklySchedule = schedule.getWeeklySchedule();
    dailySchedule = schedule.getDailySchedule();

    if ( (schedule.isActive()) && (weeklySchedule == null || weeklySchedule.isActive())
      && (dailySchedule == null || dailySchedule.isActive()) )
      return true;
    else
      return false;

  }



  private void generateReportToNotification(String performanceReport) {
   
    ////System.out.println("wrx--in eis,monitorjob: " + performanceReport);

    EisSimulatorImpl.trace.log("->MonitorJob.generateReportToNotification()", 1);

    PerformanceDataEventDescriptor pded = new PerformanceDataEventDescriptorImpl();
    PerformanceDataEvent pde = pded.makePerformanceDataEvent();
    pde.setPerformanceMonitorReport(performanceReport);
    ReportFormat format = pmValue.getReportFormat();
    pde.setReportFormat(format);
    Date dateNow = new Date();
    pde.setEventTime(dateNow);
    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd_hh-mm-ss");
    String nowStr = sdf.format(dateNow);
    PerformanceMonitorKey pmKey = (PerformanceMonitorKey) pmValue.getPerformanceMonitorKey();
    pde.setApplicationDN("Reference Implementation");
    pde.setManagedObjectClass(pmKey.getType());
    //System.out.println("wrx-in eis,monitor job:setManagedObjectClass " + pmKey.getType()); 
    pde.setManagedObjectInstance(pmKey.getPerformanceMonitorPrimaryKey());
    //System.out.println("wrx-in eis,monitor job:setManagedObjectInstance " + pmKey.getPerformanceMonitorPrimaryKey());
    
    try {
        ObjectMessage objMsg = null;
        
        objMsg = topicSession.createObjectMessage(pde);
        if (objMsg != null) {
        //Set properties
        objMsg.setStringProperty(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
          PerformanceDataEventDescriptor.OSS_EVENT_TYPE_VALUE);
        objMsg.setStringProperty(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME, pmKey.getApplicationDN());
        objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_EVENT_TIME_PROP_NAME, nowStr);
        objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_MANAGED_ENTITY_PK_PROP_NAME, pmKey.getPerformanceMonitorPrimaryKey());
        objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_MANAGED_ENTITY_TYPE_PROP_NAME, pmKey.getType());

        //Publish
        topicPublisher.publish(objMsg);
        System.out.println("send out message");
      }
    }
    catch (JMSException jmse) {
        EisSimulatorImpl.trace.log("An exception occurred in publishing: " + jmse);
    }
    EisSimulatorImpl.trace.log("<-MonitorJob.generateReportToNotification()", 1);
  }

    private void generateReportToFile(String performanceReport, String fileName) {
        EisSimulatorImpl.trace.log("->MonitorJob.generateReportToFile()", 1);

    PerformanceDataAvailableEventDescriptor pdaed = new PerformanceDataAvailableEventDescriptorImpl();
    PerformanceDataAvailableEvent pdae = pdaed.makePerformanceDataAvailableEvent();
    ReportInfo info = pdae.makeReportInformation();
    ReportFormat format = pmValue.getReportFormat();
    info.setReportFormat(format);
    Date dateNow = new Date();
    pdae.setEventTime(dateNow);
    PerformanceMonitorKey pmKey = (PerformanceMonitorKey) pmValue.getPerformanceMonitorKey();

    //pdae.setPerformanceMonitorKey(pmKey);
    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd_hh-mm-ss");
    Date now = new Date();
    String nowStr = sdf.format(now);
    String filepath = pmDataPath + fileName;
    pdae.setApplicationDN("Reference Implementation");
    pdae.setManagedObjectClass(pmKey.getType());
    pdae.setManagedObjectInstance(pmKey.getPerformanceMonitorPrimaryKey());

    try {
      PrintStream fwriter = new PrintStream(new FileOutputStream(filepath));

      fwriter.print(performanceReport);
      fwriter.close();
      EisSimulatorImpl.trace.log("Wrote " + ftpURL + fileName + " to local directory " + filepath);
      URL url = new URL(ftpURL + fileName);

      info.setURL(url);
    }
    catch (FileNotFoundException fe) {
      EisSimulatorImpl.trace.log("An exception occurred in file creation: " + fe, 1);
    }
    catch (MalformedURLException me) {
      EisSimulatorImpl.trace.log("An exception occurred in URL creation: " + me, 1);
    }

    pdae.setReportInformation(info);
    reportInformation.setReportInfo(pmKey, info);

    if (reportInformation == null)

      try {
        ObjectMessage objMsg = null;

        objMsg = topicSession.createObjectMessage(pdae);

        if (objMsg != null) {

          //Set properties
          objMsg.setStringProperty(EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
            PerformanceDataAvailableEventDescriptor.OSS_EVENT_TYPE_VALUE);
          objMsg.setStringProperty(EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME, pmKey.getApplicationDN());
          objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_EVENT_TIME_PROP_NAME, nowStr);
          objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_MANAGED_ENTITY_PK_PROP_NAME, pmKey.getPerformanceMonitorPrimaryKey());
          objMsg.setStringProperty(IRPEventPropertyDescriptor.OSS_MANAGED_ENTITY_TYPE_PROP_NAME, pmKey.getType());

          //Publish
          topicPublisher.publish(objMsg);
        }
      }
      catch (JMSException jmse) {
        EisSimulatorImpl.trace.log("An exception occurred in publishing: " + jmse, 1);
      }

      EisSimulatorImpl.trace.log("<-MonitorJob.generateReportToFile()", 1);
  }


  public String getCurrentResultReport() {

    // Init with dummy values.
    String[] report = new String[] {"", "error.xml"};

    // Go and get a performance report!
    // VP see: NOTE xxx
    
    try {
      report = networkModel.getReport( byObject.getObservedObjects(), byObject.getMeasurementAttributes(),
                                               pmValue.getGranularityPeriod(), new Date(reportGenerateTime) );
    }
    catch (Exception e) {
        EisSimulatorImpl.trace.log("-- MonitorJob.getCurrentResultReport: networkModel.getReport Exception:");
        EisSimulatorImpl.trace.log(e.toString(), 1);
    }

    return report[0];
  }


  public void resume() {


    if ( isJobOnDuty() == true ) {
      pmValue.setState(PerformanceMonitorState.ACTIVE_ON_DUTY);
    }
    else {
      pmValue.setState(PerformanceMonitorState.ACTIVE_OFF_DUTY);
    }
  }

  public void suspend() {
//VP
//System.out.println("******************************");
//System.out.println(pmValue.getState());
    pmValue.setState(PerformanceMonitorState.SUSPENDED);
//System.out.println(pmValue.getState());
//System.out.println("******************************");
  }

  public void begin() {
    EisSimulatorImpl.trace.log("->MonitorJob.begin()", 1);
    job.start();
    EisSimulatorImpl.trace.log("<-MonitorJob.begin()", 1);
  }

  public void quit() {
    EisSimulatorImpl.trace.log("->MonitorJob.quit()", 1);
    job.interrupt();
    EisSimulatorImpl.trace.log("<-MonitorJob.quit()", 1);
  }


  public PerformanceMonitorValue getPerformanceMonitorValue() {
    EisSimulatorImpl.trace.log("->MonitorJob.getPerformanceMonitorValue()", 1);
    EisSimulatorImpl.trace.log("<-MonitorJob.getPerformanceMonitorValue()", 1);
    return pmValue;
  }


}
