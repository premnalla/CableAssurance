package ossj.qos.ri.pm.testclient;
/* $Id: setThresholdServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.oss.*;
import javax.oss.fm.monitor.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.threshold.*;



public class setThresholdServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String objectName = request.getParameter("objectName");
        String jobName = request.getParameter("jobName");
        String attName = request.getParameter("attName");

        String thresholdName = request.getParameter("thresholdName");
        String thresholdValue = request.getParameter("thresholdValue");
        String thresholdDir = request.getParameter("thresholdDir");
        String thresholdSev = request.getParameter("thresholdSev");

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);

        //System.out.println("wrx--realPath:" + realPath);
        IrenePmClient pmClient  = IrenePmClient.getInstance(realPath + "/pmclient.properties");
        ThresholdClient thresholdClient = ThresholdClient.getInstance(realPath + "/thresholdclient.properties");

        PerformanceMonitorValue perfValue = pmClient.findMonitorJobByName(jobName);
        PerformanceMonitorKey perfKey = perfValue.getPerformanceMonitorKey();
        int useGranu = 0;
        try {
        int granu[] = pmClient.pmSession.getSupportedGranularities( perfValue);
        useGranu = granu[0];
        } catch (Exception e) {
            System.out.println(e);
        }
        ThresholdMonitorValue tmValue = thresholdClient.findThreshold(perfKey,attName);
        ThresholdMonitorKey tmKey = null;
        if (tmValue !=null) {
            tmKey = tmValue.getThresholdMonitorKey();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
        try {
            if(tmKey != null) {
                thresholdClient.removeThresholdMonitor(tmKey);
            }
            thresholdClient.createThresholdMonitor(objectName,attName,perfKey,thresholdName,thresholdValue,thresholdDir,thresholdSev,useGranu);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>really create a job </title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form method=\"POST\">" );
            out.println(":-)congratulations,you have create a threshold ");
            out.println("</form> </body> </html>");
        } catch (Exception e) {

            out.println("<html>");
            out.println(":-(failed to create a threshold !");
            out.println(e);
            out.println("</html>");

        }


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



