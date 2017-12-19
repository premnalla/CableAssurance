/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.ejb.EJBLocalObject; // EJB2.0 required!

import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.measurement.PerformanceMonitorKey;

/**
 * These are the local business methods of ThresholdMonitorEntityBean. This
 * local interface is what local clients operate on when they interact with the
 * bean. The container will implement this interface; the implemented object is
 * called the EJB local object, which delegates invocations to instances of the
 * entity bean class.
 *
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorEntityBean
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorBean
 * @author Henrik Lindstrom, Ericsson
 */
public interface ThresholdMonitorEntityLocal extends EJBLocalObject {
	public String getThresholdMonitorPrimaryKey();
    public ThresholdMonitorKey getThresholdMonitorKey();
    public ThresholdMonitorValue getThresholdMonitorValue();
    public void setThresholdMonitorValue( ThresholdMonitorValue value );
    public int getThresholdMonitorStatus();
    public void setThresholdMonitorStatus( int status );
    public String getPerformanceMonitorPrimaryKey();
    public void setPerformanceMonitorPrimaryKey( String key );
    public void setPerformanceMonitorKey( PerformanceMonitorKey key );
    public PerformanceMonitorKey getPerformanceMonitorKey();
    public String getThresholdAlarmPrimaryKey();
    public void setThresholdAlarmPrimaryKey(String primaryKey);
    public long getThresholdAlarmNotificationId();
    public void setThresholdAlarmNotificationId( long id );
}