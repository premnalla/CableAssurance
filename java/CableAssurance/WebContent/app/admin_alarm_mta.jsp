<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT"%>
<%@page import="com.palmyrasyscorp.db.tables.AppConfig"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	/*
	 * Get default values thru the Service layer
	 */
	AdminHelper admHelper = new AdminHelper();
	MtaAlarmConfigT ac = admHelper.getMtaAlarmConfig();
	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	SoakDurationListBox sd = SoakDurationListBox.getInstance();
%>

<% 
	/*
	 * Fill the bean with values from the Service layer
	 */
	AlarmTypeConfigT atc;
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_unavail_alarm_nm, ac);
	String mta_unavail_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_unavail_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_unavail_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_unavail_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_onbatt_alarm_nm, ac);
	String mta_onbatt_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_onbatt_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_onbatt_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_onbatt_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_battmiss_alarm_nm, ac);
	String mta_battmiss_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_battmiss_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_battmiss_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_battmiss_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_replbatt_alarm_nm, ac);
	String mta_replbatt_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_replbatt_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_replbatt_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_replbatt_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_cmsloc_alarm_nm, ac);
	String mta_cmsloc_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_cmsloc_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_cmsloc_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_cmsloc_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_hwfail_alarm_nm, ac);
	String mta_hwfail_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_hwfail_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_hwfail_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_hwfail_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
	///
    atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_lcfail_alarm_nm, ac);
	String mta_lcfail_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String mta_lcfail_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String mta_lcfail_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String mta_lcfail_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
%>

<div class="center">
<table>
<tr>
	<td class="ca_h2"><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_ALARM) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="adminAlarmHfcForm" action="../app/admin_alarm_mta_action.jsp" method="post">
<div>
<table>
<tr class="grid">
	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_UNAVAIL_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaUnavailWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_unavail_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaUnavailWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_unavail_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaUnavailWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_unavail_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaUnavailWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_unavail_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
	
	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_ON_BATT_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaOnBattWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_onbatt_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaOnBattWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_onbatt_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaOnBattWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_onbatt_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaOnBattWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_onbatt_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
	
	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_REPLACE_BATT_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaReplBattWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_replbatt_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaReplBattWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_replbatt_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaReplBattWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_replbatt_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaReplBattWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_replbatt_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
	
</tr>

<tr class="grid">

	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_BATT_MISS_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaBattMissWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_battmiss_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaBattMissWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_battmiss_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaBattMissWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_battmiss_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaBattMissWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_battmiss_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>

	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_CMS_LOC_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaCmsLocWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_cmsloc_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaCmsLocWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_cmsloc_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaCmsLocWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_cmsloc_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaCmsLocWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_cmsloc_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
	
	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_HW_FAIL_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaHwFailWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_hwfail_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaHwFailWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_hwfail_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaHwFailWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_hwfail_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaHwFailWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_hwfail_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
	
</tr>

<tr class="grid">
	<td>
	<table ><thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_MTA_LC_FAIL_ALARM) %></td></tr></thead>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaLcFailWin1SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_lcfail_soak_win_1_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaLcFailWin1SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_lcfail_soak_win_1_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
			<td class="value_lg">
				<select name="mtaLcFailWin2SoakStartTime">
				<%
					for (int i=0; i<hod.getOptionList().size(); i++) {
						String s = (String) hod.getOptionList().get(i);
						if (s.equals(mta_lcfail_soak_win_2_start_tm)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
			<td class="value_lg">
				<select name="mtaLcFailWin2SoakDuration">
				<%
					for (int i=0; i<sd.getOptionList().size(); i++) {
						String s = (String) sd.getOptionList().get(i);
						if (s.equals(mta_lcfail_soak_win_2_duration)) {
				%>
					<option value="<%=s %>" selected="selected"><%=s %></option>
				<%
						} else {
				%>
					<option value="<%=s %>"><%=s %></option>
				<%
						}
				%>
				<%
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	</td>
</tr>

</table>
</div>

<div>
<table>
<tr>
	<td><input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="save"/></td>
</tr>
</table>
</div>

</form>
</div>

</body>
</html>