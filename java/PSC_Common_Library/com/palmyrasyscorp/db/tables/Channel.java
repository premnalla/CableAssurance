/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.helper.*;

/**
 * @author Prem
 * 
 */
public class Channel extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(Channel.class);

	private Long m_id;

	private Long m_channel_res_id;

	private Long m_cmts_res_id;
	
	private Long m_channel_index;

	private String m_name;
	
	private Integer m_channel_type;
	
	private Integer m_alert_level;
	public int getAlertLevel() {return m_alert_level.intValue();}
	
	private Boolean m_is_deleted;

	private LinkedList m_upstream_cable_modems = new LinkedList();

	private LinkedList m_upstream_emtas = new LinkedList();

	private CurrentCounts m_currentCounts;
	public void setCurrentCounts(CurrentCounts cc) {
		m_currentCounts = cc;
	}
	public CurrentCounts getCurrentCounts() {
		return (m_currentCounts);
	}
	
	protected Channel() {
	}

	public Channel(long res_id) {
		m_channel_res_id = new Long(res_id);
		querySelf();
	}

	/*
	 * public Channel(long id, long channel_res_id, long cmts_res_id, String
	 * name) { m_id = id; m_channel_res_id = channel_res_id; m_cmts_res_id =
	 * cmts_res_id; m_name = name; }
	 */

	/**
	 * 
	 */
	public Channel(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_channel_res_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			m_channel_index = (Long) rs.getObject(i++);
			i++; // last updated
			m_name = rs.getString(i++);
			m_channel_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	public Channel(ProxyDbResultSet rs, IntegerHolder ih) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_channel_res_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			m_channel_index = (Long) rs.getObject(i++);
			i++; // last updated
			m_name = rs.getString(i++);
			m_channel_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}

	public boolean isAlarmed() {
		boolean ret = AlarmHelper.isAlarmForResource(m_channel_res_id);
		return (ret);
	}

	public boolean isServiceOutage() {
		boolean ret = ServiceOutageHelper.isOutageForResource(m_channel_res_id);
		return (ret);
	}

	public boolean isPerformanceException() {
		boolean ret = PerformanceHelper.isExceptionForResource(m_channel_res_id);
		return (ret);
	}

	public Long getId() {
		return m_id;
	}

	public Long getChannelResId() {
		return m_channel_res_id;
	}

	public Long getCmtsResId() {
		return m_cmts_res_id;
	}

	public String getName() {
		return m_name;
	}

	public LinkedList getListOfUpstreamCableModems() {
		return m_upstream_cable_modems;
	}

	public LinkedList getListOfUpstreamEmtas() {
		return m_upstream_emtas;
	}

//	public void queryChildren() {
//		queryUpstreamCableModemChildren();
//		queryUpstreamMtaChildren();
//	}

	public void queryUpstreamCableModemChildren() {
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
			String qryStr = GlobalQueries.QRY_CM_IN_UP_CHANNEL + getChannelResId();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				CableModem cm = new CableModem(rs);
				m_upstream_cable_modems.add(cm);
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

	public void queryUpstreamMtaChildren() {
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
			String qryStr = GlobalQueries.QRY_MTA_IN_UP_CHANNEL + getChannelResId();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				Emta e = new Emta(rs);
				m_upstream_emtas.add(e);
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CHANNEL);
			qryStr.append(" where ch.channel_res_id=").append(getChannelResId());

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
				i++; // m_channel_res_id = (Long) rs.getObject(i++);
				m_cmts_res_id = (Long) rs.getObject(i++);
				m_channel_index = (Long) rs.getObject(i++);
				i++; // last updated
				m_name = rs.getString(i++);
				m_channel_type = (Integer) rs.getObject(i++);
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

//	public LinkedList queryOfflineCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OFFLINE_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryOnlineCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_ONLINE_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryNotOnlineAndNotOfflineCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OTHER_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryCableModemsInAlarm()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryCableModemsInException()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryCableModemsInServiceOutage()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_CM_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryMtasInAlarm()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INALARM_MTA_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryMtasInException()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_INEXCEP_MTA_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryMtasInServiceOutage()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_OOS_MTA_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryAvailableMtas()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_AVAIL_MTA_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryUnavailableMtas()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_UNAVAIL_MTA_IN_CHANNEL + getChannelResId().longValue();
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
//	public LinkedList queryNotAvailNotUnavailMtas()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//		    String qryStr = GlobalQueries.QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_CHANNEL + getChannelResId();
//		    rs = stmt.executeQuery(qryStr);
//
//		    while (rs.next()) {
//				Emta mta = new Emta(rs);
//				ret.add(mta);
//		    }
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

}
