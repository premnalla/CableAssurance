package ossj.qos.pm.measurement;

import javax.oss.pm.measurement.PerformanceMonitorKeyResult;
import javax.oss.pm.measurement.PerformanceMonitorKey;
import javax.oss.ManagedEntityKey;
import ossj.qos.ManagedEntityKeyResultImpl;


import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;


/**
 * Title:        ossj.qos
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson Radio Systems AB
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Katarina Wahlström, Hooman Tahamtani
 * @version 1.0
 */

public class PerformanceMonitorKeyResultImpl extends ManagedEntityKeyResultImpl
             implements PerformanceMonitorKeyResult {


  public PerformanceMonitorKeyResultImpl() {
  }


  public PerformanceMonitorKey getPerformanceMonitorKey() {
    return (PerformanceMonitorKey) super.getManagedEntityKey();
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