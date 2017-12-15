/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.ProxyDbResultSet;

/**
 * @author Prem
 * 
 */
public abstract class AbstractStatusSummary extends AbstractSummary {

	private static Log log = LogFactory.getLog(AbstractStatusSummary.class);

	protected Long m_resId;
	
	public Long getResId() {
		return m_resId;
	}

	protected Integer m_day;

	public Integer getDay() {
		return m_day;
	}

	protected Integer m_month;

	public Integer getMonth() {
		return m_month;
	}

	protected Integer m_year;

	public Integer getYear() {
		return m_year;
	}

	protected Long m_sumGoodStateTime;

	public Long getSumGoodStateTime() {
		return m_sumGoodStateTime;
	}

	protected Long m_sumBadStateTime;

	public Long getSumBadStateTime() {
		return m_sumBadStateTime;
	}

	protected Integer m_sumStateChanges;

	public Integer getSumStateChages() {
		return m_sumStateChanges;
	}

	/**
	 * 
	 * 
	 */
	protected AbstractStatusSummary() {
		super();
	}

	/**
	 * 
	 * 
	 */
	protected AbstractStatusSummary(ProxyDbResultSet rs) {
		super();
		
		int i = 1;
		try {
			i++; // skip id;
			m_resId = (Long) rs.getObject(i++);
			m_day = (Integer) rs.getObject(i++);
			m_month = (Integer) rs.getObject(i++);
			m_year = (Integer) rs.getObject(i++);
			m_sumGoodStateTime = (Long) rs.getObject(i++);
			m_sumBadStateTime = (Long) rs.getObject(i++);
			m_sumStateChanges = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * 
	 */
	protected AbstractStatusSummary(ProxyDbResultSet rs, boolean summaryOnly) {
		super();
		
		int i = 1;
		try {
			// i++; // skip id;
			m_resId = (Long) rs.getObject(i++);
			// m_day = (Integer) rs.getObject(i++);
			// m_month = (Integer) rs.getObject(i++);
			// m_year = (Integer) rs.getObject(i++);
			m_sumGoodStateTime = (Long) rs.getObject(i++);
			m_sumBadStateTime = (Long) rs.getObject(i++);
			m_sumStateChanges = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param qryStr
	 * @param fromDate
	 * @param toDate
	 */
	protected void appendDateFilter(StringBuffer qryStr, Calendar fromDate,
			Calendar toDate) {
		if (fromDate.get(Calendar.YEAR) == toDate.get(Calendar.YEAR) &&
			fromDate.get(Calendar.MONTH) == toDate.get(Calendar.MONTH) &&
			fromDate.get(Calendar.DAY_OF_MONTH) == toDate.get(Calendar.DAY_OF_MONTH)) {
			qryStr.append(" m.year=").append(fromDate.get(Calendar.YEAR))
			.append(" and m.month=").append(fromDate.get(Calendar.MONTH))
			.append(" and m.day=").append(fromDate.get(Calendar.DAY_OF_MONTH));
		} else {
			qryStr.append(" m.year>=").append(fromDate.get(Calendar.YEAR))
			.append(" and m.year<=").append(toDate.get(Calendar.YEAR))
			.append(" and m.month>=").append(fromDate.get(Calendar.MONTH))
			.append(" and m.month<=").append(toDate.get(Calendar.MONTH))
			.append(" and m.day>=").append(fromDate.get(Calendar.DAY_OF_MONTH))
			.append(" and m.day<=").append(toDate.get(Calendar.DAY_OF_MONTH));
		}
	}
	
	/**
	 * 
	 * @param rs
	 * @param startId
	 */
	protected void seekToBeginingOfBatch(ProxyDbResultSet rs, long startId) {
		try {
			if (startId != 0) {
				while(rs.next()) {
					if (rs.getLong(1) == startId) {
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
}
