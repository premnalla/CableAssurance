/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.oss.ManagedEntityValue;
import javax.oss.cbe.EntityValue;
import javax.oss.cbe.measurement.PerformanceMonitorValue;
import javax.oss.cbe.measurement.PerformanceMonitorValueIterator;


/**
 * PerformanceMonitorValueIterator Bean Implementation Class
 *
 * Usage:<BR>
 * in the following the xx represent for example the AlarmValue
 * <code>
 *
 * Class myEjbBean {
 *
 * ...
 * xxxIterator gethhhhhh() {
 *
 *     xxxIteratorHome iterHome = null;
       try
       {
           iterHome = (xxxIteratorHome)
               initCtx.lookup("java:comp/env/ejb/xxxIteratorHome");
       }
       catch (NamingException nex)
       {
           nex.printStackTrace();
       }

       xxx myValues[] = null;

       // get the whole Value[] here

       xxxIterator retIter = null;
       xxxIteratorIF ejbIter = null;
       try
       {
          ejbIter = iterHome.create(myValues);
          retIter = new xxxIteratorDepEjbImpl(ejbIter);
       }
       catch (CreateException cex){
          throw new EJBException("CreateException" + cex.getMessage());
       }
       catch (RemoteException rex){
           throw new EJBException("RemoteException" + rex.getMessage());
       }
       return retIter;
   }
   </code>
 *
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class PerformanceMonitorValueIteratorBean implements SessionBean {
    private javax.oss.cbe.measurement.PerformanceMonitorValueIterator iter = null;
    private SessionContext ctx;

    //----------------------------------------------------------------
    // Container callbacks
    //----------------------------------------------------------------
    public void ejbCreate(PerformanceMonitorValue[] values)
        throws CreateException, EJBException {
        iter = new PerformanceMonitorValueIteratorImpl(values);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     */
    public void ejbActivate() throws RemoteException {
    }

    /**
     * DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     */
    public void ejbPassivate() throws RemoteException {
    }

    /**
     * DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     */
    public void ejbRemove() throws RemoteException {
        try {
            iter.remove();
        } catch (javax.ejb.RemoveException rex) {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param sContext DOCUMENT ME!
     *
     * @throws RemoteException DOCUMENT ME!
     */
    public void setSessionContext(SessionContext sContext)
        throws RemoteException {
        this.ctx = sContext;
    }

    /**
     * get "howMany" subsequent ManagedEntityValue objects
     *
     * @param howMany how many ManagedEntityValue objects the client wishes to retrieve
     * @return ManagedEntityValue array
     * @exception javax.ejb.EJBException
     */
    public ManagedEntityValue[] getNext(int howMany) throws javax.ejb.EJBException {
        try {
            return iter.getNext(howMany);
        } catch (java.rmi.RemoteException rex) {
            throw new javax.ejb.EJBException(rex.getMessage());
        }
    }

    /**
     * get "howMany" subsequent PerformanceMonitorValue objects
     *
     * @param howMany how many PerformanceMonitorValue objects the client wishes to retrieve
     * @return PerformanceMonitorValue array
     * @exception javax.ejb.EJBException
     */
    public PerformanceMonitorValue[] getNextPerformanceMonitors(int howMany)
        throws javax.ejb.EJBException {
        try {
            return iter.getNextPerformanceMonitors(howMany);
        } catch (java.rmi.RemoteException rex) {
            throw new javax.ejb.EJBException(rex.getMessage());
        }
    }

    /**
     * get "howMany" subsequent EntityValue objects
     *
     * @param howMany how many AlarmValue objects the client wishes to retrieve
     * @return AlarmValue array
     * @exception javax.ejb.EJBException
     */
    public EntityValue[] getNextEntities(int howMany) throws javax.ejb.EJBException {
        try {
            return iter.getNextEntities(howMany);
        } catch (java.rmi.RemoteException rex) {
            throw new javax.ejb.EJBException(rex.getMessage());
        }
    }
}
