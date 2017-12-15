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
<%@ page import="java.text.*"%>
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
<title>CableAssurance - Alarms</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/view_alarms_menu.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	String alarmType = request.getParameter(UrlHelper.ALARM_TYPE);
	String clear = request.getParameter(UrlHelper.CLEAR_NM);
	String filter = request.getParameter(UrlHelper.FILTER_NM);

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?").append(
			request.getQueryString());
	
	String fm = request.getParameter(UrlHelper.FROM_COUNT);
	String to = request.getParameter(UrlHelper.TO_COUNT);
	StringBuffer nextUrl = new StringBuffer(".." + request.getServletPath());
	StringBuffer prevUrl = new StringBuffer(nextUrl);
	
	String baseUrl = "../app/view_alarms_by_type.jsp?";
	
	StringBuffer hfcUrl = new StringBuffer(baseUrl);
	hfcUrl.append(UrlHelper.ALARM_TYPE).append("=HFC");
	
	StringBuffer mtaUrl = new StringBuffer(baseUrl);
	mtaUrl.append(UrlHelper.ALARM_TYPE).append("=MTA");
	
	StringBuffer cmsUrl = new StringBuffer(baseUrl);
	cmsUrl.append(UrlHelper.ALARM_TYPE).append("=CMS");
	
	StringBuffer cmtsUrl = new StringBuffer(baseUrl);
	cmtsUrl.append(UrlHelper.ALARM_TYPE).append("=CMTS");
	
	AlarmTimeFilterBean filterBean = (AlarmTimeFilterBean) 
		request.getSession().getAttribute(BeanConstants.ALARM_FILTER_BEAN_ID);
	if (filterBean != null && filter != null) {
		// filterBean.setBatch(request.getParameter("batch"));
		filterBean.setAttributes(request);
	}
	
	if (clear != null && filterBean != null) {
		request.removeAttribute(BeanConstants.ALARM_FILTER_BEAN_ID);
%>
<jsp:forward page="../app/view_alarms.jsp"></jsp:forward>
<%
	} // end if
%>

<div id="menuBar" class="imp_menuBar"></div>
<div id="Refresh" class="imp_menuTrigger"
	onmouseout="this.style.color='';"
	onmouseover="this.style.color='yellow';"><a href="<%=refreshUrl.toString() %>" ><%=rb.getString(ResourceKeys.K_REFRESH) %> </a></div>
<div id="Alarms" class="imp_menuTrigger"
	onmouseout="hideMenu('mnuAlarms');this.style.color='white';"
	onmouseover="showMenu('mnuAlarms'); this.style.color='yellow';">| <%=rb.getString(ResourceKeys.K_ALARMS) %></div>
<!-- 
<div id="TCAs" class="imp_menuTrigger"
	onmouseout="this.style.color='white';"
	onmouseover="this.style.color='yellow';">| TCA's</div>
-->
<div id="mnuAlarms" class="imp_menu" onmouseout="hideMenu(mnuSelected)"
	onmouseover="showMenu(mnuSelected)">
<p onmouseover="this.style.backgroundColor='red'"
	onmouseout="this.style.backgroundColor=''"><a href="<%=hfcUrl.toString() %>"><%=rb.getString(ResourceKeys.K_HFC) %></a></p>
<p onmouseover="this.style.backgroundColor='red'"
	onmouseout="this.style.backgroundColor=''"><a href="<%=mtaUrl.toString() %>"><%=rb.getString(ResourceKeys.K_MTA) %></a></p>
<p onmouseover="this.style.backgroundColor='red'"
	onmouseout="this.style.backgroundColor=''"><a href="<%=cmsUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CMS) %></a></p>
<p onmouseover="this.style.backgroundColor='red'"
	onmouseout="this.style.backgroundColor=''"><a href="<%=cmtsUrl.toString() %>"><%=rb.getString(ResourceKeys.K_CMTS) %></a></p>
<p onmouseover="this.style.backgroundColor='red'"
	onmouseout="this.style.backgroundColor=''"><a href="../app/view_alarms.jsp"><%=rb.getString(ResourceKeys.K_ALL) %></a></p>
</div>

<div class="dropDownMenuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_ALARMS) %></td>
</tr>
</table>
</div>
</div>

<div class="dropDownMenuOffset">
<%
	AlarmHelper aH = new AlarmHelper();
	int batchSize = filterBean.retBatch();
	int fmIndex = 0;
	int toIndex = batchSize - 1;
	int[] prevNextIdx = UrlHelper.DetermineBatchIndices(
			fm, to, batchSize);
	if (fm != null && to != null) {
		fmIndex = UrlHelper.StringToInt(fm);
		toIndex = UrlHelper.StringToInt(to);
	}
	ResultBatchT batch = new ResultBatchT(fmIndex, toIndex);
	InputTimeT fromTime = new InputTimeT();
	InputTimeT toTime = new InputTimeT();
	// StringBuffer s = new StringBuffer();
	// s.append("[0]:").append(prevNextIdx[0]).append(";");
	// s.append("[1]:").append(prevNextIdx[1]).append(";");
	// s.append("[2]:").append(prevNextIdx[2]).append(";");
	// s.append("[3]:").append(prevNextIdx[3]).append(";");
	// System.out.println(s);

	QueryStateT[] qs = null;
	SessionQueryStates sessionQs = (SessionQueryStates) 
		request.getSession().getAttribute(BeanConstants.QUERY_STATE_OBJECT_ID);
	if (fmIndex != 0 && sessionQs != null) {
		qs = sessionQs.getViewAlarmsQs();
	}
	
	CurrentAlarmsRespT resp = null;
	CurrentAlarmT[] alms = null;
	
	if (alarmType == null) {
		resp = aH.getCurrentAlarms(fromTime, toTime, batch, qs);
	} else {
		resp = aH.getCurrentAlarmsByType(alarmType, 
				null, fromTime, toTime, batch, qs);
	}
	
	if (resp != null) {
		alms = resp.getAlarms();
		if (sessionQs != null) {
			sessionQs.setViewAlarmsQs(resp.getQueryState());
		} else {
			// first time ever, create and store obj in session
			sessionQs = new SessionQueryStates();
			sessionQs.setViewAlarmsQs(resp.getQueryState());
			request.getSession().setAttribute(BeanConstants.QUERY_STATE_OBJECT_ID,
					sessionQs);
		}
	}

%>
<form name="viewAlarmClearForm" action="../app/view_alarm_clear.jsp" method="post" >
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
			AlarmViewJspAlarm cja = new AlarmViewJspAlarm(alm, df);
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
	
	if (alarmType != null) {
		nextUrl.append("&").append(UrlHelper.ALARM_TYPE)
			.append("=").append(alarmType);
		prevUrl.append("&").append(UrlHelper.ALARM_TYPE)
			.append("=").append(alarmType);
	}
%>
</table>
</form>
</div>

<div class="dropDownMenuOffset">
<p>
<%
	if (prevNextIdx[0] >= 0) {
%>
<a href="<%=prevUrl.toString() %>">&lt; <%=rb.getString(ResourceKeys.K_PREVIOUS) %></a>
<%
	}
%>
<%
	if (prevNextIdx[2] >= 0) {
%>
<a href="<%=nextUrl.toString() %>"><%=rb.getString(ResourceKeys.K_NEXT) %> &gt;</a>
<%
	}
%>
</p>
</div>

</body>
</html>
