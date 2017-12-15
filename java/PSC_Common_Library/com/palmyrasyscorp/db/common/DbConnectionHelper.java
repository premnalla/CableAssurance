/**
 * 
 */
package com.palmyrasyscorp.db.common;

/**
 * @author Prem
 *
 */
public class DbConnectionHelper {

	private DbConnection m_readConn;
	private DbConnection m_writeConn;
	
	public DbConnectionHelper() {
		
	}
	
	protected void finalize() throws Throwable {
		/*
		 * Do our freeing/cleanup
		 */
		if (m_readConn != null) {
			// DbConnectionPool.getInstance().releaseWriteConnection(m_readConn);
			m_readConn = null;
		}

		if (m_writeConn != null) {
			DbConnectionPool.getInstance().releaseWriteConnection(m_writeConn);
			m_writeConn = null;
		}
		
		/*
		 * Finally call parents finalize()
		 */
		super.finalize();
	}
	
	public DbConnection getReadConnection() {
		if (m_readConn == null) {
			m_readConn = DbConnectionPool.getInstance().getConnection();
		}
		return (m_readConn);
	}
	
	public DbConnection getWriteConnection () {
		if (m_writeConn == null) {
			m_writeConn = DbConnectionPool.getInstance().getWriteConnection();
		}
		return (m_writeConn);		
	}
}
