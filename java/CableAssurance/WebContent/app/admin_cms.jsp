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
<jsp:forward page="../app/admin_cms_not_supp.jsp"></jsp:forward>
<%
	} // end if
%>

<div class="center">
<table>
<tr>
<td class="ca_h2"><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_ADMINISTRATION) %></td>
</tr>
</table>
</div>

<div class="center">
<form name="addNewCmsForm" action="../app/add_new_cms_form.jsp" method="get">
<p>
<input type="submit" value="<%=rb.getString(ResourceKeys.K_ADD_NEW) %> <%=rb.getString(ResourceKeys.K_CMS) %>" name="addNewCmsButton"/>
</p>
</form>
</div>

<div class="center">
<form name="editDelteCmsForm" action="../app/edit_delete_cms.jsp" method="get">
<table border="1">
<tr class="alarms_tr_th">
<th><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_NAME) %></th>
<th><%=rb.getString(ResourceKeys.K_CMS) %> <%=rb.getString(ResourceKeys.K_HOST) %></th>
</tr>

<%
	TopologyHelper topoHelper = new TopologyHelper();
	CmsT[] cmsArray = topoHelper.getCmses();

	for (int i = 0; cmsArray != null && i < cmsArray.length; i++) {
		CmsT cms = cmsArray[i];
		StringBuffer sfx = new StringBuffer();
		sfx.append(cms.getTopologyKey().getRegionId()).append(UrlHelper.ID_SEPERATOR)
		.append(cms.getTopologyKey().getMarketId()).append(UrlHelper.ID_SEPERATOR)
		.append(cms.getTopologyKey().getBladeId()).append(UrlHelper.ID_SEPERATOR)
		.append(cms.getCmsResId());
		StringBuffer editName = new StringBuffer(UrlHelper.CMS_EDIT_PREFIX);
		editName.append(sfx);
		StringBuffer delName = new StringBuffer(UrlHelper.CMS_DELETE_PREFIX);
		delName.append(sfx);
%>

<tr class="alarms">
<td><%=cms.getCmsName() %></td>
<td><%=cms.getCmsHost() %></td>
<td>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_EDIT) %>" name="<%=editName.toString() %>"/>
	<input type="submit" value="<%=rb.getString(ResourceKeys.K_DELETE) %>" name="<%=delName.toString() %>"/>
</td>
</tr>

<%
	} // end foreach cms
%>
</table>
</form>
</div>

</body>
</html>