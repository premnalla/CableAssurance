/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.util.Vector;

import javax.oss.ManagedEntityValue;
import javax.oss.ManagedEntityValueIterator;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ManagedEntityValueIteratorImpl implements ManagedEntityValueIterator {
    private Vector valueVector;

    /**
     * Creates an empty Iterator for ManagedEntityValues.
     *
     */
    public ManagedEntityValueIteratorImpl() {
        valueVector = new Vector();
    }

    /**
     * Creates a new ManagedEntityValueIteratorImpl object.
     *
     * @param values DOCUMENT ME!
     */
    public ManagedEntityValueIteratorImpl(ManagedEntityValue[] values) {
        valueVector = new Vector();
        addManagedEntityValues(values);
    }

    /**
     * Adds all the values specified in the given array, increasing its size
     * by the number of elements.
     *
     * @param results
     */
    public void addManagedEntityValues(ManagedEntityValue[] values) {
        for (int i = 0; i < values.length; i++) {
            valueVector.addElement(values[i]);
        }
    }

    /**
     * Returns the next specified number of elements in the iteration.
     * @param howMany
     * @return
     * @throws java.rmi.RemoteException
     */
    public ManagedEntityValue[] getNext(int howMany) throws java.rmi.RemoteException {
        int sz = howMany;

        if (valueVector.size() < howMany) {
            sz = valueVector.size();
        }

        ManagedEntityValue[] retObjs = new ManagedEntityValue[sz];

        for (int x = 0; x < sz; x++) {
            retObjs[x] = (ManagedEntityValue) valueVector.firstElement();
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
