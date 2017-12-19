<%@page contentType="text/html"%>
<%@page import="com.esri.arcweb.v2.*,com.themindelectric.www._package.com_esri_is_services_common_v2.*,com.themindelectric.www._package.com_esri_is_services_common_v2_geom.*,com.themindelectric.www._package.com_esri_is_services_glue_v2_addressfinder
.*" %>

<%	/*
	* File : addressFinderRes.jsp
	* This is an ArcWeb Services Sample Result for AddressFinder ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl
	*/
%>

<html>
    <head><title>AddressFinder Result- ArcWeb Services Sample</title>
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
    <center><span class=boldfont>AddressFinder Result- ArcWeb Services Sample </span><br><br>

<%
String action = request.getParameter("action");

// ================ if the form is submitted =================
if (action != null) {

    try {
        // ================== Parsing input form parameters ===================
        String street = request.getParameter("street") ;
        String intersection = request.getParameter("intersection") ;
        String city = request.getParameter("city") ;
        String state = request.getParameter("state") ;
        String zone = request.getParameter("zip") ;
        String country = request.getParameter("country") ;
        String datasource = request.getParameter("datasource") ;

        /* ==================== Get the Authentication Token ================================
            authenticationManger.jsp sets the value of the token in the variable token */ %>
        <jsp:include page="authenticationManger.jsp" flush="true" />
            <% String  token = ((String) request.getAttribute("token")) ; %>

        <%
        //======================locate and bind to AddressFinder ArcWeb service ====================
        IAddressFinder addressFinder = new AddressFinderLocator().getIAddressFinder();

            // address - to hold all address information
            Address address = new Address();

                if((street != null) && (!street.equals(""))) {
                    address.setStreet(street) ;
                }
                if((intersection != null) && (!intersection.equals(""))) {
                    address.setIntersection(intersection) ;
                }
                if((city != null) && (!city.equals(""))) {
                    address.setCity(city) ;
                }
                if((zone != null) && (!zone.equals(""))) {
                    address.setZone(zone) ;
                }
                if((state != null) && (!state.equals(""))) {
                    address.setState_prov(state) ;
                }
                if((country != null) && (!country.equals(""))) {
                    address.setCountry(country) ;
                }


            // addressFinderOptions - to hold all address information
            AddressFinderOptions addressFinderOptions = new AddressFinderOptions();
                addressFinderOptions.setDataSource(datasource) ;

	// =======================Calling AddressFinder Web Service =======================
	LocationInfo locationInfo =  addressFinder.findAddress(address,addressFinderOptions,token);


        // ======================= Display Results =======================================  %>
            <table cellpadding='4' cellspacing='0' border='0' width='400'>
                <tr><td><span class=boldfont>Result for : </span><span class=italicfont><%= street %>, <%= intersection %>, <%= city %>, <%= state %>, <%=zone %></span></td></tr>
                    <table cellpadding='3' cellspacing='0' border='0' width='398'>

                    <%  // if address match is found
                        if ((locationInfo.getMatchType().equals("CANDIDATES")) || (locationInfo.getMatchType().equals("EXACT"))) {

                            // location object for candidate[s]
                            Location[] location = locationInfo.getCandidates();
                            Point point ;

                            //loop through the location object
                            for(int i=0; i < location.length  ;i++ ) {
                                // Point object of location holds x and y for i candidate
                                point = location[i].getPoint() ;
                        %>

                                <tr class=textfont>
                                    <td><b><%= (i+1)%>) </B>
                                    <%= location[i].getDescription1() %><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    X = <%= point.getX() %> ,
                                    Y = <%= point.getY() %></td></tr>
                            <% }
                        } else { // if no match is found     %>
                            <tr><td><b><span class=boldfont><font color=red> * No match found </font></span></b></td></tr>
                        <% } %>

                        </td></tr></table>
                </td></tr></table>

<%  }
// ========================== Print Exception if thrown================================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
    }
} %>
<br><a href="javascript:window.close()" class=boldfont> Close </a>
</center></body>
</html>