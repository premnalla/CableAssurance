/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public interface AccessPrivilege {

	/**
	 * 
	 * @return
	 */
	public boolean readAccess();
	
	/**
	 * 
	 * @return
	 */
	public boolean writeAccess();
	
}
