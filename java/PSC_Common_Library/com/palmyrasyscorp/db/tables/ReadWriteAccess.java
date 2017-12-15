/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class ReadWriteAccess extends AccessPrivilegeBase {

	private static Log log = LogFactory.getLog(ReadWriteAccess.class);
	
	/**
	 * 
	 *
	 */
	private ReadWriteAccess() {
		
	}
	
	/**
	 * 
	 * @param objName
	 */
	public ReadWriteAccess(String objName) {
		setObjectName(objName);
	}
	
	/**
	 * 
	 */
	public boolean readAccess() {
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean writeAccess() {
		return true;
	}

}
