package ossj.qos.ri.pm.testclient;
import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.oss.*;
import javax.oss.fm.monitor.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.threshold.*;
import javax.oss.pm.util.*;

//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.Cursor;

import org.w3c.dom.Document; // for XML report

    public class PerformanceDataEventHandler implements MessageListener {

        /**
         * Handles an performance data event.
         */
        public void onMessage( Message msg ) {
		System.out.println("=========into onMessage=========");
            try
            {
                if (msg instanceof ObjectMessage) {
                    ObjectMessage omsg = (ObjectMessage) msg;
                    Object obj = omsg.getObject();
                    if ( obj instanceof PerformanceDataEvent ) {
                        System.out.println( "PerformanceDataEvent" );

                        PerformanceDataEvent pmEvent = (PerformanceDataEvent)obj;

                        String[] names = pmEvent.getPopulatedAttributeNames();
                        StringBuffer buf = new StringBuffer();
                        buf.append('[');
                        for (int i=0;i<names.length;i++) {
                            buf.append( names[i] );
                            if ( i!=names.length-1) {
                                buf.append(',');
                            }
                        }
                        buf.append(']');
                        System.out.println("Populated attributes="+buf.toString());

                        ArrayList rowData = new ArrayList();
                        rowData.add( pmEvent.getAttributeValue( PerformanceDataEvent.EVENT_TIME ) );
                        rowData.add( pmEvent.getAttributeValue( PerformanceDataEvent.MANAGED_OBJECT_INSTANCE ) );
                        Object report = pmEvent.getAttributeValue( PerformanceDataEvent.REPORT );
                        String measXMLReport = null;
                        if ( report instanceof String ) {
                            measXMLReport = (String)report;
			    System.out.println("Report data  :" );
                            System.out.println( (String) report );
                        /*    String realPath = request.getRealPath("/index.html");
                            //System.out.println("wrx--realPath:" + realPath);
                            int realLength = realPath.length();
                            realPath = realPath.substring(0,realLength-11);
                            PmClient pmClient  = PmClient.getInstance("pmclient.properties");
                            pmClient.realtimeData = ( (String) report );*/


                        } else if ( report instanceof String[] ) { // fix for current delivered events from PM.
                            measXMLReport = ((String[])report)[0]; // first element is the report, second is a filename...
			    System.out.println("Report Data :" );
                            System.out.println( ((String[])report)[0] );
                        } else { // unknown format
                            System.out.println("onMessage()" + "unknown report format!");
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("onMessage()" + "exception="+e);
            }
		System.out.println("=========End of  onMessage=========");

        }//end of onMessages()

    }//PerformanceDataEventHandler class

