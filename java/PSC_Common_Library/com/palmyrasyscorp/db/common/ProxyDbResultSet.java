/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.ResultSet;
// import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public class ProxyDbResultSet {

	private static Log log = LogFactory.getLog(ProxyDbResultSet.class);
	
	/**
	 * 
	 */
	private ResultSet m_rs;

	/**
	 * 
	 * 
	 */
	protected ProxyDbResultSet() {
		super();
	}

	/**
	 * 
	 * @param rs
	 */
	public ProxyDbResultSet(ResultSet rs) {
		m_rs = rs;
	}

	/**
	 * 
	 * @return
	 */
	public boolean next() {
		boolean ret = false;

		try {
			ret = m_rs.next();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Object getObject(int index) {
		Object ret = null;

		try {
			ret = m_rs.getObject(index);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getString(int index) {
		String ret = null;

		try {
			ret = m_rs.getString(index);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Long getLong(int index) {
		Long ret = null;

		try {
			ret = m_rs.getLong(index);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * 
	 */
	public void cleanup() {
		try {
			m_rs.close();
			// } catch (SQLException se) {
			// handleException(se);
		} catch (Exception e) {
			log.error(null, e);
		}
		m_rs = null;
	}

}
