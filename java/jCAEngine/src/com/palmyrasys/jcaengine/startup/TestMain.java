/**
 * 
 */
package com.palmyrasys.jcaengine.startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class TestMain {

	private static Log log = LogFactory.getLog(TestMain.class);

	public TestMain() {
		
	}
	
	/**
	 * Load using arguments
	 */
	public void load(String args[]) {

	}

	/**
	 * Start a new server instance.
	 */
	public void start() {

		MyWorker t = new MyWorker();
		t.start();
		
	}
		
}

final class MyWorker extends Thread {
	
	/**
	 * Overloaded Thread::run().
	 */
	public void run() {

		boolean forever = true;
		
		try {
			while (forever) {
				Thread.sleep(5000);
				// System.out.println("jCAEngine worker thread alive and kicking");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Exitting jCAEngine worker...");
	}
	
}
