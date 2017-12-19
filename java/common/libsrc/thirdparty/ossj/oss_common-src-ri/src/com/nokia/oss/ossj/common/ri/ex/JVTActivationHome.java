package com.nokia.oss.ossj.common.ri.ex;





import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.rmi.RemoteException;


/**
 * EJB home interface for an JVTActivationSession.
 * <p>
 * To locate an JVTActivationHome, consult chapter 3 "Using the API".
 *
 **/


public interface JVTActivationHome extends EJBHome {
    public JVTActivationSession create() throws CreateException, EJBException, RemoteException;
}


