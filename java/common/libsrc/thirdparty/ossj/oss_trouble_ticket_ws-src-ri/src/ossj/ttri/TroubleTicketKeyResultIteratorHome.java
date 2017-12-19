package ossj.ttri;


/**
 * TroubleTicketKeyResultIterator Session Bean Interface Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface TroubleTicketKeyResultIteratorHome extends javax.ejb.EJBHome {

    public TroubleTicketKeyResultIteratorIF create(Operation asmtop)
            throws javax.ejb.CreateException,
            java.rmi.RemoteException;

}
