<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	StringBuffer fillMissingDataUrl = new StringBuffer("../app/edit_topo_blade_form_mf.jsp?");
	fillMissingDataUrl.append(request.getQueryString());
	StringBuffer back = new StringBuffer("../app/admin_topo_blade.jsp");
%>

<jsp:useBean id="BladeBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.BladeFormBean"></jsp:useBean>
<jsp:setProperty name="BladeBean" property="*"/>

<%
	if (BladeBean.getCancel() != null) {
%>
<jsp:forward page="<%=back.toString() %>"></jsp:forward>
<%
	}
	if (!BladeBean.validate()) {
%>
<jsp:forward page="<%=fillMissingDataUrl.toString() %>"></jsp:forward>
<%		
	}
%>

<%
	 /*
	 * Update the Blade to the DB and return to previous page
	 */
	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	TopologyHelper th = new TopologyHelper();
	BladeT blade = th.getBlade(tK.getRegionId(), tK.getMarketId(), tK.getBladeId());
	blade.setName(BladeBean.getName());
	blade.setHost(BladeBean.getHost());
	AdminHelper ah = new AdminHelper();
	ah.updateBlade(blade);
%>

<jsp:forward page="<%=back.toString() %>"></jsp:forward>

</body>
</html>