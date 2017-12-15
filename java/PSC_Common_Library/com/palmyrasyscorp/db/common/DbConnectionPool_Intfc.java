/**
 * 
 */
package com.palmyrasyscorp.db.common;

/**
 * @author Prem
 *
 */
public interface DbConnectionPool_Intfc {

	/**
	 * 
	 * @return
	 */
	public ProxyDbConnection getConnection();
	
	/**
	 * 
	 * @param c
	 */
	public void releaseConnection(ProxyDbConnection c);
	
}
