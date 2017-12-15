<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%

	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	/*
	 * Get default values thru the Service layer
	 */
	AdminHelper admHelper = new AdminHelper();
	PollingIntervalsT pi = admHelper.getPollingIntervals();
	PollingIntervalListBox lb = PollingIntervalListBox.getInstance();
	String cmtsPollValue = lb.convertToOption(pi.getCmtsPollInterval());
	String cmPollValue = lb.convertToOption(pi.getCmPollInterval());
	String mtaPollValue = lb.convertToOption(pi.getMtaPollInterval());
	String mtaPingValue = lb.convertToOption(pi.getMtaPingInterval());

%>

<% 
	/*
	 * Fill the bean with values from the Service layer
	 */
	PollingIntervalListBox opt = PollingIntervalListBox.getInstance();
	List optL = opt.getOptionList();
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_GENERAL) %> <%=rb.getString(ResourceKeys.K_SETTINGS) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="adminGeneralForm" action="../app/admin_general_action.jsp" method="post">
<div class="table_1">
<table border="0">
<tr>
<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_POLL_INTERVAL) %></td>
<td class="value_lg">
	<select name="cmtsPollInterval">
	<%
		for (int i=0; i<optL.size(); i++) {
			String s = (String) optL.get(i);
			if (s.equals(cmtsPollValue)) {
	%>
				<option value="<%=s %>" selected="selected"><%=s %></option>
	<%
			} else {
	%>
				<option value="<%=s %>" ><%=s %></option>
	<%
			}
		}
	%>
	</select>
</td>
</tr>
<tr>
<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_POLL_INTERVAL) %></td>
<td class="value_lg">
	<select name="cmPollInterval">
	<%
		for (int i=0; i<optL.size(); i++) {
			String s = (String) optL.get(i);
			if (s.equals(cmPollValue)) {
	%>
				<option value="<%=s %>" selected="selected"><%=s %></option>
	<%
			} else {
	%>
				<option value="<%=s %>" ><%=s %></option>
	<%
			}
		}
	%>
	</select>
</td>
</tr>
<tr>
<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_POLL_INTERVAL) %></td>
<td class="value_lg">
	<select name="mtaPollInterval">
	<%
		for (int i=0; i<optL.size(); i++) {
			String s = (String) optL.get(i);
			if (s.equals(mtaPollValue)) {
	%>
				<option value="<%=s %>" selected="selected"><%=s %></option>
	<%
			} else {
	%>
				<option value="<%=s %>" ><%=s %></option>
	<%
			}
		}
	%>
	</select>
</td>
</tr>
<tr>
<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_PING_INTERVAL) %></td>
<td class="value_lg">
	<select name="mtaPingInterval">
	<%
		for (int i=0; i<optL.size(); i++) {
			String s = (String) optL.get(i);
			if (s.equals(mtaPingValue)) {
	%>
				<option value="<%=s %>" selected="selected"><%=s %></option>
	<%
			} else {
	%>
				<option value="<%=s %>" ><%=s %></option>
	<%
			}
		}
	%>
	</select>
</td>
</tr>
<tr >
<td colspan="2">
<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="save"/>
</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>