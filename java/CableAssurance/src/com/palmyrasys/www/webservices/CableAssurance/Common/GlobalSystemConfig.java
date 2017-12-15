/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

/**
 * @author Prem
 *
 */
public class GlobalSystemConfig {

	/**
	 * 
	 */
	private boolean m_mktHasCmts = true;
	
	/**
	 * 
	 */
	private String m_wsPort = "8080";
	
	/**
	 * 
	 */
	private static GlobalSystemConfig m_instance = null;
	
	/**
	 * 
	 *
	 */
	private GlobalSystemConfig() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static GlobalSystemConfig getInstance() {
		if (m_instance == null) {
			m_instance = new GlobalSystemConfig();
		}
		return (m_instance);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean doesMarketHaveCmts() {
		return (m_mktHasCmts);
	}
	
	/**
	 * 
	 * @param b
	 */
	public void setMarketHasCmts(boolean b) {
		m_mktHasCmts = b;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWebServicesPort() {
		return (m_wsPort);
	}
}
