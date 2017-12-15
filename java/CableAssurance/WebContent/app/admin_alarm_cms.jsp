<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT"%>
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
	CmsAlarmConfigT ac = admHelper.getCmsAlarmConfig();
	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	SoakDurationListBox sd = SoakDurationListBox.getInstance();

	/*
	 * Fill the bean with values from the Service layer
	 */
	AlarmTypeConfigT atc = ac.getCmsLossOfComm();
    // atc = MtaAlarmConfigHelper.getAlarmTypeConfig(AppConfig.mta_unavail_alarm_nm, ac);
	String cms_loc_soak_win_1_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_1_StartTime());
	String cms_loc_soak_win_2_start_tm = hod.convertToOption(atc.getSoakWindow().getSoakWindow_2_StartTime());
	String cms_loc_soak_win_1_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_1_Duration());
	String cms_loc_soak_win_2_duration = sd.convertToOption(atc.getSoakWindow().getSoakWindow_2_Duration());
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_ALARM) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="adminAlarmCmsForm" action="../app/admin_alarm_cms_action.jsp" method="post">
<div>
<table>
<thead>
<tr>
	<td colspan="2" class="thead">
		<%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_LOC_ALARM) %>
	</td>
</tr>
</thead>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> - <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
	<td class="value_lg">
		<select name="cmsLocWin1SoakStartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(cms_loc_soak_win_1_start_tm)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> - <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
	<td class="value_lg">
		<select name="cmsLocWin1SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(cms_loc_soak_win_1_duration)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> - <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
	<td class="value_lg">
		<select name="cmsLocWin2SoakStartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(cms_loc_soak_win_2_start_tm)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_2) %> - <%=rb.getString(ResourceKeys.K_SOAK_DURATION) %></td>
	<td class="value_lg">
		<select name="cmsLocWin2SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(cms_loc_soak_win_2_duration)) {
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
	<td colspan="2" class="center"><input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="save"/></td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>