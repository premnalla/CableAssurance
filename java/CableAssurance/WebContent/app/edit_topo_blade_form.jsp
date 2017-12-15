<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.resourcebundle.*"%>
<%@ page import="com.palmyrasyscorp.www.servlet.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
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

<jsp:useBean id="Blade" scope="request" class="com.palmyrasyscorp.www.jsp.bean.BladeFormBean"></jsp:useBean>

<%
	GenericExtractionHelper genEx = new GenericExtractionHelper(request);
	genEx.setPrefix(UrlHelper.CMTS_EDIT_PREFIX);
	List l = genEx.parseAttributes();
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey("0", "0", (String)l.get(0));
	TopologyHelper th = new TopologyHelper();
	BladeT svcBld = th.getBlade(tK.getRegionId(), tK.getMarketId(), tK.getBladeId());

	/*
	 * Edit Blade
	 */
	StringBuffer actionUrl = new StringBuffer("../app/edit_topo_blade.jsp");
	if (svcBld != null) {
		Blade.setName(svcBld.getName());
		Blade.setHost(svcBld.getHost());
		UrlHelper.AppendTopoHierarchyIDs(actionUrl, tK);
	}
%>

<div class="center">
<table>
<tr>
	<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CHANGE) %> <%=rb.getString(ResourceKeys.K_BLADE) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD) %></em></small>
</p>

<form name="editBladeForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_BLADE) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=Blade.getName() %>" name="name"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_BLADE) %> <%=rb.getString(ResourceKeys.K_HOST) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=Blade.getHost() %>" name="host"/></td>
</tr>
<tr >
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE_CHANGES) %>" name="save"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>