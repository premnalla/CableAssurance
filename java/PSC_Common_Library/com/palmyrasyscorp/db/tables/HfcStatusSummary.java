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
public class HfcStatusSummary extends AbstractStatusSummary {

	private static Log log = LogFactory.getLog(HfcStatusSummary.class);

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
	public HfcStatusSummary() {
		super();
	}

	/**
	 * 
	 * @param rs
	 */
	public HfcStatusSummary(ProxyDbResultSet rs) {
		super(rs);
	}

	/**
	 * 
	 * @param rs
	 * @param summaryOnly
	 */
	public HfcStatusSummary(ProxyDbResultSet rs, boolean summaryOnly) {
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
	protected List<HfcStatusSummary> queryDb(String qryStr, short batchSize, long startId) {
		List<HfcStatusSummary> ret = new LinkedList<HfcStatusSummary>();

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
					HfcStatusSummary s = new HfcStatusSummary(rs, true);
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
	public List getLongestAlarm(Calendar fromDate, Calendar toDate,
			short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_HFC_STATUS_SUMMARY);

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_HFC_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_HFC_STATUS_SUMM_ORDERBY_1);

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
	public List getLongestAlarmForCmts(long cmtsResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_HFC_STATUS_SUMMARY_FOR_CMTS);

		qryStr.append("cmts.cmts_res_id=").append(cmtsResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_HFC_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_HFC_STATUS_SUMM_ORDERBY_1);

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
	public List getHighestAlarmFrequency(Calendar fromDate, Calendar toDate,
			short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_HFC_STATUS_SUMMARY);

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_HFC_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_HFC_STATUS_SUMM_ORDERBY_2);

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
	public List getHighestAlarmFrequencyForCmts(long cmtsResId, Calendar fromDate,
			Calendar toDate, short batchSize, long startId) {
		List ret;

		StringBuffer qryStr = new StringBuffer(
				GlobalQueries.QRY_HFC_STATUS_SUMMARY_FOR_CMTS);

		qryStr.append("cmts.cmts_res_id=").append(cmtsResId).append(" and ");

		appendDateFilter(qryStr, fromDate, toDate);

		qryStr.append(GlobalQueries.QRY_HFC_STATUS_SUMM_GRPBY).append(
				GlobalQueries.QRY_HFC_STATUS_SUMM_ORDERBY_2);

		ret = queryDb(qryStr.toString(), batchSize, startId);

		return (ret);
	}

}
