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
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.bean.*"%>
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

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?").append(
			request.getQueryString());
	
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
	if (filterBean == null) {
		// System.out.println("Not found filter bean");
		filterBean = new AlarmTimeFilterBean();
		request.getSession().
			setAttribute(BeanConstants.ALARM_FILTER_BEAN_ID, filterBean);
	}
	
	StringBuffer actionUrl = new StringBuffer("../app/view_alarms_filtered.jsp");
	if (alarmType != null) {
		actionUrl.append("?").append(UrlHelper.ALARM_TYPE).append("=")
		.append(alarmType);
	} else {
		alarmType = rb.getString(ResourceKeys.K_UNKNOWN);
	}
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

<div class="drpDwnMnuPlusFilterOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=alarmType %> <%=rb.getString(ResourceKeys.K_ALARMS) %></td>
</tr>
</table>
</div>
</div>

<div class="alarmQueryFilterForm">
<form name="alarmQueryFilterForm" action="../app/view_alarms_filtered.jsp" method="post">
<table>
	<tr class="xsmall">
		<td>From</td>
		<td><input type="text" size="1" name="<%=BeanConstants.FROM_MONTH %>" value="<%=filterBean.getFmMonth() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.FROM_DAY %>" value="<%=filterBean.getFmDay() %>" /></td>
		<td><input type="text" size="2" name="<%=BeanConstants.FROM_YEAR %>" value="<%=filterBean.getFmYear() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.FROM_HOUR %>" value="<%=filterBean.getFmHour() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.FROM_MINUTE %>" value="<%=filterBean.getFmMinute() %>" /></td>
		<td><%=rb.getString(ResourceKeys.K_ROWS) %><input type="text" size="2" name="<%=BeanConstants.BATCH %>" value="<%=filterBean.getBatch() %>" /></td>
	</tr>
	<tr class="xsmall">
		<td></td>
		<td><%=rb.getString(ResourceKeys.K_MONTH_SHORT) %></td>
		<td><%=rb.getString(ResourceKeys.K_DAY_SHORT) %></td>
		<td><%=rb.getString(ResourceKeys.K_YEAR_SHORT) %></td>
		<td><%=rb.getString(ResourceKeys.K_HOUR_SHORT) %></td>
		<td><%=rb.getString(ResourceKeys.K_MINUTE_SHORT) %></td>
	</tr>
	<tr class="xsmall">
		<td>To</td>
		<td><input type="text" size="1" name="<%=BeanConstants.TO_MONTH %>" value="<%=filterBean.getToMonth() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.TO_DAY %>" value="<%=filterBean.getToDay() %>" /></td>
		<td><input type="text" size="2" name="<%=BeanConstants.TO_YEAR %>" value="<%=filterBean.getToYear() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.TO_HOUR %>" value="<%=filterBean.getToHour() %>" /></td>
		<td><input type="text" size="1" name="<%=BeanConstants.TO_MINUTE %>" value="<%=filterBean.getToMinute() %>" /></td>
		<td><input type="submit" value="<%=rb.getString(ResourceKeys.K_CLEAR) %>" name="clear"/><input type="submit" value="<%=rb.getString(ResourceKeys.K_FILTER) %>" name="filter"/></td>
	</tr>
</table>
</form>
</div>

<div class="drpDwnMnuPlusFilterOffset">
<%
	AlarmHelper aH = new AlarmHelper();
	ResultBatchT batch = new ResultBatchT(0, filterBean.retBatch()-1);
	InputTimeT fromTime = new InputTimeT();
	InputTimeT toTime = new InputTimeT();
	QueryStateT[] qs = null;
	CurrentAlarmsRespT resp = aH.getCurrentAlarmsByType(alarmType, 
			null, fromTime, toTime, batch, qs);
	CurrentAlarmT[] alms = resp.getAlarms();
	if (alms != null) {
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
	%>
</table>
<%
	} // if (alms != null)
%>
</form>
</div>

</body>
</html>
