/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Prem
 *
 */
public class CmtsEditExtractionHelper extends AbstractEnduserExtractionHelper {

	/**
	 * 
	 *
	 */
	protected CmtsEditExtractionHelper() {
		super(null);
	}
	
	/**
	 * 
	 * @param req
	 */
	public CmtsEditExtractionHelper(HttpServletRequest req) {
		super(req);
	}
	
	protected  String getPrefix() {
		return (UrlHelper.CMTS_EDIT_PREFIX);
	}


}
