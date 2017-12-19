
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports
 */
import javax.oss.cfi.activity.ReportFormat;


/**
 * Base Interface implementation for the Activity ReportData events.
 * Raised by an OSS/J application, when individual Activities are
 * removed.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityReportDataEventImpl
extends com.nec.oss.cfi.activity.ri.ActivityEventImpl
implements javax.oss.cfi.activity.ActivityReportDataEvent
{

    /**
     * Report data embedded in this event.
     */
    protected Object reportData;

    /**
     * Description of the report data embedded in this event.
     */
    protected ReportFormat reportFormat;
    
    /**
     * Default constructor -
     * Creates new ActivityReportDataEvent empty instance.
     */
    public ActivityReportDataEventImpl()
    {
    }

    /**
     * Get embedded report data.
     * @return Report data for this event
     * @exception java.lang.IllegalStateException Thrown if the report data is
     * not set
     */
    public Object getReportData()
        throws java.lang.IllegalStateException
    {
        return reportData;
    }
    

    /**
     * Set report data as a notification attribute.
     *
     * @param reportData Report data for this event
     * @exception java.lang.IllegalArgumentException Thrown if the report data
     * is invalid
     */
    public void setReportData(Object reportData)
        throws java.lang.IllegalArgumentException
    {
        this.reportData = reportData;
    }
    

    /**
     * Get the report format.
     * @return ReportFormat for this event
     * @exception java.lang.IllegalStateException Thrown if the ReportFormat is
     * not set
     */
    public ReportFormat getReportFormat()
        throws java.lang.IllegalStateException
    {
        if(reportFormat == null)
        {
            throw new java.lang.IllegalStateException(
                "ReportFormat attribute was not set");
        }

        return (ReportFormat) reportFormat.clone();
    }
    
    /**
     * Set the report format.
     *
     * @param reportFormat ReportFormat for this event
     * @exception java.lang.IllegalArgumentException Thrown if the ReportFormat
     * is invalid
     */
    public void setReportFormat(ReportFormat reportFormat)
        throws java.lang.IllegalArgumentException
    {
        if(reportFormat == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ReportFormat parameter is not valid");
            
        }
        
        this.reportFormat = (ReportFormat) reportFormat.clone();
    }
    

    /**
     * Make an empty instance of report format.
     * @return New instance of ReportFormat
     */
    public ReportFormat makeReportFormat()
    {
        return new ReportFormatImpl();
    }


    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {
        ActivityReportDataEventImpl clone = null;

        clone = (ActivityReportDataEventImpl) super.clone();

        if(reportData != null)
        {
            clone.setReportData(getReportData());
        }
       
        if(reportFormat != null)
        {
            clone.reportFormat= (ReportFormat)
                reportFormat.clone();
        }
        
        return clone;
    }

    /**
     * Checks if two ActivityReportDataEvent values are equal.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof ActivityReportDataEventImpl))
        {
            return false;
        }

		ActivityReportDataEventImpl otherMC = (ActivityReportDataEventImpl)other;

		if(otherMC.hashCode() == hashCode())
		{

			if(reportData != null)
			{
				if(!this.reportData.equals(otherMC.reportData))
				{
        			return false;
				}
			}
			else if (this.reportData != otherMC.reportData)
			{
   	    		return false;
			}
	
			if(reportFormat != null)
			{
				if(!this.reportFormat.equals(otherMC.reportFormat))
				{
       		 		return false;
				}
			}
			else if(this.reportFormat != otherMC.reportFormat)
			{
   				return false;
			}
		}
		return true;
   	 }

   /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

		if(reportData != null)
		{
			hashCode = reportData.hashCode();
		}
		if(reportFormat != null)
		{
			hashCode ^= reportFormat.hashCode();
		}

		return hashCode;
	}
}
