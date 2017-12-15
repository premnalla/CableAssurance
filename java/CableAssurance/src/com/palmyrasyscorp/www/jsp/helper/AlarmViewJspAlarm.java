/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.text.DateFormat;

import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT;

/**
 * @author Prem
 *
 */
public class AlarmViewJspAlarm extends AbstractJspAlarm {

	/**
	 * 
	 * @param ca
	 * @param df
	 */
	public AlarmViewJspAlarm(CurrentAlarmT ca, DateFormat df) {
		super(ca.getAbstractAlarm(), df);
		setNetObjectTargetWindow(UrlHelper.SELF);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean generateHtml() {
		boolean ret = true;
		
		ret = super.generateHtml();
		
		return (ret);
	}

}
