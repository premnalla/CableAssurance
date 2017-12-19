<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : siteReportRes.jsp
	* This is an ArcWeb Services Sample result for Report ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of Report = http://arcweb.esri.com/services/v2/Report.wsdl
	*/
%>

<html>
    <head><title>Report - SiteReport Result - ArcWeb Services Sample</title>
	<style type="text/css">
		<!--
		.boldfont{FONT: bold 14px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
		.textfont{FONT: 12px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
                .textboldfont{FONT: bold 12px Verdana, Arial, Helvetica, sans-serif;COLOR: #000000;}
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
    <center><font class='boldfont' ><center>Report - SiteReport  Result- ArcWeb Services Sample</font><br><br>

<%
String action  = request.getParameter("action");

// ================ if the form is submitted  =================
if (action  != null) {
    try {

        // ==================== Get the Authentication Token ================================
            //authenticationManger.jsp sets the value of the token in the variable token  %>
          <jsp:include page="authenticationManger.jsp" flush="true" />
		  <% String  token = ((String) request.getAttribute("token")) ; %>

<%        // ================== getting input form parameters ===================
            double lat = 0 ;
            double lon = 0 ;
            if ((request.getParameter("lat") != null) && (!request.getParameter("lat").equalsIgnoreCase(""))) {
                lat = Double.parseDouble(request.getParameter("lat")) ;
            }
            if ((request.getParameter("lon") != null) && (!request.getParameter("lon").equalsIgnoreCase(""))) {
                lon = Double.parseDouble(request.getParameter("lon")) ;
            }

            int radius1 = 0 ;
            int radius2 = 0 ;
            int radius3 = 0 ;
            if ((request.getParameter("radius1") != null) && (!request.getParameter("radius1").equalsIgnoreCase(""))) {
                radius1 = Integer.parseInt(request.getParameter("radius1")) ;
            }
            if ((request.getParameter("radius2") != null) && (!request.getParameter("radius2").equalsIgnoreCase(""))) {
                radius2 = Integer.parseInt(request.getParameter("radius2")) ;
            }
            if ((request.getParameter("radius3") != null) && (!request.getParameter("radius3").equalsIgnoreCase(""))) {
                radius3 = Integer.parseInt(request.getParameter("radius3")) ;
            }

            String calcType = request.getParameter("calcType") ;
            String dataSource = request.getParameter("dataSource") ;
            String reportFormat = request.getParameter("reportFormat") ;
            String siteName = request.getParameter("siteName") ;


        //================= Bind to Report Web service =================
        IReport report = new ReportLocator().getIReport();

                // Set Site values
                Site site = new Site() ;

                    //Set Site - ringRadii
                        double[] radii = new double[3] ;
                            radii[0] = radius1 ;
                            radii[1] = radius2 ;
                            radii[2] = radius3 ;
                    site.setRingRadii(radii) ;
                    site.setCalcType(calcType) ; //calcType - type of rings

                    //Set Site - siteLocation
                        Point point = new Point();
                            point.setX(lon);
                            point.setY(lat) ;
                    site.setSiteLocation(point) ;

            //Set KeyValues for the Header.
             KeyValue keyvalue[] = new KeyValue[15];

                keyvalue[0]  = new KeyValue();
                keyvalue[0].setKey("latitudename");
                keyvalue[0].setValue("Latitude") ;

                keyvalue[1]  = new KeyValue();
                keyvalue[1].setKey("latitudevalue");
                keyvalue[1].setValue(request.getParameter("lat"));

                keyvalue[2]  = new KeyValue();
                keyvalue[2].setKey("longitudename");
                keyvalue[2].setValue("Longitude") ;

                keyvalue[3]  = new KeyValue();
                keyvalue[3].setKey("longitudevalue") ;
                keyvalue[3].setValue(request.getParameter("lon")) ;

                keyvalue[4]  = new KeyValue() ;
                keyvalue[4].setKey("subtitle") ;
                keyvalue[4].setValue(request.getParameter("subtitle")) ;

                keyvalue[5]  = new KeyValue() ;
                keyvalue[5].setKey("locationname") ;
                keyvalue[5].setValue(request.getParameter("locationname")) ;

                // customlogo headerkey's value can't be an empty string
                // it must be a valid URL to a GIF or JPEG image.
                if ((request.getParameter("customlogo") != null) && (!request.getParameter("customlogo").equalsIgnoreCase(""))) {
                    keyvalue[6]  = new KeyValue() ;
                    keyvalue[6].setKey("customlogo") ;
                    keyvalue[6].setValue(request.getParameter("customlogo")) ;
                }

                keyvalue[7]  = new KeyValue() ;
                keyvalue[7].setKey("sitetype") ;
                keyvalue[7].setValue(request.getParameter("sitetype")) ;

                keyvalue[8]  = new KeyValue() ;
                keyvalue[8].setKey("sitetypevalue") ;
                keyvalue[8].setValue(request.getParameter("sitetypevalue")) ;

                keyvalue[9]  = new KeyValue() ;
                keyvalue[9].setKey("siteunitsname1") ;
                keyvalue[9].setValue(request.getParameter("siteunitsname1")) ;

                keyvalue[10]  = new KeyValue() ;
                keyvalue[10].setKey("siteunitsvalue1") ;
                keyvalue[10].setValue(request.getParameter("radius1")) ;

                keyvalue[11]  = new KeyValue() ;
                keyvalue[11].setKey("siteunitsname2") ;
                keyvalue[11].setValue(request.getParameter("siteunitsname2")) ;

                keyvalue[12]  = new KeyValue() ;
                keyvalue[12].setKey("siteunitsvalue2") ;
                keyvalue[12].setValue(request.getParameter("radius2")) ;

                keyvalue[13]  = new KeyValue() ;
                keyvalue[13].setKey("siteunitsname3") ;
                keyvalue[13].setValue(request.getParameter("siteunitsname3")) ;

                keyvalue[14]  = new KeyValue() ;
                keyvalue[14].setKey("siteunitsvalue3") ;
                keyvalue[14].setValue(request.getParameter("radius3")) ;

                // Set ReportOptions
                ReportOptions reportOptions[] = new ReportOptions[1] ;
                    reportOptions[0] = new ReportOptions() ;
                    reportOptions[0].setDataSource(dataSource);
                    reportOptions[0].setReportFormat(reportFormat);
                    reportOptions[0].setReportHeader(keyvalue) ;

                // =============Calling Report webservice ==================
                ReportInfo reportInfos[] = report.getSiteReports(site,reportOptions,true,token);

                // ============= Display Results ===============================  %>
                    <br><font class='boldfont'>Results</font>
                    <table width='550' border='0' cellpadding='2' cellspacing='2'>
<%                  ReportInfo reportInfo = null ;
                    if (reportInfos[0] != null) {
                        reportInfo = reportInfos[0] ; %>
                        <tr><td colspan='5' class='textfont'>Report is available at : &nbsp;</td></tr>
                        <tr><td colspan='5' class='textfont'><a href='<%=reportInfo.getReportUrl()%>' target='_blank' ><%=reportInfo.getReportUrl()%> </a>&nbsp;</td></tr>
<%                  } else {%>
                        <tr><td colspan='5' class='textfont'>No reports generated &nbsp;</td></tr>
<%                  } %>
                        <tr  class="textfont"><td></td></tr>
                    </table>

<%    }
    // ========================== Print Exception =================== //
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>

    <br><center><a href="javascript:window.close()" class=boldfont> Close </a></center>
    </body>
</html>