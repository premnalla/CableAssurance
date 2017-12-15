<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
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

	String filter = request.getParameter(UrlHelper.FILTER_NM);
	String fmIndex = request.getParameter(UrlHelper.FROM_COUNT);
	String toIndex = request.getParameter(UrlHelper.TO_COUNT);
	StringBuffer nextUrl = new StringBuffer(".." + request.getServletPath());
	StringBuffer prevUrl = new StringBuffer(nextUrl);

	InputTimeT fromTime = new InputTimeT();
	InputTimeT toTime = new InputTimeT();

	ReportTimeFilterBean filterBean = (ReportTimeFilterBean) 
		request.getSession().getAttribute(BeanConstants.REPORT_FILTER_BEAN_ID);
	if (filterBean != null && filter != null) {
		// filterBean.setBatch(request.getParameter("batch"));
		filterBean.setAttributes(request);
		filterBean.setInputTime(fromTime, toTime);
	}

	StringBuffer reportsUrl = new StringBuffer(CAJspPages.JSP_TOPO_MKT_REPORTS);
%>

<div id="menuBar" class="menuBar"></div>

<div id="menuItem" class="menuItem">
<p id="pReports" class="menuItem"><a id="aReports"
	href="<%=reportsUrl.toString() %>" , class="menuItem" alt=""><%=rb.getString(ResourceKeys.K_REPORTS) %> <%=rb.getString(ResourceKeys.K_HOME) %></a></p>
</div>

<div class="menuOffset">
<div class="center">
<table>
<tr>
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_CM_TCA_LONGEST) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<div class="center">
<%
	ReportsHelper rh = new ReportsHelper();
	int batchSize = filterBean.retBatch();
	int start = 1;
	int end = batchSize;
	
	int[] prevNextIdx = UrlHelper.DetermineBatchIndices(
			fmIndex, toIndex, batchSize);
	
	if (fmIndex != null && toIndex != null) {
		start = UrlHelper.StringToInt(fmIndex);
		end = UrlHelper.StringToInt(toIndex);
	}
	
	ResultBatchT batch = new ResultBatchT(start, end);
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

%>
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
		} // foreach row

		if (results.length < batchSize) {
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
</div>
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