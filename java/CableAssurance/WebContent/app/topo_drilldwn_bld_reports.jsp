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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_bld.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
	request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	LocalSystemHelper lh = new LocalSystemHelper();
	String mktName = lh.getLocalSystemType().getSystemName();

	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey("0", "0", "0");
	BigInteger rId = new BigInteger("0");

	StringBuffer hfcDurUrl = new StringBuffer(CAJspPages.JSP_REPORT_HFC_STATUS);
	UrlHelper.AppendTopoHierarchyIDs(hfcDurUrl, tK);
	hfcDurUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(rId).append("&").append(UrlHelper.TYPE).append("=");
	StringBuffer hfcFreqUrl = new StringBuffer(hfcDurUrl);
	hfcDurUrl.append(UrlHelper.REPORT_TYPE_ST_DURATION);
	hfcFreqUrl.append(UrlHelper.REPORT_TYPE_ST_FREQUENCY);

	StringBuffer mtaDurUrl = new StringBuffer(CAJspPages.JSP_REPORT_MTA_STATUS);
	UrlHelper.AppendTopoHierarchyIDs(mtaDurUrl, tK);
	mtaDurUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(rId).append("&").append(UrlHelper.TYPE).append("=");
	StringBuffer mtaFreqUrl = new StringBuffer(mtaDurUrl);
	mtaDurUrl.append(UrlHelper.REPORT_TYPE_ST_DURATION);
	mtaFreqUrl.append(UrlHelper.REPORT_TYPE_ST_FREQUENCY);

	StringBuffer cmTcaDurUrl = new StringBuffer(CAJspPages.JSP_REPORT_CM_TCA);
	UrlHelper.AppendTopoHierarchyIDs(cmTcaDurUrl, tK);
	cmTcaDurUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(rId).append("&").append(UrlHelper.TYPE).append("=");
	StringBuffer cmTcaFreqUrl = new StringBuffer(cmTcaDurUrl);
	cmTcaDurUrl.append(UrlHelper.REPORT_TYPE_ST_DURATION);
	cmTcaFreqUrl.append(UrlHelper.REPORT_TYPE_ST_FREQUENCY);

	StringBuffer cmDurUrl = new StringBuffer(CAJspPages.JSP_REPORT_CM_STATUS);
	UrlHelper.AppendTopoHierarchyIDs(cmDurUrl, tK);
	cmDurUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(rId).append("&").append(UrlHelper.TYPE).append("=");
	StringBuffer cmFreqUrl = new StringBuffer(cmDurUrl);
	cmDurUrl.append(UrlHelper.REPORT_TYPE_ST_DURATION);
	cmFreqUrl.append(UrlHelper.REPORT_TYPE_ST_FREQUENCY);

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?")
	.append(request.getQueryString());

	StringBuffer cmtsUrl = new StringBuffer(CAJspPages.JSP_TOPO_ROOT_BLADE);
	StringBuffer reportsUrl = new StringBuffer(CAJspPages.JSP_TOPO_BLD_REPORTS);
%>

<div id="menuBar" class="menuBar"></div>

<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" , class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %> </a></p>
<p id="pCmts" class="menuItem"><a id="aCmts"
	href="<%=cmtsUrl.toString() %>" , class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_CMTS) %></a></p>
<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" , class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_REPORTS) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_REPORTS_FOR_BLD) %> : </td>
<td class="ca_h1_val"><%=mktName%></td>
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
	<td class="reports"><span><a href="<%=hfcDurUrl.toString() %>"><%=rb.getString(ResourceKeys.K_HFC_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="<%=hfcFreqUrl.toString() %>"><%=rb.getString(ResourceKeys.K_HFC_OOS_FREQUENT) %></a></span></td>
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
	<td class="reports"><span><a href="<%=mtaDurUrl.toString() %>"><%=rb.getString(ResourceKeys.K_MTA_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="<%=mtaFreqUrl.toString() %>"><%=rb.getString(ResourceKeys.K_MTA_OOS_FREQUENT) %></a></span></td>
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
	<td class="reports"><span><a href="<%=cmDurUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CM_OOS_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">2.</td>
	<td class="reports"><span><a href="<%=cmFreqUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CM_OOS_FREQUENT) %></a></span></td>
</tr>
<tr>
	<td class="reports">3.</td>
	<td class="reports"><span><a href="<%=cmTcaDurUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CM_TCA_LONGEST) %></a></span></td>
</tr>
<tr>
	<td class="reports">4.</td>
	<td class="reports"><span><a href="<%=cmTcaFreqUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CM_TCA_FREQUENT) %></a></span></td>
</tr>
</table>
</div>
</div>

</body>
</html>