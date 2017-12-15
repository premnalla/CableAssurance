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
<script src="../js/net_obj_menu.js" type="text/javascript"></script>
</head>
<body class="default_background">
<%
LocalSystemHelper sh = new LocalSystemHelper();
LocalSystemT lst = sh.getLocalSystemType();

String rootPage = "../app/empty_page.jsp";
String sysType = lst.getSystemType().toString();
if (sysType.equalsIgnoreCase(SystemTypeT._MarketServer)) {
	rootPage = "../app/topo_root_market.jsp";
} else if (sysType.equalsIgnoreCase(SystemTypeT._BladeServer)) {
	rootPage = "../app/topo_root_blade.jsp";
}
%>
<jsp:forward page="<%=rootPage%>"></jsp:forward>

</body>
</html>