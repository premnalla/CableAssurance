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

import javax.oss.service.ServiceKey;
import javax.oss.ManagedEntityValue;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

import javax.oss.service.*;

import com.nokia.oss.ossj.sa.ri.order.OrderLocal;

import java.util.*;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public interface ServiceLocal extends EJBLocalObject, java.io.Serializable {

    // all attributes are updated
    public void setAttributes(RiServiceValue serviceValue);

    // only dirty attributes are updated
    public void updateAttributes(RiServiceValue orderValue);
    
    // read attributes and return populated RiServiceValue
    public RiServiceValue getAttributes(String[] attributes);

    public ServiceKey getServiceKey();

    // Remote-CMP methods
    
    public java.util.Date getLastModified();
    
    /** Set the state the service should have after order has been executed.
     * @param state see {@link javax.oss.service.ServiceState}
     */    
    public void setState(String newValue);

    /** See {@link #setState }
     * @return the state of the service
     */    
    public String getState(); // Business method
    
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
    public void setSubscriberId(String subscriber);
    
    /**
     * Get the subscriber who is using this service.
     * @see #setSubscriberId
     * @return the subscriber Identification.
     */
    public String getSubscriberId();

    public void setServiceActivatorHomeJndiName(String newValue);
    public String getServiceActivatorHomeJndiName();
    
    public void setPositionInOrderValueArray(int position);
    public int getPositionInOrderValueArray();
    
    public void setAdditionalAttributes(TreeMap additionalAttribtues);
    public TreeMap getAdditionalAttributes();
    
    public void setFailed(boolean failed);
    public boolean isFailed();
    
    public void setFailedException(Exception exception);
    public Exception getFailedException();
    
//    // Remote-CMR methods
//    
//    public void setOrder(Order anOrder);
//    public Order getOrder();
//    

}
