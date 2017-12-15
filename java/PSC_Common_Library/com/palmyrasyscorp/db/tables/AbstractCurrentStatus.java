/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public class AbstractCurrentStatus extends AbstractDbObject {

	protected Long m_resId;
	
	protected Long m_sumGoodStateTime;
	public Long getSumGoodStateTime() { return m_sumGoodStateTime; }
	
	protected Long m_sumBadStateTime;
	public Long getSumBadStateTime() { return m_sumBadStateTime; }
	
	protected Long m_lastLogTime;
	public Long getLastLogTime() { return m_lastLogTime; }
	
	protected Long m_lastStateChangeTime;
	public Long getLastStateChangeTime() { return m_lastStateChangeTime; }
	
	protected Integer m_sumStateChanges;
	public Integer getSumStateChages() { return m_sumStateChanges; }
	
	protected Integer m_currentState;
	public Integer getCurrentState() { return m_currentState; }
	
	protected AbstractCurrentStatus() {
		
	}
	
	protected AbstractCurrentStatus(Long resId) {
		m_resId = resId;
	}
	
	public Long getResId() {
		return m_resId;
	}

}
