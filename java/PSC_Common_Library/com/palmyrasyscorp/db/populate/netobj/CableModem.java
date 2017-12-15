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
public class CableModem extends AbstractNetworkObject {

	private Long m_channelResId = null;
	private Long m_hfcResId = null;
	private int m_cmIndex = 0;
	private Long m_cmResId = null;
	private Long m_cmtsResId = null;
	private Short m_endUserDeviceType = null;
	
	/**
	 * 
	 */
	protected CableModem() {
		super();
	}

	/**
	 * @param conn
	 */
	public CableModem(DbConnection conn, Long cmtsResId, Long channelResId, Long hfcResId, int cmIndex) {
		super(conn);
		m_channelResId = channelResId;
		m_hfcResId = hfcResId;
		m_cmIndex = cmIndex;
		m_cmtsResId = cmtsResId;
		m_endUserDeviceType = new Short((short)1);
	}

	public void setEndUserDeviceType(short dt) {
		m_endUserDeviceType = new Short(dt);
	}
	
	public void create() {
		m_cmResId = createSelf();
	}

	public Long getCmResId() {
		return (m_cmResId);
	}
	
	private Long createSelf() {
		
		Long resId = createResource(Resource.RES_TYPE_CABLE_MODEM);
		
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
			
			qryStr = new StringBuffer("insert into canet.cable_modem (cm_res_id,cmts_res_id,hfc_res_id,upstream_chnl_res_id,mac_address,end_user_dev_type,docsis_state,alert_level) values(")
			.append(resId).append(",").append(m_cmtsResId).append(",").append(m_hfcResId).append(",")
			.append(m_channelResId).append(",'")
			.append(GlobalUtils.GetNextMacAddress()).append("',")
			.append(m_endUserDeviceType).append(", 6,1)");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.cm_current_status(cm_res_id,last_log_tm,online_state) values(");
			qryStr.append(resId).append(",").append(sec).append(",1)");
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.cm_current_perf(cm_res_id,last_log_tm) values(");
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
