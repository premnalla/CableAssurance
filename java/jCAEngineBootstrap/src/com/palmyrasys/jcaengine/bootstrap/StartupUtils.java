/**
 * 
 */
package com.palmyrasys.jcaengine.bootstrap;

import java.io.File;
import java.io.IOException;

/**
 * @author Prem
 * 
 */
public final class StartupUtils {

	public static String setJCAEngineBase() {

		String jCAEngineBase = System
				.getProperty(GlobalConstants.JCAENGINE_BASE);

		if (jCAEngineBase == null) {
			jCAEngineBase = System.getProperty(GlobalConstants.USER_DIR);
			if (jCAEngineBase.endsWith("bin")) {
				jCAEngineBase += ".." + File.separator;
			}
			File base = new File(jCAEngineBase);
			if (!base.isAbsolute()) {
				try {
					jCAEngineBase = base.getCanonicalPath();
				} catch (IOException e) {
					jCAEngineBase = base.getAbsolutePath();
				}
			}
			System.setProperty(GlobalConstants.JCAENGINE_BASE, jCAEngineBase);
		}

		// String jCAHttpServerBase = jCAEngineBase + File.separator
		// 		+ GlobalConstants.JCAHTTP_SERVER_DIR;
		// System.setProperty(GlobalConstants.JCAHTTP_SERVER_BASE,
		// 		jCAHttpServerBase);

		System.setProperty(GlobalConstants.JCAHTTP_SERVER_BASE,
				jCAEngineBase);

		return (jCAEngineBase);
	}

	public static String setJCAEngineHome() {

		String jCAEngineHome = System
				.getProperty(GlobalConstants.JCAENGINE_HOME);

		if (jCAEngineHome == null) {
			jCAEngineHome = setJCAEngineBase();
			File home = new File(jCAEngineHome);
			if (!home.isAbsolute()) {
				try {
					jCAEngineHome = home.getCanonicalPath();
				} catch (IOException e) {
					jCAEngineHome = home.getAbsolutePath();
				}
			}
			System.setProperty(GlobalConstants.JCAENGINE_HOME, jCAEngineHome);
		}

		// String jCAHttpServerHome = jCAEngineHome + File.separator
		// 		+ GlobalConstants.JCAHTTP_SERVER_DIR;
		// System.setProperty(GlobalConstants.JCAHTTP_SERVER_HOME,
		// 		jCAHttpServerHome);

		System.setProperty(GlobalConstants.JCAHTTP_SERVER_HOME,
				jCAEngineHome);

		return (jCAEngineHome);
	}

}
