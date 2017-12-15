<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
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
	 * Get Blades
	 */
	// AdminHelper ah = new AdminHelper();
	LocalSystemHelper lsh = new LocalSystemHelper();
	LocalSystemT ls = lsh.getLocalSystemType();
	// BladeT[] blades = th.getBlades();
	StringBuffer actionUrl = new StringBuffer("../app/edit_topo_bld_blade.jsp");
%>

<div class="center">
<table>
<tr>
	<td class="ca_h2"><%=rb.getString(ResourceKeys.K_BLADE) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="editBladeForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="1">
<tr class="alarms_tr_th">
<th><%=rb.getString(ResourceKeys.K_MARKET) %> <%=rb.getString(ResourceKeys.K_HOST) %></th>
</tr>
<tr class="alarms">
	<td>
		<input type="text" value="<%=ls.getParentHost() %>" name="parentHost"/>
	</td>
</tr>
<tr>
	<td>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE_CHANGES) %>" name="save"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_DOWNLOAD_CFG) %>" name="download"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>