/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public interface JmsSenderHome extends EJBHome {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws CreateException DOCUMENT ME!
     * @throws RemoteException DOCUMENT ME!
     */
    public JmsSender create() throws CreateException, RemoteException;
}
