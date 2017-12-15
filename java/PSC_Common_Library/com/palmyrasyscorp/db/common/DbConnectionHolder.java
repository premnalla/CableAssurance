/**
 * 
 */
package com.palmyrasyscorp.db.common;

/**
 * @author Prem
 *
 */
public class DbConnectionHolder {

	private ProxyDbConnection_Intfc m_connection;
	public void setConnection(ProxyDbConnection_Intfc c) {
		m_connection = c;
	}
	public ProxyDbConnection_Intfc getConnection() {
		return (m_connection);
	}
	
	/**
	 * 
	 *
	 */
	private DbConnectionHolder() {
		
	}
	
	/**
	 * 
	 * @param c
	 */
	public DbConnectionHolder(ProxyDbConnection_Intfc c) {
		m_connection = c;
	}
}
