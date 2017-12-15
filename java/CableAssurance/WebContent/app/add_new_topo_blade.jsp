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
	StringBuffer missingDataUrl = new StringBuffer("../app/add_new_topo_blade_form_mf.jsp?");
	missingDataUrl.append(request.getQueryString());
	StringBuffer back = new StringBuffer("../app/admin_topo_blade.jsp");
%>

<jsp:useBean id="BladeBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.BladeFormBean"></jsp:useBean>
<jsp:setProperty name="BladeBean" property="*"/>

<%
	if (BladeBean.getCancel() != null) {
%>
<jsp:forward page="<%=back.toString() %>"></jsp:forward>
<%
	}
	if (!BladeBean.validate()) {
%>
<jsp:forward page="<%=missingDataUrl.toString() %>"></jsp:forward>
<%		
	}
%>

<%
	/*
	 * Add the Blade to the DB and return to previous page
	 */
	BladeT bld = new BladeT();
	bld.setName(BladeBean.getName());
	bld.setHost(BladeBean.getHost());
	AdminHelper ah = new AdminHelper();
	ah.addBlade(bld);	
%>

<jsp:forward page="<%=back.toString() %>"></jsp:forward>

</body>
</html>