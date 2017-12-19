package com.nokia.oss.ossj.common.ri;

import javax.oss.*;
import java.util.*;
import javax.naming.*;
import java.lang.reflect.*;

/**
 *
 * @author BanuPrasad Dhanakoti Nokia Networks
 * @version 1.1
 * January 2002
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
    public ApplicationContextImpl(String factoryClass, Map systemProperties, String url) {
        this.factoryClass = factoryClass;
        if (systemProperties != null) 
            this.systemProperties = new java.util.TreeMap(systemProperties);
        else 
            this.systemProperties = new java.util.TreeMap(); 
        
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
