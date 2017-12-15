<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<%
	StringBuffer emtaUrl = new StringBuffer("../app/topo_drilldwn_emta.jsp");
	emtaUrl.append("?").append(request.getQueryString());
	StringBuffer emtaPlotsUrl = new StringBuffer("../app/topo_drilldwn_emta_plots.jsp");
	emtaPlotsUrl.append("?").append(request.getQueryString());	
%>
<frameset rows="60px,250px,*">
	<frame src="../app/logo_header.jsp" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="CM_DRILL_DOWN_LOGO_FRAME">
	</frame>
	<frame src="<%=emtaUrl.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="CM_DRILL_DOWN_DETAILS_FRAME">
	</frame>
	<frame src="<%=emtaPlotsUrl.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="CM_DRILL_DOWN_PLOTS_FRAME">
	</frame>
</frameset>
</html>
