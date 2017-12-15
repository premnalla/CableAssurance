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
<script src="../js/admin_perf_tabs.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_PERFORMANCE) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div id="tabItem" class="tabItem">
<p id="pCm" class="tabItem"><a id="aCm" href="../app/admin_perf_cm.jsp" 
	class="tabItem" target="<%=UrlHelper.ADMIN_PERF_TAB_DATA_FRAME %>" alt=""><%=rb.getString(ResourceKeys.K_CM) %></a></p>
</div>

</body>
</html>