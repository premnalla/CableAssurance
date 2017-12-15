<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.*"%>
<%@ page import="com.palmyrasys.www.webservices.CableAssurance.Common.*"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<%
	System.out.println("QueryString: " + request.getQueryString());

	String regionId = request.getParameter(UrlHelper.REGION_ID);
	String marketId = request.getParameter(UrlHelper.MARKET_ID);
	String bladeId = request.getParameter(UrlHelper.BLADE_ID);

	TopoHierarchyKeyT tK = TopoKeyHelper.getTopoKey(regionId, marketId,	bladeId);

	String[] ids = request.getParameterValues(UrlHelper.ID);
	final String frameSize = "200px";
	StringBuffer frmSetCols = new StringBuffer();
	for (int i=0; i < ids.length; i++) {
		if (i != 0) {
	frmSetCols.append(",").append(frameSize);
		} else {
	frmSetCols.append(frameSize);	
		}
	}
	
	System.out.println(frmSetCols);
	
	if (ids.length > 0) {
%>
<frameset cols="<%=frmSetCols.toString() %>">
<%
		for (int i=0; i < ids.length; i++) {
			String id = ids[i];
			StringBuffer url = new StringBuffer("../app/multi_cm_cmp.jsp");
			UrlHelper.AppendTopoHierarchyIDs(url, tK);
			// url.append("?").append(request.getQueryString());
			url.append("&").append(UrlHelper.RESOURCE_ID).append("=").append(id);
			
			// System.out.println("URL: " + url);
			
%>
<frame src="<%=url.toString() %>" frameborder="0" marginheight="0" marginwidth="0" noresize="noresize" name="<%=id %>">
</frame>
<%
			url = null;
		} // foreach id in list
%>
</frameset>
<%
	} // endif (size > 0)
%>
</html>
