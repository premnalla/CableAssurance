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

import java.io.Serializable;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderEventPropertyDescriptorImpl 
//VP extends java.lang.Object //VP see bug id 6193340
//VP 2 extends PortableRemoteObject
//VP implements javax.oss.order.OrderEventPropertyDescriptor, java.rmi.Remote {
implements javax.oss.order.OrderEventPropertyDescriptor, Serializable {

    String type;
    String[] propertyNames;
    String[] propertyTypes;
    javax.oss.Event dummyEvent;
    
    /** Creates new OrderEventPropertyDescriptorImpl */
    public OrderEventPropertyDescriptorImpl(String type, String[] properties, String[] propertyTypes, javax.oss.Event dummyEvent) 
		throws java.rmi.RemoteException
	{
		//VP 
		//VP super();

        this.type = type;
        this.propertyNames = propertyNames;
        this.propertyTypes = propertyTypes;
        this.dummyEvent = dummyEvent;

    }

    /**
     * Get the name of the event type
     * associated with that descriptor.
     *
     * @return Fully qualified name of the Event interface
     * associated with that Event descriptor.
 */
    public String getEventType() {
        return type;
    }
    
    /**
     * Get the names of the filterable properties
     * associated with an Event.
     *
     * @return String array of <i>XXX</i>_PROP_NAME's
 */
    public String[] getPropertyNames() {
        return propertyNames;
    }
    
    /**
     * Get the types for the filterable properties
     * associated with an Event.
     *
     * @return String array of <i>XXX</i>_PROP_TYPE's
 */
    public String[] getPropertyTypes() {
        return propertyTypes;
    }
    
    /**
     * Factory for Event associated with
     * that Event Descriptor.
     *
     * @return An implementation of the Event interface.
 */
    public javax.oss.Event makeEvent() {
        return dummyEvent;
    }
    
}
