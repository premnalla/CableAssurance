/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorKey;
import javax.oss.cbe.schedule.Schedule;
import ossj.common.cbe.schedule.ScheduleImpl;
import javax.oss.cbe.report.ReportFormat;
import ossj.common.cbe.report.ReportFormatImpl;
import javax.oss.cbe.measurement.PerformanceMonitorValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorValueImpl
extends ossj.common.cbe.EntityValueImpl
implements PerformanceMonitorValue
{ 

    /**
     * Constructs a new PerformanceMonitorValueImpl with empty attributes.
     * 
     */

    public PerformanceMonitorValueImpl() {
        super();
        setManagedEntityKeyInstance( new PerformanceMonitorKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PerformanceMonitorValue.GRANULARITY_PERIOD,
        PerformanceMonitorValue.NAME,
        PerformanceMonitorValue.REPORT_BY_EVENT,
        PerformanceMonitorValue.REPORT_BY_FILE,
        PerformanceMonitorValue.REPORT_FORMAT,
        PerformanceMonitorValue.REPORT_PERIOD,
        PerformanceMonitorValue.SCHEDULE,
        PerformanceMonitorValue.STATE
    };

    private static final String[] settableAttributeNames = {
        PerformanceMonitorValue.GRANULARITY_PERIOD,
        PerformanceMonitorValue.NAME,
        PerformanceMonitorValue.REPORT_BY_EVENT,
        PerformanceMonitorValue.REPORT_BY_FILE,
        PerformanceMonitorValue.REPORT_FORMAT,
        PerformanceMonitorValue.REPORT_PERIOD,
        PerformanceMonitorValue.SCHEDULE,
        PerformanceMonitorValue.STATE
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PerformanceMonitorValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PerformanceMonitorValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PerformanceMonitorValueImpl.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }

    /**
     * Holds the Attribute manager that manage the optional fields
    */
    private static AttributeManager attributemanager = null;

    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }
    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }

    public String[] attributeTypeForReportFormat() {
        return attributeTypeFor("reportFormat");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.report.ReportFormat makeReportFormat(String type){
        if(type.equals("javax.oss.cbe.report.ReportFormat") || type.equals("ossj.common.cbe.report.ReportFormatImpl")) {
            return new ReportFormatImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForSchedule() {
        return attributeTypeFor("schedule");
    }

    public javax.oss.cbe.schedule.Schedule makeSchedule(String type){
        if(type.equals("javax.oss.cbe.schedule.Schedule") || type.equals("ossj.common.cbe.schedule.ScheduleImpl")) {
            return new ScheduleImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.measurement.PerformanceMonitorKey makePerformanceMonitorKey(){
        return (PerformanceMonitorKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "int";
        addAttributeTypes("reportByFile", list);
        list[0] = "int";
        addAttributeTypes("reportPeriod", list);
        list[0] = "int";
        addAttributeTypes("reportByEvent", list);
        list[0] = "java.lang.String";
        addAttributeTypes("state", list);
        list[0] = "javax.oss.cbe.schedule.Schedule";
        addAttributeTypes("schedule", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
        list[0] = "javax.oss.cbe.report.ReportFormat";
        addAttributeTypes("reportFormat", list);
        list[0] = "int";
        addAttributeTypes("granularityPeriod", list);
    }

    private int _performancemonitorvalueimpl_granularityPeriod;
    private java.lang.String _performancemonitorvalueimpl_name = null;
    private int _performancemonitorvalueimpl_reportByEvent;
    private int _performancemonitorvalueimpl_reportByFile;
    private javax.oss.cbe.report.ReportFormat _performancemonitorvalueimpl_reportFormat = null;
    private int _performancemonitorvalueimpl_reportPeriod;
    private javax.oss.cbe.schedule.Schedule _performancemonitorvalueimpl_schedule = null;
    private java.lang.String _performancemonitorvalueimpl_state = null;


    /**
     * Changes the granularityPeriod to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new granularityPeriod for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setGranularityPeriod(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.GRANULARITY_PERIOD);
        _performancemonitorvalueimpl_granularityPeriod = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's granularityPeriod
     * 
     * @return the granularityPeriod
     * 
    */

    public int getGranularityPeriod()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.GRANULARITY_PERIOD);
        return _performancemonitorvalueimpl_granularityPeriod;
    }

    /**
     * Changes the name to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new name for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.NAME);
        _performancemonitorvalueimpl_name = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.NAME);
        return _performancemonitorvalueimpl_name;
    }

    /**
     * Changes the performanceMonitorKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new performanceMonitorKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerformanceMonitorKey(javax.oss.cbe.measurement.PerformanceMonitorKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this PerformanceMonitorValueImpl's performanceMonitorKey
     * 
     * @return the performanceMonitorKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorKey getPerformanceMonitorKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.measurement.PerformanceMonitorKey)getEntityKey();
    }

    /**
     * Changes the reportByEvent to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new reportByEvent for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setReportByEvent(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.REPORT_BY_EVENT);
        _performancemonitorvalueimpl_reportByEvent = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's reportByEvent
     * 
     * @return the reportByEvent
     * 
    */

    public int getReportByEvent()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.REPORT_BY_EVENT);
        return _performancemonitorvalueimpl_reportByEvent;
    }

    /**
     * Changes the reportByFile to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new reportByFile for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setReportByFile(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.REPORT_BY_FILE);
        _performancemonitorvalueimpl_reportByFile = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's reportByFile
     * 
     * @return the reportByFile
     * 
    */

    public int getReportByFile()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.REPORT_BY_FILE);
        return _performancemonitorvalueimpl_reportByFile;
    }

    /**
     * Changes the reportFormat to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new reportFormat for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setReportFormat(javax.oss.cbe.report.ReportFormat value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.REPORT_FORMAT);
        _performancemonitorvalueimpl_reportFormat = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's reportFormat
     * 
     * @return the reportFormat
     * 
    */

    public javax.oss.cbe.report.ReportFormat getReportFormat()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.REPORT_FORMAT);
        return _performancemonitorvalueimpl_reportFormat;
    }

    /**
     * Changes the reportPeriod to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new reportPeriod for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setReportPeriod(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.REPORT_PERIOD);
        _performancemonitorvalueimpl_reportPeriod = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's reportPeriod
     * 
     * @return the reportPeriod
     * 
    */

    public int getReportPeriod()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.REPORT_PERIOD);
        return _performancemonitorvalueimpl_reportPeriod;
    }

    /**
     * Changes the schedule to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new schedule for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSchedule(javax.oss.cbe.schedule.Schedule value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.SCHEDULE);
        _performancemonitorvalueimpl_schedule = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's schedule
     * 
     * @return the schedule
     * 
    */

    public javax.oss.cbe.schedule.Schedule getSchedule()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.SCHEDULE);
        return _performancemonitorvalueimpl_schedule;
    }

    /**
     * Changes the state to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new state for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorValue.STATE);
        _performancemonitorvalueimpl_state = value;
    }


    /**
     * Returns this PerformanceMonitorValueImpl's state
     * 
     * @return the state
     * 
    */

    public java.lang.String getState()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorValue.STATE);
        return _performancemonitorvalueimpl_state;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorValue val = null;
            val = (PerformanceMonitorValue)super.clone();

            if( isPopulated(PerformanceMonitorValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(PerformanceMonitorValue.REPORT_FORMAT)) {
                if( this.getReportFormat()!=null) 
                    val.setReportFormat((javax.oss.cbe.report.ReportFormat)((javax.oss.cbe.report.ReportFormat) this.getReportFormat()).clone());
                else
                    val.setReportFormat( null );
            }

            if( isPopulated(PerformanceMonitorValue.SCHEDULE)) {
                if( this.getSchedule()!=null) 
                    val.setSchedule((javax.oss.cbe.schedule.Schedule)((javax.oss.cbe.schedule.Schedule) this.getSchedule()).clone());
                else
                    val.setSchedule( null );
            }

            if( isPopulated(PerformanceMonitorValue.STATE)) {
                if( this.getState()!=null) 
                    val.setState(((java.lang.String) new String( this.getState())));
                else
                    val.setState( null );
            }

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorValue object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorValue)) {
            PerformanceMonitorValue argVal = (PerformanceMonitorValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
