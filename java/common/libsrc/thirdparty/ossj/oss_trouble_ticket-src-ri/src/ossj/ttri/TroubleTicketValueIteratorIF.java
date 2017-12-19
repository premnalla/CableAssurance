package ossj.ttri;

import javax.ejb.EJBObject;
import javax.oss.ManagedEntityValue;
import javax.oss.trouble.TroubleTicketValue;

/**
 * TroubleTicketValueIterator Interface
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface TroubleTicketValueIteratorIF extends EJBObject {
    public TroubleTicketValue[] getNextTroubleTickets(int howMany)
            throws java.rmi.RemoteException;

    public ManagedEntityValue[] getNext(int howMany)
            throws java.rmi.RemoteException;


}
