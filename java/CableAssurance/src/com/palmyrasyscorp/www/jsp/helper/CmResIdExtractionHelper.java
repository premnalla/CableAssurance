/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Prem
 *
 */
public class CmResIdExtractionHelper extends AbstractEnduserExtractionHelper {

	/**
	 * 
	 *
	 */
	protected CmResIdExtractionHelper() {
		super(null);
	}
	
	/**
	 * 
	 * @param req
	 */
	public CmResIdExtractionHelper(HttpServletRequest req) {
		super(req);
	}
	
	protected  String getPrefix() {
		return ("cm_");
	}

}
