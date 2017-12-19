

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.oss.pm.measurement.*;

public class getooAttributes  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        ArrayList webOOAttributes = new ArrayList();
        String attribute = null;
        PerformanceAttributeDescriptor descriptor=null;

        String objectlist = request.getParameter("objectList");  

    // get performance attribute descriptor
        int stop = objectlist.lastIndexOf('=');
        int start = objectlist.lastIndexOf(',');
        String objectClassName = objectlist.substring(
               start+1, stop);
        
System.out.println("--->>>>>++++After   ThresholdClient.getInstanc ");
        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
        CommandThresholdClient tmClient  = CommandThresholdClient.getInstance(realPath +"/thresholdclient.properties");
System.out.println("++++++++++++++++After   ThresholdClient.getInstanc ");   
    
boolean ifCreated = true;        
try{   
    webOOAttributes = tmClient.getObservableObjectAttributes(objectClassName);
    } catch ( ThresholdClientException  e ) {
       System.out.println("+++++++++++++ get OO attributes failed "); 
        System.out.println(e);
        ifCreated = false;
    }
    
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
            if( ifCreated )  {


        out.println("<html>");
        out.println("<head>");
        out.println("<title>Create a job </title>");
        out.println("</head>");
        out.println("<body>");

       out.println("<form method=\"POST\" action=\"setThreshold\">");
       out.println("<p>select Attribute:<select name=\"ooAttribute\" size=\"1\">");
       for(int i = 0 ;i < webOOAttributes.size(); i++) {

           descriptor = (PerformanceAttributeDescriptor)webOOAttributes.get(i);
           attribute =descriptor.getName();
           out.println("<option value="+ i + ">" + attribute + "</option>");
        
       }
       out.println("</select> </p>");

       out.println("<p>input Threshold name:<input type=\"text\" name=\"ThresName\" size=\"10\"></p>");
       out.println("<p>input Threshold value:<input type=\"text\" name=\"ThresValue\" size=\"10\"></p>");
       out.println("<p>input Threshold Offset:<input type=\"text\" name=\"ThresOffset\" size=\"10\"></p>");

       out.println("<p> Direction <input type=\"radio\" name=\"direction\" value=\"0\" checked> Rising ");
       out.println(" <input type=\"radio\" name=\"direction\" value=\"1\"> Falling");

      out.println("<p>Granularity <select name=\"Granularity\" >");
      out.println("<option value=60> 60 </option>");
      out.println("<option value=900> 900 </option>");
      out.println("</select> </p>");
      
      out.println("<p><DT> Severity : ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"1\" checked> WARNING ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"2\" checked> MINOR  ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"3\" checked> MAJOR   ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"4\" checked> INDETERMINATE   ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"5\" checked> CRITICAL   ");
      out.println("<p> <DD><input type=\"radio\" name=\"Severity\"value=\"6\" checked> CLEARED  ");
      out.println("</DL>");


      out.println("<p><input type=\"submit\" value=\"create a Threshold\" name=\"createThres\"></p> ");
System.out.println(" =----> in getooAttributes.java" + objectlist );
     out.println("<p><input type=\"hidden\" name=\"objectName\" value=" + objectlist +">");  
     out.println("</form>");
      out.println("</body> </html>"); 

    } else {
            out.println("<html>");
            out.println(":-(failed to get OO attributes!");
            out.println("</html>");

        }


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



