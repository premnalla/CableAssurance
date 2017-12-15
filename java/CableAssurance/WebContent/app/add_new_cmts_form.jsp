<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
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

	TopologyHelper topoHelper = new TopologyHelper();
	BladeT[] blades = topoHelper.getBlades();
%>

<jsp:useBean id="CmtsBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewCmtsFormBean"></jsp:useBean>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_NEW) %> <%=rb.getString(ResourceKeys.K_CMTS) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD) %></em></small>
</p>

<form name="addNewCmtsForm" action="../app/add_new_cmts.jsp" method="post">
<div>
<table>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_BLADE) %></td>
	<td class="value_lg">
		<select name="bladeName">
		<%
			for (int i=0; blades != null && i < blades.length; i++) {
				BladeT b = blades[i];
		%>
		<option value="<%=b.getName() %>"><%=b.getName() %></option>
		<%
			}
		%>
		</select>
	</td>
</tr>
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
<br/>
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
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="addNewCmts"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancelNewCmts"/>
	</td>
</tr>
</table>
</div>

</form>
</div>

</body>
</html>