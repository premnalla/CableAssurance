
package javax.oss;
import java.io.Serializable;

/**
 * A <CODE>ManagedEntityKey</CODE> is a unique identifier for a
 * <CODE>ManagedEntityValue</CODE>.
 * <p>
 * The Type, ApplicationDN and ApplicationContext
 * are included in <CODE>ManagedEntityKey</CODE>, because
 * a <CODE>primaryKey</CODE> is only unique within an
 * application instance as given by the ApplicationDN running
 * in a given naming system as given by the ApplicationContext URL.
 *
 *
 */
public interface ManagedEntityKey extends java.io.Serializable,
Cloneable, javax.oss.SerializerFactory
{

    /**
     * Deep copy  this key.
     *
     * @return deep copy of this key.
     */
    public Object clone();

    /**
     * Factory method for ApplicationContext
     *
     *
     * @return a newly created ApplicationContext object.
     */

    public javax.oss.ApplicationContext makeApplicationContext();

    /**
     * Get the ApplicationContext which can be used to locate the Application
     * Components in charge of the managed entity referenced by this key.
     *
     * @return the ApplicationContext.
     */

    public javax.oss.ApplicationContext getApplicationContext();


    /**
     * Set the ApplicationContext.
     * <p>
     * Because a key is only unique within a given naming system
     * (as given by the JNDI provider URL) and for a given Application
     * instance ( as given by the ApplicationDN ) the ApplicationContext
     * must be set to uniquely identify a key.
     *
     * @param ctx the ApplicationContext
     * @throws java.lang.IllegalArgumentException if ApplicationContext
     * argument is not valid in some way.
     */
    public void setApplicationContext( ApplicationContext ctx )
    throws java.lang.IllegalArgumentException;


    /**
     * Set the ApplicationDN.
     * <p>
     * Because a key is only unique within a given naming system
     * (as given by the JNDI provider URL) and for a given Application
     * instance ( as given by the ApplicationDN ) the ApplicationDN
     * must be set to uniquely identify a key.
     *
     * @param applicationDN the application DN
     * @throws java.lang.IllegalArgumentException if applicationDN
     * argument is malformed.
     */
    public void setApplicationDN( String applicationDN)
    throws java.lang.IllegalArgumentException;

   /**
    * The DN of the Application JNDI Naming Context.
    * The format of this DN is: <p>
    * < Optional Deployment dependent Top Context >/
    * System/
    * < System Name >/
    * ApplicatonType/
    * < Application Type Name >/
    * Application/
    * < API Version Number >;< Product Release Number >;< Product Name >
    *<P>
    * where the format of the API Version Number and the Product Release
    * Number is: <p> < MajorVersionNumber-MinorVersionNumber >
    * <p>
    * @return the ApplicationDN
    */
    public String getApplicationDN();

    /** Get the type of the <CODE>ManagedEntityValue</CODE> referenced by
     *  the <CODE>ManagedEntityKey</CODE>.
     * <p>
     * The type is part of <CODE>ManagedEntityKey</CODE>,
     * because <CODE>primaryKey</CODE>s can be
     * duplicate between <CODE>ManagedEntityValue</CODE>, implemented by the same
     * class, but from different type (not java type).
     * @return String entity's type name
     */
    public String getType();

    /** Set a new value for type.
     *  <p>
     * @param type the new value for type
     * @throws java.lang.IllegalArgumentException if type is not valid.
     */
    public void setType( String type )
    throws java.lang.IllegalArgumentException;

     /** Get the primary key.
      *  <p>
      *  This is a unique identifier for a Managed Entity within the scope
      *  of application context, application DN, and type.
      *  <p>
     * In case the implementation uses Entity Beans,
     * an implementation may map the primary key to the EJB primary key.
     * @return the primary key
     */
    public Object getPrimaryKey();

    /**
     * Sets a new value for the primary key.
     *
     * @param key the new value for the primary key.
     * @throws java.lang.IllegalArgumentException if key is bad in some way.
     */
    public void setPrimaryKey( Object key)
    throws java.lang.IllegalArgumentException;

    /**
     * Manufacture a PrimaryKey.
     *
     * @return a primary key object of the appropriate implementation class.
     * (Note: this is just an empty object, not a unique primary key.)
     */


    public Object makePrimaryKey();

}

