/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Utility class to read the bootstrap Catalina configuration.
 * 
 * @author Remy Maucherat
 * @version $Revision: 1.1 $ $Date: 2007/03/01 16:05:40 $
 */

public class CatalinaProperties {

	// ------------------------------------------------------- Static Variables

	private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(CatalinaProperties.class);

	private static Properties properties = null;

	static {

		loadProperties();

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
			File properties = new File(getCatalinaHome() + File.separator
					+ GlobalConstants.SERVER_PROPERTIES_FILE);
			is = new FileInputStream(properties);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
			// Ignore
		}

		try {
			properties = new Properties();
			properties.load(is);
			is.close();
		} catch (Throwable t) {
			System.exit(-1);
		}

		// Register the properties as system properties
		Enumeration enumeration = properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = properties.getProperty(name);
			if (value != null) {
				System.setProperty(name, value);
				// System.out.println("Setting:" + name + "=" + value);
			}
		}

	}

	/**
	 * Get the value of the jcaengine.home environment variable.
	 */
	public static String getCatalinaHome() {
		String home = System.getProperty(GlobalConstants.JCAHTTP_SERVER_HOME);
		if (home == null) {
			System.out.println(GlobalConstants.JCAHTTP_SERVER_HOME
					+ "not set!!! Exitting...");
			System.exit(-1);
		}
		return (home);
	}

	/**
	 * Get the value of the jcaengine.base environment variable.
	 */
	public static String getCatalinaBase() {
		String base = System.getProperty(GlobalConstants.JCAHTTP_SERVER_BASE);
		if (base == null) {
			System.out.println(GlobalConstants.JCAHTTP_SERVER_BASE
					+ "not set!!! Exitting...");
			System.exit(-1);
		}
		return (base);
	}

}
