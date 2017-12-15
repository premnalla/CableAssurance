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
public class CmCurrentStatus extends AbstractCurrentStatus {

	private static Log log = LogFactory.getLog(CmCurrentStatus.class);

	public CmCurrentStatus(Long resId) {
		super(resId);
		querySelf();
	}

	public CmCurrentStatus(ProxyDbResultSet rs) {
		int i = 1;
		try {
			i++; // skip 'id' field
			m_resId = (Long) rs.getObject(i++);
			m_sumGoodStateTime = (Long) rs.getObject(i++);
			m_sumBadStateTime = (Long) rs.getObject(i++);
			m_lastLogTime = (Long) rs.getObject(i++);
			m_lastStateChangeTime = (Long) rs.getObject(i++);
			m_sumStateChanges = (Integer) rs.getObject(i++);
			m_currentState = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CURRENT_CM_STATUS);
			qryStr.append(m_resId);

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
				i++; // skip id;
				i++; // m_resId = (Long) rs.getObject(i++);
				m_sumGoodStateTime = (Long) rs.getObject(i++);
				m_sumBadStateTime = (Long) rs.getObject(i++);
				m_lastLogTime = (Long) rs.getObject(i++);
				m_lastStateChangeTime = (Long) rs.getObject(i++);
				m_sumStateChanges = (Integer) rs.getObject(i++);
				m_currentState = (Integer) rs.getObject(i++);
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
