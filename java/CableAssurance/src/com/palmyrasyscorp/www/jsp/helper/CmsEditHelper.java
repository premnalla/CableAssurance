/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.math.BigInteger;
import java.util.StringTokenizer;

import com.palmyrasys.www.webservices.CableAssurance.CmsT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.Common.TopoKeyHelper;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;

/**
 * @author Prem
 *
 */
public class CmsEditHelper extends AbstractEditHelper {

	private String m_inputString;

	/**
	 * 
	 * 
	 */
	protected CmsEditHelper() {
		super();
	}

	/**
	 * 
	 * @param s
	 */
	public CmsEditHelper(String s) {
		super();
		m_inputString = s;
	}

	/**
	 * 
	 * @return
	 */
	public CmsT getCms() {
		CmsT ret = null;

		String rgnId = null;
		String mktId = null;
		String bldId = null;
		String resId = null;
		
		StringTokenizer strtok = new StringTokenizer(m_inputString,
				UrlHelper.ID_SEPERATOR);
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
			resId = strtok.nextToken();
		}

		TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(rgnId, mktId, bldId);
		BigInteger rId = new BigInteger(resId);
		TopologyHelper th = new TopologyHelper();

		ret = th.getCms(tK, rId);;

		return (ret);
	}
	
}
