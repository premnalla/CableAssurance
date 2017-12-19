package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.QueryPerformanceMonitorValue;

import ossj.qos.*;

import javax.oss.pm.measurement.PerformanceMonitorState;
import javax.oss.pm.measurement.PerformanceMonitorByClassesValue;
import javax.oss.pm.measurement.PerformanceMonitorByObjectsValue;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlstrröm
 * @version 0.2
 */


/*
public class QueryPerformanceMonitorValueImpl extends QueryValueImpl implements QueryPerformanceMonitorValue {


  public QueryPerformanceMonitorValueImpl() {
    super();
    map.put(this.GRANULARITY_PERIOD, null);
    map.put(this.NAME, null);
    map.put(this.VALUE_TYPE, null);
    map.put(this.STATE, null);
  }

  public int getGranularityPeriod() throws java.lang.IllegalStateException {

   if(this.isPopulated(this.GRANULARITY_PERIOD) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer) this.getAttributeValue(this.GRANULARITY_PERIOD)).intValue();
  }

  public String getName() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.NAME) == false)
      throw new java.lang.IllegalStateException();

    return (String) this.getAttributeValue(this.NAME);
  }

  public String getValueType() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.VALUE_TYPE) == false)
      throw new java.lang.IllegalStateException();

    return (String) this.getAttributeValue(this.VALUE_TYPE);
  }

  public int getState() throws java.lang.IllegalStateException {

    if(this.isPopulated(this.STATE) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer)this.getAttributeValue(this.STATE)).intValue();
  }

  public void setGranularityPeriod(int granularityPeriod) throws java.lang.IllegalArgumentException {

    if(granularityPeriod < 0)
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.GRANULARITY_PERIOD, new Integer(granularityPeriod));
  }

  public void setName(String measurementName) throws java.lang.IllegalArgumentException {

    if(measurementName == null)
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.NAME, measurementName);
  }

  public void setValueType(String performanceMonitorValueType)throws java.lang.IllegalArgumentException {

    if(performanceMonitorValueType == null)
      throw new java.lang.IllegalArgumentException();

   if(performanceMonitorValueType.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) != 0)
       throw new java.lang.IllegalArgumentException();

   if(performanceMonitorValueType.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) != 0)
       throw new java.lang.IllegalArgumentException();


    this.setAttributeValue(this.VALUE_TYPE, performanceMonitorValueType);
  }

  public void setState(int state) throws java.lang.IllegalArgumentException {

    if( (state != PerformanceMonitorState.ACTIVE_ON_DUTY) &&
        (state != PerformanceMonitorState.ACTIVE_OFF_DUTY) &&
        (state != PerformanceMonitorState.SUSPENDED) )
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.STATE, new Integer(state));
  }

  protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.NAME )
        && attrValue != null && attrValue instanceof  String)
      isValid = true;

    if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.GRANULARITY_PERIOD )
        && attrValue != null && attrValue instanceof Integer ) {
       int granularityPeriod = ((Integer)attrValue).intValue();
       if(granularityPeriod >= 0)
         isValid = true;
    }

     if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.VALUE_TYPE )
        && attrValue != null && attrValue instanceof String ) {

       String performanceMonitorValueType = (String) attrValue;

      if(performanceMonitorValueType.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) == 0)
         isValid = true;

       if(performanceMonitorValueType.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) == 0)
         isValid = true;
     }

     if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.STATE)
        && attrValue != null && attrValue instanceof Integer ) {
        int state = ((Integer)attrValue).intValue();
        if( (state == PerformanceMonitorState.ACTIVE_ON_DUTY) ||
            (state == PerformanceMonitorState.ACTIVE_OFF_DUTY)||
            (state == PerformanceMonitorState.SUSPENDED) )
            isValid = true;
     }


    return isValid;
  }




}
*/



//public class QueryPerformanceMonitorValueImpl extends QueryValueImpl implements QueryPerformanceMonitorValue {

public class QueryPerformanceMonitorValueImpl extends PmQueryValueImpl implements QueryPerformanceMonitorValue {

  public QueryPerformanceMonitorValueImpl() {
    super();
    map.put(this.GRANULARITY_PERIOD, null);
    map.put(this.NAME, null);
    map.put(this.VALUE_TYPE, null);
    map.put(this.STATE, null);
  }

  public int getGranularityPeriod() throws java.lang.IllegalStateException {

   if(this.isPopulated(this.GRANULARITY_PERIOD) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer) this.getAttributeValue(this.GRANULARITY_PERIOD)).intValue();
  }

  public String getName() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.NAME) == false)
      throw new java.lang.IllegalStateException();

    return (String) this.getAttributeValue(this.NAME);
  }

  public String getValueType() throws java.lang.IllegalStateException {

     if(this.isPopulated(this.VALUE_TYPE) == false)
      throw new java.lang.IllegalStateException();

    return (String) this.getAttributeValue(this.VALUE_TYPE);
  }

  public int getState() throws java.lang.IllegalStateException {

    if(this.isPopulated(this.STATE) == false)
      throw new java.lang.IllegalStateException();

    return ((Integer)this.getAttributeValue(this.STATE)).intValue();
  }

  public void setGranularityPeriod(int granularityPeriod) throws java.lang.IllegalArgumentException {

    if(granularityPeriod < 0)
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.GRANULARITY_PERIOD, new Integer(granularityPeriod));
  }

  public void setName(String measurementName) throws java.lang.IllegalArgumentException {

    if(measurementName == null)
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.NAME, measurementName);
  }

  public void setValueType(String performanceMonitorValueType)throws java.lang.IllegalArgumentException {

    if(performanceMonitorValueType == null)
      throw new java.lang.IllegalArgumentException();

    if(performanceMonitorValueType.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) != 0)
       throw new java.lang.IllegalArgumentException();

    if(performanceMonitorValueType.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) != 0)
       throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.VALUE_TYPE, performanceMonitorValueType);
  }

  public void setState(int state) throws java.lang.IllegalArgumentException {

    if( (state != PerformanceMonitorState.ACTIVE_ON_DUTY) &&
        (state != PerformanceMonitorState.ACTIVE_OFF_DUTY) &&
        (state != PerformanceMonitorState.SUSPENDED) )
      throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.STATE, new Integer(state));
  }

  protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.NAME )
        && attrValue != null && attrValue instanceof  String)
      isValid = true;

    if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.GRANULARITY_PERIOD )
        && attrValue != null && attrValue instanceof Integer ) {
       int granularityPeriod = ((Integer)attrValue).intValue();
       if(granularityPeriod >= 0)
         isValid = true;
    }

     if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.VALUE_TYPE )
        && attrValue != null && attrValue instanceof String ) {

       String performanceMonitorValueType = (String) attrValue;

      if(performanceMonitorValueType.compareTo(PerformanceMonitorByObjectsValue.VALUE_TYPE) == 0)
         isValid = true;
      if(performanceMonitorValueType.compareTo(PerformanceMonitorByClassesValue.VALUE_TYPE) == 0)
         isValid = true;
     }

     if ( attrName != null && attrName.equals( QueryPerformanceMonitorValue.STATE)
        && attrValue != null && attrValue instanceof Integer ) {
        int state = ((Integer)attrValue).intValue();
        if( (state == PerformanceMonitorState.ACTIVE_ON_DUTY) ||
            (state == PerformanceMonitorState.ACTIVE_OFF_DUTY)||
            (state == PerformanceMonitorState.SUSPENDED) )
            isValid = true;
     }


    return isValid;
  }




}