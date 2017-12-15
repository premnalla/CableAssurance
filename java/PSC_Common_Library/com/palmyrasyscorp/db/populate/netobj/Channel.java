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
public class Channel extends AbstractNetworkObject {

	private Long m_cmtsResId = null;
	private int m_channelIndex = 0;
	
	/**
	 * 
	 */
	protected Channel() {
		super();
	}

	/**
	 * @param conn
	 */
	public Channel(DbConnection conn, Long cmtsResId, int channelIndex) {
		super(conn);
		m_channelIndex = channelIndex;
		m_cmtsResId = cmtsResId;
	}

	public void create() {
		Long channelResId = createSelf();
		createChildren(channelResId);
	}
	
	private Long createSelf() {
		
		Long resId = createResource(Resource.RES_TYPE_CHANNEL);
		
		if (resId == null) {
			// System.out.println("Unable to create resource")
			return (resId);
		}

		GregorianCalendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		
		int major = m_channelIndex / 10;
		int minor = m_channelIndex % 10;
		String channelName = "Upstream " + major + "/" + minor;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into canet.channel (channel_res_id,cmts_res_id,name,channel_type,channel_index,alert_level) values(")
			.append(resId).append(",").append(m_cmtsResId).append(",'").append(channelName).append("',1,")
			.append(m_channelIndex).append(",1)");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
			qryStr = new StringBuffer("insert into caperf.channel_current_counts(channel_res_id,last_log_tm) values(");
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
	
	private void createChildren(Long channelResId) {
		
		HfcPlant hfc = new HfcPlant(getConnection(), m_cmtsResId, m_channelIndex);
		hfc.create();
		Long hfcResId = hfc.getHfcResId();
		hfc = null;
		
		int i=1;
		for (; i<=GlobalConstants.CABLEMODEMS_PER_CHANNEL; i++) {
			CableModem c = new CableModem(getConnection(), m_cmtsResId, channelResId, hfcResId, i);
			c.create();
			c = null;
		}
		
		int endIndex = i + GlobalConstants.MTAS_PER_CHANNEL;
		for (int j=i; j<=endIndex; j++) {
			Emta c = new Emta(getConnection(), m_cmtsResId, channelResId, hfcResId, j);
			c.create();
			c = null;
		}

	}

}
