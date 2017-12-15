<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.db.tables.IntegerHolder"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_mkt.js" type="text/javascript">
</script>
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

	StringBuffer cmtsUrl = new StringBuffer("../app/topo_root_market.jsp");
	StringBuffer cmsUrl = new StringBuffer("../app/topo_drilldwn_mkt_cmses.jsp");
	StringBuffer reportsUrl = new StringBuffer("../app/topo_drilldwn_mkt_reports.jsp");
%>

<div id="menuBar" class="menuBar"></div>

<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" , class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %> </a></p>
<p id="pCmts" class="menuItem"><a id="aCmts"
	href="<%=cmtsUrl.toString() %>" , class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_CMTS) %></a></p>
<p id="pCms" class="menuItem"><a id="aCms"
	href="<%=cmsUrl.toString() %>" , class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_CMS) %></a></p>
<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" , class="menuItem" alt="">| <%=rb.getString(ResourceKeys.K_REPORTS) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1_nm"><%=rb.getString(ResourceKeys.K_CMTS_ES) %> <%=rb.getString(ResourceKeys.K_FOR) %> <%=rb.getString(ResourceKeys.K_MARKET) %> : </td>
<td class="ca_h1_val"><%=sysName%></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<%
	TopologyHelper topoHelper = new TopologyHelper();
	CmtsT[] cmtsArray = topoHelper.getCmtses();
%>

<table>
<%
	IntegerHolder iH = new IntegerHolder();
	iH.value = -1;
	final int numItemsPerRow = 5;
	for (int i = 0; cmtsArray != null && i < cmtsArray.length; i++) {
		StringBuffer url = new StringBuffer("../app/topo_drilldwn_cmts.jsp");
		CmtsT cmts = cmtsArray[i];
		UrlHelper.AppendTopoHierarchyIDs(url, cmts.getTopologyKey());
		url.append("&").append(UrlHelper.RESOURCE_ID).append("=")
			.append(cmts.getCmtsResId());
		String cmtsName = cmts.getCmtsName();
		String statusImg = StatusColorHelper.getStatusImageUrl(cmts.getStatusColor());
		if (DisplayAlgorithms.IsTableBeginTr(i, numItemsPerRow)) {
%>
<tr class="net_obj_cmts">
<%
		}
%>
		<td>
			<span>
				<a href="<%= url.toString() %>"	target="NET_OBJ_DATA">
					<img src="<%= statusImg %>" class="statusBullet" /> <br />
						<%=cmtsName%> 
				</a>
			</span>
		</td>
<%
		if (DisplayAlgorithms.IsTableEndTr(i, numItemsPerRow, cmtsArray.length, iH)) {
%>
</tr>
<%
		}

	} // end for each cmts in list ...
%>
</table>
</div>

</body>
</html>
