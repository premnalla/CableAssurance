<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : proximityRes.jsp
	* This is an ArcWeb Services Sample result for Proximity ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl
	* WSDL location of Proximity = http://arcweb.esri.com/services/v2/Proximity.wsdl
	*
	* This sample illustrates how to use the Proximity Web Service to find locations within a
	* specified distance of an address. The address is geocoded using AddressFinder ArcWeb service.
        * Geocoding is the process of getting the x and y coordinates for an address.
	*/
%>

<html>
    <head><title>Proximity Result- ArcWeb Services Sample</title>	<style type="text/css">
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
             var h = 600;
             window.moveTo(10,10);
             window.resizeTo(w,h);
             }
            //-->
        </script>
    </head>
    <body onload=resizeWin();> <center>
    <font class='boldfont'>Proximity Result- ArcWeb Services Sample </font><br><br>

<%
String action = request.getParameter("action");

// ================ if the form is submitted =================
if (action != null) {

    // first get the X and Y location of the Address using AddressFinder ArcWeb service
    try {
         // ================== Parsing input form parameters ===================
        String dataSource = request.getParameter("dataSource");
        String startRecord = request.getParameter("startRecord");
        String count = request.getParameter("count");

        boolean useWhereExpression = false ;
            if (request.getParameter("useWhereExpression") != null) useWhereExpression = true ;

        boolean useRadius = false ;
            if (request.getParameter("useRadius") != null) useRadius = true ;

        // ==================== Get the Authentication Token ================================
        //authenticationManger.jsp sets the value of the token in the variable token  %>
            <jsp:include page="authenticationManger.jsp" flush="true" />
            <% String  token = ((String) request.getAttribute("token")) ; %>

<%	// ===================Geocode Address ======================

        /* geocodeAddress.jsp is used as an include file to geocode the address .
           For successful geocoding, it returns location object and sets geocodeSuccess flag to true.
           For failed geocoding, it only returns geocodeSuccess flag and set its value to false */    %>
        <jsp:include page="geocodeAddress.jsp" flush="true" >
            <jsp:param name="street" value='<%=request.getParameter("street") %>' />
            <jsp:param name="city" value='<%= request.getParameter("city") %>' />
            <jsp:param name="state" value='<%= request.getParameter("state") %>' />
            <jsp:param name="zip" value='<%= request.getParameter("zip") %>' />
            <jsp:param name="country" value='<%= request.getParameter("country") %>' />
            <jsp:param name="dataSource" value='<%= request.getParameter("geoCodeDataSource") %>' />
            <jsp:param name="token" value='<%= token %>' />
        </jsp:include>
<%
        //For successful geocode, get the X and Y from the first candidate
        boolean geocodeSuccess = ((String) request.getAttribute("geocodeSuccess")).equals("true") ;
        ResultSet resultSet = null ;

        if (geocodeSuccess) {
            Point geocodePoint = (Point) request.getAttribute("point");
            double x = geocodePoint.getX() ;
            double y = geocodePoint.getY() ;

            // =================== locate and bind to Proximity ArcWeb services ================
            IProximity proximity = new ProximityLocator().getIProximity();

                //  Point object to hold X and Y
                Point point = new Point() ;
                    point.setX(x) ;
                    point.setY(y);

                //  ProximityOptions
                ProximityOptions proximityOptions = new ProximityOptions();

                    // ProximityOptions - set data source
                    proximityOptions.setDataSource(dataSource) ;

                    // ProximityOptions - set whereClause
                    if (useWhereExpression) {
                        String whereExpression = request.getParameter("whereExpression");
                        proximityOptions.setWhereClause(whereExpression) ;
                    }

                    // ProximityOptions - set Resultset Range
                    if ((startRecord != null) && (!startRecord.equals("")) && (count != null) && (!count.equals(""))){
                        ResultSetRange resultSetRange = new ResultSetRange();
                            // starting row
                            resultSetRange.setStartIndex(Integer.parseInt(startRecord)) ;
                            // Number of records to return
                            resultSetRange.setCount(Integer.parseInt(count)) ;
                        proximityOptions.setResultRange(resultSetRange) ;
                    }

            // =======================Calling Proximity Web Service =======================
            if (useRadius) { // for radius search use findWithin()
                int radius = Integer.parseInt(request.getParameter("radius"));
                resultSet = proximity.findWithin(point,radius,proximityOptions,token) ;

            } else {  //  use findNearest() when radius is not specified
                resultSet = proximity.findNearest(point,proximityOptions,token) ;
            }

                // get the field Data from Result set
                FieldDesc fields[] = resultSet.getFields() ;
                // get the Row Data from Result set
                RowData rows[] = resultSet.getRows() ;

            // ============= Display Results ==============================================  %>

                    <br><font class='boldfont'>Results</font><br>

                    <% // If the record count > 0 , display the field names and the row values
                    if ((rows != null) && (rows.length > 0)) {		%>
                        <table width='100%' border='0' cellpadding='0' cellspacing='0' bgcolor='black'>
                        <tr><td>
                        <table width='100%' border='0' cellpadding='3' cellspacing='1' bgcolor=black class='textfont'>

                        <%// display the fields names     %>
                        <tr bgcolor='4271ad'><td ><font color='ffffff'><b>Serial No. </b></td>
                        <% for (int i=0;i<fields.length;i++) {	%>
                            <td> <font color='ffffff'><b>&nbsp;&nbsp;<%= fields[i].getFieldName()%>&nbsp;&nbsp;</b></font></td>
                        <% } %>
                        </tr>

                        <%// display the row values, loop through the rows
                        String rowValue[]  ;
                        for (int j=0;j<rows.length;j++) {
                            rowValue = rows[j].getFieldValues() ; %>
                            <tr  bgcolor='ddebf1'><td><%=(j+1)%></td>
                            <%  for (int k=0;k<rowValue.length;k++){  %>
                                <td><font size=-1><%= rowValue[k] %></font>&nbsp;&nbsp;</td>
                            <% } %>
                            </tr>
                        <% } %>

                        </table>
                        </td></tr></table>
                    <% } else { // if record count is 0  %>
                        <font color=red> * No Records Found in the Search Criteria </font><br><br><br>
                    <% } %>

    <%  } else { // if address did not match   %>
            <font color=red> * Address did not Match </font><br><br><br>
    <%  }

    }
	// ========================== Print Exception if thrown===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>
	<br><a href="javascript:window.close()" class=boldfont> Close </a></center>
</body>
</html>