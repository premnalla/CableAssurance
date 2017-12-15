/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import
// com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;
import com.palmyrasys.www.webservices.CableAssurance.Reports.*;
import com.palmyrasys.www.webservices.CableAssurance.*;

// import com.palmyrasyscorp.db.tables.Blade;

/**
 * @author Prem
 * 
 */
public class ReportsHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(ReportsHelper.class);

	/**
	 * 
	 */
	private ReportsSOAPBindingStub m_binding = null;

	/**
	 * 
	 * 
	 */
	public ReportsHelper() {
		try {
			m_binding = (ReportsSOAPBindingStub) new ReportsServiceLocator()
					.getReportsEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			// jrlog.error(null, e);
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("m_binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	/**
	 * 
	 * @param host
	 */
	public ReportsHelper(String host) {
		try {
			ReportsServiceLocator loc = new ReportsServiceLocator();
			loc.setHost(host);
			m_binding = (ReportsSOAPBindingStub) loc.getReportsEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			// jrlog.error(null, e);
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("m_binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param rows
	 * @param queryState
	 * @return
	 */
	public HfcStatusSummaryRespT getHfcStatusSummary1(TopoHierarchyKeyT topologyKey,
			BigInteger resId, ResourceTypeT hierarchy, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT rows, QueryStateT[] queryState) {
		// Test operation
		HfcStatusSummaryRespT value = null;

		try {
			value = m_binding.getHfcStatusSummary1(topologyKey, resId, hierarchy, fromTime,
					toTime, rows, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param rows
	 * @param queryState
	 * @return
	 */
	public HfcStatusSummaryRespT getHfcStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT rows, QueryStateT[] queryState) {
		// Test operation
		HfcStatusSummaryRespT value = null;

		try {
			value = m_binding.getHfcStatusSummary2(topologyKey, resId, hierarchy, fromTime,
					toTime, rows, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public MtaStatusSummaryRespT getMtaStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		MtaStatusSummaryRespT value = null;

		try {
			value = m_binding.getMtaStatusSummary1(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public MtaStatusSummaryRespT getMtaStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		MtaStatusSummaryRespT value = null;

		try {
			value = m_binding.getMtaStatusSummary2(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public CmStatusSummaryRespT getCmStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		CmStatusSummaryRespT value = null;

		try {
			value = m_binding.getCmStatusSummary1(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public CmStatusSummaryRespT getCmStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		CmStatusSummaryRespT value = null;

		try {
			value = m_binding.getCmStatusSummary2(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public CmStatusSummaryRespT getCmTcaStatusSummary1(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		CmStatusSummaryRespT value = null;

		try {
			value = m_binding.getCmTcaStatusSummary1(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param resId
	 * @param hierarchy
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @param queryState
	 * @return
	 */
	public CmStatusSummaryRespT getCmTcaStatusSummary2(
			TopoHierarchyKeyT topologyKey, BigInteger resId, ResourceTypeT hierarchy,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT[] queryState) {
		CmStatusSummaryRespT value = null;

		try {
			value = m_binding.getCmTcaStatusSummary2(topologyKey, resId, hierarchy, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

}
