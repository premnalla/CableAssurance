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

<jsp:useBean id="LSBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.LocalSystemFormBean"></jsp:useBean>
<jsp:setProperty name="LSBean" property="*"/>

<% 
	/*
	 * Fill the Service layer with values from the bean & update backend
	 */
	AdminHelper admHelper = new AdminHelper();
	LocalSystemT ls = new LocalSystemT();
	ls.setSystemName(LSBean.getSystemName());
	ls.setSystemType(LocalSystemHelper.getSystemType(LSBean.getSystemType()));
	admHelper.updateLocalSystem(ls);
%>

<jsp:forward page="../app/admin_topo_local_sys.jsp"></jsp:forward>

</body>
</html>