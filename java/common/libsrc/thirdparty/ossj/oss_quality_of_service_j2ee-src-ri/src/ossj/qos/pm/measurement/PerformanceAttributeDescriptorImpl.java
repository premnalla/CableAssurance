package ossj.qos.pm.measurement;


import javax.oss.pm.measurement.PerformanceAttributeDescriptor;

//import ossj.qos.pm.util.Log;

import ossj.qos.util.Log;


import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;




/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi, Katarina Wahlström, Hooman Tahamtani
 * @version 0.2
 */

public class PerformanceAttributeDescriptorImpl implements PerformanceAttributeDescriptor {

  private boolean isArray;
  private String collectionMethod;
  private int type;
  private String name;

  public PerformanceAttributeDescriptorImpl(String collectionMethod) {
    this.collectionMethod = collectionMethod;
  }

  public PerformanceAttributeDescriptorImpl() {
  }

  public Object clone() {
    Object clone = null;
    try {
      clone = super.clone();
    }
    catch(CloneNotSupportedException e) {
      Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
    }
    return clone;
  }


  public String getCollectionMethod() {
    return collectionMethod;
  }

  public String getName() {
    return name;
  }

  public int getType() {
    return type;
  }

  public boolean isArray() {
    return isArray;
  }

  public void setName(String name) throws java.lang.IllegalArgumentException {
    if(name == null)
      throw new java.lang.IllegalArgumentException();

    this.name = name;
  }

  public void setType(int type) throws java.lang.IllegalArgumentException {
    if(type != this.INTEGER && type != this.REAL && type != this.STRING)
      throw new java.lang.IllegalArgumentException();

    this.type = type;
  }

  public void setCollectionMethod(String collectionMethod) throws java.lang.IllegalArgumentException{
     if((collectionMethod.compareTo(this.CUMULATIVE_COUNTER) != 0) &&
        (collectionMethod.compareTo(this.DISCRETE_EVENT_REGISTRATION) != 0) &&
        (collectionMethod.compareTo(this.GAUGE) != 0) &&
        (collectionMethod.compareTo(this.STATUS_INSPECTION) != 0))
        throw new java.lang.IllegalArgumentException();

    this.collectionMethod = collectionMethod;
  }

  public void setIsArray(boolean isArray) {
    this.isArray = isArray;
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
