package ossj.qos.ri.pm.testclient;
/* $Id: setThresholdPageServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
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

public class setThresholdPageServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String objectName = request.getParameter("objectName");
        String jobName = request.getParameter("jobName");
        String attName = request.getParameter("attName"); 

        //System.out.println("wrx--objectName: " + objectName);
        //System.out.println("wrx--jobName:" + jobName);
        //System.out.println("wrx--attName:" + attName);

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
        IrenePmClient pmClient  = IrenePmClient.getInstance(realPath + "/pmclient.properties");
        ThresholdClient thresholdClient = ThresholdClient.getInstance(realPath + "/thresholdclient.properties");

        PerformanceMonitorValue perfValue = pmClient.findMonitorJobByName(jobName);
        System.out.println("perfvalue: " + perfValue);
        PerformanceMonitorKey perfKey = perfValue.getPerformanceMonitorKey();
        System.out.println("perfkey: " + perfKey);
        ThresholdMonitorValue thrValue = thresholdClient.findThreshold(perfKey,attName);
        //System.out.println("wrx--thrValue: " + thrValue);
        SimpleThresholdMonitorValue smValue = null;
        String tmName = "";
        String tmValue = "";
        String tmDir = "";
        String tmSev = "" ;
        if( thrValue != null) {  
            try {
            if (thrValue instanceof  SimpleThresholdMonitorValue) {
                //System.out.println("wrx--in Simple");
                smValue = (SimpleThresholdMonitorValue)thrValue;
                //System.out.println("wrx--smValue: " + smValue);
                tmName = smValue.getName();
                //System.out.println("wrx--tmName: " + tmName);
                ThresholdDefinition td = smValue.getThresholdDefinition();
                tmValue = td.getValue().toString();
                if( td.getDirection() == ThresholdDirection.RISING) {
                    tmDir = "Rising";
                 } else {
                    tmDir = "Falling";
                 }
                 AlarmConfig ac = smValue.getAlarmConfig();
                 if (ac.getPerceivedSeverity() == PerceivedSeverity.CRITICAL) {
                     tmSev = "Critical";
                 } else {
                     tmSev = "Warning";
                 }
            }
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>really create a job </title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form method=\"POST\" action=\"setThresholdServlet\">" );
            out.println("<p>name:<input type=\"text\" name=\"thresholdName\" size=\"10\" value =" + tmName + "></p>");
            out.println("<p>value:<input type=\"text\" name=\"thresholdValue\" size=\"5\" value =" + tmValue + "></p>");
            out.println("<p>Direction:</p>");
            if (tmDir.equals("Falling")) {
                out.println("<p><input type=\"radio\" value=\"Rising\" name=\"thresholdDir\">Rising");
                out.println("<input type=\"radio\" value=\"Falling\" checked name=\"thresholdDir\">Falling</p>");
            } else {
                out.println("<p><input type=\"radio\" value=\"Rising\" checked name=\"thresholdDir\">Rising");
                out.println("<input type=\"radio\" value=\"Falling\" name=\"thresholdDir\">Falling </p>");
            }

            out.println("<p>Severity:</p>");
            out.println("<p><select name=\"thresholdSev\" size=\"1\">");
            if (tmSev.equals("Critical")) {
                out.println("<option selected value=\"CRITICAL\">CRITICAL</option>");
                out.println("<option value=\"WARNING\">WARNING</option>");
            } else {
                out.println("<option value=\"CRITICAL\">CRITICAL</option>");
                out.println("<option selected value=\"WARNING\">WARNING</option>");
            }            
            out.println("</select> </p>");

            out.println("<p><input type=\"hidden\" name=\"objectName\" size=\"10\" value=" +                            objectName + ">" +
                            "<input type=\"hidden\" name=\"jobName\" size=\"10\" value=" +
                            jobName + ">" +
                            "<input type=\"hidden\" name=\"attName\" size=\"10\" value=" +
                            attName + "> </p>");
             out.println("<p><input type=\"submit\" value=\"create a threshold \" name=\"createThreshold\"></p>");

            out.println("</form> </body> </html>");


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



