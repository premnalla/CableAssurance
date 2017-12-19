package ossj.qos.pm.measurement;


import ossj.qos.util.IRPEventImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002,
 * Company:   Ericsson AB
 * @author  Hooman Tahamtani, Katarina Wahlström
 * @version 1.0
 */

public class PmIRPEventImpl extends ossj.qos.util.IRPEventImpl{

  public PmIRPEventImpl() {

    super();
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