
/**
 * Copyright © 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.nec.oss.ipb.producer.ri;

/**
 * Standard imports
 */
import java.rmi.RemoteException;

/**
 * OSS/J Interface imports.
 */
import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.producer.ProducerPrimaryKey;
import javax.oss.ipb.producer.ProducerValue;
import javax.oss.ipb.producer.MediationCapability;
import javax.oss.cfi.activity.ActivityControlParams;
import javax.oss.cfi.activity.ActivityExecParams;
import javax.oss.cfi.activity.ActivityReportParams;
import javax.oss.cfi.activity.SubscriptionParams;
import javax.oss.cfi.activity.Schedule;


/**
 * RI imports.
 */

/**
 * The base interface for the business methods of a Producer Entity.
 * <p>
 * This remote interface is what remote clients operate on when
 * they interact with beans. The EJB container will implement
 * this interface; the implemented object is called the EJB Object,
 * which delegates invocations to instances of the
 * entity bean class.
 * <p>
 * This represents the persistent managed entity bean remote interface
 * in the backend for the client facing ProducerValue object.
 * This entity can include more implementation attributes and operations
 * than the javax.oss.ipb.producer.ProducerValue interface specifies.
 * <p>
 * An OSS/J client only interacts thru' the JVTProducerSession interface,
 * and can never access this interface directly. Therefore, it is not
 * necessary to make this interface available to the javax.oss. package
 * hierarchy.
 *
 * <p>
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */

public interface ProducerEntity
extends javax.ejb.EJBObject
{

    public String getActivityName() throws RemoteException;
    public void setActivityName(String producerName)
        throws RemoteException;

    public String getActivityId() throws RemoteException;

    public String getProducerType() throws RemoteException;

    public MediationCapability[] getMediationCapabilityValues()
        throws RemoteException;
    public void setMediationCapabilityValues(MediationCapability[] medCaps)
        throws RemoteException;
       
    public int getControlState()
        throws RemoteException;
    public void setControlState(int controlState)
        throws RemoteException;
       
    public int getExecutionStatus()
        throws RemoteException;
    public void setExecutionStatus(int executionStatus)
        throws RemoteException;
       
    public ActivityControlParams getActivityControlParams()
        throws RemoteException;
    public void setActivityControlParams(
						ActivityControlParams activityControlParams)
        throws RemoteException;
       
    public ActivityExecParams getActivityExecParams()
        throws RemoteException;
    public void setActivityExecParams(ActivityExecParams activityExecParams)
        throws RemoteException;
       
    public ActivityReportParams[] getActivityReportParams()
        throws RemoteException;
    public void setActivityReportParams(
						ActivityReportParams[] activityReportParams)
        throws RemoteException;
       
    public SubscriptionParams getSubscriptionParams()
        throws RemoteException;
    public void setSubscriptionParams(SubscriptionParams subscriptionParams)
        throws RemoteException;
       
    public Schedule getSchedule()
        throws RemoteException;
    public void setSchedule(Schedule schedule)
        throws RemoteException;
       
    /**
     * Getter and Setter methods for ProducerValue representation.
     */
    
    /**
     * Gets a ProducerValue object with only the specified attributes
     * populated.
     */
    public ProducerValue getAttributes(String[] attributes)
        throws RemoteException;

    /**
     * Sets the specified attributes taken from ProducerValue object.
     */
    public void setAttributes(
        String[] attributes,
        ProducerValue value)
        throws RemoteException;

    /**
     * Sets all the attributes taken from ProducerValue object.
     */
    public void setAttributes(ProducerValue value)
        throws RemoteException;

    /**
     * Only dirty attributes from ProducerValue are updated.
     * Returns attributes which really changed.
     */
    public String[] updateAttributes(ProducerValue value)
        throws RemoteException;

    /**
     * Checks MediationCapability from ProducerValue against the input 
     * @return true if match, false if different
     */
    public boolean checkMediationCapabilityForMatch(
										MediationCapability[] template)
        throws RemoteException;

    /**
     * Checks all attributes from ProducerValue against the input template
     * @return true if match, false if different
     */
    public boolean checkAllAttributesForMatch(ProducerValue template)
        throws RemoteException;
}