/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

import javax.ejb.EJBLocalHome; // EJB2.0 required!
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.pm.threshold.ThresholdMonitorValue;
import javax.oss.pm.measurement.PerformanceMonitorKey;

/**
 * This is the local home inteface for ThresholdMonitorEntity. This interface is
 * implemented by the EJB container. The implemented object is called the local
 * home object, and serves as a factory for EJB local objects. The create()
 * method corresponds to the ejbCreate() method in the bean class.
 *
 * @author Henrik Lindstrom, Ericsson
 * @see ossj.qos.ri.pm.threshold.adapter.ThresholdMonitorEntityBean
 */
public interface ThresholdMonitorEntityLocalHome extends EJBLocalHome {
    /**
     * Creates the ThresholdMonitorEntity
     */
    ThresholdMonitorEntityLocal create( ThresholdMonitorKey key,
                                        ThresholdMonitorValue value,
                                        PerformanceMonitorKey performanceMonitorKey )
                                        throws CreateException;

    // Finder metods. These are implemented by the container. It is possible to
    // customize the functionality of these methods in the deployment descriptor
    // through EJB-QL and container tools.

    public ThresholdMonitorEntityLocal findByPrimaryKey( String key )
        throws FinderException;

    public Collection findAllThresholds() throws FinderException;

    public Collection findThresholdsWithPerformanceMonitorPrimaryKey( String key ) throws FinderException;

}