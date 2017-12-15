<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.LocalSystem.*"%>
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
	AbstractResourceBundle rb = (AbstractResourceBundle)
		request.getSession().getAttribute(ServletConstants.RESOURCE_BUNDLE);

	if (!LocalSystemSingleton.getInstance().isMarket()) {
%>
<jsp:forward page="../app/admin_cmts_not_supp.jsp"></jsp:forward>
<%
	} // end if
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewCmtsForm" action="../app/add_new_cmts_form.jsp" method="get">
<p>
<input type="submit" value="<%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_CMTS) %>" name="addNewCmtsButton"/>
</p>
</form>
</div>

<div class="center">
<form name="editDelteCmtsForm" action="../app/edit_delete_cmts.jsp" method="get">
<table border="1">
<tr class="alarms_tr_th">
<th><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_NAME) %></th>
<th><%=rb.getString(ResourceKeys.K_CMTS) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></th>
<th><%=rb.getString(ResourceKeys.K_CM) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></th>
<th><%=rb.getString(ResourceKeys.K_MTA) %> <%=rb.getString(ResourceKeys.K_SNMP_R_COMMUNITY) %></th>
</tr>

<%
	TopologyHelper topoHelper = new TopologyHelper();
	CmtsT[] cmtsArray = topoHelper.getCmtses();

	for (int i = 0; cmtsArray != null && i < cmtsArray.length; i++) {
		CmtsT cmts = cmtsArray[i];
		StringBuffer sfx = new StringBuffer();
		sfx.append(cmts.getTopologyKey().getRegionId()).append(UrlHelper.ID_SEPERATOR)
			.append(cmts.getTopologyKey().getMarketId()).append(UrlHelper.ID_SEPERATOR)
			.append(cmts.getTopologyKey().getBladeId()).append(UrlHelper.ID_SEPERATOR)
			.append(cmts.getCmtsResId());
		StringBuffer editName = new StringBuffer(UrlHelper.CMTS_EDIT_PREFIX);
		editName.append(sfx);
		StringBuffer delName = new StringBuffer(UrlHelper.CMTS_DELETE_PREFIX);
		delName.append(sfx);
		SnmpV2CAttributesT[] v2cAttrs = 
			topoHelper.getCmtsAllSnmpV2CAttributes(cmts.getTopologyKey(), cmts.getCmtsResId());
		String cmtsReadC = "";
		String cmReadC = "";
		String mtaReadC = "";
		if (v2cAttrs != null) {
			int j = 0;
			cmtsReadC = v2cAttrs[j++].getReadCommnunity();
			cmReadC = v2cAttrs[j++].getReadCommnunity();
			mtaReadC = v2cAttrs[j++].getReadCommnunity();
		}
%>

<tr class="alarms">
<td><%=cmts.getCmtsName() %></td>
<td><%=cmtsReadC %></td>
<td><%=cmReadC %></td>
<td><%=mtaReadC %></td>
<td>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_EDIT) %>" name="<%=editName.toString() %>"/>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_DELETE) %>" name="<%=delName.toString() %>"/>
</td>
</tr>

<%
	} // end foreach cmts
%>
</table>
</form>
</div>

</body>
</html>