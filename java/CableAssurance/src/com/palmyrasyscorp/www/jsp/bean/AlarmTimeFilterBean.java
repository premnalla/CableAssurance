/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class AlarmTimeFilterBean extends AbstractTimeBasedFilterBean {

	/**
	 * 
	 *
	 */
	public AlarmTimeFilterBean() {
		setDefaultBatchSize("4");
		setBatch("4");		
	}
}
