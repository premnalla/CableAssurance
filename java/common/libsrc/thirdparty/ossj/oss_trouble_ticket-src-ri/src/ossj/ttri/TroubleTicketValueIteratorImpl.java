package ossj.ttri;

import javax.oss.ManagedEntityValue;
import javax.oss.trouble.TroubleTicketValue;
import javax.oss.trouble.TroubleTicketValueIterator;

/**
 * TroubleTicketValueIterator Interface
 *
 *
 * @author Vincent Perrot, Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * @version 1.0
 * @since September 9, 2003
 */

public class TroubleTicketValueIteratorImpl implements TroubleTicketValueIterator {

    protected TroubleTicketValueIteratorIF ttIterator;

    /** Creates a new instance of Class */
    public TroubleTicketValueIteratorImpl(TroubleTicketValueIteratorIF ttIterator) {
        this.ttIterator = ttIterator;
    }


    public TroubleTicketValue[] getNextTroubleTickets(int howMany)
            throws java.rmi.RemoteException {
        return (ttIterator.getNextTroubleTickets(howMany));
    }

    public ManagedEntityValue[] getNext(int howMany)
            throws java.rmi.RemoteException {
        return (ttIterator.getNext(howMany));
    }

    public void remove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
        ttIterator.remove();
    }

}
