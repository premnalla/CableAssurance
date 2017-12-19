/* $Id: watchThreshold.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
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

public class watchThreshold  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String ThresName = request.getParameter("thresholdName");
        Vector  ThresAttriNames  = new Vector();
        Vector  ThresAttriValues  = new Vector();
System.out.println(" --> into watchThreshold" + ThresName);
String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);
        //System.out.println("wrx--realPath:" + realPath);
CommandThresholdClient tmClient  = CommandThresholdClient.getInstance(realPath +"/thresholdclient.properties");
System.out.println(" --> after ThresholdClient.getInstance");

ThresAttriNames = tmClient.ThresAttriNames;
ThresAttriValues = tmClient.ThresAttriValues;

try{
    tmClient.printThresholdValue(tmClient.findThresholdJobByName(ThresName));
    }
   catch ( Exception e ) {
             System.out.println(" --> printThresholdValue exception");
             System.out.println("exception="+e);
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
/*
        try {
*/       

            out.println("<html>");
            out.println("<head>");
            out.println("<title>The Threshold Values </title>");
            out.println("</head>");
            out.println("<body>");
/*
            out.println("<form method=\"POST\">" );
            String currentData = pmClient.currentData;
            if(currentData.indexOf("|") > 0 ) {
                Vector vv = new Vector();
                StringTokenizer tokens = new StringTokenizer(currentData , "|");
                while (tokens.hasMoreTokens()) {
                    vv.add(tokens.nextToken().trim());
                }
*/

                out.println("<BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + "This is the Threshold Value of " + ThresName +"</H1>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Threshold Name<TH>Threshold Value(s)");

                for ( int i = 0; i < ThresAttriNames.size() ; i++) {

                    out.print("<TR><TD>" + (String)ThresAttriNames.get(i)   + "\n<TD>");
                    out.println( (String)ThresAttriValues.get(i)  );

               }
               out.println("</TABLE>");
/*
            } else {
               out.println(pmClient.currentData);
            }
*/            
            out.println("</form> </body> </html>");
/*
        } catch ( Exception e ) {
            out.println("<html>");
            out.println("failed to get the data ");
            out.println("</html>");

            System.out.println("exception="+e);
        }
*/

    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



