package ossj.qos.mdbimpl.pm;

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


public class ApplicationConnector {


  javax.oss.fm.monitor.JVTAlarmMonitorSession       fmRemote = null;
  javax.oss.pm.measurement.JVTPerformanceMonitorSession pmRemote = null;
  javax.oss.pm.threshold.JVTThresholdMonitorSession   tmRemote = null;
  Properties p = new Properties();
  FileInputStream fis;

  public ApplicationConnector() {
  try{
   fis = new FileInputStream("ApplicationConnector.properties");
   p.load(fis);
  }catch(Exception e){
      System.out.println(e.getMessage());
      }


  }

  public javax.oss.fm.monitor.JVTAlarmMonitorSession getFMRemote(){
     try{
      Context ctx = getInitialContext();

      javax.oss.fm.monitor.JVTAlarmMonitorHome home = (javax.oss.fm.monitor.JVTAlarmMonitorHome)PortableRemoteObject.narrow(ctx.lookup(p.getProperty("FMHOME")), javax.oss.fm.monitor.JVTAlarmMonitorHome.class);

            //Alarm Remote Interface
        fmRemote = home.create();
        ctx.close();

        }catch(Exception e){
         e.getMessage();
        }
       return fmRemote;
    }

  public javax.oss.pm.measurement.JVTPerformanceMonitorSession getPMRemote(){
         try{
         Context ctx = getInitialContext();

     javax.oss.pm.measurement.JVTPerformanceMonitorHome home = (javax.oss.pm.measurement.JVTPerformanceMonitorHome)PortableRemoteObject.narrow(ctx.lookup(p.getProperty("PMHOME")), javax.oss.pm.measurement.JVTPerformanceMonitorHome.class);
            //Alarm Remote Interface
        pmRemote = home.create();
        ctx.close();

        }catch(Exception e){
         e.getMessage();
        }
      return pmRemote;
    }

  public javax.oss.pm.threshold.JVTThresholdMonitorSession getTMRemote(){
        try{
         Context ctx = getInitialContext();

      javax.oss.pm.threshold.JVTThresholdMonitorHome home = (javax.oss.pm.threshold.JVTThresholdMonitorHome)PortableRemoteObject.narrow(ctx.lookup(p.getProperty("TMHOME")), javax.oss.pm.threshold.JVTThresholdMonitorHome.class);
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

        h.put(Context.INITIAL_CONTEXT_FACTORY,p.getProperty("INITIAL_CONTEXT_FACTORY"));
        h.put(Context.PROVIDER_URL,p.getProperty("PROVIDER_URL"));
        h.put(Context.SECURITY_PRINCIPAL,p.getProperty("SECURITY_PRINCIPAL"));
        h.setProperty(Context.SECURITY_CREDENTIALS,p.getProperty("SECURITY_CREDENTIALS"));

      } catch (Exception ne) {
        System.out.println("We were unable to get a connection to the WebLogic server ");
        System.out.println("Please make sure that the server is running.");
        ne.getMessage();
      }
       return new InitialContext(h);
    }

  public String getJMSQueueConnectionFactory(){

     String s1 = p.getProperty("JMS_CONNECTION_FACTORY");
       return s1;
    }


}