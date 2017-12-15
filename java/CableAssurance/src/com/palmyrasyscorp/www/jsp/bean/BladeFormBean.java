/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class BladeFormBean extends AbstractBean {

	private String m_name = "";
	private String m_host = "";
	private String m_cancel;
	private String m_save;
	private String m_add;
		
	public BladeFormBean() {
		super();
	}
	
	public String getName() {
		return m_name;
	}
	public void setName(String s) {
		m_name = s;
	}
	
	public String getHost() {
		return m_host;
	}
	public void setHost(String s) {
		m_host = s;
	}
	
	public String getCancel() {
		return m_cancel;
	}
	public void setCancel(String s) {
		m_cancel = s;
	}

	public String getSave() {
		return m_save;
	}
	public void setSave(String s) {
		m_save = s;
	}	

	public String getAdd() {
		return m_add;
	}
	public void setAdd(String s) {
		m_add = s;
	}	
	
	public boolean validate() {
		boolean ret = true;

		if (m_name.length()==0 ||
				m_host.length()==0
			) {
			ret = false;
		}
		return (ret);		
	}
}
