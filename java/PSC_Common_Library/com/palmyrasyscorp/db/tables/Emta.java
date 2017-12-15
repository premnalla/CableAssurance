/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.helper.*;

/**
 * @author Prem
 * 
 */
public class Emta extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(Emta.class);

	private Long m_id;
	public Long getId() {
		return m_id;
	}

	private Long m_emta_res_id;
	public Long getEmtaResId() {
		return m_emta_res_id;
	}

	private Long m_cms_res_id;
	public Long getCmsResId() {
		return m_cms_res_id;
	}

	private Long m_cm_res_id;
	public Long getCmResId() {
		return m_cm_res_id;
	}

	private java.sql.Timestamp m_last_updated;

	private String m_fqdn;
	public String getFqdn() {
		return m_fqdn;
	}

	private String m_host;
	public String getHost() {
		return m_host;
	}

	private String m_mac_address;
	public String getMacAddress() {
		return m_mac_address;
	}

	private Integer m_pktcable_prov_state;
	public Integer getPktCableProvStatus() {
		return (m_pktcable_prov_state);
	}

	private Integer m_ping_state;
	public Integer getPingStatus() {
		return (m_ping_state);
	}

	private Integer m_ip_address_type;

	private Integer m_alert_level;
	public int getAlertLevel() {return m_alert_level.intValue();}

	private Boolean m_is_prov_pass;

	private Boolean m_is_ping_failure;

	private Boolean m_available;

	private Boolean m_is_deleted;

	/**
	 * 
	 *
	 */
	protected Emta() {

	}
	
	/**
	 * 
	 * @param emta_res_id
	 */
	public Emta(long emta_res_id) {
		m_emta_res_id = new Long(emta_res_id);
		querySelf();
	}
	
	/**
	 * 
	 * @param mac
	 */
	public Emta(String mac) {
		m_mac_address = mac;
		querySelf();
	}
	
	/**
	 * 
	 * @param rs
	 */
	public Emta(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_emta_res_id = (Long) rs.getObject(i++);
			m_cms_res_id = (Long) rs.getObject(i++);
			m_cm_res_id = (Long) rs.getObject(i++);
			i++; // last_updated
			m_fqdn = rs.getString(i++);
			m_host = rs.getString(i++);
			m_mac_address = rs.getString(i++);
			m_pktcable_prov_state = (Integer) rs.getObject(i++);
			m_ping_state = (Integer) rs.getObject(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_prov_pass = (Boolean) rs.getObject(i++);
			m_is_ping_failure = (Boolean) rs.getObject(i++);
			m_available = (Boolean) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAlarmed() {
		boolean ret = AlarmHelper.isAlarmForResource(m_emta_res_id);
		return (ret);
	}

//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isServiceOutage() {
//		boolean ret = ServiceOutageHelper.isOutageForResource(m_emta_res_id);
//		return (ret);
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isPerformanceException() {
//		boolean ret = PerformanceHelper.isExceptionForResource(m_emta_res_id);
//		return (ret);
//	}

	/**
	 * 
	 * @return
	 */
	public String getPingStatusString() {
		String ret = null;
		short val = 0;

		if (m_ping_state != null) {
			val = m_ping_state.shortValue();
		} else {
			return (ret);
		}

		if (val == 1) {
			ret = "Alive";
		} else if (val == 2) {
			ret = "Unreachable";
		} else {
			ret = "Unknown";
		}

		return (ret);
	}

//	public String getHtmlTdForPingStatus() {
//		String ret = null;
//		short val = 0;
//
//		if (m_ping_state != null) {
//			val = m_ping_state.shortValue();
//		} else {
//			return (ret);
//		}
//
//		if (val == 1) {
//			ret = "<td>";
//		} else if (val == 2) {
//			ret = GlobalStrings.TABLE_CELL_TAG_RED;
//		} else {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		}
//
//		return (ret);
//	}

	public String getProvStatusString() {
		String ret = null;
		short val = 0;

		if (m_pktcable_prov_state != null) {
			val = m_pktcable_prov_state.shortValue();
		} else {
			return (ret);
		}

		if (val == 1 || val == 4 || val == 5) {
			ret = "Pass";
		} else if (val == 2) {
			ret = "In progress";
		} else if (val == 3) {
			ret = "Config file error";
		} else if (val == 6) {
			ret = "Internal error";
		} else if (val == 7) {
			ret = "Failure";
		} else {
			ret = "Unknown";
		}

		return (ret);
	}

//	public String getHtmlTdForProvStatus() {
//		String ret = null;
//		short val = 0;
//
//		if (m_pktcable_prov_state != null) {
//			val = m_pktcable_prov_state.shortValue();
//		} else {
//			return (ret);
//		}
//
//		if (val == 1 || val == 4 || val == 5) {
//			ret = "<td>";
//		} else if (val == 2) {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		} else if (val == 3 || val == 6 || val == 7) {
//			ret = GlobalStrings.TABLE_CELL_TAG_RED;
//		} else {
//			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
//		}
//
//		return (ret);
//	}

	/**
	 * 
	 */
	public boolean isProvStatusPass() {

		boolean ret = false;
		short val = 0;

		if (m_pktcable_prov_state != null) {
			val = m_pktcable_prov_state.shortValue();
		} else {
			return (ret);
		}

		if (val == 1 || val == 4 || val == 5) {
			ret = true;
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAvailable() {
		boolean ret;

		if (m_available != null) {
			ret = m_available.booleanValue();
		} else {
			ret = false;
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	public String getAvailableStatusString() {
		String ret;

		if (m_available != null) {
			if (m_available.booleanValue()) {
				ret = "Available";
			} else {
				ret = "Unavailable";
			}
		} else {
			ret = "";
		}

		return (ret);
	}

//	public String getHtmlTdForAvailable() {
//		String ret;
//
//		if (m_available != null) {
//			if (m_available.booleanValue()) {
//				ret = "<td>";
//			} else {
//				ret = GlobalStrings.TABLE_CELL_TAG_RED;
//			}
//		} else {
//			ret = "";
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
			StringBuffer qryStr = new StringBuffer("SELECT * FROM canet.emta WHERE ");
			if (m_emta_res_id != null) {
				qryStr.append("emta_res_id=").append(m_emta_res_id);
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
				m_emta_res_id = (Long) rs.getObject(i++);
				m_cms_res_id = (Long) rs.getObject(i++);
				m_cm_res_id = (Long) rs.getObject(i++);
				i++; // last_updated
				m_fqdn = rs.getString(i++);
				m_host = rs.getString(i++);
				m_mac_address = rs.getString(i++);
				m_pktcable_prov_state = (Integer) rs.getObject(i++);
				m_ping_state = (Integer) rs.getObject(i++);
				m_ip_address_type = (Integer) rs.getObject(i++);
				m_alert_level = (Integer) rs.getObject(i++);
				m_is_prov_pass = (Boolean) rs.getObject(i++);
				m_is_ping_failure = (Boolean) rs.getObject(i++);
				m_available = (Boolean) rs.getObject(i++);
				m_is_deleted = (Boolean) rs.getObject(i++);
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

}
