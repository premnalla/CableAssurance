/**
 * 
 */
package com.palmyrasys.jcaengine.bootstrap;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.palmyrasys.server.common.security.SecurityClassLoad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public final class Bootstrap {

	private static Log log = LogFactory.getLog(Bootstrap.class);

	// -------------------------------------------------------------- Constants

	// ------------------------------------------------------- Static Variables

	/**
	 * Daemon object used by main.
	 */
	private static Bootstrap m_daemon = null;

	// -------------------------------------------------------------- Variables

	/**
	 * Daemon reference.
	 */
	private Object m_jCAEngineDaemon = null;

	private Object m_jCAHttpServerDaemon = null;

	private ClassLoader m_jCAEngineCommonLoader = null;

	public ClassLoader getJCAEngineCommonLoader() {
		return m_jCAEngineCommonLoader;
	}

	private ClassLoader m_jCAEngineServerLoader = null;

	// private ClassLoader jCAHttpCommonLoader = null;

	// private ClassLoader jCAHttpServerLoader = null;

	// -------------------------------------------------------- Private Methods

	/**
	 * 
	 */
	private void initClassLoaders() {
		try {

			/*
			 * Check for jcaengine_common.loader
			 */
			ClassLoader myLoader = this.getClass().getClassLoader();

			m_jCAEngineCommonLoader = createClassLoader(
			// 		GlobalConstants.JCAENGINE_COMMON_LOADER, null);
					GlobalConstants.JCAHTTP_COMMON_LOADER, myLoader);

			if (m_jCAEngineCommonLoader == null) {
				System.out.println("Unable to create common class loader");
				System.exit(1);
				m_jCAEngineCommonLoader = this.getClass().getClassLoader();
			}

			/*
			 * Check for jcaengine_server.loader
			 */
			m_jCAEngineServerLoader = createClassLoader(
					GlobalConstants.JCAHTTP_SERVER_LOADER,
					m_jCAEngineCommonLoader);

			// jCAHttpCommonLoader = createClassLoader(
			// 		GlobalConstants.JCAHTTP_COMMON_LOADER,
			// 		m_jCAEngineCommonLoader);

			// jCAHttpServerLoader = createClassLoader(
			// 		GlobalConstants.JCAHTTP_SERVER_LOADER, jCAHttpCommonLoader);

		} catch (Throwable t) {
			t.printStackTrace();
			log.error("Class loader creation threw exception", t);
			System.exit(1);
		}
	}

	/**
	 * Creates Class loader and makes association with paretn class loader
	 * 
	 * @param name
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	private ClassLoader createClassLoader(String name, ClassLoader parent)
			throws Exception {

		String props = name + ".loader";
		System.out.println(props);
		String value = JCAEngineProperties.getProperty(props);
		if ((value == null) || (value.equals(""))) {
			System.out.println(props + "not found");
			return parent;
		}
		System.out.println(value);

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
							+ getHttpServerHome()
							+ repository.substring(i
									+ GlobalConstants.JCAHTTP_HOME_TOKEN
											.length());
				} else {
					repository = getHttpServerHome()
							+ repository
									.substring(GlobalConstants.JCAHTTP_HOME_TOKEN
											.length());
				}
			}
			while ((i = repository.indexOf(GlobalConstants.JCAHTTP_BASE_TOKEN)) >= 0) {
				replace = true;
				if (i > 0) {
					repository = repository.substring(0, i)
							+ getHttpServerHome()
							+ repository.substring(i
									+ GlobalConstants.JCAHTTP_BASE_TOKEN
											.length());
				} else {
					repository = getHttpServerHome()
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

		/*
		 * Ask the ClassLoaderFactory for class loader
		 */
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
				"jCAEngine:type=ServerClassLoader,name=" + name);
		mBeanServer.registerMBean(classLoader, objectName);

		return classLoader;

	}

	/**
	 * Initialize daemon.
	 */
	public void init() throws Exception {

		/*
		 * Set jCAEngine path NOTE: order is important. First Base, then Home.
		 */
		StartupUtils.setJCAEngineBase();
		StartupUtils.setJCAEngineHome();

		initClassLoaders();

		Class startupClass;
		Object startupInstance;

		Thread.currentThread().setContextClassLoader(m_jCAEngineServerLoader);

		SecurityClassLoad.securityClassLoad(m_jCAEngineServerLoader);

		// Load our startup class and call its process() method
		if (log.isDebugEnabled())
			log.debug("Loading jCAEngine startup class");

		startupClass = m_jCAEngineServerLoader
			.loadClass(GlobalConstants.JCAENGINE_MAIN_CLASS);
		startupInstance = startupClass.newInstance();

		m_jCAEngineDaemon = startupInstance;

		// Thread.currentThread().setContextClassLoader(jCAHttpServerLoader);

		// SecurityClassLoad.securityClassLoad(jCAHttpServerLoader);

		// Load our startup class and call its process() method
		if (log.isDebugEnabled())
			log.debug("Loading jCAHttpServer startup class");

		startupClass = m_jCAEngineServerLoader
				.loadClass(GlobalConstants.JCAHTTP_SERVER_MAIN_CLASS);
		startupInstance = startupClass.newInstance();

		m_jCAHttpServerDaemon = startupInstance;
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

		Method method;

		/*
		 * Invoke: JCAEngine.load(String[] args);
		 */
		method = m_jCAEngineDaemon.getClass().getMethod(methodName,
		paramTypes);
		if (log.isDebugEnabled())
			log.debug("Calling jCAEngine startup class " + method);
		method.invoke(m_jCAEngineDaemon, param);
		
		/*
		 * Invoke: Catalina.load(String[] args);
		 */
		method = m_jCAHttpServerDaemon.getClass().getMethod(methodName,
				paramTypes);
		if (log.isDebugEnabled())
			log.debug("Calling jCAHttpServer startup class " + method);
		method.invoke(m_jCAHttpServerDaemon, param);
	}

	// ----------------------------------------------------------- Main Program

	/**
	 * Start the jCAEngine daemon.
	 */
	public void start() throws Exception {
		// if (m_jCAEngineDaemon == null)
		// init();

		Method method;

		/*
		 * Invoke: JCAEngine.start();
		 */
		method = m_jCAEngineDaemon.getClass().getMethod("start", (Class[]) null);
		method.invoke(m_jCAEngineDaemon, (Object[]) null);
		
		/*
		 * Invoke: Catalina.start();
		 */
		method = m_jCAHttpServerDaemon.getClass().getMethod("start", (Class[]) null);
		method.invoke(m_jCAHttpServerDaemon, (Object[]) null);
	}

	/**
	 * Stop the jCAEngine Daemon.
	 */
	public void stop() throws Exception {
		/*
		 * Invoke: JCAEngine.stop();
		 */
		Method method = m_jCAEngineDaemon.getClass().getMethod("stop",
				(Class[]) null);
		method.invoke(m_jCAEngineDaemon, (Object[]) null);
	}

	/**
	 * Stop the standlone server.
	 */
	public void stopServer() throws Exception {
		/*
		 * Invoke: JCAEngine.stopServer();
		 */
		Method method = m_jCAEngineDaemon.getClass().getMethod("stopServer",
				(Class[]) null);
		method.invoke(m_jCAEngineDaemon, (Object[]) null);
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

		/*
		 * Invoke: JCAEngine.stopServer(String[] args);
		 */
		Method method = m_jCAEngineDaemon.getClass().getMethod("stopServer",
				paramTypes);
		method.invoke(m_jCAEngineDaemon, param);
	}

	/**
	 * Set flag.
	 */
	public void setAwait(boolean await) throws Exception {

		Class paramTypes[] = new Class[1];
		paramTypes[0] = Boolean.TYPE;
		Object paramValues[] = new Object[1];
		paramValues[0] = new Boolean(await);

		/*
		 * Invoke: JCAEngine.setAwait(boolean);
		 */
		Method method = m_jCAEngineDaemon.getClass().getMethod("setAwait",
				paramTypes);
		method.invoke(m_jCAEngineDaemon, paramValues);
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            Command line arguments to be processed
	 */
	public static void main(String args[]) {

		try {

			// Attempt to load JMX class
			ObjectName oN = new ObjectName("jCAEngine:foo=bar");

		} catch (Throwable t) {

			System.out.println(GlobalConstants.JVM_VERSION_REQ);
			try {
				// Give users some time to read the message before exiting
				Thread.sleep(5000);
			} catch (Exception ex) {
			}
			return;

		}

		if (m_daemon == null) {

			m_daemon = new Bootstrap();

			try {

				m_daemon.init();

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

			if (command.equals("start")) {

				// m_daemon.setAwait(true);
				m_daemon.load(args);
				m_daemon.start();

			} else if (command.equals("stop")) {

				m_daemon.stopServer(args);

			} else {

				log.warn("Bootstrap: command \"" + command
						+ "\" does not exist.");
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Get the value of the jcaengine.home environment variable.
	 */
	public static String getHome() {
		String home = System.getProperty(GlobalConstants.JCAENGINE_HOME);
		if (home == null) {
			System.out.println(GlobalConstants.JCAENGINE_HOME
					+ "not set!!! Exitting...");
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
			System.out.println(GlobalConstants.JCAENGINE_BASE
					+ "not set!!! Exitting...");
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
			System.out.println(GlobalConstants.JCAHTTP_SERVER_HOME
					+ "not set!!! Exitting...");
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
			System.out.println(GlobalConstants.JCAHTTP_SERVER_BASE
					+ "not set!!! Exitting...");
			System.exit(-1);
		}
		return (base);
	}

}
