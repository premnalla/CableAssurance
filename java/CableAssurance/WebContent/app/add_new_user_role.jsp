<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	AdminHelper admHelper = new AdminHelper();

	StringBuffer missingDataUrl = new StringBuffer("../app/add_new_user_role_form_mf.jsp?");
	missingDataUrl.append(request.getQueryString());
	StringBuffer back = new StringBuffer("../app/admin_user_role.jsp");
%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewRoleFormBean"></jsp:useBean>
<jsp:setProperty name="BackBean" property="*"/>

<%
	if (BackBean.getCancel() != null) {
%>
<jsp:forward page="<%=back.toString() %>"></jsp:forward>
<%
	}
	if (!BackBean.validate()) {
%>
<jsp:forward page="<%=missingDataUrl.toString() %>"></jsp:forward>
<%		
	}
%>

<%
	/*
	 * Add the Role to the DB and return to previous page
	 */
	BackBean.getAttributes(request);
	RoleT u = new RoleT(BackBean.getRole());
	admHelper.addRole(u);
%>

<jsp:forward page="<%=back.toString() %>"></jsp:forward>

</body>
</html>