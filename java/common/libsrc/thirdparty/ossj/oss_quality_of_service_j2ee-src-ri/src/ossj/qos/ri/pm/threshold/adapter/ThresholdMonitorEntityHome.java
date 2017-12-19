/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

import java.rmi.RemoteException;
import java.util.Collection;

// api imports
import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.measurement.PerformanceMonitorKey;

/**
 * This is the home interface for the ThresholdMonitorEntityBean. This interface
 * is implemented by the EJB container. The implemented object is called the home
 * object, and serves as a factory for EJB objects. The create() method is in
 * this interface corresponds to the ejbCreate() method in the bean class.
 *
 * @author Henrik Lindstrom, Ericsson
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorEntityBean
 */
public interface ThresholdMonitorEntityHome extends EJBHome {

    /**
     * Creates the ThresholdMonitorEntityBean.
     * @param key the primary key for threshold monitor
     * @param value the threshold monitor value
     * @return the created EJB object
     */
    ThresholdMonitorEntity create( ThresholdMonitorKey key,
                                   ThresholdMonitorValue value,
                                   PerformanceMonitorKey performanceMonitorKey )
        throws CreateException, RemoteException;

    // Finder metods. These are implemented by the container. It is possible to
    // customize the functionality of these methods in the deployment descriptor
    // through EJB-QL and container tools.

    /**
     * Find a threshold using the primary key. The primary key is
     * <code>ThresholdMonitorKey.getThresholdMonitorPrimaryKey()</code>.
     * @param key the primary key
     */
    public ThresholdMonitorEntity findByPrimaryKey( String key )
        throws FinderException, RemoteException;

    /**
     * Find all thresholds.
     */
    public Collection findAllThresholds() throws FinderException, RemoteException;

    /**
     * Find all thresholds that have the performance monitor primary key.
     */
    public Collection findThresholdsWithPerformanceMonitorPrimaryKey( String key ) throws FinderException, RemoteException;

}