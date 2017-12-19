package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * XVTTroubleTicketSessionHome Interface
 * Defines the create method for the XVT TT Session
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface XVTTroubleTicketSessionHome extends EJBHome {
    // returns the remote interface
    public XVTTroubleTicketSession create() throws CreateException, RemoteException;
}
