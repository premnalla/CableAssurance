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
	CTEDataT custData = null;
	CableModemT cm = null;
	// EmtaT mta = null;
	synchronized (session) {
		custData = (CTEDataT) session.getAttribute(ServletConstants.CUSTOMER);
		cm = (CableModemT) session.getAttribute(ServletConstants.HTTP_CM);
		// mta = (EmtaT) session.getAttribute(ServletConstants.HTTP_MTA);
	}
	if (custData == null) {
		if (cm != null) {
			CTEQueryInputT[] qI = new CTEQueryInputT[1];
			qI[0] = new CTEQueryInputT();
			qI[0].setCmMac(cm.getMacAddress());
			CteHelper ch = new CteHelper();
			CTEDataT[] ca = ch.getCteData(qI);
			if (ca != null && ca.length >= 1) {
				custData = ca[0];
			}
		}
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CTE_INFO) %></td>
</tr>
</table>
</div>

<%
	if (custData != null) {
%>

<div class="center">
<table border="0">
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_ACCOUNT_NUM) %></td>
	<td class="value_csr"><%=custData.getCustomer().getAccountNumber() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CUSTOMER_NAME) %></td>
	<td class="value_csr"><%=custData.getCustomer().getLastName() %>, <%=custData.getCustomer().getFirstName() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CUST_FULL_ADDR) %></td>
	<td class="value_csr"><%=custData.getCustomer().getStreet1() %>
<%
		if (custData.getCustomer().getStreet2() != null) {
%>	
, <%=custData.getCustomer().getStreet2() %>
<%
		}
%>	
, <%=custData.getCustomer().getCity() %>, <%=custData.getCustomer().getState() %>, <%=custData.getCustomer().getZip() %>
	</td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
	<td class="value_csr"><%=custData.getCm().getMac() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_FQDN) %></td>
	<td class="value_csr"><%=custData.getCm().getFqdn() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_IP) %></td>
	<td class="value_csr"><%=custData.getCm().getHost() %></td>
</tr>
<tr >
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_csr"><%=custData.getCmts().getName() %></td>
</tr>
<tr >
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_HOST) %></td>
	<td class="value_csr"><%=custData.getCmts().getHost() %></td>
</tr>
<tr >
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_FQDN) %></td>
	<td class="value_csr"><%=custData.getCmts().getFqdn() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_csr"><%=custData.getCms().getName() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_HOST) %></td>
	<td class="value_csr"><%=custData.getCms().getHost() %></td>
</tr>
<tr >
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_MAC) %></td>
	<td class="value_csr"><%=custData.getMta().getMac() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_FQDN) %></td>
	<td class="value_csr"><%=custData.getMta().getFqdn() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_IP) %></td>
	<td class="value_csr"><%=custData.getMta().getHost() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_csr"><%=custData.getHfcName() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_PHONE_LINE_1) %></td>
	<td class="value_csr"><%=custData.getCustomer().getPhone1() %></td>
</tr>
<tr>
	<td class="name_csr"><%=rb.getString(ResourceKeys.K_PHONE_LINE_2) %></td>
	<td class="value_csr"><%=custData.getCustomer().getPhone2() %></td>
</tr>
</table>
</div>

<%
	}
%>

</body>
</html>