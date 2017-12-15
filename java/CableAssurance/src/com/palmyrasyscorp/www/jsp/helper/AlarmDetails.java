/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.AlarmHistoryT;
import com.palmyrasys.www.webservices.CableAssurance.CACustomerT;
import com.palmyrasys.www.webservices.CableAssurance.CableModemT;
import com.palmyrasys.www.webservices.CableAssurance.ChannelT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT;
import com.palmyrasys.www.webservices.CableAssurance.EmtaT;
import com.palmyrasys.www.webservices.CableAssurance.HfcT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.www.resourcebundle.AbstractResourceBundle;
import com.palmyrasyscorp.www.resourcebundle.ResourceKeys;
import com.palmyrasyscorp.www.webservices.helper.AlarmHelper;
import com.palmyrasyscorp.www.webservices.helper.TopologyHelper;

/**
 * @author Prem
 * 
 */
public class AlarmDetails {

	private static Log log = LogFactory.getLog(AlarmDetails.class);

	private TopoHierarchyKeyT m_topoKey;

	private BigInteger m_rootAlarmId;

	private AbstractResourceBundle m_resBundle;

	/**
	 * 
	 * @param topoKey
	 * @param resId
	 */
	public AlarmDetails(TopoHierarchyKeyT topoKey, BigInteger rootAlarmId) {
		m_topoKey = topoKey;
		m_rootAlarmId = rootAlarmId;
	}

	/**
	 * 
	 * @param rb
	 */
	public void setResourceBundle(AbstractResourceBundle rb) {
		m_resBundle = rb;
	}

	/**
	 * 
	 * @return
	 */
	public String generateHtml() {
		StringBuffer ret = new StringBuffer();

		try {
			AlarmHelper ah = new AlarmHelper();
			CurrentAlarmT ca = ah.getCurrentAlarm(m_topoKey, m_rootAlarmId);
			TopologyHelper th = new TopologyHelper();
			ResourceT res = th.getResource(m_topoKey, ca.getAbstractAlarm()
					.getResourceId());
			if (res.getResourceType().toString().compareTo(
					ResourceTypeT.CMS.toString()) == 0) {

			} else if (res.getResourceType().toString().compareTo(
					ResourceTypeT.CMTS.toString()) == 0) {

			} else if (res.getResourceType().toString().compareTo(
					ResourceTypeT.HFC.toString()) == 0) {
				generateHfcHtml(ca, ret);
			} else if (res.getResourceType().toString().compareTo(
					ResourceTypeT.MTA.toString()) == 0) {
				generateMtaHtml(ca, ret);
			} else {

			}

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}

		return (ret.toString());
	}

	/**
	 * 
	 * @param sb
	 */
	private void generateHfcHtml(CurrentAlarmT ca, StringBuffer sb) {
		TopologyHelper th = new TopologyHelper();
		HfcT hfc = th.getHfc(m_topoKey, ca.getAbstractAlarm().getResourceId());
		CmtsT cmts = th.getCmts(m_topoKey, hfc.getCmtsResId());

		sb.append("<table>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_HFC));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(hfc.getHfcName());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_CMTS));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(cmts.getCmtsName());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		
		AlarmHelper ah = new AlarmHelper();
		AlarmHistoryT[] hists = ah.getAlarmHistory(m_topoKey, m_rootAlarmId);
		if (hists != null) {
			sb.append("</br></br>");
			sb.append("<table>");
	
			sb.append("<tr class=\"alarms_tr_th\">");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_TIME)).append("</th>");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_STATE)).append("</th>");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_INFO)).append("</th>");
			sb.append("</tr>");
			
			for (int i=0; i<hists.length; i++) {
				sb.append("<tr>");
				sb.append("<td>").append(hists[i].getTime()).append("</td>");
				sb.append("<td>").append(hists[i].getState()).append("</td>");
				if (hists[i].getExtraInfo() != null) {
					sb.append("<td>").append(hists[i].getExtraInfo()).append("</td>");
				} else {
					sb.append("<td>").append("</td>");					
				}
				sb.append("</tr>");
			}
	
			sb.append("</table>");
		}
		
	}

	/**
	 * 
	 * @param sb
	 */
	private void generateMtaHtml(CurrentAlarmT ca, StringBuffer sb) {
		TopologyHelper th = new TopologyHelper();
		CACustomerT cust = th.getCustomerForResource(m_topoKey, ca
				.getAbstractAlarm().getResourceId());
		EmtaT mta = th
				.getEmta(m_topoKey, ca.getAbstractAlarm().getResourceId());
		CableModemT cm = th.getCableModem(m_topoKey, mta.getCmResId());
		CmtsT cmts = th.getCmts(m_topoKey, cm.getCmtsResId());
		ChannelT chnl = th.getChannel(m_topoKey, cm.getUpChannelResId());
		HfcT hfc = th.getHfc(m_topoKey, cm.getHfcResId());

		sb.append("<table>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_MTA)).append(" ")
				.append(m_resBundle.getString(ResourceKeys.K_MAC));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(mta.getMacAddress());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_CM)).append(" ").append(
				m_resBundle.getString(ResourceKeys.K_MAC));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(cm.getMacAddress());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_UPSTREAM));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(chnl.getChannelName());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_HFC));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(hfc.getHfcName());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td class=\"name_lg\">");
		sb.append(m_resBundle.getString(ResourceKeys.K_CMTS));
		sb.append("</td>");
		sb.append("<td class=\"value_lg\">");
		sb.append(cmts.getCmtsName());
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");
		
		AlarmHelper ah = new AlarmHelper();
		AlarmHistoryT[] hists = ah.getAlarmHistory(m_topoKey, m_rootAlarmId);
		if (hists != null) {
			sb.append("</br></br>");
			sb.append("<table>");
	
			sb.append("<tr class=\"alarms_tr_th\">");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_TIME)).append("</th>");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_STATE)).append("</th>");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_INFO)).append("</th>");
			sb.append("</tr>");
			
			for (int i=0; i<hists.length; i++) {
				sb.append("<tr>");
				sb.append("<td>").append(hists[i].getTime()).append("</td>");
				sb.append("<td>").append(hists[i].getState()).append("</td>");
				if (hists[i].getExtraInfo() != null) {
					sb.append("<td>").append(hists[i].getExtraInfo()).append("</td>");
				} else {
					sb.append("<td>").append("</td>");					
				}
				sb.append("</tr>");
			}
	
			sb.append("</table>");
		}
		
		if (cust != null) {
			sb.append("</br></br>");
			sb.append("<table>");
	
			sb.append("<tr class=\"alarms_tr_th\">");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_ACCOUNT_NUM)).append("</th>");
			sb.append("<th>").append(m_resBundle.getString(ResourceKeys.K_CUST_FULL_ADDR)).append("</th>");
			sb.append("</tr>");			
			
			sb.append("<tr>");
			sb.append("<td>").append(cust.getAccountNumber()).append("</td>");
			sb.append("<td>").append(cust.getAddress()).append("</td>");
			sb.append("</tr>");

			sb.append("</table>");
		}
	}

}
