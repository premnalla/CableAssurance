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
<script src="../js/net_obj_menu_cms.js" type="text/javascript"></script>
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
	refreshUrl.append(request.getServletPath()).append("?").append(
	request.getQueryString());

	/*
	 * Get the CMS
	 */
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,
	bladeId);
	BigInteger cmsResId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	CmsT svcCms = th.getCms(tK, cmsResId);

	StringBuffer alarmsUrl = new StringBuffer(
		"../app/topo_drilldwn_cms_alarms.jsp");
	
	UrlHelper.AppendTopoHierarchyIDs(alarmsUrl, tK);
	alarmsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
	.append(resId);
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">

<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %> </a></p>
	
<p id="pAlarms" class="menuItem"><a id="aAlarms"
	href="<%=alarmsUrl.toString() %>" class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_ALARMS) %> </a></p>

</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CMS) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
<div class="left">
<table>
<tr>
	<td class="td_nm"><%=rb.getString(ResourceKeys.K_NAME) %> :</td>
	<td class="td_val"><em><%=svcCms.getCmsName()%></em></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset" >
</div>

</body>
</html>
