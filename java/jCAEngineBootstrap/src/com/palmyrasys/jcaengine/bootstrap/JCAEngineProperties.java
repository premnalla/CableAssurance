/**
 * 
 */
package com.palmyrasys.jcaengine.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public class JCAEngineProperties {

	// ------------------------------------------------------- Static Variables

	private static Log log = LogFactory.getLog(JCAEngineProperties.class);

	private static Properties properties = null;

	static {

		loadProperties();
		// loadHttpServerProperties();

	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Return specified property value.
	 */
	public static String getProperty(String name) {

		return properties.getProperty(name);

	}

	/**
	 * Return specified property value.
	 */
	public static String getProperty(String name, String defaultValue) {

		return properties.getProperty(name, defaultValue);

	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Load properties.
	 */
	private static void loadProperties() {

		InputStream is = null;

		try {
			File properties = new File(getHome() + File.separator
					+ GlobalConstants.JCAENGINE_PROPERTIES_FILE);
			is = new FileInputStream(properties);
		} catch (Throwable t) {
			t.printStackTrace();
			log.error(t);
			// Ignore
			System.out.println("Exitting...");
			System.exit(-1);
		}

		try {
			properties = new Properties();
			properties.load(is);
			is.close();
		} catch (Throwable t) {
			t.printStackTrace();
			log.error(t);
			System.out.println("Exitting...");
			System.exit(-1);
		}

		// Register the properties as system properties
		Enumeration enumeration = properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = properties.getProperty(name);
			if (value != null) {
				System.setProperty(name, value);
			}
		}

	}

	/**
	 * Load Http Server properties.
	 */
	private static void loadHttpServerProperties() {

		InputStream is = null;

		try {
			File properties = new File(getHttpServerHome() + File.separator
					+ GlobalConstants.JCAHTTP_SERVER_PROPERTIES_FILE);
			is = new FileInputStream(properties);
		} catch (Throwable t) {
			t.printStackTrace();
			log.error(t);
			// Ignore
			System.out.println("Exitting...");
			System.exit(-1);
		}

		Properties props = null;
		
		try {
			props = new Properties();
			props.load(is);
			is.close();
		} catch (Throwable t) {
			t.printStackTrace();
			log.error(t);
			System.out.println("Exitting...");
			System.exit(-1);
		}

		// Register the properties as system properties
		Enumeration enumeration = props.propertyNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = props.getProperty(name);
			if (value != null) {
				properties.setProperty(name, value);
				System.setProperty(name, value);
			}
		}

	}

	/**
	 * Get the value of the jcaengine.home environment variable.
	 */
	public static String getHome() {
		String home = System.getProperty(GlobalConstants.JCAENGINE_HOME);
		if (home == null) {
			System.out.println(GlobalConstants.JCAENGINE_HOME + "not set!!! Exitting...");
			System.exit(-1);
		}
		return (home);
	}

	/**
	 * Get the value of the jcaengine.base environment variable.
	 */
	public static String getBase() {
		String base = System.getProperty(GlobalConstants.JCAENGINE_BASE);
		if (base == null) {
			System.out.println(GlobalConstants.JCAENGINE_BASE + "not set!!! Exitting...");
			System.exit(-1);
		}
		return (base);
	}

	/**
	 * Get the value of the jcaengine.home environment variable.
	 */
	public static String getHttpServerHome() {
		String home = System.getProperty(GlobalConstants.JCAHTTP_SERVER_HOME);
		if (home == null) {
			System.out.println(GlobalConstants.JCAHTTP_SERVER_HOME + "not set!!! Exitting...");
			System.exit(-1);
		}
		return (home);
	}

	/**
	 * Get the value of the jcaengine.base environment variable.
	 */
	public static String getHttpServerBase() {
		String base = System.getProperty(GlobalConstants.JCAHTTP_SERVER_BASE);
		if (base == null) {
			System.out.println(GlobalConstants.JCAHTTP_SERVER_BASE + "not set!!! Exitting...");
			System.exit(-1);
		}
		return (base);
	}
	
}
