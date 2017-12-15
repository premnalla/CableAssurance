/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class HfcPlant extends AbstractNetworkObject {

	private Long m_cmtsResId = null;
	private int m_hfcIndex = 0;
	private Long m_hfcResId = null;
	
	/**
	 * 
	 */
	public HfcPlant() {
		super();
	}

	/**
	 * @param conn
	 */
	public HfcPlant(DbConnection conn, Long cmtsResId, int hfcIndex) {
		super(conn);
		m_hfcIndex = hfcIndex;
		m_cmtsResId = cmtsResId;
	}

	public void create() {
		m_hfcResId = createSelf();
	}

	public Long getHfcResId() {
		return (m_hfcResId);
	}
	
	private Long createSelf() {
		
		Long resId = createResource(Resource.RES_TYPE_HFC);
		
		if (resId == null) {
			// System.out.println("Unable to create resource")
			return (resId);
		}

		GregorianCalendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		
		String hfcName = "A0" + m_hfcIndex;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into canet.hfc_plant (hfc_res_id,cmts_res_id,name,alert_level) values(")
			.append(resId).append(",").append(m_cmtsResId).append(",'").append(hfcName).append("',1)");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.hfc_current_counts(hfc_res_id,last_log_tm) values(");
			qryStr.append(resId).append(",").append(sec).append(")");
			stmt.executeUpdate(qryStr.toString());

			qryStr = new StringBuffer("insert into caperf.hfc_current_alarm(hfc_res_id,last_log_tm) values(");
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

}
