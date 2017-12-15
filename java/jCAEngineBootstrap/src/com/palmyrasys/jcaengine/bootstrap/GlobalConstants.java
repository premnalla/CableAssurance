/**
 * 
 */
package com.palmyrasys.jcaengine.bootstrap;

import java.io.File;

/**
 * @author Prem
 * 
 */
public final class GlobalConstants {

	public static final String JCAENGINE_HOME = "jcaengine.home";

	public static final String JCAENGINE_BASE = "jcaengine.base";

	public static final String USER_DIR = "user.dir";

	public static final String JAVA_TEMP_DIR = "java.io.tmpdir";

	public static final String BOOTSTRAP_JAR = "bootstrap.jar";

	public static final String JCAENGINE_HOME_TOKEN = "${" + JCAENGINE_HOME + "}";

	public static final String JCAENGINE_BASE_TOKEN = "${" + JCAENGINE_BASE + "}";

	public static final String JVM_VERSION_REQ = "Require JVM 1.5 or higher";

	public static final String JCAENGINE_PROPERTIES = "jcaengine.properties";

	public static final String JCAENGINE_JAR_PROPERTIES = 
		"/com/palmyrasys/jcaengine/startup/jcaengine.properties";

	public static final String JCAENGINE_CONF_FILE = "jcaengine.xml";

	public static final String JCAENGINE_COMMON_LOADER = "jcaengine_common";
	// public static final String JCAENGINE_COMMON_LOADER = "common";

	public static final String JCAENGINE_SERVER_LOADER = "jcaengine_server";
	// public static final String JCAENGINE_SERVER_LOADER = "server";

	// public static final String JCAENGINE_MAIN_CLASS = "com.palmyrasys.jcaengine.startup.JCAEngine";
	public static final String JCAENGINE_MAIN_CLASS = "com.palmyrasys.jcaengine.startup.TestMain";

	public static final String JCAENGINE_PROPERTIES_FILE = "conf" + File.separator
			+ JCAENGINE_PROPERTIES;

	public static final String SERVER_CONF_FILE = "conf" + File.separator
	+ JCAENGINE_CONF_FILE;

	public static final String JCAHTTP_SERVER_HOME = "jcahttpserver.home";

	public static final String JCAHTTP_SERVER_BASE = "jcahttpserver.base";

	public static final String JCAHTTP_HOME_TOKEN = "${" + JCAHTTP_SERVER_HOME+ "}";

	public static final String JCAHTTP_BASE_TOKEN = "${" + JCAHTTP_SERVER_BASE + "}";

	public static final String JCAHTTP_SERVER_DIR = "jCAHttpServer";

	public static final String JCAHTTP_COMMON_LOADER = "common";

	public static final String JCAHTTP_SERVER_LOADER = "server";

	public static final String JCAHTTP_SERVER_PROPERTIES = "jcahttpserver.properties";

	public static final String JCAHTTP_SERVER_PROPERTIES_FILE = "conf" + File.separator
	+ JCAHTTP_SERVER_PROPERTIES;

	public static final String JCAHTTP_SERVER_PROPERTIES_PKG = 
		"/org/apache/catalina/startup/jcahttpserver.properties";

	public static final String JCAHTTP_SERVER_MAIN_CLASS = "org.apache.catalina.startup.Catalina";

	private GlobalConstants() {

	}
}
