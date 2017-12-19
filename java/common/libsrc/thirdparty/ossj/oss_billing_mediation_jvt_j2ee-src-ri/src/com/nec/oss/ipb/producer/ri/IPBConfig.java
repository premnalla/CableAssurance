/**
 * Copyright 2003 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */
package com.nec.oss.ipb.producer.ri;


import javax.oss.ApplicationContext;
import javax.naming.*;


import com.nokia.oss.ossj.common.ri.ApplicationContextImpl;
import javax.oss.ipb.producer.ProducerKey;


/**
 * Global Config Singleton Class used to provide access to JNDI environment
 * parameters
 *
 */
public class IPBConfig
{
    //singleton pattern
    private static IPBConfig singleton = new IPBConfig();

    /**
    * Version ID for this implementation
    */ 
    public static final String versionNumber = "IP Billing API v 1.1";
    /**
    * Application type for this implementation
    */ 
    public static final String applicationTypeDN = "IP Billing API";
    protected String applicationDN = "initial DN value";
    protected ApplicationContext appContext = null;
    protected String producerValueClass= "com.nec.oss.ipb.producer.ri.ProducerValueImpl.class";

    /**
     * Singleton accessor method to get the handle to this object.
     * 
     * @return IPBConfig instance
     */
    public static final IPBConfig getInstance()
    {
        return singleton;
    }

    /**
     * Constructor - looks up the URL, factory class, and application DN
     * in the JNDI environment.
     * 
     * @return IPBConfig instance
     */
    private IPBConfig() 
	{
        String urlCFG = null;
        String factoryClassCFG = null;
        String applicationDNCFG =null;
        try 
		{
            Context ctx = new InitialContext();
            urlCFG = new String((String) ctx.lookup( "java:comp/env/ApplicationURL"));
            factoryClassCFG = new String((String) ctx.lookup( "java:comp/env/ApplicationFactoryClass"));
            applicationDNCFG = new String((String) ctx.lookup( "java:comp/env/ApplicationDN"));
        }
        catch( javax.naming.NamingException e ) 
		{
			System.out.println("EXCEPTION IN LOOKUP CONFIG:" + e);
			e.printStackTrace();
        }
        ApplicationContextImpl newAppContext = new ApplicationContextImpl();
        newAppContext.setFactoryClass( factoryClassCFG );
        newAppContext.setURL( urlCFG );
        setApplicationContext( newAppContext );
        setApplicationDN( applicationDNCFG );
    }


    /**
     * Set the applicationDN value
     * 
     * @param appDN applicationDN value
     */
    public void setApplicationDN( String appDN ) 
	{
        applicationDN = appDN;
    }

    /**
     * Set the ApplicationContext value
     * 
     * @param ctx  ApplicationContext value
     */
    public void setApplicationContext( ApplicationContext ctx ) 
	{
        appContext = ctx;
    }

    /**
     * Get the ApplicationContext value
     * 
     * @return  ApplicationContext value
     */
    public ApplicationContext getApplicationContext() 
	{
        if( appContext == null ) System.out.println("---NULL APP CONTEXT---" );
        return appContext;
    }

    /**
     * Set the applicationDN value
     * 
     * @return applicationDN value String
     */
    public String getApplicationDN() 
	{
        if( applicationDN == null ) System.out.println("---NULL APP TYPE DN---" );
        return applicationDN;
    }

    /**
     * Utility method to refresh a ProducerKey retrieved from the DB. Sets
     * the ApplicationContext, ApplicationDN, and the type.
     * 
     * @return applicationDN value String
     */
	public ProducerKey refreshProducerKey(ProducerKey prodKey)
	{
		prodKey.setApplicationContext(IPBConfig.getInstance().getApplicationContext());
		prodKey.setApplicationDN(IPBConfig.getInstance().getApplicationDN());
		prodKey.setType(versionNumber);
		return(prodKey);
	}

    /**
     * Utility method to initialize a newly created ProducerKey. Sets the
     * calls refreshProducerKey(), clears the PrimaryKey and sets the 
     * ApplicationContext.
     * 
     * @return applicationDN value String
     */
	public ProducerKey initializeNewProducerKey(ProducerKey prodKey)
	{
		prodKey=refreshProducerKey(prodKey);
		prodKey.setPrimaryKey(null);
		prodKey.setApplicationContext(getApplicationContext());

		return(prodKey);
	}
}
