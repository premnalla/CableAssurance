<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : reverseGeocodeRes .jsp
	* This is an ArcWeb Services Sample Result for AddressFinder ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl
	*/
 %>

<html>
    <head><title>AddressFinder - Reverse Geocoding Result - ArcWeb Services Sample</title>
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
    <center><span class=boldfont>AddressFinder - Reverse Geocoding Result - ArcWeb Services Sample </span><br><br>
<%
String action = request.getParameter("action");

// ================ if the form is submitted =================
if (action != null) {

    try {
        // ================== Parsing input form parameters ===================
        String x = request.getParameter("x") ;
        String y = request.getParameter("y") ;
        String project = request.getParameter("project") ;

        boolean useProjection = false ;
        if (request.getParameter("useProjection") != null) useProjection  = true ;

        /* ==================== Get the Authentication Token ================================
           authenticationManger.jsp sets the value of the token in the variable token */ %>
        <jsp:include page="authenticationManger.jsp" flush="true" />
        <% String  token = ((String) request.getAttribute("token")) ; %>

        <%
        //======================locate and bind to AddressFinder ArcWeb service ====================
        IAddressFinder addressFinder = new AddressFinderLocator().getIAddressFinder();

            // point
            Point point = new Point();
                point.setX(Double.parseDouble(x));
                point.setY(Double.parseDouble(y));

                if (useProjection) {
                    if((project != null) && (!project.equals(""))) {
                    // set projection to the point
                     CoordinateSystem coordinateSystem = new CoordinateSystem();
                        coordinateSystem.setProjection(project)  ;
                    point.setCoordinateSystem(coordinateSystem) ;
                    }
	        }

            // addressFinderOptions - to hold all address information
            AddressFinderOptions addressFinderOptions = new AddressFinderOptions();
                addressFinderOptions.setDataSource("GDT.Streets.US") ;

	// =======================Calling AddressFinder Web Service =======================
	Address address  =  addressFinder.getAddress(point,addressFinderOptions,token);

        // ======================= Display Results =======================================  %>
            <table cellpadding='1' cellspacing='0' border='0' width='600' >
                <tr><td align=center><span class=boldfont>Result </span><span class=italicfont></span></td></tr>
                 <tr><td align=center>
                    <table cellpadding='1' cellspacing='2' border='0'>
                    <%  // if address match is found
                        if (address != null) { %>
                         <tr><td  class=textfont>House Number </td><td> = </td><td class=textfont> &nbsp; <%= address.getHouseNumber() %></td></tr>
                          <tr ><td class=textfont>Street </td><td> = </td><td class=textfont> &nbsp; <%= address.getStreet() %>   </td></tr>
                          <tr ><td class=textfont>Intersection </td><td> = </td><td class=textfont> &nbsp; <%= address.getIntersection() %> </td></tr>
                          <tr ><td class=textfont>City </td><td> = </td><td class=textfont> &nbsp; <%= address.getCity() %>   </td></tr>
                          <tr ><td class=textfont>State </td><td> = </td><td class=textfont> &nbsp; <%= address.getState_prov() %></td></tr>
                          <tr ><td class=textfont>Country </td><td> = </td><td class=textfont> &nbsp; <%= address.getCountry() %></td></tr>
                          <tr ><td class=textfont>Zone </td><td> = </td><td class=textfont> &nbsp; <%= address.getZone() %></td></tr>
                    <% } else{ // if no match is found     %>
                            <tr><td><b><span class=boldfont><font color=red> * No match found </font></span></b></td></tr>
                        <% } %>
                </td></tr></table>
<%  }
// ========================== Print Exception if thrown================================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
    }
} %>
    <a href="javascript:window.close()" class=boldfont> Close </a></center>
    </body>
</html>