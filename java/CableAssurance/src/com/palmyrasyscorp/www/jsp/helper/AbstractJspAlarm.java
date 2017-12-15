/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.text.DateFormat;
import java.util.Date;

import com.palmyrasys.www.webservices.CableAssurance.AbstractAlarmT;

/**
 * @author Prem
 *
 */
public abstract class AbstractJspAlarm extends AbstractInternalToJspBase {

	private AbstractAlarmT m_absAlarm;
	protected AbstractAlarmT getAbstractAlarm() {
		return m_absAlarm;
	}
	
	private DateFormat m_df;
	protected DateFormat getDateFormat() {
		return (m_df);
	}

	private String m_netObjTargetWindow = UrlHelper.NET_OBJ_DATA;
	public void setNetObjectTargetWindow(String in) {
		m_netObjTargetWindow = in;
	}
	
	/**
	 * 
	 * @param ca
	 * @param df
	 */
	protected AbstractJspAlarm(AbstractAlarmT aa, DateFormat df) {
		m_absAlarm = aa;
		m_df = df;
	}

	private String m_alarmTypeStr;
	public String getAlarmType() {
		return m_alarmTypeStr;
	}
	private void generateAlarmType() {
		m_alarmTypeStr = m_absAlarm.getAlarmType() + "-"
		+ m_absAlarm.getAlarmSubType();
	}
	
	private String m_alarmTime;
	public String getAlarmTime() {
		return m_alarmTime;
	}
	private void generateAlarmTime() {
		Date dt = new Date(m_absAlarm.getAlarmTime().longValue()*1000);
		m_alarmTime = m_df.format(dt);
	}
	
	private String m_resourceNameAnchor;
	public String getResourceNameAnchor() {
		return m_resourceNameAnchor;
	}
	
//	private void generateResourceNameAndUrl() {
//		m_resourceName = m_absAlarm.getResourceName();
//	}
//	private String m_resourceNameAnchor;
//	public String getResourceNameAnchor() {
//		return m_resourceNameAnchor;
//	}
	
	private String m_alarmState;
	public String getAlarmState() {
		return m_alarmState;
	}
	private void generateAlarmState() {
		m_alarmState = m_absAlarm.getAlarmState();
	}
	
	private String m_csrPortalAnchor;
	public String getCsrPortalAnchor() {
		return m_csrPortalAnchor;
	}
	
	private String m_alarmDetailsUrl;
	public String getAlarmDetailsUrl() {
		return m_alarmDetailsUrl;
	}
	
	private String m_alarmHistoryUrl;
	public String getAlarmHistoryUrl() {
		return m_alarmHistoryUrl;
	}
	
//	private String m_hfc;
//	public String getHfc() {
//		return m_hfc;
//	}
//	
//	private String m_upstream;
//	public String getUpstream() {
//		return m_upstream;
//	}
//	
//	private String m_cmts;
//	public String getCmts() {
//		return m_cmts;
//	}
//	
//	private String m_cms;
//	public String getCms() {
//		return m_cms;
//	}
	
	public boolean generateHtml() {
		boolean ret = true;
		
		generateAlarmType();
		generateAlarmTime();
		generateAlarmState();
		
		/*
		 * Resource Name + Underlying URL to goto
		 */
		String jspHandler;
		StringBuffer targetWindow = new StringBuffer();
		m_csrPortalAnchor = UrlHelper.UNKNOWN_FILLER;		
		if (m_alarmTypeStr.startsWith(UrlHelper.MTA)) {
			jspHandler = "../app/topo_drilldwn_emta_plus_plots.jsp";
			targetWindow.append(m_netObjTargetWindow);
	
			/*
			 * CSR Portal URL
			 */
			StringBuffer url = new StringBuffer("../app/csr_portal_result_frm.jsp");
			UrlHelper.AppendTopoHierarchyIDs(url, m_absAlarm.getTopologyKey());
			// url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
			// .append(m_absAlarm.getResourceId());
			url.append("&").append(UrlHelper.MAC).append("=")
			.append(m_absAlarm.getResourceName());
			url.append("&").append(UrlHelper.TYPE).append("=")
			.append(UrlHelper.MTA);
			
			StringBuffer csrPortalUrl = new StringBuffer("<span><a href=\"");
			csrPortalUrl.append(url).append("\" target=\"").append(UrlHelper.CSR_MAIN_FRAME)
			.append("\">CSR</a>").append("</span>");
			m_csrPortalAnchor = csrPortalUrl.toString();

		} else if (m_alarmTypeStr.startsWith(UrlHelper.HFC)) {
			jspHandler = "../app/topo_drilldwn_hfc.jsp";
			targetWindow.append(m_netObjTargetWindow);
		} else if (m_alarmTypeStr.startsWith(UrlHelper.CMTS)) {
			jspHandler = "../app/topo_drilldwn_cmts.jsp";
			targetWindow.append(m_netObjTargetWindow);
		} else if (m_alarmTypeStr.startsWith(UrlHelper.CMS)) {
			jspHandler = "../app/topo_drilldwn_cms.jsp";
			targetWindow.append(m_netObjTargetWindow);
		} else {
			ret = false;
			return (ret);
		}		
		StringBuffer url = new StringBuffer(jspHandler);
		UrlHelper.AppendTopoHierarchyIDs(url, m_absAlarm.getTopologyKey());
		url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(m_absAlarm.getResourceId());
		StringBuffer anchor = new StringBuffer("<span><a href=\"");
		anchor.append(url.toString()).append("\" target=\"").append(targetWindow)
		.append("\">").append(m_absAlarm.getResourceName()).append("</a></span>");
		m_resourceNameAnchor = anchor.toString();
		// System.out.println(m_resourceNameAnchor);
		
		/*
		 * Alarm Details URL
		 */
		url = new StringBuffer("../app/alarm_details.jsp");
		UrlHelper.AppendTopoHierarchyIDs(url, m_absAlarm.getTopologyKey());
		url.append("&").append(UrlHelper.ROOT_ALARM_ID).append("=")
		.append(m_absAlarm.getAlarmId());
		m_alarmDetailsUrl = url.toString();

		/*
		 * Alarm History URL
		 */
		url = new StringBuffer("../app/alarm_history.jsp");
		UrlHelper.AppendTopoHierarchyIDs(url, m_absAlarm.getTopologyKey());
		url.append("&").append(UrlHelper.ROOT_ALARM_ID).append("=")
		.append(m_absAlarm.getAlarmId());
		m_alarmHistoryUrl = url.toString();

		// System.out.println(url);
		
		return (ret);
	}
}
