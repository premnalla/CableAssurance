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
public class ReadOnlyAccess extends AccessPrivilegeBase 
	implements AccessPrivilege {

	private static Log log = LogFactory.getLog(ReadOnlyAccess.class);
	
	/**
	 * 
	 *
	 */
	private ReadOnlyAccess() {
		
	}
	
	/**
	 * 
	 * @param objName
	 */
	public ReadOnlyAccess(String objName) {
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
	 */
	public boolean writeAccess() {
		return false;
	}

}
