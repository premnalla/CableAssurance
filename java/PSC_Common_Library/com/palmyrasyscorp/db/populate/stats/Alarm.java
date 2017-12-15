/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import sun.awt.image.ImageWatched.Link;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class Alarm {

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

	private Integer m_soakDuration;
	private Integer m_alarmState;
	
	private Integer m_presoakTotal;
	private Integer m_presoakCount;
	private Integer m_postsoakTotal;
	private Integer m_postsoakCount;
	private Integer m_alarmDetWindow;
	private Integer m_alarmThreshold;
	
	private LinkedList m_presoakDevList = new LinkedList();
	private LinkedList m_postSoakDevList = new LinkedList();
	private LinkedList m_preSoakCoincidentals = new LinkedList();
	private LinkedList m_postSoakCoincidentals = new LinkedList();
	
	/**
	 * 
	 * 
	 */
	protected Alarm() {

	}

	/**
	 * 
	 * @param rs
	 */
	public Alarm(ResultSet rs) {
		int i = 1;
		try {
			m_rootAlarmId = (BigInteger) rs.getObject(i++);
			m_resId = (Long) rs.getObject(i++);
			m_detectionTime = (Long) rs.getObject(i++);
			m_detectionTimeUSec = (Long) rs.getObject(i++);
			m_alarmType = (Integer) rs.getObject(i++);
			m_alarmSubType = (Integer) rs.getObject(i++);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param resId
	 * @param detTime
	 * @param detTimeUSec
	 * @param almType
	 * @param almSubType
	 */
	public Alarm(Long resId, Long detTime, Long detTimeUSec,
			Integer almType, Integer almSubType, Integer soakDuration,
			Integer alarmState) {
		m_resId = resId;
		m_detectionTime = detTime;
		m_detectionTimeUSec = detTimeUSec;
		m_alarmType = almType;
		m_alarmSubType = almSubType;
		m_soakDuration = soakDuration;
		m_alarmState = alarmState;
	}

	/**
	 * 
	 * @return
	 */
	public boolean insertAlarm() {
		boolean ret = true;
		
		ret = insertRootAlarm();
		
		if (ret) {
			ret = insertCurrentAlarm();
			ret = insertHistoricalAlarm();
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean insertRootAlarm() {
		boolean ret = true;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnectionPool.getInstance().getConnection()
					.getConnection().createStatement(
							java.sql.ResultSet.TYPE_FORWARD_ONLY,
							java.sql.ResultSet.CONCUR_UPDATABLE);

			StringBuffer qryStr = new StringBuffer(
					"insert into caalarm.root_alarm (res_id,detection_time,det_time_usec,alarm_type,alarm_sub_type) ");
			qryStr.append("values(").append(m_resId).append(",").append(
					m_detectionTime).append(",").append(m_detectionTimeUSec)
					.append(",").append(m_alarmType).append(",").append(
							m_alarmSubType).append(")");

			System.out.println(qryStr);

			stmt.executeUpdate(qryStr.toString(),
					Statement.RETURN_GENERATED_KEYS);

			rs = stmt.getGeneratedKeys();

			int i = 1;
			if (rs.next()) {
				Long l = (Long) rs.getObject(i++);
				m_rootAlarmId = new BigInteger (l.toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	private boolean insertCurrentAlarm() {
		boolean ret = true;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnectionPool.getInstance().getConnection()
					.getConnection().createStatement();

			StringBuffer qryStr = new StringBuffer(
					"insert into caalarm.current_alarm (root_alarm_id,soak_duration,alarm_state) ");
			qryStr.append("values(").append(m_rootAlarmId).append(",").append(
					m_soakDuration).append(",").append(m_alarmState).append(")");

			System.out.println(qryStr);

			ret = stmt.execute(qryStr.toString());

		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean insertHistoricalAlarm() {
		boolean ret = true;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnectionPool.getInstance().getConnection()
					.getConnection().createStatement();

			StringBuffer qryStr = new StringBuffer(
					"insert into caalarm.historical_alarm (root_alarm_id,soak_duration,alarm_state) ");
			qryStr.append("values(").append(m_rootAlarmId).append(",").append(
					m_soakDuration).append(",").append(m_alarmState).append(")");

			System.out.println(qryStr);

			ret = stmt.execute(qryStr.toString());

		} catch (Exception ex) {
			ex.printStackTrace();
			ret = false;
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}

}
