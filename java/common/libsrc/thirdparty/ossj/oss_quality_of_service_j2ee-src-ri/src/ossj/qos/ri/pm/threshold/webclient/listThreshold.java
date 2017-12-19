/* $Id: listThreshold.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class listThreshold  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
    
     String thresholdName = request.getParameter("thresholdName");
     Vector allThres  = new Vector();

 System.out.println( "+++++++--> into listThreshold ");
 String realPath = request.getRealPath("/index.html");
 int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
CommandThresholdClient tmClient  = CommandThresholdClient.getInstance(realPath +"/thresholdclient.properties");

        try {
  
            if(thresholdName != null) {
                tmClient.removeThresholdMonitor(thresholdName);
            }
            tmClient.listAllThreshold();
            allThres = tmClient.allThresName;

        } catch ( Exception e ) {
            System.out.println("###########---> into listThreshold exception");
            System.out.println("exception="+e);
          //  System.exit(1);
        }
/*        
tmClient.listAllThreshold();
System.out.println( "+++++++--> after tmClient.listAllThreshold()");

if (tmClient.ThresNames.length == 0) {
     System.out.println( " There is not a threshold");
     }
     else {
      for (int i=0; i<tmClient.ThresNum; i++) {
       System.out.println( tmClient.ThresNames[i]);
       }
     }
*/

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>list all Thresholds </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<table border=\"1\" cellspacing=\"1\" width=\"100%\">");
       out.println("<tr>");
       out.println("<td width=\"15%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Threshold Name");
       out.println("</strong>");
       out.println("</td>");
/*
       out.println("<td width=\"15%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Observed Object");
       out.println("</strong>");
       out.println("</td>");
*/
       out.println("<td width=\"35%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("data");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"35%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("delete operation");
       out.println("</strong>");
       out.println("</td>");

       out.println("</tr>");

       for(int i = 0 ; i < allThres.size(); i++) {
           String currentThres = (String)allThres.get(i);
//           String currentObject = pmClient.getObjectOfJob(currentJob);
           out.println("<tr>");
           out.println("<td width=\"15%\">");
           out.println(currentThres);
           out.println("</td>");
/*
           out.println("<td width=\"15%\">");
           out.println(currentObject);
           out.println("</td>");
*/
           out.println("<td width=\"35%\">");
           out.println("<a href=\"watchThreshold?thresholdName=" + currentThres + "\">watch the current Threshold</a>");
           out.println("</td>");

           out.println("<td width=\"35%\">");
           out.println("<a href=\"listThreshold?thresholdName=" + currentThres + "\" target=\"right\">delete the threshold </a>");
           out.println("</td>");

           out.println("</tr>");

           
        }
        out.println("</table>");
        out.println("</body> </html>"); 
     

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



