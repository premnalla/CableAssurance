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
package com.nokia.oss.ossj.sa.ri.service;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;
import com.nokia.oss.ossj.common.ri.*;
import java.util.Map;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class RiServiceValueImpl extends ManagedEntityValueImpl implements RiServiceValue, java.io.Serializable {

    private static AttributeManager serviceAttributeManager;
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
        STATE, SUBSCRIBER_ID,
        SERVICE_ACTIVATOR_HOME_JNDI_NAME
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames = {
        /*STATE,*/ SUBSCRIBER_ID, 
        SERVICE_ACTIVATOR_HOME_JNDI_NAME
    };
    
    private String state;
    private ManagedEntityKey managedEntityKey;
    private String serviceActivatorHomeJndiName;
    private String subscriberId;
    
    private String tempServiceType;
    
    // Array of Classes which contains attribute classes in same order as attributeNames
    private static Map attributeClasses;
    
    /** Creates new ServiceValueImpl */
    public RiServiceValueImpl() {
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
        return serviceAttributeManager;
    }

    protected AttributeManager makeAttributeManager() {
        serviceAttributeManager = new AttributeManager();
        return serviceAttributeManager;
    }

    public String getServiceType() {
        if (!isPopulated(KEY)) {
            return tempServiceType;
        }
        return managedEntityKey.getType();
    }
    
    public void setServiceType(String value) {
        if (!isPopulated(KEY)) {
            tempServiceType=value;
        }
    }
    
    public String getState(){
        checkAttribute(STATE);
        return state;
    }

    // service states sorted from most derived state to the most simple - important for for-loop in setState
    public static final String[] serviceStates = 
    {
        ServiceState.ACTIVE,
        ServiceState.INACTIVE
    };
    
    public void setState(String value) {
        if ( value == null ) {
            throw new java.lang.IllegalArgumentException("State \""+value+"\" is null.");
        }
        boolean valid = false;
        // first check if the new state is one of the default states in OrderState - should be the case 80%!
        for (int i=0 ; i<serviceStates.length && !valid ; i++) {
            if ( value.equals(serviceStates[i]) ) {
                valid = true;
            }
        }
        // if the prior test failed, value still can be a substate
        for (int i=0 ; i<serviceStates.length && !valid; i++) {
            if ( value.startsWith(serviceStates[i]+".") && // invalidates "openx"
                 (serviceStates[i].length()+1)<(value.length()) && // invalidates "open."
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
    
    public void setServiceActivatorHomeJndiName(String newName) {
        setDirtyAttribute(SERVICE_ACTIVATOR_HOME_JNDI_NAME);
        serviceActivatorHomeJndiName = newName;
    }
    
    public String getServiceActivatorHomeJndiName() {
        checkAttribute(SERVICE_ACTIVATOR_HOME_JNDI_NAME);
        return serviceActivatorHomeJndiName;
    }
    
    
    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
 */
    public void setManagedEntityKeyDummy(javax.oss.ManagedEntityKey managedEntityKeyDummy) {
        if (managedEntityKeyDummy instanceof ServiceKey) {
            super.setManagedEntityKeyDummy(managedEntityKeyDummy);
        }
    }

    public void setServiceKey(javax.oss.service.ServiceKey managedEntityKey) {
        setDirtyAttribute(KEY);
        this.managedEntityKey = managedEntityKey;
        this.tempServiceType = null;
    }
    
    public ServiceKey getServiceKey() {
        checkAttribute(KEY);
        return (ServiceKey)managedEntityKey;
    }
    
    public ManagedEntityKey getManagedEntityKey(){
        return getServiceKey();
    }
    
    public void setManagedEntityKey( ManagedEntityKey managedEntityKey ){
        if (managedEntityKey instanceof ServiceKey) {
            setServiceKey((ServiceKey)managedEntityKey);
        }
    }

    public ServiceKey makeServiceKey() {
        return (ServiceKey)makeManagedEntityKey();
    }

    /**
     * Set the subscriber who is using this service.
     * <p>
     * This is an indication which subscriber or customer is using
     * the service. The format of the string is not specified.
     * However, it is expected that the string will be reference
     * to a customer or subscriber database entry.
     * <p>
     * @see #getSubscriberId
     * @param subscriber A string identifying the subscriber.
     */
    public void setSubscriberId(String subscriber) {
        setDirtyAttribute(SUBSCRIBER_ID);
        subscriberId = subscriber;
    }
    
    /**
     * Get the subscriber who is using this service.
     * @see #setSubscriberId
     * @return the subscriber Identification.
     */
    public String getSubscriberId() {
        checkAttribute(SUBSCRIBER_ID);
        return subscriberId;
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
        RiServiceValueImpl anRiSvImplClone = (RiServiceValueImpl)myClone;

        if (managedEntityKey!=null) anRiSvImplClone.managedEntityKey = (ManagedEntityKey)managedEntityKey.clone();

        return anRiSvImplClone;
    }
}
