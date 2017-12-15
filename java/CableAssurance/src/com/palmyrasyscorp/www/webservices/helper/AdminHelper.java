/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.Administration.*;
import com.palmyrasys.www.webservices.CableAssurance.*;

/**
 * @author Prem
 *
 */
public class AdminHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(AdminHelper.class);

	/**
	 * 
	 */
	private AdministrationSOAPBindingStub m_binding = null;
	
	/**
	 * 
	 *
	 */
	public AdminHelper() {
		try {
			m_binding = (AdministrationSOAPBindingStub) new AdministrationServiceLocator()
					.getAdministrationEP();
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
	public AdminHelper(String host) {
		try {
			AdministrationServiceLocator loc = new AdministrationServiceLocator();
			loc.setHost(host);
			m_binding = (AdministrationSOAPBindingStub) loc.getAdministrationEP();
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

    public short updateLocalSystem(LocalSystemT l) {
		short ret = -1;
		
		try {
			ret = m_binding.updateLocalSystem(l);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short updateRegion(RegionT r) {
		short ret = -1;
		
		try {
			ret = m_binding.updateRegion(r);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short addRegion(RegionT r) {
		short ret = -1;
		
		try {
			ret = m_binding.addRegion(r);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short updateMarket(MarketT m) {
		short ret = -1;
		
		try {
			ret = m_binding.updateMarket(m);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short addMarket(MarketT m) {
		short ret = -1;
		
		try {
			ret = m_binding.addMarket(m);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short updateBlade(BladeT b) {
		short ret = -1;
		
		try {
			ret = m_binding.updateBlade(b);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }

    public short addBlade(BladeT b) {
		short ret = -1;
		
		try {
			ret = m_binding.addBlade(b);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
    }


	/**
	 * 
	 */
	public short updateCmts(CmtsT cmts) {
		short ret = -1;
		
		try {
			ret = m_binding.updateCmts(cmts);
		} catch (Exception e) {
			log.error(null, e);
		}
		
		return ret;
	}

	/**
	 * 
	 */
    public short addCmts(CmtsT cmts) {
		short ret = -1;
    	
		try {
			ret = m_binding.addCmts(cmts);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);
    }
    
    /**
     * 
     */
    public short deleteCmts(CmtsT cmts) {
		short ret = -1;
    	
		try {
			ret = m_binding.deleteCmts(cmts);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }
    
    /**
     * 
     */
    public short addCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmtsResId, 
    		SnmpV2CAttributesT[] attributes) {
		short ret = -1;
    	
		try {
			ret = m_binding.addCmtsAllSnmpV2CAttributes(tK, cmtsResId, attributes);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);
    }
    
    /**
     * 
     */
    public short updateCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT tK, BigInteger cmtsResId, 
    		SnmpV2CAttributesT[] attributes) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCmtsAllSnmpV2CAttributes(tK, cmtsResId, attributes);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }
    
    /**
     * 
     */
    public short addCms(CmsT cms) {
		short ret = -1;
    	
		try {
			ret = m_binding.addCms(cms);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }
    
    /**
     * 
     */
    public short deleteCms(CmsT cms) {
		short ret = -1;
    	
		try {
			ret = m_binding.deleteCms(cms);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

    /**
     * 
     * @return
     */
	public PollingIntervalsT getPollingIntervals() {
		PollingIntervalsT ret = null;
    	
		try {
			ret = m_binding.getPollingIntervals();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 * @param pollintInterval
	 * @return
	 */
	public short updatePollingIntervals(PollingIntervalsT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updatePollingIntervals(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 * @return
	 */
    public HfcAlarmConfigT getHfcAlarmConfig() {
    	HfcAlarmConfigT ret = null;
    	
		try {
			ret = m_binding.getHfcAlarmConfig();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

	/**
	 * 
	 * @return
	 */
    public MtaAlarmConfigT getMtaAlarmConfig() {
    	MtaAlarmConfigT ret = null;
    	
		try {
			ret = m_binding.getMtaAlarmConfig();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

	/**
	 * 
	 * @return
	 */
    public CmtsAlarmConfigT getCmtsAlarmConfig() {
    	CmtsAlarmConfigT ret = null;
    	
		try {
			ret = m_binding.getCmtsAlarmConfig();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

	/**
	 * 
	 * @return
	 */
    public CmsAlarmConfigT getCmsAlarmConfig() {
    	CmsAlarmConfigT ret = null;
    	
		try {
			ret = m_binding.getCmsAlarmConfig();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

    /**
     * 
     * @param alarmConfig
     * @return
     */
    public short updateHfcAlarmConfig(HfcAlarmConfigT alarmConfig) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateHfcAlarmConfig(alarmConfig);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
    }

	/**
	 * 
	 */
	public short updateCmtsAlarmConfig(CmtsAlarmConfigT alarmConfig) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCmtsAlarmConfig(alarmConfig);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateCmtsStatusThreshold(CmtsStatusThresholdT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCmtsStatusThreshold(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateHfcStatusThreshold(HfcStatusThresholdT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateHfcStatusThreshold(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateMtaAlarmConfig(MtaAlarmConfigT alarmConfig) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateMtaAlarmConfig(alarmConfig);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateMtaStatusThreshold(MtaStatusThresholdT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateMtaStatusThreshold(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public UserT getUser(String loginName) {
		UserT ret = null;
    	
		try {
			ret = m_binding.getUser(loginName);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short addUser(UserT user) {
		short ret = -1;
    	
		try {
			ret = m_binding.addUser(user);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateUser(UserT user) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateUser(user);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
 	}

	/**
	 * 
	 */
	public short updateUserPassword(String loginName, String newPassword) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateUserPassword(loginName, newPassword);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public UserT[] getUsers() {
		UserT[] ret = null;
    	
		try {
			// System.out.println("Calling service...");
			ret = m_binding.getUsers();
			// System.out.println("Finished calling service...");
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public ApplicationDomainT[] getApplicationDomains() {
		ApplicationDomainT[] ret = null;
    	
		try {
			ret = m_binding.getApplicationDomains();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public UserAccessT[] getAccessRights() {
		UserAccessT[] ret = null;
    	
		try {
			ret = m_binding.getAccessRights();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public RoleT[] getRoles() {
		RoleT[] ret = null;
    	
		try {
			ret = m_binding.getRoles();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public RoleT getRole(String roleName) {
		RoleT ret = null;
    	
		try {
			ret = m_binding.getRole(roleName);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short addRole(RoleT role) {
		short ret = -1;
    	
		try {
			ret = m_binding.addRole(role);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateRole(RoleT role) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateRole(role);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateChannelStatusThreshold(
			ChannelStatusThresholdT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateChannelStatusThreshold(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateCms(CmsT cms) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCms(cms);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateCmsAlarmConfig(CmsAlarmConfigT alarmConfig) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCmsAlarmConfig(alarmConfig);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public short updateCmsStatusThreshold(CmsStatusThresholdT pollintInterval) {
		short ret = -1;
    	
		try {
			ret = m_binding.updateCmsStatusThreshold(pollintInterval);
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public MtaStatusThresholdT getMtaStatusThreshold() {
		MtaStatusThresholdT ret = null;
    	
		try {
			ret = m_binding.getMtaStatusThreshold();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public HfcStatusThresholdT getHfcStatusThreshold() {
		HfcStatusThresholdT ret = null;
    	
		try {
			ret = m_binding.getHfcStatusThreshold();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public CmtsStatusThresholdT getCmtsStatusThreshold() {
		CmtsStatusThresholdT ret = null;
    	
		try {
			ret = m_binding.getCmtsStatusThreshold();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public CmsStatusThresholdT getCmsStatusThreshold() {
		CmsStatusThresholdT ret = null;
    	
		try {
			ret = m_binding.getCmsStatusThreshold();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 */
	public ChannelStatusThresholdT getChannelStatusThreshold() {
		ChannelStatusThresholdT ret = null;
    	
		try {
			ret = m_binding.getChannelStatusThreshold();
		} catch (Exception e) {
			log.error(null, e);
		}
		
    	return (ret);    	
	}

	/**
	 * 
	 * @param bld
	 * @return
	 */
    public short deleteBlade(BladeT bld) {
		short ret = 0;

		try {
			ret = m_binding.deleteBlade(bld);
		} catch (Exception e) {
			log.error(null, e);
		}

		return ret;
    }

    /**
     * 
     * @return
     */
    public CmPerformanceConfigT getCmPerfConfig() { 
    	CmPerformanceConfigT ret = null;
    	
		try {
			ret = m_binding.getCmPerfConfig();
		} catch (Exception e) {
			log.error(null, e);
		}

    	return (ret);
    }

    /**
     * 
     * @param cmPerf
     * @return
     */
    public short updateCmPerfConfig(CmPerformanceConfigT cmPerf) { 
    	short ret = 0;
    	
		try {
			ret = m_binding.updateCmPerfConfig(cmPerf);
		} catch (Exception e) {
			log.error(null, e);
		}

    	return (ret);
    }

    /**
     * 
     * @return
     */
    public short downloadConfigFromParent() { 
    	short ret = 0;
    	
		try {
			ret = m_binding.downloadConfigFromParent();
		} catch (Exception e) {
			log.error(null, e);
		}

    	return (ret);
    }
    
    /**
     * 
     * @return
     */
    public ConfigDownloadT getConfig() { 
    	ConfigDownloadT ret = null;
    	
		try {
			ret = m_binding.getConfig();
		} catch (Exception e) {
			log.error(null, e);
		}

    	return (ret);
    }
    

}
