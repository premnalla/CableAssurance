/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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
public class AlarmHistory extends AbstractDbObject {

	private static Log log = LogFactory.getLog(AlarmHistory.class);

	private Long m_id;
	private BigInteger m_rootAlmId;
	
	private Timestamp m_ts;
	public Timestamp getTimestamp() { return m_ts; }
	
	private Integer m_almState;
	public Integer getAlarmState() {return m_almState;}
	
	private String m_almStateStr;
	public String getAlarmStateStr() { return m_almStateStr; }
	
	private String m_almStateMetaData;
	public String getExtraInfo() { return m_almStateMetaData; }
	
	/**
	 * 
	 *
	 */
	private AlarmHistory() {
		super();
	}

//	/**
//	 * 
//	 * @param rootAlarmId
//	 */
//	public AlarmHistory(BigInteger rootAlarmId) {
//		super();
//		m_rootAlmId = rootAlarmId;
//		querySelf();
//	}

	/**
	 * 
	 * @param rs
	 */
	public AlarmHistory(ProxyDbResultSet rs) {
		int i = 1;
		try {
			// alarm_state_history.*
			m_id = (Long) rs.getObject(i++);
			m_rootAlmId = (BigInteger) rs.getObject(i++);
			m_ts = (Timestamp) rs.getObject(i++);
			m_almState = (Integer) rs.getObject(i++);

			// alarm_state_metadata.*
			i++; // skip
			i++; // skip
			m_almStateMetaData = (String) rs.getObject(i++);
			
			// alarm_states.*
			i++; // skip
			m_almStateStr = (String) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
	}

//	/**
//	 * 
//	 * 
//	 */
//	public void querySelf() {
//		ProxyDbConnection c = null;
//		ProxyDbStatement stmt = null;
//		ProxyDbResultSet rs = null;
//
//		try {
//			/*
//			 * Get connection
//			 */
//			c = CADbConnectionPool.getInstance().getConnection();
//
//			/*
//			 * Create sql
//			 */
//			String qryStr = GlobalQueries.QRY_ALARM_STATE_HISTORY + m_rootAlmId;
//
//			/*
//			 * Create statement
//			 */
//			stmt = c.createStatement();
//
//			/*
//			 * execute query
//			 */
//			rs = stmt.executeQuery(qryStr.toString());
//
//			if (rs.next()) {
//				int i = 1;
//				// alarm_state_history.*
//				m_id = (Long) rs.getObject(i++);
//				m_rootAlmId = (BigInteger) rs.getObject(i++);
//				i++; // timestamp
//				m_almState = (Integer) rs.getObject(i++);
//
//				// alarm_state_metadata.*
//				i++; // skip
//				i++; // skip
//				m_almStateMetaData = (String) rs.getObject(i++);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);
//		} finally {
//			if (stmt != null) {
//				stmt.cleanup();
//			}
//			if (rs != null) {
//				rs.cleanup();
//			}
//			if (c != null) {
//				c.cleanup();
//			}
//		}
//
//	}

	/**
	 * 
	 * @return
	 */
	public static List<AlarmHistory> GetHistories(BigInteger rootAlmId) {
		List<AlarmHistory> ret = new LinkedList<AlarmHistory>();

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
			String qryStr = GlobalQueries.QRY_ALARM_STATE_HISTORY + rootAlmId;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				AlarmHistory ah = new AlarmHistory(rs);
				ret.add(ah);
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
