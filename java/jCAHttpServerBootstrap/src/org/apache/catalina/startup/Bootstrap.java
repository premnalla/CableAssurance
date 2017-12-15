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

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.apache.catalina.security.SecurityClassLoad;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Boostrap loader for Catalina. This application constructs a class loader for
 * use in loading the Catalina internal classes (by accumulating all of the JAR
 * files found in the "server" directory under
 * GlobalConstants.JCAHTTP_SERVER_HOME), and starts the regular execution of the
 * container. The purpose of this roundabout approach is to keep the Catalina
 * internal classes (and any other classes they depend on, such as an XML
 * parser) out of the system class path and therefore not visible to application
 * level classes.
 * 
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @version $Revision: 1.1 $ $Date: 2007/03/01 16:05:40 $
 */

public final class Bootstrap {

	private static Log log = LogFactory.getLog(Bootstrap.class);

	// -------------------------------------------------------------- Constants

	// ------------------------------------------------------- Static Variables

	/**
	 * Daemon object used by main.
	 */
	private static Bootstrap daemon = null;

	// -------------------------------------------------------------- Variables

	/**
	 * Daemon reference.
	 */
	private Object catalinaDaemon = null;

	private ClassLoader parentLoader = null;
	public void setParentClassLoader(ClassLoader cl) {
		parentLoader = cl;
	}

	protected ClassLoader commonLoader = null;

	protected ClassLoader catalinaLoader = null;

	protected ClassLoader sharedLoader = null;

	// -------------------------------------------------------- Constructors
	
	/**
	 * 
	 */
	public Bootstrap() {
		
	}
	
	// -------------------------------------------------------- Private Methods

	private void initClassLoaders() {
		try {
			commonLoader = 
				createClassLoader(GlobalConstants.JCAHTTP_COMMON_LOADER, parentLoader);
			if (commonLoader == null) {
				// no config file, default to this loader - we might be in a
				// 'single' env.
				commonLoader = this.getClass().getClassLoader();
			}
			catalinaLoader = 
				createClassLoader(GlobalConstants.JCAHTTP_SERVER_LOADER, commonLoader);
			// sharedLoader = createClassLoader("shared", commonLoader);
		} catch (Throwable t) {
			log.error("Class loader creation threw exception", t);
			System.exit(1);
		}
	}

	private ClassLoader createClassLoader(String name, ClassLoader parent)
			throws Exception {

		String value = CatalinaProperties.getProperty(name + ".loader");
		if ((value == null) || (value.equals("")))
			return parent;

		ArrayList repositoryLocations = new ArrayList();
		ArrayList repositoryTypes = new ArrayList();
		int i;

		StringTokenizer tokenizer = new StringTokenizer(value, ",");
		while (tokenizer.hasMoreElements()) {
			String repository = tokenizer.nextToken();

			// Local repository
			boolean replace = false;
			String before = repository;
			while ((i = repository.indexOf(GlobalConstants.JCAHTTP_HOME_TOKEN)) >= 0) {
				replace = true;
				if (i > 0) {
					repository = repository.substring(0, i)
							+ getCatalinaHome()
							+ repository.substring(i
									+ GlobalConstants.JCAHTTP_HOME_TOKEN
											.length());
				} else {
					repository = getCatalinaHome()
							+ repository
									.substring(GlobalConstants.JCAHTTP_HOME_TOKEN
											.length());
				}
			}
			while ((i = repository.indexOf(GlobalConstants.JCAHTTP_BASE_TOKEN)) >= 0) {
				replace = true;
				if (i > 0) {
					repository = repository.substring(0, i)
							+ getCatalinaBase()
							+ repository.substring(i
									+ GlobalConstants.JCAHTTP_BASE_TOKEN
											.length());
				} else {
					repository = getCatalinaBase()
							+ repository
									.substring(GlobalConstants.JCAHTTP_BASE_TOKEN
											.length());
				}
			}
			if (replace && log.isDebugEnabled())
				log.debug("Expanded " + before + " to " + replace);

			// Check for a JAR URL repository
			try {
				URL url = new URL(repository);
				repositoryLocations.add(repository);
				repositoryTypes.add(ClassLoaderFactory.IS_URL);
				continue;
			} catch (MalformedURLException e) {
				// Ignore
			}

			if (repository.endsWith("*.jar")) {
				repository = repository.substring(0, repository.length()
						- "*.jar".length());
				repositoryLocations.add(repository);
				repositoryTypes.add(ClassLoaderFactory.IS_GLOB);
			} else if (repository.endsWith(".jar")) {
				repositoryLocations.add(repository);
				repositoryTypes.add(ClassLoaderFactory.IS_JAR);
			} else {
				repositoryLocations.add(repository);
				repositoryTypes.add(ClassLoaderFactory.IS_DIR);
			}
		}

		String[] locations = (String[]) repositoryLocations
				.toArray(new String[0]);
		Integer[] types = (Integer[]) repositoryTypes.toArray(new Integer[0]);

		ClassLoader classLoader = ClassLoaderFactory.createClassLoader(
				locations, types, parent);

		// Retrieving MBean server
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer) MBeanServerFactory
					.findMBeanServer(null).get(0);
		} else {
			mBeanServer = MBeanServerFactory.createMBeanServer();
		}

		// Register the server classloader
		ObjectName objectName = new ObjectName(
				"Catalina:type=ServerClassLoader,name=" + name);
		mBeanServer.registerMBean(classLoader, objectName);

		return classLoader;

	}

	/**
	 * Initialize daemon.
	 */
	public void init() throws Exception {

		initClassLoaders();

		Thread.currentThread().setContextClassLoader(catalinaLoader);

		SecurityClassLoad.securityClassLoad(catalinaLoader);

		// Load our startup class and call its process() method
		if (log.isDebugEnabled())
			log.debug("Loading startup class");
		Class startupClass = catalinaLoader
				.loadClass(GlobalConstants.JCAHTTP_MAIN_CLASS);
		Object startupInstance = startupClass.newInstance();

		// Set the shared extensions class loader
		// if (log.isDebugEnabled())
		// 	log.debug("Setting startup class properties");
		// String methodName = "setParentClassLoader";
		// Class paramTypes[] = new Class[1];
		// paramTypes[0] = Class.forName("java.lang.ClassLoader");
		// Object paramValues[] = new Object[1];
		// paramValues[0] = sharedLoader;
		// Method method = startupInstance.getClass().getMethod(methodName,
		// 		paramTypes);
		// method.invoke(startupInstance, paramValues);

		catalinaDaemon = startupInstance;

	}

	/**
	 * Load daemon.
	 */
	private void load(String[] arguments) throws Exception {

		// Call the load() method
		String methodName = "load";
		Object param[];
		Class paramTypes[];
		if (arguments == null || arguments.length == 0) {
			paramTypes = null;
			param = null;
		} else {
			paramTypes = new Class[1];
			paramTypes[0] = arguments.getClass();
			param = new Object[1];
			param[0] = arguments;
		}
		Method method = catalinaDaemon.getClass().getMethod(methodName,
				paramTypes);
		if (log.isDebugEnabled())
			log.debug("Calling startup class " + method);
		method.invoke(catalinaDaemon, param);

	}

	// ----------------------------------------------------------- Main Program

	/**
	 * Load the Catalina daemon.
	 */
	public void init(String[] arguments) throws Exception {

		init();
		load(arguments);

	}

	/**
	 * Start the Catalina daemon.
	 */
	public void start() throws Exception {
		if (catalinaDaemon == null)
			init();

		Method method = catalinaDaemon.getClass().getMethod("start",
				(Class[]) null);
		method.invoke(catalinaDaemon, (Object[]) null);

	}

	/**
	 * Stop the Catalina Daemon.
	 */
	public void stop() throws Exception {

		Method method = catalinaDaemon.getClass().getMethod("stop",
				(Class[]) null);
		method.invoke(catalinaDaemon, (Object[]) null);

	}

	/**
	 * Stop the standlone server.
	 */
	public void stopServer() throws Exception {

		Method method = catalinaDaemon.getClass().getMethod("stopServer",
				(Class[]) null);
		method.invoke(catalinaDaemon, (Object[]) null);

	}

	/**
	 * Stop the standlone server.
	 */
	public void stopServer(String[] arguments) throws Exception {

		Object param[];
		Class paramTypes[];
		if (arguments == null || arguments.length == 0) {
			paramTypes = null;
			param = null;
		} else {
			paramTypes = new Class[1];
			paramTypes[0] = arguments.getClass();
			param = new Object[1];
			param[0] = arguments;
		}
		Method method = catalinaDaemon.getClass().getMethod("stopServer",
				paramTypes);
		method.invoke(catalinaDaemon, param);

	}

	/**
	 * Set flag.
	 */
	public void setAwait(boolean await) throws Exception {

		Class paramTypes[] = new Class[1];
		paramTypes[0] = Boolean.TYPE;
		Object paramValues[] = new Object[1];
		paramValues[0] = new Boolean(await);
		Method method = catalinaDaemon.getClass().getMethod("setAwait",
				paramTypes);
		method.invoke(catalinaDaemon, paramValues);

	}

	public boolean getAwait() throws Exception {
		Class paramTypes[] = new Class[0];
		Object paramValues[] = new Object[0];
		Method method = catalinaDaemon.getClass().getMethod("getAwait",
				paramTypes);
		Boolean b = (Boolean) method.invoke(catalinaDaemon, paramValues);
		return b.booleanValue();
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

    /**
     * Main method, used for testing only.
     *
     * @param args Command line arguments to be processed
     */
    public static void main(String args[]) {

        try {
            // Attempt to load JMX class
            new ObjectName("test:foo=bar");
        } catch (Throwable t) {
            System.out.println(GlobalConstants.JVM_VERSION_REQ);
            try {
                // Give users some time to read the message before exiting
                Thread.sleep(5000);
            } catch (Exception ex) {
            }
            return;
        }

        if (daemon == null) {
            daemon = new Bootstrap();
            try {
                daemon.init();
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
        }

        try {
            String command = "start";
            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if (command.equals("startd")) {
                args[0] = "start";
                daemon.load(args);
                daemon.start();
            } else if (command.equals("stopd")) {
                args[0] = "stop";
                daemon.stop();
            } else if (command.equals("start")) {
                daemon.setAwait(true);
                daemon.load(args);
                daemon.start();
            } else if (command.equals("stop")) {
                daemon.stopServer(args);
            } else {
                log.warn("Bootstrap: command \"" + command + "\" does not exist.");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


}
