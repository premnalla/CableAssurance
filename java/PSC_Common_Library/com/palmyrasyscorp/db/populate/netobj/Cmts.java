/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class Cmts extends AbstractNetworkObject {

	private String m_cmtsName = null;

	/**
	 * 
	 */
	protected Cmts() {
		super();
	}

	/**
	 * @param conn
	 */
	public Cmts(DbConnection conn, String cmtsName) {
		super(conn);
		m_cmtsName = cmtsName;
	}

	public void create() {
		Long cmtsResId = createSelf();
		createChildren(cmtsResId);
	}
	
	private Long createSelf() {
		
		Long resId = createResource(Resource.RES_TYPE_CMTS);
		
		if (resId == null) {
			// System.out.println("Unable to create resource")
			return (resId);
		}

		GregorianCalendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;
		
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into canet.cmts (cmts_res_id,name,online_state,alert_level) values(")
			.append(resId).append(",'").append(m_cmtsName).append("',1,1)");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.cmts_current_counts(cmts_res_id,last_log_tm) values(");
			qryStr.append(resId).append(",").append(sec).append(")");
			stmt.executeUpdate(qryStr.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
			qryStr = null;
		}

		return (resId);
	}
	
	private void createChildren(Long cmtsResId) {
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		StringBuffer qryStr2 = null;
		StringBuffer qryStr3 = null;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into canet.cmts_mta_v2c_attributes (cmts_res_id,read_community) values(")
			.append(cmtsResId).append(",'").append("foo").append("')");
			qryStr2 = new StringBuffer("insert into canet.cmts_cm_v2c_attributes (cmts_res_id,read_community) values(")
			.append(cmtsResId).append(",'").append("foo").append("')");
			qryStr3 = new StringBuffer("insert into canet.cmts_v2c_attributes (cmts_res_id,read_community) values(")
			.append(cmtsResId).append(",'").append("foo").append("')");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			stmt.executeUpdate(qryStr2.toString());
			stmt.executeUpdate(qryStr3.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
			qryStr = null;
		}
		
		/*
		 * 
		 */
		for (int i=1; i<=GlobalConstants.CHANNELS_PER_CMTS; i++) {
			Channel c = new Channel(getConnection(), cmtsResId, i);
			c.create();
			c = null;
		}
		
	}

}
