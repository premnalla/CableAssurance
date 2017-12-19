

/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

// rmi import
import java.rmi.RemoteException;

// ejb import
import javax.ejb.EJBObject;

import java.util.Vector;

// api import

import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.measurement.PerformanceMonitorKey;


/**
 * These are the public business mehtods of the ThresholdMonitorEntityBean. This
 * remote interface is what remote clients operate on when they interact with
 * beans. The EJB container will implement this interface; the implemented object
 * is called the EJB Object, which delegates invocations to instances of the
 * entity bean class.
 *
 * @author Henrik Lindstrom, Ericsson
 */
public interface ThresholdMonitorEntity extends EJBObject {
    public String getThresholdMonitorPrimaryKey() throws RemoteException;
    public ThresholdMonitorKey getThresholdMonitorKey() throws RemoteException;
    public ThresholdMonitorValue getThresholdMonitorValue() throws RemoteException;
    public void setThresholdMonitorValue( ThresholdMonitorValue value ) throws RemoteException;
    public int getThresholdMonitorStatus() throws RemoteException;
    public void setThresholdMonitorStatus( int status )  throws RemoteException;
    public String getPerformanceMonitorPrimaryKey() throws RemoteException;
    public void setPerformanceMonitorPrimaryKey( String key ) throws RemoteException;
    public void setPerformanceMonitorKey( PerformanceMonitorKey key ) throws RemoteException;
    public PerformanceMonitorKey getPerformanceMonitorKey() throws RemoteException;
    public String getThresholdAlarmPrimaryKey() throws RemoteException;
    public void setThresholdAlarmPrimaryKey(String primaryKey) throws RemoteException;
    public long getThresholdAlarmNotificationId() throws RemoteException;
    public void setThresholdAlarmNotificationId( long id ) throws RemoteException;
}