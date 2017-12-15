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
<script type="text/javascript"></script>
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
	BigInteger cmtsRId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	CmtsT cmts = th.getCmts(tK, cmtsRId);

	StringBuffer hfcOosLongest = new StringBuffer("../app/report_hfc_oos_longest.jsp");
	UrlHelper.AppendTopoHierarchyIDs(hfcOosLongest, tK);
	hfcOosLongest.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(cmtsRId);
%>

<div id="menuBar" class="menuBar"></div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_REPORTS_FOR_CMTS) %> : <%=cmts.getCmtsName() %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_HFC) %></td>
</tr>
</table>
</div>
<div class="center">
<table border="0">
<tr>
	<td class="reports">1.</td>
	<td class="reports"><span><a href="<%=hfcOosLongest.toString() %>"><%=rb.getString(ResourceKeys.K_HFC_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_HFC_OOS_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">3.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_HFC_TCA_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">3.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_HFC_TCA_FREQUENT) %></a></span></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_MTA) %></td>
</tr>
</table>
</div>
<div class="center">
<table border="0">
<tr>
	<td class="reports">1.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_OOS_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">3.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_CMSLOC_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">4.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_CMSLOC_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">5.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_HWFAIL_LOGNEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">6.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_HWFAIL_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">7.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_LCFAIL_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">8.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_MTA_LCFAIL_FREQUENT) %></a></span></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CABLE_MODEM) %></td>
</tr>
</table>
</div>
<div class="center">
<table border="0">
<tr>
	<td class="reports">1.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_CM_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_CM_OOS_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">3.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_CM_TCA_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">4.</td>
	<td class="reports"><span><a href="../app/report_foo.jsp"><%=rb.getString(ResourceKeys.K_CM_TCA_FREQUENT) %></a></span></td>
</tr>
</table>
</div>
</div>

</body>
</html>