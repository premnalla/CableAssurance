package ossj.qos.pm.measurement;

//import ossj.qos.util.IRPEventImpl;


import javax.oss.pm.measurement.*;

import java.net.URL;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 0.2
 */

//public class PerformanceDataAvailableEventImpl extends IRPEventImpl implements PerformanceDataAvailableEvent {

public class PerformanceDataAvailableEventImpl extends PmIRPEventImpl implements PerformanceDataAvailableEvent {


  public PerformanceDataAvailableEventImpl() {
    super();
    map.put(this.REPORT_INFO, null);

  }

   public ReportInfo getReportInformation() throws java.lang.IllegalStateException {
    ReportInfo ri;
    if(this.isPopulated(this.REPORT_INFO) == false)
      ri = null;
    else
      ri = (ReportInfo) this.getAttributeValue(this.REPORT_INFO);
    return ri;
  }

  public ReportInfo makeReportInformation() {
    ReportInfo info = new ReportInfoImpl();

    return info;
  }

  public void setReportInformation(ReportInfo info)  throws java.lang.IllegalArgumentException {
    if(info == null)
      throw new java.lang.IllegalArgumentException();
    ReportFormat rf = info.getReportFormat();
    URL url = info.getURL();
    if(rf == null || url == null)
      throw new java.lang.IllegalArgumentException();
    this.setAttributeValue(this.REPORT_INFO, info);
  }

   protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);

    if ( attrName != null && attrName.equals( PerformanceDataAvailableEvent.REPORT_INFO)
        && attrValue != null && attrValue instanceof ReportInfo ){
        ReportInfo info = (ReportInfo) attrValue;
        ReportFormat rf = info.getReportFormat();
        URL url = info.getURL();
        if(rf != null && url != null)
          isValid = true;
   }

    return isValid;
  }
}
