<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.StatusColorHelper"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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

	LocalSystemHelper sh = new LocalSystemHelper();
	LocalSystemT lst = sh.getLocalSystemType();
	String sysName = lst.getSystemName().toString();
	
	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?")
	.append(request.getQueryString());	
%>

<div id="menuBar" class="menuBar"></div>

<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" , class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1_nm"><%=rb.getString(ResourceKeys.K_BLADE) %> : </td>
<td class="ca_h1_val"><%=sysName%></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="left">
<%
	TopologyHelper topoHelper = new TopologyHelper();
	CmtsT[] cmtsArray = topoHelper.getCmtses();
%>
<table>
	<%
		StringBuffer url;
		for (int i = 0; cmtsArray != null && i < cmtsArray.length; i++) {
			url = new StringBuffer("../app/topo_drilldwn_cmts.jsp");
			CmtsT cmts = cmtsArray[i];
			UrlHelper.AppendTopoHierarchyIDs(url, cmts.getTopologyKey());
			url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(cmts.getCmtsResId());
			String cmtsName = cmts.getCmtsName();
			String statusImg = StatusColorHelper.getStatusImageUrl(cmts.getStatusColor());
	%>
	<tr class="cmts">
		<td>
			<span>
				<a href="<%= url.toString() %>"	target="NET_OBJ_DATA">
					<img src="<%= statusImg %>" class="statusBullet" /> 
						<%=cmtsName%> 
				</a>
			</span>
		</td>
	</tr>
	<%
		} // end foreach cmts in array
	%>
</table>
</div>
</div>

</body>
</html>
