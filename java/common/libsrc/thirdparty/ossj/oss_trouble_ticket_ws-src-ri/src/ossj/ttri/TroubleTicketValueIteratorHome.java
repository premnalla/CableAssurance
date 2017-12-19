package ossj.ttri;


/**
 * TroubleTicketValueIteratorHome Interface
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface TroubleTicketValueIteratorHome extends javax.ejb.EJBHome {


    public TroubleTicketValueIteratorIF create(Operation asmtoperation)
            throws javax.ejb.CreateException,
            java.rmi.RemoteException;


}
