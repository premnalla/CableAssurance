package javax.oss;

/**
 * The ApplicationContext interface contains the URL and
 * other system properties required to set up an initial
 * connection with the JNDI provider into which the
 * components in charge of that managed entity are registered.
 * <p>
 * Security Credentials are not passed.
 * <p>
 *
 */
public interface ApplicationContext extends java.io.Serializable , java.lang.Cloneable
{

    /**
     * @return A deep copy of the ApplicationContext object.
     */
    public Object clone();

    /**
     * The name of the factory class required to setup the
     * initial context with the JNDI provider.
     *
     * @return name of factory class
     */
    public String getFactoryClass();

    /**
     * The URL of the JNDI provider where the Home interfaces
     * of the Session Bean(s) in charge of the entity could be found.
     *
     * @return URL of the JNDI provider
     */
    public String getURL();

    /**
     * Other properties that may be necessary and
     * vendor specific to setup an initial context with the
     * JNDI Provider.
     *
     * @return Map of system properties
     */
    public java.util.Map getSystemProperties();

    /**
     * Set the Factory Class Name.
     *
     * @param factoryClassName
     */

    public void setFactoryClass(String factoryClassName)
    throws java.lang.IllegalArgumentException;


    /**
     * Set the System properties.
     *
     * @param properties
     */
    public void setSystemProperties(java.util.Map properties)
    throws java.lang.IllegalArgumentException;


    /**
     * Set the URL.
     *
     * @param URL
     */
    public void setURL(String URL)
    throws java.lang.IllegalArgumentException;


}
