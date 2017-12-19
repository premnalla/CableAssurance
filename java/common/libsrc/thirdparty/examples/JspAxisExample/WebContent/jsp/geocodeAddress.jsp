<%@page import="esri.arcwebservices.v2.*" %>

<%
/*
* File : geocodeAddress.jsp
* This is an ArcWeb services sample file.

* WSDL location of AddressFinder = http://arcweb.esri.com/services/v2/AddressFinder.wsdl

* usage
    geocodeAddress.jsp is included inside the sample jsp pages, which needs to geocode an address .

* input  - street, city ,state ,zip , country, dataSource and token (Authentication)
* output -
*        For successful geocoding, it returns location object and sets geocodeSuccess flag object to true.
*        For failed geocoding, it only returns geocodeSuccess flag object and set its value to false
*/

try {
        String street = request.getParameter("street") ;
        String city = request.getParameter("city") ;
        String state = request.getParameter("state") ;
        String zip = request.getParameter("zip") ;
        String country = request.getParameter("country") ;
        String dataSource = request.getParameter("dataSource") ;
        String token = request.getParameter("token") ;

        //======================locate and bind to AddressFinder ArcWeb service ====================
        IAddressFinder addressFinder = new AddressFinderLocator().getIAddressFinder();

	        Address address = new Address();   //address object

                if((street != null) && (!street.equals("")))
                    address.setStreet(street) ;

                if((city != null) && (!city.equals("")))
                    address.setCity(city) ;

                if((zip != null) && (!zip.equals("")))
                    address.setZone(zip) ;

                if((state != null) && (!state.equals("")))
                    address.setState_prov(state) ;

                if((country != null) && (!country.equals("")))
                    address.setCountry(country) ;

            // addressFinderOptions - to hold all address information
            AddressFinderOptions addressFinderOptions = new AddressFinderOptions();
                if ((dataSource != null) && (!dataSource.equals("")))
                  addressFinderOptions.setDataSource(dataSource) ;
                else
                  addressFinderOptions.setDataSource("GDT.Address.US") ;

	// =======================Calling AddressFinder Web Service =======================
	LocationInfo locationInfo =  addressFinder.findAddress(address,addressFinderOptions,token);

        // ==============================set results=======================================
        // if the geocoding returns address match
         if ((locationInfo.getMatchType().equals("CANDIDATES")) || (locationInfo.getMatchType().equals("EXACT"))) {

            //For successful geocode, get the X and Y from the first candidate of the location object
            Location[] location = locationInfo.getCandidates();
            Point  point = location[0].getPoint() ;

            request.setAttribute("geocodeSuccess","true");
            request.setAttribute("point",point);
            request.setAttribute("description",location[0].getDescription1()); // description of the first candidate
        }
        else {
            // if the geocoding does not return address match
            request.setAttribute("geocodeSuccess","false");
        }
}
// ========================== Print Exception if thrown================================
catch (Exception e) {
        request.setAttribute("geocodeSuccess","false");
	out.println("<b>Error geocoding Address- "+e.getMessage()+"</b><br>") ;
}
%>