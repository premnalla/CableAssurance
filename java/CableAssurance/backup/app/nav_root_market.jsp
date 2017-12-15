<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.webservices.helper.*"%>
<%@ page import="com.nds.www.wsdl.CableAssurance.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:directive.include file="../inc/htmlhead.jsp" />
<body class="default_background">
<%
LocalSystemHelper sh = new LocalSystemHelper();
LocalSystemT lst = sh.getLocalSystemType();
String sysName = lst.getSystemName().toString();
%>
<div>
<p>
Market: <%=sysName%>
</p>
</div>
<%
TopologyHelper topoHelper = new TopologyHelper();
CmtsT[] cmtsArray = topoHelper.getCmtses();
%>
<table>
<%
String urlBase = "../app/main_cmts_page.jsp";
for (int i=0; i<cmtsArray.length; i++) {
	CmtsT cmts = cmtsArray[i];
	String url = urlBase + "?resid=" + cmts.getCmtsResId();
	String cmtsName = cmts.getCmtsName();
	String statusImg = null;
	if (cmts.getStatusColor().toString().equalsIgnoreCase(StatusColorT._Gray)) {
		statusImg = "../images/status_clr_grey.gif";
	} else if (cmts.getStatusColor().toString().equalsIgnoreCase(StatusColorT._Green)) {
		statusImg = "../images/status_clr_green.gif";
	} else if (cmts.getStatusColor().toString().equalsIgnoreCase(StatusColorT._Yellow)) {
		statusImg = "../images/status_clr_yellow.gif";
	} else if (cmts.getStatusColor().toString().equalsIgnoreCase(StatusColorT._Orange)) {
		statusImg = "../images/status_clr_orange.gif";
	} else if (cmts.getStatusColor().toString().equalsIgnoreCase(StatusColorT._Red)) {
		statusImg = "../images/status_clr_red.gif";
	} else {
		statusImg = "../images/status_clr_black.gif";
	}
%>
<tr>
<td><span><img href="<%= statusImg %>" class="statusColorSmall"/></span></td>
<td><span><a href="<%= url %>" target="NET_OBJ_DATA"> <%= cmtsName %> </a></span></td>
</tr>
<%
} // end foreach cmts in array
%>
</table>
</body>
</html>