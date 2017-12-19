package ossj.ttri;

/**
 * XmlResponseIterator Interface Class
 * Used by XVT Session Bean.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public interface XmlResponseIterator extends java.io.Serializable {
    //public String[] getNext( int howMany )
    public String getNext(int howMany)
            throws java.rmi.RemoteException;

    // remove is called, when the client has finished iterating
    // through  the collection to allow resources to be deallocated.

    public void remove()
            throws java.rmi.RemoteException, javax.ejb.RemoveException;
}
