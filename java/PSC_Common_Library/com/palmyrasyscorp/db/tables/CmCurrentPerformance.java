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
public class CmCurrentPerformance extends AbstractDbObject {

	private static Log log = LogFactory.getLog(CmCurrentPerformance.class);

	public static String QRY_STRING = "select * from caperf.cm_current_perf where cm_res_id=";

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

	private Long m_sumTcaFreeTime;
	public Long getSumTcaFreeTime() { return m_sumTcaFreeTime; }
	
	private Long m_sumTcaTime;
	public Long getSumTcaTime() { return m_sumTcaTime; }
	
	private Long m_lastLogTime;
	public Long getLastLogTime() { return m_lastLogTime; }
	
	private Long m_tcaStateChangeTime;
	public Long getTcaStateChangeTime() { return m_tcaStateChangeTime; }
	
	private Integer m_tcaStateChanges;
	public Integer getTcaStateChanges() {return m_tcaStateChanges;}
	
	/**
	 * 
	 *
	 */
	protected CmCurrentPerformance() {

	}

	/**
	 * 
	 * @param rs
	 */
	public CmCurrentPerformance(ProxyDbResultSet rs) {
		int i = 1;
		try {
			i++; // id
			m_resId = (Long) rs.getObject(i++);			
			m_sumTcaFreeTime = (Long) rs.getObject(i++);
			m_sumTcaTime = (Long) rs.getObject(i++);
			m_lastLogTime = (Long) rs.getObject(i++);
			m_tcaStateChangeTime = (Long) rs.getObject(i++);
			m_t1Timeouts = (Long) rs.getObject(i++);
			m_t2Timeouts = (Long) rs.getObject(i++);
			m_t3Timeouts = (Long) rs.getObject(i++);
			m_t4Timeouts = (Long) rs.getObject(i++);			
			m_downstreamSNR = (Integer) rs.getObject(i++);
			m_downstreamPower = (Integer) rs.getObject(i++);
			m_upstreamPower = (Integer) rs.getObject(i++);			
			m_tcaStateChanges = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param resId
	 */
	public CmCurrentPerformance(Long resId) {
		m_resId = resId;
		querySelf();
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
			String qryStr = QRY_STRING + m_resId;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				short i = 1;
				i++; // id;
				i++; // m_resId = (Long) rs.getObject(i++);
				m_sumTcaFreeTime = (Long) rs.getObject(i++);
				m_sumTcaTime = (Long) rs.getObject(i++);
				m_lastLogTime = (Long) rs.getObject(i++);
				m_tcaStateChangeTime = (Long) rs.getObject(i++);
				m_t1Timeouts = (Long) rs.getObject(i++);
				m_t2Timeouts = (Long) rs.getObject(i++);
				m_t3Timeouts = (Long) rs.getObject(i++);
				m_t4Timeouts = (Long) rs.getObject(i++);			
				m_downstreamSNR = (Integer) rs.getObject(i++);
				m_downstreamPower = (Integer) rs.getObject(i++);
				m_upstreamPower = (Integer) rs.getObject(i++);			
				m_tcaStateChanges = (Integer) rs.getObject(i++);
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
