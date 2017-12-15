<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:directive.include file="../inc/htmlhead.jsp" />
<body class="default_background">
<%
LocalSystemHelper sh = new LocalSystemHelper();
LocalSystemT lst = sh.getLocalSystemType();

// new
// LocalSystemT[] syses = sh.getLocalSystems();
// end new

String rootPage;
String sysType = lst.getSystemType().toString();
if (sysType.equalsIgnoreCase(SystemTypeT._EnterpriseServer)) {
	rootPage = "../app/nav_root_enterprise.jsp";
} else if (sysType.equalsIgnoreCase(SystemTypeT._RegionServer)) {
	rootPage = "../app/nav_root_region.jsp";
} else if (sysType.equalsIgnoreCase(SystemTypeT._MarketServer)) {
	rootPage = "../app/nav_root_market.jsp";
} else if (sysType.equalsIgnoreCase(SystemTypeT._BladeServer)) {
	rootPage = "../app/nav_root_blade.jsp";
} else {
	rootPage = "/error_page.jsp";
}
%>
<jsp:forward page="<%=rootPage%>"></jsp:forward>
</body>
</html>