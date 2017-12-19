/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.report;

import java.util.Calendar;
import java.net.URL;
import javax.oss.cbe.report.ReportFormat;
import javax.oss.cbe.report.ReportInfo;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.report.ReportInfo</CODE> interface.  
 * 
 * @see javax.oss.cbe.report.ReportInfo
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ReportInfoImpl
implements ReportInfo
{ 

    /**
     * Constructs a new ReportInfoImpl with empty attributes.
     * 
     */

    public ReportInfoImpl() {
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

    private java.net.URL _reportinfoimpl_URL = null;
    private java.util.Calendar _reportinfoimpl_expirationDate = null;
    private javax.oss.cbe.report.ReportFormat _reportinfoimpl_reportFormat = null;


    /**
     * Changes the URL to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new URL for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setURL(java.net.URL value)
    throws java.lang.IllegalArgumentException    {
        _reportinfoimpl_URL = value;
    }


    /**
     * Returns this ReportInfoImpl's URL
     * 
     * @return the URL
     * 
    */

    public java.net.URL getURL() {
        return _reportinfoimpl_URL;
    }

    /**
     * Changes the expirationDate to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new expirationDate for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setExpirationDate(java.util.Calendar value)
    throws java.lang.IllegalArgumentException    {
        _reportinfoimpl_expirationDate = value;
    }


    /**
     * Returns this ReportInfoImpl's expirationDate
     * 
     * @return the expirationDate
     * 
    */

    public java.util.Calendar getExpirationDate() {
        return _reportinfoimpl_expirationDate;
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
        _reportinfoimpl_reportFormat = value;
    }


    /**
     * Returns this ReportInfoImpl's reportFormat
     * 
     * @return the reportFormat
     * 
    */

    public javax.oss.cbe.report.ReportFormat getReportFormat() {
        return _reportinfoimpl_reportFormat;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ReportInfo val = null;
        try { 
            val = (ReportInfo)super.clone();

            val.setURL(this.getURL());

            if( this.getExpirationDate()!=null) 
                val.setExpirationDate((java.util.Calendar)((java.util.Calendar) this.getExpirationDate()).clone());
            else
                val.setExpirationDate( null );

            if( this.getReportFormat()!=null) 
                val.setReportFormat((javax.oss.cbe.report.ReportFormat)((javax.oss.cbe.report.ReportFormat) this.getReportFormat()).clone());
            else
                val.setReportFormat( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ReportInfoImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two ReportInfo are equal.
     * The result is true if and only if the argument is not null 
     * and is an ReportInfo object that has the arguments.
     * 
     * @param value the Object to compare with this ReportInfo
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ReportInfo)) {
            ReportInfo argVal = (ReportInfo) value;
            if( !Utils.compareAttributes( getURL(), argVal.getURL())) { return false; } 
            if( !Utils.compareAttributes( getExpirationDate(), argVal.getExpirationDate())) { return false; } 
            if( !Utils.compareAttributes( getReportFormat(), argVal.getReportFormat())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
