package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.*;
// VP
import ossj.qos.util.Log;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright 2001 Ericsson Radio Systems AB
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Hooman Tahamtani, Katarina Wahlström
 * @version 0.2
 */

public class PerformanceMonitorByClassesValueImpl extends PerformanceMonitorValueImpl implements PerformanceMonitorByClassesValue {

  public PerformanceMonitorByClassesValueImpl() {
    super();
    map.put(this.MEASUREMENT_ATTRIBUTES, null);
    map.put(this.OBSERVABLE_OBJECT_CLASSES, null);
    map.put(this.SCOPE, null);
  }

   public Object clone() {

     PerformanceMonitorByClassesValueImpl clone = null;
     clone = (PerformanceMonitorByClassesValueImpl) super.clone();

     PerformanceAttributeDescriptor[] measurementAttributeNames = null;
     String[] observableObjectClasses = null;
     String scope = null;

     if(this.isPopulated(this.MEASUREMENT_ATTRIBUTES) == true)
      measurementAttributeNames = (PerformanceAttributeDescriptor[]) this.getAttributeValue(this.MEASUREMENT_ATTRIBUTES);

     if(this.isPopulated(this.OBSERVABLE_OBJECT_CLASSES) == true)
      observableObjectClasses = (String[]) this.getAttributeValue(this.OBSERVABLE_OBJECT_CLASSES);

     if(this.isPopulated(this.SCOPE) == true)
      scope = this.getScope();

     if(measurementAttributeNames != null) {
      PerformanceAttributeDescriptor[] maNamesClone = (PerformanceAttributeDescriptor[]) measurementAttributeNames.clone();
      for(int i =0; i<measurementAttributeNames.length; i++)
        maNamesClone[i] = (PerformanceAttributeDescriptor) measurementAttributeNames[i].clone();
      clone.setAttributeValue(this.MEASUREMENT_ATTRIBUTES, maNamesClone);
     }
     if(observableObjectClasses != null) {
      String[] ooClone = (String[]) observableObjectClasses.clone();
      clone.setAttributeValue(this.OBSERVABLE_OBJECT_CLASSES, ooClone);
     }
     if(scope != null) {
      String scopeClone = scope;
      clone.setAttributeValue(this.SCOPE, scopeClone);
     }
      return clone;
  }
  // ATTRIBUTE OPERATIONS
  //*********************

  /**
   * Returns a list of measurement attributes.
   *
   *@return PerformanceAttributeDescriptor[] List of measurement attribute.
   */
  public PerformanceAttributeDescriptor[] getMeasurementAttributes() throws java.lang.IllegalStateException {
    if(this.isPopulated(this.MEASUREMENT_ATTRIBUTES) == false)
      throw new java.lang.IllegalStateException();

    return (PerformanceAttributeDescriptor[]) this.getAttributeValue(this.MEASUREMENT_ATTRIBUTES);
  }

  /**
   * Sets the measurement attributes to be monitored.
   *
   * <p>
   * The supplied measurement attribute must be supported of all monitored objects.
   *
   * <p>
   * The measurement attributes will be validated when the value object is passed
   * to the performance monitor bean.
   *
   *@param measurmentAttributeNames List of measurement attribute.
   */
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
        throw new java.lang.IllegalArgumentException();

      if((collectionMethod != null) &&
	     (collectionMethod.compareTo(PerformanceAttributeDescriptor.CUMULATIVE_COUNTER) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.GAUGE) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION) != 0) &&
         (collectionMethod.compareTo(PerformanceAttributeDescriptor.STATUS_INSPECTION) != 0))
        throw new java.lang.IllegalArgumentException();

      if((type != 0) &&
	     (type != PerformanceAttributeDescriptor.INTEGER) &&
         (type != PerformanceAttributeDescriptor.REAL) &&
         (type != PerformanceAttributeDescriptor.STRING))
        throw new java.lang.IllegalArgumentException();
    }

    this.setAttributeValue(this.MEASUREMENT_ATTRIBUTES, measurementAttributeNames);
  }

  /**
   * Returns a list of observable object class names.
   *
   *@return String[] List of observable object class names.
   */
  public String[] getObservedObjectClasses() throws java.lang.IllegalStateException {
    if(this.isPopulated(this.OBSERVABLE_OBJECT_CLASSES) == false){
// VP
//System.out.println("Observable object classes is not populated.");

      throw new java.lang.IllegalStateException();
    }

    return (String[]) this.getAttributeValue(this.OBSERVABLE_OBJECT_CLASSES);
  }

  /**
   * Sets the observable object classes that shall be monitored.
   *
   * <p>
   * The observable object classes will be validated when the value object is passed
   * to the performance monitor bean.
   *
   *@param observedObjectClasses List of observable object class names.
   */
  public void setObservedObjectClasses( String[] observedObjectClasses ) throws java.lang.IllegalArgumentException {
    if(observedObjectClasses == null)
      throw new java.lang.IllegalArgumentException();

    for(int i=0; i<observedObjectClasses.length; i++)
          if(observedObjectClasses[i] == null)
            throw new java.lang.IllegalArgumentException();

    this.setAttributeValue(this.OBSERVABLE_OBJECT_CLASSES, observedObjectClasses);
  }

  /**
   * Returns the distinguished name of the object where the scope start from.
   *
   *@return String The distinguished name of the object where the scope start from.
   */
  public String getScope() throws java.lang.IllegalStateException {
    if(this.isPopulated(this.SCOPE) == false)
      throw new java.lang.IllegalStateException();

    return (String) this.getAttributeValue(this.SCOPE);
  }

  /**
   * Sets the distinguished name of the object where the scope shall start from.
   *
   *@param base The distinguished name of the object where the scope shall start from.
   */
  public void setScope( String base ) throws java.lang.IllegalArgumentException {
    if(base == null /*|| this.SCOPE == null*/)
      throw new java.lang.IllegalArgumentException();

    /*
    if(this.isPopulated(this.SCOPE)== false)
      throw new java.lang.IllegalArgumentException();
*/
      this.setAttributeValue(this.SCOPE, base);


  }

  protected boolean isValidAttribute( String attrName, Object attrValue ) {
    boolean isValid = false;

    isValid = super.isValidAttribute(attrName, attrValue);

    if ( attrName != null && attrName.equals( PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES )
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

    if ( attrName != null && attrName.equals( PerformanceMonitorByClassesValue.OBSERVABLE_OBJECT_CLASSES )
        && attrValue != null && attrValue instanceof String[] ) {
        isValid = true;
        String[] obsObjCl = (String[]) attrValue;
        for(int i=0; i<obsObjCl.length; i++)
          if(obsObjCl[i] == null)
            isValid = false;
    }

    //Hooman
    if ( attrName != null && attrName.equals( PerformanceMonitorByClassesValue.SCOPE )
    && attrValue != null && attrValue instanceof String)
         isValid = true;

    return isValid;
  }

  public PerformanceAttributeDescriptor makePerformanceAttributeDescriptor() {
    return new PerformanceAttributeDescriptorImpl();
  }

}
