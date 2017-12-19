package ossj.qos.pm.measurement;


import javax.oss.ManagedEntityKey;
import javax.oss.pm.measurement.*;
import javax.oss.pm.util.*;

import ossj.qos.pm.measurement.*;
//import ossj.qos.pm.util.*;
import ossj.qos.util.ScheduleImpl;
import ossj.qos.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 0.2
 */

public class PerformanceMonitorValueImpl extends PmManagedEntityValueImpl implements javax.oss.pm.measurement.PerformanceMonitorValue {


  public PerformanceMonitorValueImpl() {
    super();

    map.put(this.NAME, null);
    map.put(this.GRANULARITY_PERIOD, null);
    map.put(this.REPORT_BY_FILE, null);
    map.put(this.REPORT_BY_EVENT, null);
    map.put(this.REPORTING_PERIOD, null);
    map.put(this.REPORT_FORMAT, null);
    map.put(this.SCHEDULE, null);
    map.put(this.STATE, null);
  }

  // ATTRIBUTE OPERATIONS
  //*********************

  /**
   * Returns the name of the measurement job.
   *
   *@return String The name of the measurement job.
   */


  public Object clone() {

    PerformanceMonitorValueImpl clone = null;

      clone = (PerformanceMonitorValueImpl) super.clone();

      ReportFormat rf = null;
      Schedule schedule = null;

      if(this.isPopulated(this.REPORT_FORMAT) == true)
        rf = (ReportFormat) this.getAttributeValue(this.REPORT_FORMAT);

      if(this.isPopulated(this.SCHEDULE) == true)
        schedule = (Schedule) this.getAttributeValue(this.SCHEDULE);

      if(rf != null) {
        ReportFormat rfClone = (ReportFormat) rf.clone();
        clone.setAttributeValue(this.REPORT_FORMAT, rfClone);
      }
      if(schedule != null) {
        Schedule scheduleClone = (Schedule) schedule.clone();
        clone.setAttributeValue(this.SCHEDULE, scheduleClone);
      }
    return clone;
  }



  public String getName() throws java.lang.IllegalStateException{
     if(this.isPopulated(this.NAME) == false){
      //VP
	  //System.out.println("name is not populated");
      throw new java.lang.IllegalStateException();
     }
      return (String) this.getAttributeValue(this.NAME);
  }

  /**
   * Sets the name of the measurement job.
   *
   * <p>
   * The measurement name will be validated when the value object is passed
   * to the performance monitor bean. It is optional to set the name.
   *
   *@param name The name of the measurement job.
   */
  public void setName( String name) throws java.lang.IllegalArgumentException {

    if(name == null)
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.NAME, name);
  }

  /**
   * Gets the granularity period of the measurement job.
   *
   *@return int The granularity period in seconds.
   */



  public int getGranularityPeriod() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.GRANULARITY_PERIOD) == false)
      throw new java.lang.IllegalStateException();

    return  ((Integer) this.getAttributeValue(this.GRANULARITY_PERIOD)).intValue();
  }

  /**
   * Sets the granularity period for the measurement job.
   *
   * <p>
   * The granularity period is the time between the initiation of two successive
   * gatherings of measurement data, within the timeframe specified in the measurement
   * scheduling. Examples of granularity period can be 5 minutes, 15 minutes, 30 minutes,
   * 1 hour. The granularity period of 5 minutes is used in most cases, but for some
   * measurements it may only make sense to collect data in a larger granularity period.
   *
   * <p>
   * The granularity period will be validated when the value object is passed
   * to the performance monitor bean.
   *
   * <p>
   * The granularity period is specified in seconds.
   *
   *@param granularityPeriod The granularity period, in seconds, of the measurement job.
   */
  public void setGranularityPeriod( int granularityPeriod ) throws java.lang.IllegalArgumentException {

    this.setAttributeValue(this.GRANULARITY_PERIOD, new Integer(granularityPeriod));
  }

  /**
   * Gets the file reporting mode.
   *
   *@return int Returns the file reporting mode or ReportMode.NO_REPORT_MODE if file report mode is not set.
   *@see ReportMode
   */



  public int getReportByFile() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.REPORT_BY_FILE) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer) this.getAttributeValue(this.REPORT_BY_FILE)).intValue();
  }

  /**
   * Sets the file reporting mode.
   *
   * <p>
   * If the reporting mode is set to file, the measurement job will capture the
   * measurement result reports into a data storage and then emit an event to the client
   * about the availability of the data.  The frequency of this event is determined by the
   * data storage creation frequency and can not be set by the client. When the client
   * receives the event of data availability, the client can retrieve the data, by using the
   * URL to make a connection to the system.
   *
   *@param reportMode The file reporting mode.
   *@exception IllegalArgumentException Is raised if the reporting mode is not a file reporting mode.
   *@see ReportMode
   */
  public void setReportByFile( int reportMode )
  throws IllegalArgumentException {

   this.setAttributeValue(this.REPORT_BY_FILE, new Integer(reportMode));
  }

  /**
   * Gets the event reporting mode.
   *
   *@return int Returns the event reporting mode or ReportMode.NO_REPORT_MODE if event report mode is not set.
   *@see ReportMode
   */



  public int getReportByEvent() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.REPORT_BY_EVENT) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer)this.getAttributeValue(this.REPORT_BY_EVENT)).intValue();
  }

  /**
   * Sets the event reporting mode.
   *
   * <p>
   * If the reporting mode is set to event the system will emit a event that carries the
   * measurement result reports.
   *
   *@param reportMode The event reporting mode.
   *@exception IllegalArgumentException Is raised if the reporting mode is not a event reporting mode.
   *@see ReportMode
   */
  public void setReportByEvent( int reportMode )
  throws IllegalArgumentException {

    this.setAttributeValue(this.REPORT_BY_EVENT, new Integer(reportMode));

  }

  /**
   * Returns the reporting period for result report of the measurement job.
   *
   *@return int Reporting period in number of granularity periods.
   */



  public int getReportPeriod() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.REPORTING_PERIOD) == false)
      throw new java.lang.IllegalStateException("Report Period has not been set");

    if(this.isPopulated(this.REPORT_BY_FILE) == false)
      throw new java.lang.IllegalStateException("Report By File has not been set");

    if(this.getReportByFile() != ReportMode.FILE_SINGLE)
      throw new java.lang.IllegalStateException("Report mode has not been set with the File Single");

    return ((Integer)this.getAttributeValue(this.REPORTING_PERIOD)).intValue();
  }

  /**
   * Sets the reporting period of result report of the measurement job.
   *
   * <p>
   * The reporting period specifies how often measurement result report will be generated
   * and is valid if reporting mode is set to ReportMode.FILE_SINGLE.
   *
   * <p>
   * With the reporting mode set to ReportMode.FILE_SINGLE, the reporting period is the
   * number of granularity periods between the initiation of two successive result report
   * generation. The result report shall include all data that have been collected since
   * the last result report generation.
   *
   * <p>
   * If the report period is not set, a value equal to the granularity period of the
   * measurement job is used. If reporting period is set to zero and report mode is set
   * only to ReportMode.FILE_SINGLE no reports will be generated.
   *
   *@param period Reporting period in number of granularity periods.
   *@exception IllegalArgumentException Is raised if report period is less then zero (0).
   *@see ReportMode
   */

  public void setReportPeriod( int period ) throws IllegalArgumentException {

    if(this.isPopulated(this.REPORT_BY_FILE) == false)
      throw new java.lang.IllegalArgumentException("Can not set report period when report by file has not been set");

    if(this.getReportByFile() != ReportMode.FILE_SINGLE)
      throw new java.lang.IllegalArgumentException("Can not set report period when Report mode has not been set with the File Single");

    this.setAttributeValue(this.REPORTING_PERIOD, new Integer(period));
  }

  /**
   * Gets the report format of the measurement job.
   *
   *@return ReportFormat The report format of the measurement job.
   */


  public ReportFormat getReportFormat() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.REPORT_FORMAT) == false)
      throw new java.lang.IllegalStateException();

    return (ReportFormat) this.getAttributeValue(this.REPORT_FORMAT);
  }

  /**
   * Sets the report format of the measurement job.
   *
   * <p>
   * The report format defines the format of the result reports to be generated.
   * If the report mode is set to ReportMode.FILE_MULTIPLE or EVENT_MULTIPLE the
   * client should not set the report format.
   *
   * <p>
   * The supporting report format can be retrieved by using the PerformanceMonitorMethods.getReportFormats().
   *
   *@param format Report format of the measurement job.
   */
  public void setReportFormat( ReportFormat format ) throws java.lang.IllegalArgumentException {

    if (format == null)
      throw new java.lang.IllegalArgumentException("Report format is null");

    this.setAttributeValue(this.REPORT_FORMAT, format);
  }

  /**
   * Creates a new instance of the Schedule interface.
   *
   *@return Schedule The created object. The object is empty.
   */



  public javax.oss.pm.util.Schedule makeSchedule() {
    return new ScheduleImpl();
  }



  /**
   * Returns the schedule of the measurement job.
   *
   *@return Date Schedule of the measurement job.
   */
  public javax.oss.pm.util.Schedule getSchedule() throws java.lang.IllegalStateException{

    if(this.isPopulated(this.SCHEDULE) == false)
      throw new java.lang.IllegalStateException();

    return (Schedule) this.getAttributeValue(this.SCHEDULE);
  }

  /**
   * Sets the schedule of the measurement job.
   *
   * <p>
   * The measurement schedule specifies the time frames during which the measurement job
   * will be active. The measurement job is active as soon as the start time - if set - is
   * reached. If no start time is provided, the measurement job shall become active
   * immediately. The measurement job remains active until the stop time - if set - is
   * reached. If no  stop time is specified the measurement job will run
   * indefinitely and can only be stopped by system intervention, i.e. by deleting or
   * suspending the measurement job. The time frame defined by the measurement schedule may
   * contain one or more recording intervals. These recording intervals may repeat on
   * weekly basis. If weekly schedule is omitted the measurement job will run all days of
   * the week. Alternatively the weekly schedule will indicate which days of the week the
   * measurement job will be run. The daily schedule of the measurement schedule specifies
   * the time frames during the day which the measurement job will be active. The time
   * frame defined by the measurement schedule may contain one or more recording intervals.
   * These recording intervals specify the time periods during which the measurement data
   * is collected. The start time and end time define a recording interval, which lie
   * between 00.00 and 24.00 hours, aligned on granularity period boundaries. Thus the
   * length of a recording interval will be a multiple of the granularity period. If daily
   * interval is omitted, the measurement job will run continuously through the day.
   *
   *@param schedule Schedule of the measurement job.
   */
  public void setSchedule( javax.oss.pm.util.Schedule schedule ) throws java.lang.IllegalArgumentException {

    if(schedule == null)
      throw new java.lang.IllegalArgumentException();
    this.setAttributeValue(this.SCHEDULE, schedule);
  }

  /**
   * Gets the state of the measurement job.
   *
   *@return int The state of the measurement job.
   *@see PerformanceMonitorState
   */
  public int getState() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.STATE) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer)this.getAttributeValue(this.STATE)).intValue();
  }

  /**
   * Sets the state of the measurement job.
   *
   *@exception IllegalArgumentException Is raised if the state is not a legal state.
   *@see PerformanceMonitorState
   */
  public void setState(int state)
  throws IllegalArgumentException {

   this.setAttributeValue(this.STATE, new Integer(state));
  }

  /**
   * Creates a new instance of the PerformanceMonitorKey interface.
   *
   *@return PerformanceMonitorKey The created object. The object is empty.
   */
  public PerformanceMonitorKey makePerformanceMonitorKey() {
    return new PerformanceMonitorKeyImpl();
  }

  /**
  * Creates a new instance of the ReportFormat interface.
  *
  * @return ReportFormat The created object.
  */
   public ReportFormat makeReportFormat() {
    return new ReportFormatImpl();
   }


  /**
   * Gets the identification key of the measurement job.
   *
   *@return PerformanceMonitorKey The primary key of the measurement job.
   */
  public PerformanceMonitorKey getPerformanceMonitorKey() throws java.lang.IllegalStateException {
     if(this.isPopulated(this.KEY) == false)
      throw new java.lang.IllegalStateException();

     PerformanceMonitorKey pmKey = (PerformanceMonitorKey) super.getManagedEntityKey();

    return pmKey;
  }

  /**
   * Sets the identification key of the measurement job.
   *
   */
  public void setPerformanceMonitorKey(PerformanceMonitorKey key)  throws java.lang.IllegalArgumentException {

    if(key == null || key.getPerformanceMonitorPrimaryKey() == null)
      throw new java.lang.IllegalArgumentException();

    super.setManagedEntityKey(key);
  }

  public void setManagedEntityKey( ManagedEntityKey key ) throws java.lang.IllegalArgumentException{
    if((key instanceof PerformanceMonitorKey) == false)
        throw new java.lang.IllegalArgumentException();
    super.setManagedEntityKey(key);
  }


 protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);


    if ( attrName != null && attrName.equals( PerformanceMonitorValue.NAME )
        && attrValue != null && attrValue instanceof  String)
      isValid = true;

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.GRANULARITY_PERIOD )
        && attrValue != null && attrValue instanceof Integer ) {
       int granularityPeriod = ((Integer)attrValue).intValue();
       if(granularityPeriod >= 0)
         isValid = true;
    }

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.REPORT_BY_EVENT )
        && attrValue != null && attrValue instanceof Integer ) {
        int reportMode = ((Integer)attrValue).intValue();
        if (  reportMode == ReportMode.EVENT_MULTIPLE ||
              reportMode == ReportMode.EVENT_SINGLE  ||
              reportMode == ReportMode.NO_REPORT_MODE )
          isValid = true;

        // If EVENT_MULTIPLE the report format is not allowed to be set!

        Object reportFormat = null;
        try {
         reportFormat = this.getAttributeValue(this.REPORT_FORMAT);
        }
        catch(java.lang.Exception e){}

        if ( reportFormat != null && reportMode == ReportMode.EVENT_MULTIPLE )
          isValid = false;
    }

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.REPORT_BY_FILE)
        && attrValue != null && attrValue instanceof Integer ) {
        int reportMode = ((Integer)attrValue).intValue();
        if ( reportMode == ReportMode.FILE_MULTIPLE  ||
             reportMode == ReportMode.FILE_SINGLE    ||
             reportMode == ReportMode.NO_REPORT_MODE )
          isValid = true;

        Object reportFormat = null;
        try {
         reportFormat = this.getAttributeValue(this.REPORT_FORMAT);
        }
        catch(java.lang.Exception e){}

        if ( reportFormat != null && reportMode == ReportMode.FILE_MULTIPLE )
          isValid = false;

    }

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.REPORT_FORMAT )
        && attrValue != null && attrValue instanceof ReportFormat ) {

      isValid = true;


      // If EVENT_MULTIPLE or FILE_MULTIPLE already set the report format can not be set.

      Integer reportMode = null;

      Object reportByEvent = null;
      Object reportByFile  = null;

      try {
        reportByEvent = this.getAttributeValue(this.REPORT_BY_EVENT);
      }
    catch (Exception e) {}

      try {
        reportByFile = this.getAttributeValue(this.REPORT_BY_FILE);
      }
      catch (Exception e) {}


      if ( reportByEvent != null ) {
        reportMode = (Integer) reportByEvent;
        if ( reportMode.intValue() == ReportMode.EVENT_MULTIPLE )
          isValid = false;
      }

      if ( reportByFile != null ) {
        reportMode = (Integer) reportByFile;
        if ( reportMode.intValue() == ReportMode.FILE_MULTIPLE )
          isValid = false;
      }
    }

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.REPORTING_PERIOD )
        && attrValue != null && attrValue instanceof Integer ) {
       int period = ((Integer)attrValue).intValue();
       if(period >= 0)
         isValid = true;
    }

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.SCHEDULE )
        && attrValue != null && attrValue instanceof Schedule )
      isValid = true;

    if ( attrName != null && attrName.equals( PerformanceMonitorValue.STATE)
        && attrValue != null && attrValue instanceof Integer ) {
      int state = ((Integer)attrValue).intValue();
      if ( state == PerformanceMonitorState.ACTIVE_OFF_DUTY ||
           state == PerformanceMonitorState.ACTIVE_ON_DUTY   ||
           state == PerformanceMonitorState.SUSPENDED )
           isValid = true;
    }
    return isValid;
  }
}



