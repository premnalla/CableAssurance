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
public abstract class AbstractMtaDataCreator extends DataCreatorBase {

	/**
	 * 
	 */
	private TopoHierarchyKeyT m_tK;

	/**
	 * 
	 * @return
	 */
	protected TopoHierarchyKeyT getTopKey() {
		return m_tK;
	}

	/**
	 * 
	 */
	private BigInteger m_mtaResId;

	/**
	 * 
	 * @return
	 */
	protected BigInteger getMtaResId() {
		return m_mtaResId;
	}

	/**
	 * 
	 */
	private LinkedList m_data = new LinkedList();

	/**
	 * 
	 * @return
	 */
	public LinkedList getMtaData() {
		return m_data;
	}

	/**
	 * 
	 */
	protected AbstractMtaDataCreator(TopoHierarchyKeyT tK, BigInteger mtaResId) {
		super();
		m_tK = tK;
		m_mtaResId = mtaResId;
	}

	/**
	 * 
	 */
	public void clearData() {
		m_data.clear();
	}

}
