/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.ProxyDbResultSet;

/**
 * @author Prem
 *
 */
public class CurrentCounts extends AbstractDbObject {

	private static Log log = LogFactory.getLog(CurrentCounts.class);

	protected Long m_resId;
	public Long getResId() {return m_resId;}

	protected Long m_timeSec;
	public Long getTimeSec() {return m_timeSec;}

	protected Integer m_cmTotal;
	public Integer getCmTotal() {return m_cmTotal;}
	
	protected Integer m_cmOnline;
	public Integer getCmOnline() {return m_cmOnline;}
	
	protected Integer m_mtaTotal;
	public Integer getMtaTotal() {return m_mtaTotal;}
	
	protected Integer m_mtaAvailable;
	public Integer getMtaAvailable() {return m_mtaAvailable;}

	/**
	 * 
	 *
	 */
	protected CurrentCounts() {
		
	}
	
	/**
	 * 
	 * @param rs
	 */
	public CurrentCounts(ProxyDbResultSet rs) {
		int i=1;
		try {
			i++; // skip 'id' field
			m_resId = (Long) rs.getObject(i++);
			m_timeSec = (Long) rs.getObject(i++);
			m_cmTotal = (Integer) rs.getObject(i++);
			m_cmOnline = (Integer) rs.getObject(i++);
			m_mtaTotal = (Integer) rs.getObject(i++);
			m_mtaAvailable = (Integer) rs.getObject(i++);
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
	public CurrentCounts(ProxyDbResultSet rs, IntegerHolder ih) {
		int i = ih.value;
		try {
			i++; // skip 'id' field
			m_resId = (Long) rs.getObject(i++);
			m_timeSec = (Long) rs.getObject(i++);
			m_cmTotal = (Integer) rs.getObject(i++);
			m_cmOnline = (Integer) rs.getObject(i++);
			m_mtaTotal = (Integer) rs.getObject(i++);
			m_mtaAvailable = (Integer) rs.getObject(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}
	
}
