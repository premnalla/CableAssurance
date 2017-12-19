<%@page import="esri.arcwebservices.v2.*" %>

<%
/*
* File : authenticationManger.jsp
* This is an ArcWeb services sample file.

* Usage
     Included inside the JSP page, which needs token to access web service.

* WSDL location of Authentication = https://arcweb.esri.com/services/v2/Authentication.wsdl

* Description
    AuthenticationManger.jsp first checks if the token was requested before.
    If the token was not requested, a new token is requested.
    If the token was requested, it checks whether the token is expired or not
        and requests a new token if the token has expired.
    The token and the time the token was requested are stored in application scope,
        so it is common through out the application. In the sample, token expiration time is 60 min.
    To change the token expiration time modify the variable tokenExpireMin and restart the webserver .

* input = null
* output = token
*/

String token = null ;

try {
    String user = "[USERNAME]" ;								//arcweb username (case sensitive)
    String password = "[PASSWORD]" ;								//arcweb password (case sensitive)
    int tokenExpireMin = 60 ;       								// token expiration time in minutes

    // ==================== check if valid token exist in application scope ===========
    boolean reqNewToken = false;                                        // set request new token to false by default
    java.util.Date curDate = new java.util.Date();                      // get the server time
    String curMin = String.valueOf(curDate.getTime()/(1000*60));	// convert to minutes

    // get the time, if the token was last requested and stored in application object
        String tokenReqMin = (String) (application.getAttribute("appTokenReqMin")) ;

    // if the token was not requested, set request new token to true
    	if (tokenReqMin == null) {
    	    reqNewToken = true ;
    	}

    // if the token was requested before, get the time difference in minutes between
    //   the current time and previous request made for getting the token
    	else {
    	   long timeDiff = (Long.parseLong(curMin) - Long.parseLong(tokenReqMin)) ;

	// if the time difference is greater than the token expiry time, set request new token to true
    	    if (timeDiff >= (tokenExpireMin-1)) {
    	        reqNewToken = true ;
    	    }
    	}


	// ==================== request a new token ====================================
	if (reqNewToken) {
        // ================ locate and bind to Authentication Web Service ==========
        IAuthentication authentication = new AuthenticationLocator().getIAuthentication();

            // ============ calling Authentication Web Service ==========
            token= authentication.getToken(user,password,tokenExpireMin) ;

                // synchronize the Application object
                synchronized(application){
                    // update the application object with new token and the current min
                    application.setAttribute("appToken",token);
                    application.setAttribute("appTokenReqMin",curMin);
                }
	}
	// ==================== use an existing token from application scope======
	else {
            token = (String) application.getAttribute("appToken");
	}

	// set Token to be used by include pages
	request.setAttribute("token",token);
}
// ======================== Print Exception if thrown================================
catch (Exception e) {
    out.println("<b>Error in Authentication - "+e.getMessage()+"</b><br>") ;
}
%>