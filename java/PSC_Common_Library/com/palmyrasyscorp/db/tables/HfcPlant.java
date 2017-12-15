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
public class HfcPlant extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(HfcPlant.class);

	private Long m_id;
	private Long m_hfc_res_id;
	private Long m_cmts_res_id;
	private java.sql.Timestamp m_last_updated;
	private String m_name;
	private Integer m_alert_level;
	public int getAlertLevel() {return m_alert_level.intValue();}
	private Boolean m_is_deleted;
	
	private LinkedList m_cable_modems = new LinkedList();
	private LinkedList m_emtas = new LinkedList();
	
	private CurrentCounts m_currentCounts;
	public void setCurrentCounts(CurrentCounts cc) {
		m_currentCounts = cc;
	}
	public CurrentCounts getCurrentCounts() {
		return (m_currentCounts);
	}
	
	protected HfcPlant() {}
	
	public HfcPlant(long res_id)
	{
		m_hfc_res_id = new Long(res_id);
		querySelf();
	}
	
	/**
	 * 
	 */
	public HfcPlant(ProxyDbResultSet rs)
	{
		int i=1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_hfc_res_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			i++; // last_updated
			m_name = rs.getString(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
	
	/**
	 * 
	 * @param rs
	 * @param ih
	 */
	public HfcPlant(ProxyDbResultSet rs, IntegerHolder ih)
	{
		int i=1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_hfc_res_id = (Long) rs.getObject(i++);
			m_cmts_res_id = (Long) rs.getObject(i++);
			i++; // last_updated
			m_name = rs.getString(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}

	public boolean isAlarmed() {
		boolean ret = AlarmHelper.isAlarmForResource(m_hfc_res_id);
		return (ret);
	}

	public boolean isServiceOutage() {
		boolean ret = ServiceOutageHelper.isOutageForResource(m_hfc_res_id);
		return (ret);
	}

	public boolean isPerformanceException() {
		boolean ret = PerformanceHelper.isExceptionForResource(m_hfc_res_id);
		return (ret);
	}

	public Long getId() { return m_id; }
	public Long getHfcResId() { return m_hfc_res_id; }
	public Long getCmtsResId() { return m_cmts_res_id; }
	public String getName() { return m_name; }
	public LinkedList getListOfCableModems() { return m_cable_modems; }
	public LinkedList getListOfEmtas() { return m_emtas; }
	
	public void queryChildren()
	{
		queryCableModemChildren();
		queryMtaChildren();
	}
	
	public void queryCableModemChildren()
	{
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
			String qryStr = GlobalQueries.QRY_CM_IN_HFC + getHfcResId().longValue();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while(rs.next()) {
				CableModem cm = new CableModem(rs);
				m_cable_modems.add(cm);
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
	
	public void queryMtaChildren()
	{
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
			String qryStr = GlobalQueries.QRY_MTA_IN_HFC + getHfcResId().longValue();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while(rs.next()) {
				Emta e = new Emta(rs);
				m_emtas.add(e);
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
	
	private void querySelf()
	{
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
			qryStr.append(" where h.hfc_res_id=").append(getHfcResId());

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i=1;
				m_id = (Long) rs.getObject(i++);
				i++; // m_hfc_res_id = (Long) rs.getObject(i++);
				m_cmts_res_id = (Long) rs.getObject(i++);
				i++; // last_updated
				m_name = rs.getString(i++);
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
	
//	public LinkedList queryOffCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OFFLINE_CM_IN_HFC + getHfcResId().longValue();
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
//	public LinkedList queryOnCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_ONLINE_CM_IN_HFC + getHfcResId().longValue();
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
//	public LinkedList queryNotOnAndNotOffCableModems()
//	{
//		LinkedList ret = new LinkedList();
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		  
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ST_OTHER_CM_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_INALARM_CM_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_INEXCEP_CM_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_OOS_CM_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_INALARM_MTA_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_INEXCEP_MTA_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_OOS_MTA_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_AVAIL_MTA_IN_HFC + getHfcResId().longValue();
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
//			String qryStr = GlobalQueries.QRY_UNAVAIL_MTA_IN_HFC + getHfcResId().longValue();
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
//		    String qryStr = GlobalQueries.QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_HFC + getHfcResId();
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
