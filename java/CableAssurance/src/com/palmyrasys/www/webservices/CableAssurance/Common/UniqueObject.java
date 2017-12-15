/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * @author Prem
 *
 */
public interface UniqueObject {

	/**
	 * 
	 * @return
	 */
	public TopoHierarchyKeyT getTopologyHierarchyKey();
	
	/**
	 * 
	 * @return
	 */
	public long getId();
}
