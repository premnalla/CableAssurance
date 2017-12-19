/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;


/**
 * EJB home interface for an JVTActivationSession.
 * <p>
 * To locate an JVTActivationHome, consult chapter 3 "Using the API".
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 *
 **/
public interface JVTActivationHome extends EJBHome {
    public JVTActivationSession create() throws CreateException, RemoteException;
}


