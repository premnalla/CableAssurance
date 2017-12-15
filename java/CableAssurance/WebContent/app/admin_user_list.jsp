<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
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
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_USER) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewUserForm" action="../app/add_new_user_form.jsp" method="get">
<p>
<input type="submit" value="<%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_USER) %>" name="addNewUserButton"/>
</p>
</form>
</div>

<div class="center">
<form name="editDelteUserForm" action="../app/edit_delete_user.jsp" method="get">
<table border="1">
<tr class="alarms_tr_th">
<th><%=rb.getString(ResourceKeys.K_NAME) %></th>
<th><%=rb.getString(ResourceKeys.K_LOGIN) %></th>
<th><%=rb.getString(ResourceKeys.K_ROLE) %></th>
<th><%=rb.getString(ResourceKeys.K_LOCATION) %></th>
<th><%=rb.getString(ResourceKeys.K_ACTIVE) %></th>
</tr>

<%
	AdminHelper admHelper = new AdminHelper();
	UserT[] userArray = admHelper.getUsers();

	for (int i = 0; userArray != null && i < userArray.length; i++) {
		UserT user = userArray[i];
		StringBuffer editName = new StringBuffer(UrlHelper.CMTS_EDIT_PREFIX);
		editName.append(user.getLoginName());
		StringBuffer delName = new StringBuffer(UrlHelper.CMTS_DELETE_PREFIX);
		delName.append(user.getLoginName());
		StringBuffer resetPw = new StringBuffer(UrlHelper.RESET_PASSWORD_PREFIX);
		resetPw.append(user.getLoginName());
		String isActive = UrlHelper.BitToYesNo(user.getIsActive().shortValue());
%>

<tr class="alarms">
<td><%=user.getFirstName() %> <%=user.getMiddleInitial() %>. <%=user.getLastName() %></td>
<td><%=user.getLoginName() %></td>
<td><%=user.getRoleName() %></td>
<td><%=user.getLocation() %></td>
<td><%=isActive %></td>
<td>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_EDIT) %>" name="<%=editName.toString() %>"/>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_DELETE) %>" name="<%=delName.toString() %>"/>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_RESET_PW) %>" name="<%=resetPw.toString() %>"/>
</td>
</tr>

<%
	} // end foreach cmts
%>
</table>
</form>
</div>

</body>
</html>