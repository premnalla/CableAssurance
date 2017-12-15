<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/admin_main_tabs.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?").append(
			request.getQueryString());
%>

<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_SYS_ADMIN) %></td>
</tr>
</table>
</div>

<div id="tabItem" class="tabItem">
<p id="pGeneral" class="tabItem"><a id="aGeneral" href="../app/admin_general.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_GENERAL) %></a></p>
<p id="pAlarms" class="tabItem"><a id="aAlarms" href="../app/admin_alarms_frm.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_ALARMS) %></a></p>
<p id="pPerf" class="tabItem"><a id="aPerf" href="../app/admin_perf_frm.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_PERFORMANCE) %></a></p>
<p id="pTopo" class="tabItem"><a id="aTopo" href="../app/admin_topo_frm.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_TOPOLOGY) %></a></p>
<p id="pCmts" class="tabItem"><a id="aCmts" href="../app/admin_cmts.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_CMTS) %></a></p>
<p id="pCms" class="tabItem"><a id="aCms" href="../app/admin_cms.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_CMS) %></a></p>
<p id="pUser" class="tabItem"><a id="aUser" href="../app/admin_user_frm.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_USER) %></a></p>
</div>

</body>
</html>