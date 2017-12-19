/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.ejb.EJBObject;

import javax.oss.ManagedEntityValue;
import javax.oss.cbe.EntityValue;
import javax.oss.cbe.measurement.PerformanceMonitorValue;


/**
 * PerformanceMonitorValueIterator Interface
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface PerformanceMonitorValueIteratorIF extends EJBObject {
    /**
     * DOCUMENT ME!
     *
     * @param howMany DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     */
    public PerformanceMonitorValue[] getNextPerformanceMonitors(int howMany)
        throws java.rmi.RemoteException;

    /**
     * DOCUMENT ME!
     *
     * @param howMany DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     */
    public ManagedEntityValue[] getNext(int howMany) throws java.rmi.RemoteException;

    /**
     * Returns the next specified number of elements in the iteration.
     * @param howMany
     * @return
     * @throws java.rmi.RemoteException
     * @see javax.oss.EntityValueIterator#getNextEntities(int)
     */
    public EntityValue[] getNextEntities(int howMany) throws java.rmi.RemoteException;

    /**
     * Returns the next specified number of elements in the iteration.
     * @param howMany
     * @return
     * @throws java.rmi.RemoteException
     * @see javax.oss.EntityValueIterator#getNextEntities(int)
     */
    public EntityValue[] getNextCBEManagedEntities(int howMany) throws java.rmi.RemoteException;
}
