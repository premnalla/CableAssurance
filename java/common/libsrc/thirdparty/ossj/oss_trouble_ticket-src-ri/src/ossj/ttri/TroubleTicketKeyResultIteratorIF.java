package ossj.ttri;

import javax.ejb.EJBObject;
import javax.oss.ManagedEntityKeyResult;
import javax.oss.trouble.TroubleTicketKeyResult;

/**
 * TroubleTicketKeyResultIterator Interface Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public interface TroubleTicketKeyResultIteratorIF extends EJBObject {
    public TroubleTicketKeyResult[] getNextTroubleTicketKeyResults(int howMany)
            throws java.rmi.RemoteException;

    public ManagedEntityKeyResult[] getNext(int howMany)
            throws java.rmi.RemoteException;
}
