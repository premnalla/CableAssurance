/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasys.www.webservices.CableAssurance.Performance.*;

/**
 * @author Prem
 * 
 */
public class PerformanceHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(PerformanceHelper.class);

	/**
	 * 
	 */
	private PerformanceSOAPBindingStub m_binding = null;

	/**
	 * 
	 *
	 */
	public PerformanceHelper() {

		try {
			m_binding = (PerformanceSOAPBindingStub) new PerformanceServiceLocator()
					.getPerformanceEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	/**
	 * 
	 * @param host
	 */
	public PerformanceHelper(String host) {

		try {
			PerformanceServiceLocator loc = new PerformanceServiceLocator();
			loc.setHost(host);
			m_binding = (PerformanceSOAPBindingStub) loc.getPerformanceEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	public GenericCountsT getCurrentCmtsCounts(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId) {
		GenericCountsT ret = null;

		try {
			ret = m_binding.getCurrentCmtsCounts(topologyKey, cmtsResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public GenericCountsHistoryT[] getCmtsCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmtsResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		GenericCountsHistoryT[] ret = null;

		try {
			ret = m_binding.getCmtsCountsHistory(topologyKey, cmtsResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public GenericCountsT getCurrentChannelCounts(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId) {
		GenericCountsT ret = null;

		try {
			ret = m_binding.getCurrentChannelCounts(topologyKey, channelResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public GenericCountsHistoryT[] getChannelCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger channelResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		GenericCountsHistoryT[] ret = null;

		try {
			ret = m_binding.getChannelCountsHistory(topologyKey, channelResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public GenericCountsT getCurrentHfcCounts(TopoHierarchyKeyT topologyKey,
			BigInteger hfcResId) {
		GenericCountsT ret = null;

		try {
			ret = m_binding.getCurrentHfcCounts(topologyKey, hfcResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public GenericCountsHistoryT[] getHfcCountsHistory(
			TopoHierarchyKeyT topologyKey, BigInteger hfcResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		GenericCountsHistoryT[] ret = null;

		try {
			ret = m_binding.getHfcCountsHistory(topologyKey, hfcResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public CmStatusT getCurrentCmStatus(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId) {
		CmStatusT ret = null;

		try {
			ret = m_binding.getCurrentCmStatus(topologyKey, cmResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public CmStatusHistoryT[] getCmStatusHistory(TopoHierarchyKeyT topologyKey,
			BigInteger cmResId, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT queryState) {
		CmStatusHistoryT[] ret = null;

		try {
			ret = m_binding.getCmStatusHistory(topologyKey, cmResId, fromTime,
					toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public CmCurrentPerformanceT getCurrentCmPerformance(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId) {
		CmCurrentPerformanceT ret = null;

		try {
			ret = m_binding.getCurrentCmPerformance(topologyKey, cmResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public CmPerformanceHistoryT[] getCmPerformanceHistory(
			TopoHierarchyKeyT topologyKey, BigInteger cmResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		CmPerformanceHistoryT[] ret = null;

		try {
			ret = m_binding.getCmPerformanceHistory(topologyKey, cmResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaAvailabilityT getCurrentMtaAvailability(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId) {
		MtaAvailabilityT ret = null;

		try {
			ret = m_binding.getCurrentMtaAvailability(topologyKey, mtaResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaAvailabilityHistoryT[] getMtaAvailabilityHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		MtaAvailabilityHistoryT[] ret = null;

		try {
			ret = m_binding.getMtaAvailabilityHistory(topologyKey, mtaResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaProvStatusT getCurrentMtaProvisionedStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId) {
		MtaProvStatusT ret = null;

		try {
			ret = m_binding.getCurrentMtaProvisionedStatus(topologyKey,
					mtaResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaProvStatusHistoryT[] getMtaProvisionedStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		MtaProvStatusHistoryT[] ret = null;

		try {
			ret = m_binding.getMtaProvisionedStatusHistory(topologyKey,
					mtaResId, fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaPingStatusT getCurrentMtaPingStatus(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId) {
		MtaPingStatusT ret = null;

		try {
			ret = m_binding.getCurrentMtaPingStatus(topologyKey, mtaResId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	public MtaPingStatusHistoryT[] getMtaPingStatusHistory(
			TopoHierarchyKeyT topologyKey, BigInteger mtaResId,
			InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch,
			QueryStateT queryState) {
		MtaPingStatusHistoryT[] ret = null;

		try {
			ret = m_binding.getMtaPingStatusHistory(topologyKey, mtaResId,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

}
