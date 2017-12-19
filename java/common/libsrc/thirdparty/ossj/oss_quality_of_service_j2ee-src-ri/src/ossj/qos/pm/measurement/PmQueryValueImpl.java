package ossj.qos.pm.measurement;


import ossj.qos.*;
import javax.oss.*;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public abstract class PmQueryValueImpl extends QueryValueImpl{

  public PmQueryValueImpl() {

  }
  public String[] getSupportedSerializerTypes() {
    String[] serializerTypes = new String[1];
    serializerTypes[0] = new String( PmXmlSerializerImpl.class.getName());
    return serializerTypes;
  }


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