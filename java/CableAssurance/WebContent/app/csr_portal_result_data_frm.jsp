<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<%
	StringBuffer url_1 = new StringBuffer("../app/csr_portal_result_row1_frm.jsp");
	url_1.append("?").append(request.getQueryString());
	StringBuffer url_2 = new StringBuffer("../app/csr_portal_result_row2_frm.jsp");
	url_2.append("?").append(request.getQueryString());
%>
<frameset rows="50%,50%">
	<frame src="<%=url_1.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="<%=UrlHelper.CSR_DATA_ROW_1_FRAME %>" >
	</frame>
	<frame src="<%=url_2.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="<%=UrlHelper.CSR_DATA_ROW_2_FRAME %>">
	</frame>
</frameset>
</html>
