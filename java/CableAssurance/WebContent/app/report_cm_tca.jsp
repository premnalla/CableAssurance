<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.bean.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
<script src="../js/net_obj_menu_reports.js" type="text/javascript"></script>
</head>
<body class="default_background" onload="myInit();">

<%
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);
	String resId = request.getParameter(UrlHelper.RESOURCE_ID);
	String qryType = request.getParameter(UrlHelper.TYPE);
	
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	BigInteger rId = new BigInteger(resId);

	StringBuffer refreshUrl = new StringBuffer("..");
	refreshUrl.append(request.getServletPath()).append("?")
		.append(request.getQueryString());
	
	StringBuffer actionUrl = new StringBuffer(CAJspPages.JSP_REPORT_CM_TCA_FILT);
	actionUrl.append("?").append(request.getQueryString());

	ReportTimeFilterBean filterBean = (ReportTimeFilterBean) 
		request.getSession().getAttribute(BeanConstants.REPORT_FILTER_BEAN_ID);
	if (filterBean == null) {
		// System.out.println("Not found filter bean");
		filterBean = new ReportTimeFilterBean();
		request.getSession().
			setAttribute(BeanConstants.REPORT_FILTER_BEAN_ID, filterBean);
	}

	StringBuffer reportsUrl = new StringBuffer(CAJspPages.JSP_TOPO_MKT_REPORTS);
%>

<div id="menuBar" class="menuBar"></div>

<div id="menuItem" class="menuItem">
<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" , class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REPORTS) %> <%=rb.getString(ResourceKeys.K_HOME) %></a></p>
</div>

<div class="menuOffset">
<div class="left">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CM_TCA_LONGEST) %></td>
</tr>
</table>
</div>
</div>

<div class="alarmQueryFilterForm">
<form name="reportMtaOosQryFilterForm" action="<%=actionUrl.toString() %>" method="post">
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

<div class="menuPlusFilterOffset">
<div class="center">
<table border="1">
<tr class="alarms_tr_th">
	<th><%=rb.getString(ResourceKeys.K_CM) %></th>
	<th><%=rb.getString(ResourceKeys.K_SUM_TCA_DURATION) %></th>
	<th><%=rb.getString(ResourceKeys.K_SUM_TCA_FREE_DURATION) %></th>
	<th><%=rb.getString(ResourceKeys.K_SUM_STATE_CHANGES) %></th>
	<th><%=rb.getString(ResourceKeys.K_HFC) %></th>
	<th><%=rb.getString(ResourceKeys.K_CMTS) %></th>
</tr>
<%
	ResultBatchT batch = new ResultBatchT(0, /* filterBean.retBatch() */ 50);
	InputTimeT fromTime = new InputTimeT();
	InputTimeT toTime = new InputTimeT();
	ReportsHelper rh = new ReportsHelper();
	QueryStateT[] qrySt = null;
	CmStatusSummaryRespT result;
	if (qryType.compareTo(UrlHelper.REPORT_TYPE_ST_DURATION)==0) {
		result = 
			rh.getCmTcaStatusSummary1(tK, null, null, fromTime, toTime, batch, qrySt);		
	} else if (qryType.compareTo(UrlHelper.REPORT_TYPE_ST_FREQUENCY)==0) {
		result = 
			rh.getCmTcaStatusSummary2(tK, null, null, fromTime, toTime, batch, qrySt);				
	} else {
		result = null;
	}
	CmStatusSummaryT[] results;
	if (result != null) {
		results = result.getCmData();
	} else {
		results = null;		
	}
	if (results != null) {
		StringBuffer mta = new StringBuffer(CAJspPages.JSP_TOPO_CM_PLUS_PLOTS);
		StringBuffer hfc = new StringBuffer(CAJspPages.JSP_TOPO_HFC);
		StringBuffer cmts = new StringBuffer(CAJspPages.JSP_TOPO_CMTS);
		for (int i=0; i<results.length; i++) {
			CmStatusSummaryT r = results[i];
			String redDur = TimeHelper.secondsToHMS(r.getStatusSummary().getSumRedStatusTime());
			String greenDur = TimeHelper.secondsToHMS(r.getStatusSummary().getSumGreenStatusTime());
			
			StringBuffer mtaUrl = new StringBuffer(mta);
			UrlHelper.AppendTopoHierarchyIDs(mtaUrl, tK);
			mtaUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(r.getStatusSummary().getResId());

			StringBuffer hfcUrl = new StringBuffer(hfc);
			UrlHelper.AppendTopoHierarchyIDs(hfcUrl, tK);
			hfcUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(r.getHfcResId());

			StringBuffer cmtsUrl = new StringBuffer(cmts);
			UrlHelper.AppendTopoHierarchyIDs(cmtsUrl, tK);
			cmtsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(r.getCmtsResId());

%>
<tr class="alarms">
	<td><span><a href="<%=mtaUrl.toString() %>"><%=r.getStatusSummary().getName() %></a></span></td>
	<td><%=redDur %></td>
	<td><%=greenDur %></td>
	<td><%=r.getStatusSummary().getSumStateChanges() %></td>
	<td><span><a href="<%=hfcUrl.toString() %>"><%=r.getHfcName() %></a></span></td>
	<td><span><a href="<%=cmtsUrl.toString() %>"><%=r.getCmtsName() %></a></span></td>
</tr>
<%
		}		
	}
%>
</table>
</div>
</div>

</body>
</html>