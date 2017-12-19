package ossj.qos.fmri;

import javax.oss.pm.measurement.PerformanceAttributeDescriptor;
import javax.oss.fm.monitor.AlarmCountsValue;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;
import ossj.qos.util.Util;


/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
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
      //System.out.println("Problem cloning PerformanceAttributeDescriptor");
    }
    return clone;
  }

    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in PerformanceAttributeDescriptorImpl instance are equal to the
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof PerformanceAttributeDescriptorImpl ) {
            PerformanceAttributeDescriptorImpl objPerfAttrDesc = (PerformanceAttributeDescriptorImpl)anObject;
            isEqual = ( Util.isEqual(collectionMethod, objPerfAttrDesc.collectionMethod ) &&
                        Util.isEqual( name, objPerfAttrDesc.name ) &&
                        ( type == objPerfAttrDesc.type ) &&
                        ( isArray == objPerfAttrDesc.isArray ) );
        }
        return isEqual;
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

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(" << PerformanceAttributeDescriptorImpl >> \n" );
        buffer.append( Util.rightJustify(30,"isArray = ") + isArray + "\n");
        buffer.append( Util.rightJustify(30,"collectionMethod = ") + collectionMethod + "\n");
        buffer.append( Util.rightJustify(30,"type = ") + type );
        buffer.append(Util.rightJustify(30,"name = ") + name + "\n");

        return buffer.toString();
    }
    /**
     * Returns a serializer (FmXmlserializer)
     *
     * @return Serializer
     */

     /* Added on 2002-03-14
      * to comply with the new version
      * Hooman Tahamtnai, Ericsson Microwave AB
      * Gothenburg, Sweden.
      */
      public Serializer makeSerializer(String seriializerType)
                        throws java.lang.IllegalArgumentException{
         Serializer serializer = null;
         try{
            serializer = new FmXmlSerializerImpl(seriializerType);
         }
         catch (java.lang.IllegalArgumentException ie){
            ie.getMessage();
         }
         catch (Exception ex){
            ex.getMessage();
         }
         return serializer;
      }

      /**
       * Returns all the serializer types than can be created by this factory
       */

       /* Added on 2002-03-14
        * to comply with the new version
        * Hooman Tahamtnai, Ericsson Microwave AB
        * Gothenburg, Sweden.
        */

      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( FmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

}
