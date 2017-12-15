/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.util.StringTokenizer;
import java.math.BigInteger;

// import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT;
import com.palmyrasys.www.webservices.CableAssurance.Common.TopoKeyHelper;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;

/**
 * @author Prem
 * 
 */
public class CmtsEditHelper extends AbstractEditHelper {

	private String m_inputString;

	/**
	 * 
	 * 
	 */
	protected CmtsEditHelper() {
		super();
	}

	/**
	 * 
	 * @param s
	 */
	public CmtsEditHelper(String s) {
		super();
		m_inputString = s;
	}

	/**
	 * 
	 * @return
	 */
	public CmtsEditHolder getCmts() {
		CmtsEditHolder ret = null;

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

		CmtsEditHolder h = new CmtsEditHolder();
		h.cmts = th.getCmts(tK, rId);;

		SnmpV2CAttributesT[] v2cAttrs = th.getCmtsAllSnmpV2CAttributes(tK, rId);
		if (v2cAttrs != null && v2cAttrs.length == 3) {
			int i = 0;
			h.cmtsV2C = v2cAttrs[i++];
			h.cmV2C = v2cAttrs[i++];
			h.mtaV2C = v2cAttrs[i++];
			ret = h;
		}
				
		return (ret);
	}
	
}
