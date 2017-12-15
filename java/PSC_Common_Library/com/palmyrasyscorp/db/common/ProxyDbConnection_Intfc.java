/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.SQLException;

/**
 * @author Prem
 *
 */
public interface ProxyDbConnection_Intfc {

	// public void getConnection();
	
	// public void releaseConnection();
	
	// public boolean testConnection();
	
	/**
	 * 
	 */
	public void handleException(SQLException e);
	
	/**
	 * 
	 */
	public void cleanup();
	
}
