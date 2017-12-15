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

	CableModemT cm = null;
	// EmtaT mta = null;
	synchronized (session) {
		cm = (CableModemT) session.getAttribute(ServletConstants.HTTP_CM);
		// mta = (EmtaT) session.getAttribute(ServletConstants.HTTP_MTA);
	}
	
	CmDataT cmData = null;
	// ChannelT upstrmData = null;
	if (cm != null) {
		CPeerHelper ch = new CPeerHelper();
		cmData = ch.getCmData(cm.getTopologyKey(), cm.getCmResId());
		// TopologyHelper th = new TopologyHelper();
		// upstrmData = th.getChannel(cm.getTopologyKey(), cm.getUpChannelResId());
	}

%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CM_INFO) %></td>
</tr>
</table>
</div>

<%
if (cmData != null) {
%>

<div class="center">
<table border="0">
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
	<td class="value_csr"><%=cmData.getMac() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_IP) %></td>
	<td class="value_csr"><%=cmData.getHost() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MODEM_INDEX) %></td>
	<td class="value_csr"><%=cmData.getCmIndex() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_DOWNSTREAM) %> <%=rb.getString(ResourceKeys.K_SNR) %></td>
	<td class="value_csr"><%=cmData.getDownstreamSNR() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_DOWNSTREAM) %> <%=rb.getString(ResourceKeys.K_POWER) %></td>
	<td class="value_csr"><%=cmData.getDownstreamPower() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_UPSTREAM) %> <%=rb.getString(ResourceKeys.K_POWER) %></td>
	<td class="value_csr"><%=cmData.getUpstreamPower() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_T1_COUNTS) %></td>
	<td class="value_csr"><%=cmData.getT1Count() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_T2_COUNTS) %></td>
	<td class="value_csr"><%=cmData.getT2Count() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_T3_COUNTS) %></td>
	<td class="value_csr"><%=cmData.getT3Count() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_T4_COUNTS) %></td>
	<td class="value_csr"><%=cmData.getT4Count() %></td>
</tr>
</table>
</div>

<%
} // endif
%>

</body>
</html>