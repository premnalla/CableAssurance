<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<body class="default_background" >

<%
	StringBuffer fillMissingDataUrl = new StringBuffer("../app/edit_cms_form_mf.jsp?");
	fillMissingDataUrl.append(request.getQueryString());
	StringBuffer back = new StringBuffer("../app/admin_cms.jsp");
%>

<jsp:useBean id="CmsBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewCmsFormBean"></jsp:useBean>
<jsp:setProperty name="CmsBean" property="*"/>


<%
	if (CmsBean.getCancel() != null) {
%>
<jsp:forward page="<%=back.toString() %>"></jsp:forward>
<%
	}
	if (!CmsBean.validate()) {
%>
<jsp:forward page="<%=fillMissingDataUrl.toString() %>"></jsp:forward>
<%		
	}
%>

<%
	 /*
	 * Update the CMS to the DB and return to previous page
	 */
	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);
	String resId = request.getParameter(UrlHelper.RESOURCE_ID);
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	BigInteger cmsResId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	CmsT svcCms = th.getCms(tK, cmsResId);
	svcCms.setCmsName(CmsBean.getCmsName());
	svcCms.setCmsHost(CmsBean.getCmsHost());
	AdminHelper ah = new AdminHelper();
	ah.updateCms(svcCms);
%>

<jsp:forward page="<%=back.toString() %>"></jsp:forward>

</body>
</html>