<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<%@page import="com.palmyrasyscorp.www.webservices.helper.AdminHelper"%>
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
		 * Edit User
		 */
%>
<jsp:forward page="../app/edit_user_form.jsp"></jsp:forward>
<%
	} else {
		CmtsDeleteExtrationHelper dh = new CmtsDeleteExtrationHelper(request);
		result = dh.parseAttributes();
		if (result.size() > 0) {
			/*
			 * Delete User
			 */
			AdminHelper ah = new AdminHelper();
			String loginId = (String)result.get(0);
			// ah.deleteUser(loginId);
%>
<jsp:forward page="../app/admin_user.jsp"></jsp:forward>
<%
		} else {		
			GenericExtractionHelper genEx = new GenericExtractionHelper(request);
			genEx.setPrefix(UrlHelper.RESET_PASSWORD_PREFIX);
			result = genEx.parseAttributes();
			if (result.size() > 0) {
				/*
				 * Reset Password
				 */
				StringBuffer resetUrl = new StringBuffer("../app/reset_userpw_form.jsp?");
				resetUrl.append(request.getQueryString());
%>
<jsp:forward page="<%=resetUrl.toString() %>"></jsp:forward>
<%
			} else {
				/*
				 * ERROR
				 */
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<%
			}
%>
<jsp:forward page="../app/empty_page.jsp"></jsp:forward>
<%
		}
	}
%>

<jsp:forward page="../app/empty_page.jsp"></jsp:forward>

</body>
</html>