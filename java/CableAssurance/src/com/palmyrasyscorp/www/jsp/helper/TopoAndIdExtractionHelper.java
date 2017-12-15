/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 *
 */
public class TopoAndIdExtractionHelper extends AbstractEnduserExtractionHelper {

	private static Log log = LogFactory.getLog(TopoAndIdExtractionHelper.class);

	private String m_string;
	
	/**
	 * 
	 *
	 */
	public TopoAndIdExtractionHelper() {
		super(null);
	}
	
	/**
	 * 
	 *
	 */
	public TopoAndIdExtractionHelper(String s) {
		super(null);
		m_string = s;
	}
	
	/**
	 * Not used
	 */
	protected String getPrefix() {
		return ("");
	}

	/**
	 *
	 * @return
	 */
	public List parseAttributes(List l) {
		LinkedList ret = new LinkedList();
		
		try {
			for (int i=0; i<l.size(); i++) {
				String atNm = (String) l.get(i);
				String rgnId = null;
				String mktId = null;
				String bldId = null;
				String id = null;
				StringTokenizer strtok = new StringTokenizer(atNm, "_");
				if (strtok.hasMoreTokens()) {
					rgnId = strtok.nextToken();
				}
				if (strtok.hasMoreTokens()) {
					mktId = strtok.nextToken();
				}
				if (strtok.hasMoreTokens()) {
					bldId = strtok.nextToken();
				}
				if (strtok.hasMoreTokens()) {
					id = strtok.nextToken();
				}
				if (rgnId != null && mktId != null && bldId != null && id != null) {
					ret.add(new TopoKeyAndId(rgnId, mktId, bldId, id));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
				
		return (ret);
	}

	/**
	 *
	 * @return
	 */
	public List parseAttributes() {
		LinkedList ret = new LinkedList();

		String rgnId = null;
		String mktId = null;
		String bldId = null;
		String id = null;
		StringTokenizer strtok = new StringTokenizer(m_string, "_");
		if (strtok.hasMoreTokens()) {
			rgnId = strtok.nextToken();
		}
		if (strtok.hasMoreTokens()) {
			mktId = strtok.nextToken();
		}
		if (strtok.hasMoreTokens()) {
			bldId = strtok.nextToken();
		}
		if (strtok.hasMoreTokens()) {
			id = strtok.nextToken();
		}
		if (rgnId != null && mktId != null && bldId != null && id != null) {
			ret.add(new TopoKeyAndId(rgnId, mktId, bldId, id));
		}
		
		return (ret);
	}
	
}
