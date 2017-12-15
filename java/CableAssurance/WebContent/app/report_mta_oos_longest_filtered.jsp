<?xml version="1.0" encoding="ISO-8859-1" ?>
<!-- OBSOLETED by report_mta_status_filtered.jsp -->
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

	StringBuffer reportsUrl = new StringBuffer("../app/topo_drilldwn_mkt_reports.jsp");
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
<td class="ca_h1"><%=rb.getString(ResourceKeys.K_MTA_OOS_LONGEST) %></td>
</tr>
</table>
</div>
</div>

<div class="menuOffset">
<%
	ReportsHelper rh = new ReportsHelper();
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
	QueryStateT[] qrySt = new QueryStateT[1];

	MtaStatusSummaryRespT result = 
		rh.getMtaStatusSummary1(tK, null, null, fromTime, toTime, batch, qrySt);
%>
<table border="1">
	<tr class="alarms_tr_th">
		<th><%=rb.getString(ResourceKeys.K_MTA) %></th>
		<th><%=rb.getString(ResourceKeys.K_DURATION) %></th>
		<th><%=rb.getString(ResourceKeys.K_CMTS) %></th>
	</tr>
<%
	MtaStatusSummaryT[] results;
	if (result != null) {
		results = result.getMtaData();
	} else {
		results = null;
	}
	
	if (results != null) {
		for (int i = 0; i < results.length; i++) {
			MtaStatusSummaryT r = results[i];
			String strDuration = TimeHelper.secondsToHMS(r.getStatusSummary().getSumRedStatusTime());
			
			StringBuffer mtaUrl = new StringBuffer("../app/topo_drilldwn_mta.jsp");
			UrlHelper.AppendTopoHierarchyIDs(mtaUrl, tK);
			mtaUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(r.getStatusSummary().getResId());

			StringBuffer cmtsUrl = new StringBuffer("../app/topo_drilldwn_cmts.jsp");
			UrlHelper.AppendTopoHierarchyIDs(cmtsUrl, tK);
			cmtsUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
				.append(r.getCmtsResId());

%>
<tr class="alarms">
	<td><span><a href="<%=mtaUrl.toString() %>"><%=r.getStatusSummary().getName() %></a></span></td>
	<td><%=strDuration %></td>
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