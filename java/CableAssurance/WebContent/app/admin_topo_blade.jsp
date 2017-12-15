<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	if (LocalSystemSingleton.getInstance().isMarket()) {
%>
<jsp:forward page="../app/admin_topo_mkt_blade.jsp"></jsp:forward>
<%
	} else if (LocalSystemSingleton.getInstance().isBlade()) {
%>
<jsp:forward page="../app/admin_topo_bld_blade.jsp"></jsp:forward>
<%
	} else {
%>
<jsp:forward page="../app/topo_blade_not_supp.jsp"></jsp:forward>
<%
	} // end if
%>

</body>
</html>