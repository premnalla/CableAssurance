/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;
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
public class CurrentAlarm extends AbstractAlarmWithInfo {

	private static Log log = LogFactory.getLog(CurrentAlarm.class);

	public CurrentAlarm() {
		super(null);
	}

	public CurrentAlarm(BigInteger rootAlarmId) {
		// m_root_alarm_id = rootAlarmId;
		super(rootAlarmId);
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public CurrentAlarm(ProxyDbResultSet rs) {
		int i = 1;
		try {
			// root_alarm.*
			m_root_alarm_id = (BigInteger) rs.getObject(i++);
			m_resource_res_id = (Long) rs.getObject(i++);
			m_alarm_detection_time = (Long) rs.getObject(i++);
			m_alarm_detection_time_usec = (Long) rs.getObject(i++);
			m_alarm_type = (Integer) rs.getObject(i++);
			m_alarm_sub_type = (Integer) rs.getObject(i++);

			// current_alarm.*
			m_id = (BigInteger) rs.getObject(i++);
			i++; // skip: root_alarm_id
			m_soak_duration = (Integer) rs.getObject(i++);
			m_alarm_state = (Integer) rs.getObject(i++);

			// alarm_types.*
			i++; // skip type and sub-type
			i++;
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
			String qryStr = GlobalQueries.QRY_CURR_ALARM + m_root_alarm_id;

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

				// current_alarm.*
				m_id = (BigInteger) rs.getObject(i++);
				i++; // skip: root_alarm_id
				m_soak_duration = (Integer) rs.getObject(i++);
				m_alarm_state = (Integer) rs.getObject(i++);

				// alarm_types.*
				i++; // skip type and sub-type
				i++;
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
	 * @return
	 */
	public List<CurrentAlarm> getAlarms(long fromSec, long toSec,
			short batchSize, long startId) {
		List<CurrentAlarm> ret = new LinkedList<CurrentAlarm>();

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

		String states = QueryMetaData.GetMetaData(QueryMetaData.CURRENT_ALARM_STATE);

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.QRY_CURR_ALARMS);
			if (applyTimeFilter) {
				qryStr.append("where ra.detection_time>").append(fromSec)
						.append(" and ra.detection_time<=").append(toSec);
			}
			
			if (states != null) {
				if (!applyTimeFilter) {
					qryStr.append(" where ");
				} else {
					qryStr.append(" and ");
				}
				qryStr.append(" ca.alarm_state ").append(states);
			}
			
			qryStr.append(GlobalQueries.QRY_CURR_ALARM_POSTFIX);

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
					CurrentAlarm alm = new CurrentAlarm(rs);
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

	/**
	 * 
	 * @return
	 */
	public List<CurrentAlarm> getAlarmsForResource(BigInteger resId) {
		List<CurrentAlarm> ret = new LinkedList<CurrentAlarm>();

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		String states = QueryMetaData.GetMetaData(QueryMetaData.CURRENT_ALARM_STATE);

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.QRY_CURR_ALARMS);
			qryStr.append("where ra.res_id=").append(resId);
			if (states != null) {
				qryStr.append(" and ca.alarm_state ").append(states);
			}
			qryStr.append(GlobalQueries.QRY_CURR_ALARM_POSTFIX);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				CurrentAlarm alm = new CurrentAlarm(rs);
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
	public List<CurrentAlarm> getAlarmsByType(String alarmType,
			String alarmSubType, long fromSec, long toSec, short batchSize,
			long startId) {
		List<CurrentAlarm> ret = new LinkedList<CurrentAlarm>();

		String states = QueryMetaData.GetMetaData(QueryMetaData.CURRENT_ALARM_STATE);

		int[] alarmTypeInfo = getAlarmType(alarmType, alarmSubType);

		if (alarmTypeInfo == null) {
			return (ret);
		}

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
					GlobalQueries.QRY_CURR_ALARMS);

			if (applyTimeFilter) {
				qryStr.append("where ra.detection_time>").append(fromSec)
						.append(" and ra.detection_time<=").append(toSec)
						.append(" and ");
			} else {
				qryStr.append("where ");
			}

			if (states != null) {
				qryStr.append(" ca.alarm_state ").append(states).append(" and ");
			}			

			int j = 0;
			qryStr.append("ra.alarm_type=").append(alarmTypeInfo[j++]);

			if (j < alarmTypeInfo.length) {
				qryStr.append(" and ra.alarm_sub_type=").append(
						alarmTypeInfo[j++]);
			}

			qryStr.append(GlobalQueries.QRY_CURR_ALARM_POSTFIX);

			// System.out.println(qryStr);

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
					CurrentAlarm alm = new CurrentAlarm(rs);
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

	/**
	 * 
	 * @return
	 */
	public boolean removeAlarm() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			String qryStr = "delete from caalarm.current_alarm where root_alarm_id="
					+ m_root_alarm_id;

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

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
	 * @param soakDuration
	 * @param alarmState
	 * @return
	 */
	public boolean addAlarm(int soakDuration, int alarmState) {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					"insert into caalarm.current_alarm(root_alarm_id,soak_duration,alarm_state) values(");
			qryStr.append(m_root_alarm_id).append(",").append(soakDuration)
					.append(",").append(alarmState).append(")");

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

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
	 * @return
	 */
	public boolean updateAlarm() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer("update caalarm.current_alarm set alarm_state=");
			qryStr.append(m_alarm_state).append(" where root_alarm_id=").append(m_root_alarm_id);

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

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
	 * @return
	 */
	public boolean clearAlarm() {
		boolean ret = false;

		m_alarm_state = AlarmStates.CLEARED;

		ret = updateAlarm();
		
		/*
		 * TODO: additinal updates to HISTORICAL_ALARMS, etc. tables
		 * Additional updates to the user who cleared the alarm and at what time.
		 */
		
		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	// public String getResourceName() {
	// String ret = null;
	//
	// switch (m_alarm_type) {
	// case AlarmTypes.ALARM_TYPE_HFC:
	// HfcPlant h = new HfcPlant(m_resource_res_id);
	// ret = h.getName();
	// break;
	// case AlarmTypes.ALARM_TYPE_MTA:
	// Emta e = new Emta(m_resource_res_id);
	// ret = e.getMacAddress();
	// break;
	// case AlarmTypes.ALARM_TYPE_CMTS:
	// Cmts c = new Cmts(m_resource_res_id);
	// ret = c.getName();
	// break;
	// case AlarmTypes.ALARM_TYPE_CMS:
	// break;
	// default:
	// break;
	// }
	//
	// return (ret);
	// }
	// /**
	// *
	// * @return
	// */
	// public boolean setAdditionalInfo() {
	// boolean ret = true;
	//
	// switch (m_alarm_type) {
	// case AlarmTypes.ALARM_TYPE_HFC:
	// HfcPlant h = new HfcPlant(m_resource_res_id);
	// m_resourceName = h.getName();
	// break;
	// case AlarmTypes.ALARM_TYPE_MTA:
	// Emta e = new Emta(m_resource_res_id);
	// m_resourceName = e.getMacAddress();
	// break;
	// case AlarmTypes.ALARM_TYPE_CMTS:
	// Cmts c = new Cmts(m_resource_res_id);
	// m_resourceName = c.getName();
	// break;
	// case AlarmTypes.ALARM_TYPE_CMS:
	// break;
	// default:
	// break;
	// }
	//
	// return (ret);
	// }
}
