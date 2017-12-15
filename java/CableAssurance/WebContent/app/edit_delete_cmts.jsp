<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
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
	CmtsEditExtractionHelper h = new CmtsEditExtractionHelper(request);
	List result = h.parseAttributes();
	if (result.size() > 0) {
		/*
		 * Edit CMTS
		 */
%>
<jsp:forward page="../app/edit_cmts_form.jsp"></jsp:forward>
<%
	} else {
		CmtsDeleteExtrationHelper dh = new CmtsDeleteExtrationHelper(request);
		result = dh.parseAttributes();
		if (result.size() > 0) {
			/*
			 * Delete CMTS
			 */
		} else {		
			/*
			 * Error
			 */
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<%
		}
	}
%>

</body>
</html>