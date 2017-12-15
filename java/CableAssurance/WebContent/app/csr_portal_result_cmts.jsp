<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
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
<body class="default_background">

<%
			AbstractResourceBundle rb = (AbstractResourceBundle) request
			.getSession()
			.getAttribute(ServletConstants.RESOURCE_BUNDLE);

	CableModemT cm = null;
	// EmtaT mta = null;
	synchronized (session) {
		cm = (CableModemT) session
		.getAttribute(ServletConstants.HTTP_CM);
		// mta = (EmtaT) session.getAttribute(ServletConstants.HTTP_MTA);
	}

	CmtsCmDataT cmtsCmData = null;
	ChannelT upstrmData = null;
	if (cm != null) {
		CPeerHelper ch = new CPeerHelper();
		cmtsCmData = ch.getCmtsCmData(cm.getTopologyKey(), cm.getCmtsResId(), 
				cm.getCmResId());
		if (cmtsCmData != null) {
			TopologyHelper th = new TopologyHelper();
			upstrmData = th.getChannel(cm.getTopologyKey(), new BigInteger(
				cmtsCmData.getUpstreamChannelIndex()));
		}
	}
%>

<div class="center">
<table>
	<tr>
		<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CMTS_INFO)%></td>
	</tr>
</table>
</div>

<%
if (cmtsCmData != null) {
%>
<div class="center">
<table border="0">
	<tr>
		<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM)%> <%=rb.getString(ResourceKeys.K_MAC)%></td>
		<td class="value_csr"><%=cmtsCmData.getCmMac()%></td>
	</tr>
	<tr>
		<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM)%> <%=rb.getString(ResourceKeys.K_IP)%></td>
		<td class="value_csr"><%=cmtsCmData.getCmIpAddress()%></td>
	</tr>
	
<%
	if (upstrmData != null) {
%>
	<tr>
		<td class="name_csr"><%=rb.getString(ResourceKeys.K_UPSTREAM)%>
		<%=rb.getString(ResourceKeys.K_UPSTREAM)%></td>
		<td class="value_csr"><%=upstrmData.getChannelName() %></td>
	</tr>
<%
	} // endif upstrmData
%>

	<tr>
		<td class="name_csr"><%=rb.getString(ResourceKeys.K_MODEM_INDEX)%></td>
		<td class="value_csr"><%=cmtsCmData.getCmIndex()%></td>
	</tr>
	<tr>
		<td class="name_csr"><%=rb.getString(ResourceKeys.K_DOCSIS_STATUS)%></td>
		<td class="value_csr"><%=cmtsCmData.getCmStatus()%></td>
	</tr>
</table>
</div>

<%
} // endif
%>

</body>
</html>
