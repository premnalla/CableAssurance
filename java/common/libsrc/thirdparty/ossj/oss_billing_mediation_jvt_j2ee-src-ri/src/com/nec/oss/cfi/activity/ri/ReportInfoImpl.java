
package com.nec.oss.cfi.activity.ri;

/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports.
 */
import java.net.URL;
import java.net.MalformedURLException;

import java.util.Calendar;
/**
 * Spec imports.
 */
import javax.oss.cfi.activity.ReportFormat;


/**
 * This interface represents information related to a generated Report file.
 */
public class ReportInfoImpl
implements javax.oss.cfi.activity.ReportInfo
{
    /**
     * Members
     */
    private URL uRL;
    private Calendar expirationDate;
    private ReportFormat reportFormat;
    private Object additionalInfo;
    
    /**
     * Constructor - empty
     */
    public ReportInfoImpl()
    {
    }
    
    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {
        ReportInfoImpl clone = null;
        
        try
        {
            clone = (ReportInfoImpl) super.clone();
            
            if(reportFormat != null)
            {
                clone.reportFormat = (ReportFormat) reportFormat.clone();
            }
            
            
            if(expirationDate != null)
            {
                clone.expirationDate = (Calendar) expirationDate.clone();
            }
            

            if(uRL != null)
            {
                try
                {
                    clone.uRL = new URL(uRL.toString());
                }
                catch (MalformedURLException e)
                {
                    clone.uRL = null;
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
                }
            }

        }
        
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }
        return clone;
    }
    
    /**
     * Returns a valid non-null expiration date, if set. Otherwise,
     * throws exception.
     *
     * @return expiration date for the generated report
     * @exception java.lang.IllegalStateException Thrown if the expiration date
     * isn't set
     */
    public Calendar getExpirationDate()
        throws java.lang.IllegalStateException
    {
        if(expirationDate == null)
        {
            throw new java.lang.IllegalStateException(
                "ExpirationDate attribute is not set");
        }
        

        return ((Calendar) expirationDate.clone());
        
    }
    
    /**
     * If this operation returns false, it means expiration date is
     * forever. We are not using <b>null</b> as the special value to
     * indicate this, as it is not a good practice.
     * Defaults to false.
     * @return Status of the expiration date
     */
    public boolean isSetExpirationDate()
    {
        if(expirationDate == null)
        {
            return false;
        }
        return true;
    }
            
    /**
     * Get the ReportFormat defining the generated report.
     *
     * @return ReportFormat for the generated report
     * @exception java.lang.IllegalStateException Thrown if the report format
     * isn't set
     */
    public ReportFormat getReportFormat()
        throws java.lang.IllegalStateException
    {
        if(reportFormat == null)
        {
            throw new java.lang.IllegalStateException(
                "ReportFormat attribute is not set");
        }
        
        return (ReportFormat)reportFormat.clone();
            
    }
    
    /**
     * A report has an associated URL. Get this URL value.
     *
     * @return URL for the generated report
     * @exception java.lang.IllegalStateException Thrown if the URL
     * isn't set
     */
    public URL getURL()
        throws java.lang.IllegalStateException
    {
        URL newuRL = null;

        if(uRL == null)
        {
            throw new java.lang.IllegalStateException(
                "URL attribute is not set.");
        }
        
        try
        {
            newuRL = new URL(uRL.toString());
        }
        catch (MalformedURLException e)
        {
            throw new java.lang.IllegalStateException(
                "Exception ocurred in URL-creation: " + e);
        }
        return newuRL;
    }
    
    /**
     * Create a new instance of the ReportFormat object
     *
     * @return A new instance of the ReportFormat object
     */
    public ReportFormat makeReportFormat()
    {
        return new ReportFormatImpl();
    }
    
    /**
     * Sets expiration date to a valid non-null value. Otherwise,
     * throws exception.
     *
     * @param calendar expiration date of the generated report
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * expiration date isn't valid
     */
    public void setExpirationDate(Calendar expirationDate)
        throws java.lang.IllegalArgumentException
    {
        if(expirationDate == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid ExpirationDate parameter");
        }

        this.expirationDate = (Calendar) expirationDate.clone();
        
    }
    
    /**
     * Set the ReportFormat defining the generated report.
     *
     * @param reportFormat ReportFormat for the generated report
     * @exception java.lang.IllegalArgumentException Thrown if the input report
     * format isn't valid
     */
    public void setReportFormat(ReportFormat reportFormat)
        throws java.lang.IllegalArgumentException        
    {
        if(reportFormat == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid ReportFormat parameter");
        }

        this.reportFormat = (ReportFormat) reportFormat.clone();
    }
    
    /**
     * A report has an associated URL. Set this URL value.
     *
     * @param uRL URL for the generated report
     * @exception java.lang.IllegalArgumentException Thrown if the input URL
     * isn't valid
     */
    public void setURL(URL uRL)
        throws java.lang.IllegalArgumentException 
    {
        if(uRL == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid URL parameter");
        }
        try
        {
            this.uRL = new URL(uRL.toString());
        }
        
        catch (MalformedURLException e)
        {
            throw new java.lang.IllegalArgumentException(
                "Exception ocurred in copying url parameter: " + e);
        }
    }
    
    /**
     * In the case of external protocol based transfers,
     * there is a need for more specific information on how the
     * information can be retrieved by the client or pushed
     * to the client. This attribute can be used to store
     * such an implemenation specific details about the protocol.
     * 
     * @return supporting info for the generated report
     * @exception java.lang.IllegalStateException Thrown if the suppporting
     * information isn't set. Always thrown for this implementation
     */
    
    public Object getAdditionalInfo()
        throws java.lang.IllegalStateException
    {
        if(additionalInfo == null)
        {
            throw new java.lang.IllegalStateException(
                "AdditionalInfo attribute is not set");
        }
        
/*        
		NOTE: This is similar to the processing which should be performed

        Object newAdditionalInfo = null;
        try
        {
            newAdditionalInfo = additionalInfo.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new java.lang.IllegalStateException(
                "AdditionalInfo attribute could not be cloned");
        }
        
        return newAdditionalInfo;
        */
        return additionalInfo;
    }

    /**
     * In the case of external protocol based transfers,
     * there is a need for more specific information on how the
     * information can be retrieved by the client or pushed
     * to the client. This attribute can be used to store
     * such an implemenation specific details about the protocol.
     *
     * @param additionalInfo Supporting info for the generated report
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * info object isn't valid. Always thrown for this implementation
     */
    public void setAdditionalInfo(Object additionalInfo)
        throws java.lang.IllegalArgumentException        
    {
        if(additionalInfo == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Invalid AdditionalInfo parameter");
        }
/**
		NOTE: This is similar to the processing which should be performed

        try
        {
            this.additionalInfo = additionalInfo.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new java.lang.IllegalArgumentException(
                "AdditionalInfo parameter could not be cloned.");
        }
        */

        this.additionalInfo = additionalInfo;

    }

	/*
     * Deep equality checking of the instance.
     *
     * @return Flag indicating if the objects were equal
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof ReportInfoImpl))
        {
            return false;
        }

        ReportInfoImpl localOther = 
							(ReportInfoImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (uRL != null)
			{
				if  (!(uRL.equals(localOther.uRL)))
				{
					return false;
				}
			}
			else if(uRL != localOther.uRL)
			{
				return false;
			}

			if (expirationDate != null)
			{
				if  (!(expirationDate.equals(localOther.expirationDate)))
				{
					return false;
				}
			}
			else if(expirationDate != localOther.expirationDate)
			{
				return false;
			}

			if (reportFormat != null)
			{
				if  (!(reportFormat.equals(localOther.reportFormat)))
				{
					return false;
				}
			}
			else if(reportFormat != localOther.reportFormat)
			{
				return false;
			}

			if (additionalInfo != null)
			{
				if  (!(additionalInfo.equals(localOther.additionalInfo)))
				{
					return false;
				}
			}
			else if(additionalInfo != localOther.additionalInfo)
			{
				return false;
			}

		}
		else
		{
			return false;
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

        if(uRL != null)
        {
            hashCode = uRL.hashCode();
        }

        if(expirationDate != null)
        {
            hashCode ^= expirationDate.hashCode();
        }

        if(reportFormat != null)
        {
            hashCode ^= reportFormat.hashCode();
        }

        if(additionalInfo != null)
        {
            hashCode ^= additionalInfo.hashCode();
        }

		return hashCode;
	}
}
