<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : thematicRes.jsp
	* This is an ArcWeb Services Sample result for MapImage ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of MapImage = http://arcweb.esri.com/services/v2/MapImage.wsdl
	*/
%>

<html>
    <head><title>MapImage Result - Thematic Map - ArcWeb Services Sample</title>
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
    <center><font class='boldfont'>MapImage Result- Thematic Map - ArcWeb Services Sample</font><br><br>
<%
String action  = request.getParameter("action");

// ================ if the form is submitted =================
if (action  != null) {
    try {

        //  Get the Authentication Token using authenticationManger.jsp,
        //  it sets the value of the token in the variable token  %>
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
        String thematicField  = request.getParameter("thematicField") ;
        String method = request.getParameter("method") ;
        String palette  = request.getParameter("palette") ;
        int classes = Integer.parseInt(request.getParameter("classes")) ;

        boolean showMapLegend = false ;
        if (request.getParameter("showMapLegend") != null) showMapLegend  = true ;

        // ================== Setting form parameters =========================
            // Extent of the map
            Envelope envelope = new Envelope() ;
                envelope.setMinx(minX);
                envelope.setMiny(minY);
                envelope.setMaxx(maxX);
                envelope.setMaxy(maxY);

            // MapImageOptions
            MapImageOptions  mapImageOptions = new MapImageOptions();

                // MapImageOptions - set data source
                mapImageOptions.setDataSource(datasource);

                // MapImageOptions - set map image size
                    MapImageSize mapImageSize = new MapImageSize();
                        mapImageSize.setWidth(width);
                        mapImageSize.setHeight(height);
                mapImageOptions.setMapImageSize(mapImageSize);

                 // MapImageOptions - Legend
                mapImageOptions.setReturnLegend(showMapLegend);

            // thematicOptions
            ThematicOptions thematicOptions = new ThematicOptions();
                thematicOptions.setNumberOfClasses(classes);
                thematicOptions.setClassificationMethod(method);
                thematicOptions.setColorPalette(palette);

        // ================== locate and bind to MapImage Web Service ====================
        IMapImage mapImage = new MapImageLocator().getIMapImage();

        // ================= Calling Map Image Web Service - getThematicMap()=======================
        MapImageInfo mapImageInfo = mapImage.getThematicMap(envelope,mapImageOptions,thematicField,thematicOptions,token);

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

    // ========================== Print Exception if thrown===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>
<br><center><a href="javascript:window.close()" class=boldfont> Close </a>
</body>
</html>