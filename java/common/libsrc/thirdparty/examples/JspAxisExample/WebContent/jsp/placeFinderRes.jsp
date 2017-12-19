<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : placeFinderRes.jsp
	* This is an ArcWeb Services Sample Result for PlaceFinder ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of PlaceFinder = http://arcweb.esri.com/services/v2/PlaceFinder.wsdl
	*/
%>

<html>
    <head><title>PlaceFinder Result- ArcWeb Services Sample</title>
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
             var w = 650;
             var h = 450;
             window.moveTo(10,10);
             window.resizeTo(w,h);
             window.focus();   }
            //-->
        </script>
    </head>
    <body onload=resizeWin();><center>
        <font class='boldfont' ><center>PlaceFinder Result- ArcWeb Services Sample</font><br><br>

<%
String action  = request.getParameter("action");

// ================ if the form is submitted =================
if (action  != null) {
    try {

        /* ==================== Get the Authentication Token ================================
            authenticationManger.jsp sets the value of the token in the variable token */ %>
          <jsp:include page="authenticationManger.jsp" flush="true" />
		  <% String  token = ((String) request.getAttribute("token")) ; %>

<%      // ================== Parsing input form parameters ===================
        String place = request.getParameter("place") ;
        double minX = Double.parseDouble(request.getParameter("minX")) ;
        double minY = Double.parseDouble(request.getParameter("minY")) ;
        double maxX = Double.parseDouble(request.getParameter("maxX")) ;
        double maxY = Double.parseDouble(request.getParameter("maxY")) ;

        boolean useRecordCount = false ;
            if (request.getParameter("useRecordCount") != null) useRecordCount = true ;

        boolean usefilterType = false ;
            if (request.getParameter("usefilterType") != null) usefilterType = true ;

        boolean useEnvelopeFilter = false ;
            if (request.getParameter("useEnvelopeFilter") != null) useEnvelopeFilter = true ;

        //======================locate and bind to PlaceFinder Web service =================
        IPlaceFinder placeFinder = new PlaceFinderLocator().getIPlaceFinder();

            // ================== Setting form parameters =========================
            // PlaceFinderOptions
            PlaceFinderOptions placeFinderOptions = new PlaceFinderOptions();

            // PlaceFinderOptions -  set dataSource
            placeFinderOptions.setDataSource(request.getParameter("datasource")) ;

            // PlaceFinderOptions -  set filter envelope
            if (useEnvelopeFilter)  {

                Envelope envelope = new Envelope() ; // Envelope
                    envelope.setMinx(minX) ;
                    envelope.setMiny(minY) ;
                    envelope.setMaxx(maxX) ;
                    envelope.setMaxy(maxY) ;

            placeFinderOptions.setFilterEnvelope(envelope) ;
            }

            // PlaceFinderOptions - set filter type
            if (usefilterType)  {
                String filterType = request.getParameter("filterType") ;
                if (!(filterType.equals(""))) {
                    placeFinderOptions.setFilterType(filterType) ;
                }
            }

            // PlaceFinderOptions - set number of record count(s) to return
            if (useRecordCount)  {
                String count = request.getParameter("count") ;
                if (!(count.equals("")))  {
                    placeFinderOptions.setCount(Integer.parseInt(count)) ;
                }
            }

        // =============Calling Place Finder webservice ==================
        LocationInfo locationInfo = placeFinder.findPlace(place,placeFinderOptions,token);

        // ============= Display Results =============================== %>
        <br><font class='boldfont'>Results</font>
        <table width='550' border='0' cellpadding='2' cellspacing='2'>

<%      // if address match is found
        if ((locationInfo.getMatchType().equals("CANDIDATES")) || (locationInfo.getMatchType().equals("EXACT"))) {

         Location[] location =locationInfo.getCandidates();  // location object for candidate[s]
         Point point ;

         // loop through the candidate[s]
            for (int i=0 ; i < location.length; i++ ) {
                point = location[i].getPoint() ;       %>

                <tr  class="textfont">
                    <td><%= (i + 1) %>) <i> <%= location[i].getDescription1() %></i> Lon :<%= point.getX() %>, Lat : <%= point.getY() %></td></tr>
<%          }
        } else {  // if no match is found %>
			<tr><td align=center class="textfont"><font color=red>No Match Found </font></td></tr>
<%      }   %>
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