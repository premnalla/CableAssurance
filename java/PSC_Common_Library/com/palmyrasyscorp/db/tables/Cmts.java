/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.helper.*;

/**
 * @author Prem
 * 
 */
public class Cmts extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(Cmts.class);

	public final int CMTS_ONLINE_STATUS_UNKNOWN = 0;

	public final int CMTS_ONLINE_STATUS_ONLINE = 1;

	public final int CMTS_ONLINE_STATUS_OFFLINE = 2;

	private Long m_id;

	public Long getId() {
		return m_id;
	}

	private Long m_cmts_res_id;

	public Long getCmtsResId() {
		return m_cmts_res_id;
	}

	private String m_name;

	public String getName() {
		return m_name;
	}

	public void setName(String s) {
		m_name = s;
	}

	private String m_fqdn;

	public String getFqdn() {
		return m_fqdn;
	}

	private String m_host;

	public String getHost() {
		return m_host;
	}

	public void setHose(String s) {
		m_host = s;
	}

	private String m_cmts_read_community;

	public String getCmtsReadCommunity() {
		return m_cmts_read_community;
	}

	private String m_cmts_write_community;

	public String getCmtsWriteCommunity() {
		return m_cmts_write_community;
	}

	private String m_cm_read_community;

	public String getCmReadCommunity() {
		return m_cm_read_community;
	}

	private String m_cm_write_community;

	public String getCmWriteCommunity() {
		return m_cm_write_community;
	}

	private String m_mta_read_community;

	public String getMtaReadCommunity() {
		return m_mta_read_community;
	}

	private String m_mta_write_community;

	public String getMtaWriteCommunity() {
		return m_mta_write_community;
	}

	private Integer m_cmts_snmp_version;

	private Integer m_cm_snmp_version;

	private Integer m_mta_snmp_version;

	private Integer m_online_state;

	private Boolean m_is_deleted;

	private Integer m_ip_address_type;

	private Integer m_alert_level;

	public int getAlertLevel() {
		return m_alert_level.intValue();
	}

	private LinkedList m_hfcPlantChildren = new LinkedList();

	private LinkedList m_channelChildren = new LinkedList();

	private CurrentCounts m_currentCounts;

	public void setCurrentCounts(CurrentCounts cc) {
		m_currentCounts = cc;
	}

	public CurrentCounts getCurrentCounts() {
		return (m_currentCounts);
	}

	public Cmts() {
	}

	public Cmts(long res_id) {
		m_cmts_res_id = new Long(res_id);
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public Cmts(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			i++;
			m_name = rs.getString(i++);
			m_fqdn = rs.getString(i++);
			m_host = rs.getString(i++);
			m_cmts_snmp_version = (Integer) rs.getObject(i++);
			m_cm_snmp_version = (Integer) rs.getObject(i++);
			m_mta_snmp_version = (Integer) rs.getObject(i++);
			m_online_state = (Integer) rs.getObject(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);

			/*
			 * 
			 * m_cmts_read_community = rs.getString(i++); m_cmts_write_community =
			 * rs.getString(i++); m_cm_read_community = rs.getString(i++);
			 * m_cm_write_community = rs.getString(i++); m_mta_read_community =
			 * rs.getString(i++); m_mta_write_community = rs.getString(i++);
			 * 
			 */
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param rs
	 */
	public Cmts(ProxyDbResultSet rs, IntegerHolder ih) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			i++;
			m_name = rs.getString(i++);
			m_fqdn = rs.getString(i++);
			m_host = rs.getString(i++);
			m_cmts_snmp_version = (Integer) rs.getObject(i++);
			m_cm_snmp_version = (Integer) rs.getObject(i++);
			m_mta_snmp_version = (Integer) rs.getObject(i++);
			m_online_state = (Integer) rs.getObject(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}

	/**
	 * 
	 * 
	 */
	private void querySelf() {
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
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.QRY_CMTS_IN_LOCAL_SYSTEM);
			qryStr.append(" where cmts_res_id=").append(getCmtsResId());

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				m_id = (Long) rs.getObject(i++);

				// m_cmts_res_id = mrid;
				i++;

				// last updated
				i++;

				m_name = rs.getString(i++);
				m_fqdn = rs.getString(i++);
				m_host = rs.getString(i++);
				m_cmts_snmp_version = (Integer) rs.getObject(i++);
				m_cm_snmp_version = (Integer) rs.getObject(i++);
				m_mta_snmp_version = (Integer) rs.getObject(i++);
				m_online_state = (Integer) rs.getObject(i++);
				m_ip_address_type = (Integer) rs.getObject(i++);
				m_alert_level = (Integer) rs.getObject(i++);
				m_is_deleted = (Boolean) rs.getObject(i++);

				IntegerHolder ih = new IntegerHolder();
				ih.value = i;
				m_currentCounts = new CurrentCounts(rs, ih);
				ih = null;

			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	}

	/**
	 * 
	 * @return
	 */
	public SnmpV2CAttributes[] getSnmpV2CAttributes() {
		SnmpV2CAttributes[] ret = null;

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
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.QRY_SNMP_V2C_ATTRS_FOR_CMTS);
			qryStr.append(getCmtsResId());

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			IntegerHolder ih = new IntegerHolder();
			ih.value = 1;
			if (rs.next()) {
				ret = new SnmpV2CAttributes[3];
				int i = 0;
				ret[i++] = new SnmpV2CAttributes(rs, ih);
				ret[i++] = new SnmpV2CAttributes(rs, ih);
				ret[i++] = new SnmpV2CAttributes(rs, ih);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	/**
	 * 
	 * @return
	 */
	public boolean updateRow() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer updStr = new StringBuffer(
					GlobalQueries.UPD_CMTS_IN_LOCAL_SYSTEM);
			updStr.append("name=\"").append(m_name).append("\", ").append(
					"ip_address=\"").append(m_host).append("\", ").append(
					"is_deleted=").append(m_is_deleted?1:0).append(
					" where cmts_res_id=").append(m_cmts_res_id);

			// System.out.println(updStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(updStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	public boolean insertRow() {
		boolean ret = false;

		Cmts cmts = GetCmtsByName2(m_name);
		if (cmts != null) {
			if (cmts.m_is_deleted) {
				cmts.m_is_deleted = false;
				cmts.m_host = m_host;
				ret = cmts.updateRow();
				return (ret);
			}
		}
		
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create resource
			 */
			Resource rx = new Resource(ResourceTypes.RES_TYPE_CMTS);
			rx.setConnection(c);
			Long resId = rx.createRow();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * Create sql
			 */
			StringBuffer insStr = new StringBuffer(
					GlobalQueries.INS_CMTS_IN_LOCAL_SYSTEM_1);
			insStr.append("(").append(resId).append(", \"").append(m_name)
					.append("\", \"").append(m_host).append("\")");
			stmt.execute(insStr.toString());

			Calendar cal = new GregorianCalendar();
			insStr = null;
			insStr = new StringBuffer(GlobalQueries.INS_CMTS_IN_LOCAL_SYSTEM_2);
			insStr.append("(").append(resId).append(",").append(
					cal.getTimeInMillis() / 1000).append(")");
			stmt.execute(insStr.toString());

			insStr = new StringBuffer(GlobalQueries.INS_CMTS_IN_LOCAL_SYSTEM_3)
					.append(resId).append(")");
			stmt.execute(insStr.toString());

			insStr = new StringBuffer(GlobalQueries.INS_CMTS_IN_LOCAL_SYSTEM_4)
			.append(resId).append(")");
			stmt.execute(insStr.toString());

			insStr = new StringBuffer(GlobalQueries.INS_CMTS_IN_LOCAL_SYSTEM_5)
			.append(resId).append(")");
			stmt.execute(insStr.toString());

			// Finally
			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	/**
	 * @return
	 */
	public boolean deleteRow() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer updStr = new StringBuffer(
					GlobalQueries.DEL_CMTS_IN_LOCAL_SYSTEM);
			updStr.append(m_cmts_res_id);

			// System.out.println(updStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(updStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	public LinkedList getListOfChannels() {
		return m_channelChildren;
	}

	public LinkedList getListOfHfcPlants() {
		return m_hfcPlantChildren;
	}

	public boolean isOnline() {
		boolean ret = false;
		short val = m_online_state.shortValue();

		if (val == CMTS_ONLINE_STATUS_ONLINE) {
			ret = true;
		}

		return (ret);
	}

	public boolean isAlarmed() {
		boolean ret = AlarmHelper.isAlarmForResource(m_cmts_res_id);
		return (ret);
	}

	public String getOnlineStatusString() {
		String ret = null;
		short val = m_online_state.shortValue();

		if (val == CMTS_ONLINE_STATUS_ONLINE) {
			ret = GlobalStrings.GUI_ONLINE_STATUS_STRING;
		} else if (val == CMTS_ONLINE_STATUS_OFFLINE) {
			ret = GlobalStrings.GUI_OFFLINE_STATUS_STRING;
		} else if (val == CMTS_ONLINE_STATUS_UNKNOWN) {
			ret = GlobalStrings.GUI_OTHER_STATUS_STRING;
		} else {
			ret = GlobalStrings.GUI_NO_VALUE_STRING;
		}

		return (ret);
	}

//	public void queryChildren() {
//		queryHfcPlantChildren();
//		queryChannelChildren();
//	}

	public void queryChannelChildren() {
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CHANNEL);
			qryStr.append(" where ch.cmts_res_id=").append(getCmtsResId());

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			IntegerHolder ih = new IntegerHolder();
			while (rs.next()) {
				ih.value = 0;
				Channel ch = new Channel(rs, ih);
				CurrentCounts cc = new CurrentCounts(rs, ih);
				ch.setCurrentCounts(cc);
				m_channelChildren.add(ch);
			}
			ih = null;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	}

	public void queryHfcPlantChildren() {
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_HFC);
			qryStr.append(" where h.cmts_res_id=").append(getCmtsResId());

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			IntegerHolder ih = new IntegerHolder();
			while (rs.next()) {
				ih.value = 0;
				HfcPlant h = new HfcPlant(rs, ih);
				CurrentCounts cc = new CurrentCounts(rs, ih);
				h.setCurrentCounts(cc);
				m_hfcPlantChildren.add(h);
			}
			ih = null;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	}

//	public LinkedList queryHfcsInAlarm() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_HFC_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				HfcPlant h = new HfcPlant(rs);
//				ret.add(h);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryHfcsInServiceOutage() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_HFC_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				HfcPlant h = new HfcPlant(rs);
//				ret.add(h);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryHfcsInException() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_HFC_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				HfcPlant h = new HfcPlant(rs);
//				ret.add(h);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryChannelsInAlarm() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_CHANNEL_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Channel c = new Channel(rs);
//				ret.add(c);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryChannelsInServiceOutage() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_CHANNEL_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Channel c = new Channel(rs);
//				ret.add(c);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryChannelsInException() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_CHANNEL_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Channel c = new Channel(rs);
//				ret.add(c);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryOfflineCableModems() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OFFLINE_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryAllCableModems() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryOnlineCableModems() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_ONLINE_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryNotOnlineAndNotOfflineCableModems() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OTHER_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryCableModemsInAlarm() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryCableModemsInException() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryCableModemsInServiceOutage() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_CM_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				CableModem cm = new CableModem(rs);
//				ret.add(cm);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryMtasInAlarm() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryMtasInException() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryMtasInServiceOutage() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryAvailableMtas() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_AVAIL_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryUnavailableMtas() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_UNAVAIL_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryNotAvailNotUnavailMtas() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_CMTS
//					+ getCmtsResId();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	public LinkedList queryAllMtas() {
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_MTA_IN_CMTS
//					+ getCmtsResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//			}
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}

	/**
	 * Get the undeleted CMTS who name matches the input 
	 * @param cn
	 * @return
	 */
	static public Cmts GetCmtsByName(String cn) {
		Cmts ret = null;

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
			StringBuffer qryStr = new StringBuffer(
					"SELECT * FROM canet.cmts WHERE name=\"");
			qryStr.append(cn).append("\" and is_deleted=0");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new Cmts(rs);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	/**
	 * Get the CMTS whose name matches the input irrespective of whether
	 * the CMTS is in deleted state or not.
	 * @param cn
	 * @return
	 */
	static public Cmts GetCmtsByName2(String cn) {
		Cmts ret = null;

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
			StringBuffer qryStr = new StringBuffer(
					"SELECT * FROM canet.cmts WHERE name=\"");
			qryStr.append(cn).append("\"");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new Cmts(rs);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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
