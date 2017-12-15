<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.UrlHelper"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/admin_topo_tabs.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_TOPOLOGY) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div id="tabItem" class="tabItem">
<p id="pLocalSys" class="tabItem"><a id="aLocalSys" href="../app/admin_topo_local_sys.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TOPO_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_LOCAL_SYS_TAB) %></a></p>
<p id="pRegion" class="tabItem"><a id="aRegion" href="../app/admin_topo_region.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TOPO_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_REGION) %></a></p>
<p id="pMarket" class="tabItem"><a id="aMarket" href="../app/admin_topo_market.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TOPO_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_MARKET) %></a></p>
<p id="pBlade" class="tabItem"><a id="aBlade" href="../app/admin_topo_blade.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TOPO_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_BLADE) %></a></p>
</div>

</body>
</html>