/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

// import java.util.StringTokenizer;
import com.palmyrasys.www.webservices.CableAssurance.UserT;

/**
 * @author Prem
 *
 */
public class NewUserFormBean extends AbstractBean {

	private String m_appUserId = "";
	private String m_firstName = "";
	private String m_middleInitial = "";
	private String m_lastName = "";
	private String m_loginUserId = "";
	private String m_loginPw = "";
	private String m_loginPw2 = "";	
	private String m_userLocation = "";
	private String m_roleName = "";
	private String m_accountActive;
	private String m_cancel;
	private String m_save;
	
	
	public NewUserFormBean() {
		super();
	}
	
	public NewUserFormBean(UserT u) {
		m_firstName = u.getFirstName();
		m_middleInitial = u.getMiddleInitial();
		m_lastName = u.getLastName();
		m_loginUserId = u.getLoginName();
		m_roleName = u.getRoleName();
		// m_loginPw = u.getLoginPassword();
		m_userLocation = u.getLocation();
		String isDeactive;
		if (u.getIsActive().shortValue()==0) {
			isDeactive = "on";
		} else {
			isDeactive = "off";			
		}
		m_accountActive = isDeactive;
	}
	
	public String getAppUserId() {
		return m_appUserId;
	}
	public void setAppUserId(String s) {
		m_appUserId = s;
	}
	
	public String getFirstName() {
		return m_firstName;
	}
	public void setFirstName(String s) {
		m_firstName = s;
	}
	
	public String getMiddleInitial() {
		return m_middleInitial;
	}
	public void setMiddleInitial(String s) {
		m_middleInitial = s;
	}
	
	public String getLastName() {
		return m_lastName;
	}
	public void setLastName(String s) {
		m_lastName = s;
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

	public String getLoginPw2() {
		return m_loginPw2;
	}
	public void setLoginPw2(String s) {
		m_loginPw2 = s;
	}

	public String getUserLocation() {
		return m_userLocation;
	}
	public void setUserLocation(String s) {
		m_userLocation = s;
	}

	public String getRoleName() {
		return m_roleName;
	}
	public void setRoleName(String rn) {
		m_roleName = rn;
	}
	
	public String getAccountActive() {
		return m_accountActive;
	}
	public void setAccountActive(String s) {
		m_accountActive = s;
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

	/**
	 * Validate new user
	 * @return
	 */
	public boolean validate() {
		boolean ret = true;

		if (m_firstName.length()==0
				|| m_lastName.length()==0
				|| m_loginUserId.length()==0
				|| m_userLocation.length()==0
				|| m_loginPw.length()==0
			) {
			ret = false;
		}
		
		/*
		 * user-id cannot have underscores
		 */
		if (ret) {
			if (m_loginUserId.contains("_")) {
				ret = false;
			}
		}
				
		return (ret);
	}
	
	/**
	 * Validate updates to existing user
	 * @return
	 */
	public boolean validateUpdate() {
		boolean ret = true;

		if (m_firstName.length()==0
				|| m_lastName.length()==0
				|| m_loginUserId.length()==0
				|| m_userLocation.length()==0
			) {
			ret = false;
		}
		
		/*
		 * user-id cannot have underscores
		 */
		if (ret) {
			if (m_loginUserId.contains("_")) {
				ret = false;
			}
		}
				
		return (ret);
	}

	public boolean validatePwReset() {
		boolean ret = true;

		if (m_loginPw.length()==0
				|| m_loginPw2.length()==0
			) {
			ret = false;
		}

		if (ret) {
			if (!m_loginPw.equals(m_loginPw2)) {
				ret = false;
			}
		}
		
		return (ret);
	}
}
