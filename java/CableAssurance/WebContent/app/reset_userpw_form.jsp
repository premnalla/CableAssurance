<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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
	final int textFieldSize = 30;

	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	StringBuffer actionUrl = new StringBuffer("../app/reset_userpw.jsp?");
	GenericExtractionHelper ex = new GenericExtractionHelper(request);
	ex.setPrefix(UrlHelper.RESET_PASSWORD_PREFIX);
	List l = ex.parseAttributes();
	if (l.size() > 0) {
		actionUrl.append(UrlHelper.LOGIN_ID).append("=").append((String)l.get(0));
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_RESET_PW) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewUserForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_NEW_PASSWD) %></td>
	<td class="value_lg"><input type="password" size="<%=textFieldSize %>" name="loginPw"/></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_RETYPE_PASSWD) %></td>
	<td class="value_lg"><input type="password" size="<%=textFieldSize %>" name="loginPw2"/></td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_RESET) %>" name="save"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>