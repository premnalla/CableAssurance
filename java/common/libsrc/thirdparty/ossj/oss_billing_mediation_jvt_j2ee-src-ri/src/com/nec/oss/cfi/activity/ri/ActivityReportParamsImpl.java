
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports.
 */
import javax.oss.cfi.activity.ActivityReportParams;
import javax.oss.cfi.activity.ReportFormat;
import javax.oss.cfi.activity.ReportMode;


/**
 * Implements ActivityReportParams interface representing
 * the Reporting parameters of an Activity.
 * The following reporting parameters constitute this interface:
 * <UL>
 * <LI> {@link ReportFormat}: Format of the report results.
 * <LI> long ReportPeriod: Specifies how often the reports will be generated.
 * </UL>
 *
 */
public class ActivityReportParamsImpl
implements ActivityReportParams
{

    /**
     * Members
     */

    /**
     * Format of the report. Default is not set.
     */
    protected ReportFormat reportFormat;

    /**
     * Report mode. Default is
     {@link ReportMode#NO_REPORT_MODE}.
     */
    protected int reportMode = ReportMode.NO_REPORT_MODE;

    /**
     * Indicates how many times the job must be run.
     * Defaults to {@link ActivityReportParams#REPORT_PERIOD_USE_GRANULARITY_PERIOD} value.
     * A negative value indicates that it is not set.
     */
    protected long reportPeriod =
    ActivityReportParams.REPORT_PERIOD_USE_GRANULARITY_PERIOD;

    /**
     * Destination type. Default is
     {@link ActivityReportParams#EVENT_DESTINATION_TYPE_NOT_SET}.
     */
    protected int eventDestinationType =
    ActivityReportParams.EVENT_DESTINATION_TYPE_NOT_SET;
    
    /**
     * Event destination name. It is the name of JMS Queue or Topic,
     * if the eventDestinationType has been set.
     * Prereq: EventDestinationType must be already set.
     */
    protected String eventDestinationName;

    /**
     * Reporting protocol name.
     */
    protected String reportProtocolName;

    /**
     * The version of the reporting protocol being used.
     */
    protected String reportProtocolVersion;
    
    /**
     * OPERATIONS
     */

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        ActivityReportParamsImpl clone = null;
        try
        {
            clone = (ActivityReportParamsImpl) super.clone();

            if(reportFormat != null)
            {
                clone.reportFormat = (ReportFormat) reportFormat.clone();
            }
            
        }
        
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
            
        }

        return clone;
    }
    
    
    /**
     * Make an empty instance of Report format.
     * @return new instance of Report format.
     */
    public ReportFormat makeReportFormat()
    {
        return new ReportFormatImpl();
    }
    
    
    /**
     * Get Report's Format.
     *
     * @return ReportFormat for this ActivityReportParams
     * @exception java.lang.IllegalStateException Thrown if ReportFormat is not
     * set
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
     * Set Report's Format.
     *
     * @param reportFormat ReportFormat for this ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if ReportFormat is
     * no valid
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
     * Get Report Period.
     * @return report period for this ActivityReportParams
     */
    public long getReportPeriod()
    {
        return this.reportPeriod = reportPeriod;
    }
    
    
    /**
     * Set Report Period.
     * @param reportPeriod Report period for this ActivityReportParams
     */
    public void setReportPeriod(long reportPeriod)
    {
        if(reportPeriod < ActivityReportParams.REPORT_PERIOD_NOT_SET)
        {
            this.reportPeriod = ActivityReportParams.REPORT_PERIOD_NOT_SET;
        }
        else
        {
            this.reportPeriod = reportPeriod;
        }
    }

    /**
     * Get the reporting Mode.
     * One of the enumerations in the {@link javax.oss.cfi.activity.ReportMode}
     * interface.
     * @return report mode for this ActivityReportParams
     */
    public int getReportMode()
    {
        return reportMode;
    }
    
    /**
     * Set the file reporting mode. If the reportMode being set is
     * not one of the recognized report mode values
     (see {@link ReportMode}, an
     * exception is thrown.
     * @param reportMode Report mode for this ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if Report mode is
     * not valid
     */
    public void setReportMode(int reportMode)
        throws java.lang.IllegalArgumentException
    {
        if (
            ( reportMode != ReportMode.NO_REPORT_MODE) &&
            ( reportMode != ReportMode.EVENT_SINGLE) &&
            ( reportMode != ReportMode.EVENT_MULTIPLE) &&
            ( reportMode != ReportMode.FILE_SINGLE) &&
            ( reportMode != ReportMode.FILE_MULTIPLE) &&
            ( reportMode != ReportMode.STREAM_SINGLE) &&
            ( reportMode != ReportMode.STREAM_MULTIPLE) &&
            ( reportMode != ReportMode.ITERATOR_SINGLE) &&
            ( reportMode != ReportMode.ITERATOR_MULTIPLE) )
        {
            throw new java.lang.IllegalArgumentException(
                "ReportMode parameter value is not valid");
        }
        this.reportMode = reportMode;
    }
    

    /**
     * Various boolean operations to check the reporting mode.
     */
    /**
     * Returns true if reportMode is other than
     * {@link ReportMode#NO_REPORT_MODE}.
	 * @return flag indicating if the report mode is set
     */
    public boolean isReportModeSet()
    {
        if(reportMode != ReportMode.NO_REPORT_MODE)
        {
            return true;
        }

        return false;
    }

    /**
     * Returns true if reportMode is by file.
     * @see ReportMode
	 * @return flag indicating if the report mode is by file
     */
    public boolean isFileReportMode()
    {
        if ( (reportMode == ReportMode.FILE_SINGLE) ||
             (reportMode == ReportMode.FILE_MULTIPLE) )
        {
            return true;
        }

        return false;
    }
    

    /**
     * Returns true if reportMode is by event.
     * @see ReportMode
	 * @return flag indicating if the report mode is by event
     */
    public boolean isEventReportMode()
    {
        if ( (reportMode == ReportMode.EVENT_SINGLE) ||
             (reportMode == ReportMode.EVENT_MULTIPLE) )
        {
            return true;
        }

        return false;
    }
    
    /**
     * Returns true if reportMode is by stream.
     * @see ReportMode
	 * @return flag indicating if the report mode is by stream
     */
    public boolean isStreamReportMode()
    {
        if ( (reportMode == ReportMode.STREAM_SINGLE) ||
             (reportMode == ReportMode.STREAM_MULTIPLE) )
        {
            return true;
        }

        return false;
    }
    

    /**
     * Returns true if reportMode is by iterator.
     * @see ReportMode
	 * @return flag indicating if the report mode is by iterator
     */
    public boolean isIteratorReportMode()
    {
        if ( (reportMode == ReportMode.ITERATOR_SINGLE) ||
             (reportMode == ReportMode.ITERATOR_MULTIPLE) )
        {
            return true;
        }

        return false;
    }
    

    /**
     * Get the desination's type where the AvailabilityEvent or DataEvent
     * will be sent.
     * <p>
     * This attribute is determined the Server implementation, not by the
     * client. Hence, client should never call this method. When
     * passed to the Bean, this value is checked to make sure that it
     * it is not set. Otherwise, an exception will be thrown at appropriate
     * time.
     * <p>
     * By allowing an server implementation to decide what kind of
     * destination and which destination to use to publish events, we allow
     * maximum implementation flexibility. The server implemeation can
     * use one single JMS Topic or many individual JMS Topics
     * (perhaps one for each Activity value) to do event publishing.
     * <p>
     * The use of separate destination for the publishing of events
     * is an optional one and is left to the implementation. Clearly, this
     * is not needed for the case of reporting mode being
     * ReportMode.NO_REPORT_MODE.
     * <p> If the an implementation chooses to publish all events to
     * a single jms topic (well known as "JVTEventTopic"), then it may
     * leave this attribute value's as
     * >CODE>EVENT_DESTINATION_TYPE_NOT_SET</CODE>.
     * <p>
     * This method is callable by both server implemetation
     * and the clients of the API.
     * Default value for this attribute is
     * <CODE>EVENT_DESTINATION_TYPE_NOT_SET</CODE>
     * Following the common design guidelines, this Event will be published
     * to JVTEventTopic topic.
	 * @return flag indicating if the event destination type
     */
    public int getEventDestinationType()
    {
        return eventDestinationType;
    }
    
    
    /**
     * Set the desination's type where the AvailabilityEvent or DataEvent
     * will be sent.
     * <p>
     * This attribute is determined the Server implementation, not by the
     * client. Hence, client should never call this method. When
     * passed to the Bean, this value is checked to make sure that it
     * it is not set. Otherwise, an exception will be thrown at appropriate
     * time.
     * <p>
     * By allowing an server implementation to decide what kind of
     * destination and which destination to use to publish events, we allow
     * maximum implementation flexibility. The server implemeation can
     * use one single JMS Topic or many individual JMS Topics
     * (perhaps one for each Activity value) to do event publishing.
     * <p>
     * The use of separate destination for the publishing of events
     * is an optional one and is left to the implementation. Clearly, this
     * is not needed for the case of reporting mode being
     * ReportMode.NO_REPORT_MODE.
     * <p> If the an implementation chooses to publish all events to
     * a single jms topic (well known as "JVTEventTopic"), then it may
     * leave this attribute value's as
     * >CODE>EVENT_DESTINATION_TYPE_NOT_SET</CODE>.
     * <p>
     * This method should be used only by server implemetation.
     * The client API should just use the #getEventDestinationType to obtain
     * the type of the JMS destination.
     * Default value for this attribute is
     * <CODE>EVENT_DESTINATION_TYPE_NOT_SET</CODE>
     * Following the common design guidelines, this Event will be published
     * to JVTEventTopic topic.
     * @param eventDestinationType event destination type for this
     * ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if the input param
     * not valid
     */
    public void setEventDestinationType(int eventDestinationType)
        throws java.lang.IllegalArgumentException
    {
        if( ( eventDestinationType !=
              ActivityReportParams.EVENT_DESTINATION_TYPE_NOT_SET ) &&
            ( eventDestinationType !=
              ActivityReportParams.EVENT_DESTINATION_TYPE_JMS_QUEUE ) &&
            ( eventDestinationType !=
              ActivityReportParams.EVENT_DESTINATION_TYPE_JMS_TOPIC ) )
        {
            throw new java.lang.IllegalArgumentException(
                "EventDestinationType parameter is not valid");
        }

        this.eventDestinationType = eventDestinationType;
            
    }
    

    /**
     * Get the desination's name, where the AvailabilityEvent or DataEvent
     * will be sent. This will be the JNDI name of the destination, which
     * the clients can lookup and create a subscription to receive
     * the events.
     * <p>
     * This attribute is determined the Server implementation, not by the
     * client. Hence, client should never call this method. When
     * passed to the Bean, this value is checked to make sure that it
     * it is not set. Otherwise, an exception will be thrown at appropriate
     * time.
     * <p>
     * By allowing an server implementation to decide what kind of
     * destination and which destination to use to publish events, we allow
     * maximum implementation flexibility. The server implemeation can
     * use one single JMS Topic or many individual JMS Topics
     * (perhaps one for each Activity) to do event publishing.
     * <p>
     * The use of separate destination for the publishing of events
     * is an optional one and is left to the implementation. Clearly, this
     * is not needed for the case of reporting mode being
     * ReportMode.NO_REPORT_MODE.
     * <p> If the an implementation chooses to publish all events to
     * a single jms topic (well known as "JVTEventTopic"), then it may
     * leave this attribute value's as empty string or null.
     * <p>
     * This method is callable by both server implemetation
     * and the clients of the API.
     * Default value for this attribute is empty string.
     * Following the common design guidelines, this Event will be published
     * to JVTEventTopic topic.
     *
     * @return event destination name for this ActivityReportParams
     * @exception java.lang.IllegalStateException Thrown if event
     * destination name is not set
     */
    public String getEventDestinationName()
        throws java.lang.IllegalStateException
    {
        if( (eventDestinationName == null) ||
            (eventDestinationName.length() == 0) ||
            ( eventDestinationType ==
              ActivityReportParams.EVENT_DESTINATION_TYPE_NOT_SET ) )
        {
            throw new java.lang.IllegalStateException(
                "EventDestinationName attribute is not set or valid");
        }
        
        return eventDestinationName;
    }
    
    
    /**
     * Set the desination's name, where the AvailabilityEvent or DataEvent
     * will be sent. This will be the JNDI name of the destination, which
     * the clients can lookup and create a subscription to receive
     * the events.
     * <p>
     * This attribute is determined the Server implementation, not by the
     * client. Hence, client should never call this method. When
     * passed to the Bean, this value is checked to make sure that it
     * it is not set. Otherwise, an exception will be thrown at appropriate
     * time.
     * <p>
     * By allowing an server implementation to decide what kind of
     * destination and which destination to use to publish events, we allow
     * maximum implementation flexibility. The server implemeation can
     * use one single JMS Topic or many individual JMS Topics
     * (perhaps one for each Activity value) to do event publishing.
     * <p>
     * The use of separate destination for the publishing of events
     * is an optional one and is left to the implementation. Clearly, this
     * is not needed for the case of reporting mode being
     * ReportMode.NO_REPORT_MODE.
     * <p> If the an implementation chooses to publish all events to
     * a single jms topic (well known as "JVTEventTopic"), then it may
     * leave this attribute value's as empty string or null.
     * <p>
     * This method should be used only by server implemetation.
     * The client API should just use the #getEventDestinationType to obtain
     * the type of the JMS destination.
     * Default value for this attribute is empty string.
     * Following the common design guidelines, this Event will be published
     * to JVTEventTopic topic.
     * @param eventDestinationName event destination name for this
     * ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if the input param
     * not valid
     */
    public void setEventDestinationName(String eventDestinationName)
        throws java.lang.IllegalArgumentException
    {
        if( (eventDestinationName == null) ||
            (eventDestinationName.length() == 0) ||
            ( eventDestinationType ==
             ActivityReportParams.EVENT_DESTINATION_TYPE_NOT_SET ) )
        {
            throw new java.lang.IllegalArgumentException(
                "EventDestinationName parameter is not valid or not applicable");
        }
        
        this.eventDestinationName = eventDestinationName;
    }
    

    /**
     * Get the user friendly name for the underlying reporting scheme.
     * @return report protocol name for this ActivityReportParams
     * @exception java.lang.IllegalStateException Thrown if the report protocol
     * name is not set
     */
    public String getReportProtocolName()
        throws java.lang.IllegalStateException
    {
        if( (reportProtocolName == null) ||
            (reportProtocolName.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "ReportProtocolName is not set");
        }

        return reportProtocolName;
    }
    

    /**
     * Set the user friendly name for the underlying reporting scheme.
     * Many times, it is possible that an existing data reporting mechanism
     * is re-used by making a OSS/J wrapper for it. It is then useful
     * to know what underlying protocol this reporting scheme is being
     * mapped to.
     * <p>
     * Examples could include:  FTP, HTTP, IPDR, CMIP
     * or even purely OSS/J exchange primitives.
     * @param reportProtocolName report protocol name for this
     * ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if the input param
     * not valid
     */
    public void setReportProtocolName(String reportProtocolName)
        throws java.lang.IllegalArgumentException
    {
        if( (reportProtocolName == null) ||
            (reportProtocolName.length() == 0) )
        {
            throw new java.lang.IllegalArgumentException(
                "ReportProtocolName parameter is not valid");
            
        }
        this.reportProtocolName = reportProtocolName;
        
    }
    

    /**
     * Get the version string for the underlying reporting scheme.
     * @return report protocol version for this ActivityReportParams
     * @exception java.lang.IllegalStateException Thrown if the report protocol
     * version is not set
     */
    public String getReportProtocolVersion()
        throws java.lang.IllegalStateException
    {
        
        if( (reportProtocolVersion == null) ||
            (reportProtocolVersion.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "ReportProtocolVersion is not set");
        }

        return reportProtocolVersion;
    }
    
    /**
     * Set the version for the underlying reporting scheme.
     * Many times, it is possible that an existing data reporting mechanism
     * is re-used by making a OSS/J wrapper for it. It is then useful
     * to know what underlying protocol this reporting scheme is being
     * mapped to and which version is being supported.
     * <p>
     * Examples could include: GTP', FTP, HTTP, IPDR or CMIP exchange
     * primitives.
     * @param reportProtocolVersion report protocol version for this
     * ActivityReportParams
     * @exception java.lang.IllegalArgumentException Thrown if the input param
     * not valid
     */
    public void setReportProtocolVersion(String reportProtocolVersion)
        throws java.lang.IllegalArgumentException
    {
        /**
         * Make sure that the reporting protocol name is already set.
         */
        try
        {
            String name = getReportProtocolName();
            // okay.
        }
        catch (java.lang.IllegalStateException e)
        {
            throw new java.lang.IllegalArgumentException(
                "Prerequisite ReportProtocolName has not been set");
        }
        
        if( ( reportProtocolVersion == null) ||
            (reportProtocolVersion.length() == 0) )
        {
            throw new java.lang.IllegalArgumentException(
                "ReportProtocolVersion parameter is not valid");
            
        }
        
        this.reportProtocolVersion = reportProtocolVersion;
    }
        
	/**
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

        if(!(other instanceof ActivityReportParamsImpl))
        {
            return false;
        }

        ActivityReportParamsImpl localOther = 
							(ActivityReportParamsImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			if (( reportMode == localOther.reportMode ) &&
				( reportPeriod == localOther.reportPeriod ) &&
				( eventDestinationType == localOther.eventDestinationType ))
			{

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

				if(eventDestinationName != null)
				{
					if  (!(eventDestinationName.
								equals(localOther.eventDestinationName)))
					{
						return false;
					}
				}
				else if(eventDestinationName !=
									localOther.eventDestinationName)
				{
					return false;
				}
   	                      
				if (reportProtocolName != null)
				{
					if  (!(reportProtocolName.equals(
										localOther.reportProtocolName)))
					{
						return false;
					}
	
				}
				else if(reportProtocolName !=
									localOther.reportProtocolName)
				{
					return false;
				}
	
				if (reportProtocolVersion != null)
				{
					if  (!(reportProtocolVersion.
								equals(localOther.reportProtocolVersion)))
					{
						return false;
					}
				}
				else if(reportProtocolVersion !=
									localOther.reportProtocolVersion)
				{
					return false;
				}

			}
			else
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

        if(reportFormat != null)
        {
            hashCode = reportFormat.hashCode();
        }

        if(eventDestinationName != null)
        {
            hashCode ^= eventDestinationName.hashCode();
        }

        if(reportProtocolName != null)
        {
            hashCode ^= reportProtocolName.hashCode();
        }

        if(reportProtocolVersion != null)
        {
            hashCode ^= reportProtocolVersion.hashCode();
        }

		return hashCode;
	}
}

