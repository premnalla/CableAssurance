package ossj.ttri;

import javax.oss.ApplicationContext;

/**
 * ApplicationContext Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class ApplicationContextImpl implements ApplicationContext,
        java.io.Serializable {

    public String url = null;
    public String factoryClass = null;
    public java.util.Map map = null;

    public void setSystemProperties(java.util.Map map) {
    }

    public Object clone() {

        ApplicationContextImpl newVal = new ApplicationContextImpl();
        if (url != null) {
            newVal.setURL(new String(getURL()));
        } else
            newVal.setURL(null);
        if (factoryClass != null) {
            newVal.setFactoryClass(new String(getFactoryClass()));
        } else
            newVal.setFactoryClass(null);

        return newVal;
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
     * The URL of the JNDI provider where the Home interfaces
     * of the Session Bean(s) in charge of the entity could be found.
     *
     * @return URL of the JNDI provider
     */
    public String getURL() {
        return url;
    }

    /**
     * Other properties that may be necessay and
     * vendor specific to setup an initial context with the
     * JNDI Provider
     *
     * @return Map of system properties
     */
    public java.util.Map getSystemProperties() {
        return map;
    }

    public void setURL(String value) {
        log("setURL: " + value);
        url = value;
    }

    public void setFactoryClass(String value) {
        log("setFactoryClass: " + value);
        factoryClass = value;
    }

    private void log(String s) {
        Logger.log(s);
    }


}
