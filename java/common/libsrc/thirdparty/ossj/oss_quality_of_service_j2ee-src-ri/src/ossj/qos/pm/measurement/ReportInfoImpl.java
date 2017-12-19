package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.*;
import java.net.*;
import java.util.Calendar;

//import ossj.qos.pm.util.Log;
import ossj.qos.util.Log;
import ossj.qos.util.Util;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class ReportInfoImpl implements ReportInfo {

  private URL url;
  private Calendar expirationDate;
  private ReportFormat repf;

  public ReportInfoImpl() {
  }

  public Object clone() {
    ReportInfoImpl clone = null;

    try {
      clone = (ReportInfoImpl) super.clone();

      if(repf != null)
        clone.repf = (ReportFormat) repf.clone();

      if(expirationDate != null)
        clone.expirationDate = (Calendar) expirationDate.clone();
    }
    catch(CloneNotSupportedException e) {
      Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
    }
    return clone;
  }

  public Calendar getExpirationDate() {
    return expirationDate;
  }

  public ReportFormat getReportFormat() {
    return repf;
  }

  public URL getURL() {
    URL newurl = null;

    try {
      newurl = new URL(url.toString());
    }
    catch (MalformedURLException e) {
      //VP
	  //System.out.println("Exception ocurred in URL-creation: " + e);
    }
    return newurl;
  }

  public ReportFormat makeReportFormat() {
    return new ReportFormatImpl();
  }

  public void setExpirationDate(Calendar expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setReportFormat(ReportFormat repf) {
    this.repf = repf;
  }

  public void setURL(URL url) {
    this.url = url;
  }

  public int hashCode() {
	return ((url != null) ? url.hashCode() : -1);
  }

  public boolean equals(Object o) {
	if (this == o) {
		return true;
	}
	if (o == null || o.getClass() != this.getClass()) {
		return false;
	}
	ReportInfoImpl rinfo = (ReportInfoImpl)o;
	return (Util.isEqual(this.url, rinfo.url) &&
		    Util.isEqual(this.repf, rinfo.repf) &&
			Util.isEqual(this.expirationDate, rinfo.expirationDate));
  }

}






