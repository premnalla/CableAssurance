/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class NewCmtsFormBean extends AbstractBean {

	private String m_bladeName = "";
	private String m_cmtsName = "";
	private String m_cmtsHost = "";
	private String m_cmtsReadCommunity = "";
	private String m_cmReadCommunity = "";
	private String m_cmWriteCommunity = "";
	private String m_mtaReadCommunity = "";
	private String m_mtaWriteCommunity = "";
	private String m_addNewCmts;
	private String m_cancelNewCmts;	
	private String m_updateCmts;	
	
	public NewCmtsFormBean() {
		super();
	}
	
	public String getBladeName() {
		return m_bladeName;
	}
	public void setBladeName(String s) {
		m_bladeName = s;
	}
	
	public String getCmtsName() {
		return m_cmtsName;
	}
	public void setCmtsName(String s) {
		m_cmtsName = s;
	}
	
	public String getCmtsHost() {
		return m_cmtsHost;
	}
	public void setCmtsHost(String s) {
		m_cmtsHost = s;
	}
	
	public String getCmtsReadCommunity() {
		return m_cmtsReadCommunity;
	}
	public void setCmtsReadCommunity(String s) {
		m_cmtsReadCommunity = s;
	}
	
	public String getCmReadCommunity() {
		return m_cmReadCommunity;
	}
	public void setCmReadCommunity(String s) {
		m_cmReadCommunity = s;
	}
	
	public String getCmWriteCommunity() {
		return m_cmWriteCommunity;
	}
	public void setCmWriteCommunity(String s) {
		m_cmWriteCommunity = s;
	}
		
	public String getMtaReadCommunity() {
		return m_mtaReadCommunity;
	}
	public void setMtaReadCommunity(String s) {
		m_mtaReadCommunity = s;
	}
	
	public String getMtaWriteCommunity() {
		return m_mtaWriteCommunity;
	}
	public void setMtaWriteCommunity(String s) {
		m_mtaWriteCommunity = s;
	}

	public String getAddNewCmts() {
		return m_addNewCmts;
	}
	public void setAddNewCmts(String s) {
		m_addNewCmts = s;
	}

	public String getCancelNewCmts() {
		return m_cancelNewCmts;
	}
	public void setCancelNewCmts(String s) {
		m_cancelNewCmts = s;
	}

	public String getUpdateCmts() {
		return m_updateCmts;
	}
	public void setUpdateCmts(String s) {
		m_updateCmts = s;
	}

	public boolean validate() {
		boolean ret = true;

//		private String m_cmtsName = "";
//		private String m_cmtsHost = "";
//		private String m_cmtsReadCommunity = "";
//		private String m_cmReadCommunity = "";
//		private String m_cmWriteCommunity = "";
//		private String m_mtaReadCommunity = "";
//		private String m_mtaWriteCommunity = "";

		if (m_cmtsName.length()==0 ||
				m_cmtsHost.length()==0 ||
				m_cmtsReadCommunity.length()==0 ||
				m_cmReadCommunity.length()==0 ||
				m_cmWriteCommunity.length()==0 ||
				m_mtaReadCommunity.length()==0 ||
				m_mtaWriteCommunity.length()==0) {
			ret = false;
		}
		return (ret);
	}
}
