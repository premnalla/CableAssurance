package ossj.qos.ri.pm.measurement.eis;

import java.net.URL;
import java.io.File;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.*;
import javax.oss.pm.measurement.PerformanceMonitorKey;
import javax.oss.pm.measurement.ReportInfo;


/** <code>ReportInformation</class> class.
 *  <p>
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */

public class ReportInformation implements Runnable {

  /**
   *
   */
  private Thread check = new Thread(this);

  /**
   *
   */
  private Hashtable reportInfoCollection;

  /**
   *
   */
  private String pmDataPath;


  /** <code>ReportInformation</code>
   *  <p>
   *  @param pmDataPath
   */
  public ReportInformation(String pmDataPath) {
    this.pmDataPath = pmDataPath;

    reportInfoCollection = new Hashtable();
    check.start();
  }


  /** <code>run</code>
   *  <p>
   */

  public void run() {
    EisSimulatorImpl.trace.log("->ReportInformation.run()", 1);

    while ( !Thread.interrupted()) {

      Enumeration renum = reportInfoCollection.elements();

      while (renum.hasMoreElements()) {
        Vector v = (Vector) renum.nextElement();

	  //VP
	  synchronized(v) {
		ReportInfo[] ris = new ReportInfo[v.size()];
		ris = (ReportInfo[])v.toArray(ris);
	
        for (int i = 0; i < ris.length; i++) {
          ReportInfo ri = (ReportInfo) ris[i];
          Calendar expirationDate = ri.getExpirationDate();

          if (expirationDate != null && expirationDate.before(Calendar.getInstance())) {
            URL url = ri.getURL();
            String filepath = url.getFile();
            String filename = filepath.substring(filepath.lastIndexOf('/') + 1);
            File reportFile = new File(pmDataPath + filename);

            reportFile.delete();
			v.removeElementAt(i);
			}
          }
        }
      }

      try {
		  // sleep for half an hour
         
          //Thread.sleep(60 * 60 * 1000);
          Thread.sleep(30 * 60 * 1000);
      }
      catch ( InterruptedException e ) {
        break;
      }

    }
    EisSimulatorImpl.trace.log("<-ReportInformation.run()", 1);
  }

  /** <code>initializeKey</code>
   *  <p>
   * @param pmKey
   */

  public void initializeKey(PerformanceMonitorKey pmKey) {
    EisSimulatorImpl.trace.log("->ReportInformation.initializeKey() for key ["+pmKey+"]", 1);
    Vector emptyVector = new Vector();

    reportInfoCollection.put(pmKey.getPerformanceMonitorPrimaryKey(), emptyVector);
    EisSimulatorImpl.trace.log("<-ReportInformation.initializeKey()", 1);
  }

  /** <code>getReportInfos</code>
   *  <p>
   * @param pmKey
   * @return
   */

  public Vector getReportInfos(PerformanceMonitorKey pmKey) {
    EisSimulatorImpl.trace.log("->ReportInformation.getReportInfos() for key ["+pmKey+"]", 1);
    Vector reportInfos = (Vector) reportInfoCollection.get(pmKey.getPerformanceMonitorPrimaryKey());
    Vector reportInfoClone = null;

    if (reportInfos != null) {
      reportInfoClone = new Vector(reportInfos.size());
      for (int i = 0; i < reportInfos.size(); i++) {
        ReportInfo ri = (ReportInfo) reportInfos.get(i);

        reportInfoClone.add(ri.clone());
      }
    }
    EisSimulatorImpl.trace.log("<-ReportInformation.getReportInfos()", 1);
    return reportInfoClone;
  }

  public Vector getAllReportInfos() {
    EisSimulatorImpl.trace.log("->ReportInformation.getAllReportInfos()", 1);
	HashSet rval = new HashSet();
	for (Iterator it = reportInfoCollection.values().iterator(); it.hasNext();) {
		Vector v = (Vector)it.next();
		rval.addAll(v);
	}
    EisSimulatorImpl.trace.log("<-ReportInformation.getAllReportInfos()", 1);
	return new Vector(rval);
  }


  /** <code>setReportInfo</code>
   *  <p>
   * @param pmKey
   * @param ri
   */

  public void setReportInfo(PerformanceMonitorKey pmKey, ReportInfo ri) {
    EisSimulatorImpl.trace.log("->ReportInformation.setReportInfo() for key ["+pmKey+"]", 1);
    Vector reportInfos = (Vector) reportInfoCollection.get(pmKey.getPerformanceMonitorPrimaryKey());

    reportInfos.add(ri);
    EisSimulatorImpl.trace.log("<-ReportInformation.setReportInfo()", 1);
  }

}
