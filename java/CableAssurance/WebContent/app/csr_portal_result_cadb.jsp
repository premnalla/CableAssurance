<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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
	
	HfcT hfcData = null;
	CmtsT cmtsData = null;
	ChannelT upstrmData = null;
	if (cm != null) {
		TopologyHelper th = new TopologyHelper();
		hfcData = th.getHfc(cm.getTopologyKey(), cm.getHfcResId());
		cmtsData = th.getCmts(cm.getTopologyKey(), cm.getCmtsResId());
		upstrmData = th.getChannel(cm.getTopologyKey(), cm.getUpChannelResId());
	}

%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CA_INFO) %></td>
</tr>
</table>
</div>

<%
if (cm != null) {
%>
<div class="center">
<table border="0">
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
	<td class="value_csr"><%=cm.getMacAddress() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_IP) %></td>
	<td class="value_csr"><%=cm.getHost() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_FQDN) %></td>
	<td class="value_csr"><%=cm.getFqdn() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_DOCSIS_STATUS) %></td>
	<td class="value_csr"><%=cm.getCmStatus() %></td>
</tr>

<%
	if (upstrmData != null) {
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_UPSTREAM) %> <%=rb.getString(ResourceKeys.K_UPSTREAM) %></td>
	<td class="value_csr"><%=upstrmData.getChannelName() %></td>
</tr>
<%
	} // endif upstrmData
%>

<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MODEM_INDEX) %></td>
	<td class="value_csr"><%=cm.getCmIndex() %></td>
</tr>

<%
	if (cmtsData != null) {
%>
<tr >
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_csr"><%=cmtsData.getCmtsName() %></td>
</tr>
<%
	} // endif cmts
	if (hfcData != null) {
		String statusImg = StatusColorHelper.getStatusImageUrl(hfcData.getStatusColor());
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_csr"><%=hfcData.getHfcName() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_STATUS) %></td>
	<td class="value_csr"><img src="<%= statusImg %>" class="statusBullet" /></td>
</tr>
<%
	} // endif hfcData
%>

</table>
</div>

<%
} // endif cm != null
%>

</body>
</html>