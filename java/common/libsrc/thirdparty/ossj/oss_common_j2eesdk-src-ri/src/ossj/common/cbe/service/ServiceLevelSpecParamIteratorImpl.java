/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecParam;
import javax.oss.cbe.service.ServiceLevelSpecParamIterator;
import java.rmi.RemoteException;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecParamIterator</CODE> interface.
 *
 * @see javax.oss.cbe.service.ServiceLevelSpecParamIterator
 *
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2
 * @since September 2005
 */
public class ServiceLevelSpecParamIteratorImpl implements ServiceLevelSpecParamIterator {
    private ServiceLevelSpecParam[] values = null;
    private int currentPos = -1;

    /**
     * Constructs a new ServiceLevelSpecParamIteratorImpl with empty attributes.
     *
     */
    private ServiceLevelSpecParamIteratorImpl() {
    }

    /**
     * Constructs a new ServiceLevelSpecParamIteratorImpl with given Values.
     * @param givenValues
     */
    public ServiceLevelSpecParamIteratorImpl(ServiceLevelSpecParam[] givenValues) {
        if (givenValues != null) {
            values = new ServiceLevelSpecParamImpl[givenValues.length];
            System.arraycopy(givenValues, 0, values, 0, givenValues.length);
        }
    }

    /**
    * Returns the next specified number of elements in the iteration.
    * @param howMany
    * @return the selected number of elements
    * @throws java.rmi.RemoteException
     */
    public ServiceLevelSpecParam[] getNextServiceLevelSpecParams(int how_many)
        throws java.rmi.RemoteException {
        return getNext(how_many);
    }

    /**
    * Returns the next specified number of elements in the iteration.
    * It is identical to getNextServiceLevelSpecParams
    * 
    * @param howMany
    * @return the selected number of elements
    * @throws java.rmi.RemoteException
    * @see javax.oss.ManagedEntityValueIterator#getNext(int)
     */
    public ServiceLevelSpecParam[] getNext(int how_many)
        throws RemoteException {
        int numberOfObjectsToReturn;
        ServiceLevelSpecParam[] nextValues = null;

        if (values == null) {
            return new ServiceLevelSpecParamImpl[0];
        }

        // Determine the number of objects to return
        if ((currentPos + how_many) <= (values.length - 1)) {
            numberOfObjectsToReturn = how_many;
        } else {
            numberOfObjectsToReturn = (values.length - 1) - currentPos;
        }

        if (numberOfObjectsToReturn > 0) {
            nextValues = new ServiceLevelSpecParamImpl[numberOfObjectsToReturn];

            try {
                System.arraycopy(values, currentPos + 1, nextValues, 0, numberOfObjectsToReturn);
                currentPos += numberOfObjectsToReturn;
            } catch (Exception e) {
                throw new RemoteException(e.getMessage());
            }
        } else {
            nextValues = new ServiceLevelSpecParamImpl[0];
        }

        return nextValues;
    }

    /**
     * Remove the iterator.
     */
    public void remove() {
        values = null;
    }
    
        /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        ServiceLevelSpecParamIterator val = null;
        try {
            val = (ServiceLevelSpecParamIterator)super.clone();
            
        } catch (CloneNotSupportedException cnse) {
            //cnse.printStackTrace();
            throw new java.lang.RuntimeException("ManagedEntityKeyImpl: Error invoking clone() CloneNotSupportedException, " +cnse.getMessage());
            //return null;
        }
        return val;
    }
}
