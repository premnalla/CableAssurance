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

<%
	/*
	 *
	 */
	AdminHelper admHelper = new AdminHelper();

%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.AdminMtaAlarmFormBean"></jsp:useBean>
<jsp:setProperty name="BackBean" property="*"/>

<% 
	/*
	 * Fill the Service layer with values from the bean & update backend
	 */
	if (true) {
		MtaAlarmConfigT mta = new MtaAlarmConfigT(BackBean);	
		admHelper.updateMtaAlarmConfig(mta);
%>
		<jsp:forward page="../app/admin_alarm_mta.jsp"></jsp:forward>
<%
	} else {
%>
<p>
		<%=BackBean.getMtaUnavailWin1SoakDuration()%>
		<%=BackBean.getMtaUnavailWin1SoakStartTime()%>
		<%=BackBean.getMtaUnavailWin2SoakDuration()%>
		<%=BackBean.getMtaUnavailWin2SoakStartTime()%>
		
		<%=BackBean.getMtaCmsLocWin1SoakDuration()%>
		<%=BackBean.getMtaCmsLocWin1SoakStartTime()%>
		<%=BackBean.getMtaCmsLocWin2SoakDuration()%>
		<%=BackBean.getMtaCmsLocWin2SoakStartTime()%>

		<%=BackBean.getMtaBattMissWin1SoakDuration()%>
		<%=BackBean.getMtaBattMissWin1SoakStartTime()%>
		<%=BackBean.getMtaBattMissWin2SoakDuration()%>
		<%=BackBean.getMtaBattMissWin2SoakStartTime()%>

		<%=BackBean.getMtaOnBattWin1SoakDuration()%>
		<%=BackBean.getMtaOnBattWin1SoakStartTime()%>
		<%=BackBean.getMtaOnBattWin2SoakDuration()%>
		<%=BackBean.getMtaOnBattWin2SoakStartTime()%>

		<%=BackBean.getMtaReplBattWin1SoakDuration()%>
		<%=BackBean.getMtaReplBattWin1SoakStartTime()%>
		<%=BackBean.getMtaReplBattWin2SoakDuration()%>
		<%=BackBean.getMtaReplBattWin2SoakStartTime()%>

		<%=BackBean.getMtaHwFailWin1SoakDuration()%>
		<%=BackBean.getMtaHwFailWin1SoakStartTime()%>
		<%=BackBean.getMtaHwFailWin2SoakDuration()%>
		<%=BackBean.getMtaHwFailWin2SoakStartTime()%>

		<%=BackBean.getMtaLcFailWin1SoakDuration()%>
		<%=BackBean.getMtaLcFailWin1SoakStartTime()%>
		<%=BackBean.getMtaLcFailWin2SoakDuration()%>
		<%=BackBean.getMtaLcFailWin2SoakStartTime()%>

</p>
<%
	}
%>

</body>
</html>