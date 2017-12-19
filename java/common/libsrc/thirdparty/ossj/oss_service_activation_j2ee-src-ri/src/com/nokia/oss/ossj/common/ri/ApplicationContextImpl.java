/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */

package com.nokia.oss.ossj.common.ri;

import javax.oss.*;
import java.util.*;
import javax.naming.*;
import java.lang.reflect.*;

/**
 *
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class ApplicationContextImpl extends java.lang.Object implements javax.oss.ApplicationContext,  java.lang.Cloneable {
    
    /** Holds value of property factoryClass. */
    private String factoryClass;
    
    /** Holds value of property systemProperties. */
    private java.util.TreeMap systemProperties;
    
    /** Holds value of property url. */
    private String url;
    
    /** Creates new ApplicationContextImpl
     * @param factoryClass Name of the class which has to be used as INITIAL_CONTEXT_FACTORY when a Context is created
     * @param systemProperties additional properties, i.e. username and password
     * @param url Parameter of PROVIDER.URL during Context creation
     */
    public ApplicationContextImpl() {
        this.factoryClass = null;
        this.systemProperties = new java.util.TreeMap();
        this.url = null;
	}
    public ApplicationContextImpl(String factoryClass, Map systemProperties, String url) {
        this.factoryClass = factoryClass;
        this.systemProperties = new java.util.TreeMap(systemProperties);
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
    
    public boolean equals( Object other ) {
        if ( ! (other instanceof ApplicationContext)) {
            return false;
        }
        ApplicationContext otherContext = (ApplicationContext)other;
        return (
        ( factoryClass==null ? otherContext.getFactoryClass()==null: factoryClass.equals(otherContext.getFactoryClass()) ) &&
        ( systemProperties==null ? otherContext.getSystemProperties()==null: systemProperties.equals(otherContext.getSystemProperties()) ) &&
        ( url==null ? otherContext.getURL()==null: url.equals(otherContext.getURL()) ) );
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
            ApplicationContextImpl anAppConClone = (ApplicationContextImpl)myClone;
            
            anAppConClone.systemProperties = (java.util.TreeMap)systemProperties.clone();
            
            return anAppConClone;
            
        } catch (CloneNotSupportedException cnse) {
            System.out.println(cnse.toString());
            return null;
        }
    }
    
    /**
     * Creates an ApplicationContext based on the current server configuration
     */
    public static ApplicationContextImpl getApplicationContext(Context aNamingContext) {
        try {

            return new ApplicationContextImpl(((String) aNamingContext.lookup( "factoryClass")), 
						new java.util.HashMap(), 
						((String) aNamingContext.lookup( "URL")));
        } catch (NamingException ne) {
			
			ne.printStackTrace();
            return null;
        }
    }
    
    /**
     * Set the Factory Class Name.
     *
     * @param factoryClassName
     */
    public void setFactoryClass(String factoryClassName) throws java.lang.IllegalArgumentException {
        this.factoryClass = factoryClass;
    }
    
    /**
     * Set the System properties
     *
     * @param properties
     */
    public void setSystemProperties(java.util.Map properties) throws java.lang.IllegalArgumentException {
        this.systemProperties = new java.util.TreeMap(systemProperties);
    }
    
    /**
     * Set the URL
     *
     * @param URL
     */
    public void setURL(String URL) throws java.lang.IllegalArgumentException {
        this.url = url;
    }
    
}
