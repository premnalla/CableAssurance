/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */
package com.nokia.oss.ossj.sa.ri.order;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.ejb.FinderException;

import javax.naming.NamingException;

import java.util.*;
import java.net.InetAddress;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicPublisher;
import javax.jms.Topic;
import javax.jms.ObjectMessage;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.directory.DirContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.directory.SearchControls;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.service.*;
import com.nokia.oss.ossj.common.ri.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class JVTActivationBean implements javax.ejb.SessionBean {

	private static final String[] MY_ORDER_TYPES =
		{
			CancelOrderValue.class.getName(),
			CreateOrderValue.class.getName(),
			ModifyOrderValue.class.getName()};

	// array has to be sorted
	private static final String[] MY_SUPPORTED_OPERATIONS =
		{
			JVTActivationSessionOptionalOps.GET_ORDERS_BY_TEMPLATES,
			JVTActivationSessionOptionalOps.REMOVE_ORDER_BY_KEY };

	private Map mySupportedServices;
	private String[] mySupportedServicesStr;

	private static final String[] MY_QUERY_TYPES =
		{
			com.nokia.oss.ossj.sa.ri.order.QueryOrdersByDateInterval.class.getName(),
			com.nokia.oss.ossj.sa.ri.order.QueryUrgentOrders.class.getName(),
			javax.oss.order.QueryAllOrdersValue.class.getName()};

	private static final String[] MY_EVENT_TYPES =
		{
			javax.oss.order.OrderCreateEvent.class.getName(),
			javax.oss.order.OrderRemoveEvent.class.getName(),
			javax.oss.order.OrderAttributeValueChangeEvent.class.getName(),
			javax.oss.order.OrderStateChangeEvent.class.getName()};

	private static final Map ORDER_EVENT_DESCRIPTOR_MAP;

	//VP make the propnames static final
	static {
		ORDER_EVENT_DESCRIPTOR_MAP = new java.util.HashMap();
	}

	//VP static {
	//VP	String[] propnames =
		private static final String[] propnames =
			{
				javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_NAME,
				javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_NAME,
				javax.oss.order.OrderEventPropertyDescriptor.OSS_ORDER_TYPE_PROP_NAME,
				javax.oss.order.OrderEventPropertyDescriptor.OSS_API_CLIENT_ID_PROP_NAME };

		// VP String[] proptypes =
		private static final String[] proptypes =
			{
				javax.oss.EventPropertyDescriptor.OSS_EVENT_TYPE_PROP_TYPE,
				javax.oss.EventPropertyDescriptor.OSS_APPLICATION_DN_PROP_TYPE,
				javax.oss.order.OrderEventPropertyDescriptor.OSS_ORDER_TYPE_PROP_TYPE,
				javax.oss.order.OrderEventPropertyDescriptor.OSS_API_CLIENT_ID_PROP_TYPE };

		//VP move the init of the map to ejbCreate method 

	//VP }

	private BeaTrace myLog;
	private String myApplicationDN;
	private JmsSender myJmsSender;
	private OrderProcessor myOrderProcessor;
	private SessionContext mySessionContext;
	private Context myNamingContext;
	private QueryHelper aQueryHelper;
	private ApplicationContext myApplicationContext;
	private UniqueKeyGenerator myUniqueKeyGenerator;
	private boolean isLoggingEnabled = true;

	/** Creates new GprsOrderManager */
	public JVTActivationBean() {
	}

	/**
	 * Returns a new order value for usage in the client.
	 * <p>
	 * This method does not create a (back-end) order object,
	 * just a order value object.
	 * It is the factory method for the value object that this session bean
	 * can handle.
	 * <p>
	 * If typeName is
	 * <code>"</code>{@link javax.oss.order.CreateOrderValue}<code>"</code>
	 * or a derived interfaces, then
	 * all attributes of the order are populated, including the attribute
	 * SERVICES.
	 * The values of these populated attributes are implementation-dependent.
	 * An implementation must provide valid default values to create and
	 * start the order. <p>
	 * Thus the following code should not raise any non-technical exception :
	 * <pre>
	 *	OrderValue order = makeOrderValue(CreateOrderValue.class.getName());
	 *	OrderKey ok = createOrderByValue(order);
	 *	startOrderByKey(ok);
	 * </pre>
	 *
	 * <p>
	 * For all other order types, the implementation may decide about which attributes
	 * are populated and what are their values.
	 * <p>
	 * This method is equivalent to {@link javax.oss.JVTSession#makeManagedEntityValue}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>The type of the returned object is as requested:<br>
	 *	  <code>result instanceof Class.forName(typeName)</code>
	 * </li>
	 * <li>The key is not populated, done by {@link #createOrderByValue createOrderByValue}:<br>
	 *	  <code>result.isPopulated(KEY) == false</code>
	 * </li>
	 * <li>The state is not populated:<br>
	 *	  <code>result.isPopulated(STATE) == false</code>
	 * </li>
	 * </ul>
	 *
	 * @param typeName The fully qualified type of the OrderValue that is to be created.
	 * @returns an Objects which is derived from the given typeName.
	 * @exception javax.oss.IllegalArgumentException if precondition violated: <br>
	 *  type must be one of the strings returned by {@link #getOrderTypes}
	 *
	 */
	public OrderValue makeOrderValue(String typeName)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"makeOrderValue",
					new Object[][] { { "typeName", typeName }
		});

		if (!Arrays.asList(MY_ORDER_TYPES).contains(typeName)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Order type " + typeName + " ist not supported");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		OrderValueImpl orderValue = new OrderValueImpl();
		orderValue.setOrderType(typeName);
		try {
		orderValue.setManagedEntityKeyDummy(
			new OrderKeyImpl(
				getApplicationContext(),
				getApplicationDN(),
				typeName,
				//VP put empty string instead of : null
				getUniqueKey()));
		} catch(RemoteException re){
		  throw new javax.oss.IllegalArgumentException("Remote Exception when generating the unique key");
		}
				
		orderValue.setLastModifiedOnServer(new java.util.Date());

		if (typeName.equals(CreateOrderValue.class.getName())) {
			// prepare order so that a new order can be created with it
			orderValue.setService(makeServiceValue(mySupportedServicesStr[0]));
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"makeOrderValue",
				new Object[] { "orderValue", orderValue });
		return orderValue;
	}

	/**
	 * Create a Value Type object for a specific Managed Entity type.
	 * Not to be confused with the creation of a Managed Entity.
	 * The Session Bean is used as a factory for the creation of
	 * Value types.
	 *
	 * @param valuetype fully qualified name of the leaf managed entity value interface
	 * @param valueType
	 * @return the implementation class of a managed entity of a specific type
	 * @exception javax.oss.IllegalArgumentException
	 * @exception javax.ejb.CreateException
	 */

	public ManagedEntityValue makeManagedEntityValue(String valueType)
		throws javax.oss.IllegalArgumentException {
		return makeOrderValue(valueType);
	}

	// State handling of orders
	// ------------------------

	/**
	 * Creates a new order object and returns the key for the new object.
	 * <p>
	 * The populated attributes of <code>value</code> are used to initialize the order.
	 * All other attributes are initialzed by the implementation to their default
	 * values (which may be a null value for non-primitive types).
	 * The values of the attributes <code>key()</code> and <code>state</code> are ignored,
	 * even if they are populated. Instead, a new, unique
	 * key is created by this method and returned as result. The state is
	 * initialized to NOT_STARTED.
	 * <p>
	 * Implementation controlled attributes are ignored if they are populated.
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>The order type is as requested:<br>
	 *	 <code>result.getType().equals(type)</code>, where type is the one used in makeOrderType(type).
	 * </li>
	 * <li><code>result.getPrimaryKey().toString()()</code> is different from all other
	 *	 primary keys of this implementation bean instance.
	 * </li>
	 * <li>All other attributes of result have values, such that the client
	 *	 can re-located this implementation bean instance.
	 * </li>
	 * <li>{@link #getOrder(OrderKey) getOrder} returns attribute values,
	 *	 that are equal to the populated attributes of <code>value</code>,
	 *	 if these attributes are client controlled.
	 * </ul>
	 *
	 * <p><b>Message Postcondition:</b>
	 * When this method is called, a JMS message <code>msg</code> will be published:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderCreateEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderCreateEvent</code>
	 * <li><code>msg.getObject().getOrderValue()</code> must have the
	 *		  attributes KEY and STATE populated.
	 *		  An implementation may populate further attributes,
	 *		  especially the ones that were populated in <code>value</code>
	 * </ul>
	 * @exception javax.oss.IllegalArgumentException if precondition violated: <br>
	 *  <code>value</code> must be a return value of {@link #makeOrderValue makeOrderValue}
	 *  from this implementation bean instance.
	 * @exception javax.oss.IllegalAttributeValueException if precondition violated: <br>
	 *  all populated attribute values must be in the value range
	 *  that is allowed be the implementation.
	 * @exception javax.ejb.CreateException in case of an implementation internal problem.
	 */
	public OrderKey createOrderByValue(OrderValue orderValue)
		throws
			javax.oss.IllegalArgumentException,
			javax.oss.IllegalAttributeValueException,
			javax.ejb.CreateException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"createOrderByValue",
					new Object[][] { { "orderValue", orderValue }
		});

		if (orderValue == null || !(orderValue instanceof OrderValueImpl)) {
			CreateException e = new CreateException("Wrong OrderValue-Object");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		if (!orderValue.isPopulated(OrderValue.SERVICES)
			|| orderValue.getServices().length == 0) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Order had no services.");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} else {
			ServiceValue[] services = orderValue.getServices();
			for (int i = 0; i < services.length; i++) {
				if (services[i] == null) {
					javax.oss.IllegalArgumentException e =
						new javax.oss.IllegalArgumentException(
							"At least one element in service array is null.");
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					throw e;
				}
			}
		}

		OrderValueImpl anOrderValue = (OrderValueImpl) orderValue;

		try {
			anOrderValue.setOrderKey(
				new OrderKeyImpl(
					getApplicationContext(),
					getApplicationDN(),
					anOrderValue.getOrderType(),
					getUniqueKey()));
			applyDefaultValues(anOrderValue);
			anOrderValue.setState(OrderState.NOT_STARTED);

			// check if services are of correct type, assign key if they have none and it is a create order, etc...
			handleServices((OrderValueImpl) anOrderValue);
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.ejb.CreateException(re.getMessage());
		}

		// shorten Strings etc. sends AttribtueValueChangeEvents
		String[] changedAttributes = makeDatabaseCompatible(anOrderValue);

//VP		OrderLocalHome anOrderHome = (OrderLocalHome) lookup("java:comp/env/ejb/Order");
		OrderLocalHome anOrderHome = (OrderLocalHome) lookup("ejb/Order");
		if (isLoggingEnabled)
			myLog.log("Got Order home interface.");

		OrderLocal anOrder = null;
			//VP
		try {
			anOrder = anOrderHome.create(anOrderValue);
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.ejb.CreateException(re.getMessage());
		}
			// delay service instantiation --- services are not set, when an order is created
			anOrder.setServices(anOrderValue.getServices());

		if (isLoggingEnabled)
			myLog.log("Created persistent order.");

		try {
			// make sure to return exactly the same as it is in database now, reduces potential value change events to be sent
			// (for example to notify that failed services changed to an emtpy array first...)
			getJmsSender().publish(
				new OrderCreateEventImpl(
					getApplicationDN(),
					anOrder.getAttributes(null)),
				OrderCreateEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.ejb.CreateException(re.getMessage());
		}

		OrderKey returnOrderKey = anOrderValue.getOrderKey();
		if (isLoggingEnabled)
			myLog.logMethodExit(
				"createOrderByValue",
				new Object[] { "returnOrderKey", returnOrderKey });
		return returnOrderKey;
	}

	/**
	 * Creates multiple order objects and returns for each a new key.
	 *
	 * The semantics of this method is equivalent to the semantics of this code:
	 * <pre>
	 *	public OrderKeyResult[] tryCreateOrdersByValues(OrderValue[] values)
	 *	throws javax.oss.IllegalArgumentException, javax.ejb.CreateException
	 *	{
	 *	    if (values == null)
	 *		throw new javax.oss.IllegalArgumentException ("OrderValue[] argument may not be null");
	 *
	 *	    OrderKeyResult[] result = new OrderKeyResultImpl[values.length];
	 *	    for (int i=0; i<values.length; i++) {
	 *		try {
	 *		    OrderKey k = createOrderByValue(values[i]);
	 *		    result[i] = new OrderKeyResultImpl(k, true, null);
	 *		} catch (javax.oss.IllegalAttributeValueException e) {
	 *		    result[i] = new OrderKeyResultImpl(null, false, e);
	 *		} catch (CreateException ce) {
	 *		    result[i] = new OrderKeyResultImpl(null, false, ce);
	 *		} catch (javax.oss.IllegalArgumentException e) {
	 *		    result[i] = new OrderKeyResultImpl(null, false, e);
	 *		}
	 *	    }
	 *
	 *	    return result;
	 *	}
	 * </pre>
	 * @param values all order values
	 * @throws IllegalArgumentException see {@link createOrdersByValue}
	 * @throws CreateException {@link createOrdersByValue}
	 * @return the returned key result array has the same size as the
	 * <CODE>values</CODE> parameter. A key result at position i
	 * in the array either contains the <CODE>OrderKey</CODE> for the <CODE>OrderValue</CODE>
	 * which was on the same position in the <CODE>values</CODE> array or
	 * the exception why the creation failed
	 */
	public OrderKeyResult[] tryCreateOrdersByValues(OrderValue[] values)
		throws javax.oss.IllegalArgumentException, javax.ejb.CreateException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"tryCreateOrdersByValues",
					new Object[][] { { "values", values }
		});

		if (values == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderValue[] argument may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException("null parameter", e);
			throw e;
		}

		OrderKeyResult[] result = new OrderKeyResultImpl[values.length];
		for (int i = 0; i < values.length; i++) {
			try {
				OrderKey k = createOrderByValue(values[i]);
				result[i] = new OrderKeyResultImpl(k, true, null);
			} catch (javax.oss.IllegalAttributeValueException e) {
				result[i] = new OrderKeyResultImpl(null, false, e);
			} catch (CreateException ce) {
				result[i] = new OrderKeyResultImpl(null, false, ce);
			} catch (javax.oss.IllegalArgumentException e) {
				result[i] = new OrderKeyResultImpl(null, false, e);
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("tryCreateOrdersByValues", null);
		return result;
	}

	/**
	 * Puts the order in state {@link OrderState#RUNNING RUNNING}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>getOrder(key).getState().startsWith(RUNNING)</code>
	 * </ul>
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published to the topic:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderStateChangeEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderStateChangeEvent</code>
	 * <li><code>msg.getObject().getOrderKey().equals(key)</code>
	 * <li><code>msg.getObject().getCurrentState().equals(RUNNING)</code>
	 * </ul>
	 * @param key Key for an order.
	 * @exception
	 *  javax.oss.IllegalStateException if precondition violated:
	 *		<code>getOrder(key).getState().startsWith(NOT_STARTED)</code>
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *		The <code>key</code> has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *		of this JVTActivationSession before.
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 *
	 */
	public void startOrderByKey(OrderKey orderKey)
		throws
			javax.oss.IllegalStateException,
			javax.ejb.ObjectNotFoundException,
			javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"startOrderByKey",
					new Object[][] { { "orderKey", orderKey }
		});

		checkOrderKey(orderKey);
		try {
			getOrderProcessor().startOrderByKey(orderKey);
		} catch (RemoteException re) {
			myLog.logException(re);
			// what kind of app exception should be thrown here?!
			markRollback();
			throw new javax.oss.IllegalArgumentException(re.getMessage());
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("startOrderByKey", null);
	}

	/**
	 * Starts multiple order objects.
	 * <p>
	 * <pre>
	 *	public OrderKeyResult[] tryStartOrdersByKeys(OrderKey[] orderKeys)
	 *	throws javax.oss.IllegalArgumentException {
	 *
	 *	    if (orderKeys == null)
	 *		throw new javax.oss.IllegalArgumentException ("OrderKey[] argument may not be null");
	 *
	 *	     Vector result = new Vector();
	 *	     for (int i=0; i<orderKeys.length; i++) {
	 *		 try {
	 *		     startOrderByKey(orderKeys[i]);
	 *		 } catch (javax.ejb.ObjectNotFoundException e) {
	 *		     result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
	 *		 } catch (javax.oss.IllegalStateException e) {
	 *		     result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
	 *		 } catch (javax.oss.IllegalArgumentException e) {
	 *		     result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
	 *		 }
	 *	     }
	 *
	 *	     return (OrderKeyResult[])result.toArray(new OrderKeyResultImpl[0]);
	 *	 }
	 *
	 * </pre>
	 * @param orderKeys keys for which the order shall be started
	 * @throws IllegalArgumentException is throws, if the array or one of the keys is null
	 * @return the OrderKeyResult array with all failed orders
	 */
	public OrderKeyResult[] tryStartOrdersByKeys(OrderKey[] orderKeys)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"tryStartOrdersByKeys",
					new Object[][] { { "orderKeys", orderKeys }
		});

		if (orderKeys == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderKey[] argument may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException("null parameter", e);
			throw e;
		}

		Vector result = new Vector();
		for (int i = 0; i < orderKeys.length; i++) {
			try {
				startOrderByKey(orderKeys[i]);
			} catch (javax.ejb.ObjectNotFoundException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.IllegalStateException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.IllegalArgumentException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("tryStartOrdersByKeys", null);
		return (OrderKeyResult[]) result.toArray(new OrderKeyResultImpl[0]);
	}

	/**
	 * Put the order from state {@link OrderState#RUNNING RUNNING} in
	 * the state {@link OrderState#SUSPENDED SUSPENDED}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>getOrder(key).getState().startsWith(SUSPENDED)</code>
	 * <li><code>JVTActivationOption.SUSPENDED_STATE in getSupportedOptionalOperations()</code>
	 * </ul>
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published to the topic:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderStateChangeEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderStateChangeEvent</code>
	 * <li><code>msg.getObject().getOrderKey().equals(key)</code>
	 * <li><code>msg.getObject().getCurrentState().startsWith(SUSPENDED)</code>
	 * </ul>
	 * @param key Key for an order.
	 * @exception
	 *  javax.oss.IllegalStateException if precondition violated:
	 *		 <code>getOrder(key).getState().startsWith(NOT_STARTED)</code>
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *	The <code>key</code> has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *	   of this JVTActivationSession before.
	 * @exception
	 *  javax.oss.UnsupportedOperationException if precondition violated:
	 *	 <code>JVTActivationOption.SUSPEND_ORDER returned by getSupportedOptionalOperations()</code>
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 */
	public void suspendOrderByKey(OrderKey orderKey)
		throws
			javax.oss.IllegalStateException,
			javax.oss.IllegalArgumentException,
			javax.oss.UnsupportedOperationException,
			ObjectNotFoundException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"suspendOrderByKey",
					new Object[][] { { "orderKey", orderKey }
		});

		// WSAD requires all exceptions from the remote interface to be thrown, so let's invent something ugly here
		if (Arrays
			.binarySearch(
				MY_SUPPORTED_OPERATIONS,
				JVTActivationSessionOptionalOps.SUSPEND_ORDER_BY_KEY)
			< 0) {
			javax.oss.UnsupportedOperationException e =
				new javax.oss.UnsupportedOperationException("not supported");
			if (isLoggingEnabled)
				myLog.logThrownException("not supported", e);
			throw e;
		}

		throw new ObjectNotFoundException();
	}

	/**
	 * Puts the order from state {@link OrderState#SUSPENDED SUSPENDED}
	 * back in state {@link OrderState#RUNNING RUNNING}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>getOrder(key).getState().startsWith(RUNNING)</code>
	 * </ul>
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderStateChangEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderStateChangeEvent</code>
	 * <li><code>msg.getObject().getOrderKey().equals(key)</code>
	 * <li><code>msg.getObject().getCurrentState().startsWith(RUNNING)</code>
	 * </ul>
	 * @param key Key for an order.
	 * @exception
	 *  javax.oss.IllegalStateException if precondition violated:
	 *		 <code>getOrder(key).getState().startsWith(SUSPENDED)</code>
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *	The <code>key</code> has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *	   of this JVTActivationSession before.
	 * @exception
	 *  javax.oss.UnsupportedOperationException if precondition violated:
	 *	 <code>JVTActivationOption.RESUME_ORDER returned by getSupportedOptionalOperations()</code>
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 */
	public void resumeOrderByKey(OrderKey orderKey)
		throws
			javax.oss.IllegalStateException,
			javax.oss.IllegalArgumentException,
			javax.oss.UnsupportedOperationException,
			ObjectNotFoundException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"resumeOrderByKey",
					new Object[][] { { "orderKey", orderKey }
		});

		// WSAD requires all exceptions from the remote interface to be thrown, so let's invent something ugly here
		if (Arrays
			.binarySearch(
				MY_SUPPORTED_OPERATIONS,
				JVTActivationSessionOptionalOps.RESUME_ORDER_BY_KEY)
			< 0) {
			javax.oss.UnsupportedOperationException e =
				new javax.oss.UnsupportedOperationException("not supported");
			if (isLoggingEnabled)
				myLog.logThrownException("not supported", e);
			throw e;
		}

		throw new ObjectNotFoundException();
	}

	/**
	 * Abort the order and put it to state {@link OrderState#ABORTED ABORTED}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>getOrder(key).getState().startsWith(ABORTED)</code>
	 * </ul>
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published to the topic:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderStateChangeEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderStateChangeEvent</code>
	 * <li><code>msg.getObject().getOrderKey().equals(key)</code>
	 * <li><code>msg.getObject().getCurrentState().startsWith(ABORTED)</code>
	 * </ul>
	 * @param key Key for an order.
	 * @exception
	 *  javax.oss.IllegalStateException if precondition violated:
	 *		 <code>getOrder(key).getState().startsWith(OPEN)</code>
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *	The <code>key</code> has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *	   of this JVTActivationSession before.
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 */
	public void abortOrderByKey(OrderKey orderKey)
		throws
			javax.oss.IllegalStateException,
			javax.ejb.ObjectNotFoundException,
			javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"abortOrderByKey",
					new Object[][] { { "orderKey", orderKey }
		});

		checkOrderKey(orderKey);

		//VP OrderLocalHome anOrderHome = (OrderLocalHome) lookup("java:comp/env/ejb/Order");
		OrderLocalHome anOrderHome = (OrderLocalHome) lookup("ejb/Order");
		OrderLocal anOrder = null;
		try {
			anOrder =
				anOrderHome.findByPrimaryKey(new CMPManagedEntityKey(orderKey));
		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			javax.ejb.ObjectNotFoundException e =
				new javax.ejb.ObjectNotFoundException(
					"Could not find an order for key [" + orderKey + "]");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		String orderState = null;
			orderState = anOrder.getState();
		if (!orderState.startsWith(OrderState.OPEN)) {
			javax.oss.IllegalStateException e =
				new javax.oss.IllegalStateException(
					"Order is not in state ["
						+ OrderState.OPEN
						+ "], but in ["
						+ orderState
						+ "]!");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		try {
			anOrder.setState(OrderState.ABORTED_BYCLIENT);

			getJmsSender().publish(
				new OrderStateChangeEventImpl(
					orderKey.getApplicationDN(),
					"order aborted upon client request",
					OrderState.ABORTED_BYCLIENT,
					orderKey),
				OrderStateChangeEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.oss.IllegalArgumentException(re.getMessage());
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("abortOrderByKey", null);
	}

	/**
	 * Aborts multiple order objects.
	 * <p>
	 * The semantics of this method is equivalent to the semantics of this pseudo code:
	 * <pre>
	 * Vector result = new Vector();
	 * for (int i=0; i&lt;values.size(); i++) {
	 *	   try {
	 *		   abortOrderByKey(values[i]);
	 *	   } catch (ObjectNotFoundException e,
	 *				IllegalAttributeValueException e,
	 *				IllegalStateException e) {
	 *		   result.add(new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
	 *	   }
	 * }
	 * return result.toArray();
	 * </pre>
	 * @param orderKeys
	 * @throws IllegalArgumentException
	 * @return	*/
	public OrderKeyResult[] tryAbortOrdersByKeys(OrderKey[] orderKeys)
		throws javax.oss.IllegalArgumentException, javax.oss.IllegalStateException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"tryAbortOrdersByKeys",
					new Object[][] { { "orderKeys", orderKeys }
		});

		if (orderKeys == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderKey[] argument may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException("null parameter", e);
			throw e;
		}

		Vector result = new Vector();
		for (int i = 0; i < orderKeys.length; i++) {
			try {
				abortOrderByKey(orderKeys[i]);
			} catch (javax.oss.IllegalStateException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.ejb.ObjectNotFoundException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.IllegalArgumentException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("tryAbortOrdersByKeys", null);
		return (OrderKeyResult[]) result.toArray(new OrderKeyResultImpl[0]);
	}

	/**
	 * Terminates the lifetime of an order. <p>
	 *
	 * The order is removed from the implementation. <p>
	 * If this method is not supported, it can be assumed that the
	 * implementation takes care of removing the orders by itself
	 * (e.g. after a certain time period after the order CLOSED).
	 * See also {@link JVTActivationOption}.
	 * <p>
	 * If this method is supported, then it's used by the client to indicate
	 * that it does not need the order anymore. Even if the method is supported,
	 * the implementation might remove the order by itself.
	 * <p>
	 * If the order is removed (either because of a call to this method or
	 * because the implementation removes the order by itself)
	 * a JMS message {@link OrderRemoveEvent} is sent.
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published to the topic:
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderRemoveEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderRemoveEvent</code>
	 * <li><code>msg.getObject().getOrderValue() returns the order value</code>
	 * </ul>
	 * @exception
	 *  javax.oss.IllegalStateException if precondition violated:
	 *		 <code>getOrder(key).getState().startsWith(CLOSED)</code>
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *	The <code>key</code> has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *	   of this JVTActivationSession before.
	 * @exception
	 *  javax.oss.UnsupportedOperationException if precondition violated:
	 *	 <code>JVTActivationOption.REMOVE_ORDER returned by getSupportedOptionalOperations()</code>
	 * @exception javax.ejb.RemoveException in case of an implementation internal problem.
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 */
	public void removeOrderByKey(OrderKey orderKey)
		throws
			javax.oss.IllegalArgumentException,
			javax.oss.IllegalStateException,
			javax.ejb.ObjectNotFoundException,
			javax.oss.UnsupportedOperationException,
			javax.ejb.RemoveException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"removeOrderByKey",
					new Object[][] { { "orderKey", orderKey }
		});
		checkOrderKey(orderKey);

		//VPOrderLocalHome anOrderHome = (OrderLocalHome) lookup("java:comp/env/ejb/Order");
		OrderLocalHome anOrderHome = (OrderLocalHome) lookup("ejb/Order");
		OrderLocal anOrder = null;
		try {
			anOrder =
				anOrderHome.findByPrimaryKey(new CMPManagedEntityKey(orderKey));
		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			javax.ejb.ObjectNotFoundException e =
				new javax.ejb.ObjectNotFoundException(
					"Could not find an order for key [" + orderKey + "]");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
        }
		String orderState = null;
			orderState = anOrder.getState();
		if (!orderState.startsWith(OrderState.CLOSED)) {
			javax.oss.IllegalStateException e =
				new javax.oss.IllegalStateException(
					"Order is not in state ["
						+ OrderState.CLOSED
						+ "] but in ["
						+ orderState
						+ "].");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		try {
			OrderValue anOrderValue = anOrder.getAttributes(null);
			anOrder.remove();
			getJmsSender().publish(
					new OrderRemoveEventImpl(
						anOrderValue.getOrderKey().getApplicationDN(),
						anOrderValue),
					OrderRemoveEvent.class.getName(),
					anOrderValue.getApiClientId(), anOrderValue.getOrderKey());
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.ejb.RemoveException(re.getMessage());
/*		} catch (RemoveException re) {
			myLog.logException(re);
			markRollback();
			throw re;*/
		}
		
		if (isLoggingEnabled)
			myLog.logMethodExit("removeOrderByKey", null);
	}

	/**
	 * Removes multiple order objects.
	 * <p>
	 * The semantics of this method is equivalent to the semantics of this pseudo code:
	 * <pre>
	 * Vector result = new Vector();
	 * for (int i=0; i&lt;values.size(); i++) {
	 *	   try {
	 *		   removeOrderByKey(values[i]);
	 *	   } catch (ObjectNotFoundException e,
	 *				IllegalStateException e) {
	 *		   result.add(new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
	 *	   }
	 * }
	 * return result.toArray();
	 * </pre>
	 * @param orderKeys
	 * @throws IllegalArgumentException
	 * @throws UnsupportedOperationException
	 * @throws RemoveException
	 * @return	*/
	public OrderKeyResult[] tryRemoveOrdersByKeys(OrderKey[] orderKeys)
		throws
			javax.oss.IllegalArgumentException,
			javax.oss.UnsupportedOperationException,
			javax.ejb.RemoveException {

		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"tryRemoveOrdersByKeys",
					new Object[][] { { "orderKeys", orderKeys }
		});

		if (orderKeys == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderKey[] argument may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException("null parameter", e);
			throw e;
		}

		Vector result = new Vector();
		for (int i = 0; i < orderKeys.length; i++) {
			try {
				removeOrderByKey(orderKeys[i]);
			} catch (javax.ejb.ObjectNotFoundException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.IllegalStateException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.IllegalArgumentException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			} catch (javax.oss.UnsupportedOperationException e) {
				result.add(new OrderKeyResultImpl(orderKeys[i], false, e));
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("tryRemoveOrdersByKeys", null);
		return (OrderKeyResult[]) result.toArray(new OrderKeyResultImpl[0]);
	}

	// Changing and retrieving orders
	// ------------------------------

	/**
	 * Changes the attribute values of an order.
	 * <p>
	 * Only the attributes that are populated are changed.
	 * The attribute key identifies the order to change.
	 * Implementation controlled attributes, inluding the attribute state are ignored,
	 * even if populated.
	 * <p>
	 * Otherwise this method is atomic in the sense that all attributes are updated:
	 * If some attribute values are not correct, the order object is not changed at all.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>
	 * </ul>
	 * <p><b>Message Postcondition:</b>
	 * The following is true on the JMS message <code>msg</code> published to the topic :
	 * <ul>
	 * <li><code>msg.getStringProperty(OSS_EVENT_TYPE_PROP_NAME).equals(OrderAttributeValueChangeEvent.class.getName())</code>
	 * <li><code>msg.getObject() instanceof OrderAttributeValueChangeEvent</code>
	 * <li><code>msg.getObject().getNewOrderValue()</code> contains all the attributes that have been changed.
	 * </ul>
	 * @param order OrderValue.
	 * @exception javax.oss.IllegalArgumentException (Programmin Errorg) if precondition violated:
	 * <ul><li><code> order.isPopulated(KEY)</code></li>
	 *	  <li>precondition of getOrder(order.getOrderKey()) must be true</li>
	 * </ul>
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *	 The <code>order.getOrderKey()</code> must refer to an existing object.
	 * @exception javax.oss.IllegalAttributeValueException if precondition violated:
	 * all populated attribute values must be in the value range
	 * that is allowed be the implementation.
	 * @exception javax.oss.IllegalStateException if precondition violated:
	 *  The order must be in the correct state, at least NOT_STARTED.
	 *  For all other states, the implemantation may throw this exception
	 *  For state CLOSED it must throw this exception.
	 * @exception javax.oss.SetException in case of an implementation internal problem.
	 * @exception javax.oss.ResyncRequiredException if precondition violated:
	 *	 If checkConcurrentAccess is set, then a the lastUpdateVersionNumber must be correct:
	 *	 <pre>
	 *	 checkConcurrentAccess =>
	 *		order.getLastUpdateVersionNumber() ==
	 *			  getOrder(order.getOrderKey()).getLastUpdateVersionNumber()
	 *	 </pre>
	 */
	public void setOrderByValue(
		OrderValue orderValue,
		boolean checkConcurrentAccess)
		throws
			javax.oss.IllegalArgumentException,
			javax.ejb.ObjectNotFoundException,
			javax.oss.IllegalAttributeValueException,
			javax.oss.IllegalStateException,
			javax.oss.SetException,
			javax.oss.ResyncRequiredException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"setOrderByValue",
					new Object[][] { { "orderValue", orderValue }, {
				"checkConcurrentAccess", new Boolean(checkConcurrentAccess)
				}
		});

		if (orderValue == null || !(orderValue instanceof OrderValueImpl)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException("Wrong OrderValue type");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		if (!orderValue.isPopulated(OrderValue.KEY)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"No OrderKey in OrderValue");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		OrderKey orderKey = orderValue.getOrderKey();
		OrderLocal anOrder = null;
		try {
			anOrder =
				(
					//VP (OrderLocalHome) lookup("java:comp/env/ejb/Order")).findByPrimaryKey(new CMPManagedEntityKey(orderKey));
					(OrderLocalHome) lookup("ejb/Order")).findByPrimaryKey(new CMPManagedEntityKey(orderKey));
			// complain if date of last modification on server is "bigger" than when it was when orderValue was retreived.
			if (checkConcurrentAccess
				&& anOrder.getLastModified().compareTo(
					((OrderValueImpl) orderValue).getLastModifiedOnServer())
					> 0) {
				javax.oss.ResyncRequiredException e =
					new javax.oss.ResyncRequiredException(
						orderKey,
						"Provided OrderValue is out of date! Date on Server is "
							+ anOrder.getLastModified().toString()
							+ ", while date on supplied OrderValue is "
							+ ((OrderValueImpl) orderValue)
								.getLastModifiedOnServer());
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
			String orderState = anOrder.getState();
			if (orderState.startsWith(OrderState.CLOSED)
				|| orderState.startsWith(OrderState.SUSPENDED)
				|| orderState.startsWith(OrderState.RUNNING)) {
				javax.oss.IllegalStateException e =
					new javax.oss.IllegalStateException(
						"Order is in state ["
							+ orderState
							+ "], expected states were all except ["
							+ OrderState.CLOSED
							+ "], ["
							+ OrderState.SUSPENDED
							+ "] or ["
							+ OrderState.RUNNING
							+ "].");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			javax.ejb.ObjectNotFoundException e =
				new javax.ejb.ObjectNotFoundException(
					"Order could not be found");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		String[] changedDbAttributes =
			makeDatabaseCompatible((OrderValueImpl) orderValue);

		try {
			if (orderValue.isPopulated(OrderValue.SERVICES)) {
				// check if services are of correct type, assign key if they have none and it is a create order, etc...
				handleServices((OrderValueImpl) orderValue);

				// search and destroy all services which are now not anymore present in the new array

				// search all services which are now referenced by this order
				ServiceLocal[] currentServices = anOrder.getServiceEntityBeans();

				//DEBUG
				if (currentServices == null || currentServices.length == 0) {
					javax.oss.SetException e =
						new javax.oss.SetException(
							"Current Order with key "
								+ orderValue
									.getOrderKey()
									.getPrimaryKey()
									.toString()
								+ " contains no services");
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					markRollback();
					throw e;
				}

				// compare to all new service values
				ServiceValue[] newServices = orderValue.getServices();
				String[] newServicesPK = new String[newServices.length];
				for (int i = 0; i < newServicesPK.length; i++) {
					if (newServices[i].isPopulated(ServiceValue.KEY)) {
						// this is an old service, which already has a primary key assigned
						newServicesPK[i] =
							newServices[i]
								.getServiceKey()
								.getPrimaryKey()
								.toString();
					} else {
						// take the chance and assign a new key to the service - IDIOT!
						// this is done in handleServices
					}
				}
				for (int i = 0; i < currentServices.length; i++) {
					boolean serviceStillInUse = false;
					String currentServicePK =
						currentServices[i]
							.getServiceKey()
							.getPrimaryKey()
							.toString();
					for (int j = 0;
						j < newServicesPK.length && !serviceStillInUse;
						j++) {
						// newServicesPK[j] maybe not, currentServicePK is never null
						if (currentServicePK.equals(newServicesPK[j])) {
							serviceStillInUse = true;
						}
					}

					// remove Service if new OrderValue does not reference it anymore
					if (!serviceStillInUse) {

						try {
							currentServices[i].remove();
						} catch (RemoveException re) {
							if (isLoggingEnabled)
								myLog.logException(
									"Could not remove Service for key "
										+ currentServicePK,
									re);
							markRollback();
							throw new javax.oss.SetException(re.getMessage());
						} 
					}
				}
			}
		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.oss.SetException(re.getMessage());
		}

		try {

			String[] changedAttributes = anOrder.updateAttributes(orderValue);

			if (changedAttributes.length + changedDbAttributes.length > 0) {

				// merge attribute lists
				List attributeList = new java.util.ArrayList();
				for (int i = 0; i < changedDbAttributes.length; i++) {
					if (!attributeList.contains(changedDbAttributes[i])) {
						attributeList.add(changedDbAttributes[i]);
					}
				}
				for (int i = 0; i < changedAttributes.length; i++) {
					if (!attributeList.contains(changedAttributes[i])) {
						attributeList.add(changedAttributes[i]);
					}
				}

				// add KEY to attribtues
				String[] changedAttributesAndKey =
					new String[changedAttributes.length + 1];
				System.arraycopy(
					changedAttributes,
					0,
					changedAttributesAndKey,
					0,
					changedAttributes.length);
				changedAttributesAndKey[changedAttributes.length] =
					OrderValue.KEY;

				OrderValue deltaOrder =
					anOrder.getAttributes(changedAttributesAndKey);

				OrderAttributeValueChangeEvent aChangeEvent =
					new OrderAttributeValueChangeEventImpl(
						getApplicationDN(),
						deltaOrder);
				getJmsSender().publish(
					aChangeEvent,
					OrderAttributeValueChangeEvent.class.getName(),
					anOrder.getApiClientId(), anOrder.getOrderKey());
			}

		} catch (RemoteException re) {
			myLog.logException(re);
			markRollback();
			throw new javax.oss.SetException(re.getMessage());
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("setOrderByValue", null);
	}

	/**
	 * Changes the attributes of multiple orders.
	 * <p>
	 * The semantics of this method is equivalent to the semantics of this pseudo code:
	 * <pre>
	 * Vector result = new Vector();
	 * for (int i=0; i&lt;values.size(); i++) {
	 *	   try {
	 *		   setOrderByValue(values[i]);
	 *	   } catch (ObjectNotFoundException e,
	 *				IllegalAttributeValueException e,
	 *				IllegalStateException e) {
	 *		   result.add(new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
	 *	   }
	 * }
	 * return result.toArray();
	 * </pre>
	 * @param values
	 * @param checkAccess
	 * @throws SetException
	 * @exception javax.oss.IllegalArgumentException (Programming Error) if precondition violated:
	 * <ul><li>All values must be non null:<br>
	 *		   <code>values != null && forall v in values: v!= null</code>
	 *	   </li>
	 *	   <li>All keys most be populated<br>
	 *		   <code>forall v in values: isPopulated(v, KEY)</code>
	 *	   </li>
	 *	   <li>All keys must be different</li>
	 * </ul>
	 * @return	*/
	public OrderKeyResult[] trySetOrdersByValues(
		OrderValue[] values,
		boolean checkAccess)
		throws javax.oss.IllegalArgumentException, javax.oss.SetException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"trySetOrdersByValues",
					new Object[][] { { "values", values }
		});

		if (values == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderValue[] argument may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException("null parameter", e);
			throw e;
		}

		Vector result = new Vector();
		for (int i = 0; i < values.length; i++) {
			try {
				setOrderByValue(values[i], checkAccess);
			} catch (ObjectNotFoundException e) {
				result.add(
					new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
			} catch (javax.oss.ResyncRequiredException e) {
				result.add(
					new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
			} catch (IllegalAttributeValueException e) {
				result.add(
					new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
			} catch (javax.oss.IllegalStateException e) {
				result.add(
					new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
			} catch (javax.oss.IllegalArgumentException e) {
				// there was possibly something wrong with the key, or no key at all
				if (!values[i].isPopulated(OrderValue.KEY)) {
					result.add(new OrderKeyResultImpl(null, false, e));
				} else {
					result.add(
						new OrderKeyResultImpl(
							values[i].getOrderKey(),
							false,
							e));
				}
			} catch (javax.oss.SetException e) {
				result.add(
					new OrderKeyResultImpl(values[i].getOrderKey(), false, e));
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("trySetOrdersByValues", null);
		return (OrderKeyResult[]) result.toArray(new OrderKeyResultImpl[0]);
	}

	/**
	 * Returns values for the order identified by the (unique) key.
	 *
	 * <p>
	 * If the argument <code>attributeNames</code> is empty, then this method returns a
	 * fully populated order value object.
	 * <p>
	 * If the argument <code>attributeNames</code> is contains elements, then only these attributes
	 * are populated. Illegal attribute names are ignored, to be consistent with queries
	 * that deal with polymorphic results.
	 * <p>
	 * An implementation must support the following attributes:
	 * <ul><li>All attributes of the order</li>
	 *	  <li>All attributes of an service. The attribute names to be used have to be
	 *		  prefixes with "services.".</li>
	 * </ul>
	 * If the argument attributeNames contains one or more strings with prefix
	 * "services.", then the returned order attribute "services" is populated and
	 * contains all services. In each
	 * ServiceValue, the relevant attribute is populated.
	 * <p>
	 * Here is an example to retrieve all service keys for an order ok:
	 * <pre>
	 *	ov = getOrder(ok, "services.key");	// or
	 *	ov = getOrder(ok, OrderValue.SERVICES + "." + ServiceValue.KEY);
	 * </pre>
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>If the result key is populated, it must refer to the requested order: <br>
	 *	  <code>result.isPopulated(KEY) => result.getOrderKey().equals(key)</code>
	 * <li>Order attributes are populated as requested, ignoring the illegal ones:
	 *	  <pre>
	 *	  forall a in result.getAttributesNames():
	 *		   a in attributeNames => result.isPopulated(a)
	 *	  </pre>
	 * </li>
	 * <li>Service attributes are populated as requested, ignoring the illegal ones:
	 *	  <pre>
	 *	  forall s in result.getServices():
	 *			for all a in s.getAttributeNames():
	 *				"services."+a in attributeNames =>
	 *					result.isPopulated("services") && s.isPopulated(a)
	 *	 </pre>
	 * </li>
	 * </ul>
	 * @param key identifies the order.
	 * @param attributeNames only extract part of the attributes.
	 *		  In this case this is null, all possible properties are extracted.
	 * @exception javax.ejb.ObjectNotFoundException if precondition violated:
	 *  <ul>
	 *  <li>The order as identified by the key must exist.
	 *  </li>
	 *  </ul>
	 * @exception javax.oss.IllegalArgumentException (Programming Error) if precondition violated:
	 *  <ul>
	 *  <li>Key must be a valid key for this implementation:<br>
	 *		The <code>key != null</code> and has been returned by {@link JVTActivationSession#createOrderByValue createOrderByValue}
	 *		before.
	 *  </li>
	 *  <li>attributeNames must be provided: <br>
	 *	   <code>attributeNames != null</code>
	 *  </li>
	 *  <li>attributeNames cannot be null:
	 *	  <pre>
	 *	  attributeNames != null =>
	 *			forall a in attributesNames: a != null
	 *	  </pre>
	 *  </li>
	 *  </ul>
	 */
	public OrderValue getOrderByKey(OrderKey orderKey, String[] attributeNames)
		throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"getOrderByKey",
					new Object[][] { { "orderKey", orderKey }, {
				"attributeNames", attributeNames }
		});

		checkOrderKey(orderKey);

		if (attributeNames == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Array attributeNames is null");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		OrderValue orderValue = null;
		try {
			OrderLocalHome anOrderHome =
				//VP (OrderLocalHome) lookup("java:comp/env/ejb/Order");
				(OrderLocalHome) lookup("ejb/Order");
			OrderLocal anOrder =
				anOrderHome.findByPrimaryKey(new CMPManagedEntityKey(orderKey));
			orderValue = anOrder.getAttributes(attributeNames);
		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			javax.ejb.ObjectNotFoundException e =
				new javax.ejb.ObjectNotFoundException(
					"Could not find an order for key [" + orderKey + "]");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getOrderByKey",
				new Object[] { "orderValue", orderValue });
		return orderValue;
	}

	/**
	 * Equivalent to <code>{@link #getOrderByKey(OrderKey,String[]) getOrder}(key, new String[]())</code>.
	 * @see #getOrderByKey(OrderKey,String[])
	 */
	public OrderValue getOrderByKeyAllAttr(OrderKey orderKey)
		throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"getOrderByKeyAllAttr",
					new Object[][] { { "orderKey", orderKey }
		});

		OrderValue aValue = getOrderByKey(orderKey, new String[0]);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getOrderByKeyAllAttr",
				new Object[] { "aValue", aValue });
		return aValue;
	}

	/**
	 * Returns values for a list of orders identified by the (unique) keys.
	 * <p>
	 * The semantics of this method is equivalent to the semantics of this code:
	 * <pre>
	 *	OrderValue[] result = new OrderValue[keys.size()];
	 *	for (int i=0; i&lt;keys.size(); i++) {
	 *	   try {
	 *		  result[i] = getOrderByKey(keys[i], attributeNames);
	 *	   catch (javax.ejb.ObjectNotFoundException) {
	 *		  result[i] = null;
	 *	   }
	 *	}
	 *	return result;
	 * </pre>
	 * The method throws the same exception as getOrderByKeyAllAttr, only ObjectNotFoundExceptionExceptions
	 * are replaced with null values.
	 * <p>
	 * More detailed semantics, including postconditions and exceptions,
	 * are described in "getOrderByKey".
	 * @see #getOrderByKey(OrderKey, String[])
	 * @exception javax.oss.IllegalArgumentException (Programming Error) if precondition violated:
	 *  <li>preconditions for attributeNames as in getOrder.</li>
	 *  <li>All keys must be non null:<br>
	 *	   <code>keys != null && forall k in keys: k!= null</code>
	 *  </li>
	 */
	public OrderValue[] getOrdersByKeys(
		OrderKey[] orderKeys,
		String[] attributeNames)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"getOrdersByKeys",
					new Object[][] { { "orderKeys", orderKeys }, {
				"attributeNames", attributeNames }, });

		if (orderKeys == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Key array is of zero length");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		OrderValue[] result = new OrderValue[orderKeys.length];

		for (int i = 0; i < orderKeys.length; i++) {
			try {
				result[i] = getOrderByKey(orderKeys[i], attributeNames);
			} catch (javax.ejb.ObjectNotFoundException e) {
				if (isLoggingEnabled)
					myLog.logException(e);
				result[i] = null;
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getOrdersByKeys",
				new Object[] { "result", result });
		return result;
	}

	/**
	 * Queries orders and returns the ones that match at least one of the template orders.
	 * <p>
	 * This method can be used to perform simple queries on the orders. It can handle all
	 * queries that need to compare attributes by equality.
	 * <p>
	 * The method uses the populated attribute values provided in the argument <code>templates</code>
	 * to find matching orders. For the matching orders, the attributes named in
	 * <code>attributeNames</code> are populated. Note that this two steps are
	 * logically independent: it is possible to populate more attributes in <code>templates</code>
	 * than in <code>attributeNames</code> or vice versa.
	 * <p>
	 * The objects that are indirectly returned by the iterator have the
	 * following attribute values (which need not be populated when they
	 * they are returned to the client!):
	 * <ul>
	 * <li>Every order must matches at least one template (logical or).
	 * </li>
	 * <li>An order matches a template if for <i>all populated template attributes</i> the corrosponding order attribute
	 *	  <i>matches</i> the template attribute (logical and).<br>
	 *	  <i>All populated template attributes</i> refers to the following attribute:<br>
	 *		- All attributes defined in OrderValue, except KEY and SERVICES.<br>
	 *		- The type attribute of an order key.<br>
	 *		- All attributes of the first ServiceValue object of the order, if it exists.
	 *			For the key the same exceptions as for orders apply.<br>
	 *		- An implementation may support additional attributes, but must not.<br>
	 *
	 *	All other attributes are ignored, even when they are populated. For example
	 *	Domain and PrimaryKey of Key and all non-first service values are ignored.
	 * </li>
	 * <li>An order attribute <i>matches</i> an template attribute depending on the following cases:<br>
	 *		-	If the order attribute does not exist, it matches.<br>
	 *		-	If the order attribute is a key attribute, then it matches,
	 *			if the type values are equal. I.e. other values of the key are ignored,
	 *			for example primaryKey. This also applies to the service key.<br>
	 *		-	Otherwise the attribute matches if their values are equal.<br>
	 *
	 * </li>
	 * </ul>
	 * <p>
	 * The returned objects have attributes populated as described in getOrder().
	 * <p>
	 * The following examples matches all orders that are CLOSED and
	 * have the same types as the service sv:
	 * <pre>
	 *	ServiceValue sv = ...
	 *	OrderValue ov = ...
	 *	ov.unpopulateAllAttributes();
	 *	ov.setState(CLOSED);
	 *	ServiceValue newSv = sv.clone();
	 *	newSv.unpopulateAllAttributes();
	 *	newSv.setKey(newSv.makeKey());
	 *	om.getOrderByKey(new OrderValue[] {ov}, new String[0]{});
	 * </pre>
	 * <p>
	 *
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *  <ul>
	 *  <li>At least one template must be provided: <br>
	 * <code>templates != null && templates.size() > 0</code>
	 *  </li>
	 *  <li>Each template must have at least one attribute populated: <br>
	 *	  for all t in templates: <code>t.getPopulatedAttributesNames().size() > 0</code>
	 *  </li>
	 *  <li>attributeNames must fullfill the same conditions as in getOrder().</li>
	 *  </ul>
	 **@exception
	 *  javax.oss.UnsupportedOperationException if precondition violated:
	 *	 <code>JVTActivationOption.GET_ORDERS returned by getSupportedOptionalOperations()</code>
	 *
	 */
	public OrderValueIterator getOrdersByTemplates(
		OrderValue[] orderValueTemplates,
		String[] attributeNames)
		throws
			javax.oss.IllegalArgumentException,
			javax.oss.UnsupportedOperationException {
		myLog
			.logMethodEntry(
				"getOrdersByTemplates",
				new Object[][] {
					{ "orderValueTemplates", orderValueTemplates },
					{
				"attributeNames", attributeNames }
		});

		//WSAD required all specifed exceptions to be possibly thrown, so let's fake the unsupported operation exception
		if (Arrays
			.binarySearch(
				MY_SUPPORTED_OPERATIONS,
				JVTActivationSessionOptionalOps.GET_ORDERS_BY_TEMPLATES)
			< 0) {
			throw new javax.oss.UnsupportedOperationException();
		}

		for (int i = 0; i < orderValueTemplates.length; i++) {
			int populatedUsefulAttributes =
				orderValueTemplates[i].getPopulatedAttributeNames().length;
			if (isLoggingEnabled)
				myLog.log(
					"order "
						+ i
						+ "has "
						+ populatedUsefulAttributes
						+ " populated");
			// Attribute FAILED SERVICSE is not used in a template search
			if (orderValueTemplates[i]
				.isPopulated(OrderValue.FAILED_SERVICES)) {
				populatedUsefulAttributes--;
				if (isLoggingEnabled)
					myLog.log(
						"failed services is disregarded, remaining "
							+ populatedUsefulAttributes);
			}
			// Only type attribute of a key is used
			if (orderValueTemplates[i].isPopulated(OrderValue.KEY)) {
				String type = orderValueTemplates[i].getOrderKey().getType();
				if (type == null || type.equals("")) {
					populatedUsefulAttributes--;
					if (isLoggingEnabled)
						myLog.log(
							"key is disregarded, remaining "
								+ populatedUsefulAttributes);
				}
			}
			// disregard service if it only contains a key without type
			if (orderValueTemplates[i].isPopulated(OrderValue.SERVICES)) {
				ServiceValue aServiceValue =
					orderValueTemplates[i].getServices()[0];
				int populatedServiceAttributes =
					aServiceValue.getPopulatedAttributeNames().length;
				if (isLoggingEnabled)
					myLog.log(
						"services[0] is populated and has "
							+ populatedServiceAttributes
							+ " populated");
				if (aServiceValue.isPopulated(ServiceValue.KEY)) {
					String serviceType =
						aServiceValue.getServiceKey().getType();
					if (serviceType == null || serviceType.equals("")) {
						populatedServiceAttributes--;
						if (isLoggingEnabled)
							myLog.log(
								"servicekey is disregarded, remaining "
									+ populatedServiceAttributes);
					}
				}
				if (populatedServiceAttributes == 1) {
					populatedUsefulAttributes--;
					if (isLoggingEnabled)
						myLog.log(
							"service[0] is disregarded, remaining "
								+ populatedUsefulAttributes);
				}
			}

			if (populatedUsefulAttributes == 0) {
				javax.oss.IllegalArgumentException e =
					new javax.oss.IllegalArgumentException(
						"OrderValue has no populated attributes!"
							+ "(FAILED_SERVICES and Order/ServiceKeys without type are disregarded)"
							+ " If you wish to get all existig orders please use a query value");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
		}

		OrderValueIterator anIterator = null;
		try {
			OrderLocalHome anOrderHome =
				//VP (OrderLocalHome) lookup("java:comp/env/ejb/Order");
				(OrderLocalHome) lookup("ejb/Order");
			Collection orderKeys =
				anOrderHome.filterOrders(orderValueTemplates);
			OrderValueIteratorHome anIteratorHome =
				//VP (OrderValueIteratorHome) lookup("java:comp/env/ejb/OrderValueIterator");
				(OrderValueIteratorHome) lookup("ejb/OrderValueIterator");
			RemoteOrderValueIterator rovi =
				anIteratorHome.create(
					(OrderKey[]) orderKeys.toArray(new OrderKey[0]),
					attributeNames);
			anIterator = new OrderValueIteratorProxy(rovi.getHandle());
		} catch (java.sql.SQLException sqle) {
			if (isLoggingEnabled)
				myLog.logException(sqle);
			EJBException e =
				new EJBException(
					"Backend failure while searching orders",
					sqle);
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} catch (NamingException ne) {
			if (isLoggingEnabled)
				myLog.logException(ne);
			EJBException e =
				new EJBException("Backend failure while searching orders", ne);
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} catch (CreateException ce) {
			if (isLoggingEnabled)
				myLog.logException(ce);
			EJBException e =
				new EJBException("Could not create an OrderValueIterator", ce);
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			EJBException e =
				new EJBException(
					"Could not query for orders or create the iterator",
					re);
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getOrdersByTemplates",
				new Object[] { "anIterator", anIterator });
		return anIterator;
	}

	// Query Methods
	// -------------

	/**
	 * Returns the fully qualified names of all implemented query types.
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>At least QueryAllOrders must be supported:<br>
	 *	  <code>result != && result.size()>0 &&</code>
	 *	   result contains javax.oss.order.QueryAllOrder.class.getName();
	 * </li>
	 * </ul>
	 * @see #makeQueryValue
	 */
	public String[] getQueryTypes() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getQueryTypes", null);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getQueryTypes",
				new Object[] { "MY_QUERY_TYPES", MY_QUERY_TYPES });
		return MY_QUERY_TYPES;
	}

	/**
	 * Returns a value object that can be used to query for orders.
	 *
	 * By using the set methods of the returned value object, the parameter values for query
	 * can be given and afterwards queryOrders can be called.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>result instanceof Class.forName(queryName)</code>.
	 *	The queryName is one of the base types of the result.
	 * </li>
	 * </ul>
	 * @param queryName identifies the type of query.
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *  <ul>
	 *  <li>queryName returned by getQueryTypes()
	 *  </ul>
	 **/
	public QueryValue makeQueryValue(String queryName)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"makeQueryValue",
					new Object[][] { { "queryName", queryName }
		});

		if (!java.util.Arrays.asList(MY_QUERY_TYPES).contains(queryName)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"query \"" + queryName + "\" not available");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		QueryValue aQueryValue = null;
		if (queryName.equals(QueryOrdersByDateInterval.class.getName())) {
			aQueryValue = new QueryOrdersByDateIntervalImpl();
		} else if (queryName.equals(QueryUrgentOrders.class.getName())) {
			aQueryValue = new QueryUrgentOrdersImpl();
		} else if (queryName.equals(QueryAllOrdersValue.class.getName())) {
			aQueryValue = new QueryAllOrdersValueImpl();
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"makeQueryValue",
				new Object[] { "aQueryValue", aQueryValue });
		return aQueryValue;
	}

	/**
	 * Runs a (complex) query and returns the matching orders.
	 *
	 * @param parameters must be one of the value objects returned by makeQueryValue.
	 * @param attributeNames indicates which attributes should be populated in the result.
	 *		 This is further explained in getOrder().
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *  <ul>
	 *  <li>For attributeNames, the conditions as listed in getOrder() apply.</li>
	 *  <li>parameter has been retrieved by calling makeQueryValue()
	 *  </ul>
	 **/
	public OrderValueIterator queryOrders(
		QueryValue aQueryValue,
		String[] attributeNames)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"queryOrders",
					new Object[][] { { "aQueryValue", aQueryValue }, {
				"attributeNames", attributeNames }
		});

		OrderValueIterator anOrderValueIterator = null;

		int invalidAttribute =
			(new OrderValueImpl()).validateAttributeNames(attributeNames);
		if (invalidAttribute >= 0) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Attribute \""
						+ attributeNames[invalidAttribute]
						+ "\" is not a valid attribute");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		try {
			// call overloaded method, throws an illegalArgumentException if not a proper query
			anOrderValueIterator =
				getQueryHelper().processQuery(aQueryValue, attributeNames);
		} catch (FinderException fe) {
			// why should this happen?
			if (isLoggingEnabled)
				myLog.logException(fe);
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			throw new javax.oss.IllegalArgumentException(re.getMessage());
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"queryOrders",
				new Object[] { "anOrderValueIterator", anOrderValueIterator });
		return anOrderValueIterator;
	}

	/**
	 * Query Multiple Managed Entities using a QueryValue
	 *
	 * @param query A QueryValue object representing the Query.
	 * @param attrNames representing the attribute names
	 * @return A ManagedEnityValueIterator used to extract the results of the Query.
	 * @exception javax.oss.IllegalArgumentException
	 * @exception java.rmi.RemoteException
	 */

	//VP public OrderValueIterator queryManagedEntities(
	// comes from the JVTSession respect the JVTSession exact profile
	public ManagedEntityValueIterator queryManagedEntities(
		QueryValue query,
		String[] attrNames)
		throws javax.oss.IllegalArgumentException {
		return queryOrders(query, attrNames);
	}

	// Order Manager information
	// -------------------------

	/**
	 * Returns the fully qualified names of event types that are published by this implementation.
	 * <p>
	 * Can be used to dynmically download the classes of the the event types
	 * to the client and thus automatically unmarshall JMS messages.
	 * <p>
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>result != null && result.size() >= 4</code>At least the four
	 * event types listed below must be supported.
	 * </li>
	 * </ul>
	 *
	 * @see OrderCreateEvent
	 * @see OrderRemoveEvent
	 * @see OrderAttributeValueChangeEvent
	 * @see OrderStateChangeEvent
	 */
	public String[] getEventTypes() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getEventTypes", null);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getEventTypes",
				new Object[] { "MY_EVENT_TYPES", MY_EVENT_TYPES });
		return MY_EVENT_TYPES;
	}

	/**
	 * Get the Event Descriptor associated with an event type name.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>The returned descriptor must be one for the requested type:<br>
	 *		  <code>result.getEventType().equals(eventType)</code>.
	 * </li>
	 * </ul>
	 * @param eventType identifies the type of the event.
	 * @exception
	 *  javax.oss.IllegalArgumentException if precondition violated:
	 *  <ul>
	 *  <li>eventType returned by getEventTypes()
	 *  </ul>
	 */

  	public javax.oss.EventPropertyDescriptor getEventDescriptor(
		String eventType)
		throws javax.oss.IllegalArgumentException {
		//VP
		if (isLoggingEnabled)
			myLog.logMethodEntry("getEventDescriptor",
					new Object[][] { { "eventType", eventType }});

		for (int i = 0; i < MY_EVENT_TYPES.length; i++) {
			
			if (MY_EVENT_TYPES[i].equals(eventType)) {
				javax.oss.EventPropertyDescriptor returnEventDescriptor =
					(javax.oss.EventPropertyDescriptor) ORDER_EVENT_DESCRIPTOR_MAP
							.get(eventType);


				if (isLoggingEnabled)
					myLog.logMethodExit("getEventDescriptor",
						new Object[] {"returnEventDescriptor", returnEventDescriptor });



				return returnEventDescriptor;
			}
		}
		javax.oss.IllegalArgumentException e =
			new javax.oss.IllegalArgumentException(
				"eventType must be  value as returned by getEventTypes()");
		if (isLoggingEnabled)
			myLog.logThrownException(e.getMessage(), e);
		throw e;
	}

	/**
	 * Returns all implemented Order types, that can be used with {@link #makeOrderValue makeOrderValue}.
	 * <p>
	 * At least
	 * <code>"</code>{@link javax.oss.order.CreateOrderValue}<code>"</code>
	 * is returned, it is recommended
	 * that the implementation also returns
	 * <code>"</code>{@link javax.oss.order.ModifyOrderValue}<code>"</code>
	 * and
	 * <code>"</code>{@link javax.oss.order.CancelOrderValue}<code>"</code>.
	 * <p>
	 * In addition, other order types may be returned by the implementation.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>At least one order type must be supported:<br>
	 *	  <code>result.size()>0</code>
	 * </li>
	 * <li>Support for a creation order is mandatory:<br>
	 *	  <code>result</code> must include
	 *	  <code>"</code>{@link javax.oss.order.CreateOrderValue}<code>"</code>
	 * <li>The result is the same as from {@link javax.oss.JVTSession#getManagedEntityTypes}:<br>
	 *	  <code>result.equal(getManagedEntityTypes())</code>
	 * </ul>
	 *
	 * @returns String[] a list of fully qualified type names.
	 */
	public String[] getOrderTypes() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getOrderTypes", null);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getOrderTypes",
				new Object[] { "MY_ORDER_TYPES", MY_ORDER_TYPES });
		return MY_ORDER_TYPES;
	}

	/**
	 * Get the Managed Entity types supported by a JVT Session Bean
	 *
	 * @return String array which contains the fully qualified names of the leaf
	 * node interfaces representing the supported managed entity types.
	 * Note that it is not the implementation class name that is returned.
	 */

	public String[] getManagedEntityTypes() {
		return getOrderTypes();
	}

	private Context getNamingContext() {
		if (myNamingContext == null) {
			try {
				//VP myNamingContext = new javax.naming.InitialContext();
				myNamingContext = (Context) new javax.naming.InitialContext().lookup("java:comp/env");
			} catch (javax.naming.NamingException ne) {
				if (isLoggingEnabled)
					myLog.logException(ne);
			}
		}

		return myNamingContext;
	}

	private Object lookup(String name) {
		Object obj = null;
		try {
			obj = getNamingContext().lookup(name);
		} catch (javax.naming.NamingException ne) {
			if (isLoggingEnabled)
				myLog.logException(ne);
		}

		return obj;
	}

	public void setSessionContext(final SessionContext sCtx)
		throws EJBException {

		// create new logger
		isLoggingEnabled =
			//VP ((java.lang.Boolean) lookup("java:comp/env/loggingEnabled"))
			((java.lang.Boolean) lookup("loggingEnabled"))
				.booleanValue();
		createLog("OSS/J SA RI");

		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"setSessionContext",
					new Object[][] { { "sCtx", sCtx }
		});

		mySessionContext = sCtx;

		if (isLoggingEnabled)
			myLog.logMethodExit("setSessionContext", null);
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbCreate() throws CreateException, EJBException {
		//VP
		try {
			myNamingContext = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("ejbCreate(): " + e.getMessage());
		} 
		//end VP

		if (isLoggingEnabled)
			myLog.logMethodEntry("ejbCreate", null);

		readMySupportedServices();

		if (isLoggingEnabled)
			myLog.logMethodExit("ejbCreate", null);

		//VP intiate the MAP here instead of in a static statement

		try {
		ORDER_EVENT_DESCRIPTOR_MAP.put(
			javax.oss.order.OrderCreateEvent.class.getName(),
			new OrderEventPropertyDescriptorImpl(
				javax.oss.order.OrderCreateEvent.class.getName(),
				propnames,
				proptypes,
				new OrderCreateEventImpl(
					"dummy applicationDN",
					new OrderValueImpl())));
		ORDER_EVENT_DESCRIPTOR_MAP.put(
			javax.oss.order.OrderRemoveEvent.class.getName(),
			new OrderEventPropertyDescriptorImpl(
				javax.oss.order.OrderRemoveEvent.class.getName(),
				propnames,
				proptypes,
				new OrderRemoveEventImpl(
					"dummy applicationDN",
					new OrderValueImpl())));
		ORDER_EVENT_DESCRIPTOR_MAP.put(
			javax.oss.order.OrderAttributeValueChangeEvent.class.getName(),
			new OrderEventPropertyDescriptorImpl(
				javax.oss.order.OrderAttributeValueChangeEvent.class.getName(),
				propnames,
				proptypes,
				new OrderAttributeValueChangeEventImpl(
					"dummy applicationDN",
					new OrderValueImpl())));
		ORDER_EVENT_DESCRIPTOR_MAP.put(
			javax.oss.order.OrderStateChangeEvent.class.getName(),
			new OrderEventPropertyDescriptorImpl(
				javax.oss.order.OrderStateChangeEvent.class.getName(),
				propnames,
				proptypes,
				new OrderStateChangeEventImpl(
					"dummy applicationDN",
					"dummy reason",
					"dummy state",
					new OrderKeyImpl(
						new ApplicationContextImpl(
							"dummy factory",
							new java.util.HashMap(),
							"dummy provider"),
						"dummy applicationDN",
						"dummy type",
						"dummy pk"))));
		} catch (java.rmi.RemoteException rex){
			System.out.println("Unable to create event descriptor map....");
			rex.printStackTrace();
		}

	}

	/**
	 * Returns a list of Service types that can be used with {@link #makeServiceValue makeerviceValue}. <p>
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>At least one servce type is returned:<br>
	 *	  <code>result != null && result.size()>0</code>
	 * </li>
	 * </ul>
	 * @returns String[] a list of fully qualified type names.
	 */
	public String[] getServiceTypes() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getServiceTypes", null);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getServiceTypes",
				new Object[] { "mySupportedServices", mySupportedServicesStr });
		return mySupportedServicesStr;
	}

	/**
	 * Returns a new service value object.
	 * <p>
	 * The attribute values of the returned object should contain
	 * usefull default values. The key is not populated, for more details on the
	 * how the implementation deals with the key, see
	 * the different order types
	 * {@link CreateOrderValue},
	 * {@link ModifyOrderValue}, and
	 * {@link CancelOrderValue}.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li><code>result.isPopulated(KEY) == false</code>
	 * <li><code>result instanceof Class.forName(typeName)</code>
	 *	  The type of the returned object is as requested.
	 * </ul>
	 *
	 * @param serviceType A fully qualified type name.
	 * @returns ServiceValue
	 * @exception javax.oss.IllegalArgumentException if precondition violated:<br>
	 *  serviceType most be one of the strings returned by {@link #getServiceTypes}
	 */
	 public javax.oss.service.ServiceValue makeServiceValue(String serviceType)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"makeServiceValue",
					new Object[][] { { "serviceType", serviceType }
		});

		if (!mySupportedServices.keySet().contains(serviceType)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"Service " + serviceType + " is not supported.");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		RiServiceValue serviceValue =
			(RiServiceValue) mySupportedServices.get(serviceType);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"makeServiceValue",
				new Object[] { "serviceValue", serviceValue });
		return serviceValue;
	}

	/**
	 * Gives information which methods will not throw UnsupportedOperationException.
	 *
	 * <p><b>Postcondition:</b>
	 * <ul>
	 * <li>Every returned string must be one mentioned in JVTActivationOption</li>
	 * <li>If no option is supported, then an empty array is returned:<br>
	 * <code>result != null</code>
	 * </li>
	 * </ul>
	 *
	 * @see JVTActivationOption
	 */
	public String[] getSupportedOptionalOperations() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getSupportedOptionalOperations", null);

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getSupportedOptionalOperations",
				new Object[] {
					"MY_SUPPORTED_OPERATIONS",
					MY_SUPPORTED_OPERATIONS });
		return MY_SUPPORTED_OPERATIONS;
	}

	private String getApplicationDN() {
		if (myApplicationDN == null) {
			//VP myApplicationDN = (String) lookup("java:comp/env/ApplicationDN");
			myApplicationDN = (String) lookup("ApplicationDN");
		}
		return myApplicationDN;
	}

	private JmsSender getJmsSender() {
		if (myJmsSender == null) {
			try {
				JmsSenderHome aHome =
					//VP (JmsSenderHome) lookup("java:comp/env/ejb/JmsSender");
					(JmsSenderHome) lookup("ejb/JmsSender");
				if (isLoggingEnabled)
					myLog.log("received JmsSenderHome: " + aHome);
				myJmsSender = aHome.create();
				if (isLoggingEnabled)
					myLog.log("created JmsSender: " + myJmsSender);
			} catch (RemoteException re) {
				if (isLoggingEnabled)
					myLog.logException("Could not create JmsSender", re);
			} catch (CreateException ce) {
				if (isLoggingEnabled)
					myLog.logException("Could not create JmsSender", ce);
			}
		}
		return myJmsSender;
	}

	private OrderProcessor getOrderProcessor() {
		if (myOrderProcessor == null) {
			try {
				OrderProcessorHome aHome =
					//VP (OrderProcessorHome) lookup("java:comp/env/ejb/OrderProcessor");
					(OrderProcessorHome) lookup("ejb/OrderProcessor");
				if (isLoggingEnabled)
					myLog.log("received OrderProcessorHome: " + aHome);
				myOrderProcessor = aHome.create();
				if (isLoggingEnabled)
					myLog.log("created OrderProcessorr: " + myOrderProcessor);
			} catch (RemoteException re) {
				if (isLoggingEnabled)
					myLog.logException("Could not create OrderProcessor", re);
			} catch (CreateException ce) {
				if (isLoggingEnabled)
					myLog.logException("Could not create OrderProcessor", ce);
			}
		}
		return myOrderProcessor;
	}

	private void createLog(String subSystem) {
		myLog = null;
		String loggingVersion = "";
		// First, try to use WLS 6.1 facility

		try {
			Class wls6Log = Class.forName("weblogic.logging.NonCatalogLogger");
			// if there is no exception thrown, this bean is running under WLS6, so that logging
			// facility can be used
			myLog =
				(BeaTrace) Class
					.forName("com.nokia.oss.ossj.common.ri.BeaTrace60")
					.newInstance();
			loggingVersion = "WebLogic 6.1 logging classes";
		} catch (ClassNotFoundException cnfe) {
			//VP System.out.println(cnfe.toString());
		} catch (InstantiationException ie) {
			//VP System.out.println(ie.toString());
		} catch (IllegalAccessException iae) {
			//VP System.out.println(iae.toString());
		}
		if (myLog == null) {
			// if this is not WLS6.1, simulate logging
			myLog = new BeaTraceSimulator();
			loggingVersion = "Bea logging simulator";
		}
		myLog.setSubSystem(subSystem);
		if (isLoggingEnabled)
			myLog.log("Created new logger, using " + loggingVersion);
	}

	private UniqueKeyGenerator getUniqueKeyGenerator() {
		if (myUniqueKeyGenerator == null) {
			try {
				UniqueKeyGeneratorHome myUniqueKeyHome =
					//VP (UniqueKeyGeneratorHome) lookup("java:comp/env/ejb/UniqueKeyGenerator");
					(UniqueKeyGeneratorHome) lookup("ejb/UniqueKeyGenerator");
				myUniqueKeyGenerator = myUniqueKeyHome.create();
			} catch (CreateException ce) {
				if (isLoggingEnabled)
					myLog.logException(ce);
			} 
            //VP add catch remote exception
            catch (RemoteException re) {
				if (isLoggingEnabled)
					myLog.logException(re);
			}
		}
		return myUniqueKeyGenerator;
	}

	private String[] getUniqueKeys(int amount) throws RemoteException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"getUniqueKeys",
					new Object[][] { { "amount", new Integer(amount)}
		});

		String[] uniqueKeys = null;
		try {
			uniqueKeys = getUniqueKeyGenerator().getUniqueKeys(amount);
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			throw re;
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getUniqueKeys",
				new Object[] { "uniqueKeys", uniqueKeys });
		return uniqueKeys;
	}

	private String getUniqueKey() throws RemoteException {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getUniqueKey", null);

		String uniqueKey = null;
		try {
			uniqueKey = getUniqueKeyGenerator().getUniqueKey();
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			throw re;
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getUniqueKey",
				new Object[] { "uniqueKey", uniqueKey });
		return uniqueKey;
	}

	private void readMySupportedServices() {
		if (isLoggingEnabled)
			myLog.logMethodEntry("readMySupportedServices", null);

		//VP String serviceList = (String) lookup("java:comp/env/SupportedServices");
		String serviceList = (String) lookup("SupportedServices");
		StringTokenizer tokenizer = new StringTokenizer(serviceList);

		mySupportedServices = new HashMap();

		String aService;
		Class serviceClass;
		Class riServiceClass =
			com.nokia.oss.ossj.sa.ri.service.RiServiceValue.class;
		while (tokenizer.hasMoreTokens()) {
			aService = tokenizer.nextToken();
			serviceClass = null;

			try {
				// Check if service is an interface
				serviceClass = Class.forName(aService);
			} catch (ClassNotFoundException cnfe) {
				if (isLoggingEnabled)
					myLog.logException(
						"While trying to read in supported Service interfaces...",
						cnfe);
			}

			if (serviceClass != null) {
				try {
					// Check that interface extends RI-interface
					if (!riServiceClass.isAssignableFrom(serviceClass)) {
						if (isLoggingEnabled)
							myLog.log(
								"Specified Service \""
									+ aService
									+ "\" does not extend reference implementation service");
					} else {

						// try to instanciate implementation class
						RiServiceValue serviceInstance =
							(RiServiceValue) Class
								.forName(aService + "Impl")
								.newInstance();

						// set default values for service
						serviceInstance.setState(ServiceState.ACTIVE);
						if (serviceInstance
							instanceof ManagedEntityValueImpl) {
							// TODO how to set that in a custom implementation of a RiServiceValue?!
							(
								(
									ManagedEntityValueImpl) serviceInstance)
										.setManagedEntityKeyDummy(
								new ServiceKeyImpl(
									getApplicationContext(),
									getApplicationDN(),
									aService,
									null));
						}

						// everything is ok, store service type and implementation.
						mySupportedServices.put(aService, serviceInstance);
						if (isLoggingEnabled)
							myLog.log("added service \"" + aService + "\"");
					}
				} catch (ClassNotFoundException cnfe) {
					if (isLoggingEnabled)
						myLog.logException(cnfe);
				} catch (IllegalAccessException iae) {
					if (isLoggingEnabled)
						myLog.logException(iae);
				} catch (InstantiationException ie) {
					if (isLoggingEnabled)
						myLog.logException(ie);
				}
			}
		}
		mySupportedServicesStr =
			(String[]) mySupportedServices.keySet().toArray(new String[0]);

		if (isLoggingEnabled)
			myLog.logMethodExit("readMySupportedServices", null);
	}

	/**This method checks if a RiServiceValue type parameter is one of the supported types by this implementation
	 */
	private void checkServiceValue(RiServiceValue aServiceValue)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"checkServiceValue",
					new Object[][] { { "aServiceValue", aServiceValue }
		});

		if (aServiceValue == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException("Service value is null");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		Class[] interfaces = aServiceValue.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if (mySupportedServices
				.keySet()
				.contains(interfaces[i].getName())) {

				if (isLoggingEnabled)
					myLog.logMethodExit("checkServiceValue", null);
				return;
			}
		}
		javax.oss.IllegalArgumentException e =
			new javax.oss.IllegalArgumentException(
				"Service value did not implement a supported type");
		if (isLoggingEnabled)
			myLog.logThrownException(e.getMessage(), e);
		throw e;
	}

	/**
	 * This method should check if a service with a certain key is existant, and thus can be modified or canceled.
	 */
	private void checkServiceExistance(ServiceKey aServiceKey)
		throws javax.oss.IllegalArgumentException, RemoteException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"checkServiceExistance",
					new Object[][] { { "aServiceKey", aServiceKey }
		});

		if (aServiceKey == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"ServiceKey may not be null");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		ServiceLocalHome aServiceHome =
			//VP (ServiceLocalHome) lookup("java:comp/env/ejb/Service");
			(ServiceLocalHome) lookup("ejb/Service");
		if (aServiceHome == null) {
			RemoteException e =
				new RemoteException("could not find remote home interface for services");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		try {
			ServiceLocal existingService =
				aServiceHome.findExistingService(
					aServiceKey.getPrimaryKey().toString());
			// if no exception is thrown, there is a service with this key, so everything is ok
			if (isLoggingEnabled)
				myLog.logMethodExit("checkServiceExistance", null);
			return;
		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"there is no service existing with service key ["
						+ aServiceKey.toString()
						+ "].");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

	}

	private void applyDefaultValues(OrderValue anOrderValue) {
		if (!anOrderValue.isPopulated(OrderValue.PRIORITY)) {
			anOrderValue.setPriority(OrderPriority.NORMAL);
		}
	}

	private QueryHelper getQueryHelper() {
		if (aQueryHelper == null) {
			aQueryHelper =
				new QueryHelper(
					//VP (OrderLocalHome) lookup("java:comp/env/ejb/Order"),
					(OrderLocalHome) lookup("ejb/Order"),
					//VP (OrderValueIteratorHome) lookup("java:comp/env/ejb/OrderValueIterator"));
					(OrderValueIteratorHome) lookup("ejb/OrderValueIterator"));
		}
		return aQueryHelper;
	}

	private ApplicationContext getApplicationContext() {
		if (myApplicationContext == null) {
			myApplicationContext =
				ApplicationContextImpl.getApplicationContext(
					getNamingContext());
		}
		return myApplicationContext;
	}

	private String[] makeDatabaseCompatible(OrderValueImpl anOrderValue) {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"makeDatabaseCompatible",
					new Object[][] { { "anOrderValue", anOrderValue }
		});

		java.util.Set changedAttributes = new java.util.HashSet();
		if (anOrderValue.isPopulated(OrderValue.API_CLIENT_ID)
			&& anOrderValue.getApiClientId() != null
			&& anOrderValue.getApiClientId().length() > 255) {
			anOrderValue.setApiClientId(
				anOrderValue.getApiClientId().substring(0, 255));
			changedAttributes.add(OrderValue.API_CLIENT_ID);
		}
		if (anOrderValue.isPopulated(OrderValue.DESCRIPTION)
			&& anOrderValue.getDescription() != null
			&& anOrderValue.getDescription().length() > 255) {
			anOrderValue.setDescription(
				anOrderValue.getDescription().substring(0, 255));
			changedAttributes.add(OrderValue.DESCRIPTION);
		}
		if (anOrderValue.isPopulated(OrderValue.PURCHASE_ORDER)
			&& anOrderValue.getPurchaseOrder() != null
			&& anOrderValue.getPurchaseOrder().length() > 255) {
			anOrderValue.setPurchaseOrder(
				anOrderValue.getPurchaseOrder().substring(0, 255));
			changedAttributes.add(OrderValue.PURCHASE_ORDER);
		}
		if (anOrderValue.isPopulated(OrderValue.STATE)
			&& anOrderValue.getState() != null
			&& anOrderValue.getState().length() > 255) {
			// shorten state to a valid superstate
			String state = anOrderValue.getState();
			while (state.length() > 255) {
				state = state.substring(0, state.lastIndexOf('.'));
			}
			anOrderValue.setState(state);
			changedAttributes.add(OrderValue.STATE);
		}
		ServiceValue[] serviceValues = anOrderValue.getServices();
		for (int i = 0; i < serviceValues.length; i++) {
			RiServiceValue anRiServiceValue = (RiServiceValue) serviceValues[i];
			if (anRiServiceValue.isPopulated(RiServiceValue.SUBSCRIBER_ID)
				&& anRiServiceValue.getSubscriberId() != null
				&& anRiServiceValue.getSubscriberId().length() > 255) {
				anRiServiceValue.setSubscriberId(
					anRiServiceValue.getSubscriberId().substring(0, 255));
				changedAttributes.add(OrderValue.SERVICES);
			}
			if (anRiServiceValue
				.isPopulated(RiServiceValue.SERVICE_ACTIVATOR_HOME_JNDI_NAME)
				&& anRiServiceValue.getServiceActivatorHomeJndiName() != null
				&& anRiServiceValue.getServiceActivatorHomeJndiName().length()
					> 255) {
				anRiServiceValue.setServiceActivatorHomeJndiName(
					anRiServiceValue
						.getServiceActivatorHomeJndiName()
						.substring(
						0,
						255));
				changedAttributes.add(OrderValue.SERVICES);
			}
			if (anRiServiceValue.isPopulated(RiServiceValue.STATE)
				&& anRiServiceValue.getState() != null
				&& anRiServiceValue.getState().length() > 255) {
				// shorten state to a valid superstate
				String state = anRiServiceValue.getState();
				while (state.length() > 255) {
					state = state.substring(0, state.lastIndexOf('.'));
				}
				anRiServiceValue.setState(state);
				changedAttributes.add(OrderValue.SERVICES);
			}
		}

		String[] dirtyAttributes = anOrderValue.getDirtyAttributeNames();
		if (isLoggingEnabled)
			myLog.logMethodExit(
				"makeDatabaseCompatible",
				new Object[] { "dirtyAttributes", dirtyAttributes });
		return dirtyAttributes;
	}

	private void handleServices(OrderValueImpl anOrderValue)
		throws javax.oss.IllegalArgumentException, RemoteException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"handleServices",
					new Object[][] { { "anOrderValue", anOrderValue }
		});

		ServiceValue[] serviceValues = anOrderValue.getServices();
		List servicesLackingKey = new ArrayList();
		// check if Service value is an implementation returned by newServiceValue, IllegalArgumentException thrown
		// if service is not supported
		for (int i = 0; i < serviceValues.length; i++) {
			checkServiceValue((RiServiceValue) serviceValues[i]);

			// only create a new service key for activate orders, for all other orders, there must already be
			// a service present and a key provided
			if (anOrderValue
				.getOrderType()
				.equals(CreateOrderValue.class.getName())) {

				if (!serviceValues[i].isPopulated(ServiceValue.STATE)) {
					serviceValues[i].setState(ServiceState.ACTIVE);
				}
				servicesLackingKey.add(serviceValues[i]);
			} else {
				if (serviceValues[i].isPopulated(ServiceValue.KEY)) {
					// if key is either null or a service for this key cannot be found, an IAE is thrown
					checkServiceExistance(serviceValues[i].getServiceKey());
				} else {
					javax.oss.IllegalArgumentException e =
						new javax.oss.IllegalArgumentException(
							"Services without a key may only be added to a CreateOrderValue!");
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					throw e;
				}
			}

		}
		// set keys on all services which do not have one yet
		if (!servicesLackingKey.isEmpty()) {
			int size = servicesLackingKey.size();
			// only one service? take a shortcut
			if (size == 1) {
				ServiceValue currentService =
					(ServiceValue) servicesLackingKey.get(0);
				//reconstruct service type from implementation class name
				String serviceImplType = currentService.getClass().getName();
				String serviceType =
					serviceImplType.substring(
						0,
						serviceImplType.lastIndexOf("Impl"));
				currentService.setServiceKey(
					new ServiceKeyImpl(
						getApplicationContext(),
						getApplicationDN(),
						serviceType,
						getUniqueKey()));
			} else {
				String[] primaryKeys = getUniqueKeys(size);
				ApplicationContext anAppContext = getApplicationContext();
				String appDN = getApplicationDN();
				ServiceValue currentService;
				for (int i = 0; i < size; i++) {
					currentService = (ServiceValue) servicesLackingKey.get(i);
					//reconstruct service type from implementation class name
					String serviceImplType =
						currentService.getClass().getName();
					String serviceType =
						serviceImplType.substring(
							0,
							serviceImplType.lastIndexOf("Impl"));
					currentService.setServiceKey(
						new ServiceKeyImpl(
							anAppContext,
							appDN,
							serviceType,
							primaryKeys[i]));
				}
			}
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("handleServices", null);
	}

	private void checkOrderKey(OrderKey anOrderKey)
		throws javax.oss.IllegalArgumentException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"checkOrderKey",
					new Object[][] { { "anOrderKey", anOrderKey }
		});

		if (anOrderKey == null) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException("OrderKey is null");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		if (!(anOrderKey instanceof OrderKeyImpl)) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderKey not of the correct type");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		if (!getApplicationDN().equals(anOrderKey.getApplicationDN())) {
			javax.oss.IllegalArgumentException e =
				new javax.oss.IllegalArgumentException(
					"OrderKey has different ApplicationDN than this application");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("checkOrderKey", null);
	}

	/*    
	    protected void forceRollback() {
		forceRollback("unexpected exception");
	    }
	    protected void forceRollback(String message) {
		markRollback();
		throw new RuntimeException(message);
	    }
	*/

	protected void markRollback() {
		try {
			mySessionContext.setRollbackOnly();
		} catch (java.lang.IllegalStateException ise) {
			ise.printStackTrace();
		}
	}

}
