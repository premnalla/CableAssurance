<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT"%>
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
	HfcAlarmConfigT ac = admHelper.getHfcAlarmConfig();
	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	SoakDurationListBox sd = SoakDurationListBox.getInstance();
	DetectionWindowListBox dw = DetectionWindowListBox.getInstance();
%>

<jsp:useBean id="BackBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.AdminHfcAlarmFormBean"></jsp:useBean>

<% 
	/*
	 * Fill the bean with values from the Service layer
	 */
	BackBean.setCmThreshold1(ac.getCmThresold().getPercentCmOffline_1());
	BackBean.setCmThreshold1Cm(ac.getCmThresold().getMaxCm_1());
	BackBean.setCmThreshold2(ac.getCmThresold().getPercentCmOffline_2());
	BackBean.setCmThreshold2Cm(ac.getCmThresold().getMaxCm_2());
	String cmDetectionWindow = dw.convertToOption(ac.getCmThresold().getDetectionWindow());
	String cmThresholdWin1StartTime = hod.convertToOption(ac.getCmSoakWindow().getSoakWindow_1_StartTime());
	String cmThresholdWin2StartTime = hod.convertToOption(ac.getCmSoakWindow().getSoakWindow_2_StartTime());
	String cmThresholdWin1SoakDuration = sd.convertToOption(ac.getCmSoakWindow().getSoakWindow_1_Duration());
	String cmThresholdWin2SoakDuration = sd.convertToOption(ac.getCmSoakWindow().getSoakWindow_2_Duration());
	
	BackBean.setMtaThreshold1(ac.getMtaThresold().getMtaThresholdCount());
	String mtaDetectionWindow = dw.convertToOption(ac.getMtaThresold().getDetectionWindow());
	String mtaThresholdWin1StartTime = hod.convertToOption(ac.getMtaSoakWindow().getSoakWindow_1_StartTime());
	String mtaThresholdWin2StartTime = hod.convertToOption(ac.getMtaSoakWindow().getSoakWindow_2_StartTime());
	String mtaThresholdWin1SoakDuration = sd.convertToOption(ac.getMtaSoakWindow().getSoakWindow_1_Duration());
	String mtaThresholdWin2SoakDuration = sd.convertToOption(ac.getMtaSoakWindow().getSoakWindow_2_Duration());

	BackBean.setMtaPowerThreshold1(ac.getPowerThresold().getThresholdCount());
	String mtaPwrDetectionWindow = dw.convertToOption(ac.getPowerThresold().getDetectionWindow());
	String mtaPwrThresholdWin1StartTime = hod.convertToOption(ac.getPowerSoakWindow().getSoakWindow_1_StartTime());
	String mtaPwrThresholdWin2StartTime = hod.convertToOption(ac.getPowerSoakWindow().getSoakWindow_2_StartTime());
	String mtaPwrThresholdWin1SoakDuration = sd.convertToOption(ac.getPowerSoakWindow().getSoakWindow_1_Duration());
	String mtaPwrThresholdWin2SoakDuration = sd.convertToOption(ac.getPowerSoakWindow().getSoakWindow_2_Duration());
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_ALARM) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="adminAlarmHfcForm" action="../app/admin_alarm_hfc_action.jsp" method="post">
<div>
<table>
<tr>
	<td colspan="2" class="thead">
		<%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_CM_OFFLINE_THRESHOLD) %>
	</td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_CM_PERCENT_THRESH_1) %> (%):</td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getCmThreshold1() %>" name="cmThreshold1" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_CM_PERCENT_THRESH_1_CM) %></td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getCmThreshold1Cm() %>" name="cmThreshold1Cm" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_CM_PERCENT_THRESH_2) %> (%):</td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getCmThreshold2() %>" name="cmThreshold2" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_CM_PERCENT_THRESH_2_CM) %></td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getCmThreshold2Cm() %>" name="cmThreshold2Cm" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_ALARM_DETECT_WIN) %></td>
	<td class="value_lg">
		<select name="cmDetectionWindow">
		<%
			for (int i=0; i<dw.getOptionList().size(); i++) {
				String s = (String) dw.getOptionList().get(i);
				if (s.equals(cmDetectionWindow)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
	<td class="value_lg">
		<select name="cmSoakWindow1StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(cmThresholdWin1StartTime)) {
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
		<select name="cmWindow1SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(cmThresholdWin1SoakDuration)) {
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
		<select name="cmSoakWindow2StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(cmThresholdWin2StartTime)) {
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
		<select name="cmWindow2SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(cmThresholdWin2SoakDuration)) {
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
</div>

<div>
<br/>
</div>

<div>
<table>
<tr>
	<td colspan="2" class="thead">
		<%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_MTA_UNAVAIL_THRESHOLD) %>
	</td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_MTA_COUNT) %></td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getMtaThreshold1() %>" name="mtaThreshold1" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_ALARM_DETECT_WIN) %></td>
	<td class="value_lg">
		<select name="mtaDetectionWindow">
		<%
			for (int i=0; i<dw.getOptionList().size(); i++) {
				String s = (String) dw.getOptionList().get(i);
				if (s.equals(mtaDetectionWindow)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
	<td class="value_lg">
		<select name="mtaSoakWindow1StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(mtaThresholdWin1StartTime)) {
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
		<select name="mtaWindow1SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(mtaThresholdWin1SoakDuration)) {
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
		<select name="mtaSoakWindow2StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(mtaThresholdWin2StartTime)) {
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
		<select name="mtaWindow2SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(mtaThresholdWin2SoakDuration)) {
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
</div>

<div>
<br/>
</div>

<div>
<table>
<tr>
	<td colspan="2" class="thead">
		<%=rb.getString(ResourceKeys.K_HFC) %> <%=rb.getString(ResourceKeys.K_MTA_POWER_THRESHOLD) %>
	</td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_MTA_COUNT_ON_BATTERY) %></td>
	<td class="value_lg"><input type="text" size="2" value="<%=BackBean.getMtaPowerThreshold1() %>" name="mtaPowerThreshold1" /></td>
</tr>
<tr>
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_ALARM_DETECT_WIN) %></td>
	<td class="value_lg">
		<select name="mtaPowerDetectionWindow">
		<%
			for (int i=0; i<dw.getOptionList().size(); i++) {
				String s = (String) dw.getOptionList().get(i);
				if (s.equals(mtaPwrDetectionWindow)) {
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
	<td class="name_lg"><%=rb.getString(ResourceKeys.K_DETECTION_WIN_1) %> <%=rb.getString(ResourceKeys.K_START_TIME) %></td>
	<td class="value_lg">
		<select name="mtaPowerSoakWindow1StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(mtaPwrThresholdWin1StartTime)) {
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
		<select name="mtaPowerWindow1SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(mtaPwrThresholdWin1SoakDuration)) {
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
		<select name="mtaPowerSoakWindow2StartTime">
		<%
			for (int i=0; i<hod.getOptionList().size(); i++) {
				String s = (String) hod.getOptionList().get(i);
				if (s.equals(mtaPwrThresholdWin2StartTime)) {
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
		<select name="mtaPowerWindow2SoakDuration">
		<%
			for (int i=0; i<sd.getOptionList().size(); i++) {
				String s = (String) sd.getOptionList().get(i);
				if (s.equals(mtaPwrThresholdWin2SoakDuration)) {
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
</div>

<div>
<br/>
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