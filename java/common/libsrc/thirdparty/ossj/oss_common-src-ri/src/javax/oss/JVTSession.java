package javax.oss;

/**
 * Every JVT&lt;ApplicationType&gt;Session Bean interface must derive from
 * the following base session interface. The base JVTSession interface
 * provides a common set of operations shared by all the JVT Session Beans.
 * The implementation of the <CODE>JVTSession</CODE> operations are mandatory.
 *
 *
 */
public interface JVTSession extends javax.ejb.EJBObject
{


    /**
     * Get the names of the optional operations supported by this JVT
     * Session Bean. The names of the optional operations are defined
     * in the <CODE>JVT&lt;ApplicationType&gt;SessionOptionalOps</CODE> interface as
     * defined by the API.
     *
     *
     * @return String array which contains the names of all the optional
     * operations supported by an implementation of a JVT Session Bean.
     *
     * @exception java.rmi.RemoteException
     */
    String[] getSupportedOptionalOperations( )
    throws java.rmi.RemoteException;

    /**
     * Get the Managed Entity types supported by a JVT Session Bean.
     *
     * @return String array which contains the fully qualified names of the leaf
     * node interfaces representing the supported managed entity types.
     * Note that it is not the implementation class name that is returned.
     * @exception java.rmi.RemoteException
     */

    String[] getManagedEntityTypes()
    throws java.rmi.RemoteException;

    /**
     * Get the Query type names supported by a JVT Session Bean
     *
     * @return String array which contains the fully qualified names of the leaf
     * node interfaces representing the supported query value types,
     * i.e., interfaces which extend QueryValue.
     * @exception java.rmi.RemoteException
     */

    String[] getQueryTypes()
    throws java.rmi.RemoteException;

    /**
     * Get the Event Type names supported by the JVT Session Bean
     *
     * @return String array which contains the fully qualified names of the
     * leaf node interfaces representing the supported event types.
     * @exception java.rmi.RemoteException
     */
    //Get the Event Type Names supported by the Session Component
    String[] getEventTypes()
    throws java.rmi.RemoteException;

     /**
      * Create a QueryValue Instance matching a Query type name.
      * The Session Bean is used as a factory for the creation of
      * query values.
      *
      * @param type fully qualified name of the leaf node QueryValue interface.
      * @return query value object of the specified type.
      * @exception javax.oss.IllegalArgumentException unknown or unsupported
      * query type.
      * @exception java.rmi.RemoteException
      */

    QueryValue makeQueryValue(String type)
    throws javax.oss.IllegalArgumentException ,java.rmi.RemoteException;

    /**
     * Get the EventPropertyDescriptor associated with an event type name.
     *
     * @param eventType fully qualified name of the leaf node Event interface.
     * @return EventPropertyDescriptor which can be used to discover the
     * filterable properties of the specified event type.
     * @exception javax.oss.IllegalArgumentException unknown or unsupported
     * event type.
     * @exception java.rmi.RemoteException
     */

    EventPropertyDescriptor getEventDescriptor(String eventType)
    throws javax.oss.IllegalArgumentException , java.rmi.RemoteException;



    /**
     * Create a Value Type object for a specific Managed Entity type.
     * Not to be confused with the creation of a Managed Entity.
     * The Session Bean is used as a factory for the creation of
     * Value Types.
     *
     * @param valueType fully qualified name of the leaf managed entity value
     * interface.
     * @return managed entity value object of the specified type.
     * @exception javax.oss.IllegalArgumentException unknown or unsupported
     * managed entity value type.
     * @exception java.rmi.RemoteException
     */

    ManagedEntityValue makeManagedEntityValue( String valueType)
    throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

    /**
     * Query multiple Managed Entities using a QueryValue.
     *
     * @param query a QueryValue object representing the query.
     * @param attrNames names of attributes which should be populated in the
     * ManagedEntityValue objects returned by the ManagedEntityValueIterator.
     * @return a ManagedEnityValueIterator used to extract the results of the query.
     * @exception javax.oss.IllegalArgumentException unsupported query value type.
     * (Note: if an attribute specified in the attributeName array is not
     * supported, it is simply ignored and and does not result in an exception.)
     * @exception java.rmi.RemoteException
     */

    ManagedEntityValueIterator
    queryManagedEntities( QueryValue query , String[] attributeNames)
    throws javax.oss.IllegalArgumentException ,java.rmi.RemoteException;

}

