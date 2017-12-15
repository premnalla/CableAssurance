<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_mta.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);
	String resId = request.getParameter(UrlHelper.RESOURCE_ID);
	
	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?")
		.append(request.getQueryString());
	
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	BigInteger rId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	EmtaT emta = th.getEmta(tK, rId);
	CableModemT cm = th.getCableModem(tK, emta.getCmResId());
	CmtsT cmts = th.getCmts(tK, cm.getCmtsResId());
	HfcT hfc = th.getHfc(tK, cm.getHfcResId());
	ChannelT chnl = th.getChannel(tK, cm.getUpChannelResId());
	
	StringBuffer clearAlarmUrl = 
		new StringBuffer("../app/topo_drilldwn_emta_alarm_clear.jsp?")
	.append(request.getQueryString());
	
	StringBuffer alarmsUrl = new StringBuffer(
	"../app/topo_drilldwn_emta_alarms.jsp?").append(request.getQueryString());

	StringBuffer reportsUrl = new StringBuffer(
	"../app/topo_drilldwn_mta_reports.jsp?").append(request.getQueryString());

%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>
<p id="pAlarms" class="menuItem"><a id="aAlarms"
	href="<%=alarmsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_ALARMS) %> </a></p>
<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_REPORTS) %> </a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_MTA) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<div class="left">
<table>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_MAC) %> :</td>
	<td class="td_val"><em><%=emta.getMacAddress()%></em></td>
</tr>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_MAC) %> :</td>
	<td class="td_val"><em><%=cm.getMacAddress()%></em></td>
</tr>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_CMTS) %> :</td>
	<td class="td_val"><em><%=cmts.getCmtsName()%></em></td>
</tr>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_HFC) %> :</td>
	<td class="td_val"><em><%=hfc.getHfcName()%></em></td>
</tr>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_CHANNEL) %> :</td>
	<td class="td_val"><em><%=chnl.getChannelName()%></em></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_ALARMS) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<%
	AlarmHelper aH = new AlarmHelper();
	CurrentAlarmsRespT resp = null;
	CurrentAlarmT[] alms = null;
	
	resp = aH.getCurrentAlarmsForResource(tK, rId);
		
	if (resp != null) {
		alms = resp.getAlarms();
	}	
%>
<form name="alarmClearForm" action="<%=clearAlarmUrl.toString() %>" method="post" >
<table>
<tr>
<td><input type="submit" name="<%=UrlHelper.SELECT_ALL_NM %>" value="<%=rb.getString(ResourceKeys.K_SELECT_ALL) %>"/></td>
<td><input type="submit" name="<%=UrlHelper.UNSELECT_ALL_NM %>" value="<%=rb.getString(ResourceKeys.K_UNSELECT_ALL) %>"/></td>
<td><input type="submit" name="<%=UrlHelper.CLEAR_ALARM_NM %>" value="<%=rb.getString(ResourceKeys.K_CLEAR_ALARMS) %>"/></td>
</tr>
</table>
<table border="1">
<tr class="alarms_tr_th">
	<th><%=rb.getString(ResourceKeys.K_SELECT) %></th>
	<th><%=rb.getString(ResourceKeys.K_ALARM) %></th>
	<th><%=rb.getString(ResourceKeys.K_OBJECT) %></th>
	<th><%=rb.getString(ResourceKeys.K_TIME) %></th>
	<th><%=rb.getString(ResourceKeys.K_STATE) %></th>
	<th><%=rb.getString(ResourceKeys.K_CSR_PORTAL) %></th>
	<th><%=rb.getString(ResourceKeys.K_ALARM_DETAILS) %></th>
</tr>
<%
	if (alms != null) {
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
		for (int i = 0; i < alms.length; i++) {
			CurrentAlarmT alm = alms[i];
			CurrentJspAlarm cja = new CurrentJspAlarm(alm, df);
			cja.generateHtml();
			StringBuffer checkBoxName = new StringBuffer(UrlHelper.ALARM_PREFIX);
			UrlHelper.AppendTopoKeysToName(checkBoxName, alm.getTopologyHierarchyKey());
			checkBoxName.append(alm.getAbstractAlarm().getAlarmId());
%>
			<tr class="alarms">
				<td><input type="checkbox" name="<%=checkBoxName.toString() %>" checked="checked" /></td>
				<td><%= cja.getAlarmType() %></td>
				<td><%= cja.getResourceNameAnchor() %></td>
				<td><%= cja.getAlarmTime() %></td>
				<td><%= cja.getAlarmState() %></td>
				<td><%= cja.getCsrPortalAnchor() %></td>
				<td><span><a href="<%= cja.getAlarmDetailsUrl() %>">D</a></span></td>
			</tr>
<%
		} // end foreach current alarm in array
		
		// if (alms != null)
	}
%>
</table>
</form>
</div>
</div>

</body>
</html>
