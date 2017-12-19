package ossj.qos.pm.measurement;

import javax.oss.pm.measurement.ReportData;
import javax.oss.pm.measurement.ReportFormat;
//import ossj.qos.pm.util.Util;

import ossj.qos.util.Util;

/**
 * Title:        ossj.qos
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson Radio Systems AB
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 */

public class ReportDataImpl implements ReportData {

  private ReportFormat reportFormat = null;
  private Object report = null;

  public ReportDataImpl() {
  }

  public ReportDataImpl(Object report, ReportFormat reportFormat) {
   try {
    this.report = Util.clone(report);
    this.reportFormat = (ReportFormat) reportFormat.clone();
	} catch (java.lang.CloneNotSupportedException clex){
	}
  }

  public Object clone() {

    ReportDataImpl clone = null;
    try {
      clone = (ReportDataImpl) super.clone();
      if ( report != null )
        clone.report = Util.clone( report );
      if ( reportFormat != null )
        clone.reportFormat = (ReportFormatImpl) reportFormat.clone();
    }
    catch (CloneNotSupportedException e) {
      // @todo
    }

    return clone;
  }

  public Object getPerformanceMonitorReport() {
    return report;
  }

  public ReportFormat getReportFormat() {
    return reportFormat;
  }

}
