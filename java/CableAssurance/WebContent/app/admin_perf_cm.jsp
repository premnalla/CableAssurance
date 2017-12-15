<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<jsp:useBean id="Bean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.AdminPerfCmBean"></jsp:useBean>

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	/*
	 * Get default values thru the Service layer
	 */
	AdminHelper admHelper = new AdminHelper();
	CmPerformanceConfigT p = admHelper.getCmPerfConfig();	
	if (p != null) {
		Bean.setLowDwnStrmSnr(p.getDownstreamSnrLower());
		
		Bean.setLowDwnStrmPower(p.getDownstreamPowerLower());
		Bean.setUpDwnStrmPower(p.getDownstreamPowerUpper());
		
		Bean.setLowUpStrmPower(p.getUpstreamPowerLower());
		Bean.setUpUpStrmPower(p.getUpstreamPowerUpper());
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_PERFORMANCE) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="adminPerfCmForm" action="../app/admin_perf_cm_action.jsp" method="post">
<div>
<table border="1">
<thead><tr><td colspan="3" class="thead"><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_PERFORMANCE) %> <%=rb.getString(ResourceKeys.K_THRESHOLDS) %></td></tr></thead>
<tr class="alarms_tr_th">
	<th></th>
	<th><%=rb.getString(ResourceKeys.K_LOWER_LIMIT) %></th>
	<th><%=rb.getString(ResourceKeys.K_UPPER_LIMIT) %></th>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DOWNSTREAM) %> <%=rb.getString(ResourceKeys.K_SNR) %> (dB)</td>
	<td><input size="2" type="text" name="lowDwnStrmSnr" value="<%=Bean.getLowDwnStrmSnr() %>"/></td>
	<td>---</td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DOWNSTREAM) %> <%=rb.getString(ResourceKeys.K_POWER) %> (dBMv)</td>
	<td><input size="2" type="text" name="lowDwnStrmPower" value="<%=Bean.getLowDwnStrmPower() %>"/></td>
	<td><input size="2" type="text" name="upDwnStrmPower" value="<%=Bean.getUpDwnStrmPower() %>"/></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_UPSTREAM) %> <%=rb.getString(ResourceKeys.K_POWER) %> (dBMv)</td>
	<td><input size="2" type="text" name="lowUpStrmPower" value="<%=Bean.getLowUpStrmPower() %>"/></td>
	<td><input size="2" type="text" name="upUpStrmPower" value="<%=Bean.getUpUpStrmPower() %>"/></td>
</tr>
<tr>
	<td colspan="3"><input type="submit" name="save" value="<%=rb.getString(ResourceKeys.K_SAVE) %>"/></td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>