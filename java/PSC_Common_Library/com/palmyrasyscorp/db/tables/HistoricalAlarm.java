/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.LinkedList;
import java.util.List;
import java.math.BigInteger;

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
public class HistoricalAlarm extends AbstractAlarmWithInfo {

	private static Log log = LogFactory.getLog(HistoricalAlarm.class);

	private Long m_cleared_user_id;

	public Long getClearedUserId() {
		return m_cleared_user_id;
	}

	public HistoricalAlarm() {

	}

	public HistoricalAlarm(BigInteger rootAlarmId) {
		// m_root_alarm_id = rootAlarmId;
		super(rootAlarmId);
		querySelf();
	}

	public HistoricalAlarm(ProxyDbResultSet rs) {
		int i = 1;
		try {
			// root_alarm.*
			m_root_alarm_id = (BigInteger) rs.getObject(i++);
			m_resource_res_id = (Long) rs.getObject(i++);
			m_alarm_detection_time = (Long) rs.getObject(i++);
			m_alarm_detection_time_usec = (Long) rs.getObject(i++);
			m_alarm_type = (Integer) rs.getObject(i++);
			m_alarm_sub_type = (Integer) rs.getObject(i++);

			// historical_alarm.*
			m_id = (BigInteger) rs.getObject(i++);
			i++; // skip: root_alarm_id
			m_cleared_user_id = (Long) rs.getObject(i++);
			m_soak_duration = (Integer) rs.getObject(i++);
			m_alarm_state = (Integer) rs.getObject(i++);

			// alarm_types.*
			i++;
			i++; // skip type and sub-type
			m_alarm_type_str = (String) rs.getObject(i++);
			m_alarm_sub_type_str = (String) rs.getObject(i++);

			// alarm_states.*
			i++; // skip state
			m_alarm_state_str = (String) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * 
	 */
	public void querySelf() {
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
			String qryStr = GlobalQueries.QRY_HIST_ALARM + m_root_alarm_id;

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
				// root_alarm.*
				m_root_alarm_id = (BigInteger) rs.getObject(i++);
				m_resource_res_id = (Long) rs.getObject(i++);
				m_alarm_detection_time = (Long) rs.getObject(i++);
				m_alarm_detection_time_usec = (Long) rs.getObject(i++);
				m_alarm_type = (Integer) rs.getObject(i++);
				m_alarm_sub_type = (Integer) rs.getObject(i++);

				// historical_alarm.*
				m_id = (BigInteger) rs.getObject(i++);
				i++; // skip: root_alarm_id
				m_cleared_user_id = (Long) rs.getObject(i++);
				m_soak_duration = (Integer) rs.getObject(i++);
				m_alarm_state = (Integer) rs.getObject(i++);

				// alarm_types.*
				i++;
				i++; // skip type and sub-type
				m_alarm_type_str = (String) rs.getObject(i++);
				m_alarm_sub_type_str = (String) rs.getObject(i++);

				// alarm_states.*
				i++; // skip state
				m_alarm_state_str = (String) rs.getObject(i++);
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

	/**
	 * 
	 * @param fromSec
	 * @param toSec
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List<HistoricalAlarm> getAlarms(long fromSec, long toSec, short batchSize,
			long startId) {
		List<HistoricalAlarm> ret = new LinkedList<HistoricalAlarm>();

		boolean applyTimeFilter = false;
		boolean bailOut = false;

		if (batchSize < 0) {
			bailOut = true;
		}

		if (toSec > 0 && fromSec >= 0 && toSec > fromSec) {
			applyTimeFilter = true;
		} else if (toSec < 0 || fromSec < 0 || toSec < fromSec) {
			bailOut = true;
		}

		if (bailOut) {
			// System.out.println("CurrentAlarm::getAlarms(): Bailing out..");
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_HIST_ALARMS);
			if (applyTimeFilter) {
				qryStr.append(" where ra.detection_time>=").append(fromSec).append(
						" and ra.detection_time<=").append(toSec);
			}
			qryStr.append(GlobalQueries.QRY_HIST_ALARM_POSTFIX);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			short i = 0;
			seekToBeginningOfBatch(rs, startId);			
			while (rs.next()) {
				if (i < batchSize) {
					HistoricalAlarm alm = new HistoricalAlarm(rs);
					ret.add(alm);
				} else {
					break;
				}
				i++;
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

	public List<HistoricalAlarm> getAlarmsForResource(BigInteger resId) {
		List<HistoricalAlarm> ret = new LinkedList<HistoricalAlarm>();

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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_HIST_ALARMS);
			qryStr.append(" where ra.res_id=").append(resId);
			qryStr.append(GlobalQueries.QRY_HIST_ALARM_POSTFIX);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				HistoricalAlarm alm = new HistoricalAlarm(rs);
				ret.add(alm);
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

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @param fromSec
	 * @param toSec
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public List getAlarmsByType(String alarmType, String alarmSubType,
			long fromSec, long toSec, short batchSize, long startId) {
		LinkedList ret = new LinkedList();

		int[] alarmTypeInfo = getAlarmType(alarmType, alarmSubType);

		boolean applyTimeFilter = false;
		boolean bailOut = false;

		if (batchSize < 0) {
			bailOut = true;
		}

		if (toSec > 0 && fromSec >= 0 && toSec > fromSec) {
			applyTimeFilter = true;
		} else if (toSec < 0 || fromSec < 0 || toSec < fromSec) {
			bailOut = true;
		}

		if (bailOut) {
			// System.out.println("CurrentAlarm::getAlarms(): Bailing out..");
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
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.QRY_HIST_ALARMS);

			if (applyTimeFilter) {
				qryStr.append("where ra.detection_time>").append(fromSec)
						.append(" and ra.detection_time<=").append(toSec)
						.append(" and ");
			} else {
				qryStr.append("where ");
			}

			qryStr.append("ra.alarm_type=").append(alarmTypeInfo[0]).append(
					" and ra.alarm_sub_type=").append(alarmTypeInfo[1]);
			qryStr.append(GlobalQueries.QRY_HIST_ALARM_POSTFIX);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			short i = 0;
			seekToBeginningOfBatch(rs, startId);			
			while (rs.next()) {
				if (i < batchSize) {
					HistoricalAlarm alm = new HistoricalAlarm(rs);
					ret.add(alm);
				} else {
					break;
				}
				i++;
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
