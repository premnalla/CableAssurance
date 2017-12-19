/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.cbe.measurement;

import javax.oss.ManagedEntityValue;
import javax.oss.cbe.CBEManagedEntityValue;
import javax.oss.cbe.EntityValue;
import javax.oss.cbe.measurement.PerformanceMonitorValue;
import javax.oss.cbe.measurement.PerformanceMonitorValueIterator;


/**
 * PerformanceMonitorValueIterator as dependent object when statefull session bean is used
 * this is the object that sjhall be return to the client
 * @see ossj.common.cbe.measurement.PerformanceMonitorValueIteratorBean
 * @see ossj.common.cbe.measurement.PerformanceMonitorValueIteratorIF
 * @see ossj.common.cbe.measurement.PerformanceMonitorValueIteratorHome
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class PerformanceMonitorValueIteratorDepEjbImpl implements PerformanceMonitorValueIterator {
    protected PerformanceMonitorValueIteratorIF iter;
    
    /** block default Ctor */
    private PerformanceMonitorValueIteratorDepEjbImpl() {
    }
    
    /** Creates a new instance of Class */
    public PerformanceMonitorValueIteratorDepEjbImpl(PerformanceMonitorValueIteratorIF iter) {
        this.iter = iter;
    }
    
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
    throws java.rmi.RemoteException {
        return (iter.getNextPerformanceMonitors(howMany));
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param howMany DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     */
    public EntityValue[] getNextEntities(int howMany) throws java.rmi.RemoteException {
        return (iter.getNextEntities(howMany));
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param howMany DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     */
    public CBEManagedEntityValue[] getNextCBEManagedEntities(int howMany) throws java.rmi.RemoteException {
        return (iter.getNextCBEManagedEntities(howMany));
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param howMany DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     */
    public ManagedEntityValue[] getNext(int howMany) throws java.rmi.RemoteException {
        return (iter.getNext(howMany));
    }
    
    /**
     * DOCUMENT ME!
     *
     * @throws java.rmi.RemoteException DOCUMENT ME!
     * @throws javax.ejb.RemoveException DOCUMENT ME!
     */
    public void remove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
        iter.remove();
    }
}
