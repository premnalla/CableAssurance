/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class LocalSystemFormBean extends AbstractBean {

	private String m_systemName = "";
	private String m_systemType = "";
	private String m_cancel;
	private String m_update;	
	
	public LocalSystemFormBean() {
		super();
	}
	
	public String getSystemName() {
		return m_systemName;
	}
	public void setSystemName(String s) {
		m_systemName = s;
	}
	
	public String getSystemType() {
		return m_systemType;
	}
	public void setSystemType(String s) {
		m_systemType = s;
	}
	
	public String getCancel() {
		return m_cancel;
	}
	public void setCancel(String s) {
		m_cancel = s;
	}

	public String getUpdate() {
		return m_update;
	}
	public void setUpdate(String s) {
		m_update = s;
	}	

}
