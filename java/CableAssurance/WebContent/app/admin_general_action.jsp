<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	/*
	 *
	 */
	AdminHelper admHelper = new AdminHelper();
	PollingIntervalsT pi = new PollingIntervalsT();
	PollingIntervalListBox lb = PollingIntervalListBox.getInstance();
%>

<jsp:useBean id="GenAdminBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.GeneralAdminFormBean"></jsp:useBean>
<jsp:setProperty name="GenAdminBean" property="*"/>

<% 
	/*
	 * Fill the Service layer with values from the bean & update backend
	 */
	pi.setCmtsPollInterval(lb.convertToStrSeconds(GenAdminBean.getCmtsPollInterval()));
	pi.setCmPollInterval(lb.convertToStrSeconds(GenAdminBean.getCmPollInterval()));
	pi.setMtaPollInterval(lb.convertToStrSeconds(GenAdminBean.getMtaPollInterval()));
	pi.setMtaPingInterval(lb.convertToStrSeconds(GenAdminBean.getMtaPingInterval()));
	admHelper.updatePollingIntervals(pi);
%>

<jsp:forward page="../app/admin_general.jsp"></jsp:forward>

</body>
</html>