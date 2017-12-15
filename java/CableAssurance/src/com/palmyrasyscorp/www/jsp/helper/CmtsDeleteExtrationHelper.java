/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Prem
 *
 */
public class CmtsDeleteExtrationHelper extends AbstractEnduserExtractionHelper {

	/**
	 * 
	 *
	 */
	protected CmtsDeleteExtrationHelper() {
		super(null);
	}
	
	/**
	 * 
	 * @param req
	 */
	public CmtsDeleteExtrationHelper(HttpServletRequest req) {
		super(req);
	}
	
	protected  String getPrefix() {
		return (UrlHelper.CMTS_DELETE_PREFIX);
	}

}
