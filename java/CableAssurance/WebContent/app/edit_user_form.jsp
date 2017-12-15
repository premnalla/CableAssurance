<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
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
	final int textFieldSize = 30;

	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
	
%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewUserFormBean"></jsp:useBean>

<%
	CmtsEditExtractionHelper h = new CmtsEditExtractionHelper(request);
	List result = h.parseAttributes();
	/*
	 * Edit User
	 */
	AdminHelper ah = new AdminHelper();
	String loginId = (String)result.get(0);
	UserT user = ah.getUser(loginId);
	RoleT[] roles = ah.getRoles();
	StringBuffer actionUrl = new StringBuffer("../app/edit_user.jsp?");
	StringBuffer resetPwName = new StringBuffer(UrlHelper.RESET_PASSWORD_PREFIX);
	resetPwName.append(loginId);
	boolean bIsDeactive = false;
	if (user != null) {
		BackBean.setFirstName(user.getFirstName());
		BackBean.setMiddleInitial(user.getMiddleInitial());
		BackBean.setLastName(user.getLastName());
		BackBean.setLoginUserId(user.getLoginName());
		BackBean.setLoginPw(user.getLoginPassword());
		BackBean.setRoleName(user.getRoleName());
		BackBean.setUserLocation(user.getLocation());
		// String isDeactive;
		if (user.getIsActive().shortValue()==0) {
			bIsDeactive = true;
			// isDeactive = "on";
		} else {
			bIsDeactive = false;
			// isDeactive = "off";			
		}
		// BackBean.setAccountActive(isDeactive);
		actionUrl.append("&").append(UrlHelper.LOGIN_ID).append("=")
		.append(user.getLoginName());
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CHANGE) %> <%=rb.getString(ResourceKeys.K_USER) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup> Required field</em></small>
</p>

<form name="editUserForm" action="<%=actionUrl.toString() %>" method="post">
<div class="table_1">
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
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_ROLE) %></td>
	<td class="value_lg">
		<select name="roleName">
		<%
			for (int i=0; roles != null && i < roles.length; i++) {
				RoleT r = roles[i];
				if (r.getRoleName().equals(BackBean.getRoleName())) {
					%>
					<option value="<%=r.getRoleName() %>" selected="selected"><%=r.getRoleName() %></option>
					<%					
				} else {
					%>
					<option value="<%=r.getRoleName() %>"><%=r.getRoleName() %></option>
					<%
				} // endif
			} // for
		%>
		</select>
	</td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_LOCATION) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getUserLocation() %>" name="userLocation"/></td>
</tr>

<%
	if (bIsDeactive) {
%>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DEACT_USER) %></td>
	<td class="value_lg"><input type="checkbox" name="accountActive" checked="checked"/></td>
</tr>
<%
	} else {
%>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DEACT_USER) %></td>
	<td class="value_lg"><input type="checkbox" name="accountActive"/></td>
</tr>
<%
	}
%>
<tr >
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE_CHANGES) %>" name="save"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_RESET_PW) %>" name="<%=resetPwName.toString() %>"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
		<input type="hidden" size="<%=textFieldSize %>" name="loginPw" />
	<td>
</tr>

</table>
</div>
</form>
</div>

</body>
</html>