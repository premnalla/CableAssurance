/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.cbe.report;

import ossj.common.Utils;

import javax.oss.cbe.report.CurrentResultReport;
import javax.oss.cbe.report.ReportData;
import javax.oss.cbe.report.ReportInfo;


/**
 * An implementation class for the <CODE>javax.oss.cbe.report.CurrentResultReport</CODE> interface.
 *
 * @see javax.oss.cbe.report.CurrentResultReport
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class CurrentResultReportImpl implements CurrentResultReport {
    // The server decides if the performance data should be returned
    // as a file or as a return value.
    // The Reference Implementation will give a return value (isDataType == true)
    private boolean _currentresultreportImpl_isURLType = false;
    private boolean _currentresultreportImpl_isDataType = true;
    private ReportInfo _currentresultreportImpl_reportInformation = null;
    private ReportData _currentresultreportImpl_reportData = null;
    
    /**
     * Constructs a new CurrentResultReportImpl with empty attributes.
     *
     */
    public CurrentResultReportImpl() {
        super();
    }
    
    /**
     * Constructs a new CurrentResultReportImpl using the given ReportInfo and
     * ReportData assuming isURLType = false and isDataType = true;
     *
     * @param reportInformation
     * @param reportData
     */
    public CurrentResultReportImpl(ReportInfo reportInformation, ReportData reportData) {
        super();
        this._currentresultreportImpl_reportInformation = (ReportInfo) reportInformation.clone();
        this._currentresultreportImpl_reportData = (ReportData) reportData.clone();
    }
    
    /**
     * Constructs a new CurrentResultReportImpl using given parameters
     * @param reportInformation
     * @param reportData
     * @param isUrl
     * @param isdata
     */
    public CurrentResultReportImpl(ReportInfo reportInformation, ReportData reportData,
            boolean isUrl, boolean isdata) {
        super();
        this._currentresultreportImpl_reportInformation = (ReportInfo) reportInformation.clone();
        this._currentresultreportImpl_reportData = (ReportData) reportData.clone();
        this._currentresultreportImpl_isURLType = isUrl;
        this._currentresultreportImpl_isDataType = isdata;
    }
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.report.ReportInfo makeReportInfo(String type){
        if(type.equals("javax.oss.cbe.report.ReportInfo") || type.equals("ossj.common.cbe.report.ReportInfoImpl")) {
            return new ReportInfoImpl();
        } else {
            return null;
        }
    }
    
    public javax.oss.cbe.report.ReportData makeReportData(String type){
        if(type.equals("javax.oss.cbe.report.ReportData") || type.equals("ossj.common.cbe.report.ReportDataImpl")) {
            return new ReportDataImpl();
        } else {
            return null;
        }
    }
    
    public String[] attributeTypeForReportInformation() {
        String [] list = new String[1];
        list[0] = "javax.oss.cbe.report.ReportInfo";
        return list;
    }
    
    public String[] attributeTypeForReportData() {
        String [] list = new String[1];
        list[0] = "javax.oss.cbe.report.ReportData";
        return list;
    }
    
    /**
     * Returns this CurrentResultReportImpl's reportInformation
     *
     */
    public javax.oss.cbe.report.ReportInfo getReportInformation()
    throws java.lang.IllegalStateException {
        return _currentresultreportImpl_reportInformation;
    }
    
    /**
     * Changes the information for this CurrentResultReportImpl
     *
     */
    public void setReportInformation(javax.oss.cbe.report.ReportInfo value) {
        _currentresultreportImpl_reportInformation = value;
    }
    
    /**
     * Returns this CurrentResultReportImpl's dataType
     *
     */
    public boolean isDataType() {
        return _currentresultreportImpl_isDataType;
    }
    
    /**
     * Returns this CurrentResultReportImpl's reportData
     * @return the report data
     */
    public javax.oss.cbe.report.ReportData getReportData()
    throws java.lang.IllegalStateException {
        return _currentresultreportImpl_reportData;
    }
    
    /**
     * Change the data for this CurrentResultReportImpl's
     * @param value the new report data
     */
    public void setReportData(javax.oss.cbe.report.ReportData value) {
        _currentresultreportImpl_reportData = value;
    }
    
    /**
     * Returns this CurrentResultReportImpl's URLType
     * @return true if the type is URL
     */
    public boolean isURLType() {
        return _currentresultreportImpl_isURLType;
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        CurrentResultReport val = null;
        try {
            val = (CurrentResultReport)super.clone();
            
            if( this.getReportData()!=null)
                val.setReportData((javax.oss.cbe.report.ReportData)((javax.oss.cbe.report.ReportData) this.getReportData()).clone());
            else
                val.setReportData( null );
            
            if( this.getReportInformation()!=null)
                val.setReportInformation((javax.oss.cbe.report.ReportInfo)((javax.oss.cbe.report.ReportInfo) this.getReportInformation()).clone());
            else
                val.setReportInformation( null );
            
            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("CurrentResultReportImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    
    /**
     * Checks whether two CurrentResultReport are equal.
     * The result is true if and only if the argument is not null
     * and is an CurrentResultReport object that has the arguments.
     *
     * @param value the Object to compare with this CurrentResultReport
     * @return if the objects are equal; false otherwise.
     */
	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof CurrentResultReport)) {
			CurrentResultReport argVal = (CurrentResultReport) value;
			if( this.isURLType() != argVal.isURLType()) { return false; } 
			if( this.isDataType() != argVal.isDataType()) { return false; } 
			if( !Utils.compareAttributes( getReportData(), argVal.getReportData())) { return false; } 
			if( !Utils.compareAttributes( getReportInformation(), argVal.getReportInformation())) { return false; } 

			return true;
		} else {
			return super.equals(value);
		}
	}
}
