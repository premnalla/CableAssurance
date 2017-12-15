<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<%
	String devType = request.getParameter(UrlHelper.TYPE);
	LinkedList l = new LinkedList();
	CsrUrlHolder h;
	String fCols = "100%";
	if (devType != null && devType.equals(UrlHelper.MTA)) {
		StringBuffer url_1 = new StringBuffer("../app/csr_portal_result_cust_db.jsp");
		url_1.append("?").append(request.getQueryString());
		h = new CsrUrlHolder();
		h.url = url_1.toString();
		h.frameName = UrlHelper.CSR_DATA_CUSTDB_FRAME;
		l.add(h);
		
		StringBuffer url_2 = new StringBuffer("../app/csr_portal_result_cms.jsp");
		url_2.append("?").append(request.getQueryString());
		h = new CsrUrlHolder();
		h.url = url_2.toString();
		h.frameName = UrlHelper.CSR_DATA_CMS_FRAME;
		l.add(h);
		
		StringBuffer url_3 = new StringBuffer("../app/csr_portal_result_mta.jsp");
		url_3.append("?").append(request.getQueryString());		
		h = new CsrUrlHolder();
		h.url = url_3.toString();
		h.frameName = UrlHelper.CSR_DATA_MTA_FRAME;
		l.add(h);
		
		fCols = "33%,33%,33%";
		
	} else if (devType != null && devType.equals(UrlHelper.CM)) {
		StringBuffer url_1 = new StringBuffer("../app/csr_portal_result_cust_db.jsp");
		url_1.append("?").append(request.getQueryString());
		h = new CsrUrlHolder();
		h.url = url_1.toString();
		h.frameName = UrlHelper.CSR_DATA_CUSTDB_FRAME;
		l.add(h);
		
		StringBuffer url_2 = new StringBuffer("../app/csr_portal_result_cmts.jsp");
		url_2.append("?").append(request.getQueryString());
		h = new CsrUrlHolder();
		h.url = url_2.toString();
		h.frameName = UrlHelper.CSR_DATA_CMTS_FRAME;
		l.add(h);
		
		fCols = "50%,50%";
				
	} else {
		StringBuffer url = new StringBuffer("../app/empty_page.jsp");
		url.append("?").append(request.getQueryString());		
		h = new CsrUrlHolder();
		h.url = url.toString();
		h.frameName = UrlHelper.CSR_DATA_MTA_FRAME;
		l.add(h);		
	}
%>
<frameset cols="<%=fCols %>">
<%
	for (int i=0; i<l.size(); i++) {
		h = (CsrUrlHolder) l.get(i);
%>
	<frame src="<%=h.url %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="<%=h.frameName %>">
	</frame>
<%
	} // foreach ...
%>
</frameset>
</html>
