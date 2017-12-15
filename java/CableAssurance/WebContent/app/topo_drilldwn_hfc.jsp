<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="org.jfree.data.general.PieDataset"%>
<%@ page import="com.palmyrasyscorp.www.chart.CountsPieChart"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@ page import="com.palmyrasyscorp.db.tables.IntegerHolder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_hfc.js" type="text/javascript"></script>
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
	
	StringBuffer cmpUrl = new StringBuffer("../app/multi_cm_mta_cmp_form.jsp?");
	cmpUrl.append(request.getQueryString());
	
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	BigInteger hfcRId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	HfcT hfc = th.getHfc(tK, hfcRId);
	CmtsT cmts = th.getCmts(tK, hfc.getCmtsResId());
	GenericCountsT cnts = hfc.getCounts();
	short totalCm = cnts.getTotalCm();
	short onlineCm = cnts.getOnlineCm();
	float percentOffline = 
		ConversionHelper.getPercentToOneDecDigit(totalCm, (short)totalCm-onlineCm);

	short totalMta = cnts.getTotalMta();
	short availMta = cnts.getAvailableMta();
	float percentUnavail = 
		ConversionHelper.getPercentToOneDecDigit(totalMta, (short)totalMta-availMta);
	
	CableModemT[] cmS = th.getHfcCmes(tK, hfcRId);
	EmtaT[] emtaS = th.getHfcEmtas(tK, hfcRId);

	StringBuffer plotsUrl = new StringBuffer(
	"../app/topo_drilldwn_hfc_plots.jsp?").append(request.getQueryString());

	StringBuffer alarmsUrl = new StringBuffer(
		"../app/topo_drilldwn_hfc_alarms.jsp?").append(request.getQueryString());

	StringBuffer reportsUrl = new StringBuffer(
	"../app/topo_drilldwn_hfc_reports.jsp?").append(request.getQueryString());
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">

<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>

<p id="pCompare" class="menuItem"><a id="aCompare"
	href="<%=cmpUrl.toString() %>" class="menuItem" alt="">| Compare</a></p>
	
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
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_HFC) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<div class="left">
<table>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_NAME) %> :</td>
	<td class="td_val"><em><%=hfc.getHfcName()%></em></td>
</tr>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_CMTS) %> :</td>
	<td class="td_val"><em><%=cmts.getCmtsName()%></em></td>
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

<div class="topoDrillDownCmes">
<h3><%=rb.getString(ResourceKeys.K_CABLE_MODEMS) %></h3>
<table>
<%
	IntegerHolder iH = new IntegerHolder();
	iH.value = -1;
	int numItemsPerRow = 3;
	for (int i = 0; cmS != null && i < cmS.length; i++) {
		StringBuffer url = new StringBuffer("../app/topo_drilldwn_cm_plus_plots.jsp");
		CableModemT cm = cmS[i];
		UrlHelper.AppendTopoHierarchyIDs(url, tK);
		url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
			.append(cm.getCmResId());
		String cmMac = cm.getMacAddress();
		String statusImg = StatusColorHelper.getStatusImageUrl(cm
			.getStatusColor().toString());
		if (DisplayAlgorithms.IsTableBeginTr(i, numItemsPerRow)) {
%>
<tr class="net_obj_cmts_child">
<%
		} // end if
%>
		<td>
			<span>
				<a href="<%= url.toString() %>"	>
					<img src="<%= statusImg %>" class="statusBullet" /> <br />
						<%=cmMac%> 
				</a>
			</span>
		</td>
<%
		if (DisplayAlgorithms.IsTableEndTr(i, numItemsPerRow, cmS.length, iH)) {
%>
</tr>
<%
		} // end if
	} // outer for loop
%>
</table>
</div>

<div class="topoDrillDownMtas">
<h3><%=rb.getString(ResourceKeys.K_MTA_S) %></h3>
<table>
<%
	iH.value = -1;
	numItemsPerRow = 3;
	for (int i = 0; emtaS != null && i < emtaS.length; i++) {
		StringBuffer url = new StringBuffer("../app/topo_drilldwn_emta_plus_plots.jsp");
		EmtaT emta = emtaS[i];
		UrlHelper.AppendTopoHierarchyIDs(url, tK);
		url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
			.append(emta.getEmtaResId());
		String mtaMac = emta.getMacAddress();
		String statusImg = StatusColorHelper.getStatusImageUrl(emta
			.getStatusColor().toString());
		if (DisplayAlgorithms.IsTableBeginTr(i, numItemsPerRow)) {
%>
<tr class="net_obj_cmts_child">
<%
		} // end if
%>
		<td>
			<span>
				<a href="<%= url.toString() %>"	>
					<img src="<%= statusImg %>" class="statusBullet" /> <br />
						<%=mtaMac%> 
				</a>
			</span>
		</td>
<%
		if (DisplayAlgorithms.IsTableEndTr(i, numItemsPerRow, emtaS.length, iH)) {
%>
	</tr>
<%
		} // end if
	} // outer for loop
%>
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
