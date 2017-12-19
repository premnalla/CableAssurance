/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorValue;
/**
 * PerformanceMonitorValueIteratorHome Interface
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface PerformanceMonitorValueIteratorHome extends javax.ejb.EJBHome
{
    public PerformanceMonitorValueIteratorIF create(PerformanceMonitorValue[] values)
           throws javax.ejb.CreateException,
                  java.rmi.RemoteException;
}
