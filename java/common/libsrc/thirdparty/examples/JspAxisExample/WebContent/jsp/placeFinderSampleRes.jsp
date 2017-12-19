<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : placeFinderSampleRes.jsp
	* This is an ArcWeb services sample result file for PlaceFinderSample ArcWeb Service.
	*
	* WSDL location of service used.
	* WSDL location of PlaceFinderSample = http://arcweb.esri.com/services/v2/PlaceFinderSample.wsdl
	*/
%>

<html>
    <head><title>PlaceFinderSample Result- ArcWeb Services Sample</title>
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
    <body onload=resizeWin(); ><center>
        <font class='boldfont'><center>PlaceFinderSample Result- ArcWeb Services Sample</font><br><br>
<%
    String action  = request.getParameter("action");

    // ================ if the form is submitted =================
    if (action  != null) {

        try {
            String place = request.getParameter("place") ;
            String dataSource = request.getParameter("datasource") ;

            // ===================Locate and bind to PlaceFinderSample ArcWEB service =================
            IPlaceFinderSample placeFinderSample = new PlaceFinderSampleLocator().getIPlaceFinderSample();

            PlaceFinderOptions placeFinderOptions  = new PlaceFinderOptions() ;
                placeFinderOptions.setDataSource(dataSource) ;

            // =================== Calling PlaceFinderSample Web Service =========================
            LocationInfo locationInfo = placeFinderSample.findPlace(place,placeFinderOptions);

            // =================== Display Results ============================================   %>
            <br><font class='boldfont'>Results</font>
            <table width='550' border='0' cellpadding='2' cellspacing='2'>

<%          // if address match is found
            if ((locationInfo.getMatchType().equals("CANDIDATES")) || (locationInfo.getMatchType().equals("EXACT"))) {

                Location[] location = locationInfo.getCandidates();  // location object for candidate[s]
                Point point ;

                // loop through the candidate[s]
                for (int i=0 ; i < location.length; i++ ) {
                    point = location[i].getPoint() ;               %>
                    <tr  class="textfont">
                        <td><%= (i + 1) %>) <i> <%= location[i].getDescription1() %></i> Lon :<%= point.getX() %>, Lat : <%= point.getY() %></td>
                    </tr>
<%		}

            } else { // if no match is found  %>
                 <tr><td align=center><font color=red>No Match Found </font></td></tr>
<%		} %>
              </table>

<%     }
       // ========================== Print Exception if thrown===================
        catch(Exception e) {
            out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        }
    } %>
	<br>
	<a href="javascript:window.close()" class=boldfont> Close </a>
    </body>
</html>
