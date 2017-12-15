/**
 * 
 */
package com.palmyrasyscorp.db.tables.helper;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class CmPerformanceHelper {

	/**
	 * 
	 */
	private CmPerformanceHelper() {
		super();
	}

	static public final CmPerfEntry GetLatestPerfRecord(Long cmResId) {
		CmPerfEntry ret = null;
		
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
			String qryStr = "SELECT * FROM cm_perf_log WHERE cm_res_id=" + cmResId + " order by id desc limit 1";

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new CmPerfEntry(rs);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			// log.error(null, e);	
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
