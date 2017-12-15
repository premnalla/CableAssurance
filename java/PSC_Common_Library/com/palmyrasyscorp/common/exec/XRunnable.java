/**
 * 
 */
package com.palmyrasyscorp.common.exec;

/**
 * @author Prem
 *
 */
public interface XRunnable {

	/**
	 * 
	 *
	 */
	public void preRun();
	
	/**
	 * 
	 *
	 */
	public void run();
	
	/**
	 * 
	 *
	 */
	public void postRun();
	
}
