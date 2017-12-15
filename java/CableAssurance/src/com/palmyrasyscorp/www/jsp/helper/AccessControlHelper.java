/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT;
import com.palmyrasys.www.webservices.CableAssurance.RoleT;
import com.palmyrasys.www.webservices.CableAssurance.UserAccessT;

/**
 * @author Prem
 *
 */
public class AccessControlHelper {

	/**
	 * 
	 *
	 */
	private AccessControlHelper() {
		
	}
	
	
	/**
	 * 
	 * @param domName
	 * @return
	 */
	public static ApplicationDomainT GetDomainInRole(RoleT role, String domName) {
		ApplicationDomainT ret = null;
		
		try {
			ApplicationDomainT[] doms = role.getDomains();
			if (doms != null) {
				for (int i=0; i<doms.length; i++) {
					if (domName.equals(doms[i].getType().getValue())) {
						ret = doms[i];
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param domName
	 * @return
	 */
	public static UserAccessT GetAccessRightInDomain(ApplicationDomainT dom, String accessRight) {
		UserAccessT ret = null;
		
		try {
			UserAccessT[] rights = dom.getAccessRights();
			if (rights != null) {
				for (int i=0; i<rights.length; i++) {
					if (accessRight.equals(rights[i].getType().getValue())) {
						ret = rights[i];
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (ret);
	}
	
	
}
