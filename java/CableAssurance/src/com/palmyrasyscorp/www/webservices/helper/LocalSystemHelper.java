/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.*;
import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasyscorp.db.tables.LocalSystem;

/**
 * @author Prem
 * 
 */
public class LocalSystemHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(LocalSystemHelper.class);

	private LocalSystemSOAPBindingStub m_binding = null;

	public LocalSystemHelper() {
		try {
			m_binding = (LocalSystemSOAPBindingStub) new LocalSystemServiceLocator()
					.getLocalSystemEP();
		} catch (javax.xml.rpc.ServiceException jre) {
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
	 * @return
	 */
	public LocalSystemT getLocalSystemType() {
		// Test operation
		LocalSystemT value = null;
		try {
			value = m_binding.getLocalSystem();
		} catch (Exception e) {
			log.error(null, e);
		}

		return (value);
	}

	/**
	 * 
	 * @param dbLs
	 * @param svcLs
	 */
	public static void SetSystemType(LocalSystem dbLs, LocalSystemT svcLs) {
		String s = svcLs.getSystemType().getValue();
		if (s.equals(SystemTypeT._EnterpriseServer)) {
			dbLs.setSystemType(LocalSystem.LSTYPE_ENTERPRISE);
		} else if (s.equals(SystemTypeT._RegionServer)) {
			dbLs.setSystemType(LocalSystem.LSTYPE_REGION);
		} else if (s.equals(SystemTypeT._MarketServer)) {
			dbLs.setSystemType(LocalSystem.LSTYPE_MARKET);
		} else if (s.equals(SystemTypeT._BladeServer)) {
			dbLs.setSystemType(LocalSystem.LSTYPE_BLADE);
		} else {
			// dbLs.setSystenType(arg0)
		}
	}

	/**
	 * 
	 * @param sysType
	 * @return
	 */
	public static SystemTypeT getSystemType(String sysType) {
		SystemTypeT ret = null;

		if (sysType.equals(SystemTypeT._EnterpriseServer)) {
			ret = SystemTypeT.EnterpriseServer;
		} else if (sysType.equals(SystemTypeT._RegionServer)) {
			ret = SystemTypeT.RegionServer;
		} else if (sysType.equals(SystemTypeT._MarketServer)) {
			ret = SystemTypeT.MarketServer;
		} else if (sysType.equals(SystemTypeT._BladeServer)) {
			ret = SystemTypeT.BladeServer;
		}

		return (ret);
	}

	/**
	 * 
	 * @param sysType
	 * @return
	 */
	public static SystemTypeT getSystemType(int sysType) {
		SystemTypeT ret = null;
		
		switch (sysType) {
		case LocalSystem.LSTYPE_ENTERPRISE:
			ret = SystemTypeT.EnterpriseServer;
			break;
		case LocalSystem.LSTYPE_REGION:
			ret = SystemTypeT.RegionServer;
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = SystemTypeT.MarketServer;
			break;
		case LocalSystem.LSTYPE_BLADE:
			ret = SystemTypeT.BladeServer;
			break;
		default:
			break;
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param dbSystemType
	 * @return
	 */
	public static LocalSystemT getLocalSystem(LocalSystem dbLs) {
		LocalSystemT ret = null;

		switch (dbLs.getSystemType().intValue()) {
		case LocalSystem.LSTYPE_ENTERPRISE:
			ret = new LocalSystemT(SystemTypeT.EnterpriseServer, dbLs
					.getSystemName(), dbLs.getParentHost());
			break;
		case LocalSystem.LSTYPE_REGION:
			ret = new LocalSystemT(SystemTypeT.RegionServer, dbLs
					.getSystemName(), dbLs.getParentHost());
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new LocalSystemT(SystemTypeT.MarketServer, dbLs
					.getSystemName(), dbLs.getParentHost());
			break;
		case LocalSystem.LSTYPE_BLADE:
			ret = new LocalSystemT(SystemTypeT.BladeServer, dbLs
					.getSystemName(), dbLs.getParentHost());
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	public List getSystemNames() {
		LinkedList ret = new LinkedList();

		ret.add(new String(SystemTypeT.EnterpriseServer.getValue()));
		ret.add(new String(SystemTypeT.RegionServer.getValue()));
		ret.add(new String(SystemTypeT.MarketServer.getValue()));
		ret.add(new String(SystemTypeT.BladeServer.getValue()));

		return (ret);
	}
}
