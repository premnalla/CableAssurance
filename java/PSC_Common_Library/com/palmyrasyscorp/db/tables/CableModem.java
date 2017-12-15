/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class CableModem extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(CableModem.class);

	private Long m_id;

	private Long m_cm_res_id;

	private Long m_cmts_res_id;

	private Long m_hfc_res_id;

	private Long m_up_channel_res_id;
	private Long m_down_channel_res_id;

	private Long m_cm_index;

	// private Long m_online_offline_time;

	private String m_mac_address;

	private String m_fqdn;

	private String m_ip_address;

	private Integer m_docsis_state;

	private Integer m_eu_device_type;

	private Integer m_ip_address_type;

	private Integer m_alert_level;
	public int getAlertLevel() {return m_alert_level.intValue();}

	private Boolean m_is_deleted;
	
	private Boolean m_is_online;
	
	private LinkedList m_emtaList = new LinkedList();

	protected CableModem() {
	}

	/**
	 * 
	 * @param cm_res_id
	 */
	public CableModem(long cm_res_id) {
		m_cm_res_id = new Long(cm_res_id);
		querySelf();
	}

	/**
	 * 
	 * @param mac
	 */
	public CableModem(String mac) {
		m_mac_address = mac;
		querySelf();
	}
	
	/**
	 * 
	 * @param rs
	 */
	public CableModem(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_cm_res_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			m_hfc_res_id = (Long) rs.getObject(i++);
			m_up_channel_res_id = (Long) rs.getObject(i++);
			m_down_channel_res_id = (Long) rs.getObject(i++);
			m_cm_index = (Long) rs.getObject(i++);
			// m_online_offline_time = (Long) rs.getObject(i++);
			i++; // last_updated
			m_mac_address = rs.getString(i++);
			m_fqdn = rs.getString(i++);
			m_ip_address = rs.getString(i++);
			m_docsis_state = (Integer) rs.getObject(i++);
			m_eu_device_type = (Integer) rs.getObject(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
			m_is_online = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isAlarmed() {
//		boolean ret = AlarmHelper.isAlarmForResource(m_cm_res_id);
//		return (ret);
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isServiceOutage() {
//		boolean ret = ServiceOutageHelper.isOutageForResource(m_cm_res_id);
//		return (ret);
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isPerformanceException() {
//		boolean ret = PerformanceHelper.isExceptionForResource(m_cm_res_id);
//		return (ret);
//	}

	public Long getId() {
		return m_id;
	}

	public Long getCmResId() {
		return m_cm_res_id;
	}

	public Long getCmtsResId() {
		return m_cmts_res_id;
	}

	public Long getHfcResId() {
		return m_hfc_res_id;
	}

	public Long getUpChannelResId() {
		return m_up_channel_res_id;
	}

	public Long getDownChannelResId() {
		return m_down_channel_res_id;
	}
	
	public Long getCmIndex() {
		return m_cm_index;
	}
	
//	public Long getOnlineOfflineTime() {
//		return m_online_offline_time;
//	}
	
	public String getMacAddress() {
		return m_mac_address;
	}

	public String getFqdn() {
		return m_fqdn;
	}

	public String getIpAddress() {
		return m_ip_address;
	}

	public Integer getDocsisState() {
		return m_docsis_state;
	}

	public Integer getEndUserDeviceType() {
		return m_eu_device_type;
	}

	public Integer getIpAddressType() {
		return m_ip_address_type;
	}

	public Boolean getIsDeleted() {
		return m_is_deleted;
	}
	
	public Boolean getIsOnline() {
		return m_is_online;
	}
	
	public boolean isOnline() {
		short ds;
		boolean ret = false;

		if (m_docsis_state != null) {
			ds = m_docsis_state.shortValue();
		} else {
			return (ret);
		}

		if (ds == 1 || ds == 4 || ds == 5) {
			ret = true;
		} else {
			ret = true;
		}

		return (ret);
	}

	public boolean isOffline() {
		return (!isOnline());
	}

	public String getDocsisStatusString() {
		String ret = null;
		short val = m_docsis_state.shortValue();
		
		if (val==6) {
			ret = "RegistrationComplete";
		} else if (val==1) {
			ret = "other";
		} else if (val==2) {
			ret = "Ranging";
		} else if (val==3) {
			ret = "RangingAborted";
		} else if (val==4) {
			ret = "RangingComplete";
		} else if (val==5) {
			ret = "IpComplete";
		} else if (val==7) {
			ret = "AccessDenied";
		} else if (val==8) {
			ret = "Operational";
		} else if (val==9) {
			ret = "RegisteredBPIInitializing";
		} else {
			ret = "Unknown";
		}
		
		return (ret);
	}

//	public String getHtmlTdForDocsisStatus() {
//		String ret = null;
//		short val = m_docsis_state.shortValue();
//		
//		if (val==6) {
//			ret = "<td>";
//		} else if (val==1) {
//			ret = GlobalStrings.TABLE_CELL_TAG_RED;
//		} else if (val==2) {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		} else if (val==3) {
//			ret = GlobalStrings.TABLE_CELL_TAG_RED;
//		} else if (val==4) {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		} else if (val==5) {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		} else if (val==7) {
//			ret = GlobalStrings.TABLE_CELL_TAG_RED;
//		} else if (val==8) {
//			ret = "<td>";
//		} else if (val==9) {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		} else {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		}
//		
//		return (ret);
//	}

	/**
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
			StringBuffer qryStr = new StringBuffer("SELECT * FROM canet.cable_modem WHERE ");
			if (m_cm_res_id != null) {
				qryStr.append("cm_res_id=").append(m_cm_res_id);
			} else if (m_mac_address != null) {
				qryStr.append("mac_address=\"").append(m_mac_address).append("\"");				
			} else {
				return;
			}

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
				i++; // m_cm_res_id = (Long) rs.getObject(i++);
				m_cmts_res_id = (Long) rs.getObject(i++);
				m_hfc_res_id = (Long) rs.getObject(i++);
				m_up_channel_res_id = (Long) rs.getObject(i++);
				m_down_channel_res_id = (Long) rs.getObject(i++);
				m_cm_index = (Long) rs.getObject(i++);
				// m_online_offline_time = (Long) rs.getObject(i++);
				i++; // last_updated
				m_mac_address = rs.getString(i++);
				m_fqdn = rs.getString(i++);
				m_ip_address = rs.getString(i++);
				m_docsis_state = (Integer) rs.getObject(i++);
				m_eu_device_type = (Integer) rs.getObject(i++);
				m_ip_address_type = (Integer) rs.getObject(i++);
				m_alert_level = (Integer) rs.getObject(i++);
				m_is_deleted = (Boolean) rs.getObject(i++);
				m_is_online = (Boolean) rs.getObject(i++);
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

//	/**
//	 * 
//	 * @return
//	 */
//	public LinkedList getListOfEmtas() {
//		return (m_emtaList);
//	}

//	/**
//	 * 
//	 *
//	 */
//	public void queryEmtaChildren() {
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = "SELECT * FROM canet.emta WHERE cm_res_id="
//					+ getCmResId().longValue();
//			rs = stmt.executeQuery(qryStr);
//
//			while (rs.next()) {
//				Emta e = new Emta(rs);
//				m_emtaList.add(e);
//			}
//		} catch (Exception e) {
//			// e.printStackTrace();
//			log.error(null, e);	
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//	}

}
