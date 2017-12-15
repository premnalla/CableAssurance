/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.*;

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
public class MtaProvisionedHistory extends AbstractHistory implements History {

	private static Log log = LogFactory.getLog(MtaProvisionedHistory.class);
	
	public static String QRY_STRING = "select * from caperf.mta_prov_status_log ";

	private Long m_resId;

	private Integer m_provState;

	/**
	 * 
	 * @return
	 */
	public Integer getProvStatus() {
		return m_provState;
	}

	/**
	 * 
	 *
	 */
	protected MtaProvisionedHistory() {

	}

	/**
	 * 
	 * @param rs
	 */
	public MtaProvisionedHistory(ProxyDbResultSet rs) {
		int i = 1;
		try {
			i++; // id
			m_resId = (Long) rs.getObject(i++);
			m_timeSec = (Long) rs.getObject(i++);
			m_provState = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param resId
	 */
	public MtaProvisionedHistory(Long resId) {
		m_resId = resId;
	}

	/**
	 * 
	 */
	public List queryHistory(long fromSec, long toSec, short fromIndex,
			short toIndex) {
		LinkedList ret = new LinkedList();

		boolean applyIndexFilter = false;
		boolean applyTimeFilter = false;
		boolean bailOut = false;
		
		if (toIndex > 0 && fromIndex >= 0 && toIndex >= fromIndex) {
			applyIndexFilter = true;
		} else if (toIndex < 0 || fromIndex < 0 || toIndex < fromIndex) {
			bailOut = true;
		}
		
		if (toSec > 0 && fromSec >= 0 && toSec > fromSec) {
			applyTimeFilter = true;
		} else if (toSec < 0 || fromSec < 0 || toSec < fromSec) {
			bailOut = true;
		}

		if (bailOut) {
			return (ret);
		}
		
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
			StringBuffer qryStr = new StringBuffer(QRY_STRING);
			qryStr.append("WHERE mta_res_id=").append(m_resId);
			if (applyTimeFilter) {
				qryStr.append(" and tm_sec>").append(fromSec).append(" and tm_sec<=")
				.append(toSec);				
			}

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			short i = 0;
			while (rs.next()) {
				if (applyIndexFilter) {
					i++;
					if (i >= fromIndex && i <= toIndex) {
						MtaProvisionedHistory alm = new MtaProvisionedHistory(rs);
						ret.add(alm);
					} else if (i > toIndex) {
						break;
					}
				} else {
					MtaProvisionedHistory alm = new MtaProvisionedHistory(rs);
					ret.add(alm);
				}
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

		return (ret);
	}

}
