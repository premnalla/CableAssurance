/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

// import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// import com.palmyrasyscorp.www.webservices.helper.CsrPortalHelper;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;
// import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
// import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.MappedEuDevicesT;
//import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
//import com.palmyrasys.www.webservices.CableAssurance.HfcT;
//import com.palmyrasys.www.webservices.CableAssurance.ChannelT;
//import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
//import com.palmyrasys.www.webservices.CableAssurance.Common.TopoKeyHelper;
import com.palmyrasyscorp.www.servlet.ServletConstants;

/**
 * @author Prem
 *
 */
public class CsrResultMediator {

	private HttpServletRequest m_request;
	
	/**
	 * 
	 *
	 */
	public CsrResultMediator(HttpServletRequest req) {
		super();
		m_request = req;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean process() {
		boolean ret = true;
		
		String cmMac = null;
		String mtaMac = null;
		
		String devType = m_request.getParameter(UrlHelper.TYPE);
		String macAddress = m_request.getParameter(UrlHelper.MAC);

		// boolean found = false;
		HttpSession session = m_request.getSession();
		
		if (macAddress != null && macAddress.length() != 0) {
			// CsrPortalHelper h = new CsrPortalHelper();
			TopologyHelper th = new TopologyHelper();
			if (devType != null && devType.equals(UrlHelper.MTA)) {
				mtaMac = macAddress;				
				// found = true;
				MappedEuDevicesT devs = th.getDevicesForCsrPortal(cmMac, mtaMac);				
				
//				/*
//				 * BEGIN TEMP
//				 */
//				EmtaT e = new EmtaT();
//				e.setMacAddress("000102030405");
//				/*
//				 * END TEMP
//				 */
				
				// addMtaAndChildren(e);
				if (devs != null) {
					synchronized (session) {
						if (devs.getMta() != null) {
							session.setAttribute(ServletConstants.HTTP_MTA, devs.getMta());
						}
						if (devs.getCm() != null) { 
							session.setAttribute(ServletConstants.HTTP_CM, devs.getCm());
						}
					}
				} else {
					System.out.println("Devs==null");
				}
			} else if (devType != null && devType.equals(UrlHelper.CM)) {
				cmMac = macAddress;
				// found = true;
				MappedEuDevicesT devs = th.getDevicesForCsrPortal(cmMac, mtaMac);
				synchronized (session) {
					if (devs != null && devs.getCm() != null) { 
						session.setAttribute(ServletConstants.HTTP_CM, devs.getCm());
					}
				}				
				// CableModemT cm = devs.getCm();				
				// addCmAndChildren(cm);
			}
		}
		
//		if (!found) {
//			String regionId = m_request.getParameter(UrlHelper.REGION_ID);
//			String marketId = m_request.getParameter(UrlHelper.MARKET_ID);
//			String bladeId = m_request.getParameter(UrlHelper.BLADE_ID);
//			String resId = m_request.getParameter(UrlHelper.RESOURCE_ID);
//			TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
//			TopologyHelper th = new TopologyHelper();
//			if (devType != null && devType.equals(UrlHelper.MTA)) {
//				found = true;
//				EmtaT e = null;
//				try {
//					e = th.getEmta(tK, new BigInteger(resId));
//				} catch (Exception ex) {
//				}
//				addMtaAndChildren(e);
//			} else if (devType != null && devType.equals(UrlHelper.CM)) {
//				found = true;
//				CableModemT cm = null;
//				try {
//					cm = th.getCableModem(tK, new BigInteger(resId));
//				} catch (Exception ex) {
//				}
//				addCmAndChildren(cm);
//			}
//		}
				
		return (ret);
	}
	
	/**
	 * 
	 * @param e
	 */
//	private void addMtaAndChildren(EmtaT e) {
//		if (e != null) {
//			// m_request.setAttribute(ServletConstants.HTTP_MTA, e);
//			m_request.getSession().setAttribute(ServletConstants.HTTP_MTA, e);
//			System.out.println("Added MTA to request: " + 
//					m_request.getAttribute(ServletConstants.HTTP_MTA));
////			TopologyHelper th = new TopologyHelper();
////			CableModemT cm = th.getCableModem(e.getTopologyKey(), e.getCmResId());
////			addCmAndChildren(cm);
//		}		
//	}
	
	/**
	 * 
	 * @param cm
	 */
//	private void addCmAndChildren(CableModemT cm) {
//		if (cm != null) {
//			m_request.setAttribute(ServletConstants.HTTP_CM, cm);
//			
//			/*
//			 * Do we need to get and set the following???
//			 */
////			TopologyHelper th = new TopologyHelper();
////			CmtsT cmts = th.getCmts(cm.getTopologyKey(), cm.getCmtsResId());
////			if (cmts != null) {
////				m_request.setAttribute(ServletConstants.HTTP_CMTS, cmts);							
////			}
////			
////			HfcT hfc = th.getHfc(cm.getTopologyKey(), cm.getHfcResId());
////			if (hfc != null) {
////				m_request.setAttribute(ServletConstants.HTTP_HFC, hfc);							
////			}
////			
////			ChannelT chnl = th.getChannel(cm.getTopologyKey(), cm.getUpChannelResId());
////			if (chnl != null) {
////				m_request.setAttribute(ServletConstants.HTTP_CHANNEL, chnl);							
////			}
//		}		
//	}
	
	
}
