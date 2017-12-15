/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class MainAlarmTimeFilterBean extends AbstractTimeBasedFilterBean {

	/**
	 * 
	 *
	 */
	public MainAlarmTimeFilterBean() {
		setDefaultBatchSize("4");
		setBatch("4");
	}
}
