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
	HfcAlarmConfigT hfc = new HfcAlarmConfigT();
	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	SoakDurationListBox sd = SoakDurationListBox.getInstance();
	DetectionWindowListBox dw = DetectionWindowListBox.getInstance();

%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.AdminHfcAlarmFormBean"></jsp:useBean>
<jsp:setProperty name="BackBean" property="*"/>

<% 
	/*
	 * Fill the Service layer with values from the bean & update backend
	 */
	AggregateCmOfflineTresholdT cm;
	AggregateMtaTresholdT mta;
	HfcPowerTresholdT power;
	SoakWindowT s;
	
	cm = new AggregateCmOfflineTresholdT();
	hfc.setCmThresold(cm);
	cm.setMaxCm_1(BackBean.getCmThreshold1Cm());
	cm.setMaxCm_2(BackBean.getCmThreshold2Cm());
	cm.setPercentCmOffline_1(BackBean.getCmThreshold1());
	cm.setPercentCmOffline_2(BackBean.getCmThreshold2());
	cm.setDetectionWindow(dw.convertToStrSeconds(BackBean.getCmDetectionWindow()));
	s = new SoakWindowT();
	hfc.setCmSoakWindow(s);
	s.setSoakWindow_1_StartTime(hod.convertToStrHour(BackBean.getCmSoakWindow1StartTime()));
	s.setSoakWindow_1_Duration(sd.convertToStrSeconds(BackBean.getCmWindow1SoakDuration()));
	s.setSoakWindow_2_StartTime(hod.convertToStrHour(BackBean.getCmSoakWindow2StartTime()));
	s.setSoakWindow_2_Duration(sd.convertToStrSeconds(BackBean.getCmWindow2SoakDuration()));
	
	mta = new AggregateMtaTresholdT();
	hfc.setMtaThresold(mta);
	mta.setDetectionWindow(dw.convertToStrSeconds(BackBean.getMtaDetectionWindow()));
	mta.setMtaThresholdCount(BackBean.getMtaThreshold1());
	s = new SoakWindowT();
	hfc.setMtaSoakWindow(s);
	s.setSoakWindow_1_StartTime(hod.convertToStrHour(BackBean.getMtaSoakWindow1StartTime()));
	s.setSoakWindow_1_Duration(sd.convertToStrSeconds(BackBean.getMtaWindow1SoakDuration()));
	s.setSoakWindow_2_StartTime(hod.convertToStrHour(BackBean.getMtaSoakWindow2StartTime()));
	s.setSoakWindow_2_Duration(sd.convertToStrSeconds(BackBean.getMtaWindow2SoakDuration()));
		
	power = new HfcPowerTresholdT();
	s = new SoakWindowT();
	hfc.setPowerThresold(power);
	hfc.setPowerSoakWindow(s);
	power.setDetectionWindow(dw.convertToStrSeconds(BackBean.getMtaPowerDetectionWindow()));
	power.setThresholdCount(BackBean.getMtaPowerThreshold1());
	s.setSoakWindow_1_StartTime(hod.convertToStrHour(BackBean.getMtaPowerSoakWindow1StartTime()));
	s.setSoakWindow_1_Duration(sd.convertToStrSeconds(BackBean.getMtaPowerWindow1SoakDuration()));
	s.setSoakWindow_2_StartTime(hod.convertToStrHour(BackBean.getMtaPowerSoakWindow2StartTime()));
	s.setSoakWindow_2_Duration(sd.convertToStrSeconds(BackBean.getMtaPowerWindow2SoakDuration()));
	
	admHelper.updateHfcAlarmConfig(hfc);
%>

<jsp:forward page="../app/admin_alarm_hfc.jsp"></jsp:forward>

</body>
</html>