<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
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
	final int textFieldSize = 30;

	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

%>

<jsp:useBean id="CmtsBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewCmtsFormBean"></jsp:useBean>

<%
	CmtsEditExtractionHelper h = new CmtsEditExtractionHelper(request);
	List result = h.parseAttributes();
	/*
	 * Edit CMTS
	 */
	CmtsEditHelper eh = new CmtsEditHelper((String)result.get(0));
	CmtsEditHolder holder = eh.getCmts();
	StringBuffer actionUrl = new StringBuffer("../app/edit_cmts.jsp");
	if (holder != null) {
		CmtsBean.setCmtsName(holder.cmts.getCmtsName());
		CmtsBean.setCmtsHost(holder.cmts.getCmtsHost());
		CmtsBean.setCmtsReadCommunity(holder.cmtsV2C.getReadCommnunity());
		CmtsBean.setCmReadCommunity(holder.cmV2C.getReadCommnunity());
		CmtsBean.setCmWriteCommunity(holder.cmV2C.getWriteCommnunity());
		CmtsBean.setMtaReadCommunity(holder.mtaV2C.getReadCommnunity());
		CmtsBean.setMtaWriteCommunity(holder.mtaV2C.getWriteCommnunity());
		UrlHelper.AppendTopoHierarchyIDs(actionUrl, holder.cmts.getTopologyKey());
		actionUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(holder.cmts.getCmtsResId());
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CHANGE) %> <%=rb.getString(ResourceKeys.K_CMTS) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD) %></em></small>
</p>

<form name="editCmtsForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getCmtsName() %>" name="cmtsName"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_HOST) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getCmtsHost() %>" name="cmtsHost"/></td>
</tr>
</table>
</div>
<div>
<br/><br/>
</div>
<div>
<table border="0">
<thead><tr><td colspan="2" class="thead"><%=rb.getString(ResourceKeys.K_SNMP_V2C_ATTRIBUTES) %></td></tr></thead>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getCmtsReadCommunity() %>" name="cmtsReadCommunity"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getCmReadCommunity() %>" name="cmReadCommunity"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_SNMP_W_COMMUNITY) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getCmWriteCommunity() %>" name="cmWriteCommunity"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getMtaReadCommunity() %>" name="mtaReadCommunity"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_SNMP_W_COMMUNITY) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmtsBean.getMtaWriteCommunity() %>" name="mtaWriteCommunity"/></td>
</tr>
<tr >
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE_CHANGES) %>" name="updateCmts"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancelNewCmts"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>