package ossj.qos.pm.measurement;


import ossj.qos.AttributeAccessImpl;
import javax.oss.Serializer;
import ossj.qos.xmlri.pmxmlri.PmXmlSerializerImpl;
import ossj.qos.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class PmAttributeAccessImpl {

  public PmAttributeAccessImpl() {
    super();
  }

    // clone this instance
    public Object clone() {
        PmAttributeAccessImpl clone = null;
        try {
            clone = (PmAttributeAccessImpl) super.clone();
            //clone.map = (HashMap) map.clone();
        } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e); // "should not happen"
        }
        return clone;
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