/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 *
 */
public class CmStatusSummary extends AbstractStatusSummary {

	private static Log log = LogFactory.getLog(CmStatusSummary.class);

	private String m_cmMac;
	
	public String getCmMac() {
		return m_cmMac;
	}
	
	private Long m_hfcResId;

	public Long getHfcResId() {
		return m_hfcResId;
	}

	private String m_hfcName;

	public String getHfcName() {
		return m_hfcName;
	}

	private Long m_cmtsResId;

	public Long getCmtsResId() {
		return m_cmtsResId;
	}

	private String m_cmtsName;

	public String getCmtsName() {
		return m_cmtsName;
	}

	/**
	 * 
	 * 
	 */
	public CmStatusSummary() {
		super();
	}

	/**
	 * 
	 * @param rs
	 */
	public CmStatusSummary(ProxyDbResultSet rs) {
		super(rs);
	}

	/**
	 * 
	 * @param rs
	 * @param summaryOnly
	 */
	public CmStatusSummary(ProxyDbResultSet rs, boolean summaryOnly) {
		super();

		int i = 1;
		BigDecimal bd;
		try {
			// i++; // skip id;
			m_resId = (Long) rs.getObject(i++);
			// m_day = (Integer) rs.getObject(i++);
			// m_month = (Integer) rs.getObject(i++);
			// m_year = (Integer) rs.getObject(i++);
			
			bd = (BigDecimal) rs.getObject(i++);
			m_sumGoodStateTime = new Long(bd.longValue());
			
			bd = (BigDecimal) rs.getObject(i++);
			m_sumBadStateTime = new Long(bd.longValue());
			
			bd = (BigDecimal) rs.getObject(i++);
			m_sumStateChanges = new Integer(bd.intValue());
			
			m_cmMac = rs.getString(i++);
			m_hfcResId = (Long) rs.getObject(i++);
			m_hfcName = rs.getString(i++);
			m_cmtsResId = (Long) rs.getObject(i++);
			m_cmtsName = rs.getString(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * Utility method to query and get resultset in a in a list
	 * 
	 * @param query
	 * @param startId
	 * @return
	 */
	protected List<CmStatusSummary> queryDb(String qryStr, short batchSize, long startId) {
		LinkedList<CmStatusSummary> ret = new LinkedList<CmStatusSummary>();

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr);

			short i = 0;
			seekToBeginingOfBatch(rs, startId);
			while (rs.next()) {
				if (i < batchSize) {
					CmStatusSummary s = new CmStatusSummary(rs, true);
					// System.out.println("Addeing MtaStatusSummary...");
					ret.add(s);
				} else {
					break;
				}
				i++;
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
	 * @param fromSec
	 * @param toSec
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getLongestOffline(Calendar fromDate, Calendar toDate,
			short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY);

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_1);

		// System.out.println(qryStr);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);		
	}

	/**
	 * 
	 * @param cmtsResId
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getLongestOfflineForCmts(long cmtsResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY_FOR_CMTS);

		qryStr.append("cmts.cmts_res_id=").append(cmtsResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_1);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);		
	}

	/**
	 * 
	 * @param hfcResId
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getLongestOfflineForHfc(long hfcResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY_FOR_HFC);

		qryStr.append("hfc.hfc_res_id=").append(hfcResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_1);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);
	}

	/**
	 * 
	 * @param fromSec
	 * @param toSec
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getHighestOfflineFrequency(Calendar fromDate, Calendar toDate,
			short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY);

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_2);

		System.out.println(qryStr);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);
	}

	/**
	 * 
	 * @param cmtsResId
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getHighestOfflineFrequencyForCmts(long cmtsResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY_FOR_CMTS);

		qryStr.append("cmts.cmts_res_id=").append(cmtsResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_2);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);
	}

	/**
	 * 
	 * @param hfcResId
	 * @param fromDate
	 * @param toDate
	 * @param batchSize
	 * @param startId
	 * @return
	 */
	public List getHighestOfflineFrequencyForHfc(long hfcResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_CM_STATUS_SUMMARY_FOR_HFC);

		qryStr.append("hfc.hfc_res_id=").append(hfcResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_CM_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_CM_STATUS_SUMM_ORDERBY_2);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);
	}

}
