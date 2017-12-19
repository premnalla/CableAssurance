package ossj.qos.ri.pm.testclient;
/* $Id: createSelectServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
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

public class createSelectServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String observeObject = request.getParameter("objectList");
        String jobName = request.getParameter("jobName");

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
   
        //System.out.println("wrx--realPath:" + realPath);
        PmClient pmClient  = PmClient.getInstance(realPath + "/pmclient.properties");

ArrayList attributes = pmClient.discoverObjects("kernel-reader-simple") ;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

       
            if(pmClient.createPmMonitorJobByObject(jobName,observeObject) != null)  {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>really create a job </title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form method=\"POST\">" );
            out.println(":-)congratulations,you have create a job");
            out.println("</form> </body> </html>");

    } else {
            out.println("<html>");
            out.println(":-(failed to create a job!");
            out.println("</html>");

        }


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



