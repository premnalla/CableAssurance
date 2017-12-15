<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@page import="com.palmyrasyscorp.www.webservices.helper.AdminHelper"%>
<%@page import="com.palmyrasys.www.webservices.CableAssurance.RoleT"%>
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
	
	AdminHelper ah = new AdminHelper();
	RoleT[] roles = ah.getRoles();
	
%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewUserFormBean"></jsp:useBean>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_USER) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: red;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD_WARM) %></em></small>
</p>

<form name="addNewUserForm" action="../app/add_new_user.jsp" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_FIRST_NAME) %></td>
	<td class="left"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getFirstName() %>" name="firstName"/></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_MIDDLE_INITIAL) %></td>
	<td class="value_lg"><input type="text" size="1" value="<%=BackBean.getMiddleInitial() %>" name="middleInitial"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LAST_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getLastName() %>" name="lastName"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LOGIN) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getLoginUserId() %>" name="loginUserId"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_PASSWORD) %></td>
	<td class="value_lg"><input type="password" size="<%=textFieldSize %>" value="<%=BackBean.getLoginPw() %>" name="loginPw"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_ROLE) %></td>
	<td class="value_lg">
		<select name="roleName">
		<%
			for (int i=0; roles != null && i < roles.length; i++) {
				RoleT r = roles[i];
		%>
		<option value="<%=r.getRoleName() %>"><%=r.getRoleName() %></option>
		<%
			}
		%>
		</select>
	</td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LOCATION) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getUserLocation() %>" name="userLocation"/></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DEACT_USER) %></td>
	<td class="value_lg"><input type="checkbox" name="accountActive"/></td>
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