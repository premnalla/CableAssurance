/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.report;

import java.lang.reflect.Method;
import javax.oss.cbe.report.ReportFormat;
import javax.oss.cbe.report.ReportData;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.report.ReportData</CODE> interface.  
 * 
 * @see javax.oss.cbe.report.ReportData
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ReportDataImpl
implements ReportData
{ 

    /**
     * Constructs a new ReportDataImpl with empty attributes.
     * 
     */

    public ReportDataImpl() {
    }

    public String[] attributeTypeForReportFormat() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.report.ReportFormat";
        return list;
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

    private java.lang.Object _reportdataimpl_report = null;
    private javax.oss.cbe.report.ReportFormat _reportdataimpl_reportFormat = null;



    /**
     * Returns this ReportDataImpl's performanceMonitorReport
     * 
     * @return the performanceMonitorReport
     * 
    */

    public java.lang.Object getPerformanceMonitorReport() {
        // TODO There is no setter for this value
        // The Value have to be computed
        return null;
    }

    /**
     * Changes the report to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new report for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setReport(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _reportdataimpl_report = value;
    }


    /**
     * Returns this ReportDataImpl's report
     * 
     * @return the report
     * 
    */

    public java.lang.Object getReport() {
        return _reportdataimpl_report;
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
        _reportdataimpl_reportFormat = value;
    }


    /**
     * Returns this ReportDataImpl's reportFormat
     * 
     * @return the reportFormat
     * 
    */

    public javax.oss.cbe.report.ReportFormat getReportFormat() {
        return _reportdataimpl_reportFormat;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ReportData val = null;
        try { 
            val = (ReportData)super.clone();

            if( this.getReport()!=null) 
                val.setReport((java.lang.Object)clone(getReport()));
            else
                val.setReport( null );

            if( this.getReportFormat()!=null) 
                val.setReportFormat((javax.oss.cbe.report.ReportFormat)clone(getReportFormat()));
            else
                val.setReportFormat( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ReportDataImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    
    private Object clone(Object obj) {
        Object clone = obj;
        if ((obj != null) && obj instanceof Cloneable) {
            try {
                Method method = obj.getClass().getMethod("clone",(Class[]) null);
                clone = method.invoke(obj, (Object[])null);
            } catch (Exception e) {
                return null;
            }
        }
        return clone;
    }

    /**
     * Checks whether two ReportData are equal.
     * The result is true if and only if the argument is not null 
     * and is an ReportData object that has the arguments.
     * 
     * @param value the Object to compare with this ReportData
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ReportData)) {
            ReportData argVal = (ReportData) value;
            if( !Utils.compareAttributes( getPerformanceMonitorReport(), argVal.getPerformanceMonitorReport())) { return false; } 
            if( !Utils.compareAttributes( getReport(), argVal.getReport())) { return false; } 
            if( !Utils.compareAttributes( getReportFormat(), argVal.getReportFormat())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
