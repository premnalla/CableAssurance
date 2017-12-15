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
	StringBuffer adminUrl = new StringBuffer("../app/main_admin_data_frm.jsp");
	adminUrl.append("?").append(request.getQueryString());
%>
<frameset rows="60px,*">
	<frame src="../app/logo_header.jsp" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="ADMIN_LOGO_FRAME">
	</frame>
	<frame src="<%=adminUrl.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="ADMIN_DATA_FRAME">
	</frame>
</frameset>
</html>