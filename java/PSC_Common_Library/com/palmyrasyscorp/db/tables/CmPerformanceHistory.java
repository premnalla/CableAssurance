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
public class CmPerformanceHistory extends AbstractHistory implements History {

	private static Log log = LogFactory.getLog(CmPerformanceHistory.class);

	public static String QRY_STRING = "select * from caperf.cm_perf_log ";

	private Long m_resId;

	private Integer m_downstreamSNR;
	public Integer getDownstreamSNR() {return m_downstreamSNR;}
	
	private Integer m_downstreamPower;
	public Integer getDownstreamPower() {return m_downstreamPower;}
	
	private Integer m_upstreamPower;
	public Integer getUpstreamPower() {return m_upstreamPower;}
	
	private Long m_t1Timeouts;
	public Long getT1Timeouts() {return m_t1Timeouts;}
	
	private Long m_t2Timeouts;
	public Long getT2Timeouts() {return m_t2Timeouts;}
	
	private Long m_t3Timeouts;
	public Long getT3Timeouts() {return m_t3Timeouts;}
	
	private Long m_t4Timeouts;
	public Long getT4Timeouts() {return m_t4Timeouts;}	

	/**
	 * 
	 *
	 */
	protected CmPerformanceHistory() {

	}

	/**
	 * 
	 * @param rs
	 */
	public CmPerformanceHistory(ProxyDbResultSet rs) {
		int i = 1;
		try {
			i++; // id
			m_resId = (Long) rs.getObject(i++);
			m_timeSec = (Long) rs.getObject(i++);
			m_t1Timeouts = (Long) rs.getObject(i++);
			m_t2Timeouts = (Long) rs.getObject(i++);
			m_t3Timeouts = (Long) rs.getObject(i++);
			m_t4Timeouts = (Long) rs.getObject(i++);
			m_downstreamSNR = (Integer) rs.getObject(i++);
			m_downstreamPower = (Integer) rs.getObject(i++);
			m_upstreamPower = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param resId
	 */
	public CmPerformanceHistory(Long resId) {
		m_resId = resId;
	}

	/**
	 * 
	 */
	public List<CmPerformanceHistory> queryHistory(long fromSec, long toSec, short fromIndex,
			short toIndex) {
		LinkedList<CmPerformanceHistory> ret = new LinkedList<CmPerformanceHistory>();

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
			qryStr.append("WHERE cm_res_id=").append(m_resId);
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
						CmPerformanceHistory alm = new CmPerformanceHistory(rs);
						ret.add(alm);
					} else if (i > toIndex) {
						break;
					}
				} else {
					CmPerformanceHistory alm = new CmPerformanceHistory(rs);
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
