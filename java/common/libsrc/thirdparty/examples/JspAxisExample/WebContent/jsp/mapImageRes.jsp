<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : mapImageRes.jsp
	* This is an ArcWeb Services Sample result for MapImage ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of MapImage = http://arcweb.esri.com/services/v2/MapImage.wsdl
	*/
%>

<html>
    <head><title>MapImage Result- ArcWeb Services Sample</title>
	<style type="text/css">
		<!--
		.boldfont{FONT: bold 14px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.textfont{FONT: 12px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.smallfont{FONT:10px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.italicfont{FONT:12px Verdana, Arial, Helvetica, sans-serif; font-style: italic; COLOR: #000000;}
		-->
	</style>        <script language="JavaScript">
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
    <center>
    <font class='boldfont'>MapImage Result- ArcWeb Services Sample</font><br><br>

<%
String action  = request.getParameter("action");

// ================ if the form is submitted =================
if (action  != null) {
    try {

        //  Get the Authentication Token using authenticationManger.jsp,
        //it sets the value of the token in the variable token  %>
        <jsp:include page="authenticationManger.jsp" flush="true" />
            <% String  token = ((String) request.getAttribute("token")) ; %>

<%
        // ================== Parsing input form parameters ===================
        double minX = Double.parseDouble(request.getParameter("minX")) ;
        double minY = Double.parseDouble(request.getParameter("minY")) ;
        double maxX = Double.parseDouble(request.getParameter("maxX")) ;
        double maxY = Double.parseDouble(request.getParameter("maxY")) ;

        int width  = Integer.parseInt(request.getParameter("width")) ;
        int height  = Integer.parseInt(request.getParameter("height")) ;
        String datasource  = request.getParameter("datasource") ;
        String imageformat  = request.getParameter("imageformat") ;
        String red  = request.getParameter("red") ;
        String green  = request.getParameter("green") ;
        String blue  = request.getParameter("blue") ;

        boolean showScaleBar = false ;
        if (request.getParameter("showScaleBar") != null) showScaleBar = true ;

        boolean showMarker = false ;
        if (request.getParameter("showMarker") != null) showMarker = true ;

        boolean showCircle = false ;
        if (request.getParameter("showCircle") != null) showCircle = true ;

        boolean showMapLegend = false ;
        if (request.getParameter("showMapLegend") != null) showMapLegend  = true ;

        // ================== locate and bind to MapImage Web Service ====================
        IMapImage mapImage = new MapImageLocator().getIMapImage();

        // ================== Setting form parameters =========================
            // Extent of the map
            Envelope envelope = new Envelope() ;
                envelope.setMinx(minX) ;
                envelope.setMiny(minY) ;
                envelope.setMaxx(maxX) ;
                envelope.setMaxy(maxY) ;

           // MapImageOptions
           MapImageOptions  mapImageOptions = new MapImageOptions();

                // MapImageOptions - set data source
                mapImageOptions.setDataSource(datasource) ;

                // MapImageOptions - set map image format
                mapImageOptions.setMapImageFormat(imageformat) ;

                // MapImageOptions - set maps background color,  should be (0-255,0-255,0-255) for (R,G,B)
                mapImageOptions.setBackgroundColor(red+","+green+","+blue) ;

                // MapImageOptions - set map image size
                    MapImageSize mapImageSize = new MapImageSize();
                        mapImageSize.setWidth(width);
                        mapImageSize.setHeight(height);
                mapImageOptions.setMapImageSize(mapImageSize) ;

                // MapImageOptions - set scale bar - Acetate layer
                if (showScaleBar) {
                    int sbarX  = Integer.parseInt(request.getParameter("sbarX")) ;
                    int sbarY  = Integer.parseInt(request.getParameter("sbarY")) ;

                         PixelCoord pixelCoord = new PixelCoord();
                            pixelCoord.setX(sbarX);
                            pixelCoord.setY(sbarY);

                mapImageOptions.setScaleBarPixelLocation(pixelCoord); // scale bar location, specified in pixel
                mapImageOptions.setDrawScaleBar(showScaleBar);         // set drawScaleBar to true
                }

                // MapImageOptions - set marker(Icon) symbol - Acetate layer
                if  (showMarker) {
                    String markerDatasource  = "ESRI.Simple.Icons" ;
                    String markerSymbol  = request.getParameter("markerSymbol") ;
                    double markerX  = Double.parseDouble(request.getParameter("markerX")) ;
                    double markerY  = Double.parseDouble(request.getParameter("markerY")) ;

                        Point point = new Point();
                            point.setX(markerX) ;
                            point.setY(markerY) ;

                    MarkerDescription markers[] = new MarkerDescription[1];
                    MarkerDescription markers1 = new MarkerDescription() ;
                        markers1.setIconDataSource(markerDatasource) ;
                        markers1.setName(markerSymbol) ;
                        markers1.setLocation(point) ;  // marker location specified in map co-ordinates

                    markers[0] = markers1;

                mapImageOptions.setMarkers(markers) ;
                }

                // MapImageOptions - set overlay circle - Acetate layer
                if  (showCircle) {
                    double cirRadius = Double.parseDouble(request.getParameter("cirRadius")) ;
                    String cirUnits  = request.getParameter("cirUnits") ;
                    double cirX  = Double.parseDouble(request.getParameter("cirX")) ;
                    double cirY  = Double.parseDouble(request.getParameter("cirY")) ;

                        Point cirPoint = new Point();
                            cirPoint.setX(cirX) ;
                            cirPoint.setY(cirY) ;

                        CircleDescription circle[] = new  CircleDescription[1];
                        CircleDescription circle1 = new CircleDescription();
                            circle1.setCenter(cirPoint) ; // circle center point specified in map co-ordinates
                            circle1.setRadius(cirRadius) ;
                            circle1.setRadiusUnits(cirUnits)  ; // Miles or Km
                        circle[0] = circle1;

                mapImageOptions.setCircles(circle) ;
                }

                 // MapImageOptions - Legend
                mapImageOptions.setReturnLegend(showMapLegend);

        // ================= Calling Map Image Web Service =======================
        MapImageInfo mapImageInfo = mapImage.getMap(envelope,mapImageOptions,token);

        // ================== Display Results =================================   %>
        <br><font class='boldfont'>Results</font><br>
        <table border='1' cellpadding='2' cellspacing='2'><tr>

		<% if (mapImageInfo != null) {

                        if(mapImageInfo.getMapUrl() != null) {   // display mapImage  %>
                            <td valign=top align=left><img align=top src='<%= mapImageInfo.getMapUrl() %>'></td>
			<% }
			if(!(mapImageInfo.getLegendUrl().equals(""))) {   // display Legend  %>
                            <td valign=top align=right><img align=top src='<%= mapImageInfo.getLegendUrl() %> '></td>
			<% }
                }  %>
        </tr></table>

<%  }
    // ========================== Print Exception if thrown ===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>
<br><center><a href="javascript:window.close()" class=boldfont> Close </a>
</body>
</html>