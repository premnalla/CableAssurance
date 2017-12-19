package ossj.qos.ri.fm.testclient;

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

    public class AlarmEventHandler implements MessageListener {

        public void onMessage( Message msg ) {
		System.out.println("=========into AlarmEvent Handler onMessage=========");
            try
            {
               if (msg instanceof ObjectMessage) {
                    ObjectMessage omsg = (ObjectMessage) msg;
                    Object obj = omsg.getObject();
                    if ( obj instanceof AlarmEvent ) {
                        AlarmEvent alarmEvent = (AlarmEvent) obj;
                        System.out.println( alarmEvent.toString() );

                        ArrayList rowData = new ArrayList();

                        // severity
                        short severity = alarmEvent.getPerceivedSeverity();
                        switch ( severity ) {
                            case PerceivedSeverity.CLEARED:
                                rowData.add( "CLEARED" );
                                break;
                            case PerceivedSeverity.CRITICAL:
                                rowData.add( "CRITICAL" );
                                break;
                            case PerceivedSeverity.INDETERMINATE:
                                rowData.add( "INDETERMINATE" );
                                break;
                            case PerceivedSeverity.MAJOR:
                                rowData.add( "MAJOR" );
                                break;
                            case PerceivedSeverity.MINOR:
                                rowData.add( "MINOR" );
                                break;
                            case PerceivedSeverity.WARNING:
                                rowData.add( "WARNING" );
                                break;
                            default:
                                rowData.add( "unknown" );
                        }


                        // time
                        rowData.add( alarmEvent.getEventTime() );

                        // object
                        rowData.add( alarmEvent.getManagedObjectInstance() );

                        // alarm
                        rowData.add( alarmEvent.getAlarmType() );

                        //ThresholdClient.this.alarmTableModel.addRow( rowData );
			AlarmKey key = alarmEvent.getAlarmKey();
			
			System.out.println("Alarm Primary Key : " +
				key.getAlarmPrimaryKey() );

                    } else {
                        System.out.println("onMessage()"+"not handled message: " + obj.getClass().getName());
                    }
                }

            } catch (Exception e) {
                System.out.println("onMessage()" + "exception="+e);
            }
		System.out.println("=========End of  onMessage=========");

        }//end of onMessages()

    }//end

