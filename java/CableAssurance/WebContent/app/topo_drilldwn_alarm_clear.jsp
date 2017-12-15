<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.palmyrasys.www.webservices.CableAssurance.AlarmIdT"%>
<%@page import="com.palmyrasyscorp.www.webservices.helper.AlarmHelper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body>

<%

	StringBuffer viewAlarmsUrl = new StringBuffer("../app/topo_drilldwn_alarms.jsp?");
	viewAlarmsUrl.append(request.getQueryString());

	StringBuffer selectAlarmsUrl = new StringBuffer("../app/topo_drilldwn_alarm_selected.jsp?");
	selectAlarmsUrl.append(request.getQueryString());
	
	GenericExtractionHelper genEx = new GenericExtractionHelper(request);
	genEx.setPrefix(UrlHelper.ALARM_PREFIX);
	List l = genEx.parseAttributes();
	String selectAlarmPressed = request.getParameter(UrlHelper.SELECT_ALL_NM);
	String clearAlarmPressed = request.getParameter(UrlHelper.CLEAR_ALARM_NM);	
	StringBuffer nextPage = viewAlarmsUrl;
	if (selectAlarmPressed != null) {
		nextPage = selectAlarmsUrl;
	}
	if (clearAlarmPressed != null) {
		if (l.size() > 0) {
			/*
			 * Clear selected alarms in the list
			 */
			// System.out.println("Start alarms to clear:");
			TopoAndIdExtractionHelper ex = new TopoAndIdExtractionHelper();
			List idList = ex.parseAttributes(l);
			AlarmIdT[] alarms = TopoKeyAndId.ConvertTopoAndIdToAlarmId(idList);
			AlarmHelper ah = new AlarmHelper();
			ah.clearAlarms(alarms);
			// System.out.println("End alarms to clear:");
		}
	}
%>

<jsp:forward page="<%=nextPage.toString() %>"></jsp:forward>

</body>
</html>
