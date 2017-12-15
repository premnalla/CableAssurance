<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/main_menu.js" type="text/javascript"></script>
</head>
<body class="logo_background" onload="myInit();">

<div id="menuBar" class="logoMenuItem"></div>

<div id="menuItem" class="logoMenuItem">
<a id="aHome"
	href="../caservlet/HomeServlet" target="<%=UrlHelper.MAIN_FRAME %>"
	class="logoMenuItem" alt="">| Home</a>
<a id="aAlarms"
	href="../app/view_alarms_frm.jsp" target="<%=UrlHelper.ALARMS_VIEW_FRAME %>"
	class="logoMenuItem" alt="">| Alarms</a>
<a id="aReports"
	href="../app/view_reports.jsp" target="<%=UrlHelper.REPORTS_VIEW_FRAME %>"
	class="logoMenuItem" alt="">| Reports</a>
<a id="aCsrPortal"
	href="../app/csr_portal_input_frm.jsp" target="<%=UrlHelper.CSR_MAIN_FRAME %>"
	class="logoMenuItem" alt="">| CSR Portal</a>
<a id="aUserPref"
	href="../app/main_admin_frm.jsp" target="<%=UrlHelper.ADMIN_FRAME %>"
	class="logoMenuItem" alt="">| Administration</a>
<a id="aLogout"
	href="../caservlet/HomeServlet?op=logout" target="<%=UrlHelper.MAIN_FRAME %>"
	class="logoMenuItem" alt="">| Logout |</a>
</div>

</body>
</html>
