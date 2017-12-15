/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance;

import java.math.BigInteger;

/**
 * @author Prem
 *
 */
public final class TopoKeyHelper {

	private TopoKeyHelper() {
		
	}
	
	public static TopoHierarchyKeyT getTopoKey(String rgnId, String mktId, String bldId) {
		TopoHierarchyKeyT ret;
		
		BigInteger rId = new BigInteger(rgnId);
		BigInteger mId = new BigInteger(mktId);
		BigInteger bId = new BigInteger(bldId);
		ret = new TopoHierarchyKeyT(rId, mId, bId);
		
		return (ret);
	}
}
