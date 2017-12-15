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
<script src="../js/alarm_menu.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?").append(
			request.getQueryString());
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CSR_PORTAL) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<form name="mainAlarmQueryFilterForm" action="../caservlet/CsrInputFormServlet" target="<%=UrlHelper.CSR_MAIN_FRAME %>" method="post">
<table>
	<tr>
		<td class="name_lg"><%=rb.getString(ResourceKeys.K_ACCOUNT_NUM) %></td>
		<td class="value_lg"><input type="text" size="25" name="acctNumber" /></td>
	</tr>
	<tr>
		<td class="name_lg"><%=rb.getString(ResourceKeys.K_CUSTOMER_NAME) %></td>
		<td class="value_lg"><input type="text" size="25" name="custName" /></td>
	</tr>
	<tr>
		<td class="name_lg"><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
		<td class="value_lg"><input type="text" size="25" name="mtaMac" /></td>
	</tr>
	<tr>
		<td class="name_lg"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
		<td class="value_lg"><input type="text" size="25" name="cmMac" /></td>
	</tr>
	<tr>
		<td></td>
		<td class="value_lg"><input type="reset" value="<%=rb.getString(ResourceKeys.K_CLEAR) %>" name="clear"/><input type="submit" value="<%=rb.getString(ResourceKeys.K_LOOK_UP) %>" name="query"/></td>
	</tr>
</table>
</form>
</div>
</div>

</body>
</html>
