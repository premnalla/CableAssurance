<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_NEW) %> <%=rb.getString(ResourceKeys.K_CMS) %></td>
</tr>
</table>
</div>

<div class="center">

<p style="{color: red;}">
<small><em><sup>*</sup><%=rb.getString(ResourceKeys.K_REQ_FIELD_WARM) %></em></small>
</p>

<form name="addNewCmsForm" action="../app/add_new_cms.jsp" method="post">
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
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_SAVE) %>" name="add"/>
		<input type="submit" value="<%=rb.getString(ResourceKeys.K_CANCEL) %>" name="cancel"/>
	</td>
</tr>
</table>
</div>
</form>
</div>

</body>
</html>