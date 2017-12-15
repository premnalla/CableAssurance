/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;
import com.palmyrasys.www.webservices.CableAssurance.Topology.*;
import com.palmyrasys.www.webservices.CableAssurance.*;
// import com.palmyrasyscorp.db.tables.Blade;

/**
 * @author Prem
 *
 */
public class TopologyHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(TopologyHelper.class);

	/**
	 * 
	 */
	private TopologySOAPBindingStub m_binding = null;
	
	/**
	 * 
	 *
	 */
	public TopologyHelper() {
		try {
			m_binding = (TopologySOAPBindingStub) new TopologyServiceLocator()
					.getTopologyEP();
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
	 *
	 */
	public TopologyHelper(String host) {
		try {
			TopologyServiceLocator loc = new TopologyServiceLocator();
			loc.setHost(host);
			m_binding = (TopologySOAPBindingStub) loc.getTopologyEP();
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
	 * @param resourceId
	 * @return
	 */
	public ResourceT getResource(TopoHierarchyKeyT topologyKey,
			BigInteger resourceId) {
		// Test operation
		ResourceT value = null;
		
		try {
			value = m_binding.getResource(topologyKey, resourceId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @return
	 */
	public RegionT[] getRegions() {
		// Test operation
		RegionT[] value = null;
		
		try {
			value = m_binding.getRegions();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @return
	 */
	public MarketT[] getMarkets() {
		// Test operation
		MarketT[] value = null;
		
		try {
			value = m_binding.getMarkets();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}
	
	/**
	 * 
	 * @return
	 */
	public BladeT[] getBlades() {
		// Test operation
		BladeT[] value = null;
		
		try {
			value = m_binding.getBlades();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}
	
	/**
	 * 
	 * @param regionId
	 * @param marketId
	 * @param bladeId
	 * @return
	 */
    public BladeT getBlade(BigInteger regionId, BigInteger marketId, 
    		BigInteger bladeId) {
    	BladeT ret = null;
    	
		try {
			ret = m_binding.getBlade(regionId, marketId, bladeId);
		} catch (Exception e) {
			log.error(null, e);
		}
    	
    	return (ret);
    }

    /**
     * 
     * @param regionId
     * @param marketId
     * @param bladeName
     * @return
     */
    public BladeT getBladeByName(BigInteger regionId, BigInteger marketId, String bladeName) {
    	BladeT ret = null;
    	
		try {
			ret = m_binding.getBladeByName(regionId, marketId, bladeName);
		} catch (Exception e) {
			log.error(null, e);
		}
    	
    	return (ret);
    }


    /**
	 * 
	 * @return
	 */
	public CmsT[] getCmses() {
		// Test operation
		CmsT[] value = null;
		
		try {
			value = m_binding.getCmses();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @return
	 */
	public CmtsT[] getCmtses() {
		// Test operation
		CmtsT[] value = null;
		
		try {
			value = m_binding.getCmtses();
			/*
			 * Debug
			 * 
			System.out.println("\n CMTS's start: client");
			for(int i=0; i<value.length; i++) {
				CmtsT c = value[i];
				System.out.println(c.getCmtsName());
			}
			System.out.println("\n CMTS's end");
			 *
			 */
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public CmsT getCms(TopoHierarchyKeyT tK, BigInteger cmsResId) {
		CmsT ret = null;

		try {
			ret = m_binding.getCms(tK, cmsResId);
			/*
			 * Debug
			 */
			System.out.println("\n CMS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public SnmpV2CAttributesT getCmtsSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			ret = m_binding.getCmtsSnmpV2CAttributes(tK, cmsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public SnmpV2CAttributesT getCmSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			ret = m_binding.getCmSnmpV2CAttributes(tK, cmsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public SnmpV2CAttributesT getMtaSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmsResId) {
		SnmpV2CAttributesT ret = null;

		try {
			ret = m_binding.getMtaSnmpV2CAttributes(tK, cmsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public SnmpV2CAttributesT[] getCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmsResId) {
		SnmpV2CAttributesT[] ret = null;

		try {
			ret = m_binding.getCmtsAllSnmpV2CAttributes(tK, cmsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public CmtsT getCmts(TopoHierarchyKeyT tK, BigInteger cmtsResId) {
		CmtsT ret = null;

		try {
			ret = m_binding.getCmts(tK, cmtsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
	}
	
    public CmtsT getCmtsByName(TopoHierarchyKeyT topologyKey, String cmtsName) {
		CmtsT ret = null;

		try {
			ret = m_binding.getCmtsByName(topologyKey, cmtsName);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return (ret);
    }

	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public ChannelT[] getChannels(TopoHierarchyKeyT tK, BigInteger cmtsResId) {
		ChannelT[] ret = null;

		try {
			ret = m_binding.getChannels(tK, cmtsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param chnlResId
	 * @return
	 */
	public ChannelT getChannel(TopoHierarchyKeyT tK, BigInteger chnlResId) {
		ChannelT ret = null;

		try {
			ret = m_binding.getChannel(tK, chnlResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param cmtsResId
	 * @return
	 */
	public HfcT[] getHfcs(TopoHierarchyKeyT tK, BigInteger cmtsResId) {
		HfcT[] ret = null;
		
		try {
			ret = m_binding.getHfcs(tK, cmtsResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param hfcResId
	 * @return
	 */
	public HfcT getHfc(TopoHierarchyKeyT tK, BigInteger hfcResId) {
		HfcT ret = null;
		
		try {
			ret = m_binding.getHfc(tK, hfcResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param chnlResId
	 * @return
	 */
	public CableModemT[] getChannelCmes(TopoHierarchyKeyT tK, BigInteger chnlResId) {
		CableModemT[] ret = null;
		
		try {
			ret = m_binding.getChannelCmes(tK, chnlResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param chnlResId
	 * @return
	 */
	public EmtaT[] getChannelEmtas(TopoHierarchyKeyT tK, BigInteger chnlResId) {
		EmtaT[] ret = null;
		
		try {
			ret = m_binding.getChannelEmtas(tK, chnlResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param tK
	 * @param chnlResId
	 * @return
	 */
	public CableModemT[] getHfcCmes(TopoHierarchyKeyT tK, BigInteger hfcResId) {
		CableModemT[] ret = null;
		
		try {
			ret = m_binding.getHfcCmes(tK, hfcResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param chnlResId
	 * @return
	 */
	public EmtaT[] getHfcEmtas(TopoHierarchyKeyT tK, BigInteger hfcResId) {
		EmtaT[] ret = null;
		
		try {
			ret = m_binding.getHfcEmtas(tK, hfcResId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param hfcResId
	 * @return
	 */
	public CableModemT getCableModem(TopoHierarchyKeyT tK, BigInteger resId) {
		CableModemT ret = null;

		try {
			ret = m_binding.getCableModem(tK, resId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}
	
	public EmtaT getEmta(TopoHierarchyKeyT tK, BigInteger resId) {
		EmtaT ret = null;

		try {
			ret = m_binding.getEmta(tK, resId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param resId
	 * @return
	 */
	public EmtaSecondaryT getEmtaSecondary(TopoHierarchyKeyT tK, BigInteger resId) {
		EmtaSecondaryT ret = null;

		try {
			ret = m_binding.getEmtaSecondary(tK, resId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public MappedEuDevicesT getDevicesForCsrPortal(String cmMac, String mtaMac) {
		MappedEuDevicesT ret = null;

		try {
			ret = m_binding.getDevicesForCsrPortal(cmMac, mtaMac);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);		
	}

	/**
	 * 
	 */
	public CACustomerT getCustomerForResource(TopoHierarchyKeyT topologyKey,
			BigInteger resourceId) {
		CACustomerT ret = null;

		try {
			ret = m_binding.getCustomerForResource(topologyKey, resourceId);
			/*
			 * Debug
			 */
			// System.out.println("\n CMTS's start: client");
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);		
	}

}
