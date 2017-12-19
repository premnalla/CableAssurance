package ossj.ttri;

import javax.oss.ManagedEntityKeyResult;
import javax.oss.trouble.TroubleTicketKeyResult;
import javax.oss.trouble.TroubleTicketKeyResultIterator;

/**
 * TroubleTicketKeyResultIterator Local Implementation Class
 * Used by the JMS Proxy in the TCK.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketKeyResultIteratorLocalImpl
        implements TroubleTicketKeyResultIterator, java.io.Serializable {
    private java.util.Vector keyResultVector;

    public TroubleTicketKeyResultIteratorLocalImpl() {
        keyResultVector = new java.util.Vector();
    }

    public void addTroubleTicketKeyResults(TroubleTicketKeyResult[] results) {
        for (int i = 0; i < results.length; i++) {
            keyResultVector.addElement(results[i]);
        }

    }


    public TroubleTicketKeyResult[] getNextTroubleTicketKeyResults(int howMany)
            throws java.rmi.RemoteException {


        int sz = howMany;
        if (keyResultVector.size() < howMany) sz = keyResultVector.size();

        TroubleTicketKeyResult[] retObjs = new TroubleTicketKeyResult[sz];
        for (int x = 0; x < sz; x++) {
            retObjs[x] = (TroubleTicketKeyResult) keyResultVector.firstElement();

            keyResultVector.removeElementAt(0);
        }
        return retObjs;
    }


    public ManagedEntityKeyResult[] getNext(int howMany)
            throws java.rmi.RemoteException {
        return (ManagedEntityKeyResult[]) getNextTroubleTicketKeyResults(howMany);
    }

    public void remove()
            throws java.rmi.RemoteException, javax.ejb.RemoveException {
        keyResultVector.clear();
    }
}
