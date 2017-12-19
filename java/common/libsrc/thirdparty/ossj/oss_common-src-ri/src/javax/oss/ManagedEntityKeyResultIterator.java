package javax.oss;

/**
 * Base interface for application specific <CODE>&lt;ManagedEntity&gt;KeyResultIterator</CODE>
 * <p>
 * Because some JVT operations could potentially return large amounts of Key Results,
 * the iterator design pattern is used for returning the results.
 * <p>
 * The behavior of the Iterator is the same as that defined for the
 * ManagedEntityValueIterator interface.
 * <p>
 * An application specific <CODE>&lt;ManagedEntity&gt;KeyResultIterator</CODE> interface must comply
 * with the following template:
 * <pre>
 * public interface &lt;ManagedEntity&gt;KeyResultIterator extends ManagedEntityKeyResultIterator
 * {
 *     public &lt;ManagedEntity&gt;KeyResult[]
 *     getNext&lt;ManagedEntity&gt;KeyResult( int howMany )
 *     throws java.rmi.RemoteException;
 * }
 * </pre>
 *
 */
public interface ManagedEntityKeyResultIterator extends java.io.Serializable
{

     /** Retrieve the next available results.
     *
     * @param howMany maximum number of results to return.
     * @return an array of ManagedEntityKeyResults with a size of at most howMany.
     * When no more results are available, an empty array is returned.
     * @exception java.rmi.RemoteException
     */
    public ManagedEntityKeyResult[] getNext( int howMany )
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
