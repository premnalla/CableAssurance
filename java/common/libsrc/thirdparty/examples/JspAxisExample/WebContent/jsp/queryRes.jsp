<%@page contentType="text/html"%>
<%@page import="esri.arcwebservices.v2.*" %>

<%	/*
	* File : queryRes.jsp
	* This is an ArcWeb Services Sample result for Query ArcWeb Service.
	*
	* WSDL location of services used.
	* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl
	* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl
	* WSDL location of Query  = http://arcweb.esri.com/services/v2/Query.wsdl
	*
	* This sample illustrates how to use the Query Web Service to return information about the selected location.
	* The address is geocoded using AddressFinder ArcWeb service.
        *  Geocoding is the process of getting the x and y coordinates for an address.
	*/
%>

<html>
    <head><title>Query Result- ArcWeb Services Sample</title>
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
             var w = 800;
             var h = 600;
             window.moveTo(10,10);
             window.resizeTo(w,h);
             window.focus();   }
            //-->
        </script>
    </head>
    <body onload=resizeWin();><center>
<font class='boldfont'>Query Result -ArcWeb Services Sample </font><br><br>
<%
String action = request.getParameter("action");

// ================ if the form is submitted =================
if (action != null) {
    try {

        // ==================== Get the Authentication Token ================================
        //    authenticationManger.jsp sets the value of the token in the variable token  %>
        <jsp:include page="authenticationManger.jsp" flush="true" />
            <% String  token = ((String) request.getAttribute("token")) ; %>

<%
        String dataSource = request.getParameter("dataSource");
        String geoLevel = request.getParameter("geoLevel") ;

        // ===================Geocode Address ======================

        /* geocodeAddress.jsp is used as an include file to geocode the address .
        For successful geocoding, it returns location object and sets geocodeSuccess flag to true.
        For failed geocoding, it only returns geocodeSuccess flag and set its value to false   */  %>
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

            // ====================== locate and bind to Query ArcWEB service ===================
            IQuery query = new QueryLocator().getIQuery();

                //  Point object to hold X and Y
                Point point = new Point() ;
                    point.setX(x) ;
                    point.setY(y) ;

                //  QueryOptions
                QueryOptions queryOptions = new QueryOptions();

                    //  QueryOptions  - Set data source
                    queryOptions.setDataSource(dataSource) ;

                    //  QueryOptions  - Set geographyLevel
                    queryOptions.setGeographyLevel(geoLevel) ;

            // =======================Calling Query Web Service =======================
            resultSet = query.pointQuery(point,queryOptions,token) ;

            // get the field Data from Result set
            FieldDesc fields[] = resultSet.getFields() ;

            // get the Row Data from Result set
            RowData rows[] = resultSet.getRows() ;

            // ============= Display Results ==============================================  %>

            <font class='boldfont'>Results</font>
            <table width='100%' border='0' cellpadding='0' cellspacing='0' bgcolor='black'>
            <tr><td>
            <table width='100%' border='0' cellpadding='3' cellspacing='1' bgcolor=black class='textfont'>
                <tr bgcolor='4271ad'>

            <%// display the field names
                for (int i=0;i<fields.length;i++) { %>
                    <td> <font size=-1 color='white'><b>&nbsp;&nbsp; <%=fields[i].getFieldName()%> &nbsp;&nbsp;</b></font></td>
            <%  } %>
                </tr>

            <% // display the row values
            if (rows.length != 0) {
                String rowValue[]  ;
                for (int j=0;j<rows.length;j++) {
                    rowValue = rows[j].getFieldValues() ;    %>

                    <tr  bgcolor='ddebf1'>
                     <% // loop through the rows
                        for (int k=0;k<rowValue.length;k++){  %>
                            <td><font size=-1><%= rowValue[k] %></font>&nbsp;&nbsp;</td>
                    <% } %>
                    </tr>

               <% }
           } else { %>
           <tr  bgcolor='ddebf1'><td colspan='<%= fields.length %>'><font size=-1>No Data</font>&nbsp;&nbsp;</td></tr>
           <% }            %>

            </table>
            </td></tr></table><br><br>

    <%  } else { // if address did not match  %>
            <font color=red> * Address did not match &nbsp; &nbsp; </font><br><br>
    <%  }
    }
	// ========================== Print Exception if thrown===================
    catch(Exception e) {
        out.println("<b> Error - "+ e.getMessage()+"</b><br>") ;
        e.printStackTrace();
    }
} %>
	<a href="javascript:window.close()" class=boldfont> Close </a>
</center>
</body>
</html>