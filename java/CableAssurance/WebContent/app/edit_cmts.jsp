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
	StringBuffer fillMissingDataUrl = new StringBuffer("../app/edit_cmts_form_mf.jsp?");
	fillMissingDataUrl.append(request.getQueryString());
	StringBuffer back = new StringBuffer("../app/admin_cmts.jsp");
%>

<jsp:useBean id="CmtsBean" scope="request" class="com.palmyrasyscorp.www.jsp.bean.NewCmtsFormBean"></jsp:useBean>
<jsp:setProperty name="CmtsBean" property="*"/>


<%
	if (CmtsBean.getCancelNewCmts() != null) {
%>
<jsp:forward page="<%=back.toString() %>"></jsp:forward>
<%
	}
	if (!CmtsBean.validate()) {
%>
<jsp:forward page="<%=fillMissingDataUrl.toString() %>"></jsp:forward>
<%		
	}
%>

<%
	 /*
	 * Update the CMTS to the DB and return to previous page
	 */
	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);
	String resId = request.getParameter(UrlHelper.RESOURCE_ID);
	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);
	BigInteger cmtsResId = new BigInteger(resId);
	TopologyHelper th = new TopologyHelper();
	CmtsT svcCmts = th.getCmts(tK, cmtsResId);
	svcCmts.setCmtsName(CmtsBean.getCmtsName());
	svcCmts.setCmtsHost(CmtsBean.getCmtsHost());
	AdminHelper ah = new AdminHelper();
	ah.updateCmts(svcCmts);
	SnmpV2CAttributesT cmtsV2C = new SnmpV2CAttributesT();
	cmtsV2C.setReadCommnunity(CmtsBean.getCmtsReadCommunity());
	cmtsV2C.setWriteCommnunity("");
	SnmpV2CAttributesT cmV2C = new SnmpV2CAttributesT();
	cmV2C.setReadCommnunity(CmtsBean.getCmReadCommunity());
	cmV2C.setWriteCommnunity(CmtsBean.getCmWriteCommunity());
	SnmpV2CAttributesT mtaV2C = new SnmpV2CAttributesT();
	mtaV2C.setReadCommnunity(CmtsBean.getMtaReadCommunity());
	mtaV2C.setWriteCommnunity(CmtsBean.getMtaWriteCommunity());
	SnmpV2CAttributesT[] v2cArray = new SnmpV2CAttributesT[3];
	int i = 0;
	v2cArray[i++] = cmtsV2C;
	v2cArray[i++] = cmV2C;
	v2cArray[i++] = mtaV2C;
	ah.updateCmtsAllSnmpV2CAttributes(tK, cmtsResId, v2cArray);
%>

<jsp:forward page="<%=back.toString() %>"></jsp:forward>

</body>
</html>