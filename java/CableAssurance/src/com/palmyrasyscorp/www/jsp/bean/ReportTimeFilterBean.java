/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class ReportTimeFilterBean extends AbstractTimeBasedFilterBean {

	/**
	 * 
	 *
	 */
	public ReportTimeFilterBean() {
		setDefaultBatchSize("50");
		setBatch("50");		
	}

}
