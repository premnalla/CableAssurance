/**
 * 
 */
package com.palmyrasyscorp.db.tables.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public final class AlarmHelper {

	/**
	 * 
	 */
	private AlarmHelper() {
	}

	static public final LinkedList GetAlarmsForResource(Long resId) {
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT * FROM alarm_basic WHERE resource_res_id=" + resId;
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				// Alarm c = new Alarm(rs);
				// c.getAlarmDescription();
				// ret.add(c);
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}
	
	static public final LinkedList GetAlarmsForResource(Long resId, int resType) {
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT * FROM alarm_basic WHERE resource_res_id=" + resId;
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				// Alarm c = new Alarm(rs);
				
				/*
				if (resType != ResourceTypes.RES_TYPE_EMTA && 
						resType != ResourceTypes.RES_TYPE_CABLE_MODEM) {
					c.getAlarmDescription();
					// c.getAlarmDetails();
				}
				*/
				
				// ret.add(c);
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}

	static public final String GetAlarmDetailsForAlarm(Long alarmId) {
		String ret = null;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.alarm_details FROM alarm_details a WHERE a.alarm_id=" + alarmId;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				ret = rs.getString(1);
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}
	
	static public final String GetRootCauseForAlarm(Long alarmId) {
		String ret = null;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.alarm_rootcause FROM alarm_root_cause a WHERE a.alarm_id=" + alarmId;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				ret = rs.getString(1);
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}

	static public final boolean isAlarmForResource(Long resId) {
		boolean ret = false;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.alarm_id FROM alarm_basic a WHERE a.resource_res_id=" + resId;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				ret = true;
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}

}
