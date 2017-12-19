package ossj.qos.pm.threshold;

import ossj.qos.util.IRPEventImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.tmxmlri.TmXmlSerializerImpl;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class TmIRPEventImpl extends ossj.qos.util.IRPEventImpl {

  public TmIRPEventImpl() {
    super();
  }

  public String[] getSupportedSerializerTypes() {
    String[] serializerTypes = new String[1];
    serializerTypes[0] = new String( TmXmlSerializerImpl.class.getName());
    return serializerTypes;
  }


  public Serializer makeSerializer( String serializerType )
  throws java.lang.IllegalArgumentException {
    Serializer serializer = null;
    try{
      serializer = new TmXmlSerializerImpl(serializerType);
    }catch(java.lang.IllegalArgumentException e){
      throw new java.lang.IllegalArgumentException(e.getMessage());
    }
    return serializer;
  }

}