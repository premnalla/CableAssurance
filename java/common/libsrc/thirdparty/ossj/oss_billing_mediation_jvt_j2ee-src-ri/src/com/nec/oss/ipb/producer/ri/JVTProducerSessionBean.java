/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */
package com.nec.oss.ipb.producer.ri;


/**
 * Standard imports
 */

import com.nec.oss.cfi.activity.ri.ScheduleImpl;
import javax.oss.cfi.activity.WeeklyScheduleInfo;
import java.util.TimeZone;

import java.net.URL;

import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;


import java.rmi.*;

import javax.ejb.*;

import javax.naming.*;

import javax.rmi.PortableRemoteObject;

import javax.oss.IllegalArgumentException;

/**
 * Spec imports.
 */

import javax.oss.QueryValue;
import javax.oss.ApplicationContext;
import javax.oss.SetException;
import javax.oss.EventPropertyDescriptor;
import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityKey;
import javax.oss.ManagedEntityValueIterator;

import javax.oss.ipb.exception.IPBAPIException;

import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.ProducerPrimaryKey;
import javax.oss.ipb.producer.ProducerValueIterator;
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.MediationCapability;
import javax.oss.ipb.producer.ProducerKeyResult;
import javax.oss.ipb.producer.ProducerKeyResultIterator;

import javax.oss.ipb.type.UsageRecFilterValue;
import javax.oss.ipb.type.UsageRecValueIterator;
import javax.oss.ipb.type.UsageRecValue;

import javax.oss.ipb.transfer.TransferStatusValueIterator;

import javax.oss.ipb.event.UsageDataAvailableEvent;
import javax.oss.ipb.event.MediationCapabilityChangeEvent;

import javax.oss.ipb.query.QueryByUsageRecFilter;

import javax.oss.cfi.activity.ReportInfoIterator;
import javax.oss.cfi.activity.ReportInfo;
import javax.oss.cfi.activity.ReportFormat;
import javax.oss.cfi.activity.ActivityKey;
import javax.oss.cfi.activity.ActivityKeyResult;
import javax.oss.cfi.activity.ActivityCreationEvent;
import javax.oss.cfi.activity.ActivityRemovalEvent;
import javax.oss.cfi.activity.ActivitySuspendEvent;
import javax.oss.cfi.activity.ActivityResumeEvent;
import javax.oss.cfi.activity.ActivityValue;
import javax.oss.cfi.activity.ActivityExecParams;
import javax.oss.cfi.activity.ActivityReportParams;
import javax.oss.cfi.activity.ActivityControlException;
import javax.oss.cfi.activity.ControlState;
import javax.oss.cfi.activity.ExecutionStatus;
import javax.oss.cfi.activity.AttributeDescriptor;
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.cfi.activity.Schedule;


import com.nokia.oss.ossj.common.ri.ManagedEntityValueImpl;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ApplicationContextImpl;

import com.nec.oss.ipb.query.ri.QueryByUsageRecFilterImpl;
import com.nec.oss.ipb.producer.ri.ProducerValueImpl;
import com.nec.oss.ipb.producer.ri.ProducerValueIteratorHome;
import com.nec.oss.ipb.producer.ri.ProducerPrimaryKeyImpl;
import com.nec.oss.ipb.producer.ri.ProducerKeyResultImpl;

import com.nec.oss.ipb.type.ri.UsageRecFilterValueImpl;
import com.nec.oss.ipb.type.ri.UsageRecValueIteratorHome;
import com.nec.oss.ipb.type.ri.UsageRecValueStatefulIterator;
import com.nec.oss.ipb.type.ri.UsageRecValueIteratorImpl;
import com.nec.oss.ipb.type.ri.UsageRecValueImpl;

import com.nec.oss.cfi.activity.ri.ActivityRemovalEventImpl;
import com.nec.oss.cfi.activity.ri.ActivityRemovalEventPropertyDescriptorImpl;
import com.nec.oss.cfi.activity.ri.ActivityCreationEventImpl;
import com.nec.oss.cfi.activity.ri.ActivityCreationEventPropertyDescriptorImpl;
import com.nec.oss.cfi.activity.ri.ActivitySuspendEventImpl;
import com.nec.oss.cfi.activity.ri.ActivitySuspendEventPropertyDescriptorImpl;
import com.nec.oss.cfi.activity.ri.ActivityResumeEventImpl;
import com.nec.oss.cfi.activity.ri.ActivityResumeEventPropertyDescriptorImpl;
import com.nec.oss.cfi.activity.ri.RecordDescriptorImpl;
import com.nec.oss.cfi.activity.ri.ReportInfoImpl;
import com.nec.oss.cfi.activity.ri.ReportInfoIteratorHome;
import com.nec.oss.cfi.activity.ri.ReportInfoStatefulIterator;
import com.nec.oss.cfi.activity.ri.ReportInfoIteratorImpl;

import com.nec.oss.ipb.event.ri.MediationCapabilityChangeEventImpl;
import com.nec.oss.ipb.event.ri.MediationCapabilityChangeEventPropertyDescriptorImpl;
import com.nec.oss.ipb.event.ri.UsageDataAvailableEventImpl;
import com.nec.oss.ipb.event.ri.UsageDataAvailableEventPropertyDescriptorImpl;

import com.nec.oss.ipb.query.ri.QueryByUsageRecFilterImpl;
import com.nec.oss.ipb.query.ri.QueryProducersByMediationCapabilityImpl;

/**
 *  Util and Entity imports
 */
import com.nec.oss.ipb.producer.ri.ProducerEntityHome;
import com.nec.oss.ipb.producer.ri.ProducerEntity;
import com.nec.oss.ipb.producer.ri.util.QueryHelper;
import com.nec.oss.ipb.producer.ri.util.EventHelper;


/**
 * A javax.oss.ipb.producer.JVTProducerSession implementation.
 *
 * The <CODE>JVTProducerSession</CODE> interface defines an IP Billing
 * Data Producer's
 * ability to collect, mediate and report the Usage Data Records in
 * a variety of formats, using a variety of transfer protcols.
 * This is the central EJB remote interface of the IP Billing API.
 *
 * <p>
 * A client of IP Billing API could be a Billing Application wishing to
 * receive Usage Data Records. This interface provides an IP Billing API
 * client with:
 * <UL>
 * <li> ability to query and find the type of <CODE>Producer</CODE> Entity it is interested in
 * <li> ability to inspect the capability of a chosen <CODE>Producer</CODE> entity
 * <li> negotiate the capabilities for a particular Data Transfer session
 * <li> ability to define schedules on <CODE>Producer</CODE> system to collect
 * the Usage Data Records
 * <li> subscription based retrieval of Usage Data Records with support for <CODE>Producer</CODE>-side filtering
 * <li> query <CODE>Producer</CODE> to receive a snapshot of a subset of Usage Data Records
 * in the System.
 * </UL>
 *
 * <p> A client application of the IP Billing API need not necessarily be
 * another J2EE application in order to use the IP Billing API <CODE>Producer</CODE>.
 *
 * <p>
 * The <CODE>JVTProducerSession</CODE> interface defines the business functions for
 * the Usage Data Collectiona and Retrieval in a <CODE>Producer</CODE> remote
 * interface and the bean implementation.
 *
 <p>
 * By providing the business operations in a separate interface allows
 * the bean implementors to get compile time checks that their bean
 * will work with the remote interface.
 *
 * @see javax.oss.ipb.producer.ProducerValue
 * @see javax.oss.ipb.producer.ProducerKey
 * @see javax.oss.ipb.type.UsageRecValue
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */
public class JVTProducerSessionBean
implements javax.ejb.SessionBean
{
    /** ---------------------------------------------------------------
     * JVTProducerSession Bean variables
     * -------------------------------------------------------------*/
    /**
     * Holds the application context.
     */
    protected ApplicationContext myApplicationContext = null;

    /**
     * Holds the naming context.
     */
    protected InitialContext myNamingContext;

    /**
     * ProducerEntity Remote Home handle
     */
    protected ProducerEntityHome myProducerEntityHome = null;
    
    /**
     * ProducerValueIterator Remote Home handle
     */
    protected ProducerValueIteratorHome myProducerValueIteratorHome = null;

    /**
     * ProducerKeyResultIterator Remote Home handle
     */
    protected ProducerKeyResultIteratorHome myProducerKeyResultIteratorHome = null;

    /**
     * UsageRecValueIterator Remote Home handle
     */
    protected UsageRecValueIteratorHome myUsageRecValueIteratorHome = null;

    /**
     * ReportInfoIterator Remote Home handle
     */
    protected ReportInfoIteratorHome myReportInfoIteratorHome = null;

    /**
     * The session context.
     */
    protected SessionContext sessionContext;
    
    /**
     * The application distinguished name.
     */
    protected String myApplicationDN = null;

    /**
     * Event helper class that takes care of JMS messaging.
     */
    protected EventHelper myEventHelper = null;

    /**
     * supported producer types.
     */
    protected ArrayList psProducerTypes = null;

    /**
     * supported usagerecfilter types.
     */
    protected ArrayList psUsageRecFilterTypes = null;

    /**
     * Supported Query types.
     */
    protected ArrayList psQueryTypes = null;

    /**
     * Supported Event types.
     */
    protected ArrayList psEventTypes = null;

    /**
     * Supported Event descriptors
     */
    protected HashMap psEventDescMap = null;

    /**
     * supported MediationCapability types.
     */
    protected ArrayList psMediationCapabilityTypes = null;

    /**
     * Canned usage data and report info
     */
    protected ArrayList cannedUsageDataList = null;
    protected RecordDescriptor cannedUsageDataDescriptor = null;
    protected ArrayList cannedReportInfoList = null;

    /**##########################################################
     * ActivityCapability Operations
     ##########################################################*/

    /**
     * Get supported granularities by this Activity.
	 *
	 * @return Array of granularities supported by this JVTProducerSession
	 * @param value Used to specify granularities.
	 * @exception RemoteException
	 * @exception javax.oss.IllegalArgumentException
     */
    public long[] getSupportedGranularities(ActivityValue value)
        throws javax.oss.IllegalArgumentException
    {
        ArrayList aList = new ArrayList();
		long[] producerGranularities=null;
		ActivityExecParams tmpExecParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_EXEC_PARAMS
		};

        /** 
         * If there is a producerKey specified, return the Granularities 
         * list for that Producer only
         */
		if(value != null)
		{
			ProducerValue inputVal = (ProducerValue)value;

			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey( 
						(ProducerPrimaryKeyImpl)inputVal.getProducerKey().
													getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new javax.ejb.EJBException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the Granularity
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				tmpExecParams=prodValue.getActivityExecParams();
				producerGranularities = new long[1];
				producerGranularities[0]=tmpExecParams.getGranularityPeriod();
        	}

        	catch (Exception e)
        	{
				producerGranularities = new long[0];
            	throw new javax.ejb.EJBException(
                	"Unknown error getting Granularity values" + e);
        	}
       	}

        /** 
         * Otherwise, build a list of unique Granularities
         * supported by all Producers
         */
		else
		{
        	Collection allProducers=null;

			try
			{
        		// Now attempt to create the producer entity value.
        		ProducerEntityHome peh = locateProducerEntityHome();
        		allProducers = peh.findAllProducers();
			}
            catch (Exception e)
            {
                throw new javax.ejb.EJBException(
                        "Error on finding all producers" + e);
            }

			if(allProducers != null)
			{
				Iterator prodIter=allProducers.iterator();
				ProducerEntity prodEnt;

				while(prodIter.hasNext())
				{
                	try
                	{
						Long retrievedGranularity;
						prodEnt=(ProducerEntity)prodIter.next();

        				ProducerValue prodValue = 
										prodEnt.getAttributes(attrNames);
						tmpExecParams=prodValue.getActivityExecParams();
						retrievedGranularity=
								new Long(tmpExecParams.getGranularityPeriod());
					   	if(!aList.contains(retrievedGranularity))
					   	{
                		   	aList.add(retrievedGranularity);
					    }
                	}
                	catch (java.lang.IllegalStateException e)
                	{
						// OK - the GranularityPeriod isn't set for this one
                	}
               
                	catch (Exception e)
                	{
						producerGranularities = new long[0];
                    	throw new javax.ejb.EJBException(
                        	"Unknown getting Granularity values" + e);
                	}
            	}

				int numValues=aList.size();
            	producerGranularities =  new long[numValues];

				for(int valIdx=0; valIdx<numValues; ++valIdx)
				{

					producerGranularities[valIdx] = 
										((Long)(aList.get(valIdx))).longValue();
				}
			}
			else
			{
				producerGranularities = new long[0];
                    throw new javax.ejb.EJBException(
                        "Error on finding all producers");
			}
		}

		return(producerGranularities);
    }
    

    /**
     * Get the Report Modes provided by this JVTProducerSession.
     * 
     * @return Array of unique report modes supported by this JVTProducerSession
     * @exception javax.oss.IllegalStateException
     * @exception RemoteException
     */
    public int[] getSupportedReportModes()
        throws javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		int[] producerReportModes=null;
		ActivityReportParams[] tmpReportParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};
       	Collection allProducers=null;

		try
		{
       		// Now attempt to create the producer entity value.
       		ProducerEntityHome peh = locateProducerEntityHome();
       		allProducers = peh.findAllProducers();
		}
		catch (Exception e)
        {
			producerReportModes = new int[0];
                   throw new javax.ejb.EJBException(
                       "Error on finding all producers" + e);
        }

		if(allProducers != null)
		{
			Iterator prodIter=allProducers.iterator();
			ProducerEntity prodEnt;

			while(prodIter.hasNext())
			{
               	try
               	{
					Integer retrievedReportMode;
					prodEnt=(ProducerEntity)prodIter.next();

       				ProducerValue prodValue = 
									prodEnt.getAttributes(attrNames);

					tmpReportParams=prodValue.getActivityReportParams();
					int numRptParams=tmpReportParams.length;

					for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
					{
						retrievedReportMode=
						 new Integer(tmpReportParams[paramIdx].getReportMode());

				   		if(!aList.contains(retrievedReportMode))
				   		{
               		   		aList.add(retrievedReportMode);
				    	}
				   	}
               	}
               	catch (java.lang.IllegalStateException e)
               	{
					// OK - the ReportMode isn't set for this one
               	}
              
               	catch (Exception e)
               	{
					producerReportModes = new int[0];
                   	throw new javax.ejb.EJBException(
                       	"Unknown getting ReportMode values" + e);
               	}
           	}

			int numValues=aList.size();
           	producerReportModes =  new int[numValues];

			for(int valIdx=0; valIdx<numValues; ++valIdx)
			{

				producerReportModes[valIdx] = 
									((Integer)(aList.get(valIdx))).intValue();
			}
		}
		else
		{
			producerReportModes = new int[0];
                   throw new javax.ejb.EJBException(
                       "Error on finding all producers");
    	}

		return(producerReportModes);
    }
    

    /**
     * Get the Report Modes provided by a particular activity.
     * 
     * @param activityKey Activity to query for report modes
     * @return Array of unique report modes supported by the activity
     * @exception RemoteException
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalStateException
     */
    public int[] getSupportedReportModesByActivity(
        ActivityKey activityKey)
        throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		int[] producerRptModes=null;
		ActivityReportParams[] tmpRptParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};

		// If there is a value provided, get the asssociated
		// Report parameters
		if (activityKey != null)
		{
			ProducerKey inputVal = (ProducerKey)activityKey;

			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey( 
					(ProducerPrimaryKeyImpl)inputVal.getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new javax.ejb.EJBException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the Report Modes
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				Integer retrievedReportMode;
				tmpRptParams=prodValue.getActivityReportParams();
				int numRptParams=tmpRptParams.length;

				for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
				{
					retrievedReportMode=
					 new Integer(tmpRptParams[paramIdx].getReportMode());

			   		if(!aList.contains(retrievedReportMode))
			   		{
              			aList.add(retrievedReportMode);
			    	}
			   	}
        	}

        	catch (Exception e)
        	{
            	throw new javax.ejb.EJBException(
                	"Unknown error getting Report Mode values" + e);
        	}
        
			int numValues=aList.size();
           	producerRptModes =  new int[numValues];

			for(int valIdx=0; valIdx<numValues; ++valIdx)
			{
				producerRptModes[valIdx] = 
									((Integer)(aList.get(valIdx))).intValue();
			}
		}
		else
		{
           	throw new javax.ejb.EJBException(
               	"Invalid ProducerKey specified:" + activityKey);
		}
		return(producerRptModes);
    }
    

    /**
     * Get the Report Formats provided by this JVTProducerSession.
     * 
     * @return Array of unique report formats supported by this 
	 * JVTProducerSession
     * @exception javax.oss.IllegalStateException
     * @exception RemoteException
     */
    public ReportFormat[] getSupportedReportFormats()
        throws javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		ReportFormat[] producerReportFormats=null;
		ActivityReportParams[] tmpReportParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};
       	Collection allProducers=null;

		try
		{
       		// Now attempt to create the producer entity value.
       		ProducerEntityHome peh = locateProducerEntityHome();
       		allProducers = peh.findAllProducers();
		}
		catch (Exception e)
        {
                   throw new javax.ejb.EJBException(
                       "Error on finding all producers" + e);
        }

		if(allProducers != null)
		{
			Iterator prodIter=allProducers.iterator();
			ProducerEntity prodEnt;

			while(prodIter.hasNext())
			{
               	try
               	{
					ReportFormat retrievedReportFormat;
					prodEnt=(ProducerEntity)prodIter.next();

       				ProducerValue prodValue = 
									prodEnt.getAttributes(attrNames);

					tmpReportParams=prodValue.getActivityReportParams();
					int numRptParams=tmpReportParams.length;

					for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
					{
						retrievedReportFormat=
						 			tmpReportParams[paramIdx].getReportFormat();

				   		if(!aList.contains(retrievedReportFormat))
				   		{
               		   		aList.add(retrievedReportFormat);
				    	}
				   	}
               	}
               	catch (java.lang.IllegalStateException e)
               	{
					// OK - the ReportFormat isn't set for this one
               	}
              
               	catch (Exception e)
               	{
                   	throw new javax.ejb.EJBException(
                       	"Unknown getting ReportFormat values" + e);
               	}
           	}

        	producerReportFormats = 
            	(ReportFormat[]) aList.toArray(new ReportFormat[aList.size()]);
		}
		else
		{
			throw new javax.ejb.EJBException(
											"Error on finding all producers");
    	}

		return(producerReportFormats);
    }
    

    /**
     * Get the Report Formats provided by a particular activity.
     * 
     * @param activityKey Activity to query for report formats
     * @return Array of unique report formats supported by the activity
     * @exception javax.oss.IllegalStateException
     * @exception javax.oss.UnsupportedOperationException
     * @exception RemoteException
     */
    public ReportFormat[] getSupportedReportFormatsByActivity(
        ActivityKey activityKey)
        throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		ReportFormat[] producerRptFormats=null;
		ActivityReportParams[] tmpRptParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};

		// If there is a value provided, get the asssociated
		// Report parameters
		if (activityKey != null)
		{
			ProducerKey inputVal = (ProducerKey)activityKey;

			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey( 
					(ProducerPrimaryKeyImpl)inputVal.getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new javax.ejb.EJBException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the Report Formats
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				ReportFormat retrievedReportFormat;
				tmpRptParams=prodValue.getActivityReportParams();
				int numRptParams=tmpRptParams.length;

				for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
				{
					retrievedReportFormat=
									tmpRptParams[paramIdx].getReportFormat();

			   		if(!aList.contains(retrievedReportFormat))
			   		{
              			aList.add(retrievedReportFormat);
			    	}
			   	}
        	}

        	catch (Exception e)
        	{
            	throw new javax.ejb.EJBException(
                	"Unknown error getting Report Format values" + e);
        	}
        
        	producerRptFormats = 
            	(ReportFormat[]) aList.toArray(new ReportFormat[aList.size()]);
		}
		else
		{
           	throw new javax.ejb.EJBException(
               	"Invalid ProducerKey specified:" + activityKey);
		}
		return(producerRptFormats);
    }
    
    

    /**
     * Returns the underlying protocol's names for the supported
     * reporting mechanisms of the JVT Session Bean.
     * At least one valid string value should be returned in the results,
     * even if only pure OSS/J based reporting scheme is supported and this
     * scheme is not based on any known protocol. In such case,
     * the returned results should contain "OSS/J" string value.
     * <p>
     * For various report modes, it is necessary to elaborate on exactly
     * what type of protocol and version will be used to do the reporting.
     * Examples are given below:
     * <UL>
     * <LI> Report mode is EVENT_SINGLE or EVENT_MULTIPLE: The protocol string
     * is JMS. The version string is: "1.0.2'. 
     * <LI> Report mode is FILE_SINGLE or FILE_MULTIPLE: The protocol string
     * could be "IPDR", if IPDR compliant file transfer protocol is being used.
     * The version string could be: "3.0". 
     * <LI> Another example of Report mode being FILE_SINGLE or FILE_MULTIPLE:
     * The report protocol being used might be pure "FTP".
     * <LI> Report mode is STREAM_SINGLE or STREAM_MULTIPLE: The protocol strings
     * could be any of "HTTP", "HTTPS", "GTP" or any other name that makes sense
     * to both the Producer system and the client of the API. 
     * <LI> Report mode is ITERATOR_SINGLE or ITERATOR_MULTIPLE: The protcol string
     * is "OSS/J".
     * </UL>
     * 
     * @see javax.oss.cfi.activity.ActivityReportParams
     */
    public String[] getSupportedReportProtocolNames()
        throws javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		String[] producerReportProtocolNames=null;
		ActivityReportParams[] tmpReportParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};
       	Collection allProducers=null;

		try
		{
       		// Now attempt to create the producer entity value.
       		ProducerEntityHome peh = locateProducerEntityHome();
       		allProducers = peh.findAllProducers();
		}
		catch (Exception e)
        {
                   throw new javax.ejb.EJBException(
                       "Error on finding all producers" + e);
        }

		if(allProducers != null)
		{
			Iterator prodIter=allProducers.iterator();
			ProducerEntity prodEnt;

			while(prodIter.hasNext())
			{
               	try
               	{
					String retrievedReportProtocolName;
					prodEnt=(ProducerEntity)prodIter.next();

       				ProducerValue prodValue = 
									prodEnt.getAttributes(attrNames);

					tmpReportParams=prodValue.getActivityReportParams();
					int numRptParams=tmpReportParams.length;

					for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
					{
						retrievedReportProtocolName=
							tmpReportParams[paramIdx].getReportProtocolName();

				   		if(!aList.contains(retrievedReportProtocolName))
				   		{
               		   		aList.add(retrievedReportProtocolName);
				    	}
				   	}
               	}
               	catch (java.lang.IllegalStateException e)
               	{
					// OK - the ReportProtocolName isn't set for this one
               	}
              
               	catch (Exception e)
               	{
                   	throw new javax.ejb.EJBException(
                       	"Unknown getting ReportProtocolName values" + e);
               	}
           	}

        	 producerReportProtocolNames =
            				(String[]) aList.toArray(new String[aList.size()]);
		}
		else
		{
			throw new javax.ejb.EJBException(
							"Error on finding all producers");
    	}

		return(producerReportProtocolNames);
    }
    

    /**
     * Returns the underlying protocol's names for the supported
     * reporting mechanisms of the specified activity. If no information
     * regarding the supported protocol names is available, empty string
     * array may be returned.
     * @see #getSupportedReportProtocolNames for more details.
     * 
     * @param Activity for which the report formats should be queried
     * @return Array of unique report protocol names supported by the
	 * provided activity
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalStateException
     * @exception RemoteException
     */
    public String[] getSupportedReportProtocolNamesByActivity(
        ActivityKey activityKey)
        throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		String[] producerRptProtocolNames=null;
		ActivityReportParams[] tmpRptParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};

		// If there is a value provided, get the asssociated
		// Report parameters
		if (activityKey != null)
		{
			ProducerKey inputVal = (ProducerKey)activityKey;

			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey( 
					(ProducerPrimaryKeyImpl)inputVal.getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new javax.ejb.EJBException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the Report ProtocolNames
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				String retrievedReportProtocolName;
				tmpRptParams=prodValue.getActivityReportParams();
				int numRptParams=tmpRptParams.length;

				for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
				{
					retrievedReportProtocolName=tmpRptParams[paramIdx].getReportProtocolName();

			   		if(!aList.contains(retrievedReportProtocolName))
			   		{
              			aList.add(retrievedReportProtocolName);
			    	}
			   	}
        	}

        	catch (Exception e)
        	{
            	throw new javax.ejb.EJBException(
                	"Unknown error getting Report ProtocolName values" + e);
        	}
        
        	 producerRptProtocolNames =
            				(String[]) aList.toArray(new String[aList.size()]);
		}
		else
		{
           	throw new javax.ejb.EJBException(
               	"Invalid ProducerKey specified:" + activityKey);
		}

		return(producerRptProtocolNames);
    }

    /**
     * Returns the underlying protocol's versions for the supported
     * reporting mechanisms of the JVT Session Bean. If no information
     * regarding the supported versions is available, empty string
     * array may be returned.
     * @see #getSupportedReportProtocolNames for more details.
     * 
     * @return Array of unique report protocol versions supported by this 
	 * JVTProducerSession
     * @exception javax.oss.IllegalStateException
     * @exception RemoteException
     */
    public String[] getSupportedReportProtocolVersions()
        throws javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		String[] producerReportProtocolVersions=null;
		ActivityReportParams[] tmpReportParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};
       	Collection allProducers=null;

		try
		{
       		// Now attempt to create the producer entity value.
       		ProducerEntityHome peh = locateProducerEntityHome();
       		allProducers = peh.findAllProducers();
		}
		catch (Exception e)
        {
                   throw new javax.ejb.EJBException(
                       "Error on finding all producers" + e);
        }

		if(allProducers != null)
		{
			Iterator prodIter=allProducers.iterator();
			ProducerEntity prodEnt;

			while(prodIter.hasNext())
			{
               	try
               	{
					String retrievedReportProtocolVersion;
					prodEnt=(ProducerEntity)prodIter.next();

       				ProducerValue prodValue = 
									prodEnt.getAttributes(attrNames);

					tmpReportParams=prodValue.getActivityReportParams();
					int numRptParams=tmpReportParams.length;

					for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
					{
						retrievedReportProtocolVersion=
						   tmpReportParams[paramIdx].getReportProtocolVersion();

				   		if(!aList.contains(retrievedReportProtocolVersion))
				   		{
               		   		aList.add(retrievedReportProtocolVersion);
				    	}
				   	}
               	}
               	catch (java.lang.IllegalStateException e)
               	{
					// OK - the ReportProtocolVersion isn't set for this one
               	}
              
               	catch (Exception e)
               	{
                   	throw new javax.ejb.EJBException(
                       	"Unknown getting ReportProtocolVersion values" + e);
               	}
           	}

        	 producerReportProtocolVersions =
            				(String[]) aList.toArray(new String[aList.size()]);
		}
		else
		{
			throw new javax.ejb.EJBException(
							"Error on finding all producers");
    	}

		return(producerReportProtocolVersions);
    }

    /**
     * Returns the underlying protocol's versions for the supported
     * reporting mechanisms of the specified activity. If no information
     * regarding the supported versions is available, empty string
     * array may be returned.
     * @see #getSupportedReportProtocolNames for more details.
     * 
     * @param Activity for which the report formats should be queried
     * @return Array of unique report protocol versions supported by the
	 * provided activity
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalStateException
     * @exception RemoteException
     */
    public String[] getSupportedReportProtocolVersionsByActivity(
        ActivityKey activityKey)
        throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalStateException
    {
        ArrayList aList = new ArrayList();
		String[] producerRptProtocolVersions=null;
		ActivityReportParams[] tmpRptParams=null;
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_REPORT_PARAMS
		};

		// If there is a value provided, get the asssociated
		// Report parameters
		if (activityKey != null)
		{
			ProducerKey inputVal = (ProducerKey)activityKey;

			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey( 
					(ProducerPrimaryKeyImpl)inputVal.getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new javax.ejb.EJBException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the Report ProtocolVersions
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				String retrievedReportProtocolVersion;
				tmpRptParams=prodValue.getActivityReportParams();
				int numRptParams=tmpRptParams.length;

				for(int paramIdx=0; paramIdx<numRptParams; ++paramIdx)
				{
					retrievedReportProtocolVersion=tmpRptParams[paramIdx].getReportProtocolVersion();

			   		if(!aList.contains(retrievedReportProtocolVersion))
			   		{
              			aList.add(retrievedReportProtocolVersion);
			    	}
			   	}
        	}

        	catch (Exception e)
        	{
            	throw new javax.ejb.EJBException(
                	"Unknown error getting Report ProtocolVersion values" + e);
        	}
        
        	 producerRptProtocolVersions =
            				(String[]) aList.toArray(new String[aList.size()]);
		}
		else
		{
           	throw new javax.ejb.EJBException(
               	"Invalid ProducerKey specified:" + activityKey);
		}

		return(producerRptProtocolVersions);
    }


    /**##########################################################
     * ActivityController Operations
     ##########################################################*/
    /**
     * activate a specific Activity by its key.
	 *
     * @param keyValue ActivityKey Used to specify the ActivityKeyResult
     * @return ActivityKeyResult
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException 
     * @exception javax.oss.IllegalArgumentException 
     * @exception RemoteException
     * @exception ActivityControlException
     */
    public ActivityKeyResult activateByKey(ActivityKey keyValue)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		String[] attrNames = 
		{
			ProducerValue.SCHEDULE,
			ProducerValue.ACTIVITY_NAME
		};

		// Check the arg
		if((keyValue == null) || (!(keyValue instanceof ProducerKeyImpl)))
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// Find the ProducerEntity associated with the key
		ProducerKey prodKey = (ProducerKey) keyValue;
		ProducerValue prodVal=null;
		try
		{
			prodVal=getProducerByKey(prodKey, attrNames);
		}

		catch(Exception ex)
		{
			throw new ObjectNotFoundException("Error on getProducerByKey()");
		}

		if(prodVal == null)
		{
			throw new javax.ejb.EJBException(
						"Got null ProducerValue from getProducerByKey()");
		}

		// Verify that there is a Schedule defined for this producer
		try
		{
			Schedule prodSched = prodVal.getSchedule();
		}
		catch(IllegalStateException ex)
		{
			throw new ActivityControlException("No Schedule defined for Producer");
		}

		// Set the control state to indicate that the schedule is active
		prodVal.setControlState(
				ControlState.ACTIVITY_CONTROL_STATUS_ACTIVATED_OFF_DUTY);

		// Initialize the result 
		ProducerKeyResultImpl result = new ProducerKeyResultImpl();
		result.setManagedEntityKey(prodKey);
		result.setSuccess(true);
		result.setException(null);

		// Set the control status on the Producer Entity
		try
		{
			setProducerByValue(prodVal, false);
		}

		catch (Exception ex)
		{
			result.setSuccess(false);
			result.setException(ex);
		}

		return result;
    }
 
    /**
     * activate a set of Activities by their keys. Best Effort operation.
     *
     * @param keyValues Array of ActivityKey used to specify the ProducerValue
     * @return array of ActivityKeyResult
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
	 *
     */
    public ActivityKeyResult[] tryActivateByKeys(ActivityKey[] keyValues)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		// Check the arg
		if(keyValues == null)
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// For each keyValue, try to activate.
		int numKeys=keyValues.length;
        ArrayList aList = new ArrayList();
		ProducerKeyResult tmpResult=null;
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			// If this call succeeds, then we will use the keyResult
			// returned by the method
			try
			{
				tmpResult=(ProducerKeyResult)activateByKey(keyValues[keyIdx]);
			}

			// If we encounter an exception, use this as the keyResult
			// exception
			catch (Exception ex)
			{
				tmpResult = new ProducerKeyResultImpl();
				tmpResult.setManagedEntityKey(keyValues[keyIdx]);
				tmpResult.setSuccess(false);
				tmpResult.setException(ex);
			}

			aList.add(tmpResult);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }

    /**
     * de-activate a specific Activity by its key.
     *
     * @param keyvalue ActivityKey used to specify the ProducerValue
     * @return ActivityKeyResult
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult deactivateByKey(ActivityKey keyValue)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		String[] attrNames = 
		{
			ProducerValue.SCHEDULE,
			ProducerValue.ACTIVITY_NAME
		};

		// Check the arg
		if((keyValue == null) || (!(keyValue instanceof ProducerKeyImpl)))
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// Find the ProducerEntity associated with the key
		ProducerKey prodKey = (ProducerKey) keyValue;
		ProducerValue prodVal=null;
		try
		{
			prodVal=getProducerByKey(prodKey, attrNames);
		}

		catch(Exception ex)
		{
			throw new ObjectNotFoundException("Error on getProducerByKey()");
		}

		if(prodVal == null)
		{
			throw new javax.ejb.EJBException(
						"Got null ProducerValue from getProducerByKey()");
		}

		// Verify that there is a Schedule defined for this producer
		try
		{
			Schedule prodSched = prodVal.getSchedule();
		}
		catch(IllegalStateException ex)
		{
			throw new ActivityControlException("No Schedule defined for Producer");
		}

		// Set the control state to indicate that the schedule is inactive
		prodVal.setControlState(
					ControlState.ACTIVITY_CONTROL_STATUS_SUSPENDED);

		// Initialize the result 
		ProducerKeyResultImpl result = new ProducerKeyResultImpl();
		result.setManagedEntityKey(prodKey);
		result.setSuccess(true);
		result.setException(null);

		// Set the control status on the Producer Entity
		try
		{
			setProducerByValue(prodVal, false);
		}

		catch (Exception ex)
		{
			result.setSuccess(false);
			result.setException(ex);
		}

		return result;
    }

    /**
     * Deactivate a set of Activities by their keys. Best Effort operation.
     * The Activity definitions are not deleted. 
	 *
     * @param keyvalues Array of ActivityKey used to specify the ProducerValue
     * @return Array of ActivityKeyResult
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult[] tryDeactivateByKeys(ActivityKey[] keyValues)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		// Check the arg
		if(keyValues == null)
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// For each keyValue, try to deactivate.
		int numKeys=keyValues.length;
        ArrayList aList = new ArrayList();
		ProducerKeyResult tmpResult=null;
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			// If this call succeeds, then we will use the keyResult
			// returned by the method
			try
			{
				tmpResult=(ProducerKeyResult)deactivateByKey(keyValues[keyIdx]);	
			}

			// If we encounter an exception, use this as the keyResult
			// exception
			catch (Exception ex)
			{
				tmpResult = new ProducerKeyResultImpl();
				tmpResult.setManagedEntityKey(keyValues[keyIdx]);
				tmpResult.setSuccess(false);
				tmpResult.setException(ex);
			}

			aList.add(tmpResult);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }

    /**
     * suspend a specific Activity by its key.
     * The on-going results, if any, of the activity are discarded.
     *
     * @param keyvalue ActivityKey used to specify the ProducerValue
     * @return ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult suspendByKey(ActivityKey keyValue)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		String[] attrNames = 
		{
			ProducerValue.SCHEDULE,
			ProducerValue.ACTIVITY_NAME
		};

		// Check the arg
		if((keyValue == null) || (!(keyValue instanceof ProducerKeyImpl)))
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// Find the ProducerEntity associated with the key
		ProducerKey prodKey = (ProducerKey) keyValue;
		ProducerValue prodVal=null;
		try
		{
			prodVal=getProducerByKey(prodKey, attrNames);
		}

		catch(Exception ex)
		{
			throw new ObjectNotFoundException("Error on getProducerByKey()");
		}

		if(prodVal == null)
		{
			throw new javax.ejb.EJBException(
						"Got null ProducerValue from getProducerByKey()");
		}

		// Verify that there is a Schedule defined for this producer
		try
		{
			Schedule prodSched = prodVal.getSchedule();
		}
		catch(IllegalStateException ex)
		{
			throw new ActivityControlException("No Schedule defined for Producer");
		}

		// Set the control state to indicate that the schedule is suspended
		prodVal.setControlState(ControlState.ACTIVITY_CONTROL_STATUS_SUSPENDED);

		// Initialize the result 
		ProducerKeyResultImpl result = new ProducerKeyResultImpl();
		result.setManagedEntityKey(prodKey);
		result.setSuccess(true);
		result.setException(null);

		// Set the control status on the Producer Entity
		try
		{
			setProducerByValue(prodVal, false);
		}

		catch (Exception ex)
		{
			result.setSuccess(false);
			result.setException(ex);
		}

		// If this was a success, then generate the Event indicating that
		// the activity was suspended.
		if(result.isSuccess())
		{
        	// Emit a JMS message.
			myEventHelper.sendSuspendEvent(prodVal);
		}

		return result;
    }

    /**
     * suspend a set of Activities by their keys. Best Effort operation.
     *
     * @param keyvalues Array of ActivityKey used to specify the ProducerValue
     * @return Array of ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult[] trySuspendByKeys(ActivityKey[] keyValues)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		// Check the arg
		if(keyValues == null)
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// For each keyValue, try to suspend.
		int numKeys=keyValues.length;
        ArrayList aList = new ArrayList();
		ProducerKeyResult tmpResult=null;
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			// If this call succeeds, then we will use the keyResult
			// returned by the method
			try
			{
				tmpResult=(ProducerKeyResult)suspendByKey(keyValues[keyIdx]);	
			}

			// If we encounter an exception, use this as the keyResult
			// exception
			catch (Exception ex)
			{
				tmpResult = new ProducerKeyResultImpl();
				tmpResult.setManagedEntityKey(keyValues[keyIdx]);
				tmpResult.setSuccess(false);
				tmpResult.setException(ex);
			}

			aList.add(tmpResult);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }

    /**
     * Resume a specific Activity by its key.
     *
     * @param keyvalue ActivityKey used to specify the ProducerValue
     * @return ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult resumeByKey(ActivityKey keyValue)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		String[] attrNames = 
		{
			ProducerValue.SCHEDULE,
			ProducerValue.ACTIVITY_NAME
		};

		// Check the arg
		if((keyValue == null) || (!(keyValue instanceof ProducerKeyImpl)))
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// Find the ProducerEntity associated with the key
		ProducerKey prodKey = (ProducerKey) keyValue;
		ProducerValue prodVal=null;
		try
		{
			prodVal=getProducerByKey(prodKey, attrNames);
		}

		catch(Exception ex)
		{
			throw new ObjectNotFoundException("Error on getProducerByKey()");
		}

		if(prodVal == null)
		{
			throw new javax.ejb.EJBException(
						"Got null ProducerValue from getProducerByKey()");
		}

		// Verify that there is a Schedule defined for this producer
		try
		{
			Schedule prodSched = prodVal.getSchedule();
		}
		catch(IllegalStateException ex)
		{
			throw new ActivityControlException("No Schedule defined for Producer");
		}

		// Set the control state to indicate that the schedule is active
		prodVal.setControlState(
				ControlState.ACTIVITY_CONTROL_STATUS_ACTIVATED_OFF_DUTY);

		// Initialize the result 
		ProducerKeyResultImpl result = new ProducerKeyResultImpl();
		result.setManagedEntityKey(prodKey);
		result.setSuccess(true);
		result.setException(null);

		// Set the control status on the Producer Entity
		try
		{
			setProducerByValue(prodVal, false);
		}

		catch (Exception ex)
		{
			result.setSuccess(false);
			result.setException(ex);
		}

		// If this was a success, then generate the Event indicating that
		// the activity was resumed.
		if(result.isSuccess())
		{
        	// Emit a JMS message.
			myEventHelper.sendResumeEvent(prodVal);
		}

		return result;
    }

    /**
     * Resume a set of Activities by their keys. Best Effort operation.
     *
     * @param keyvalues Array of ActivityKey used to specify the ProducerValue
     * @return Array of ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult[] tryResumeByKeys(ActivityKey[] keyValues)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		// Check the arg
		if(keyValues == null)
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// For each keyValue, try to resume.
		int numKeys=keyValues.length;
        ArrayList aList = new ArrayList();
		ProducerKeyResult tmpResult=null;
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			// If this call succeeds, then we will use the keyResult
			// returned by the method
			try
			{
				tmpResult=(ProducerKeyResult)resumeByKey(keyValues[keyIdx]);	
			}

			// If we encounter an exception, use this as the keyResult
			// exception
			catch (Exception ex)
			{
				tmpResult = new ProducerKeyResultImpl();
				tmpResult.setManagedEntityKey(keyValues[keyIdx]);
				tmpResult.setSuccess(false);
				tmpResult.setException(ex);
			}

			aList.add(tmpResult);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }


     /**
     * One-shot running of a specific Activity by its key.
     *
     * @param keyvalue ActivityKey used to specify the ProducerValue
     * @return Array of ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult oneShotByKey(ActivityKey keyValue)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		String[] attrNames = 
		{
			ProducerValue.SCHEDULE,
			ProducerValue.ACTIVITY_NAME
		};

		// Check the arg
		if((keyValue == null) || (!(keyValue instanceof ProducerKeyImpl)))
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// Find the ProducerEntity associated with the key
		ProducerKey prodKey = (ProducerKey) keyValue;
		ProducerValue prodVal=null;
		try
		{
			prodVal=getProducerByKey(prodKey, attrNames);
		}

		catch(Exception ex)
		{
			throw new ObjectNotFoundException("Error on getProducerByKey()");
		}

		if(prodVal == null)
		{
			throw new javax.ejb.EJBException(
						"Got null ProducerValue from getProducerByKey()");
		}

		// Verify that there is a Schedule defined for this producer
		try
		{
			Schedule prodSched = prodVal.getSchedule();
		}
		catch(IllegalStateException ex)
		{
			throw new ActivityControlException("No Schedule defined for Producer");
		}

		// No real processing occurs at this point for the one-shot.
		// In an actual implementation, the call out to the mediation
		// system would occur here. 
		// Just initialize the result and return it 
		ProducerKeyResultImpl result = new ProducerKeyResultImpl();
		result.setManagedEntityKey(prodKey);
		result.setSuccess(true);
		result.setException(null);

		return result;
    }

    /**
     * One-shot running of a set of Activities by their keys. Best Effort
     * operation.
     *
     * @param keyvalues Array of ActivityKey used to specify the ProducerValue
     * @return Array of ActivityKeyResult
     *
     * @exception javax.oss.UnsupportedOperationException
     * @exception ObjectNotFoundException
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception ActivityControlException
     *
     */
    public ActivityKeyResult[] tryOneShotByKeys(ActivityKey[] keyValues)
        throws javax.oss.UnsupportedOperationException,
            ObjectNotFoundException,
            javax.oss.IllegalArgumentException,
            ActivityControlException
    {
		// Check the arg
		if(keyValues == null)
		{
			throw new IllegalArgumentException("keyValue is not valid");
		}

		// For each keyValue, try to resume.
		int numKeys=keyValues.length;
        ArrayList aList = new ArrayList();
		ProducerKeyResult tmpResult=null;
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			// If this call succeeds, then we will use the keyResult
			// returned by the method
			try
			{
				tmpResult=(ProducerKeyResult)oneShotByKey(keyValues[keyIdx]);	
			}

			// If we encounter an exception, use this as the keyResult
			// exception
			catch (Exception ex)
			{
				tmpResult = new ProducerKeyResultImpl();
				tmpResult.setManagedEntityKey(keyValues[keyIdx]);
				tmpResult.setSuccess(false);
				tmpResult.setException(ex);
			}

			aList.add(tmpResult);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }

    /**##########################################################
     * JVTProducerBean Operations
     ##########################################################*/
    
	/**
	 * Lists the known ProducerValue sub types in the Producer System.
     * <p>
     * This method can be invoked by the clients of the API to know
     * the various supported Producer Entity types of a given system.
     *
	 * @return An array of String values containing type names of various
     * Producer entities supported by this system.
	 *
	 * @exception RemoteException
	 *
	 * @see ProducerValue
     */
    public String[] getProducerValueTypes()
    {
        String [] result =
            (String[]) psProducerTypes.toArray(
                new String[psProducerTypes.size()]);
        
        return result;
    }
    
	/**
	 * Create a new value object for a <CODE>Producer</CODE> entity.
     * The attributes of the value are not initialized.
     * <p>
     * This method can be invoked by the clients of the API to make
     * template value requests.
     *
     * @param producerValueType - This specifies the loadable type name
     * of the producerValue object. A Specific ProducerValue instance
     * can be constructed using this interface.
     * <p>
     * The supported types of <CODE>Producer</CODE>s
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getProducerValueTypes} method.
     *
     * The producerValueType parameter can be null. The type of the Producer
     * Value returned will then be dependent upon the implementation.
     * 
	 * @exception javax.oss.IllegalArgumentException Thrown if the type is
     * invalid
	 * @exception RemoteException Thrown if a system processing error occurs
     *
     * @return The created value object is returned.
     *
	 */
	public ProducerValue makeProducerValue(
        String producerValueType)
        throws javax.oss.IllegalArgumentException
    {
        ProducerValue value = null;
        
		// If a null Producer type is provided, return a default ProducerValue
        if(producerValueType == null)
        {
			// Default to the first value on the list, assuming one exists
			if(!psProducerTypes.isEmpty())
			{
        		producerValueType = (String)psProducerTypes.get(0);
			}
			else
			{
            	throw new javax.ejb.EJBException(
                  "There are no ProducerValue types specified on the server.");
			}
        }

        if(!(psProducerTypes.contains(
            producerValueType)))
        {
            throw new javax.oss.IllegalArgumentException(
                "ProducerValueType parameter value is not recognized");
        }

        for (int i = 0; i < psProducerTypes.size(); ++i)
        {
            if(producerValueType.equals(
                psProducerTypes.get(i)))
            {
                try
                {
                    ClassLoader cl = getClass().getClassLoader();
                    Class producerValueClass =
                        cl.loadClass(producerValueType);
                    value =  (ProducerValue)
                        producerValueClass.newInstance();

                    //
                    // Get the supported key type.
                    //
                    ProducerKey producerKey = value.makeProducerKey();

					value.setManagedEntityKey(IPBConfig.getInstance().
										initializeNewProducerKey(producerKey));
        			value.setLastUpdateVersionNumber(new Date().getTime());
                }
                
                catch (Exception e)
                {
                    throw new javax.oss.IllegalArgumentException(
                        "Unknown ProducerValue Type" + e);
                }

                finally
                {
                    break;
                }
            }
        }
        
        return value;
    }
    

	/**
	 * Lists the known ProducerKey sub types in the Producer System.
     * <p>
     * This method can be invoked by the clients of the API to know
     * the Key instances of various supported Producer Entity types
     * of a given system.
     *
	 * @return An array of String values containing key type names of various
     * Producer keys supported by this system.
	 *
	 * @exception RemoteException
     *
	 * @see ProducerKey
     */
    public String[] getProducerKeyTypes()
    {
        ArrayList aList = null;
        try
        {
            aList = getSupportedProducerKeyTypes();
        }
        catch (Exception e)
        {
            throw new javax.ejb.EJBException(
                "Error while processing getProducerKeyTypes request");
        }

        return (String[]) aList.toArray(new String[aList.size()]);
        
    }
    
	/**
	 * Create a empty key object for a specific <CODE>Producer</CODE> entity.
     * The attributes of the value are not initialized.
     * <p>
     * This method can be invoked by the clients of the API to make
     * queries.
     *
     * @param producerKeyType - This specifies the loadable type name
     * of the producerKey object. A Specific ProducerKey instance
     * can be constructed using this interface.
     * <p>
     * The supported key types of <CODE>Producer</CODE>s
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getProducerKeyTypes} method.
     *
     * The producerKeyType parameter can be null. The type of the Producer
     * Key returned will then be dependent upon the implementation.
     * 
     * @excepiton RemoteException
     *
     * @return The created key object is returned.
     *
	 */
	public ProducerKey makeProducerKey(
        String producerKeyType)
    {
        //ProducerKeyImpl theKey =  null;
        ProducerKey theKey =  null;

		// First, get the list of supported ProducerKeys
       	ArrayList keys = getSupportedProducerKeyTypes();

		// If a null ProducerKey type is provided, return a default 
        if(producerKeyType == null)
        {
			// Default to the first value on the list, assuming one exists
			if(!keys.isEmpty())
			{
        		producerKeyType = (String)keys.get(0);
			}
			else
			{
            	throw new javax.ejb.EJBException(
                  "There are no ProducerKey types specified on the server.");
			}
        }
        
        if(!(keys.contains(producerKeyType)))
        {
            throw new javax.ejb.EJBException(
                "ProducerKeyType parameter (" + producerKeyType +
                ") is not recognized");
        }

        try
        {
            ClassLoader cl = getClass().getClassLoader();
        
            Class producerKeyClass =
                cl.loadClass(producerKeyType);
            //theKey =  (ProducerKeyImpl)
            theKey =  (ProducerKey)
                producerKeyClass.newInstance();
        }
        
        catch(Exception e)
        {
            throw new javax.ejb.EJBException("Unknown ProducerKey Type" + e);
        }

		theKey=IPBConfig.getInstance().initializeNewProducerKey(theKey);
        
        return theKey;
    }
    

	/**
	 * Lists the known UsageRecFilterTypes sub types in the Producer System.
     * <p>
     * This method can be invoked by the clients of the API to know
     * the various supported Usage Record Filter types of a given system.
     *
	 * @return An array of String values containing type names of various
     * UsageRecFilterValue types supported by this system.
	 *
     * @excepiton RemoteException
     *
	 * @see UsageRecFilterValue
     */
    
    public String[] getUsageRecFilterTypes()
    {
        String [] result =
            (String[]) psUsageRecFilterTypes.toArray(
                new String[psUsageRecFilterTypes.size()]);
        
        return result;
    }
    
	/**
	 * Create a new value object for a Usage Record Filter. The attributes
     * of the value are not initialized.
     * <p>
     * This method can be invoked by the clients of the API to make
     * query request for usage records.
     *
     * @param usageRecFilterValueType - This specifies the loadable type name
     * of the UsageRecFilterValue object. A Specific UsageRecFilterValue instance
     * can be constructed using this interface.
     * <p>
     * The supported types of <CODE>UsageRecFilterValue</CODE>s
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getUsageRecFilterTypes} method.
     * <p>
     * The usageRecFilterValueType parameter can be null. The type
     * of the UsageRecFilterValue
     * returned will then be dependent upon the implementation.
     * <p>
	 * 
	 * @return The created value object representation of Usage Record
     * Filter.
	 *
     * @excepiton RemoteException
     *
	 * @see UsageRecFilterValue
	 */
	public UsageRecFilterValue makeUsageRecFilterValue(
        String usageRecFilterValueType)
    {
        UsageRecFilterValueImpl value = null;

		// If a null UsageRecFilterValue type is provided, return a default 
        if(usageRecFilterValueType == null)
        {
			// Default to the first value on the list, assuming one exists
			if(!psUsageRecFilterTypes.isEmpty())
			{
        		usageRecFilterValueType = (String)psUsageRecFilterTypes.get(0);
			}
			else
			{
            	throw new javax.ejb.EJBException(
                  "There are no UsageRecFilterValue types specified on the server.");
			}
        }
        
        if(!(psUsageRecFilterTypes).contains(
            usageRecFilterValueType))
        {
            throw new javax.ejb.EJBException(
                "UsageRecFilterValueType parameter value is not recognized");
        }

        for (int i = 0;
             i < psUsageRecFilterTypes.size();
             ++i)
        {
            if(usageRecFilterValueType.equals(
                psUsageRecFilterTypes.get(i)))
            {
                try
                {
                    ClassLoader cl = getClass().getClassLoader();
                    Class usageRecFilterValueClass =
                        cl.loadClass(usageRecFilterValueType);
                    value =  (UsageRecFilterValueImpl)
                        usageRecFilterValueClass.newInstance();
                }
                
                catch (Exception e)
                {
                    throw new javax.ejb.EJBException(
                        "Unknown UsageRecFilterValue Type" + e);
                }
                
                finally
                {
                    break;
                }
            }
            
        }
        
        return value;
        
    }
    

	/**
	 * Lists the known MediationCapability sub types in the Producer System.
     * <p>
     * This method can be invoked by the clients of the API to know
     * the various supported mediation capability types of a given system.
     *
	 * @return An array of String values containing type names of various
     * Mediation Capabilities supported by this system.
	 *
     * @excepiton RemoteException
     *
	 * @see MediationCapability
     */
    public String[] getMediationCapabilityTypes()
    {
        String [] result =
            (String[]) psMediationCapabilityTypes.toArray(
                new String[psMediationCapabilityTypes.size()]);
        
        return result;
    }
    
        
	/**
	 * Create a new value object for a Mediation Capability. The attributes
     * of the value are not initialized.
     * <p>
     * This method can be invoked by the clients of the API to make
     * template value requests.
     *
     * @param mediationCapabilityType - This specifies the loadable type name
     * of the MediationCapability object. A Specific MediationCapability instance
     * can be constructed using this interface.
     * <p>
     * The supported types of mediation capabilities
     * of a system can be found by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getMediationCapabilityTypes} method.
     *
     * <p>
     * The mediationCapabilityType parameter can be null. The type
     * of the MediationCapability
     * returned will then be dependent upon the implementation.
     * <p>
	 * 
	 * @return The created value object representation of Mediation Capability.
	 *
     * @excepiton RemoteException
     *
	 * @see MediationCapability
	 */
	public MediationCapability makeMediationCapability(
        String mediationCapabilityType)
    {
		MediationCapability value = null;

		// If a null MediationCapability type is provided, return a default 
        if(mediationCapabilityType == null)
        {
			// Default to the first value on the list, assuming one exists
			if(!psMediationCapabilityTypes.isEmpty())
			{
        		mediationCapabilityType = (String)psMediationCapabilityTypes.get(0);
			}
			else
			{
            	throw new javax.ejb.EJBException(
                  "There are no MediationCapability types specified on the server.");
			}
        }

        if(!(psMediationCapabilityTypes.contains(mediationCapabilityType)))
        {
            throw new javax.ejb.EJBException(
                "MediationCapabilityType parameter value is not recognized");
        }
        try
        {
            ClassLoader cl = getClass().getClassLoader();
            Class mediationCapabilityClass =
                cl.loadClass(mediationCapabilityType);
            value = (MediationCapability)
                mediationCapabilityClass.newInstance();
        }

        catch (Exception e)
        {
            throw new javax.ejb.EJBException(
                "Unknown MediationCapability Type" + e);
        }

		return value;
    }
    

	/**
     * Create a new <CODE>Producer</CODE> Entity. 
     *
     * <p>
     * On creation of the <CODE>Producer</CODE>, minimally necessary information must be
     * supplied in order to identify the <CODE>Producer</CODE>. This includes the
     * supplying the information on <CODE>Producer</CODE>'s key attributes,
     * its name and DN.
     * 
     * <p>
     * If a <CODE>ProducerKey</CODE> attribute is specified in the <CODE>ProducerValue</CODE>
     * value object during the create operation, an IllegalArgumentException
     * is raised.
     * <p>
     * The name of a <CODE>Producer</CODE> must be unique within a given system
     * deploying the IP Billing API.
     *
     * <p>
     * The capability descriptor of the created <CODE>Producer</CODE>'s entity, namely
     * attribute that is the instance of <CODE>MediationCapability</CODE>, may be
     * left empty (unset) at the creation time, implying that a <CODE>Producer</CODE>
     * is not ready to be discovered. <p>
     * The <CODE>MediationCapability</CODE> attribute of the <CODE>Producer</CODE> may be set
     * to different values during its lifetime. Each time, this value is changed,
     * the Producer Entity emits a
     * {@link javax.oss.ipb.event.MediationCapabilityChangeEvent}
     * Notification
     * to the common JVTEventTopic, which then will be received by the
     * registered clients. However, a <CODE>Producer</CODE> that
     * does not support setting this attribute, may throw
     * an UnsupportedOperationException.
     * Also, a <CODE>Producer</CODE> Entity may throw a IllegalArgumentException
     * for a Set operation on this attribute, if it indicates a arising of
     * a potential conflict with on-going operations.
     * <p>
     * Similar rules apply to other Value objects, such as Usage Record
     * Descriptors, that are associated with a <CODE>Producer</CODE> Entity. 
     *
     * @param value The input parameter to create a <CODE>Producer</CODE> entity.
     *
     * @return The key of the created <CODE>Producer</CODE> Entity.
     *
     * @exception CreateException Is raised if the <CODE>Producer</CODE> could not be
     * created, due to malformed, unsupported or unexpected attribute values of
     * parameter supplied.
     * @exception IllegalArgumentException Is raised if supplied parameters
     * are of wrong type.
     * @exception DuplicateKeyException If a <CODE>Producer</CODE> already exists, because
     * another <CODE>Producer</CODE> with the same key already exists.
     * @exception RemoteException Is raised when an unexpected system exception
     * occurrs.
     * @exception UnsupportedOperationException
     * Is raised when the client is not expected to create <CODE>Producer</CODE> at this time.
	 */
	public ProducerKey createProducerByValue(
        ProducerValue value)
		throws javax.oss.IllegalArgumentException, CreateException,
            javax.oss.UnsupportedOperationException,
            DuplicateKeyException, NamingException
    {
        ProducerKey key = null;
        ProducerPrimaryKey primaryKey = null;
	boolean isDuplicate = true;
        
        if(value == null)
        {
            throw new javax.oss.IllegalArgumentException(
                "ProducerValue argument is null");
        }

        key = value.makeProducerKey();
       
		value.setManagedEntityKey(IPBConfig.getInstance().
							initializeNewProducerKey(key));
       	value.setLastUpdateVersionNumber(new Date().getTime());
 
        primaryKey = key.makeProducerPrimaryKey();

        // Make sure producer name is set.
        String producerName = null;
        try
        {
            producerName = value.getActivityName();

            //
            // For now, just set the key's id attribute to
            // to be same as the user-friendly name of the activity.
            //
            primaryKey.setActivityId(producerName);
            primaryKey.setProducerType(producerName);
        }
        catch(java.lang.IllegalStateException ise)
        {
            throw new javax.oss.IllegalArgumentException(
                "Missing activityId attribute value");
        }
        catch(java.lang.IllegalArgumentException iae)
        {
            throw new javax.oss.IllegalArgumentException(
                "Error setting the Id of primarykey: " +
                value.getActivityName());
        }

        key.setProducerPrimaryKey(primaryKey);

        ProducerValueImpl implvalue = (ProducerValueImpl) value;
        implvalue.setLastModifiedOnServer(new Date());

        // Now attempt to create the producer entity value.
        ProducerEntityHome peh = locateProducerEntityHome();

		value.setProducerKey(key);
		value.setActivityKey(key);
		// For sjsas:bug id: 4930974
	try
        {
		peh.findByPrimaryKey((ProducerPrimaryKeyImpl)primaryKey);
	}
	catch(FinderException fie)
	{
		//ok object not present
		isDuplicate = false;
	}
	catch(RemoteException ree)
	{
		throw new javax.ejb.EJBException("Error while finding the ProducerEntity:"+ree);        
	}
	if(isDuplicate)
	{
		throw new DuplicateKeyException("Specified key is already existing");	
	}
	//For sjsas				
        try
        {
        ProducerEntity producerEnt = peh.create(
            key, value);
		//why was this afterward? value.setProducerKey(key);
		}
		catch(CreateException cee)
		{
         throw new CreateException("Error while creating the ProducerEntity:"+cee);
		}
		catch(java.rmi.RemoteException ree)
        {
         throw new javax.ejb.EJBException("Error while creating the ProducerEntity:"+ree);
        }
        // Emit a JMS message.
		myEventHelper.sendCreationEvent(value);
        return key;
    }
    

	/**
	 * Create multiple <CODE>Producer</CODE> entities with the specified value templates.
     * <p>
	 *  A Standard OSS/J bulk create entity operation, with best effort semantics.
	 * 
	 * @param values Instances of <CODE>ProducerValue</CODE>, containing initial attribute values.
	 *
	 * @return An array of KeyResult instances of the create operation.
	 *
	 * @exception CreateException If none of the entities could not be created.
	 * @exception DuplicateKeyException If the entities creation would cause duplicate entities with the same primaryKey.
	 * @exception RemoteException If unknown system error occurrs.
	 * @exception javax.oss.IllegalArgumentException If passed parameter
     * values are malformed.
	 * @exception javax.oss.UnsupportedOperationException If this operation
     * cannot be supported at this time.
	 *
	 */
	public ProducerKeyResult[] tryCreateProducersByValues(ProducerValue[] values) 
		throws javax.oss.IllegalArgumentException,
            CreateException,
            javax.oss.UnsupportedOperationException,
            DuplicateKeyException
    {
		// Verify the input parameter
		if((values == null) ||  (values.length <= 0))
		{
			throw new javax.oss.IllegalArgumentException("Invalid value array");
		}

		int numValues=values.length;
		ProducerKeyResultImpl[] results= new ProducerKeyResultImpl[numValues];
		ProducerKey resultKey=null;

		for(int valIdx=0; valIdx<numValues; ++valIdx)
		{
			results[valIdx] = new ProducerKeyResultImpl();
			try
			{
				resultKey=createProducerByValue(values[valIdx]);
				results[valIdx].setManagedEntityKey(resultKey);
				results[valIdx].setSuccess(true);
				results[valIdx].setException(null);
			}
			catch (Exception ex)
			{
				results[valIdx].setManagedEntityKey(resultKey);
				results[valIdx].setSuccess(false);
				results[valIdx].setException(ex);
			}
		}
		
		return(results);
    }


	/**
	 * Remove a single <CODE>Producer</CODE> entity.
     * <p>
     * A Standard OSS/J atomic remove operation to remove
     * a single <CODE>Producer</CODE> Enity.
     * 
     * @param key The <CODE>ProducerKey</CODE> for the entity to remove
     * 
	 * @exception javax.oss.IllegalArgumentException If passed parameter
     * values are malformed.
	 * @exception javax.oss.UnsupportedOperationException If this operation
     * cannot be supported at this time.
	 * @exception ObjectNotFoundException If corresponding entity can't be found
	 * @exception RemoveException If the object couldn't be removed.
	 * @exception RemoteException If unknown system error occurrs.
	 */
	public void removeProducerByKey(ProducerKey key)
		throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            ObjectNotFoundException, RemoveException
    {
		// Verify the input parameter
		if(key == null)
		{
			throw new javax.oss.IllegalArgumentException("Null key value");
		}

        // Now attempt to remove the producer entity value.
        try
        {
            ProducerEntityHome peh = locateProducerEntityHome();
        
            ProducerEntity producerEnt = peh.findByPrimaryKey(
                (ProducerPrimaryKeyImpl) (key.getProducerPrimaryKey()));
            
        	// After finding the producer entity, get the ProducerValue to
			// return back in the JMS message
        	ProducerValue value = producerEnt.getAttributes(null);
			value.setProducerKey(key);

            producerEnt.remove();
            
        	// Emit a JMS message.
			myEventHelper.sendRemovalEvent(value);
        }

        catch(ObjectNotFoundException e)
        {
            throw e;
        }

        catch(Exception e)
        {
            throw new RemoveException(e.toString());
        }
    }
    
	/**
	 * Remove multiple <CODE>Producers</CODE> by keys, using best effort.
     * <p>
     * A Standard OSS/J bulk remove operation to remove
     * multiple <CODE>Producer</CODE> Entities, using best effort semantics.
     * 
     * @param key The <CODE>ProducerKey</CODE> for the entity to remove
     * 
     * @return Array of ProducerKeyResult for the failed removals. NULL if
     * all succeeded
     * 
	 * @exception javax.oss.IllegalArgumentException If passed parameter
     * values are malformed.
	 * @exception javax.oss.UnsupportedOperationException If this operation
     * cannot be supported at this time.
	 * @exception FinderException If corresponding entity can't be found
	 * @exception RemoveException If the object couldn't be removed due to
     * backend failure
	 * @exception RemoteException If unknown system error occurrs.
	 */
	public ProducerKeyResult[] tryRemoveProducersByKeys(ProducerKey[] keys)
		throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            FinderException, RemoveException
    {
		ProducerKeyResult[] failedKeys=null;

		// Verify the input parameter
		if((keys == null) || (keys.length <= 0))
		{
			throw new javax.oss.IllegalArgumentException("Invalid value array");
		}

		int numKeys=keys.length;
        ArrayList aList = new ArrayList();

		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			try
			{
				removeProducerByKey(keys[keyIdx]);
			}
			catch (Exception ex)
			{
				ProducerKeyResultImpl result=new ProducerKeyResultImpl();
				result.setManagedEntityKey(keys[keyIdx]);
				result.setSuccess(false);
				result.setException(ex);
				aList.add(result);
			}
		}

		// If there were any failures, store them in the array
		if(aList.size() > 0)
		{
			failedKeys = (ProducerKeyResult[]) aList.toArray(
								new ProducerKeyResult[aList.size()]);
		}
		return(failedKeys);
    }
    

	/**
	 * Remove multiple <CODE>Producers</CODE> by value template,
     * using best effort.
     *
     * @param template ProducerValue used to get the ProducerValues
     * that match this template
     * @param failuresOnly if true then only failures information is
     * set to the ProducerKeyResultIterator  
     *
     * @return ProducerKeyResultIterator
     *
     * @exception javax.oss.IllegalArgumentException 
     * @exception javax.oss.UnsupportedOperationException
     * @exception FinderException
     * @exception RemoveException
     * @exception RemoteException
     *
	 */
	public ProducerKeyResultIterator tryRemoveProducersByTemplate(
        ProducerValue template, 
        boolean failuresOnly)
        throws javax.oss.IllegalArgumentException,
            javax.oss.UnsupportedOperationException,
            FinderException, RemoveException
    {
		String[] attrNames = 
		{
			ProducerValue.ACTIVITY_NAME
		};

		ArrayList aList = new ArrayList();	
		ProducerKeyResultIteratorHome localProdKeyIterHome = null;

		// First, check that the arguments are valid
		if(template == null)
		{
			throw new javax.oss.IllegalArgumentException(
									"Input template is null");
		}
		else if (!(template instanceof ProducerValueImpl))
		{
			throw new javax.oss.IllegalArgumentException(
									"Template is not a ProducerValueImpl");
		}

		// Call the method to get all of the producers that match
		// this template
		ProducerValueIterator matchedProdIter = null;
		try
		{
			matchedProdIter = getProducersByTemplate(template, attrNames);
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException("Error on getProducersByTemplate():"+ex);
		}

		// Get the ProducerKeyResultIteratorHome reference
		try
		{
			localProdKeyIterHome = locateProducerKeyResultIteratorHome();
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException(
						"Error getting ProducerKeyResultIteratorHome:" + ex);
		}

		// Verify that there were Producers that matched
		if(matchedProdIter != null)
		{
			ProducerValue[] producerValues;
			try
			{
			while(((producerValues=matchedProdIter.getNextProducers(1)) != null)
								&& (producerValues.length > 0))
			{
				Exception savedExcept=null;
				boolean success=true;
				try
				{
					removeProducerByKey(producerValues[0].getProducerKey());
				}
				catch(Exception ex)
				{
					savedExcept=ex;
					success=false;
				}
				finally
				{
					if((!failuresOnly) || (savedExcept != null))
					{
						ProducerKeyResultImpl results = 
													new ProducerKeyResultImpl();
						results.setManagedEntityKey(
											producerValues[0].getProducerKey());

						results.setSuccess(success);
						results.setException(savedExcept);

						aList.add(results);
					}
				}
			}
			}
			catch(RemoteException ree)
			{
			throw new javax.ejb.EJBException(ree);
			}
            try
            {
                matchedProdIter.remove();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                throw new javax.ejb.EJBException("Error in removeProducerValueIterator(): " + e);
            }

		}

		// Convert the List to an array
        ProducerKeyResult[] prodKeyRes = 
							(ProducerKeyResult[]) aList.toArray(
										new ProducerKeyResult[aList.size()]);

		// Create the iterator and return it
        ProducerKeyResultStatefulIterator prodKeyBeanIter = null;
		try
		{
			prodKeyBeanIter=localProdKeyIterHome.create(prodKeyRes);
		}
		catch(Exception ex)
		{
			throw new javax.ejb.EJBException(
							"Error creating ProducerKeyResultIterator:"+ ex);
		}
		ProducerKeyResultIteratorImpl prodResultIter = 
						new ProducerKeyResultIteratorImpl();
		prodResultIter.setIterBean(prodKeyBeanIter);

		return(prodResultIter);
    }
 

	/**
	 * Getter operations.
	 */

	/**
	 * Get attributes of a single <CODE>Producer</CODE> entity
     *
     * @param key ProducerKey used to specify the Producer
     * @param attrNames Attribute names
     * @return The producerValue is returned.
	 *
     * @exception javax.oss.IllegalArgumentException
     * @exception ObjectNotFoundException
     * @exception RemoteException
     * @exception javax.oss.UnsupportedOperationException
     * @exception NamingException
     * @exception FinderException
	 *
	 */
	public ProducerValue getProducerByKey(ProducerKey key, String[] attrNames) 
			throws javax.oss.IllegalArgumentException,
            ObjectNotFoundException,
            javax.oss.UnsupportedOperationException,
            NamingException, FinderException
    {
		// Verify the input parameters
		if(key == null)
		{
			throw new javax.oss.IllegalArgumentException("Input key is null");
		}

        // Now attempt to find the Producer entity home interface
       	ProducerEntityHome peh = null;
		try
		{
        	peh = locateProducerEntityHome();
		}
		catch(NamingException ex)
		{
			throw ex;
		}
        
		// Now get the Producer entity	
        ProducerEntity pe = null;
		try
		{
        	pe = peh.findByPrimaryKey(
            			(ProducerPrimaryKeyImpl) key.getProducerPrimaryKey());
		}

		catch(RemoteException ex)
		{
			throw new javax.ejb.EJBException(ex);
		}
        try
        {
        // After finding the producer entity, get the attributes
        ProducerValue value = pe.getAttributes(attrNames);
		value.setManagedEntityKey(IPBConfig.getInstance().refreshProducerKey(value.getProducerKey()));
        return value;
        }
        catch(RemoteException ree)
        {
			throw new javax.ejb.EJBException(ree);
        }


    }
    

	/**
	 * Get attributes of multiple <CODE>Producer</CODE> entities by keys.
	 *
     * @param keys Array of ProducerKey used to specify the Producers
     * @param attrNames Attribute names
     * @return array of the producerValues is returned.
     *
     * @exception javax.oss.IllegalArgumentException
     * @exception FinderException
     * @exception RemoteException
     * @exception javax.oss.UnsupportedOperationException
     * @exception NamingException
	 *
	 */
	public ProducerValue[] getProducersByKeys(ProducerKey[] keys, String[] attrNames) 
		throws javax.oss.IllegalArgumentException,
            FinderException,
            javax.oss.UnsupportedOperationException,
            NamingException
    {

		// Verify the input parameters
		if((keys == null) || (keys.length <= 0))
		{
			throw new javax.oss.IllegalArgumentException("No keys specified");
		}

		if((attrNames == null) || (attrNames.length <= 0))
		{
			throw new javax.oss.IllegalArgumentException("No attribute names specified");
		}

		// For each key, retrieve the associated values
		int keyLen=keys.length;
		ArrayList aList = new ArrayList();
		ProducerValue tmpProdVal=null;

		for(int keyIdx=0; keyIdx<keyLen; ++keyIdx)
		{
			tmpProdVal=getProducerByKey(keys[keyIdx], attrNames);
			aList.add(tmpProdVal);
		}

		// Return the List to an array
		return((ProducerValue[]) aList.toArray(new ProducerValue[aList.size()]));
    }
    

	/**
	 * Get attributes of multiple <CODE>Producer</CODE> entities by templeate
     *
     * @param template ProducerValue used to specify the Producer
     * @param attrNames Attribute names
     * @return The producerValue is returned.
	 *
     * @exception javax.oss.IllegalArgumentException
     * @exception FinderException
     * @exception RemoteException
     * @exception javax.oss.UnsupportedOperationException
	 *
	 */
	public ProducerValueIterator getProducersByTemplate(
        ProducerValue template, String[] attrNames) 
		throws javax.oss.IllegalArgumentException,
            FinderException,
            javax.oss.UnsupportedOperationException
    {
		// First, check that the arguments are valid
		if(template == null)
		{
			throw new javax.oss.IllegalArgumentException(
									"Input template is null");
		}
		else if (!(template instanceof ProducerValueImpl))
		{
			throw new javax.oss.IllegalArgumentException(
									"Template is not a ProducerValueImpl");
		}

		if((attrNames == null) || (attrNames.length == 0))
		{
			throw new javax.oss.IllegalArgumentException(
									"Input attrNames is null or empty");
		}

		ProducerValueImpl localTemplate = null;
		try
		{
			localTemplate = (ProducerValueImpl) template;
		}
		catch (Exception ex)
		{
			throw new javax.oss.IllegalArgumentException(
										"Error casting input template:"+ex);
		}
	
		// Now, get all of the producers
		Collection allProducers=null;

		try
		{
       		// Attempt to create the producer entity value.
       		ProducerEntityHome peh = locateProducerEntityHome();
       		allProducers = peh.findAllProducers();
		}
        catch (Exception e)
        {
            throw new javax.ejb.EJBException(
						"Error on finding all producers" + e);
        }
	
		// Verify the return value
		if(allProducers == null)
		{
			throw new javax.ejb.EJBException("Error finding producers");
        }

		// Take the attributes that are passed in and find the producers
		// that have attributes which match
		Iterator prodIter=allProducers.iterator();
		ProducerEntity prodEnt;
		ArrayList aList = new ArrayList();
		while(prodIter.hasNext())
		{
			try
			{
				prodEnt=(ProducerEntity)prodIter.next();
				if(prodEnt.checkAllAttributesForMatch(localTemplate))
				{
					ProducerKey pKey = new ProducerValueImpl().makeProducerKey();
					ProducerPrimaryKey primKey = pKey.makeProducerPrimaryKey();
					primKey.setActivityId(prodEnt.getActivityId());
					primKey.setProducerType(prodEnt.getProducerType());
					pKey.setProducerPrimaryKey(primKey);

					pKey=IPBConfig.getInstance().refreshProducerKey(pKey);

					aList.add(pKey);
				}
			}
			catch(Exception ex)
			{
				throw new javax.ejb.EJBException(
								"Error on checkAllAttributesForMatch():"+ex);
			}
		}

		// Now that we have the list, put it in an iterator.
		ProducerValueStatefulIterator prodValBeanIter=null;
		try
		{
			ProducerValueIteratorHome prodIterHome = 
							locateProducerValueIteratorHome();
        	ProducerKey[] producerKeys  = (ProducerKey[]) aList.toArray(
                    							new ProducerKey[aList.size()]);
			prodValBeanIter=prodIterHome.create(producerKeys, attrNames);
		}
		catch(Exception ex)
		{

			throw new javax.ejb.EJBException(
						"Error creating ProducerValueIterator:" + ex);
		}
		ProducerValueIteratorImpl prodValIter = new ProducerValueIteratorImpl();
		prodValIter.setIterBean(prodValBeanIter);

        return prodValIter;
    }
    

	/**
	 * Set operations
	 */

	/**
	 * Set attributes of a single managed object, by its key
	 *
     * @param value ProducerValue
     * @param resyncRequired If true, check if the attributes on
     *  the input ProducerValue match the corresponding producerEntity
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalArgumentException
     * @exception SetException
     * @exception ObjectNotFoundException
     * @exception RemoteException
     * @exception javax.oss.ResyncRequiredException
	 *
	 */
	public void setProducerByValue(ProducerValue value, boolean resyncRequired) 
		throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException, SetException,
            ObjectNotFoundException,
			javax.oss.ResyncRequiredException
    {
        /** 
         * Verify that there is a input value object
         */
		if(value == null)
		{
			throw new javax.oss.IllegalArgumentException("Input value is null");
		}

		// Else, find that producer
		else
		{
			ProducerKey valueKey = value.getProducerKey();
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey(
				(ProducerPrimaryKeyImpl)valueKey.getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new ObjectNotFoundException(
							"Error finding matching Producer:" + ex);
			}

			// Before proceeding, see if the MediationCapabilities are included
			// in the input attributes
			boolean medCapsDiffer=false;
			MediationCapability[] oldMedCaps=null;
			try
			{
				MediationCapability[] inputMedCap=null;
				inputMedCap=value.getMediationCapabilityValues();

				// If the MediationCapabities are different, then set a flag
				if((inputMedCap != null) && !(matchingProducer.
								checkMediationCapabilityForMatch(inputMedCap)))
				{
					medCapsDiffer=true;
					oldMedCaps=matchingProducer.getMediationCapabilityValues();
				}
			}
			catch (Exception ex)
			{
				// OK - if they aren't set, it's not an issue
			}

			// Now that the producer is retrieved, set the values
			try
			{
        		matchingProducer.setAttributes(value);
        	}

        	catch (Exception ex)
			{
				throw new SetException("Error on setAttributes():" + ex);
			}

			// If the MediationCapabities were modified, then send the
			// JMS message indicating this.
			if(medCapsDiffer)
			{
				myEventHelper.sendMediationCapabilityChangeEvent(value, 
																	oldMedCaps);
			}

			// If the input resync flag is set, check if the attributes on the
			// input ProducerValue match the corresponding producerEntity
			try
			{
			if(resyncRequired)
			{
				// First check if they have the same attributes populated
				String[] inputPopAttr=value.getPopulatedAttributeNames();
				ProducerValue entityVal=matchingProducer.getAttributes(null);
				String[] valPopAttr=entityVal.getPopulatedAttributeNames();

				int inPopLen=inputPopAttr.length;
				int valPopLen=valPopAttr.length;

				if(inPopLen != valPopLen)
				{
					throw new javax.oss.ResyncRequiredException(valueKey, "Attribute count has changed.");
				}


				for(int inIdx=0; inIdx<inPopLen; ++inIdx)
				{
					boolean found=false;
					for(int valIdx=0; valIdx<valPopLen; ++valIdx)
					{
						if(valPopAttr[valIdx].equals(inputPopAttr[inIdx]))
						{
							found=true;
							break;
						}
					}
					if(!found)
					{
						throw new javax.oss.ResyncRequiredException(valueKey, "Previously cleared attribute has been set.");
					}
				}

				// If we got this far, then all of the populated attributes
				// match. Check if the values match
				if(!matchingProducer.checkAllAttributesForMatch(value))
				{
					throw new javax.oss.ResyncRequiredException(valueKey, "An attribute value has been changed.");
				}
			}
		  }catch(RemoteException ree)
		  {
		  throw new javax.ejb.EJBException(ree);
		  }

		}
    }

	/**
	 * Set attributes of multiple managed objects by their keys - best effort.
	 *
     * @param keys array of ProducerKey 
     * @param value ProducerValue used to specify the Producer
	 * @return Array of ProducerKeyResult
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalArgumentException
     * @exception SetException
     * @exception FinderException
     * @exception RemoteException
	 *
	 */
    
	public ProducerKeyResult[] trySetProducersByKeys(
        ProducerKey[] keys, ProducerValue value)
		throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException, SetException,
            FinderException
    {
		// First check the input parameters
		if((keys == null) || (keys.length <= 0 ))
		{
			throw new IllegalArgumentException("No keys were provided");
		}
		if(value == null)
		{
			throw new IllegalArgumentException("No value was provided");
		}

		// For each key passed in, find the corresponding producer entity
		// and set the arguments
		int numKeys=keys.length;
		ProducerKey resultKey=null;
		ArrayList aList = new ArrayList();
		for(int keyIdx=0; keyIdx<numKeys; ++keyIdx)
		{
			boolean success=false;
			ProducerEntity matchingProducer=null;
			ProducerKeyResultImpl results = null;
        	try
        	{
				// Initialize the result values
				results = new ProducerKeyResultImpl();
				results.setManagedEntityKey(keys[keyIdx]);

				// Find the producer entity
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey((ProducerPrimaryKeyImpl)
										keys[keyIdx].getProducerPrimaryKey());

				// Set the attributes
        		matchingProducer.setAttributes(value);

				// If we got this far, all is well
				results.setSuccess(true);
				results.setException(null);
        	}

			// If we get an exception, set it in the results
			catch (Exception ex)
			{
				results.setSuccess(false);
				results.setException(ex);
			}

			aList.add(results);
		}

       	return((ProducerKeyResult[]) 
					aList.toArray(new ProducerKeyResult[aList.size()]));
    }
    

	/**
	 * Set attributes of multiple managed objects by template - best effort.
	 *
     * @param template ProducerValue 
     * @param value ProducerValue
     * @param failuresOnly if true then only failures information is
     * set to the ProducerKeyResultIterator  
     *
     * @return ProducerKeyResultIterator
	 *
     * @exception javax.oss.UnsupportedOperationException
     * @exception javax.oss.IllegalArgumentException
     * @exception SetException
     * @exception FinderException
     * @exception RemoteException
	 *
	 */
	public ProducerKeyResultIterator trySetProducersByTemplate(
        ProducerValue template, 
        ProducerValue value, 
        boolean failuresOnly)
        throws javax.oss.UnsupportedOperationException,
            javax.oss.IllegalArgumentException,
            SetException, FinderException
    {
		String[] attrNames =  
		{
			ProducerValue.ACTIVITY_NAME
		};

		ArrayList aList = new ArrayList();	
		ProducerKeyResultIteratorHome localProdKeyIterHome = null;

		// First, check that the arguments are valid
		if(template == null)
		{
			throw new javax.oss.IllegalArgumentException(
									"Input template is null");
		}
		else if (!(template instanceof ProducerValueImpl))
		{
			throw new javax.oss.IllegalArgumentException(
									"Template is not a ProducerValueImpl");
		}

		if(value == null)
		{
			throw new javax.oss.IllegalArgumentException(
									"Input value is null");
		}
		else if (!(value instanceof ProducerValueImpl))
		{
			throw new javax.oss.IllegalArgumentException(
									"Value is not a ProducerValueImpl");
		}

		// Call the method to get all of the producers that match
		// this template
		ProducerValueIterator matchedProdIter = null;
		try
		{
			matchedProdIter = getProducersByTemplate(template, attrNames);
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException("Error on getProducersByTemplate():"+ex);
		}

		// Get the ProducerKeyResultIteratorHome reference
		try
		{
			localProdKeyIterHome = locateProducerKeyResultIteratorHome();
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException(
						"Error getting ProducerKeyResultIteratorHome:" + ex);
		}

		// Verify that there were Producers that matched
		if(matchedProdIter != null)
		{
			ProducerValue[] producerValues;
			try
			{
			while(((producerValues=matchedProdIter.getNextProducers(1)) != null)
								&& (producerValues.length > 0))
			{
				Exception savedExcept=null;
				boolean success=true;
				try
				{
					value.setProducerKey(producerValues[0].getProducerKey());
					setProducerByValue(value, false);
				}
				catch(Exception ex)
				{
					savedExcept=ex;
					success=false;
				}
				finally
				{
					if((!failuresOnly) || (savedExcept != null))
					{
						ProducerKeyResultImpl results = 
													new ProducerKeyResultImpl();
						results.setManagedEntityKey(
											producerValues[0].getProducerKey());

						results.setSuccess(success);
						results.setException(savedExcept);

						aList.add(results);
					}
				}
			}
			}
			catch(RemoteException eex)
			{
			throw new javax.ejb.EJBException(eex);
			}

            try
            {
                matchedProdIter.remove();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                throw new javax.ejb.EJBException("Error in removeProducerValueIterator(): " + e);
            }

		}

		// Convert the List to an array
        ProducerKeyResult[] prodKeyRes = 
							(ProducerKeyResult[]) aList.toArray(
										new ProducerKeyResult[aList.size()]);

		// Create the iterator and return it
        ProducerKeyResultStatefulIterator prodKeyBeanIter = null;
		try
		{
			prodKeyBeanIter=localProdKeyIterHome.create(prodKeyRes);
		}
		catch(Exception ex)
		{
			throw new javax.ejb.EJBException(
							"Error creating ProducerKeyResultIterator:"+ ex);
		}

		ProducerKeyResultIteratorImpl prodResultIter = 
					new ProducerKeyResultIteratorImpl();
		prodResultIter.setIterBean(prodKeyBeanIter);

		return(prodResultIter);
    }
    
	/**
	 * Query the <CODE>Producer</CODE> by its capability attributes.
     * This method can use <CODE>QueryProducersByMediationCapability</CODE>
     * named query.
	 *
     * @param query QueryValue 
     * @param attrNames Attribute Names
     * @return ProducerValueIterator
	 *
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     * @exception NamingException
     * @exception FinderException
     * @exception CreateException
	 *
     * @see javax.oss.ipb.query.QueryProducersByMediationCapability
	 */
	public ProducerValueIterator queryProducers(
        QueryValue query,
        String[] attrNames) 
		throws javax.oss.IllegalArgumentException,
            NamingException,
            FinderException, CreateException
    {
        QueryHelper qHelper = new QueryHelper();

        qHelper.setProducerEntityHome(
            locateProducerEntityHome());
        
        qHelper.setProducerValueIteratorHome(
            locateProducerValueIteratorHome());
        try
        {
        return qHelper.processProducerQueryResults(
            query, attrNames);
        }
        catch(RemoteException ree)
        {
        throw new javax.ejb.EJBException(ree);
        }
    }
    


    /**
	 * ***************************
	 * The following section defines the discovery of capabilities of
	 * a <CODE>Producer</CODE> by client applications.
	 * ***************************
	 */

    /**
     * Returns the list of all Mediation (Conversion) possibilities
     * supported by IP Billing System.
     * <p>
     * This method is primarily invoked by the clients to find out the
     * current system's mediation capabilities.
     * Once the desired set of mediation capability values are
     * located in the results, the client typically extracts these
     * and uses them to create an instance of ProducerValue with
     * additional attributes.
     * <p>
     * The client may then locate an existing
     * Producer by the created value template (see
     * {@link #getProducersByTemplate}),
     * or query the producer
     * by the mediation capability values
     * (see {@link javax.oss.ipb.query.QueryProducersByMediationCapability}).
     * <p>
     * Alternatively, the client may also create a new Producer Entity by
     * using any one of the createProducer... type methods.
     * 
     * @param value Producer whose mediation capabilities are requested if
     *        null then the MediationCapability objects supported by all
     *        Producers is returned.
     * 
     * @return array of MediationCapability objects supported 
     * 
     * @exception javax.oss.IllegalArgumentException thrown if the argument is
     * malformed
     * @exception ObjectNotFoundException thrown if the ProducerValue isn't 
     * found
     * @exception RemoteException thrown if a system error occurs
     * @exception javax.oss.UnsupportedOperationException Thrown if this 
     * operation isn't supported
     * 
     */
    public MediationCapability[] getSupportedMediationCapabilities(
        ProducerValue value)
		throws javax.oss.IllegalArgumentException, ObjectNotFoundException,
            javax.oss.UnsupportedOperationException
    {
        ArrayList aList = new ArrayList();
		MediationCapability[] producerMedCapabilities=null;
		String[] attrNames = 
		{
			ProducerValue.MEDIATION_CAPABILITIES
		};

        /** 
         * If there is a producerKey specified, return the MediationCapability
         * list for that Producer only
         */
		if(value != null)
		{
			// First, find that Producer.
			ProducerEntity matchingProducer=null;
        	try
        	{
        		ProducerEntityHome peh = locateProducerEntityHome();
        		matchingProducer = peh.findByPrimaryKey(
				(ProducerPrimaryKeyImpl)value.getProducerKey().getProducerPrimaryKey());
        	}
			catch (Exception ex)
			{
				throw new ObjectNotFoundException(
							"Error finding matching Producer:" + ex);
			}

			// Now that the producer is retrieved, get the MediationCapability
			try
			{
        		ProducerValue prodValue = matchingProducer.getAttributes(
																attrNames);
				producerMedCapabilities=prodValue.getMediationCapabilityValues();
        	}

        	catch (Exception e)
        	{
				producerMedCapabilities = new MediationCapability[0];
            	throw new javax.ejb.EJBException(
                	"Unknown error getting MediationCapability values" + e);
        	}
       	}

        /** 
         * Otherwise, build a list of unique MediationCapability objects
         * supported by all Producers
         */
		else
		{
        	Collection allProducers=null;

			try
			{
        		// Now attempt to create the producer entity value.
        		ProducerEntityHome peh = locateProducerEntityHome();
        		allProducers = peh.findAllProducers();
			}
            catch (Exception e)
            {
			   	producerMedCapabilities = new MediationCapability[0];
                    throw new javax.ejb.EJBException(
                        "Error on finding all producers" + e);
            }

			if(allProducers != null)
			{
				Iterator prodIter=allProducers.iterator();

				ProducerEntity prodEnt;

				while(prodIter.hasNext())
				{
                	try
                	{
						MediationCapability[] localProducerMedCap;
						prodEnt=(ProducerEntity)prodIter.next();

        				ProducerValue prodValue = prodEnt.getAttributes(attrNames);

                    	localProducerMedCap=
									prodValue.getMediationCapabilityValues();

				    	MediationCapability retrievedMedCap;
				    	int numMedCap=localProducerMedCap.length;
				    	for(int medCapIdx=0; medCapIdx<numMedCap; ++medCapIdx)
				    	{
					    	retrievedMedCap=localProducerMedCap[medCapIdx];
					    	if(!aList.contains(retrievedMedCap))
					    	{
                		    	aList.add(retrievedMedCap);
					    	}
				    	}
                	}
                	catch (java.lang.IllegalStateException e)
                	{
						// OK - the MediationCapability isn't set for this one
                	}
               
                	catch (Exception e)
                	{
						producerMedCapabilities = new MediationCapability[0];
                    	throw new javax.ejb.EJBException(
                        	"Unknown getting MediationCapability values" + e);
                	}
            	}

            	producerMedCapabilities  =
                	(MediationCapability[]) aList.toArray(
                    	new MediationCapability[aList.size()]);
			}
		}

        return producerMedCapabilities;
    }
    

	/**
     * Returns a MediationCapability instance that is a subset of
     * value array returned by {@link #getSupportedMediationCapabilities},
     * for the specified <CODE>desiredCapability</CODE> parameter.
     * <p>
     * This is the negotiation method for the client to request
     * Mediation capabilities from a Producer.
     * The client builds a desired mediation capability value and then
     * passes it along to the producer session to negotiate this capability
     * from a specific producer. If the <CODE>producerKey</CODE> is omitted,
     * then all the <CODE>Producer</CODE>s for this session will be
     * considered.
     * <p>
	 * If not acceptable, the <CODE>Producer</CODE> will throw
	 * an IPBAPIException. Otherwise, it returns a new
     * <CODE>MediationCapability</CODE>
	 * value that contains the commonly agreeable set of capabilities.
     * 
     * @param pKey ProducerKey used to specify producer
     * @param desiredCapability MediationCapability to try to match
     * @return MediationCapability that matched
     * 
     * @exception javax.oss.IllegalArgumentException thrown if the argument is
     * malformed
     * @exception ObjectNotFoundException thrown if the ProducerKey isn't 
     * found
     * @exception RemoteException Thrown if a system error occurs
     * @exception javax.oss.UnsupportedOperationException Thrown if this 
     * operation isn't supported
     * @exception IPBAPIException Thrown if an IP Billing API incompatiblity
     * occurs 
     * 
	 */
	public MediationCapability matchMediationCapability(
        ProducerKey pKey, 
        MediationCapability desiredCapability)
        throws javax.oss.IllegalArgumentException,
            ObjectNotFoundException,
            javax.oss.UnsupportedOperationException, IPBAPIException
    {
        MediationCapability [] supportedMedCap=null;
        MediationCapability  matchedMedCap=null;

       	/** 
       	* If the Producer Key is specified, get the MediationCapability 
       	* objects supported by that Producer
       	*/
		if(pKey != null)
		{
			String[] attrNames = 
			{
				ProducerValue.MEDIATION_CAPABILITIES
			};

			ProducerValue localProdVal = null;

			try
			{
				localProdVal = getProducerByKey(pKey,attrNames);
			}
			catch(NamingException ex)
			{
				throw new javax.ejb.EJBException(
					"NamingException on getProducerByKey for key:" +
					pKey + "Except: " + ex);
			}
			catch(FinderException ex)
			{
				throw new javax.ejb.EJBException(
					"FinderException on getProducerByKey for key:" +
					pKey + "Except: " + ex);
			}

			supportedMedCap = localProdVal.getMediationCapabilityValues();
		}

       	/** 
       	* Else, get the MediationCapability objects supported by all
		* Producers
       	*/
		else
		{
			supportedMedCap = getSupportedMediationCapabilities(null);
		}

       	/** 
       	* For each MediationCapability objects that is supported,
       	* check the attributes to see if there is a good match.
       	*/
		int medCapLen=supportedMedCap.length;
		for(int medCapIdx=0; medCapIdx < medCapLen; ++medCapIdx)
		{
       		/** 
       		* For this implementation we will check if there is an exact
			* match. Another implementation could weight the attributes
			* of the MediationCapability object to determine which 
			* object is the 'best fit'.
       		*/
			if(desiredCapability.equals(supportedMedCap[medCapIdx]))
			{
				matchedMedCap = 
					(MediationCapability)supportedMedCap[medCapIdx].clone();
				break;
			}
		}

       	/** 
       	* If there wasn't a match found, throw the IPBAPIException.
       	*/
		 
		if( matchedMedCap == null)
		{
			throw new IPBAPIException(
				"Couldn't find match for:" + desiredCapability + 
				" where ProducerKey=" + pKey);
		}
		return matchedMedCap;
    }


    /**
     * This method indicates whether Usage Data Transfers can be monitored
     * for the Producer in question,
     * by creating the TransferStatusValue audit record by the implementation.
     * <p>
     * If an implementation doesn't support audit trailing of the usage data
     * transfers, it should return <CODE>false</CODE> value.
     * <p>
     * NOTE: The JSR130 reference implementation
     * doesn't support this advanced feature.
     * @param value ProducerValue to check for Transfer Monitoring
     * @return flag indicating if Transfer Monitoring is supported
     * @exception javax.oss.IllegalArgumentException thrown if the argument is
     * malformed
     * @exception ObjectNotFoundException thrown if the ProducerValue isn't 
     * found
     * @exception RemoteException Thrown if a system error occurs
     * @exception javax.oss.UnsupportedOperationException Thrown if this 
     * operation isn't supported
     * 
     */
    public boolean supportsTransferMonitoring(
        ProducerValue value)
		throws javax.oss.IllegalArgumentException, ObjectNotFoundException,
            javax.oss.UnsupportedOperationException
    {
        return false;
    }
    
    
        
	/**
	 * Ad-hoc Querying methods.
	 */

    /**
     * Query the Usage Records by its filterable attribues.
     * This is a one time query method which returns a bunch of
     * matching usage reocrds as a iterator. However, no record of
     * any querying is kept. After the iterator is removed,
     * this query and its results are completely forgotten.
     * <p>
     * This is a client initiated ad-hoc querying of usage data in a
     * System. An implementation that supports this method, will
     * be able to service real-time queries from the client
     * for usage data for both current (snap-shot) as well historical data.
     * <p>
     * This operations returns the Iterator to the UsageRecValue resutls.
     * This operation can be invoked by the client at any time.
     * <p>
     * If an implementation cannot support returning the Usage Data
     * as an Iterator, it will throw a UnsupportedOperationException.
     * <p>
     * The difference between this operation and
     * {@link #queryUsageDataInformation}
     * is that the latter returns an iterator to the multiple ReportInfo values.
     * Each of the ReportInfo value can then be inspected by the client
     * to discover the URL or stream connection information to the actual Usage
     * Data on the System. Such approach can be used by the implementations
     * that do not support Iterator based retrieval of Usage Record Data,
     * but still want to allow the clients to query for the indirect
     * Report Information.
     * <p>
     * NOTE: The JSR130 reference implementation supports this advanced
     * feature by returning a fixed set of data on each method invocation.
     *
     * @param query QueryValue specifying the query parameters
     * @param attrNames Names of attributes to return. If null, all attributes
     * are returned
     * @return UsageRecValueIterator of the matching records
     * @exception javax.oss.IllegalArgumentException thrown if the argument is
     * malformed
     * @exception RemoteException Thrown if a system error occurs
     * @exception CreateException thrown if an error occurs in formatting the
     * iterator which is returned
     * @exception javax.oss.UnsupportedOperationException Thrown if this 
     * operation isn't supported
     * @exception NamingException Thrown if a naming error occurs
     *
     * @see javax.oss.ipb.query.QueryByUsageRecFilter
     * 
     */
	public UsageRecValueIterator queryUsageRecords(
        QueryValue query,   // filter to narrow the results.
        String [] attrNames)     // specify which attributes to return.
		throws javax.oss.IllegalArgumentException,
            CreateException, javax.oss.UnsupportedOperationException,
            NamingException
    {
		// Note that this won't really be a query, but will just return
		// an iterator that holds the canned data.

		// Get the iterator home
		UsageRecValueIteratorHome localUsageRecValIterHome = null;
		try
		{
			localUsageRecValIterHome = locateUsageRecValueIteratorHome();
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException(
						"Error getting UsageRecValueIteratorHome:" + ex);
		}

		// Convert the List to an array
        UsageRecValue[] usageRecArray = 
							(UsageRecValue[]) cannedUsageDataList.toArray(
								new UsageRecValue[cannedUsageDataList.size()]);

		// Create the iterator and return it
		UsageRecValueStatefulIterator usageRecBeanIter=null;
		try
		{
			AttributeDescriptor[] attrDescs = 
					cannedUsageDataDescriptor.getFieldsInformation();
			usageRecBeanIter=localUsageRecValIterHome.create(
									cannedUsageDataDescriptor, usageRecArray);
		}
		catch(Exception ex)
		{
			throw new javax.ejb.EJBException(
							"Error creating UsageRecValueIterator:"+ ex);
		}
		UsageRecValueIteratorImpl usageRecIter = new UsageRecValueIteratorImpl();
		usageRecIter.setIterBean(usageRecBeanIter);

		return(usageRecIter);
    }
    

    /**
     * Query the on-going or completed or failed
     * Transfers by queryable attribues. 
     * <p>
     * NOTE: The JSR130  reference implementation
     * doesn't support this advanced feature.
     *
     * @see javax.oss.ipb.query.QueryTransferStatusValue
     *
     * @param query QueryValue specifying the query parameters
     * @param attrNames Names of attributes to return. If null, all attributes
     * are returned
     * @return TransferStatusValueIterator of the matching transfer stat records
     * @exception javax.oss.IllegalArgumentException thrown if the argument is
     * malformed
     * @exception RemoteException Thrown if a system error occurs
     * @exception CreateException thrown if an error occurs in formatting the
     * iterator which is returned
     * @exception javax.oss.UnsupportedOperationException Thrown if this 
     * operation isn't supported
     * @exception NamingException Thrown if a naming error occurs
     *
     */
	public TransferStatusValueIterator queryTransferStatusValues(
        QueryValue query,   // filter to narrow the results.
        String [] attrNames)     // specify which attributes to return.
		throws javax.oss.IllegalArgumentException,
            CreateException, javax.oss.UnsupportedOperationException,
            NamingException
    {
		// The JSR130 reference implementation doesn't support this advanced 
		// feature.
		throw new javax.oss.UnsupportedOperationException(
						"Not supported by the JSR130 reference implementation");
    }
    
    /**
     * Get the Iterator to the Usage Data Records collected by the Producer
     * This is called by the client after it receives a
     * UsageDataAvailableEvent notification from the Producer.
     * The client passes the same notfication value back to the
     * Producer with additional parameters identifying
     * date constraints for the results, to aid in possibly
     * retrieving only newer information.
     * <p>
     * This operation is used by the client to retrieve the results using
     * OSS/J Iterator approach. If the Iterator Report Mode is not one of the
     * Report Modes supported by this Session, an UnsupportedOperationException
     * is thrown. Please see {@link #getSupportedReportModes}.
     * <p>
     * If beginTime parameter is not null, all the reports created
     * beginning from this date are returned by the iterator.
     * If it is null, all the reports by this Activity are retrieved until
     * the endDate is reached.
     * <p>
     * If the endDate is specified, only Report Data collected until
     * this endDate is returned. If it is null, all Report Data from
     * the beginDate is returned.
     * @param usageDataAvailableEvent Event which was broadcast, indicating the
     * availability of new usage data
     * @param filterValue Filter of data that should be returned.
     * @param createTransferStatus Flag indicating if a transfer status
     * record should be created for this request.
     * @param attrNames Array of the names of attributes to retrieve. If null,
     * then all attributes will be retrieved.
     * @param beginDate Time-based filter of start time of data to be returned
     * @param endDate Time-based filter of end time of data to be returned
     * @return  Iterator of UsageRecValues matching the request criteria
     * @exception javax.oss.IllegalArgumentException Thrown if the input
     * parameters are malformed
     * @exception javax.ejb.ObjectNotFoundException Thrown if there is not a
     * Producer or UsageRecValue that matches the request
     * @exception RemoteException If unknown system error occurs.
     * @exception javax.oss.UnsupportedOperationException If this operation
     * cannot be supported at this time.
     */
    public UsageRecValueIterator getUsageRecordsByIterator(
        UsageDataAvailableEvent usageDataAvailableEvent,
        UsageRecFilterValue filterValue,
        boolean createTransferStatus,
        String [] attrNames,
        Calendar beginDate,
        Calendar endDate)
        throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.oss.UnsupportedOperationException
    {
		// The JSR130 reference implementation doesn't currently support this 
		// feature.
		throw new javax.oss.UnsupportedOperationException(
			"Not supported by the JSR130 reference implementation");
    }

    /**
     * Get the Iterator to the Usage Data Record Results Information.
     * This introduces an extra level of indirection over the
     * method {@link #getUsageRecordsByIterator}.
     * 
     * This can be called by the client after it receives a
     * UsageDataAvailableEvent notification or at anytime to
     * retrieve a list of ReportInfo about the Usage Records in a Producer.
     * The client passes the Producer Key to the
     * Producer Session with additional parameters identifying
     * date constraints for the results, to aid in possibly
     * retrieving only newer information.
     * <p>
     * If beginTime parameter is not null, information for
     * all the reports accumulated
     * beginning from this date are returned by the iterator.
     * If it is null, information for all the reports by this Activity 
     * accumulated until the endDate is reached are returned.
     * <p>
     * If the endDate is specified, only Report Data collected until
     * this endDate is returned. If it is null, all Report Data accumulated
     * from the beginDate is returned.
     * <p>
     * NOTE: The JSR130 reference implementation supports this advanced
     * feature by returning a fixed set of data on each method invocation.
     *
     * @param producerKey ProducerKey indicating the entity to use in the query
     * @param filterValue Filter of data that should be returned.
     * @param beginDate Time-based filter of start time of data to be returned
     * @param endDate Time-based filter of end time of data to be returned
     * @return  Iterator of ReportInfo matching the request criteria
     * @exception javax.oss.IllegalArgumentException Thrown if the input
     * parameters are malformed
     * @exception javax.ejb.ObjectNotFoundException Thrown if there is not a
     * Producer that matches the request
     * @exception RemoteException If unknown system error occurs.
     */
    public ReportInfoIterator getUsageDataInformation(
        ProducerKey producerKey,
        UsageRecFilterValue filterValue,
        Calendar beginDate,
        Calendar endDate)
        throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException
    {
		// Note that this won't really be a query, but will just return
		// an iterator that holds the canned data.

		// Get the iterator home
		ReportInfoIteratorHome localReportInfoIterHome = null;
		try
		{
			localReportInfoIterHome = locateReportInfoIteratorHome();
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException(
						"Error getting ReportInfoIteratorHome:" + ex);
		}

		// Convert the List to an array
        ReportInfo[] reportInfoArray = 
							(ReportInfo[]) cannedReportInfoList.toArray(
								new ReportInfo[cannedReportInfoList.size()]);


		// Create the iterator and return it
		ReportInfoStatefulIterator reportInfoBeanIter=null;
		try
		{
			AttributeDescriptor[] attrDescs = 
						cannedUsageDataDescriptor.getFieldsInformation();
			reportInfoBeanIter=localReportInfoIterHome.create(reportInfoArray);
		}
		catch(Exception ex)
		{
			throw new javax.ejb.EJBException(
							"Error creating ReportInfoIterator:"+ ex);
		}
		ReportInfoIteratorImpl rptInfoIter = new ReportInfoIteratorImpl();
		rptInfoIter.setIterBean(reportInfoBeanIter);

		return(rptInfoIter);
    }

    /**
     * Get the Iterator to the Usage Data Results Information.
     * This introduces an extra level of indirection over the
     * method {@link #queryUsageRecords}.
     * <p>
     * This operations returns the Iterator to the ReportInfo values.
     * This operation can be invoked by the client at any time
     * during its life-time.
     * <p>
     * This operation returns an iterator to the multiple ReportInfo values.
     * Each of the ReportInfo value can then be inspected by the client
     * to discover the URL or stream information to the actual Usage Record
     * Data on the System. Such approach can be used by the implementations
     * that do not support Iterator based retrieval for direct Usage Record Data,
     * but still want to allow the clients to query for the indirect
     *
     * Report Information about the Usage Data Records.
     * <p>
     * NOTE: The JSR130 reference implementation supports this advanced
     * feature by returning a fixed set of data on each method invocation.
     *
     * @see javax.oss.ipb.query.QueryByUsageRecFilter
     * @param queryValue Query specifyin criteria for data to return
     * @param attributeNames Array of the names of attributes to retrieve. If
     * null, then all attributes will be retrieved.
     * @return  Iterator of ReportInfo matching the request criteria
     * @exception javax.oss.IllegalArgumentException Thrown if the input
     * parameters are malformed
     * @exception javax.ejb.ObjectNotFoundException Thrown if there is not a
     * Producer that matches the request
     * @exception RemoteException If unknown system error occurs.
     * @exception javax.oss.UnsupportedOperationException If this operation
     * cannot be supported at this time.
     */
    public ReportInfoIterator queryUsageDataInformation(
        QueryValue queryValue,
        String [] attributeNames)
        throws javax.oss.IllegalArgumentException,
            javax.ejb.ObjectNotFoundException,
            javax.oss.UnsupportedOperationException
    {
		// Note that this won't really be a query, but will just return
		// an iterator that holds the canned data.

		// Get the iterator home
		ReportInfoIteratorHome localReportInfoIterHome = null;
		try
		{
			localReportInfoIterHome = locateReportInfoIteratorHome();
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException(
						"Error getting ReportInfoIteratorHome:" + ex);
		}

		// Convert the List to an array
        ReportInfo[] reportInfoArray = 
							(ReportInfo[]) cannedReportInfoList.toArray(
								new ReportInfo[cannedReportInfoList.size()]);


		// Create the iterator and return it
		ReportInfoStatefulIterator reportInfoBeanIter=null;
		try
		{
			AttributeDescriptor[] attrDescs = 
						cannedUsageDataDescriptor.getFieldsInformation();
			reportInfoBeanIter=localReportInfoIterHome.create(reportInfoArray);
		}
		catch(Exception ex)
		{
			throw new javax.ejb.EJBException(
							"Error creating ReportInfoIterator:"+ ex);
		}
		ReportInfoIteratorImpl rptInfoIter = new ReportInfoIteratorImpl();
		rptInfoIter.setIterBean(reportInfoBeanIter);

		return(rptInfoIter);
    }
    


    /**------------------------------------------------------------
     * EJB specific methods.
     * ----------------------------------------------------------*/

    /**
     * Initialize home interface for JVTProducerSession Bean.
     */
    public void ejbCreate()
    {
        /**
         * Initialize the members.
         */
        if(psProducerTypes == null)
        {
            psProducerTypes = new ArrayList();

            String [] res =
            {
                ProducerValueImpl.class.getName()
            };
            
            addMoreSupportedProducerTypes(
                res);
        }

        if(psUsageRecFilterTypes == null)
        {
            psUsageRecFilterTypes = new ArrayList();

            String [] res =
            {
                UsageRecFilterValueImpl.class.getName()
            };
            
            addMoreSupportedUsageRecFilterTypes(
                res);
        }

        if(psEventTypes == null)
        {
            psEventTypes = new ArrayList();

            String [] res =
            {
                MediationCapabilityChangeEventImpl.class.getName(),
                UsageDataAvailableEventImpl.class.getName(),
                ActivityRemovalEventImpl.class.getName(),
                ActivityCreationEventImpl.class.getName(),
                ActivitySuspendEventImpl.class.getName(),
                ActivityResumeEventImpl.class.getName()
            };
            
            addMoreSupportedEventTypes(
                res);
        }

        if(psEventDescMap == null)
        {
            psEventDescMap = new HashMap();

			psEventDescMap.put(
					MediationCapabilityChangeEventImpl.class.getName(),
					new MediationCapabilityChangeEventPropertyDescriptorImpl());
			psEventDescMap.put(
					UsageDataAvailableEventImpl.class.getName(),
					new UsageDataAvailableEventPropertyDescriptorImpl());
			psEventDescMap.put(
					ActivityRemovalEventImpl.class.getName(),
					new ActivityRemovalEventPropertyDescriptorImpl());
			psEventDescMap.put(
					ActivityCreationEventImpl.class.getName(),
					new ActivityCreationEventPropertyDescriptorImpl());
			psEventDescMap.put(
					ActivitySuspendEventImpl.class.getName(),
					new ActivitySuspendEventPropertyDescriptorImpl());
			psEventDescMap.put(
					ActivityResumeEventImpl.class.getName(),
					new ActivityResumeEventPropertyDescriptorImpl());
        }

        if(psQueryTypes == null)
        {
            psQueryTypes = new ArrayList();

            String [] res =
            {
                QueryByUsageRecFilterImpl.class.getName(),
                QueryProducersByMediationCapabilityImpl.class.getName()
            };
            
            addMoreSupportedQueryTypes(
                res);
        }

        if(psMediationCapabilityTypes == null)
        {
            psMediationCapabilityTypes = new ArrayList();

            String [] res =
            {
                MediationCapabilityImpl.class.getName()
            };
            
            addMoreSupportedMediationCapabilityTypes(
                res);
        }

		if(myEventHelper ==  null)
		{
			myEventHelper = EventHelper.getInstance();
		}

		if(cannedUsageDataList == null)
		{
			initializeCannedUsageData();
		}

		if(cannedReportInfoList == null)
		{
			initializeCannedReportInfo();
		}
    }

    /**
     *
     * Free the resources. Resets the references to
     * Producer Entity beans.
     *
     * @exception RemoteException
     */
    public void ejbRemove()
        throws RemoteException
    {
        // Clean up the JMS stuff.
        if(myEventHelper != null)
        {
            myEventHelper.cleanup();
        }
        
        return;
    }

    /**
     * Activation callback.
     *
     * @exception RemoteException
     */
    public void ejbActivate()
        throws RemoteException
    {
        return;
    }

    /**
     * De-Activattion callback.
     *
     * @exception RemoteException
     */
    public void ejbPassivate()
        throws RemoteException
    {
    }

    /**
     * SessionContext callback.
     *
     * @exception RemoteException
     */
    public void setSessionContext(
        SessionContext sessionContext)
        throws RemoteException
    {
        this.sessionContext = sessionContext;

    }

    /**------------------------------------------------------------
     * javax.oss.JVTSession specific methods.
     * ----------------------------------------------------------*/
    /**
     * Get the names of the optional operations supported by a JVT 
     * Session Bean. The names of the optional operations are defined
     * in the <CODE>JVT<ApplicationType>SessionOptionalOps</CODE> interface as defined 
     * by the API
     *
     * 
     * @return String array which contains the names of all the optional 
     * operations supported by an implementation of a JVT Session Bean.
     *
     * @exception RemoteException
     */
    public String[] getSupportedOptionalOperations()
    {
        return new String[0];
    }
    
    /**
     * Get the Managed Entity types supported by a JVT Session Bean
     * 
     * @return String array which contains the fully qualified names of the leaf 
     * node interfaces representing the supported managed entity types. 
     * Note that it is not the implementation class name that is returned.
     * @exception RemoteException
     */
    
    public String[] getManagedEntityTypes()
    {
        return getProducerValueTypes();
    }
    

    /**
     * Get the Query type names supported by a JVT Session Bean
     * 
     * @exception RemoteException
     */
    
    public String[] getQueryTypes()
    {
        String [] result =
            (String[]) psQueryTypes.toArray(
                new String[psQueryTypes.size()]);
        
        return result;
    }


    /**
     * Get the Event Type names supported by the JVT Session Bean
     * 
     * @return array of string which contains the fully qualified name of the 
     * leaf node representing the supported event types
     * @exception RemoteException
     */
    //Get the Event Type Names supported by the Session Component
    public String[] getEventTypes()
    {
        String [] result =
            (String[]) psEventTypes.toArray(
                new String[psEventTypes.size()]);
        return result;
    }


    /**
     * Create a Query Value Instance matching a Query type name.
     * 
     * @param type fully qualified name of the leaf node QueryValue interface
     * @return implementation class of a query value of a specific type
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     */
     
    public QueryValue makeQueryValue(String type)
        throws javax.oss.IllegalArgumentException
    {
        QueryValue qValue = null;
        
        if(type == null)
        {
            throw new javax.oss.IllegalArgumentException(
                "Supplied NULL Query typename argument");
            
        }

        if(psQueryTypes.contains(type))
        {
            // We support this type.
            // Create an instance of it.

            ClassLoader cl = getClass().getClassLoader();
            Class queryClass = null;
            try
            {
                queryClass = cl.loadClass(type);

                qValue = (QueryValue) queryClass.newInstance();
            }
            catch(Exception e)
            {
                throw new javax.ejb.EJBException(
                    "Unknown Exception while looking for " + type +
                    e);
            }

        }
        
        /**
         * At present we don't handle anything else. Just throw an exception
         */
        else
        {
            throw new javax.oss.IllegalArgumentException (
                "Unsupported Query Type received: " + type);
        }
        
        return qValue;
    }
    

    /**
     * Get the Event Descriptor associated with an event type name.
     * 
     * @param event_type fully qualified name of the leaf node Event interface
     * @param eventType
     * @return EventPropertyDescriptor which can be used to discover the 
     * filterable properties of a specific event type.
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     */
    
    public EventPropertyDescriptor getEventDescriptor(
        String eventType)
        throws javax.oss.IllegalArgumentException
    {
		// First verify that the argument is valid
		if ((eventType == null) || (eventType.length() == 0))
		{
			throw new javax.oss.IllegalArgumentException(
				"Input event type is not valid:" + eventType);
		}		

		// Now verify that this is a supported event type
		if(!psEventTypes.contains(eventType))
		{
			throw new javax.oss.IllegalArgumentException(
				"Input event type is not supported:" + eventType);
		}

		// Now, create an instance of the event descriptor
		EventPropertyDescriptor eventDescInst = 
						(EventPropertyDescriptor) psEventDescMap.get(eventType);

		// If there isn't a descriptor defined, throw an exception
		if(eventDescInst == null)
		{
			throw new javax.oss.IllegalArgumentException(
						"No descriptor defined for eventType: " + eventType);
		}

        return (eventDescInst);
    }

    /**
     * Create a Value Type object for a specific Managed Entity type. 
     * Not to be confused with the creation of a Managed Entity. 
     * The Session Bean is used as a factory for the creation of 
     * Value types.
     * 
     * @param valueType fully qualified name of the leaf managed entity value interface
     * @return the implementation class of a managed entity of a specific type
	 * @exception javax.oss.IllegalArgumentException Thrown if the type is
     * invalid
	 * @exception RemoteException Thrown if a system processing error occurs
     */
    
    public ManagedEntityValue makeManagedEntityValue( String valueType)
        throws javax.oss.IllegalArgumentException
    {
		return(makeProducerValue(valueType)); 
    }
    
    /**
     * Query Multiple Managed Entities using a QueryValue
     * 
     * @param query A QueryValue object representing the Query.
     * @param attrNames representing the attribute names
     * @return A ManagedEnityValueIterator used to extract the results of the Query.
     * @exception javax.oss.IllegalArgumentException
     * @exception RemoteException
     */
    public ManagedEntityValueIterator queryManagedEntities(
        QueryValue query , String[] attrNames)
        throws javax.oss.IllegalArgumentException
    {
		ProducerValueIterator prodValIter = null;

		// First, check the args
		if ((query != null) && 
			(!(query instanceof QueryProducersByMediationCapabilityImpl)))
		{
			throw new javax.oss.IllegalArgumentException(
				"Template is not a QueryProducersByMediationCapabilityImpl");
		}

		// Call the derived method for querying the Producers
		try
		{
			prodValIter = queryProducers(query,attrNames);
		}
		catch (Exception ex)
		{
			throw new javax.ejb.EJBException("Error on queryProducers():" + ex);
		}

		return((ManagedEntityValueIterator)prodValIter);
    }
    
    /**------------------------------------------------------------
     * javax.oss.JVTSession specific methods.
     * ----------------------------------------------------------
     */


    /**
     * Protected methods of Producer Session.
     */
    /**
     * Gets the Naming Context for this Bean.
     * 
     * @return InitialContext  
     */
    protected InitialContext getNamingContext()
    {
        if (myNamingContext == null)
        {
            try
            {
                myNamingContext = new javax.naming.InitialContext();
            }
            catch (javax.naming.NamingException ne)
            {
                ne.printStackTrace();
                myNamingContext = null;
            }
        }
        
        return myNamingContext;
    }

    /**
     * Gets the Application DN Value.
     * 
     * @return Application DN Value
     */
    protected String getApplicationDN()
    {
        Object applicationDNValue = null;
        
        if (myApplicationDN == null)
        {
            applicationDNValue = lookup("java:comp/env/ApplicationDN");
            if(applicationDNValue == null)
            {
                return null;
            }
            
            myApplicationDN = (String)applicationDNValue;
        }

        return myApplicationDN;
    }

    /**
     * Looks up the value for the specified key in the JNDI.
     * 
     * @param  name it is used to look up the value for specified key in the JNDI
     * @return NamingContext if it can find
     */
    protected Object lookup(String name)
    {
        Object obj = null;
        try
        {
            obj = getNamingContext().lookup(name);
        }
        catch (javax.naming.NamingException ne)
        {
            ne.printStackTrace();
        }
        
        return obj;
    }
    
    /**
     * Gets the Application Context 
     * 
     * @return Application Context 
     */
    protected ApplicationContext getApplicationContext()
    {
        if(myApplicationContext == null)
        {
            myApplicationContext =
                new ApplicationContextImpl(null, null, null);
        }
        
        return myApplicationContext;
    }

    /**
     * Gets the Application Context 
     * 
     * @return ArrayList of the supported ProducerKeyTypes 
     * @exception RemoteException
     */
    protected ArrayList getSupportedProducerKeyTypes()
    {
        ArrayList aList = new ArrayList(2);

        for (int i = 0; i < psProducerTypes.size(); ++i)
        {
            try
            {
                ClassLoader cl = getClass().getClassLoader();
                Class producerValueClass =
                    cl.loadClass((String) psProducerTypes.get(i));
                ProducerValueImpl value =  (ProducerValueImpl)
                    producerValueClass.newInstance();

                /**
                 * Get the supported key type.
                 */
                aList.add(value.getProducerKeyType());

            }
                
            catch (Exception e)
            {
                throw new javax.ejb.EJBException(
                    "Unknown ProducerValue Type" + e);
            }
                
            break;
        }
        
        return aList;
        
    }

    /**
     * Add more Producer Managed Entity types
     * to be handled by this session bean.
     *
     * @param types String array which containes Producer Managed Entity type names
     */
    protected void addMoreSupportedProducerTypes(
        String [] types)
    {
        if(types == null)
        {
            return;
        }

        for(int i = 0; i < types.length; ++i)
        {
            // check if this type has already been added to the list.
            if(psProducerTypes.contains(types[i]))
            {
                // This is already added.
                continue;
            }
            // Check if this is a loadable class and instatiable class.
            
            try
            {
                // We discard the returned class instance for
                // garbage collection
                loadAndCreateInstance(types[i]);

                // No exception means, everything is fine.
                psProducerTypes.add(types[i]);
            }

            catch(Exception e)
            {
                // log the error.
                System.err.println(
                    "Invalid class type specification at index: " +
                    i + " Entry: " + types[i] + ":" + e);

                // Continue with the rest of the entries.
            }
            
        }
        
    }

    /**
     * Add more Usage Record Filter types to be handled by this session bean.
     *
     * @param types String array which containes Usage Record Filter type names
     */
    protected void addMoreSupportedUsageRecFilterTypes(
        String [] types)
    {
        if(types == null)
        {
            return;
        }

        for(int i = 0; i < types.length; ++i)
        {
            // check if this type has already been added to the list.
            if(psUsageRecFilterTypes.contains(types[i]))
            {
                // This is already added.
                continue;
            }
            
            // Check if this is a loadable class and instatiable class.
            try
            {
                // We discard the returned class instance for
                // garbage collection
                loadAndCreateInstance(types[i]);

                // No exception means, everything is fine.
                psUsageRecFilterTypes.add(types[i]);
            }

            catch(Exception e)
            {
                // log the error.
                System.err.println(
                    "Invalid class type specification at index: " +
                    i + " Entry: " + types[i]);

                // Continue with the rest of the entries.
            }
            
        }
        
    }

    /**
     * Add more Event types to be handled by this session bean.
     *
     * @param types String array which containes Event type names
     */
    protected void addMoreSupportedEventTypes(
        String [] types)
    {
        if(types == null)
        {
            return;
        }

        for(int i = 0; i < types.length; ++i)
        {
            // check if this type has already been added to the list.
            if(psEventTypes.contains(types[i]))
            {
                // This is already added.
                continue;
            }
            
            // Check if this is a loadable class and instatiable class.
            try
            {
                // We discard the returned class instance for
                // garbage collection
                loadAndCreateInstance(types[i]);

                // No exception means, everything is fine.
                psEventTypes.add(types[i]);
            }

            catch(Exception e)
            {
                // log the error.
                System.err.println(
                    "Invalid class type specification at index: " +
                    i + " Entry: " + types[i]);

                // Continue with the rest of the entries.
            }
            
        }
        
    }

    /**
     * Add more Query types to be handled by this session bean.
     *
     * @param types String array which containes Query type names
     */
    protected void addMoreSupportedQueryTypes(
        String [] types)
    {
        if(types == null)
        {
            return;
        }

        for(int i = 0; i < types.length; ++i)
        {
            // check if this type has already been added to the list.
            if(psQueryTypes.contains(types[i]))
            {
                // This is already added.
                continue;
            }
            
            // Check if this is a loadable class and instatiable class.
            try
            {
                // We discard the returned class instance for
                // garbage collection
                loadAndCreateInstance(types[i]);

                // No exception means, everything is fine.
                psQueryTypes.add(types[i]);
            }

            catch(Exception e)
            {
                // log the error.
                System.err.println(
                    "Invalid class type specification at index: " +
                    i + " Entry: " + types[i]);

                // Continue with the rest of the entries.
            }
        }
    }


    /**
     * Add more MediationCapability types
     * to be handled by this session bean.
     *
     * @param types String array which containes MediationCapability type names
     */
    protected void addMoreSupportedMediationCapabilityTypes(
        String [] types)
    {
        if(types == null)
        {
            return;
        }

        for(int i = 0; i < types.length; ++i)
        {
            // check if this type has already been added to the list.
            if(psMediationCapabilityTypes.contains(types[i]))
            {
                // This is already added.
                continue;
            }
            // Check if this is a loadable class and instatiable class.
            
            try
            {
                // We discard the returned class instance for
                // garbage collection
                loadAndCreateInstance(types[i]);

                // No exception means, everything is fine.
                psMediationCapabilityTypes.add(types[i]);
            }

            catch(Exception e)
            {
                // log the error.
                System.err.println(
                    "Invalid class type specification at index: " +
                    i + " Entry: " + types[i]);

                // Continue with the rest of the entries.
            }
        }
    }

 
 	/**
	 * Utility method to load a class and create an instance of it
	 * 
	 * @param type Name of the Class to load
	 * @return Object created
	 * @exception Exception Thrown when an error occurs 
	 */
    protected Object loadAndCreateInstance(
        String type)
        throws Exception
    {
        Class cls = loadClassType(type);
        return createInstance(cls);
    }

 	/**
	 * Utility method to load a class
	 * 
	 * @param type Name of the Class to load
	 * @return Class loaded
	 * @exception Exception Thrown when an error occurs loading the class
	 */
    protected Class loadClassType(
        String type)
        throws Exception
    {
        if(type == null)
        {
            throw new Exception("Invalid Class Type specification");
        }
        
        ClassLoader cl = getClass().getClassLoader();

        return cl.loadClass(type);
    }

 	/**
	 * Utility method to create an instance of a class
	 * 
	 * @param classValue Class of the object to create
	 * @return Object created
	 * @exception Exception Thrown when an error occurs creating the object
	 */
    protected Object createInstance(
        Class classValue)
        throws Exception
    {
        if(classValue == null)
        {
            throw new Exception("Invalid Class specification");
        }
        
        Object tmpObj= classValue.newInstance();
        return tmpObj;
    }

 	/**
	 * Lookup the ProducerEntity Home interface
	 * 
	 * @return ProducerEntityHome interface
	 * @exception NamingException Thrown when an error occurs finding the I/F
	 */
    protected ProducerEntityHome locateProducerEntityHome()
        throws NamingException
    {
        if(myProducerEntityHome != null)
        {
            return myProducerEntityHome;
        }

		Context ctx = new InitialContext();
		String prodEntJNDIName =
			(String) ctx.lookup( "java:comp/env/ProducerEntityBean_JNDI_Name");

        Object home = getNamingContext().lookup(prodEntJNDIName);

        myProducerEntityHome =  (ProducerEntityHome)
            PortableRemoteObject.narrow(home,
                                        ProducerEntityHome.class);

        return myProducerEntityHome;
        
    }

 	/**
	 * Lookup the ProducerValueIterator Home interface
	 * 
	 * @return ProducerValueIteratorHome interface
	 * @exception NamingException Thrown when an error occurs finding the I/F
	 */
    protected ProducerValueIteratorHome locateProducerValueIteratorHome()
        throws NamingException
    {
        final String PROD_VAL_ITERATOR_JNDI_NAME =
            "java:comp/env/ejb/ProducerValueIteratorHome";

        if(myProducerValueIteratorHome != null)
        {
            return myProducerValueIteratorHome;
        }
        
        Object home = getNamingContext().lookup(PROD_VAL_ITERATOR_JNDI_NAME);

        myProducerValueIteratorHome =
            (ProducerValueIteratorHome) PortableRemoteObject.narrow(
                home,
                ProducerValueIteratorHome.class);

        return myProducerValueIteratorHome;
    }

 	/**
	 * Lookup the ProducerKeyResultIterator Home interface
	 * 
	 * @return ProducerKeyResultIteratorHome interface
	 * @exception NamingException Thrown when an error occurs finding the I/F
	 */
    protected ProducerKeyResultIteratorHome locateProducerKeyResultIteratorHome()
        throws NamingException
    {
        final String PROD_VAL_ITERATOR_JNDI_NAME =
            "java:comp/env/ejb/ProducerKeyResultIteratorHome";

        if(myProducerKeyResultIteratorHome != null)
        {
            return myProducerKeyResultIteratorHome;
        }
        
        Object home = getNamingContext().lookup(PROD_VAL_ITERATOR_JNDI_NAME);

        myProducerKeyResultIteratorHome =
            (ProducerKeyResultIteratorHome) PortableRemoteObject.narrow(
                home,
                ProducerKeyResultIteratorHome.class);

        return myProducerKeyResultIteratorHome;
    }

 	/**
	 * Lookup the UsageRecValueIterator Home interface
	 * 
	 * @return UsageRecValueIteratorHome interface
	 * @exception NamingException Thrown when an error occurs finding the I/F
	 */
    protected UsageRecValueIteratorHome locateUsageRecValueIteratorHome()
        throws NamingException
    {
        final String USAGE_REC_VAL_ITERATOR_JNDI_NAME =
            "java:comp/env/ejb/UsageRecValueIteratorHome";

        if(myUsageRecValueIteratorHome != null)
        {
            return myUsageRecValueIteratorHome;
        }
        
        Object home = getNamingContext().lookup(USAGE_REC_VAL_ITERATOR_JNDI_NAME);

        myUsageRecValueIteratorHome =
            (UsageRecValueIteratorHome) PortableRemoteObject.narrow(
                home,
                UsageRecValueIteratorHome.class);

        return myUsageRecValueIteratorHome;
    }

 	/**
	 * Lookup the ReportInfoIterator Home interface
	 * 
	 * @return ReportInfoIteratorHome interface
	 * @exception NamingException Thrown when an error occurs finding the I/F
	 */
    protected ReportInfoIteratorHome locateReportInfoIteratorHome()
        throws NamingException
    {
        final String REPORT_INFO_ITERATOR_JNDI_NAME =
            "java:comp/env/ejb/ReportInfoIteratorHome";

        if(myReportInfoIteratorHome != null)
        {
            return myReportInfoIteratorHome;
        }
        
        Object home = getNamingContext().lookup(REPORT_INFO_ITERATOR_JNDI_NAME);

        myReportInfoIteratorHome =
            (ReportInfoIteratorHome) PortableRemoteObject.narrow(
                home,
                ReportInfoIteratorHome.class);

        return myReportInfoIteratorHome;
    }

 	/**
	 * Initialized the canned UsageData used by the RI
	 */
    protected void initializeCannedUsageData()
    {
		cannedUsageDataList = new ArrayList();

		for(int theIndex=0; theIndex < 5; ++theIndex)
		{
			// Create a Usage Record 

			UsageRecValue usageRec = new UsageRecValueImpl();
	
			// Fill in all of the types of the Usage Record with data values
			usageRec.setBooleanValue("theboolean", true);
			usageRec.setByteValue("thebyte", (byte)1234);
			usageRec.setShortValue("theshort", (short)5678);
			usageRec.setCharValue("thechar", '\u1234');
			usageRec.setIntValue("theint", (int)5678);
			usageRec.setLongValue("thelong", (long)1234);
			usageRec.setFloatValue("thefloat", (float)5678);
			usageRec.setDoubleValue("thedouble", (double)1234);
			usageRec.setStringValue("theString", "String Value");

			cannedUsageDataList.add(usageRec);

		}

		// Create a Record Descriptor
		cannedUsageDataDescriptor = new RecordDescriptorImpl();
		cannedUsageDataDescriptor.setReportRecordType("Canned JSR130 RI Data");

		AttributeDescriptor[] attrDescs = new AttributeDescriptor[9];
		attrDescs[0] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[0].setType(AttributeDescriptor.BOOLEAN);
		attrDescs[0].setName("theboolean");
		attrDescs[1] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[1].setType(AttributeDescriptor.BYTE);
		attrDescs[1].setName("thebyte");
		attrDescs[2] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[2].setType(AttributeDescriptor.SHORT);
		attrDescs[2].setName("theshort");
		attrDescs[3] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[3].setType(AttributeDescriptor.CHAR);
		attrDescs[3].setName("thechar");
		attrDescs[4] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[4].setType(AttributeDescriptor.INTEGER);
		attrDescs[4].setName("theint");
		attrDescs[5] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[5].setType(AttributeDescriptor.LONG);
		attrDescs[5].setName("thelong");
		attrDescs[6] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[6].setType(AttributeDescriptor.FLOAT);
		attrDescs[6].setName("thefloat");
		attrDescs[7] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[7].setType(AttributeDescriptor.DOUBLE);
		attrDescs[7].setName("thedouble");
		attrDescs[8] = cannedUsageDataDescriptor.makeAttributeDescriptor();
		attrDescs[8].setType(AttributeDescriptor.STRING);
		attrDescs[8].setName("theString");

		cannedUsageDataDescriptor.setFieldsInformation(attrDescs);
    }

 	/**
	 * Initialized the canned ReportData used by the RI
	 */
    protected void initializeCannedReportInfo()
    {
		cannedReportInfoList = new ArrayList();

		for(int theIndex=0; theIndex < 5; ++theIndex)
		{
			// Create a ReportInfo object
			ReportInfo reportInfo = new ReportInfoImpl();

			// Make a ReportFormat object and initialize it
			ReportFormat theFmt = reportInfo.makeReportFormat();
			theFmt.setType(ReportFormat.XML);
			theFmt.setVersion("3.1");
			theFmt.setTechnology("VOD");

			try
			{
				// Fill in all the fields of the ReportInfo
				reportInfo.setReportFormat(theFmt);
				reportInfo.setURL(new URL("http://localhost/report_"+theIndex));
			}

			catch(Exception ex)
			{
				System.out.println("Error formatting URL");
			}

			cannedReportInfoList.add(reportInfo);
		}
    }
}
