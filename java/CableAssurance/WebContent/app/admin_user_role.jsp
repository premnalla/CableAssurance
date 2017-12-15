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

	AdminHelper ah = new AdminHelper();
	RoleT[] roles = ah.getRoles();
	ApplicationDomainT[] domTypes = ah.getApplicationDomains();
	UserAccessT[] accessRights = ah.getAccessRights();
	
	if (roles == null || roles.length == 0 ||
			domTypes == null || domTypes.length == 0 &&
			accessRights == null && accessRights.length == 0) {
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<%
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_USER) %> <%=rb.getString(ResourceKeys.K_ROLE) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewRoleForm" action="../app/add_new_user_role_form.jsp" method="get">
<p>
<input type="submit" value="<%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_ROLE) %>" name="addNewRoleButton"/>
</p>
</form>
</div>

<div class="center">
<form name="editDelteRoleForm" action="../app/edit_delete_user_role.jsp" method="get">
<table border="1">
<tr class="alarms_tr_th">
<th rowspan="2"><%=rb.getString(ResourceKeys.K_ROLE) %></th>
<th rowspan="2"><%=rb.getString(ResourceKeys.K_APP_DOMAIN) %></th>
<th rowspan="1" colspan="<%=accessRights.length %>"><%=rb.getString(ResourceKeys.K_ACCESS_RIGHTS) %></th>
</tr>
<tr class="alarms_tr_th">
<%
	for (int p=0; p<accessRights.length; p++) {
%>
<th><%=accessRights[p].getType().getValue() %></th>
<%
	} // foreach accessRight
%>
</tr>

<%

	for (int i = 0; i < roles.length; i++) {
		RoleT role = roles[i];
		if (role != null) {
			StringBuffer editName = new StringBuffer(UrlHelper.CMTS_EDIT_PREFIX);
			editName.append(role.getRoleName());
			StringBuffer delName = new StringBuffer(UrlHelper.CMTS_DELETE_PREFIX);
			delName.append(role.getRoleName());
%>

<tr class="alarms">
<td rowspan="<%=domTypes.length %>"><%=role.getRoleName() %></td>
<td><%=domTypes[0].getType().getValue() %></td>
<%
			ApplicationDomainT rDom = 
				AccessControlHelper.GetDomainInRole(role, domTypes[0].getType().getValue());
			for (int p=0; p<accessRights.length; p++) {
				if (rDom != null) {
					UserAccessT dAcc = 
						AccessControlHelper.GetAccessRightInDomain(rDom, accessRights[p].getType().getValue());
					if (dAcc != null) {
%>
<td>X</td>
<%
					} else {
						// dAcc == null
%>
<td><%=UrlHelper.UNKNOWN_FILLER %></td>
<%
						
					}
				} else {
					// rDom == null
%>
<td><%=UrlHelper.UNKNOWN_FILLER %></td>
<%
				}
			} // foreach accessRight
%>
<td rowspan="<%=domTypes.length %>">
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_EDIT) %>" name="<%=editName.toString() %>"/>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_DELETE) %>" name="<%=delName.toString() %>"/>
</td>
</tr>

<%
			// NOTE: j has to start from 1 instead of 0 because we have
			// already diplayed one row of this item above.
			for (int j=1; j<domTypes.length; j++) {
%>
<tr class="alarms">
<td><%=domTypes[j].getType().getValue() %></td>
<%
			rDom = AccessControlHelper.GetDomainInRole(role, domTypes[j].getType().getValue());
			for (int p=0; p<accessRights.length; p++) {
				if (rDom != null) {
					UserAccessT dAcc = 
						AccessControlHelper.GetAccessRightInDomain(rDom, accessRights[p].getType().getValue());
					if (dAcc != null) {
%>
<td>X</td>
<%
					} else {
						// dAcc == null
%>
<td><%=UrlHelper.UNKNOWN_FILLER %></td>
<%
						
					}
				} else {
					// rDom == null
%>
<td><%=UrlHelper.UNKNOWN_FILLER %></td>
<%
				}
			} // foreach accessRight
%>
</tr>
<%
			} // foreach domType
%>

<%
		} // endif (role != null)
		
	} // foreach role
%>

</table>
</form>
</div>

</body>
</html>