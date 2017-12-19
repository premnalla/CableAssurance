package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.*;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlstr÷m
 * @version 0.2
 */

public class PerformanceMonitorByObjectsValueImpl extends PerformanceMonitorValueImpl implements javax.oss.pm.measurement.PerformanceMonitorByObjectsValue {

  public Object clone() {

     PerformanceMonitorByObjectsValueImpl clone = null;
     clone = (PerformanceMonitorByObjectsValueImpl) super.clone();

     PerformanceAttributeDescriptor[] measurementAttributeNames = null;
     String[] observedObjects = null;

     if(this.isPopulated(this.MEASUREMENT_ATTRIBUTES) == true)
      measurementAttributeNames = (PerformanceAttributeDescriptor[]) this.getAttributeValue(this.MEASUREMENT_ATTRIBUTES);

     if(this.isPopulated(this.OBSERVED_OBJECTS) == true)
      observedObjects = (String[]) this.getAttributeValue(this.OBSERVED_OBJECTS);

     if(measurementAttributeNames != null) {
      PerformanceAttributeDescriptor[] maNamesClone = (PerformanceAttributeDescriptor[]) measurementAttributeNames.clone();
      for(int i =0; i<measurementAttributeNames.length; i++)
        maNamesClone[i] = (PerformanceAttributeDescriptor) measurementAttributeNames[i].clone();
      clone.setAttributeValue(this.MEASUREMENT_ATTRIBUTES, maNamesClone);
     }
     if(observedObjects != null) {
      String[] ooClone = (String[]) observedObjects.clone();
      clone.setAttributeValue(this.OBSERVED_OBJECTS, ooClone);
     }
      return clone;
  }

  public PerformanceMonitorByObjectsValueImpl() {
    super();
    map.put(this.MEASUREMENT_ATTRIBUTES, null);
    map.put(this.OBSERVED_OBJECTS, null);
  }

  public PerformanceAttributeDescriptor[] getMeasurementAttributes() throws java.lang.IllegalStateException {
    if(this.isPopulated(this.MEASUREMENT_ATTRIBUTES) == false)
      throw new java.lang.IllegalStateException();

    return (PerformanceAttributeDescriptor[]) this.getAttributeValue(this.MEASUREMENT_ATTRIBUTES);
  }

  public PerformanceAttributeDescriptor makePerformanceAttributeDescriptor() {
    return new PerformanceAttributeDescriptorImpl();
  }

  public void setMeasurementAttributes( PerformanceAttributeDescriptor[] measurementAttributeNames ) throws java.lang.IllegalArgumentException {
    int start, stop;

    if(measurementAttributeNames == null)
      throw new java.lang.IllegalArgumentException();

    for(int i=0; i<measurementAttributeNames.length; i++)
          if(measurementAttributeNames[i] == null)
            throw new java.lang.IllegalArgumentException();

    for(int i=0; i<measurementAttributeNames.length; i++) {
      String collectionMethod = measurementAttributeNames[i].getCollectionMethod();
      String name = measurementAttributeNames[i].getName();
      int type = measurementAttributeNames[i].getType();

      if(name == null)
        throw new java.lang.IllegalArgumentException("PerformanceAttributeDescriptor name is null");

      if((collectionMethod != null) &&
	     (collectionMethod.compareTo(PerformanceAttributeDescriptor.CUMULATIVE_COUNTER) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.GAUGE) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.STATUS_INSPECTION) != 0))
        throw new java.lang.IllegalArgumentException(collectionMethod + " is not a valid collection method");

      if((type != 0) &&
	     (type != PerformanceAttributeDescriptor.INTEGER) &&
         (type != PerformanceAttributeDescriptor.REAL) &&
         (type != PerformanceAttributeDescriptor.STRING))
        throw new java.lang.IllegalArgumentException("Invalid PerformanceAttributeDescriptor type: " + type);
    }

    this.setAttributeValue(this.MEASUREMENT_ATTRIBUTES, measurementAttributeNames);
  }

  public String[] getObservedObjects() throws java.lang.IllegalStateException {
    if(this.isPopulated(this.OBSERVED_OBJECTS) == false){
      throw new java.lang.IllegalStateException();
    }

    return (String[]) this.getAttributeValue(this.OBSERVED_OBJECTS);
  }

  public void setObservedObjects( String[] observedObjects ) throws java.lang.IllegalArgumentException {

    if(observedObjects == null)
      throw new java.lang.IllegalArgumentException();

    for(int i=0; i<observedObjects.length; i++)
          if(observedObjects[i] == null)
            throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.OBSERVED_OBJECTS, observedObjects);
  }

   protected boolean isValidAttribute( String attrName, Object attrValue ) {

    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);

    if ( attrName != null && attrName.equals( PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES )
        && attrValue != null && attrValue instanceof  PerformanceAttributeDescriptor[]) {
        isValid = true;
        PerformanceAttributeDescriptor[] measurementAttributeNames = (PerformanceAttributeDescriptor[]) attrValue;
        for(int i=0; i<measurementAttributeNames.length; i++)
          if(measurementAttributeNames[i] == null)
            isValid = false;

        if(isValid == true) {

          for(int i=0; i<measurementAttributeNames.length; i++) {
            String collectionMethod = measurementAttributeNames[i].getCollectionMethod();
            String name = measurementAttributeNames[i].getName();
            int type = measurementAttributeNames[i].getType();

            if(name == null)
              isValid = false;

//  collectionMethod and type do not have to be set when creating PM job -- FDP Oct 9, 2002
//
//            if((collectionMethod.compareTo(PerformanceAttributeDescriptor.CUMULATIVE_COUNTER) != 0) &&
//              (collectionMethod.compareTo(PerformanceAttributeDescriptor.GAUGE) != 0) &&
//              (collectionMethod.compareTo(PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION) != 0) &&
//              (collectionMethod.compareTo(PerformanceAttributeDescriptor.STATUS_INSPECTION) != 0))
//                isValid = false;
//
//           if((type != PerformanceAttributeDescriptor.INTEGER) &&
//              (type != PerformanceAttributeDescriptor.REAL) &&
//              (type != PerformanceAttributeDescriptor.STRING))
//               isValid = false;
          }
        }

    }

    if ( attrName != null && attrName.equals( PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS )
        && attrValue != null && attrValue instanceof String[] ) {
        isValid = true;
        String[] obsObj = (String[]) attrValue;
        for(int i=0; i<obsObj.length; i++)
          if(obsObj[i] == null)
            isValid = false;

    }

    return isValid;
  }

}
