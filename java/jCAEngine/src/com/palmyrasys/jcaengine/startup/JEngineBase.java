/**
 * 
 */
package com.palmyrasys.jcaengine.startup;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.server.common.util.log.SystemLogHandler;
import com.palmyrasys.jcaengine.bootstrap.GlobalConstants;

/**
 * @author Prem
 * 
 */
public abstract class JEngineBase {

	/**
	 * 
	 */
	private static Log log = LogFactory.getLog(JEngineBase.class);

    /**
     * Use await.
     */
    protected boolean await = false;

    /**
     * Is standard streams redirection enabled ?
     */
    protected boolean redirectStreams = true;

    /**
	 * 
	 * 
	 */
	protected JEngineBase() {

	}

	protected void initDirs() {

		StartupUtils.setJCAEngineBase();
		StartupUtils.setJCAEngineHome();
		
		String temp = System.getProperty(GlobalConstants.JAVA_TEMP_DIR);
		if (temp == null || (!(new File(temp)).exists())
				|| (!(new File(temp)).isDirectory())) {
			// log.error(sm.getString("embedded.notmp", temp));
			log.error("java.io.tmp not set");
		}
	}

	/**
	 * 
	 * 
	 */
	protected void initNaming() {
		// Setting additional variables
	}

    public void setAwait(boolean b) {
        await = b;
    }

    protected void initStreams() {
        if (redirectStreams) {
            // Replace System.out and System.err with a custom PrintStream
            SystemLogHandler systemlog = new SystemLogHandler(System.out);
            System.setOut(systemlog);
            System.setErr(systemlog);
        }
    }
    
}
