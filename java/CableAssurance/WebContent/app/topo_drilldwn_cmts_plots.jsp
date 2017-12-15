<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.palmyrasyscorp.www.chart.CmtsCountsChartCollection"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="org.jfree.data.general.PieDataset"%>
<%@ page import="com.palmyrasyscorp.www.chart.CountsPieChart"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_cmts.js" type="text/javascript"></script>
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
	
	/*
	 * Get the CMTS
	 */
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,
	bladeId);
	BigInteger cmtsResId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	CmtsT svcCmts = th.getCmts(tK, cmtsResId);
	GenericCountsT cnts = svcCmts.getCounts();
	short totalCm = cnts.getTotalCm();
	short onlineCm = cnts.getOnlineCm();
	float percentOffline = 
		ConversionHelper.getPercentToOneDecDigit(totalCm, (short)totalCm-onlineCm);

	short totalMta = cnts.getTotalMta();
	short availMta = cnts.getAvailableMta();
	float percentUnavail = 
		ConversionHelper.getPercentToOneDecDigit(totalMta, (short)totalMta-availMta);

	StringBuffer chnlUrl = new StringBuffer(
	"../app/topo_drilldwn_channels.jsp");
	StringBuffer hfcUrl = new StringBuffer(
	"../app/topo_drilldwn_hfcs.jsp");
	StringBuffer plotsUrl = new StringBuffer(
	"../app/topo_drilldwn_cmts_plots.jsp");
	StringBuffer alarmsUrl = new StringBuffer(
		"../app/topo_drilldwn_alarms.jsp");
	
	UrlHelper.AppendTopoHierarchyIDs(chnlUrl, tK);
	chnlUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
	
	UrlHelper.AppendTopoHierarchyIDs(hfcUrl, tK);
	hfcUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(resId);
	
	UrlHelper.AppendTopoHierarchyIDs(plotsUrl, tK);
	plotsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
	
	UrlHelper.AppendTopoHierarchyIDs(alarmsUrl, tK);
	alarmsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
	
	StringBuffer reportsUrl = new StringBuffer(
	"../app/topo_drilldwn_cmts_reports.jsp");
	UrlHelper.AppendTopoHierarchyIDs(reportsUrl, tK);
	reportsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
	
	CmtsCountsChartCollection ccc = new CmtsCountsChartCollection(tK, cmtsResId);
	LinkedList filenameList = ccc.generateCharts(session, new PrintWriter(out));
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">

<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>
	
<p id="pChannels" class="menuItem"><a id="aChannels"
	href="<%=chnlUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_CHANNELS) %> </a></p>
	
<p id="pHfcs" class="menuItem"><a id="aHfcs"
	href="<%=hfcUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_HFCS) %> </a></p>
	
<p id="pPlots" class="menuItem"><a id="aPlots"
	href="<%=plotsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_PLOTS) %> </a></p>
	
<p id="pAlarms" class="menuItem"><a id="aAlarms"
	href="<%=alarmsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_ALARMS) %> </a></p>

<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_REPORTS) %> </a></p>

</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CMTS) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<div class="left">
<table>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_NAME) %> :</td>
	<td class="td_val"><em><%=svcCmts.getCmtsName()%></em></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<table border="1">
<tr class="counts_tr_th_r1">
<th colspan="3"><%=rb.getString(ResourceKeys.K_CABLE_MODEM) %></th>
<th colspan="3"><%=rb.getString(ResourceKeys.K_MTA) %></th>
</tr>
<tr class="counts_tr_th_r2">
<th><%=rb.getString(ResourceKeys.K_TOTAL) %></th>
<th><%=rb.getString(ResourceKeys.K_ONLINE) %></th>
<th><%=rb.getString(ResourceKeys.K_OFFLINE) %> (%)</th>
<th><%=rb.getString(ResourceKeys.K_TOTAL) %></th>
<th><%=rb.getString(ResourceKeys.K_AVAILABLE) %></th>
<th><%=rb.getString(ResourceKeys.K_UNAVAILABLE) %> (%)</th>
</tr>
<tr>
<td><%=totalCm %></td>
<td><%=onlineCm %></td>
<td><%=percentOffline %></td>
<td><%=totalMta %></td>
<td><%=availMta %></td>
<td><%=percentUnavail %></td>
<td></td>
</tr>
</table>
</div>

<div>
<p>
<br/>
</p>
</div>

<div class="menuOffset">
<div class="left">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_PLOTS) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<table>
<tr>
<%
	for (int i=0; i<filenameList.size(); i++) {
		String filename = (String) filenameList.get(i);
		String graphURL = request.getContextPath() + "/caservlet/DisplayChart?filename=" + filename;
%>
<td>
<p>
	<img src="<%= graphURL %>" class="plots" border=0 usemap="#<%= filename %>" />
</p>
</td>
<%
	} // end for each filename in list ...
	filenameList.clear();
	filenameList = null;
%>
</tr>
</table>
</div>

<div class="countsPieChart">
<%
	if (cnts != null) {
		/*
		 * CM counts
		 */
		CmCountsPieChartHelper cmPie = new CmCountsPieChartHelper(cnts);
		PieDataset cmDs = cmPie.getDataset();
		CountsPieChart cmChart = new CountsPieChart(cmPie.getChartName(), cmDs);
		String cmChartUrl = UrlHelper.getChartUrl(
				cmChart.generateChart(request.getSession(), new PrintWriter(out)));
		
		/*
		 * MTA counts
		 */
		MtaCountsPieChartHelper mtaPie = new MtaCountsPieChartHelper(cnts);
		PieDataset mtaDs = mtaPie.getDataset();
		CountsPieChart mtaChart = new CountsPieChart(mtaPie.getChartName(), mtaDs);
		String mtaChartUrl = UrlHelper.getChartUrl(
				mtaChart.generateChart(request.getSession(), new PrintWriter(out)));
%>
<table>
<tr>
	<td><img src="<%=cmChartUrl %>"/></td>
	<td><img src="<%=mtaChartUrl %>"/></td>
</tr>
</table>
<%
	}
%>
</div>

</body>
</html>