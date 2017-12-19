<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : utilityRes.jsp
	* This is an ArcWeb Services Sample Result for Utility ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of Utility = http://arcweb.esri.com/services/v2/Utility.wsdl
	*/
%>

<html>
    <head><title>Utility Result - ArcWeb Services Sample</title>
	<style type="text/css">
		<!--
		.boldfont{FONT: bold 14px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.textfont{FONT: 12px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.smallfont{FONT:10px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.italicfont{FONT:12px Verdana, Arial, Helvetica, sans-serif; font-style: italic; COLOR: #000000;}
		-->
	</style>
        <script language="JavaScript">
            <!--
            function resizeWin() {
            window.focus();
             var w = 650 ;
             var h = 450;
             window.moveTo(10,10);
             window.resizeTo(w,h);
             }
            //-->
        </script>
    </head>
    <body onload=resizeWin();>
    <center><font class=boldfont>Utility Result - ArcWeb Services Sample </font><br><br>
<%
String action  = request.getParameter("action");

// ================ if the form is submitted =================
if (action  != null) {
    try {

        /*  Get the Authentication Token using authenticationManger.jsp,
            it sets the value of the token in the variable token */ %>
        <jsp:include page="authenticationManger.jsp" flush="true" />
        <% String  token = ((String) request.getAttribute("token")) ; %>

<%
        // ================== Parsing input form parameters ===================
        double minX = Double.parseDouble(request.getParameter("minX")) ;
        double minY = Double.parseDouble(request.getParameter("minY")) ;
        double maxX = Double.parseDouble(request.getParameter("maxX")) ;
        double maxY = Double.parseDouble(request.getParameter("maxY")) ;
        double x = Double.parseDouble(request.getParameter("x")) ;
        double y = Double.parseDouble(request.getParameter("y")) ;
        String method  = request.getParameter("method") ;
        String projectFrom = request.getParameter("projectFrom") ;
        String projectTo = request.getParameter("projectTo") ;
        Envelope toEnvelope  = null ;
        Point toPoint = null ;

        // ================== locate and bind to Utility Web Service ====================
        IUtility utility = new UtilityLocator().getIUtility();

        // ================== Setting form parameters =========================
        CoordinateSystem fromCoord = new CoordinateSystem();
            fromCoord.setProjection(projectFrom) ;

        CoordinateSystem toCoord = new CoordinateSystem();
            toCoord.setProjection(projectTo);

        if (method.equals("envelope")) {

            Envelope fromEnvelope = new Envelope() ;
                fromEnvelope.setMinx(minX) ;
                fromEnvelope.setMiny(minY) ;
                fromEnvelope.setMaxx(maxX) ;
                fromEnvelope.setMaxy(maxY) ;
                fromEnvelope.setCoordinateSystem(fromCoord) ;

            // ================= Calling Utility Web Service to project Envelope ================
            toEnvelope = utility.projectEnvelope(fromEnvelope,toCoord,token);

        } else if (method.equals("point")) {
           Point  fromPoint = new Point() ;
                fromPoint.setX(x);
                fromPoint.setY(y);
                fromPoint.setCoordinateSystem(fromCoord) ;

           // ================= Calling Utility Web Service to project Point ================
           toPoint = utility.projectPoint(fromPoint, toCoord,token);
        }

        // ================== Display Results =================================   %>
            <table cellpadding='4' cellspacing='0' border='0' width='600'>
                <tr><td><span class=boldfont>Results : </span><br>
                        <span class=italicfont>Project From : <%= projectFrom %><br>
                        Project To : <%= projectTo %></span><br><br></td></tr>
                <tr><td CLASS="textfont">

                <% if (method.equals("point")) {   %>
                        <span class=boldfont>Input </span>: <br>
                        X = <%= x %> <br>
                        Y = <%= y %>
                        <br><br> <span class=boldfont>Output </span>: <br>
                        X = <%= toPoint.getX() %> <br>
                        Y = <%= toPoint.getY() %>

                <%  } else if (method.equals("envelope")) {   %>
                        <span class=boldfont>Input :</span> <br>
                         MinX = <%= minX %> <br>
                         MinY = <%= minY %> <br>
                         MaxX = <%= maxX %> <br>
                         MaxY =<%= maxY %>
                        <br><br> <span class=boldfont>Output :</span> <br>
                         MinX =<%=toEnvelope.getMinx()%> <br>
                         MinY =<%=toEnvelope.getMiny()%> <br>
                         MaxX =<%=toEnvelope.getMaxx()%> <br>
                         MaxY =<%=toEnvelope.getMaxy()%>
                <%  } %>
            </td></tr>
        </table>
<%  }
    // ========================== Print Exception if thrown===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>
<br><center><a href="javascript:window.close()" class=boldfont> Close </a></center>
</body>
</html>