/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.util.Vector;

import javax.oss.ManagedEntityKeyResult;
import javax.oss.ManagedEntityKeyResultIterator;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ManagedEntityKeyResultIteratorImpl implements ManagedEntityKeyResultIterator {
    private Vector valueVector;

    /**
     * Creates an Iterator for ManagedEntityKeyResult.
     *
     */
    public ManagedEntityKeyResultIteratorImpl() {
        valueVector = new Vector();
    }

    /**
     * Creates an Iterator for ManagedEntityKeyResult using the given results.
     *
     */
    public ManagedEntityKeyResultIteratorImpl(ManagedEntityKeyResult[] results) {
        valueVector = new Vector();

        try {
            addManagedEntityKeyResults(results);
        } catch (java.rmi.RemoteException rex) {
        }
    }

    /**
     * Adds all the results specified in the given array, increasing its size by the number of elements.
     *
     * @param results
     * @throws java.rmi.RemoteException
     */
    public void addManagedEntityKeyResults(ManagedEntityKeyResult[] results)
        throws java.rmi.RemoteException {
        for (int i = 0; i < results.length; i++) {
            valueVector.addElement(results[i]);
        }
    }

    /**
     * Returns the next specified number of elements in the iteration.
     * @param howMany
     * @return
     * @throws java.rmi.RemoteException
     */
    public ManagedEntityKeyResult[] getNext(int howMany)
        throws java.rmi.RemoteException {
        int sz = howMany;

        if (valueVector.size() < howMany) {
            sz = valueVector.size();
        }

        ManagedEntityKeyResult[] retObjs = new ManagedEntityKeyResult[sz];

        for (int x = 0; x < sz; x++) {
            retObjs[x] = (ManagedEntityKeyResult) valueVector.firstElement();
            valueVector.removeElementAt(0);
        }

        return retObjs;
    }

    /**
     * Removes all the element from the Iterator.
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.RemoveException
     */
    public void remove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
        valueVector.clear();
    }
}
