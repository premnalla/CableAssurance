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

// OSS/J imports

import javax.oss.*;
import javax.oss.service.*;
import javax.oss.order.*;

// RI imports

import com.nokia.oss.ossj.common.ri.*;
import com.nokia.oss.ossj.sa.ri.*;
import com.nokia.oss.ossj.sa.ri.service.*;
import com.nokia.oss.ossj.sa.ri.order.*;

// Utility imports

import java.util.*;

// Expetion imports

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public abstract class ServiceBean extends TimestampedMEV {

    // additional CMP-Key field
    public abstract void setAdditionalAttributesCmp(TreeMap additionalAttribtues);
    public abstract TreeMap getAdditionalAttributesCmp();
    
    public abstract void setFailedCmp(boolean failed);
    public abstract boolean getFailedCmp();
    
    public abstract void setFailedExceptionCmp(Exception exception);
    public abstract Exception getFailedExceptionCmp();
    
    public abstract void setOrderPrimaryKey(String newValue);
    public abstract String getOrderPrimaryKey();

    public abstract void setPositionInOrderValueArrayCmp(int position);
    public abstract int getPositionInOrderValueArrayCmp();
    
    public abstract void setServiceActivatorHomeJndiNameCmp(String  newValue);
    public abstract String getServiceActivatorHomeJndiNameCmp();
    
    public abstract void setStateCmp(String newValue);
    public abstract String getStateCmp();
    
    public abstract void setSubscriberIdCmp(String newValue);
    public abstract String getSubscriberIdCmp();
    
    public abstract void setOrderCmr(OrderLocal anOrder);
    public abstract OrderLocal getOrderCmr();
    
    public CMPServiceDeltaKey ejbCreate(RiServiceValueImpl mev, String anOrderPrimaryKey) throws CreateException {
        super.create(mev);
        
        // see comment in super.ejbCreate
        ManagedEntityKey key = mev.getManagedEntityKey();
        //WLS6.0sp2 error
        //java.lang.IllegalStateException: The setXXX method for a primary key field may only be called during ejbCreate.
        mev.unpopulateAttribute(javax.oss.ManagedEntityValue.KEY);
        
        //set all attributes beside key
        setAttributes(mev);
        
        //set CMP primary key
        setMevPrimaryKey((String)key.getPrimaryKey());
        setOrderPrimaryKey(anOrderPrimaryKey);

        //set other stuff from mev-primary key
        setMevType(key.getType());
        
        //re-set key in orderValue
        mev.setManagedEntityKey(key);
        
        return null;
    }
    
    public void ejbPostCreate(RiServiceValueImpl mev, String anOrderPrimaryKey) throws CreateException {
        //VP workaround bug id 4149119
		//super.ejbPostCreate(mev);
        super.my_ejbPostCreate(mev);
    }
    
    public void ejbStore() {
        super.ejbStore();
    }
    // Business Methods
    
    public void setAttributes(RiServiceValue aServiceValue) {
        String[] populatedAttributes = aServiceValue.getPopulatedAttributeNames();
        for (int i=0 ; i < populatedAttributes.length; i++) {
            try {
                setAttribute(populatedAttributes[i], aServiceValue);
                
            } catch (java.lang.IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }
    
    public void updateAttributes(RiServiceValue aServiceValue) {
        String[] attributes = null;
        if (aServiceValue instanceof RiServiceValueImpl) {
            attributes = ((RiServiceValueImpl)aServiceValue).getDirtyAttributeNames();
        } else {
            attributes = aServiceValue.getPopulatedAttributeNames();
        }
        for (int i=0 ; i < attributes.length; i++) {
            try {
                setAttribute(attributes[i], aServiceValue);
            } catch (java.lang.IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }
    
    public void setAttribute(String attributeName, RiServiceValue aServiceValue) {
        try {
            if (attributeName.equals(ServiceValue.KEY)){
                return;
                // neither primary key nor type should be rewritten to the service
            } else {
                GenericPropertyUtil.setAttributeValue(this, attributeName, aServiceValue.getAttributeValue(attributeName));
            }
        } catch (java.lang.IllegalArgumentException iae) {
            // put all unkown attributes (i.e. extentions to the RI!) in a map
            setAdditionalAttribute(attributeName, aServiceValue.getAttributeValue(attributeName));
        }
    }
    
    protected void setAdditionalAttribute(String attributeName, Object newValue) {
        TreeMap additionalAttributes = getAdditionalAttributes();
        if (additionalAttributes == null) {
            additionalAttributes = new TreeMap();
        }
        additionalAttributes.put(attributeName, newValue);
        punchTime();
        if (!additionalAttributes.isEmpty()) {
            setAdditionalAttributes(additionalAttributes);
        }
    }

    public RiServiceValue getAttributes(String[] requestedAttributes) {
        try {
            RiServiceValue aServiceValue = (RiServiceValue)Class.forName(getMevType()+"Impl").newInstance();

            if (requestedAttributes == null || requestedAttributes.length==0) {
                requestedAttributes = aServiceValue.getAttributeNames();
            }

            Map additionalAttributes = getAdditionalAttributes();
            for (int i=0; i<requestedAttributes.length; i++) {
            try {
                if (requestedAttributes[i].equals(ServiceValue.KEY)) {
                    aServiceValue.setServiceKey(getServiceKey());
                } else {
                    Object newValue = null;
                    boolean initiated = false;
                    if (additionalAttributes!=null && additionalAttributes.containsKey(requestedAttributes[i])) {
                        newValue = getAdditionalAttributes().get(requestedAttributes[i]);
                        initiated = true;
                    } else {
                        try {
                            newValue = GenericPropertyUtil.getAttributeValue(this, requestedAttributes[i]);
                            initiated = true;
                        } catch (java.lang.IllegalArgumentException iae) {
                        } catch (java.lang.IllegalStateException ise) {
                        }
                    }
                    if (initiated) {
                        aServiceValue.setAttributeValue(requestedAttributes[i], newValue);
                    }
                }
            } catch (java.lang.IllegalArgumentException iae) {
                throw new EJBException("illegal value stored in database", iae);
            }
        }
        
        if (aServiceValue instanceof ManagedEntityValueImpl) {
            ((ManagedEntityValueImpl)aServiceValue).cleanAttributes();
        }
        
        return aServiceValue;
        
        // no exceptions should happen, because there always is a Class instanciable from interfacename+Impl
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        } catch (InstantiationException ie) {
            System.out.println(ie);
        } catch (IllegalAccessException iae) {
            System.out.println(iae);
        } 
        return null;
    }
    
    public ServiceKey getServiceKey() {
        return new ServiceKeyImpl(  ApplicationContextImpl.getApplicationContext(getNamingContext()), 
                                    getApplicationDN(), 
                                    getMevType(), 
                                    (String)getMevPrimaryKey());
    }
    
    // Remote Wrapper methods for CMP/CMR fields
    // CMP/CMR should not be exposed in Remote interface
    
    public void setState(String newValue){
        punchTime();
        setStateCmp(newValue);
    }
    public String getState() {
        return getStateCmp();
    }
    
    public void setSubscriberId(String newValue){
        punchTime();
        setSubscriberIdCmp(newValue);
    }
    public String getSubscriberId() {
        return getSubscriberIdCmp();
    }
    
    public void setServiceActivatorHomeJndiName(String  newValue) {
        punchTime();
        setServiceActivatorHomeJndiNameCmp(newValue);
    }
    public String getServiceActivatorHomeJndiName() {
        return getServiceActivatorHomeJndiNameCmp();
    }
    
    public void setPositionInOrderValueArray(int position) {
        punchTime();
        setPositionInOrderValueArrayCmp(position);
    }
    
    public int getPositionInOrderValueArray() {
        return getPositionInOrderValueArrayCmp();
    }
    
    public void setAdditionalAttributes(TreeMap additionalAttribtues) {
        punchTime();
        setAdditionalAttributesCmp(additionalAttribtues);
    }
    public TreeMap getAdditionalAttributes() {
        return getAdditionalAttributesCmp();
    }
    
//    public void setOrder(OrderLocal anOrder) {
//        punchTime();
//        setOrderCmr(anOrder);
//    }
//
//    public OrderLocal getOrder() {
//        return getOrderCmr();
//    }
//
    public void setFailed(boolean failed) {
        punchTime();
        setFailedCmp(failed);
    }
    
    public boolean isFailed() {
        return getFailedCmp();
    }
    
    
    public void setFailedException(Exception exception){
        punchTime();
        setFailedExceptionCmp(exception);
    }
    
    public Exception getFailedException(){
        return getFailedExceptionCmp();
    }
    
    
    
}
