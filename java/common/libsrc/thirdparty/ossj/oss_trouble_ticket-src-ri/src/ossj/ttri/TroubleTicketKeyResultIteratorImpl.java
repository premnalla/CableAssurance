package ossj.ttri;

import javax.oss.ManagedEntityKeyResult;
import javax.oss.trouble.TroubleTicketKeyResult;
import javax.oss.trouble.TroubleTicketKeyResultIterator;

/**
 * TroubleTicketKeyResultIteratorImpl Interface Class
 *
 *
 * @author Vincent perrot Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * @version 1.0
 * @since September 2003
 */

public class TroubleTicketKeyResultIteratorImpl implements TroubleTicketKeyResultIterator {

    protected TroubleTicketKeyResultIteratorIF kresiter;

    /** Creates a new instance of TroubleTicketKeyResultIteratorImpl */
    public TroubleTicketKeyResultIteratorImpl(TroubleTicketKeyResultIteratorIF kresiter) {
        this.kresiter = kresiter;
    }

    public TroubleTicketKeyResult[] getNextTroubleTicketKeyResults(int howMany)
            throws java.rmi.RemoteException {
        return (kresiter.getNextTroubleTicketKeyResults(howMany));
    }

    public ManagedEntityKeyResult[] getNext(int howMany)
            throws java.rmi.RemoteException {
        return (kresiter.getNext(howMany));
    }

    public void remove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
        kresiter.remove();
    }
}