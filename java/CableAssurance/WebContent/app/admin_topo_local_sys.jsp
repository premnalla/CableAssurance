<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.palmyrasyscorp.www.webservices.helper.LocalSystemHelper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	final int textFieldSize = 30;

	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

%>

<jsp:useBean id="LSBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.LocalSystemFormBean"></jsp:useBean>

<%
	/*
	 * Edit Local System
	 */
	LocalSystemHelper ls = new LocalSystemHelper();
	LocalSystemT svcLs = ls.getLocalSystemType();
	StringBuffer actionUrl = new StringBuffer("../app/admin_topo_local_sys_action.jsp");
	if (svcLs != null) {
		LSBean.setSystemName(svcLs.getSystemName());
		LSBean.setSystemType(svcLs.getSystemType().getValue());
	}	
	List sysTypeList = ls.getSystemNames();
%>

<div class="center">
<table>
<tr>
	<td class="ca_h2"><%=rb.getString(ResourceKeys.K_LOCAL_SYSTEM) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD) %></em></small>
</p>

<form name="editLocalSystemForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LOCAL_SYSTEM) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=LSBean.getSystemName() %>" name="systemName"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LOCAL_SYSTEM) %> <%=rb.getString(ResourceKeys.K_TYPE) %></td>
	<td class="value_lg">
		<select name="systemType">
		<%
			for (int i=0; i<sysTypeList.size(); i++) {
				String s = (String) sysTypeList.get(i);
				if (s.equals(LSBean.getSystemType())) {
		%>
			<option value="<%=s %>" selected="selected"><%=s %></option>
		<%
				} else {
		%>
			<option value="<%=s %>"><%=s %></option>
		<%
				}
		%>
		<%
			}
		%>
		</select>
	</td>
</tr>
<tr >
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="save"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>