<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
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

<jsp:useBean id="CmsBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewCmsFormBean"></jsp:useBean>

<%
	CmtsEditExtractionHelper h = new CmtsEditExtractionHelper(request);
	List result = h.parseAttributes();
	/*
	 * Edit CMS
	 */
	CmsEditHelper eh = new CmsEditHelper((String)result.get(0));
	CmsT cms = eh.getCms();
	StringBuffer actionUrl = new StringBuffer("../app/edit_cms.jsp");
	if (cms != null) {
		CmsBean.setCmsName(cms.getCmsName());
		CmsBean.setCmsHost(cms.getCmsHost());
		UrlHelper.AppendTopoHierarchyIDs(actionUrl, cms.getTopologyKey());
		actionUrl.append("&").append(UrlHelper.RESOURCE_ID).append("=")
		.append(cms.getCmsResId());
	}
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CHANGE) %> <%=rb.getString(ResourceKeys.K_CMS) %></td>
</tr>
</table>
</div>

<div class="center">
<p style="{color: blue;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD) %></em></small>
</p>

<form name="editCmsForm" action="<%=actionUrl.toString() %>" method="post">
<div>
<table border="0">
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_NAME) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmsBean.getCmsName() %>" name="cmsName"/></td>
</tr>
<tr>
	<td class="name_lg"><small><sup>*</sup></small><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_HOST) %></td>
	<td class="value_lg"><input type="text" size="<%=textFieldSize %>" value="<%=CmsBean.getCmsHost() %>" name="cmsHost"/></td>
</tr>
<tr >
	<td colspan="2">
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE_CHANGES) %>" name="update"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>