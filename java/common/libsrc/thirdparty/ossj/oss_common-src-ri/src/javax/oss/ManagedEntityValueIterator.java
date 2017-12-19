package javax.oss;

/**
 * Base ManagedEntityValue iterator.
 * <p>
 * This object is created to contain the results of an operation so that
 * the results may be returned to the client at a rate determined by the
 * client.  The client receives the iterator as a result of a query operation.
 * The client then invokes
 * operations on the iterator to receive batches of results in sizes
 * determined by the client.
 *<p>
 * The iterator keeps track of how far through the results the client has
 * progressed.
 *
 *
 */
public interface ManagedEntityValueIterator extends java.io.Serializable
{

     /** Retrieve the next available values.
     *
     * @param howMany maximum number of values to return.
     * @return an array of ManagedEntityValues with a size of at most howMany.
     * When no more values are available, an empty array is returned.
     * @exception java.rmi.RemoteException
     */
    public ManagedEntityValue[] getNext( int howMany )
    throws java.rmi.RemoteException;

    /**
     * Deallocate resources associated with this iterator.
     * <p>
     * This should be called when the client is finished iterating through the
     * collection.
     *
     * @exception java.rmi.RemoteException
     * @exception javax.ejb.RemoveException
     */
    public void remove()
    throws java.rmi.RemoteException, javax.ejb.RemoveException;
}
