<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@page import="com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton"%>
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

	if (!LocalSystemSingleton.getInstance().isEnterprise()) {
%>
<jsp:forward page="../app/topo_region_not_supp.jsp"></jsp:forward>
<%
	} // end if
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<div class="center">
</div>

</body>
</html>