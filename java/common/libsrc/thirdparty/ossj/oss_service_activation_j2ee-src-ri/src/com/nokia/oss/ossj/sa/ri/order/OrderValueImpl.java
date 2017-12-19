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

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;
import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.service.RiServiceValue;
import java.util.Map;

import java.util.Date;
/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderValueImpl extends ManagedEntityValueImpl implements java.io.Serializable,
    CancelOrderValue, CreateOrderValue, ModifyOrderValue
{
    private static AttributeManager orderAttributeManager;
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
	STATE, SERVICES, FAILED_SERVICES, REQUESTED_COMPLETION_DATE, PRIORITY,
	ORDER_DATE, DESCRIPTION, API_CLIENT_ID, ACTUAL_COMPLETION_DATE, PURCHASE_ORDER
    };
    // writeable attributes
    private static final String[] settableAttributeNames = {
		/*STATE,*/ SERVICES, /*FAILED_SERVICES,*/ REQUESTED_COMPLETION_DATE, PRIORITY,
		ORDER_DATE, DESCRIPTION, API_CLIENT_ID, /*ACTUAL_COMPLETION_DATE,*/ PURCHASE_ORDER
    };
    
    // OrderValue
    private String apiClientId;
    private String description;
    private Date requestedCompletionDate;
    private Date actualCompletionDate;
    private Date orderDate;
    private int priority;
    private String purchaseOrder;
    private ServiceValue[] services;
    private ServiceKeyResult[] failedServices;
    private String state;
    private ManagedEntityKey managedEntityKey;
    
    //temporary OrderType - valid as long as no primary key, containing the type, was created
    private String tempOrderType;
    
    // Array of Classes which contains attribute classes in same order as attributeNames
    private static Map attributeClasses;
    
    
		/** Creates new OrderValueImpl */
    public OrderValueImpl() {
	super();
    }
    

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    
    protected void addAttributesTo(AttributeManager anAttributeManager) {
	anAttributeManager.addAttributes(this.attributeNames);
	super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(AttributeManager anAttributeManager) {
	anAttributeManager.setSettableAttributes(this.settableAttributeNames);
	super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager() {
	return orderAttributeManager;
    }

    protected AttributeManager makeAttributeManager() {
	orderAttributeManager = new AttributeManager();
	return orderAttributeManager;
    }

    /**
     * Returns all attribute names which has been changed since the value object was loaded from 
     * the server.
     */
    public String[] getDirtyAttributeNames() {
	if ( !isDirty(SERVICES) ) {
	    ServiceValue[] services = getServices();
	    boolean areServicesDirty = false;
	    for (int i=0 ; i<services.length && !areServicesDirty ; i++) {
		if (services[i] instanceof ManagedEntityValueImpl &&
		((ManagedEntityValueImpl)services[i]).getDirtyAttributeNames().length>0) {
		    areServicesDirty = true;
		}
	    }
	    if (areServicesDirty) {
		setDirtyAttribute(OrderValue.SERVICES);
	    }
	}
	// update for FailedServices can be ignored, since they cannot be updated on the server anyway.
	return super.getDirtyAttributeNames();
    }
    
    public String getOrderType() {
	if (!isPopulated(KEY)) {
	    return tempOrderType;
	}
	return managedEntityKey.getType();
    }
    
    public void setOrderType(String value) {
	if (!isPopulated(KEY)) {
	    tempOrderType=value;
	}
    }
    
    public String getApiClientId(){
	checkAttribute(API_CLIENT_ID);
	return apiClientId;
    }
    
    public void setApiClientId(String name){
	setDirtyAttribute(API_CLIENT_ID);
	this.apiClientId = name;
    }
    
    public String getDescription(){
	checkAttribute(DESCRIPTION);
	return description;
    }
    
    public void setDescription(String text){
	setDirtyAttribute(DESCRIPTION);
	description = text;
    }
    
    /**
     * The Service values attached to this order.
     * <p>
     * This is the most important aspect of an order, since most orders 
     * aim at changing the service.
     * <p>
     * The service value array contains ServiceValues as follows:
     * <ul><li>it can contain one ore more service values.</li>
     *	   <li>it can contain service value of one or more service types.</li>
     * </ul>
     * <p>
     * This is a client controlled, mandatory attribute.
     * If the order is in state NOT_RUNNING.NOT_STARTED, the client
     * can change this attribute by using setOrderByValue. This includes this array itself, the number
     * of service values it contains, and the attribute values of each service value.
     * <p>
     * Once the order has been started, the implementation may raise an 
     * javax.oss.IllegalStateException if the client wants to change this
     * attribute.
     * <p>
     * Further restrictions are defined in specific order types, see
     * {@link CreateOrderValue}, {@link ModifyOrderValue}, and 
     * {@link CancelOrderValue}.
     * <p>
     * An implementation may only support one ServiceValue. In this case,
     * {@link #getSupportedOptionalAttributes()} does not return SERVICES.
     * 
     * @throws 
     *	IllegalArgumentException if violated: 
     *	<ul>
     *	<li>the ServiceValue is one that is supported by JVTActivationSession.getServiceTypes()</li>
     *	<li>The array of ServiceValue must contain at least one element:<br>
	    <code>services != null && services.length > 0</code>
	</li>
     *	<li>all elements sv of the array are != null</li>
     *	<li>all elements sv of the array have different keys</li>
     *	<li>OrderValue.SERVICES is not returned by
     *	    getSupportedOptionalAttributes() => services.size() == 1
     *	</li>
     *	</ul>
     * @see #getServices
     */
    public void setServices(ServiceValue[] services) {
	if (services == null || services.length == 0 ) {
	    throw new java.lang.IllegalArgumentException("Services may not be null or empty");
	}
	for (int i=0 ; i<services.length ; i++) {
	    if ( ! (services[i] instanceof RiServiceValue)) {
		throw new java.lang.IllegalArgumentException("Service "+i+" in array is not of type "+RiServiceValue.class.getName());
	    }
	}
	setDirtyAttribute(SERVICES);
	this.services = services;
    }

    /** Setter for property failedServiceValues.
     * <p>
     * This is a implementation controlled attribute.
     * If {@link #getSupportedOptionalAttributes} returns SERVICES, 
     * then this attribute must be supported. In that case, this attribute indicates
     * which services could not be completed by the order.
     * <p>
     * Otherwise the attribute value is ignored.
     * @see #getFailedServices
     * @param failedServiceValues New value of property failedServiceValues.
     */
    public void setFailedServices(ServiceKeyResult[] failedServices) {
	if (failedServices == null) {
	    throw new java.lang.IllegalArgumentException("FailedServices may not be null");
	}
	setDirtyAttribute(FAILED_SERVICES);
	this.failedServices = failedServices;
    }
    
    /** Getter for attribute failedServiceValues. <p>
     * If the value is non-null, then the following conditions are true:
     * <pre>
     *	for all elements e:
	    e.isSuccess() == false && 
	    getServiceKey() returns one of services &&
     *	    getException( )!= null
     * </pre>
     * @see #setFailedServices
     * @return Value of property failedServiceValues.
     */
    public ServiceKeyResult[] getFailedServices() {
	checkAttribute(FAILED_SERVICES);
	ServiceKeyResult[] copyOfFailedServices = new ServiceKeyResult[failedServices.length];
	System.arraycopy(failedServices, 0, copyOfFailedServices, 0, failedServices.length);
	return copyOfFailedServices;
    }	 
    
    /**
     * Returns a <b>copy</b> of the contained service value array. This is necessary to force the client to use 
     * setServices() in order to replace a service instead of retreiving the array and replacing a service there.
     * Only then, changes in Services can be tracked and be evaluated by the server.
     *
      * @see #setServices
      */
    public ServiceValue[] getServices(){
	checkAttribute(SERVICES);
	ServiceValue[] copyOfServices = new ServiceValue[services.length];
	System.arraycopy(services, 0, copyOfServices, 0, services.length);
	return copyOfServices;
    }
    
    /**
     * Utility method to set one service value.
     * <p>
     * This is only a utility function, that is equivalent to the following code:
     * <pre>
     *	   setServices( new ServiceValue[] { service } );
     * </pre>
     * @see #setServices
     */
    public void setService(ServiceValue service){
	if (service == null) {
	    throw new java.lang.IllegalArgumentException("Service may not be null");
	}
	setServices( new ServiceValue[] { service } );
    }
    
    public int getPriority(){
	checkAttribute(PRIORITY);
	return priority;
    }
    
    public void setPriority(int value) {
	if (OrderPriority.LOW <= value && value <= OrderPriority.EXPEDITE) {
	    setDirtyAttribute(PRIORITY);
	    priority = value;
	} else {
	    throw new java.lang.IllegalArgumentException("Priority value has to be in OrderPriorityEnum");
	}
    }
    
    public String getState(){
	checkAttribute(STATE);
	return state;
    }
    
    // orderStates sorted from most derived state to the most simple - important for for-loop in setState
    public static final String[] orderStates = 
	{
	OrderState.RUNNING, 
	OrderState.NOT_STARTED, 
	OrderState.SUSPENDED, 
	OrderState.NOT_RUNNING, 
	OrderState.OPEN, 
	OrderState.ABORTED_BYCLIENT, 
	OrderState.ABORTED_BYSERVER,
	OrderState.ABORTED, 
	OrderState.COMPLETED, 
	OrderState.CLOSED
	 };
					 
    public void setState(String value) {
	if ( value == null ) {
	    throw new java.lang.IllegalArgumentException("State \""+value+"\" is null.");
	}
	boolean valid = false;
	// first check if the new state is one of the default states in OrderState - should be the case 80%!
	for (int i=0 ; i<orderStates.length && !valid ; i++) {
	    if ( value.equals(orderStates[i]) ) {
		valid = true;
	    }
	}
	// if the prior test failed, value still can be a substate
	for (int i=0 ; i<orderStates.length && !valid; i++) {
	    if ( value.startsWith(orderStates[i]+".") && // invalidates "openx"
		 (orderStates[i].length()+1)<(value.length()) && // invalidates "open."
		 Character.isJavaIdentifierStart(value.charAt(value.length()-1)) //invalidates "open.#state"
	       ) {
		valid = true;
	    }
	}
	if (!valid) {
	    throw new java.lang.IllegalArgumentException("State \""+value+"\" is not a valid state.");
	} else {
	    setDirtyAttribute(STATE);
	    this.state = value;
	}
    }
    
		/**
		 * Client definable date, when the service activation should take place,
		 * <p>
		 * Issue: Do we require some internal activities in the server? Which?
		 *
		 * @see #getrequestedCompletionDate
		 */
    public void setRequestedCompletionDate(Date date) {
	setDirtyAttribute(OrderValue.REQUESTED_COMPLETION_DATE);
	requestedCompletionDate = date;
    }
		/**
		 * @see #setrequestedCompletionDate
		 */
    public Date getRequestedCompletionDate() {
	checkAttribute(REQUESTED_COMPLETION_DATE);
	return requestedCompletionDate;
    }
    
		/**
		 * Date, when the order reached the state CLOSED,
		 * <p>
		 * This is an output attribute, it is set by the implementation.
		 * The client cannot change it by calling the OrderManager.
		 *
		 * @see #getActualCompletionDate
		 */
    public void setActualCompletionDate(Date date) {
	setDirtyAttribute(OrderValue.ACTUAL_COMPLETION_DATE);
	actualCompletionDate = date;
    }
		/**
		 * @see #setActualCompletionDate
		 */
    public Date getActualCompletionDate() {
	checkAttribute(ACTUAL_COMPLETION_DATE);
	return actualCompletionDate;
    }
    
		/**
		 * Date when the order was received from the customer.
		 * <p>
		 * This is set by the client to the date,
		 * when the order has been received by the customer care agent.
		 *
		 * @see #getOrderDate
		 */
    public void setOrderDate(Date date) {
	setDirtyAttribute(OrderValue.ORDER_DATE);
	orderDate = date;
    }
    
		/**
		 * @see #setOrderDate
		 */
    public Date getOrderDate() {
	checkAttribute(ORDER_DATE);
	return orderDate;
    }
    
    public java.lang.String getPurchaseOrder() {
	checkAttribute(PURCHASE_ORDER);
	return purchaseOrder;
    }
    
    public void setPurchaseOrder(java.lang.String purchaseOrder) {
	setDirtyAttribute(PURCHASE_ORDER);
	this.purchaseOrder = purchaseOrder;
    }
    
    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
 */
    public void setManagedEntityKeyDummy(javax.oss.ManagedEntityKey managedEntityKeyDummy) {
	if (managedEntityKeyDummy instanceof OrderKey) {
	    super.setManagedEntityKeyDummy(managedEntityKeyDummy);
	}
    }

    public OrderKey getOrderKey(){
	checkAttribute(KEY);
	return (OrderKey)managedEntityKey;
    }
    
    public ManagedEntityKey getManagedEntityKey(){
	return getOrderKey();
    }
    
    public void setOrderKey(OrderKey managedEntityKey) {
	setDirtyAttribute(KEY);
	this.managedEntityKey = managedEntityKey;
	this.tempOrderType = null;
    }
    
    public void setManagedEntityKey( ManagedEntityKey managedEntityKey) {
	if (managedEntityKey instanceof OrderKey) {
	    setOrderKey((OrderKey)managedEntityKey);
	} else throw new java.lang.IllegalArgumentException("Not the correct type of key");
    }
    
   /** 
    * Make a new key for this value object.
    * This method is equivalent to {@link javax.oss.ManagedEntityValue#makeManagedEntityKey makeManagedEntityKey}
    * <p>
    * <p><b>Postcondition:</b>
    * <ul><li><code>result.getPrimarykey() == null</code></li>
    *	  <li><code>this instanceof Class.forName(result.getType())</code></li>
    * </ul>
    *
    * @see #setOrderKey
    */	  
    public OrderKey makeOrderKey() {
	return (OrderKey)makeManagedEntityKey();
    }

    
    /** Returns the optional attributes, that are supported by this order. <p>
     * In this API, two attributes are optional and by using this method,
     * a client can verify to what extend the attributes are supported:
     * <ul>
     * <li>PRIORITY: See {@link #setPriority} for details.. 
     * </li>
     * <li>SERVICES: See {@link #setServices} and {@link #setFailedServices} for details.
     * </li>
     * <p><b>Postcondition:</b>
     * <ul><li>At least an empty array must be returned, in case no option is supported:<br>
     *	       <code>result != null</code></li>
     *	   <li>All returned strings must be valid attribute names:<br>
     *	       <code>forall a in result: a!=null && a in getAttributeNames()</code></li>
     * </ul>
     * @return An array of attribute names that are supported by the back-end.
     */
    public String[] getSupportedOptionalAttributes() {
	return new String[] { PRIORITY, SERVICES };
    }

    /**
     * Returns a deep copy of this value
     *
     * Subclasses of ManagedEntity should call this method first, which calls ManagedEntityValueImpl.clone() itself to create 
     * a shallow copy first, and then hande all properties which have to be deeply cloned.
     * @return deep copy of this Event
     */
    public Object clone() {

	// first call clone in Object
	Object myClone = super.clone();

	// then update all fields which are only a shallow copy
	OrderValueImpl anOvImplClone = (OrderValueImpl)myClone;

	if (requestedCompletionDate!=null) anOvImplClone.requestedCompletionDate = (Date)requestedCompletionDate.clone();
	if (actualCompletionDate!=null) anOvImplClone.actualCompletionDate = (Date)actualCompletionDate.clone();
	if (orderDate!=null) anOvImplClone.orderDate = (Date)orderDate.clone();
	if (managedEntityKey!=null) anOvImplClone.managedEntityKey = (ManagedEntityKey)managedEntityKey.clone();
	if (services!=null) {
	    anOvImplClone.services = new ServiceValue[services.length];
	    for (int i=0; i<services.length; i++) {
		anOvImplClone.services[i] = (ServiceValue)services[i].clone();
	    }
	}
	// failed services need not to be cloned, ManagedEntityKeyResult is not cloneable anyway

	return anOvImplClone;
    }

}
