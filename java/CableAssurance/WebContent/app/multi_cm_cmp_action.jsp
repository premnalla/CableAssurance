<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.palmyrasyscorp.www.jsp.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>CableAssurance</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css" />
</head>
<%
	StringBuffer hfcUrl = new StringBuffer("../app/multi_eud_cmp_top_hfc.jsp");
	hfcUrl.append("?").append(request.getQueryString());
	
	StringBuffer plotFrmUrl = new StringBuffer("../app/multi_cm_cmp_sub_bottom_frm.jsp");
	plotFrmUrl.append("?").append(request.getQueryString());
	
	CmResIdExtractionHelper h = new CmResIdExtractionHelper(request);
	List idL = h.parseAttributes();
	for (int i=0; i < idL.size(); i++) {
		plotFrmUrl.append("&").append(UrlHelper.ID).append("=").append(idL.get(i));			
	}
	
	if (idL.size() > 0) {
%>
<frameset rows="60px,150px,*">
	<frame src="../app/logo_header.jsp" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="MULTI_CM_CMP_LOGO_HDR_FRAME">
	</frame>
	<frame src="<%=hfcUrl.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="MULTI_EUD_CMP_HFC_FRAME">
	</frame>
	<frame src="<%=plotFrmUrl.toString() %>" frameborder="0" marginheight="0"
		marginwidth="0" noresize="noresize" name="MULTI_CM_CMP_PLOT_FRAME">
	</frame>
</frameset>
<%
	} // end-if
%>
</html>
