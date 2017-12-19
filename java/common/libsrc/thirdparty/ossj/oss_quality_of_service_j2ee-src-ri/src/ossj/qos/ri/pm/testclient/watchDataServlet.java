package ossj.qos.ri.pm.testclient;
/* $Id: watchDataServlet.java,v 1.1.1.1 2006/04/12 14:35:38 prem Exp $
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


/**
 * The simplest possible servlet.
 *
 * @author James Duncan Davidson
 */

public class watchDataServlet  extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String jobName = request.getParameter("jobName");
        String objectName = request.getParameter("objectName");


        HashMap serverityMap = new HashMap();
        serverityMap.put(new Integer( PerceivedSeverity.CRITICAL), new String( "CRITICAL"));
        serverityMap.put(new Integer( PerceivedSeverity.MINOR), new String( "MINOR" ));
        serverityMap.put(new Integer( PerceivedSeverity.WARNING), new String( "WARNING" ) );
        serverityMap.put(new Integer( PerceivedSeverity.INDETERMINATE), new String( "INDETERMINATE" ) );
        serverityMap.put(new Integer( PerceivedSeverity.MAJOR), new String(  "MAJOR"  ));
        serverityMap.put(new Integer( PerceivedSeverity.CLEARED),new String( "CLEARED" ));
        serverityMap.put(new Integer( PerceivedSeverity.CRITICAL),new String( "CRITICAL"));


        String observableObjectClassName;
        int index = objectName.indexOf(",");
        observableObjectClassName = objectName.substring(index+1);
        index = observableObjectClassName.indexOf("=");
        observableObjectClassName = observableObjectClassName.substring(0,index);
        observableObjectClassName = observableObjectClassName.trim();

        String realPath = request.getRealPath("/index.html");
        int realLength = realPath.length();
        realPath = realPath.substring(0,realLength-11);

        //System.out.println("wrx--realPath:" + realPath);
        IrenePmClient pmClient  = IrenePmClient.getInstance(realPath + "/pmclient.properties");
        /*ThresholdClient thresholdClient = ThresholdClient.getInstance();

        FmClient fmClient = FmClient.getInstance(realPath + "/fmClient.properties");
        AlarmValue[] almvs = null;
        try {
            almvs = fmClient.QueryAllAlarms();
        } catch (Exception e) {
            System.out.println(e);
        }*/

         PerformanceMonitorValue perfValue = pmClient.findMonitorJobByName(jobName);
         PerformanceMonitorKey perfKey = perfValue.getPerformanceMonitorKey();
         String primaryKey = perfKey.getPerformanceMonitorPrimaryKey();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
          /*  ArrayList observableObjectAttribute = thresholdClient.getObservableObjectAttributes( observableObjectClassName );*/
       
            pmClient.getReportData(jobName);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>really create a job </title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form method=\"POST\">" );
            String currentData = pmClient.currentData;
            System.out.println("currentData: " + currentData);
            PerformanceReportHandler.createDocument(currentData);
            PerformanceReportHandler.getMeasurementValue(objectName);

                out.println("<BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + "This is the monitor DATA:" + "</H1>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Parameter Name<TH>Parameter Value(s)</TR>");

                out.println("<tr><td>Object</td>");
                out.println("<td>"+objectName+"</td>");
                out.println("</tr>");

               System.out.println("PerformanceReportHandler lenght: " + PerformanceReportHandler.getItemName().length);
                
                for ( int i = 0; i < PerformanceReportHandler.getItemName().length  ; i++) {
                     out.println("<tr>");
                     out.println("<td>" + PerformanceReportHandler.getItemName()[i] + "</td>");   
                     out.println("<td>" + PerformanceReportHandler.getItemValue()[i] + "</td>");
                     String status ="--" ;
                     PerformanceAttributeDescriptor descriptor = null;
                     /*for (int m=0;m<observableObjectAttribute.size();m++) {

                         if( ((((PerformanceAttributeDescriptor)observableObjectAttribute.get(m)).getName()).trim()).equals(PerformanceReportHandler.getItemName()[i].trim())) {
                             descriptor = (PerformanceAttributeDescriptor)observableObjectAttribute.get(m);
                             break;
                          }
                     }*/
                  
                     /*if( descriptor != null) {
                     if(descriptor.getType()==PerformanceAttributeDescriptor.REAL || descriptor.getType()==PerformanceAttributeDescriptor.INTEGER) {


     
                     if(almvs !=null ) {
                     for(int j = 0; j < almvs.length; j++) {
                         if( (almvs[j].getManagedObjectInstance()).equals(primaryKey ) &&  ((almvs[j].getMonitoredAttributes())[0].getAttributeName()).equals(PerformanceReportHandler.getItemName()[i]) ) {
                             status = (String)serverityMap.get(new Integer(almvs[j].getPerceivedSeverity()));
                             break;
                         }
                     }
                     }
                     if(status.length() > 0)
                         out.println("<td>" + status + " </td>"); 




                     out.println("<td>");
                     out.println("<a href=\"setThresholdPageServlet?jobName=" + jobName + "&objectName=" + objectName + "&attName=" +  PerformanceReportHandler.getItemName()[i] +  "\">set the threshold</a>"); 
                     out.println("</td");
                     } else {
                         out.println("<td>" + "---" + "</td>");
                         out.println("<td>" + "---" + "</td>");
                     }
                     }*/
                     out.println("</tr>");

               }

               out.println("</TABLE>");
               out.println("</form> </body> </html>");

        } catch ( Exception e ) {
            out.println("<html>");
            out.println("failed to get the data ");
            out.println("</html>");

            System.out.println("exception="+e);
        }


    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException {
        doGet(req, res);
    }

}



