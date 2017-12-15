<?xml version="1.0" encoding="ISO-8859-1" ?>
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<!-- OBSOLETED -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.bean.*"%>
<%@page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/alarm_menu.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">
<%

	AbstractResourceBundle rb = (AbstractResourceBundle)
	request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);
	
	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?").append(
			request.getQueryString());
	
	String fmIndex = request.getParameter(UrlHelper.FROM_COUNT);
	String toIndex = request.getParameter(UrlHelper.TO_COUNT);
	StringBuffer nextUrl = new StringBuffer(".." + request.getServletPath());
	StringBuffer prevUrl = new StringBuffer(nextUrl);
	
	MainAlarmTimeFilterBean filterBean = (MainAlarmTimeFilterBean) request
			.getSession().getAttribute(
			BeanConstants.MAIN_ALARM_FILTER_BEAN_ID);
	if (filterBean != null) {
		// filterBean.setBatch(request.getParameter("batch"));
		filterBean.setAttributes(request);
	}
%>

<div id="menuBar" class="menuBar"></div>
<div id="menuItem" class="menuItem">
<p id="pRefresh" class="menuItem"><a id="aRefresh"
	href="<%=refreshUrl.toString() %>" class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REFRESH) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1_nm"><%=rb.getString(ResourceKeys.K_ALARMS) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<%
	AlarmHelper aH = new AlarmHelper();
	int batchSize = filterBean.retBatch();
	int start = 1;
	int end = batchSize;
	int[] prevNextIdx = UrlHelper.DetermineBatchIndices(
			fmIndex, toIndex, batchSize);
	if (!(fmIndex == null && toIndex == null)) {
		start = UrlHelper.StringToInt(fmIndex);
		end = UrlHelper.StringToInt(toIndex);
	}
	ResultBatchT batch = new ResultBatchT(start, end);
	InputTimeT fromTime = new InputTimeT();
	InputTimeT toTime = new InputTimeT();
	QueryStateT[] qs = null;
	CurrentAlarmsRespT resp = aH.getCurrentAlarms(fromTime, toTime, batch, qs);
	CurrentAlarmT[] alms = resp.getAlarms();
%>
<form name="alarmClearForm" action="../app/alarm_clear.jsp" method="post" >
<table>
<tr>
<td><input type="submit" name="<%=UrlHelper.SELECT_ALL_NM %>" value="<%=rb.getString(ResourceKeys.K_SELECT_ALL) %>"/></td>
<td><input type="submit" name="<%=UrlHelper.UNSELECT_ALL_NM %>" value="<%=rb.getString(ResourceKeys.K_UNSELECT_ALL) %>"/></td>
<td><input type="submit" name="<%=UrlHelper.CLEAR_ALARM_NM %>" value="<%=rb.getString(ResourceKeys.K_CLEAR_ALARMS) %>"/></td>
</tr>
</table>
<table border="1">
<tr class="alarms_tr_th">
	<th><%=rb.getString(ResourceKeys.K_SELECT) %></th>
	<th><%=rb.getString(ResourceKeys.K_ALARM) %></th>
	<th><%=rb.getString(ResourceKeys.K_OBJECT) %></th>
	<th><%=rb.getString(ResourceKeys.K_TIME) %></th>
	<th><%=rb.getString(ResourceKeys.K_STATE) %></th>
	<th><%=rb.getString(ResourceKeys.K_CSR_PORTAL) %></th>
	<th><%=rb.getString(ResourceKeys.K_ALARM_DETAILS) %></th>
	<th><%=rb.getString(ResourceKeys.K_ALARM_HISTORY) %></th>
</tr>
<%
	if (alms != null) {
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
		for (int i = 0; i < alms.length; i++) {
			CurrentAlarmT alm = alms[i];
			CurrentJspAlarm cja = new CurrentJspAlarm(alm, df);
			cja.generateHtml();
			StringBuffer checkBoxName = new StringBuffer(UrlHelper.ALARM_TYPE);
			checkBoxName.append(alm.getAbstractAlarm().getAlarmId());
%>
	<tr class="alarms">
		<td><input type="checkbox" name="<%=checkBoxName.toString() %>"/></td>
		<td><%= cja.getAlarmType() %></td>
		<td><%= cja.getResourceNameAnchor() %></td>
		<td><%= cja.getAlarmTime() %></td>
		<td><%= cja.getAlarmState() %></td>
		<td><%= cja.getCsrPortalAnchor() %></td>
		<td><span><a href="<%= cja.getAlarmDetailsUrl() %>">D</a></span></td>
		<td><span><a href="<%= cja.getAlarmHistoryUrl() %>">H</a></span></td>
	</tr>
<%
		} // end foreach current alarm in array
		
		if (alms.length < batchSize) {
			prevNextIdx[2] = prevNextIdx[3] = -1;
		}
		
		// if (alms != null)
	} else {
		prevNextIdx[2] = prevNextIdx[3] = -1;
	}

	nextUrl.append("?").append(UrlHelper.FROM_COUNT)
	.append("=").append(prevNextIdx[2]).append("&")
	.append(UrlHelper.TO_COUNT).append("=")
	.append(prevNextIdx[3]);
	
	prevUrl.append("?").append(UrlHelper.FROM_COUNT)
	.append("=").append(prevNextIdx[0]).append("&")
	.append(UrlHelper.TO_COUNT).append("=")
	.append(prevNextIdx[1]);
	
%>
</table>
</form>
</div>

<div class="menuOffset">
<p>
<%
	if (prevNextIdx[0] > 0) {
%>
<a href="<%=prevUrl.toString() %>">&lt; <%=rb.getString(ResourceKeys.K_PREVIOUS) %></a>
<%
	}
%>
<%
	if (prevNextIdx[2] > 0) {
%>
<a href="<%=nextUrl.toString() %>"><%=rb.getString(ResourceKeys.K_NEXT) %> &gt;</a>
<%
	}
%>
</p>
</div>

</body>
</html>
