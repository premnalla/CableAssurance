/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;

/**
 * Used to save Query States for a Servlet Session.
 * 
 * @author Prem
 *
 */
public class SessionQueryStates {

	/**
	 * View Alarms page
	 */
	private QueryStateT[] m_viewAlarmsQs = null;
	public void setViewAlarmsQs(QueryStateT[] in) {
		m_viewAlarmsQs = in;
	}
	public QueryStateT[] getViewAlarmsQs() {
		return (m_viewAlarmsQs);
	}
	
	/**
	 * 
	 *
	 */
	public SessionQueryStates() {
		
	}
}
