/**
 * 
 */
package org.apache.catalina.startup;

import java.io.File;

/**
 * @author Prem
 * 
 */
public final class GlobalConstants {

	public static final String JVM_VERSION_REQ = "Require JVM 1.5 or higher";

	public static final String JCAHTTP_COMMON_LOADER = "common";

	public static final String JCAHTTP_SERVER_LOADER = "server";

	public static final String JCAHTTP_MAIN_CLASS = "org.apache.catalina.startup.Catalina";

	public static final String JCAHTTP_SERVER_PROPERTIES = "jcahttpserver.properties";

	public static final String JCAHTTP_SERVER_PROPERTIES_PKG = 
		"/org/apache/catalina/startup/catalina.properties";

	public static final String USER_DIR = "user.dir";

	public static final String JAVA_TEMP_DIR = "java.io.tmpdir";

	public static final String BOOTSTRAP_JAR = "bootstrap.jar";

	public static final String JCAHTTP_HOME_TOKEN = "${jcahttpserver.home}";

	public static final String JCAHTTP_BASE_TOKEN = "${jcahttpserver.base}";

	public static final String JCAHTTP_SERVER_HOME = "jcahttpserver.home";

	public static final String JCAHTTP_SERVER_BASE = "jcahttpserver.base";

	public static final String SERVER_CONF_FILE = "conf" + File.separator
			+ "jcahttpserver.xml";

	public static final String SERVER_PROPERTIES_FILE = "conf" + File.separator
			+ JCAHTTP_SERVER_PROPERTIES;

	private GlobalConstants() {

	}
}
