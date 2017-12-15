/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.LocalSystem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.*;
import com.palmyrasys.www.webservices.CableAssurance.Topology.*;
import com.palmyrasys.www.webservices.CableAssurance.Alarm.*;
import com.palmyrasys.www.webservices.CableAssurance.Performance.*;
import com.palmyrasys.www.webservices.CableAssurance.Cms.BladeCmsImpl;
import com.palmyrasys.www.webservices.CableAssurance.Cms.CmsEP;
import com.palmyrasys.www.webservices.CableAssurance.Cms.MarketCmsImpl;
import com.palmyrasys.www.webservices.CableAssurance.CsrPortal.*;
import com.palmyrasys.www.webservices.CableAssurance.Cte.BladeCteImpl;
import com.palmyrasys.www.webservices.CableAssurance.Cte.CteEP;
import com.palmyrasys.www.webservices.CableAssurance.Cte.MarketCteImpl;
import com.palmyrasys.www.webservices.CableAssurance.Administration.*;
import com.palmyrasys.www.webservices.CableAssurance.Reports.*;

/**
 * @author Prem
 * 
 */
public class LocalSystemSingleton {

	private static Log log = LogFactory.getLog(LocalSystemSingleton.class);

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
		dbLs = null;
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
	public boolean isMarket() {
		boolean ret;
		
		if (m_localSystemType == LocalSystem.LSTYPE_MARKET) {
			ret = true;
		} else {
			ret = false;
		}
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRegion() {
		boolean ret;
		
		if (m_localSystemType == LocalSystem.LSTYPE_REGION) {
			ret = true;
		} else {
			ret = false;
		}
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEnterprise() {
		boolean ret;
		
		if (m_localSystemType == LocalSystem.LSTYPE_ENTERPRISE) {
			ret = true;
		} else {
			ret = false;
		}
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBlade() {
		boolean ret;
		
		if (m_localSystemType == LocalSystem.LSTYPE_BLADE) {
			ret = true;
		} else {
			ret = false;
		}
		return (ret);
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
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeTopologyImpl();
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
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeAlarmImpl();
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
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladePerformanceImpl();
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

	/**
	 * Returns the appropriate CSR Portal handler
	 * 
	 * @return
	 */
	public CsrPortalEP getCsrPortalHandler() {
		CsrPortalEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_BLADE:
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketCsrPortalImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * Returns Admin handler
	 * 
	 * @return
	 */
	public AdministrationEP getAdminHandler() {
		AdministrationEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeAdminImpl();
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketAdminImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * Returns Admin handler
	 * 
	 * @return
	 */
	public ReportsEP getReportsHandler() {
		ReportsEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeReportImpl();
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketReportImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * Returns Admin handler
	 * 
	 * @return
	 */
	public CteEP getCteHandler() {
		CteEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeCteImpl();
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketCteImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

	/**
	 * Returns Admin handler
	 * 
	 * @return
	 */
	public CmsEP getCmsHandler() {
		CmsEP ret = null;

		switch (m_localSystemType) {
		case LocalSystem.LSTYPE_BLADE:
			ret = new BladeCmsImpl();
			break;
		case LocalSystem.LSTYPE_ENTERPRISE:
			break;
		case LocalSystem.LSTYPE_MARKET:
			ret = new MarketCmsImpl();
			break;
		case LocalSystem.LSTYPE_REGION:
			break;
		default:
			break;
		}

		return (ret);
	}

}
