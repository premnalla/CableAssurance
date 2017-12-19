package ossj.qos.ri.fm.testclient;

/* $Id: ListAlarmCountServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.oss.*;
import javax.oss.fm.monitor.*;


public class ListAlarmCountServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        try {
	System.out.println("ListAlarmCountServlet.doGet()");
        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);

        FmClient fmClient = FmClient.getInstance(realPath + "/fmClient.properties");
	System.out.println("ListAlarmCountServlet. getInstance end ");

	System.out.println("fmClient.QueryAlarmsCount");
		 AlarmCountsValue acv = fmClient.QueryAlarmsCount();

	System.out.println("fmClient.QueryAlarmsCount end ");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>List Alarm Count </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<table border=\"1\" cellspacing=\"1\" width=\"50%\">");
       out.println("<tr>");
       out.println("<td width=\"25%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Severity");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"25%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Number");
       out.println("</strong>");
       out.println("</td>");

       out.println("</tr>");
	
        out.println("<tr><td width=\"25%\">Cleared</td><td width=\"25%\"> " + (new Integer( acv.getClearedCount() )).toString() + "</td></tr>");
        out.println("<tr><td width=\"25%\">Critical</td><td width=\"25%\"> " + (new Integer( acv.getCriticalCount() )).toString() + "</td></tr>");
        out.println("<tr><td width=\"25%\">Major</td><td width=\"25%\"> " + (new Integer( acv.getMajorCount() )).toString() + "</td></tr>");
        out.println("<tr><td width=\"25%\">Minor</td><td width=\"25%\"> " + (new Integer( acv.getMinorCount() )).toString() + "</td></tr>");
        out.println("<tr><td width=\"25%\">Warning</td><td width=\"25%\"> " + (new Integer( acv.getWarningCount() )).toString() + "</td></tr>");
        out.println("<tr><td width=\"25%\">Indeterminate</td><td width=\"25%\"> " + (new Integer( acv.getIndeterminateCount() )).toString() + "</td></tr>");
	
        out.println("</p></body> </html>"); 

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



