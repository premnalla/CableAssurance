package com.nokia.oss.ossj.sa.ri.order;

import java.rmi.RemoteException;

import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.oss.IllegalArgumentException;
import javax.oss.ManagedEntityValue;
import javax.oss.order.OrderValue;
import javax.oss.order.OrderValueIterator;

/**
 * @author aebbert
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OrderValueIteratorProxy implements OrderValueIterator {

    RemoteOrderValueIterator myIterator;
    Handle myHandle;
    
    public OrderValueIteratorProxy(Handle aHandle) throws IllegalArgumentException {
        if (aHandle==null)
        {
            throw new IllegalArgumentException("Handle may not be null");
        }
        myHandle = aHandle;
    }
    
    private RemoteOrderValueIterator getIterator() throws RemoteException {
        if (myIterator == null) {
            myIterator = (RemoteOrderValueIterator)myHandle.getEJBObject();
        }
        return myIterator;
    }
    
	/**
	 * @see javax.oss.order.OrderValueIterator#getNextOrder(int)
	 */
	public OrderValue[] getNextOrder(int howMany)
		throws IllegalArgumentException, RemoteException {
		return getIterator().getNextOrder(howMany);
	}

	/**
	 * @see javax.oss.ManagedEntityValueIterator#getNext(int)
	 */
	public ManagedEntityValue[] getNext(int howMany) throws RemoteException {
		return getIterator().getNext(howMany);
	}

	/**
	 * @see javax.oss.ManagedEntityValueIterator#remove()
	 */
	public void remove() throws RemoteException, RemoveException {
        getIterator().remove();
	}

}
