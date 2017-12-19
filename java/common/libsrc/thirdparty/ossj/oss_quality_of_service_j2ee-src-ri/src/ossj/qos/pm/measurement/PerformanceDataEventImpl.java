package ossj.qos.pm.measurement;


//import ossj.qos.util.IRPEventImpl;

import javax.oss.pm.measurement.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlstr÷m
 * @version 0.2
 */

//public class PerformanceDataEventImpl extends IRPEventImpl implements PerformanceDataEvent {

public class PerformanceDataEventImpl extends PmIRPEventImpl implements PerformanceDataEvent {
  public PerformanceDataEventImpl() {
    super();

    map.put(this.REPORT, null);
    map.put(this.REPORT_FORMAT, null);
  }


  public Object getPerformanceMonitorReport() throws java.lang.IllegalStateException {
    Object report;
    if(this.isPopulated(this.REPORT) == false)
      report = null;
    else
      report = (Object) this.getAttributeValue(this.REPORT);
    return report;
  }

  public ReportFormat getReportFormat() throws java.lang.IllegalStateException {
    ReportFormat format;
    if(this.isPopulated(this.REPORT_FORMAT) == false)
      format = null;
    else
      format = (ReportFormat) this.getAttributeValue(this.REPORT_FORMAT);
    return format;
  }


  public ReportFormat makeReportFormat() {
    return new ReportFormatImpl();
  }

  public void setPerformanceMonitorReport(Object report) throws java.lang.IllegalArgumentException {
    this.setAttributeValue(this.REPORT, report);
  }

  public void setReportFormat(ReportFormat format) throws java.lang.IllegalArgumentException{
    this.setAttributeValue(this.REPORT_FORMAT, format);
  }

  protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);

    if ( attrName != null && attrName.equals( PerformanceDataEvent.REPORT )
        && attrValue != null && attrValue instanceof Object )
      isValid = true;

    if ( attrName != null && attrName.equals( PerformanceDataEvent.REPORT_FORMAT )
        && attrValue != null && attrValue instanceof ReportFormat )
      isValid = true;

    return isValid;
  }
}
