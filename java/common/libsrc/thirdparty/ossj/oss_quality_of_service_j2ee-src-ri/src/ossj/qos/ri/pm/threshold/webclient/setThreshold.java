

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class setThreshold  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        String objectname = request.getParameter("objectName");  

       // int objectname = Integer.parseInt(request.getParameter("objectName"));  
        int ooAttribute = Integer.parseInt(request.getParameter("ooAttribute"));  
        
        String thresholdName = request.getParameter("ThresName");
        String thresholdVal = request.getParameter("ThresValue");
        String thresholdOffset = request.getParameter("ThresOffset");
        int direction = Integer.parseInt(request.getParameter("direction"));
        int granularity = Integer.parseInt(request.getParameter("Granularity"));
        int severity= Integer.parseInt(request.getParameter("Severity"));
        
System.out.println("--->>>>>++++After   ThresholdClient.getInstanc ");
String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);

        CommandThresholdClient tmClient  = CommandThresholdClient.getInstance(realPath +"/thresholdclient.properties");
System.out.println("++++++++++++++++After   ThresholdClient.getInstanc ");   
    
boolean ifCreated = true;        
try{ 
System.out.println("====> objectname" + ooAttribute  );       
    tmClient.createThresholdMonitor(thresholdName, objectname,ooAttribute, thresholdVal,thresholdOffset,direction, granularity, severity);
    } catch ( ThresholdClientException  e ) {
       System.out.println(e);
       System.out.println("+++++++++++++ create Threshold failed "); 
        ifCreated = false;
    }
    
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
            if( ifCreated )  {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>really set a threshold </title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form method=\"POST\">" );
            out.println(":-)congratulations,you have set a threshold ");
            out.println("</form> </body> </html>");

    } else {
            out.println("<html>");
            out.println(":-(failed to set a threshold!");
            out.println("</html>");

        }


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



