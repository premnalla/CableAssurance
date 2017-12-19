package ossj.qos.ri.fm.testclient;

/* $Id: ListAlarmsServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.oss.*;
import javax.oss.fm.monitor.*;
import java.util.Hashtable;


public class ListAlarmsServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        try {

	HashMap serverityMap = new HashMap();
	serverityMap.put(new Integer( PerceivedSeverity.CRITICAL), new String( "CRITICAL"));
	serverityMap.put(new Integer( PerceivedSeverity.MINOR), new String( "MINOR" ));
	serverityMap.put(new Integer( PerceivedSeverity.WARNING), new String( "WARNING" ) );
	serverityMap.put(new Integer( PerceivedSeverity.INDETERMINATE), new String( "INDETERMINATE" ) );
	serverityMap.put(new Integer( PerceivedSeverity.MAJOR), new String(  "MAJOR"  ));
	serverityMap.put(new Integer( PerceivedSeverity.CLEARED),new String( "CLEARED" ));
	serverityMap.put(new Integer( PerceivedSeverity.CRITICAL),new String( "CRITICAL"));


	System.out.println("ListAlarmsServlet.doGet()");
        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
        FmClient fmClient = FmClient.getInstance(realPath + "/fmClient.properties");

	System.out.println("fmClient.QueryAlarms");
		AlarmValue[] almvs = fmClient.QueryAllAlarms();

	System.out.println("fmClient.QueryAlarms end ");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>List All Alarms  </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<table border=\"1\" cellspacing=\"1\" width=\"200%\">");
       out.println("<tr>");
       out.println("<td width=\"20%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Severity");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"30%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Raised Time");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"60%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Additional Text");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"20%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Object Class");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"70%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Object Instance");
       out.println("</strong>");
       out.println("</td>");

       out.println("</tr>");

       for(int i = 0 ; i < almvs.length; i++) {
	   
           out.println("<tr>");
           out.println("<td width=\"20%\">");
           out.println(serverityMap.get(new Integer(almvs[i].getPerceivedSeverity())));
           out.println("</td>");

           out.println("<td width=\"30%\">");
           out.println(almvs[i].getAlarmRaisedTime().toString());
           out.println("</td>");

           out.println("<td width=\"60%\">");
	   out.println(almvs[i].getAdditionalText());
           out.println("</td>");

           out.println("<td width=\"20%\">");
	   out.println(almvs[i].getManagedObjectClass());
           out.println("</td>");

           out.println("<td width=\"70%\">");
	   out.println(almvs[i].getManagedObjectInstance());
           out.println("</td>");

           out.println("</tr>");

        }
        out.println("</table>");
        out.println("</body> </html>"); 

        } catch ( Exception e ) {
            System.out.println("exception="+e);
		e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



