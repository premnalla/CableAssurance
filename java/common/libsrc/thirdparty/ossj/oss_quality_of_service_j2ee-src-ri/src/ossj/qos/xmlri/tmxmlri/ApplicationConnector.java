package ossj.qos.xmlri.tmxmlri;


/**
 * Title:        OSS\J XML RI Telegea
 * Description:  XML based Reference Implementation for QoS API
 * Copyright:    Copyright (c) 2002
 * Company:      Telegea Inc.
 * @author Vijay Sharma
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import javax.ejb.RemoveException;
import javax.naming.*;
import javax.oss.fm.monitor.*;
import javax.oss.pm.measurement.*;
import javax.oss.pm.threshold.*;


public class ApplicationConnector {


  JVTAlarmMonitorSession       fmRemote = null;
  JVTPerformanceMonitorSession pmRemote = null;
  JVTThresholdMonitorSession   tmRemote = null;

  public ApplicationConnector() {
  }

  public JVTAlarmMonitorSession getFMRemote(){
     try{
      Context ctx = getInitialContext();

      JVTAlarmMonitorHome home = (JVTAlarmMonitorHome)PortableRemoteObject.narrow(ctx.lookup("System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTHome"), javax.oss.fm.monitor.JVTAlarmMonitorHome.class);

            //Alarm Remote Interface
        fmRemote = home.create();
        ctx.close();

        }catch(Exception e){
         e.getMessage();
        }
       return fmRemote;
    }

  public JVTPerformanceMonitorSession getPMRemote(){
         try{
         Context ctx = getInitialContext();

     JVTPerformanceMonitorHome home = (JVTPerformanceMonitorHome)PortableRemoteObject.narrow(ctx.lookup("System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTHome"), javax.oss.pm.measurement.JVTPerformanceMonitorHome.class);
            //Alarm Remote Interface
        pmRemote = home.create();
        ctx.close();

        }catch(Exception e){
         e.getMessage();
        }
      return pmRemote;
    }

  public JVTThresholdMonitorSession getTMRemote(){
        try{
         Context ctx = getInitialContext();

      JVTThresholdMonitorHome home = (JVTThresholdMonitorHome)PortableRemoteObject.narrow(ctx.lookup("System/Linkoping/ApplicationType/ThresholdMonitor/Application/1-0;1-0;JSR90RITM/Comp/JVTHome"), javax.oss.pm.threshold.JVTThresholdMonitorHome.class);
            //Alarm Remote Interface
        tmRemote = home.create();
        ctx.close();

        }catch(Exception e){
         e.getMessage();
        }
       return tmRemote;
    }

  public Context getInitialContext() throws NamingException {

      Properties h = new Properties();
      try {
        // Get an InitialContext

        h.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
        h.put(Context.PROVIDER_URL,"t3://localhost:7001");
        h.put(Context.SECURITY_PRINCIPAL,"system");
        h.setProperty(Context.SECURITY_CREDENTIALS,"adminadmin");

      } catch (Exception ne) {
        System.out.println("We were unable to get a connection to the WebLogic server ");
        System.out.println("Please make sure that the server is running.");
        ne.getMessage();
      }
       return new InitialContext(h);
    }




}