/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Prem
 *
 */
public class GenericExtractionHelper extends AbstractEnduserExtractionHelper {

	private String m_prefix;
	public void setPrefix(String s) { m_prefix = s; }
	
	/**
	 * 
	 *
	 */
	protected GenericExtractionHelper() {
		super(null);
	}
	
	/**
	 * 
	 * @param req
	 */
	public GenericExtractionHelper(HttpServletRequest req) {
		super(req);
	}
	
	protected  String getPrefix() {
		return (m_prefix);
	}


}
