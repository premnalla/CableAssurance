package com.nec.oss.ipb.producer.ri.xml;

import com.nec.oss.cfi.activity.ri.ReportInfoStatefulIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.rmi.RemoteException;
import java.util.*;
import java.lang.reflect.*;
import javax.ejb.RemoveException;
import javax.oss.*;
import javax.oss.cfi.activity.ReportInfoIterator;
import javax.oss.cfi.activity.ReportIterator;
import org.jdom.Namespace;
import com.ilog.ossj.xml.mdb.XmlMessageDrivenBean;
import com.ilog.ossj.xml.mdb.JVTSessionXmlDelegate;
import com.ilog.ossj.xml.binding.*;
import com.ilog.ossj.xml.util.Utilities;
import com.ilog.ossj.xml.util.JDOMUtils;
import javax.oss.ipb.type.UsageRecValue;
import javax.oss.ipb.type.UsageRecValueIterator;
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.cfi.activity.AttributeDescriptor;

/**
 * This class extends the XML tooling class JVTSessionXmlDelegate to provide
 * specific handling for the IP Billing API. The specific functionality needed
 * is as follows:
 * <P>
 * - Since the UsageRecValueIterator needs to include the RecordDescriptor in
 * the response message, the formatting of the XML response for that data
 * type needs to add the RecordDescriptor in the response message.
 * <P>
 * - Since the UsageRecValue represents the attributes as name/value pairs, the
 * response message needs to use the NameValueFieldDescriptor to format the
 * XML response.
 */
public class IPBJVTSessionXmlDelegate extends JVTSessionXmlDelegate 
{
	protected static Log	log = 
		LogFactory.getLog(IPBJVTSessionXmlDelegate.class);

	/**
	 * Constructor - passes the input arg to the parent constructor
	 * @param mdb MessageDrivenBean for this implementation
	 */
	public IPBJVTSessionXmlDelegate(XmlMessageDrivenBean mdb) 
	{
		super(mdb);
	}

	/**
	 * Process the XML request message from the MessageDrivenBean. Invoke the
	 * appropriate JVTProducerSession method and send the result as an XML
	 * response message
	 * @requestElement XML request received by the MessageDrivenBean
	 * @requestSenderId JMS Sender ID
	 * @replyTo  JMS destination to send the response XML message
	 */
	public void processXmlRequest(org.jdom.Element requestElement, 
								  String requestSenderId, 
								  javax.jms.Destination replyTo) 
	{

		// Determine the name of the XML request
		String  requestName = getRequestNameFromXml(requestElement);

		log.trace("Request name: " + requestName);

		// Retrieve the associated Java method
		MethodDescriptor	methodDesc = 
			getRequestMethodDescriptor(getJvtSessionType(), requestName);
		Method				requestMethod = methodDesc.getMethod();

		log.trace("Found corresponding method on JVTSession");

		// De-serialize the method parameter values from XML
		Vector  paramValues = getRequestParametersFromXml(methodDesc, 
			requestElement);
		boolean bIteratorRequest = methodDesc.isIteratorRequest();
		int		howMany = -1;

		if (bIteratorRequest) 
		{
			howMany = ((Integer) paramValues.get(0)).intValue();

			paramValues.remove(0);
		}

		Object[]	parameters = new Object[paramValues.size()];

		paramValues.toArray(parameters);
		log.trace("Parsed " + parameters.length + " parameters");

		// Invoke the request and send the response
		try 
		{

			// Invoke the java method
			Object  result = requestMethod.invoke(mdb.getJvtSession(), 
												  parameters);

			// Return the resulting XML response element
			if (bIteratorRequest) 
			{
				sendIteratorResponse(methodDesc, result, howMany, 
									 requestSenderId, replyTo);
			} 
			else 
			{
				org.jdom.Element	responseElement = 
					createResponseElement(methodDesc, result);

				log.trace("Created response element");
				mdb.sendResponseMessage(responseElement, requestSenderId, 
										replyTo);
			}
		} 
		catch (Exception exception) 
		{
			// In case of exception, return the corresponding XML exception 
  			// element
			try 
			{
				if (InvocationTargetException.class.isInstance(exception)) 
				{
					log.trace("Extracting root cause exception from invocation target exception");

					exception = 
					(Exception) ((InvocationTargetException) exception).getTargetException();

					log.warn("Method invocation threw exception for request " 
							 + requestName, exception);
				} 
				else 
				{
					log.warn("XML processing or MDB threw exception for request " 
							 + requestName, exception);

					exception = new RemoteException("Internal error: " 
													+ exception.getMessage(), 
													exception);
				}

				org.jdom.Element	exceptionElement = 
					createExceptionElement(methodDesc, exception);

				log.trace("Returning exception response");
				mdb.sendResponseMessage(exceptionElement, requestSenderId, 
										replyTo);
			} 
			catch (Exception e) 
			{
				log.error("Caught exception while creating exception element. Reply lost", 
						  e);
			}
		}
	}

	/**
	 * Utility method to set the namespaces for the XML response message
	 * @param rootElement XML Response Element
	 */
	protected void setAdditionalNamespaces(org.jdom.Element rootElement) 
	{
		rootElement.addNamespaceDeclaration(Namespace.getNamespace("http://www.w3.org/2001/XMLSchema"));
		rootElement.addNamespaceDeclaration(Utilities.getXsiNamespace());

		Namespace   namespace = rootElement.getNamespace();
		Namespace[] registeredNamespaces = 
			Utilities.getAllRegisteredNamespaces();

		for (int i = 0; i < registeredNamespaces.length; i++) 
		{
			if (!registeredNamespaces[i].equals(namespace)) 
			{
				rootElement.addNamespaceDeclaration(registeredNamespaces[i]);
			} 
		}
	}

	/**
	 * Send the iterator respone to the client, handling the special case for
     * the UsageRecValueIterator
	 * 
	 * @param methodDesc MethodDescriptor for the request
	 * @param result Return value from the request
	 * @param howMany Number of values requested per response by the client
	 * @param requestSenderId JMS Sender ID of the client
	 * @param replyTo JMS Destination for the client
	 *
	 * @exception MarshalException Thrown if there is an error marshalling to XML
	 * @exception RemoveException Thrown if there is a removal error
	 * @exception javax.jms.JMSException Thrown if there is a JMS error
	 * @exception RemoteException  Thrown if there is a system error
	 *
	 */
	public void sendIteratorResponse(MethodDescriptor methodDesc, 
									 Object result, int howMany, 
									 String requestSenderId, 
									 javax.jms.Destination replyTo) 
		throws RemoteException, RemoveException, MarshalException, 
				 javax.jms.JMSException 
	{
		org.jdom.Element	responseElement = null;
		org.jdom.Element	previousResponseElement = null;
		int					sequenceNum = 0;

		do
		{
			previousResponseElement = responseElement;
			responseElement = createIteratorResponseElement(methodDesc, 
				result, howMany, sequenceNum);

			boolean emptyResults = false;

			if (responseElement != null) 
			{
				List		children = responseElement.getChildren();
				int			numOfChildren = children.size();
				Iterator	it = children.iterator();

				for (int i = 0; it.hasNext(); i++) 
				{
					org.jdom.Element	childElement = 
						(org.jdom.Element) it.next();
				}

				org.jdom.Element	resultsChild = 
					(org.jdom.Element) children.get(2);
				List				resultsChildren = 
					resultsChild.getChildren();
				int					numOfChildrenR = resultsChildren.size();

				if (numOfChildrenR == 0) 
				{
					emptyResults = true;
				}

				Iterator	itR = resultsChildren.iterator();

				for (int i = 0; itR.hasNext(); i++) 
				{
					org.jdom.Element	childElementR = 
						(org.jdom.Element) itR.next();
				}
			}

			// If there is no more data, then set the flag on the previous element
			if ((responseElement == null) || (emptyResults)) 
			{
				if (previousResponseElement != null) 
				{
					org.jdom.Element	eorElement = 
						previousResponseElement.getChild("endOfReply", 
														 Utilities.getCommonNamespace());

					if (eorElement != null) 
					{
						eorElement.setText("true");
						log.trace("End of reply flag set to true for iterator response element #" 
								  + (sequenceNum - 1));

						responseElement = null;
					} 
					else 
					{
						log.warn("Previous iterator response element did not have endOfReply field for iterator request ");
					}
				} 
				else 
				{

					// This happens when there are no values at all to return
					previousResponseElement = responseElement;
					responseElement = null;

					log.trace("Created empty iterator response element");
				}
			} 
			else 
			{
				log.trace("Created iterator response element #" 
						  + sequenceNum);
			}
			if (previousResponseElement != null) 
			{
				mdb.sendResponseMessage(previousResponseElement, 
										requestSenderId, replyTo);
			}

			sequenceNum++;
		} 
		while (responseElement != null);

		// Now that all of the responses have been processed, remove the
		// iterator
		Class   resultType = result.getClass();

		log.trace("Empty results array, discarding response and releasing iterator");

		// Release the iterator
		if (ManagedEntityValueIterator.class.isAssignableFrom(resultType)) 
		{
			((ManagedEntityValueIterator) result).remove();
		} 
		else if (ManagedEntityKeyResultIterator.class.isAssignableFrom(resultType))
		{
			((ManagedEntityKeyResultIterator) result).remove();
		} 
		else if (ReportInfoIterator.class.isAssignableFrom(resultType)) 
		{
			((ReportInfoIterator) result).remove();
		} 
		else if (ReportIterator.class.isAssignableFrom(resultType)) 
		{
			((ReportIterator) result).remove();
		} 
		else 
		{
			log.warn("Unknown iterator type - not removed!");
		}
	}

	/**
	 * Create an XML response element representing the given iterator response.
	 * Note that special handling for a UsageRecValueIterator response is
	 * performed here
	 * 
	 * @param requestMethodDesc MethodDescriptor for the request
	 * @param result Return value from the request
	 * @param howMany Number of values requested per response by the client
	 * @param sequenceNum Sequence of this iterator response in the total
	 *
	 * @exception MarshalException Thrown if there is an error marshalling to XML
	 * @exception RemoveException Thrown if there is a removal error
	 * @exception RemoteException  Thrown if there is a system error
	 */
	public org.jdom.Element createIteratorResponseElement(
		MethodDescriptor requestMethodDesc, 
		Object result, int howMany, int sequenceNum) 
		throws RemoteException, RemoveException, MarshalException
	{
		ClassDescriptor usageRecClassDesc = null;
		ClassDescriptor bkupUsageRecClassDesc = null;
		Class			resultType = result.getClass();

		log.trace("Retrieving next batch of results from iterator of type " 
				  + resultType.getName());

		Object[] values = null;
		Method	getNextMethod = requestMethodDesc.getIteratorGetNextMethod();

		if (getNextMethod != null) 
		{
			log.trace("Asking for " + howMany + " values from iterator");

			try 
			{
				values = (Object[])getNextMethod.invoke(result, new Object[]{new Integer(howMany)});
				log.trace("Received " + values.length + " values of type " 
						  + values.getClass().getName());
			} 
			catch (Exception e) 
			{
				log.warn("Caught exception while retrieving values from iterator", 
						 e);

				values = null;
			}
		} 
		else 
		{
			log.warn("No getNext method defined for iterator");
		}

		// Empty array mean no more results
		if (values == null) 
		{
			return null;
		}

		log.trace("Generating response #" + sequenceNum);

		ClassDescriptor valuesClassDesc = 
			this.mdb.getRegistry().getClassDescriptor(getNextMethod.getReturnType());

		log.trace("ClassDescriptor for results array is: " 
				  + valuesClassDesc.getName());

		org.jdom.Element	additionalInfoElement = null;

		if (result instanceof UsageRecValueIterator) 
		{

			// Get the RecordDescriptor
			UsageRecValueIterator   usageRecValIter = 
				(UsageRecValueIterator) result;
			RecordDescriptor		recDesc = 
				usageRecValIter.getRecordDescriptor();

			// Get the AttributeDescriptors
			AttributeDescriptor[]   attrDesc = recDesc.getFieldsInformation();
			int						numAttr = attrDesc.length;
			ArrayList				nameValFieldDescList = new ArrayList();

			usageRecClassDesc = 
				this.mdb.getRegistry().getClassDescriptor(UsageRecValue.class);
			bkupUsageRecClassDesc = copyClassDescriptor(usageRecClassDesc);

			ClassLoader classLoader = this.getClass().getClassLoader();

			// For each attribute name, create a NameValueFieldDescriptor
			for (int attrIdx = 0; attrIdx < attrDesc.length; ++attrIdx) 
			{
				String  attrName = attrDesc[attrIdx].getName();
				int		attrType = attrDesc[attrIdx].getType();

				// For each attribute, get the return type
				Class   returnType = null;

				switch (attrType) 
				{

					case AttributeDescriptor.INTEGER: 
						returnType = int.class;

						break;

					case AttributeDescriptor.STRING: 
						returnType = String.class;

						break;

					case AttributeDescriptor.BOOLEAN: 
						returnType = boolean.class;

						break;

					case AttributeDescriptor.BYTE: 
						returnType = byte.class;

						break;

					case AttributeDescriptor.LONG: 
						returnType = long.class;

						break;

					case AttributeDescriptor.SHORT: 
						returnType = short.class;

						break;

					case AttributeDescriptor.DATE: 
						returnType = Date.class;

						break;

					case AttributeDescriptor.CHAR: 
						returnType = char.class;

						break;

					case AttributeDescriptor.FLOAT: 
						returnType = float.class;

						break;

					case AttributeDescriptor.DOUBLE: 
						returnType = double.class;

						break;

					case AttributeDescriptor.OBJECT: 
						String  typeClassName = 
							attrDesc[attrIdx].getTypeName();

						try 
						{
							returnType = classLoader.loadClass(typeClassName);
						} 
						catch (Exception ex) 
						{

							// System.out.println("Error loading class " + typeClassName + ":" + ex);
							returnType = String.class;
						}

						break;
				}

				if (attrDesc[attrIdx].isArray()) 
				{
					if (returnType != null) 
					{
						Object  arrayReturnType = 
							Array.newInstance(returnType, 1);

						returnType = arrayReturnType.getClass();
					}
				}

				// Now, step through each Field Descriptor to see which one
				// has a type that matches
				Iterator					iter = 
					usageRecClassDesc.getFieldDescriptors().iterator();
				NameValueFieldDescriptor	nameValFieldDesc = null;

				while (iter.hasNext()) 
				{
					FieldDescriptor desc = (FieldDescriptor) iter.next();

					if (desc.getFieldType().equals(returnType)) 
					{
						nameValFieldDesc = new NameValueFieldDescriptor(desc.getFieldName(), 
							desc.getClassDescriptor(), 
							desc.getDeclaringClassDescriptor());

						nameValFieldDesc.setField(desc.getField());
						nameValFieldDesc.setGetter(desc.getGetter());
						nameValFieldDesc.setSetter(desc.getSetter());
						nameValFieldDesc.setEnumType(desc.getEnumType());
						nameValFieldDesc.setExtensibleEnum(desc.isExtensibleEnum());
						nameValFieldDesc.setDirectAccess(desc.isDirectAccess());
						nameValFieldDesc.setIgnorable(desc.isIgnorable());
						nameValFieldDesc.setSerializationOnly(desc.isSerializationOnly());
						nameValFieldDesc.setValueName(attrDesc[attrIdx].getName());

						break;
					}
				}

				nameValFieldDescList.add(nameValFieldDesc);
			}

			usageRecClassDesc.setLocalFieldDescriptors(nameValFieldDescList);

			ClassDescriptor recDescClassDesc = 
				this.mdb.getRegistry().getClassDescriptor(recDesc.getClass());

			XmlHandler		recDescXmlHdlr = 
				new XmlHandler(this.mdb.getRegistry(), recDescClassDesc);

			additionalInfoElement = recDescXmlHdlr.marshal(recDesc, 
				"recordDescriptor", requestMethodDesc.getNamespace());
		}

		XmlHandler			xmlHandler = 
			new XmlHandler(this.mdb.getRegistry(), valuesClassDesc);
		org.jdom.Element	resultElement = xmlHandler.marshal(values, 
			"results", requestMethodDesc.getNamespace());

		log.trace("Created results element with " 
				  + resultElement.getChildren().size() + " children");

		// endOfReply is false by default, we don't know whether its the last
		// element until we get an empty array back from the iterator
		org.jdom.Element	returnElement = 
			doCreateIteratorResponseElement(requestMethodDesc, sequenceNum, 
											false, resultElement);

		if (result instanceof UsageRecValueIterator) 
		{
			usageRecClassDesc.setLocalFieldDescriptors(bkupUsageRecClassDesc.getLocalFieldDescriptors());
		}
		if (additionalInfoElement != null) 
		{
			returnElement.addContent(additionalInfoElement);
		}

		setAdditionalNamespaces(returnElement);

		return (returnElement);
	}

	/**
	 * Utility method to copy a ClassDescriptor 
     * 
	 * @param source Source ClassDescriptor to copy
	 *
	 * @return ClassDescriptor created and copied
	 */
	ClassDescriptor copyClassDescriptor(ClassDescriptor source) 
	{
		ClassDescriptor retVal = new ClassDescriptor(source.getJavaType());

		retVal.setImplementationClass(source.getImplementationClass());
		retVal.setParentDescriptor(source.getParentDescriptor());
		retVal.setUsingSubInterface(source.isUsingSubInterface());
		retVal.setSerializationOnly(source.isSerializationOnly());
		retVal.setLocalFieldDescriptors(source.getLocalFieldDescriptors());

		return retVal;
	}
}
