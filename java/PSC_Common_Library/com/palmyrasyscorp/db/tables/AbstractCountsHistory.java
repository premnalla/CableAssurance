/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public abstract class AbstractCountsHistory extends AbstractHistory {

	protected Long m_resId;

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
	protected AbstractCountsHistory() {

	}

	/**
	 * 
	 * @param resId
	 */
	public AbstractCountsHistory(Long resId) {
		m_resId = resId;
	}


}
