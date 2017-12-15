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
	EmtaT mta = null;
	synchronized (session) {
		cm = (CableModemT) session.getAttribute(ServletConstants.HTTP_CM);
		mta = (EmtaT) session.getAttribute(ServletConstants.HTTP_MTA);
	}

	CMSLineT[] cmsData = null;
	if (mta != null && cm != null) {
		TopologyHelper th = new TopologyHelper();
		EmtaSecondaryT mtaExtra = th.getEmtaSecondary(mta.getTopologyKey(), mta.getEmtaResId());
		if (mtaExtra != null) {
			CmsHelper ch = new CmsHelper();
			CMSLineT[] input = new CMSLineT[2];
			int i = 0;
			input[i++].setNumber(mtaExtra.getPhone1());
			input[i++].setNumber(mtaExtra.getPhone2());
			cmsData = ch.getLineStatus(input);
		}
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CMS_INFO) %></td>
</tr>
</table>
</div>

<%
	if (cmsData != null && cmsData.length >= 1) {
		int j = 0;
%>

<div class="center">
<table border="0">
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_PHONE_LINE_1) %></td>
	<td class="value_csr"><%=cmsData[j].getNumber() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_LINE_1_STATUS) %></td>
	<td class="value_csr"><%=cmsData[j++].getStatus() %></td>
</tr>
<%
		if (cmsData.length > j && cmsData[j] != null) {
%>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_PHONE_LINE_2) %></td>
	<td class="value_csr"><%=cmsData[j].getNumber() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_LINE_2_STATUS) %></td>
	<td class="value_csr"><%=cmsData[j++].getStatus() %></td>
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