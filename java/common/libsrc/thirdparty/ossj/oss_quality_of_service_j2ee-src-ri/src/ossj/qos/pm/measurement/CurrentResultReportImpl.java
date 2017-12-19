package ossj.qos.pm.measurement;

import javax.oss.pm.measurement.CurrentResultReport;
import javax.oss.pm.measurement.ReportInfo;
import javax.oss.pm.measurement.ReportData;


import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;


/**
 * Title:        ossj.qos
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson Radio Systems AB
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 1.0
 */

public class CurrentResultReportImpl implements CurrentResultReport {


  // The server decides if the performance data should be returned
  // as a file or as a return value.
  // The Reference Implementation will give a return value (isDataType == true)
  private boolean isURLType = false;
  private boolean isDataType = true;

  private ReportInfo reportInformation = null;
  private ReportData reportData = null;


  public CurrentResultReportImpl() {
  }

  public CurrentResultReportImpl( ReportInfo reportInformation, ReportData reportData) {
    this.reportInformation = (ReportInfo) reportInformation.clone();
    this.reportData = (ReportData) reportData.clone();
  }

  public Object clone()  {

    CurrentResultReportImpl clone = null;
    try {
      clone = (CurrentResultReportImpl) super.clone();
      if ( reportData != null )
        clone.reportData = (ReportData) reportData.clone();
      if ( reportInformation != null )
        clone.reportInformation = (ReportInfo) reportInformation.clone();
    }
    catch(CloneNotSupportedException e) {
      // @todo Handling of exception here!
    }

    return clone;
  }

  public boolean isURLType() {
    return isURLType;

  }
  public boolean isDataType() {
    return isDataType;
  }

  public ReportInfo getReportInformation() throws java.lang.IllegalStateException {
    return reportInformation;
  }

  public ReportData getReportData() throws java.lang.IllegalStateException {
    return reportData;
  }

  /*
  Added the method Below to comply with the new implementation.
  March 15, 2002.
  Hooman Tahamtani, Ericsson Microwave AB
  Gothenburg, Sweden
  */
      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( PmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

     /*
        Added the method below to comply with the new implementation.
        March 15, 2002
        Hooman Tahamtani, Ericsson Microwave AB
        Gothenburg, Sweden
     */

    public Serializer makeSerializer( String serializerType )
    throws java.lang.IllegalArgumentException {
        Serializer serializer = null;
     try{
        serializer = new PmXmlSerializerImpl(serializerType);
     }catch(java.lang.IllegalArgumentException e){
        throw new java.lang.IllegalArgumentException(e.getMessage());
     }
        return serializer;
    }

}