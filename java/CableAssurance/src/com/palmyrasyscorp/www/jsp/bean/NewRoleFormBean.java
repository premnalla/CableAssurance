/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.palmyrasys.www.webservices.CableAssurance.RoleT;
import com.palmyrasyscorp.db.tables.OAAccess;
import com.palmyrasyscorp.db.tables.OAObject;
import com.palmyrasyscorp.db.tables.Role;

/**
 * @author Prem
 *
 */
public class NewRoleFormBean extends AbstractBean {

	private Role m_role = new Role();
	public Role getRole() { return m_role; }
	
	// private String m_roleName = "";
	private String m_cancel;
	private String m_save;
	
	
	public NewRoleFormBean() {
		super();
	}
	
	public NewRoleFormBean(RoleT role) {
		m_role.setRoleName(role.getRoleName());
	}
	
	public String getRoleName() {
		String ret = m_role.getRoleName();
		if (ret == null) {
			ret = "";
		}
		return (ret);
	}
	public void setRoleName(String s) {
		m_role.setRoleName(s);
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

		if (m_role.getRoleName() == null || 
				m_role.getRoleName().length()==0) {
			ret = false;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param request
	 */
	public void getAttributes(HttpServletRequest request) {
		try {
			List domList =  OAObject.GetList();
			List accList = OAAccess.GetList();
			Enumeration params = request.getParameterNames();
			for (; params.hasMoreElements(); ) {
				String param = (String) params.nextElement();
				StringTokenizer strtok = new StringTokenizer(param, "_");
				String domId = null;
				String accessId = null;
				if (strtok.hasMoreElements()) {
					strtok.nextElement();
				}
				if (strtok.hasMoreElements()) {
					domId = (String) strtok.nextElement();
				}
				if (strtok.hasMoreElements()) {
					accessId = (String) strtok.nextElement();
				}
				if (domId != null && accessId != null) {
					System.out.println(domId + "," + accessId);
					
					int iDomId = Integer.parseInt(domId);
					int iAccId = Integer.parseInt(accessId);
					OAObject oDom = (OAObject) domList.get(iDomId);
					OAAccess oAcc = (OAAccess) accList.get(iAccId);
					if (!m_role.getObjectMap().containsKey(oDom.getAppDomain())) {
						m_role.getObjectMap().put(oDom.getAppDomain(), oDom);
					} else {
						oDom = (OAObject) m_role.getObjectMap().get(oDom.getAppDomain());
					}
					oDom.getAccessMap().put(oAcc.getAccess(), oAcc);
				}
			}
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * 
	 * @param role
	 */
	public void setAttributes(RoleT role) {
		
	}
}
