/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

;

/**
 * @author Prem
 * 
 */
public class RootAlarm extends AbstractAlarm {

	private static Log log = LogFactory.getLog(RootAlarm.class);

	private BigInteger m_rootAlarmId;

	public BigInteger getRootAlarmId() {
		return m_rootAlarmId;
	}

	private Long m_resId;

	public Long getResId() {
		return m_resId;
	}

	private Long m_detectionTime;

	public Long getDetectionTime() {
		return m_detectionTime;
	}

	private Long m_detectionTimeUSec;

	public Long getDetectionTimeUSec() {
		return m_detectionTimeUSec;
	}

	private Integer m_alarmType;

	public Integer getAlarmType() {
		return m_alarmType;
	}

	private Integer m_alarmSubType;

	public Integer getAlarmSubType() {
		return m_alarmSubType;
	}

	/**
	 * 
	 * 
	 */
	protected RootAlarm() {

	}

	/**
	 * 
	 * @param rs
	 */
	public RootAlarm(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_rootAlarmId = (BigInteger) rs.getObject(i++);
			m_resId = (Long) rs.getObject(i++);
			m_detectionTime = (Long) rs.getObject(i++);
			m_detectionTimeUSec = (Long) rs.getObject(i++);
			m_alarmType = (Integer) rs.getObject(i++);
			m_alarmSubType = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			log.error(null, e);
		}
	}

	/**
	 * 
	 * @param rootAlmId
	 */
	public RootAlarm(BigInteger rootAlmId) {
		m_rootAlarmId = rootAlmId;
		querySelf();
	}

	/**
	 * 
	 * @param resId
	 * @param detTime
	 * @param detTimeUSec
	 * @param almType
	 * @param almSubType
	 */
	public RootAlarm(Long resId, Long detTime, Long detTimeUSec,
			Integer almType, Integer almSubType) {
		m_resId = resId;
		m_detectionTime = detTime;
		m_detectionTimeUSec = detTimeUSec;
		m_alarmType = almType;
		m_alarmSubType = almSubType;
	}

	/**
	 * 
	 * @return
	 */
	public boolean insertRow() {
		boolean ret = false;

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
			StringBuffer qryStr = new StringBuffer(
					"insert into root_alarm (root_alarm_id,res_id,detection_time,det_time_usec,alarm_type,alarm_sub_type) ");
			qryStr.append("values(").append(m_resId).append(",").append(
					m_detectionTime).append(",").append(m_detectionTimeUSec)
					.append(",").append(m_alarmType).append(",").append(
							m_alarmSubType).append(")");
			// System.out.println(insStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			/*
			 * execute query
			 */
			stmt.executeUpdate(qryStr.toString(),
					Statement.RETURN_GENERATED_KEYS);

			rs = stmt.getGeneratedKeys();

			int i = 1;
			if (rs.next()) {
				m_rootAlarmId = (BigInteger) rs.getObject(i++);
			}

			ret = true;

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	/**
	 * 
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
			String qryStr = "SELECT * FROM caalarm.root_alarm WHERE root_alarm_id="
				+ m_rootAlarmId;

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
				i++; // m_rootAlarmId = (BigInteger) rs.getObject(i++);
				m_resId = (Long) rs.getObject(i++);
				m_detectionTime = (Long) rs.getObject(i++);
				m_detectionTimeUSec = (Long) rs.getObject(i++);
				m_alarmType = (Integer) rs.getObject(i++);
				m_alarmSubType = (Integer) rs.getObject(i++);
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
