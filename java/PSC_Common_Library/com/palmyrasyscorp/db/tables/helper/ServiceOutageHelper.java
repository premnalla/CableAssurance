/**
 * 
 */
package com.palmyrasyscorp.db.tables.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class ServiceOutageHelper {

	/**
	 * 
	 */
	protected ServiceOutageHelper() {
		super();
	}

	static public final LinkedList GetOutagesForResource(Long resId) {
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT * FROM service_outage_basic WHERE resource_res_id=" + resId;
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				ServiceOutage c = new ServiceOutage(rs);
				// c.getAlarmDescription();
				ret.add(c);
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

	static public final String GetOutageDetailsForOutage(Long outageId) {
		String ret = null;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.outage_details FROM service_outage_details a WHERE a.outage_id=" + outageId;
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
	
	static public final String GetRootCauseForOutage(Long outageId) {
		String ret = null;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.outage_rootcause FROM service_root_cause a WHERE a.outage_id=" + outageId;
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

	static public final boolean isOutageForResource(Long resId) {
		boolean ret = false;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.outage_id FROM service_outage_basic a WHERE a.resource_res_id=" + resId;
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
