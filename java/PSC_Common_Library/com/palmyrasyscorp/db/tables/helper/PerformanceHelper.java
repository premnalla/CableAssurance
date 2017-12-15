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
public class PerformanceHelper {

	/**
	 * 
	 */
	private PerformanceHelper() {
		super();
	}

	static public final LinkedList GetPerformancefExceptionForResource(Long resId) {
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT * FROM exception_basic WHERE resource_res_id=" + resId;
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				PerformanceException c = new PerformanceException(rs);
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

	static public final boolean isExceptionForResource(Long resId) {
		boolean ret = false;
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT a.exception_id FROM exception_basic a WHERE a.resource_res_id=" + resId;
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
