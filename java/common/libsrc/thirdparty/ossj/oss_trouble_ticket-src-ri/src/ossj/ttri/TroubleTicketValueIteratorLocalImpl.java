package ossj.ttri;

import javax.oss.ManagedEntityValue;
import javax.oss.trouble.TroubleTicketValue;
import javax.oss.trouble.TroubleTicketValueIterator;

/**
 * TroubleTicketValueIterator Local Classs
 * Used by JMS/Proxy in TCK
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketValueIteratorLocalImpl
        implements TroubleTicketValueIterator, java.io.Serializable {
    private java.util.Vector valueVector;

    public TroubleTicketValueIteratorLocalImpl() {
        valueVector = new java.util.Vector();
    }

    public void addTroubleTicketValues(TroubleTicketValue[] results) {
        for (int i = 0; i < results.length; i++) {
            valueVector.addElement(results[i]);
        }

    }

    public TroubleTicketValue[] getNextTroubleTickets(int howMany)
            throws java.rmi.RemoteException {
        int sz = howMany;
        if (valueVector.size() < howMany) sz = valueVector.size();

        TroubleTicketValue[] retObjs = new TroubleTicketValue[sz];
        for (int x = 0; x < sz; x++) {
            retObjs[x] = (TroubleTicketValue) valueVector.firstElement();
            valueVector.removeElementAt(0);
        }
        return retObjs;

    }


    public ManagedEntityValue[] getNext(int howMany)
            throws java.rmi.RemoteException {
        return (ManagedEntityValue[]) getNextTroubleTickets(howMany);
    }


    public void remove()
            throws java.rmi.RemoteException, javax.ejb.RemoveException {
        valueVector.clear();
    }
}
