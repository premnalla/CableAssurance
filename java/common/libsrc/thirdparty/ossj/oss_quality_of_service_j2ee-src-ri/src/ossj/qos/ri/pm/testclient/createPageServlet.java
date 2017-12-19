package ossj.qos.ri.pm.testclient;
/* $Id: createPageServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
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

public class createPageServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        ArrayList observeObject  = new ArrayList();

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
        //IrenePmClient.setRealPath(realPath);
        IrenePmClient pmClient  = IrenePmClient.getInstance(realPath + "/pmclient.properties");

        try {
       
            pmClient.discoverObservableObjects();  
            observeObject = pmClient.webObject;

        } catch ( Exception e ) {
            System.out.println("exception="+e);
            
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>create a job </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<form method=\"POST\" action=\"createJobServlet\">");
       out.println("<p>select object:<select name=\"objectList\" size=\"1\">");
       for(int i = 0 ;i < observeObject.size(); i++) {
           out.println("<option value="+ observeObject.get(i) + ">" + observeObject.get(i) + "</option>");
       }
       out.println("</select> </p>");
       out.println("<p>input job name:<input type=\"text\" name=\"jobName\" size=\"10\"></p>");
      out.println("<p><input type=\"submit\" value=\"create a job\" name=\"createJob\"></p> </form>");
      out.println("</body> </html>"); 

    }

    /*public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }*/

}



