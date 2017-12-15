<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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
	/*
	 *
	 */
	AdminHelper admHelper = new AdminHelper();
	CmtsAlarmConfigT cmts = new CmtsAlarmConfigT();
	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	SoakDurationListBox sd = SoakDurationListBox.getInstance();

%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.AdminCmtsAlarmFormBean"></jsp:useBean>
<jsp:setProperty name="BackBean" property="*"/>

<% 
	/*
	 * Fill the Service layer with values from the bean & update backend
	 */
	SoakWindowT s;
	AlarmTypeConfigT atc = new AlarmTypeConfigT();	
	s = new SoakWindowT();
	atc.setSoakWindow(s);
	atc.setAlarmType("foo");
	cmts.setCmtsDown(atc);
	s.setSoakWindow_1_StartTime(hod.convertToStrHour(BackBean.getCmtsCommsFailWin1SoakStartTime()));
	s.setSoakWindow_1_Duration(sd.convertToStrSeconds(BackBean.getCmtsCommsFailWin1SoakDuration()));
	s.setSoakWindow_2_StartTime(hod.convertToStrHour(BackBean.getCmtsCommsFailWin2SoakStartTime()));
	s.setSoakWindow_2_Duration(sd.convertToStrSeconds(BackBean.getCmtsCommsFailWin2SoakDuration()));
		
	admHelper.updateCmtsAlarmConfig(cmts);
%>

<jsp:forward page="../app/admin_alarm_cmts.jsp"></jsp:forward>

</body>
</html>