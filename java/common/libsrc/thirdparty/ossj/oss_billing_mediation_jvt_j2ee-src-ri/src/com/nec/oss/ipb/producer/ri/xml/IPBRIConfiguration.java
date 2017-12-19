package com.nec.oss.ipb.producer.ri.xml;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.lang.reflect.Method;
import javax.oss.*;
import javax.oss.ApplicationContext;
import javax.oss.EventPropertyDescriptor;
import javax.oss.ManagedEntityKey;
import javax.oss.ManagedEntityValue;
import javax.oss.cfi.activity.*;
import javax.oss.ipb.event.*;
import javax.oss.ipb.exception.*;
import javax.oss.ipb.producer.*;
import javax.oss.ipb.query.*;
import javax.oss.ipb.transfer.*;
import javax.oss.ipb.type.*;
import com.nec.oss.cfi.activity.ri.*;
import com.nec.oss.ipb.event.ri.*;
import com.nec.oss.ipb.producer.ri.*;
import com.nec.oss.ipb.query.ri.*;
import com.nec.oss.ipb.transfer.ri.*;
import com.nec.oss.ipb.type.ri.*;
import com.nokia.oss.ossj.common.ri.*;
import javax.oss.JVTSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.ilog.ossj.xml.binding.ClassDescriptor;
import com.ilog.ossj.xml.binding.FieldDescriptor;
import com.ilog.ossj.xml.binding.MethodDescriptor;
import com.ilog.ossj.xml.binding.Registry;
import com.ilog.ossj.xml.binding.ClassUtils;
import com.ilog.ossj.xml.util.Utilities;
import com.ilog.ossj.xml.util.XmlProperties;
import com.nec.oss.ipb.producer.ri.xml.IPBRIRegistry;

/**
 * This is a utility class which configures the XML Tooling Registry.
 * This class performs the IP Billing API initialization of the Registry
 * to allow use of the XML Tooling API classes to perform marshal/unmarshal
 * between Java objects and XML. This class follows the example of the
 * SampleConfiguration provided with the XML Tooling API
 */
public class IPBRIConfiguration 
{
	protected static IPBRIRegistry  registry = null;
	protected static HashMap		methodArgs = new HashMap();

	/**
	 * Configure the XML binding for types in the Common API.
	 */
	public static void configureCommonAPIBinding() 
	{
		ClassDescriptor classDesc;

		// javax.oss.ApplicationContext is special case, see below
		// ManagedEntityKey
		classDesc = registry.getOrMakeClassDescriptor(ManagedEntityKey.class);

		classDesc.ignoreField("primaryKey");
		classDesc.setLocalFieldOrder(IPBRIConfiguration.ManagedEntityKeyFields);

		// ManagedEntityKeyResults
		classDesc = 
			registry.getOrMakeClassDescriptor(ManagedEntityKeyResult.class);

		classDesc.setLocalFieldOrder(IPBRIConfiguration.ManagedEntityKeyResultFields);

		// ManagedEntityValue
		classDesc = 
			registry.getOrMakeClassDescriptor(ManagedEntityValue.class);

		classDesc.ignoreField("managedEntityKey");

		// QueryValue
		classDesc = registry.getOrMakeClassDescriptor(QueryValue.class);

		classDesc.setImplementationClass(com.nec.oss.ri.QueryValueImpl.class);

		// Event
		classDesc = registry.getOrMakeClassDescriptor(Event.class);

		classDesc.setLocalFieldOrder(EventFields);
	}

	/**
	 * Handle non-standard cases for types in the Common API.
	 */
	public static void handleCommonAPISpecialCases() 
	{

		// Rename URL field for ApplicationContext, because the API is not JavaBeans compliant
		ClassDescriptor classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ApplicationContext.class);

		classDesc.setImplementationClass(com.nokia.oss.ossj.common.ri.ApplicationContextImpl.class);
		classDesc.getFieldDescriptor("URL").setFieldName("url");

		// Set field order
		classDesc.setLocalFieldOrder(IPBRIConfiguration.ApplicationContextFields);

		// Event property descriptors don't have setter methods in the i
		// interface, so we need to use the ones in the implementation class
		try 
		{
			classDesc = registry.getOrMakeClassDescriptor(
										EventPropertyDescriptor.class);
			FieldDescriptor fieldDesc = classDesc.getFieldDescriptor("eventType");
			Method setter = 
				com.nec.oss.ri.EventPropertyDescriptorImpl.class.getMethod(
					"setEventType", new Class[]{String.class});
			fieldDesc.setSetter(setter);
	 
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = 
				com.nec.oss.ri.EventPropertyDescriptorImpl.class.getMethod(
					"setPropertyNames", new Class[]{String[].class});
			fieldDesc.setSetter(setter);
	 
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = 
				com.nec.oss.ri.EventPropertyDescriptorImpl.class.getMethod(
						"setPropertyTypes", new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(
						com.nec.oss.ri.EventPropertyDescriptorImpl.class);
			classDesc.setLocalFieldOrder(
						IPBRIConfiguration.EventPropertyDescriptorFields);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Configure the XML binding for types in the IP Billing API.
	 */
	public static void configureIPBillingAPIBinding() 
	{
		ClassDescriptor classDesc;
		Date[]			tmpDateArray = new Date[0];
		ClassDescriptor dateArray = 
			registry.getOrMakeClassDescriptor(tmpDateArray.getClass());
		ClassDescriptor objCD = 
			registry.getOrMakeClassDescriptor(java.lang.Object.class);
		ArrayList		medCapChgEvtFieldsToSkip = new ArrayList();

		medCapChgEvtFieldsToSkip.add("mediationCapabilityChangeEvent");
		registry.setFieldsToSkip(javax.oss.ipb.event.MediationCapabilityChangeEventDecoder.class, 
								 medCapChgEvtFieldsToSkip);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.event.UsageDataAvailableEvent.class);

		classDesc.setLocalFieldOrder(UsageDataAvailableEventFields);
		classDesc.setImplementationClass(UsageDataAvailableEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.event.MediationCapabilityChangeEvent.class);

		classDesc.setLocalFieldOrder(MediationCapabilityChangeEventFields);
		classDesc.setImplementationClass(MediationCapabilityChangeEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.event.MediationCapabilityChangeEventDecoder.class);

		classDesc.setLocalFieldOrder(MediationCapabilityChangeEventDecoderFields);
		classDesc.setImplementationClass(MediationCapabilityChangeEventDecoderImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.type.UsageDataSchema.class);

		classDesc.setLocalFieldOrder(UsageDataSchemaFields);
		classDesc.setImplementationClass(UsageDataSchemaImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.type.UsageRecFilterValue.class);

		classDesc.setLocalFieldOrder(UsageRecFilterValueFields);
		classDesc.setImplementationClass(UsageRecFilterValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.type.UsageRecValue.class);

		classDesc.setLocalFieldOrder(UsageRecValueFields);
		classDesc.ignoreField("usageAttributeValue");
		classDesc.setImplementationClass(UsageRecValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.query.QueryByUsageRecFilter.class);

		classDesc.setLocalFieldOrder(QueryByUsageRecFilterFields);
		classDesc.setImplementationClass(QueryByUsageRecFilterImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.query.QueryProducersByMediationCapability.class);

		classDesc.setLocalFieldOrder(QueryProducersByMediationCapabilityFields);
		classDesc.setImplementationClass(QueryProducersByMediationCapabilityImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.transfer.TransferStatusPrimaryKey.class);

		classDesc.setLocalFieldOrder(TransferStatusPrimaryKeyFields);
		classDesc.setImplementationClass(TransferStatusPrimaryKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.transfer.TransferStatusValue.class);

		classDesc.setLocalFieldOrder(TransferStatusValueFields);
		classDesc.setImplementationClass(TransferStatusValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.transfer.TransferStatusKey.class);

		classDesc.setLocalFieldOrder(TransferStatusKeyFields);
		classDesc.setImplementationClass(TransferStatusKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.ProducerValue.class);

		classDesc.setLocalFieldOrder(ProducerValueFields);
		classDesc.ignoreField("activityKey");
		classDesc.setImplementationClass(ProducerValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.ProducerPrimaryKey.class);

		classDesc.setLocalFieldOrder(ProducerPrimaryKeyFields);
		classDesc.setImplementationClass(ProducerPrimaryKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.MediationCapability.class);

		classDesc.setLocalFieldOrder(MediationCapabilityFields);
		classDesc.setImplementationClass(MediationCapabilityImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.ProducerKeyResult.class);

		classDesc.ignoreField("producerKey");
		classDesc.setImplementationClass(ProducerKeyResultImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.ProducerKey.class);

		classDesc.setLocalFieldOrder(ProducerKeyFields);
		classDesc.ignoreField("producerPrimaryKey");
		classDesc.setImplementationClass(ProducerKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.SubscriptionFilter.class);

		classDesc.setLocalFieldOrder(SubscriptionFilterFields);
		classDesc.setImplementationClass(SubscriptionFilterImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.QueryActivityReportData.class);

		classDesc.setLocalFieldOrder(QueryActivityReportDataFields);
		classDesc.setImplementationClass(QueryActivityReportDataImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityCreationEvent.class);

		classDesc.setImplementationClass(ActivityCreationEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.DailyScheduleInfo.class);

		classDesc.setLocalFieldOrder(DailyScheduleInfoFields);
		classDesc.setImplementationClass(DailyScheduleInfoImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityValue.class);

		classDesc.setEnumType("controlState", registry, 
							  javax.oss.cfi.activity.ControlState.class);
		classDesc.setEnumType("executionStatus", registry, 
							  javax.oss.cfi.activity.ExecutionStatus.class);
		classDesc.setLocalFieldOrder(ActivityValueFields);
		classDesc.setImplementationClass(ActivityValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.QueryActivityValue.class);

		classDesc.setLocalFieldOrder(QueryActivityValueFields);
		classDesc.setImplementationClass(QueryActivityValueImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityKey.class);

		classDesc.setLocalFieldOrder(ActivityKeyFields);
		classDesc.setImplementationClass(ActivityKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityControlParams.class);

		classDesc.setLocalFieldOrder(ActivityControlParamsFields);
		classDesc.setImplementationClass(ActivityControlParamsImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityRemovalEvent.class);

		classDesc.setImplementationClass(ActivityRemovalEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.SubscriptionParams.class);

		classDesc.setLocalFieldOrder(SubscriptionParamsFields);
		classDesc.setImplementationClass(SubscriptionParamsImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityKeyResult.class);

		classDesc.setLocalFieldOrder(ActivityKeyResultFields);
		classDesc.setImplementationClass(ActivityKeyResultImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityReportAvailableEvent.class);

		classDesc.setLocalFieldOrder(ActivityReportAvailableEventFields);
		classDesc.setImplementationClass(ActivityReportAvailableEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.Schedule.class);

		classDesc.setLocalFieldOrder(ScheduleFields);
		classDesc.setImplementationClass(ScheduleImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ReportRecord.class);

		classDesc.setImplementationClass(ReportRecordImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.AttributeDescriptor.class);

		classDesc.setLocalFieldOrder(AttributeDescriptorFields);
		classDesc.setImplementationClass(AttributeDescriptorImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityPrimaryKey.class);

		classDesc.setLocalFieldOrder(ActivityPrimaryKeyFields);
		classDesc.setImplementationClass(ActivityPrimaryKeyImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivitySuspendEvent.class);

		classDesc.setImplementationClass(ActivitySuspendEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.RecordDescriptor.class);

		classDesc.setLocalFieldOrder(RecordDescriptorFields);
		classDesc.setImplementationClass(RecordDescriptorImpl.class);

		//javax.oss.cfi.activity.ReportInfo.class is a special case
		//see handleBillingMediationXMLToolingSpecialCases()

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityReportDataEvent.class);

		classDesc.setLocalFieldOrder(ActivityReportDataEventFields);
		classDesc.setImplementationClass(ActivityReportDataEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.WeeklyScheduleInfo.class);

		classDesc.setLocalFieldOrder(WeeklyScheduleInfoFields);
		classDesc.setImplementationClass(WeeklyScheduleInfoImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityResumeEvent.class);

		classDesc.setImplementationClass(ActivityResumeEventImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityExecParams.class);

		classDesc.setLocalFieldOrder(ActivityExecParamsFields);
		classDesc.setImplementationClass(ActivityExecParamsImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityReportParams.class);

		classDesc.setEnumType("reportMode", registry, 
							  javax.oss.cfi.activity.ReportMode.class);
		classDesc.setLocalFieldOrder(ActivityReportParamsFields);
		classDesc.setImplementationClass(ActivityReportParamsImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ReportFormat.class);

		classDesc.setLocalFieldOrder(ReportFormatFields);
		classDesc.setImplementationClass(ReportFormatImpl.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityEvent.class);

		classDesc.setLocalFieldOrder(ActivityEventFields);
		classDesc.setImplementationClass(ActivityEventImpl.class);

		// Event property descriptors don't have setter methods in the
		// interface, so we need to use the ones in the implementation class
		try 
		{
			ClassDescriptor evtDescClassDesc;
			FieldDescriptor fieldDesc;
			Method setter;
			classDesc = registry.getOrMakeClassDescriptor((UsageDataAvailableEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = UsageDataAvailableEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = UsageDataAvailableEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = UsageDataAvailableEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(UsageDataAvailableEventPropertyDescriptorImpl.class);
	
			classDesc = registry.getOrMakeClassDescriptor((MediationCapabilityChangeEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = MediationCapabilityChangeEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = MediationCapabilityChangeEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = MediationCapabilityChangeEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(MediationCapabilityChangeEventPropertyDescriptorImpl.class);
	
			classDesc = registry.getOrMakeClassDescriptor((ActivityCreationEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityCreationEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityCreationEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityCreationEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityCreationEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivityRemovalEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityRemovalEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityRemovalEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityRemovalEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityRemovalEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivitySuspendEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivitySuspendEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivitySuspendEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivitySuspendEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivitySuspendEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivityResumeEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityResumeEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityResumeEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityResumeEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityResumeEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivityReportAvailableEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityReportAvailableEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityReportAvailableEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityReportAvailableEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityReportAvailableEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivityReportDataEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityReportDataEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityReportDataEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityReportDataEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityReportDataEventPropertyDescriptorImpl.class);

			classDesc = registry.getOrMakeClassDescriptor((ActivityEventPropertyDescriptor.class));
			fieldDesc = classDesc.getFieldDescriptor("eventType");
			setter = ActivityEventPropertyDescriptorImpl.class.getMethod("setEventType",  new Class[]{String.class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyNames");
			setter = ActivityEventPropertyDescriptorImpl.class.getMethod("setPropertyNames",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			fieldDesc = classDesc.getFieldDescriptor("propertyTypes");
			setter = ActivityEventPropertyDescriptorImpl.class.getMethod("setPropertyTypes",  new Class[]{String[].class});
			fieldDesc.setSetter(setter);
			classDesc.setImplementationClass(ActivityEventPropertyDescriptorImpl.class);
		}
		catch (Exception ex) 
		{
			System.out.println("Error configuring event descriptors: " + ex);
			ex.printStackTrace();
		}
	}

	/**
	 * Handle non-standard cases for types in the IP Billing API.
	 */
	public static void handleIPBillingAPISpecialCases() 
	{
		try 
		{
			// Need to set the isArray method for the AttributeDescriptor class
			ClassDescriptor attrDescCD = 
				registry.getOrMakeClassDescriptor((AttributeDescriptor.class));
			ClassDescriptor fieldCD = 
				registry.getOrMakeClassDescriptor(boolean.class);
			FieldDescriptor fieldDesc = new FieldDescriptor("isArray", 
				fieldCD, attrDescCD);

			Method setter = AttributeDescriptorImpl.class.getMethod(
						"setIsArray", new Class[]{boolean.class});
			fieldDesc.setSetter(setter);
			Method getter = AttributeDescriptorImpl.class.getMethod(
						"isArray", new Class[]{});
			 fieldDesc.setGetter(getter);
			List localFieldDesc = attrDescCD.getLocalFieldDescriptors();
			localFieldDesc.add(fieldDesc);
			attrDescCD.setLocalFieldDescriptors(localFieldDesc);
		} 
		catch (Exception ex) 
		{
			System.out.println("Error configuring IPBilling API special cases:" 
							   + ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Handle XML Tooling1.4 upgrades issues in BillingMediation API.
	 */
	public static void handleBillingMediationXMLToolingSpecialCases() 
	{
		try 
		{
			ClassDescriptor classDesc = 
				registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ReportInfo.class);
			classDesc.setImplementationClass(ReportInfoImpl.class);
			classDesc.getFieldDescriptor("URL").setFieldName("uRL");
			classDesc.setLocalFieldOrder(ReportInfoFields);
		} 
		catch (Exception ex) 
		{
			System.out.println("Error configuring XMLTooling1.4 special cases:" 
							   + ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Configure the exceptions with the XML Tooling
	 */
	public static void configureExceptions() 
	{
		ClassDescriptor classDesc;

		classDesc = 
			registry.getOrMakeClassDescriptor(java.lang.Exception.class);

		if (classDesc.getFieldDescriptor("stackTrace") != null) 
		{
			classDesc.ignoreField("stackTrace");
		} 

		classDesc.getFieldDescriptor("message").setSerializationOnly(true);
		registry.getOrMakeClassDescriptor(javax.oss.IllegalArgumentException.class);
		registry.getOrMakeClassDescriptor(javax.oss.UnsupportedOperationException.class);
		registry.getOrMakeClassDescriptor(javax.ejb.CreateException.class);
		registry.getOrMakeClassDescriptor(javax.ejb.FinderException.class);
		registry.getOrMakeClassDescriptor(javax.ejb.DuplicateKeyException.class);
		registry.getOrMakeClassDescriptor(java.rmi.RemoteException.class);

		classDesc = 
			registry.getOrMakeClassDescriptor(javax.oss.ResyncRequiredException.class);

		classDesc.getFieldDescriptor("managedEntityKey").setSerializationOnly(true);

		// Add exceptions
		registry.getOrMakeClassDescriptor(javax.ejb.ObjectNotFoundException.class);
		registry.getOrMakeClassDescriptor(javax.oss.ipb.exception.IPBDataFormatException.class);
		//javax.oss.ipb.exception.IPBAPIException.class is a special case
		//see handleExceptionsXMLToolingSpecialCases()
		registry.getOrMakeClassDescriptor(javax.oss.cfi.activity.ActivityControlException.class);
	}

	/**
	 * Handle XML Tooling1.4 upgrades issues in BillingMediation API.
	 */
	public static void handleExceptionsXMLToolingSpecialCases() 
	{
		ClassDescriptor classDesc;
		classDesc = registry.getOrMakeClassDescriptor(javax.oss.ipb.exception.IPBAPIException.class);
		classDesc.setName("iPBAPIException");
	}
	
	/**
	 * This method configures the methods of the input JVTSession  
     * with the Registry.
     *
	 * @param jvtSessionInterface The JVTSession which should have its
     * methods registered
	 *
	 * @see
	 */
	public static void configureJVTSessionMethods(Class jvtSessionInterface) 
	{
		MethodDescriptor	tmpMD = null;

		try 
		{
			Method[]	methods = jvtSessionInterface.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) 
			{

				// @todo (maybe): filter out methods that are not remote
				tmpMD = 
					registry.getOrMakeMethodDescriptor(JVTProducerSession.class,
													   methods[i]);

				// Lookup the argNames in the map
				ArrayList   argNames = 
					(ArrayList) methodArgs.get(tmpMD.getName());

				if (argNames != null) 
				{
					FieldDescriptor[]   argDesc = 
						tmpMD.getParameterDescriptors();

					if (argDesc != null) 
					{
						int numArgs = argDesc.length;

						if (numArgs != argNames.size()) 
						{
							System.err.println("Arg mismatch for " 
											   + tmpMD.getName() + ": " 
											   + numArgs + "!=" 
											   + argNames.size());
						} 
						else 
						{
							for (int argIdx = 0; argIdx < numArgs; ++argIdx) 
							{
								argDesc[argIdx].setFieldName((String) argNames.get(argIdx));
							}
						}
					}
				}
			}
		} 
		catch (Exception e) {}
	}

	/**
	 * Configure the methods which are special cases and require
     * custom configuration
	 * 
	 */
	public static void configureSpecialMethods() 
	{
		MethodDescriptor		tmpMD = null;
		Method					tmpMethod = null;

		// Need to be register methods for the UsageRecValue iterator
		MethodDescriptor		mDesc = null;
		ClassDescriptor			producerKey = 
			registry.getOrMakeClassDescriptor(javax.oss.ipb.producer.ProducerKey.class);

		// Need to set up these arrays within the Registry, as they are only
		// return values and not method args
		ReportInfo[]			tmpRptInfoArray = new ReportInfo[0];
		ClassDescriptor			reportInfoArray = 
			registry.getOrMakeClassDescriptor(tmpRptInfoArray.getClass());
		UsageRecValue[]			tmpUsageRecValueArray = new UsageRecValue[0];
		ClassDescriptor			usageRecInfoArray = 
			registry.getOrMakeClassDescriptor(tmpUsageRecValueArray.getClass());
		ManagedEntityValue[]	tmpManagedEntValArray = 
			new ManagedEntityValue[0];
		ClassDescriptor			managedEntValArray = 
			registry.getOrMakeClassDescriptor(tmpManagedEntValArray.getClass());

		// Set up the 'next' methods for the iterators
		try 
		{
			mDesc = registry.getMethodDescriptor(JVTProducerSession.class, 
												 "getUsageDataInformation");

			Class   returnValue = mDesc.getReturnType();

			Method nextMethod = returnValue.getMethod("getNextReportInfos", new Class[]{int.class});
			mDesc.setIteratorGetNextMethod(nextMethod);
			mDesc = registry.getMethodDescriptor(JVTProducerSession.class, "queryUsageDataInformation");
			returnValue = mDesc.getReturnType();
			nextMethod = returnValue.getMethod("getNextReportInfos", new Class[]{int.class});
			mDesc.setIteratorGetNextMethod(nextMethod);
			mDesc = registry.getMethodDescriptor(JVTProducerSession.class, "queryUsageRecords");
			returnValue = mDesc.getReturnType();
			nextMethod = returnValue.getMethod("getNextUsageRecValues", new Class[]{int.class});
			mDesc.setIteratorGetNextMethod(nextMethod);
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR: Couldn't set up iterator classes");
		}

		// Need to set the return values that are enumerations (or arrays of
		// enumerations)
		ReportMode[]	tmpReportModeArray = new ReportMode[0];

		mDesc = 
			registry.getMethodDescriptor(JVTProducerSession.class, 
										 "getSupportedReportModesByActivity");

		mDesc.setReturnEnumType(registry.getOrMakeClassDescriptor(tmpReportModeArray.getClass()));

		mDesc = registry.getMethodDescriptor(JVTProducerSession.class, 
											 "getSupportedReportModes");

		mDesc.setReturnEnumType(registry.getOrMakeClassDescriptor(tmpReportModeArray.getClass()));
	}

	/**
	 * This method registers the argument names for each JVTSession method. 
     * These names will be used when the parameter names are set with the
     * Registry
     * 
	 */
	public static void configureMethodArgHashMap() 
	{
		ArrayList   paramList;

		paramList = new ArrayList();

		methodArgs.put("getSupportedOptionalOperations", paramList);

		paramList = new ArrayList();

		methodArgs.put("getManagedEntityTypes", paramList);

		paramList = new ArrayList();

		methodArgs.put("getQueryTypes", paramList);

		paramList = new ArrayList();

		methodArgs.put("getEventTypes", paramList);

		paramList = new ArrayList();

		paramList.add("type");
		methodArgs.put("makeQueryValue", paramList);

		paramList = new ArrayList();

		paramList.add("eventType");
		methodArgs.put("getEventDescriptor", paramList);

		paramList = new ArrayList();

		paramList.add("valueType");
		methodArgs.put("makeManagedEntityValue", paramList);

		paramList = new ArrayList();

		paramList.add("query");
		paramList.add("attrNames");
		methodArgs.put("queryManagedEntities", paramList);

		paramList = new ArrayList();

		paramList.add("value");
		methodArgs.put("getSupportedGranularities", paramList);

		paramList = new ArrayList();

		methodArgs.put("getSupportedReportModes", paramList);

		paramList = new ArrayList();

		paramList.add("activityKey");
		methodArgs.put("getSupportedReportModesByActivity", paramList);

		paramList = new ArrayList();

		methodArgs.put("getSupportedReportFormats", paramList);

		paramList = new ArrayList();

		paramList.add("activityKey");
		methodArgs.put("getSupportedReportFormatsByActivity", paramList);

		paramList = new ArrayList();

		methodArgs.put("getSupportedReportProtocolNames", paramList);

		paramList = new ArrayList();

		paramList.add("activityKey");
		methodArgs.put("getSupportedReportProtocolNamesByActivity", 
					   paramList);

		paramList = new ArrayList();

		methodArgs.put("getSupportedReportProtocolVersions", paramList);

		paramList = new ArrayList();

		paramList.add("activityKey");
		methodArgs.put("getSupportedReportProtocolVersionsByActivity", 
					   paramList);

		paramList = new ArrayList();

		paramList.add("keyValue");
		methodArgs.put("activateByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keyValues");
		methodArgs.put("tryActivateByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("keyValue");
		methodArgs.put("deactivateByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keyValues");
		methodArgs.put("tryDeactivateByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("keyValue");
		methodArgs.put("suspendByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keyValues");
		methodArgs.put("trySuspendByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("keyValue");
		methodArgs.put("resumeByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keyValues");
		methodArgs.put("tryResumeByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("keyValue");
		methodArgs.put("oneShotByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keyValues");
		methodArgs.put("tryOneShotByKeys", paramList);

		paramList = new ArrayList();

		methodArgs.put("getProducerValueTypes", paramList);

		paramList = new ArrayList();

		paramList.add("producerValueType");
		methodArgs.put("makeProducerValue", paramList);

		paramList = new ArrayList();

		methodArgs.put("getProducerKeyTypes", paramList);

		paramList = new ArrayList();

		paramList.add("producerKeyType");
		methodArgs.put("makeProducerKey", paramList);

		paramList = new ArrayList();

		methodArgs.put("getUsageRecFilterTypes", paramList);

		paramList = new ArrayList();

		paramList.add("usageRecFilterValueType");
		methodArgs.put("makeUsageRecFilterValue", paramList);

		paramList = new ArrayList();

		methodArgs.put("getMediationCapabilityTypes", paramList);

		paramList = new ArrayList();

		paramList.add("mediationCapabilityType");
		methodArgs.put("makeMediationCapability", paramList);

		paramList = new ArrayList();

		paramList.add("value");
		methodArgs.put("createProducerByValue", paramList);

		paramList = new ArrayList();

		paramList.add("values");
		methodArgs.put("tryCreateProducersByValues", paramList);

		paramList = new ArrayList();

		paramList.add("key");
		methodArgs.put("removeProducerByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keys");
		methodArgs.put("tryRemoveProducersByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("template");
		paramList.add("failuresOnly");
		methodArgs.put("tryRemoveProducersByTemplate", paramList);

		paramList = new ArrayList();

		paramList.add("key");
		paramList.add("attrNames");
		methodArgs.put("getProducerByKey", paramList);

		paramList = new ArrayList();

		paramList.add("keys");
		paramList.add("attrNames");
		methodArgs.put("getProducersByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("template");
		paramList.add("attrNames");
		methodArgs.put("getProducersByTemplate", paramList);

		paramList = new ArrayList();

		paramList.add("value");
		paramList.add("resyncRequired");
		methodArgs.put("setProducerByValue", paramList);

		paramList = new ArrayList();

		paramList.add("keys");
		paramList.add("value");
		methodArgs.put("trySetProducersByKeys", paramList);

		paramList = new ArrayList();

		paramList.add("template");
		paramList.add("value");
		paramList.add("failuresOnly");
		methodArgs.put("trySetProducersByTemplate", paramList);

		paramList = new ArrayList();

		paramList.add("query");
		paramList.add("attrNames");
		methodArgs.put("queryProducers", paramList);

		paramList = new ArrayList();

		paramList.add("value");
		methodArgs.put("getSupportedMediationCapabilities", paramList);

		paramList = new ArrayList();

		paramList.add("pKey");
		paramList.add("desiredCapability");
		methodArgs.put("matchMediationCapability", paramList);

		paramList = new ArrayList();

		paramList.add("value");
		methodArgs.put("supportsTransferMonitoring", paramList);

		paramList = new ArrayList();

		paramList.add("query");
		paramList.add("attrNames");
		methodArgs.put("queryUsageRecords", paramList);

		paramList = new ArrayList();

		paramList.add("query");
		paramList.add("attrNames");
		methodArgs.put("queryTransferStatusValues", paramList);

		paramList = new ArrayList();

		paramList.add("usageDataAvailableEvent");
		paramList.add("filterValue");
		paramList.add("createTransferStatus");
		paramList.add("attrNames");
		paramList.add("beginDate");
		paramList.add("endDate");
		methodArgs.put("getUsageRecordsByIterator", paramList);

		paramList = new ArrayList();

		paramList.add("producerKey");
		paramList.add("filterValue");
		paramList.add("beginDate");
		paramList.add("endDate");
		methodArgs.put("getUsageDataInformation", paramList);

		paramList = new ArrayList();

		paramList.add("queryValue");
		paramList.add("attributeNames");
		methodArgs.put("queryUsageDataInformation", paramList);
	}

	/**
	 * Configure the XML binding for the IP Billing API classes and JVTSession
     * methods
	 */
	public static IPBRIRegistry configureXmlBinding() 
	{
		registry = new IPBRIRegistry();

		// -----------------------------------------
		// Use the property file in the config directory
		// -----------------------------------------
		XmlProperties.setPropertyFilePath("xml.properties");
		configureCommonAPIBinding();
		handleCommonAPISpecialCases();
		configureIPBillingAPIBinding();
		handleIPBillingAPISpecialCases();
		handleBillingMediationXMLToolingSpecialCases();
		configureExceptions();
		handleExceptionsXMLToolingSpecialCases();
		configureMethodArgHashMap();
		configureJVTSessionMethods(JVTProducerSession.class);
		configureJVTSessionMethods(JVTSession.class);
		configureJVTSessionMethods(ActivityCapability.class);
		configureJVTSessionMethods(ActivityController.class);
		configureSpecialMethods();

		return IPBRIConfiguration.registry;
	}

	/**
	 * Returns the registry.
	 * @return Registry
	 */
	public static Registry getRegistry() 
	{
		if (registry == null) 
		{
			registry = configureXmlBinding();
		} 

		return registry;
	}

	// ---------------------------
	// Common types
	// ---------------------------
	public static String[]  ApplicationContextFields = 
	{
		"factoryClass", "url", "systemProperties"
	};
	public static String[]  ManagedEntityKeyFields = 
	{
		"applicationContext", "applicationDN", "type"
	};
	public static String[]  ManagedEntityKeyResultFields = 
	{
		"success", "exception"
	};
	public static String[]  EventFields = 
	{
		"applicationDN", "eventTime"
	};
	public static String[]  EventPropertyDescriptorFields = 
	{
		"eventType", "propertyNames", "propertyTypes"
	};

	// IPB data
	public static String[]  UsageDataAvailableEventFields = 
	{
		"availableSince"
	};
	public static String[]  MediationCapabilityChangeEventFields = 
	{
		"description", "newMediationCapability", "oldMediationCapability", 
		"producerKey"
	};
	public static String[]  MediationCapabilityChangeEventDecoderFields = 
	{
		"mediationCapabilityChangeEvent"
	};
	public static String[]  UsageDataSchemaFields = 
	{
		"name", "baseType", "schemaDefinition"
	};
	public static String[]  UsageRecFilterValueFields = 
	{
		"usageDataCategory", "usageDataFormat", "usageDataFormatVersion", 
		"usageDataEncoding", "usageDataTimestamp", "collectionTimePeriod"
	};
	public static String[]  UsageRecValueFields = 
	{
		"objectValue", "booleanValue", "byteValue", "shortValue", 
		"charValue", "intValue", "longValue", "floatValue", "doubleValue", 
		"stringValue", "timestampValue", "booleanArrayValue", 
		"byteArrayValue", "shortArrayValue", "charArrayValue", 
		"intArrayValue", "longArrayValue", "floatArrayValue", 
		"doubleArrayValue", "stringArrayValue", "timestampArrayValue", 
		"objectArrayValue"
	};
	public static String[]  QueryByUsageRecFilterFields = 
	{
		"usageRecFilterValue", "producerKey"
	};
	public static String[]  QueryProducersByMediationCapabilityFields = 
	{
		"mediationCapabilityValues", "joinOperand"
	};
	public static String[]  TransferStatusPrimaryKeyFields = 
	{
		"sessionId", "producerKey"
	};
	public static String[]  TransferStatusValueFields = 
	{
		"transferStatusKey", "transferStatusCode", "transferFailureReason", 
		"reportingParams"
	};
	public static String[]  TransferStatusKeyFields = 
	{
		"transferStatusPrimaryKey"
	};
	public static String[]  ProducerValueFields = 
	{
		"producerKey", "mediationCapabilityValues", "transferStatusSupported"
	};
	public static String[]  ProducerPrimaryKeyFields = 
	{
		"producerType"
	};
	public static String[]  MediationCapabilityFields = 
	{
		"name", "collectionCategories", "collectionFormat", 
		"collectionFormatVersion", "collectionEncoding", "collectionSchema", 
		"outputFormats", "outputFormatVersions", "outputEncodings", 
		"outputSchemas", "changeEventDecoder"
	};
	public static String[]  ProducerKeyResultFields = 
	{
		"producerKey"
	};
	public static String[]  ProducerKeyFields = 
	{
		"producerPrimaryKey"
	};
	public static String[]  SubscriptionFilterFields = 
	{
		"dataType", "dataSource"
	};
	public static String[]  QueryActivityReportDataFields = 
	{
		"subscriptionFilter", "beginDate", "endDate"
	};
	public static String[]  DailyScheduleInfoFields = 
	{
		"startTimes", "stopTimes"
	};
	public static String[]  ActivityValueFields = 
	{
		"activityKey", "activityName", "controlState", "executionStatus", 
		"schedule", "activityControlParams", "activityExecParams", 
		"activityReportParams", "subscriptionParams"
	};
	public static String[]  QueryActivityValueFields = 
	{
		"activityName", "granularityPeriod", "controlState", 
		"executionStatus", "activityType", "oneShotCapability", 
		"scheduleCategory"
	};
	public static String[]  ActivityKeyFields = 
	{
		"activityPrimaryKey"
	};
	public static String[]  ActivityControlParamsFields = 
	{
		"supportsOneShot", "overlapAllowed", "failureToleranceLimit"
	};
	public static String[]  SubscriptionParamsFields = 
	{
		"subscriber", "subscriptionName", "subscriptionFilter"
	};
	public static String[]  ActivityKeyResultFields = 
	{
		"activityKey"
	};
	public static String[]  ActivityReportAvailableEventFields = 
	{
		"reportInformation"
	};
	public static String[]  ScheduleFields = 
	{
		"timeZone", "startTime", "stopTime", "dailyScheduleInfo", 
		"weeklyScheduleInfo"
	};
	public static String[]  AttributeDescriptorFields = 
	{
		"name", "type", "typeName", "isArray"
	};
	public static String[]  ActivityPrimaryKeyFields = 
	{
		"activityId"
	};
	public static String[]  RecordDescriptorFields = 
	{
		"reportRecordType", "fieldsInformation"
	};
	public static String[]  ReportInfoFields = 
	{
		"uRL", "reportFormat", "expirationDate", "additionalInfo"
	};
	public static String[]  ActivityReportDataEventFields = 
	{
		"reportData", "reportFormat"
	};
	public static String[]  WeeklyScheduleInfoFields = 
	{
		"mondayActive", "tuesdayActive", "wednesdayActive", "thursdayActive", 
		"fridayActive", "saturdayActive", "sundayActive"
	};
	public static String[]  ActivityExecParamsFields = 
	{
		"maxIterations", "granularityPeriod", "delayInterval"
	};
	public static String[]  ActivityReportParamsFields = 
	{
		"reportFormat", "reportPeriod", "reportMode", "eventDestinationType", 
		"eventDestinationName", "reportProtocolName", "reportProtocolVersion"
	};
	public static String[]  ReportFormatFields = 
	{
		"type", "version", "owner", "technology", "specification"
	};
	public static String[]  ActivityEventFields = 
	{
		"activityValue"
	};
}
