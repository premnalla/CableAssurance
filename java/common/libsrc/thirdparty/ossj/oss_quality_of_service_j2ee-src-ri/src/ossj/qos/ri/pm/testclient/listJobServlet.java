package ossj.qos.ri.pm.testclient;
/* $Id: listJobServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The simplest possible servlet.
 *
 * @author James Duncan Davidson
 */

public class listJobServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String jobName = request.getParameter("jobName"); 
        Vector allJob  = new Vector();

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);

        //System.out.println("wrx--realPath:" + realPath);
        IrenePmClient pmClient = IrenePmClient.getInstance(realPath + "/pmclient.properties");

        try {
  
            if(jobName != null) {
                pmClient.removeSingle(jobName);
            }
            pmClient.listAllPMJob();  
            allJob = pmClient.allJobName;

        } catch ( Exception e ) {
            System.out.println("exception="+e);
            System.exit(1);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>list all job </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<table border=\"1\" cellspacing=\"1\" width=\"100%\">");
       out.println("<tr>");
       out.println("<td width=\"15%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Job Name");
       out.println("</strong>");
       out.println("</td>");

       out.println("<td width=\"15%\">");
       out.println("<font color = \"#FF0080\">");
       out.println("<strong>");
       out.println("Observed Object");
       out.println("</strong>");
       out.println("</td>");

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

       for(int i = 0 ; i < allJob.size(); i++) {
           String currentJob = (String)allJob.get(i);
           String currentObject = pmClient.getObjectOfJob(currentJob);
           out.println("<tr>");
           out.println("<td width=\"15%\">");
           out.println(currentJob);
           out.println("</td>");

           out.println("<td width=\"15%\">");
           out.println(currentObject);
           out.println("</td>");

           out.println("<td width=\"35%\">");
           out.println("<a href=\"watchDataServlet?jobName=" + currentJob + "&objectName=" + currentObject + "\">watch the current data</a>");
           out.println("</td>");

           out.println("<td width=\"35%\">");
           out.println("<a href=\"listJobServlet?jobName=" + currentJob + "\" target=\"right\">delete the job </a>");
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



