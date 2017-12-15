/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class GeneralAdminFormBean extends AbstractBean {

	private String m_cmtsPollInterval = "";
	private String m_cmPollInterval = "";
	private String m_mtaPollInterval = "";
	private String m_mtaPingInterval = "";
	private String m_save;
	private String m_cancel;
	
	/**
	 * 
	 *
	 */
	public GeneralAdminFormBean() {
		
	}
	
	public String getCmtsPollInterval() {
		return m_cmtsPollInterval;
	}
	public void setCmtsPollInterval(String s) {
		m_cmtsPollInterval = s;
	}
	
	public String getCmPollInterval() {
		return m_cmPollInterval;
	}
	public void setCmPollInterval(String s) {
		m_cmPollInterval = s;
	}
	
	public String getMtaPollInterval() {
		return m_mtaPollInterval;
	}
	public void setMtaPollInterval(String s) {
		m_mtaPollInterval = s;
	}
	
	public String getMtaPingInterval() {
		return m_mtaPingInterval;
	}
	public void setMtaPingInterval(String s) {
		m_mtaPingInterval = s;
	}
	
	public String getSave() {
		return m_save;
	}
	public void setSave(String s) {
		m_save = s;
	}
	
	public String getCancel() {
		return m_cancel;
	}
	public void setCancel(String s) {
		m_cancel = s;
	}
}
