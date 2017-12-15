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
<script src="../js/admin_alarm_tabs.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_ALARM) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div id="tabItem" class="tabItem">
<p id="pCmts" class="tabItem"><a id="aCmts" href="../app/admin_alarm_cmts.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_ALARM_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_CMTS) %></a></p>
<p id="pCms" class="tabItem"><a id="aCms" href="../app/admin_alarm_cms.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_ALARM_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_CMS) %></a></p>
<p id="pHfc" class="tabItem"><a id="aHfc" href="../app/admin_alarm_hfc.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_ALARM_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_HFC) %></a></p>
<p id="pMta" class="tabItem"><a id="aMta" href="../app/admin_alarm_mta.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_ALARM_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_MTA) %></a></p>
</div>

</body>
</html>