
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
import javax.oss.cfi.activity.ReportInfo;


/**
 * Base Interface implementation for the Activity ReportAvailable events.
 * Raised by an OSS/J application, when an Activity has
 * report results ready, to be sent to the client.
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class ActivityReportAvailableEventImpl
extends com.nec.oss.cfi.activity.ri.ActivityEventImpl
implements javax.oss.cfi.activity.ActivityReportAvailableEvent
{
    /**
     * ReportInfo describing the report that is now available
     */
    protected ReportInfo reportInfo;
    
    /**
     * Default constructor -
     * Creates new ActivityReportAvailableEvent empty instance.
     */
    public ActivityReportAvailableEventImpl()
    {
    }

    /**
     * Get report information needed to retrieve the report data by
     * the client.
     */
    public ReportInfo getReportInformation()
        throws java.lang.IllegalStateException
    {
        if(reportInfo == null)
        {
            throw new java.lang.IllegalStateException(
                "Report Information event attribute was not set");
        }

        return reportInfo;
    }
    

    /**
     * Set report information needed to retrieve the report data by
     * the client.
     * @return ReportInfo associated with this event
     * @exception java.lang.IllegalStateException thrown when value isn't set
     */
    public void setReportInformation(ReportInfo reportInfo)
        throws java.lang.IllegalArgumentException
    {
        if(reportInfo == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ReportInfo Parameter is not valid");
        }

        this.reportInfo = (ReportInfo) reportInfo.clone();
    }
    

    /**
     * Make an empty instance of report information.
    * @return new instance of ReportInfo
     */
    public ReportInfo makeReportInformation()
    {
        return new ReportInfoImpl();
    }

    /**
     * Deep copy of this key
     * 
     * @return deep copy of this key
     */
    public Object clone()
    {

        ActivityReportAvailableEventImpl myClone = 
					(ActivityReportAvailableEventImpl)super.clone();

        if(reportInfo != null)
        {
            myClone.reportInfo = (ReportInfo) reportInfo.clone();
        }
        
        return myClone;
    }

    /**
     * Compare two instances of this class.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     *
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof ActivityReportAvailableEventImpl))
        {
            return false;
        }
		ActivityReportAvailableEventImpl localOther =
					(ActivityReportAvailableEventImpl) other;

        boolean result = true;

		if (!super.equals(localOther))
		{
			result = false;
		}

		if(reportInfo != null)
		{
			if(!this.reportInfo.equals(localOther.reportInfo))
			{
				return false;
			}
		}
		else if(this.reportInfo != localOther.reportInfo)
		{
			return false;
		}
        
        return result;
    }
}
