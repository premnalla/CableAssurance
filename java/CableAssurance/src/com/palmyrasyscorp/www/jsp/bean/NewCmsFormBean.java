/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class NewCmsFormBean extends AbstractBean {

	private String m_cmsName = "";
	private String m_cmsHost = "";
	private String m_cmsType = "";
	private String m_cmsSubType = "";
	private String m_cmsVendor = "";
	private String m_cmsSoapUrl = "";
	private String m_description = "";
	private String m_loginUserId = "";
	private String m_loginPw = "";	
	private String m_ipAddressType = "";
	private String m_addNewCms;
	private String m_cancelNewCms;
	private String m_updateCms;
	
	
	public NewCmsFormBean() {
		super();
	}
	
	public String getCmsName() {
		return m_cmsName;
	}
	public void setCmsName(String s) {
		m_cmsName = s;
	}
	
	public String getCmsHost() {
		return m_cmsHost;
	}
	public void setCmsHost(String s) {
		m_cmsHost = s;
	}
	
	public String getCmsVendor() {
		return m_cmsVendor;
	}
	public void setCmsVendor(String s) {
		m_cmsVendor = s;
	}
	
	public String getSoapUrl() {
		return m_cmsSoapUrl;
	}
	public void setSoapUrl(String s) {
		m_cmsSoapUrl = s;
	}
	
	public String getCmsType() {
		return m_cmsType;
	}
	public void setCmsType(String s) {
		m_cmsType = s;
	}
	
	public String getCmsSubType() {
		return m_cmsSubType;
	}
	public void setCmsSubType(String s) {
		m_cmsSubType = s;
	}
		
	public String getDescription() {
		return m_description;
	}
	public void setDescription(String s) {
		m_description = s;
	}
	
	public String getLoginUserId() {
		return m_loginUserId;
	}
	public void setLoginUserId(String s) {
		m_loginUserId = s;
	}

	public String getLoginPw() {
		return m_loginPw;
	}
	public void setLoginPw(String s) {
		m_loginPw = s;
	}

	public String getIpAddressType() {
		return m_ipAddressType;
	}
	public void setIpAddressType(String s) {
		m_ipAddressType = s;
	}

	public String getAdd() {
		return m_addNewCms;
	}
	public void setAdd(String s) {
		m_addNewCms = s;
	}

	public String getCancel() {
		return m_cancelNewCms;
	}
	public void setCancel(String s) {
		m_cancelNewCms = s;
	}

	public String getUpdate() {
		return m_updateCms;
	}
	public void setUpdate(String s) {
		m_updateCms = s;
	}

	public boolean validate() {
		boolean ret = true;

		if (m_cmsName.length()==0 ||
				m_cmsHost.length()==0
//				m_cmtsReadCommunity.length()==0 ||
//				m_cmReadCommunity.length()==0 ||
//				m_cmWriteCommunity.length()==0 ||
//				m_mtaReadCommunity.length()==0 ||
//				m_mtaWriteCommunity.length()==0
			) {
			ret = false;
		}
		return (ret);
	}
	
}
