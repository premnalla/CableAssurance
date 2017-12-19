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

import java.sql.*;

import java.rmi.RemoteException;

import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;
import javax.naming.directory.*;

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
public class OrderProcessorBean implements javax.ejb.SessionBean {

	private BeaTrace myLog;
	private UniqueKeyGenerator myUniqueKeyGenerator;
	private boolean isLoggingEnabled = false;

	public OrderProcessorBean() {
	}
	private SessionContext mySessionContext;

	public void setSessionContext(final SessionContext sCtx)
		throws EJBException {

		// create new logger
		isLoggingEnabled =
			//VP ((java.lang.Boolean) lookup("java:comp/env/loggingEnabled"))
			((java.lang.Boolean) lookup("loggingEnabled"))
				.booleanValue();
		createLog("OSS/J SA Order Processor");

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

	public void ejbRemove()
		throws javax.ejb.EJBException, java.rmi.RemoteException {
	}

	public void ejbPassivate()
		throws javax.ejb.EJBException, java.rmi.RemoteException {
	}

	public void ejbActivate()
		throws javax.ejb.EJBException, java.rmi.RemoteException {
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

		if (isLoggingEnabled)
			myLog.logMethodExit("ejbCreate", null);
	}

	protected void forceRollback() {
		markRollback();
		throw new RuntimeException("unexpected exception");
	}

	protected void markRollback() {
		try {
			mySessionContext.setRollbackOnly();
		} catch (java.lang.IllegalStateException ise) {
			ise.printStackTrace();
		}
	}
	// BUSINESS METHODS

	private JmsSender myJmsSender;
	private String myApplicationDN;
	//VP private InitialContext myNamingContext;
	private Context myNamingContext;

	public void startOrderByKey(OrderKey anOrderKey)
		throws
			javax.ejb.ObjectNotFoundException,
			javax.oss.IllegalArgumentException,
			javax.oss.IllegalStateException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"startOrderByKey",
					new Object[][] { { "anOrderKey", anOrderKey }
		});

		try {
			// get Order Entity Bean
			CMPManagedEntityKey aCmpKey = new CMPManagedEntityKey(anOrderKey);
			OrderLocal anOrder = getOrderHome().findByPrimaryKey(aCmpKey);

			// check order for correct state
			String orderState = anOrder.getState();
			if (!orderState.equals(OrderState.NOT_STARTED)) {
				javax.oss.IllegalStateException e =
					new javax.oss.IllegalStateException(
						"Order \""
							+ anOrderKey.toString()
							+ "\" is not in state "
							+ OrderState.NOT_STARTED
							+ ", but in "
							+ orderState
							+ "!");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}

			// send state change event to OrderState.RUNNING
			anOrder.setState(OrderState.RUNNING);
			OrderStateChangeEvent anEvent =
				new OrderStateChangeEventImpl(
					getApplicationDN(),
					"Order started immediatly upon client request.",
					OrderState.RUNNING,
					anOrderKey);
			getJmsSender().publish(
				anEvent,
				OrderStateChangeEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());

			// finish Order, when no requestedCompletionDate is set
			if (anOrder.getRequestedCompletionDate() == null) {
				finishOrder(anOrder);
			} else {
				// all other cases will be finished by the OrderScheduler
			}

		} catch (FinderException fe) {
			if (isLoggingEnabled)
				myLog.logException(fe);
			markRollback();
			javax.ejb.ObjectNotFoundException e =
				new javax.ejb.ObjectNotFoundException(
					"Could not find an order for key ["
						+ anOrderKey.toString()
						+ "].");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			forceRollback();
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("startOrderByKey", null);
	}

	public void finishOrder(OrderKey anOrderKey)
		throws javax.oss.IllegalStateException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"finishOrder",
					new Object[][] { { "anOrderKey", anOrderKey }
		});

		OrderLocal anOrder;
		try {
			// get Order Entity Bean
			CMPManagedEntityKey aCmpKey = new CMPManagedEntityKey(anOrderKey);
			anOrder = getOrderHome().findByPrimaryKey(aCmpKey);
		} catch (FinderException fe) {
			if (isLoggingEnabled) {
				myLog.logException(fe);
				myLog.logMethodExit("finishOrder", null);
			}
			forceRollback();
			return;
		}
		String orderState = null;
		// check order for correct state
		orderState = anOrder.getState();
		if (!orderState.equals(OrderState.RUNNING)) {
			javax.oss.IllegalStateException e =
				new javax.oss.IllegalStateException(
					"Order \""
						+ anOrderKey.toString()
						+ "\" is not in state "
						+ OrderState.NOT_STARTED
						+ ", but in "
						+ orderState
						+ "!");
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}

		finishOrder(anOrder);

		if (isLoggingEnabled)
			myLog.logMethodExit("finishOrder", null);
	}

	private void finishOrder(OrderLocal anOrder) {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"finishOrder",
					new Object[][] { { "anOrder", anOrder }
		});
		OrderKey anOrderKey = null;
		anOrderKey = anOrder.getOrderKey();
		String saHomeJndiName = null;
		try { // exception during order processing
			ServiceLocal[] services = anOrder.getServiceEntityBeans();

			for (int i = 0; i < services.length; i++) {

				try { // failed to activate a service
					saHomeJndiName =
						services[i].getServiceActivatorHomeJndiName();

					if (saHomeJndiName != null && !saHomeJndiName.equals("")) {

						ServiceActivatorHome saHome =
							(ServiceActivatorHome) lookup(saHomeJndiName);

						if (saHome == null) {
							markServiceFailed(
								services[i],
								new ServiceException(
									"ServiceActivator SessionBean home interface \""
										+ saHomeJndiName
										+ "\" could not be found."));
							continue;
						}

						ServiceActivator aServiceActivator = saHome.create();
						delegatedServiceHandling(
							aServiceActivator,
							anOrderKey.getType(),
							services[i].getAttributes(null));
					}
					internalServiceHandling(anOrderKey.getType(), services[i]);
				} catch (ServiceException se) {
					markServiceFailed(services[i], se);
				} catch (CreateException ce) {
					markServiceFailed(services[i], ce);
				} catch (RemoteException re) {
					markServiceFailed(services[i], re);
				}
			}
			anOrder.setActualCompletionDate(
				new Timestamp(System.currentTimeMillis()));
			getJmsSender().publish(
				new OrderAttributeValueChangeEventImpl(
					anOrderKey.getApplicationDN(),
					anOrder.getAttributes(
						new String[] {
							OrderValue.KEY,
							OrderValue.ACTUAL_COMPLETION_DATE,
							OrderValue.FAILED_SERVICES })),
				OrderAttributeValueChangeEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());

		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			abortOrderByKey(
				anOrder,
				"Order aborted, unknown reason.\n" + re.toString());
			return;
		}

		try {
			anOrder.setState(OrderState.COMPLETED);
			OrderStateChangeEvent anEvent =
				new OrderStateChangeEventImpl(
					getApplicationDN(),
					"Order finished either by client request or by requestedCompletionDate.",
					OrderState.COMPLETED,
					anOrderKey);
			getJmsSender().publish(
				anEvent,
				OrderStateChangeEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			forceRollback();
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("finishOrder", null);

	}

	private void markServiceFailed(ServiceLocal aService, Exception reason) {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"markServiceFailed",
					new Object[][] { { "aService", aService }, {
				"reason", reason }
		});

			aService.setFailed(true);
			aService.setFailedException(reason);

		if (isLoggingEnabled)
			myLog.logMethodExit("markServiceFailed", null);
	}

	private void abortOrderByKey(OrderLocal anOrder, String abortReason) {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"abortOrderByKey",
					new Object[][] { { "anOrder", anOrder }, {
				"abortReason", abortReason }
		});

		try {
			anOrder.setState(OrderState.ABORTED_BYSERVER);
			if (isLoggingEnabled)
				myLog.log(
					"Order could not be processed, aborted by server!\n"
						+ abortReason);
			OrderStateChangeEvent anEvent =
				new OrderStateChangeEventImpl(
					getApplicationDN(),
					abortReason,
					OrderState.ABORTED_BYSERVER,
					anOrder.getOrderKey());
			getJmsSender().publish(
				anEvent,
				OrderStateChangeEvent.class.getName(),
				anOrder.getApiClientId(), anOrder.getOrderKey());
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			forceRollback();
		}

		if (isLoggingEnabled)
			myLog.logMethodExit("abortOrderByKey", null);

	}

	// UTILITY METHODS

	private void createService(ServiceLocal aService) throws ServiceException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"createService",
					new Object[][] { { "aService", aService }
		});

			OrderLocalHome anOrderHome =
				//VP (OrderLocalHome) lookup("java:comp/env/ejb/Order");
				(OrderLocalHome) lookup("ejb/Order");
			OrderLocal inventoryOrder = null;
			String invOrderKey = null;
			try {
				inventoryOrder = anOrderHome.findInventoryOrder();
				invOrderKey =
					(String) inventoryOrder.getOrderKey().getPrimaryKey();
			} catch (FinderException fe) {
				OrderValueImpl anOrderValue = new OrderValueImpl();
				try {
					invOrderKey = getUniqueKey();
				} catch (SQLException sqle) {
					if (isLoggingEnabled)
						myLog.logException(sqle);
					markRollback();
					ServiceException e =
						new ServiceException(
							"could not generate unique key for dummy order for service inventory\n"
								+ sqle.toString());
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					throw e;
				}
				anOrderValue.setOrderKey(
					new OrderKeyImpl(
						null,
						null,
						"---INVENTORY---",
						invOrderKey));
				anOrderValue.setState(OrderState.CLOSED + ".inventory_dummy");
				try {
					inventoryOrder = anOrderHome.create(anOrderValue);
				} catch (CreateException ce) {
					if (isLoggingEnabled)
						myLog.logException(ce);
					markRollback();
					ServiceException e =
						new ServiceException(
							"could not create dummy order for service inventory\n"
								+ ce.toString());
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					throw e;
				} catch (java.rmi.RemoteException re) {
					if (isLoggingEnabled)
						myLog.logException(re);
					markRollback();
					ServiceException e =
						new ServiceException(
							"could not create dummy order for service inventory\n"
								+ re.toString());
					if (isLoggingEnabled)
						myLog.logThrownException(e.getMessage(), e);
					throw e;
				}
			}
			ServiceValue aServiceValue = aService.getAttributes(null);
			ServiceLocalHome aServiceHome =
				//VP (ServiceLocalHome) lookup("java:comp/env/ejb/Service");
				(ServiceLocalHome) lookup("ejb/Service");
			try {
				aServiceHome.create(
					(RiServiceValueImpl) aServiceValue,
					invOrderKey);
			} catch (CreateException ce) {
				if (isLoggingEnabled)
					myLog.logException(ce);
				markRollback();
				ServiceException e =
					new ServiceException(
						"could not create service in inventory\n"
							+ ce.toString());
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}

		if (isLoggingEnabled)
			myLog.logMethodExit("createService", null);
	}

	private void modifyService(ServiceLocal aService) throws ServiceException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"modifyService",
					new Object[][] { { "aService", aService }
		});

		try {
			ServiceLocalHome aServiceHome =
				//VP (ServiceLocalHome) lookup("java:comp/env/ejb/Service");
				(ServiceLocalHome) lookup("ejb/Service");
			ServiceLocal existingService = null;
			if (aServiceHome == null) {
				RemoteException e =
					new RemoteException("could not find remote home interface for services");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
			try {
				existingService =
					aServiceHome.findExistingService(
						((CMPServiceDeltaKey) aService.getPrimaryKey())
							.mevPrimaryKey
							.toString());
			} catch (FinderException fe) {
				if (isLoggingEnabled)
					myLog.logException(fe);
				markRollback();
				ServiceException e =
					new ServiceException(
						"there is no service existing with service key ["
							+ ((CMPServiceDeltaKey) aService.getPrimaryKey())
								.mevPrimaryKey
								.toString()
							+ "].");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
			ServiceValue aServiceValue = aService.getAttributes(null);

			existingService.setAttributes((RiServiceValue) aServiceValue);

		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			ServiceException e =
				new ServiceException(
					"could not get or set attributes on service in for service inventory\n"
						+ re.toString());
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		if (isLoggingEnabled)
			myLog.logMethodExit("modifyService", null);
	}

	private void removeService(ServiceLocal aService)
		throws ServiceException, RemoteException {
		if (isLoggingEnabled)
			myLog
				.logMethodEntry(
					"removeService",
					new Object[][] { { "aService", aService }
		});

		try {
			ServiceLocalHome aServiceHome =
				//VP (ServiceLocalHome) lookup("java:comp/env/ejb/Service");
				(ServiceLocalHome) lookup("ejb/Service");
			ServiceLocal existingService = null;
			if (aServiceHome == null) {
				RemoteException e =
					new RemoteException("could not find remote home interface for services");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
			try {
				existingService =
					aServiceHome.findExistingService(
						((CMPServiceDeltaKey) aService.getPrimaryKey())
							.mevPrimaryKey
							.toString());
			} catch (FinderException fe) {
				if (isLoggingEnabled)
					myLog.logException(fe);
				markRollback();
				ServiceException e =
					new ServiceException(
						"there is no service existing with service key ["
							+ ((CMPServiceDeltaKey) aService.getPrimaryKey())
								.mevPrimaryKey
								.toString()
							+ "].");
				if (isLoggingEnabled)
					myLog.logThrownException(e.getMessage(), e);
				throw e;
			}
			existingService.remove();
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			ServiceException e =
				new ServiceException(
					"could not remove existing service from service inventory\n"
						+ re.toString());
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		} catch (RemoveException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
			markRollback();
			ServiceException e =
				new ServiceException(
					"could not remove existing service from service inventory\n"
						+ re.toString());
			if (isLoggingEnabled)
				myLog.logThrownException(e.getMessage(), e);
			throw e;
		}
		if (isLoggingEnabled)
			myLog.logMethodExit("removeService", null);
	}

	// ENVIRONMENT METHODS#

	private OrderLocalHome getOrderHome() {
		//VP return (OrderLocalHome) lookup("java:comp/env/ejb/Order");
		return (OrderLocalHome) lookup("ejb/Order");
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

	private String getApplicationDN() {
		if (myApplicationDN == null) {
			//VP myApplicationDN = (String) lookup("java:comp/env/ApplicationDN");
			myApplicationDN = (String) lookup("ApplicationDN");
		}
		return myApplicationDN;
	}

	private Context getNamingContext() {

		if (myNamingContext == null) {
			try {
				//VP myNamingContext = new javax.naming.InitialContext();
				myNamingContext = (Context) new javax.naming.InitialContext().lookup("java:comp/env");
			} catch (javax.naming.NamingException ne) {
				if (isLoggingEnabled)
					myLog.logException(
						"Tried to establish initial context",
						ne);
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
				myLog.logException("Tried to lookup \"" + name + "\"", ne);
		}

		return obj;
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

	private String getUniqueKey() throws SQLException {
		if (isLoggingEnabled)
			myLog.logMethodEntry("getUniqueKey", null);

		String uniqueKey = null;
		try {
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
			}
			uniqueKey = myUniqueKeyGenerator.getUniqueKey();
		} catch (RemoteException re) {
			if (isLoggingEnabled)
				myLog.logException(re);
		}

		if (isLoggingEnabled)
			myLog.logMethodExit(
				"getUniqueKey",
				new Object[] { "uniqueKey", uniqueKey });
		return uniqueKey;
	}

	protected void delegatedServiceHandling(
		ServiceActivator aServiceActivator,
		String orderType,
		RiServiceValue aServiceValue)
		throws ServiceException, RemoteException {
		if (orderType.equals(CreateOrderValue.class.getName())) {
			aServiceActivator.createService(aServiceValue);
		} else if (orderType.equals(ModifyOrderValue.class.getName())) {
			aServiceActivator.modifyService(aServiceValue);
		} else if (orderType.equals(CancelOrderValue.class.getName())) {
			aServiceActivator.cancelService(aServiceValue);
		}
	}

	protected void internalServiceHandling(String orderType, ServiceLocal aService)
		throws ServiceException, RemoteException {
		if (orderType.equals(CreateOrderValue.class.getName())) {
			createService(aService);
		} else if (orderType.equals(ModifyOrderValue.class.getName())) {
			modifyService(aService);
		} else if (orderType.equals(CancelOrderValue.class.getName())) {
			removeService(aService);
		}
	}
}
