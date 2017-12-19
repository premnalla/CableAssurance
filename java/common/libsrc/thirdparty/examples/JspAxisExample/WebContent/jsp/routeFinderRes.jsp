<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : routeFinderRes.jsp
	* This is an ArcWeb Services Sample Result for RouteFinder ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl
	* WSDL location of RouteFinder = http://arcweb.esri.com/services/v2/RouteFinder.wsdl
	*
	* To get the Route, first geocode the FROM and TO address using AddressFinder ArcWeb service
	*	and use RouteFinder ArcWeb service for getting the Route.
        *  Geocoding is the process of getting the x and y coordinates for an address.
	*/
%>
<html>
    <head><title>RouteFinder Result- ArcWeb Services Sample</title>
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
             var w = 800 ;
             var h = 800;
             window.moveTo(10,10);
             window.resizeTo(w,h);
             }
            //-->
        </script>
	</head>
    <body onload=resizeWin();>
    <center><font class='boldfont'>RouteFinder Result - ArcWeb Services Sample </font><br><br>

<%
String action = request.getParameter("action");

// ================ if the form is submitted =================
if (action != null) {

	try {
        /* ==================== Get the Authentication Token ================================
            authenticationManger.jsp sets the value of the token in the variable token */ %>
          <jsp:include page="authenticationManger.jsp" flush="true" />
		  <% String  token = ((String) request.getAttribute("token")) ; %>

<%      // ===================Geocode FROM-Address ======================
        /* geocodeAddress.jsp is used as an include file to geocodes the address .
            For successful geocoding, it returns (X,Y)Point object, totalDescription and sets geocodeSuccess flag object to true.
	    For failed geocoding, it only returns geocodeSuccess flag object and set it value to false */    %>

        <jsp:include page="geocodeAddress.jsp" flush="true" >
            <jsp:param name="street" value='<%=request.getParameter("fromStreet") %>' />
            <jsp:param name="city" value='<%= request.getParameter("fromCity") %>' />
            <jsp:param name="state" value='<%= request.getParameter("fromState") %>' />
            <jsp:param name="zip" value='<%= request.getParameter("fromZip") %>' />
            <jsp:param name="country" value='<%= request.getParameter("fromCountry") %>' />
            <jsp:param name="dataSource" value='<%= request.getParameter("fromGeoCodeDataSource") %>' />
            <jsp:param name="token" value='<%= token %>' />
        </jsp:include>

<%      //For successful geocode, get the X,Y and description from the first candidate
        boolean fromGeocodeSuccess = ((String) request.getAttribute("geocodeSuccess")).equals("true") ;
        String fromDescription = null ;
        Point fromPoint = null ;
        if (fromGeocodeSuccess) {
            fromPoint = (Point) request.getAttribute("point");
            fromDescription = (String) request.getAttribute("description") ;
        }

        // ===================Geocode TO-Address ======================
        /* geocodeAddress.jsp is used as an include file to geocodes the address .
            For successful geocoding, it returns  (X,Y)Point object, totalDescription and sets geocodeSuccess object flag to true.
            For failed geocoding, it only returns geocodeSuccess flag object and set it value to false  */   %>

        <jsp:include page="geocodeAddress.jsp" flush="true" >
            <jsp:param name="street" value='<%=request.getParameter("toStreet") %>' />
            <jsp:param name="city" value='<%= request.getParameter("toCity") %>' />
            <jsp:param name="state" value='<%= request.getParameter("toState") %>' />
            <jsp:param name="zip" value='<%= request.getParameter("toZip") %>' />
            <jsp:param name="country" value='<%= request.getParameter("toCountry") %>' />
            <jsp:param name="dataSource" value='<%= request.getParameter("toGeoCodeDataSource") %>' />
            <jsp:param name="token" value='<%= token %>' />
        </jsp:include>

<%      //For successful geocode, get the X,Y and description to the first candidate of the location object
        boolean toGeocodeSuccess = ((String) request.getAttribute("geocodeSuccess")).equals("true") ;
        String toDescription = null ;
        Point toPoint = null ;

        if (toGeocodeSuccess) {
            toPoint = (Point) request.getAttribute("point");
            toDescription = (String) request.getAttribute("description") ;
        }

        // =================Get Route for successful geocoding of FROM and TO Addresses ===========
        RouteInfo routeInfo = null ;
        if ((fromGeocodeSuccess) && (toGeocodeSuccess)) {

            //======================locate and bind to RouteFinder Web service =================
            IRouteFinder routeFinder = new RouteFinderLocator().getIRouteFinder();

                // RouteStop array to hold different Stops
                RouteStop[] routeStop = new RouteStop[2] ;

                    // First RouteStop for FROM Address
                    RouteStop fromRouteStop = new RouteStop();
                    // Second RouteStop for TO Address
                    RouteStop toRouteStop = new RouteStop();

                        fromRouteStop.setPoint(fromPoint) ;
                        fromRouteStop.setDescription(fromDescription) ;

                        toRouteStop.setPoint(toPoint) ;
                        toRouteStop.setDescription(toDescription);

                    routeStop[0] = fromRouteStop ;
                    routeStop[1] = toRouteStop ;

                // RouteFinderOptions
                RouteFinderOptions  routeFinderOptions = new RouteFinderOptions();

                    routeFinderOptions.setDataSource(request.getParameter("routeDataSource")) ;  // RouteFinderOptions - set data source
                    routeFinderOptions.setReturnDirections(true) ;                      // set return directions to true
                    routeFinderOptions.setReturnMap(true) ;                             // set return Map to true
                    routeFinderOptions.setLanguage(request.getParameter("language")) ;  // set language
                    routeFinderOptions.setUnits(request.getParameter("distanceUnits")) ;// set distance Units

                        RouteDisplayOptions routeDisplayOptions = new RouteDisplayOptions() ;
                            routeDisplayOptions.setTransparency(Double.parseDouble(request.getParameter("transparency"))) ;
                            routeDisplayOptions.setThickness(Integer.parseInt(request.getParameter("thickness"))) ;
                            routeDisplayOptions.setColor(request.getParameter("color")) ;

                    routeFinderOptions.setRouteDisplayOptions(routeDisplayOptions) ;

            // =======================Calling RouteFinder Web Service =======================
            routeInfo = routeFinder.findRoute(routeStop,routeFinderOptions,token) ;
        }

        // ============= Display Results ==============================================  %>
        <br><font class='boldfont'>Results</font><br>

       <%// if geocoding of FROM and TO address is successful
        if ((fromGeocodeSuccess) && (toGeocodeSuccess)) {
            // if route is returned from the server
            if (routeInfo != null) {  %>
                <table border='1' cellpadding='2' cellspacing='2'><tr><td>
                <table border='0' cellpadding='2' cellspacing='2' class='textfont'>
                <tr><td><font size=-1><b> FROM : </b></font></td><td><font size=-1><b><%= fromDescription %></b></font></td></tr>
                <tr><td><font size=-1><b> TO : </b></font></td><td><font size=-1><b><%= toDescription %></b></font></td></tr>
                <tr><td colspan=2> &nbsp; </td></tr>

               <% //========= Get the Map Image Url ================
                MapImageInfo mapImageInfo =  routeInfo.getMapImageInfo() ;  %>
                <tr><td colspan=2 align=center><img border=1 src='<%=mapImageInfo.getMapUrl() %>' ></td></tr>
                <tr><td colspan=2> &nbsp; </td></tr>
                <tr><td colspan=2> <b>Directions </b></td></tr>

                 <% //================Individual Description ==========
                    SegDescription[] segDescriptions =  routeInfo.getSegDescriptions() ;

                    //=========loop through and display individual Segment description==========
                    for (int j=0; j < segDescriptions.length; j++) {

                        if (j == 0) {	// for first Segment, display description 1,2 and 3  %>
                            <tr><td><font size=-1><%= segDescriptions[j].getDescription1() %></font><br>
                            <font size=-1><%= segDescriptions[j].getDescription2() %></font></td>
                            <td valign=bottom><font size=-1> &nbsp; &nbsp;<%= segDescriptions[j].getDescription3() %> </font></td></tr>

                        <% } else {  // from second Segment, display description 1 and 2  %>
                            <tr><td><font size=-1><%= segDescriptions[j].getDescription1() %></font></td>
                            <td><font size=-1> &nbsp; &nbsp;<%= segDescriptions[j].getDescription2() %></font></td></tr>
                        <% }
                    }

                //================Total Segment Description ==========
                SegDescription totalDescription = routeInfo.getTotalDescription() ;

                // Total Segment - description1 holds total Driving distance 		%>
                <tr><td colspan=2 ><font size=-1> <b><%= totalDescription.getDescription1() %></b></font></td></tr>

                <% // Total Segment - description2 holds total Driving time  %>
                <tr><td colspan=2 ><font size=-1> <b> <%= totalDescription.getDescription2() %></b></font></td></tr>

                </table>
                </td></tr></table>

            <% } else { // Display error message, if route is not returned from the server  %>
                <font color=red> No Route is returned for the given address location </font><br>
            <% }

        } else { // Display error message for unsuccessful geocoding
            if (!(fromGeocodeSuccess)) {   %>
                <font color=red> * FROM Address did not match</font><br>
            <% }
            if (!(toGeocodeSuccess)) {   %>
                <font color=red> * TO Address did not match &nbsp; &nbsp; </font><br>
            <% } %><br><br>
    <%  }

    }
    // ========================== Print Exception if thrown===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }

} %>
    <br><br><a href="javascript:window.close()" class=boldfont> Close </a></center>
</body>
</html>