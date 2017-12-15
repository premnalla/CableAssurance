/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import java.math.BigInteger;
import java.util.LinkedList;

import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * @author Prem
 * 
 */
public abstract class AbstractCmDataCreator extends DataCreatorBase {

	private TopoHierarchyKeyT m_tK;

	protected TopoHierarchyKeyT getTopKey() {
		return m_tK;
	}

	private BigInteger m_cmResId;

	protected BigInteger getCmResId() {
		return m_cmResId;
	}

	private LinkedList m_data = new LinkedList();

	/**
	 * 
	 * @return
	 */
	public LinkedList getCmData() {
		return m_data;
	}

	/**
	 * 
	 */
	protected AbstractCmDataCreator(TopoHierarchyKeyT tK, BigInteger cmResId) {
		super();
		m_tK = tK;
		m_cmResId = cmResId;
	}

	/**
	 * 
	 */
	public void clearData() {
		m_data.clear();
	}

}
