<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.chart.CmChartCollection"%>
<%@ page import="com.palmyrasyscorp.db.tables.IntegerHolder"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu.js" type="text/javascript"></script>
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
	CableModemT cm = th.getCableModem(tK, rId);
	CmtsT cmts = th.getCmts(tK, cm.getCmtsResId());
	HfcT hfc = th.getHfc(tK, cm.getHfcResId());
	ChannelT chnl = th.getChannel(tK, cm.getUpChannelResId());
	CmChartCollection ccc = new CmChartCollection(tK, rId);
	LinkedList filenameList = ccc.generateCharts(session, new PrintWriter(out));

	StringBuffer reportsUrl = new StringBuffer(
	"../app/topo_drilldwn_cm_reports.jsp");
	UrlHelper.AppendTopoHierarchyIDs(reportsUrl, tK);
	reportsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">

<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>

<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_REPORTS) %> </a></p>

</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CABLE_MODEM) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<div class="left">
<table>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_MAC) %> :</td>
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

<div class="topoDrillDownOffset" >
<table>
<%
	IntegerHolder iH = new IntegerHolder();
	iH.value = -1;
	int numItemsPerRow = 2;
	for (int i=0; i<filenameList.size(); i++) {
		String filename = (String) filenameList.get(i);
		String graphURL = request.getContextPath() + "/caservlet/DisplayChart?filename=" + filename;
		if (DisplayAlgorithms.IsTableBeginTr(i, numItemsPerRow)) {
%>
<tr>
<%
		}
%>
<td>
<p>
	<img src="<%= graphURL %>" class="plots" border=0 usemap="#<%= filename %>" />
</p>
</td>
<%
		if (DisplayAlgorithms.IsTableEndTr(i, numItemsPerRow, filenameList.size(), iH)) {
%>
</tr>
<%
		}

	} // end for each filename in list ...
	filenameList.clear();
	filenameList = null;
%>
</table>
</div>

</body>
</html>
