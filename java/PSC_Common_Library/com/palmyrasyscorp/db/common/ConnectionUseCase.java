/**
 * 
 */
package com.palmyrasyscorp.db.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class ConnectionUseCase {

	private static Log log = LogFactory.getLog(ConnectionUseCase.class);

	/**
	 * 
	 *
	 */
	public ConnectionUseCase() {
		try {
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
	}
	
	
}
