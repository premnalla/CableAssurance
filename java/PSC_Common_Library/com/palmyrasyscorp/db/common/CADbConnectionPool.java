/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class CADbConnectionPool extends AbstractDbConnectionPool {

	private static Log log = LogFactory.getLog(CADbConnectionPool.class);
	
	private static CADbConnectionPool m_instance = null;
	
	/**
	 * 
	 *
	 */
	private CADbConnectionPool() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static CADbConnectionPool getInstance() {
		if (m_instance == null) {
			m_instance = new CADbConnectionPool();
			m_instance.init();
		}
		return (m_instance);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getDriverClass() {
		String ret = null;
		
		ret = "com.mysql.jdbc.Driver";
		
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getConnectionUrl() {
		String ret = null;
		
		// ret = "jdbc:mysql://localhost/canet?user=root&password=manager";
		ret = "jdbc:mysql://192.168.1.104/canet?user=root&password=manager";
		
		return (ret);		
	}
	
	/**
	 * 
	 * @return
	 */
	protected int getNumConnections() {
		int ret = 1;
		
		// ret = "jdbc:mysql://localhost/canet?user=root&password=manager";
		
		return (ret);		
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	protected ProxyDbConnection instantiateProxyConnection(Connection c) {
		ProxyDbConnection ret = new ProxyDbCAConnection(c);
		return (ret);
	}
	
}
