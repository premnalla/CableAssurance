/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.LocalSystem;

import com.palmyrasyscorp.db.tables.*;
import com.palmyrasys.www.webservices.CableAssurance.Topology.*;
import com.palmyrasys.www.webservices.CableAssurance.Alarm.*;
import com.palmyrasys.www.webservices.CableAssurance.Performance.*;

/**
 * @author Prem
 * 
 */
public class LocalSystemSingleton {

	/**
	 * 
	 */
	private static LocalSystemSingleton m_instance = null;

	/**
	 * 
	 */
	private short m_localSystemType;

	/**
	 * 
	 * 
	 */
	protected LocalSystemSingleton() {
		LocalSystem dbLs = new LocalSystem();
		m_localSystemType = dbLs.getSystemType().shortValue();
	}

	/**
	 * 
	 * @return
	 */
	public static LocalSystemSingleton getInstance() {
		if (m_instance == null) {
			m_instance = new LocalSystemSingleton();
		}
		return (m_instance);
	}

	/**
	 * 
	 * @return
	 */
	public short getLocalSystemType() {
		return (m_localSystemType);
	}

	/**
	 * Returns the appropriate Topology handler
	 * @return
	 */
	public TopologyEP getHandler() {
		TopologyEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_DOMAIN:
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketTopologyImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}
	
	/**
	 * Returns the appropriate Alarm handler
	 * 
	 * @return
	 */
	public AlarmEP getAlarmHandler() {
		AlarmEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_DOMAIN:
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketAlarmImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * Returns the appropriate Alarm handler
	 * 
	 * @return
	 */
	public PerformanceEP getPerformanceHandler() {
		PerformanceEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_DOMAIN:
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketPerformanceImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

}
