/**
 * 
 */
package com.palmyrasys.jcaengine.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.reflect.Method;

// import org.apache.catalina.Container;
// import org.apache.catalina.Lifecycle;
// import org.apache.catalina.LifecycleException;
// import org.apache.catalina.core.StandardServer;
// import org.apache.catalina.startup.ClusterRuleSetFactory;
// import org.apache.catalina.startup.ConnectorCreateRule;
// import org.apache.catalina.startup.ContextRuleSet;
// import org.apache.catalina.startup.EngineRuleSet;
// import org.apache.catalina.startup.HostRuleSet;
// import org.apache.catalina.startup.NamingRuleSet;
// import org.apache.catalina.startup.SetAllPropertiesRule;
// import org.apache.tomcat.util.digester.Digester;
// import org.apache.tomcat.util.digester.Rule;

import com.palmyrasys.server.common.Server;
import com.palmyrasys.server.common.Lifecycle;
import com.palmyrasys.server.common.LifecycleException;
import com.palmyrasys.server.common.core.StandardServer;
import com.palmyrasys.jcaengine.bootstrap.ClassLoaderFactory;
import com.palmyrasys.jcaengine.bootstrap.GlobalConstants;
import com.palmyrasys.server.common.util.digester.Digester;
// import com.palmyrasys.server.common.util.digester.Rule;

// import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public class JCAEngine extends JEngineBase {

	private static Log log = LogFactory.getLog(JCAEngine.class);

	// ----------------------------------------------------- Instance Variables

	/**
	 * Pathname to the server configuration file.
	 */
	protected String configFileName = GlobalConstants.SERVER_CONF_FILE;

	/**
	 * The shared extensions class loader for this server.
	 */
	protected ClassLoader parentClassLoader = JCAEngine.class.getClassLoader();

	/**
	 * The server component we are starting or stopping
	 */
	protected Server server = null;

	/**
	 * Are we starting a new server?
	 */
	protected boolean starting = false;

	/**
	 * Are we stopping an existing server?
	 */
	protected boolean stopping = false;

	/**
	 * Use shutdown hook flag.
	 */
	protected boolean useShutdownHook = true;

	/**
	 * Shutdown hook.
	 */
	protected Thread shutdownHook = null;

	// ------------------------------------------------------------- Properties

	/**
	 * 
	 */
	public JCAEngine() {

	}

	public void setConfigFileName(String file) {
		configFileName = file;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setUseShutdownHook(boolean useShutdownHook) {
		this.useShutdownHook = useShutdownHook;
	}

	public boolean getUseShutdownHook() {
		return useShutdownHook;
	}

	/**
	 * Set the shared extensions class loader.
	 * 
	 * @param parentClassLoader
	 *            The shared extensions class loader.
	 */
	public void setParentClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}

	/**
	 * Set the server instance we are configuring.
	 * 
	 * @param server
	 *            The new server
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	// ----------------------------------------------------------- Main Program

	/**
	 * The application main program.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String args[]) {
		(new JCAEngine()).process(args);
	}

	/**
	 * The instance main program.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public void process(String args[]) {

		setAwait(true);

		StartupUtils.setJCAEngineBase();
		StartupUtils.setJCAEngineHome();

		try {
			if (arguments(args)) {
				if (starting) {
					load(args);
					start();
				} else if (stopping) {
					stopServer();
					// stop();
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	// ------------------------------------------------------ Protected Methods

	/**
	 * Process the specified command line arguments, and return
	 * <code>true</code> if we should continue processing; otherwise return
	 * <code>false</code>.
	 * 
	 * @param args
	 *            Command line arguments to process
	 */
	protected boolean arguments(String args[]) {

		if (args.length < 1) {
			usage();
			return (false);
		}

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("start")) {
				starting = true;
				stopping = false;
			} else if (args[i].equals("stop")) {
				starting = false;
				stopping = true;
			} else {
				usage();
				return (false);
			}
		}

		return (true);

	}

	/**
	 * Return a File object representing our configuration file.
	 */
	protected File getConfigFile() {

		File file = new File(System.getProperty(GlobalConstants.JCAENGINE_HOME)
				+ File.separator + configFileName);

		return (file);

	}

	/**
	 * Create and configure the Digester we will be using for startup.
	 */
	protected Digester createStartDigester() {

		long t1 = System.currentTimeMillis();

		// Initialize the digester
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.setClassLoader(StandardServer.class.getClassLoader());

		// Configure the actions we will be using
		digester
				.addObjectCreate("Server",
						"com.palmyrasys.server.common.core.StandardServer",
						"className");
		digester.addSetProperties("Server");
		digester.addSetNext("Server", "setServer",
				"com.palmyrasys.server.common.Server");

		long t2 = System.currentTimeMillis();
		if (log.isDebugEnabled())
			log.debug("Digester for server.xml created " + (t2 - t1));
		return (digester);

	}

	/**
	 * Create and configure the Digester we will be using for shutdown.
	 */
	protected Digester createStopDigester() {

		// Initialize the digester
		Digester digester = new Digester();

		// Configure the rules we need for shutting down
		digester
				.addObjectCreate("Server",
						"com.palmyrasys.server.common.core.StandardServer",
						"className");
		digester.addSetProperties("Server");
		digester.addSetNext("Server", "setServer",
				"com.palmyrasys.server.common.Server");

		return (digester);

	}

	public void stopServer() {
		stopServer(null);
	}

	public void stopServer(String[] arguments) {

		if (arguments != null) {
			arguments(arguments);
		}

		if (server == null) {
			// Create and execute our Digester
			Digester digester = createStopDigester();
			digester.setClassLoader(Thread.currentThread()
					.getContextClassLoader());
			File file = getConfigFile();
			try {
				InputSource is = new InputSource("file://"
						+ file.getAbsolutePath());
				FileInputStream fis = new FileInputStream(file);
				is.setByteStream(fis);
				digester.push(this);
				digester.parse(is);
				fis.close();
			} catch (Exception e) {
				log.error("JCAEngine.stop: ", e);
				System.exit(1);
			}
		}

		// Stop the existing server
		try {
			Socket socket = new Socket("127.0.0.1", server.getPort());
			OutputStream stream = socket.getOutputStream();
			String shutdown = server.getShutdown();
			for (int i = 0; i < shutdown.length(); i++)
				stream.write(shutdown.charAt(i));
			stream.flush();
			stream.close();
			socket.close();
		} catch (IOException e) {
			log.error("JCAEngine.stop: ", e);
			System.exit(1);
		}

	}

	/**
	 * Start a new server instance.
	 */
	public void load() {

		initDirs();

		// Before digester - it may be needed

		initNaming();

		// Create and execute our Digester
		Digester digester = createStartDigester();
		long t1 = System.currentTimeMillis();

		Exception ex = null;
		InputSource inputSource = null;
		InputStream inputStream = null;
		File file = null;
		try {
			file = getConfigFile();
			inputStream = new FileInputStream(file);
			inputSource = new InputSource("file://" + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			inputSource.setByteStream(inputStream);
			digester.push(this);
			digester.parse(inputSource);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Stream redirection
		initStreams();

		// Start the new server
		if (server instanceof Lifecycle) {
			try {
				server.initialize();
			} catch (LifecycleException e) {
				log.error("JCAEngine.start: ", e);
			}
		}

		long t2 = System.currentTimeMillis();
		if (log.isInfoEnabled())
			log.info("Initialization processed in " + (t2 - t1) + " ms");

	}

	/*
	 * Load using arguments
	 */
	public void load(String args[]) {

		try {
			if (arguments(args))
				load();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	/**
	 * 
	 * 
	 */
	public void create() {
	}

	/**
	 * 
	 * 
	 */
	public void destroy() {
	}

	/**
	 * Start a new server instance.
	 */
	public void start() {

		if (server == null) {
			load();
		}

		long t1 = System.currentTimeMillis();

		System.out.println("Starting StandardServer");
		
		// Start the new server
		if (server instanceof Lifecycle) {
			try {
				((Lifecycle) server).start();
			} catch (LifecycleException e) {
				log.error("JCAEngine.start: ", e);
			}
		}

		System.out.println("Started StandardServer");
		
		long t2 = System.currentTimeMillis();
		if (log.isInfoEnabled())
			log.info("Server startup in " + (t2 - t1) + " ms");

		try {
			// Register shutdown hook
			if (useShutdownHook) {
				if (shutdownHook == null) {
					shutdownHook = new JCAEngineShutdownHook();
				}
				Runtime.getRuntime().addShutdownHook(shutdownHook);
			}
		} catch (Throwable t) {
		}

		try {
			startChildren();
		} catch (Exception e) {
		}

		if (await) {
			await();
			stop();
		}

	}

	private void startChildren() {

		try {

			System.out.println("about to exec init(args)");

			ClassLoader commonLoader = this.getClass().getClassLoader()
					.getParent();
			Object bootstrapInstance = getHttpServerBootstrapInstance(commonLoader);

			// Invoke the "start" method in the bootstrap class
			if (log.isDebugEnabled())
				log.debug("Attempting to invoke start method in http server bootstrap");
			String methodName = "init";
			String[] arguments = new String[1];
			arguments[0] = "start";
			
			Object param[] = new Object[1];
			Class paramTypes[] = new Class[1];
			paramTypes[0] = arguments.getClass();
			param[0] = arguments;
			
			Method method = bootstrapInstance.getClass().getMethod(methodName,
					paramTypes);
			method.invoke(bootstrapInstance, param);
			
			System.out.println("executed init(args)");
			
			methodName = "start";
			method = bootstrapInstance.getClass().getMethod(methodName,
					(Class[]) null);
			method.invoke(bootstrapInstance, (Object[]) null);
		} catch (Exception ex) {
			System.out.println("Failed to invoke bootstrap of httpserver");
			ex.printStackTrace();
		}
		
	}

	private void stopChildren() {

		try {
			ClassLoader commonLoader = this.getClass().getClassLoader()
					.getParent();
			Object bootstrapInstance = getHttpServerBootstrapInstance(commonLoader);

			// Invoke the "start" method in the bootstrap class
			if (log.isDebugEnabled())
				log.debug("Attempting to invoke stop method in http server bootstrap");
			String methodName = "stop";
			Method method = bootstrapInstance.getClass().getMethod(methodName,
					(Class[]) null);
			method.invoke(bootstrapInstance, (Object[]) null);
		} catch (Exception ex) {

		}

	}

	private Object getHttpServerBootstrapInstance(ClassLoader parent) {
		String[] locations = new String[1];
		String jar = System.getProperty(GlobalConstants.JCAHTTP_SERVER_HOME)
				+ File.separator + "bin/bootstrap.jar";
		locations[0] = jar;
		Integer[] types = new Integer[1];
		types[0] = ClassLoaderFactory.IS_JAR;

		Object bootstrapInstance = null;

		try {
			ClassLoader classLoader = ClassLoaderFactory.createClassLoader(
					locations, types, parent);
			Class bootstrapClass = classLoader
					.loadClass("org.apache.catalina.startup.Bootstrap");
			bootstrapInstance = bootstrapClass.newInstance();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return (bootstrapInstance);
	}

	/**
	 * Stop an existing server instance.
	 */
	public void stop() {

		try {
			// Remove the ShutdownHook first so that server.stop()
			// doesn't get invoked twice
			if (useShutdownHook) {
				Runtime.getRuntime().removeShutdownHook(shutdownHook);
			}
		} catch (Throwable t) {
		}

		try {
			stopChildren();
		} catch (Exception e) {
		}

		// Shut down the server
		if (server instanceof Lifecycle) {
			try {
				((Lifecycle) server).stop();
			} catch (LifecycleException e) {
				log.error("JCAEngine.stop", e);
			}
		}

	}

	/**
	 * Await and shutdown.
	 */
	public void await() {
		// server.await();
	}

	/**
	 * Print usage information for this application.
	 */
	protected void usage() {

		System.out
				.println("usage: java com.palmyrasys.jcaengine.startup.JCAEngine"
						+ " {start | stop}");

	}

	// --------------------------------------- JCAEngineShutdownHook Inner Class

	/**
	 * Shutdown hook which will perform a clean shutdown of JCAEngine if needed.
	 */
	protected class JCAEngineShutdownHook extends Thread {

		public void run() {

			if (server != null) {
				JCAEngine.this.stop();
			}

		}

	}

}
