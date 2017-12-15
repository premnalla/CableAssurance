<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
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
<body class="default_background" >

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	// CableModemT cm = null;
	EmtaT mta = null;
	synchronized (session) {
		// cm = (CableModemT) session.getAttribute(ServletConstants.HTTP_CM);
		mta = (EmtaT) session.getAttribute(ServletConstants.HTTP_MTA);
	}
	
	MtaDataT mtaData = null;
	if (mta != null) {
		CPeerHelper ch = new CPeerHelper();
		mtaData = ch.getMtaData(mta.getTopologyKey(), mta.getEmtaResId());
	}

%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_MTA_INFO) %></td>
</tr>
</table>
</div>

<%
	if (mtaData != null) {
%>

<div class="center">
<table border="0">
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MTA_PROV_STATUS) %></td>
	<td class="value_csr"><%=mtaData.getProvStatus() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MTA_PROV_COUNTER) %></td>
	<td class="value_csr"><%=mtaData.getProvCounter() %></td>
</tr>

<%
		if (mtaData.getBatteryStatus1() != null) {
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_BATTERY_1_STATUS) %></td>
	<td class="value_csr"><%=mtaData.getBatteryStatus1() %></td>
</tr>
<%
		} // endif
		if (mtaData.getBatteryStatus2() != null) {
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_BATTERY_2_STATUS) %></td>
	<td class="value_csr"><%=mtaData.getBatteryStatus2() %></td>
</tr>
<%
		} // endif
		if (mtaData.getPingStatus() != null) {
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_PING) %> <%=rb.getString(ResourceKeys.K_STATUS) %></td>
	<td class="value_csr"><%=mtaData.getPingStatus() %></td>
</tr>
<%
		} // endif
%>

</table>
</div>

<%
	} // endif
%>

</body>
</html>