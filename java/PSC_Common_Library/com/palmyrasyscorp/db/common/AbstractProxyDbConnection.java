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
public abstract class AbstractProxyDbConnection implements
		ProxyDbConnection_Intfc {

	private static Log log = LogFactory.getLog(AbstractProxyDbConnection.class);

	/**
	 * 
	 */
	private static int _connId = 1;

	/**
	 * 
	 */
	private Integer m_connId = new Integer(_connId++);

	/**
	 * Underlying Vendor specific connection
	 */
	private Connection m_conn;

	/**
	 * 
	 * 
	 */
	private AbstractProxyDbConnection() {
		super();
	}

	/**
	 * 
	 * @param c
	 */
	protected AbstractProxyDbConnection(Connection c) {
		super();
		m_conn = c;
	}

	/**
	 * 
	 * @return
	 */
	public Object getKey() {
		return (m_connId);
	}

	/**
	 * 
	 */
	public int hashCode() {
		return (m_connId.intValue());
	}

	public boolean equals(Object o) {
		boolean ret = false;

		try {
			AbstractProxyDbConnection oo = (AbstractProxyDbConnection) o;
			if (m_connId.hashCode() == oo.hashCode()) {
				ret = true;
			}

			// Above, OR
			// if (m_connId.equals((Integer)oo.getKey())) {
			// ret = true;
			// }

		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * Used to set/reset conneciton by sub-classes
	 * 
	 * @param c
	 */
	protected void setConnection(Connection c) {
		m_conn = c;
	}

	/**
	 * 
	 * @return
	 */
	public ProxyDbStatement createStatement() {
		ProxyDbStatement ret;

		while ((ret = _createStatement()) == null) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				log.error(null, e);
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	public ProxyDbStatement createStatement(int resultSetType,
			int resultSetConcurrency) {
		ProxyDbStatement ret;

		while ((ret = _createStatement(resultSetType, resultSetConcurrency)) == null) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				log.error(null, e);
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	protected ProxyDbStatement _createStatement() {
		ProxyDbStatement ret = null;

		try {
			ret = new ProxyDbStatement(m_conn.createStatement());
		} catch (SQLException e) {
			handleException(e);
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	protected ProxyDbStatement _createStatement(int resultSetType,
			int resultSetConcurrency) {
		ProxyDbStatement ret = null;

		try {
			ret = new ProxyDbStatement(m_conn.createStatement(resultSetType,
					resultSetConcurrency));
		} catch (SQLException e) {
			handleException(e);
		}

		return (ret);
	}

}
