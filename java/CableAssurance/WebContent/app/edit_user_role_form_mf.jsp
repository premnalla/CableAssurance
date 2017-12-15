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

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewRoleFormBean"></jsp:useBean>

<%
	CmtsEditExtractionHelper h = new CmtsEditExtractionHelper(request);
	List result = h.parseAttributes();
	/*
	 * Edit Role
	 */
	AdminHelper ah = new AdminHelper();
	ApplicationDomainT[] domTypes = ah.getApplicationDomains();
	UserAccessT[] accessRights = ah.getAccessRights();
	String roleName = (String)result.get(0);
	RoleT role = ah.getRole(roleName);
	StringBuffer actionUrl = new StringBuffer("../app/edit_user_role.jsp?");

	if (role == null || domTypes == null || domTypes.length == 0 ||
			accessRights == null || accessRights.length == 0) {
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<%
	}
	
	BackBean.setRoleName(role.getRoleName());
	actionUrl.append("&").append(UrlHelper.NAME).append("=")
		.append(role.getRoleName());

%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CHANGE) %> <%=rb.getString(ResourceKeys.K_ROLE) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD_WARM) %></em></small>
</p>

<form name="editUserRoleForm" action="<%=actionUrl.toString() %>" method="post">
<div class="table_1">
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_ROLE) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="left"><input type="text" size="<%=textFieldSize %>" value="<%=BackBean.getRoleName() %>" name="roleName"/></td>
</tr>
</table>

<table border="1">
<tr>
<th rowspan="2"><%=rb.getString(ResourceKeys.K_APP_DOMAIN) %></th>
<th rowspan="1" colspan="<%=accessRights.length %>"><%=rb.getString(ResourceKeys.K_ACCESS_RIGHTS) %></th>
</tr>
<tr>
<%
	for (int i=0; i<accessRights.length; i++) {
%>
<th><%=accessRights[i].getType().getValue() %></th>
<%
	} // foreach accessRight
%>
</tr>

<%
	for (int i=0; i<domTypes.length; i++) {
		ApplicationDomainT d = AccessControlHelper.GetDomainInRole(role, domTypes[i].getType().getValue());
%>
<tr>
<td class="name_lg"><%=domTypes[i].getType().getValue() %></td>
<%
		for (int j=0; j<accessRights.length; j++) {
			StringBuffer id = new StringBuffer("accessRight_").append(i).append("_").append(j);
			UserAccessT a = null;
			if (d != null) {
				a = AccessControlHelper.GetAccessRightInDomain(d, accessRights[j].getType().getValue());
			}
			if (a == null) {
				// System.out.println(accessRights[j].getType() + " not found" );
%>
<td><input type="checkbox" name="<%=id.toString() %>"/></td>
<%
			} else {
%>
<td><input type="checkbox" name="<%=id.toString() %>" checked="checked" /></td>
<%				
			}
		} // foreach accessRight
%>
</tr>
<%
	} // foreach domainTYpe
%>

<tr>
	<td class="center" colspan="<%=accessRights.length+1 %>">
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