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
public class Emta extends AbstractNetworkObject {

	private Long m_channelResId = null;
	private Long m_hfcResId = null;
	private int m_cmIndex = 0;
	private Long m_cmtsResId = null;

	/**
	 * 
	 */
	protected Emta() {
		super();
	}

	/**
	 * @param conn
	 */
	public Emta(DbConnection conn, Long cmtsResId, Long channelResId, Long hfcResId, int cmIndex) {
		super(conn);
		m_channelResId = channelResId;
		m_hfcResId = hfcResId;
		m_cmIndex = cmIndex;
		m_cmtsResId = cmtsResId;
	}

	public void create() {
		createSelf();
	}

	private void createSelf() {
		
		CableModem cm = new CableModem(getConnection(), m_cmtsResId, m_channelResId,m_hfcResId,m_cmIndex);
		cm.setEndUserDeviceType((short)2);
		cm.create();
		Long cmResId = cm.getCmResId();
		
		Long resId = createResource(Resource.RES_TYPE_EMTA);
		
		if (resId == null) {
			// System.out.println("Unable to create resource")
			return;
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
			
			qryStr = new StringBuffer("insert into canet.emta (emta_res_id,cm_res_id,mac_address,pktcbl_prov_state,ping_state,available,alert_level) values(")
			.append(resId).append(",").append(cmResId).append(",'").append(GlobalUtils.GetNextMacAddress())
			.append("', 1,1,1,1)");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.mta_current_avail(mta_res_id,last_log_tm,available) values(");
			qryStr.append(resId).append(",").append(sec).append(",1)");
			System.out.println(qryStr);
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

		cmResId = null;
		cm = null;

	}

}
