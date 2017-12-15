/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 *
 */
public class EmtaSecondary extends AbstractDbObject {

	private static Log log = LogFactory.getLog(EmtaSecondary.class);

	private Long m_id;
	public Long getId() {
		return m_id;
	}

	private Long m_emta_res_id;
	public Long getEmtaResId() {
		return m_emta_res_id;
	}

	private String m_phone1;
	public String getPhone1() {
		return m_phone1;
	}
	
	private String m_phone2;
	public String getPhone2() {
		return m_phone2;
	}	
	
	/**
	 * 
	 *
	 */
	protected EmtaSecondary() {

	}
	
	/**
	 * 
	 * @param emta_res_id
	 */
	public EmtaSecondary(long emta_res_id) {
		m_emta_res_id = new Long(emta_res_id);
		querySelf();
	}
	
	/**
	 * 
	 * @param rs
	 */
	public EmtaSecondary(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_emta_res_id = (Long) rs.getObject(i++);
			m_phone1 = rs.getString(i++);
			m_phone2 = rs.getString(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 */
	private void querySelf() {
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer("SELECT * FROM canet.emta_secondary WHERE ");
			qryStr.append("emta_res_id=").append(m_emta_res_id);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				m_id = (Long) rs.getObject(i++);
				m_emta_res_id = (Long) rs.getObject(i++);
				m_phone1 = rs.getString(i++);
				m_phone2 = rs.getString(i++);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (rs != null) {
				rs.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

	}

}
