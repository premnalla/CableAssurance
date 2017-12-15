/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import sun.awt.Mutex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public abstract class AbstractDbConnectionPool implements
		DbConnectionPool_Intfc {

	private static Log log = LogFactory.getLog(AbstractDbConnectionPool.class);
	
	private HashMap<Integer, ProxyDbConnection> m_connMap = new HashMap<Integer, ProxyDbConnection>();

	private LinkedList m_freeList = new LinkedList();

	private Mutex m_freeListLock = new Mutex();

	private LinkedList m_usedList = new LinkedList();

	/**
	 * 
	 * @return
	 */
	protected abstract String getDriverClass();

	/**
	 * 
	 * @return
	 */
	protected abstract String getConnectionUrl();

	/**
	 * 
	 * @return
	 */
	protected abstract int getNumConnections();

	/**
	 * 
	 * @param c
	 * @return
	 */
	protected abstract ProxyDbConnection instantiateProxyConnection(Connection c);
	
	/**
	 * 
	 * 
	 */
	protected AbstractDbConnectionPool() {
		super();
	}

	/**
	 * 
	 */
	public ProxyDbConnection getConnection() {
		ProxyDbConnection ret = null;

		try {
			while ((ret = _getConnection()) == null) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public void releaseConnection(ProxyDbConnection c) {
		addConnection(c);
	}

	/**
	 * 
	 */
	protected ProxyDbConnection _getConnection() {
		ProxyDbConnection ret;

		// This
		m_freeListLock.lock();
		if (m_freeList.size() > 0) {
			ret = (ProxyDbConnection) m_freeList.remove();
		} else {
			ret = null;
		}
		m_freeListLock.unlock();

		// OR, This
		// m_freeListLock.lock();
		// Iterator<ProxyDbConnection> iter = m_connMap.values().iterator();
		// if (iter.hasNext()) {
		// ret = iter.next();
		// } else {
		// ret = null;
		// }
		// m_freeListLock.unlock();

		return (ret);
	}

	/**
	 * Called to create a brand new connection in the event an existing
	 * connection has timed-out or lost connectivity for some reason.
	 * 
	 * @return
	 */
	public Connection createNewConnection() {
		Connection ret = null;

		try {
			ret = DriverManager.getConnection(getConnectionUrl());
		} catch (SQLException ex) {
			// handle any errors
			log.error(null, ex);
		}

		return (ret);
	}

	/**
	 * 
	 * 
	 */
	protected void init() {

		try {
			Class.forName(getDriverClass()).newInstance();
		} catch (Exception ex) {
			log.error(null, ex);
		}

		for (int i = 0, maxConns = getNumConnections(); i < maxConns; i++) {
			try {
				Connection conn = DriverManager.getConnection(getConnectionUrl());
				ProxyDbConnection c = instantiateProxyConnection(conn);
				addConnection(c);
				
				System.out.println("Got connection");
				
			} catch (SQLException ex) {
				// handle any errors
				log.error(null, ex);
			}
		}
	}
	
	/**
	 * 
	 * @param c
	 */
	protected void addConnection(ProxyDbConnection c) {
		// m_freeListLock.lock();
		m_freeList.add(c);
		// m_freeListLock.unlock();
	}

}
