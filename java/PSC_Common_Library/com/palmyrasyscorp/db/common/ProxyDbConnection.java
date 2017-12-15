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
public abstract class ProxyDbConnection extends AbstractProxyDbConnection {

	private static Log log = LogFactory.getLog(ProxyDbConnection.class);
	
	/**
	 * 
	 *
	 */
	protected ProxyDbConnection() {
		super(null);
	}
	
	/**
	 * 
	 * @param c
	 */
	protected ProxyDbConnection(Connection c) {
		super(c);
	}
	
	/**
	 * 
	 */
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
}
