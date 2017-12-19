package ossj.qos;

import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityKey;
import javax.oss.Serializer;
import javax.oss.SerializerFactory;

import ossj.qos.util.Log;
import ossj.qos.util.Util;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Henrik Lindström
 * @version 0.2
 */

public class ManagedEntityKeyImpl implements ManagedEntityKey
{
    /**
     * The application context.
     */
    protected ApplicationContext applicationContext = null;
    /**
     * Application DN.
     */
    protected String applicationDN = null;
    /**
     * Domain.
     */
    private String domain;
    /**
     * Type
     */
    private String type;
    /**
     * primary key
     */
    private Object primaryKey;


    public ManagedEntityKeyImpl()
    {
        Log.write(this,Log.LOG_ALL,"ManagedEntityKeyImpl()","called");
    }

    /**
     * Factory method for ApplicationContext
     * 
     * 
     * @return ApplicationContext
     */

	public javax.oss.ApplicationContext makeApplicationContext() {
				return ((ApplicationContext) new ApplicationContextImpl() ) ;
    }

     /**
     * The Domain name identifies an application's administrative domain.
     * An administrative domain maintains a JNDI namespace.
     *
     * @return String domain name
     */
    public String getDomain()
    {
      return domain;
    }

    public void setDomain(String domain)
    throws java.lang.IllegalArgumentException
    {
      this.domain = domain;
    }

    /**
     *
     *
     * @return String entity's type name
     */
    public String getType()
    {
      return this.type;

    }

    public void setType( String type )
        throws java.lang.IllegalArgumentException
    {
      this.type = type;
    }

     /**
     * The unique identifier for the Managed Entity
     * This key is mappable to the EJB primary key
     * when the ManagedEntity is represented by an
     * Entity Bean
     */
    public Object getPrimaryKey()
    {
      return primaryKey;
    }

    public void setPrimaryKey(Object key)
    throws java.lang.IllegalArgumentException
    {
      primaryKey = key;
    }


    /**
     * ApplicationContext used to locate the Application
     * Components in charge of this managed entity
     *
     * @return ApplicationContext
     */
    public javax.oss.ApplicationContext getApplicationContext() {
        Log.write(this,Log.LOG_ALL,"getApplicationContext()","applicationContext="+applicationContext);
        return applicationContext;
    }

    /**
     * Set the application context.
     * @param ctx application context
     */
    public void setApplicationContext( ApplicationContext ctx ) {
        applicationContext = ctx;
    }

   /**
    * The DN of the Application JNDI Naming Context
    * The format of this DN is: <p>
    *
    * <i>optional deployment dependent top context/</i>
    * System/<i>system</i>/ApplicatonType/Activation/Application/
    * <i>major</i>-<i>minor</i>-<i>build</i>-<i>product</i>/
    * <p>
    * under which the topics and homes of the application that is
    * sending the event are deployed.
    *
    * @return DN of ApplicationDN
    */
    public String getApplicationDN() {
       Log.write(this,Log.LOG_ALL,"getApplicationDN()","applicationDN="+applicationDN);
        return applicationDN;
    }

    /**
     * Set application DN.
     * @param dn of application
     */
    public void setApplicationDN( String dn ) {
        applicationDN = dn;
    }

    /**
     * Manufacture a PrimaryKey
     *
     * @return PrimaryKey implementation class
     */
    public synchronized Object makePrimaryKey() {
        return Util.makeGUID(this);
    }

    public Object clone()
    {
        ManagedEntityKeyImpl clone = null;
        try {
            clone = (ManagedEntityKeyImpl)super.clone();
            if ( primaryKey != null ) {
              clone.primaryKey = Util.clone( this.primaryKey ); // if possible clone obj
            }
            if ( applicationContext != null ) {
              clone.applicationContext = (ApplicationContext)Util.clone( this.applicationContext ); // if poss. clone obj
           }
        } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            // "should not happen"
        }
       return clone;
    }

    public String toString()
    {
        String str = "pk=" + primaryKey + ",dmn=" + domain + ",type=" + type
            + ",applDN=" + applicationDN;
        return str;
    }

    public String[] getSupportedSerializerTypes() {
      /**@todo Implement this javax.oss.SerializerFactory method*/
      Log.write(this,Log.LOG_ALL,"getSupportedSerializerTypes()","Not implemented");
      return new String[0];
    }

    public Serializer makeSerializer( String serializerType )
                          throws java.lang.IllegalArgumentException {
      /**@todo Implement this javax.oss.SerializerFactory method*/
      Log.write(this,Log.LOG_ALL,"makeSerializer()","Not implemented");
        throw new IllegalArgumentException( "NOT IMPLEMENTED/SUPPORTED" );

    }

}
