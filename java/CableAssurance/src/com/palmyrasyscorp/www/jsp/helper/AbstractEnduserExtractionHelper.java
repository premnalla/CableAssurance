/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.util.List;
import java.util.LinkedList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Prem
 *
 */
public abstract class AbstractEnduserExtractionHelper extends
		AbstractExtractionHelper {

	/**
	 * 
	 */
	private HttpServletRequest m_req;
	protected HttpServletRequest getRequest() {
		return (m_req);
	}
	
	
	/**
	 * 
	 *
	 */
	private AbstractEnduserExtractionHelper() {
		
	}

	/**
	 * 
	 *
	 */
	protected AbstractEnduserExtractionHelper(HttpServletRequest req) {
		m_req = req;
	}

	protected abstract String getPrefix();
	
	/**
	 * 
	 * @return
	 */
	public List parseAttributes() {
		LinkedList ret = new LinkedList();
		
		String prefix = getPrefix();
		int prefixLen = prefix.length();

		// System.out.println("Trying to parse attribute-names from request");
		for (Enumeration e = m_req.getParameterNames(); e.hasMoreElements();) {
			// System.out.println("Multi-Compare request param: " + e.nextElement());
			String atNm = e.nextElement().toString();
			if (atNm.startsWith(prefix)) {
				String resId = atNm.substring(prefixLen);
				// System.out.println("ResID: " + resId);
				ret.add(new String(resId));
			}
		}
		
		return (ret);
	}
}
