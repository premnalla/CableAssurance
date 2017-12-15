<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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
	TopologyHelper th = new TopologyHelper();
	BladeT[] blades = th.getBlades();
	StringBuffer actionUrl = new StringBuffer("../app/edit_delete_topo_blade.jsp");
%>

<div class="center">
<table>
<tr>
	<td class="ca_h2"><%=rb.getString(ResourceKeys.K_BLADE) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewBladeForm" action="../app/add_new_topo_blade_form.jsp" method="get">
<p>
<input type="submit" value="<%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_BLADE) %>" name="add"/>
</p>
</form>
</div>


<div class="center">
<form name="editDeleteBladeForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="1">
<tr class="alarms_tr_th">
<th><%=rb.getString(ResourceKeys.K_NAME) %></th>
<th><%=rb.getString(ResourceKeys.K_HOST) %></th>
</tr>

<%
	for (int i = 0; blades != null && i < blades.length; i++) {
		BladeT blade = blades[i];
		StringBuffer editName = new StringBuffer(UrlHelper.CMTS_EDIT_PREFIX);
		editName.append(blade.getBladeId());
		StringBuffer delName = new StringBuffer(UrlHelper.CMTS_DELETE_PREFIX);
		delName.append(blade.getBladeId());
%>

<tr class="alarms">
	<td><%=blade.getName() %></td>
	<td><%=blade.getHost() %></td>
	<td>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_EDIT) %>" name="<%=editName.toString() %>"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_DELETE) %>" name="<%=delName.toString() %>"/>
	</td>
</tr>

<%
	} // end foreach cmts
%>

</table>
</div>
</form>
</div>

</body>
</html>