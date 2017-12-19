/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.lang.reflect.*;

import java.util.*;

import javax.naming.*;

import javax.oss.*;


/**
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ApplicationContextImpl 
extends java.lang.Object 
implements javax.oss.ApplicationContext,  java.lang.Cloneable {
	
	/** Holds value of property factoryClass. */
	private String factoryClass;
	
	/** Holds value of property systemProperties. */
	private java.util.TreeMap systemProperties;
	
	/** Holds value of property url. */
	private String url;
	
	/** Creates new default ApplicationContextImpl.
	 * The factoryClass and the URL are set to null.
	 * The systemProperties is set to an empty Map.
	 */
	
	//fix to bug ID 4925234
	public ApplicationContextImpl() {
		this.factoryClass = null;
		this.systemProperties = new java.util.TreeMap(); 
		this.url = null;
	}
	
	/** Creates new ApplicationContextImpl
	 * @param factoryClass Name of the class which has to be used as INITIAL_CONTEXT_FACTORY when a Context is created
	 * @param systemProperties additional properties, i.e. username and password
	 * @param url Parameter of PROVIDER.URL during Context creation
	 */
	public ApplicationContextImpl(String factoryClass, Map systemProperties, String url) {
		this.factoryClass = factoryClass;

		if (systemProperties != null) {
			this.systemProperties = new java.util.TreeMap(systemProperties);
		} else {
			this.systemProperties = new java.util.TreeMap();
		}

		this.url = url;
	}
	
	/**
	 * The name of the factory class required to setup the
	 * initial context with the JNDI provider
	 *
	 * @return name of factory class
	 */
	public String getFactoryClass() {
		return factoryClass;
	}
	
	/**
	 * Other properties that may be necessay and
	 * vendor specific to setup an initial context with the
	 * JNDI Provider
	 *
	 * @return Map of system properties
	 */
	public java.util.Map getSystemProperties() {
		return systemProperties;
	}
	
	/**
	 * The URL of the JNDI provider where the Home interfaces
	 * of the Session Bean(s) in charge of the entity could be found.
	 *
	 * @return URL of the JNDI provider
	 */
	public String getURL() {
		return url;
	}
	
	/** Compares this ApplicationContext to the specified object.
	 * The result is true if and only if the argument is not null
	 * and is an ApplicationContext that represents the same context as this object.
	 *
	 * @param other the object to compare this ApplicationContext against
	 * @return true if the ApplicationContext are equals; false otherwise
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (!(other instanceof ApplicationContext)) {
			return false;
		}

		ApplicationContext otherContext = (ApplicationContext) other;

		return (((factoryClass == null) ? (otherContext.getFactoryClass() == null)
										: factoryClass.equals(otherContext.getFactoryClass()))
		&& ((systemProperties == null) ? (otherContext.getSystemProperties() == null)
									   : systemProperties.equals(otherContext.getSystemProperties()))
		&& ((url == null) ? (otherContext.getURL() == null) : url.equals(otherContext.getURL())));
	}
	
	/**
	 * Deep copy of this application Context
	 *
	 * @return depp copy of this application Context
	 */
	public Object clone() {
		try {
			// first call clone in Object
			Object myClone = super.clone();

			// then update all fields which are only a shallow copy
			ApplicationContextImpl anAppConClone = (ApplicationContextImpl) myClone;

			anAppConClone.setSystemProperties((java.util.TreeMap) this.systemProperties.clone());
			//VP clone already copy primitives and immutables 
			//anAppConClone.setFactoryClass(this.factoryClass);
			//anAppConClone.setURL(this.url);

			return anAppConClone;
		} catch (CloneNotSupportedException cnse) {
			//cnse.printStackTrace();
            throw new java.lang.RuntimeException("ApplicationContextImpl: Error invoking clone() CloneNotSupportedException, " +cnse.getMessage());

			//return null;
		}
	}
	
	public static ApplicationContextImpl getApplicationContext(Context aNamingContext) {
	try {
                                                                                                                                             
		return new ApplicationContextImpl(((String) aNamingContext.lookup( "factoryClass")),
											new java.util.HashMap(),
											((String) aNamingContext.lookup( "URL")));
	   } catch (NamingException ne) {
                                                                                                                                             
		 // System.out.println("+++++++++> VP :"+ne.toString());
					ne.printStackTrace();
		 return null;
	   }
	}
	
	
	/**
	 * Set the Factory Class Name.
	 *
	 * @param factoryClassName
	 */

	//fix to bug ID 4925234
	public void setFactoryClass(String factoryClass) throws java.lang.IllegalArgumentException {
		this.factoryClass = factoryClass;
	}
	
	/**
	 * Set the System properties
	 *
	 * @param properties
	 */

	//fix to bug ID 4925234
	public void setSystemProperties(java.util.Map systemProperties)
		throws java.lang.IllegalArgumentException {
		this.systemProperties = new java.util.TreeMap(systemProperties);
	}
	
	/**
	 * Set the URL
	 *
	 * @param URL
	 */

	//fix to bug ID 4925234
	public void setURL(String url) throws java.lang.IllegalArgumentException {
		this.url = url;
	}	
	
}
