package ossj.qos;

import javax.oss.ApplicationContext;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.Context;

import java.lang.reflect.Method;
import ossj.qos.util.Util;

/**
 * ApplicationContextImpl
 *
 * @author  
 * @version 1.0
 *
 */
public class ApplicationContextImpl implements ApplicationContext {

    private Map systemProps = null;
    private String factoryName = null;
    private String url = null;

    /** Creates new ApplicationContextImpl */
    public ApplicationContextImpl() {
    }
  public ApplicationContextImpl(Properties p) {
    factoryName = p.getProperty(Context.INITIAL_CONTEXT_FACTORY);

    systemProps = new HashMap();
    String securityPrincipal = p.getProperty(Context.SECURITY_PRINCIPAL);
    String securityCredentials = p.getProperty(Context.SECURITY_CREDENTIALS);
    systemProps.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
    systemProps.put(Context.SECURITY_CREDENTIALS, securityCredentials);

    url = p.getProperty(Context.PROVIDER_URL);
  }


    /**
     * Deep copy of this key
     *
     * @return deep copy of the application context
     */
    public Object clone() {
        ApplicationContextImpl obj = null;

        try {
            obj = (ApplicationContextImpl) super.clone();
            obj.systemProps = (Map)Util.clone( systemProps );
        }
        catch ( Exception cex ) {
            //System.out.println("Problem cloning ApplicationContextImpl");
        }
        return obj;
    }

    /**
     * The name of the factory class required to setup the
     * initial context with the JNDI provider
     *
     * @return name of factory class
     */
    public String getFactoryClass() {
        return factoryName;
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

    /**
     * Other properties that may be necessary and
     * vendor specific to setup an initial context with the
     * JNDI Provider
     *
     * @return Map of system properties
     */
    public java.util.Map getSystemProperties() {
        return systemProps;
    }

    /** The name of the factory class required to setup the
     * initial context with the JNDI provider
     *
     * @param factoryClassName name of the factory class
     */
    public void setFactoryClass(String factoryClassName) {
        factoryName = factoryClassName;
        return;
    }

    /** The URL of the JNDI provider where the Home interfaces
     * of the Session Bean(s) in charge of the entity could be found.
     *
     * @param url of the JNDI provider
     */
    public void setURL(String URL) {
        url = URL;
        return;
    }

    /** Other properties that may be necessary and
     * vendor specific to setup an initial context with the
     * JNDI Provider
     *
     * @param properties the system properties
     */
    public void setSystemProperties(java.util.Map properties) {
        systemProps = properties;
        return;
    }

    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in ApplicationContextImpl instance are equal to the
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof ApplicationContextImpl ) {
            ApplicationContextImpl objAppContext = (ApplicationContextImpl)anObject;
            isEqual = ( Util.isEqual( factoryName, objAppContext.factoryName ) &&
                        Util.isEqual( url, objAppContext.url ) &&
                        Util.isEqual( systemProps, objAppContext.systemProps ) );
        }
        return isEqual;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(300);
        buf.append( Util.rightJustify(30,"factory class name = ") + factoryName + "\n" );
        buf.append( Util.rightJustify(30,"URL = ") + url + "\n" );
        buf.append( Util.rightJustify(30,"system properties = ") + Util.printObject( systemProps ) );

        return buf.toString();
    }

   // added Oct 3, 2002 -- FDP
   public int hashCode() {
	int rval = -1;
	if (this.getURL() != null) {
		rval ^= this.getURL().hashCode();
	}
	if (this.getFactoryClass() != null) {
		rval ^= this.getFactoryClass().hashCode();
	}
	return rval;
   }

}
