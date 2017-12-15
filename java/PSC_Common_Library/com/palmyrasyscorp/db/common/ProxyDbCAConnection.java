/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class ProxyDbCAConnection extends ProxyDbConnection {

	private static Log log = LogFactory.getLog(ProxyDbCAConnection.class);
	

	/**
	 * 
	 *
	 */
	protected ProxyDbCAConnection() {
		super();
	}
	
	/**
	 * 
	 * @param c
	 */
	public ProxyDbCAConnection(Connection c) {
		super(c);
	}
	
	/**
	 * 
	 * @param e
	 */
	public void handleException(SQLException e) {
		if (e.getSQLState().equals("08S01")) {
			/*
			 * Reconnect
			 */
			Connection c = CADbConnectionPool.getInstance().createNewConnection();
			setConnection(c);
		}
	}

	/**
	 * 
	 *
	 */
	public void cleanup() {
		CADbConnectionPool.getInstance().releaseConnection(this);
	}
	
}
